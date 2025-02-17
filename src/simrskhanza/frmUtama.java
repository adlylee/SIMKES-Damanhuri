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

import inventory.DlgObatPenyakit;
import setting.DlgRunTeks;
import laporan.DlgSirkulasiBerkas;
import permintaan.DlgCariPermintaanLab;
import permintaan.DlgBookingRegistrasi;
import permintaan.DlgBookingMJKN;
import permintaan.DlgBookingOperasi;
import bridging.DlgSKDPBPJS;
import kepegawaian.DlgPenggajian;
import laporan.DlgMutasiBerkas;
import laporan.DlgBerkasRawat;
import laporan.DlgRetensi;
import inventory.DlgResepObat;
import laporan.DlgDataHAIs;
import bridging.AplicareCekReferensiKamar;
import bridging.AplicareKetersediaanKamar;
import bridging.BPJSCekDetailSEP2;
import bridging.BPJSCekKartu;
import bridging.BPJSCekNIK2;
import bridging.BPJSCekReferensiFaskes;
import bridging.BPJSCekReferensiPenyakit;
import bridging.BPJSCekReferensiPoli;
import bridging.BPJSCekRiwayatRujukanPCare;
import bridging.BPJSCekNoRujukanPCare;
import bridging.BPJSCekNoRujukanRS;
import bridging.BPJSCekReferensiCaraKeluar;
import bridging.BPJSCekReferensiDokter;
import bridging.BPJSCekReferensiDokterDPJP;
import bridging.BPJSCekReferensiKabupaten;
import bridging.BPJSCekReferensiKecamatan;
import bridging.BPJSCekReferensiKelasRawat;
import bridging.BPJSCekReferensiPascaPulang;
import bridging.BPJSCekReferensiPropinsi;
import bridging.BPJSCekReferensiProsedur;
import bridging.BPJSCekReferensiRuangRawat;
import bridging.BPJSCekReferensiSpesialistik;
import bridging.BPJSCekRiwayatRujukanRS;
import bridging.BPJSCekRujukanKartuPCare;
import bridging.BPJSCekRujukanKartuRS;
import bridging.BPJSCekSKDP;
import bridging.BPJSCekTanggalRujukan;
import bridging.BPJSDataSEP;
import bridging.BPJSMapingPoli;
import bridging.BPJSMapingDokterDPJP;
import bridging.BPJSMonitoringKlaim;
import bridging.BPJSMonitoringKunjungan;
import bridging.BPJSRujukanKeluar;
import bridging.BPJSSuratKontrol;
import bridging.INACBGCariCoderNIK;
import bridging.INACBGCoderNIK;
import bridging.INACBGHybrid;
import bridging.InhealthCekEligibilitas;
import bridging.InhealthCekReferensiFaskes;
import bridging.InhealthCekReferensiPoli;
import bridging.InhealthDataSJP;
import bridging.InhealthReferensiJenpelRuang;
import informasi.InformasiAnalisaKamin;
import laporan.DlgDkkSurveilansRalan;
import laporan.DlgFrekuensiPenyakitRanap;
import laporan.DlgFrekuensiPenyakitRanapPerDokter;
import laporan.DlgFrekuensiPenyakitRalan;
import laporan.DlgFrekuensiPenyakitPerRujukMasuk;
import laporan.DlgDkkSurveilansRanap;
import laporan.DlgDkkPenyakitTidakMenularRalan;
import laporan.DlgDkkSurveilansPD3I;
import laporan.DlgRekapDiet;
import setting.DlgUser;
import setting.DlgSetKamarInap;
import setting.DlgSetOtoLokasi;
import setting.DlgSetTarif;
import setting.DlgSetAplikasi;
import setting.DlgSetPenjabLab;
import setting.DlgSetOtoRalan;
import setting.DlgSetRM;
import setting.DlgSetHarga;
import setting.DlgBiayaSekaliMasuk;
import setting.DlgAdmin;
import setting.DlgBiayaHarian;
import setting.DlgSetPenjabBNM;
import inventory.DlgSuplier;
import kepegawaian.DlgBarcode;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import informasi.InformasiJadwal;
import informasi.InformasiKamar;
import informasi.InformasiKamarInap;
import informasi.InformasiTarifLab;
import informasi.InformasiTarifOperasi;
import informasi.InformasiTarifRadiologi;
import informasi.InformasiTarifRalan;
import informasi.InformasiTarifRanap;
import inventaris.InventarisBarang;
import inventaris.InventarisKoleksi;
import inventaris.InventarisJenis;
import inventaris.InventarisKategori;
import inventaris.InventarisMerk;
import inventaris.InventarisProdusen;
import inventaris.InventarisRuang;
import inventaris.InventarisSirkulasi;
import inventory.DlgCariPengambilanUTD;
import inventory.Dlg10ObatTerbanyakPoli;
import inventory.DlgCariPenjualan;
import inventory.DlgDaruratStok;
import inventory.HibahObatBHP;
import inventory.DlgIndustriFarmasi;
import inventory.DlgInputStok;
import inventory.DlgKonversi;
import inventory.DlgMutasiBarang;
import inventory.DlgPembelian;
import inventory.DlgPemesanan;
import inventory.DlgPengambilanUTD;
import inventory.DlgPenjualan;
import inventory.DlgPiutang;
import inventory.DlgProyeksiBeriObat;
import inventory.DlgProyeksiBeriObat2;
import inventory.DlgProyeksiJual;
import inventory.DlgReturBeli;
import inventory.DlgReturJual;
import inventory.DlgReturObatPasien;
import inventory.DlgReturPiutang;
import inventory.DlgSirkulasiBarang;
import inventory.DlgSirkulasiBarang2;
import inventory.DlgStokPasien;
import inventory.DlgPaketOperasi;
import ipsrs.DlgBarangIPSRS;
import ipsrs.DlgCariPengambilanPenunjangUTD;
import ipsrs.DlgJenisIPSRS;
import ipsrs.DlgPembelianIPSRS;
import ipsrs.DlgPengambilanPenunjangUTD;
import ipsrs.DlgPengeluaranIPSRS;
import ipsrs.DlgRBiayaHarianIPSRS;
import ipsrs.DlgRHPembelianIPSRS;
import ipsrs.DlgRHPengeluaranIPSRS;
import ipsrs.DlgSuplierIPSRS;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import keuangan.DlgAkunBayar;
import keuangan.DlgBayarPemesanan;
import keuangan.DlgBayarPiutang;
import keuangan.DlgBubes;
import keuangan.DlgCashflow;
import keuangan.DlgDetailJMDokter;
import keuangan.DlgDetailPotongan;
import keuangan.DlgDetailTambahan;
import keuangan.DlgFeeBacaanEKG;
import keuangan.DlgFeePeriksaRalan;
import keuangan.DlgFeeRujukanRontgen;
import keuangan.DlgFeeVisitDokter;
import keuangan.DlgJnsPerawatanLab;
import keuangan.DlgJnsPerawatanOperasi;
import keuangan.DlgJnsPerawatanRadiologi;
import keuangan.DlgJnsPerawatanUTD;
import keuangan.DlgJurnal;
import keuangan.DlgJurnalHarian;
import keuangan.DlgLabaRugi;
import keuangan.DlgLhtBiaya;
import keuangan.DlgLhtPiutang;
import keuangan.DlgPaymentPoint;
import keuangan.DlgPemasukanLain;
import keuangan.DlgPembayaranRalan;
import keuangan.DlgPembayaranRalanPerHari;
import keuangan.DlgPembayaranRanap;
import keuangan.DlgPembyaranRanapPerhari;
import keuangan.DlgPengaturanRekening;
import keuangan.DlgPengeluaranHarian;
import keuangan.DlgPiutangBelumLunas;
import keuangan.DlgRBJS;
import keuangan.DlgRBJmDokter;
import keuangan.DlgRBJmParamedis;
import keuangan.DlgRBObatBangsal;
import keuangan.DlgRBObatDokterPeresep;
import keuangan.DlgRBObatDokterRalan;
import keuangan.DlgRBObatDokterRanap;
import keuangan.DlgRBObatPercaraBayar;
import keuangan.DlgRBObatPoli;
import keuangan.DlgRBPaketBHP;
import keuangan.DlgRBTindakanDokter;
import keuangan.DlgRBTindakanKamar;
import keuangan.DlgRBTindakanPoli;
import keuangan.DlgRHJS;
import keuangan.DlgRHJmDokter;
import keuangan.DlgRHJmParamedis;
import keuangan.DlgRHPaketBHP;
import keuangan.DlgRekapPerShift;
import keuangan.DlgRekening;
import keuangan.DlgRekeningTahun;
import keuangan.DlgPengaturanSubRekening;
import bridging.ReklasifikasiRalan;
import bridging.ReklasifikasiRanap;
import bridging.SiranapKetersediaanKamar;
import bridging.SisruteCekReferensiAlasanRujuk;
import bridging.SisruteCekReferensiDiagnosa;
import bridging.SisruteCekReferensiFaskes;
import bridging.SisruteRujukanKeluar;
import bridging.SisruteRujukanMasukan;
import informasi.InformasiTelusurKunjunganPasien;
import inventaris.InventarisBarangCSSD;
import inventory.DlgCariPPNObat;
import inventory.DlgDaftarPermintaanResep;
import inventory.DlgDataBatch;
import inventory.DlgGolongan;
import inventory.DlgKategori;
import inventory.DlgKegiatanFarmasi;
import inventory.DlgMetodeRacik;
import inventory.DlgObatPerTanggal;
import inventory.DlgPengeluaranApotek;
import inventory.DlgPenggunaObat;
import inventory.DlgPenjualanPerTanggal;
import inventory.DlgPermintaan;
import inventory.DlgRekapPenerimaan;
import inventory.DlgRekapPermintaan;
import inventory.DlgRiwayatBarangMedis;
import inventory.DlgRiwayatBatch;
import inventory.DlgSirkulasiBarang3;
import inventory.DlgSirkulasiBarang4;
import inventory.DlgSuratPemesanan;
import ipsrs.DlgInputStokIPSRS;
import ipsrs.DlgPemesananIPSRS;
import ipsrs.DlgPengadaanIPSRSPerTanggal;
import ipsrs.DlgPermintaanNonMedis;
import ipsrs.DlgRekapPenerimaanNonMedis;
import ipsrs.DlgRekapPermintaanNonMedis;
import ipsrs.DlgSirkulasiNonMedis;
import ipsrs.DlgStokKeluarIPSRSPerTanggal;
import ipsrs.DlgSuratPemesananNonMedis;
import java.awt.event.KeyListener;
import javax.swing.event.DocumentEvent;
import keuangan.DlgAkunPiutang;
import keuangan.DlgDetailJMDokter2;
import keuangan.DlgDetailTindakan;
import keuangan.DlgDetailVKOK;
import keuangan.DlgHutangObatBelumLunas;
import keuangan.DlgPembayaranPerAKunBayar;
import keuangan.DlgPembayaranPerPoli;
import keuangan.DlgPiutangPercaraBayar;
import keuangan.DlgPiutangRalan;
import keuangan.DlgPiutangRanap;
import keuangan.DlgRBKSO;
import keuangan.DlgRBMenejemen;
import keuangan.DlgRHKSO;
import keuangan.DlgRHMenejemen;
import keuangan.DlgRekapPembayaranPerPoli;
import keuangan.DlgRekapPoliAnak;
import keuangan.DlgRincianPiutangPasien;
import laporan.DlgBulananHAIs;
import laporan.DlgCekEntryRalan;
import laporan.DlgDkkPenyakitMenularRalan;
import laporan.DlgDkkPenyakitMenularRanap;
import laporan.DlgDkkPenyakitTidakMenularRanap;
import laporan.DlgHarianHAIs;
import laporan.DlgHitungALOS;
import laporan.DlgHitungBOR;
import laporan.DlgICD9;
import laporan.DlgKunjunganRalan;
import laporan.DlgKunjunganRanap;
import laporan.DlgPelayananApotek;
import laporan.DlgPelayananRalan;
import laporan.DlgPembatalanPeriksaPerDokter;
import laporan.DlgPenyakitPd3i;
import laporan.DlgRL4A;
import laporan.DlgRL4ASebab;
import laporan.DlgRL4B;
import laporan.DlgRL4BSebab;
import laporan.DlgRl31;
import laporan.DlgRl32;
import laporan.DlgRl33;
import laporan.DlgLaporanStokOpname;
import laporan.DlgRl36;
import laporan.DlgRl37;
import laporan.DlgRl38;
import laporan.DlgSensusHarianPoli;
import laporan.DlgSensusHarianRalan;
import laporan.frmPengaduan;
import kepegawaian.DlgBelum;
import kepegawaian.DlgBerkasKepegawaian;
import kepegawaian.DlgBulanan;
import kepegawaian.DlgBulanan2;
import kepegawaian.DlgHarian;
import kepegawaian.DlgJadwalPegawai;
import kepegawaian.DlgJadwalTambahan;
import kepegawaian.DlgJamMasuk;
import kepegawaian.DlgKegiatanIlmiah;
import kepegawaian.DlgKehadiran;
import kepegawaian.DlgKehadiran2;
import kepegawaian.DlgMasterBerkasPegawai;
import kepegawaian.DlgPulang;
import kepegawaian.DlgRiwayatJabatan;
import kepegawaian.DlgRiwayatNaikGaji;
import kepegawaian.DlgRiwayatPendidikan;
import kepegawaian.DlgRiwayatPenelitian;
import kepegawaian.DlgRiwayatPenghargaan;
import kepegawaian.DlgSidikJari;
import kepegawaian.DlgTemporaryPresensi;
import keuangan.DlgBayarPemesananNonMedis;
import keuangan.DlgHutangNonMedisBelumLunas;
import keuangan.DlgPiutangPerAKunPiutang;
import laporan.DlgDataInsidenKeselamatan;
import laporan.DlgInsidenKeselamatan;
import laporan.DlgPenyakitRanapPerCaraBayar;
import laporan.DlgRanapPerRuang;
import permintaan.DlgCariPermintaanRadiologi;
import setting.DlgClosingKasir;
import setting.DlgSetEmbalase;
import setting.DlgSetHargaKamar;
import setting.DlgSetHargaObatRalan;
import setting.DlgSetHargaObatRanap;
import setting.DlgSetKeterlambatan;
import setting.DlgSetNota;
import keuangan.DlgDeposit;
import keuangan.DlgJnsPerawatanRalan;
import keuangan.DlgPaymentPoint2;
import keuangan.DlgPembayaranPerAKunBayar2;
import keuangan.DlgSaldoAkunPerBulan;
import laporan.DlgHAIsPerBangsal;
import laporan.DlgJumlahMacamDiet;
import laporan.DlgJumlahPorsiDiet;
import laporan.DlgKunjunganLabRalan;
import laporan.DlgKunjunganLabRanap;
import laporan.DlgKunjunganRadRalan;
import laporan.DlgKunjunganRadRanap;
import laporan.DlgPelayananLab;
import laporan.DlgPelayananRadiologi;
import laporan.DlgPerujukLabPerTahun;
import laporan.DlgPerujukRadiologiPerTahun;
import laporan.DlgRekapLabPerTahun;
import laporan.DlgRekapRadiologiPerTahun;
import setting.DlgRunTeksApotek;
import setting.DlgSetInputParsial;
import kepegawaian.DlgJadwal;
import inventory.DlgResepPulang;
import keuangan.DlgPembayaranPerAKunBayar3;
import bridging.DlgDataTB;
import bridging.MyLimsMapping;
import inventaris.KeslingLimbahB3Medis;
import inventaris.KeslingPemakaiaanAirPDAM;
import inventory.DlgKadaluarsaBatch;
import inventory.DlgObatPeresep;
import inventory.DlgSisaStok;
import inventory.DlgLapelfar;
import laporan.DlgPelayananPenunjang;
import laporan.DlgRl35;
import setting.DlgPasswordAsuransi;
import surat.SuratAlmari;
import surat.SuratBalas;
import surat.SuratIndeks;
import surat.SuratKeluar;
import surat.SuratKlasifikasi;
import surat.SuratMap;
import surat.SuratMasuk;
import surat.SuratRak;
import surat.SuratRuang;
import surat.SuratSifat;
import surat.SuratStatus;
import tranfusidarah.UTDCariPenyerahanDarah;
import tranfusidarah.UTDDonor;
import tranfusidarah.UTDMedisRusak;
import tranfusidarah.UTDCekalDarah;
import tranfusidarah.UTDKomponenDarah;
import tranfusidarah.UTDPemisahanDarah;
import tranfusidarah.UTDPenunjangRusak;
import tranfusidarah.UTDPenyerahanDarah;
import tranfusidarah.UTDStokDarah;
import informasi.InformasiKerohanian;
import java.awt.geom.RoundRectangle2D;
import laporan.DlgFrekuensiPenyakitRalanDanRanap;
import laporan.DlgFrekuensiPenyakitRanapBaru;
import laporan.DlgJumlahPasien;
import laporan.DlgRekapKegiatanRad;
import permintaan.DlgCariPermintaanUTD;
import mpp.DlgSkriningMPP;
import perlap.DlgLapRBA;
import perlap.DlgPermintaanRBA;
import setting.DlgPindahRiwPasien;
import setting.DlgSetHariLibur;
import setting.DlgSetMCU;
import setting.DlgSetUtd;
import setting.DlgTrialWhatsapp;

/**
 *
 * @author perpustakaan
 */
public class frmUtama extends javax.swing.JFrame {
    private final Connection koneksi=koneksiDB.condb();
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private final DlgKasirRalan kasirralan=new DlgKasirRalan(this,false);
    private final DlgAbout About=new DlgAbout(this,false);   
    private final INACBGHybrid inacbgklaim=new INACBGHybrid(this,false);
    private final INACBGCariCoderNIK cariNIK=new INACBGCariCoderNIK(this,false);
    private final InformasiKamarInap informasikamar=new InformasiKamarInap(this,false);
    private final InformasiAnalisaKamin analisakamin=new InformasiAnalisaKamin(this,false);
    private static frmUtama myInstance;
    private PreparedStatement ps;
    private ResultSet rs;
    private final Properties prop = new Properties();     
    private int jmlmenu=0;
    private String coder_nik="",pilihpage="",judulform="";
    /** Creates new form frmUtama */
    private frmUtama() {
        super();
        initComponents();
        setIconImage(new ImageIcon(super.getClass().getResource("/picture/Hospital .png")).getImage());
        
        this.setExtendedState(MAXIMIZED_BOTH);
//        this.setSize(screen.width,screen.height);
        //desktop.setPreferredSize(new Dimension(800,1000));
        //desktop.setAutoscrolls(true);
        edAdmin.setDocument(new batasInput((byte)100).getKata(edAdmin));
        edPwd.setDocument(new batasInput((byte)100).getKata(edPwd));
        PassLama.setDocument(new batasInput((byte)100).getKata(PassLama));
        Passbaru1.setDocument(new batasInput((byte)100).getKata(Passbaru1));
        PassBaru2.setDocument(new batasInput((byte)100).getKata(PassBaru2));

        DlgLogin.setSize(600,310);
        //<a href="https://www.freepik.com/free-vector/security-background-design_1028145.htm#query=password%20key&position=10&from_view=keyword">Image by GraphiqaStock</a> on Freepik
        //<a href="https://www.flaticon.com/free-icons/bed" title="bed icons">Bed icons created by Freepik - Flaticon</a>
        DlgLogin.setVisible(false);
        DlgLogin.setLocationRelativeTo(null);
        
        WindowInput.setSize(349,180);
        WindowInput.setVisible(false);
        WindowInput.setLocationRelativeTo(null);

        cariNIK.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    if(cariNIK.getTable().getSelectedRow()!= -1){                   
                        coder_nik=cariNIK.getTable().getValueAt(cariNIK.getTable().getSelectedRow(),2).toString();
                        isTutup();
                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        try {
                            inacbgklaim.loadURL("http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"inacbg/login.php?act=login&usere=admin&passwordte=akusayangsamakamu&page="+pilihpage+"&codernik="+coder_nik);                    
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : "+ex);
                        }

                        inacbgklaim.setJudul(judulform);
                        inacbgklaim.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
                        inacbgklaim.setLocationRelativeTo(PanelUtama);                    
                        inacbgklaim.setVisible(true);        
                        DlgHome.dispose();
                        setCursor(Cursor.getDefaultCursor());
                    }                         
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        lblTgl.setText(tanggal.getSelectedItem().toString());
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
        } catch (Exception e) {
            System.out.println("Notif Setting : "+e);
        }
        
        FlayMenu.setVisible(false);
        TCari.setVisible(false);
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        isTampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        isTampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        isTampil();
                    }
                }
            });
        } 
        
        try{    
            if(prop.getProperty("MENUTRANSPARAN").equals("yes")){
//                com.sun.awt.AWTUtilities.setWindowOpacity(DlgHome,0.62f);
            }     
        }catch(Exception e){            
        }             
    }
    
    public static frmUtama getInstance() {
        if (myInstance == null)
            myInstance = new frmUtama();

        return myInstance;
    }
    
 
    //private DlgMenu menu=new DlgMenu(this,false); 
    private final Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();   

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DlgLogin = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        internalFrame3 = new widget.InternalFrame();
        jLabel6 = new javax.swing.JLabel();
        BtnLogin = new widget.Button();
        BtnCancel = new widget.Button();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        edAdmin = new widget.PasswordBox();
        edPwd = new widget.PasswordBox();
        WindowInput = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        PassLama = new widget.TextBox();
        jLabel9 = new widget.Label();
        BtnClosePass = new widget.Button();
        BtnSimpanPass = new widget.Button();
        jLabel10 = new widget.Label();
        Passbaru1 = new widget.TextBox();
        jLabel12 = new widget.Label();
        PassBaru2 = new widget.TextBox();
        DlgHome = new javax.swing.JDialog();
        panelMenu = new widget.InternalFrame();
        panelisi2 = new widget.panelisi();
        label36 = new widget.Label();
        button1 = new widget.Button();
        label35 = new widget.Label();
        cmbMenu = new widget.ComboBox();
        TCari = new widget.TextBox();
        ChkInput = new widget.CekBox();
        scrollPane2 = new widget.ScrollPane();
        Panelmenu = new widget.panelGlass();
        btnBarcode = new widget.ButtonBig();
        btnICD = new widget.ButtonBig();
        btnObat = new widget.ButtonBig();
        btnObatPenyakit = new widget.ButtonBig();
        btnKamar = new widget.ButtonBig();
        btnTindakanRalan = new widget.ButtonBig();
        btnDokter = new widget.ButtonBig();
        btnPegawai = new widget.ButtonBig();
        btnPasien = new widget.ButtonBig();
        btnRegistrasi = new widget.ButtonBig();
        btnRalan = new widget.ButtonBig();
        btnKamarInap = new widget.ButtonBig();
        btnRanap = new widget.ButtonBig();
        btnResepObat = new widget.ButtonBig();
        btnRujukPasien = new widget.ButtonBig();
        btnBeriObat = new widget.ButtonBig();
        btnPasienMati = new widget.ButtonBig();
        btnAdmin = new widget.ButtonBig();
        btnUser = new widget.ButtonBig();
        btnVakum = new widget.ButtonBig();
        btnDisplay = new widget.ButtonBig();
        btnSetupHarga = new widget.ButtonBig();
        btnSuplier = new widget.ButtonBig();
        btnJnsBarang = new widget.ButtonBig();
        btnKonversi = new widget.ButtonBig();
        btnSatuan = new widget.ButtonBig();
        btnCashFlow = new widget.ButtonBig();
        btnBubes = new widget.ButtonBig();
        btnPostingJurnal = new widget.ButtonBig();
        btnRekeningTahun = new widget.ButtonBig();
        btnRekening = new widget.ButtonBig();
        btnPembelian = new widget.ButtonBig();
        btnPenjualan = new widget.ButtonBig();
        btnPiutang = new widget.ButtonBig();
        btnBayarPiutang = new widget.ButtonBig();
        btnOpname = new widget.ButtonBig();
        btnReturBeli = new widget.ButtonBig();
        btnReturJual = new widget.ButtonBig();
        btnSirkulasi = new widget.ButtonBig();
        btnKeuntungan = new widget.ButtonBig();
        btnLabaRugi = new widget.ButtonBig();
        btnReturPiutang = new widget.ButtonBig();
        btnAnalisaKamar = new widget.ButtonBig();
        btnRHDOkter = new widget.ButtonBig();
        btnRBDokter = new widget.ButtonBig();
        btnTagihanMasuk = new widget.ButtonBig();
        btnResume = new widget.ButtonBig();
        btnDiet = new widget.ButtonBig();
        btnRHParamedis = new widget.ButtonBig();
        btnRBParamedis = new widget.ButtonBig();
        btnKasir = new widget.ButtonBig();
        btnLahir = new widget.ButtonBig();
        btnSetBiayaHarian = new widget.ButtonBig();
        btnJenisInventaris = new widget.ButtonBig();
        btnKategoriInventaris = new widget.ButtonBig();
        btnLihatPiutang = new widget.ButtonBig();
        btnLaboratorium = new widget.ButtonBig();
        btnRalanMasuk = new widget.ButtonBig();
        btnSetupAplikasi = new widget.ButtonBig();
        btnSetOtoRalan = new widget.ButtonBig();
        btnRanapMasuk = new widget.ButtonBig();
        btnProdusenInventaris = new widget.ButtonBig();
        btnSetBiayaMasukSekali = new widget.ButtonBig();
        btnPaketOperasi = new widget.ButtonBig();
        btnTagihanOperasi = new widget.ButtonBig();
        BtnJadwal = new widget.ButtonBig();
        btnMerkInventaris = new widget.ButtonBig();
        btnRuangInventaris = new widget.ButtonBig();
        btnBarangInventaris = new widget.ButtonBig();
        btnInventaris = new widget.ButtonBig();
        btnSirkulasiInventaris = new widget.ButtonBig();
        btnFrekuensiRalan = new widget.ButtonBig();
        btnFrekuensiRanap = new widget.ButtonBig();
        btnSetupOtoLokasi = new widget.ButtonBig();
        btnTagihanPoli = new widget.ButtonBig();
        btnRujukMasuk = new widget.ButtonBig();
        btnTracker = new widget.ButtonBig();
        btnTindakanRanap = new widget.ButtonBig();
        btnSetupJamInap = new widget.ButtonBig();
        btnStokObatPasien = new widget.ButtonBig();
        btnTarifLab = new widget.ButtonBig();
        btnSetPenjab = new widget.ButtonBig();
        btnTagihanObatPoli = new widget.ButtonBig();
        btnTagihanObatBangsal = new widget.ButtonBig();
        btnReturPasien = new widget.ButtonBig();
        btnKeuntunganObatRanap = new widget.ButtonBig();
        btnPenggajian = new widget.ButtonBig();
        btnRekapPresensi = new widget.ButtonBig();
        btnRekapHarian = new widget.ButtonBig();
        btnRekapBulanan = new widget.ButtonBig();
        btnDeposit = new widget.ButtonBig();
        btnSetupRM = new widget.ButtonBig();
        btnResepPulang = new widget.ButtonBig();
        btnSetupTarif = new widget.ButtonBig();
        btnBarangIpsrs = new widget.ButtonBig();
        btnPembelianIpsrs = new widget.ButtonBig();
        btnPengeluaranIpsrs = new widget.ButtonBig();
        btnRHMasukIpsrs = new widget.ButtonBig();
        btnRHKeluarIpsrs = new widget.ButtonBig();
        btnRBiayaIpsrs = new widget.ButtonBig();
        btnTarifRadiologi = new widget.ButtonBig();
        btnPeriksaRadiologi = new widget.ButtonBig();
        btnTagihanRalanPerhari = new widget.ButtonBig();
        btnTagihanRanapPerhari = new widget.ButtonBig();
        btnSetupEmbalase = new widget.ButtonBig();
        btnSirkulasiBerkas = new widget.ButtonBig();
        btnObatPasienRalan = new widget.ButtonBig();
        btnObatPasienRanap = new widget.ButtonBig();
        btnPemesanan = new widget.ButtonBig();
        btnPengeluaran = new widget.ButtonBig();
        btnTambahanBiaya = new widget.ButtonBig();
        btnPotonganBiaya = new widget.ButtonBig();
        btnJMDetailDokter = new widget.ButtonBig();
        btnIGD = new widget.ButtonBig();
        btnSetObatRalan = new widget.ButtonBig();
        btnSetObatRanap = new widget.ButtonBig();
        btnPenyakitPD3I = new widget.ButtonBig();
        btnSurveilansPD3I = new widget.ButtonBig();
        btnSurveilansRalan = new widget.ButtonBig();
        btnDiagnosa = new widget.ButtonBig();
        btnSurveilansRanap = new widget.ButtonBig();
        btnPnyTakMenularRanap = new widget.ButtonBig();
        btnPnyTakMenularRalan = new widget.ButtonBig();
        btnKunjunganRalan = new widget.ButtonBig();
        btnRl31 = new widget.ButtonBig();
        btnRl32 = new widget.ButtonBig();
        btnRl33 = new widget.ButtonBig();
        btnRl37 = new widget.ButtonBig();
        btnRl38 = new widget.ButtonBig();
        btnRl34 = new widget.ButtonBig();
        btnRl4a = new widget.ButtonBig();
        btnRl36 = new widget.ButtonBig();
        btnRl4b = new widget.ButtonBig();
        btnRl4asebab = new widget.ButtonBig();
        btnRl4bsebab = new widget.ButtonBig();
        btnTagihanDokter = new widget.ButtonBig();
        btnSidikJari = new widget.ButtonBig();
        btnJamPresensi = new widget.ButtonBig();
        btnJadwalPegawai = new widget.ButtonBig();
        btnSetupNota = new widget.ButtonBig();
        BtnDpjp = new widget.ButtonBig();
        btnMutasiBarang = new widget.ButtonBig();
        btnfee_visit_dokter = new widget.ButtonBig();
        btnfee_bacaan_ekg = new widget.ButtonBig();
        btnfee_rujukan_rontgen = new widget.ButtonBig();
        btnfee_rujukan_ranap = new widget.ButtonBig();
        btnfee_ralan = new widget.ButtonBig();
        btnakun_bayar = new widget.ButtonBig();
        btnbayar_pemesanan = new widget.ButtonBig();
        btnObatPasienPeresep = new widget.ButtonBig();
        btnJenisIpsrs = new widget.ButtonBig();
        btnPemasukanLain = new widget.ButtonBig();
        btnPengaturanRekening = new widget.ButtonBig();
        btnJadwalTambahan = new widget.ButtonBig();
        btnClosingKasir = new widget.ButtonBig();
        btnKeterlambatanPresensi = new widget.ButtonBig();
        btnSetHargaKamar = new widget.ButtonBig();
        btnRekapPershift = new widget.ButtonBig();
        btnCekBPJSNik = new widget.ButtonBig();
        btnCekBPJSKartu = new widget.ButtonBig();
        btnCekBPJSRiwayatRujukanPCare = new widget.ButtonBig();
        btnRekapPresensi2 = new widget.ButtonBig();
        btnObatPerCaraBayar = new widget.ButtonBig();
        btnKunjunganRanap = new widget.ButtonBig();
        btnPaymentPoint = new widget.ButtonBig();
        btnCekBPJSNomorRujukanPCare = new widget.ButtonBig();
        btnICD9 = new widget.ButtonBig();
        btnDaruratStok = new widget.ButtonBig();
        btnRetensiRM = new widget.ButtonBig();
        btnTemporaryPresensi = new widget.ButtonBig();
        btnJurnalHarian = new widget.ButtonBig();
        btnSirkulasi2 = new widget.ButtonBig();
        btnCekBPJSDiagnosa = new widget.ButtonBig();
        btnCekBPJSPoli = new widget.ButtonBig();
        btnIndustriFarmasi = new widget.ButtonBig();
        btnRHJasaSarana = new widget.ButtonBig();
        btnRBJasaSarana = new widget.ButtonBig();
        btnRHPaketBHP = new widget.ButtonBig();
        btnRBPaketBHP = new widget.ButtonBig();
        btnPiutangBelumLunas = new widget.ButtonBig();
        btnCekBPJSFaskes = new widget.ButtonBig();
        btnBPJSSEP = new widget.ButtonBig();
        btnPengambilanUTD = new widget.ButtonBig();
        btnTarifUtd = new widget.ButtonBig();
        btnPengambilanUTD2 = new widget.ButtonBig();
        btnUTDMedisRusak = new widget.ButtonBig();
        btnPengambilanPenunjangUTD = new widget.ButtonBig();
        btnPengambilanPenunjangUTD2 = new widget.ButtonBig();
        btnUTDPenunjangRusak = new widget.ButtonBig();
        btnSuplierIPSRS = new widget.ButtonBig();
        btnUTDDonorDarah = new widget.ButtonBig();
        btnMonitoringKlaim = new widget.ButtonBig();
        btnUTDCekalDarah = new widget.ButtonBig();
        btnUTDKomponenDarah = new widget.ButtonBig();
        btnUTDStokDarah = new widget.ButtonBig();
        btnUTDPemisahanDarah = new widget.ButtonBig();
        btnHarianKamar = new widget.ButtonBig();
        btnRincianPiutangPasien = new widget.ButtonBig();
        btnKeuntunganObat2 = new widget.ButtonBig();
        btnReklasifikasiRalan = new widget.ButtonBig();
        btnReklasifikasiRanap = new widget.ButtonBig();
        btnUTDPenyerahanDarah = new widget.ButtonBig();
        btnHutangObat = new widget.ButtonBig();
        btnRiwayatBarangMedis = new widget.ButtonBig();
        btnSensusHarianPoli = new widget.ButtonBig();
        btnAplicareReferensiKamar = new widget.ButtonBig();
        btnAplicareKetersediaanKamar = new widget.ButtonBig();
        btnInaCBGKlaimBaruOtomatis = new widget.ButtonBig();
        btnInaCBGKlaimBaruManual = new widget.ButtonBig();
        btnInaCBGCoderNIK = new widget.ButtonBig();
        btnMutasiBerkas = new widget.ButtonBig();
        btnAkunPiutang = new widget.ButtonBig();
        btnRHKSO = new widget.ButtonBig();
        btnRBKSO = new widget.ButtonBig();
        btnRHMenejemen = new widget.ButtonBig();
        btnRBMenejemen = new widget.ButtonBig();
        btnCekEligibilitasInhealth = new widget.ButtonBig();
        btnReferensiKamarInhealth = new widget.ButtonBig();
        btnCekInhealthPoli = new widget.ButtonBig();
        btnCekInhealthFaskes = new widget.ButtonBig();
        btnInhealthSJP = new widget.ButtonBig();
        btnPiutangRalan = new widget.ButtonBig();
        btnPiutangRanap = new widget.ButtonBig();
        btnPiutangPerCaraBayar = new widget.ButtonBig();
        btnLamaPelayananRalan = new widget.ButtonBig();
        btnCatatanPasien = new widget.ButtonBig();
        btnDataHAIs = new widget.ButtonBig();
        btnHarianHAIs = new widget.ButtonBig();
        btnBulananHAIs = new widget.ButtonBig();
        btnHitungBor = new widget.ButtonBig();
        btnPerusahaan = new widget.ButtonBig();
        btnDaftarPermintaanResep = new widget.ButtonBig();
        btnLamaPelayananApotek = new widget.ButtonBig();
        btnHitungAlos = new widget.ButtonBig();
        btnDetailTindakan = new widget.ButtonBig();
        btnRekapPoliAnak = new widget.ButtonBig();
        btnBerkasDigitalPerawatan = new widget.ButtonBig();
        btnPnyMenularRanap = new widget.ButtonBig();
        btnPnyMenularRalan = new widget.ButtonBig();
        btnKategoriBarang = new widget.ButtonBig();
        btnGolonganBarang = new widget.ButtonBig();
        btnObatPerTanggal = new widget.ButtonBig();
        btnPenjualanPerTanggal = new widget.ButtonBig();
        btnPembatalanPeriksaDokter = new widget.ButtonBig();
        btnPembayaranPerUnit = new widget.ButtonBig();
        btnRekapPembayaranPerUnit = new widget.ButtonBig();
        btnPengadaanIPSRSPerTanggal = new widget.ButtonBig();
        btnStokKeluarIPSRSPerTanggal = new widget.ButtonBig();
        btnCekEntryRalan = new widget.ButtonBig();
        btnInaCBGKlaimBaruManual2 = new widget.ButtonBig();
        btnPermintaanMedis = new widget.ButtonBig();
        btnRekapPermintaanMedis = new widget.ButtonBig();
        btnSuratPemesananMedis = new widget.ButtonBig();
        btnPermintaanNonMedis = new widget.ButtonBig();
        btnRekapPermintaanNonMedis = new widget.ButtonBig();
        btnSuratPemesananNonMedis = new widget.ButtonBig();
        btnCekReferensiProsedurBPJS = new widget.ButtonBig();
        btnCekReferensiKelasRawatBPJS = new widget.ButtonBig();
        btnCekReferensiDokterBPJS = new widget.ButtonBig();
        btnCekReferensiSpesialistikBPJS = new widget.ButtonBig();
        btnCekReferensiRuangRawatBPJS = new widget.ButtonBig();
        btnCekReferensiCaraKeluarBPJS = new widget.ButtonBig();
        btnCekReferensiPascaPulangBPJS = new widget.ButtonBig();
        btnDetailVKOK = new widget.ButtonBig();
        btnCekBPJSNomorRujukanRS = new widget.ButtonBig();
        btnCekBPJSRujukanKartuPCare = new widget.ButtonBig();
        btnCekBPJSRujukanKartuRS = new widget.ButtonBig();
        btnRujukanKeluarBPJS = new widget.ButtonBig();
        btnStokKeluarMedis = new widget.ButtonBig();
        btnJMDetailDokter2 = new widget.ButtonBig();
        btnPengaduan = new widget.ButtonBig();
        btnSensusHarianRalan = new widget.ButtonBig();
        btnMetodeRacik = new widget.ButtonBig();
        btnPembayaranAkunBayar = new widget.ButtonBig();
        btnPenggunaObatResep = new widget.ButtonBig();
        btnRekapPenerimaanObat = new widget.ButtonBig();
        btnMasterBerkasPegawai = new widget.ButtonBig();
        btnBerkasPegawai = new widget.ButtonBig();
        btnRiwayatJabatan = new widget.ButtonBig();
        btnRiwayatPendidikan = new widget.ButtonBig();
        btnRiwayatNaikGaji = new widget.ButtonBig();
        btnKegiatanIlmiah = new widget.ButtonBig();
        btnRiwayatPenghargaan = new widget.ButtonBig();
        btnRiwayatPenelitian = new widget.ButtonBig();
        btnPenerimaanNonMedis = new widget.ButtonBig();
        btnBayarPesanNonMedis = new widget.ButtonBig();
        btnHutangNonMedis = new widget.ButtonBig();
        btnRekapPenerimaanNonMedis = new widget.ButtonBig();
        btnInsidenKeselamatan = new widget.ButtonBig();
        btnInsidenKeselamatanPasien = new widget.ButtonBig();
        btnRiwayatBatch = new widget.ButtonBig();
        btnPiutangPerAkunPiutang = new widget.ButtonBig();
        btnSuku = new widget.ButtonBig();
        btnBahasa = new widget.ButtonBig();
        btnCacatFisik = new widget.ButtonBig();
        btnJadwalOperasi = new widget.ButtonBig();
        btnMapingPoliBPJS = new widget.ButtonBig();
        btnBarangCSSD = new widget.ButtonBig();
        btnSKDPBPJS = new widget.ButtonBig();
        btnBookingRegistrasi = new widget.ButtonBig();
        btnCekReferensiPropinsiBPJS = new widget.ButtonBig();
        btnCekReferensiKabupatenBPJS = new widget.ButtonBig();
        btnCekReferensiKecamatanBPJS = new widget.ButtonBig();
        btnCekReferensiDokterDPJPBPJS = new widget.ButtonBig();
        btnCekBPJSRiwayatRujukanRS = new widget.ButtonBig();
        btnCekBPJSTanggalRujukan = new widget.ButtonBig();
        btnPermintaanLab = new widget.ButtonBig();
        btnPermintaanRadiologi = new widget.ButtonBig();
        btnSuratIndeks = new widget.ButtonBig();
        btnSuratMap = new widget.ButtonBig();
        btnSuratAlmari = new widget.ButtonBig();
        btnSuratRak = new widget.ButtonBig();
        btnSuratRuang = new widget.ButtonBig();
        btnSuratKlasifikasi = new widget.ButtonBig();
        btnSuratStatus = new widget.ButtonBig();
        btnSuratSifat = new widget.ButtonBig();
        btnSuratBalas = new widget.ButtonBig();
        btnSuratMasuk = new widget.ButtonBig();
        btnSirkulasi3 = new widget.ButtonBig();
        btnRanapPerRuang = new widget.ButtonBig();
        btnPenyakitRanapCaraBayar = new widget.ButtonBig();
        btnSetInputParsial = new widget.ButtonBig();
        btnLamaPelayananRadiologi = new widget.ButtonBig();
        btnLamaPelayananLab = new widget.ButtonBig();
        btnCekSEP = new widget.ButtonBig();
        btnSuratKeluar = new widget.ButtonBig();
        btnKegiatanFarmasi = new widget.ButtonBig();
        btnOpnameIPSRS = new widget.ButtonBig();
        btnSirkulasiNonMedis = new widget.ButtonBig();
        btnRekapLabPerTahun = new widget.ButtonBig();
        btnPerujukLabPerTahun = new widget.ButtonBig();
        btnRekapRadiologiPerTahun = new widget.ButtonBig();
        btnPerujukRadiologiPerTahun = new widget.ButtonBig();
        btnJumlahPorsiDiet = new widget.ButtonBig();
        btnJumlahMacamDiet = new widget.ButtonBig();
        btnPaymentPoint2 = new widget.ButtonBig();
        btnPembayaranAkunBayar2 = new widget.ButtonBig();
        btnHAIsBangsal = new widget.ButtonBig();
        btnPPNObat = new widget.ButtonBig();
        btnSaldoAkunPerBulan = new widget.ButtonBig();
        btnDisplayApotek = new widget.ButtonBig();
        btnCekSisruteFaskes = new widget.ButtonBig();
        btnCekSisruteAlasanRujuk = new widget.ButtonBig();
        btnCekSisruteDiagnosa = new widget.ButtonBig();
        btnRujukanMasukSisrute = new widget.ButtonBig();
        btnRujukanKeluarSisrute = new widget.ButtonBig();
        btnCekBPJSSKDP = new widget.ButtonBig();
        btnDataBatch = new widget.ButtonBig();
        btnKunjunganLabRalan = new widget.ButtonBig();
        btnKunjunganLabRanap = new widget.ButtonBig();
        btnKunjunganRadRalan = new widget.ButtonBig();
        btnKunjunganRadRanap = new widget.ButtonBig();
        btnPembayaranAkunBayar3 = new widget.ButtonBig();
        btnPasswordAsuransi = new widget.ButtonBig();
        btnDataSITT = new widget.ButtonBig();
        btnSiranapKetersediaanKamar = new widget.ButtonBig();
        btnKadaluarsaBatch = new widget.ButtonBig();
        btnSisaStok = new widget.ButtonBig();
        btnObatPerResep = new widget.ButtonBig();
        btnPemakaianAirPDAM = new widget.ButtonBig();
        btnLimbahB3Medis = new widget.ButtonBig();
        btn10Obat = new widget.ButtonBig();
        btnMapingDokterDPJP = new widget.ButtonBig();
        tanggal = new widget.Tanggal();
        btnDataPenjualan = new widget.ButtonBig();
        btnInputPenjualan = new widget.ButtonBig();
        btnDataPenyerahanDarah = new widget.ButtonBig();
        btnResepObatDepan = new widget.ButtonBig();
        btnHibahObatBHP = new widget.ButtonBig();
        btnRl35 = new widget.ButtonBig();
        btnLapStokOp = new widget.ButtonBig();
        btnLapPelFar = new widget.ButtonBig();
        btnPelayananPenunjang = new widget.ButtonBig();
        btnFrekuensiRanapPerDokter = new widget.ButtonBig();
        btnMonitoringKunjungan = new widget.ButtonBig();
        btnFrekuensiRalanDanRanap = new widget.ButtonBig();
        btnPelFar = new widget.ButtonBig();
        btnKerohanian = new widget.ButtonBig();
        btnMyLimsMapping = new widget.ButtonBig();
        btnSetPenjabBNM = new widget.ButtonBig();
        btnRekapDiet = new widget.ButtonBig();
        btnUTDPermintaan = new widget.ButtonBig();
        btnSetSubAkun = new widget.ButtonBig();
        btnFrekuensiPenyakitRanapBaru = new widget.ButtonBig();
        btnFrekuensiPerPerujuk = new widget.ButtonBig();
        btnPaketObatOp = new widget.ButtonBig();
        btnPermintaanMPP = new widget.ButtonBig();
        btnSirkulasiObat4 = new widget.ButtonBig();
        btnLapRBA = new widget.ButtonBig();
        btnBookingMJKN = new widget.ButtonBig();
        btnSetKompUtd = new widget.ButtonBig();
        btnRekapKegiatanRad = new widget.ButtonBig();
        btnPermintaanRBA = new widget.ButtonBig();
        btnSetHariLibur = new widget.ButtonBig();
        btnTrialSendWA = new widget.ButtonBig();
        btnSetPaketMCU = new widget.ButtonBig();
        btnLapKunjunganPasien = new widget.ButtonBig();
        btnPindahRiw = new widget.ButtonBig();
        btnSurkonBPJS = new widget.ButtonBig();
        internalFrame1 = new widget.InternalFrame();
        BtnMenu = new widget.ButtonBig();
        jSeparator4 = new javax.swing.JSeparator();
        BtnToolReg = new widget.ButtonBig();
        btnToolIGD = new widget.ButtonBig();
        jSeparator5 = new javax.swing.JSeparator();
        btnToolLab = new widget.ButtonBig();
        btnToolRad = new widget.ButtonBig();
        BtnToolJualObat = new widget.ButtonBig();
        jSeparator9 = new javax.swing.JSeparator();
        BtnToolKamnap = new widget.ButtonBig();
        BtnToolKasir = new widget.ButtonBig();
        jSeparator7 = new javax.swing.JSeparator();
        BtnLog = new widget.ButtonBig();
        BtnClose = new widget.ButtonBig();
        internalFrame4 = new widget.InternalFrame();
        lblStts = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblUser = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblTgl = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        PanelUtama = new javax.swing.JPanel();
        scrollPane1 = new widget.ScrollPane();
        PanelWall = new usu.widget.glass.PanelGlass();
        panelJudul = new usu.widget.glass.PanelGlass();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        FlayMenu = new usu.widget.glass.PanelGlass();
        MenuBar = new widget.MenuBar();
        jMenu1 = new javax.swing.JMenu();
        MnLogin = new javax.swing.JMenuItem();
        MnGantiPassword = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        MenuKeluar = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        MnBelumDatang1 = new javax.swing.JMenuItem();
        MnBelumDatang = new javax.swing.JMenuItem();
        MnSudahPulang = new javax.swing.JMenuItem();
        MnRekapBulanan = new javax.swing.JMenuItem();
        MnRekapHadir = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        MnRekapBulanan1 = new javax.swing.JMenuItem();
        MnRekapBulanan3 = new javax.swing.JMenuItem();
        MnRekapBulanan2 = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        MnSudahPulang1 = new javax.swing.JMenuItem();
        MnSudahPulang3 = new javax.swing.JMenuItem();
        MnSudahPulang6 = new javax.swing.JMenuItem();
        MnSudahPulang4 = new javax.swing.JMenuItem();
        MnSudahPulang2 = new javax.swing.JMenuItem();
        MnSudahPulang5 = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        MnRekapHadir1 = new javax.swing.JMenuItem();
        MnInfoBed = new javax.swing.JMenuItem();
        MnInfoBed2 = new javax.swing.JMenuItem();
        MnInfoBed1 = new javax.swing.JMenuItem();
        MnInfoBed3 = new javax.swing.JMenuItem();
        MnInfoBed4 = new javax.swing.JMenuItem();
        MnInfoBed5 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        MnAnjungan = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        MnRekapHadir3 = new javax.swing.JMenuItem();
        MnRekapHadir4 = new javax.swing.JMenuItem();
        MnRekapHadir5 = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        MnRekapHadir6 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();

        DlgLogin.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgLogin.setName("DlgLogin"); // NOI18N
        DlgLogin.setUndecorated(true);
        DlgLogin.setResizable(false);

        internalFrame2.setBackground(new java.awt.Color(0, 0, 0));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setWarnaAtas(new java.awt.Color(224, 226, 255));
        internalFrame2.setWarnaBawah(new java.awt.Color(244, 244, 255));
        internalFrame2.setLayout(null);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setWarnaAtas(new java.awt.Color(204, 255, 102));
        internalFrame3.setWarnaBawah(new java.awt.Color(237, 237, 250));
        internalFrame3.setLayout(null);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/shield.png"))); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        internalFrame3.add(jLabel6);
        jLabel6.setBounds(355, 10, 240, 300);

        BtnLogin.setForeground(new java.awt.Color(60, 60, 60));
        BtnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/lock.png"))); // NOI18N
        BtnLogin.setMnemonic('Z');
        BtnLogin.setText("Log-in");
        BtnLogin.setToolTipText("Alt+Z");
        BtnLogin.setGlassColor(new java.awt.Color(0, 102, 255));
        BtnLogin.setName("BtnLogin"); // NOI18N
        BtnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLoginActionPerformed(evt);
            }
        });
        internalFrame3.add(BtnLogin);
        BtnLogin.setBounds(50, 220, 105, 32);

        BtnCancel.setForeground(new java.awt.Color(60, 60, 60));
        BtnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnCancel.setMnemonic('Y');
        BtnCancel.setText("Batal");
        BtnCancel.setToolTipText("Alt+Y");
        BtnCancel.setGlassColor(new java.awt.Color(235, 255, 245));
        BtnCancel.setName("BtnCancel"); // NOI18N
        BtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelActionPerformed(evt);
            }
        });
        internalFrame3.add(BtnCancel);
        BtnCancel.setBounds(230, 220, 105, 32);

        jLabel5.setForeground(new java.awt.Color(60, 60, 60));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Password :");
        jLabel5.setName("jLabel5"); // NOI18N
        internalFrame3.add(jLabel5);
        jLabel5.setBounds(30, 150, 80, 23);

        jLabel4.setForeground(new java.awt.Color(60, 60, 60));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Username :");
        jLabel4.setName("jLabel4"); // NOI18N
        internalFrame3.add(jLabel4);
        jLabel4.setBounds(30, 120, 80, 23);

        edAdmin.setForeground(new java.awt.Color(60, 60, 60));
        edAdmin.setToolTipText("Silahkan masukkan ID Admin");
        edAdmin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        edAdmin.setName("edAdmin"); // NOI18N
        edAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edAdminKeyPressed(evt);
            }
        });
        internalFrame3.add(edAdmin);
        edAdmin.setBounds(120, 120, 220, 23);

        edPwd.setForeground(new java.awt.Color(60, 60, 60));
        edPwd.setToolTipText("Silahkan masukkan password");
        edPwd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        edPwd.setName("edPwd"); // NOI18N
        edPwd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edPwdKeyPressed(evt);
            }
        });
        internalFrame3.add(edPwd);
        edPwd.setBounds(120, 150, 220, 23);

        internalFrame2.add(internalFrame3);
        internalFrame3.setBounds(0, 0, 600, 310);

        DlgLogin.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        DlgLogin.setShape(new RoundRectangle2D.Double(0, 0, 600, 310, 25, 25));

        WindowInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput.setModal(true);
        WindowInput.setName("WindowInput"); // NOI18N
        WindowInput.setUndecorated(true);
        WindowInput.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ubah Password ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(null);

        PassLama.setHighlighter(null);
        PassLama.setName("PassLama"); // NOI18N
        internalFrame6.add(PassLama);
        PassLama.setBounds(128, 30, 190, 23);

        jLabel9.setText("Password Lama :");
        jLabel9.setName("jLabel9"); // NOI18N
        internalFrame6.add(jLabel9);
        jLabel9.setBounds(0, 30, 125, 23);

        BtnClosePass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnClosePass.setMnemonic('2');
        BtnClosePass.setText("Tutup");
        BtnClosePass.setToolTipText("Alt+2");
        BtnClosePass.setName("BtnClosePass"); // NOI18N
        BtnClosePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnClosePassActionPerformed(evt);
            }
        });
        BtnClosePass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnClosePassKeyPressed(evt);
            }
        });
        internalFrame6.add(BtnClosePass);
        BtnClosePass.setBounds(230, 130, 100, 30);

        BtnSimpanPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanPass.setMnemonic('1');
        BtnSimpanPass.setText("Simpan");
        BtnSimpanPass.setToolTipText("Alt+1");
        BtnSimpanPass.setName("BtnSimpanPass"); // NOI18N
        BtnSimpanPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanPassActionPerformed(evt);
            }
        });
        BtnSimpanPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanPassKeyPressed(evt);
            }
        });
        internalFrame6.add(BtnSimpanPass);
        BtnSimpanPass.setBounds(20, 130, 100, 30);

        jLabel10.setText("Password Baru :");
        jLabel10.setName("jLabel10"); // NOI18N
        internalFrame6.add(jLabel10);
        jLabel10.setBounds(0, 60, 125, 23);

        Passbaru1.setHighlighter(null);
        Passbaru1.setName("Passbaru1"); // NOI18N
        internalFrame6.add(Passbaru1);
        Passbaru1.setBounds(128, 60, 190, 23);

        jLabel12.setText("Password Baru :");
        jLabel12.setName("jLabel12"); // NOI18N
        internalFrame6.add(jLabel12);
        jLabel12.setBounds(0, 90, 125, 23);

        PassBaru2.setHighlighter(null);
        PassBaru2.setName("PassBaru2"); // NOI18N
        internalFrame6.add(PassBaru2);
        PassBaru2.setBounds(128, 90, 190, 23);

        WindowInput.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        DlgHome.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgHome.setAlwaysOnTop(true);
        DlgHome.setIconImage(null);
        DlgHome.setName("DlgHome"); // NOI18N
        DlgHome.setUndecorated(true);
        DlgHome.setResizable(false);

        panelMenu.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 225, 205)), "::[ Menu Utama ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(60, 60, 60))); // NOI18N
        panelMenu.setName("panelMenu"); // NOI18N
        panelMenu.setPreferredSize(new java.awt.Dimension(2412, 3653));
        panelMenu.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 250, 228)));
        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 40));
        panelisi2.setWarnaBawah(new java.awt.Color(255, 255, 254));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        label36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(1, 23));
        panelisi2.add(label36);

        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        button1.setGlassColor(new java.awt.Color(255, 255, 255));
        button1.setMinimumSize(new java.awt.Dimension(28, 23));
        button1.setName("button1"); // NOI18N
        button1.setPreferredSize(new java.awt.Dimension(25, 23));
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        panelisi2.add(button1);

        label35.setForeground(new java.awt.Color(60, 60, 60));
        label35.setText("Tampilkan Menu :");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(105, 23));
        panelisi2.add(label35);

        cmbMenu.setForeground(new java.awt.Color(60, 60, 60));
        cmbMenu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[A] Registrasi, Tagihan Ranap & Ralan, Pelayanan & Billing Pasien", "[C] Presensi, Manajemen & Penggajian Pegawai Rumah Sakit", "[D] Transaksi Inventory Obat, BHP Medis, Alat Kesehatan Pasien", "[E] Transaksi Inventory Barang Non Medis", "[F] Aset, Inventaris Barang Rumah Sakit & Instalasi Kesehatan Lingkungan", "[H] Olah Data Tagihan Rawat Inap & Rawat Jalan", "[I] Olah Data Penyakit, Laporan DKK, Laporal RL & Laporan Internal Rumah Sakit", "[J] Tarif Pelayanan & Keuangan Rumah Sakit", "[K] Bridging VClaim, Aplicare, PCare, INACBG, Kemenkes & Pihak Ke 3", "[L] Olah Data Pasien", "[M] Unit Pelayanan Tranfusi Darah", "[O] Manajemen Surat Masuk & Keluar", "[P] Pengaturan Program Aplikasi HMS" }));
        cmbMenu.setName("cmbMenu"); // NOI18N
        cmbMenu.setPreferredSize(new java.awt.Dimension(470, 23));
        cmbMenu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMenuItemStateChanged(evt);
            }
        });
        panelisi2.add(cmbMenu);

        TCari.setForeground(new java.awt.Color(60, 60, 60));
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(470, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi2.add(TCari);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setSelected(true);
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setOpaque(false);
        ChkInput.setPreferredSize(new java.awt.Dimension(25, 23));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        panelisi2.add(ChkInput);

        panelMenu.add(panelisi2, java.awt.BorderLayout.PAGE_START);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 250, 228)));
        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setName("scrollPane2"); // NOI18N

        Panelmenu.setBorder(null);
        Panelmenu.setMinimumSize(new java.awt.Dimension(1975, 2826));
        Panelmenu.setName("Panelmenu"); // NOI18N
        Panelmenu.setLayout(new java.awt.GridLayout(0, 12));

        btnBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360484263_barcode.png"))); // NOI18N
        btnBarcode.setText("Barcode Presensi");
        btnBarcode.setIconTextGap(0);
        btnBarcode.setName("btnBarcode"); // NOI18N
        btnBarcode.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarcodeActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBarcode);

        btnICD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnICD.setText("ICD 10");
        btnICD.setIconTextGap(0);
        btnICD.setName("btnICD"); // NOI18N
        btnICD.setPreferredSize(new java.awt.Dimension(200, 90));
        btnICD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnICDActionPerformed(evt);
            }
        });
        Panelmenu.add(btnICD);

        btnObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360815855_laboratory.png"))); // NOI18N
        btnObat.setText("Obat, Alkes & BHP");
        btnObat.setIconTextGap(0);
        btnObat.setName("btnObat"); // NOI18N
        btnObat.setPreferredSize(new java.awt.Dimension(200, 90));
        btnObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObatActionPerformed(evt);
            }
        });
        Panelmenu.add(btnObat);

        btnObatPenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360484848_applications-science.png"))); // NOI18N
        btnObatPenyakit.setText("Obat Penyakit");
        btnObatPenyakit.setIconTextGap(0);
        btnObatPenyakit.setName("btnObatPenyakit"); // NOI18N
        btnObatPenyakit.setPreferredSize(new java.awt.Dimension(200, 90));
        btnObatPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObatPenyakitActionPerformed(evt);
            }
        });
        Panelmenu.add(btnObatPenyakit);

        btnKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/industry.png"))); // NOI18N
        btnKamar.setText("Kamar");
        btnKamar.setIconTextGap(0);
        btnKamar.setName("btnKamar"); // NOI18N
        btnKamar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKamarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKamar);

        btnTindakanRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/plaster.png"))); // NOI18N
        btnTindakanRalan.setText("Tarif Ralan");
        btnTindakanRalan.setIconTextGap(0);
        btnTindakanRalan.setName("btnTindakanRalan"); // NOI18N
        btnTindakanRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTindakanRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTindakanRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTindakanRalan);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/doctor2.png"))); // NOI18N
        btnDokter.setText("Dokter");
        btnDokter.setIconTextGap(0);
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDokter);

        btnPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/receptionist.png"))); // NOI18N
        btnPegawai.setText("Petugas");
        btnPegawai.setIconTextGap(0);
        btnPegawai.setName("btnPegawai"); // NOI18N
        btnPegawai.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPegawaiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPegawai);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/patient.png"))); // NOI18N
        btnPasien.setText("Pasien");
        btnPasien.setIconTextGap(0);
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPasien);

        btnRegistrasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist.png"))); // NOI18N
        btnRegistrasi.setText("Registrasi");
        btnRegistrasi.setIconTextGap(0);
        btnRegistrasi.setName("btnRegistrasi"); // NOI18N
        btnRegistrasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRegistrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRegistrasi);

        btnRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/folder.png"))); // NOI18N
        btnRalan.setText("Tindakan Ralan");
        btnRalan.setIconTextGap(0);
        btnRalan.setName("btnRalan"); // NOI18N
        btnRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRalan);

        btnKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Home.png"))); // NOI18N
        btnKamarInap.setText("Kamar Inap");
        btnKamarInap.setIconTextGap(0);
        btnKamarInap.setName("btnKamarInap"); // NOI18N
        btnKamarInap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKamarInapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKamarInap);

        btnRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/bedroom.png"))); // NOI18N
        btnRanap.setText("Tindakan Ranap");
        btnRanap.setIconTextGap(0);
        btnRanap.setName("btnRanap"); // NOI18N
        btnRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRanap);

        btnResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/stock_task.png"))); // NOI18N
        btnResepObat.setText("No. Resep");
        btnResepObat.setIconTextGap(0);
        btnResepObat.setName("btnResepObat"); // NOI18N
        btnResepObat.setPreferredSize(new java.awt.Dimension(200, 90));
        btnResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResepObatActionPerformed(evt);
            }
        });
        Panelmenu.add(btnResepObat);

        btnRujukPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485357758_Doctor.png"))); // NOI18N
        btnRujukPasien.setText("Rujukan Keluar");
        btnRujukPasien.setIconTextGap(0);
        btnRujukPasien.setName("btnRujukPasien"); // NOI18N
        btnRujukPasien.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRujukPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRujukPasienActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRujukPasien);

        btnBeriObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/first_aid_kit.png"))); // NOI18N
        btnBeriObat.setText("Beri Obat/BHP");
        btnBeriObat.setIconTextGap(0);
        btnBeriObat.setName("btnBeriObat"); // NOI18N
        btnBeriObat.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBeriObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBeriObatActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBeriObat);

        btnPasienMati.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Ambulance.png"))); // NOI18N
        btnPasienMati.setText("Pasien Meninggal");
        btnPasienMati.setIconTextGap(0);
        btnPasienMati.setName("btnPasienMati"); // NOI18N
        btnPasienMati.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPasienMati.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienMatiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPasienMati);

        btnAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/preferences-desktop-cryptography.png"))); // NOI18N
        btnAdmin.setText("Set Admin");
        btnAdmin.setIconTextGap(0);
        btnAdmin.setName("btnAdmin"); // NOI18N
        btnAdmin.setPreferredSize(new java.awt.Dimension(200, 90));
        btnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminActionPerformed(evt);
            }
        });
        Panelmenu.add(btnAdmin);

        btnUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360484978_application-pgp-signature.png"))); // NOI18N
        btnUser.setText("Set User");
        btnUser.setIconTextGap(0);
        btnUser.setName("btnUser"); // NOI18N
        btnUser.setPreferredSize(new java.awt.Dimension(200, 90));
        btnUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserActionPerformed(evt);
            }
        });
        Panelmenu.add(btnUser);

        btnVakum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486615_remove-from-database.png"))); // NOI18N
        btnVakum.setText("Vakum Table");
        btnVakum.setIconTextGap(0);
        btnVakum.setName("btnVakum"); // NOI18N
        btnVakum.setPreferredSize(new java.awt.Dimension(200, 90));
        Panelmenu.add(btnVakum);

        btnDisplay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/find.png"))); // NOI18N
        btnDisplay.setText("Display Antrian Registrasi & Poli");
        btnDisplay.setIconTextGap(0);
        btnDisplay.setName("btnDisplay"); // NOI18N
        btnDisplay.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDisplay);

        btnSetupHarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487093_price.png"))); // NOI18N
        btnSetupHarga.setText("Set Harga Obat");
        btnSetupHarga.setIconTextGap(0);
        btnSetupHarga.setName("btnSetupHarga"); // NOI18N
        btnSetupHarga.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetupHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetupHargaActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetupHarga);

        btnSuplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485357959_truck.png"))); // NOI18N
        btnSuplier.setText("Suplier Obat/Alkes/BHP");
        btnSuplier.setIconTextGap(0);
        btnSuplier.setName("btnSuplier"); // NOI18N
        btnSuplier.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuplierActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuplier);

        btnJnsBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Card_file.png"))); // NOI18N
        btnJnsBarang.setText("Jenis Obat, Alkes & BHP");
        btnJnsBarang.setIconTextGap(0);
        btnJnsBarang.setName("btnJnsBarang"); // NOI18N
        btnJnsBarang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnJnsBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJnsBarangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnJnsBarang);

        btnKonversi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/ark2.png"))); // NOI18N
        btnKonversi.setText("Konversi Satuan");
        btnKonversi.setIconTextGap(0);
        btnKonversi.setName("btnKonversi"); // NOI18N
        btnKonversi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKonversi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKonversiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKonversi);

        btnSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/bag1.png"))); // NOI18N
        btnSatuan.setText("Satuan Barang");
        btnSatuan.setIconTextGap(0);
        btnSatuan.setName("btnSatuan"); // NOI18N
        btnSatuan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSatuanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSatuan);

        btnCashFlow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486845_23.png"))); // NOI18N
        btnCashFlow.setText("Cash Flow");
        btnCashFlow.setIconTextGap(0);
        btnCashFlow.setName("btnCashFlow"); // NOI18N
        btnCashFlow.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCashFlow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCashFlowActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCashFlow);

        btnBubes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnBubes.setText("Buku Besar");
        btnBubes.setIconTextGap(0);
        btnBubes.setName("btnBubes"); // NOI18N
        btnBubes.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBubes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBubesActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBubes);

        btnPostingJurnal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485642_edit-notes.png"))); // NOI18N
        btnPostingJurnal.setText("Posting Jurnal");
        btnPostingJurnal.setIconTextGap(0);
        btnPostingJurnal.setName("btnPostingJurnal"); // NOI18N
        btnPostingJurnal.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPostingJurnal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPostingJurnalActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPostingJurnal);

        btnRekeningTahun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/money_bag.png"))); // NOI18N
        btnRekeningTahun.setText("Rekening Tahun");
        btnRekeningTahun.setIconTextGap(0);
        btnRekeningTahun.setName("btnRekeningTahun"); // NOI18N
        btnRekeningTahun.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekeningTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekeningTahunActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekeningTahun);

        btnRekening.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/kwrite.png"))); // NOI18N
        btnRekening.setText("Akun Rekening");
        btnRekening.setIconTextGap(0);
        btnRekening.setName("btnRekening"); // NOI18N
        btnRekening.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekening.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekeningActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekening);

        btnPembelian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487067_calculator.png"))); // NOI18N
        btnPembelian.setText("Pengadaan Obat & BHP");
        btnPembelian.setIconTextGap(0);
        btnPembelian.setName("btnPembelian"); // NOI18N
        btnPembelian.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPembelianActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPembelian);

        btnPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/cashbox.png"))); // NOI18N
        btnPenjualan.setText("Penjualan Obat & BHP");
        btnPenjualan.setIconTextGap(0);
        btnPenjualan.setName("btnPenjualan"); // NOI18N
        btnPenjualan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenjualanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPenjualan);

        btnPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist.png"))); // NOI18N
        btnPiutang.setText("Piutang Obat & BHP");
        btnPiutang.setIconTextGap(0);
        btnPiutang.setName("btnPiutang"); // NOI18N
        btnPiutang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPiutangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPiutang);

        btnBayarPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404046811_money.png"))); // NOI18N
        btnBayarPiutang.setText("Bayar Piutang");
        btnBayarPiutang.setIconTextGap(0);
        btnBayarPiutang.setName("btnBayarPiutang"); // NOI18N
        btnBayarPiutang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBayarPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarPiutangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBayarPiutang);

        btnOpname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487078_shipping.png"))); // NOI18N
        btnOpname.setText("Stok Opname Obat & BHP");
        btnOpname.setIconTextGap(0);
        btnOpname.setName("btnOpname"); // NOI18N
        btnOpname.setPreferredSize(new java.awt.Dimension(200, 90));
        btnOpname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpnameActionPerformed(evt);
            }
        });
        Panelmenu.add(btnOpname);

        btnReturBeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360816189_arrow_down.png"))); // NOI18N
        btnReturBeli.setText("Retur Ke Suplier");
        btnReturBeli.setIconTextGap(0);
        btnReturBeli.setName("btnReturBeli"); // NOI18N
        btnReturBeli.setPreferredSize(new java.awt.Dimension(200, 90));
        btnReturBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturBeliActionPerformed(evt);
            }
        });
        Panelmenu.add(btnReturBeli);

        btnReturJual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486142_shopping_cart.png"))); // NOI18N
        btnReturJual.setText("Retur Dari Pembeli");
        btnReturJual.setIconTextGap(0);
        btnReturJual.setName("btnReturJual"); // NOI18N
        btnReturJual.setPreferredSize(new java.awt.Dimension(200, 90));
        btnReturJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturJualActionPerformed(evt);
            }
        });
        Panelmenu.add(btnReturJual);

        btnSirkulasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487125_system-restart-panel.png"))); // NOI18N
        btnSirkulasi.setText("Sirkulasi Obat, Alkes & BHP");
        btnSirkulasi.setIconTextGap(0);
        btnSirkulasi.setName("btnSirkulasi"); // NOI18N
        btnSirkulasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSirkulasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSirkulasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSirkulasi);

        btnKeuntungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/coins.png"))); // NOI18N
        btnKeuntungan.setText("Keuntungan Penjualan");
        btnKeuntungan.setIconTextGap(0);
        btnKeuntungan.setName("btnKeuntungan"); // NOI18N
        btnKeuntungan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKeuntungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeuntunganActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKeuntungan);

        btnLabaRugi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486822_20.png"))); // NOI18N
        btnLabaRugi.setText("Keuangan");
        btnLabaRugi.setIconTextGap(0);
        btnLabaRugi.setName("btnLabaRugi"); // NOI18N
        btnLabaRugi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLabaRugi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLabaRugiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnLabaRugi);

        btnReturPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/custom-reports.png"))); // NOI18N
        btnReturPiutang.setText("Retur Piutang Pembeli");
        btnReturPiutang.setIconTextGap(0);
        btnReturPiutang.setName("btnReturPiutang"); // NOI18N
        btnReturPiutang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnReturPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturPiutangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnReturPiutang);

        btnAnalisaKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485357890_hospital.png"))); // NOI18N
        btnAnalisaKamar.setText("Informasi Kamar");
        btnAnalisaKamar.setIconTextGap(0);
        btnAnalisaKamar.setName("btnAnalisaKamar"); // NOI18N
        btnAnalisaKamar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnAnalisaKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalisaKamarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnAnalisaKamar);

        btnRHDOkter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnRHDOkter.setText("Harian Dokter");
        btnRHDOkter.setIconTextGap(0);
        btnRHDOkter.setName("btnRHDOkter"); // NOI18N
        btnRHDOkter.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRHDOkter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRHDOkterActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRHDOkter);

        btnRBDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnRBDokter.setText("Bulanan Dokter");
        btnRBDokter.setIconTextGap(0);
        btnRBDokter.setName("btnRBDokter"); // NOI18N
        btnRBDokter.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRBDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRBDokterActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRBDokter);

        btnTagihanMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404046800_Cash_register.png"))); // NOI18N
        btnTagihanMasuk.setText("Tagihan Masuk");
        btnTagihanMasuk.setIconTextGap(0);
        btnTagihanMasuk.setName("btnTagihanMasuk"); // NOI18N
        btnTagihanMasuk.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTagihanMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagihanMasukActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTagihanMasuk);

        btnResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360816018_tests.png"))); // NOI18N
        btnResume.setText("Riwayat Perawatan");
        btnResume.setIconTextGap(0);
        btnResume.setName("btnResume"); // NOI18N
        btnResume.setPreferredSize(new java.awt.Dimension(200, 90));
        btnResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResumeActionPerformed(evt);
            }
        });
        Panelmenu.add(btnResume);

        btnDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486936_pie-chart.png"))); // NOI18N
        btnDiet.setText("Diet Pasien");
        btnDiet.setIconTextGap(0);
        btnDiet.setName("btnDiet"); // NOI18N
        btnDiet.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDietActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDiet);

        btnRHParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485894_add-notes.png"))); // NOI18N
        btnRHParamedis.setText("Harian Paramedis");
        btnRHParamedis.setIconTextGap(0);
        btnRHParamedis.setName("btnRHParamedis"); // NOI18N
        btnRHParamedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRHParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRHParamedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRHParamedis);

        btnRBParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485894_add-notes.png"))); // NOI18N
        btnRBParamedis.setText("Bulanan Paramedis");
        btnRBParamedis.setIconTextGap(0);
        btnRBParamedis.setName("btnRBParamedis"); // NOI18N
        btnRBParamedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRBParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRBParamedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRBParamedis);

        btnKasir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/cashbox.png"))); // NOI18N
        btnKasir.setText("Kasir Ralan");
        btnKasir.setIconTextGap(0);
        btnKasir.setName("btnKasir"); // NOI18N
        btnKasir.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKasir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKasirActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKasir);

        btnLahir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/baby-girl.png"))); // NOI18N
        btnLahir.setText("Kelahiran Bayi");
        btnLahir.setIconTextGap(0);
        btnLahir.setName("btnLahir"); // NOI18N
        btnLahir.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLahir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLahirActionPerformed(evt);
            }
        });
        Panelmenu.add(btnLahir);

        btnSetBiayaHarian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486845_23.png"))); // NOI18N
        btnSetBiayaHarian.setText("Biaya Harian");
        btnSetBiayaHarian.setIconTextGap(0);
        btnSetBiayaHarian.setName("btnSetBiayaHarian"); // NOI18N
        btnSetBiayaHarian.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetBiayaHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetBiayaHarianActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetBiayaHarian);

        btnJenisInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/cabinet.png"))); // NOI18N
        btnJenisInventaris.setText("Jenis");
        btnJenisInventaris.setIconTextGap(0);
        btnJenisInventaris.setName("btnJenisInventaris"); // NOI18N
        btnJenisInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnJenisInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJenisInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnJenisInventaris);

        btnKategoriInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487078_shipping.png"))); // NOI18N
        btnKategoriInventaris.setText("Kategori");
        btnKategoriInventaris.setIconTextGap(0);
        btnKategoriInventaris.setName("btnKategoriInventaris"); // NOI18N
        btnKategoriInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKategoriInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKategoriInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKategoriInventaris);

        btnLihatPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist_pencil-o.png"))); // NOI18N
        btnLihatPiutang.setText("Piutang Pasien");
        btnLihatPiutang.setIconTextGap(0);
        btnLihatPiutang.setName("btnLihatPiutang"); // NOI18N
        btnLihatPiutang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLihatPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLihatPiutangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnLihatPiutang);

        btnLaboratorium.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481002039_laboratory.png"))); // NOI18N
        btnLaboratorium.setText("Periksa Lab");
        btnLaboratorium.setIconTextGap(0);
        btnLaboratorium.setName("btnLaboratorium"); // NOI18N
        btnLaboratorium.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLaboratorium.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaboratoriumActionPerformed(evt);
            }
        });
        Panelmenu.add(btnLaboratorium);

        btnRalanMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404047007_02.png"))); // NOI18N
        btnRalanMasuk.setText("Pembayaran Ralan");
        btnRalanMasuk.setIconTextGap(0);
        btnRalanMasuk.setName("btnRalanMasuk"); // NOI18N
        btnRalanMasuk.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRalanMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRalanMasukActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRalanMasuk);

        btnSetupAplikasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/local_network.png"))); // NOI18N
        btnSetupAplikasi.setText("Set Aplikasi");
        btnSetupAplikasi.setIconTextGap(0);
        btnSetupAplikasi.setName("btnSetupAplikasi"); // NOI18N
        btnSetupAplikasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetupAplikasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetupAplikasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetupAplikasi);

        btnSetOtoRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/stethoscope (1).png"))); // NOI18N
        btnSetOtoRalan.setText("Set Oto Ralan");
        btnSetOtoRalan.setIconTextGap(0);
        btnSetOtoRalan.setName("btnSetOtoRalan"); // NOI18N
        btnSetOtoRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetOtoRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetOtoRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetOtoRalan);

        btnRanapMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404047007_02.png"))); // NOI18N
        btnRanapMasuk.setText("Pembayaran Ranap");
        btnRanapMasuk.setIconTextGap(0);
        btnRanapMasuk.setName("btnRanapMasuk"); // NOI18N
        btnRanapMasuk.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRanapMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRanapMasukActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRanapMasuk);

        btnProdusenInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/industry.png"))); // NOI18N
        btnProdusenInventaris.setText("Produsen");
        btnProdusenInventaris.setIconTextGap(0);
        btnProdusenInventaris.setName("btnProdusenInventaris"); // NOI18N
        btnProdusenInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnProdusenInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdusenInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnProdusenInventaris);

        btnSetBiayaMasukSekali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486845_23.png"))); // NOI18N
        btnSetBiayaMasukSekali.setText("Biaya Masuk Sekali");
        btnSetBiayaMasukSekali.setIconTextGap(0);
        btnSetBiayaMasukSekali.setName("btnSetBiayaMasukSekali"); // NOI18N
        btnSetBiayaMasukSekali.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetBiayaMasukSekali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetBiayaMasukSekaliActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetBiayaMasukSekali);

        btnPaketOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487111_stock_paste.png"))); // NOI18N
        btnPaketOperasi.setText("Tarif Operasi/VK");
        btnPaketOperasi.setIconTextGap(0);
        btnPaketOperasi.setName("btnPaketOperasi"); // NOI18N
        btnPaketOperasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPaketOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaketOperasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPaketOperasi);

        btnTagihanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/surgeon.png"))); // NOI18N
        btnTagihanOperasi.setText("Operasi/VK");
        btnTagihanOperasi.setIconTextGap(0);
        btnTagihanOperasi.setName("btnTagihanOperasi"); // NOI18N
        btnTagihanOperasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTagihanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagihanOperasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTagihanOperasi);

        BtnJadwal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/doctor 3.png"))); // NOI18N
        BtnJadwal.setText("Jadwal Praktek");
        BtnJadwal.setIconTextGap(0);
        BtnJadwal.setName("BtnJadwal"); // NOI18N
        BtnJadwal.setPreferredSize(new java.awt.Dimension(200, 90));
        BtnJadwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJadwalActionPerformed(evt);
            }
        });
        Panelmenu.add(BtnJadwal);

        btnMerkInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/bag1.png"))); // NOI18N
        btnMerkInventaris.setText("Merk");
        btnMerkInventaris.setIconTextGap(0);
        btnMerkInventaris.setName("btnMerkInventaris"); // NOI18N
        btnMerkInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnMerkInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMerkInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnMerkInventaris);

        btnRuangInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/bedroom.png"))); // NOI18N
        btnRuangInventaris.setText("Ruang");
        btnRuangInventaris.setIconTextGap(0);
        btnRuangInventaris.setName("btnRuangInventaris"); // NOI18N
        btnRuangInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRuangInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRuangInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRuangInventaris);

        btnBarangInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/fax.png"))); // NOI18N
        btnBarangInventaris.setText("Koleksi");
        btnBarangInventaris.setIconTextGap(0);
        btnBarangInventaris.setName("btnBarangInventaris"); // NOI18N
        btnBarangInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBarangInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBarangInventaris);

        btnInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486921_bar-code.png"))); // NOI18N
        btnInventaris.setText("Inventaris");
        btnInventaris.setIconTextGap(0);
        btnInventaris.setName("btnInventaris"); // NOI18N
        btnInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnInventaris);

        btnSirkulasiInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487125_system-restart-panel.png"))); // NOI18N
        btnSirkulasiInventaris.setText("Sirkulasi Inventaris");
        btnSirkulasiInventaris.setIconTextGap(0);
        btnSirkulasiInventaris.setName("btnSirkulasiInventaris"); // NOI18N
        btnSirkulasiInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSirkulasiInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSirkulasiInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSirkulasiInventaris);

        btnFrekuensiRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnFrekuensiRalan.setText("RL 5.3 10 Besar Penyakit Ralan");
        btnFrekuensiRalan.setIconTextGap(0);
        btnFrekuensiRalan.setName("btnFrekuensiRalan"); // NOI18N
        btnFrekuensiRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnFrekuensiRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFrekuensiRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnFrekuensiRalan);

        btnFrekuensiRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnFrekuensiRanap.setText("RL 5.4 10 Besar Penyakit Ranap");
        btnFrekuensiRanap.setIconTextGap(0);
        btnFrekuensiRanap.setName("btnFrekuensiRanap"); // NOI18N
        btnFrekuensiRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnFrekuensiRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFrekuensiRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnFrekuensiRanap);

        btnSetupOtoLokasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/our_process_2.png"))); // NOI18N
        btnSetupOtoLokasi.setText("Set Oto Lokasi");
        btnSetupOtoLokasi.setIconTextGap(0);
        btnSetupOtoLokasi.setName("btnSetupOtoLokasi"); // NOI18N
        btnSetupOtoLokasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetupOtoLokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetupOtoLokasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetupOtoLokasi);

        btnTagihanPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404047106_emblem-money.png"))); // NOI18N
        btnTagihanPoli.setText("Harian Dokter Poli");
        btnTagihanPoli.setIconTextGap(0);
        btnTagihanPoli.setName("btnTagihanPoli"); // NOI18N
        btnTagihanPoli.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTagihanPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagihanPoliActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTagihanPoli);

        btnRujukMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_vector_65_13_473800.png"))); // NOI18N
        btnRujukMasuk.setText("Rujukan Masuk");
        btnRujukMasuk.setIconTextGap(0);
        btnRujukMasuk.setName("btnRujukMasuk"); // NOI18N
        btnRujukMasuk.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRujukMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRujukMasukActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRujukMasuk);

        btnTracker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/receptionist.png"))); // NOI18N
        btnTracker.setText("Tracker Login");
        btnTracker.setIconTextGap(0);
        btnTracker.setName("btnTracker"); // NOI18N
        btnTracker.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTracker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrackerActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTracker);

        btnTindakanRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/doctor (2).png"))); // NOI18N
        btnTindakanRanap.setText("Tarif Ranap");
        btnTindakanRanap.setIconTextGap(0);
        btnTindakanRanap.setName("btnTindakanRanap"); // NOI18N
        btnTindakanRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTindakanRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTindakanRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTindakanRanap);

        btnSetupJamInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Time.png"))); // NOI18N
        btnSetupJamInap.setText("Set Kamar Inap");
        btnSetupJamInap.setIconTextGap(0);
        btnSetupJamInap.setName("btnSetupJamInap"); // NOI18N
        btnSetupJamInap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetupJamInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetupJamInapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetupJamInap);

        btnStokObatPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487078_shipping.png"))); // NOI18N
        btnStokObatPasien.setText("Stok Obat Pasien");
        btnStokObatPasien.setIconTextGap(0);
        btnStokObatPasien.setName("btnStokObatPasien"); // NOI18N
        btnStokObatPasien.setPreferredSize(new java.awt.Dimension(200, 90));
        btnStokObatPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStokObatPasienActionPerformed(evt);
            }
        });
        Panelmenu.add(btnStokObatPasien);

        btnTarifLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnTarifLab.setText("Tarif Lab");
        btnTarifLab.setIconTextGap(0);
        btnTarifLab.setName("btnTarifLab"); // NOI18N
        btnTarifLab.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTarifLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTarifLabActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTarifLab);

        btnSetPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/user3.png"))); // NOI18N
        btnSetPenjab.setText("Set P.J. Unit Penunjang");
        btnSetPenjab.setIconTextGap(0);
        btnSetPenjab.setName("btnSetPenjab"); // NOI18N
        btnSetPenjab.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetPenjabActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetPenjab);

        btnTagihanObatPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360815855_laboratory.png"))); // NOI18N
        btnTagihanObatPoli.setText("Obat Per Poli");
        btnTagihanObatPoli.setIconTextGap(0);
        btnTagihanObatPoli.setName("btnTagihanObatPoli"); // NOI18N
        btnTagihanObatPoli.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTagihanObatPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagihanObatPoliActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTagihanObatPoli);

        btnTagihanObatBangsal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360815855_laboratory.png"))); // NOI18N
        btnTagihanObatBangsal.setText("Obat Per Kamar");
        btnTagihanObatBangsal.setIconTextGap(0);
        btnTagihanObatBangsal.setName("btnTagihanObatBangsal"); // NOI18N
        btnTagihanObatBangsal.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTagihanObatBangsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagihanObatBangsalActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTagihanObatBangsal);

        btnReturPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360815295_medical_case.png"))); // NOI18N
        btnReturPasien.setText("Retur Obat Ranap");
        btnReturPasien.setIconTextGap(0);
        btnReturPasien.setName("btnReturPasien"); // NOI18N
        btnReturPasien.setPreferredSize(new java.awt.Dimension(200, 90));
        btnReturPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturPasienActionPerformed(evt);
            }
        });
        Panelmenu.add(btnReturPasien);

        btnKeuntunganObatRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/coins.png"))); // NOI18N
        btnKeuntunganObatRanap.setText("Keuntungan Beri Obat ");
        btnKeuntunganObatRanap.setIconTextGap(0);
        btnKeuntunganObatRanap.setName("btnKeuntunganObatRanap"); // NOI18N
        btnKeuntunganObatRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKeuntunganObatRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeuntunganObatRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKeuntunganObatRanap);

        btnPenggajian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404046603_wallet.png"))); // NOI18N
        btnPenggajian.setText("Kepegawaian & Gaji");
        btnPenggajian.setIconTextGap(0);
        btnPenggajian.setName("btnPenggajian"); // NOI18N
        btnPenggajian.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPenggajian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenggajianActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPenggajian);

        btnRekapPresensi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/sign-up.png"))); // NOI18N
        btnRekapPresensi.setText("Rekap Kehadiran");
        btnRekapPresensi.setIconTextGap(0);
        btnRekapPresensi.setName("btnRekapPresensi"); // NOI18N
        btnRekapPresensi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapPresensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapPresensiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekapPresensi);

        btnRekapHarian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/rekap.png"))); // NOI18N
        btnRekapHarian.setText("Presensi Harian");
        btnRekapHarian.setIconTextGap(0);
        btnRekapHarian.setName("btnRekapHarian"); // NOI18N
        btnRekapHarian.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapHarianActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekapHarian);

        btnRekapBulanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486898_project-plan.png"))); // NOI18N
        btnRekapBulanan.setText("Presensi Bulanan");
        btnRekapBulanan.setIconTextGap(0);
        btnRekapBulanan.setName("btnRekapBulanan"); // NOI18N
        btnRekapBulanan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapBulanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapBulananActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekapBulanan);

        btnDeposit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Money.png"))); // NOI18N
        btnDeposit.setText("Deposit Pasien");
        btnDeposit.setIconTextGap(0);
        btnDeposit.setName("btnDeposit"); // NOI18N
        btnDeposit.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepositActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDeposit);

        btnSetupRM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/patient (1).png"))); // NOI18N
        btnSetupRM.setText("Set RM");
        btnSetupRM.setIconTextGap(0);
        btnSetupRM.setName("btnSetupRM"); // NOI18N
        btnSetupRM.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetupRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetupRMActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetupRM);

        btnResepPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485642_edit-notes.png"))); // NOI18N
        btnResepPulang.setText("Resep Pulang");
        btnResepPulang.setIconTextGap(0);
        btnResepPulang.setName("btnResepPulang"); // NOI18N
        btnResepPulang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnResepPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResepPulangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnResepPulang);

        btnSetupTarif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/x-office-address-book.png"))); // NOI18N
        btnSetupTarif.setText("Set Penggunaan Tarif");
        btnSetupTarif.setIconTextGap(0);
        btnSetupTarif.setName("btnSetupTarif"); // NOI18N
        btnSetupTarif.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetupTarif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetupTarifActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetupTarif);

        btnBarangIpsrs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487078_shipping.png"))); // NOI18N
        btnBarangIpsrs.setText("Barang Non Medis");
        btnBarangIpsrs.setIconTextGap(0);
        btnBarangIpsrs.setName("btnBarangIpsrs"); // NOI18N
        btnBarangIpsrs.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBarangIpsrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangIpsrsActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBarangIpsrs);

        btnPembelianIpsrs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/inventory-maintenance.png"))); // NOI18N
        btnPembelianIpsrs.setText("Pengadaan Barang Non Medis");
        btnPembelianIpsrs.setIconTextGap(0);
        btnPembelianIpsrs.setName("btnPembelianIpsrs"); // NOI18N
        btnPembelianIpsrs.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPembelianIpsrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPembelianIpsrsActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPembelianIpsrs);

        btnPengeluaranIpsrs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/shopping-basket-full.png"))); // NOI18N
        btnPengeluaranIpsrs.setText("Stok Keluar Non Medis");
        btnPengeluaranIpsrs.setIconTextGap(0);
        btnPengeluaranIpsrs.setName("btnPengeluaranIpsrs"); // NOI18N
        btnPengeluaranIpsrs.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPengeluaranIpsrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPengeluaranIpsrsActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPengeluaranIpsrs);

        btnRHMasukIpsrs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/shopping_cart.png"))); // NOI18N
        btnRHMasukIpsrs.setText("Rekap Pengadaan Non Medis");
        btnRHMasukIpsrs.setIconTextGap(0);
        btnRHMasukIpsrs.setName("btnRHMasukIpsrs"); // NOI18N
        btnRHMasukIpsrs.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRHMasukIpsrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRHMasukIpsrsActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRHMasukIpsrs);

        btnRHKeluarIpsrs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360816018_tests.png"))); // NOI18N
        btnRHKeluarIpsrs.setText("Rekap Stok Keluar Non Medis");
        btnRHKeluarIpsrs.setIconTextGap(0);
        btnRHKeluarIpsrs.setName("btnRHKeluarIpsrs"); // NOI18N
        btnRHKeluarIpsrs.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRHKeluarIpsrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRHKeluarIpsrsActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRHKeluarIpsrs);

        btnRBiayaIpsrs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486845_23.png"))); // NOI18N
        btnRBiayaIpsrs.setText("Biaya Pengadaan Non Medis");
        btnRBiayaIpsrs.setIconTextGap(0);
        btnRBiayaIpsrs.setName("btnRBiayaIpsrs"); // NOI18N
        btnRBiayaIpsrs.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRBiayaIpsrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRBiayaIpsrsActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRBiayaIpsrs);

        btnTarifRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1410153940_radiology.png"))); // NOI18N
        btnTarifRadiologi.setText("Tarif Radiologi");
        btnTarifRadiologi.setIconTextGap(0);
        btnTarifRadiologi.setName("btnTarifRadiologi"); // NOI18N
        btnTarifRadiologi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTarifRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTarifRadiologiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTarifRadiologi);

        btnPeriksaRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Print.png"))); // NOI18N
        btnPeriksaRadiologi.setText("Periksa Radiologi");
        btnPeriksaRadiologi.setIconTextGap(0);
        btnPeriksaRadiologi.setName("btnPeriksaRadiologi"); // NOI18N
        btnPeriksaRadiologi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPeriksaRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeriksaRadiologiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPeriksaRadiologi);

        btnTagihanRalanPerhari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485865_schedule.png"))); // NOI18N
        btnTagihanRalanPerhari.setText("Rekap Pembayaran Ralan");
        btnTagihanRalanPerhari.setIconTextGap(0);
        btnTagihanRalanPerhari.setName("btnTagihanRalanPerhari"); // NOI18N
        btnTagihanRalanPerhari.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTagihanRalanPerhari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagihanRalanPerhariActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTagihanRalanPerhari);

        btnTagihanRanapPerhari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485865_schedule.png"))); // NOI18N
        btnTagihanRanapPerhari.setText("Rekap Pembayaran Ranap");
        btnTagihanRanapPerhari.setIconTextGap(0);
        btnTagihanRanapPerhari.setName("btnTagihanRanapPerhari"); // NOI18N
        btnTagihanRanapPerhari.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTagihanRanapPerhari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagihanRanapPerhariActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTagihanRanapPerhari);

        btnSetupEmbalase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Money.png"))); // NOI18N
        btnSetupEmbalase.setText("Set Embalase & Tuslah");
        btnSetupEmbalase.setIconTextGap(0);
        btnSetupEmbalase.setName("btnSetupEmbalase"); // NOI18N
        btnSetupEmbalase.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetupEmbalase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetupEmbalaseActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetupEmbalase);

        btnSirkulasiBerkas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/data_management.png"))); // NOI18N
        btnSirkulasiBerkas.setText("Peminjaman Berkas RM");
        btnSirkulasiBerkas.setIconTextGap(0);
        btnSirkulasiBerkas.setName("btnSirkulasiBerkas"); // NOI18N
        btnSirkulasiBerkas.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSirkulasiBerkas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSirkulasiBerkasActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSirkulasiBerkas);

        btnObatPasienRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360815855_laboratory.png"))); // NOI18N
        btnObatPasienRalan.setText("Obat Per Dokter Ralan");
        btnObatPasienRalan.setIconTextGap(0);
        btnObatPasienRalan.setName("btnObatPasienRalan"); // NOI18N
        btnObatPasienRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnObatPasienRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObatPasienRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnObatPasienRalan);

        btnObatPasienRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360815855_laboratory.png"))); // NOI18N
        btnObatPasienRanap.setText("Obat Per Dokter Ranap");
        btnObatPasienRanap.setIconTextGap(0);
        btnObatPasienRanap.setName("btnObatPasienRanap"); // NOI18N
        btnObatPasienRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnObatPasienRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObatPasienRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnObatPasienRanap);

        btnPemesanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/kwrite.png"))); // NOI18N
        btnPemesanan.setText("Penerimaan Obat & BHP");
        btnPemesanan.setIconTextGap(0);
        btnPemesanan.setName("btnPemesanan"); // NOI18N
        btnPemesanan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPemesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPemesananActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPemesanan);

        btnPengeluaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404047106_emblem-money.png"))); // NOI18N
        btnPengeluaran.setText("Pengeluaran Harian");
        btnPengeluaran.setIconTextGap(0);
        btnPengeluaran.setName("btnPengeluaran"); // NOI18N
        btnPengeluaran.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPengeluaranActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPengeluaran);

        btnTambahanBiaya.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404046786_Money.png"))); // NOI18N
        btnTambahanBiaya.setText("Tambahan Biaya Pasien");
        btnTambahanBiaya.setIconTextGap(0);
        btnTambahanBiaya.setName("btnTambahanBiaya"); // NOI18N
        btnTambahanBiaya.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTambahanBiaya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahanBiayaActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTambahanBiaya);

        btnPotonganBiaya.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404046786_Money.png"))); // NOI18N
        btnPotonganBiaya.setText("Potongan Biaya Pasien");
        btnPotonganBiaya.setIconTextGap(0);
        btnPotonganBiaya.setName("btnPotonganBiaya"); // NOI18N
        btnPotonganBiaya.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPotonganBiaya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPotonganBiayaActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPotonganBiaya);

        btnJMDetailDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnJMDetailDokter.setText("Detail JM Dokter");
        btnJMDetailDokter.setIconTextGap(0);
        btnJMDetailDokter.setName("btnJMDetailDokter"); // NOI18N
        btnJMDetailDokter.setPreferredSize(new java.awt.Dimension(200, 90));
        btnJMDetailDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJMDetailDokterActionPerformed(evt);
            }
        });
        Panelmenu.add(btnJMDetailDokter);

        btnIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist.png"))); // NOI18N
        btnIGD.setText("IGD/UGD");
        btnIGD.setIconTextGap(0);
        btnIGD.setName("btnIGD"); // NOI18N
        btnIGD.setPreferredSize(new java.awt.Dimension(200, 90));
        btnIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIGDActionPerformed(evt);
            }
        });
        Panelmenu.add(btnIGD);

        btnSetObatRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487093_price.png"))); // NOI18N
        btnSetObatRalan.setText("Set Obat Ralan");
        btnSetObatRalan.setIconTextGap(0);
        btnSetObatRalan.setName("btnSetObatRalan"); // NOI18N
        btnSetObatRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetObatRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetObatRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetObatRalan);

        btnSetObatRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487093_price.png"))); // NOI18N
        btnSetObatRanap.setText("Set Obat Ranap");
        btnSetObatRanap.setIconTextGap(0);
        btnSetObatRanap.setName("btnSetObatRanap"); // NOI18N
        btnSetObatRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetObatRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetObatRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetObatRanap);

        btnPenyakitPD3I.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnPenyakitPD3I.setText("Penyakit AFP & PD3I");
        btnPenyakitPD3I.setIconTextGap(0);
        btnPenyakitPD3I.setName("btnPenyakitPD3I"); // NOI18N
        btnPenyakitPD3I.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPenyakitPD3I.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenyakitPD3IActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPenyakitPD3I);

        btnSurveilansPD3I.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnSurveilansPD3I.setText("Surveilans AFP & PD3I");
        btnSurveilansPD3I.setIconTextGap(0);
        btnSurveilansPD3I.setName("btnSurveilansPD3I"); // NOI18N
        btnSurveilansPD3I.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSurveilansPD3I.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSurveilansPD3IActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSurveilansPD3I);

        btnSurveilansRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnSurveilansRalan.setText("Surveilans Ralan");
        btnSurveilansRalan.setIconTextGap(0);
        btnSurveilansRalan.setName("btnSurveilansRalan"); // NOI18N
        btnSurveilansRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSurveilansRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSurveilansRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSurveilansRalan);

        btnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/health.png"))); // NOI18N
        btnDiagnosa.setText("Diagnosa Pasien");
        btnDiagnosa.setIconTextGap(0);
        btnDiagnosa.setName("btnDiagnosa"); // NOI18N
        btnDiagnosa.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosaActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDiagnosa);

        btnSurveilansRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnSurveilansRanap.setText("Surveilans Ranap");
        btnSurveilansRanap.setIconTextGap(0);
        btnSurveilansRanap.setName("btnSurveilansRanap"); // NOI18N
        btnSurveilansRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSurveilansRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSurveilansRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSurveilansRanap);

        btnPnyTakMenularRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnPnyTakMenularRanap.setText("Pny Tdk Menular Ranap");
        btnPnyTakMenularRanap.setIconTextGap(0);
        btnPnyTakMenularRanap.setName("btnPnyTakMenularRanap"); // NOI18N
        btnPnyTakMenularRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPnyTakMenularRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPnyTakMenularRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPnyTakMenularRanap);

        btnPnyTakMenularRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnPnyTakMenularRalan.setText("Pny Tdk Menular Ralan");
        btnPnyTakMenularRalan.setIconTextGap(0);
        btnPnyTakMenularRalan.setName("btnPnyTakMenularRalan"); // NOI18N
        btnPnyTakMenularRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPnyTakMenularRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPnyTakMenularRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPnyTakMenularRalan);

        btnKunjunganRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnKunjunganRalan.setText("Kunjungan Ralan");
        btnKunjunganRalan.setIconTextGap(0);
        btnKunjunganRalan.setName("btnKunjunganRalan"); // NOI18N
        btnKunjunganRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKunjunganRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKunjunganRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKunjunganRalan);

        btnRl31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnRl31.setText("RL 3.1 Rawat Inap");
        btnRl31.setIconTextGap(0);
        btnRl31.setName("btnRl31"); // NOI18N
        btnRl31.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRl31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRl31ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRl31);

        btnRl32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnRl32.setText("RL 3.2 Rawat Darurat");
        btnRl32.setIconTextGap(0);
        btnRl32.setName("btnRl32"); // NOI18N
        btnRl32.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRl32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRl32ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRl32);

        btnRl33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnRl33.setText("RL 3.3 Gigi dan Mulut");
        btnRl33.setIconTextGap(0);
        btnRl33.setName("btnRl33"); // NOI18N
        btnRl33.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRl33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRl33ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRl33);

        btnRl37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnRl37.setText("RL 3.7 Radiologi");
        btnRl37.setIconTextGap(0);
        btnRl37.setName("btnRl37"); // NOI18N
        btnRl37.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRl37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRl37ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRl37);

        btnRl38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnRl38.setText("RL 3.8 Laboratorium");
        btnRl38.setIconTextGap(0);
        btnRl38.setName("btnRl38"); // NOI18N
        btnRl38.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRl38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRl38ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRl38);

        btnRl34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnRl34.setText("RL 3.4 Kebidanan");
        btnRl34.setIconTextGap(0);
        btnRl34.setName("btnRl34"); // NOI18N
        btnRl34.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRl34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRl34ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRl34);

        btnRl4a.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1491582089_Finance_financial_report.png"))); // NOI18N
        btnRl4a.setText("RL 4A Morbiditas Ranap");
        btnRl4a.setIconTextGap(0);
        btnRl4a.setName("btnRl4a"); // NOI18N
        btnRl4a.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRl4a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRl4aActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRl4a);

        btnRl36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnRl36.setText("RL 3.6 Pembedahan");
        btnRl36.setIconTextGap(0);
        btnRl36.setName("btnRl36"); // NOI18N
        btnRl36.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRl36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRl36ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRl36);

        btnRl4b.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1491582089_Finance_financial_report.png"))); // NOI18N
        btnRl4b.setText("RL 4B Morbiditas Ralan");
        btnRl4b.setIconTextGap(0);
        btnRl4b.setName("btnRl4b"); // NOI18N
        btnRl4b.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRl4b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRl4bActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRl4b);

        btnRl4asebab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1491582089_Finance_financial_report.png"))); // NOI18N
        btnRl4asebab.setText("RL 4A Sebab Morbiditas Ranap");
        btnRl4asebab.setIconTextGap(0);
        btnRl4asebab.setName("btnRl4asebab"); // NOI18N
        btnRl4asebab.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRl4asebab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRl4asebabActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRl4asebab);

        btnRl4bsebab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1491582089_Finance_financial_report.png"))); // NOI18N
        btnRl4bsebab.setText("RL 4B Sebab Morbiditas Ralan");
        btnRl4bsebab.setIconTextGap(0);
        btnRl4bsebab.setName("btnRl4bsebab"); // NOI18N
        btnRl4bsebab.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRl4bsebab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRl4bsebabActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRl4bsebab);

        btnTagihanDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404047106_emblem-money.png"))); // NOI18N
        btnTagihanDokter.setText("Harian Dokter Ralan");
        btnTagihanDokter.setIconTextGap(0);
        btnTagihanDokter.setName("btnTagihanDokter"); // NOI18N
        btnTagihanDokter.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTagihanDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagihanDokterActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTagihanDokter);

        btnSidikJari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/finger.png"))); // NOI18N
        btnSidikJari.setText("Sidik Jari");
        btnSidikJari.setIconTextGap(0);
        btnSidikJari.setName("btnSidikJari"); // NOI18N
        btnSidikJari.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSidikJari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSidikJariActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSidikJari);

        btnJamPresensi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Time.png"))); // NOI18N
        btnJamPresensi.setText("Jam Presensi");
        btnJamPresensi.setIconTextGap(0);
        btnJamPresensi.setName("btnJamPresensi"); // NOI18N
        btnJamPresensi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnJamPresensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJamPresensiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnJamPresensi);

        btnJadwalPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485865_schedule.png"))); // NOI18N
        btnJadwalPegawai.setText("Jadwal Pegawai");
        btnJadwalPegawai.setIconTextGap(0);
        btnJadwalPegawai.setName("btnJadwalPegawai"); // NOI18N
        btnJadwalPegawai.setPreferredSize(new java.awt.Dimension(200, 90));
        btnJadwalPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJadwalPegawaiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnJadwalPegawai);

        btnSetupNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485642_edit-notes.png"))); // NOI18N
        btnSetupNota.setText("Set Billing");
        btnSetupNota.setIconTextGap(0);
        btnSetupNota.setName("btnSetupNota"); // NOI18N
        btnSetupNota.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetupNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetupNotaActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetupNota);

        BtnDpjp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/doctor2.png"))); // NOI18N
        BtnDpjp.setText("DPJP Ranap");
        BtnDpjp.setIconTextGap(0);
        BtnDpjp.setName("BtnDpjp"); // NOI18N
        BtnDpjp.setPreferredSize(new java.awt.Dimension(200, 90));
        BtnDpjp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDpjpActionPerformed(evt);
            }
        });
        Panelmenu.add(BtnDpjp);

        btnMutasiBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485642_edit-notes.png"))); // NOI18N
        btnMutasiBarang.setText("Mutasi Obat & BHP");
        btnMutasiBarang.setIconTextGap(0);
        btnMutasiBarang.setName("btnMutasiBarang"); // NOI18N
        btnMutasiBarang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnMutasiBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMutasiBarangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnMutasiBarang);

        btnfee_visit_dokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485865_schedule.png"))); // NOI18N
        btnfee_visit_dokter.setText("Fee Visit Dokter");
        btnfee_visit_dokter.setIconTextGap(0);
        btnfee_visit_dokter.setName("btnfee_visit_dokter"); // NOI18N
        btnfee_visit_dokter.setPreferredSize(new java.awt.Dimension(200, 90));
        btnfee_visit_dokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfee_visit_dokterActionPerformed(evt);
            }
        });
        Panelmenu.add(btnfee_visit_dokter);

        btnfee_bacaan_ekg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485865_schedule.png"))); // NOI18N
        btnfee_bacaan_ekg.setText("Fee Bacaan EKG");
        btnfee_bacaan_ekg.setIconTextGap(0);
        btnfee_bacaan_ekg.setName("btnfee_bacaan_ekg"); // NOI18N
        btnfee_bacaan_ekg.setPreferredSize(new java.awt.Dimension(200, 90));
        btnfee_bacaan_ekg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfee_bacaan_ekgActionPerformed(evt);
            }
        });
        Panelmenu.add(btnfee_bacaan_ekg);

        btnfee_rujukan_rontgen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485865_schedule.png"))); // NOI18N
        btnfee_rujukan_rontgen.setText("Fee Rujukan Rontgen");
        btnfee_rujukan_rontgen.setIconTextGap(0);
        btnfee_rujukan_rontgen.setName("btnfee_rujukan_rontgen"); // NOI18N
        btnfee_rujukan_rontgen.setPreferredSize(new java.awt.Dimension(200, 90));
        btnfee_rujukan_rontgen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfee_rujukan_rontgenActionPerformed(evt);
            }
        });
        Panelmenu.add(btnfee_rujukan_rontgen);

        btnfee_rujukan_ranap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485865_schedule.png"))); // NOI18N
        btnfee_rujukan_ranap.setText("Fee Rujukan Ranap");
        btnfee_rujukan_ranap.setIconTextGap(0);
        btnfee_rujukan_ranap.setName("btnfee_rujukan_ranap"); // NOI18N
        btnfee_rujukan_ranap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnfee_rujukan_ranap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfee_rujukan_ranapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnfee_rujukan_ranap);

        btnfee_ralan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485865_schedule.png"))); // NOI18N
        btnfee_ralan.setText("Fee Periksa Ralan");
        btnfee_ralan.setIconTextGap(0);
        btnfee_ralan.setName("btnfee_ralan"); // NOI18N
        btnfee_ralan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnfee_ralan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfee_ralanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnfee_ralan);

        btnakun_bayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist.png"))); // NOI18N
        btnakun_bayar.setText("Akun Bayar");
        btnakun_bayar.setIconTextGap(0);
        btnakun_bayar.setName("btnakun_bayar"); // NOI18N
        btnakun_bayar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnakun_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnakun_bayarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnakun_bayar);

        btnbayar_pemesanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/cashbox.png"))); // NOI18N
        btnbayar_pemesanan.setText("Bayar Pesan Obat/BHP");
        btnbayar_pemesanan.setIconTextGap(0);
        btnbayar_pemesanan.setName("btnbayar_pemesanan"); // NOI18N
        btnbayar_pemesanan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnbayar_pemesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbayar_pemesananActionPerformed(evt);
            }
        });
        Panelmenu.add(btnbayar_pemesanan);

        btnObatPasienPeresep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360815855_laboratory.png"))); // NOI18N
        btnObatPasienPeresep.setText("Obat Per Dokter Peresep");
        btnObatPasienPeresep.setIconTextGap(0);
        btnObatPasienPeresep.setName("btnObatPasienPeresep"); // NOI18N
        btnObatPasienPeresep.setPreferredSize(new java.awt.Dimension(200, 90));
        btnObatPasienPeresep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObatPasienPeresepActionPerformed(evt);
            }
        });
        Panelmenu.add(btnObatPasienPeresep);

        btnJenisIpsrs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/cabinet.png"))); // NOI18N
        btnJenisIpsrs.setText("Jenis Barang");
        btnJenisIpsrs.setIconTextGap(0);
        btnJenisIpsrs.setName("btnJenisIpsrs"); // NOI18N
        btnJenisIpsrs.setPreferredSize(new java.awt.Dimension(200, 90));
        btnJenisIpsrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJenisIpsrsActionPerformed(evt);
            }
        });
        Panelmenu.add(btnJenisIpsrs);

        btnPemasukanLain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486845_23.png"))); // NOI18N
        btnPemasukanLain.setText("Pemasukan Lain-Lain");
        btnPemasukanLain.setIconTextGap(0);
        btnPemasukanLain.setName("btnPemasukanLain"); // NOI18N
        btnPemasukanLain.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPemasukanLain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPemasukanLainActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPemasukanLain);

        btnPengaturanRekening.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/gtk-stock-book.png"))); // NOI18N
        btnPengaturanRekening.setText("Pengaturan Rekening");
        btnPengaturanRekening.setIconTextGap(0);
        btnPengaturanRekening.setName("btnPengaturanRekening"); // NOI18N
        btnPengaturanRekening.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPengaturanRekening.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPengaturanRekeningActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPengaturanRekening);

        btnJadwalTambahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485865_schedule.png"))); // NOI18N
        btnJadwalTambahan.setText("Jadwal Tambahan");
        btnJadwalTambahan.setIconTextGap(0);
        btnJadwalTambahan.setName("btnJadwalTambahan"); // NOI18N
        btnJadwalTambahan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnJadwalTambahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJadwalTambahanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnJadwalTambahan);

        btnClosingKasir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485865_schedule.png"))); // NOI18N
        btnClosingKasir.setText("Closing Kasir");
        btnClosingKasir.setIconTextGap(0);
        btnClosingKasir.setName("btnClosingKasir"); // NOI18N
        btnClosingKasir.setPreferredSize(new java.awt.Dimension(200, 90));
        btnClosingKasir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClosingKasirActionPerformed(evt);
            }
        });
        Panelmenu.add(btnClosingKasir);

        btnKeterlambatanPresensi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Time.png"))); // NOI18N
        btnKeterlambatanPresensi.setText("Set Keterlambatan Presensi");
        btnKeterlambatanPresensi.setIconTextGap(0);
        btnKeterlambatanPresensi.setName("btnKeterlambatanPresensi"); // NOI18N
        btnKeterlambatanPresensi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKeterlambatanPresensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeterlambatanPresensiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKeterlambatanPresensi);

        btnSetHargaKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/bedroom.png"))); // NOI18N
        btnSetHargaKamar.setText("Set Harga Kamar");
        btnSetHargaKamar.setIconTextGap(0);
        btnSetHargaKamar.setName("btnSetHargaKamar"); // NOI18N
        btnSetHargaKamar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetHargaKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetHargaKamarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetHargaKamar);

        btnRekapPershift.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist.png"))); // NOI18N
        btnRekapPershift.setText("Rekap Uang Pershift");
        btnRekapPershift.setIconTextGap(0);
        btnRekapPershift.setName("btnRekapPershift"); // NOI18N
        btnRekapPershift.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapPershift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapPershiftActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekapPershift);

        btnCekBPJSNik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/family.png"))); // NOI18N
        btnCekBPJSNik.setText("Cek NIK VClaim");
        btnCekBPJSNik.setIconTextGap(0);
        btnCekBPJSNik.setName("btnCekBPJSNik"); // NOI18N
        btnCekBPJSNik.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekBPJSNik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekBPJSNikActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekBPJSNik);

        btnCekBPJSKartu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/administrator.png"))); // NOI18N
        btnCekBPJSKartu.setText("Cek No.Kartu VClaim");
        btnCekBPJSKartu.setIconTextGap(0);
        btnCekBPJSKartu.setName("btnCekBPJSKartu"); // NOI18N
        btnCekBPJSKartu.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekBPJSKartu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekBPJSKartuActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekBPJSKartu);

        btnCekBPJSRiwayatRujukanPCare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/doctor 3.png"))); // NOI18N
        btnCekBPJSRiwayatRujukanPCare.setText("Riwayat Rujukan PCare di VClaim");
        btnCekBPJSRiwayatRujukanPCare.setIconTextGap(0);
        btnCekBPJSRiwayatRujukanPCare.setName("btnCekBPJSRiwayatRujukanPCare"); // NOI18N
        btnCekBPJSRiwayatRujukanPCare.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekBPJSRiwayatRujukanPCare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekBPJSRiwayatRujukanPCareActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekBPJSRiwayatRujukanPCare);

        btnRekapPresensi2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/sign-up.png"))); // NOI18N
        btnRekapPresensi2.setText("Rekap Kehadiran 2");
        btnRekapPresensi2.setIconTextGap(0);
        btnRekapPresensi2.setName("btnRekapPresensi2"); // NOI18N
        btnRekapPresensi2.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapPresensi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapPresensi2ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekapPresensi2);

        btnObatPerCaraBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360815855_laboratory.png"))); // NOI18N
        btnObatPerCaraBayar.setText("Obat Per Cara Bayar");
        btnObatPerCaraBayar.setIconTextGap(0);
        btnObatPerCaraBayar.setName("btnObatPerCaraBayar"); // NOI18N
        btnObatPerCaraBayar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnObatPerCaraBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObatPerCaraBayarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnObatPerCaraBayar);

        btnKunjunganRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnKunjunganRanap.setText("Kunjungan Ranap");
        btnKunjunganRanap.setIconTextGap(0);
        btnKunjunganRanap.setName("btnKunjunganRanap"); // NOI18N
        btnKunjunganRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKunjunganRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKunjunganRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKunjunganRanap);

        btnPaymentPoint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/coins.png"))); // NOI18N
        btnPaymentPoint.setText("Payment Point");
        btnPaymentPoint.setIconTextGap(0);
        btnPaymentPoint.setName("btnPaymentPoint"); // NOI18N
        btnPaymentPoint.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPaymentPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaymentPointActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPaymentPoint);

        btnCekBPJSNomorRujukanPCare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist.png"))); // NOI18N
        btnCekBPJSNomorRujukanPCare.setText("Cek No.Rujukan PCare di VClaim");
        btnCekBPJSNomorRujukanPCare.setIconTextGap(0);
        btnCekBPJSNomorRujukanPCare.setName("btnCekBPJSNomorRujukanPCare"); // NOI18N
        btnCekBPJSNomorRujukanPCare.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekBPJSNomorRujukanPCare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekBPJSNomorRujukanPCareActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekBPJSNomorRujukanPCare);

        btnICD9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnICD9.setText("ICD 9");
        btnICD9.setIconTextGap(0);
        btnICD9.setName("btnICD9"); // NOI18N
        btnICD9.setPreferredSize(new java.awt.Dimension(200, 90));
        btnICD9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnICD9ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnICD9);

        btnDaruratStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487078_shipping.png"))); // NOI18N
        btnDaruratStok.setText("Darurat Stok");
        btnDaruratStok.setIconTextGap(0);
        btnDaruratStok.setName("btnDaruratStok"); // NOI18N
        btnDaruratStok.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDaruratStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaruratStokActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDaruratStok);

        btnRetensiRM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/reportorium.png"))); // NOI18N
        btnRetensiRM.setText("Retensi Berkas R.M.");
        btnRetensiRM.setIconTextGap(0);
        btnRetensiRM.setName("btnRetensiRM"); // NOI18N
        btnRetensiRM.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRetensiRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetensiRMActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRetensiRM);

        btnTemporaryPresensi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404047834_application-vnd.ms-excel.png"))); // NOI18N
        btnTemporaryPresensi.setText("Temporary Presensi");
        btnTemporaryPresensi.setIconTextGap(0);
        btnTemporaryPresensi.setName("btnTemporaryPresensi"); // NOI18N
        btnTemporaryPresensi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTemporaryPresensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTemporaryPresensiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTemporaryPresensi);

        btnJurnalHarian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485865_schedule.png"))); // NOI18N
        btnJurnalHarian.setText("Jurnal Harian");
        btnJurnalHarian.setIconTextGap(0);
        btnJurnalHarian.setName("btnJurnalHarian"); // NOI18N
        btnJurnalHarian.setPreferredSize(new java.awt.Dimension(200, 90));
        btnJurnalHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJurnalHarianActionPerformed(evt);
            }
        });
        Panelmenu.add(btnJurnalHarian);

        btnSirkulasi2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487125_system-restart-panel.png"))); // NOI18N
        btnSirkulasi2.setText("Sirkulasi Obat, Alkes & BHP 2");
        btnSirkulasi2.setIconTextGap(0);
        btnSirkulasi2.setName("btnSirkulasi2"); // NOI18N
        btnSirkulasi2.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSirkulasi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSirkulasi2ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSirkulasi2);

        btnCekBPJSDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnCekBPJSDiagnosa.setText("Referensi Diagnosa VClaim");
        btnCekBPJSDiagnosa.setIconTextGap(0);
        btnCekBPJSDiagnosa.setName("btnCekBPJSDiagnosa"); // NOI18N
        btnCekBPJSDiagnosa.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekBPJSDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekBPJSDiagnosaActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekBPJSDiagnosa);

        btnCekBPJSPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/logout.png"))); // NOI18N
        btnCekBPJSPoli.setText("Referensi Poli VClaim");
        btnCekBPJSPoli.setIconTextGap(0);
        btnCekBPJSPoli.setName("btnCekBPJSPoli"); // NOI18N
        btnCekBPJSPoli.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekBPJSPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekBPJSPoliActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekBPJSPoli);

        btnIndustriFarmasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486910_company.png"))); // NOI18N
        btnIndustriFarmasi.setText("Industri Farmasi");
        btnIndustriFarmasi.setIconTextGap(0);
        btnIndustriFarmasi.setName("btnIndustriFarmasi"); // NOI18N
        btnIndustriFarmasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnIndustriFarmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndustriFarmasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnIndustriFarmasi);

        btnRHJasaSarana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnRHJasaSarana.setText("Harian Jasa Sarana");
        btnRHJasaSarana.setIconTextGap(0);
        btnRHJasaSarana.setName("btnRHJasaSarana"); // NOI18N
        btnRHJasaSarana.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRHJasaSarana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRHJasaSaranaActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRHJasaSarana);

        btnRBJasaSarana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnRBJasaSarana.setText("Bulanan Jasa Sarana");
        btnRBJasaSarana.setIconTextGap(0);
        btnRBJasaSarana.setName("btnRBJasaSarana"); // NOI18N
        btnRBJasaSarana.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRBJasaSarana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRBJasaSaranaActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRBJasaSarana);

        btnRHPaketBHP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360815855_laboratory.png"))); // NOI18N
        btnRHPaketBHP.setText("Harian BHP Medis/Paket Obat");
        btnRHPaketBHP.setIconTextGap(0);
        btnRHPaketBHP.setName("btnRHPaketBHP"); // NOI18N
        btnRHPaketBHP.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRHPaketBHP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRHPaketBHPActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRHPaketBHP);

        btnRBPaketBHP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360815855_laboratory.png"))); // NOI18N
        btnRBPaketBHP.setText("Bulanan BHP Medis/Paket Obat");
        btnRBPaketBHP.setIconTextGap(0);
        btnRBPaketBHP.setName("btnRBPaketBHP"); // NOI18N
        btnRBPaketBHP.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRBPaketBHP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRBPaketBHPActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRBPaketBHP);

        btnPiutangBelumLunas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist_pencil-o.png"))); // NOI18N
        btnPiutangBelumLunas.setText("Piutang Belum Lunas");
        btnPiutangBelumLunas.setIconTextGap(0);
        btnPiutangBelumLunas.setName("btnPiutangBelumLunas"); // NOI18N
        btnPiutangBelumLunas.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPiutangBelumLunas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPiutangBelumLunasActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPiutangBelumLunas);

        btnCekBPJSFaskes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/industry.png"))); // NOI18N
        btnCekBPJSFaskes.setText("Referensi Faskes VClaim");
        btnCekBPJSFaskes.setIconTextGap(0);
        btnCekBPJSFaskes.setName("btnCekBPJSFaskes"); // NOI18N
        btnCekBPJSFaskes.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekBPJSFaskes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekBPJSFaskesActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekBPJSFaskes);

        btnBPJSSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481038237_Adobe_Bridge_CS.png"))); // NOI18N
        btnBPJSSEP.setText("Data Bridging SEP VClaim");
        btnBPJSSEP.setIconTextGap(0);
        btnBPJSSEP.setName("btnBPJSSEP"); // NOI18N
        btnBPJSSEP.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBPJSSEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBPJSSEPActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBPJSSEP);

        btnPengambilanUTD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487078_shipping.png"))); // NOI18N
        btnPengambilanUTD.setText("Pengambilan BHP UTD");
        btnPengambilanUTD.setIconTextGap(0);
        btnPengambilanUTD.setName("btnPengambilanUTD"); // NOI18N
        btnPengambilanUTD.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPengambilanUTD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPengambilanUTDActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPengambilanUTD);

        btnTarifUtd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481001686_injection_blood.png"))); // NOI18N
        btnTarifUtd.setText("Tarif UTD");
        btnTarifUtd.setIconTextGap(0);
        btnTarifUtd.setName("btnTarifUtd"); // NOI18N
        btnTarifUtd.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTarifUtd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTarifUtdActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTarifUtd);

        btnPengambilanUTD2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487078_shipping.png"))); // NOI18N
        btnPengambilanUTD2.setText("Pengambilan BHP Medis");
        btnPengambilanUTD2.setIconTextGap(0);
        btnPengambilanUTD2.setName("btnPengambilanUTD2"); // NOI18N
        btnPengambilanUTD2.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPengambilanUTD2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPengambilanUTD2ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPengambilanUTD2);

        btnUTDMedisRusak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486858_stock-market.png"))); // NOI18N
        btnUTDMedisRusak.setText("BHP Medis Rusak");
        btnUTDMedisRusak.setIconTextGap(0);
        btnUTDMedisRusak.setName("btnUTDMedisRusak"); // NOI18N
        btnUTDMedisRusak.setPreferredSize(new java.awt.Dimension(200, 90));
        btnUTDMedisRusak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUTDMedisRusakActionPerformed(evt);
            }
        });
        Panelmenu.add(btnUTDMedisRusak);

        btnPengambilanPenunjangUTD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481002155_skills.png"))); // NOI18N
        btnPengambilanPenunjangUTD.setText("Pengambilan UTD");
        btnPengambilanPenunjangUTD.setIconTextGap(0);
        btnPengambilanPenunjangUTD.setName("btnPengambilanPenunjangUTD"); // NOI18N
        btnPengambilanPenunjangUTD.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPengambilanPenunjangUTD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPengambilanPenunjangUTDActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPengambilanPenunjangUTD);

        btnPengambilanPenunjangUTD2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481002155_skills.png"))); // NOI18N
        btnPengambilanPenunjangUTD2.setText("Pengambilan BHP Non Medis");
        btnPengambilanPenunjangUTD2.setIconTextGap(0);
        btnPengambilanPenunjangUTD2.setName("btnPengambilanPenunjangUTD2"); // NOI18N
        btnPengambilanPenunjangUTD2.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPengambilanPenunjangUTD2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPengambilanPenunjangUTD2ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPengambilanPenunjangUTD2);

        btnUTDPenunjangRusak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/inventory-maintenance.png"))); // NOI18N
        btnUTDPenunjangRusak.setText("BHP Non Medis Rusak");
        btnUTDPenunjangRusak.setIconTextGap(0);
        btnUTDPenunjangRusak.setName("btnUTDPenunjangRusak"); // NOI18N
        btnUTDPenunjangRusak.setPreferredSize(new java.awt.Dimension(200, 90));
        btnUTDPenunjangRusak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUTDPenunjangRusakActionPerformed(evt);
            }
        });
        Panelmenu.add(btnUTDPenunjangRusak);

        btnSuplierIPSRS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481002123_wheelchair.png"))); // NOI18N
        btnSuplierIPSRS.setText("Suplier Non Medis");
        btnSuplierIPSRS.setIconTextGap(0);
        btnSuplierIPSRS.setName("btnSuplierIPSRS"); // NOI18N
        btnSuplierIPSRS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuplierIPSRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuplierIPSRSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuplierIPSRS);

        btnUTDDonorDarah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481001706_heart_beat.png"))); // NOI18N
        btnUTDDonorDarah.setText("Donor Darah");
        btnUTDDonorDarah.setIconTextGap(0);
        btnUTDDonorDarah.setName("btnUTDDonorDarah"); // NOI18N
        btnUTDDonorDarah.setPreferredSize(new java.awt.Dimension(200, 90));
        btnUTDDonorDarah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUTDDonorDarahActionPerformed(evt);
            }
        });
        Panelmenu.add(btnUTDDonorDarah);

        btnMonitoringKlaim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486845_23.png"))); // NOI18N
        btnMonitoringKlaim.setText("Monitoring Verifikasi Klaim BPJS");
        btnMonitoringKlaim.setIconTextGap(0);
        btnMonitoringKlaim.setName("btnMonitoringKlaim"); // NOI18N
        btnMonitoringKlaim.setPreferredSize(new java.awt.Dimension(200, 90));
        btnMonitoringKlaim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonitoringKlaimActionPerformed(evt);
            }
        });
        Panelmenu.add(btnMonitoringKlaim);

        btnUTDCekalDarah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487093_price.png"))); // NOI18N
        btnUTDCekalDarah.setText("Pencekalan Darah");
        btnUTDCekalDarah.setIconTextGap(0);
        btnUTDCekalDarah.setName("btnUTDCekalDarah"); // NOI18N
        btnUTDCekalDarah.setPreferredSize(new java.awt.Dimension(200, 90));
        btnUTDCekalDarah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUTDCekalDarahActionPerformed(evt);
            }
        });
        Panelmenu.add(btnUTDCekalDarah);

        btnUTDKomponenDarah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481001686_injection_blood.png"))); // NOI18N
        btnUTDKomponenDarah.setText("Komponen Darah");
        btnUTDKomponenDarah.setIconTextGap(0);
        btnUTDKomponenDarah.setName("btnUTDKomponenDarah"); // NOI18N
        btnUTDKomponenDarah.setPreferredSize(new java.awt.Dimension(200, 90));
        btnUTDKomponenDarah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUTDKomponenDarahActionPerformed(evt);
            }
        });
        Panelmenu.add(btnUTDKomponenDarah);

        btnUTDStokDarah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481001585_blood_drop.png"))); // NOI18N
        btnUTDStokDarah.setText("Stok Darah");
        btnUTDStokDarah.setIconTextGap(0);
        btnUTDStokDarah.setName("btnUTDStokDarah"); // NOI18N
        btnUTDStokDarah.setPreferredSize(new java.awt.Dimension(200, 90));
        btnUTDStokDarah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUTDStokDarahActionPerformed(evt);
            }
        });
        Panelmenu.add(btnUTDStokDarah);

        btnUTDPemisahanDarah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360815855_laboratory.png"))); // NOI18N
        btnUTDPemisahanDarah.setText("Pemisahan Darah");
        btnUTDPemisahanDarah.setIconTextGap(0);
        btnUTDPemisahanDarah.setName("btnUTDPemisahanDarah"); // NOI18N
        btnUTDPemisahanDarah.setPreferredSize(new java.awt.Dimension(200, 90));
        btnUTDPemisahanDarah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUTDPemisahanDarahActionPerformed(evt);
            }
        });
        Panelmenu.add(btnUTDPemisahanDarah);

        btnHarianKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404047106_emblem-money.png"))); // NOI18N
        btnHarianKamar.setText("Harian Kamar");
        btnHarianKamar.setIconTextGap(0);
        btnHarianKamar.setName("btnHarianKamar"); // NOI18N
        btnHarianKamar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnHarianKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHarianKamarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnHarianKamar);

        btnRincianPiutangPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist_pencil-o.png"))); // NOI18N
        btnRincianPiutangPasien.setText("Rincian Piutang Pasien");
        btnRincianPiutangPasien.setIconTextGap(0);
        btnRincianPiutangPasien.setName("btnRincianPiutangPasien"); // NOI18N
        btnRincianPiutangPasien.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRincianPiutangPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRincianPiutangPasienActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRincianPiutangPasien);

        btnKeuntunganObat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/coins.png"))); // NOI18N
        btnKeuntunganObat2.setText("Keuntungan Beri Obat 2");
        btnKeuntunganObat2.setIconTextGap(0);
        btnKeuntunganObat2.setName("btnKeuntunganObat2"); // NOI18N
        btnKeuntunganObat2.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKeuntunganObat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeuntunganObat2ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKeuntunganObat2);

        btnReklasifikasiRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404047007_02.png"))); // NOI18N
        btnReklasifikasiRalan.setText("Reklasifikasi Ralan");
        btnReklasifikasiRalan.setIconTextGap(0);
        btnReklasifikasiRalan.setName("btnReklasifikasiRalan"); // NOI18N
        btnReklasifikasiRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnReklasifikasiRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReklasifikasiRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnReklasifikasiRalan);

        btnReklasifikasiRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404047007_02.png"))); // NOI18N
        btnReklasifikasiRanap.setText("Reklasifikasi Ranap");
        btnReklasifikasiRanap.setIconTextGap(0);
        btnReklasifikasiRanap.setName("btnReklasifikasiRanap"); // NOI18N
        btnReklasifikasiRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnReklasifikasiRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReklasifikasiRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnReklasifikasiRanap);

        btnUTDPenyerahanDarah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/health.png"))); // NOI18N
        btnUTDPenyerahanDarah.setText("Penyerahan Darah");
        btnUTDPenyerahanDarah.setIconTextGap(0);
        btnUTDPenyerahanDarah.setName("btnUTDPenyerahanDarah"); // NOI18N
        btnUTDPenyerahanDarah.setPreferredSize(new java.awt.Dimension(200, 90));
        btnUTDPenyerahanDarah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUTDPenyerahanDarahActionPerformed(evt);
            }
        });
        Panelmenu.add(btnUTDPenyerahanDarah);

        btnHutangObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/cashbox.png"))); // NOI18N
        btnHutangObat.setText("Hutang Obat & BHP");
        btnHutangObat.setIconTextGap(0);
        btnHutangObat.setName("btnHutangObat"); // NOI18N
        btnHutangObat.setPreferredSize(new java.awt.Dimension(200, 90));
        btnHutangObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHutangObatActionPerformed(evt);
            }
        });
        Panelmenu.add(btnHutangObat);

        btnRiwayatBarangMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/gnome-searchtool.png"))); // NOI18N
        btnRiwayatBarangMedis.setText("Riwayat Obat, Alkes & BHP");
        btnRiwayatBarangMedis.setIconTextGap(0);
        btnRiwayatBarangMedis.setName("btnRiwayatBarangMedis"); // NOI18N
        btnRiwayatBarangMedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRiwayatBarangMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRiwayatBarangMedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRiwayatBarangMedis);

        btnSensusHarianPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/custom-reports.png"))); // NOI18N
        btnSensusHarianPoli.setText("Sensus Harian Poli");
        btnSensusHarianPoli.setIconTextGap(0);
        btnSensusHarianPoli.setName("btnSensusHarianPoli"); // NOI18N
        btnSensusHarianPoli.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSensusHarianPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSensusHarianPoliActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSensusHarianPoli);

        btnAplicareReferensiKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/bedroom.png"))); // NOI18N
        btnAplicareReferensiKamar.setText("Referensi Kamar Aplicare");
        btnAplicareReferensiKamar.setIconTextGap(0);
        btnAplicareReferensiKamar.setName("btnAplicareReferensiKamar"); // NOI18N
        btnAplicareReferensiKamar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnAplicareReferensiKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicareReferensiKamarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnAplicareReferensiKamar);

        btnAplicareKetersediaanKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485357524_Company.png"))); // NOI18N
        btnAplicareKetersediaanKamar.setText("Ketersediaan Kamar Aplicare");
        btnAplicareKetersediaanKamar.setIconTextGap(0);
        btnAplicareKetersediaanKamar.setName("btnAplicareKetersediaanKamar"); // NOI18N
        btnAplicareKetersediaanKamar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnAplicareKetersediaanKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicareKetersediaanKamarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnAplicareKetersediaanKamar);

        btnInaCBGKlaimBaruOtomatis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485358050_receptionist.png"))); // NOI18N
        btnInaCBGKlaimBaruOtomatis.setText("Klaim Baru Otomatis INACBG");
        btnInaCBGKlaimBaruOtomatis.setIconTextGap(0);
        btnInaCBGKlaimBaruOtomatis.setName("btnInaCBGKlaimBaruOtomatis"); // NOI18N
        btnInaCBGKlaimBaruOtomatis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnInaCBGKlaimBaruOtomatis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInaCBGKlaimBaruOtomatisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnInaCBGKlaimBaruOtomatis);

        btnInaCBGKlaimBaruManual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485358050_receptionist.png"))); // NOI18N
        btnInaCBGKlaimBaruManual.setText("Klaim Baru Manual INACBG");
        btnInaCBGKlaimBaruManual.setIconTextGap(0);
        btnInaCBGKlaimBaruManual.setName("btnInaCBGKlaimBaruManual"); // NOI18N
        btnInaCBGKlaimBaruManual.setPreferredSize(new java.awt.Dimension(200, 90));
        btnInaCBGKlaimBaruManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInaCBGKlaimBaruManualActionPerformed(evt);
            }
        });
        Panelmenu.add(btnInaCBGKlaimBaruManual);

        btnInaCBGCoderNIK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481002113_guard.png"))); // NOI18N
        btnInaCBGCoderNIK.setText("Coder NIK INACBG");
        btnInaCBGCoderNIK.setIconTextGap(0);
        btnInaCBGCoderNIK.setName("btnInaCBGCoderNIK"); // NOI18N
        btnInaCBGCoderNIK.setPreferredSize(new java.awt.Dimension(200, 90));
        btnInaCBGCoderNIK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInaCBGCoderNIKActionPerformed(evt);
            }
        });
        Panelmenu.add(btnInaCBGCoderNIK);

        btnMutasiBerkas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485642_edit-notes.png"))); // NOI18N
        btnMutasiBerkas.setText("Mutasi Berkas RM");
        btnMutasiBerkas.setIconTextGap(0);
        btnMutasiBerkas.setName("btnMutasiBerkas"); // NOI18N
        btnMutasiBerkas.setPreferredSize(new java.awt.Dimension(200, 90));
        btnMutasiBerkas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMutasiBerkasActionPerformed(evt);
            }
        });
        Panelmenu.add(btnMutasiBerkas);

        btnAkunPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404046603_wallet.png"))); // NOI18N
        btnAkunPiutang.setText("Akun Piutang");
        btnAkunPiutang.setIconTextGap(0);
        btnAkunPiutang.setName("btnAkunPiutang"); // NOI18N
        btnAkunPiutang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnAkunPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAkunPiutangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnAkunPiutang);

        btnRHKSO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnRHKSO.setText("Harian KSO");
        btnRHKSO.setIconTextGap(0);
        btnRHKSO.setName("btnRHKSO"); // NOI18N
        btnRHKSO.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRHKSO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRHKSOActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRHKSO);

        btnRBKSO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnRBKSO.setText("Bulanan KSO");
        btnRBKSO.setIconTextGap(0);
        btnRBKSO.setName("btnRBKSO"); // NOI18N
        btnRBKSO.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRBKSO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRBKSOActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRBKSO);

        btnRHMenejemen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnRHMenejemen.setText("Harian Menejemen");
        btnRHMenejemen.setIconTextGap(0);
        btnRHMenejemen.setName("btnRHMenejemen"); // NOI18N
        btnRHMenejemen.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRHMenejemen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRHMenejemenActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRHMenejemen);

        btnRBMenejemen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnRBMenejemen.setText("Bulanan Menejemen");
        btnRBMenejemen.setIconTextGap(0);
        btnRBMenejemen.setName("btnRBMenejemen"); // NOI18N
        btnRBMenejemen.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRBMenejemen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRBMenejemenActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRBMenejemen);

        btnCekEligibilitasInhealth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/receptionist.png"))); // NOI18N
        btnCekEligibilitasInhealth.setText("Cek Eligibilitas Inhealth");
        btnCekEligibilitasInhealth.setIconTextGap(0);
        btnCekEligibilitasInhealth.setName("btnCekEligibilitasInhealth"); // NOI18N
        btnCekEligibilitasInhealth.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekEligibilitasInhealth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekEligibilitasInhealthActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekEligibilitasInhealth);

        btnReferensiKamarInhealth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Home.png"))); // NOI18N
        btnReferensiKamarInhealth.setText("Referensi Ruang Rawat Inhealth");
        btnReferensiKamarInhealth.setIconTextGap(0);
        btnReferensiKamarInhealth.setName("btnReferensiKamarInhealth"); // NOI18N
        btnReferensiKamarInhealth.setPreferredSize(new java.awt.Dimension(200, 90));
        btnReferensiKamarInhealth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReferensiKamarInhealthActionPerformed(evt);
            }
        });
        Panelmenu.add(btnReferensiKamarInhealth);

        btnCekInhealthPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/logout.png"))); // NOI18N
        btnCekInhealthPoli.setText("Referensi Poli Inhealth");
        btnCekInhealthPoli.setIconTextGap(0);
        btnCekInhealthPoli.setName("btnCekInhealthPoli"); // NOI18N
        btnCekInhealthPoli.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekInhealthPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekInhealthPoliActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekInhealthPoli);

        btnCekInhealthFaskes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/industry.png"))); // NOI18N
        btnCekInhealthFaskes.setText("Referensi Faskes Inhealth");
        btnCekInhealthFaskes.setIconTextGap(0);
        btnCekInhealthFaskes.setName("btnCekInhealthFaskes"); // NOI18N
        btnCekInhealthFaskes.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekInhealthFaskes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekInhealthFaskesActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekInhealthFaskes);

        btnInhealthSJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481038237_Adobe_Bridge_CS.png"))); // NOI18N
        btnInhealthSJP.setText("Data Bridging SJP Inhealth");
        btnInhealthSJP.setIconTextGap(0);
        btnInhealthSJP.setName("btnInhealthSJP"); // NOI18N
        btnInhealthSJP.setPreferredSize(new java.awt.Dimension(200, 90));
        btnInhealthSJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInhealthSJPActionPerformed(evt);
            }
        });
        Panelmenu.add(btnInhealthSJP);

        btnPiutangRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404047007_02.png"))); // NOI18N
        btnPiutangRalan.setText("Piutang Ralan");
        btnPiutangRalan.setIconTextGap(0);
        btnPiutangRalan.setName("btnPiutangRalan"); // NOI18N
        btnPiutangRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPiutangRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPiutangRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPiutangRalan);

        btnPiutangRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404047007_02.png"))); // NOI18N
        btnPiutangRanap.setText("Piutang Ranap");
        btnPiutangRanap.setIconTextGap(0);
        btnPiutangRanap.setName("btnPiutangRanap"); // NOI18N
        btnPiutangRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPiutangRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPiutangRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPiutangRanap);

        btnPiutangPerCaraBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist_pencil-o.png"))); // NOI18N
        btnPiutangPerCaraBayar.setText("Piutang Per Cara Bayar");
        btnPiutangPerCaraBayar.setIconTextGap(0);
        btnPiutangPerCaraBayar.setName("btnPiutangPerCaraBayar"); // NOI18N
        btnPiutangPerCaraBayar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPiutangPerCaraBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPiutangPerCaraBayarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPiutangPerCaraBayar);

        btnLamaPelayananRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnLamaPelayananRalan.setText("Lama Pelayanan Ralan");
        btnLamaPelayananRalan.setIconTextGap(0);
        btnLamaPelayananRalan.setName("btnLamaPelayananRalan"); // NOI18N
        btnLamaPelayananRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLamaPelayananRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamaPelayananRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnLamaPelayananRalan);

        btnCatatanPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/kwrite.png"))); // NOI18N
        btnCatatanPasien.setText("Catatan Pasien");
        btnCatatanPasien.setIconTextGap(0);
        btnCatatanPasien.setName("btnCatatanPasien"); // NOI18N
        btnCatatanPasien.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCatatanPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCatatanPasienActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCatatanPasien);

        btnDataHAIs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360816018_tests.png"))); // NOI18N
        btnDataHAIs.setText("Data HAIs");
        btnDataHAIs.setIconTextGap(0);
        btnDataHAIs.setName("btnDataHAIs"); // NOI18N
        btnDataHAIs.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDataHAIs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataHAIsActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDataHAIs);

        btnHarianHAIs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_house_shelf_1378832.png"))); // NOI18N
        btnHarianHAIs.setText("Harian HAIs");
        btnHarianHAIs.setIconTextGap(0);
        btnHarianHAIs.setName("btnHarianHAIs"); // NOI18N
        btnHarianHAIs.setPreferredSize(new java.awt.Dimension(200, 90));
        btnHarianHAIs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHarianHAIsActionPerformed(evt);
            }
        });
        Panelmenu.add(btnHarianHAIs);

        btnBulananHAIs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_house_shelf_1378832.png"))); // NOI18N
        btnBulananHAIs.setText("Bulanan HAIs");
        btnBulananHAIs.setIconTextGap(0);
        btnBulananHAIs.setName("btnBulananHAIs"); // NOI18N
        btnBulananHAIs.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBulananHAIs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBulananHAIsActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBulananHAIs);

        btnHitungBor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/bedroom.png"))); // NOI18N
        btnHitungBor.setText("Hitung BOR");
        btnHitungBor.setIconTextGap(0);
        btnHitungBor.setName("btnHitungBor"); // NOI18N
        btnHitungBor.setPreferredSize(new java.awt.Dimension(200, 90));
        btnHitungBor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHitungBorActionPerformed(evt);
            }
        });
        Panelmenu.add(btnHitungBor);

        btnPerusahaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485357524_Company.png"))); // NOI18N
        btnPerusahaan.setText("Instansi/Perusahaan Pasien");
        btnPerusahaan.setIconTextGap(0);
        btnPerusahaan.setName("btnPerusahaan"); // NOI18N
        btnPerusahaan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPerusahaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerusahaanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPerusahaan);

        btnDaftarPermintaanResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485894_add-notes.png"))); // NOI18N
        btnDaftarPermintaanResep.setText("Daftar Resep Dokter");
        btnDaftarPermintaanResep.setIconTextGap(0);
        btnDaftarPermintaanResep.setName("btnDaftarPermintaanResep"); // NOI18N
        btnDaftarPermintaanResep.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDaftarPermintaanResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaftarPermintaanResepActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDaftarPermintaanResep);

        btnLamaPelayananApotek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnLamaPelayananApotek.setText("Lama Pelayanan Apotek");
        btnLamaPelayananApotek.setIconTextGap(0);
        btnLamaPelayananApotek.setName("btnLamaPelayananApotek"); // NOI18N
        btnLamaPelayananApotek.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLamaPelayananApotek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamaPelayananApotekActionPerformed(evt);
            }
        });
        Panelmenu.add(btnLamaPelayananApotek);

        btnHitungAlos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/bedroom.png"))); // NOI18N
        btnHitungAlos.setText("Hitung ALOS");
        btnHitungAlos.setIconTextGap(0);
        btnHitungAlos.setName("btnHitungAlos"); // NOI18N
        btnHitungAlos.setPreferredSize(new java.awt.Dimension(200, 90));
        btnHitungAlos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHitungAlosActionPerformed(evt);
            }
        });
        Panelmenu.add(btnHitungAlos);

        btnDetailTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnDetailTindakan.setText("Detail Tindakan");
        btnDetailTindakan.setIconTextGap(0);
        btnDetailTindakan.setName("btnDetailTindakan"); // NOI18N
        btnDetailTindakan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDetailTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailTindakanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDetailTindakan);

        btnRekapPoliAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnRekapPoliAnak.setText("Rekap Poli Anak");
        btnRekapPoliAnak.setIconTextGap(0);
        btnRekapPoliAnak.setName("btnRekapPoliAnak"); // NOI18N
        btnRekapPoliAnak.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapPoliAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapPoliAnakActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekapPoliAnak);

        btnBerkasDigitalPerawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_folder_images_61610.png"))); // NOI18N
        btnBerkasDigitalPerawatan.setText("Berkas Digital Perawatan");
        btnBerkasDigitalPerawatan.setIconTextGap(0);
        btnBerkasDigitalPerawatan.setName("btnBerkasDigitalPerawatan"); // NOI18N
        btnBerkasDigitalPerawatan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBerkasDigitalPerawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBerkasDigitalPerawatanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBerkasDigitalPerawatan);

        btnPnyMenularRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnPnyMenularRanap.setText("Pny Menular Ranap");
        btnPnyMenularRanap.setIconTextGap(0);
        btnPnyMenularRanap.setName("btnPnyMenularRanap"); // NOI18N
        btnPnyMenularRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPnyMenularRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPnyMenularRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPnyMenularRanap);

        btnPnyMenularRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnPnyMenularRalan.setText("Pny Menular Ralan");
        btnPnyMenularRalan.setIconTextGap(0);
        btnPnyMenularRalan.setName("btnPnyMenularRalan"); // NOI18N
        btnPnyMenularRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPnyMenularRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPnyMenularRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPnyMenularRalan);

        btnKategoriBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnKategoriBarang.setText("Kategori Obat, Alkes & BHP");
        btnKategoriBarang.setIconTextGap(0);
        btnKategoriBarang.setName("btnKategoriBarang"); // NOI18N
        btnKategoriBarang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKategoriBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKategoriBarangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKategoriBarang);

        btnGolonganBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485894_add-notes.png"))); // NOI18N
        btnGolonganBarang.setText("Golongan Obat, Alkes & BHP");
        btnGolonganBarang.setIconTextGap(0);
        btnGolonganBarang.setName("btnGolonganBarang"); // NOI18N
        btnGolonganBarang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnGolonganBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGolonganBarangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnGolonganBarang);

        btnObatPerTanggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485865_schedule.png"))); // NOI18N
        btnObatPerTanggal.setText("Obat/Alkes/BHP Per Tanggal");
        btnObatPerTanggal.setIconTextGap(0);
        btnObatPerTanggal.setName("btnObatPerTanggal"); // NOI18N
        btnObatPerTanggal.setPreferredSize(new java.awt.Dimension(200, 90));
        btnObatPerTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObatPerTanggalActionPerformed(evt);
            }
        });
        Panelmenu.add(btnObatPerTanggal);

        btnPenjualanPerTanggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485865_schedule.png"))); // NOI18N
        btnPenjualanPerTanggal.setText("Penjualan Bebas Per Tanggal");
        btnPenjualanPerTanggal.setIconTextGap(0);
        btnPenjualanPerTanggal.setName("btnPenjualanPerTanggal"); // NOI18N
        btnPenjualanPerTanggal.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPenjualanPerTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenjualanPerTanggalActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPenjualanPerTanggal);

        btnPembatalanPeriksaDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnPembatalanPeriksaDokter.setText("Pembatalan Periksa Per Dokter");
        btnPembatalanPeriksaDokter.setIconTextGap(0);
        btnPembatalanPeriksaDokter.setName("btnPembatalanPeriksaDokter"); // NOI18N
        btnPembatalanPeriksaDokter.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPembatalanPeriksaDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPembatalanPeriksaDokterActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPembatalanPeriksaDokter);

        btnPembayaranPerUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_TextEdit_37595.png"))); // NOI18N
        btnPembayaranPerUnit.setText("Pembayaran Per Unit");
        btnPembayaranPerUnit.setIconTextGap(0);
        btnPembayaranPerUnit.setName("btnPembayaranPerUnit"); // NOI18N
        btnPembayaranPerUnit.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPembayaranPerUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPembayaranPerUnitActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPembayaranPerUnit);

        btnRekapPembayaranPerUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_kde-document-open_23426.png"))); // NOI18N
        btnRekapPembayaranPerUnit.setText("Rekap Pembayaran Per Unit");
        btnRekapPembayaranPerUnit.setIconTextGap(0);
        btnRekapPembayaranPerUnit.setName("btnRekapPembayaranPerUnit"); // NOI18N
        btnRekapPembayaranPerUnit.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapPembayaranPerUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapPembayaranPerUnitActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekapPembayaranPerUnit);

        btnPengadaanIPSRSPerTanggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_document-new_23212.png"))); // NOI18N
        btnPengadaanIPSRSPerTanggal.setText("Pengadaan Non Medis Per Tanggal");
        btnPengadaanIPSRSPerTanggal.setIconTextGap(0);
        btnPengadaanIPSRSPerTanggal.setName("btnPengadaanIPSRSPerTanggal"); // NOI18N
        btnPengadaanIPSRSPerTanggal.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPengadaanIPSRSPerTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPengadaanIPSRSPerTanggalActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPengadaanIPSRSPerTanggal);

        btnStokKeluarIPSRSPerTanggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_document-open_23214.png"))); // NOI18N
        btnStokKeluarIPSRSPerTanggal.setText("Stok Keluar Non Medis Per Tanggal");
        btnStokKeluarIPSRSPerTanggal.setIconTextGap(0);
        btnStokKeluarIPSRSPerTanggal.setName("btnStokKeluarIPSRSPerTanggal"); // NOI18N
        btnStokKeluarIPSRSPerTanggal.setPreferredSize(new java.awt.Dimension(200, 90));
        btnStokKeluarIPSRSPerTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStokKeluarIPSRSPerTanggalActionPerformed(evt);
            }
        });
        Panelmenu.add(btnStokKeluarIPSRSPerTanggal);

        btnCekEntryRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_TextEdit_37595.png"))); // NOI18N
        btnCekEntryRalan.setText("Cek Entry Ralan");
        btnCekEntryRalan.setIconTextGap(0);
        btnCekEntryRalan.setName("btnCekEntryRalan"); // NOI18N
        btnCekEntryRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekEntryRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekEntryRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekEntryRalan);

        btnInaCBGKlaimBaruManual2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485358050_receptionist.png"))); // NOI18N
        btnInaCBGKlaimBaruManual2.setText("Klaim Baru Manual INACBG 2");
        btnInaCBGKlaimBaruManual2.setIconTextGap(0);
        btnInaCBGKlaimBaruManual2.setName("btnInaCBGKlaimBaruManual2"); // NOI18N
        btnInaCBGKlaimBaruManual2.setPreferredSize(new java.awt.Dimension(200, 90));
        btnInaCBGKlaimBaruManual2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInaCBGKlaimBaruManual2ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnInaCBGKlaimBaruManual2);

        btnPermintaanMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_packing_49602.png"))); // NOI18N
        btnPermintaanMedis.setText("Permintaan Obat & BHP");
        btnPermintaanMedis.setIconTextGap(0);
        btnPermintaanMedis.setName("btnPermintaanMedis"); // NOI18N
        btnPermintaanMedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPermintaanMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPermintaanMedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPermintaanMedis);

        btnRekapPermintaanMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_binary-tree_49580.png"))); // NOI18N
        btnRekapPermintaanMedis.setText("Rekap Permintaan Obat & BHP");
        btnRekapPermintaanMedis.setIconTextGap(0);
        btnRekapPermintaanMedis.setName("btnRekapPermintaanMedis"); // NOI18N
        btnRekapPermintaanMedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapPermintaanMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapPermintaanMedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekapPermintaanMedis);

        btnSuratPemesananMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_Select-Language_49621.png"))); // NOI18N
        btnSuratPemesananMedis.setText("Surat Pemesanan Obat & BHP");
        btnSuratPemesananMedis.setIconTextGap(0);
        btnSuratPemesananMedis.setName("btnSuratPemesananMedis"); // NOI18N
        btnSuratPemesananMedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuratPemesananMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuratPemesananMedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuratPemesananMedis);

        btnPermintaanNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_packing_49602.png"))); // NOI18N
        btnPermintaanNonMedis.setText("Permintaan Barang Non Medis");
        btnPermintaanNonMedis.setIconTextGap(0);
        btnPermintaanNonMedis.setName("btnPermintaanNonMedis"); // NOI18N
        btnPermintaanNonMedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPermintaanNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPermintaanNonMedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPermintaanNonMedis);

        btnRekapPermintaanNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_binary-tree_49580.png"))); // NOI18N
        btnRekapPermintaanNonMedis.setText("Rekap Permintaan Barang Non Medis");
        btnRekapPermintaanNonMedis.setIconTextGap(0);
        btnRekapPermintaanNonMedis.setName("btnRekapPermintaanNonMedis"); // NOI18N
        btnRekapPermintaanNonMedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapPermintaanNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapPermintaanNonMedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekapPermintaanNonMedis);

        btnSuratPemesananNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_Select-Language_49621.png"))); // NOI18N
        btnSuratPemesananNonMedis.setText("Surat Pemesanan Barang Non Medis");
        btnSuratPemesananNonMedis.setIconTextGap(0);
        btnSuratPemesananNonMedis.setName("btnSuratPemesananNonMedis"); // NOI18N
        btnSuratPemesananNonMedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuratPemesananNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuratPemesananNonMedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuratPemesananNonMedis);

        btnCekReferensiProsedurBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/stethoscope (1).png"))); // NOI18N
        btnCekReferensiProsedurBPJS.setText("Referensi Prosedur VClaim");
        btnCekReferensiProsedurBPJS.setIconTextGap(0);
        btnCekReferensiProsedurBPJS.setName("btnCekReferensiProsedurBPJS"); // NOI18N
        btnCekReferensiProsedurBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekReferensiProsedurBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekReferensiProsedurBPJSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekReferensiProsedurBPJS);

        btnCekReferensiKelasRawatBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnCekReferensiKelasRawatBPJS.setText("Referensi Kelas Rawat VClaim");
        btnCekReferensiKelasRawatBPJS.setIconTextGap(0);
        btnCekReferensiKelasRawatBPJS.setName("btnCekReferensiKelasRawatBPJS"); // NOI18N
        btnCekReferensiKelasRawatBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekReferensiKelasRawatBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekReferensiKelasRawatBPJSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekReferensiKelasRawatBPJS);

        btnCekReferensiDokterBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/user3.png"))); // NOI18N
        btnCekReferensiDokterBPJS.setText("Referensi Dokter VClaim");
        btnCekReferensiDokterBPJS.setIconTextGap(0);
        btnCekReferensiDokterBPJS.setName("btnCekReferensiDokterBPJS"); // NOI18N
        btnCekReferensiDokterBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekReferensiDokterBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekReferensiDokterBPJSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekReferensiDokterBPJS);

        btnCekReferensiSpesialistikBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/teacher.png"))); // NOI18N
        btnCekReferensiSpesialistikBPJS.setText("Referensi Spesialistik VClaim");
        btnCekReferensiSpesialistikBPJS.setIconTextGap(0);
        btnCekReferensiSpesialistikBPJS.setName("btnCekReferensiSpesialistikBPJS"); // NOI18N
        btnCekReferensiSpesialistikBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekReferensiSpesialistikBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekReferensiSpesialistikBPJSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekReferensiSpesialistikBPJS);

        btnCekReferensiRuangRawatBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485357890_hospital.png"))); // NOI18N
        btnCekReferensiRuangRawatBPJS.setText("Referensi Ruang Rawat VClaim");
        btnCekReferensiRuangRawatBPJS.setIconTextGap(0);
        btnCekReferensiRuangRawatBPJS.setName("btnCekReferensiRuangRawatBPJS"); // NOI18N
        btnCekReferensiRuangRawatBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekReferensiRuangRawatBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekReferensiRuangRawatBPJSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekReferensiRuangRawatBPJS);

        btnCekReferensiCaraKeluarBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485642_edit-notes.png"))); // NOI18N
        btnCekReferensiCaraKeluarBPJS.setText("Referensi Cara Keluar VClaim");
        btnCekReferensiCaraKeluarBPJS.setIconTextGap(0);
        btnCekReferensiCaraKeluarBPJS.setName("btnCekReferensiCaraKeluarBPJS"); // NOI18N
        btnCekReferensiCaraKeluarBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekReferensiCaraKeluarBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekReferensiCaraKeluarBPJSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekReferensiCaraKeluarBPJS);

        btnCekReferensiPascaPulangBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/health.png"))); // NOI18N
        btnCekReferensiPascaPulangBPJS.setText("Referensi Pasca Pulang VClaim");
        btnCekReferensiPascaPulangBPJS.setIconTextGap(0);
        btnCekReferensiPascaPulangBPJS.setName("btnCekReferensiPascaPulangBPJS"); // NOI18N
        btnCekReferensiPascaPulangBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekReferensiPascaPulangBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekReferensiPascaPulangBPJSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekReferensiPascaPulangBPJS);

        btnDetailVKOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnDetailVKOK.setText("Detail VK & OK");
        btnDetailVKOK.setIconTextGap(0);
        btnDetailVKOK.setName("btnDetailVKOK"); // NOI18N
        btnDetailVKOK.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDetailVKOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailVKOKActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDetailVKOK);

        btnCekBPJSNomorRujukanRS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist.png"))); // NOI18N
        btnCekBPJSNomorRujukanRS.setText("Cek No.Rujukan RS di VClaim");
        btnCekBPJSNomorRujukanRS.setIconTextGap(0);
        btnCekBPJSNomorRujukanRS.setName("btnCekBPJSNomorRujukanRS"); // NOI18N
        btnCekBPJSNomorRujukanRS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekBPJSNomorRujukanRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekBPJSNomorRujukanRSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekBPJSNomorRujukanRS);

        btnCekBPJSRujukanKartuPCare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist.png"))); // NOI18N
        btnCekBPJSRujukanKartuPCare.setText("Cek Rujukan Kartu PCare di VClaim");
        btnCekBPJSRujukanKartuPCare.setIconTextGap(0);
        btnCekBPJSRujukanKartuPCare.setName("btnCekBPJSRujukanKartuPCare"); // NOI18N
        btnCekBPJSRujukanKartuPCare.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekBPJSRujukanKartuPCare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekBPJSRujukanKartuPCareActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekBPJSRujukanKartuPCare);

        btnCekBPJSRujukanKartuRS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist.png"))); // NOI18N
        btnCekBPJSRujukanKartuRS.setText("Cek Rujukan Kartu RS di VClaim");
        btnCekBPJSRujukanKartuRS.setIconTextGap(0);
        btnCekBPJSRujukanKartuRS.setName("btnCekBPJSRujukanKartuRS"); // NOI18N
        btnCekBPJSRujukanKartuRS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekBPJSRujukanKartuRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekBPJSRujukanKartuRSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekBPJSRujukanKartuRS);

        btnRujukanKeluarBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485357758_Doctor.png"))); // NOI18N
        btnRujukanKeluarBPJS.setText("Data Rujukan Keluar VClaim");
        btnRujukanKeluarBPJS.setIconTextGap(0);
        btnRujukanKeluarBPJS.setName("btnRujukanKeluarBPJS"); // NOI18N
        btnRujukanKeluarBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRujukanKeluarBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRujukanKeluarBPJSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRujukanKeluarBPJS);

        btnStokKeluarMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/shopping-basket-full.png"))); // NOI18N
        btnStokKeluarMedis.setText("Stok Keluar Medis");
        btnStokKeluarMedis.setIconTextGap(0);
        btnStokKeluarMedis.setName("btnStokKeluarMedis"); // NOI18N
        btnStokKeluarMedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnStokKeluarMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStokKeluarMedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnStokKeluarMedis);

        btnJMDetailDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnJMDetailDokter2.setText("Detail JM Dokter 2");
        btnJMDetailDokter2.setIconTextGap(0);
        btnJMDetailDokter2.setName("btnJMDetailDokter2"); // NOI18N
        btnJMDetailDokter2.setPreferredSize(new java.awt.Dimension(200, 90));
        btnJMDetailDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJMDetailDokter2ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnJMDetailDokter2);

        btnPengaduan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_message_add_17398.png"))); // NOI18N
        btnPengaduan.setText("Pengaduan/Chat");
        btnPengaduan.setIconTextGap(0);
        btnPengaduan.setName("btnPengaduan"); // NOI18N
        btnPengaduan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPengaduan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPengaduanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPengaduan);

        btnSensusHarianRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/custom-reports.png"))); // NOI18N
        btnSensusHarianRalan.setText("Sensus Harian Ralan");
        btnSensusHarianRalan.setIconTextGap(0);
        btnSensusHarianRalan.setName("btnSensusHarianRalan"); // NOI18N
        btnSensusHarianRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSensusHarianRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSensusHarianRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSensusHarianRalan);

        btnMetodeRacik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_document-new_23212.png"))); // NOI18N
        btnMetodeRacik.setText("Metode Racik");
        btnMetodeRacik.setIconTextGap(0);
        btnMetodeRacik.setName("btnMetodeRacik"); // NOI18N
        btnMetodeRacik.setPreferredSize(new java.awt.Dimension(200, 90));
        btnMetodeRacik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMetodeRacikActionPerformed(evt);
            }
        });
        Panelmenu.add(btnMetodeRacik);

        btnPembayaranAkunBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404046811_money.png"))); // NOI18N
        btnPembayaranAkunBayar.setText("Pembayaran Per Akun Bayar");
        btnPembayaranAkunBayar.setIconTextGap(0);
        btnPembayaranAkunBayar.setName("btnPembayaranAkunBayar"); // NOI18N
        btnPembayaranAkunBayar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPembayaranAkunBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPembayaranAkunBayarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPembayaranAkunBayar);

        btnPenggunaObatResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/family.png"))); // NOI18N
        btnPenggunaObatResep.setText("Pengguna Obat/Alkes/BHP Resep");
        btnPenggunaObatResep.setIconTextGap(0);
        btnPenggunaObatResep.setName("btnPenggunaObatResep"); // NOI18N
        btnPenggunaObatResep.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPenggunaObatResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenggunaObatResepActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPenggunaObatResep);

        btnRekapPenerimaanObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_TextEdit_37595.png"))); // NOI18N
        btnRekapPenerimaanObat.setText("Rekap Penerimaan Obat & BHP");
        btnRekapPenerimaanObat.setIconTextGap(0);
        btnRekapPenerimaanObat.setName("btnRekapPenerimaanObat"); // NOI18N
        btnRekapPenerimaanObat.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapPenerimaanObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapPenerimaanObatActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekapPenerimaanObat);

        btnMasterBerkasPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/folder.png"))); // NOI18N
        btnMasterBerkasPegawai.setText("Master Berkas Pegawai");
        btnMasterBerkasPegawai.setIconTextGap(0);
        btnMasterBerkasPegawai.setName("btnMasterBerkasPegawai"); // NOI18N
        btnMasterBerkasPegawai.setPreferredSize(new java.awt.Dimension(200, 90));
        btnMasterBerkasPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasterBerkasPegawaiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnMasterBerkasPegawai);

        btnBerkasPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481002437_partners.png"))); // NOI18N
        btnBerkasPegawai.setText("Berkas Kepegawaian");
        btnBerkasPegawai.setIconTextGap(0);
        btnBerkasPegawai.setName("btnBerkasPegawai"); // NOI18N
        btnBerkasPegawai.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBerkasPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBerkasPegawaiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBerkasPegawai);

        btnRiwayatJabatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_TextEdit_37595.png"))); // NOI18N
        btnRiwayatJabatan.setText("Riwayat Jabatan");
        btnRiwayatJabatan.setIconTextGap(0);
        btnRiwayatJabatan.setName("btnRiwayatJabatan"); // NOI18N
        btnRiwayatJabatan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRiwayatJabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRiwayatJabatanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRiwayatJabatan);

        btnRiwayatPendidikan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481038237_Adobe_Bridge_CS.png"))); // NOI18N
        btnRiwayatPendidikan.setText("Riwayat Pendidikan");
        btnRiwayatPendidikan.setIconTextGap(0);
        btnRiwayatPendidikan.setName("btnRiwayatPendidikan"); // NOI18N
        btnRiwayatPendidikan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRiwayatPendidikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRiwayatPendidikanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRiwayatPendidikan);

        btnRiwayatNaikGaji.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/coins.png"))); // NOI18N
        btnRiwayatNaikGaji.setText("Riwayat Naik Gaji");
        btnRiwayatNaikGaji.setIconTextGap(0);
        btnRiwayatNaikGaji.setName("btnRiwayatNaikGaji"); // NOI18N
        btnRiwayatNaikGaji.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRiwayatNaikGaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRiwayatNaikGajiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRiwayatNaikGaji);

        btnKegiatanIlmiah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_gtk-find-and-replace_39047.png"))); // NOI18N
        btnKegiatanIlmiah.setText("Kegiatan Ilmiah & Pelatihan");
        btnKegiatanIlmiah.setIconTextGap(0);
        btnKegiatanIlmiah.setName("btnKegiatanIlmiah"); // NOI18N
        btnKegiatanIlmiah.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKegiatanIlmiah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKegiatanIlmiahActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKegiatanIlmiah);

        btnRiwayatPenghargaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_folder_images_61610.png"))); // NOI18N
        btnRiwayatPenghargaan.setText("Riwayat Penghargaan");
        btnRiwayatPenghargaan.setIconTextGap(0);
        btnRiwayatPenghargaan.setName("btnRiwayatPenghargaan"); // NOI18N
        btnRiwayatPenghargaan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRiwayatPenghargaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRiwayatPenghargaanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRiwayatPenghargaan);

        btnRiwayatPenelitian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_research_87460.png"))); // NOI18N
        btnRiwayatPenelitian.setText("Riwayat Penelitian");
        btnRiwayatPenelitian.setIconTextGap(0);
        btnRiwayatPenelitian.setName("btnRiwayatPenelitian"); // NOI18N
        btnRiwayatPenelitian.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRiwayatPenelitian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRiwayatPenelitianActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRiwayatPenelitian);

        btnPenerimaanNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481038192_market.png"))); // NOI18N
        btnPenerimaanNonMedis.setText("Penerimaan Barang Non Medis");
        btnPenerimaanNonMedis.setIconTextGap(0);
        btnPenerimaanNonMedis.setName("btnPenerimaanNonMedis"); // NOI18N
        btnPenerimaanNonMedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPenerimaanNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenerimaanNonMedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPenerimaanNonMedis);

        btnBayarPesanNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487067_calculator.png"))); // NOI18N
        btnBayarPesanNonMedis.setText("Bayar Pesan Non Medis");
        btnBayarPesanNonMedis.setIconTextGap(0);
        btnBayarPesanNonMedis.setName("btnBayarPesanNonMedis"); // NOI18N
        btnBayarPesanNonMedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBayarPesanNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarPesanNonMedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBayarPesanNonMedis);

        btnHutangNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487067_calculator.png"))); // NOI18N
        btnHutangNonMedis.setText("Hutang Non Medis");
        btnHutangNonMedis.setIconTextGap(0);
        btnHutangNonMedis.setName("btnHutangNonMedis"); // NOI18N
        btnHutangNonMedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnHutangNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHutangNonMedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnHutangNonMedis);

        btnRekapPenerimaanNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_TextEdit_37595.png"))); // NOI18N
        btnRekapPenerimaanNonMedis.setText("Rekap Penerimaan Non Medis");
        btnRekapPenerimaanNonMedis.setIconTextGap(0);
        btnRekapPenerimaanNonMedis.setName("btnRekapPenerimaanNonMedis"); // NOI18N
        btnRekapPenerimaanNonMedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapPenerimaanNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapPenerimaanNonMedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekapPenerimaanNonMedis);

        btnInsidenKeselamatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481002123_wheelchair.png"))); // NOI18N
        btnInsidenKeselamatan.setText("Insiden Keselamatan");
        btnInsidenKeselamatan.setIconTextGap(0);
        btnInsidenKeselamatan.setName("btnInsidenKeselamatan"); // NOI18N
        btnInsidenKeselamatan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnInsidenKeselamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsidenKeselamatanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnInsidenKeselamatan);

        btnInsidenKeselamatanPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485357758_Doctor.png"))); // NOI18N
        btnInsidenKeselamatanPasien.setText("Data Insiden Keselamatan");
        btnInsidenKeselamatanPasien.setIconTextGap(0);
        btnInsidenKeselamatanPasien.setName("btnInsidenKeselamatanPasien"); // NOI18N
        btnInsidenKeselamatanPasien.setPreferredSize(new java.awt.Dimension(200, 90));
        btnInsidenKeselamatanPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsidenKeselamatanPasienActionPerformed(evt);
            }
        });
        Panelmenu.add(btnInsidenKeselamatanPasien);

        btnRiwayatBatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481038237_Adobe_Bridge_CS.png"))); // NOI18N
        btnRiwayatBatch.setText("Riwayat Batch");
        btnRiwayatBatch.setIconTextGap(0);
        btnRiwayatBatch.setName("btnRiwayatBatch"); // NOI18N
        btnRiwayatBatch.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRiwayatBatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRiwayatBatchActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRiwayatBatch);

        btnPiutangPerAkunPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/stock_task.png"))); // NOI18N
        btnPiutangPerAkunPiutang.setText("Piutang Per Akun Piutang");
        btnPiutangPerAkunPiutang.setIconTextGap(0);
        btnPiutangPerAkunPiutang.setName("btnPiutangPerAkunPiutang"); // NOI18N
        btnPiutangPerAkunPiutang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPiutangPerAkunPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPiutangPerAkunPiutangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPiutangPerAkunPiutang);

        btnSuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_Login Manager_3503.png"))); // NOI18N
        btnSuku.setText("Suku/Bangsa Pasien");
        btnSuku.setIconTextGap(0);
        btnSuku.setName("btnSuku"); // NOI18N
        btnSuku.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSukuActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuku);

        btnBahasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_Orange forum_54521.png"))); // NOI18N
        btnBahasa.setText("Bahasa Pasien");
        btnBahasa.setIconTextGap(0);
        btnBahasa.setName("btnBahasa"); // NOI18N
        btnBahasa.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBahasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBahasaActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBahasa);

        btnCacatFisik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/plaster.png"))); // NOI18N
        btnCacatFisik.setText("Cacat Fisik");
        btnCacatFisik.setIconTextGap(0);
        btnCacatFisik.setName("btnCacatFisik"); // NOI18N
        btnCacatFisik.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCacatFisik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCacatFisikActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCacatFisik);

        btnJadwalOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_Alarm_132148.png"))); // NOI18N
        btnJadwalOperasi.setText("Jadwal Operasi");
        btnJadwalOperasi.setIconTextGap(0);
        btnJadwalOperasi.setName("btnJadwalOperasi"); // NOI18N
        btnJadwalOperasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnJadwalOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJadwalOperasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnJadwalOperasi);

        btnMapingPoliBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_Pink_Time_Machine_B_66659.png"))); // NOI18N
        btnMapingPoliBPJS.setText("Mapping Poli VClaim");
        btnMapingPoliBPJS.setIconTextGap(0);
        btnMapingPoliBPJS.setName("btnMapingPoliBPJS"); // NOI18N
        btnMapingPoliBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnMapingPoliBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapingPoliBPJSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnMapingPoliBPJS);

        btnBarangCSSD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_order-history_49596.png"))); // NOI18N
        btnBarangCSSD.setText("Barang CSSD");
        btnBarangCSSD.setIconTextGap(0);
        btnBarangCSSD.setName("btnBarangCSSD"); // NOI18N
        btnBarangCSSD.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBarangCSSD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangCSSDActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBarangCSSD);

        btnSKDPBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_kde-document-open_23426.png"))); // NOI18N
        btnSKDPBPJS.setText("SKDP BPJS");
        btnSKDPBPJS.setIconTextGap(0);
        btnSKDPBPJS.setName("btnSKDPBPJS"); // NOI18N
        btnSKDPBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSKDPBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSKDPBPJSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSKDPBPJS);

        btnBookingRegistrasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_Address Book_32590.png"))); // NOI18N
        btnBookingRegistrasi.setText("Booking Registrasi");
        btnBookingRegistrasi.setIconTextGap(0);
        btnBookingRegistrasi.setName("btnBookingRegistrasi"); // NOI18N
        btnBookingRegistrasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBookingRegistrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookingRegistrasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBookingRegistrasi);

        btnCekReferensiPropinsiBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_map_299050.png"))); // NOI18N
        btnCekReferensiPropinsiBPJS.setText("Referensi Propinsi VClaim");
        btnCekReferensiPropinsiBPJS.setIconTextGap(0);
        btnCekReferensiPropinsiBPJS.setName("btnCekReferensiPropinsiBPJS"); // NOI18N
        btnCekReferensiPropinsiBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekReferensiPropinsiBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekReferensiPropinsiBPJSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekReferensiPropinsiBPJS);

        btnCekReferensiKabupatenBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_map_285662.png"))); // NOI18N
        btnCekReferensiKabupatenBPJS.setText("Referensi Kabupaten VClaim");
        btnCekReferensiKabupatenBPJS.setIconTextGap(0);
        btnCekReferensiKabupatenBPJS.setName("btnCekReferensiKabupatenBPJS"); // NOI18N
        btnCekReferensiKabupatenBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekReferensiKabupatenBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekReferensiKabupatenBPJSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekReferensiKabupatenBPJS);

        btnCekReferensiKecamatanBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_advantage_nearby_1034361.png"))); // NOI18N
        btnCekReferensiKecamatanBPJS.setText("Referensi Kecamatan VClaim");
        btnCekReferensiKecamatanBPJS.setIconTextGap(0);
        btnCekReferensiKecamatanBPJS.setName("btnCekReferensiKecamatanBPJS"); // NOI18N
        btnCekReferensiKecamatanBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekReferensiKecamatanBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekReferensiKecamatanBPJSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekReferensiKecamatanBPJS);

        btnCekReferensiDokterDPJPBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/doctor_assistant.png"))); // NOI18N
        btnCekReferensiDokterDPJPBPJS.setText("Referensi Dokter DPJP VClaim");
        btnCekReferensiDokterDPJPBPJS.setIconTextGap(0);
        btnCekReferensiDokterDPJPBPJS.setName("btnCekReferensiDokterDPJPBPJS"); // NOI18N
        btnCekReferensiDokterDPJPBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekReferensiDokterDPJPBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekReferensiDokterDPJPBPJSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekReferensiDokterDPJPBPJS);

        btnCekBPJSRiwayatRujukanRS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/doctor 3.png"))); // NOI18N
        btnCekBPJSRiwayatRujukanRS.setText("Riwayat Rujukan RS di VClaim");
        btnCekBPJSRiwayatRujukanRS.setIconTextGap(0);
        btnCekBPJSRiwayatRujukanRS.setName("btnCekBPJSRiwayatRujukanRS"); // NOI18N
        btnCekBPJSRiwayatRujukanRS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekBPJSRiwayatRujukanRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekBPJSRiwayatRujukanRSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekBPJSRiwayatRujukanRS);

        btnCekBPJSTanggalRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485865_schedule.png"))); // NOI18N
        btnCekBPJSTanggalRujukan.setText("Tanggal Rujukan di VClaim");
        btnCekBPJSTanggalRujukan.setIconTextGap(0);
        btnCekBPJSTanggalRujukan.setName("btnCekBPJSTanggalRujukan"); // NOI18N
        btnCekBPJSTanggalRujukan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekBPJSTanggalRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekBPJSTanggalRujukanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekBPJSTanggalRujukan);

        btnPermintaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_laboratory_44676.png"))); // NOI18N
        btnPermintaanLab.setText("Permintaan Lab");
        btnPermintaanLab.setIconTextGap(0);
        btnPermintaanLab.setName("btnPermintaanLab"); // NOI18N
        btnPermintaanLab.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPermintaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPermintaanLabActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPermintaanLab);

        btnPermintaanRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_Thorax_X-Ray_Black_63791.png"))); // NOI18N
        btnPermintaanRadiologi.setText("Permintaan Radiologi");
        btnPermintaanRadiologi.setIconTextGap(0);
        btnPermintaanRadiologi.setName("btnPermintaanRadiologi"); // NOI18N
        btnPermintaanRadiologi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPermintaanRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPermintaanRadiologiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPermintaanRadiologi);

        btnSuratIndeks.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_open-email_264844.png"))); // NOI18N
        btnSuratIndeks.setText("Indeks Surat");
        btnSuratIndeks.setIconTextGap(0);
        btnSuratIndeks.setName("btnSuratIndeks"); // NOI18N
        btnSuratIndeks.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuratIndeks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuratIndeksActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuratIndeks);

        btnSuratMap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_orange-folder-mail_25242.png"))); // NOI18N
        btnSuratMap.setText("Map Surat");
        btnSuratMap.setIconTextGap(0);
        btnSuratMap.setName("btnSuratMap"); // NOI18N
        btnSuratMap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuratMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuratMapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuratMap);

        btnSuratAlmari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_cabinet_49336.png"))); // NOI18N
        btnSuratAlmari.setText("Almari Surat");
        btnSuratAlmari.setIconTextGap(0);
        btnSuratAlmari.setName("btnSuratAlmari"); // NOI18N
        btnSuratAlmari.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuratAlmari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuratAlmariActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuratAlmari);

        btnSuratRak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_shelf_104409.png"))); // NOI18N
        btnSuratRak.setText("Rak Surat");
        btnSuratRak.setIconTextGap(0);
        btnSuratRak.setName("btnSuratRak"); // NOI18N
        btnSuratRak.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuratRak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuratRakActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuratRak);

        btnSuratRuang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_kfm_home_18010.png"))); // NOI18N
        btnSuratRuang.setText("Ruang Surat");
        btnSuratRuang.setIconTextGap(0);
        btnSuratRuang.setName("btnSuratRuang"); // NOI18N
        btnSuratRuang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuratRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuratRuangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuratRuang);

        btnSuratKlasifikasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_kontact_8762.png"))); // NOI18N
        btnSuratKlasifikasi.setText("Klasifikasi Surat");
        btnSuratKlasifikasi.setIconTextGap(0);
        btnSuratKlasifikasi.setName("btnSuratKlasifikasi"); // NOI18N
        btnSuratKlasifikasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuratKlasifikasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuratKlasifikasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuratKlasifikasi);

        btnSuratStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_26-Mail_34317.png"))); // NOI18N
        btnSuratStatus.setText("Status Surat");
        btnSuratStatus.setIconTextGap(0);
        btnSuratStatus.setName("btnSuratStatus"); // NOI18N
        btnSuratStatus.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuratStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuratStatusActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuratStatus);

        btnSuratSifat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_private_mail_44691.png"))); // NOI18N
        btnSuratSifat.setText("Sifat Surat");
        btnSuratSifat.setIconTextGap(0);
        btnSuratSifat.setName("btnSuratSifat"); // NOI18N
        btnSuratSifat.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuratSifat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuratSifatActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuratSifat);

        btnSuratBalas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_mail-reply-all_118782.png"))); // NOI18N
        btnSuratBalas.setText("Stts Balas Surat");
        btnSuratBalas.setIconTextGap(0);
        btnSuratBalas.setName("btnSuratBalas"); // NOI18N
        btnSuratBalas.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuratBalas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuratBalasActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuratBalas);

        btnSuratMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_e-mail2 _36619.png"))); // NOI18N
        btnSuratMasuk.setText("Surat Masuk");
        btnSuratMasuk.setIconTextGap(0);
        btnSuratMasuk.setName("btnSuratMasuk"); // NOI18N
        btnSuratMasuk.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuratMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuratMasukActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuratMasuk);

        btnSirkulasi3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487125_system-restart-panel.png"))); // NOI18N
        btnSirkulasi3.setText("Sirkulasi Obat, Alkes & BHP 3");
        btnSirkulasi3.setIconTextGap(0);
        btnSirkulasi3.setName("btnSirkulasi3"); // NOI18N
        btnSirkulasi3.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSirkulasi3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSirkulasi3ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSirkulasi3);

        btnRanapPerRuang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_House_132045.png"))); // NOI18N
        btnRanapPerRuang.setText("Ranap Per Ruang");
        btnRanapPerRuang.setIconTextGap(0);
        btnRanapPerRuang.setName("btnRanapPerRuang"); // NOI18N
        btnRanapPerRuang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRanapPerRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRanapPerRuangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRanapPerRuang);

        btnPenyakitRanapCaraBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_24_DNA_2064499.png"))); // NOI18N
        btnPenyakitRanapCaraBayar.setText("Penyakit Ranap Per Cara Bayar");
        btnPenyakitRanapCaraBayar.setIconTextGap(0);
        btnPenyakitRanapCaraBayar.setName("btnPenyakitRanapCaraBayar"); // NOI18N
        btnPenyakitRanapCaraBayar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPenyakitRanapCaraBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenyakitRanapCaraBayarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPenyakitRanapCaraBayar);

        btnSetInputParsial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_Finance_loan_money_1889199.png"))); // NOI18N
        btnSetInputParsial.setText("Set Input Parsial");
        btnSetInputParsial.setIconTextGap(0);
        btnSetInputParsial.setName("btnSetInputParsial"); // NOI18N
        btnSetInputParsial.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetInputParsial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetInputParsialActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetInputParsial);

        btnLamaPelayananRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnLamaPelayananRadiologi.setText("Lama Pelayanan Radiologi");
        btnLamaPelayananRadiologi.setIconTextGap(0);
        btnLamaPelayananRadiologi.setName("btnLamaPelayananRadiologi"); // NOI18N
        btnLamaPelayananRadiologi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLamaPelayananRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamaPelayananRadiologiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnLamaPelayananRadiologi);

        btnLamaPelayananLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnLamaPelayananLab.setText("Lama Pelayanan Lab");
        btnLamaPelayananLab.setIconTextGap(0);
        btnLamaPelayananLab.setName("btnLamaPelayananLab"); // NOI18N
        btnLamaPelayananLab.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLamaPelayananLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamaPelayananLabActionPerformed(evt);
            }
        });
        Panelmenu.add(btnLamaPelayananLab);

        btnCekSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_folder_search_48770.png"))); // NOI18N
        btnCekSEP.setText("Cek Nomor SEP");
        btnCekSEP.setIconTextGap(0);
        btnCekSEP.setName("btnCekSEP"); // NOI18N
        btnCekSEP.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekSEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekSEPActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekSEP);

        btnSuratKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_email_3327.png"))); // NOI18N
        btnSuratKeluar.setText("Surat Keluar");
        btnSuratKeluar.setIconTextGap(0);
        btnSuratKeluar.setName("btnSuratKeluar"); // NOI18N
        btnSuratKeluar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuratKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuratKeluarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuratKeluar);

        btnKegiatanFarmasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_website_-_address_book_3440827.png"))); // NOI18N
        btnKegiatanFarmasi.setText("Kegiatan Farmasi");
        btnKegiatanFarmasi.setIconTextGap(0);
        btnKegiatanFarmasi.setName("btnKegiatanFarmasi"); // NOI18N
        btnKegiatanFarmasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKegiatanFarmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKegiatanFarmasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKegiatanFarmasi);

        btnOpnameIPSRS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/ark2.png"))); // NOI18N
        btnOpnameIPSRS.setText("Stok Opname Non Medis");
        btnOpnameIPSRS.setIconTextGap(0);
        btnOpnameIPSRS.setName("btnOpnameIPSRS"); // NOI18N
        btnOpnameIPSRS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnOpnameIPSRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpnameIPSRSActionPerformed(evt);
            }
        });
        Panelmenu.add(btnOpnameIPSRS);

        btnSirkulasiNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487125_system-restart-panel.png"))); // NOI18N
        btnSirkulasiNonMedis.setText("Sirkulasi Non Medis");
        btnSirkulasiNonMedis.setIconTextGap(0);
        btnSirkulasiNonMedis.setName("btnSirkulasiNonMedis"); // NOI18N
        btnSirkulasiNonMedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSirkulasiNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSirkulasiNonMedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSirkulasiNonMedis);

        btnRekapLabPerTahun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_projects_63140.png"))); // NOI18N
        btnRekapLabPerTahun.setText("Rekap Lab Per Tahun");
        btnRekapLabPerTahun.setIconTextGap(0);
        btnRekapLabPerTahun.setName("btnRekapLabPerTahun"); // NOI18N
        btnRekapLabPerTahun.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapLabPerTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapLabPerTahunActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekapLabPerTahun);

        btnPerujukLabPerTahun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/doctor2.png"))); // NOI18N
        btnPerujukLabPerTahun.setText("Perujuk Lab Per Tahun");
        btnPerujukLabPerTahun.setIconTextGap(0);
        btnPerujukLabPerTahun.setName("btnPerujukLabPerTahun"); // NOI18N
        btnPerujukLabPerTahun.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPerujukLabPerTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerujukLabPerTahunActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPerujukLabPerTahun);

        btnRekapRadiologiPerTahun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_Thorax_X-Ray_Black_63791.png"))); // NOI18N
        btnRekapRadiologiPerTahun.setText("Rekap Radiologi Per Tahun");
        btnRekapRadiologiPerTahun.setIconTextGap(0);
        btnRekapRadiologiPerTahun.setName("btnRekapRadiologiPerTahun"); // NOI18N
        btnRekapRadiologiPerTahun.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapRadiologiPerTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapRadiologiPerTahunActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekapRadiologiPerTahun);

        btnPerujukRadiologiPerTahun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/doctor_assistant.png"))); // NOI18N
        btnPerujukRadiologiPerTahun.setText("Perujuk Radiologi Per Tahun");
        btnPerujukRadiologiPerTahun.setIconTextGap(0);
        btnPerujukRadiologiPerTahun.setName("btnPerujukRadiologiPerTahun"); // NOI18N
        btnPerujukRadiologiPerTahun.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPerujukRadiologiPerTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerujukRadiologiPerTahunActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPerujukRadiologiPerTahun);

        btnJumlahPorsiDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/iconfinder_bibimbub_3377053.png"))); // NOI18N
        btnJumlahPorsiDiet.setText("Rekap Bulanan Porsi Diet");
        btnJumlahPorsiDiet.setIconTextGap(0);
        btnJumlahPorsiDiet.setName("btnJumlahPorsiDiet"); // NOI18N
        btnJumlahPorsiDiet.setPreferredSize(new java.awt.Dimension(200, 90));
        btnJumlahPorsiDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJumlahPorsiDietActionPerformed(evt);
            }
        });
        Panelmenu.add(btnJumlahPorsiDiet);

        btnJumlahMacamDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/iconfinder_ramen_3377055.png"))); // NOI18N
        btnJumlahMacamDiet.setText("Rekap Bulanan Macam Diet");
        btnJumlahMacamDiet.setIconTextGap(0);
        btnJumlahMacamDiet.setName("btnJumlahMacamDiet"); // NOI18N
        btnJumlahMacamDiet.setPreferredSize(new java.awt.Dimension(200, 90));
        btnJumlahMacamDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJumlahMacamDietActionPerformed(evt);
            }
        });
        Panelmenu.add(btnJumlahMacamDiet);

        btnPaymentPoint2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/coins.png"))); // NOI18N
        btnPaymentPoint2.setText("Payment Point 2");
        btnPaymentPoint2.setIconTextGap(0);
        btnPaymentPoint2.setName("btnPaymentPoint2"); // NOI18N
        btnPaymentPoint2.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPaymentPoint2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaymentPoint2ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPaymentPoint2);

        btnPembayaranAkunBayar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404046811_money.png"))); // NOI18N
        btnPembayaranAkunBayar2.setText("Pembayaran Per Akun Bayar 2");
        btnPembayaranAkunBayar2.setIconTextGap(0);
        btnPembayaranAkunBayar2.setName("btnPembayaranAkunBayar2"); // NOI18N
        btnPembayaranAkunBayar2.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPembayaranAkunBayar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPembayaranAkunBayar2ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPembayaranAkunBayar2);

        btnHAIsBangsal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_house_shelf_1378832.png"))); // NOI18N
        btnHAIsBangsal.setText("HAIs Per Kamar/Bangsal");
        btnHAIsBangsal.setIconTextGap(0);
        btnHAIsBangsal.setName("btnHAIsBangsal"); // NOI18N
        btnHAIsBangsal.setPreferredSize(new java.awt.Dimension(200, 90));
        btnHAIsBangsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHAIsBangsalActionPerformed(evt);
            }
        });
        Panelmenu.add(btnHAIsBangsal);

        btnPPNObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_money_299107.png"))); // NOI18N
        btnPPNObat.setText("PPN Obat");
        btnPPNObat.setIconTextGap(0);
        btnPPNObat.setName("btnPPNObat"); // NOI18N
        btnPPNObat.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPPNObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPPNObatActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPPNObat);

        btnSaldoAkunPerBulan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/iconfinder_icons-05_799761.png"))); // NOI18N
        btnSaldoAkunPerBulan.setText("Saldo Akun Per Bulan");
        btnSaldoAkunPerBulan.setIconTextGap(0);
        btnSaldoAkunPerBulan.setName("btnSaldoAkunPerBulan"); // NOI18N
        btnSaldoAkunPerBulan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSaldoAkunPerBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaldoAkunPerBulanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSaldoAkunPerBulan);

        btnDisplayApotek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/find.png"))); // NOI18N
        btnDisplayApotek.setText("Display Antrian Apotek");
        btnDisplayApotek.setIconTextGap(0);
        btnDisplayApotek.setName("btnDisplayApotek"); // NOI18N
        btnDisplayApotek.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDisplayApotek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayApotekActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDisplayApotek);

        btnCekSisruteFaskes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/industry.png"))); // NOI18N
        btnCekSisruteFaskes.setText("Referensi Faskes Sisrute");
        btnCekSisruteFaskes.setIconTextGap(0);
        btnCekSisruteFaskes.setName("btnCekSisruteFaskes"); // NOI18N
        btnCekSisruteFaskes.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekSisruteFaskes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekSisruteFaskesActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekSisruteFaskes);

        btnCekSisruteAlasanRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_todo_list_add_17451.png"))); // NOI18N
        btnCekSisruteAlasanRujuk.setText("Referensi Alasan Rujuk Sisrute");
        btnCekSisruteAlasanRujuk.setIconTextGap(0);
        btnCekSisruteAlasanRujuk.setName("btnCekSisruteAlasanRujuk"); // NOI18N
        btnCekSisruteAlasanRujuk.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekSisruteAlasanRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekSisruteAlasanRujukActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekSisruteAlasanRujuk);

        btnCekSisruteDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnCekSisruteDiagnosa.setText("Referensi Diagnosa Sisrute");
        btnCekSisruteDiagnosa.setIconTextGap(0);
        btnCekSisruteDiagnosa.setName("btnCekSisruteDiagnosa"); // NOI18N
        btnCekSisruteDiagnosa.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekSisruteDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekSisruteDiagnosaActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekSisruteDiagnosa);

        btnRujukanMasukSisrute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/iconfinder_analysis_60159.png"))); // NOI18N
        btnRujukanMasukSisrute.setText("Rujukan Masuk Sisrute");
        btnRujukanMasukSisrute.setIconTextGap(0);
        btnRujukanMasukSisrute.setName("btnRujukanMasukSisrute"); // NOI18N
        btnRujukanMasukSisrute.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRujukanMasukSisrute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRujukanMasukSisruteActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRujukanMasukSisrute);

        btnRujukanKeluarSisrute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485357758_Doctor.png"))); // NOI18N
        btnRujukanKeluarSisrute.setText("Rujukan Keluar Sisrute");
        btnRujukanKeluarSisrute.setIconTextGap(0);
        btnRujukanKeluarSisrute.setName("btnRujukanKeluarSisrute"); // NOI18N
        btnRujukanKeluarSisrute.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRujukanKeluarSisrute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRujukanKeluarSisruteActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRujukanKeluarSisrute);

        btnCekBPJSSKDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_document-preview_23216.png"))); // NOI18N
        btnCekBPJSSKDP.setText("Cek SKDP VClaim");
        btnCekBPJSSKDP.setIconTextGap(0);
        btnCekBPJSSKDP.setName("btnCekBPJSSKDP"); // NOI18N
        btnCekBPJSSKDP.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCekBPJSSKDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekBPJSSKDPActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCekBPJSSKDP);

        btnDataBatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360484263_barcode.png"))); // NOI18N
        btnDataBatch.setText("Data Batch");
        btnDataBatch.setIconTextGap(0);
        btnDataBatch.setName("btnDataBatch"); // NOI18N
        btnDataBatch.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDataBatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataBatchActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDataBatch);

        btnKunjunganLabRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnKunjunganLabRalan.setText("Kunjungan Lab Ralan");
        btnKunjunganLabRalan.setIconTextGap(0);
        btnKunjunganLabRalan.setName("btnKunjunganLabRalan"); // NOI18N
        btnKunjunganLabRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKunjunganLabRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKunjunganLabRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKunjunganLabRalan);

        btnKunjunganLabRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnKunjunganLabRanap.setText("Kunjungan Lab Ranap");
        btnKunjunganLabRanap.setIconTextGap(0);
        btnKunjunganLabRanap.setName("btnKunjunganLabRanap"); // NOI18N
        btnKunjunganLabRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKunjunganLabRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKunjunganLabRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKunjunganLabRanap);

        btnKunjunganRadRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnKunjunganRadRalan.setText("Kunjungan Radiologi Ralan");
        btnKunjunganRadRalan.setIconTextGap(0);
        btnKunjunganRadRalan.setName("btnKunjunganRadRalan"); // NOI18N
        btnKunjunganRadRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKunjunganRadRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKunjunganRadRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKunjunganRadRalan);

        btnKunjunganRadRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnKunjunganRadRanap.setText("Kunjungan Radiologi Ranap");
        btnKunjunganRadRanap.setIconTextGap(0);
        btnKunjunganRadRanap.setName("btnKunjunganRadRanap"); // NOI18N
        btnKunjunganRadRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKunjunganRadRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKunjunganRadRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKunjunganRadRanap);

        btnPembayaranAkunBayar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1404046811_money.png"))); // NOI18N
        btnPembayaranAkunBayar3.setText("Pembayaran Per Akun Bayar 3");
        btnPembayaranAkunBayar3.setIconTextGap(0);
        btnPembayaranAkunBayar3.setName("btnPembayaranAkunBayar3"); // NOI18N
        btnPembayaranAkunBayar3.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPembayaranAkunBayar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPembayaranAkunBayar3ActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPembayaranAkunBayar3);

        btnPasswordAsuransi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481002113_guard.png"))); // NOI18N
        btnPasswordAsuransi.setText("Password Asuransi");
        btnPasswordAsuransi.setIconTextGap(0);
        btnPasswordAsuransi.setName("btnPasswordAsuransi"); // NOI18N
        btnPasswordAsuransi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPasswordAsuransi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasswordAsuransiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPasswordAsuransi);

        btnDataSITT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnDataSITT.setText("Data TB");
        btnDataSITT.setIconTextGap(0);
        btnDataSITT.setName("btnDataSITT"); // NOI18N
        btnDataSITT.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDataSITT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataSITTActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDataSITT);

        btnSiranapKetersediaanKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485357524_Company.png"))); // NOI18N
        btnSiranapKetersediaanKamar.setText("Ketersediaan Kamar SIRANAP");
        btnSiranapKetersediaanKamar.setIconTextGap(0);
        btnSiranapKetersediaanKamar.setName("btnSiranapKetersediaanKamar"); // NOI18N
        btnSiranapKetersediaanKamar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSiranapKetersediaanKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiranapKetersediaanKamarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSiranapKetersediaanKamar);

        btnKadaluarsaBatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/iconfinder_diagram-11_35581.png"))); // NOI18N
        btnKadaluarsaBatch.setText("Kadaluarsa Batch");
        btnKadaluarsaBatch.setIconTextGap(0);
        btnKadaluarsaBatch.setName("btnKadaluarsaBatch"); // NOI18N
        btnKadaluarsaBatch.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKadaluarsaBatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKadaluarsaBatchActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKadaluarsaBatch);

        btnSisaStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/iconfinder_icon-62-document-table_314903.png"))); // NOI18N
        btnSisaStok.setText("Sisa Stok");
        btnSisaStok.setIconTextGap(0);
        btnSisaStok.setName("btnSisaStok"); // NOI18N
        btnSisaStok.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSisaStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSisaStokActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSisaStok);

        btnObatPerResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/iconfinder_eccomerce_-_receipt_3440909.png"))); // NOI18N
        btnObatPerResep.setText("Obat Per Resep");
        btnObatPerResep.setIconTextGap(0);
        btnObatPerResep.setName("btnObatPerResep"); // NOI18N
        btnObatPerResep.setPreferredSize(new java.awt.Dimension(200, 90));
        btnObatPerResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObatPerResepActionPerformed(evt);
            }
        });
        Panelmenu.add(btnObatPerResep);

        btnPemakaianAirPDAM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/iconfinder_Energy_Energy_Oil_Water_Drop_Fuel_3911250.png"))); // NOI18N
        btnPemakaianAirPDAM.setText("Pemakaian Air PDAM");
        btnPemakaianAirPDAM.setIconTextGap(0);
        btnPemakaianAirPDAM.setName("btnPemakaianAirPDAM"); // NOI18N
        btnPemakaianAirPDAM.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPemakaianAirPDAM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPemakaianAirPDAMActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPemakaianAirPDAM);

        btnLimbahB3Medis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/iconfinder_trash red_10554.png"))); // NOI18N
        btnLimbahB3Medis.setText("Limbah Padat B3 Medis");
        btnLimbahB3Medis.setIconTextGap(0);
        btnLimbahB3Medis.setName("btnLimbahB3Medis"); // NOI18N
        btnLimbahB3Medis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLimbahB3Medis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimbahB3MedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnLimbahB3Medis);

        btn10Obat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/iconfinder_eccomerce_-_receipt_3440909.png"))); // NOI18N
        btn10Obat.setText("10 Obat Terbanyak");
        btn10Obat.setIconTextGap(0);
        btn10Obat.setName("btn10Obat"); // NOI18N
        btn10Obat.setPreferredSize(new java.awt.Dimension(200, 90));
        btn10Obat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn10ObatActionPerformed(evt);
            }
        });
        Panelmenu.add(btn10Obat);

        btnMapingDokterDPJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_Pink_Time_Machine_B_66659.png"))); // NOI18N
        btnMapingDokterDPJP.setText("Mapping DPJP VClaim");
        btnMapingDokterDPJP.setIconTextGap(0);
        btnMapingDokterDPJP.setName("btnMapingDokterDPJP"); // NOI18N
        btnMapingDokterDPJP.setPreferredSize(new java.awt.Dimension(200, 90));
        btnMapingDokterDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapingDokterDPJPActionPerformed(evt);
            }
        });
        Panelmenu.add(btnMapingDokterDPJP);

        scrollPane2.setViewportView(Panelmenu);

        panelMenu.add(scrollPane2, java.awt.BorderLayout.CENTER);

        DlgHome.getContentPane().add(panelMenu, java.awt.BorderLayout.CENTER);

        tanggal.setEditable(false);
        tanggal.setForeground(new java.awt.Color(50, 70, 50));
        tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04/05/2024" }));
        tanggal.setDisplayFormat("dd/MM/yyyy");
        tanggal.setName("tanggal"); // NOI18N
        tanggal.setOpaque(false);

        btnDataPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485357971_desktop_computer.png"))); // NOI18N
        btnDataPenjualan.setText("Data Penjualan Obat & BHP");
        btnDataPenjualan.setIconTextGap(0);
        btnDataPenjualan.setName("btnDataPenjualan"); // NOI18N
        btnDataPenjualan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDataPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataPenjualanActionPerformed(evt);
            }
        });

        btnInputPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/cashbox.png"))); // NOI18N
        btnInputPenjualan.setText("Input Penjualan Obat & BHP");
        btnInputPenjualan.setIconTextGap(0);
        btnInputPenjualan.setName("btnInputPenjualan"); // NOI18N
        btnInputPenjualan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnInputPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInputPenjualanActionPerformed(evt);
            }
        });

        btnDataPenyerahanDarah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/kwrite.png"))); // NOI18N
        btnDataPenyerahanDarah.setText("Data Penyerahan Darah");
        btnDataPenyerahanDarah.setIconTextGap(0);
        btnDataPenyerahanDarah.setName("btnDataPenyerahanDarah"); // NOI18N
        btnDataPenyerahanDarah.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDataPenyerahanDarah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataPenyerahanDarahActionPerformed(evt);
            }
        });

        btnResepObatDepan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/stock_task.png"))); // NOI18N
        btnResepObatDepan.setText("No. Resep");
        btnResepObatDepan.setIconTextGap(0);
        btnResepObatDepan.setName("btnResepObatDepan"); // NOI18N
        btnResepObatDepan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnResepObatDepan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResepObatDepanActionPerformed(evt);
            }
        });

        btnHibahObatBHP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1485357971_desktop_computer.png"))); // NOI18N
        btnHibahObatBHP.setText("Hibah Obat & BHP");
        btnHibahObatBHP.setIconTextGap(0);
        btnHibahObatBHP.setName("btnHibahObatBHP"); // NOI18N
        btnHibahObatBHP.setPreferredSize(new java.awt.Dimension(200, 90));
        btnHibahObatBHP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHibahObatBHPActionPerformed(evt);
            }
        });

        btnRl35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnRl35.setText("RL 3.5 Obgyn");
        btnRl35.setIconTextGap(0);
        btnRl35.setName("btnRl35"); // NOI18N
        btnRl35.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRl35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRl35ActionPerformed(evt);
            }
        });

        btnLapStokOp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnLapStokOp.setText("Laporan Stok Opname");
        btnLapStokOp.setIconTextGap(0);
        btnLapStokOp.setName("btnLapStokOp"); // NOI18N
        btnLapStokOp.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLapStokOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLapStokOpActionPerformed(evt);
            }
        });

        btnLapPelFar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnLapPelFar.setText("Laporan Pelayanan Kefarmasian");
        btnLapPelFar.setIconTextGap(0);
        btnLapPelFar.setName("btnLapPelFar"); // NOI18N
        btnLapPelFar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLapPelFar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLapPelFarActionPerformed(evt);
            }
        });

        btnPelayananPenunjang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnPelayananPenunjang.setText("Pelayanan Penunjang");
        btnPelayananPenunjang.setIconTextGap(0);
        btnPelayananPenunjang.setName("btnPelayananPenunjang"); // NOI18N
        btnPelayananPenunjang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPelayananPenunjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPelayananPenunjangActionPerformed(evt);
            }
        });

        btnFrekuensiRanapPerDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnFrekuensiRanapPerDokter.setText("10 Besar Penyakit Ranap Per Dokter");
        btnFrekuensiRanapPerDokter.setIconTextGap(0);
        btnFrekuensiRanapPerDokter.setName("btnFrekuensiRanapPerDokter"); // NOI18N
        btnFrekuensiRanapPerDokter.setPreferredSize(new java.awt.Dimension(200, 90));
        btnFrekuensiRanapPerDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFrekuensiRanapPerDokterActionPerformed(evt);
            }
        });

        btnMonitoringKunjungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486845_23.png"))); // NOI18N
        btnMonitoringKunjungan.setText("Monitoring Kunjungan BPJS");
        btnMonitoringKunjungan.setIconTextGap(0);
        btnMonitoringKunjungan.setName("btnMonitoringKunjungan"); // NOI18N
        btnMonitoringKunjungan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnMonitoringKunjungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonitoringKunjunganActionPerformed(evt);
            }
        });

        btnFrekuensiRalanDanRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnFrekuensiRalanDanRanap.setText("10 Besar Penyakit Ralan Dan Ranap");
        btnFrekuensiRalanDanRanap.setIconTextGap(0);
        btnFrekuensiRalanDanRanap.setName("btnFrekuensiRalanDanRanap"); // NOI18N
        btnFrekuensiRalanDanRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnFrekuensiRalanDanRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFrekuensiRalanDanRanapActionPerformed(evt);
            }
        });

        btnPelFar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnPelFar.setText("Pelayanan Farmasi");
        btnPelFar.setIconTextGap(0);
        btnPelFar.setName("btnPelFar"); // NOI18N
        btnPelFar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPelFar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPelFarActionPerformed(evt);
            }
        });

        btnKerohanian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnKerohanian.setText("Kerohanian");
        btnKerohanian.setIconTextGap(0);
        btnKerohanian.setName("btnKerohanian"); // NOI18N
        btnKerohanian.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKerohanian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKerohanianActionPerformed(evt);
            }
        });

        btnMyLimsMapping.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnMyLimsMapping.setText("My Lims Mapping");
        btnMyLimsMapping.setIconTextGap(0);
        btnMyLimsMapping.setName("btnMyLimsMapping"); // NOI18N
        btnMyLimsMapping.setPreferredSize(new java.awt.Dimension(200, 90));
        btnMyLimsMapping.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMyLimsMappingActionPerformed(evt);
            }
        });

        btnSetPenjabBNM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/if_package_utilities_3557.png"))); // NOI18N
        btnSetPenjabBNM.setText("Set Penjab Barang Non Medis");
        btnSetPenjabBNM.setIconTextGap(0);
        btnSetPenjabBNM.setName("btnSetPenjabBNM"); // NOI18N
        btnSetPenjabBNM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetPenjabBNMActionPerformed1(evt);
            }
        });

        btnRekapDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnRekapDiet.setText("Rekap Diet");
        btnRekapDiet.setIconTextGap(0);
        btnRekapDiet.setName("btnRekapDiet"); // NOI18N
        btnRekapDiet.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapDietActionPerformed(evt);
            }
        });

        btnUTDPermintaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnUTDPermintaan.setText("Permintaan UTD\n");
        btnUTDPermintaan.setIconTextGap(0);
        btnUTDPermintaan.setName("btnUTDPermintaan"); // NOI18N
        btnUTDPermintaan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnUTDPermintaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUTDPermintaanActionPerformed(evt);
            }
        });

        btnSetSubAkun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnSetSubAkun.setText("Pengaturan Sub Akun Rekening");
        btnSetSubAkun.setIconTextGap(0);
        btnSetSubAkun.setName("btnSetSubAkun"); // NOI18N
        btnSetSubAkun.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetSubAkun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetSubAkunActionPerformed(evt);
            }
        });

        btnFrekuensiPenyakitRanapBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnFrekuensiPenyakitRanapBaru.setText("10 Besar Penyakit Ranap (Dx Masuk)");
        btnFrekuensiPenyakitRanapBaru.setIconTextGap(0);
        btnFrekuensiPenyakitRanapBaru.setName("btnFrekuensiPenyakitRanapBaru"); // NOI18N
        btnFrekuensiPenyakitRanapBaru.setPreferredSize(new java.awt.Dimension(200, 90));
        btnFrekuensiPenyakitRanapBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFrekuensiPenyakitRanapBaruActionPerformed(evt);
            }
        });

        btnFrekuensiPerPerujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnFrekuensiPerPerujuk.setText("10 Besar Penyakit Per Perujuk");
        btnFrekuensiPerPerujuk.setIconTextGap(0);
        btnFrekuensiPerPerujuk.setName("btnFrekuensiPerPerujuk"); // NOI18N
        btnFrekuensiPerPerujuk.setPreferredSize(new java.awt.Dimension(200, 90));
        btnFrekuensiPerPerujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFrekuensiPerPerujukActionPerformed(evt);
            }
        });

        btnPaketObatOp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnPaketObatOp.setText("Master Paket Operasi");
        btnPaketObatOp.setIconTextGap(0);
        btnPaketObatOp.setName("btnPaketObatOp"); // NOI18N
        btnPaketObatOp.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPaketObatOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaketObatOpActionPerformed(evt);
            }
        });

        btnPermintaanMPP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnPermintaanMPP.setText("MPP");
        btnPermintaanMPP.setIconTextGap(0);
        btnPermintaanMPP.setName("btnPermintaanMPP"); // NOI18N
        btnPermintaanMPP.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPermintaanMPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPermintaanMPPActionPerformed(evt);
            }
        });

        btnSirkulasiObat4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487125_system-restart-panel.png"))); // NOI18N
        btnSirkulasiObat4.setText("Sirkulasi Obat, Alkes & BHP 4");
        btnSirkulasiObat4.setIconTextGap(0);
        btnSirkulasiObat4.setName("btnSirkulasiObat4"); // NOI18N
        btnSirkulasiObat4.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSirkulasiObat4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSirkulasiObat4ActionPerformed(evt);
            }
        });

        btnLapRBA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnLapRBA.setText("RBA");
        btnLapRBA.setIconTextGap(0);
        btnLapRBA.setName("btnLapRBA"); // NOI18N
        btnLapRBA.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLapRBA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLapRBAActionPerformed(evt);
            }
        });

        btnBookingMJKN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnBookingMJKN.setText("Booking MJKN");
        btnBookingMJKN.setIconTextGap(0);
        btnBookingMJKN.setName("btnBookingMJKN"); // NOI18N
        btnBookingMJKN.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBookingMJKN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookingMJKNActionPerformed(evt);
            }
        });

        btnSetKompUtd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist.png"))); // NOI18N
        btnSetKompUtd.setText("Set Komponen Darah");
        btnSetKompUtd.setIconTextGap(0);
        btnSetKompUtd.setName("btnSetKompUtd"); // NOI18N
        btnSetKompUtd.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetKompUtd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetKompUtdActionPerformed(evt);
            }
        });

        btnRekapKegiatanRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnRekapKegiatanRad.setText("Rekap Kegiatan Radiologi");
        btnRekapKegiatanRad.setIconTextGap(0);
        btnRekapKegiatanRad.setName("btnRekapKegiatanRad"); // NOI18N
        btnRekapKegiatanRad.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekapKegiatanRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekapKegiatanRadActionPerformed(evt);
            }
        });

        btnPermintaanRBA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnPermintaanRBA.setText("Permintaan RBA");
        btnPermintaanRBA.setIconTextGap(0);
        btnPermintaanRBA.setName("btnPermintaanRBA"); // NOI18N
        btnPermintaanRBA.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPermintaanRBA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPermintaanRBAActionPerformed(evt);
            }
        });

        btnSetHariLibur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnSetHariLibur.setText("Set Hari Libur");
        btnSetHariLibur.setToolTipText("");
        btnSetHariLibur.setIconTextGap(0);
        btnSetHariLibur.setName("btnSetHariLibur"); // NOI18N
        btnSetHariLibur.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetHariLibur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetHariLiburActionPerformed(evt);
            }
        });

        btnTrialSendWA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/whatsapp.png"))); // NOI18N
        btnTrialSendWA.setText("Send Whatsapp");
        btnTrialSendWA.setToolTipText("");
        btnTrialSendWA.setIconTextGap(0);
        btnTrialSendWA.setName("btnTrialSendWA"); // NOI18N
        btnTrialSendWA.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTrialSendWA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrialSendWAActionPerformed(evt);
            }
        });

        btnSetPaketMCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist.png"))); // NOI18N
        btnSetPaketMCU.setText("Set Paket MCU");
        btnSetPaketMCU.setToolTipText("");
        btnSetPaketMCU.setIconTextGap(0);
        btnSetPaketMCU.setName("btnSetPaketMCU"); // NOI18N
        btnSetPaketMCU.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetPaketMCU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetPaketMCUActionPerformed(evt);
            }
        });

        btnLapKunjunganPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnLapKunjunganPasien.setText("Kunjungan Pasien");
        btnLapKunjunganPasien.setToolTipText("");
        btnLapKunjunganPasien.setIconTextGap(0);
        btnLapKunjunganPasien.setName("btnLapKunjunganPasien"); // NOI18N
        btnLapKunjunganPasien.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLapKunjunganPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLapKunjunganPasienActionPerformed(evt);
            }
        });

        btnPindahRiw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist.png"))); // NOI18N
        btnPindahRiw.setText("Pindah Pasien");
        btnPindahRiw.setToolTipText("");
        btnPindahRiw.setIconTextGap(0);
        btnPindahRiw.setName("btnPindahRiw"); // NOI18N
        btnPindahRiw.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPindahRiw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPindahRiwActionPerformed(evt);
            }
        });

        btnSurkonBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1481038237_Adobe_Bridge_CS.png"))); // NOI18N
        btnSurkonBPJS.setText("Surat Kontrol BPJS");
        btnSurkonBPJS.setToolTipText("");
        btnSurkonBPJS.setIconTextGap(0);
        btnSurkonBPJS.setName("btnSurkonBPJS"); // NOI18N
        btnSurkonBPJS.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSurkonBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSurkonBPJSActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("::[ Khanza SIMKES 2019 ]::");
        setBackground(new java.awt.Color(153, 255, 153));
        setIconImages(null);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame1.setBorder(null);
        internalFrame1.setForeground(new java.awt.Color(255, 255, 255));
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(40, 44));
        internalFrame1.setVerifyInputWhenFocusTarget(false);
        internalFrame1.setWarnaAtas(new java.awt.Color(204, 255, 102));
        internalFrame1.setWarnaBawah(new java.awt.Color(226, 226, 242));
        internalFrame1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 2));

        BtnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Home_copy.png"))); // NOI18N
        BtnMenu.setMnemonic('M');
        BtnMenu.setText("Menu");
        BtnMenu.setToolTipText("Alt+M");
        BtnMenu.setEnabled(false);
        BtnMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMenu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnMenu.setIconTextGap(2);
        BtnMenu.setName("BtnMenu"); // NOI18N
        BtnMenu.setPreferredSize(new java.awt.Dimension(90, 40));
        BtnMenu.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMenuActionPerformed(evt);
            }
        });
        internalFrame1.add(BtnMenu);

        jSeparator4.setBackground(new java.awt.Color(90, 130, 80));
        jSeparator4.setForeground(new java.awt.Color(90, 130, 80));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 130, 80)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        jSeparator4.setOpaque(true);
        jSeparator4.setPreferredSize(new java.awt.Dimension(1, 37));
        internalFrame1.add(jSeparator4);

        BtnToolReg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Registrasi.png"))); // NOI18N
        BtnToolReg.setMnemonic('R');
        BtnToolReg.setText("Registrasi");
        BtnToolReg.setToolTipText("Alt+R");
        BtnToolReg.setEnabled(false);
        BtnToolReg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnToolReg.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnToolReg.setIconTextGap(2);
        BtnToolReg.setName("BtnToolReg"); // NOI18N
        BtnToolReg.setPreferredSize(new java.awt.Dimension(110, 40));
        BtnToolReg.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnToolReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnToolRegActionPerformed(evt);
            }
        });
        internalFrame1.add(BtnToolReg);

        btnToolIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/BandAid.png"))); // NOI18N
        btnToolIGD.setMnemonic('D');
        btnToolIGD.setText("IGD/UGD");
        btnToolIGD.setToolTipText("Alt+D");
        btnToolIGD.setEnabled(false);
        btnToolIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnToolIGD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnToolIGD.setIconTextGap(2);
        btnToolIGD.setName("btnToolIGD"); // NOI18N
        btnToolIGD.setPreferredSize(new java.awt.Dimension(110, 40));
        btnToolIGD.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        btnToolIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToolIGDActionPerformed(evt);
            }
        });
        internalFrame1.add(btnToolIGD);

        jSeparator5.setBackground(new java.awt.Color(90, 130, 80));
        jSeparator5.setForeground(new java.awt.Color(90, 130, 80));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 130, 80)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 37));
        internalFrame1.add(jSeparator5);

        btnToolLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/TestTubes.png"))); // NOI18N
        btnToolLab.setMnemonic('O');
        btnToolLab.setText("Laboratorium");
        btnToolLab.setToolTipText("Alt+O");
        btnToolLab.setEnabled(false);
        btnToolLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnToolLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnToolLab.setIconTextGap(2);
        btnToolLab.setName("btnToolLab"); // NOI18N
        btnToolLab.setPreferredSize(new java.awt.Dimension(130, 40));
        btnToolLab.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        btnToolLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToolLabActionPerformed(evt);
            }
        });
        internalFrame1.add(btnToolLab);

        btnToolRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Radioactive.png"))); // NOI18N
        btnToolRad.setMnemonic('A');
        btnToolRad.setText("Radiologi");
        btnToolRad.setToolTipText("Alt+A");
        btnToolRad.setEnabled(false);
        btnToolRad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnToolRad.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnToolRad.setIconTextGap(2);
        btnToolRad.setName("btnToolRad"); // NOI18N
        btnToolRad.setPreferredSize(new java.awt.Dimension(120, 40));
        btnToolRad.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        btnToolRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToolRadActionPerformed(evt);
            }
        });
        internalFrame1.add(btnToolRad);

        BtnToolJualObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Farmasi.png"))); // NOI18N
        BtnToolJualObat.setMnemonic('j');
        BtnToolJualObat.setText("Farmasi");
        BtnToolJualObat.setToolTipText("Alt+J");
        BtnToolJualObat.setEnabled(false);
        BtnToolJualObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnToolJualObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnToolJualObat.setIconTextGap(2);
        BtnToolJualObat.setName("BtnToolJualObat"); // NOI18N
        BtnToolJualObat.setPreferredSize(new java.awt.Dimension(110, 40));
        BtnToolJualObat.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnToolJualObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnToolJualObatActionPerformed(evt);
            }
        });
        internalFrame1.add(BtnToolJualObat);

        jSeparator9.setBackground(new java.awt.Color(90, 130, 80));
        jSeparator9.setForeground(new java.awt.Color(90, 130, 80));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 130, 80)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        jSeparator9.setOpaque(true);
        jSeparator9.setPreferredSize(new java.awt.Dimension(1, 37));
        internalFrame1.add(jSeparator9);

        BtnToolKamnap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/hospital-bed.png"))); // NOI18N
        BtnToolKamnap.setMnemonic('K');
        BtnToolKamnap.setText("Kamar Inap");
        BtnToolKamnap.setToolTipText("Alt+K");
        BtnToolKamnap.setEnabled(false);
        BtnToolKamnap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnToolKamnap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnToolKamnap.setIconTextGap(2);
        BtnToolKamnap.setName("BtnToolKamnap"); // NOI18N
        BtnToolKamnap.setPreferredSize(new java.awt.Dimension(120, 40));
        BtnToolKamnap.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnToolKamnap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnToolKamnapActionPerformed(evt);
            }
        });
        internalFrame1.add(BtnToolKamnap);

        BtnToolKasir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kasir.png"))); // NOI18N
        BtnToolKasir.setMnemonic('S');
        BtnToolKasir.setText("Kasir Ralan");
        BtnToolKasir.setToolTipText("Alt+S");
        BtnToolKasir.setEnabled(false);
        BtnToolKasir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnToolKasir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnToolKasir.setIconTextGap(2);
        BtnToolKasir.setName("BtnToolKasir"); // NOI18N
        BtnToolKasir.setPreferredSize(new java.awt.Dimension(120, 40));
        BtnToolKasir.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnToolKasir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnToolKasirActionPerformed(evt);
            }
        });
        internalFrame1.add(BtnToolKasir);

        jSeparator7.setBackground(new java.awt.Color(90, 130, 80));
        jSeparator7.setForeground(new java.awt.Color(90, 130, 80));
        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 130, 80)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        jSeparator7.setOpaque(true);
        jSeparator7.setPreferredSize(new java.awt.Dimension(1, 37));
        internalFrame1.add(jSeparator7);

        BtnLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/loginorg.png"))); // NOI18N
        BtnLog.setMnemonic('L');
        BtnLog.setText("Log In");
        BtnLog.setToolTipText("Alt+L");
        BtnLog.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnLog.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnLog.setIconTextGap(2);
        BtnLog.setName("BtnLog"); // NOI18N
        BtnLog.setPreferredSize(new java.awt.Dimension(110, 40));
        BtnLog.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLogActionPerformed(evt);
            }
        });
        internalFrame1.add(BtnLog);

        BtnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Keluar.png"))); // NOI18N
        BtnClose.setMnemonic('U');
        BtnClose.setText("Keluar");
        BtnClose.setToolTipText("Alt+U");
        BtnClose.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnClose.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnClose.setIconTextGap(2);
        BtnClose.setName("BtnClose"); // NOI18N
        BtnClose.setPreferredSize(new java.awt.Dimension(110, 40));
        BtnClose.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseActionPerformed(evt);
            }
        });
        internalFrame1.add(BtnClose);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.PAGE_START);

        internalFrame4.setBackground(new java.awt.Color(102, 255, 102));
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setPreferredSize(new java.awt.Dimension(330, 25));
        internalFrame4.setWarnaAtas(new java.awt.Color(204, 255, 102));
        internalFrame4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 1));

        lblStts.setForeground(new java.awt.Color(70, 70, 70));
        lblStts.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStts.setText("Status Admin :");
        lblStts.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblStts.setName("lblStts"); // NOI18N
        lblStts.setPreferredSize(new java.awt.Dimension(100, 23));
        internalFrame4.add(lblStts);

        jSeparator1.setBackground(new java.awt.Color(90, 130, 80));
        jSeparator1.setForeground(new java.awt.Color(90, 130, 80));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 130, 80)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        jSeparator1.setOpaque(true);
        jSeparator1.setPreferredSize(new java.awt.Dimension(1, 21));
        internalFrame4.add(jSeparator1);

        lblUser.setForeground(new java.awt.Color(70, 70, 70));
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setText("Log Out");
        lblUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUser.setName("lblUser"); // NOI18N
        lblUser.setPreferredSize(new java.awt.Dimension(170, 23));
        internalFrame4.add(lblUser);

        jSeparator2.setBackground(new java.awt.Color(90, 130, 80));
        jSeparator2.setForeground(new java.awt.Color(90, 130, 80));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 130, 80)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        jSeparator2.setOpaque(true);
        jSeparator2.setPreferredSize(new java.awt.Dimension(1, 21));
        internalFrame4.add(jSeparator2);

        lblTgl.setForeground(new java.awt.Color(70, 70, 70));
        lblTgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTgl.setText("Tanggal");
        lblTgl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblTgl.setName("lblTgl"); // NOI18N
        lblTgl.setPreferredSize(new java.awt.Dimension(150, 23));
        internalFrame4.add(lblTgl);

        jSeparator3.setBackground(new java.awt.Color(90, 130, 80));
        jSeparator3.setForeground(new java.awt.Color(90, 130, 80));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 130, 80)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        jSeparator3.setOpaque(true);
        jSeparator3.setPreferredSize(new java.awt.Dimension(1, 21));
        internalFrame4.add(jSeparator3);

        jLabel7.setForeground(new java.awt.Color(70, 70, 70));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/file-edit-16x16.png"))); // NOI18N
        jLabel7.setText(" Didesain & dibuat oleh Khanza.Soft Media. (Opt by ICT RSHD Barabai Build.V.20240407)");
        jLabel7.setToolTipText("");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel7.setIconTextGap(3);
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(600, 23));
        internalFrame4.add(jLabel7);

        getContentPane().add(internalFrame4, java.awt.BorderLayout.PAGE_END);

        PanelUtama.setName("PanelUtama"); // NOI18N
        PanelUtama.setOpaque(false);
        PanelUtama.setLayout(new java.awt.BorderLayout());

        scrollPane1.setBorder(null);
        scrollPane1.setName("scrollPane1"); // NOI18N

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/wallpaper.jpg"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 0, 0, 0));
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                PanelWallMouseMoved(evt);
            }
        });
        PanelWall.setLayout(new java.awt.BorderLayout());

        panelJudul.setBackground(new java.awt.Color(255, 255, 255));
        panelJudul.setOpaqueImage(false);
        panelJudul.setPreferredSize(new java.awt.Dimension(200, 170));
        panelJudul.setRound(false);
        panelJudul.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(60, 60, 60));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Your Businis Solution");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(430, 30));
        panelJudul.add(jLabel11);
        jLabel11.setBounds(110, 90, 680, 30);

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(60, 60, 60));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/hst 64px.png"))); // NOI18N
        jLabel8.setText("Khanza HMS+, Hospital Management System");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel8.setName("jLabel8"); // NOI18N
        panelJudul.add(jLabel8);
        jLabel8.setBounds(40, 0, 820, 150);

        PanelWall.add(panelJudul, java.awt.BorderLayout.PAGE_END);

        FlayMenu.setBackground(new java.awt.Color(255, 255, 255));
        FlayMenu.setOpaqueImage(false);
        FlayMenu.setPreferredSize(new java.awt.Dimension(200, 110));
        FlayMenu.setRound(false);
        FlayMenu.setLayout(new java.awt.GridLayout(1, 0, 4, 5));
        PanelWall.add(FlayMenu, java.awt.BorderLayout.PAGE_START);

        scrollPane1.setViewportView(PanelWall);

        PanelUtama.add(scrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(PanelUtama, java.awt.BorderLayout.CENTER);

        MenuBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        MenuBar.setName("MenuBar"); // NOI18N
        MenuBar.setPreferredSize(new java.awt.Dimension(227, 28));

        jMenu1.setBackground(new java.awt.Color(20, 0, 20));
        jMenu1.setForeground(new java.awt.Color(255, 255, 255));
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/85.png"))); // NOI18N
        jMenu1.setMnemonic('A');
        jMenu1.setText("Program");
        jMenu1.setToolTipText("Alt+A");
        jMenu1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu1.setIconTextGap(3);
        jMenu1.setName("jMenu1"); // NOI18N
        jMenu1.setOpaque(false);
        jMenu1.setPreferredSize(new java.awt.Dimension(80, 28));

        MnLogin.setBackground(new java.awt.Color(255, 255, 254));
        MnLogin.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLogin.setForeground(new java.awt.Color(90, 120, 80));
        MnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/login2.png"))); // NOI18N
        MnLogin.setText("Log In");
        MnLogin.setIconTextGap(6);
        MnLogin.setName("MnLogin"); // NOI18N
        MnLogin.setPreferredSize(new java.awt.Dimension(170, 30));
        MnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLoginBtnLogActionPerformed(evt);
            }
        });
        jMenu1.add(MnLogin);

        MnGantiPassword.setBackground(new java.awt.Color(255, 255, 254));
        MnGantiPassword.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGantiPassword.setForeground(new java.awt.Color(90, 120, 80));
        MnGantiPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/unlock.png"))); // NOI18N
        MnGantiPassword.setText("Ubah Password");
        MnGantiPassword.setEnabled(false);
        MnGantiPassword.setIconTextGap(6);
        MnGantiPassword.setName("MnGantiPassword"); // NOI18N
        MnGantiPassword.setPreferredSize(new java.awt.Dimension(170, 30));
        MnGantiPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGantiPasswordBtnLogActionPerformed(evt);
            }
        });
        jMenu1.add(MnGantiPassword);

        jSeparator14.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator14.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator14.setName("jSeparator14"); // NOI18N
        jSeparator14.setPreferredSize(new java.awt.Dimension(0, 1));
        jMenu1.add(jSeparator14);

        MenuKeluar.setBackground(new java.awt.Color(255, 255, 254));
        MenuKeluar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuKeluar.setForeground(new java.awt.Color(90, 120, 80));
        MenuKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Delete.png"))); // NOI18N
        MenuKeluar.setText("Keluar");
        MenuKeluar.setIconTextGap(6);
        MenuKeluar.setName("MenuKeluar"); // NOI18N
        MenuKeluar.setPreferredSize(new java.awt.Dimension(170, 30));
        MenuKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuKeluarBtnCloseActionPerformed(evt);
            }
        });
        jMenu1.add(MenuKeluar);

        MenuBar.add(jMenu1);

        jMenu5.setBackground(new java.awt.Color(20, 0, 20));
        jMenu5.setForeground(new java.awt.Color(255, 255, 255));
        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientMale.png"))); // NOI18N
        jMenu5.setMnemonic('P');
        jMenu5.setText("Presensi Pegawai");
        jMenu5.setToolTipText("Alt+P");
        jMenu5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu5.setIconTextGap(3);
        jMenu5.setName("jMenu5"); // NOI18N
        jMenu5.setOpaque(false);
        jMenu5.setPreferredSize(new java.awt.Dimension(127, 28));

        MnBelumDatang1.setBackground(new java.awt.Color(255, 255, 254));
        MnBelumDatang1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBelumDatang1.setForeground(new java.awt.Color(90, 120, 80));
        MnBelumDatang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/female-user-search24.png"))); // NOI18N
        MnBelumDatang1.setText("Presensi Pegawai");
        MnBelumDatang1.setIconTextGap(6);
        MnBelumDatang1.setName("MnBelumDatang1"); // NOI18N
        MnBelumDatang1.setPreferredSize(new java.awt.Dimension(170, 30));
        MnBelumDatang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBelumDatang1ActionPerformed(evt);
            }
        });
        jMenu5.add(MnBelumDatang1);

        MnBelumDatang.setBackground(new java.awt.Color(255, 255, 254));
        MnBelumDatang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBelumDatang.setForeground(new java.awt.Color(90, 120, 80));
        MnBelumDatang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/couple24.png"))); // NOI18N
        MnBelumDatang.setText("Belum Datang");
        MnBelumDatang.setIconTextGap(6);
        MnBelumDatang.setName("MnBelumDatang"); // NOI18N
        MnBelumDatang.setPreferredSize(new java.awt.Dimension(170, 30));
        MnBelumDatang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBelumDatangActionPerformed(evt);
            }
        });
        jMenu5.add(MnBelumDatang);

        MnSudahPulang.setBackground(new java.awt.Color(255, 255, 254));
        MnSudahPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSudahPulang.setForeground(new java.awt.Color(90, 120, 80));
        MnSudahPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/addressbook-remove24.png"))); // NOI18N
        MnSudahPulang.setText("Sudah Pulang");
        MnSudahPulang.setIconTextGap(6);
        MnSudahPulang.setName("MnSudahPulang"); // NOI18N
        MnSudahPulang.setPreferredSize(new java.awt.Dimension(170, 30));
        MnSudahPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSudahPulangActionPerformed(evt);
            }
        });
        jMenu5.add(MnSudahPulang);

        MnRekapBulanan.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapBulanan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapBulanan.setForeground(new java.awt.Color(90, 120, 80));
        MnRekapBulanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Doctor.png"))); // NOI18N
        MnRekapBulanan.setText("Rekap Bulanan");
        MnRekapBulanan.setIconTextGap(6);
        MnRekapBulanan.setName("MnRekapBulanan"); // NOI18N
        MnRekapBulanan.setPreferredSize(new java.awt.Dimension(170, 30));
        MnRekapBulanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapBulananActionPerformed(evt);
            }
        });
        jMenu5.add(MnRekapBulanan);

        MnRekapHadir.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapHadir.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHadir.setForeground(new java.awt.Color(90, 120, 80));
        MnRekapHadir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/addressbook-search24.png"))); // NOI18N
        MnRekapHadir.setText("Rekap Kehadiran");
        MnRekapHadir.setIconTextGap(6);
        MnRekapHadir.setName("MnRekapHadir"); // NOI18N
        MnRekapHadir.setPreferredSize(new java.awt.Dimension(170, 30));
        MnRekapHadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHadirActionPerformed(evt);
            }
        });
        jMenu5.add(MnRekapHadir);

        MenuBar.add(jMenu5);

        jMenu6.setBackground(new java.awt.Color(20, 0, 20));
        jMenu6.setForeground(new java.awt.Color(255, 255, 255));
        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Bar Chart.png"))); // NOI18N
        jMenu6.setMnemonic('I');
        jMenu6.setText("Informasi");
        jMenu6.setToolTipText("Alt+I");
        jMenu6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu6.setIconTextGap(3);
        jMenu6.setName("jMenu6"); // NOI18N
        jMenu6.setOpaque(false);
        jMenu6.setPreferredSize(new java.awt.Dimension(89, 28));

        MnRekapBulanan1.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapBulanan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapBulanan1.setForeground(new java.awt.Color(90, 120, 80));
        MnRekapBulanan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnRekapBulanan1.setText("Pasien Kamar Inap");
        MnRekapBulanan1.setIconTextGap(6);
        MnRekapBulanan1.setName("MnRekapBulanan1"); // NOI18N
        MnRekapBulanan1.setPreferredSize(new java.awt.Dimension(250, 30));
        MnRekapBulanan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapBulanan1ActionPerformed(evt);
            }
        });
        jMenu6.add(MnRekapBulanan1);

        MnRekapBulanan3.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapBulanan3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapBulanan3.setForeground(new java.awt.Color(90, 120, 80));
        MnRekapBulanan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnRekapBulanan3.setText("Telusur Kunjungan Pasien");
        MnRekapBulanan3.setIconTextGap(6);
        MnRekapBulanan3.setName("MnRekapBulanan3"); // NOI18N
        MnRekapBulanan3.setPreferredSize(new java.awt.Dimension(250, 30));
        MnRekapBulanan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapBulanan3ActionPerformed(evt);
            }
        });
        jMenu6.add(MnRekapBulanan3);

        MnRekapBulanan2.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapBulanan2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapBulanan2.setForeground(new java.awt.Color(90, 120, 80));
        MnRekapBulanan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnRekapBulanan2.setText("Penggunaan Kamar Inap");
        MnRekapBulanan2.setIconTextGap(6);
        MnRekapBulanan2.setName("MnRekapBulanan2"); // NOI18N
        MnRekapBulanan2.setPreferredSize(new java.awt.Dimension(250, 30));
        MnRekapBulanan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapBulanan2ActionPerformed(evt);
            }
        });
        jMenu6.add(MnRekapBulanan2);

        jSeparator10.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator10.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator10.setName("jSeparator10"); // NOI18N
        jSeparator10.setPreferredSize(new java.awt.Dimension(0, 1));
        jMenu6.add(jSeparator10);

        MnSudahPulang1.setBackground(new java.awt.Color(255, 255, 254));
        MnSudahPulang1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSudahPulang1.setForeground(new java.awt.Color(90, 120, 80));
        MnSudahPulang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnSudahPulang1.setText("Tarif Kamar");
        MnSudahPulang1.setIconTextGap(6);
        MnSudahPulang1.setName("MnSudahPulang1"); // NOI18N
        MnSudahPulang1.setPreferredSize(new java.awt.Dimension(250, 30));
        MnSudahPulang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSudahPulang1ActionPerformed(evt);
            }
        });
        jMenu6.add(MnSudahPulang1);

        MnSudahPulang3.setBackground(new java.awt.Color(255, 255, 254));
        MnSudahPulang3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSudahPulang3.setForeground(new java.awt.Color(90, 120, 80));
        MnSudahPulang3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnSudahPulang3.setText("Tarif Laboratorium");
        MnSudahPulang3.setIconTextGap(6);
        MnSudahPulang3.setName("MnSudahPulang3"); // NOI18N
        MnSudahPulang3.setPreferredSize(new java.awt.Dimension(250, 30));
        MnSudahPulang3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSudahPulang3ActionPerformed(evt);
            }
        });
        jMenu6.add(MnSudahPulang3);

        MnSudahPulang6.setBackground(new java.awt.Color(255, 255, 254));
        MnSudahPulang6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSudahPulang6.setForeground(new java.awt.Color(90, 120, 80));
        MnSudahPulang6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnSudahPulang6.setText("Tarif Radiologi");
        MnSudahPulang6.setIconTextGap(6);
        MnSudahPulang6.setName("MnSudahPulang6"); // NOI18N
        MnSudahPulang6.setPreferredSize(new java.awt.Dimension(250, 30));
        MnSudahPulang6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSudahPulang6ActionPerformed(evt);
            }
        });
        jMenu6.add(MnSudahPulang6);

        MnSudahPulang4.setBackground(new java.awt.Color(255, 255, 254));
        MnSudahPulang4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSudahPulang4.setForeground(new java.awt.Color(90, 120, 80));
        MnSudahPulang4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnSudahPulang4.setText("Tarif Operasi & VK");
        MnSudahPulang4.setIconTextGap(6);
        MnSudahPulang4.setName("MnSudahPulang4"); // NOI18N
        MnSudahPulang4.setPreferredSize(new java.awt.Dimension(250, 30));
        MnSudahPulang4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSudahPulang4ActionPerformed(evt);
            }
        });
        jMenu6.add(MnSudahPulang4);

        MnSudahPulang2.setBackground(new java.awt.Color(255, 255, 254));
        MnSudahPulang2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSudahPulang2.setForeground(new java.awt.Color(90, 120, 80));
        MnSudahPulang2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnSudahPulang2.setText("Tarif Rawat Jalan");
        MnSudahPulang2.setIconTextGap(6);
        MnSudahPulang2.setName("MnSudahPulang2"); // NOI18N
        MnSudahPulang2.setPreferredSize(new java.awt.Dimension(250, 30));
        MnSudahPulang2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSudahPulang2ActionPerformed(evt);
            }
        });
        jMenu6.add(MnSudahPulang2);

        MnSudahPulang5.setBackground(new java.awt.Color(255, 255, 254));
        MnSudahPulang5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSudahPulang5.setForeground(new java.awt.Color(90, 120, 80));
        MnSudahPulang5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnSudahPulang5.setText("Tarif Rawat Inap");
        MnSudahPulang5.setIconTextGap(6);
        MnSudahPulang5.setName("MnSudahPulang5"); // NOI18N
        MnSudahPulang5.setPreferredSize(new java.awt.Dimension(250, 30));
        MnSudahPulang5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSudahPulang5ActionPerformed(evt);
            }
        });
        jMenu6.add(MnSudahPulang5);

        jSeparator11.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator11.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator11.setName("jSeparator11"); // NOI18N
        jSeparator11.setPreferredSize(new java.awt.Dimension(0, 1));
        jMenu6.add(jSeparator11);

        MnRekapHadir1.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapHadir1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHadir1.setForeground(new java.awt.Color(90, 120, 80));
        MnRekapHadir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnRekapHadir1.setText("Jadwal Praktek Dokter");
        MnRekapHadir1.setIconTextGap(6);
        MnRekapHadir1.setName("MnRekapHadir1"); // NOI18N
        MnRekapHadir1.setPreferredSize(new java.awt.Dimension(250, 30));
        MnRekapHadir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHadir1ActionPerformed(evt);
            }
        });
        jMenu6.add(MnRekapHadir1);

        MnInfoBed.setBackground(new java.awt.Color(255, 255, 254));
        MnInfoBed.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInfoBed.setForeground(new java.awt.Color(90, 120, 80));
        MnInfoBed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnInfoBed.setText("Dashboard Info Bed 1");
        MnInfoBed.setIconTextGap(6);
        MnInfoBed.setName("MnInfoBed"); // NOI18N
        MnInfoBed.setPreferredSize(new java.awt.Dimension(250, 30));
        MnInfoBed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInfoBedActionPerformed(evt);
            }
        });
        jMenu6.add(MnInfoBed);

        MnInfoBed2.setBackground(new java.awt.Color(255, 255, 254));
        MnInfoBed2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInfoBed2.setForeground(new java.awt.Color(90, 120, 80));
        MnInfoBed2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnInfoBed2.setText("Dashboard Info Bed 2");
        MnInfoBed2.setIconTextGap(6);
        MnInfoBed2.setName("MnInfoBed2"); // NOI18N
        MnInfoBed2.setPreferredSize(new java.awt.Dimension(250, 30));
        MnInfoBed2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInfoBed2ActionPerformed(evt);
            }
        });
        jMenu6.add(MnInfoBed2);

        MnInfoBed1.setBackground(new java.awt.Color(255, 255, 254));
        MnInfoBed1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInfoBed1.setForeground(new java.awt.Color(90, 120, 80));
        MnInfoBed1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnInfoBed1.setText("Dashboard Info Jadwal Dokter");
        MnInfoBed1.setIconTextGap(6);
        MnInfoBed1.setName("MnInfoBed1"); // NOI18N
        MnInfoBed1.setPreferredSize(new java.awt.Dimension(250, 30));
        MnInfoBed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInfoBed1ActionPerformed(evt);
            }
        });
        jMenu6.add(MnInfoBed1);

        MnInfoBed3.setBackground(new java.awt.Color(255, 255, 254));
        MnInfoBed3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInfoBed3.setForeground(new java.awt.Color(90, 120, 80));
        MnInfoBed3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnInfoBed3.setText("Dashboard Info Bed & Jadwal Dokter");
        MnInfoBed3.setIconTextGap(6);
        MnInfoBed3.setName("MnInfoBed3"); // NOI18N
        MnInfoBed3.setPreferredSize(new java.awt.Dimension(250, 30));
        MnInfoBed3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInfoBed3ActionPerformed(evt);
            }
        });
        jMenu6.add(MnInfoBed3);

        MnInfoBed4.setBackground(new java.awt.Color(255, 255, 254));
        MnInfoBed4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInfoBed4.setForeground(new java.awt.Color(90, 120, 80));
        MnInfoBed4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnInfoBed4.setText("Dashboard Register Poli");
        MnInfoBed4.setIconTextGap(6);
        MnInfoBed4.setName("MnInfoBed4"); // NOI18N
        MnInfoBed4.setPreferredSize(new java.awt.Dimension(250, 30));
        MnInfoBed4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInfoBed4ActionPerformed(evt);
            }
        });
        jMenu6.add(MnInfoBed4);

        MnInfoBed5.setBackground(new java.awt.Color(255, 255, 254));
        MnInfoBed5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInfoBed5.setForeground(new java.awt.Color(90, 120, 80));
        MnInfoBed5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
        MnInfoBed5.setText("Dashboard Jadwal Operasi");
        MnInfoBed5.setIconTextGap(6);
        MnInfoBed5.setName("MnInfoBed5"); // NOI18N
        MnInfoBed5.setPreferredSize(new java.awt.Dimension(250, 30));
        MnInfoBed5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInfoBed5ActionPerformed(evt);
            }
        });
        jMenu6.add(MnInfoBed5);

        MenuBar.add(jMenu6);

        jMenu7.setBackground(new java.awt.Color(20, 0, 20));
        jMenu7.setForeground(new java.awt.Color(255, 255, 255));
        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/My-Tickets24.png"))); // NOI18N
        jMenu7.setMnemonic('N');
        jMenu7.setText("Anjungan & Antrian");
        jMenu7.setToolTipText("Alt+N");
        jMenu7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu7.setIconTextGap(3);
        jMenu7.setName("jMenu7"); // NOI18N
        jMenu7.setOpaque(false);
        jMenu7.setPreferredSize(new java.awt.Dimension(138, 28));

        MnAnjungan.setBackground(new java.awt.Color(255, 255, 254));
        MnAnjungan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAnjungan.setForeground(new java.awt.Color(90, 120, 80));
        MnAnjungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/family24.png"))); // NOI18N
        MnAnjungan.setText("Anjungan Registrasi Mandiri");
        MnAnjungan.setIconTextGap(6);
        MnAnjungan.setName("MnAnjungan"); // NOI18N
        MnAnjungan.setPreferredSize(new java.awt.Dimension(210, 30));
        MnAnjungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnAnjunganActionPerformed(evt);
            }
        });
        jMenu7.add(MnAnjungan);

        jSeparator12.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator12.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator12.setName("jSeparator12"); // NOI18N
        jSeparator12.setPreferredSize(new java.awt.Dimension(0, 1));
        jMenu7.add(jSeparator12);

        MnRekapHadir3.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapHadir3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHadir3.setForeground(new java.awt.Color(90, 120, 80));
        MnRekapHadir3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        MnRekapHadir3.setText("Antrian Loket");
        MnRekapHadir3.setIconTextGap(6);
        MnRekapHadir3.setName("MnRekapHadir3"); // NOI18N
        MnRekapHadir3.setPreferredSize(new java.awt.Dimension(210, 30));
        MnRekapHadir3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHadir3ActionPerformed(evt);
            }
        });
        jMenu7.add(MnRekapHadir3);

        MnRekapHadir4.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapHadir4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHadir4.setForeground(new java.awt.Color(90, 120, 80));
        MnRekapHadir4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        MnRekapHadir4.setText("Antrian Unit/Poliklinik");
        MnRekapHadir4.setIconTextGap(6);
        MnRekapHadir4.setName("MnRekapHadir4"); // NOI18N
        MnRekapHadir4.setPreferredSize(new java.awt.Dimension(210, 30));
        MnRekapHadir4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHadir4ActionPerformed(evt);
            }
        });
        jMenu7.add(MnRekapHadir4);

        MnRekapHadir5.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapHadir5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHadir5.setForeground(new java.awt.Color(90, 120, 80));
        MnRekapHadir5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        MnRekapHadir5.setText("Antrian Apotek");
        MnRekapHadir5.setIconTextGap(6);
        MnRekapHadir5.setName("MnRekapHadir5"); // NOI18N
        MnRekapHadir5.setPreferredSize(new java.awt.Dimension(210, 30));
        MnRekapHadir5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHadir5ActionPerformed(evt);
            }
        });
        jMenu7.add(MnRekapHadir5);

        jSeparator13.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator13.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator13.setName("jSeparator13"); // NOI18N
        jSeparator13.setPreferredSize(new java.awt.Dimension(0, 1));
        jMenu7.add(jSeparator13);

        MnRekapHadir6.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapHadir6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHadir6.setForeground(new java.awt.Color(90, 120, 80));
        MnRekapHadir6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientFile.png"))); // NOI18N
        MnRekapHadir6.setText("Verifikasi Digital Klaim");
        MnRekapHadir6.setIconTextGap(6);
        MnRekapHadir6.setName("MnRekapHadir6"); // NOI18N
        MnRekapHadir6.setPreferredSize(new java.awt.Dimension(210, 30));
        MnRekapHadir6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHadir6ActionPerformed(evt);
            }
        });
        jMenu7.add(MnRekapHadir6);

        MenuBar.add(jMenu7);

        jMenu4.setBackground(new java.awt.Color(20, 0, 20));
        jMenu4.setForeground(new java.awt.Color(255, 255, 255));
        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/63.png"))); // NOI18N
        jMenu4.setMnemonic('G');
        jMenu4.setText("Tentang Program");
        jMenu4.setToolTipText("Alt+G");
        jMenu4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu4.setIconTextGap(3);
        jMenu4.setName("jMenu4"); // NOI18N
        jMenu4.setOpaque(false);
        jMenu4.setPreferredSize(new java.awt.Dimension(124, 28));
        jMenu4.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenu4MenuSelected(evt);
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
        });
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });
        MenuBar.add(jMenu4);

        setJMenuBar(MenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        DlgHome.dispose();
        System.exit(0);
    }//GEN-LAST:event_formWindowClosed

    private void BtnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseActionPerformed
        isTutup();
        DlgHome.dispose();
        int jawab=JOptionPane.showConfirmDialog(null, "Yakin anda mau keluar dari program ini ????","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(jawab==JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_BtnCloseActionPerformed

    private void BtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelActionPerformed
        edAdmin.setText("");
        edPwd.setText("");
        DlgLogin.dispose();
    }//GEN-LAST:event_BtnCancelActionPerformed

    private void BtnLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLogActionPerformed
        try{
//            com.sun.awt.AWTUtilities.setWindowOpacity(DlgLogin,0.7f);
        }catch(Exception e){            
        }
        FlayMenu.setVisible(false);
        
        switch (BtnLog.getText().trim()) {
            case "Log Out":
                BtnToolReg.setEnabled(false);
                BtnToolKamnap.setEnabled(false);
                BtnToolKasir.setEnabled(false);
                btnToolIGD.setEnabled(false);
                MnGantiPassword.setEnabled(false);
                BtnToolJualObat.setEnabled(false);
                btnToolLab.setEnabled(false);
                btnToolRad.setEnabled(false);
                var.setpenjualan_obatfalse();
                var.setpenjualan_obatfalse();
                var.setutd_penyerahan_darahfalse();
                var.setresep_dokterfalse();
                var.setresep_obatfalse();
                var.setpermintaanlabfalse();
                var.setperiksalabfalse();
                var.setpermintaanradiologifalse();
                var.setperiksaradiologifalse();
                edAdmin.setText("");
                edPwd.setText("");
                BtnLog.setText("Log In");
                MnLogin.setText("Log In");
                lblStts.setText("Status Admin : ");
                lblUser.setText("Log Out");
                BtnMenu.setEnabled(false);
                isTutup();
                break;
            case "Log In":
                DlgLogin.setVisible(true);
                edAdmin.requestFocus();
                break;
        }
    }//GEN-LAST:event_BtnLogActionPerformed

    private void BtnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLoginActionPerformed
        if(edAdmin.getText().trim().equals("")){
            Valid.textKosong(edAdmin,"ID User");
        }else if(edPwd.getText().trim().equals("")){
            Valid.textKosong(edPwd,"Password");
        }else {
            try {                               
                var.setData(edAdmin.getText(),edPwd.getText());        
                /*if(edAdmin.getText().equals("admin") && edPwd.getText().equals("akusayangsamakamu122456")){       
                    BtnMenu.setEnabled(true);             
                    BtnToolReg.setEnabled(true);
                    BtnToolKamnap.setEnabled(true);
                    BtnToolKasir.setEnabled(true);                    
                    btnToolLab.setEnabled(true);               
                    btnToolIGD.setEnabled(true);
                    btnToolBcdRalan.setEnabled(true);
                    btnToolBcdRanap.setEnabled(true);
                    MnGantiPassword.setEnabled(false);

                    DlgLogin.dispose();
                    BtnLog.setText("Log Out");
                    MnLogin.setText("Log Out");
                    lblStts.setText("Admin : ");
                    lblUser.setText("Admin Utama");
                }else */
                if(var.getjml1()>=1){    
                    BtnMenu.setEnabled(true);
                    BtnToolReg.setEnabled(true);
                    BtnToolKamnap.setEnabled(true);
                    BtnToolKasir.setEnabled(true); 
                    btnToolIGD.setEnabled(true);
                    BtnToolJualObat.setEnabled(true);
                    btnToolLab.setEnabled(true);
                    btnToolRad.setEnabled(true);
                    btnPermintaanLab.setEnabled(true);
                    btnLaboratorium.setEnabled(true);
                    btnPermintaanRadiologi.setEnabled(true);
                    btnPeriksaRadiologi.setEnabled(true);
                    btnInputPenjualan.setEnabled(true);
                    btnDataPenjualan.setEnabled(true);
                    btnDataPenyerahanDarah.setEnabled(true);
                    btnDaftarPermintaanResep.setEnabled(true);
                    btnResepObatDepan.setEnabled(true);
                    MnGantiPassword.setEnabled(false);

                    DlgLogin.dispose();
                    BtnLog.setText("Log Out");
                    MnLogin.setText("Log Out");
                    lblStts.setText("Admin : ");
                    lblUser.setText("Admin Utama");
                }else if(var.getjml2()>=1){  
                    BtnMenu.setEnabled(true);
                    DlgLogin.dispose();
                    BtnLog.setText("Log Out");
                    MnLogin.setText("Log Out");
                    lblStts.setText("Admin : ");
                    lblUser.setText(var.getkode());
                    MnGantiPassword.setEnabled(true);
                    BtnToolReg.setEnabled(var.getregistrasi());
                    if((var.getkamar_inap()==true)||(var.getbilling_ranap()==true)||(var.gettindakan_ranap()==true)){
                        BtnToolKamnap.setEnabled(true);
                    }else{
                        BtnToolKamnap.setEnabled(var.getkamar_inap());
                    }
                    
                    if((var.getkasir_ralan()==true)||(var.getbilling_ralan()==true)){
                        BtnToolKasir.setEnabled(true);
                    }else{
                        BtnToolKasir.setEnabled(var.getkasir_ralan());
                    }
                        
                    if((var.getpermintaan_radiologi()==true)||(var.getperiksa_radiologi()==true)){
                        btnPermintaanRadiologi.setEnabled(true);
                    }else{
                        btnPermintaanRadiologi.setEnabled(var.getpermintaan_radiologi());
                    }
                    
                    if((var.getpermintaan_lab()==true)||(var.getperiksa_lab()==true)){
                        btnPermintaanLab.setEnabled(true);
                    }else{
                        btnPermintaanLab.setEnabled(var.getpermintaan_lab());
                    }
                    btnToolLab.setEnabled(true);
                    btnToolRad.setEnabled(true);
                    BtnToolJualObat.setEnabled(true);
                    btnToolIGD.setEnabled(var.getigd());                     
                    btnLaboratorium.setEnabled(var.getperiksa_lab());
                    btnPeriksaRadiologi.setEnabled(var.getperiksa_radiologi());  
                    btnInputPenjualan.setEnabled(var.getpenjualan_obat());
                    btnDataPenjualan.setEnabled(var.getpenjualan_obat());
                    btnDataPenyerahanDarah.setEnabled(var.getutd_penyerahan_darah());
                    btnDaftarPermintaanResep.setEnabled(var.getresep_dokter());
                    btnResepObatDepan.setEnabled(var.getresep_obat());
                    Sequel.menyimpan("tracker","'"+edAdmin.getText()+"',current_date(),current_time()","Login");
                }else if((var.getjml1()==0)&&(var.getjml2()==0)){
                    JOptionPane.showMessageDialog(null,"Maaf, Gagal login. ID User atau password ada yang salah ...!");
                    BtnToolReg.setEnabled(false);
                    BtnToolKamnap.setEnabled(false);
                    BtnToolKasir.setEnabled(false);
                    MnGantiPassword.setEnabled(false);  
                    btnToolIGD.setEnabled(false);
                    BtnToolJualObat.setEnabled(false);
                    btnToolLab.setEnabled(false);
                    btnToolRad.setEnabled(false);
                    btnPermintaanLab.setEnabled(false);
                    btnLaboratorium.setEnabled(false);
                    btnPermintaanRadiologi.setEnabled(false);
                    btnPeriksaRadiologi.setEnabled(false);    
                    btnInputPenjualan.setEnabled(false);
                    btnDataPenjualan.setEnabled(false);
                    btnDataPenyerahanDarah.setEnabled(false);
                    btnDaftarPermintaanResep.setEnabled(false);
                    btnResepObatDepan.setEnabled(false);
                    edAdmin.setText("");
                    edPwd.setText("");           
                     
                    BtnMenu.setEnabled(false);

                    edAdmin.requestFocus();
                    BtnLog.setText("Log In");
                    MnLogin.setText("Log In");
                    lblStts.setText("Status Admin : ");
                    lblUser.setText("Log Out");   
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }
        }
    }//GEN-LAST:event_BtnLoginActionPerformed

    private void BtnToolKamnapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnToolKamnapActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.isCek();
        kasirralan.kamarinap.emptTeks();  
        kasirralan.kamarinap.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnToolKamnapActionPerformed

private void edAdminKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edAdminKeyPressed
        Valid.pindah(evt,BtnCancel, edPwd);
}//GEN-LAST:event_edAdminKeyPressed

private void edPwdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edPwdKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnLoginActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            edAdmin.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnLogin.requestFocus();
        }
}//GEN-LAST:event_edPwdKeyPressed

private void BtnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenuActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
    isTutup();
    DlgHome.setSize(PanelUtama.getWidth()-35, PanelUtama.getHeight()-35);
    DlgHome.setLocationRelativeTo(PanelUtama);
    DlgHome.setVisible(true);
    isTampil();
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnMenuActionPerformed

private void BtnToolKasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnToolKasirActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        isTutup();
        kasirralan.isCek();      
        kasirralan.setCariKosong();  
        kasirralan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.setLocationRelativeTo(PanelUtama);
        kasirralan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnToolKasirActionPerformed

private void BtnToolRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnToolRegActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        isTutup();
        kasirralan.kamarinap.reg.emptTeks();    
        kasirralan.kamarinap.reg.isCek();
        kasirralan.kamarinap.reg.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.reg.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.reg.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());   
}//GEN-LAST:event_BtnToolRegActionPerformed

private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
    if(this.getState()==1){
        isTutup();
    }
}//GEN-LAST:event_formWindowStateChanged

private void BtnClosePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnClosePassActionPerformed
        WindowInput.dispose();
}//GEN-LAST:event_BtnClosePassActionPerformed

private void BtnClosePassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnClosePassKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            WindowInput.dispose();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            PassBaru2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            PassLama.requestFocus();
        }
}//GEN-LAST:event_BtnClosePassKeyPressed

private void BtnSimpanPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanPassActionPerformed
        if(PassLama.getText().trim().equals("")){
            Valid.textKosong(PassLama,"Password Lama");
        }else if(Passbaru1.getText().trim().equals("")){
            Valid.textKosong(Passbaru1,"Password Baru");
        }else if(PassBaru2.getText().trim().equals("")){
            Valid.textKosong(PassBaru2,"Password Baru");
        }else if(!edPwd.getText().trim().equals(PassLama.getText())){
            JOptionPane.showMessageDialog(null,"Maaf, Password lama salah...!!!");
            PassLama.requestFocus();
        }else if(!Passbaru1.getText().trim().equals(PassBaru2.getText())){
            JOptionPane.showMessageDialog(null,"Maaf, Password Baru 1 dan Password Baru 2 tidak sesuai...!!!");
            PassBaru2.requestFocus();
        }else{
            Sequel.queryu("update user set password=AES_ENCRYPT('"+PassBaru2.getText()+"','windi')  where id_user=AES_ENCRYPT('"+lblUser.getText()+"','nur')");            
            WindowInput.setVisible(false);
        }
}//GEN-LAST:event_BtnSimpanPassActionPerformed

private void BtnSimpanPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanPassKeyPressed
        Valid.pindah(evt,PassLama,PassBaru2);
}//GEN-LAST:event_BtnSimpanPassKeyPressed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        DlgHome.dispose();
    }//GEN-LAST:event_button1ActionPerformed

    private void cmbMenuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMenuItemStateChanged
        isTampil();
    }//GEN-LAST:event_cmbMenuItemStateChanged

    private void btnBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarcodeActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        DlgBarcode barcode=new DlgBarcode(this,false);
        barcode.tampil();
        barcode.isCek();
        barcode.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        barcode.setLocationRelativeTo(PanelUtama);
        barcode.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBarcodeActionPerformed

    private void btnICDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnICDActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        isTutup();
        kasirralan.kamarinap.billing.beriobat.dlgobtpny.penyakit.penyakit.isCek();
        kasirralan.kamarinap.billing.beriobat.dlgobtpny.penyakit.penyakit.emptTeks();
        kasirralan.kamarinap.billing.beriobat.dlgobtpny.penyakit.penyakit.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.billing.beriobat.dlgobtpny.penyakit.penyakit.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.billing.beriobat.dlgobtpny.penyakit.penyakit.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnICDActionPerformed

    private void btnObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObatActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        isTutup();
        kasirralan.billing.dlgobt.barang.emptTeks();
        kasirralan.billing.dlgobt.barang.isCek();
        kasirralan.billing.dlgobt.barang.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.billing.dlgobt.barang.setLocationRelativeTo(PanelUtama);
        kasirralan.billing.dlgobt.barang.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnObatActionPerformed

    private void btnObatPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObatPenyakitActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgObatPenyakit obatpenyakit=new DlgObatPenyakit(this,false);
        obatpenyakit.isCek();
        obatpenyakit.emptTeks();
        obatpenyakit.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        obatpenyakit.setLocationRelativeTo(PanelUtama);
        obatpenyakit.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnObatPenyakitActionPerformed

    private void btnKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKamarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        isTutup();
        kasirralan.kamarinap.kamar.emptTeks();
        kasirralan.kamarinap.kamar.isCek();
        kasirralan.kamarinap.kamar.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.kamar.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.kamar.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKamarActionPerformed

    private void btnTindakanRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTindakanRalanActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJnsPerawatanRalan form=new DlgJnsPerawatanRalan(null,false);
        form.emptTeks();
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTindakanRalanActionPerformed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.reg.dokter.dokter.emptTeks();
        kasirralan.kamarinap.reg.dokter.dokter.isCek();
        kasirralan.kamarinap.reg.dokter.dokter.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.reg.dokter.dokter.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.reg.dokter.dokter.setAlwaysOnTop(false);
        kasirralan.kamarinap.reg.dokter.dokter.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDokterActionPerformed

    private void btnPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPegawaiActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.billing.dlgrwjl.petugas.petugas.emptTeks();
        kasirralan.billing.dlgrwjl.petugas.petugas.isCek();
        kasirralan.billing.dlgrwjl.petugas.petugas.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.billing.dlgrwjl.petugas.petugas.setLocationRelativeTo(PanelUtama);
        kasirralan.billing.dlgrwjl.petugas.petugas.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPegawaiActionPerformed

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.reg.pasien.emptTeks();
        kasirralan.kamarinap.reg.pasien.isCek();
        kasirralan.kamarinap.reg.pasien.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.reg.pasien.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.reg.pasien.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnRegistrasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrasiActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.reg.emptTeks();
        kasirralan.kamarinap.reg.isCek();
        kasirralan.kamarinap.reg.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.reg.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.reg.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRegistrasiActionPerformed

    private void btnRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRalanActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRawatJalan form=new DlgRawatJalan(null,false);
        form.isCek();
        form.SetPj("-");
        form.SetPoli("-");
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRalanActionPerformed

    private void btnKamarInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKamarInapActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.isCek();
        kasirralan.kamarinap.emptTeks();
        kasirralan.kamarinap.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKamarInapActionPerformed

    private void btnRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRanapActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.billing.rawatinap.tampilDr();
        kasirralan.kamarinap.billing.rawatinap.isCek();
        kasirralan.kamarinap.billing.rawatinap.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.billing.rawatinap.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.billing.rawatinap.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRanapActionPerformed

    private void btnResepObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResepObatActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgResepObat resep=new DlgResepObat(this,false);
//        resep.tampil();
        resep.emptTeks();
        resep.isCek();
        resep.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        resep.setLocationRelativeTo(PanelUtama);
        resep.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnResepObatActionPerformed

    private void btnRujukPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRujukPasienActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRujuk rujuk=new DlgRujuk(this,false);
        rujuk.tampil();
        rujuk.emptTeks();
        rujuk.isCek();
        rujuk.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rujuk.setLocationRelativeTo(PanelUtama);
        rujuk.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRujukPasienActionPerformed

    private void btnBeriObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBeriObatActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.billing.beriobat.tampilPO();
        kasirralan.kamarinap.billing.beriobat.isCek();
        kasirralan.kamarinap.billing.beriobat.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.billing.beriobat.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.billing.beriobat.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBeriObatActionPerformed

    private void btnPasienMatiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienMatiActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPasienMati pasienmati=new DlgPasienMati(this,false);
        pasienmati.emptTeks();
        pasienmati.isCek();
        pasienmati.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        pasienmati.setLocationRelativeTo(PanelUtama);
        pasienmati.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPasienMatiActionPerformed

    private void btnAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgAdmin admin=new DlgAdmin(this,false);
        admin.tampil();
        admin.emptTeks();
        admin.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        admin.setLocationRelativeTo(PanelUtama);
        admin.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnAdminActionPerformed

    private void btnUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgUser user=new DlgUser(this,false);
        user.emptTeks();
        user.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        user.setLocationRelativeTo(PanelUtama);
        user.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnUserActionPerformed

    private void btnDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRunTeks runteks=new DlgRunTeks(this,false);
        runteks.emptTeks();
        runteks.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        runteks.setLocationRelativeTo(PanelUtama);
        runteks.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDisplayActionPerformed

    private void btnSetupHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetupHargaActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetHarga setharga=new DlgSetHarga(this,false);
        setharga.emptTeks();
        setharga.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        setharga.setLocationRelativeTo(PanelUtama);
        setharga.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetupHargaActionPerformed

    private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSuplier suplier=new DlgSuplier(this,false);
        suplier.isCek();
        suplier.emptTeks();
        suplier.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        suplier.setLocationRelativeTo(PanelUtama);
        suplier.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuplierActionPerformed

    private void btnJnsBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJnsBarangActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.billing.dlgobt.barang.jenis.jenis.isCek();
        kasirralan.billing.dlgobt.barang.jenis.jenis.emptTeks();
        kasirralan.billing.dlgobt.barang.jenis.jenis.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.billing.dlgobt.barang.jenis.jenis.setLocationRelativeTo(PanelUtama);
        kasirralan.billing.dlgobt.barang.jenis.jenis.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnJnsBarangActionPerformed

    private void btnKonversiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKonversiActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgKonversi konversi=new DlgKonversi(this,false);
        konversi.isCek();
        konversi.emptTeks();
        konversi.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        konversi.setLocationRelativeTo(PanelUtama);
        konversi.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKonversiActionPerformed

    private void btnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatuanActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.billing.dlgobt.barang.satuan.satuan.isCek();
        kasirralan.billing.dlgobt.barang.satuan.satuan.emptTeks();
        kasirralan.billing.dlgobt.barang.satuan.satuan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.billing.dlgobt.barang.satuan.satuan.setLocationRelativeTo(PanelUtama);
        kasirralan.billing.dlgobt.barang.satuan.satuan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSatuanActionPerformed

    private void btnCashFlowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCashFlowActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCashflow bubes=new DlgCashflow(this,false);
        bubes.isCek();
        bubes.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        bubes.setLocationRelativeTo(PanelUtama);
        bubes.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCashFlowActionPerformed

    private void btnBubesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBubesActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBubes bubes=new DlgBubes(this,false);
        bubes.isCek();
        bubes.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        bubes.setLocationRelativeTo(PanelUtama);
        bubes.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBubesActionPerformed

    private void btnPostingJurnalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPostingJurnalActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJurnal jurnal=new DlgJurnal(this,false);
        jurnal.tampil();
        jurnal.isCek();
        jurnal.emptTeks();
        jurnal.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        jurnal.setLocationRelativeTo(PanelUtama);
        jurnal.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPostingJurnalActionPerformed

    private void btnRekeningTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekeningTahunActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRekeningTahun rekeningtahun=new DlgRekeningTahun(this,false);
        rekeningtahun.tampil();
        rekeningtahun.isCek();
        rekeningtahun.emptTeks();
        rekeningtahun.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rekeningtahun.setLocationRelativeTo(PanelUtama);
        rekeningtahun.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekeningTahunActionPerformed

    private void btnRekeningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekeningActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRekening rekening=new DlgRekening(this,false);
        rekening.tampil();
        rekening.isCek();
        rekening.emptTeks();
        rekening.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rekening.setLocationRelativeTo(PanelUtama);
        rekening.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekeningActionPerformed

    private void btnPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPembelianActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPembelian pembelian=new DlgPembelian(this,false);
        pembelian.isCek();
        pembelian.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        pembelian.setLocationRelativeTo(PanelUtama);
        pembelian.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPembelianActionPerformed

    private void btnPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenjualanActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPenjualan penjualan=new DlgPenjualan(this,false);
        penjualan.isCek();
        penjualan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        penjualan.setLocationRelativeTo(PanelUtama);
        penjualan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPenjualanActionPerformed

    private void btnPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPiutangActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPiutang piutang=new DlgPiutang(this,false);
        piutang.emptTeks();
        piutang.isCek();
        piutang.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        piutang.setLocationRelativeTo(PanelUtama);
        piutang.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPiutangActionPerformed

    private void btnBayarPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarPiutangActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBayarPiutang bayarpiutang=new DlgBayarPiutang(this,false);
        bayarpiutang.tampil();
        bayarpiutang.emptTeks();
        bayarpiutang.isCek();
        bayarpiutang.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        bayarpiutang.setLocationRelativeTo(PanelUtama);
        bayarpiutang.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBayarPiutangActionPerformed

    private void btnOpnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpnameActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgInputStok opname=new DlgInputStok(this,false);
        opname.tampil();
        opname.isCek();
        opname.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        opname.setLocationRelativeTo(PanelUtama);
        opname.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnOpnameActionPerformed

    private void btnReturBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturBeliActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgReturBeli returbeli=new DlgReturBeli(this,false);
        returbeli.isCek();
        returbeli.emptTeks();
        returbeli.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        returbeli.setLocationRelativeTo(PanelUtama);
        returbeli.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnReturBeliActionPerformed

    private void btnReturJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturJualActionPerformed
        var.setform("DlgReturJual");
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgReturJual returjual=new DlgReturJual(this,false);
        returjual.emptTeks();
        returjual.isCek();
        returjual.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        returjual.setLocationRelativeTo(PanelUtama);
        returjual.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnReturJualActionPerformed

    private void btnSirkulasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSirkulasiActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSirkulasiBarang sirkulasi=new DlgSirkulasiBarang(this,false);
        sirkulasi.isCek();
        sirkulasi.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        sirkulasi.setLocationRelativeTo(PanelUtama);
        sirkulasi.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSirkulasiActionPerformed

    private void btnKeuntunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeuntunganActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgProyeksiJual projul=new DlgProyeksiJual(this,false);
        projul.isCek();
        projul.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        projul.setLocationRelativeTo(PanelUtama);
        projul.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKeuntunganActionPerformed

    private void btnLabaRugiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLabaRugiActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgLabaRugi labrug=new DlgLabaRugi(this,false);
        labrug.isCek();
        labrug.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        labrug.setLocationRelativeTo(PanelUtama);
        labrug.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLabaRugiActionPerformed

    private void btnReturPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturPiutangActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgReturPiutang returpiutang=new DlgReturPiutang(this,false);
        returpiutang.isCek();
        returpiutang.emptTeks();
        returpiutang.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        returpiutang.setLocationRelativeTo(PanelUtama);
        returpiutang.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnReturPiutangActionPerformed

    private void btnAnalisaKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalisaKamarActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InformasiAnalisaKamin analisakamin=new InformasiAnalisaKamin(this,false);
        analisakamin.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        analisakamin.setLocationRelativeTo(PanelUtama);
        analisakamin.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnAnalisaKamarActionPerformed

    private void btnRHDOkterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRHDOkterActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHJmDokter rhtindakandokter=new DlgRHJmDokter(this,false);
        rhtindakandokter.isCek();
        rhtindakandokter.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhtindakandokter.setLocationRelativeTo(PanelUtama);
        rhtindakandokter.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRHDOkterActionPerformed

    private void btnRBDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRBDokterActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBJmDokter rbtindakandokter=new DlgRBJmDokter(this,false);
        rbtindakandokter.isCek();
        rbtindakandokter.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbtindakandokter.setLocationRelativeTo(PanelUtama);
        rbtindakandokter.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRBDokterActionPerformed

    private void btnTagihanMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagihanMasukActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgLhtBiaya billing=new DlgLhtBiaya(this,false);
        billing.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        billing.setLocationRelativeTo(PanelUtama);
        billing.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTagihanMasukActionPerformed

    private void btnResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResumeActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgResumePerawatan resume=new DlgResumePerawatan(this,false);
        resume.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        resume.setLocationRelativeTo(PanelUtama);
        resume.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnResumeActionPerformed

    private void btnDietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDietActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPemberianDiet diet=new DlgPemberianDiet(this,false);
        diet.tampil();
        diet.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        diet.setLocationRelativeTo(PanelUtama);
        diet.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDietActionPerformed

    private void btnRHParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRHParamedisActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHJmParamedis rhtindakanparamedis=new DlgRHJmParamedis(this,false);
        rhtindakanparamedis.isCek();
        rhtindakanparamedis.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhtindakanparamedis.setLocationRelativeTo(PanelUtama);
        rhtindakanparamedis.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRHParamedisActionPerformed

    private void btnRBParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRBParamedisActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBJmParamedis rbtindakanparamedis=new DlgRBJmParamedis(this,false);
        rbtindakanparamedis.isCek();
        rbtindakanparamedis.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbtindakanparamedis.setLocationRelativeTo(PanelUtama);
        rbtindakanparamedis.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRBParamedisActionPerformed

    private void btnKasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKasirActionPerformed
        BtnToolKasirActionPerformed(evt);
    }//GEN-LAST:event_btnKasirActionPerformed

    private void btnLahirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLahirActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgIKBBayi lahir=new DlgIKBBayi(this,false);
        lahir.tampil();
        lahir.isCek();
        lahir.emptTeks();
        lahir.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        lahir.setLocationRelativeTo(PanelUtama);
        lahir.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLahirActionPerformed

    private void btnSetBiayaHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetBiayaHarianActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBiayaHarian biayaharian=new DlgBiayaHarian(this,false);
        biayaharian.emptTeks();
        biayaharian.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        biayaharian.setLocationRelativeTo(PanelUtama);
        biayaharian.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetBiayaHarianActionPerformed

    private void btnJenisInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJenisInventarisActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InventarisJenis jnsinventaris=new InventarisJenis(this,false);
        jnsinventaris.emptTeks();
        jnsinventaris.tampil();
        jnsinventaris.isCek();
        jnsinventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        jnsinventaris.setLocationRelativeTo(PanelUtama);
        jnsinventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnJenisInventarisActionPerformed

    private void btnKategoriInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriInventarisActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InventarisKategori ktginventaris=new InventarisKategori(this,false);
        ktginventaris.tampil();
        ktginventaris.isCek();
        ktginventaris.emptTeks();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKategoriInventarisActionPerformed

    private void btnLihatPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLihatPiutangActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgLhtPiutang billing=new DlgLhtPiutang(this,false);
        billing.tampil();
        billing.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        billing.setLocationRelativeTo(PanelUtama);
        billing.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLihatPiutangActionPerformed

    private void btnLaboratoriumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaboratoriumActionPerformed
        
        isTutup();
        DlgCariPeriksaLab produsen=new DlgCariPeriksaLab(this,false);
        //produsen.emptTeks();
        produsen.isCek();
        produsen.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        produsen.setLocationRelativeTo(PanelUtama);
        produsen.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLaboratoriumActionPerformed

    private void btnRalanMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRalanMasukActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPembayaranRalan billing=new DlgPembayaranRalan(this,false);
        billing.tampil();
        billing.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        billing.setLocationRelativeTo(PanelUtama);
        billing.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRalanMasukActionPerformed

    private void btnSetupAplikasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetupAplikasiActionPerformed
        
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetAplikasi aplikasi=new DlgSetAplikasi(this,false);
        aplikasi.emptTeks();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);

        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetupAplikasiActionPerformed

    private void btnSetOtoRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetOtoRalanActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetOtoRalan aplikasi=new DlgSetOtoRalan(this,false);
        aplikasi.emptTeks();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetOtoRalanActionPerformed

    private void btnRanapMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRanapMasukActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPembayaranRanap billing=new DlgPembayaranRanap(this,false);
        billing.tampil();
        billing.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        billing.setLocationRelativeTo(PanelUtama);
        billing.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRanapMasukActionPerformed

    private void btnProdusenInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdusenInventarisActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InventarisProdusen produsen=new InventarisProdusen(this,false);
        produsen.tampil();
        produsen.emptTeks();
        produsen.isCek();
        produsen.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        produsen.setLocationRelativeTo(PanelUtama);
        produsen.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnProdusenInventarisActionPerformed

    private void btnSetBiayaMasukSekaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetBiayaMasukSekaliActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBiayaSekaliMasuk biayaharian=new DlgBiayaSekaliMasuk(this,false);
        biayaharian.emptTeks();
        biayaharian.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        biayaharian.setLocationRelativeTo(PanelUtama);
        biayaharian.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetBiayaMasukSekaliActionPerformed

    private void btnPaketOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaketOperasiActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJnsPerawatanOperasi produsen=new DlgJnsPerawatanOperasi(this,false);
        produsen.emptTeks();
        produsen.isCek();
        produsen.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        produsen.setLocationRelativeTo(PanelUtama);
        produsen.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPaketOperasiActionPerformed

    private void btnTagihanOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagihanOperasiActionPerformed
        
        isTutup();
        DlgCariTagihanOperasi produsen=new DlgCariTagihanOperasi(this,false);
        //produsen.emptTeks();
        produsen.isCek();
        produsen.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        produsen.setLocationRelativeTo(PanelUtama);
        produsen.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTagihanOperasiActionPerformed

    private void BtnJadwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJadwalActionPerformed
        
        isTutup();
        DlgJadwal jadwal=new DlgJadwal(this,false);
        jadwal.emptTeks();
        jadwal.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        jadwal.setLocationRelativeTo(PanelUtama);
        jadwal.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnJadwalActionPerformed

    private void btnMerkInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMerkInventarisActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InventarisMerk ktginventaris=new InventarisMerk(this,false);
        ktginventaris.tampil();
        ktginventaris.isCek();
        ktginventaris.emptTeks();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnMerkInventarisActionPerformed

    private void btnRuangInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRuangInventarisActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InventarisRuang ktginventaris=new InventarisRuang(this,false);
        ktginventaris.tampil();
        ktginventaris.isCek();
        ktginventaris.emptTeks();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRuangInventarisActionPerformed

    private void btnBarangInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangInventarisActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InventarisBarang ktginventaris=new InventarisBarang(this,false);
        ktginventaris.tampil();
        ktginventaris.isCek();
        ktginventaris.emptTeks();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBarangInventarisActionPerformed

    private void btnInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarisActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InventarisKoleksi ktginventaris=new InventarisKoleksi(this,false);
        ktginventaris.tampil();
        ktginventaris.isCek();
        ktginventaris.emptTeks();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnInventarisActionPerformed

    private void btnSirkulasiInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSirkulasiInventarisActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InventarisSirkulasi ktginventaris=new InventarisSirkulasi(this,false);
        ktginventaris.tampil();
        ktginventaris.isCek();
        ktginventaris.emptTeks();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSirkulasiInventarisActionPerformed

    private void btnFrekuensiRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFrekuensiRalanActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFrekuensiPenyakitRalan ktginventaris=new DlgFrekuensiPenyakitRalan(this,false);
        ktginventaris.isCek();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnFrekuensiRalanActionPerformed

    private void btnFrekuensiRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFrekuensiRanapActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFrekuensiPenyakitRanap ktginventaris=new DlgFrekuensiPenyakitRanap(this,false);
        ktginventaris.isCek();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnFrekuensiRanapActionPerformed

    private void btnSetupOtoLokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetupOtoLokasiActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetOtoLokasi ktginventaris=new DlgSetOtoLokasi(this,false);
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetupOtoLokasiActionPerformed

    private void btnTagihanPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagihanPoliActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBTindakanPoli rbpoli=new DlgRBTindakanPoli(this,false);
        rbpoli.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbpoli.setLocationRelativeTo(PanelUtama);
        rbpoli.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTagihanPoliActionPerformed

    private void btnRujukMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRujukMasukActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRujukMasuk rujukmasuk=new DlgRujukMasuk(null,false);
        rujukmasuk.tampil();
        rujukmasuk.emptTeks();
        rujukmasuk.isCek();
        rujukmasuk.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rujukmasuk.setLocationRelativeTo(PanelUtama);
        rujukmasuk.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRujukMasukActionPerformed

    private void btnTrackerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrackerActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPenelusuranLogin rbpoli=new DlgPenelusuranLogin(this,false);
        rbpoli.isCek();
        rbpoli.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbpoli.setLocationRelativeTo(PanelUtama);
        rbpoli.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTrackerActionPerformed

    private void btnTindakanRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTindakanRanapActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.billing.rawatinap.perawatan.perawatan.emptTeks();
        kasirralan.kamarinap.billing.rawatinap.perawatan.perawatan.isCek();
        kasirralan.kamarinap.billing.rawatinap.perawatan.perawatan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.billing.rawatinap.perawatan.perawatan.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.billing.rawatinap.perawatan.perawatan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTindakanRanapActionPerformed

    private void btnSetupJamInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetupJamInapActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetKamarInap form=new DlgSetKamarInap(this,false);
        form.tampil();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetupJamInapActionPerformed

    private void btnStokObatPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStokObatPasienActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgStokPasien opname=new DlgStokPasien(this,false);
        opname.isCek();
        opname.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        opname.setLocationRelativeTo(PanelUtama);
        opname.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnStokObatPasienActionPerformed

    private void btnTarifLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTarifLabActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJnsPerawatanLab tariflab=new DlgJnsPerawatanLab(this,false);
        tariflab.emptTeks();
        tariflab.isCek();
        tariflab.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        tariflab.setLocationRelativeTo(PanelUtama);
        tariflab.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTarifLabActionPerformed

    private void btnSetPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetPenjabActionPerformed
        
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetPenjabLab aplikasi=new DlgSetPenjabLab(this,false);
        aplikasi.emptTeks();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetPenjabActionPerformed

    private void btnTagihanObatPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagihanObatPoliActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBObatPoli rbpoli=new DlgRBObatPoli(this,false);
        rbpoli.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbpoli.setLocationRelativeTo(PanelUtama);
        rbpoli.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTagihanObatPoliActionPerformed

    private void btnTagihanObatBangsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagihanObatBangsalActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBObatBangsal rbobatbangsal=new DlgRBObatBangsal(this,false);
        rbobatbangsal.isCek();
        rbobatbangsal.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbobatbangsal.setLocationRelativeTo(PanelUtama);
        rbobatbangsal.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTagihanObatBangsalActionPerformed

    private void btnReturPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturPasienActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgReturObatPasien returpasien=new DlgReturObatPasien(this,false);
        returpasien.isCek();
        returpasien.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        returpasien.setLocationRelativeTo(PanelUtama);
        returpasien.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnReturPasienActionPerformed

    private void btnKeuntunganObatRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeuntunganObatRanapActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgProyeksiBeriObat projul=new DlgProyeksiBeriObat(this,false);
        projul.isCek();
        projul.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        projul.setLocationRelativeTo(PanelUtama);
        projul.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKeuntunganObatRanapActionPerformed

    private void btnPenggajianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenggajianActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPenggajian penggajian=new DlgPenggajian(this,false);
        try {
            if(var.getpegawai_admin()==true){
                penggajian.loadURL("http://" +koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"penggajian/login.php?act=login&usere=admin&passwordte=akusayangsamakamu");
            }else if(var.getpegawai_user()==true){
                penggajian.loadURL("http://" +koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"penggajian/login.php?act=login&usere=paijo&passwordte=mumet");
            }            
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        
        penggajian.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        penggajian.setLocationRelativeTo(PanelUtama);
        penggajian.setVisible(true);
        
        
        /*try {
            if(var.getpegawai_admin()==true){
                Valid.panggilUrl("penggajian/login.php?act=login&usere=admin&passwordte=akusayangsamakamu");
            }else if(var.getpegawai_user()==true){
                Valid.panggilUrl("penggajian/login.php?act=login&usere=paijo&passwordte=mumet");                
            }            
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }  */
                
        
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPenggajianActionPerformed

    private void btnRekapPresensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapPresensiActionPerformed
        MnRekapHadirActionPerformed(evt);
    }//GEN-LAST:event_btnRekapPresensiActionPerformed

    private void btnRekapHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapHarianActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        isTutup();
        DlgHarian harian=new DlgHarian(this,false);
        harian.tampil();
        harian.isCek();
        harian.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        harian.setLocationRelativeTo(PanelUtama);
        harian.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekapHarianActionPerformed

    private void btnRekapBulananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapBulananActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        isTutup();
        DlgBulanan bulanan=new DlgBulanan(this,false);
        bulanan.isCek();
//        bulanan.tampil();
        bulanan.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        bulanan.setLocationRelativeTo(PanelUtama);
        bulanan.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekapBulananActionPerformed

    private void btnDepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepositActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDeposit deposit=new DlgDeposit(this,false);
        deposit.tampil();
        deposit.isCek();
        deposit.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        deposit.setLocationRelativeTo(PanelUtama);
        deposit.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDepositActionPerformed

    private void btnSetupRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetupRMActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetRM aplikasi=new DlgSetRM(this,false);
        aplikasi.emptTeks();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetupRMActionPerformed

    private void btnResepPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResepPulangActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgResepPulang reseppulang=new DlgResepPulang(this,false);
        reseppulang.tampil();
        reseppulang.isCek();
        reseppulang.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        reseppulang.setLocationRelativeTo(PanelUtama);
        reseppulang.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnResepPulangActionPerformed

    private void btnSetupTarifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetupTarifActionPerformed
        
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetTarif aplikasi=new DlgSetTarif(this,false);
        aplikasi.emptTeks();
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetupTarifActionPerformed

    private void btnToolLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToolLabActionPerformed
        isTutup();
        FlayMenu.removeAll();        
        FlayMenu.add(btnPermintaanLab);
        FlayMenu.add(btnLaboratorium);
        if((var.getpermintaan_lab()==true)||(var.getperiksa_lab()==true)){
            btnPermintaanLab.setEnabled(true);
        }else{
            btnPermintaanLab.setEnabled(var.getpermintaan_lab());
        }
        btnLaboratorium.setEnabled(var.getperiksa_lab());
        FlayMenu.setVisible(true); 
    }//GEN-LAST:event_btnToolLabActionPerformed

    private void btnBarangIpsrsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangIpsrsActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBarangIPSRS ipsrs=new DlgBarangIPSRS(this,false);
        ipsrs.emptTeks();
        ipsrs.onCari();
        ipsrs.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        ipsrs.setLocationRelativeTo(PanelUtama);
        ipsrs.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBarangIpsrsActionPerformed

    private void btnPembelianIpsrsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPembelianIpsrsActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPembelianIPSRS pembelian=new DlgPembelianIPSRS(this,false);
        pembelian.isCek();
        pembelian.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        pembelian.setLocationRelativeTo(PanelUtama);
        pembelian.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPembelianIpsrsActionPerformed

    private void btnPengeluaranIpsrsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPengeluaranIpsrsActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPengeluaranIPSRS pengeluaran=new DlgPengeluaranIPSRS(this,false);
        pengeluaran.isCek();
        pengeluaran.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        pengeluaran.setLocationRelativeTo(PanelUtama);
        pengeluaran.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPengeluaranIpsrsActionPerformed

    private void btnRHMasukIpsrsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRHMasukIpsrsActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHPembelianIPSRS rhipsrs=new DlgRHPembelianIPSRS(this,false);
        rhipsrs.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhipsrs.setLocationRelativeTo(PanelUtama);
        rhipsrs.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRHMasukIpsrsActionPerformed

    private void btnRHKeluarIpsrsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRHKeluarIpsrsActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHPengeluaranIPSRS rhkeluaripsrs=new DlgRHPengeluaranIPSRS(this,false);
        rhkeluaripsrs.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhkeluaripsrs.setLocationRelativeTo(PanelUtama);
        rhkeluaripsrs.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRHKeluarIpsrsActionPerformed

    private void btnRBiayaIpsrsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRBiayaIpsrsActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBiayaHarianIPSRS rhkeluaripsrs=new DlgRBiayaHarianIPSRS(this,false);
        rhkeluaripsrs.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhkeluaripsrs.setLocationRelativeTo(PanelUtama);
        rhkeluaripsrs.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRBiayaIpsrsActionPerformed

    private void btnTarifRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTarifRadiologiActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJnsPerawatanRadiologi tarifrad=new DlgJnsPerawatanRadiologi(this,false);
        tarifrad.emptTeks();
        tarifrad.isCek();
        tarifrad.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        tarifrad.setLocationRelativeTo(PanelUtama);
        tarifrad.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTarifRadiologiActionPerformed

    private void btnPeriksaRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeriksaRadiologiActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        isTutup();
        DlgCariPeriksaRadiologi produsen=new DlgCariPeriksaRadiologi(this,false);
        //produsen.emptTeks();
        produsen.isCek();
        produsen.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        produsen.setLocationRelativeTo(PanelUtama);
        produsen.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPeriksaRadiologiActionPerformed

    private void btnToolIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToolIGDActionPerformed
        btnIGDActionPerformed(evt);
    }//GEN-LAST:event_btnToolIGDActionPerformed

    private void btnTagihanRalanPerhariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagihanRalanPerhariActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPembayaranRalanPerHari rhkeluaripsrs=new DlgPembayaranRalanPerHari(this,false);
        rhkeluaripsrs.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhkeluaripsrs.setLocationRelativeTo(PanelUtama);
        rhkeluaripsrs.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTagihanRalanPerhariActionPerformed

    private void btnTagihanRanapPerhariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagihanRanapPerhariActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPembyaranRanapPerhari rhkeluaripsrs=new DlgPembyaranRanapPerhari(this,false);
        rhkeluaripsrs.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhkeluaripsrs.setLocationRelativeTo(PanelUtama);
        rhkeluaripsrs.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTagihanRanapPerhariActionPerformed

    private void btnSetupEmbalaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetupEmbalaseActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetEmbalase ktginventaris=new DlgSetEmbalase(this,false);
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetupEmbalaseActionPerformed

    private void btnSirkulasiBerkasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSirkulasiBerkasActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSirkulasiBerkas ktginventaris=new DlgSirkulasiBerkas(this,false);
        ktginventaris.tampil();
        ktginventaris.emptTeks();
        ktginventaris.isCek();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSirkulasiBerkasActionPerformed

    private void btnObatPasienRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObatPasienRalanActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBObatDokterRalan rbpoli=new DlgRBObatDokterRalan(this,false);
        rbpoli.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbpoli.setLocationRelativeTo(PanelUtama);
        rbpoli.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnObatPasienRalanActionPerformed

    private void btnObatPasienRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObatPasienRanapActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBObatDokterRanap rbpoli=new DlgRBObatDokterRanap(this,false);
        rbpoli.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbpoli.setLocationRelativeTo(PanelUtama);
        rbpoli.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnObatPasienRanapActionPerformed

    private void btnPemesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPemesananActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPemesanan pembelian=new DlgPemesanan(this,false);
        pembelian.isCek();
        pembelian.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        pembelian.setLocationRelativeTo(PanelUtama);
        pembelian.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPemesananActionPerformed

    private void btnPengeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPengeluaranActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPengeluaranHarian pembelian=new DlgPengeluaranHarian(this,false);
        pembelian.emptTeks();
        pembelian.isCek();        
        pembelian.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        pembelian.setLocationRelativeTo(PanelUtama);
        pembelian.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPengeluaranActionPerformed

    private void btnTambahanBiayaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahanBiayaActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDetailTambahan pembelian=new DlgDetailTambahan(this,false);
        pembelian.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        pembelian.setLocationRelativeTo(PanelUtama);
        pembelian.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTambahanBiayaActionPerformed

    private void btnPotonganBiayaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPotonganBiayaActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDetailPotongan pembelian=new DlgDetailPotongan(this,false);
        pembelian.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        pembelian.setLocationRelativeTo(PanelUtama);
        pembelian.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPotonganBiayaActionPerformed

    private void btnJMDetailDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJMDetailDokterActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDetailJMDokter rhtindakandokter=new DlgDetailJMDokter(this,false);
        rhtindakandokter.isCek();
        rhtindakandokter.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhtindakandokter.setLocationRelativeTo(PanelUtama);
        rhtindakandokter.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnJMDetailDokterActionPerformed

    private void btnIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIGDActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgIGD igd=new DlgIGD(this,false);
        igd.emptTeks();
        igd.isCek();
        igd.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        igd.setLocationRelativeTo(PanelUtama);
        igd.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnIGDActionPerformed

    private void btnToolRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToolRadActionPerformed
        isTutup();
        FlayMenu.removeAll();        
        FlayMenu.add(btnPermintaanRadiologi);
        FlayMenu.add(btnPeriksaRadiologi);
        if((var.getpermintaan_radiologi()==true)||(var.getperiksa_radiologi()==true)){
            btnPermintaanRadiologi.setEnabled(true);
        }else{
            btnPermintaanRadiologi.setEnabled(var.getpermintaan_radiologi());
        }
        btnPeriksaRadiologi.setEnabled(var.getperiksa_radiologi());
        FlayMenu.setVisible(true); 
    }//GEN-LAST:event_btnToolRadActionPerformed

    private void btnSetObatRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetObatRalanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetHargaObatRalan aplikasi=new DlgSetHargaObatRalan(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetObatRalanActionPerformed

    private void btnSetObatRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetObatRanapActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetHargaObatRanap aplikasi=new DlgSetHargaObatRanap(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetObatRanapActionPerformed

    private void btnPenyakitPD3IActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenyakitPD3IActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPenyakitPd3i aplikasi=new DlgPenyakitPd3i(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPenyakitPD3IActionPerformed

    private void btnSurveilansPD3IActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSurveilansPD3IActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDkkSurveilansPD3I aplikasi=new DlgDkkSurveilansPD3I(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSurveilansPD3IActionPerformed

    private void btnSurveilansRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSurveilansRalanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDkkSurveilansRalan aplikasi=new DlgDkkSurveilansRalan(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSurveilansRalanActionPerformed

    private void btnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosaActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.diagnosa.isCek();
        kasirralan.kamarinap.diagnosa.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        kasirralan.kamarinap.diagnosa.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.diagnosa.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDiagnosaActionPerformed

    private void btnSurveilansRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSurveilansRanapActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDkkSurveilansRanap aplikasi=new DlgDkkSurveilansRanap(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSurveilansRanapActionPerformed

    private void btnPnyTakMenularRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPnyTakMenularRanapActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDkkPenyakitTidakMenularRanap aplikasi=new DlgDkkPenyakitTidakMenularRanap(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPnyTakMenularRanapActionPerformed

    private void btnPnyTakMenularRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPnyTakMenularRalanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDkkPenyakitTidakMenularRalan aplikasi=new DlgDkkPenyakitTidakMenularRalan(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPnyTakMenularRalanActionPerformed

    private void btnKunjunganRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKunjunganRalanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgKunjunganRalan aplikasi=new DlgKunjunganRalan(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKunjunganRalanActionPerformed

    private void btnRl32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRl32ActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRl32 aplikasi=new DlgRl32(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRl32ActionPerformed

    private void btnRl33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRl33ActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRl33 aplikasi=new DlgRl33(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRl33ActionPerformed

    private void btnRl37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRl37ActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRl37 aplikasi=new DlgRl37(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRl37ActionPerformed

    private void btnRl38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRl38ActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRl38 aplikasi=new DlgRl38(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRl38ActionPerformed

    private void btnTagihanDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagihanDokterActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBTindakanDokter rbpoli=new DlgRBTindakanDokter(this,false);
        rbpoli.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbpoli.setLocationRelativeTo(PanelUtama);
        rbpoli.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTagihanDokterActionPerformed

    private void btnSidikJariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSidikJariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        DlgSidikJari sidikjari=new DlgSidikJari(this,false);
        sidikjari.tampil();
        sidikjari.isCek();
        sidikjari.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        sidikjari.setLocationRelativeTo(PanelUtama);
        sidikjari.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSidikJariActionPerformed

    private void btnJamPresensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJamPresensiActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        DlgJamMasuk jammasuk=new DlgJamMasuk(this,false);
        jammasuk.isCek();
        jammasuk.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        jammasuk.setLocationRelativeTo(PanelUtama);
        jammasuk.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnJamPresensiActionPerformed

    private void btnJadwalPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJadwalPegawaiActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        DlgJadwalPegawai jadwal=new DlgJadwalPegawai(this,false);
        jadwal.isCek();
        jadwal.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        jadwal.setLocationRelativeTo(PanelUtama);
        jadwal.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnJadwalPegawaiActionPerformed

    private void btnSetupNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetupNotaActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetNota aplikasi=new DlgSetNota(this,false);
        aplikasi.emptTeks();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetupNotaActionPerformed

    private void BtnDpjpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDpjpActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDpjp aplikasi=new DlgDpjp(this,false);
        aplikasi.isCek();
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnDpjpActionPerformed

    private void btnMutasiBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMutasiBarangActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgMutasiBarang aplikasi=new DlgMutasiBarang(this,false);
        aplikasi.tampilkanpermintaan=true;
        aplikasi.isCek();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnMutasiBarangActionPerformed

    private void btnRl34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRl34ActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgLaporanStokOpname aplikasi=new DlgLaporanStokOpname(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRl34ActionPerformed

    private void btnRl36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRl36ActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRl36 aplikasi=new DlgRl36(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRl36ActionPerformed

    private void btnfee_visit_dokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfee_visit_dokterActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFeeVisitDokter feevisitdokter=new DlgFeeVisitDokter(this,false);
        feevisitdokter.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        feevisitdokter.setLocationRelativeTo(PanelUtama);
        feevisitdokter.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnfee_visit_dokterActionPerformed

    private void btnfee_bacaan_ekgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfee_bacaan_ekgActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFeeBacaanEKG feebacaanekg=new DlgFeeBacaanEKG(this,false);
        feebacaanekg.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        feebacaanekg.setLocationRelativeTo(PanelUtama);
        feebacaanekg.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnfee_bacaan_ekgActionPerformed

    private void btnfee_rujukan_rontgenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfee_rujukan_rontgenActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFeeRujukanRontgen feerujukanrontgen=new DlgFeeRujukanRontgen(this,false);
        feerujukanrontgen.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        feerujukanrontgen.setLocationRelativeTo(PanelUtama);
        feerujukanrontgen.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnfee_rujukan_rontgenActionPerformed

    private void btnfee_rujukan_ranapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfee_rujukan_ranapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnfee_rujukan_ranapActionPerformed

    private void btnfee_ralanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfee_ralanActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFeePeriksaRalan feeperiksaralan=new DlgFeePeriksaRalan(this,false);
        feeperiksaralan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        feeperiksaralan.setLocationRelativeTo(PanelUtama);
        feeperiksaralan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnfee_ralanActionPerformed

    private void btnakun_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnakun_bayarActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgAkunBayar feeperiksaralan=new DlgAkunBayar(this,false);
        feeperiksaralan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        feeperiksaralan.setLocationRelativeTo(PanelUtama);
        feeperiksaralan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnakun_bayarActionPerformed

    private void btnbayar_pemesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbayar_pemesananActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBayarPemesanan bayarpesan=new DlgBayarPemesanan(this,false);
        bayarpesan.tampil();
        bayarpesan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        bayarpesan.setLocationRelativeTo(PanelUtama);
        bayarpesan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnbayar_pemesananActionPerformed

    private void btnObatPasienPeresepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObatPasienPeresepActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBObatDokterPeresep rbpoli=new DlgRBObatDokterPeresep(this,false);
        rbpoli.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbpoli.setLocationRelativeTo(PanelUtama);
        rbpoli.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnObatPasienPeresepActionPerformed

    private void btnJenisIpsrsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJenisIpsrsActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJenisIPSRS aplikasi=new DlgJenisIPSRS(this,false);
        aplikasi.isCek();
        aplikasi.emptTeks();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnJenisIpsrsActionPerformed

    private void btnPemasukanLainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPemasukanLainActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPemasukanLain aplikasi=new DlgPemasukanLain(this,false);
        aplikasi.isCek();
        aplikasi.emptTeks();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPemasukanLainActionPerformed

    private void btnPengaturanRekeningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPengaturanRekeningActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPengaturanRekening aplikasi=new DlgPengaturanRekening(this,false);
        aplikasi.isCek();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPengaturanRekeningActionPerformed

    private void btnJadwalTambahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJadwalTambahanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        DlgJadwalTambahan jadwal=new DlgJadwalTambahan(this,false);
        jadwal.isCek();
        jadwal.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        jadwal.setLocationRelativeTo(PanelUtama);
        jadwal.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnJadwalTambahanActionPerformed

    private void btnClosingKasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClosingKasirActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        DlgClosingKasir ckas=new DlgClosingKasir(this,false);
        ckas.isCek();
        ckas.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ckas.setLocationRelativeTo(PanelUtama);
        ckas.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnClosingKasirActionPerformed

    private void btnKeterlambatanPresensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeterlambatanPresensiActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetKeterlambatan keterlambatan=new DlgSetKeterlambatan(this,false);
        keterlambatan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        keterlambatan.setLocationRelativeTo(PanelUtama);
        keterlambatan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKeterlambatanPresensiActionPerformed

    private void btnSetHargaKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetHargaKamarActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetHargaKamar hargakamar=new DlgSetHargaKamar(this,false);
        hargakamar.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        hargakamar.setLocationRelativeTo(PanelUtama);
        hargakamar.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetHargaKamarActionPerformed

    private void btnRekapPershiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapPershiftActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRekapPerShift aplikasi=new DlgRekapPerShift(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekapPershiftActionPerformed

    private void btnCekBPJSNikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekBPJSNikActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekNIK2 form=new BPJSCekNIK2(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekBPJSNikActionPerformed

    private void btnCekBPJSKartuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekBPJSKartuActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekKartu form=new BPJSCekKartu(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekBPJSKartuActionPerformed

    private void btnCekBPJSRiwayatRujukanPCareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekBPJSRiwayatRujukanPCareActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekRiwayatRujukanPCare form=new BPJSCekRiwayatRujukanPCare(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekBPJSRiwayatRujukanPCareActionPerformed

    private void btnRekapPresensi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapPresensi2ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        DlgKehadiran2 hadir=new DlgKehadiran2(this,false);
        hadir.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        hadir.setLocationRelativeTo(PanelUtama);
        hadir.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekapPresensi2ActionPerformed

    private void btnObatPerCaraBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObatPerCaraBayarActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBObatPercaraBayar rbpoli=new DlgRBObatPercaraBayar(this,false);
        rbpoli.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbpoli.setLocationRelativeTo(PanelUtama);
        rbpoli.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnObatPerCaraBayarActionPerformed

    private void btnKunjunganRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKunjunganRanapActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgKunjunganRanap aplikasi=new DlgKunjunganRanap(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKunjunganRanapActionPerformed

    private void btnPaymentPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymentPointActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPaymentPoint aplikasi=new DlgPaymentPoint(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPaymentPointActionPerformed

    private void btnCekBPJSNomorRujukanPCareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekBPJSNomorRujukanPCareActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekNoRujukanPCare form=new BPJSCekNoRujukanPCare(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekBPJSNomorRujukanPCareActionPerformed

    private void btnICD9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnICD9ActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgICD9 aplikasi=new DlgICD9(this,false);
        aplikasi.emptTeks();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnICD9ActionPerformed

    private void btnDaruratStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaruratStokActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDaruratStok aplikasi=new DlgDaruratStok(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDaruratStokActionPerformed

    private void btnRetensiRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetensiRMActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRetensi retensi=new DlgRetensi(this,false);
        try {
            retensi.loadURL("http://" +koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"medrec/login.php?act=login&usere=admin&passwordte=akusayangsamakamu");                    
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        
        retensi.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        retensi.setLocationRelativeTo(PanelUtama);
        retensi.setVisible(true);        
        
        
        /*try {
            Valid.panggilUrl("medrec/login.php?act=login&usere=admin&passwordte=akusayangsamakamu");                       
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }  */
        
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRetensiRMActionPerformed

    private void btnTemporaryPresensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTemporaryPresensiActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgTemporaryPresensi temporary=new DlgTemporaryPresensi(this,false);
        temporary.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        temporary.setLocationRelativeTo(PanelUtama);
        temporary.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTemporaryPresensiActionPerformed

    private void btnJurnalHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJurnalHarianActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJurnalHarian jh=new DlgJurnalHarian(this,false);
        jh.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        jh.setLocationRelativeTo(PanelUtama);
        jh.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnJurnalHarianActionPerformed

    private void btnSirkulasi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSirkulasi2ActionPerformed
                
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSirkulasiBarang2 sirkulasi=new DlgSirkulasiBarang2(this,false);
        sirkulasi.isCek();
        sirkulasi.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        sirkulasi.setLocationRelativeTo(PanelUtama);
        sirkulasi.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSirkulasi2ActionPerformed

    private void btnCekBPJSDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekBPJSDiagnosaActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekReferensiPenyakit form=new BPJSCekReferensiPenyakit(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekBPJSDiagnosaActionPerformed

    private void btnCekBPJSPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekBPJSPoliActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekReferensiPoli form=new BPJSCekReferensiPoli(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekBPJSPoliActionPerformed

    private void btnIndustriFarmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndustriFarmasiActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgIndustriFarmasi suplier=new DlgIndustriFarmasi(this,false);
        suplier.isCek();
        suplier.emptTeks();
        suplier.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        suplier.setLocationRelativeTo(PanelUtama);
        suplier.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnIndustriFarmasiActionPerformed

    private void btnRHJasaSaranaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRHJasaSaranaActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHJS rhjs=new DlgRHJS(this,false);
        rhjs.isCek();
        rhjs.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhjs.setLocationRelativeTo(PanelUtama);
        rhjs.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRHJasaSaranaActionPerformed

    private void btnRBJasaSaranaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRBJasaSaranaActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBJS rbjs=new DlgRBJS(this,false);
        rbjs.isCek();
        rbjs.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbjs.setLocationRelativeTo(PanelUtama);
        rbjs.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRBJasaSaranaActionPerformed

    private void btnRHPaketBHPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRHPaketBHPActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHPaketBHP rhpaketbhp=new DlgRHPaketBHP(this,false);
        rhpaketbhp.isCek();
        rhpaketbhp.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhpaketbhp.setLocationRelativeTo(PanelUtama);
        rhpaketbhp.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRHPaketBHPActionPerformed

    private void btnRBPaketBHPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRBPaketBHPActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBPaketBHP rbpaketbhp=new DlgRBPaketBHP(this,false);
        rbpaketbhp.isCek();
        rbpaketbhp.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbpaketbhp.setLocationRelativeTo(PanelUtama);
        rbpaketbhp.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRBPaketBHPActionPerformed

    private void btnPiutangBelumLunasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPiutangBelumLunasActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPiutangBelumLunas rbpaketbhp=new DlgPiutangBelumLunas(this,false);
        rbpaketbhp.tampil();
        rbpaketbhp.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbpaketbhp.setLocationRelativeTo(PanelUtama);
        rbpaketbhp.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPiutangBelumLunasActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        Window[] wins = Window.getWindows();        
        for (Window win : wins) {
            if (win instanceof JDialog) {                
                DlgHome.setSize(PanelUtama.getWidth()-45, PanelUtama.getHeight()-45);
                Panelmenu.repaint();
                DlgHome.setLocationRelativeTo(PanelUtama);
            }
        }
    }//GEN-LAST:event_formComponentResized

    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
        Window[] wins = Window.getWindows();
        for (Window win : wins) {
            if (win instanceof JDialog) {
                win.setLocationRelativeTo(PanelUtama);
                win.toFront();
            }
        }
        
        setToolbar();
    }//GEN-LAST:event_formComponentMoved

    private void BtnToolJualObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnToolJualObatActionPerformed
        isTutup();
        FlayMenu.removeAll();        
        FlayMenu.add(btnInputPenjualan);
        FlayMenu.add(btnDataPenjualan);
        FlayMenu.add(btnDataPenyerahanDarah);
        FlayMenu.add(btnDaftarPermintaanResep);
        FlayMenu.add(btnResepObatDepan);
        //btnInputPenjualan.setEnabled(var.getpenjualan_obat());
        BtnToolJualObat.setEnabled(true);
        btnInputPenjualan.setEnabled(true);
        btnDataPenjualan.setEnabled(var.getpenjualan_obat());
        btnDataPenyerahanDarah.setEnabled(var.getutd_penyerahan_darah());
        btnDaftarPermintaanResep.setEnabled(var.getresep_dokter());
        btnResepObatDepan.setEnabled(var.getresep_obat());
        FlayMenu.setVisible(true);       
    }//GEN-LAST:event_BtnToolJualObatActionPerformed

    private void PanelWallMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWallMouseMoved
        setToolbar();
        Window[] wins = Window.getWindows();
        for (Window win : wins) {
            if (win instanceof JDialog) {
                win.setLocationRelativeTo(PanelUtama);
                win.toFront();
            }
        }        
        
    }//GEN-LAST:event_PanelWallMouseMoved

    private void btnCekBPJSFaskesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekBPJSFaskesActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekReferensiFaskes form=new BPJSCekReferensiFaskes(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekBPJSFaskesActionPerformed

    private void btnBPJSSEPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBPJSSEPActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSDataSEP form=new BPJSDataSEP(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBPJSSEPActionPerformed

    private void btnPengambilanUTDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPengambilanUTDActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPengambilanUTD form=new DlgPengambilanUTD(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPengambilanUTDActionPerformed

    private void btnTarifUtdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTarifUtdActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJnsPerawatanUTD tarifutd=new DlgJnsPerawatanUTD(this,false);
        tarifutd.emptTeks();
        tarifutd.isCek();
        tarifutd.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        tarifutd.setLocationRelativeTo(PanelUtama);
        tarifutd.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTarifUtdActionPerformed

    private void btnPengambilanUTD2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPengambilanUTD2ActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariPengambilanUTD form=new DlgCariPengambilanUTD(this,false);
        form.setHapus();
        form.tampil();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPengambilanUTD2ActionPerformed

    private void btnUTDMedisRusakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUTDMedisRusakActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        UTDMedisRusak form=new UTDMedisRusak(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnUTDMedisRusakActionPerformed

    private void btnPengambilanPenunjangUTDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPengambilanPenunjangUTDActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPengambilanPenunjangUTD form=new DlgPengambilanPenunjangUTD(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPengambilanPenunjangUTDActionPerformed

    private void btnPengambilanPenunjangUTD2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPengambilanPenunjangUTD2ActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariPengambilanPenunjangUTD form=new DlgCariPengambilanPenunjangUTD(this,false);
        form.setHapus();
        form.tampil();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPengambilanPenunjangUTD2ActionPerformed

    private void btnUTDPenunjangRusakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUTDPenunjangRusakActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        UTDPenunjangRusak form=new UTDPenunjangRusak(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnUTDPenunjangRusakActionPerformed

    private void btnSuplierIPSRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierIPSRSActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSuplierIPSRS suplier=new DlgSuplierIPSRS(this,false);
        suplier.isCek();
        suplier.emptTeks();
        suplier.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        suplier.setLocationRelativeTo(PanelUtama);
        suplier.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuplierIPSRSActionPerformed

    private void btnUTDDonorDarahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUTDDonorDarahActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        UTDDonor form=new UTDDonor(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnUTDDonorDarahActionPerformed

    private void btnMonitoringKlaimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMonitoringKlaimActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSMonitoringKlaim form=new BPJSMonitoringKlaim(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnMonitoringKlaimActionPerformed

    private void btnUTDCekalDarahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUTDCekalDarahActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        UTDCekalDarah form=new UTDCekalDarah(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnUTDCekalDarahActionPerformed

    private void btnUTDKomponenDarahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUTDKomponenDarahActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        UTDKomponenDarah form=new UTDKomponenDarah(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnUTDKomponenDarahActionPerformed

    private void btnUTDStokDarahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUTDStokDarahActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        UTDStokDarah form=new UTDStokDarah(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnUTDStokDarahActionPerformed

    private void btnUTDPemisahanDarahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUTDPemisahanDarahActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        UTDPemisahanDarah form=new UTDPemisahanDarah(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnUTDPemisahanDarahActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        setToolbar();
    }//GEN-LAST:event_formWindowOpened

    private void btnHarianKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHarianKamarActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBTindakanKamar rbpoli=new DlgRBTindakanKamar(this,false);
        rbpoli.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbpoli.setLocationRelativeTo(PanelUtama);
        rbpoli.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnHarianKamarActionPerformed

    private void btnRincianPiutangPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRincianPiutangPasienActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRincianPiutangPasien billing=new DlgRincianPiutangPasien(this,false);
        billing.tampil();
        billing.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        billing.setLocationRelativeTo(PanelUtama);
        billing.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRincianPiutangPasienActionPerformed

    private void btnKeuntunganObat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeuntunganObat2ActionPerformed
        
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgProyeksiBeriObat2 projul=new DlgProyeksiBeriObat2(this,false);
        projul.isCek();
        projul.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        projul.setLocationRelativeTo(PanelUtama);
        projul.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKeuntunganObat2ActionPerformed

    private void btnReklasifikasiRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReklasifikasiRalanActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        ReklasifikasiRalan billing=new ReklasifikasiRalan(this,false);
        billing.tampil();
        billing.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        billing.setLocationRelativeTo(PanelUtama);
        billing.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnReklasifikasiRalanActionPerformed

    private void btnReklasifikasiRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReklasifikasiRanapActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        ReklasifikasiRanap billing=new ReklasifikasiRanap(this,false);
        billing.tampil();
        billing.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        billing.setLocationRelativeTo(PanelUtama);
        billing.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnReklasifikasiRanapActionPerformed

    private void btnUTDPenyerahanDarahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUTDPenyerahanDarahActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        UTDPenyerahanDarah form=new UTDPenyerahanDarah(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnUTDPenyerahanDarahActionPerformed

    private void btnHutangObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHutangObatActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgHutangObatBelumLunas form=new DlgHutangObatBelumLunas(this,false);
        form.tampil();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnHutangObatActionPerformed

    private void btnRiwayatBarangMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiwayatBarangMedisActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRiwayatBarangMedis sirkulasi=new DlgRiwayatBarangMedis(this,false);
        sirkulasi.isCek();
        sirkulasi.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        sirkulasi.setLocationRelativeTo(PanelUtama);
        sirkulasi.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRiwayatBarangMedisActionPerformed

    private void btnInputPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInputPenjualanActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPenjualan penjualan=new DlgPenjualan(this,false);
        penjualan.isCek();
        penjualan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        penjualan.setLocationRelativeTo(PanelUtama);
        penjualan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnInputPenjualanActionPerformed

    private void btnDataPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataPenjualanActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariPenjualan penjualan=new DlgCariPenjualan(this,false);
        penjualan.emptTeks();
        penjualan.isCek();
        penjualan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        penjualan.setLocationRelativeTo(PanelUtama);
        penjualan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDataPenjualanActionPerformed

    private void btnDataPenyerahanDarahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataPenyerahanDarahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        UTDCariPenyerahanDarah carijual=new UTDCariPenyerahanDarah(null,false);
        carijual.emptTeks();
        carijual.isCek();
        carijual.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        carijual.setLocationRelativeTo(PanelUtama);
        carijual.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDataPenyerahanDarahActionPerformed

    private void btnSensusHarianPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSensusHarianPoliActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSensusHarianPoli aplikasi=new DlgSensusHarianPoli(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSensusHarianPoliActionPerformed

    private void btnRl4aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRl4aActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRL4A aplikasi=new DlgRL4A(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRl4aActionPerformed

    private void btnAplicareReferensiKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicareReferensiKamarActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        AplicareCekReferensiKamar form=new AplicareCekReferensiKamar(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnAplicareReferensiKamarActionPerformed

    private void btnAplicareKetersediaanKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicareKetersediaanKamarActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        AplicareKetersediaanKamar form=new AplicareKetersediaanKamar(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnAplicareKetersediaanKamarActionPerformed

    private void btnInaCBGKlaimBaruOtomatisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInaCBGKlaimBaruOtomatisActionPerformed
        if(var.getkode().equals("Admin Utama")){
            pilihpage="KlaimBaruOtomatis";
            judulform="::[ Otomatisasi Klaim Pasien Baru Dari Data SEP Ke INACBG ]::";
            isTutup();
            DlgHome.dispose();
            cariNIK.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
            cariNIK.setLocationRelativeTo(PanelUtama);
            cariNIK.setVisible(true);
        }else{
            coder_nik=Sequel.cariIsi("select no_ik from inacbg_coder_nik where nik=?",var.getkode());
            if(!coder_nik.equals("")){
                isTutup();
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    inacbgklaim.loadURL("http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"inacbg/login.php?act=login&usere=admin&passwordte=akusayangsamakamu&page=KlaimBaruOtomatis&codernik="+coder_nik);                    
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                }

                inacbgklaim.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
                inacbgklaim.setLocationRelativeTo(PanelUtama);
                inacbgklaim.setJudul("::[ Otomatisasi Klaim Pasien Baru Dari Data SEP Ke INACBG ]::");
                inacbgklaim.setVisible(true);        
                DlgHome.dispose();
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                isTutup();
                DlgHome.dispose();
                JOptionPane.showMessageDialog(null,"Coder NIK tidak ditemukan, silahkan hubungi Admin Utama..!!");
            }            
        }
    }//GEN-LAST:event_btnInaCBGKlaimBaruOtomatisActionPerformed

    private void btnInaCBGKlaimBaruManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInaCBGKlaimBaruManualActionPerformed
        if(var.getkode().equals("Admin Utama")){
            pilihpage="KlaimBaruManual";
            judulform="::[ Klaim Manual Pasien Baru Dari Data SEP Ke INACBG ]::";
            isTutup();
            DlgHome.dispose();
            cariNIK.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
            cariNIK.setLocationRelativeTo(PanelUtama);
            cariNIK.setVisible(true);
        }else{
            coder_nik=Sequel.cariIsi("select no_ik from inacbg_coder_nik where nik=?",var.getkode());
            if(!coder_nik.equals("")){
                isTutup();
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    inacbgklaim.loadURL("http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"inacbg/login.php?act=login&usere=admin&passwordte=akusayangsamakamu&page=KlaimBaruManual&codernik="+coder_nik);                    
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                }

                inacbgklaim.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
                inacbgklaim.setLocationRelativeTo(PanelUtama);
                inacbgklaim.setJudul("::[ Klaim Manual Pasien Baru Dari Data SEP Ke INACBG ]::");
                inacbgklaim.setVisible(true);        
                DlgHome.dispose();
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                isTutup();
                DlgHome.dispose();
                JOptionPane.showMessageDialog(null,"Coder NIK tidak ditemukan, silahkan hubungi Admin Utama..!!");
            }            
        }
    }//GEN-LAST:event_btnInaCBGKlaimBaruManualActionPerformed

    private void btnInaCBGCoderNIKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInaCBGCoderNIKActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        INACBGCoderNIK inacbg=new INACBGCoderNIK(this,false);
        inacbg.emptTeks();
        inacbg.isCek();
        inacbg.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        inacbg.setLocationRelativeTo(PanelUtama);
        inacbg.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnInaCBGCoderNIKActionPerformed

    private void btnMutasiBerkasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMutasiBerkasActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgMutasiBerkas mutasiberkas=new DlgMutasiBerkas(this,false);
        mutasiberkas.setJudul("::[ Mutasi Berkas Rekam Medis ]::","monitoringberkas/pages");
        try {
            mutasiberkas.loadURL("http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"monitoringberkas/login.php?act=login&usere=admin&passwordte=akusayangsamakamu");                    
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }

        mutasiberkas.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        mutasiberkas.setLocationRelativeTo(PanelUtama);        
        mutasiberkas.setVisible(true);        
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnMutasiBerkasActionPerformed

    private void btnAkunPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAkunPiutangActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgAkunPiutang form=new DlgAkunPiutang(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnAkunPiutangActionPerformed

    private void btnRHKSOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRHKSOActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHKSO rhkso=new DlgRHKSO(this,false);
        rhkso.isCek();
        rhkso.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhkso.setLocationRelativeTo(PanelUtama);
        rhkso.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRHKSOActionPerformed

    private void btnRBKSOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRBKSOActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBKSO rbkso=new DlgRBKSO(this,false);
        rbkso.isCek();
        rbkso.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbkso.setLocationRelativeTo(PanelUtama);
        rbkso.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRBKSOActionPerformed

    private void btnRHMenejemenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRHMenejemenActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHMenejemen rhmenejemen=new DlgRHMenejemen(this,false);
        rhmenejemen.isCek();
        rhmenejemen.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhmenejemen.setLocationRelativeTo(PanelUtama);
        rhmenejemen.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRHMenejemenActionPerformed

    private void btnRBMenejemenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRBMenejemenActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBMenejemen rbmenejemen=new DlgRBMenejemen(this,false);
        rbmenejemen.isCek();
        rbmenejemen.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbmenejemen.setLocationRelativeTo(PanelUtama);
        rbmenejemen.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRBMenejemenActionPerformed

    private void btnCekEligibilitasInhealthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekEligibilitasInhealthActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InhealthCekEligibilitas form=new InhealthCekEligibilitas(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekEligibilitasInhealthActionPerformed

    private void btnReferensiKamarInhealthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReferensiKamarInhealthActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InhealthReferensiJenpelRuang form=new InhealthReferensiJenpelRuang(this,false);
        form.emptTeks();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnReferensiKamarInhealthActionPerformed

    private void btnCekInhealthPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekInhealthPoliActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InhealthCekReferensiPoli form=new InhealthCekReferensiPoli(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekInhealthPoliActionPerformed

    private void btnCekInhealthFaskesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekInhealthFaskesActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InhealthCekReferensiFaskes form=new InhealthCekReferensiFaskes(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekInhealthFaskesActionPerformed

    private void btnInhealthSJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInhealthSJPActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InhealthDataSJP form=new InhealthDataSJP(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnInhealthSJPActionPerformed

    private void btnPiutangRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPiutangRalanActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPiutangRalan billing=new DlgPiutangRalan(this,false);
        billing.tampil();
        billing.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        billing.setLocationRelativeTo(PanelUtama);
        billing.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPiutangRalanActionPerformed

    private void btnPiutangRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPiutangRanapActionPerformed
                
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPiutangRanap billing=new DlgPiutangRanap(this,false);
        billing.tampil();
        billing.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        billing.setLocationRelativeTo(PanelUtama);
        billing.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPiutangRanapActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        if(ChkInput.isSelected()==true){
            cmbMenu.setVisible(true);
            cmbMenu.requestFocus();
            TCari.setVisible(false);
        }else{
            cmbMenu.setVisible(false);
            TCari.setVisible(true);
            TCari.requestFocus();            
        }
        isTampil();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isTampil();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void btnPiutangPerCaraBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPiutangPerCaraBayarActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPiutangPercaraBayar rbpaketbhp=new DlgPiutangPercaraBayar(this,false);
        rbpaketbhp.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbpaketbhp.setLocationRelativeTo(PanelUtama);
        rbpaketbhp.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPiutangPerCaraBayarActionPerformed

    private void btnLamaPelayananRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamaPelayananRalanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPelayananRalan aplikasi=new DlgPelayananRalan(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLamaPelayananRalanActionPerformed

    private void btnCatatanPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCatatanPasienActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgLhtCatatanPasien aplikasi=new DlgLhtCatatanPasien(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCatatanPasienActionPerformed

    private void btnRl4bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRl4bActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRL4B aplikasi=new DlgRL4B(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRl4bActionPerformed

    private void btnRl4asebabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRl4asebabActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRL4ASebab aplikasi=new DlgRL4ASebab(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRl4asebabActionPerformed

    private void btnRl4bsebabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRl4bsebabActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRL4BSebab aplikasi=new DlgRL4BSebab(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRl4bsebabActionPerformed

    private void btnDataHAIsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataHAIsActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDataHAIs aplikasi=new DlgDataHAIs(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.isCek();
        aplikasi.tampil();
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDataHAIsActionPerformed

    private void btnHarianHAIsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHarianHAIsActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgHarianHAIs aplikasi=new DlgHarianHAIs(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnHarianHAIsActionPerformed

    private void btnBulananHAIsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBulananHAIsActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBulananHAIs aplikasi=new DlgBulananHAIs(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBulananHAIsActionPerformed

    private void btnHitungBorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungBorActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgHitungBOR aplikasi=new DlgHitungBOR(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnHitungBorActionPerformed

    private void btnPerusahaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerusahaanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPerusahaan aplikasi=new DlgPerusahaan(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.isCek();
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
        
    }//GEN-LAST:event_btnPerusahaanActionPerformed

    private void btnDaftarPermintaanResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaftarPermintaanResepActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDaftarPermintaanResep daftar=new DlgDaftarPermintaanResep(null,false);
        daftar.emptTeks();
        daftar.isCek();
        daftar.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        daftar.setLocationRelativeTo(PanelUtama);
        daftar.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDaftarPermintaanResepActionPerformed

    private void btnLamaPelayananApotekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamaPelayananApotekActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPelayananApotek aplikasi=new DlgPelayananApotek(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLamaPelayananApotekActionPerformed

    private void btnHitungAlosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungAlosActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgHitungALOS aplikasi=new DlgHitungALOS(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnHitungAlosActionPerformed

    private void btnDetailTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailTindakanActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDetailTindakan dettin=new DlgDetailTindakan(this,false);
        dettin.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        dettin.setLocationRelativeTo(PanelUtama);
        dettin.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDetailTindakanActionPerformed

    private void btnRekapPoliAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapPoliAnakActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRekapPoliAnak aplikasi=new DlgRekapPoliAnak(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekapPoliAnakActionPerformed

    private void btnBerkasDigitalPerawatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBerkasDigitalPerawatanActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBerkasRawat berkas=new DlgBerkasRawat(this,false);
        berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
        try {
            berkas.loadURL("http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"berkasrawat/login.php?act=login&usere=admin&passwordte=akusayangsamakamu");                    
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }

        berkas.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        berkas.setLocationRelativeTo(PanelUtama);        
        berkas.setVisible(true);        
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBerkasDigitalPerawatanActionPerformed

    private void btnPnyMenularRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPnyMenularRanapActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDkkPenyakitMenularRanap aplikasi=new DlgDkkPenyakitMenularRanap(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPnyMenularRanapActionPerformed

    private void btnPnyMenularRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPnyMenularRalanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDkkPenyakitMenularRalan aplikasi=new DlgDkkPenyakitMenularRalan(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPnyMenularRalanActionPerformed

    private void btnKategoriBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriBarangActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgKategori form=new DlgKategori(this,false);
        form.isCek();
        form.emptTeks();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKategoriBarangActionPerformed

    private void btnGolonganBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGolonganBarangActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgGolongan form=new DlgGolongan(this,false);
        form.isCek();
        form.emptTeks();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnGolonganBarangActionPerformed

    private void btnObatPerTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObatPerTanggalActionPerformed
        isTutup();
        DlgObatPerTanggal form=new DlgObatPerTanggal(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnObatPerTanggalActionPerformed

    private void btnPenjualanPerTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenjualanPerTanggalActionPerformed
        isTutup();
        DlgPenjualanPerTanggal form=new DlgPenjualanPerTanggal(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPenjualanPerTanggalActionPerformed

    private void btnPembatalanPeriksaDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPembatalanPeriksaDokterActionPerformed
        isTutup();
        DlgPembatalanPeriksaPerDokter form=new DlgPembatalanPeriksaPerDokter(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPembatalanPeriksaDokterActionPerformed

    private void btnPembayaranPerUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPembayaranPerUnitActionPerformed
        isTutup();
        DlgPembayaranPerPoli form=new DlgPembayaranPerPoli(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPembayaranPerUnitActionPerformed

    private void btnRekapPembayaranPerUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapPembayaranPerUnitActionPerformed
        isTutup();
        DlgRekapPembayaranPerPoli form=new DlgRekapPembayaranPerPoli(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekapPembayaranPerUnitActionPerformed

    private void btnPengadaanIPSRSPerTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPengadaanIPSRSPerTanggalActionPerformed
        isTutup();
        DlgPengadaanIPSRSPerTanggal form=new DlgPengadaanIPSRSPerTanggal(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPengadaanIPSRSPerTanggalActionPerformed

    private void btnStokKeluarIPSRSPerTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStokKeluarIPSRSPerTanggalActionPerformed
        isTutup();
        DlgStokKeluarIPSRSPerTanggal form=new DlgStokKeluarIPSRSPerTanggal(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnStokKeluarIPSRSPerTanggalActionPerformed

    private void btnCekEntryRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekEntryRalanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCekEntryRalan aplikasi=new DlgCekEntryRalan(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekEntryRalanActionPerformed

    private void btnInaCBGKlaimBaruManual2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInaCBGKlaimBaruManual2ActionPerformed
        if(var.getkode().equals("Admin Utama")){
            pilihpage="KlaimBaruManual2";
            judulform="::[ Klaim Manual Ke INACBG ]::";
            isTutup();
            DlgHome.dispose();
            cariNIK.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
            cariNIK.setLocationRelativeTo(PanelUtama);
            cariNIK.setVisible(true);
        }else{
            coder_nik=Sequel.cariIsi("select no_ik from inacbg_coder_nik where nik=?",var.getkode());
            if(!coder_nik.equals("")){
                isTutup();
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    inacbgklaim.loadURL("http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"inacbg/login.php?act=login&usere=admin&passwordte=akusayangsamakamu&page=KlaimBaruManual2&codernik="+coder_nik);                    
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                }

                inacbgklaim.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
                inacbgklaim.setLocationRelativeTo(PanelUtama);
                inacbgklaim.setJudul("::[ Klaim Manual Ke INACBG ]::");
                inacbgklaim.setVisible(true);        
                DlgHome.dispose();
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                isTutup();
                DlgHome.dispose();
                JOptionPane.showMessageDialog(null,"Coder NIK tidak ditemukan, silahkan hubungi Admin Utama..!!");
            }            
        }
    }//GEN-LAST:event_btnInaCBGKlaimBaruManual2ActionPerformed

    private void btnPermintaanMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPermintaanMedisActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPermintaan form=new DlgPermintaan(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPermintaanMedisActionPerformed

    private void btnRekapPermintaanMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapPermintaanMedisActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRekapPermintaan rhipsrs=new DlgRekapPermintaan(this,false);
        rhipsrs.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhipsrs.setLocationRelativeTo(PanelUtama);
        rhipsrs.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekapPermintaanMedisActionPerformed

    private void btnSuratPemesananMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuratPemesananMedisActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSuratPemesanan form=new DlgSuratPemesanan(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuratPemesananMedisActionPerformed

    private void btnPermintaanNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPermintaanNonMedisActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPermintaanNonMedis form=new DlgPermintaanNonMedis(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPermintaanNonMedisActionPerformed

    private void btnRekapPermintaanNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapPermintaanNonMedisActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRekapPermintaanNonMedis rhipsrs=new DlgRekapPermintaanNonMedis(this,false);
        rhipsrs.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhipsrs.setLocationRelativeTo(PanelUtama);
        rhipsrs.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekapPermintaanNonMedisActionPerformed

    private void btnSuratPemesananNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuratPemesananNonMedisActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSuratPemesananNonMedis form=new DlgSuratPemesananNonMedis(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuratPemesananNonMedisActionPerformed

    private void btnCekReferensiProsedurBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekReferensiProsedurBPJSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekReferensiProsedur form=new BPJSCekReferensiProsedur(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekReferensiProsedurBPJSActionPerformed

    private void btnCekReferensiKelasRawatBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekReferensiKelasRawatBPJSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekReferensiKelasRawat form=new BPJSCekReferensiKelasRawat(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekReferensiKelasRawatBPJSActionPerformed

    private void btnCekReferensiDokterBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekReferensiDokterBPJSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekReferensiDokter form=new BPJSCekReferensiDokter(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekReferensiDokterBPJSActionPerformed

    private void btnCekReferensiSpesialistikBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekReferensiSpesialistikBPJSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekReferensiSpesialistik form=new BPJSCekReferensiSpesialistik(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekReferensiSpesialistikBPJSActionPerformed

    private void btnCekReferensiRuangRawatBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekReferensiRuangRawatBPJSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekReferensiRuangRawat form=new BPJSCekReferensiRuangRawat(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekReferensiRuangRawatBPJSActionPerformed

    private void btnCekReferensiCaraKeluarBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekReferensiCaraKeluarBPJSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekReferensiCaraKeluar form=new BPJSCekReferensiCaraKeluar(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekReferensiCaraKeluarBPJSActionPerformed

    private void btnCekReferensiPascaPulangBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekReferensiPascaPulangBPJSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekReferensiPascaPulang form=new BPJSCekReferensiPascaPulang(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekReferensiPascaPulangBPJSActionPerformed

    private void btnDetailVKOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailVKOKActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDetailVKOK dettin=new DlgDetailVKOK(this,false);
        dettin.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        dettin.setLocationRelativeTo(PanelUtama);
        dettin.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDetailVKOKActionPerformed

    private void btnCekBPJSNomorRujukanRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekBPJSNomorRujukanRSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekNoRujukanRS form=new BPJSCekNoRujukanRS(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekBPJSNomorRujukanRSActionPerformed

    private void btnCekBPJSRujukanKartuPCareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekBPJSRujukanKartuPCareActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekRujukanKartuPCare form=new BPJSCekRujukanKartuPCare(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekBPJSRujukanKartuPCareActionPerformed

    private void btnCekBPJSRujukanKartuRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekBPJSRujukanKartuRSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekRujukanKartuRS form=new BPJSCekRujukanKartuRS(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekBPJSRujukanKartuRSActionPerformed

    private void btnRujukanKeluarBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRujukanKeluarBPJSActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSRujukanKeluar rujuk=new BPJSRujukanKeluar(this,false);
        rujuk.tampil();
        rujuk.emptTeks();
        rujuk.isCek();
        rujuk.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rujuk.setLocationRelativeTo(PanelUtama);
        rujuk.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRujukanKeluarBPJSActionPerformed

    private void btnStokKeluarMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStokKeluarMedisActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPengeluaranApotek pengeluaran=new DlgPengeluaranApotek(this,false);
        pengeluaran.tampilkanpermintaan=true;
        pengeluaran.isCek();
        pengeluaran.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        pengeluaran.setLocationRelativeTo(PanelUtama);
        pengeluaran.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnStokKeluarMedisActionPerformed

    private void btnJMDetailDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJMDetailDokter2ActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDetailJMDokter2 rhtindakandokter=new DlgDetailJMDokter2(this,false);
        rhtindakandokter.isCek();
        rhtindakandokter.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhtindakandokter.setLocationRelativeTo(PanelUtama);
        rhtindakandokter.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnJMDetailDokter2ActionPerformed

    private void btnPengaduanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPengaduanActionPerformed
        frmPengaduan pengaduan=new frmPengaduan();
        pengaduan.setSize(850,650);
        pengaduan.setLocationRelativeTo(null);
        pengaduan.isCek();
        pengaduan.setVisible(true);
        DlgHome.dispose();
    }//GEN-LAST:event_btnPengaduanActionPerformed

    private void btnSensusHarianRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSensusHarianRalanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSensusHarianRalan aplikasi=new DlgSensusHarianRalan(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSensusHarianRalanActionPerformed

    private void btnMetodeRacikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMetodeRacikActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgMetodeRacik racik=new DlgMetodeRacik(this,false);
        racik.isCek();
        racik.emptTeks();
        racik.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        racik.setLocationRelativeTo(PanelUtama);
        racik.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnMetodeRacikActionPerformed

    private void btnPembayaranAkunBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPembayaranAkunBayarActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPembayaranPerAKunBayar aplikasi=new DlgPembayaranPerAKunBayar(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPembayaranAkunBayarActionPerformed

    private void btnResepObatDepanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResepObatDepanActionPerformed
        btnResepObatActionPerformed(evt);
    }//GEN-LAST:event_btnResepObatDepanActionPerformed

    private void btnPenggunaObatResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenggunaObatResepActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPenggunaObat penggunaobat=new DlgPenggunaObat(this,false);
        penggunaobat.isCek();
        penggunaobat.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        penggunaobat.setLocationRelativeTo(PanelUtama);
        penggunaobat.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPenggunaObatResepActionPerformed

    private void btnRekapPenerimaanObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapPenerimaanObatActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRekapPenerimaan form=new DlgRekapPenerimaan(null,false);
        form.emptTeks();    
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekapPenerimaanObatActionPerformed

    private void btnMasterBerkasPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasterBerkasPegawaiActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgMasterBerkasPegawai form=new DlgMasterBerkasPegawai(null,false);
        form.emptTeks();        
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnMasterBerkasPegawaiActionPerformed

    private void btnBerkasPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBerkasPegawaiActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBerkasKepegawaian form=new DlgBerkasKepegawaian(null,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBerkasPegawaiActionPerformed

    private void btnRiwayatJabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiwayatJabatanActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRiwayatJabatan form=new DlgRiwayatJabatan(this,false);
        try {
            form.loadURL("http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"penggajian/loginriwayatjabatan.php?act=login&usere=admin&passwordte=akusayangsamakamu");                    
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }

        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);        
        form.setVisible(true);        
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRiwayatJabatanActionPerformed

    private void btnRiwayatPendidikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiwayatPendidikanActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRiwayatPendidikan form=new DlgRiwayatPendidikan(this,false);
        try {
            form.loadURL("http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"penggajian/loginriwayatpendidikan.php?act=login&usere=admin&passwordte=akusayangsamakamu");                    
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }

        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);        
        form.setVisible(true);        
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRiwayatPendidikanActionPerformed

    private void btnRiwayatNaikGajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiwayatNaikGajiActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRiwayatNaikGaji form=new DlgRiwayatNaikGaji(this,false);
        try {
            form.loadURL("http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"penggajian/loginriwayatgaji.php?act=login&usere=admin&passwordte=akusayangsamakamu");                    
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }

        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);        
        form.setVisible(true);        
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRiwayatNaikGajiActionPerformed

    private void btnKegiatanIlmiahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKegiatanIlmiahActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgKegiatanIlmiah form=new DlgKegiatanIlmiah(this,false);
        try {
            form.loadURL("http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"penggajian/loginriwayatseminar.php?act=login&usere=admin&passwordte=akusayangsamakamu");                    
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }

        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);        
        form.setVisible(true);        
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKegiatanIlmiahActionPerformed

    private void btnRiwayatPenghargaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiwayatPenghargaanActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRiwayatPenghargaan form=new DlgRiwayatPenghargaan(this,false);
        try {
            form.loadURL("http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"penggajian/loginriwayatpenghargaan.php?act=login&usere=admin&passwordte=akusayangsamakamu");                    
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }

        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);        
        form.setVisible(true);        
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRiwayatPenghargaanActionPerformed

    private void btnRiwayatPenelitianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiwayatPenelitianActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRiwayatPenelitian form=new DlgRiwayatPenelitian(this,false);
        try {
            form.loadURL("http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"penggajian/loginriwayatpenelitian.php?act=login&usere=admin&passwordte=akusayangsamakamu");                    
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }

        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);        
        form.setVisible(true);        
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRiwayatPenelitianActionPerformed

    private void btnPenerimaanNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenerimaanNonMedisActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPemesananIPSRS pembelian=new DlgPemesananIPSRS(this,false);
        pembelian.isCek();
        pembelian.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        pembelian.setLocationRelativeTo(PanelUtama);
        pembelian.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPenerimaanNonMedisActionPerformed

    private void btnBayarPesanNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarPesanNonMedisActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBayarPemesananNonMedis bayarpesan=new DlgBayarPemesananNonMedis(this,false);
        bayarpesan.tampil();
        bayarpesan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        bayarpesan.setLocationRelativeTo(PanelUtama);
        bayarpesan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBayarPesanNonMedisActionPerformed

    private void btnHutangNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHutangNonMedisActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgHutangNonMedisBelumLunas form=new DlgHutangNonMedisBelumLunas(this,false);
        form.tampil();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnHutangNonMedisActionPerformed

    private void btnRekapPenerimaanNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapPenerimaanNonMedisActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRekapPenerimaanNonMedis form=new DlgRekapPenerimaanNonMedis(null,false);
        form.emptTeks();    
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekapPenerimaanNonMedisActionPerformed

    private void btnInsidenKeselamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsidenKeselamatanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgInsidenKeselamatan aplikasi=new DlgInsidenKeselamatan(this,false);
        aplikasi.emptTeks();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnInsidenKeselamatanActionPerformed

    private void btnInsidenKeselamatanPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsidenKeselamatanPasienActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDataInsidenKeselamatan aplikasi=new DlgDataInsidenKeselamatan(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.isCek();
        aplikasi.tampil();
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnInsidenKeselamatanPasienActionPerformed

    private void btnRiwayatBatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiwayatBatchActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRiwayatBatch aplikasi=new DlgRiwayatBatch(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRiwayatBatchActionPerformed

    private void btnPiutangPerAkunPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPiutangPerAkunPiutangActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPiutangPerAKunPiutang aplikasi=new DlgPiutangPerAKunPiutang(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPiutangPerAkunPiutangActionPerformed

    private void btnSukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSukuActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        DlgSuku form=new DlgSuku(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSukuActionPerformed

    private void btnBahasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBahasaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        DlgBahasa form=new DlgBahasa(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBahasaActionPerformed

    private void btnCacatFisikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCacatFisikActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        DlgCacatFisik form=new DlgCacatFisik(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCacatFisikActionPerformed

    private void btnJadwalOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJadwalOperasiActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBookingOperasi form=new DlgBookingOperasi(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnJadwalOperasiActionPerformed

    private void btnMapingPoliBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapingPoliBPJSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSMapingPoli form=new BPJSMapingPoli(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnMapingPoliBPJSActionPerformed

    private void btnBarangCSSDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangCSSDActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InventarisBarangCSSD form=new InventarisBarangCSSD(this,false);
        form.emptTeks();
        form.tampil();
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBarangCSSDActionPerformed

    private void btnSKDPBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSKDPBPJSActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSKDPBPJS form=new DlgSKDPBPJS(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSKDPBPJSActionPerformed

    private void btnBookingRegistrasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookingRegistrasiActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBookingRegistrasi form=new DlgBookingRegistrasi(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBookingRegistrasiActionPerformed

    private void btnCekReferensiPropinsiBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekReferensiPropinsiBPJSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekReferensiPropinsi form=new BPJSCekReferensiPropinsi(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekReferensiPropinsiBPJSActionPerformed

    private void btnCekReferensiKabupatenBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekReferensiKabupatenBPJSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekReferensiKabupaten form=new BPJSCekReferensiKabupaten(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekReferensiKabupatenBPJSActionPerformed

    private void btnCekReferensiKecamatanBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekReferensiKecamatanBPJSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekReferensiKecamatan form=new BPJSCekReferensiKecamatan(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekReferensiKecamatanBPJSActionPerformed

    private void btnCekReferensiDokterDPJPBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekReferensiDokterDPJPBPJSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekReferensiDokterDPJP form=new BPJSCekReferensiDokterDPJP(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekReferensiDokterDPJPBPJSActionPerformed

    private void btnCekBPJSRiwayatRujukanRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekBPJSRiwayatRujukanRSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekRiwayatRujukanRS form=new BPJSCekRiwayatRujukanRS(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekBPJSRiwayatRujukanRSActionPerformed

    private void btnCekBPJSTanggalRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekBPJSTanggalRujukanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekTanggalRujukan form=new BPJSCekTanggalRujukan(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekBPJSTanggalRujukanActionPerformed

    private void btnPermintaanLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPermintaanLabActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariPermintaanLab form=new DlgCariPermintaanLab(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPermintaanLabActionPerformed

    private void btnPermintaanRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPermintaanRadiologiActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariPermintaanRadiologi form=new DlgCariPermintaanRadiologi(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPermintaanRadiologiActionPerformed

    private void btnSuratIndeksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuratIndeksActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        SuratIndeks form=new SuratIndeks(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuratIndeksActionPerformed

    private void btnSuratMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuratMapActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        SuratMap form=new SuratMap(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuratMapActionPerformed

    private void btnSuratAlmariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuratAlmariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        SuratAlmari form=new SuratAlmari(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuratAlmariActionPerformed

    private void btnSuratRakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuratRakActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        SuratRak form=new SuratRak(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuratRakActionPerformed

    private void btnSuratRuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuratRuangActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        SuratRuang form=new SuratRuang(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuratRuangActionPerformed

    private void btnSuratKlasifikasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuratKlasifikasiActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        SuratKlasifikasi form=new SuratKlasifikasi(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuratKlasifikasiActionPerformed

    private void btnSuratStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuratStatusActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        SuratStatus form=new SuratStatus(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuratStatusActionPerformed

    private void btnSuratSifatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuratSifatActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        SuratSifat form=new SuratSifat(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuratSifatActionPerformed

    private void btnSuratBalasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuratBalasActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        SuratBalas form=new SuratBalas(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuratBalasActionPerformed

    private void btnSuratMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuratMasukActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SuratMasuk form=new SuratMasuk(null,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuratMasukActionPerformed

    private void btnSirkulasi3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSirkulasi3ActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSirkulasiBarang3 sirkulasi=new DlgSirkulasiBarang3(this,false);
        sirkulasi.isCek();
        sirkulasi.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        sirkulasi.setLocationRelativeTo(PanelUtama);
        sirkulasi.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSirkulasi3ActionPerformed

    private void btnRanapPerRuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRanapPerRuangActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        DlgRanapPerRuang form=new DlgRanapPerRuang(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRanapPerRuangActionPerformed

    private void btnPenyakitRanapCaraBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenyakitRanapCaraBayarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        isTutup();
        DlgPenyakitRanapPerCaraBayar form=new DlgPenyakitRanapPerCaraBayar(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPenyakitRanapCaraBayarActionPerformed

    private void btnSetInputParsialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetInputParsialActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetInputParsial form=new DlgSetInputParsial(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetInputParsialActionPerformed

    private void btnLamaPelayananRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamaPelayananRadiologiActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPelayananRadiologi aplikasi=new DlgPelayananRadiologi(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLamaPelayananRadiologiActionPerformed

    private void btnLamaPelayananLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamaPelayananLabActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPelayananLab aplikasi=new DlgPelayananLab(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLamaPelayananLabActionPerformed

    private void btnCekSEPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekSEPActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekDetailSEP2 detail=new BPJSCekDetailSEP2(null,true);
        detail.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        detail.setLocationRelativeTo(PanelUtama);
        detail.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekSEPActionPerformed

    private void btnSuratKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuratKeluarActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SuratKeluar form=new SuratKeluar(null,false);
        form.emptTeks();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuratKeluarActionPerformed

    private void btnKegiatanFarmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKegiatanFarmasiActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgKegiatanFarmasi aplikasi=new DlgKegiatanFarmasi(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKegiatanFarmasiActionPerformed

    private void btnOpnameIPSRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpnameIPSRSActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgInputStokIPSRS opname=new DlgInputStokIPSRS(this,false);
        opname.tampil();
        opname.isCek();
        opname.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        opname.setLocationRelativeTo(PanelUtama);
        opname.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnOpnameIPSRSActionPerformed

    private void btnSirkulasiNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSirkulasiNonMedisActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSirkulasiNonMedis sirkulasi=new DlgSirkulasiNonMedis(this,false);
        sirkulasi.isCek();
        sirkulasi.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        sirkulasi.setLocationRelativeTo(PanelUtama);
        sirkulasi.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSirkulasiNonMedisActionPerformed

    private void btnRekapLabPerTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapLabPerTahunActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRekapLabPerTahun aplikasi=new DlgRekapLabPerTahun(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekapLabPerTahunActionPerformed

    private void btnPerujukLabPerTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerujukLabPerTahunActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPerujukLabPerTahun aplikasi=new DlgPerujukLabPerTahun(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPerujukLabPerTahunActionPerformed

    private void btnRekapRadiologiPerTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapRadiologiPerTahunActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRekapRadiologiPerTahun aplikasi=new DlgRekapRadiologiPerTahun(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekapRadiologiPerTahunActionPerformed

    private void btnPerujukRadiologiPerTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerujukRadiologiPerTahunActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPerujukRadiologiPerTahun aplikasi=new DlgPerujukRadiologiPerTahun(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPerujukRadiologiPerTahunActionPerformed

    private void btnJumlahPorsiDietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJumlahPorsiDietActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJumlahPorsiDiet aplikasi=new DlgJumlahPorsiDiet(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnJumlahPorsiDietActionPerformed

    private void btnJumlahMacamDietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJumlahMacamDietActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJumlahMacamDiet aplikasi=new DlgJumlahMacamDiet(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnJumlahMacamDietActionPerformed

    private void btnPaymentPoint2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymentPoint2ActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPaymentPoint2 aplikasi=new DlgPaymentPoint2(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPaymentPoint2ActionPerformed

    private void btnPembayaranAkunBayar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPembayaranAkunBayar2ActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPembayaranPerAKunBayar2 aplikasi=new DlgPembayaranPerAKunBayar2(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPembayaranAkunBayar2ActionPerformed

    private void btnHAIsBangsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHAIsBangsalActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgHAIsPerBangsal aplikasi=new DlgHAIsPerBangsal(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnHAIsBangsalActionPerformed

    private void btnPPNObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPPNObatActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariPPNObat aplikasi=new DlgCariPPNObat(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPPNObatActionPerformed

    private void btnSaldoAkunPerBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaldoAkunPerBulanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSaldoAkunPerBulan aplikasi=new DlgSaldoAkunPerBulan(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSaldoAkunPerBulanActionPerformed

    private void btnDisplayApotekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayApotekActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRunTeksApotek runteks=new DlgRunTeksApotek(this,false);
        runteks.emptTeks();
        runteks.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        runteks.setLocationRelativeTo(PanelUtama);
        runteks.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDisplayApotekActionPerformed

    private void btnCekSisruteFaskesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekSisruteFaskesActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SisruteCekReferensiFaskes form=new SisruteCekReferensiFaskes(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekSisruteFaskesActionPerformed

    private void btnCekSisruteAlasanRujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekSisruteAlasanRujukActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SisruteCekReferensiAlasanRujuk form=new SisruteCekReferensiAlasanRujuk(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekSisruteAlasanRujukActionPerformed

    private void btnCekSisruteDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekSisruteDiagnosaActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SisruteCekReferensiDiagnosa form=new SisruteCekReferensiDiagnosa(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekSisruteDiagnosaActionPerformed

    private void btnRujukanMasukSisruteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRujukanMasukSisruteActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SisruteRujukanMasukan form=new SisruteRujukanMasukan(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRujukanMasukSisruteActionPerformed

    private void btnRujukanKeluarSisruteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRujukanKeluarSisruteActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SisruteRujukanKeluar form=new SisruteRujukanKeluar(this,false);
        form.isCek();
        form.tutupInput();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRujukanKeluarSisruteActionPerformed

    private void btnCekBPJSSKDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekBPJSSKDPActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSCekSKDP form=new BPJSCekSKDP(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCekBPJSSKDPActionPerformed

    private void btnDataBatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataBatchActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDataBatch form=new DlgDataBatch(this,false);
        form.isCek();
        form.emptTeks();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDataBatchActionPerformed

    private void btnKunjunganLabRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKunjunganLabRalanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgKunjunganLabRalan aplikasi=new DlgKunjunganLabRalan(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKunjunganLabRalanActionPerformed

    private void btnKunjunganLabRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKunjunganLabRanapActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgKunjunganLabRanap aplikasi=new DlgKunjunganLabRanap(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKunjunganLabRanapActionPerformed

    private void btnKunjunganRadRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKunjunganRadRalanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgKunjunganRadRalan aplikasi=new DlgKunjunganRadRalan(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKunjunganRadRalanActionPerformed

    private void btnKunjunganRadRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKunjunganRadRanapActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgKunjunganRadRanap aplikasi=new DlgKunjunganRadRanap(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKunjunganRadRanapActionPerformed

    private void btnPembayaranAkunBayar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPembayaranAkunBayar3ActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPembayaranPerAKunBayar3 aplikasi=new DlgPembayaranPerAKunBayar3(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPembayaranAkunBayar3ActionPerformed

    private void btnPasswordAsuransiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasswordAsuransiActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPasswordAsuransi form=new DlgPasswordAsuransi(this,false);
        form.emptTeks();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPasswordAsuransiActionPerformed

    private void btnDataSITTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataSITTActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgDataTB aplikasi=new DlgDataTB(this,false);
        aplikasi.emptTeks();
        aplikasi.isCek();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDataSITTActionPerformed

    private void btnSiranapKetersediaanKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiranapKetersediaanKamarActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SiranapKetersediaanKamar form=new SiranapKetersediaanKamar(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSiranapKetersediaanKamarActionPerformed

    private void btnKadaluarsaBatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKadaluarsaBatchActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgKadaluarsaBatch form=new DlgKadaluarsaBatch(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKadaluarsaBatchActionPerformed

    private void btnSisaStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSisaStokActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSisaStok form=new DlgSisaStok(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSisaStokActionPerformed

    private void btnObatPerResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObatPerResepActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgObatPeresep form=new DlgObatPeresep(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnObatPerResepActionPerformed

    private void btnPemakaianAirPDAMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPemakaianAirPDAMActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        KeslingPemakaiaanAirPDAM form=new KeslingPemakaiaanAirPDAM(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPemakaianAirPDAMActionPerformed

    private void btnLimbahB3MedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimbahB3MedisActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        KeslingLimbahB3Medis form=new KeslingLimbahB3Medis(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLimbahB3MedisActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
    
    }//GEN-LAST:event_formWindowActivated

    private void btn10ObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn10ObatActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Dlg10ObatTerbanyakPoli form=new Dlg10ObatTerbanyakPoli(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btn10ObatActionPerformed

    private void btnRl31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRl31ActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRl31 aplikasi=new DlgRl31(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRl31ActionPerformed

    private void MnLoginBtnLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLoginBtnLogActionPerformed
        try{
            //            com.sun.awt.AWTUtilities.setWindowOpacity(DlgLogin,0.7f);
        }catch(Exception e){
        }
        FlayMenu.setVisible(false);

        switch (BtnLog.getText().trim()) {
            case "Log Out":
            BtnToolReg.setEnabled(false);
            BtnToolKamnap.setEnabled(false);
            BtnToolKasir.setEnabled(false);
            btnToolIGD.setEnabled(false);
            MnGantiPassword.setEnabled(false);
            var.setpenjualan_obatfalse();
            var.setpenjualan_obatfalse();
            var.setutd_penyerahan_darahfalse();
            var.setresep_dokterfalse();
            var.setresep_obatfalse();
            var.setpermintaanlabfalse();
            var.setperiksalabfalse();
            var.setpermintaanradiologifalse();
            var.setperiksaradiologifalse();
            edAdmin.setText("");
            edPwd.setText("");
            BtnLog.setText("Log In");
            MnLogin.setText("Log In");
            lblStts.setText("Status Admin : ");
            lblUser.setText("Log Out");
            BtnMenu.setEnabled(false);
            isTutup();
            break;
            case "Log In":
            DlgLogin.setVisible(true);
            edAdmin.requestFocus();
            break;
        }
    }//GEN-LAST:event_MnLoginBtnLogActionPerformed

    private void MnGantiPasswordBtnLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGantiPasswordBtnLogActionPerformed
        isTutup();
        PassLama.setText("");
        Passbaru1.setText("");
        PassBaru2.setText("");
        WindowInput.setVisible(true);
    }//GEN-LAST:event_MnGantiPasswordBtnLogActionPerformed

    private void MenuKeluarBtnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuKeluarBtnCloseActionPerformed
        isTutup();
        DlgHome.dispose();
        int jawab=JOptionPane.showConfirmDialog(null, "Yakin anda mau keluar dari program ini ????","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(jawab==JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_MenuKeluarBtnCloseActionPerformed

    private void MnBelumDatang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBelumDatang1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Valid.panggilUrl("presensi");
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnBelumDatang1ActionPerformed

    private void MnBelumDatangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBelumDatangActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        isTutup();
        DlgBelum belum=new DlgBelum(this,false);
        belum.tampil();
        belum.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        belum.setLocationRelativeTo(PanelUtama);
        belum.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnBelumDatangActionPerformed

    private void MnSudahPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSudahPulangActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        isTutup();
        DlgPulang pulang=new DlgPulang(this,false);
        pulang.tampil();
        pulang.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        pulang.setLocationRelativeTo(PanelUtama);
        pulang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnSudahPulangActionPerformed

    private void MnRekapBulananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapBulananActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        isTutup();
        DlgBulanan2 rekapbulanan2=new DlgBulanan2(this,false);
        rekapbulanan2.tampil();
        rekapbulanan2.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        rekapbulanan2.setLocationRelativeTo(PanelUtama);
        rekapbulanan2.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnRekapBulananActionPerformed

    private void MnRekapHadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHadirActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        isTutup();
        DlgKehadiran hadir=new DlgKehadiran(this,false);
        hadir.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        hadir.setLocationRelativeTo(PanelUtama);
        hadir.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnRekapHadirActionPerformed

    private void MnRekapBulanan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapBulanan1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        DlgHome.dispose();
        informasikamar.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        informasikamar.setLocationRelativeTo(PanelUtama);
        informasikamar.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnRekapBulanan1ActionPerformed

    private void MnRekapBulanan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapBulanan3ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        DlgHome.dispose();
        InformasiTelusurKunjunganPasien belum=new InformasiTelusurKunjunganPasien(this,true);
        belum.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        belum.setLocationRelativeTo(PanelUtama);
        belum.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnRekapBulanan3ActionPerformed

    private void MnRekapBulanan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapBulanan2ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgHome.dispose();
        analisakamin.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        analisakamin.setLocationRelativeTo(PanelUtama);
        analisakamin.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnRekapBulanan2ActionPerformed

    private void MnSudahPulang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSudahPulang1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        DlgHome.dispose();
        InformasiKamar belum=new InformasiKamar(this,true);
        belum.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        belum.setLocationRelativeTo(PanelUtama);
        belum.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnSudahPulang1ActionPerformed

    private void MnSudahPulang3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSudahPulang3ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        DlgHome.dispose();
        InformasiTarifLab belum=new InformasiTarifLab(this,true);
        belum.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        belum.setLocationRelativeTo(PanelUtama);
        belum.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnSudahPulang3ActionPerformed

    private void MnSudahPulang6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSudahPulang6ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        DlgHome.dispose();
        InformasiTarifRadiologi belum=new InformasiTarifRadiologi(this,false);
        belum.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        belum.setLocationRelativeTo(PanelUtama);
        belum.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnSudahPulang6ActionPerformed

    private void MnSudahPulang4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSudahPulang4ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        DlgHome.dispose();
        InformasiTarifOperasi belum=new InformasiTarifOperasi(this,true);
        belum.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        belum.setLocationRelativeTo(PanelUtama);
        belum.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnSudahPulang4ActionPerformed

    private void MnSudahPulang2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSudahPulang2ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        DlgHome.dispose();
        InformasiTarifRalan belum=new InformasiTarifRalan(this,true);
        belum.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        belum.setLocationRelativeTo(PanelUtama);
        belum.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnSudahPulang2ActionPerformed

    private void MnSudahPulang5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSudahPulang5ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgHome.dispose();
        InformasiTarifRanap belum=new InformasiTarifRanap(this,true);
        belum.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        belum.setLocationRelativeTo(PanelUtama);
        belum.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnSudahPulang5ActionPerformed

    private void MnRekapHadir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHadir1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgHome.dispose();
        InformasiJadwal belum=new InformasiJadwal(this,true);
        belum.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        belum.setLocationRelativeTo(PanelUtama);
        belum.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnRekapHadir1ActionPerformed

    private void MnInfoBedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInfoBedActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Valid.panggilUrl("bed3.php");
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnInfoBedActionPerformed

    private void MnInfoBed2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInfoBed2ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Valid.panggilUrl("bed4.php");
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnInfoBed2ActionPerformed

    private void MnInfoBed1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInfoBed1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Valid.panggilUrl("jadwaldokter2.php");
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnInfoBed1ActionPerformed

    private void MnInfoBed3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInfoBed3ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Valid.panggilUrl("bed5.php");
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnInfoBed3ActionPerformed

    private void MnInfoBed4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInfoBed4ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Valid.panggilUrl("poli.php");
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnInfoBed4ActionPerformed

    private void MnInfoBed5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInfoBed5ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Valid.panggilUrl("jadwaloperasi.php");
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnInfoBed5ActionPerformed

    private void MnAnjunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnAnjunganActionPerformed
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Runtime.getRuntime().exec("java -jar anjunganmandiri.jar");
            this.setCursor(Cursor.getDefaultCursor());
        } catch (Exception e) {
            System.out.print("Notifikasi : "+e);
        }
    }//GEN-LAST:event_MnAnjunganActionPerformed

    private void MnRekapHadir3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHadir3ActionPerformed
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Runtime.getRuntime().exec("java -jar antrianloket.jar");
            this.setCursor(Cursor.getDefaultCursor());
        } catch (Exception e) {
            System.out.print("Notifikasi : "+e);
        }
    }//GEN-LAST:event_MnRekapHadir3ActionPerformed

    private void MnRekapHadir4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHadir4ActionPerformed
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Runtime.getRuntime().exec("java -jar antrianpoli.jar");
            this.setCursor(Cursor.getDefaultCursor());
        } catch (Exception e) {
            System.out.print("Notifikasi : "+e);
        }
    }//GEN-LAST:event_MnRekapHadir4ActionPerformed

    private void MnRekapHadir5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHadir5ActionPerformed
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Runtime.getRuntime().exec("java -jar antrianapotek.jar");
            this.setCursor(Cursor.getDefaultCursor());
        } catch (Exception e) {
            System.out.print("Notifikasi : "+e);
        }
    }//GEN-LAST:event_MnRekapHadir5ActionPerformed

    private void MnRekapHadir6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHadir6ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Valid.panggilUrl("berkasrawat");
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnRekapHadir6ActionPerformed

    private void jMenu4MenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenu4MenuSelected
        isTutup();
        About.setSize(PanelWall.getWidth(), PanelWall.getHeight());
        About.setLocationRelativeTo(PanelWall);
        About.setVisible(true);
        DlgHome.dispose();
    }//GEN-LAST:event_jMenu4MenuSelected

    private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MouseClicked
        isTutup();
        About.setSize(PanelWall.getWidth(), PanelWall.getHeight());
        About.setLocationRelativeTo(PanelWall);
        About.setVisible(true);
        DlgHome.dispose();
    }//GEN-LAST:event_jMenu4MouseClicked

    private void btnHibahObatBHPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHibahObatBHPActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        HibahObatBHP pembelian=new HibahObatBHP(this,false);
        pembelian.isCek();
        pembelian.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        pembelian.setLocationRelativeTo(PanelUtama);
        pembelian.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnHibahObatBHPActionPerformed

    private void btnRl35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRl35ActionPerformed
        // TODO add your handling code here:
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRl35 aplikasi=new DlgRl35(this,false);
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRl35ActionPerformed

    private void btnLapStokOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLapStokOpActionPerformed
        // TODO add your handling code here:
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgLaporanStokOpname aplikasi=new DlgLaporanStokOpname(this,false);
//        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLapStokOpActionPerformed

    private void btnMapingDokterDPJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapingDokterDPJPActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSMapingDokterDPJP form=new BPJSMapingDokterDPJP(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnMapingDokterDPJPActionPerformed

    private void btnLapPelFarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLapPelFarActionPerformed
        // TODO add your handling code here:
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgLapelfar aplikasi=new DlgLapelfar(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLapPelFarActionPerformed

    private void btnFrekuensiRanapPerDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFrekuensiRanapPerDokterActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFrekuensiPenyakitRanapPerDokter ktginventaris=new DlgFrekuensiPenyakitRanapPerDokter(this,false);
        ktginventaris.isCek();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnFrekuensiRanapPerDokterActionPerformed

    private void btnPelayananPenunjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPelayananPenunjangActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPelayananPenunjang aplikasi=new DlgPelayananPenunjang(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPelayananPenunjangActionPerformed

    private void btnMonitoringKunjunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMonitoringKunjunganActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSMonitoringKunjungan form=new BPJSMonitoringKunjungan(this,false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnMonitoringKunjunganActionPerformed

    private void btnFrekuensiRalanDanRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFrekuensiRalanDanRanapActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFrekuensiPenyakitRalanDanRanap ktginventaris=new DlgFrekuensiPenyakitRalanDanRanap(this,false);
        ktginventaris.isCek();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnFrekuensiRalanDanRanapActionPerformed

    private void btnPelFarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPelFarActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPelayananFarmasi aplikasi=new DlgPelayananFarmasi(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPelFarActionPerformed

    private void btnKerohanianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKerohanianActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InformasiKerohanian aplikasi=new InformasiKerohanian(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKerohanianActionPerformed

    private void btnMyLimsMappingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMyLimsMappingActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MyLimsMapping aplikasi=new MyLimsMapping(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnMyLimsMappingActionPerformed

    private void btnRekapDietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapDietActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRekapDiet aplikasi=new DlgRekapDiet(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekapDietActionPerformed

    private void btnSetPenjabBNMActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetPenjabBNMActionPerformed1
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetPenjabBNM aplikasi=new DlgSetPenjabBNM(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetPenjabBNMActionPerformed1

    private void btnUTDPermintaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUTDPermintaanActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariPermintaanUTD aplikasi=new DlgCariPermintaanUTD(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnUTDPermintaanActionPerformed

    private void btnSetSubAkunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetSubAkunActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPengaturanSubRekening aplikasi=new DlgPengaturanSubRekening(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetSubAkunActionPerformed

    private void btnFrekuensiPenyakitRanapBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFrekuensiPenyakitRanapBaruActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFrekuensiPenyakitRanapBaru aplikasi=new DlgFrekuensiPenyakitRanapBaru(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnFrekuensiPenyakitRanapBaruActionPerformed

    private void btnFrekuensiPerPerujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFrekuensiPerPerujukActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFrekuensiPenyakitPerRujukMasuk aplikasi=new DlgFrekuensiPenyakitPerRujukMasuk(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnFrekuensiPerPerujukActionPerformed

    private void btnPaketObatOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaketObatOpActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPaketOperasi aplikasi=new DlgPaketOperasi(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPaketObatOpActionPerformed

    private void btnPermintaanMPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPermintaanMPPActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSkriningMPP aplikasi=new DlgSkriningMPP(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPermintaanMPPActionPerformed

    private void btnSirkulasiObat4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSirkulasiObat4ActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSirkulasiBarang4 sirkulasi=new DlgSirkulasiBarang4(this,false);
        sirkulasi.isCek();
        sirkulasi.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        sirkulasi.setLocationRelativeTo(PanelUtama);
        sirkulasi.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSirkulasiObat4ActionPerformed

    private void btnLapRBAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLapRBAActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgLapRBA aplikasi=new DlgLapRBA(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLapRBAActionPerformed

    private void btnBookingMJKNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookingMJKNActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBookingMJKN form=new DlgBookingMJKN(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBookingMJKNActionPerformed

    private void btnSetKompUtdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetKompUtdActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetUtd aplikasi=new DlgSetUtd(this,false);
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetKompUtdActionPerformed

    private void btnRekapKegiatanRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekapKegiatanRadActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRekapKegiatanRad form=new DlgRekapKegiatanRad(this,false);
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekapKegiatanRadActionPerformed

    private void btnPermintaanRBAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPermintaanRBAActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPermintaanRBA form = new DlgPermintaanRBA(this, false);
        form.tampil();
        form.isCek();
        form.emptTeks();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPermintaanRBAActionPerformed

    private void btnSetHariLiburActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetHariLiburActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetHariLibur form = new DlgSetHariLibur(this, false);
//        form.emptTeks();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetHariLiburActionPerformed

    private void btnTrialSendWAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrialSendWAActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgTrialWhatsapp user = new DlgTrialWhatsapp(this, false);
        user.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        user.setLocationRelativeTo(PanelUtama);
        user.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTrialSendWAActionPerformed

    private void btnSetPaketMCUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetPaketMCUActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetMCU form = new DlgSetMCU(this, false);
//        form.emptTeks();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetPaketMCUActionPerformed

    private void btnLapKunjunganPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLapKunjunganPasienActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJumlahPasien form = new DlgJumlahPasien(this, false);
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLapKunjunganPasienActionPerformed

    private void btnPindahRiwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPindahRiwActionPerformed
        isTutup();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPindahRiwPasien form = new DlgPindahRiwPasien(this, false);
//        form.emptTeks();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPindahRiwActionPerformed

    private void btnSurkonBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSurkonBPJSActionPerformed
        isTutup();
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSSuratKontrol form=new BPJSSuratKontrol(this,false);
        form.isCek();
        form.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSurkonBPJSActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new frmUtama().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnCancel;
    private widget.ButtonBig BtnClose;
    private widget.Button BtnClosePass;
    private widget.ButtonBig BtnDpjp;
    private widget.ButtonBig BtnJadwal;
    private widget.ButtonBig BtnLog;
    private widget.Button BtnLogin;
    private widget.ButtonBig BtnMenu;
    private widget.Button BtnSimpanPass;
    private widget.ButtonBig BtnToolJualObat;
    private widget.ButtonBig BtnToolKamnap;
    private widget.ButtonBig BtnToolKasir;
    private widget.ButtonBig BtnToolReg;
    private widget.CekBox ChkInput;
    private javax.swing.JDialog DlgHome;
    private javax.swing.JDialog DlgLogin;
    private usu.widget.glass.PanelGlass FlayMenu;
    private widget.MenuBar MenuBar;
    private javax.swing.JMenuItem MenuKeluar;
    private javax.swing.JMenuItem MnAnjungan;
    private javax.swing.JMenuItem MnBelumDatang;
    private javax.swing.JMenuItem MnBelumDatang1;
    private javax.swing.JMenuItem MnGantiPassword;
    private javax.swing.JMenuItem MnInfoBed;
    private javax.swing.JMenuItem MnInfoBed1;
    private javax.swing.JMenuItem MnInfoBed2;
    private javax.swing.JMenuItem MnInfoBed3;
    private javax.swing.JMenuItem MnInfoBed4;
    private javax.swing.JMenuItem MnInfoBed5;
    private javax.swing.JMenuItem MnLogin;
    private javax.swing.JMenuItem MnRekapBulanan;
    private javax.swing.JMenuItem MnRekapBulanan1;
    private javax.swing.JMenuItem MnRekapBulanan2;
    private javax.swing.JMenuItem MnRekapBulanan3;
    private javax.swing.JMenuItem MnRekapHadir;
    private javax.swing.JMenuItem MnRekapHadir1;
    private javax.swing.JMenuItem MnRekapHadir3;
    private javax.swing.JMenuItem MnRekapHadir4;
    private javax.swing.JMenuItem MnRekapHadir5;
    private javax.swing.JMenuItem MnRekapHadir6;
    private javax.swing.JMenuItem MnSudahPulang;
    private javax.swing.JMenuItem MnSudahPulang1;
    private javax.swing.JMenuItem MnSudahPulang2;
    private javax.swing.JMenuItem MnSudahPulang3;
    private javax.swing.JMenuItem MnSudahPulang4;
    private javax.swing.JMenuItem MnSudahPulang5;
    private javax.swing.JMenuItem MnSudahPulang6;
    private javax.swing.JPanel PanelUtama;
    private usu.widget.glass.PanelGlass PanelWall;
    private widget.panelGlass Panelmenu;
    private widget.TextBox PassBaru2;
    private widget.TextBox PassLama;
    private widget.TextBox Passbaru1;
    private widget.TextBox TCari;
    private javax.swing.JDialog WindowInput;
    private widget.ButtonBig btn10Obat;
    private widget.ButtonBig btnAdmin;
    private widget.ButtonBig btnAkunPiutang;
    private widget.ButtonBig btnAnalisaKamar;
    private widget.ButtonBig btnAplicareKetersediaanKamar;
    private widget.ButtonBig btnAplicareReferensiKamar;
    private widget.ButtonBig btnBPJSSEP;
    private widget.ButtonBig btnBahasa;
    private widget.ButtonBig btnBarangCSSD;
    private widget.ButtonBig btnBarangInventaris;
    private widget.ButtonBig btnBarangIpsrs;
    private widget.ButtonBig btnBarcode;
    private widget.ButtonBig btnBayarPesanNonMedis;
    private widget.ButtonBig btnBayarPiutang;
    private widget.ButtonBig btnBeriObat;
    private widget.ButtonBig btnBerkasDigitalPerawatan;
    private widget.ButtonBig btnBerkasPegawai;
    private widget.ButtonBig btnBookingMJKN;
    private widget.ButtonBig btnBookingRegistrasi;
    private widget.ButtonBig btnBubes;
    private widget.ButtonBig btnBulananHAIs;
    private widget.ButtonBig btnCacatFisik;
    private widget.ButtonBig btnCashFlow;
    private widget.ButtonBig btnCatatanPasien;
    private widget.ButtonBig btnCekBPJSDiagnosa;
    private widget.ButtonBig btnCekBPJSFaskes;
    private widget.ButtonBig btnCekBPJSKartu;
    private widget.ButtonBig btnCekBPJSNik;
    private widget.ButtonBig btnCekBPJSNomorRujukanPCare;
    private widget.ButtonBig btnCekBPJSNomorRujukanRS;
    private widget.ButtonBig btnCekBPJSPoli;
    private widget.ButtonBig btnCekBPJSRiwayatRujukanPCare;
    private widget.ButtonBig btnCekBPJSRiwayatRujukanRS;
    private widget.ButtonBig btnCekBPJSRujukanKartuPCare;
    private widget.ButtonBig btnCekBPJSRujukanKartuRS;
    private widget.ButtonBig btnCekBPJSSKDP;
    private widget.ButtonBig btnCekBPJSTanggalRujukan;
    private widget.ButtonBig btnCekEligibilitasInhealth;
    private widget.ButtonBig btnCekEntryRalan;
    private widget.ButtonBig btnCekInhealthFaskes;
    private widget.ButtonBig btnCekInhealthPoli;
    private widget.ButtonBig btnCekReferensiCaraKeluarBPJS;
    private widget.ButtonBig btnCekReferensiDokterBPJS;
    private widget.ButtonBig btnCekReferensiDokterDPJPBPJS;
    private widget.ButtonBig btnCekReferensiKabupatenBPJS;
    private widget.ButtonBig btnCekReferensiKecamatanBPJS;
    private widget.ButtonBig btnCekReferensiKelasRawatBPJS;
    private widget.ButtonBig btnCekReferensiPascaPulangBPJS;
    private widget.ButtonBig btnCekReferensiPropinsiBPJS;
    private widget.ButtonBig btnCekReferensiProsedurBPJS;
    private widget.ButtonBig btnCekReferensiRuangRawatBPJS;
    private widget.ButtonBig btnCekReferensiSpesialistikBPJS;
    private widget.ButtonBig btnCekSEP;
    private widget.ButtonBig btnCekSisruteAlasanRujuk;
    private widget.ButtonBig btnCekSisruteDiagnosa;
    private widget.ButtonBig btnCekSisruteFaskes;
    private widget.ButtonBig btnClosingKasir;
    private widget.ButtonBig btnDaftarPermintaanResep;
    private widget.ButtonBig btnDaruratStok;
    private widget.ButtonBig btnDataBatch;
    private widget.ButtonBig btnDataHAIs;
    private widget.ButtonBig btnDataPenjualan;
    private widget.ButtonBig btnDataPenyerahanDarah;
    private widget.ButtonBig btnDataSITT;
    private widget.ButtonBig btnDeposit;
    private widget.ButtonBig btnDetailTindakan;
    private widget.ButtonBig btnDetailVKOK;
    private widget.ButtonBig btnDiagnosa;
    private widget.ButtonBig btnDiet;
    private widget.ButtonBig btnDisplay;
    private widget.ButtonBig btnDisplayApotek;
    private widget.ButtonBig btnDokter;
    private widget.ButtonBig btnFrekuensiPenyakitRanapBaru;
    private widget.ButtonBig btnFrekuensiPerPerujuk;
    private widget.ButtonBig btnFrekuensiRalan;
    private widget.ButtonBig btnFrekuensiRalanDanRanap;
    private widget.ButtonBig btnFrekuensiRanap;
    private widget.ButtonBig btnFrekuensiRanapPerDokter;
    private widget.ButtonBig btnGolonganBarang;
    private widget.ButtonBig btnHAIsBangsal;
    private widget.ButtonBig btnHarianHAIs;
    private widget.ButtonBig btnHarianKamar;
    private widget.ButtonBig btnHibahObatBHP;
    private widget.ButtonBig btnHitungAlos;
    private widget.ButtonBig btnHitungBor;
    private widget.ButtonBig btnHutangNonMedis;
    private widget.ButtonBig btnHutangObat;
    private widget.ButtonBig btnICD;
    private widget.ButtonBig btnICD9;
    private widget.ButtonBig btnIGD;
    private widget.ButtonBig btnInaCBGCoderNIK;
    private widget.ButtonBig btnInaCBGKlaimBaruManual;
    private widget.ButtonBig btnInaCBGKlaimBaruManual2;
    private widget.ButtonBig btnInaCBGKlaimBaruOtomatis;
    private widget.ButtonBig btnIndustriFarmasi;
    private widget.ButtonBig btnInhealthSJP;
    private widget.ButtonBig btnInputPenjualan;
    private widget.ButtonBig btnInsidenKeselamatan;
    private widget.ButtonBig btnInsidenKeselamatanPasien;
    private widget.ButtonBig btnInventaris;
    private widget.ButtonBig btnJMDetailDokter;
    private widget.ButtonBig btnJMDetailDokter2;
    private widget.ButtonBig btnJadwalOperasi;
    private widget.ButtonBig btnJadwalPegawai;
    private widget.ButtonBig btnJadwalTambahan;
    private widget.ButtonBig btnJamPresensi;
    private widget.ButtonBig btnJenisInventaris;
    private widget.ButtonBig btnJenisIpsrs;
    private widget.ButtonBig btnJnsBarang;
    private widget.ButtonBig btnJumlahMacamDiet;
    private widget.ButtonBig btnJumlahPorsiDiet;
    private widget.ButtonBig btnJurnalHarian;
    private widget.ButtonBig btnKadaluarsaBatch;
    private widget.ButtonBig btnKamar;
    private widget.ButtonBig btnKamarInap;
    private widget.ButtonBig btnKasir;
    private widget.ButtonBig btnKategoriBarang;
    private widget.ButtonBig btnKategoriInventaris;
    private widget.ButtonBig btnKegiatanFarmasi;
    private widget.ButtonBig btnKegiatanIlmiah;
    private widget.ButtonBig btnKerohanian;
    private widget.ButtonBig btnKeterlambatanPresensi;
    private widget.ButtonBig btnKeuntungan;
    private widget.ButtonBig btnKeuntunganObat2;
    private widget.ButtonBig btnKeuntunganObatRanap;
    private widget.ButtonBig btnKonversi;
    private widget.ButtonBig btnKunjunganLabRalan;
    private widget.ButtonBig btnKunjunganLabRanap;
    private widget.ButtonBig btnKunjunganRadRalan;
    private widget.ButtonBig btnKunjunganRadRanap;
    private widget.ButtonBig btnKunjunganRalan;
    private widget.ButtonBig btnKunjunganRanap;
    private widget.ButtonBig btnLabaRugi;
    private widget.ButtonBig btnLaboratorium;
    private widget.ButtonBig btnLahir;
    private widget.ButtonBig btnLamaPelayananApotek;
    private widget.ButtonBig btnLamaPelayananLab;
    private widget.ButtonBig btnLamaPelayananRadiologi;
    private widget.ButtonBig btnLamaPelayananRalan;
    private widget.ButtonBig btnLapKunjunganPasien;
    private widget.ButtonBig btnLapPelFar;
    private widget.ButtonBig btnLapRBA;
    private widget.ButtonBig btnLapStokOp;
    private widget.ButtonBig btnLihatPiutang;
    private widget.ButtonBig btnLimbahB3Medis;
    private widget.ButtonBig btnMapingDokterDPJP;
    private widget.ButtonBig btnMapingPoliBPJS;
    private widget.ButtonBig btnMasterBerkasPegawai;
    private widget.ButtonBig btnMerkInventaris;
    private widget.ButtonBig btnMetodeRacik;
    private widget.ButtonBig btnMonitoringKlaim;
    private widget.ButtonBig btnMonitoringKunjungan;
    private widget.ButtonBig btnMutasiBarang;
    private widget.ButtonBig btnMutasiBerkas;
    private widget.ButtonBig btnMyLimsMapping;
    private widget.ButtonBig btnObat;
    private widget.ButtonBig btnObatPasienPeresep;
    private widget.ButtonBig btnObatPasienRalan;
    private widget.ButtonBig btnObatPasienRanap;
    private widget.ButtonBig btnObatPenyakit;
    private widget.ButtonBig btnObatPerCaraBayar;
    private widget.ButtonBig btnObatPerResep;
    private widget.ButtonBig btnObatPerTanggal;
    private widget.ButtonBig btnOpname;
    private widget.ButtonBig btnOpnameIPSRS;
    private widget.ButtonBig btnPPNObat;
    private widget.ButtonBig btnPaketObatOp;
    private widget.ButtonBig btnPaketOperasi;
    private widget.ButtonBig btnPasien;
    private widget.ButtonBig btnPasienMati;
    private widget.ButtonBig btnPasswordAsuransi;
    private widget.ButtonBig btnPaymentPoint;
    private widget.ButtonBig btnPaymentPoint2;
    private widget.ButtonBig btnPegawai;
    private widget.ButtonBig btnPelFar;
    private widget.ButtonBig btnPelayananPenunjang;
    private widget.ButtonBig btnPemakaianAirPDAM;
    private widget.ButtonBig btnPemasukanLain;
    private widget.ButtonBig btnPembatalanPeriksaDokter;
    private widget.ButtonBig btnPembayaranAkunBayar;
    private widget.ButtonBig btnPembayaranAkunBayar2;
    private widget.ButtonBig btnPembayaranAkunBayar3;
    private widget.ButtonBig btnPembayaranPerUnit;
    private widget.ButtonBig btnPembelian;
    private widget.ButtonBig btnPembelianIpsrs;
    private widget.ButtonBig btnPemesanan;
    private widget.ButtonBig btnPenerimaanNonMedis;
    private widget.ButtonBig btnPengadaanIPSRSPerTanggal;
    private widget.ButtonBig btnPengaduan;
    private widget.ButtonBig btnPengambilanPenunjangUTD;
    private widget.ButtonBig btnPengambilanPenunjangUTD2;
    private widget.ButtonBig btnPengambilanUTD;
    private widget.ButtonBig btnPengambilanUTD2;
    private widget.ButtonBig btnPengaturanRekening;
    private widget.ButtonBig btnPengeluaran;
    private widget.ButtonBig btnPengeluaranIpsrs;
    private widget.ButtonBig btnPenggajian;
    private widget.ButtonBig btnPenggunaObatResep;
    private widget.ButtonBig btnPenjualan;
    private widget.ButtonBig btnPenjualanPerTanggal;
    private widget.ButtonBig btnPenyakitPD3I;
    private widget.ButtonBig btnPenyakitRanapCaraBayar;
    private widget.ButtonBig btnPeriksaRadiologi;
    private widget.ButtonBig btnPermintaanLab;
    private widget.ButtonBig btnPermintaanMPP;
    private widget.ButtonBig btnPermintaanMedis;
    private widget.ButtonBig btnPermintaanNonMedis;
    private widget.ButtonBig btnPermintaanRBA;
    private widget.ButtonBig btnPermintaanRadiologi;
    private widget.ButtonBig btnPerujukLabPerTahun;
    private widget.ButtonBig btnPerujukRadiologiPerTahun;
    private widget.ButtonBig btnPerusahaan;
    private widget.ButtonBig btnPindahRiw;
    private widget.ButtonBig btnPiutang;
    private widget.ButtonBig btnPiutangBelumLunas;
    private widget.ButtonBig btnPiutangPerAkunPiutang;
    private widget.ButtonBig btnPiutangPerCaraBayar;
    private widget.ButtonBig btnPiutangRalan;
    private widget.ButtonBig btnPiutangRanap;
    private widget.ButtonBig btnPnyMenularRalan;
    private widget.ButtonBig btnPnyMenularRanap;
    private widget.ButtonBig btnPnyTakMenularRalan;
    private widget.ButtonBig btnPnyTakMenularRanap;
    private widget.ButtonBig btnPostingJurnal;
    private widget.ButtonBig btnPotonganBiaya;
    private widget.ButtonBig btnProdusenInventaris;
    private widget.ButtonBig btnRBDokter;
    private widget.ButtonBig btnRBJasaSarana;
    private widget.ButtonBig btnRBKSO;
    private widget.ButtonBig btnRBMenejemen;
    private widget.ButtonBig btnRBPaketBHP;
    private widget.ButtonBig btnRBParamedis;
    private widget.ButtonBig btnRBiayaIpsrs;
    private widget.ButtonBig btnRHDOkter;
    private widget.ButtonBig btnRHJasaSarana;
    private widget.ButtonBig btnRHKSO;
    private widget.ButtonBig btnRHKeluarIpsrs;
    private widget.ButtonBig btnRHMasukIpsrs;
    private widget.ButtonBig btnRHMenejemen;
    private widget.ButtonBig btnRHPaketBHP;
    private widget.ButtonBig btnRHParamedis;
    private widget.ButtonBig btnRalan;
    private widget.ButtonBig btnRalanMasuk;
    private widget.ButtonBig btnRanap;
    private widget.ButtonBig btnRanapMasuk;
    private widget.ButtonBig btnRanapPerRuang;
    private widget.ButtonBig btnReferensiKamarInhealth;
    private widget.ButtonBig btnRegistrasi;
    private widget.ButtonBig btnRekapBulanan;
    private widget.ButtonBig btnRekapDiet;
    private widget.ButtonBig btnRekapHarian;
    private widget.ButtonBig btnRekapKegiatanRad;
    private widget.ButtonBig btnRekapLabPerTahun;
    private widget.ButtonBig btnRekapPembayaranPerUnit;
    private widget.ButtonBig btnRekapPenerimaanNonMedis;
    private widget.ButtonBig btnRekapPenerimaanObat;
    private widget.ButtonBig btnRekapPermintaanMedis;
    private widget.ButtonBig btnRekapPermintaanNonMedis;
    private widget.ButtonBig btnRekapPershift;
    private widget.ButtonBig btnRekapPoliAnak;
    private widget.ButtonBig btnRekapPresensi;
    private widget.ButtonBig btnRekapPresensi2;
    private widget.ButtonBig btnRekapRadiologiPerTahun;
    private widget.ButtonBig btnRekening;
    private widget.ButtonBig btnRekeningTahun;
    private widget.ButtonBig btnReklasifikasiRalan;
    private widget.ButtonBig btnReklasifikasiRanap;
    private widget.ButtonBig btnResepObat;
    private widget.ButtonBig btnResepObatDepan;
    private widget.ButtonBig btnResepPulang;
    private widget.ButtonBig btnResume;
    private widget.ButtonBig btnRetensiRM;
    private widget.ButtonBig btnReturBeli;
    private widget.ButtonBig btnReturJual;
    private widget.ButtonBig btnReturPasien;
    private widget.ButtonBig btnReturPiutang;
    private widget.ButtonBig btnRincianPiutangPasien;
    private widget.ButtonBig btnRiwayatBarangMedis;
    private widget.ButtonBig btnRiwayatBatch;
    private widget.ButtonBig btnRiwayatJabatan;
    private widget.ButtonBig btnRiwayatNaikGaji;
    private widget.ButtonBig btnRiwayatPendidikan;
    private widget.ButtonBig btnRiwayatPenelitian;
    private widget.ButtonBig btnRiwayatPenghargaan;
    private widget.ButtonBig btnRl31;
    private widget.ButtonBig btnRl32;
    private widget.ButtonBig btnRl33;
    private widget.ButtonBig btnRl34;
    private widget.ButtonBig btnRl35;
    private widget.ButtonBig btnRl36;
    private widget.ButtonBig btnRl37;
    private widget.ButtonBig btnRl38;
    private widget.ButtonBig btnRl4a;
    private widget.ButtonBig btnRl4asebab;
    private widget.ButtonBig btnRl4b;
    private widget.ButtonBig btnRl4bsebab;
    private widget.ButtonBig btnRuangInventaris;
    private widget.ButtonBig btnRujukMasuk;
    private widget.ButtonBig btnRujukPasien;
    private widget.ButtonBig btnRujukanKeluarBPJS;
    private widget.ButtonBig btnRujukanKeluarSisrute;
    private widget.ButtonBig btnRujukanMasukSisrute;
    private widget.ButtonBig btnSKDPBPJS;
    private widget.ButtonBig btnSaldoAkunPerBulan;
    private widget.ButtonBig btnSatuan;
    private widget.ButtonBig btnSensusHarianPoli;
    private widget.ButtonBig btnSensusHarianRalan;
    private widget.ButtonBig btnSetBiayaHarian;
    private widget.ButtonBig btnSetBiayaMasukSekali;
    private widget.ButtonBig btnSetHargaKamar;
    private widget.ButtonBig btnSetHariLibur;
    private widget.ButtonBig btnSetInputParsial;
    private widget.ButtonBig btnSetKompUtd;
    private widget.ButtonBig btnSetObatRalan;
    private widget.ButtonBig btnSetObatRanap;
    private widget.ButtonBig btnSetOtoRalan;
    private widget.ButtonBig btnSetPaketMCU;
    private widget.ButtonBig btnSetPenjab;
    private widget.ButtonBig btnSetPenjabBNM;
    private widget.ButtonBig btnSetSubAkun;
    private widget.ButtonBig btnSetupAplikasi;
    private widget.ButtonBig btnSetupEmbalase;
    private widget.ButtonBig btnSetupHarga;
    private widget.ButtonBig btnSetupJamInap;
    private widget.ButtonBig btnSetupNota;
    private widget.ButtonBig btnSetupOtoLokasi;
    private widget.ButtonBig btnSetupRM;
    private widget.ButtonBig btnSetupTarif;
    private widget.ButtonBig btnSidikJari;
    private widget.ButtonBig btnSiranapKetersediaanKamar;
    private widget.ButtonBig btnSirkulasi;
    private widget.ButtonBig btnSirkulasi2;
    private widget.ButtonBig btnSirkulasi3;
    private widget.ButtonBig btnSirkulasiBerkas;
    private widget.ButtonBig btnSirkulasiInventaris;
    private widget.ButtonBig btnSirkulasiNonMedis;
    private widget.ButtonBig btnSirkulasiObat4;
    private widget.ButtonBig btnSisaStok;
    private widget.ButtonBig btnStokKeluarIPSRSPerTanggal;
    private widget.ButtonBig btnStokKeluarMedis;
    private widget.ButtonBig btnStokObatPasien;
    private widget.ButtonBig btnSuku;
    private widget.ButtonBig btnSuplier;
    private widget.ButtonBig btnSuplierIPSRS;
    private widget.ButtonBig btnSuratAlmari;
    private widget.ButtonBig btnSuratBalas;
    private widget.ButtonBig btnSuratIndeks;
    private widget.ButtonBig btnSuratKeluar;
    private widget.ButtonBig btnSuratKlasifikasi;
    private widget.ButtonBig btnSuratMap;
    private widget.ButtonBig btnSuratMasuk;
    private widget.ButtonBig btnSuratPemesananMedis;
    private widget.ButtonBig btnSuratPemesananNonMedis;
    private widget.ButtonBig btnSuratRak;
    private widget.ButtonBig btnSuratRuang;
    private widget.ButtonBig btnSuratSifat;
    private widget.ButtonBig btnSuratStatus;
    private widget.ButtonBig btnSurkonBPJS;
    private widget.ButtonBig btnSurveilansPD3I;
    private widget.ButtonBig btnSurveilansRalan;
    private widget.ButtonBig btnSurveilansRanap;
    private widget.ButtonBig btnTagihanDokter;
    private widget.ButtonBig btnTagihanMasuk;
    private widget.ButtonBig btnTagihanObatBangsal;
    private widget.ButtonBig btnTagihanObatPoli;
    private widget.ButtonBig btnTagihanOperasi;
    private widget.ButtonBig btnTagihanPoli;
    private widget.ButtonBig btnTagihanRalanPerhari;
    private widget.ButtonBig btnTagihanRanapPerhari;
    private widget.ButtonBig btnTambahanBiaya;
    private widget.ButtonBig btnTarifLab;
    private widget.ButtonBig btnTarifRadiologi;
    private widget.ButtonBig btnTarifUtd;
    private widget.ButtonBig btnTemporaryPresensi;
    private widget.ButtonBig btnTindakanRalan;
    private widget.ButtonBig btnTindakanRanap;
    private widget.ButtonBig btnToolIGD;
    private widget.ButtonBig btnToolLab;
    private widget.ButtonBig btnToolRad;
    private widget.ButtonBig btnTracker;
    private widget.ButtonBig btnTrialSendWA;
    private widget.ButtonBig btnUTDCekalDarah;
    private widget.ButtonBig btnUTDDonorDarah;
    private widget.ButtonBig btnUTDKomponenDarah;
    private widget.ButtonBig btnUTDMedisRusak;
    private widget.ButtonBig btnUTDPemisahanDarah;
    private widget.ButtonBig btnUTDPenunjangRusak;
    private widget.ButtonBig btnUTDPenyerahanDarah;
    private widget.ButtonBig btnUTDPermintaan;
    private widget.ButtonBig btnUTDStokDarah;
    private widget.ButtonBig btnUser;
    private widget.ButtonBig btnVakum;
    private widget.ButtonBig btnakun_bayar;
    private widget.ButtonBig btnbayar_pemesanan;
    private widget.ButtonBig btnfee_bacaan_ekg;
    private widget.ButtonBig btnfee_ralan;
    private widget.ButtonBig btnfee_rujukan_ranap;
    private widget.ButtonBig btnfee_rujukan_rontgen;
    private widget.ButtonBig btnfee_visit_dokter;
    private widget.Button button1;
    private widget.ComboBox cmbMenu;
    private widget.PasswordBox edAdmin;
    private widget.PasswordBox edPwd;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame6;
    private widget.Label jLabel10;
    private javax.swing.JLabel jLabel11;
    private widget.Label jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label35;
    private widget.Label label36;
    private javax.swing.JLabel lblStts;
    private javax.swing.JLabel lblTgl;
    private javax.swing.JLabel lblUser;
    private usu.widget.glass.PanelGlass panelJudul;
    private widget.InternalFrame panelMenu;
    private widget.panelisi panelisi2;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Tanggal tanggal;
    // End of variables declaration//GEN-END:variables
    
    public void isTampil(){        
        Panelmenu.removeAll();
        
        if(ChkInput.isSelected()==true){
            isCombo();  
        }else{
            if(TCari.getText().equals("")){
                isCariKosong();                
            }else if(!TCari.getText().equals("")){
                isCariIsi();
            }    
        }
            
        if(jmlmenu<=1){
            Panelmenu.setLayout(new GridLayout(0,1));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()-5));
        }else if(jmlmenu<=4){
            Panelmenu.setLayout(new GridLayout(0,2));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()-5));
        }else if(jmlmenu<=9){
            Panelmenu.setLayout(new GridLayout(0,3));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()-5));
        }else if(jmlmenu<=16){
            Panelmenu.setLayout(new GridLayout(0,4));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()-5));
        }else if(jmlmenu<=20){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()-5));
        }else if(jmlmenu<=25){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+(scrollPane2.getHeight()/4)));
        }else if(jmlmenu<=30){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*2)));
        }else if(jmlmenu<=35){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*3)));
        }else if(jmlmenu<=40){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*4)));
        }else if(jmlmenu<=45){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*5)));
        }else if(jmlmenu<=50){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*6)));
        }else if(jmlmenu<=55){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*7)));
        }else if(jmlmenu<=60){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*8)));
        }else if(jmlmenu<=65){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*9)));
        }else if(jmlmenu<=70){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*10)));
        }else if(jmlmenu<=75){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*11)));
        }else if(jmlmenu<=80){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*12)));
        }else if(jmlmenu<=85){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*13)));
        }else if(jmlmenu<=90){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*14)));
        }else if(jmlmenu<=95){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*15)));
        }else if(jmlmenu<=100){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*16)));
        }else if(jmlmenu<=105){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*17)));
        }else if(jmlmenu<=110){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*18)));
        }else if(jmlmenu<=115){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*19)));
        }else if(jmlmenu<=120){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*20)));
        }else if(jmlmenu<=125){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*21)));
        }else if(jmlmenu<=130){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*22)));
        }else if(jmlmenu<=135){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*23)));
        }else if(jmlmenu<=140){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*24)));
        }else if(jmlmenu<=145){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*25)));
        }else if(jmlmenu<=150){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*26)));
        }else if(jmlmenu<=155){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*27)));
        }else if(jmlmenu<=160){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*28)));
        }else if(jmlmenu<=165){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*29)));
        }else if(jmlmenu<=170){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*30)));
        }else if(jmlmenu<=175){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*31)));
        }else if(jmlmenu<=180){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*32)));
        }else if(jmlmenu<=185){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*33)));
        }else if(jmlmenu<=190){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*34)));
        }else if(jmlmenu<=195){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*35)));
        }else if(jmlmenu<=200){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*36)));
        }else if(jmlmenu<=205){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*37)));
        }else if(jmlmenu<=210){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*38)));
        }else if(jmlmenu<=215){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*39)));
        }else if(jmlmenu<=220){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*40)));
        }else if(jmlmenu<=225){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*41)));
        }else if(jmlmenu<=230){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*42)));
        }else if(jmlmenu<=235){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*43)));
        }else if(jmlmenu<=240){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*44)));
        }else if(jmlmenu<=245){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*45)));
        }else if(jmlmenu<=250){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*46)));
        }else if(jmlmenu<=255){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*47)));
        }else if(jmlmenu<=260){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*48)));
        }else if(jmlmenu<=265){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*49)));
        }else if(jmlmenu<=270){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*50)));
        }else if(jmlmenu<=275){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*51)));
        }else if(jmlmenu<=280){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*52)));
        }else if(jmlmenu<=285){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*53)));
        }else if(jmlmenu<=290){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*54)));
        }else if(jmlmenu<=295){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*55)));
        }else if(jmlmenu<=300){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*56)));
        }else if(jmlmenu<=305){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*57)));
        }else if(jmlmenu<=310){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*58)));
        }else if(jmlmenu<=315){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*59)));
        }else if(jmlmenu<=320){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*60)));
        }else if(jmlmenu<=325){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*61)));
        }else if(jmlmenu<=330){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*62)));
        }else if(jmlmenu<=335){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*63)));
        }else if(jmlmenu<=340){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*64)));
        }else if(jmlmenu<=345){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*65)));
        }else if(jmlmenu<=350){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*66)));
        }else if(jmlmenu<=355){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*67)));
        }else if(jmlmenu<=360){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*68)));
        }else if(jmlmenu<=365){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*69)));
        }else if(jmlmenu<=370){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*70)));
        }else if(jmlmenu<=375){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*71)));
        }else if(jmlmenu<=380){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*72)));
        }else if(jmlmenu<=385){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*73)));
        }else if(jmlmenu<=390){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*74)));
        }else if(jmlmenu<=395){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*75)));
        }else if(jmlmenu<=400){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*76)));
        }else if(jmlmenu<=405){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*77)));
        }else if(jmlmenu<=410){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*78)));
        }else if(jmlmenu<=415){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*79)));
        }else if(jmlmenu<=420){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*80)));
        }else if(jmlmenu<=425){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*81)));
        }else if(jmlmenu<=430){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*82)));
        }else if(jmlmenu<=435){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*83)));
        }else if(jmlmenu<=440){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*84)));
        }else if(jmlmenu<=445){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*85)));
        }else if(jmlmenu<=450){
            Panelmenu.setLayout(new GridLayout(0,5));
            Panelmenu.setPreferredSize(new Dimension(scrollPane2.getWidth()-10,scrollPane2.getHeight()+((scrollPane2.getHeight()/4)*86)));
        }                                                 
        Panelmenu.repaint(); 
        DlgHome.setVisible(true);               
    }
    
    public void isWall(){
        try{            
            ps=koneksi.prepareStatement("select nama_instansi, alamat_instansi, kabupaten, propinsi, aktifkan, wallpaper,kontak,email,logo from setting");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    jLabel8.setText(rs.getString(1));
                    this.setTitle("SIM "+rs.getString("nama_instansi"));
                    jLabel11.setText(rs.getString(2) +", "+rs.getString(3) +", "+rs.getString(4) +" ");
                    var.setnamars(rs.getString("nama_instansi"));
                    var.setalamatrs(rs.getString("alamat_instansi"));
                    var.setkabupatenrs(rs.getString("kabupaten"));
                    var.setpropinsirs(rs.getString("propinsi"));
                    var.setkontakrs(rs.getString("kontak"));
                    var.setemailrs(rs.getString("email"));
                    if(rs.getString(5).equals("Yes")){
                        Blob blob = rs.getBlob(6);
                        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(blob.getBytes(1, (int) (blob.length()))));
                        repaint();
                    }
                }  
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }                 
        }catch(Exception e){
            System.out.println("Notifikasi : Silahkan Set Aplikasi "+e);
        }
    }

    private void isTutup() {
        FlayMenu.setVisible(false);
        var.setform("frmUtama");
        Window[] wins = Window.getWindows();
        for (Window win : wins) {
            if (win instanceof JDialog) {
                win.dispose();
            }
        }
    }
    
    private void setToolbar(){
        if(internalFrame1.getWidth()<(BtnMenu.getWidth()+BtnToolReg.getWidth()+btnToolIGD.getWidth()+
                btnToolLab.getWidth()+
                btnToolRad.getWidth()+BtnToolJualObat.getWidth()+BtnToolKamnap.getWidth()+
                BtnToolKasir.getWidth()+BtnLog.getWidth()+BtnClose.getWidth()+8)){
            internalFrame1.setSize(new Dimension(PanelUtama.getWidth(),160));
        }else{
            internalFrame1.setSize(new Dimension(PanelUtama.getWidth(),44));
        }
    }

    private void isCombo() {
        if(cmbMenu.getSelectedIndex()==0){
            jmlmenu=0;     
            if(var.getinformasi_kamar()==true){
                Panelmenu.add(btnAnalisaKamar);
                jmlmenu++;
            }

            if(var.getjadwal_praktek()==true){                
                Panelmenu.add(BtnJadwal);
                jmlmenu++;
            }

            if(var.getregistrasi()==true){
                Panelmenu.add(btnRegistrasi);
                jmlmenu++;
            }

            if(var.getbooking_registrasi()==true){
                Panelmenu.add(btnBookingRegistrasi);
                Panelmenu.add(btnBookingMJKN);
                jmlmenu++;
            }
            
            if(var.getigd()==true){
                Panelmenu.add(btnIGD);  
                jmlmenu++;
            }       

            if(var.gettindakan_ralan()==true){
                Panelmenu.add(btnRalan); 
                jmlmenu++;
            }

            if((var.getkamar_inap()==true)||(var.getbilling_ranap()==true)||(var.gettindakan_ranap()==true)){
                Panelmenu.add(btnKamarInap);
                jmlmenu++;
            }

            if(var.getbooking_operasi()==true){
                Panelmenu.add(btnJadwalOperasi);
                jmlmenu++;
            }
            
            if((var.getpermintaan_lab()==true)||(var.getperiksa_lab()==true)){
                Panelmenu.add(btnPermintaanLab);
                jmlmenu++;
            }
            
            if((var.getpermintaan_radiologi()==true)||(var.getperiksa_radiologi()==true)){
                Panelmenu.add(btnPermintaanRadiologi);
                jmlmenu++;
            }
            
            if(var.getdpjp_ranap()==true){
                Panelmenu.add(BtnDpjp);
                jmlmenu++;
            }

            if(var.gettindakan_ranap()==true){
                Panelmenu.add(btnRanap);
                jmlmenu++;
            }

            if(var.getoperasi()==true){
                Panelmenu.add(btnTagihanOperasi);
                jmlmenu++;
            }

            if(var.getrujukan_keluar()==true){
                Panelmenu.add(btnRujukPasien);
                jmlmenu++;
            }

            if(var.getrujukan_masuk()==true){                
                Panelmenu.add(btnRujukMasuk);
                jmlmenu++;
            }

            if(var.getberi_obat()==true){
                Panelmenu.add(btnBeriObat);
                jmlmenu++;
            }

            if(var.getresep_pulang()==true){                
                Panelmenu.add(btnResepPulang);
                jmlmenu++;
            }

            if(var.getresep_obat()==true){
                Panelmenu.add(btnResepObat);
                jmlmenu++;
            }

            if(var.getdiet_pasien()==true){
                Panelmenu.add(btnDiet);
                Panelmenu.add(btnRekapDiet);
                jmlmenu++;
            }

            if(var.getperiksa_lab()==true){
                Panelmenu.add(btnLaboratorium);
                jmlmenu++;
            }

            if(var.getperiksa_radiologi()==true){
                Panelmenu.add(btnPeriksaRadiologi);   
                jmlmenu++;
            }

            if((var.getkasir_ralan()==true)||(var.getbilling_ralan()==true)){
                Panelmenu.add(btnKasir);     
                jmlmenu++;
            }

            if(var.getdeposit_pasien()==true){                          
                Panelmenu.add(btnDeposit);
                jmlmenu++;
            }                        
        }else if(cmbMenu.getSelectedIndex()==1){ 
            jmlmenu=0;
            if(var.getpetugas()==true){
                Panelmenu.add(btnPegawai);
                jmlmenu++;
            }

            if(var.getdokter()==true){
                Panelmenu.add(btnDokter);
                jmlmenu++;
            }

            if(var.getbarcode()==true){
                Panelmenu.add(btnBarcode);  
                jmlmenu++;
            }

            if(var.getsidikjari()==true){
                Panelmenu.add(btnSidikJari);   
                jmlmenu++;
            }

            if(var.getjam_masuk()==true){
                Panelmenu.add(btnJamPresensi);  
                jmlmenu++;
            }

            if(var.getjadwal_pegawai()==true){
                Panelmenu.add(btnJadwalPegawai);   
                jmlmenu++;
                Panelmenu.add(btnJadwalTambahan);   
                jmlmenu++;
            }             

            if(var.getpresensi_harian()==true){
                Panelmenu.add(btnRekapHarian);
                jmlmenu++;
            }

            if(var.getpresensi_bulanan()==true){                
                Panelmenu.add(btnRekapBulanan);
                jmlmenu++;
            }

            if(var.gettemporary_presensi()==true){
                Panelmenu.add(btnTemporaryPresensi);   
                jmlmenu++;
            }

            Panelmenu.add(btnRekapPresensi);
            Panelmenu.add(btnRekapPresensi2);
            jmlmenu++;
            jmlmenu++;

            if((var.getpegawai_admin()==true)||(var.getpegawai_user()==true)){                
                Panelmenu.add(btnPenggajian); 
                jmlmenu++;
            }
            
            if(var.getmaster_berkas_pegawai()==true){                
                Panelmenu.add(btnMasterBerkasPegawai); 
                jmlmenu++;
            }
            
            if(var.getberkas_kepegawaian()==true){                
                Panelmenu.add(btnBerkasPegawai); 
                jmlmenu++;
            }
            
            if(var.getriwayat_jabatan()==true){                
                Panelmenu.add(btnRiwayatJabatan); 
                jmlmenu++;
            }
            
            if(var.getriwayat_pendidikan()==true){                
                Panelmenu.add(btnRiwayatPendidikan); 
                jmlmenu++;
            }
            
            if(var.getriwayat_naik_gaji()==true){                
                Panelmenu.add(btnRiwayatNaikGaji); 
                jmlmenu++;
            }
            
            if(var.getkegiatan_ilmiah()==true){                
                Panelmenu.add(btnKegiatanIlmiah); 
                jmlmenu++;
            }
            
            if(var.getriwayat_penghargaan()==true){                
                Panelmenu.add(btnRiwayatPenghargaan); 
                jmlmenu++;
            }
            
            if(var.getriwayat_penelitian()==true){                
                Panelmenu.add(btnRiwayatPenelitian); 
                jmlmenu++;
            }

        }else if(cmbMenu.getSelectedIndex()==2){ 
            jmlmenu=0;
            if(var.getindustrifarmasi()==true){
                Panelmenu.add(btnIndustriFarmasi); 
                jmlmenu++;
            }

            if(var.getsuplier()==true){
                Panelmenu.add(btnSuplier); 
                jmlmenu++;
            }

            if(var.getsatuan_barang()==true){
                Panelmenu.add(btnSatuan); 
                jmlmenu++;
            }
            
            if(var.getmetode_racik()==true){
                Panelmenu.add(btnMetodeRacik); 
                jmlmenu++;
            }

            if(var.getkonversi_satuan()==true){
                Panelmenu.add(btnKonversi); 
                jmlmenu++;
            }    

            if(var.getjenis_barang()==true){
                Panelmenu.add(btnJnsBarang);
                jmlmenu++;
            }
            
            if(var.getkategori_barang()==true){
                Panelmenu.add(btnKategoriBarang);
                jmlmenu++;
            }
            
            if(var.getgolongan_barang()==true){
                Panelmenu.add(btnGolonganBarang);
                jmlmenu++;
            }

            if(var.getobat()==true){
                Panelmenu.add(btnObat);
                Panelmenu.add(btn10Obat);
                Panelmenu.add(btnHibahObatBHP);
                Panelmenu.add(btnLapStokOp);
                Panelmenu.add(btnLapPelFar);
                Panelmenu.add(btnPelFar);
                Panelmenu.add(btnPaketObatOp);
                jmlmenu++;
            }

            if(var.getstok_opname_obat()==true){
                Panelmenu.add(btnOpname); 
                jmlmenu++;
            }

            if(var.getmutasi_barang()==true){
                Panelmenu.add(btnMutasiBarang); 
                jmlmenu++;
            }

            if(var.getstok_obat_pasien()==true){
                Panelmenu.add(btnStokObatPasien); 
                jmlmenu++;
            }

            if((var.getpermintaan_medis()==true)||(var.getmutasi_barang()==true)||(var.getpengeluaran_stok_apotek()==true)){
                Panelmenu.add(btnPermintaanMedis);   
                jmlmenu++;
            }
            
            if(var.getrekap_permintaan_medis()==true){
                Panelmenu.add(btnRekapPermintaanMedis);   
                jmlmenu++;
            }
            
            if((var.getsurat_pemesanan_medis()==true)||(var.getpemesanan_obat()==true)){
                Panelmenu.add(btnSuratPemesananMedis);   
                jmlmenu++;
            }
            
            if(var.getpengadaan_obat()==true){
                Panelmenu.add(btnPembelian);   
                jmlmenu++;
            }

            if((var.getpemesanan_obat()==true)||(var.getbayar_pemesanan_obat()==true)){
                Panelmenu.add(btnPemesanan);
                jmlmenu++;
            }

            if(var.getpenjualan_obat()==true){
                Panelmenu.add(btnPenjualan);
                jmlmenu++;
            }
            
            if(var.getresep_dokter()==true){
                Panelmenu.add(btnDaftarPermintaanResep);
                jmlmenu++;
            }

            if(var.getpiutang_obat()==true){
                 Panelmenu.add(btnPiutang);  
                 jmlmenu++;
            }
            
            if(var.getpengeluaran_stok_apotek()==true){
                 Panelmenu.add(btnStokKeluarMedis);  
                 jmlmenu++;
            }

            if(var.getretur_ke_suplier()==true){
                Panelmenu.add(btnReturBeli); 
                jmlmenu++;
            }

            if(var.getretur_dari_pembeli()==true){
                Panelmenu.add(btnReturJual); 
                jmlmenu++;
            }

            if(var.getretur_obat_ranap()==true){
                Panelmenu.add(btnReturPasien); 
                jmlmenu++;
            }

            if(var.getretur_piutang_pasien()==true){
                Panelmenu.add(btnReturPiutang); 
                jmlmenu++;
            }   

            if(var.getpengambilan_utd()==true){
                Panelmenu.add(btnPengambilanUTD); 
                jmlmenu++;
            } 

            if(var.getkeuntungan_penjualan()==true){
                Panelmenu.add(btnKeuntungan);
                jmlmenu++;
            }

            if(var.getkeuntungan_beri_obat()==true){
                Panelmenu.add(btnKeuntunganObatRanap);
                jmlmenu++;
            }

            if(var.getkeuntungan_beri_obat_nonpiutang()==true){
                Panelmenu.add(btnKeuntunganObat2);
                jmlmenu++;
            }

            if(var.getsirkulasi_obat()==true){
                Panelmenu.add(btnSirkulasi);
                jmlmenu++;
            }

            if(var.getsirkulasi_obat2()==true){
                Panelmenu.add(btnSirkulasi2);
                jmlmenu++;
            }
            
            if(var.getsirkulasi_obat3()==true){
                Panelmenu.add(btnSirkulasi3);
                Panelmenu.add(btnSirkulasiObat4);
                jmlmenu++;
            }
            
            if(var.getpemberian_obat_pertanggal()==true){
                Panelmenu.add(btnObatPerTanggal);
                jmlmenu++;
            }
            
            if(var.getpenjualan_obat_pertanggal()==true){
                Panelmenu.add(btnPenjualanPerTanggal);
                jmlmenu++;
            }

            if(var.getriwayat_obat_alkes_bhp()==true){
                Panelmenu.add(btnRiwayatBarangMedis);
                jmlmenu++;
            }

            if(var.getdarurat_stok()==true){
                Panelmenu.add(btnDaruratStok);
                jmlmenu++;
            }  
            
            if(var.getpengguna_obat_resep()==true){
                Panelmenu.add(btnPenggunaObatResep);
                jmlmenu++;
            }
            
            if(var.getrekap_pemesanan()==true){
                Panelmenu.add(btnRekapPenerimaanObat);
                jmlmenu++;
            }
            
            if(var.getdata_batch()==true){
                Panelmenu.add(btnDataBatch);
                jmlmenu++;
            }
            
            if(var.getriwayat_data_batch()==true){
                Panelmenu.add(btnRiwayatBatch);
                jmlmenu++;
            }
            
            if(var.getkegiatan_farmasi()==true){
                Panelmenu.add(btnKegiatanFarmasi);
                jmlmenu++;
            }
            
            if(var.getppn_obat()==true){
                Panelmenu.add(btnPPNObat);
                jmlmenu++;
            }
            
            if(var.getkadaluarsa_batch()==true){
                Panelmenu.add(btnKadaluarsaBatch);
                jmlmenu++;
            }
            
            if(var.getsisa_stok()==true){
                Panelmenu.add(btnSisaStok);
                jmlmenu++;
            }
            
            if(var.getobat_per_resep()==true){
                Panelmenu.add(btnObatPerResep);
                jmlmenu++;
            }
        }else if(cmbMenu.getSelectedIndex()==3){  
            jmlmenu=0;
            if(var.getsatuan_barang()==true){
                Panelmenu.add(btnSatuan);  
                jmlmenu++;
            }             

            if(var.getipsrs_jenis_barang()==true){
                Panelmenu.add(btnJenisIpsrs); 
                jmlmenu++;
            } 

            if(var.getipsrs_barang()==true){
                Panelmenu.add(btnBarangIpsrs);  
                jmlmenu++;
            } 

            if(var.getsuplier_penunjang()==true){
                Panelmenu.add(btnSuplierIPSRS);  
                jmlmenu++;
            }             

            if(var.getpermintaan_non_medis()==true){
                Panelmenu.add(btnPermintaanNonMedis);  
                jmlmenu++;
            }
            
            if(var.getrekap_permintaan_non_medis()==true){
                Panelmenu.add(btnRekapPermintaanNonMedis);  
                jmlmenu++;
            }
            
            if(var.getsurat_pemesanan_non_medis()==true){
                Panelmenu.add(btnSuratPemesananNonMedis);  
                jmlmenu++;
            }
            
            if(var.getipsrs_pengadaan_barang()==true){
                Panelmenu.add(btnPembelianIpsrs);  
                jmlmenu++;
            }
            
            if(var.getpenerimaan_non_medis()==true){
                Panelmenu.add(btnPenerimaanNonMedis);  
                jmlmenu++;
            }

            if(var.getipsrs_stok_keluar()==true){
                Panelmenu.add(btnPengeluaranIpsrs); 
                jmlmenu++;
            }

            if(var.getipsrs_pengeluaran_harian()==true){
                Panelmenu.add(btnRBiayaIpsrs);
                jmlmenu++;
            }

            if(var.getipsrs_rekap_pengadaan()==true){
                Panelmenu.add(btnRHMasukIpsrs);
                jmlmenu++;
            }

            if(var.getipsrs_rekap_stok_keluar()==true){
                Panelmenu.add(btnRHKeluarIpsrs);
                jmlmenu++;
            } 

            if(var.getpengambilan_penunjang_utd()==true){
                Panelmenu.add(btnPengambilanPenunjangUTD);
                jmlmenu++;
            } 
            
            if(var.getipsrs_pengadaan_pertanggal()==true){
                Panelmenu.add(btnPengadaanIPSRSPerTanggal);
                jmlmenu++;
            } 
            
            if(var.getipsrs_stokkeluar_pertanggal()==true){
                Panelmenu.add(btnStokKeluarIPSRSPerTanggal);
                jmlmenu++;
            } 
            
            if(var.getrekap_pemesanan_non_medis()==true){
                Panelmenu.add(btnRekapPenerimaanNonMedis);
                jmlmenu++;
            }
            
            if(var.getstok_opname_logistik()==true){
                Panelmenu.add(btnOpnameIPSRS);
                jmlmenu++;
            }
            
            if(var.getsirkulasi_non_medis()==true){
                Panelmenu.add(btnSirkulasiNonMedis);
                jmlmenu++;
            }

        }else if(cmbMenu.getSelectedIndex()==4){ 
            jmlmenu=0;
            if(var.getinventaris_jenis()==true){
                Panelmenu.add(btnJenisInventaris);  
                jmlmenu++;
            }   

            if(var.getinventaris_kategori()==true){
                Panelmenu.add(btnKategoriInventaris);
                jmlmenu++;
            }

            if(var.getinventaris_merk()==true){
                Panelmenu.add(btnMerkInventaris);
                jmlmenu++;
            }

            if(var.getinventaris_ruang()==true){
                Panelmenu.add(btnRuangInventaris);
                jmlmenu++;
            }

            if(var.getinventaris_produsen()==true){
                Panelmenu.add(btnProdusenInventaris);
                jmlmenu++;
            }

            if(var.getinventaris_koleksi()==true){
                Panelmenu.add(btnBarangInventaris);
                jmlmenu++;
            }

            if(var.getinventaris_inventaris()==true){
                Panelmenu.add(btnInventaris);  
                jmlmenu++;
            }                 

            if(var.getinventaris_sirkulasi()==true){
                Panelmenu.add(btnSirkulasiInventaris);
                jmlmenu++;
            }  
            
            if(var.getbarang_cssd()==true){
                Panelmenu.add(btnBarangCSSD);
                jmlmenu++;
            }
            
            if(var.getpemakaian_air_pdam()==true){
                Panelmenu.add(btnPemakaianAirPDAM);
                jmlmenu++;
            } 
            
            if(var.getlimbah_b3_medis()==true){
                Panelmenu.add(btnLimbahB3Medis);
                jmlmenu++;
            } 
            
//            if(var.getkamar_inap()==true || var.getregistrasi()==true){
//                
//            } 
            Panelmenu.add(btnPermintaanRBA);
            jmlmenu++;
        }else if(cmbMenu.getSelectedIndex()==5){ 
            jmlmenu=0;
            if(var.getharian_tindakan_poli()==true){
                Panelmenu.add(btnTagihanPoli); 
                jmlmenu++;
            }   

            if(var.getharian_kamar()==true){
                Panelmenu.add(btnHarianKamar); 
                jmlmenu++;
            }  

            if(var.getharian_tindakan_dokter()==true){
                Panelmenu.add(btnTagihanDokter); 
                jmlmenu++;
            } 

            if(var.getobat_per_poli()==true){
                Panelmenu.add(btnTagihanObatPoli);
                jmlmenu++;
            }

            if(var.getobat_per_kamar()==true){
                Panelmenu.add(btnTagihanObatBangsal);
                jmlmenu++;
            }

            if(var.getobat_per_dokter_ralan()==true){
                Panelmenu.add(btnObatPasienRalan);    
                jmlmenu++;
            }

            if(var.getobat_per_dokter_ranap()==true){
                Panelmenu.add(btnObatPasienRanap);
                jmlmenu++;
            }    

            if(var.getobat_per_dokter_peresep()==true){
                Panelmenu.add(btnObatPasienPeresep);
                jmlmenu++;
            }

            if(var.getobat_per_cara_bayar()==true){
                Panelmenu.add(btnObatPerCaraBayar);
                jmlmenu++;
            }

            if(var.getdetail_tindakan()==true){
                Panelmenu.add(btnDetailTindakan);
                jmlmenu++;
            } 
            
            if(var.getjm_ranap_dokter()==true){
                Panelmenu.add(btnJMDetailDokter);
                jmlmenu++;
            } 
            
            if(var.getdetailjmdokter2()==true){
                Panelmenu.add(btnJMDetailDokter2);
                jmlmenu++;
            } 

            if(var.getharian_dokter()==true){
                Panelmenu.add(btnRHDOkter);  
                jmlmenu++;
            }

            if(var.getbulanan_dokter()==true){
                Panelmenu.add(btnRBDokter);
                jmlmenu++;
            }

            if(var.getharian_paramedis()==true){
                Panelmenu.add(btnRHParamedis); 
                jmlmenu++;
            }

            if(var.getbulanan_paramedis()==true){
                Panelmenu.add(btnRBParamedis); 
                jmlmenu++;
            }

            if(var.getharian_js()==true){
                Panelmenu.add(btnRHJasaSarana);  
                jmlmenu++;
            }

            if(var.getbulanan_js()==true){
                Panelmenu.add(btnRBJasaSarana);  
                jmlmenu++;
            }

            if(var.getharian_kso()==true){
                Panelmenu.add(btnRHKSO);  
                jmlmenu++;
            }

            if(var.getbulanan_kso()==true){
                Panelmenu.add(btnRBKSO);  
                jmlmenu++;
            }

            if(var.getharian_menejemen()==true){
                Panelmenu.add(btnRHMenejemen);  
                jmlmenu++;
            }

            if(var.getbulanan_menejemen()==true){
                Panelmenu.add(btnRBMenejemen);  
                jmlmenu++;
            }

            if(var.getharian_paket_bhp()==true){
                Panelmenu.add(btnRHPaketBHP);  
                jmlmenu++;
            }

            if(var.getbulanan_paket_bhp()==true){
                Panelmenu.add(btnRBPaketBHP);  
                jmlmenu++;
            }

            if(var.getfee_visit_dokter()==true){
                Panelmenu.add(btnfee_visit_dokter); 
                jmlmenu++;
            }

            if(var.getfee_bacaan_ekg()==true){
                Panelmenu.add(btnfee_bacaan_ekg); 
                jmlmenu++;
            }

            if(var.getfee_rujukan_rontgen()==true){
                Panelmenu.add(btnfee_rujukan_rontgen); 
                jmlmenu++;
            }

            if(var.getfee_rujukan_ranap()==true){
                Panelmenu.add(btnfee_rujukan_ranap); 
                jmlmenu++;
            }

            if(var.getfee_ralan()==true){
                Panelmenu.add(btnfee_ralan); 
                jmlmenu++;
            }

            if(var.getdetail_tindakan_okvk()==true){
                Panelmenu.add(btnDetailVKOK);
                jmlmenu++;
            }
            
            if(var.getpembayaran_ralan()==true){
                Panelmenu.add(btnRalanMasuk);
                jmlmenu++;
            }

            if(var.getpembayaran_ranap()==true){
                Panelmenu.add(btnRanapMasuk);
                jmlmenu++;
            }
            
            if(var.getpiutang_pasien()==true){
               Panelmenu.add(btnLihatPiutang); 
               jmlmenu++;
            }

            if(var.getpiutang_ralan()==true){
                Panelmenu.add(btnPiutangRalan);
                jmlmenu++;
            }

            if(var.getpiutang_ranap()==true){
                Panelmenu.add(btnPiutangRanap);
                jmlmenu++;
            }

            if(var.getrekap_pembayaran_ralan()==true){
                Panelmenu.add(btnTagihanRalanPerhari);
                jmlmenu++;
            }

            if(var.getrekap_pembayaran_ranap()==true){
                Panelmenu.add(btnTagihanRanapPerhari);
                jmlmenu++;
            }
            
            if(var.getpembayaran_per_unit()==true){
                Panelmenu.add(btnPembayaranPerUnit);
                jmlmenu++;
            }
            
            if(var.getrekap_pembayaran_per_unit()==true){
                Panelmenu.add(btnRekapPembayaranPerUnit);
                jmlmenu++;
            }

            if(var.gettagihan_masuk()==true){
                Panelmenu.add(btnTagihanMasuk);
                jmlmenu++;
            }

            if(var.gettambahan_biaya()==true){
                Panelmenu.add(btnTambahanBiaya);
                jmlmenu++;
            }   

            if(var.getpotongan_biaya()==true){
                Panelmenu.add(btnPotonganBiaya);
                jmlmenu++;
            }     
            
            if(var.getrekap_poli_anak()==true){
                Panelmenu.add(btnRekapPoliAnak);
                jmlmenu++;
            } 

            if(var.getdeposit_pasien()==true){
                Panelmenu.add(btnDeposit);
                jmlmenu++;
            }

            if(var.getrekap_per_shift()==true){
                Panelmenu.add(btnRekapPershift);
                jmlmenu++;
            }            

            if(var.getpayment_point()==true){
                Panelmenu.add(btnPaymentPoint);
                jmlmenu++;
            }
            
            if(var.getpayment_point2()==true){
                Panelmenu.add(btnPaymentPoint2);
                jmlmenu++;
            }
            
            if(var.getpembayaran_akun_bayar()==true){
                Panelmenu.add(btnPembayaranAkunBayar);
                jmlmenu++;
            }
            
            if(var.getpembayaran_akun_bayar2()==true){
                Panelmenu.add(btnPembayaranAkunBayar2);
                jmlmenu++;
            }
            
            if(var.getpembayaran_akun_bayar3()==true){
                Panelmenu.add(btnPembayaranAkunBayar3);
                jmlmenu++;
            }
            
            if(var.getpiutang_akun_piutang()==true){
                Panelmenu.add(btnPiutangPerAkunPiutang);
                jmlmenu++;
            }
        }else if(cmbMenu.getSelectedIndex()==6){ 
            jmlmenu=0;
            if(var.geticd9()==true){
                Panelmenu.add(btnICD9);
                jmlmenu++;
            }

            if(var.getpenyakit()==true){
                Panelmenu.add(btnICD);
                jmlmenu++;
            }
            
            if(var.getpenyakit_pd3i()==true){
                Panelmenu.add(btnPenyakitPD3I);
                jmlmenu++;
            }

            if(var.getsurveilans_pd3i()==true){
                Panelmenu.add(btnSurveilansPD3I);
                jmlmenu++;
            }

            if(var.getsurveilans_ralan()==true){
                Panelmenu.add(btnSurveilansRalan);
                jmlmenu++;
            }

            if(var.getsurveilans_ranap()==true){
                Panelmenu.add(btnSurveilansRanap);
                jmlmenu++;
            }

            if(var.getpny_takmenular_ralan()==true){
                Panelmenu.add(btnPnyTakMenularRalan);
                jmlmenu++;
            }

            if(var.getpny_takmenular_ranap()==true){
                Panelmenu.add(btnPnyTakMenularRanap);
                jmlmenu++;
            }
            
            if(var.getpenyakit_menular_ralan()==true){
                Panelmenu.add(btnPnyMenularRalan);
                jmlmenu++;
            }

            if(var.getpenyakit_menular_ranap()==true){
                Panelmenu.add(btnPnyMenularRanap);
                jmlmenu++;
            }

            if(var.getobat_penyakit()==true){
                Panelmenu.add(btnObatPenyakit);
                jmlmenu++;
            }

            if(var.getpenyakit_ralan()==true){                
                Panelmenu.add(btnFrekuensiRalan);
                Panelmenu.add(btnFrekuensiRalanDanRanap);
                Panelmenu.add(btnFrekuensiPerPerujuk);
                jmlmenu++;
            }

            if(var.getpenyakit_ranap()==true){  
                Panelmenu.add(btnFrekuensiRanap); 
                Panelmenu.add(btnFrekuensiRanapPerDokter);
                Panelmenu.add(btnFrekuensiRalanDanRanap);
                Panelmenu.add(btnFrekuensiPenyakitRanapBaru);
                jmlmenu++;
            }

            if(var.getkunjungan_ralan()==true){  
                Panelmenu.add(btnLapKunjunganPasien);  
                Panelmenu.add(btnKunjunganRalan);  
                jmlmenu++;
            }

            if(var.getkunjungan_ranap()==true){  
                Panelmenu.add(btnKunjunganRanap); 
                jmlmenu++;
            }
            
            if(var.getkunjungan_permintaan_lab()==true){  
                Panelmenu.add(btnKunjunganLabRalan); 
                jmlmenu++;
            }
            
            if(var.getkunjungan_permintaan_lab2()==true){  
                Panelmenu.add(btnKunjunganLabRanap); 
                jmlmenu++;
            }
            
            if(var.getkunjungan_permintaan_radiologi()==true){  
                Panelmenu.add(btnKunjunganRadRalan); 
                jmlmenu++;
            }
            
            if(var.getkunjungan_permintaan_radiologi2()==true){  
                Panelmenu.add(btnKunjunganRadRanap); 
                jmlmenu++;
            }

            if(var.getsensus_harian_poli()==true){  
                Panelmenu.add(btnSensusHarianPoli);  
                jmlmenu++;
            }
            
            if(var.getsensus_harian_ralan()==true){  
                Panelmenu.add(btnSensusHarianRalan);  
                jmlmenu++;
            }

            if(var.getrl32()==true){  
                Panelmenu.add(btnRl31);
                Panelmenu.add(btnRl32); 
                Panelmenu.add(btnRl35);   
                jmlmenu++;
            }

            if(var.getrl33()==true){  
                Panelmenu.add(btnRl33);                 
                jmlmenu++;
            }

            if(var.getrl34()==true){  
                Panelmenu.add(btnRl34);   
                jmlmenu++;
            }

            if(var.getrl36()==true){  
                Panelmenu.add(btnRl36);  
                jmlmenu++;
            }

            if(var.getrl37()==true){  
                Panelmenu.add(btnRl37); 
                jmlmenu++;
            }

            if(var.getrl38()==true){  
                Panelmenu.add(btnRl38);  
                jmlmenu++;
            }

            if(var.getrl4a()==true){  
                Panelmenu.add(btnRl4a);                 
                jmlmenu++;
            }
            
            if(var.getrl4b()==true){  
                Panelmenu.add(btnRl4b);                 
                jmlmenu++;
            }
            
            if(var.getrl4asebab()==true){  
                Panelmenu.add(btnRl4asebab);                 
                jmlmenu++;
            }
            
            if(var.getrl4bsebab()==true){  
                Panelmenu.add(btnRl4bsebab);                 
                jmlmenu++;
            }
            
            if(var.getlama_pelayanan_ralan()==true){  
                Panelmenu.add(btnLamaPelayananRalan);  
                Panelmenu.add(btnPelayananPenunjang);  
                jmlmenu++;
            }
            
            if(var.getlama_pelayanan_apotek()==true){  
                Panelmenu.add(btnLamaPelayananApotek);                 
                jmlmenu++;
            }
            
            if(var.getlama_pelayanan_radiologi()==true){  
                Panelmenu.add(btnLamaPelayananRadiologi);                 
                Panelmenu.add(btnRekapKegiatanRad);                 
                jmlmenu++;
            }
            
            if(var.getlama_pelayanan_lab()==true){  
                Panelmenu.add(btnLamaPelayananLab);                 
                jmlmenu++;
            }
            
            if(var.getharian_HAIs()==true){  
                Panelmenu.add(btnHarianHAIs);                 
                jmlmenu++;
            }
            
            if(var.getbulanan_HAIs()==true){  
                Panelmenu.add(btnBulananHAIs);                 
                jmlmenu++;
            }
            
            if(var.gethais_perbangsal()==true){  
                Panelmenu.add(btnHAIsBangsal);                 
                jmlmenu++;
            }
            
            if(var.gethitung_bor()==true){  
                Panelmenu.add(btnHitungBor);                 
                jmlmenu++;
            }
            
            if(var.gethitung_alos()==true){  
                Panelmenu.add(btnHitungAlos);                 
                jmlmenu++;
            }
            
            if(var.getpembatalan_periksa_dokter()==true){  
                Panelmenu.add(btnPembatalanPeriksaDokter);                 
                jmlmenu++;
            }
            
            if(var.getcek_entry_ralan()==true){  
                Panelmenu.add(btnCekEntryRalan);                 
                jmlmenu++;
            }
            
            if(var.getranap_per_ruang()==true){  
                Panelmenu.add(btnRanapPerRuang);                 
                jmlmenu++;
            }
            
            if(var.getpenyakit_ranap_cara_bayar()==true){  
                Panelmenu.add(btnPenyakitRanapCaraBayar);                 
                jmlmenu++;
            }
            
            if(var.getrekap_lab_pertahun()==true){  
                Panelmenu.add(btnRekapLabPerTahun);                 
                jmlmenu++;
            }
            
            if(var.getperujuk_lab_pertahun()==true){  
                Panelmenu.add(btnPerujukLabPerTahun);                 
                jmlmenu++;
            }
            
            if(var.getrekap_radiologi_pertahun()==true){  
                Panelmenu.add(btnRekapRadiologiPerTahun);                 
                jmlmenu++;
            }

            if(var.getperujuk_radiologi_pertahun()==true){  
                Panelmenu.add(btnPerujukRadiologiPerTahun);                 
                jmlmenu++;
            }
            
            if(var.getjumlah_porsi_diet()==true){  
                Panelmenu.add(btnJumlahPorsiDiet);                 
                jmlmenu++;
            }
            
            if(var.getjumlah_macam_diet()==true){  
                Panelmenu.add(btnJumlahMacamDiet);                 
                jmlmenu++;
            }
            
            if(var.getkemenkes_sitt()==true){  
                Panelmenu.add(btnDataSITT);                 
                jmlmenu++;
            }
            
        }else if(cmbMenu.getSelectedIndex()==7){   
            jmlmenu=0;
            if(var.getkamar()==true){
                Panelmenu.add(btnKamar);
                jmlmenu++;
            }             

            if(var.gettarif_ralan()==true){
                Panelmenu.add(btnTindakanRalan);
                jmlmenu++;
            }

            if(var.gettarif_ranap()==true){
                Panelmenu.add(btnTindakanRanap);
                jmlmenu++;
            }

            if(var.gettarif_lab()==true){
                Panelmenu.add(btnTarifLab);
                jmlmenu++;
            }

            if(var.gettarif_radiologi()==true){
                Panelmenu.add(btnTarifRadiologi);
                jmlmenu++;
            }          

            if(var.gettarif_operasi()==true){
                Panelmenu.add(btnPaketOperasi);
                jmlmenu++;
            }

            if(var.gettarif_utd()==true){
                Panelmenu.add(btnTarifUtd);
                jmlmenu++;
            }

            if(var.getakun_rekening()==true){
                Panelmenu.add(btnRekening);  
                jmlmenu++;
            }    

            if(var.getrekening_tahun()==true){
                Panelmenu.add(btnRekeningTahun);   
                Panelmenu.add(btnLapRBA);   
                jmlmenu++;
            } 
            
            if(var.getsaldo_akun_perbulan()==true){
                Panelmenu.add(btnSaldoAkunPerBulan);   
                jmlmenu++;
            }

            if(var.getakun_bayar()==true){
                Panelmenu.add(btnakun_bayar);  
                jmlmenu++;
            }

            if(var.getakun_piutang()==true){
                Panelmenu.add(btnAkunPiutang);  
                jmlmenu++;
            }

            if(var.getpengaturan_rekening()==true){
                Panelmenu.add(btnPengaturanRekening);  
                Panelmenu.add(btnSetSubAkun);
                jmlmenu++;
            } 

            if(var.getpengeluaran()==true){
                Panelmenu.add(btnPengeluaran);
                jmlmenu++;
            }

            if(var.getpemasukan_lain()==true){
                Panelmenu.add(btnPemasukanLain);
                jmlmenu++;
            }

            if(var.getdeposit_pasien()==true){                          
                Panelmenu.add(btnDeposit);
                jmlmenu++;
            }

            if(var.getpiutang_pasien()==true){
               Panelmenu.add(btnLihatPiutang); 
               jmlmenu++;
            }

            if(var.getrincian_piutang_pasien()==true){
               Panelmenu.add(btnRincianPiutangPasien); 
               jmlmenu++;
            }

            if(var.getpiutang_pasien2()==true){
               Panelmenu.add(btnPiutangBelumLunas); 
               jmlmenu++;
            }
            
            if(var.getdetail_piutang_penjab()==true){
               Panelmenu.add(btnPiutangPerCaraBayar); 
               jmlmenu++;
            }

            if(var.getbayar_piutang()==true){
               Panelmenu.add(btnBayarPiutang); 
               jmlmenu++;
            }

            if(var.gethutang_obat()==true){
                Panelmenu.add(btnHutangObat);
                jmlmenu++;
            }

            if(var.getbayar_pemesanan_obat()==true){
                Panelmenu.add(btnbayar_pemesanan);
                jmlmenu++;
            }
            
            if(var.gethutang_barang_non_medis()==true){
                Panelmenu.add(btnHutangNonMedis);
                jmlmenu++;
            }
            
            if(var.getbayar_pesan_non_medis()==true){
                Panelmenu.add(btnBayarPesanNonMedis);
                jmlmenu++;
            }

            if(var.getposting_jurnal()==true){
                Panelmenu.add(btnPostingJurnal); 
                jmlmenu++;
            }

            if(var.getjurnal_harian()==true){
                Panelmenu.add(btnJurnalHarian);  
                jmlmenu++;
            }

            if(var.getbuku_besar()==true){
                Panelmenu.add(btnBubes);
                jmlmenu++;
            }  

            if(var.getcashflow()==true){
                Panelmenu.add(btnCashFlow);
                jmlmenu++;
            }

            if(var.getkeuangan()==true){
                Panelmenu.add(btnLabaRugi);
                jmlmenu++;
            }                               

        }else if(cmbMenu.getSelectedIndex()==8){ 
            jmlmenu=0;
            if(var.getbpjs_cek_kartu()==true){
                Panelmenu.add(btnCekBPJSKartu);
                jmlmenu++;
            }    

            if(var.getbpjs_cek_nik()==true){
                Panelmenu.add(btnCekBPJSNik);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_skdp()==true){
                Panelmenu.add(btnCekBPJSSKDP);
                jmlmenu++;
            }

            if(var.getbpjs_cek_riwayat()==true){
                Panelmenu.add(btnCekBPJSRiwayatRujukanPCare);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_riwayat_rujukanrs()==true){
                Panelmenu.add(btnCekBPJSRiwayatRujukanRS);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_tanggal_rujukan()==true){
                Panelmenu.add(btnCekBPJSTanggalRujukan);
                jmlmenu++;
            }

            if(var.getbpjs_cek_nomor_rujukan()==true){
                Panelmenu.add(btnCekBPJSNomorRujukanPCare);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_nomor_rujukan_rs()==true){
                Panelmenu.add(btnCekBPJSNomorRujukanRS);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_rujukan_kartu_pcare()==true){
                Panelmenu.add(btnCekBPJSRujukanKartuPCare);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_rujukan_kartu_rs()==true){
                Panelmenu.add(btnCekBPJSRujukanKartuRS);
                jmlmenu++;
            }

            if(var.getbpjs_referensi_diagnosa()==true){
                Panelmenu.add(btnCekBPJSDiagnosa);
                jmlmenu++;
            }

            if(var.getbpjs_referensi_poli()==true){
                Panelmenu.add(btnCekBPJSPoli);
                jmlmenu++;
            }
            
            if(var.getmapping_poli_bpjs()==true){
                Panelmenu.add(btnMapingPoliBPJS);
                Panelmenu.add(btnMapingDokterDPJP);
                Panelmenu.add(btnMyLimsMapping);
                jmlmenu++;
            }

            if(var.getbpjs_referensi_faskes()==true){
                Panelmenu.add(btnCekBPJSFaskes);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_prosedur()==true){
                Panelmenu.add(btnCekReferensiProsedurBPJS);
                jmlmenu++;
            }

            if(var.getbpjs_cek_kelas_rawat()==true){
                Panelmenu.add(btnCekReferensiKelasRawatBPJS);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_dokter()==true){
                Panelmenu.add(btnCekReferensiDokterBPJS);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_spesialistik()==true){
                Panelmenu.add(btnCekReferensiSpesialistikBPJS);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_ruangrawat()==true){
                Panelmenu.add(btnCekReferensiRuangRawatBPJS);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_carakeluar()==true){
                Panelmenu.add(btnCekReferensiCaraKeluarBPJS);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_pasca_pulang()==true){
                Panelmenu.add(btnCekReferensiPascaPulangBPJS);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_propinsi()==true){
                Panelmenu.add(btnCekReferensiPropinsiBPJS);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_kabupaten()==true){
                Panelmenu.add(btnCekReferensiKabupatenBPJS);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_kecamatan()==true){
                Panelmenu.add(btnCekReferensiKecamatanBPJS);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_dokterdpjp()==true){
                Panelmenu.add(btnCekReferensiDokterDPJPBPJS);
                jmlmenu++;
            }
            
            if(var.getbpjs_sep()==true){
                Panelmenu.add(btnBPJSSEP);
                Panelmenu.add(btnSurkonBPJS);
                jmlmenu++;
            }
            
            if(var.getbpjs_cek_sep()==true){
                Panelmenu.add(btnCekSEP);
                jmlmenu++;
            }
            
            if(var.getbpjs_rujukan_keluar()==true){
                Panelmenu.add(btnRujukanKeluarBPJS);
                jmlmenu++;
            }

            if(var.getbpjs_monitoring_klaim()==true){
                Panelmenu.add(btnMonitoringKlaim);
                Panelmenu.add(btnMonitoringKunjungan);
                jmlmenu++;
            }

            if(var.getskdp_bpjs()==true){
                Panelmenu.add(btnSKDPBPJS);
                jmlmenu++;
            }
            
            if(var.getreklasifikasi_ralan()==true){
                Panelmenu.add(btnReklasifikasiRalan);
                jmlmenu++;
            }

            if(var.getreklasifikasi_ranap()==true){
                Panelmenu.add(btnReklasifikasiRanap);
                jmlmenu++;
            }

            if(var.getaplicare_referensi_kamar()==true){
                Panelmenu.add(btnAplicareReferensiKamar);
                jmlmenu++;
            }

            if(var.getaplicare_ketersediaan_kamar()==true){
                Panelmenu.add(btnAplicareKetersediaanKamar);
                jmlmenu++;
            }

            if(var.getinacbg_coder_nik()==true){
                Panelmenu.add(btnInaCBGCoderNIK);
                jmlmenu++;
            }

            if(var.getinacbg_klaim_baru_otomatis()==true){
                Panelmenu.add(btnInaCBGKlaimBaruOtomatis);
                jmlmenu++;
            } 

            if(var.getinacbg_klaim_baru_manual()==true){
                Panelmenu.add(btnInaCBGKlaimBaruManual);
                jmlmenu++;
            } 
            
            if(var.getinacbg_klaim_baru_manual2()==true){
                Panelmenu.add(btnInaCBGKlaimBaruManual2);
                jmlmenu++;
            } 

            if(var.getinhealth_referensi_jenpel_ruang_rawat()==true){
                Panelmenu.add(btnReferensiKamarInhealth);
                jmlmenu++;
            }

            if(var.getinhealth_referensi_poli()==true){
                Panelmenu.add(btnCekInhealthPoli);
                jmlmenu++;
            }

            if(var.getinhealth_referensi_faskes()==true){
                Panelmenu.add(btnCekInhealthFaskes);
                jmlmenu++;
            }

            if(var.getinhealth_cek_eligibilitas()==true){
                Panelmenu.add(btnCekEligibilitasInhealth);
                jmlmenu++;
            }

            if(var.getinhealth_sjp()==true){
                Panelmenu.add(btnInhealthSJP);
                jmlmenu++;
            }
            
            if(var.getsisrute_referensi_faskes()==true){
                Panelmenu.add(btnCekSisruteFaskes);
                jmlmenu++;
            }
            
            if(var.getsisrute_referensi_alasanrujuk()==true){
                Panelmenu.add(btnCekSisruteAlasanRujuk);
                jmlmenu++;
            }
            
            if(var.getsisrute_referensi_diagnosa()==true){
                Panelmenu.add(btnCekSisruteDiagnosa);
                jmlmenu++;
            }
            
            if(var.getsisrute_rujukan_masuk()==true){
                Panelmenu.add(btnRujukanMasukSisrute);
                jmlmenu++;
            }
            
            if(var.getsisrute_rujukan_keluar()==true){
                Panelmenu.add(btnRujukanKeluarSisrute);
                jmlmenu++;
            }
            
            if(var.getsiranap_ketersediaan_kamar()==true){
                Panelmenu.add(btnSiranapKetersediaanKamar);
                jmlmenu++;
            }
        }else if(cmbMenu.getSelectedIndex()==9){ 
            jmlmenu=0;
            if(var.getperusahaan_pasien()==true){
                Panelmenu.add(btnPerusahaan);
                jmlmenu++;
            }
            
            if(var.getsuku_bangsa()==true){
                Panelmenu.add(btnSuku);
                jmlmenu++;
            }
            
            if(var.getbahasa_pasien()==true){
                Panelmenu.add(btnBahasa);
                jmlmenu++;
            }    
            
            if(var.getcacat_fisik()==true){
                Panelmenu.add(btnCacatFisik);
                jmlmenu++;
            }
            
            if(var.getpasien()==true){
                Panelmenu.add(btnPasien);
                jmlmenu++;
            }

            if(var.getkelahiran_bayi()==true){
                Panelmenu.add(btnLahir);
                jmlmenu++;
            }
            
            if(var.getcatatan_pasien()==true){
                Panelmenu.add(btnCatatanPasien);
                jmlmenu++;               
            }

            if(var.getpasien_meninggal()==true){
                Panelmenu.add(btnPasienMati);
                Panelmenu.add(btnKerohanian);
                Panelmenu.add(btnPermintaanMPP);
                jmlmenu++;
            }

            if(var.getdiagnosa_pasien()==true){
                Panelmenu.add(btnDiagnosa); 
                jmlmenu++;
            }
            
            if(var.getinsiden_keselamatan()==true){
                Panelmenu.add(btnInsidenKeselamatan);
                jmlmenu++;
            }
            
            if(var.getdata_HAIs()==true){
                Panelmenu.add(btnDataHAIs);  
                jmlmenu++;
            }
            
            if(var.getinsiden_keselamatan_pasien()==true){
                Panelmenu.add(btnInsidenKeselamatanPasien); 
                jmlmenu++;
            }

            if(var.getpeminjaman_berkas()==true){
               Panelmenu.add(btnSirkulasiBerkas); 
               jmlmenu++;
            }

            if(var.getresume_pasien()==true){
                Panelmenu.add(btnResume);
                jmlmenu++;
            }   

            if(var.getretensi_rm()==true){
                Panelmenu.add(btnRetensiRM);
                jmlmenu++;
            }

            if(var.getmutasi_berkas()==true){
                Panelmenu.add(btnMutasiBerkas);
                jmlmenu++;
            } 
            
            if(var.getberkas_digital_perawatan()==true){
                Panelmenu.add(btnBerkasDigitalPerawatan);
                jmlmenu++;
            } 
            
            if(var.getpengaduan_pasien()==true){
                Panelmenu.add(btnPengaduan);
                jmlmenu++;
            } 
        }else if(cmbMenu.getSelectedIndex()==10){  
            jmlmenu=0;
            if(var.getpengambilan_utd2()==true){
                Panelmenu.add(btnPengambilanUTD2); 
                jmlmenu++;
            } 

            if(var.getutd_medis_rusak()==true){
                Panelmenu.add(btnUTDMedisRusak); 
                jmlmenu++;
            } 

            if(var.getpengambilan_penunjang_utd2()==true){
                Panelmenu.add(btnPengambilanPenunjangUTD2); 
                jmlmenu++;
            }

            if(var.getutd_penunjang_rusak()==true){
                Panelmenu.add(btnUTDPenunjangRusak); 
                jmlmenu++;
            } 

            if(var.getutd_komponen_darah()==true){
                Panelmenu.add(btnUTDKomponenDarah); 
                jmlmenu++;
            } 

            if(var.getutd_donor()==true){
                Panelmenu.add(btnUTDDonorDarah); 
                jmlmenu++;
            } 

            if(var.getutd_cekal_darah()==true){
                Panelmenu.add(btnUTDCekalDarah); 
                jmlmenu++;
            } 

            if(var.getutd_pemisahan_darah()==true){
                Panelmenu.add(btnUTDPemisahanDarah); 
                jmlmenu++;
            } 

            if(var.getutd_stok_darah()==true){
                Panelmenu.add(btnUTDStokDarah); 
                jmlmenu++;
            } 

            if(var.getutd_penyerahan_darah()==true){
                Panelmenu.add(btnUTDPenyerahanDarah); 
                Panelmenu.add(btnUTDPermintaan); 
                jmlmenu++;
            } 
        }else if(cmbMenu.getSelectedIndex()==11){
            jmlmenu=0;
            if(var.getsurat_indeks()==true){
                Panelmenu.add(btnSuratIndeks);
                jmlmenu++;
            }
            
            if(var.getsurat_map()==true){
                Panelmenu.add(btnSuratMap);
                jmlmenu++;
            }
            
            if(var.getsurat_almari()==true){
                Panelmenu.add(btnSuratAlmari);
                jmlmenu++;
            }
            
            if(var.getsurat_rak()==true){
                Panelmenu.add(btnSuratRak);
                jmlmenu++;
            }
            
            if(var.getsurat_ruang()==true){
                Panelmenu.add(btnSuratRuang);
                jmlmenu++;
            }
            
            if(var.getsurat_klasifikasi()==true){
                Panelmenu.add(btnSuratKlasifikasi);
                jmlmenu++;
            }
            
            if(var.getsurat_status()==true){
                Panelmenu.add(btnSuratStatus);
                jmlmenu++;
            }
            
            if(var.getsurat_sifat()==true){
                Panelmenu.add(btnSuratSifat);
                jmlmenu++;
            }
            
            if(var.getsurat_balas()==true){
                Panelmenu.add(btnSuratBalas);
                jmlmenu++;
            }
            
            if(var.getsurat_masuk()==true){
                Panelmenu.add(btnSuratMasuk);
                jmlmenu++;
            }
            
            if(var.getsurat_keluar()==true){
                Panelmenu.add(btnSuratKeluar);
                jmlmenu++;
            }
        }else if(cmbMenu.getSelectedIndex()==12){   
            jmlmenu=0;
            if(var.getaplikasi()==true){
                Panelmenu.add(btnSetupAplikasi);
                jmlmenu++;
            }

            if(var.getadmin()==true){
                Panelmenu.add(btnAdmin);
                Panelmenu.add(btnSetHariLibur);
                Panelmenu.add(btnTrialSendWA);
                Panelmenu.add(btnSetPaketMCU);
                Panelmenu.add(btnPindahRiw);
                jmlmenu++;
            }

            if(var.getsetup_pjlab()==true){
                Panelmenu.add(btnSetPenjab);
                Panelmenu.add(btnSetPenjabBNM);
                Panelmenu.add(btnSetKompUtd);
//                jmlmenu++;
            }

            if(var.getsetup_otolokasi()==true){
                Panelmenu.add(btnSetupOtoLokasi);
                jmlmenu++;
            }

            if(var.getsetup_jam_kamin()==true){
                Panelmenu.add(btnSetupJamInap);
                jmlmenu++;
            }

            if(var.getset_harga_kamar()==true){
                Panelmenu.add(btnSetHargaKamar);
                jmlmenu++;
            }

            if(var.getsetup_embalase()==true){
                Panelmenu.add(btnSetupEmbalase);
                jmlmenu++;
            }                                

            if(var.getuser()==true){
                Panelmenu.add(btnUser);
                jmlmenu++;
            }

            if(var.gettracer_login()==true){
                Panelmenu.add(btnTracker);
                jmlmenu++;
            }

            if(var.getvakum()==true){
                Panelmenu.add(btnVakum);
                jmlmenu++;
            }

            if(var.getdisplay()==true){
               Panelmenu.add(btnDisplay); 
               jmlmenu++;
            }
            
            if(var.getdisplay_apotek()==true){
               Panelmenu.add(btnDisplayApotek); 
               jmlmenu++;
            }

            if(var.getset_harga_obat()==true){
                Panelmenu.add(btnSetupHarga);
                jmlmenu++;
            }

            if(var.getset_harga_obat_ralan()==true){
                Panelmenu.add(btnSetObatRalan);
                jmlmenu++;
            }

            if(var.getset_harga_obat_ranap()==true){
                Panelmenu.add(btnSetObatRanap);
                jmlmenu++;
            }

            if(var.getset_penggunaan_tarif()==true){
                Panelmenu.add(btnSetupTarif);
                jmlmenu++;
            }

            if(var.getset_oto_ralan()==true){
                Panelmenu.add(btnSetOtoRalan);
                jmlmenu++;
            }

            if(var.getbiaya_harian()==true){
                Panelmenu.add(btnSetBiayaHarian);
                jmlmenu++;
            }

            if(var.getbiaya_masuk_sekali()==true){
                Panelmenu.add(btnSetBiayaMasukSekali);
                jmlmenu++;
            }

            if(var.getset_no_rm()==true){
                Panelmenu.add(btnSetupRM);
                jmlmenu++;
            }

            if(var.getset_nota()==true){
                Panelmenu.add(btnSetupNota);
                jmlmenu++;
            }

            if(var.getclosing_kasir()==true){
                Panelmenu.add(btnClosingKasir);
                jmlmenu++;
            }

            if(var.getketerlambatan_presensi()==true){
                Panelmenu.add(btnKeterlambatanPresensi);
                jmlmenu++;
            }
            
            if(var.getset_input_parsial()==true){
                Panelmenu.add(btnSetInputParsial);
                jmlmenu++;
            }
            
            if(var.getpassword_asuransi()==true){
                Panelmenu.add(btnPasswordAsuransi);              
                jmlmenu++;
            }
        }    
    }

    private void isCariKosong() {
        jmlmenu=0;     
        if(var.getinformasi_kamar()==true){
            Panelmenu.add(btnAnalisaKamar);
            jmlmenu++;
        }

        if(var.getjadwal_praktek()==true){                
            Panelmenu.add(BtnJadwal);
            jmlmenu++;
        }

        if(var.getregistrasi()==true){
            Panelmenu.add(btnRegistrasi);
            jmlmenu++;
        }

        if(var.getbooking_registrasi()==true){
            Panelmenu.add(btnBookingRegistrasi);
            Panelmenu.add(btnBookingMJKN);
            jmlmenu++;
        }

        if(var.getigd()==true){
            Panelmenu.add(btnIGD);  
            jmlmenu++;
        }

        if(var.gettindakan_ralan()==true){
            Panelmenu.add(btnRalan); 
            jmlmenu++;
        }

        if((var.getkamar_inap()==true)||(var.getbilling_ranap()==true)||(var.gettindakan_ranap()==true)){
            Panelmenu.add(btnKamarInap);
            jmlmenu++;
        }
        
        if(var.getbooking_operasi()==true){
            Panelmenu.add(btnJadwalOperasi);
            jmlmenu++;
        }

        if((var.getpermintaan_lab()==true)||(var.getperiksa_lab()==true)){
            Panelmenu.add(btnPermintaanLab);
            jmlmenu++;
        }
        
        if((var.getpermintaan_radiologi()==true)||(var.getperiksa_radiologi()==true)){
            Panelmenu.add(btnPermintaanRadiologi);
            jmlmenu++;
        }

        if(var.getdpjp_ranap()==true){
            Panelmenu.add(BtnDpjp);
            jmlmenu++;
        }

        if(var.gettindakan_ranap()==true){
            Panelmenu.add(btnRanap);
            jmlmenu++;
        }

        if(var.getoperasi()==true){
            Panelmenu.add(btnTagihanOperasi);
            jmlmenu++;
        }

        if(var.getrujukan_keluar()==true){
            Panelmenu.add(btnRujukPasien);
            jmlmenu++;
        }

        if(var.getrujukan_masuk()==true){                
            Panelmenu.add(btnRujukMasuk);
            jmlmenu++;
        }

        if(var.getberi_obat()==true){
            Panelmenu.add(btnBeriObat);
            jmlmenu++;
        }

        if(var.getresep_pulang()==true){                
            Panelmenu.add(btnResepPulang);
            jmlmenu++;
        }

        if(var.getresep_obat()==true){
            Panelmenu.add(btnResepObat);
            jmlmenu++;
        }

        if(var.getdiet_pasien()==true){
            Panelmenu.add(btnDiet);
            Panelmenu.add(btnRekapDiet);
            jmlmenu++;
        }

        if(var.getperiksa_lab()==true){
            Panelmenu.add(btnLaboratorium);
            jmlmenu++;
        }

        if(var.getperiksa_radiologi()==true){
            Panelmenu.add(btnPeriksaRadiologi);   
            jmlmenu++;
        }

        if((var.getkasir_ralan()==true)||(var.getbilling_ralan()==true)){
            Panelmenu.add(btnKasir);     
            jmlmenu++;
        }

        if(var.getdeposit_pasien()==true){                          
            Panelmenu.add(btnDeposit);
            jmlmenu++;
        }

        if(var.getpiutang_pasien()==true){
           Panelmenu.add(btnLihatPiutang); 
           jmlmenu++;
        }            

        if(var.getpetugas()==true){
            Panelmenu.add(btnPegawai);
            jmlmenu++;
        }

        if(var.getdokter()==true){
            Panelmenu.add(btnDokter);
            jmlmenu++;
        }

        if(var.getbarcode()==true){
            Panelmenu.add(btnBarcode);  
            jmlmenu++;
        }

        if(var.getsidikjari()==true){
            Panelmenu.add(btnSidikJari);   
            jmlmenu++;
        }

        if(var.getjam_masuk()==true){
            Panelmenu.add(btnJamPresensi);  
            jmlmenu++;
        }

        if(var.getjadwal_pegawai()==true){
            Panelmenu.add(btnJadwalPegawai);   
            jmlmenu++;
            Panelmenu.add(btnJadwalTambahan);   
            jmlmenu++;
        }             

        if(var.getpresensi_harian()==true){
            Panelmenu.add(btnRekapHarian);
            jmlmenu++;
        }

        if(var.getpresensi_bulanan()==true){                
            Panelmenu.add(btnRekapBulanan);
            jmlmenu++;
        }

        if(var.gettemporary_presensi()==true){
            Panelmenu.add(btnTemporaryPresensi);   
            jmlmenu++;
        }

        Panelmenu.add(btnRekapPresensi);
        Panelmenu.add(btnRekapPresensi2);
        jmlmenu++;
        jmlmenu++;

        if((var.getpegawai_admin()==true)||(var.getpegawai_user()==true)){                
            Panelmenu.add(btnPenggajian); 
            jmlmenu++;
        }
        
        if(var.getmaster_berkas_pegawai()==true){                
            Panelmenu.add(btnMasterBerkasPegawai); 
            jmlmenu++;
        }

        if(var.getberkas_kepegawaian()==true){                
            Panelmenu.add(btnBerkasPegawai); 
            jmlmenu++;
        }

        if(var.getriwayat_jabatan()==true){                
            Panelmenu.add(btnRiwayatJabatan); 
            jmlmenu++;
        }
        
        if(var.getriwayat_pendidikan()==true){                
            Panelmenu.add(btnRiwayatPendidikan); 
            jmlmenu++;
        }
        
        if(var.getriwayat_naik_gaji()==true){                
            Panelmenu.add(btnRiwayatNaikGaji); 
            jmlmenu++;
        }
        
        if(var.getkegiatan_ilmiah()==true){                
            Panelmenu.add(btnKegiatanIlmiah); 
            jmlmenu++;
        }
        
        if(var.getriwayat_penghargaan()==true){                
            Panelmenu.add(btnRiwayatPenghargaan); 
            jmlmenu++;
        }

        if(var.getriwayat_penelitian()==true){                
            Panelmenu.add(btnRiwayatPenelitian); 
            jmlmenu++;
        }

        if(var.getindustrifarmasi()==true){
            Panelmenu.add(btnIndustriFarmasi); 
            jmlmenu++;
        }

        if(var.getsuplier()==true){
            Panelmenu.add(btnSuplier); 
            jmlmenu++;
        }

        if(var.getsatuan_barang()==true){
            Panelmenu.add(btnSatuan); 
            jmlmenu++;
        }
        
        if(var.getmetode_racik()==true){
            Panelmenu.add(btnMetodeRacik); 
            jmlmenu++;
        }

        if(var.getkonversi_satuan()==true){
            Panelmenu.add(btnKonversi); 
            jmlmenu++;
        }    

        if(var.getjenis_barang()==true){
            Panelmenu.add(btnJnsBarang);
            jmlmenu++;
        }

        if(var.getkategori_barang()==true){
            Panelmenu.add(btnKategoriBarang);
            jmlmenu++;
        }
        
        if(var.getgolongan_barang()==true){
            Panelmenu.add(btnGolonganBarang);
            jmlmenu++;
        }
        
        if(var.getobat()==true){
            Panelmenu.add(btnObat);
            Panelmenu.add(btn10Obat);
            Panelmenu.add(btnHibahObatBHP);
            Panelmenu.add(btnLapStokOp);
            Panelmenu.add(btnLapPelFar);
            Panelmenu.add(btnPelFar);
            Panelmenu.add(btnPaketObatOp);
            jmlmenu++;
        }

        if(var.getstok_opname_obat()==true){
            Panelmenu.add(btnOpname); 
            jmlmenu++;
        }

        if(var.getmutasi_barang()==true){
            Panelmenu.add(btnMutasiBarang); 
            jmlmenu++;
        }

        if(var.getstok_obat_pasien()==true){
            Panelmenu.add(btnStokObatPasien); 
            jmlmenu++;
        }

        if((var.getpermintaan_medis()==true)||(var.getmutasi_barang()==true)||(var.getpengeluaran_stok_apotek()==true)){
            Panelmenu.add(btnPermintaanMedis);   
            jmlmenu++;
        }
        
        if(var.getrekap_permintaan_medis()==true){
            Panelmenu.add(btnRekapPermintaanMedis);   
            jmlmenu++;
        }
        
        if((var.getsurat_pemesanan_medis()==true)||(var.getpemesanan_obat()==true)){
            Panelmenu.add(btnSuratPemesananMedis);   
            jmlmenu++;
        }

        if(var.getpengadaan_obat()==true){
            Panelmenu.add(btnPembelian);   
            jmlmenu++;
        }

        if((var.getpemesanan_obat()==true)||(var.getbayar_pemesanan_obat()==true)){
            Panelmenu.add(btnPemesanan);
            jmlmenu++;
        }

        if(var.getpenjualan_obat()==true){
            Panelmenu.add(btnPenjualan);
            jmlmenu++;
        }

        if(var.getresep_dokter()==true){
            Panelmenu.add(btnDaftarPermintaanResep);
            jmlmenu++;
        }

        if(var.getpiutang_obat()==true){
             Panelmenu.add(btnPiutang);  
             jmlmenu++;
        }
        
        if(var.getpengeluaran_stok_apotek()==true){
             Panelmenu.add(btnStokKeluarMedis);  
             jmlmenu++;
        }

        if(var.getretur_ke_suplier()==true){
            Panelmenu.add(btnReturBeli); 
            jmlmenu++;
        }

        if(var.getretur_dari_pembeli()==true){
            Panelmenu.add(btnReturJual); 
            jmlmenu++;
        }

        if(var.getretur_obat_ranap()==true){
            Panelmenu.add(btnReturPasien); 
            jmlmenu++;
        }

        if(var.getretur_piutang_pasien()==true){
            Panelmenu.add(btnReturPiutang); 
            jmlmenu++;
        }   

        if(var.getpengambilan_utd()==true){
            Panelmenu.add(btnPengambilanUTD); 
            jmlmenu++;
        } 

        if(var.getkeuntungan_penjualan()==true){
            Panelmenu.add(btnKeuntungan);
            jmlmenu++;
        }

        if(var.getkeuntungan_beri_obat()==true){
            Panelmenu.add(btnKeuntunganObatRanap);
            jmlmenu++;
        }

        if(var.getkeuntungan_beri_obat_nonpiutang()==true){
            Panelmenu.add(btnKeuntunganObat2);
            jmlmenu++;
        }

        if(var.getsirkulasi_obat()==true){
            Panelmenu.add(btnSirkulasi);
            jmlmenu++;
        }

        if(var.getsirkulasi_obat2()==true){
            Panelmenu.add(btnSirkulasi2);
            jmlmenu++;
        }
        
        if(var.getsirkulasi_obat3()==true){
            Panelmenu.add(btnSirkulasi3);
            Panelmenu.add(btnSirkulasiObat4);
            jmlmenu++;
        }

        if(var.getpemberian_obat_pertanggal()==true){
            Panelmenu.add(btnObatPerTanggal);
            jmlmenu++;
        }
        
        if(var.getpenjualan_obat_pertanggal()==true){
            Panelmenu.add(btnPenjualanPerTanggal);
            jmlmenu++;
        }

        if(var.getriwayat_obat_alkes_bhp()==true){
            Panelmenu.add(btnRiwayatBarangMedis);
            jmlmenu++;
        }

        if(var.getdarurat_stok()==true){
            Panelmenu.add(btnDaruratStok);
            jmlmenu++;
        }         

        if(var.getpengguna_obat_resep()==true){
            Panelmenu.add(btnPenggunaObatResep);
            jmlmenu++;
        }

        if(var.getrekap_pemesanan()==true){
            Panelmenu.add(btnRekapPenerimaanObat);
            jmlmenu++;
        }
        
        if(var.getdata_batch()==true){
            Panelmenu.add(btnDataBatch);
            jmlmenu++;
        }

        if(var.getriwayat_data_batch()==true){
            Panelmenu.add(btnRiwayatBatch);
            jmlmenu++;
        }
        
        if(var.getkegiatan_farmasi()==true){
            Panelmenu.add(btnKegiatanFarmasi);
            jmlmenu++;
        }
        
        if(var.getppn_obat()==true){
            Panelmenu.add(btnPPNObat);
            jmlmenu++;
        }
        
        if(var.getkadaluarsa_batch()==true){
            Panelmenu.add(btnKadaluarsaBatch);
            jmlmenu++;
        }
        
        if(var.getsisa_stok()==true){
            Panelmenu.add(btnSisaStok);
            jmlmenu++;
        }

        if(var.getobat_per_resep()==true){
            Panelmenu.add(btnObatPerResep);
            jmlmenu++;
        }

        if(var.getsatuan_barang()==true){
            Panelmenu.add(btnSatuan);  
            jmlmenu++;
        }             

        if(var.getipsrs_jenis_barang()==true){
            Panelmenu.add(btnJenisIpsrs); 
            jmlmenu++;
        } 

        if(var.getipsrs_barang()==true){
            Panelmenu.add(btnBarangIpsrs);  
            jmlmenu++;
        } 

        if(var.getsuplier_penunjang()==true){
            Panelmenu.add(btnSuplierIPSRS);  
            jmlmenu++;
        }             

        if(var.getpermintaan_non_medis()==true){
            Panelmenu.add(btnPermintaanNonMedis);  
            jmlmenu++;
        }
        
        if(var.getrekap_permintaan_non_medis()==true){
            Panelmenu.add(btnRekapPermintaanNonMedis);  
            jmlmenu++;
        }
        
        if(var.getsurat_pemesanan_non_medis()==true){
            Panelmenu.add(btnSuratPemesananNonMedis);  
            jmlmenu++;
        }

        if(var.getipsrs_pengadaan_barang()==true){
            Panelmenu.add(btnPembelianIpsrs);  
            jmlmenu++;
        }

        if(var.getpenerimaan_non_medis()==true){
            Panelmenu.add(btnPenerimaanNonMedis);  
            jmlmenu++;
        }

        if(var.getipsrs_stok_keluar()==true){
            Panelmenu.add(btnPengeluaranIpsrs); 
            jmlmenu++;
        }

        if(var.getipsrs_pengeluaran_harian()==true){
            Panelmenu.add(btnRBiayaIpsrs);
            jmlmenu++;
        }

        if(var.getipsrs_rekap_pengadaan()==true){
            Panelmenu.add(btnRHMasukIpsrs);
            jmlmenu++;
        }

        if(var.getipsrs_rekap_stok_keluar()==true){
            Panelmenu.add(btnRHKeluarIpsrs);
            jmlmenu++;
        } 

        if(var.getpengambilan_penunjang_utd()==true){
            Panelmenu.add(btnPengambilanPenunjangUTD);
            jmlmenu++;
        } 

        if(var.getipsrs_pengadaan_pertanggal()==true){
            Panelmenu.add(btnPengadaanIPSRSPerTanggal);
            jmlmenu++;
        }
        
        if(var.getipsrs_stokkeluar_pertanggal()==true){
            Panelmenu.add(btnStokKeluarIPSRSPerTanggal);
            jmlmenu++;
        } 
        
        if(var.getrekap_pemesanan_non_medis()==true){
            Panelmenu.add(btnRekapPenerimaanNonMedis);
            jmlmenu++;
        }
        
        if(var.getstok_opname_logistik()==true){
            Panelmenu.add(btnOpnameIPSRS);
            jmlmenu++;
        }
        
        if(var.getsirkulasi_non_medis()==true){
            Panelmenu.add(btnSirkulasiNonMedis);
            jmlmenu++;
        }

        if(var.getinventaris_jenis()==true){
            Panelmenu.add(btnJenisInventaris);  
            jmlmenu++;
        }   

        if(var.getinventaris_kategori()==true){
            Panelmenu.add(btnKategoriInventaris);
            jmlmenu++;
        }

        if(var.getinventaris_merk()==true){
            Panelmenu.add(btnMerkInventaris);
            jmlmenu++;
        }

        if(var.getinventaris_ruang()==true){
            Panelmenu.add(btnRuangInventaris);
            jmlmenu++;
        }

        if(var.getinventaris_produsen()==true){
            Panelmenu.add(btnProdusenInventaris);
            jmlmenu++;
        }

        if(var.getinventaris_koleksi()==true){
            Panelmenu.add(btnBarangInventaris);
            jmlmenu++;
        }

        if(var.getinventaris_inventaris()==true){
            Panelmenu.add(btnInventaris);  
            jmlmenu++;
        }                 

        if(var.getinventaris_sirkulasi()==true){
            Panelmenu.add(btnSirkulasiInventaris);
            jmlmenu++;
        }     

        if(var.getbarang_cssd()==true){
            Panelmenu.add(btnBarangCSSD);
            jmlmenu++;
        } 
        
        if(var.getpemakaian_air_pdam()==true){
            Panelmenu.add(btnPemakaianAirPDAM);
            jmlmenu++;
        }
        
        if(var.getlimbah_b3_medis()==true){
            Panelmenu.add(btnLimbahB3Medis);
            jmlmenu++;
        } 
//        if(var.getkamar_inap()==true|| var.getregistrasi()==true){
            Panelmenu.add(btnPermintaanRBA);
            jmlmenu++;
//        } 

        if(var.getharian_tindakan_poli()==true){
            Panelmenu.add(btnTagihanPoli); 
            jmlmenu++;
        }   

        if(var.getharian_kamar()==true){
            Panelmenu.add(btnHarianKamar); 
            jmlmenu++;
        }  

        if(var.getharian_tindakan_dokter()==true){
            Panelmenu.add(btnTagihanDokter); 
            jmlmenu++;
        } 

        if(var.getobat_per_poli()==true){
            Panelmenu.add(btnTagihanObatPoli);
            jmlmenu++;
        }

        if(var.getobat_per_kamar()==true){
            Panelmenu.add(btnTagihanObatBangsal);
            jmlmenu++;
        }

        if(var.getobat_per_dokter_ralan()==true){
            Panelmenu.add(btnObatPasienRalan);    
            jmlmenu++;
        }

        if(var.getobat_per_dokter_ranap()==true){
            Panelmenu.add(btnObatPasienRanap);
            jmlmenu++;
        }    

        if(var.getobat_per_dokter_peresep()==true){
            Panelmenu.add(btnObatPasienPeresep);
            jmlmenu++;
        }

        if(var.getobat_per_cara_bayar()==true){
            Panelmenu.add(btnObatPerCaraBayar);
            jmlmenu++;
        }

        if(var.getdetail_tindakan()==true){
            Panelmenu.add(btnDetailTindakan);
            jmlmenu++;
        } 

        if(var.getjm_ranap_dokter()==true){
            Panelmenu.add(btnJMDetailDokter);
            jmlmenu++;
        } 

        if(var.getdetailjmdokter2()==true){
            Panelmenu.add(btnJMDetailDokter2);
            jmlmenu++;
        } 

        if(var.getharian_dokter()==true){
            Panelmenu.add(btnRHDOkter);  
            jmlmenu++;
        }

        if(var.getbulanan_dokter()==true){
            Panelmenu.add(btnRBDokter);
            jmlmenu++;
        }

        if(var.getharian_paramedis()==true){
            Panelmenu.add(btnRHParamedis); 
            jmlmenu++;
        }

        if(var.getbulanan_paramedis()==true){
            Panelmenu.add(btnRBParamedis); 
            jmlmenu++;
        }

        if(var.getharian_js()==true){
            Panelmenu.add(btnRHJasaSarana);  
            jmlmenu++;
        }

        if(var.getbulanan_js()==true){
            Panelmenu.add(btnRBJasaSarana);  
            jmlmenu++;
        }

        if(var.getharian_kso()==true){
            Panelmenu.add(btnRHKSO);  
            jmlmenu++;
        }

        if(var.getbulanan_kso()==true){
            Panelmenu.add(btnRBKSO);  
            jmlmenu++;
        }

        if(var.getharian_menejemen()==true){
            Panelmenu.add(btnRHMenejemen);  
            jmlmenu++;
        }

        if(var.getbulanan_menejemen()==true){
            Panelmenu.add(btnRBMenejemen);  
            jmlmenu++;
        }

        if(var.getharian_paket_bhp()==true){
            Panelmenu.add(btnRHPaketBHP);  
            jmlmenu++;
        }

        if(var.getbulanan_paket_bhp()==true){
            Panelmenu.add(btnRBPaketBHP);  
            jmlmenu++;
        }

        if(var.getfee_visit_dokter()==true){
            Panelmenu.add(btnfee_visit_dokter); 
            jmlmenu++;
        }

        if(var.getfee_bacaan_ekg()==true){
            Panelmenu.add(btnfee_bacaan_ekg); 
            jmlmenu++;
        }

        if(var.getfee_rujukan_rontgen()==true){
            Panelmenu.add(btnfee_rujukan_rontgen); 
            jmlmenu++;
        }

        if(var.getfee_rujukan_ranap()==true){
            Panelmenu.add(btnfee_rujukan_ranap); 
            jmlmenu++;
        }

        if(var.getfee_ralan()==true){
            Panelmenu.add(btnfee_ralan); 
            jmlmenu++;
        }

        if(var.getdetail_tindakan_okvk()==true){
            Panelmenu.add(btnDetailVKOK);
            jmlmenu++;
        }
        
        if(var.getpembayaran_ralan()==true){
            Panelmenu.add(btnRalanMasuk);
            jmlmenu++;
        }

        if(var.getpembayaran_ranap()==true){
            Panelmenu.add(btnRanapMasuk);
            jmlmenu++;
        }

        if(var.getpiutang_ralan()==true){
            Panelmenu.add(btnPiutangRalan);
            jmlmenu++;
        }

        if(var.getpiutang_ranap()==true){
            Panelmenu.add(btnPiutangRanap);
            jmlmenu++;
        }

        if(var.getrekap_pembayaran_ralan()==true){
            Panelmenu.add(btnTagihanRalanPerhari);
            jmlmenu++;
        }

        if(var.getrekap_pembayaran_ranap()==true){
            Panelmenu.add(btnTagihanRanapPerhari);
            jmlmenu++;
        }

        if(var.getpembayaran_per_unit()==true){
            Panelmenu.add(btnPembayaranPerUnit);
            jmlmenu++;
        }
        
        if(var.getrekap_pembayaran_per_unit()==true){
            Panelmenu.add(btnRekapPembayaranPerUnit);
            jmlmenu++;
        }

        if(var.gettagihan_masuk()==true){
            Panelmenu.add(btnTagihanMasuk);
            jmlmenu++;
        }

        if(var.gettambahan_biaya()==true){
            Panelmenu.add(btnTambahanBiaya);
            jmlmenu++;
        }   

        if(var.getpotongan_biaya()==true){
            Panelmenu.add(btnPotonganBiaya);
            jmlmenu++;
        }                                                    

        if(var.getrekap_poli_anak()==true){
            Panelmenu.add(btnRekapPoliAnak);
            jmlmenu++;
        }

        if(var.getdeposit_pasien()==true){
            Panelmenu.add(btnDeposit);
            jmlmenu++;
        }

        if(var.getrekap_per_shift()==true){
            Panelmenu.add(btnRekapPershift);
            jmlmenu++;
        }            

        if(var.getpayment_point()==true){
            Panelmenu.add(btnPaymentPoint);
            jmlmenu++;
        }
        
        if(var.getpayment_point2()==true){
            Panelmenu.add(btnPaymentPoint2);
            jmlmenu++;
        }
        
        if(var.getpembayaran_akun_bayar()==true){
            Panelmenu.add(btnPembayaranAkunBayar);
            jmlmenu++;
        }
        
        if(var.getpembayaran_akun_bayar2()==true){
            Panelmenu.add(btnPembayaranAkunBayar2);
            jmlmenu++;
        }

        if(var.getpembayaran_akun_bayar3()==true){
            Panelmenu.add(btnPembayaranAkunBayar3);
            jmlmenu++;
        }
        
        if(var.getpiutang_akun_piutang()==true){
            Panelmenu.add(btnPiutangPerAkunPiutang);
            jmlmenu++;
        }

        if(var.geticd9()==true){
            Panelmenu.add(btnICD9);
            jmlmenu++;
        }

        if(var.getpenyakit()==true){
            Panelmenu.add(btnICD);
            jmlmenu++;
        }
        
        if(var.getpenyakit_pd3i()==true){
            Panelmenu.add(btnPenyakitPD3I);
            jmlmenu++;
        }

        if(var.getsurveilans_pd3i()==true){
            Panelmenu.add(btnSurveilansPD3I);
            jmlmenu++;
        }

        if(var.getsurveilans_ralan()==true){
            Panelmenu.add(btnSurveilansRalan);
            jmlmenu++;
        }

        if(var.getsurveilans_ranap()==true){
            Panelmenu.add(btnSurveilansRanap);
            jmlmenu++;
        }

        if(var.getpny_takmenular_ralan()==true){
            Panelmenu.add(btnPnyTakMenularRalan);
            jmlmenu++;
        }

        if(var.getpny_takmenular_ranap()==true){
            Panelmenu.add(btnPnyTakMenularRanap);
            jmlmenu++;
        }
        
        if(var.getpenyakit_menular_ralan()==true){
            Panelmenu.add(btnPnyMenularRalan);
            jmlmenu++;
        }

        if(var.getpenyakit_menular_ranap()==true){
            Panelmenu.add(btnPnyMenularRanap);
            jmlmenu++;
        }

        if(var.getobat_penyakit()==true){
            Panelmenu.add(btnObatPenyakit);
            jmlmenu++;
        }

        if(var.getpenyakit_ralan()==true){                
            Panelmenu.add(btnFrekuensiRalan); 
            Panelmenu.add(btnFrekuensiRalanDanRanap);
            Panelmenu.add(btnFrekuensiPerPerujuk);
            jmlmenu++;
        }

        if(var.getpenyakit_ranap()==true){  
            Panelmenu.add(btnFrekuensiRanap);
            Panelmenu.add(btnFrekuensiRanapPerDokter);
            Panelmenu.add(btnFrekuensiRalanDanRanap);
            Panelmenu.add(btnFrekuensiPenyakitRanapBaru);
            jmlmenu++;
        }

        if(var.getkunjungan_ralan()==true){  
            Panelmenu.add(btnLapKunjunganPasien);  
            Panelmenu.add(btnKunjunganRalan);  
            jmlmenu++;
        }

        if(var.getkunjungan_ranap()==true){  
            Panelmenu.add(btnKunjunganRanap); 
            jmlmenu++;
        }
        
        if(var.getkunjungan_permintaan_lab()==true){  
            Panelmenu.add(btnKunjunganLabRalan); 
            jmlmenu++;
        }
        
        if(var.getkunjungan_permintaan_lab2()==true){  
            Panelmenu.add(btnKunjunganLabRanap); 
            jmlmenu++;
        }
        
        if(var.getkunjungan_permintaan_radiologi()==true){  
            Panelmenu.add(btnKunjunganRadRalan); 
            jmlmenu++;
        }
        
        if(var.getkunjungan_permintaan_radiologi2()==true){  
            Panelmenu.add(btnKunjunganRadRanap); 
            jmlmenu++;
        }

        if(var.getsensus_harian_poli()==true){  
            Panelmenu.add(btnSensusHarianPoli);  
            jmlmenu++;
        }

        if(var.getsensus_harian_ralan()==true){  
            Panelmenu.add(btnSensusHarianRalan);  
            jmlmenu++;
        }

        if(var.getrl32()==true){  
            Panelmenu.add(btnRl31);
            Panelmenu.add(btnRl32); 
            Panelmenu.add(btnRl35);   
            jmlmenu++;
        }

        if(var.getrl33()==true){  
            Panelmenu.add(btnRl33);                 
            jmlmenu++;
        }

        if(var.getrl34()==true){  
            Panelmenu.add(btnRl34);   
            jmlmenu++;
        }

        if(var.getrl36()==true){  
            Panelmenu.add(btnRl36);  
            jmlmenu++;
        }

        if(var.getrl37()==true){  
            Panelmenu.add(btnRl37); 
            jmlmenu++;
        }

        if(var.getrl38()==true){  
            Panelmenu.add(btnRl38);  
            jmlmenu++;
        }

        if(var.getrl4a()==true){  
            Panelmenu.add(btnRl4a);                 
            jmlmenu++;
        }
        
        if(var.getrl4b()==true){  
            Panelmenu.add(btnRl4b);                 
            jmlmenu++;
        }
        
        if(var.getrl4asebab()==true){  
            Panelmenu.add(btnRl4asebab);                 
            jmlmenu++;
        }

        if(var.getrl4bsebab()==true){  
            Panelmenu.add(btnRl4bsebab);                 
            jmlmenu++;
        }
        
        if(var.getlama_pelayanan_ralan()==true){  
            Panelmenu.add(btnLamaPelayananRalan);  
            Panelmenu.add(btnPelayananPenunjang);  
            jmlmenu++;
        }
        
        if(var.getlama_pelayanan_apotek()==true){  
            Panelmenu.add(btnLamaPelayananApotek);                 
            jmlmenu++;
        }
        
        if(var.getlama_pelayanan_radiologi()==true){  
            Panelmenu.add(btnLamaPelayananRadiologi);                 
            Panelmenu.add(btnRekapKegiatanRad);                 
            jmlmenu++;
        }
        
        if(var.getlama_pelayanan_lab()==true){  
            Panelmenu.add(btnLamaPelayananLab);                 
            jmlmenu++;
        }

        if(var.getharian_HAIs()==true){  
            Panelmenu.add(btnHarianHAIs);                 
            jmlmenu++;
        }
        
        if(var.getbulanan_HAIs()==true){  
            Panelmenu.add(btnBulananHAIs);                 
            jmlmenu++;
        }
        
        if(var.gethais_perbangsal()==true){  
            Panelmenu.add(btnHAIsBangsal);                 
            jmlmenu++;
        }
        
        if(var.gethitung_bor()==true){  
            Panelmenu.add(btnHitungBor);                 
            jmlmenu++;
        }
        
        if(var.gethitung_alos()==true){  
            Panelmenu.add(btnHitungAlos);                 
            jmlmenu++;
        }
        
        if(var.getpembatalan_periksa_dokter()==true){  
            Panelmenu.add(btnPembatalanPeriksaDokter);                 
            jmlmenu++;
        }
        
        if(var.getcek_entry_ralan()==true){  
            Panelmenu.add(btnCekEntryRalan);                 
            jmlmenu++;
        }
        
        if(var.getranap_per_ruang()==true){  
            Panelmenu.add(btnRanapPerRuang);                 
            jmlmenu++;
        }
        
        if(var.getpenyakit_ranap_cara_bayar()==true){  
            Panelmenu.add(btnPenyakitRanapCaraBayar);                 
            jmlmenu++;
        }
        
        if(var.getrekap_lab_pertahun()==true){  
            Panelmenu.add(btnRekapLabPerTahun);                 
            jmlmenu++;
        }
        
        if(var.getperujuk_lab_pertahun()==true){  
            Panelmenu.add(btnPerujukLabPerTahun);                 
            jmlmenu++;
        }
        
        if(var.getrekap_radiologi_pertahun()==true){  
            Panelmenu.add(btnRekapRadiologiPerTahun);                 
            jmlmenu++;
        }
        
        if(var.getperujuk_radiologi_pertahun()==true){  
            Panelmenu.add(btnPerujukRadiologiPerTahun);                 
            jmlmenu++;
        }
        
        if(var.getjumlah_porsi_diet()==true){  
            Panelmenu.add(btnJumlahPorsiDiet);                 
            jmlmenu++;
        }
        
        if(var.getjumlah_macam_diet()==true){  
            Panelmenu.add(btnJumlahMacamDiet);                 
            jmlmenu++;
        }
        
        if(var.getkemenkes_sitt()==true){  
            Panelmenu.add(btnDataSITT);                 
            jmlmenu++;
        }

        if(var.getkamar()==true){
            Panelmenu.add(btnKamar);
            jmlmenu++;
        }             

        if(var.gettarif_ralan()==true){
            Panelmenu.add(btnTindakanRalan);
            jmlmenu++;
        }

        if(var.gettarif_ranap()==true){
            Panelmenu.add(btnTindakanRanap);
            jmlmenu++;
        }

        if(var.gettarif_lab()==true){
            Panelmenu.add(btnTarifLab);
            jmlmenu++;
        }

        if(var.gettarif_radiologi()==true){
            Panelmenu.add(btnTarifRadiologi);
            jmlmenu++;
        }          

        if(var.gettarif_operasi()==true){
            Panelmenu.add(btnPaketOperasi);
            jmlmenu++;
        }

        if(var.gettarif_utd()==true){
            Panelmenu.add(btnTarifUtd);
            jmlmenu++;
        }

        if(var.getakun_rekening()==true){
            Panelmenu.add(btnRekening);  
            jmlmenu++;
        }    

        if(var.getrekening_tahun()==true){
            Panelmenu.add(btnRekeningTahun);   
            Panelmenu.add(btnLapRBA);   
            jmlmenu++;
        } 
        
        if(var.getsaldo_akun_perbulan()==true){
            Panelmenu.add(btnSaldoAkunPerBulan);   
            jmlmenu++;
        }

        if(var.getakun_bayar()==true){
            Panelmenu.add(btnakun_bayar);  
            jmlmenu++;
        }

        if(var.getakun_piutang()==true){
            Panelmenu.add(btnAkunPiutang);  
            jmlmenu++;
        }

        if(var.getpengaturan_rekening()==true){
            Panelmenu.add(btnPengaturanRekening); 
            Panelmenu.add(btnSetSubAkun);
            jmlmenu++;
        } 

        if(var.getpengeluaran()==true){
            Panelmenu.add(btnPengeluaran);
            jmlmenu++;
        }

        if(var.getpemasukan_lain()==true){
            Panelmenu.add(btnPemasukanLain);
            jmlmenu++;
        }

        if(var.getdeposit_pasien()==true){                          
            Panelmenu.add(btnDeposit);
            jmlmenu++;
        }

        if(var.getpiutang_pasien()==true){
           Panelmenu.add(btnLihatPiutang); 
           jmlmenu++;
        }

        if(var.getrincian_piutang_pasien()==true){
           Panelmenu.add(btnRincianPiutangPasien); 
           jmlmenu++;
        }

        if(var.getpiutang_pasien2()==true){
           Panelmenu.add(btnPiutangBelumLunas); 
           jmlmenu++;
        }
        
        if(var.getdetail_piutang_penjab()==true){
            Panelmenu.add(btnPiutangPerCaraBayar); 
            jmlmenu++;
        }

        if(var.getbayar_piutang()==true){
           Panelmenu.add(btnBayarPiutang); 
           jmlmenu++;
        }

        if(var.gethutang_obat()==true){
            Panelmenu.add(btnHutangObat);
            jmlmenu++;
        }

        if(var.getbayar_pemesanan_obat()==true){
            Panelmenu.add(btnbayar_pemesanan);
            jmlmenu++;
        }
        
        if(var.gethutang_barang_non_medis()==true){
            Panelmenu.add(btnHutangNonMedis);
            jmlmenu++;
        }

        if(var.getbayar_pesan_non_medis()==true){
            Panelmenu.add(btnBayarPesanNonMedis);
            jmlmenu++;
        }
        
        if(var.getposting_jurnal()==true){
            Panelmenu.add(btnPostingJurnal); 
            jmlmenu++;
        }

        if(var.getjurnal_harian()==true){
            Panelmenu.add(btnJurnalHarian);  
            jmlmenu++;
        }

        if(var.getbuku_besar()==true){
            Panelmenu.add(btnBubes);
            jmlmenu++;
        }  

        if(var.getcashflow()==true){
            Panelmenu.add(btnCashFlow);
            jmlmenu++;
        }

        if(var.getkeuangan()==true){
            Panelmenu.add(btnLabaRugi);
            jmlmenu++;
        }                               


        if(var.getbpjs_cek_kartu()==true){
            Panelmenu.add(btnCekBPJSKartu);
            jmlmenu++;
        }    

        if(var.getbpjs_cek_nik()==true){
            Panelmenu.add(btnCekBPJSNik);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_skdp()==true){
            Panelmenu.add(btnCekBPJSSKDP);
            jmlmenu++;
        }

        if(var.getbpjs_cek_riwayat()==true){
            Panelmenu.add(btnCekBPJSRiwayatRujukanPCare);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_riwayat_rujukanrs()==true){
            Panelmenu.add(btnCekBPJSRiwayatRujukanRS);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_tanggal_rujukan()==true){
            Panelmenu.add(btnCekBPJSTanggalRujukan);
            jmlmenu++;
        }

        if(var.getbpjs_cek_nomor_rujukan()==true){
            Panelmenu.add(btnCekBPJSNomorRujukanPCare);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_nomor_rujukan_rs()==true){
            Panelmenu.add(btnCekBPJSNomorRujukanRS);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_rujukan_kartu_pcare()==true){
            Panelmenu.add(btnCekBPJSRujukanKartuPCare);
            jmlmenu++;
        }

        if(var.getbpjs_cek_rujukan_kartu_rs()==true){
            Panelmenu.add(btnCekBPJSRujukanKartuRS);
            jmlmenu++;
        }

        if(var.getbpjs_referensi_diagnosa()==true){
            Panelmenu.add(btnCekBPJSDiagnosa);
            jmlmenu++;
        }

        if(var.getbpjs_referensi_poli()==true){
            Panelmenu.add(btnCekBPJSPoli);
            jmlmenu++;
        }

        if(var.getmapping_poli_bpjs()==true){
            Panelmenu.add(btnMapingPoliBPJS);
            Panelmenu.add(btnMapingDokterDPJP);
            Panelmenu.add(btnMyLimsMapping);
            jmlmenu++;
        }

        if(var.getbpjs_referensi_faskes()==true){
            Panelmenu.add(btnCekBPJSFaskes);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_prosedur()==true){
            Panelmenu.add(btnCekReferensiProsedurBPJS);
            jmlmenu++;
        }

        if(var.getbpjs_cek_kelas_rawat()==true){
            Panelmenu.add(btnCekReferensiKelasRawatBPJS);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_dokter()==true){
            Panelmenu.add(btnCekReferensiDokterBPJS);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_spesialistik()==true){
            Panelmenu.add(btnCekReferensiSpesialistikBPJS);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_ruangrawat()==true){
            Panelmenu.add(btnCekReferensiRuangRawatBPJS);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_carakeluar()==true){
            Panelmenu.add(btnCekReferensiCaraKeluarBPJS);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_pasca_pulang()==true){
            Panelmenu.add(btnCekReferensiPascaPulangBPJS);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_propinsi()==true){
            Panelmenu.add(btnCekReferensiPropinsiBPJS);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_kabupaten()==true){
            Panelmenu.add(btnCekReferensiKabupatenBPJS);
            jmlmenu++;
        }

        if(var.getbpjs_cek_kecamatan()==true){
            Panelmenu.add(btnCekReferensiKecamatanBPJS);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_dokterdpjp()==true){
            Panelmenu.add(btnCekReferensiDokterDPJPBPJS);
            jmlmenu++;
        }

        if(var.getbpjs_sep()==true){
            Panelmenu.add(btnBPJSSEP);
            Panelmenu.add(btnSurkonBPJS);
            jmlmenu++;
        }
        
        if(var.getbpjs_cek_sep()==true){
            Panelmenu.add(btnCekSEP);
            jmlmenu++;
        }
        
        if(var.getbpjs_rujukan_keluar()==true){
            Panelmenu.add(btnRujukanKeluarBPJS);
            jmlmenu++;
        }

        if(var.getbpjs_monitoring_klaim()==true){
            Panelmenu.add(btnMonitoringKlaim);
            Panelmenu.add(btnMonitoringKunjungan);
            jmlmenu++;
        }
        
        if(var.getskdp_bpjs()==true){
            Panelmenu.add(btnSKDPBPJS);
            jmlmenu++;
        }

        if(var.getreklasifikasi_ralan()==true){
            Panelmenu.add(btnReklasifikasiRalan);
            jmlmenu++;
        }

        if(var.getreklasifikasi_ranap()==true){
            Panelmenu.add(btnReklasifikasiRanap);
            jmlmenu++;
        }

        if(var.getaplicare_referensi_kamar()==true){
            Panelmenu.add(btnAplicareReferensiKamar);
            jmlmenu++;
        }

        if(var.getaplicare_ketersediaan_kamar()==true){
            Panelmenu.add(btnAplicareKetersediaanKamar);
            jmlmenu++;
        }

        if(var.getinacbg_coder_nik()==true){
            Panelmenu.add(btnInaCBGCoderNIK);
            jmlmenu++;
        }

        if(var.getinacbg_klaim_baru_otomatis()==true){
            Panelmenu.add(btnInaCBGKlaimBaruOtomatis);
            jmlmenu++;
        } 

        if(var.getinacbg_klaim_baru_manual()==true){
            Panelmenu.add(btnInaCBGKlaimBaruManual);
            jmlmenu++;
        } 
        
        if(var.getinacbg_klaim_baru_manual2()==true){
            Panelmenu.add(btnInaCBGKlaimBaruManual2);
            jmlmenu++;
        } 

        if(var.getinhealth_referensi_jenpel_ruang_rawat()==true){
            Panelmenu.add(btnReferensiKamarInhealth);
            jmlmenu++;
        }

        if(var.getinhealth_referensi_poli()==true){
            Panelmenu.add(btnCekInhealthPoli);
            jmlmenu++;
        }

        if(var.getinhealth_referensi_faskes()==true){
            Panelmenu.add(btnCekInhealthFaskes);
            jmlmenu++;
        }

        if(var.getinhealth_cek_eligibilitas()==true){
            Panelmenu.add(btnCekEligibilitasInhealth);
            jmlmenu++;
        }

        if(var.getinhealth_sjp()==true){
            Panelmenu.add(btnInhealthSJP);
            jmlmenu++;
        }
        
        if(var.getsisrute_referensi_faskes()==true){
            Panelmenu.add(btnCekSisruteFaskes);
            jmlmenu++;
        }
        
        if(var.getsisrute_referensi_alasanrujuk()==true){
            Panelmenu.add(btnCekSisruteAlasanRujuk);
            jmlmenu++;
        }

        if(var.getsisrute_referensi_diagnosa()==true){
            Panelmenu.add(btnCekSisruteDiagnosa);
            jmlmenu++;
        }
        
        if(var.getsisrute_rujukan_masuk()==true){
            Panelmenu.add(btnRujukanMasukSisrute);
            jmlmenu++;
        }
        
        if(var.getsisrute_rujukan_keluar()==true){
            Panelmenu.add(btnRujukanKeluarSisrute);
            jmlmenu++;
        }

        if(var.getsiranap_ketersediaan_kamar()==true){
            Panelmenu.add(btnSiranapKetersediaanKamar);
            jmlmenu++;
        }

        if(var.getperusahaan_pasien()==true){
            Panelmenu.add(btnPerusahaan);
            jmlmenu++;
        }

        if(var.getsuku_bangsa()==true){
            Panelmenu.add(btnSuku);
            jmlmenu++;
        }
        
        if(var.getbahasa_pasien()==true){
            Panelmenu.add(btnBahasa);
            jmlmenu++;
        }

        if(var.getcacat_fisik()==true){
            Panelmenu.add(btnCacatFisik);
            jmlmenu++;
        }

        if(var.getpasien()==true){
            Panelmenu.add(btnPasien);
            jmlmenu++;
        }

        if(var.getkelahiran_bayi()==true){
            Panelmenu.add(btnLahir);
            jmlmenu++;
        }
        
        if(var.getcatatan_pasien()==true){
            Panelmenu.add(btnCatatanPasien);
            jmlmenu++;
        }

        if(var.getpasien_meninggal()==true){
            Panelmenu.add(btnPasienMati);
            Panelmenu.add(btnKerohanian);
            Panelmenu.add(btnPermintaanMPP);
            jmlmenu++;
        }

        if(var.getdiagnosa_pasien()==true){
            Panelmenu.add(btnDiagnosa); 
            jmlmenu++;
        }
        
        if(var.getinsiden_keselamatan()==true){
            Panelmenu.add(btnInsidenKeselamatan);
            jmlmenu++;
        }
        
        if(var.getdata_HAIs()==true){
            Panelmenu.add(btnDataHAIs); 
            jmlmenu++;
        }

        if(var.getinsiden_keselamatan_pasien()==true){
            Panelmenu.add(btnInsidenKeselamatanPasien); 
            jmlmenu++;
        }

        if(var.getpeminjaman_berkas()==true){
           Panelmenu.add(btnSirkulasiBerkas); 
           jmlmenu++;
        }

        if(var.getresume_pasien()==true){
            Panelmenu.add(btnResume);
            jmlmenu++;
        }   

        if(var.getretensi_rm()==true){
            Panelmenu.add(btnRetensiRM);
            jmlmenu++;
        }

        if(var.getmutasi_berkas()==true){
            Panelmenu.add(btnMutasiBerkas);
            jmlmenu++;
        } 
        
        if(var.getberkas_digital_perawatan()==true){
            Panelmenu.add(btnBerkasDigitalPerawatan);
            jmlmenu++;
        } 
        
        if(var.getpengaduan_pasien()==true){
            Panelmenu.add(btnPengaduan);
            jmlmenu++;
        } 

        if(var.getpengambilan_utd2()==true){
            Panelmenu.add(btnPengambilanUTD2); 
            jmlmenu++;
        } 

        if(var.getutd_medis_rusak()==true){
            Panelmenu.add(btnUTDMedisRusak); 
            jmlmenu++;
        } 

        if(var.getpengambilan_penunjang_utd2()==true){
            Panelmenu.add(btnPengambilanPenunjangUTD2); 
            jmlmenu++;
        }

        if(var.getutd_penunjang_rusak()==true){
            Panelmenu.add(btnUTDPenunjangRusak); 
            jmlmenu++;
        } 

        if(var.getutd_komponen_darah()==true){
            Panelmenu.add(btnUTDKomponenDarah); 
            jmlmenu++;
        } 

        if(var.getutd_donor()==true){
            Panelmenu.add(btnUTDDonorDarah); 
            jmlmenu++;
        } 

        if(var.getutd_cekal_darah()==true){
            Panelmenu.add(btnUTDCekalDarah); 
            jmlmenu++;
        } 

        if(var.getutd_pemisahan_darah()==true){
            Panelmenu.add(btnUTDPemisahanDarah); 
            jmlmenu++;
        } 

        if(var.getutd_stok_darah()==true){
            Panelmenu.add(btnUTDStokDarah); 
            jmlmenu++;
        } 

        if(var.getutd_penyerahan_darah()==true){
            Panelmenu.add(btnUTDPenyerahanDarah); 
            Panelmenu.add(btnUTDPermintaan); 
            jmlmenu++;
        } 

        
        if(var.getsurat_indeks()==true){
            Panelmenu.add(btnSuratIndeks);
            jmlmenu++;
        }
        
        if(var.getsurat_map()==true){
            Panelmenu.add(btnSuratMap);
            jmlmenu++;
        }
        
        if(var.getsurat_almari()==true){
            Panelmenu.add(btnSuratAlmari);
            jmlmenu++;
        }
        
        if(var.getsurat_rak()==true){
            Panelmenu.add(btnSuratRak);
            jmlmenu++;
        }
        
        if(var.getsurat_ruang()==true){
            Panelmenu.add(btnSuratRuang);
            jmlmenu++;
        }

        if(var.getsurat_klasifikasi()==true){
            Panelmenu.add(btnSuratKlasifikasi);
            jmlmenu++;
        }
        
        if(var.getsurat_status()==true){
            Panelmenu.add(btnSuratStatus);
            jmlmenu++;
        }

        if(var.getsurat_sifat()==true){
            Panelmenu.add(btnSuratSifat);
            jmlmenu++;
        }
        
        if(var.getsurat_balas()==true){
            Panelmenu.add(btnSuratBalas);
            jmlmenu++;
        }
        
        if(var.getsurat_masuk()==true){
            Panelmenu.add(btnSuratMasuk);
            jmlmenu++;
        }
        
        if(var.getsurat_keluar()==true){
            Panelmenu.add(btnSuratKeluar);
            jmlmenu++;
        }

        if(var.getaplikasi()==true){
            Panelmenu.add(btnSetupAplikasi);
            jmlmenu++;
        }

        if(var.getadmin()==true){
            Panelmenu.add(btnAdmin);
            Panelmenu.add(btnSetHariLibur);
            Panelmenu.add(btnTrialSendWA);
            Panelmenu.add(btnSetPaketMCU);
            Panelmenu.add(btnPindahRiw);
            jmlmenu++;
        }

        if(var.getsetup_pjlab()==true){
            Panelmenu.add(btnSetPenjab);
            Panelmenu.add(btnSetPenjabBNM);
            Panelmenu.add(btnSetKompUtd);
            jmlmenu++;
        }

        if(var.getsetup_otolokasi()==true){
            Panelmenu.add(btnSetupOtoLokasi);
            jmlmenu++;
        }

        if(var.getsetup_jam_kamin()==true){
            Panelmenu.add(btnSetupJamInap);
            jmlmenu++;
        }

        if(var.getset_harga_kamar()==true){
            Panelmenu.add(btnSetHargaKamar);
            jmlmenu++;
        }

        if(var.getsetup_embalase()==true){
            Panelmenu.add(btnSetupEmbalase);
            jmlmenu++;
        }                                

        if(var.getuser()==true){
            Panelmenu.add(btnUser);
            jmlmenu++;
        }

        if(var.gettracer_login()==true){
            Panelmenu.add(btnTracker);
            jmlmenu++;
        }

        if(var.getvakum()==true){
            Panelmenu.add(btnVakum);
            jmlmenu++;
        }

        if(var.getdisplay()==true){
           Panelmenu.add(btnDisplay); 
           jmlmenu++;
        }
        
        if(var.getdisplay_apotek()==true){
           Panelmenu.add(btnDisplayApotek); 
           jmlmenu++;
        }

        if(var.getset_harga_obat()==true){
            Panelmenu.add(btnSetupHarga);
            jmlmenu++;
        }

        if(var.getset_harga_obat_ralan()==true){
            Panelmenu.add(btnSetObatRalan);
            jmlmenu++;
        }

        if(var.getset_harga_obat_ranap()==true){
            Panelmenu.add(btnSetObatRanap);
            jmlmenu++;
        }

        if(var.getset_penggunaan_tarif()==true){
            Panelmenu.add(btnSetupTarif);
            jmlmenu++;
        }

        if(var.getset_oto_ralan()==true){
            Panelmenu.add(btnSetOtoRalan);
            jmlmenu++;
        }

        if(var.getbiaya_harian()==true){
            Panelmenu.add(btnSetBiayaHarian);
            jmlmenu++;
        }

        if(var.getbiaya_masuk_sekali()==true){
            Panelmenu.add(btnSetBiayaMasukSekali);
            jmlmenu++;
        }

        if(var.getset_no_rm()==true){
            Panelmenu.add(btnSetupRM);
            jmlmenu++;
        }

        if(var.getset_nota()==true){
            Panelmenu.add(btnSetupNota);
            jmlmenu++;
        }

        if(var.getclosing_kasir()==true){
            Panelmenu.add(btnClosingKasir);
            jmlmenu++;
        }

        if(var.getketerlambatan_presensi()==true){
            Panelmenu.add(btnKeterlambatanPresensi);
            jmlmenu++;
        }
        
        if(var.getset_input_parsial()==true){
            Panelmenu.add(btnSetInputParsial);
            jmlmenu++;
        }
        
        if(var.getpassword_asuransi()==true){
            Panelmenu.add(btnPasswordAsuransi);
            jmlmenu++;
        }
    }
    private void isCariIsi() {
        jmlmenu=0;     
        if(var.getinformasi_kamar()==true){
            if(btnAnalisaKamar.getText().trim().contains(TCari.getText().trim())){
                Panelmenu.add(btnAnalisaKamar);
                jmlmenu++;
            }                
        }

        if(var.getjadwal_praktek()==true){   
            if(BtnJadwal.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(BtnJadwal);
                jmlmenu++;
            }                
        }

        if(var.getregistrasi()==true){
            if(btnRegistrasi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRegistrasi);
                jmlmenu++;
            }                
        }
        
        if(var.getbooking_registrasi()==true){
            if(btnBookingRegistrasi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnBookingRegistrasi);
                Panelmenu.add(btnBookingMJKN);
                jmlmenu++;
            }                
        }

        if(var.getigd()==true){
            if(btnIGD.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnIGD);  
                jmlmenu++;
            }                
        }
        
        if(var.gettindakan_ralan()==true){
            if(btnRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRalan); 
                jmlmenu++;
            }                
        }

        if((var.getkamar_inap()==true)||(var.getbilling_ranap()==true)||(var.gettindakan_ranap()==true)){
            if(btnKamarInap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKamarInap);
                jmlmenu++;
            }                
        }
        
        if(var.getbooking_operasi()==true){
            if(btnJadwalOperasi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnJadwalOperasi);
                jmlmenu++;
            }                
        }
        
        if((var.getpermintaan_lab()==true)||(var.getperiksa_lab()==true)){
            if(btnPermintaanLab.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPermintaanLab);
                jmlmenu++;
            }                
        }
        
        if((var.getpermintaan_radiologi()==true)||(var.getperiksa_radiologi()==true)){
            if(btnPermintaanRadiologi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPermintaanRadiologi);
                jmlmenu++;
            }                
        }

        if(var.getdpjp_ranap()==true){
            if(BtnDpjp.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(BtnDpjp);
                jmlmenu++;
            }                
        }

        if(var.gettindakan_ranap()==true){
            if(btnRanap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRanap);
                jmlmenu++;
            }                
        }

        if(var.getoperasi()==true){
            if(btnTagihanOperasi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTagihanOperasi);
                jmlmenu++;
            }                
        }

        if(var.getrujukan_keluar()==true){
            if(btnRujukPasien.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRujukPasien);
                jmlmenu++;
            }                
        }

        if(var.getrujukan_masuk()==true){ 
            if(btnRujukMasuk.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRujukMasuk);
                jmlmenu++;
            }                
        }

        if(var.getberi_obat()==true){
            if(btnBeriObat.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnBeriObat);
                jmlmenu++;
            }                
        }

        if(var.getresep_pulang()==true){ 
            if(btnResepPulang.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnResepPulang);
                jmlmenu++;
            }                
        }

        if(var.getresep_obat()==true){
            if(btnResepObat.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnResepObat);
                jmlmenu++;
            }                
        }

        if(var.getdiet_pasien()==true){
            if(btnDiet.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDiet);
                Panelmenu.add(btnRekapDiet);
                jmlmenu++;
            }                
        }

        if(var.getperiksa_lab()==true){
            if(btnLaboratorium.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnLaboratorium);
                jmlmenu++;
            }                
        }

        if(var.getperiksa_radiologi()==true){
            if(btnPeriksaRadiologi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPeriksaRadiologi);   
                jmlmenu++;
            }                
        }

        if((var.getkasir_ralan()==true)||(var.getbilling_ralan()==true)){
            if(btnKasir.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKasir);     
                jmlmenu++;
            }                
        }

        if(var.getdeposit_pasien()==true){    
            if(btnDeposit.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDeposit);
                jmlmenu++;
            }                
        }

        if(var.getpiutang_pasien()==true){
            if(btnLihatPiutang.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnLihatPiutang); 
                jmlmenu++;
            }                
        }            

        if(var.getpetugas()==true){
            if(btnPegawai.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPegawai);
                jmlmenu++;
            }                
        }

        if(var.getdokter()==true){
            if(btnDokter.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDokter);
                jmlmenu++;
            }                
        }

        if(var.getbarcode()==true){
            if(btnBarcode.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnBarcode);  
                jmlmenu++;
            }                
        }

        if(var.getsidikjari()==true){
            if(btnSidikJari.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSidikJari);   
                jmlmenu++;
            }                
        }

        if(var.getjam_masuk()==true){
            if(btnJamPresensi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnJamPresensi);  
                jmlmenu++;
            }                
        }

        if(var.getjadwal_pegawai()==true){
            if(btnJadwalPegawai.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnJadwalPegawai);   
                jmlmenu++;                
            }
            if(btnJadwalTambahan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnJadwalTambahan);   
                jmlmenu++;
            }   
        }             

        if(var.getpresensi_harian()==true){
            if(btnRekapHarian.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRekapHarian);
                jmlmenu++;
            }                
        }

        if(var.getpresensi_bulanan()==true){    
            if(btnRekapBulanan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRekapBulanan);
                jmlmenu++;
            }                
        }

        if(var.gettemporary_presensi()==true){
            if(btnTemporaryPresensi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTemporaryPresensi);   
                jmlmenu++;
            }                
        }

        if(btnRekapPresensi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
            jmlmenu++;
            Panelmenu.add(btnRekapPresensi);
        }
                
        if(btnRekapPresensi2.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
            Panelmenu.add(btnRekapPresensi2);
            jmlmenu++;
        }

        if((var.getpegawai_admin()==true)||(var.getpegawai_user()==true)){  
            if(btnPenggajian.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPenggajian); 
                jmlmenu++;
            }                
        }

        if(var.getmaster_berkas_pegawai()==true){    
            if(btnMasterBerkasPegawai.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnMasterBerkasPegawai);
                jmlmenu++;
            }                
        }
        
        if(var.getberkas_kepegawaian()==true){    
            if(btnBerkasPegawai.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnBerkasPegawai);
                jmlmenu++;
            }                
        }
        
        if(var.getriwayat_jabatan()==true){    
            if(btnRiwayatJabatan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRiwayatJabatan);
                jmlmenu++;
            }                
        }
        
        if(var.getriwayat_pendidikan()==true){    
            if(btnRiwayatPendidikan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRiwayatPendidikan);
                jmlmenu++;
            }                
        }
        
        if(var.getriwayat_naik_gaji()==true){    
            if(btnRiwayatNaikGaji.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRiwayatNaikGaji);
                jmlmenu++;
            }                
        }
        
        if(var.getkegiatan_ilmiah()==true){    
            if(btnKegiatanIlmiah.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKegiatanIlmiah);
                jmlmenu++;
            }                
        }
        
        if(var.getriwayat_penghargaan()==true){    
            if(btnRiwayatPenghargaan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRiwayatPenghargaan);
                jmlmenu++;
            }                
        }
        
        if(var.getriwayat_penelitian()==true){    
            if(btnRiwayatPenelitian.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRiwayatPenelitian);
                jmlmenu++;
            }                
        }
        
        if(var.getindustrifarmasi()==true){
            if(btnIndustriFarmasi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnIndustriFarmasi); 
                jmlmenu++;
            }                
        }

        if(var.getsuplier()==true){
            if(btnSuplier.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuplier); 
                jmlmenu++;
            }                
        }

        if(var.getsatuan_barang()==true){
            if(btnSatuan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSatuan); 
                jmlmenu++;
            }                
        }
        
        if(var.getmetode_racik()==true){
            if(btnMetodeRacik.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnMetodeRacik); 
                jmlmenu++;
            }                
        }

        if(var.getkonversi_satuan()==true){
            if(btnKonversi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKonversi); 
                jmlmenu++;
            }                
        }    

        if(var.getjenis_barang()==true){
            if(btnJnsBarang.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnJnsBarang);
                jmlmenu++;
            }                
        }
        
        if(var.getkategori_barang()==true){
            if(btnKategoriBarang.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKategoriBarang);
                jmlmenu++;
            }                
        }
        
        if(var.getgolongan_barang()==true){
            if(btnGolonganBarang.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnGolonganBarang);
                jmlmenu++;
            }                
        }

        if(var.getobat()==true){
            if(btnObat.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnObat);
                Panelmenu.add(btn10Obat);
                Panelmenu.add(btnHibahObatBHP);
                Panelmenu.add(btnLapStokOp);
                Panelmenu.add(btnLapPelFar);
                Panelmenu.add(btnPelFar);
                Panelmenu.add(btnPaketObatOp);
                jmlmenu++;
            }                
        }

        if(var.getstok_opname_obat()==true){
            if(btnOpname.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnOpname); 
                jmlmenu++;
            }                
        }

        if(var.getmutasi_barang()==true){
            if(btnMutasiBarang.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnMutasiBarang); 
                jmlmenu++;
            }                
        }

        if(var.getstok_obat_pasien()==true){
            if(btnStokObatPasien.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnStokObatPasien); 
                jmlmenu++;
            }                
        }

        if((var.getpermintaan_medis()==true)||(var.getmutasi_barang()==true)||(var.getpengeluaran_stok_apotek()==true)){
            if(btnPermintaanMedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPermintaanMedis);   
                jmlmenu++;
            }                
        }
        
        if(var.getrekap_permintaan_medis()==true){
            if(btnRekapPermintaanMedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRekapPermintaanMedis);   
                jmlmenu++;
            }                
        }
        
        if((var.getsurat_pemesanan_medis()==true)||(var.getpemesanan_obat()==true)){
            if(btnSuratPemesananMedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuratPemesananMedis);   
                jmlmenu++;
            }                
        }
        
        if(var.getpengadaan_obat()==true){
            if(btnPembelian.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPembelian);   
                jmlmenu++;
            }                
        }

        if((var.getpemesanan_obat()==true)||(var.getbayar_pemesanan_obat()==true)){
            if(btnPemesanan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPemesanan);
                jmlmenu++;
            }                
        }

        if(var.getpenjualan_obat()==true){
            if(btnPenjualan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPenjualan);
                jmlmenu++;
            }                
        }
        
        if(var.getresep_dokter()==true){
            if(btnDaftarPermintaanResep.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDaftarPermintaanResep);
                jmlmenu++;
            }                
        }

        if(var.getpiutang_obat()==true){
            if(btnPiutang.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPiutang);  
                jmlmenu++;
            }                
        }
        
        if(var.getpengeluaran_stok_apotek()==true){
            if(btnStokKeluarMedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnStokKeluarMedis);  
                jmlmenu++;
            }                
        }

        if(var.getretur_ke_suplier()==true){
            if(btnReturBeli.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnReturBeli); 
                jmlmenu++;
            }                
        }

        if(var.getretur_dari_pembeli()==true){
            if(btnReturJual.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnReturJual); 
                jmlmenu++;
            }                
        }

        if(var.getretur_obat_ranap()==true){
            if(btnReturPasien.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnReturPasien); 
                jmlmenu++;
            }                
        }

        if(var.getretur_piutang_pasien()==true){
            if(btnReturPiutang.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnReturPiutang); 
                jmlmenu++;
            }                
        }   

        if(var.getpengambilan_utd()==true){
            if(btnPengambilanUTD.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPengambilanUTD); 
                jmlmenu++;
            }                
        } 

        if(var.getkeuntungan_penjualan()==true){
            if(btnKeuntungan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKeuntungan);
                jmlmenu++;
            }                
        }

        if(var.getkeuntungan_beri_obat()==true){
            if(btnKeuntunganObatRanap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKeuntunganObatRanap);
                jmlmenu++;
            }                
        }

        if(var.getkeuntungan_beri_obat_nonpiutang()==true){
            if(btnKeuntunganObat2.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKeuntunganObat2);
                jmlmenu++;
            }                
        }

        if(var.getsirkulasi_obat()==true){
            if(btnSirkulasi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSirkulasi);
                jmlmenu++;
            }                
        }

        if(var.getsirkulasi_obat2()==true){
            if(btnSirkulasi2.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSirkulasi2);
                jmlmenu++;
            }                
        }
        
        if(var.getsirkulasi_obat3()==true){
            if(btnSirkulasi3.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSirkulasi3);
                Panelmenu.add(btnSirkulasiObat4);
                jmlmenu++;
            }                
        }
        
        if(var.getpemberian_obat_pertanggal()==true){
            if(btnObatPerTanggal.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnObatPerTanggal);
                jmlmenu++;
            }                
        }
        
        if(var.getpenjualan_obat_pertanggal()==true){
            if(btnPenjualanPerTanggal.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPenjualanPerTanggal);
                jmlmenu++;
            }                
        }

        if(var.getriwayat_obat_alkes_bhp()==true){
            if(btnRiwayatBarangMedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRiwayatBarangMedis);
                jmlmenu++;
            }                
        }

        if(var.getdarurat_stok()==true){
            if(btnDaruratStok.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDaruratStok);
                jmlmenu++;
            }                
        }    
        
        if(var.getpengguna_obat_resep()==true){
            if(btnPenggunaObatResep.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPenggunaObatResep);
                jmlmenu++;
            }                
        }  
        
        if(var.getrekap_pemesanan()==true){
            if(btnRekapPenerimaanObat.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRekapPenerimaanObat);
                jmlmenu++;
            }                
        }
        
        if(var.getdata_batch()==true){
            if(btnDataBatch.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDataBatch);
                jmlmenu++;
            }                
        }
        
        if(var.getriwayat_data_batch()==true){
            if(btnRiwayatBatch.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRiwayatBatch);
                jmlmenu++;
            }                
        }
        
        if(var.getkegiatan_farmasi()==true){
            if(btnKegiatanFarmasi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKegiatanFarmasi);
                jmlmenu++;
            }                
        }
        
        if(var.getppn_obat()==true){
            if(btnPPNObat.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPPNObat);
                jmlmenu++;
            }                
        }
        
        if(var.getkadaluarsa_batch()==true){
            if(btnKadaluarsaBatch.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKadaluarsaBatch);
                jmlmenu++;
            }                
        }
        
        if(var.getsisa_stok()==true){
            if(btnSisaStok.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSisaStok);
                jmlmenu++;
            }                
        }
        
        if(var.getobat_per_resep()==true){
            if(btnObatPerResep.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnObatPerResep);
                jmlmenu++;
            }                
        }

        if(var.getsatuan_barang()==true){
            if(btnSatuan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSatuan);  
                jmlmenu++;
            }                
        }             

        if(var.getipsrs_jenis_barang()==true){
            if(btnJenisIpsrs.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnJenisIpsrs); 
                jmlmenu++;
            }                
        } 

        if(var.getipsrs_barang()==true){
            if(btnBarangIpsrs.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnBarangIpsrs);  
                jmlmenu++;
            }                
        } 

        if(var.getsuplier_penunjang()==true){
            if(btnSuplierIPSRS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuplierIPSRS);  
                jmlmenu++;
            }                
        }           
        
        if(var.getpermintaan_non_medis()==true){
            if(btnPermintaanNonMedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPermintaanNonMedis);  
                jmlmenu++;
            }                
        }
        
        if(var.getrekap_permintaan_non_medis()==true){
            if(btnRekapPermintaanNonMedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRekapPermintaanNonMedis);  
                jmlmenu++;
            }                
        }
        
        if(var.getsurat_pemesanan_non_medis()==true){
            if(btnSuratPemesananNonMedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuratPemesananNonMedis);  
                jmlmenu++;
            }                
        }

        if(var.getipsrs_pengadaan_barang()==true){
            if(btnPembelianIpsrs.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPembelianIpsrs);  
                jmlmenu++;
            }                
        }
        
        if(var.getpenerimaan_non_medis()==true){
            if(btnPenerimaanNonMedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPenerimaanNonMedis);  
                jmlmenu++;
            }                
        }

        if(var.getipsrs_stok_keluar()==true){
            if(btnPengeluaranIpsrs.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPengeluaranIpsrs); 
                jmlmenu++;
            }                
        }

        if(var.getipsrs_pengeluaran_harian()==true){
            if(btnRBiayaIpsrs.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRBiayaIpsrs);
                jmlmenu++;
            }                
        }

        if(var.getipsrs_rekap_pengadaan()==true){
            if(btnRHMasukIpsrs.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRHMasukIpsrs);
                jmlmenu++;
            }                
        }

        if(var.getipsrs_rekap_stok_keluar()==true){
            if(btnRHKeluarIpsrs.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRHKeluarIpsrs);
                jmlmenu++;
            }                
        } 

        if(var.getpengambilan_penunjang_utd()==true){
            if(btnPengambilanPenunjangUTD.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPengambilanPenunjangUTD);
                jmlmenu++;
            }                
        } 
        
        if(var.getipsrs_pengadaan_pertanggal()==true){
            if(btnPengadaanIPSRSPerTanggal.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPengadaanIPSRSPerTanggal);
                jmlmenu++;
            }                
        } 
        
        if(var.getipsrs_stokkeluar_pertanggal()==true){
            if(btnStokKeluarIPSRSPerTanggal.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnStokKeluarIPSRSPerTanggal);
                jmlmenu++;
            }                
        } 
        
        if(var.getrekap_pemesanan_non_medis()==true){
            if(btnRekapPenerimaanNonMedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRekapPenerimaanNonMedis);
                jmlmenu++;
            }                
        } 
        
        if(var.getstok_opname_logistik()==true){
            if(btnOpnameIPSRS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnOpnameIPSRS);
                jmlmenu++;
            }                
        } 
        
        if(var.getsirkulasi_non_medis()==true){
            if(btnSirkulasiNonMedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSirkulasiNonMedis);
                jmlmenu++;
            }                
        } 

        if(var.getinventaris_jenis()==true){
            if(btnJenisInventaris.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnJenisInventaris);  
                jmlmenu++;
            }                
        }   

        if(var.getinventaris_kategori()==true){
            if(btnKategoriInventaris.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKategoriInventaris);
                jmlmenu++;
            }                
        }

        if(var.getinventaris_merk()==true){
            if(btnMerkInventaris.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnMerkInventaris);
                jmlmenu++;
            }                
        }

        if(var.getinventaris_ruang()==true){
            if(btnRuangInventaris.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRuangInventaris);
                jmlmenu++;
            }                
        }

        if(var.getinventaris_produsen()==true){
            if(btnProdusenInventaris.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnProdusenInventaris);
                jmlmenu++;
            }                
        }

        if(var.getinventaris_koleksi()==true){
            if(btnBarangInventaris.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnBarangInventaris);
                jmlmenu++;
            }                
        }

        if(var.getinventaris_inventaris()==true){
            if(btnInventaris.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnInventaris);  
                jmlmenu++;
            }                
        }                 

        if(var.getinventaris_sirkulasi()==true){
            if(btnSirkulasiInventaris.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSirkulasiInventaris);
                jmlmenu++;
            }                
        }     
        
        if(var.getbarang_cssd()==true){
            if(btnBarangCSSD.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnBarangCSSD);
                jmlmenu++;
            }                
        } 
        
        if(var.getpemakaian_air_pdam()==true){
            if(btnPemakaianAirPDAM.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPemakaianAirPDAM);
                jmlmenu++;
            }                
        } 
        
        if(var.getlimbah_b3_medis()==true){
            if(btnLimbahB3Medis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnLimbahB3Medis);
                jmlmenu++;
            }                
        } 
//        if(var.getkamar_inap()==true|| var.getregistrasi()==true){
            if(btnPermintaanRBA.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPermintaanRBA);
                jmlmenu++;
            }                
//        } 

        if(var.getharian_tindakan_poli()==true){
            if(btnTagihanPoli.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTagihanPoli); 
                jmlmenu++;
            }                
        }   

        if(var.getharian_kamar()==true){
            if(btnHarianKamar.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnHarianKamar); 
                jmlmenu++;
            }                
        }  

        if(var.getharian_tindakan_dokter()==true){
            if(btnTagihanDokter.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTagihanDokter); 
                jmlmenu++;
            }                
        } 

        if(var.getobat_per_poli()==true){
            if(btnTagihanObatPoli.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTagihanObatPoli);
                jmlmenu++;
            }                
        }

        if(var.getobat_per_kamar()==true){
            if(btnTagihanObatBangsal.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTagihanObatBangsal);
                jmlmenu++;
            }                
        }

        if(var.getobat_per_dokter_ralan()==true){
            if(btnObatPasienRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnObatPasienRalan);    
                jmlmenu++;
            }                
        }

        if(var.getobat_per_dokter_ranap()==true){
            if(btnObatPasienRanap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnObatPasienRanap);
                jmlmenu++;
            }                
        }    

        if(var.getobat_per_dokter_peresep()==true){
            if(btnObatPasienPeresep.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnObatPasienPeresep);
                jmlmenu++;
            }                
        }

        if(var.getobat_per_cara_bayar()==true){
            if(btnObatPerCaraBayar.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnObatPerCaraBayar);
                jmlmenu++;
            }                
        }

        if(var.getdetail_tindakan()==true){
            if(btnDetailTindakan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDetailTindakan);
                jmlmenu++;
            }                
        }
        
        if(var.getjm_ranap_dokter()==true){
            if(btnJMDetailDokter.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnJMDetailDokter);
                jmlmenu++;
            }                
        } 
        
        if(var.getdetailjmdokter2()==true){
            if(btnJMDetailDokter2.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnJMDetailDokter2);
                jmlmenu++;
            }                
        } 

        if(var.getharian_dokter()==true){
            if(btnRHDOkter.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRHDOkter);  
                jmlmenu++;
            }                
        }

        if(var.getbulanan_dokter()==true){
            if(btnRBDokter.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRBDokter);
                jmlmenu++;
            }                
        }

        if(var.getharian_paramedis()==true){
            if(btnRHParamedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRHParamedis); 
                jmlmenu++;
            }                
        }

        if(var.getbulanan_paramedis()==true){
            if(btnRBParamedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRBParamedis); 
                jmlmenu++;
            }                
        }

        if(var.getharian_js()==true){
            if(btnRHJasaSarana.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRHJasaSarana);  
                jmlmenu++;
            }                
        }

        if(var.getbulanan_js()==true){
            if(btnRBJasaSarana.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRBJasaSarana);  
                jmlmenu++;
            }                
        }

        if(var.getharian_kso()==true){
            if(btnRHKSO.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRHKSO);  
                jmlmenu++;
            }                
        }

        if(var.getbulanan_kso()==true){
            if(btnRBKSO.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRBKSO);  
                jmlmenu++;
            }                
        }

        if(var.getharian_menejemen()==true){
            if(btnRHMenejemen.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRHMenejemen);  
                jmlmenu++;
            }                
        }

        if(var.getbulanan_menejemen()==true){
            if(btnRBMenejemen.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRBMenejemen);  
                jmlmenu++;
            }                
        }

        if(var.getharian_paket_bhp()==true){
            if(btnRHPaketBHP.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRHPaketBHP);  
                jmlmenu++;
            }                           
        }

        if(var.getbulanan_paket_bhp()==true){
            if(btnRBPaketBHP.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRBPaketBHP);  
                jmlmenu++;
            }                
        }

        if(var.getfee_visit_dokter()==true){
            if(btnfee_visit_dokter.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnfee_visit_dokter); 
                jmlmenu++;
            }                
        }

        if(var.getfee_bacaan_ekg()==true){
            if(btnfee_bacaan_ekg.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnfee_bacaan_ekg); 
                jmlmenu++;
            }                
        }

        if(var.getfee_rujukan_rontgen()==true){
            if(btnfee_rujukan_rontgen.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnfee_rujukan_rontgen); 
                jmlmenu++;
            }                
        }

        if(var.getfee_rujukan_ranap()==true){
            if(btnfee_rujukan_ranap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnfee_rujukan_ranap); 
                jmlmenu++;
            }                
        }

        if(var.getfee_ralan()==true){
            if(btnfee_ralan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnfee_ralan); 
                jmlmenu++;
            }                
        }

        if(var.getdetail_tindakan_okvk()==true){
            if(btnDetailVKOK.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDetailVKOK);
                jmlmenu++;
            }                
        }
        
        if(var.getpembayaran_ralan()==true){
            if(btnRalanMasuk.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRalanMasuk);
                jmlmenu++;
            }                
        }

        if(var.getpembayaran_ranap()==true){
            if(btnRanapMasuk.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRanapMasuk);
                jmlmenu++;
            }                
        }

        if(var.getpiutang_ralan()==true){
            if(btnPiutangRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPiutangRalan);
                jmlmenu++;
            }                
        }

        if(var.getpiutang_ranap()==true){
            if(btnPiutangRanap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPiutangRanap);
                jmlmenu++;
            }                
        }

        if(var.getrekap_pembayaran_ralan()==true){
            if(btnTagihanRalanPerhari.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTagihanRalanPerhari);
                jmlmenu++;
            }                
        }

        if(var.getrekap_pembayaran_ranap()==true){
            if(btnTagihanRanapPerhari.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTagihanRanapPerhari);
                jmlmenu++;
            }                
        }
        
        if(var.getpembayaran_per_unit()==true){
            if(btnPembayaranPerUnit.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPembayaranPerUnit);
                jmlmenu++;
            }                
        }
        
        if(var.getrekap_pembayaran_per_unit()==true){
            if(btnRekapPembayaranPerUnit.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRekapPembayaranPerUnit);
                jmlmenu++;
            }                
        }

        if(var.gettagihan_masuk()==true){
            if(btnTagihanMasuk.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTagihanMasuk);
                jmlmenu++;
            }                
        }

        if(var.gettambahan_biaya()==true){
            if(btnTambahanBiaya.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTambahanBiaya);
                jmlmenu++;
            }                
        }   

        if(var.getpotongan_biaya()==true){
            if(btnPotonganBiaya.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPotonganBiaya);
                jmlmenu++;
            }                
        }     
        
        if(var.getrekap_poli_anak()==true){
            if(btnRekapPoliAnak.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRekapPoliAnak);
                jmlmenu++;
            }                
        } 

        if(var.getdeposit_pasien()==true){
            if(btnDeposit.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDeposit);
                jmlmenu++;
            }                
        }

        if(var.getrekap_per_shift()==true){
            if(btnRekapPershift.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRekapPershift);
                jmlmenu++;
            }                
        }            

        if(var.getpayment_point()==true){
            if(btnPaymentPoint.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPaymentPoint);
                jmlmenu++;
            }                
        }
        
        if(var.getpayment_point2()==true){
            if(btnPaymentPoint2.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPaymentPoint2);
                jmlmenu++;
            }                
        }
        
        if(var.getpembayaran_akun_bayar()==true){
            if(btnPembayaranAkunBayar.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPembayaranAkunBayar);
                jmlmenu++;
            }                
        }
        
        if(var.getpembayaran_akun_bayar2()==true){
            if(btnPembayaranAkunBayar2.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPembayaranAkunBayar2);
                jmlmenu++;
            }                
        }
        
        if(var.getpembayaran_akun_bayar3()==true){
            if(btnPembayaranAkunBayar3.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPembayaranAkunBayar3);
                jmlmenu++;
            }                
        }
        
        if(var.getpiutang_akun_piutang()==true){
            if(btnPiutangPerAkunPiutang.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPiutangPerAkunPiutang);
                jmlmenu++;
            }                
        }

        if(var.geticd9()==true){
            if(btnICD9.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnICD9);
                jmlmenu++;
            }                
        }

        if(var.getpenyakit()==true){
            if(btnICD.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnICD);
                jmlmenu++;
            }                
        }
        
        if(var.getpenyakit_pd3i()==true){
            if(btnPenyakitPD3I.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPenyakitPD3I);
                jmlmenu++;
            }                
        }

        if(var.getsurveilans_pd3i()==true){
            if(btnSurveilansPD3I.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSurveilansPD3I);
                jmlmenu++;
            }                
        }

        if(var.getsurveilans_ralan()==true){
            if(btnSurveilansRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSurveilansRalan);
                jmlmenu++;
            }                
        }

        if(var.getsurveilans_ranap()==true){
            if(btnSurveilansRanap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSurveilansRanap);
                jmlmenu++;
            }                
        }

        if(var.getpny_takmenular_ralan()==true){
            if(btnPnyTakMenularRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPnyTakMenularRalan);
                jmlmenu++;
            }                
        }

        if(var.getpny_takmenular_ranap()==true){
            if(btnPnyTakMenularRanap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPnyTakMenularRanap);
                jmlmenu++;
            }                
        }
        
        if(var.getpenyakit_menular_ralan()==true){
            if(btnPnyMenularRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPnyMenularRalan);
                jmlmenu++;
            }                
        }

        if(var.getpenyakit_menular_ranap()==true){
            if(btnPnyMenularRanap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPnyMenularRanap);
                jmlmenu++;
            }                
        }

        if(var.getobat_penyakit()==true){
            if(btnObatPenyakit.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnObatPenyakit);
                jmlmenu++;
            }                
        }

        if(var.getpenyakit_ralan()==true){  
            if(btnFrekuensiRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnFrekuensiRalan); 
                Panelmenu.add(btnFrekuensiRalanDanRanap);
                Panelmenu.add(btnFrekuensiPerPerujuk);
                jmlmenu++;
            }                
        }

        if(var.getpenyakit_ranap()==true){  
            if(btnFrekuensiRanap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnFrekuensiRanap);
                Panelmenu.add(btnFrekuensiRanapPerDokter); 
                Panelmenu.add(btnFrekuensiRalanDanRanap);
                Panelmenu.add(btnFrekuensiPenyakitRanapBaru);
                jmlmenu++;
            }                
        }

        if(var.getkunjungan_ralan()==true){ 
            if(btnKunjunganRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnLapKunjunganPasien);  
                Panelmenu.add(btnKunjunganRalan);  
                jmlmenu++;
            }                
        }

        if(var.getkunjungan_ranap()==true){  
            if(btnKunjunganRanap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKunjunganRanap); 
                jmlmenu++;
            }                
        }
        
        if(var.getkunjungan_permintaan_lab()==true){  
            if(btnKunjunganLabRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKunjunganLabRalan); 
                jmlmenu++;
            }                
        }
        
        if(var.getkunjungan_permintaan_lab2()==true){  
            if(btnKunjunganLabRanap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKunjunganLabRanap); 
                jmlmenu++;
            }                
        }
        
        if(var.getkunjungan_permintaan_radiologi()==true){  
            if(btnKunjunganRadRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKunjunganRadRalan); 
                jmlmenu++;
            }                
        }
        
        if(var.getkunjungan_permintaan_radiologi2()==true){  
            if(btnKunjunganRadRanap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKunjunganRadRanap); 
                jmlmenu++;
            }                
        }

        if(var.getsensus_harian_poli()==true){  
            if(btnSensusHarianPoli.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSensusHarianPoli);  
                jmlmenu++;
            }                
        }
        
        if(var.getsensus_harian_ralan()==true){  
            if(btnSensusHarianRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSensusHarianRalan);  
                jmlmenu++;
            }                
        }

        if(var.getrl32()==true){  
            if(btnRl32.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRl31);
                Panelmenu.add(btnRl32);   
                Panelmenu.add(btnRl35);   
                jmlmenu++;
            }                
        }

        if(var.getrl33()==true){  
            if(btnRl33.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRl33);                 
                jmlmenu++;
            }                
        }

        if(var.getrl34()==true){  
            if(btnRl34.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRl34);   
                jmlmenu++;
            }                
        }

        if(var.getrl36()==true){  
            if(btnRl36.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRl36);  
                jmlmenu++;
            }                
        }

        if(var.getrl37()==true){  
            if(btnRl37.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRl37); 
                jmlmenu++;
            }                
        }

        if(var.getrl38()==true){  
            if(btnRl38.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRl38);  
                jmlmenu++;
            }                
        }

        if(var.getrl4a()==true){  
            if(btnRl4a.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRl4a);                 
                jmlmenu++;
            }                
        }
        
        if(var.getrl4b()==true){  
            if(btnRl4b.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRl4b);                 
                jmlmenu++;
            }                
        }
        
        if(var.getrl4asebab()==true){  
            if(btnRl4asebab.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRl4asebab);                 
                jmlmenu++;
            }                
        }
        
        if(var.getrl4bsebab()==true){  
            if(btnRl4bsebab.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRl4bsebab);                 
                jmlmenu++;
            }                
        }
        
        if(var.getlama_pelayanan_ralan()==true){  
            if(btnLamaPelayananRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnLamaPelayananRalan); 
                Panelmenu.add(btnPelayananPenunjang);                  
                jmlmenu++;
            }                
        }
        
        if(var.getlama_pelayanan_apotek()==true){  
            if(btnLamaPelayananApotek.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnLamaPelayananApotek);                 
                jmlmenu++;
            }                
        }
        
        if(var.getlama_pelayanan_radiologi()==true){  
            if(btnLamaPelayananRadiologi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnLamaPelayananRadiologi);                 
                Panelmenu.add(btnRekapKegiatanRad);                 
                jmlmenu++;
            }                
        }
        
        if(var.getlama_pelayanan_lab()==true){  
            if(btnLamaPelayananLab.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnLamaPelayananLab);                 
                jmlmenu++;
            }                
        }
        
        if(var.getharian_HAIs()==true){  
            if(btnHarianHAIs.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnHarianHAIs);                 
                jmlmenu++;
            }                
        }
        
        if(var.getbulanan_HAIs()==true){  
            if(btnBulananHAIs.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnBulananHAIs);                 
                jmlmenu++;
            }                
        }
        
        if(var.gethais_perbangsal()==true){  
            if(btnHAIsBangsal.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnHAIsBangsal);                 
                jmlmenu++;
            }                
        }
        
        if(var.gethitung_bor()==true){  
            if(btnHitungBor.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnHitungBor);                 
                jmlmenu++;
            }                
        }
        
        if(var.gethitung_alos()==true){  
            if(btnHitungAlos.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnHitungAlos);                 
                jmlmenu++;
            }                
        }
        
        if(var.getpembatalan_periksa_dokter()==true){  
            if(btnPembatalanPeriksaDokter.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPembatalanPeriksaDokter);                 
                jmlmenu++;
            }                
        }
        
        if(var.getcek_entry_ralan()==true){  
            if(btnCekEntryRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekEntryRalan);                 
                jmlmenu++;
            }                
        }
        
        if(var.getranap_per_ruang()==true){  
            if(btnRanapPerRuang.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRanapPerRuang);                 
                jmlmenu++;
            }                
        }
        
        if(var.getpenyakit_ranap_cara_bayar()==true){  
            if(btnPenyakitRanapCaraBayar.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPenyakitRanapCaraBayar);                 
                jmlmenu++;
            }                
        }
        
        if(var.getrekap_lab_pertahun()==true){  
            if(btnRekapLabPerTahun.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRekapLabPerTahun);                 
                jmlmenu++;
            }                
        }
        
        if(var.getperujuk_lab_pertahun()==true){  
            if(btnPerujukLabPerTahun.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPerujukLabPerTahun);                 
                jmlmenu++;
            }                
        }
        
        if(var.getrekap_radiologi_pertahun()==true){  
            if(btnRekapRadiologiPerTahun.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRekapRadiologiPerTahun);                 
                jmlmenu++;
            }                
        }
        
        if(var.getperujuk_radiologi_pertahun()==true){  
            if(btnPerujukRadiologiPerTahun.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPerujukRadiologiPerTahun);                 
                jmlmenu++;
            }                
        }
        
        if(var.getjumlah_porsi_diet()==true){  
            if(btnJumlahPorsiDiet.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnJumlahPorsiDiet);                 
                jmlmenu++;
            }                
        }
        
        if(var.getjumlah_macam_diet()==true){  
            if(btnJumlahMacamDiet.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnJumlahMacamDiet);                 
                jmlmenu++;
            }                
        }
        
        if(var.getkemenkes_sitt()==true){  
            if(btnDataSITT.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDataSITT);                 
                jmlmenu++;
            }                
        }
        
        if(var.getkamar()==true){
            if(btnKamar.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKamar);
                jmlmenu++;
            }                
        }             

        if(var.gettarif_ralan()==true){
            if(btnTindakanRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTindakanRalan);
                jmlmenu++;
            }                
        }

        if(var.gettarif_ranap()==true){
            if(btnTindakanRanap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTindakanRanap);
                jmlmenu++;
            }                
        }

        if(var.gettarif_lab()==true){
            if(btnTarifLab.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTarifLab);
                jmlmenu++;
            }                
        }

        if(var.gettarif_radiologi()==true){
            if(btnTarifRadiologi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTarifRadiologi);
                jmlmenu++;
            }                
        }          

        if(var.gettarif_operasi()==true){
            if(btnPaketOperasi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPaketOperasi);
                jmlmenu++;
            }                
        }

        if(var.gettarif_utd()==true){
            if(btnTarifUtd.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTarifUtd);
                jmlmenu++;
            }                
        }

        if(var.getakun_rekening()==true){
            if(btnRekening.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRekening);  
                jmlmenu++;
            }                
        }    

        if(var.getrekening_tahun()==true){
            if(btnRekeningTahun.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRekeningTahun);   
                Panelmenu.add(btnLapRBA);   
                jmlmenu++;
            }                
        } 
        
        if(var.getsaldo_akun_perbulan()==true){
            if(btnSaldoAkunPerBulan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSaldoAkunPerBulan);   
                jmlmenu++;
            }                
        } 

        if(var.getakun_bayar()==true){
            if(btnakun_bayar.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnakun_bayar);  
                jmlmenu++;
            }                
        }

        if(var.getakun_piutang()==true){
            if(btnAkunPiutang.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnAkunPiutang);  
                jmlmenu++;
            }                
        }

        if(var.getpengaturan_rekening()==true){
            if(btnPengaturanRekening.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPengaturanRekening);   
                Panelmenu.add(btnSetSubAkun);
                jmlmenu++;
            }                
        } 

        if(var.getpengeluaran()==true){
            if(btnPengeluaran.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPengeluaran);
                jmlmenu++;
            }                
        }

        if(var.getpemasukan_lain()==true){
            if(btnPemasukanLain.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPemasukanLain);
                jmlmenu++;
            }                
        }

        if(var.getdeposit_pasien()==true){   
            if(btnDeposit.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDeposit);
                jmlmenu++;
            }                
        }

        if(var.getpiutang_pasien()==true){
            if(btnLihatPiutang.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnLihatPiutang); 
                jmlmenu++;
            }                
        }

        if(var.getrincian_piutang_pasien()==true){
            if(btnRincianPiutangPasien.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRincianPiutangPasien); 
                jmlmenu++;
            }                
        }

        if(var.getpiutang_pasien2()==true){
            if(btnPiutangBelumLunas.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPiutangBelumLunas); 
                jmlmenu++;
            }                
        }
        
        if(var.getdetail_piutang_penjab()==true){
            if(btnPiutangPerCaraBayar.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
               Panelmenu.add(btnPiutangPerCaraBayar); 
               jmlmenu++; 
            }               
        }

        if(var.getbayar_piutang()==true){
            if(btnBayarPiutang.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnBayarPiutang); 
                jmlmenu++;
            }                
        }

        if(var.gethutang_obat()==true){
            if(btnHutangObat.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnHutangObat);
                jmlmenu++;
            }                
        }

        if(var.getbayar_pemesanan_obat()==true){
            if(btnbayar_pemesanan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnbayar_pemesanan);
                jmlmenu++;
            }                
        }
        
        if(var.gethutang_barang_non_medis()==true){
            if(btnHutangNonMedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnHutangNonMedis);
                jmlmenu++;
            }                
        }
        
        if(var.getbayar_pesan_non_medis()==true){
            if(btnBayarPesanNonMedis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnBayarPesanNonMedis);
                jmlmenu++;
            }                
        }

        if(var.getposting_jurnal()==true){
            if(btnPostingJurnal.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPostingJurnal); 
                jmlmenu++;
            }                
        }

        if(var.getjurnal_harian()==true){
            if(btnJurnalHarian.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnJurnalHarian);  
                jmlmenu++;
            }                
        }

        if(var.getbuku_besar()==true){
            if(btnBubes.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnBubes);
                jmlmenu++;
            }                
        }  

        if(var.getcashflow()==true){
            if(btnCashFlow.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCashFlow);
                jmlmenu++;
            }                
        }

        if(var.getkeuangan()==true){
            if(btnLabaRugi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnLabaRugi);
                jmlmenu++;
            }                
        }                               

        if(var.getbpjs_cek_kartu()==true){
            if(btnCekBPJSKartu.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekBPJSKartu);
                jmlmenu++;
            }                
        }    

        if(var.getbpjs_cek_nik()==true){
            if(btnCekBPJSNik.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekBPJSNik);
                jmlmenu++;
            }                
        }

        if(var.getbpjs_cek_skdp()==true){
            if(btnCekBPJSSKDP.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekBPJSSKDP);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_riwayat()==true){
            if(btnCekBPJSRiwayatRujukanPCare.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekBPJSRiwayatRujukanPCare);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_riwayat_rujukanrs()==true){
            if(btnCekBPJSRiwayatRujukanRS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekBPJSRiwayatRujukanRS);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_tanggal_rujukan()==true){
            if(btnCekBPJSTanggalRujukan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekBPJSTanggalRujukan);
                jmlmenu++;
            }                
        }

        if(var.getbpjs_cek_nomor_rujukan()==true){
            if(btnCekBPJSNomorRujukanPCare.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekBPJSNomorRujukanPCare);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_nomor_rujukan_rs()==true){
            if(btnCekBPJSNomorRujukanRS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekBPJSNomorRujukanRS);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_rujukan_kartu_pcare()==true){
            if(btnCekBPJSRujukanKartuPCare.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekBPJSRujukanKartuPCare);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_rujukan_kartu_rs()==true){
            if(btnCekBPJSRujukanKartuRS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekBPJSRujukanKartuRS);
                jmlmenu++;
            }                
        }

        if(var.getbpjs_referensi_diagnosa()==true){
            if(btnCekBPJSDiagnosa.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekBPJSDiagnosa);
                jmlmenu++;
            }                
        }

        if(var.getbpjs_referensi_poli()==true){
            if(btnCekBPJSPoli.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekBPJSPoli);
                jmlmenu++;
            }                
        }
        
        if(var.getmapping_poli_bpjs()==true){
            if(btnMapingPoliBPJS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnMapingPoliBPJS);
                Panelmenu.add(btnMapingDokterDPJP);
                Panelmenu.add(btnMyLimsMapping);
                jmlmenu++;
            }                
        }

        if(var.getbpjs_referensi_faskes()==true){
            if(btnCekBPJSFaskes.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekBPJSFaskes);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_prosedur()==true){
            if(btnCekReferensiProsedurBPJS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekReferensiProsedurBPJS);
                jmlmenu++;
            }                
        }

        if(var.getbpjs_cek_kelas_rawat()==true){
            if(btnCekReferensiKelasRawatBPJS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekReferensiKelasRawatBPJS);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_dokter()==true){
            if(btnCekReferensiDokterBPJS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekReferensiDokterBPJS);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_spesialistik()==true){
            if(btnCekReferensiSpesialistikBPJS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekReferensiSpesialistikBPJS);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_ruangrawat()==true){
            if(btnCekReferensiRuangRawatBPJS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekReferensiRuangRawatBPJS);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_carakeluar()==true){
            if(btnCekReferensiCaraKeluarBPJS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekReferensiCaraKeluarBPJS);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_pasca_pulang()==true){
            if(btnCekReferensiPascaPulangBPJS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekReferensiPascaPulangBPJS);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_propinsi()==true){
            if(btnCekReferensiPropinsiBPJS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekReferensiPropinsiBPJS);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_kabupaten()==true){
            if(btnCekReferensiKabupatenBPJS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekReferensiKabupatenBPJS);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_kecamatan()==true){
            if(btnCekReferensiKecamatanBPJS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekReferensiKecamatanBPJS);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_dokterdpjp()==true){
            if(btnCekReferensiDokterDPJPBPJS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekReferensiDokterDPJPBPJS);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_sep()==true){
            if(btnBPJSSEP.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnBPJSSEP);
                Panelmenu.add(btnSurkonBPJS);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_cek_sep()==true){
            if(btnCekSEP.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekSEP);
                jmlmenu++;
            }                
        }
        
        if(var.getbpjs_rujukan_keluar()==true){
            if(btnRujukanKeluarBPJS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRujukanKeluarBPJS);
                jmlmenu++;
            }                
        }

        if(var.getbpjs_monitoring_klaim()==true){
            if(btnMonitoringKlaim.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnMonitoringKlaim);
                Panelmenu.add(btnMonitoringKunjungan);
                jmlmenu++;
            }                
        }
        
        if(var.getskdp_bpjs()==true){
            if(btnSKDPBPJS.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSKDPBPJS);
                jmlmenu++;
            }                
        }

        if(var.getreklasifikasi_ralan()==true){
            if(btnReklasifikasiRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnReklasifikasiRalan);
                jmlmenu++;
            }                
        }

        if(var.getreklasifikasi_ranap()==true){
            if(btnReklasifikasiRanap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnReklasifikasiRanap);
                jmlmenu++;
            }                
        }

        if(var.getaplicare_referensi_kamar()==true){
            if(btnAplicareReferensiKamar.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnAplicareReferensiKamar);
                jmlmenu++;
            }                
        }

        if(var.getaplicare_ketersediaan_kamar()==true){
            if(btnAplicareKetersediaanKamar.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnAplicareKetersediaanKamar);
                jmlmenu++;
            }                
        }

        if(var.getinacbg_coder_nik()==true){
            if(btnInaCBGCoderNIK.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnInaCBGCoderNIK);
                jmlmenu++;
            }                
        }

        if(var.getinacbg_klaim_baru_otomatis()==true){
            if(btnInaCBGKlaimBaruOtomatis.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnInaCBGKlaimBaruOtomatis);
                jmlmenu++;
            }                
        } 

        if(var.getinacbg_klaim_baru_manual()==true){
            if(btnInaCBGKlaimBaruManual.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnInaCBGKlaimBaruManual);
                jmlmenu++;
            }                
        }
        
        if(var.getinacbg_klaim_baru_manual2()==true){
            if(btnInaCBGKlaimBaruManual2.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnInaCBGKlaimBaruManual2);
                jmlmenu++;
            }                
        } 

        if(var.getinhealth_referensi_jenpel_ruang_rawat()==true){
            if(btnReferensiKamarInhealth.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnReferensiKamarInhealth);
                jmlmenu++;
            }                
        }

        if(var.getinhealth_referensi_poli()==true){
            if(btnCekInhealthPoli.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekInhealthPoli);
                jmlmenu++;
            }                
        }

        if(var.getinhealth_referensi_faskes()==true){
            if(btnCekInhealthFaskes.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekInhealthFaskes);
                jmlmenu++;
            }                
        }

        if(var.getinhealth_cek_eligibilitas()==true){
            if(btnCekEligibilitasInhealth.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekEligibilitasInhealth);
                jmlmenu++;
            }                
        }

        if(var.getinhealth_sjp()==true){
            if(btnInhealthSJP.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnInhealthSJP);
                jmlmenu++;
            }                
        }
        
        if(var.getsisrute_referensi_faskes()==true){
            if(btnCekSisruteFaskes.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekSisruteFaskes);
                jmlmenu++;
            }                
        }
        
        if(var.getsisrute_referensi_alasanrujuk()==true){
            if(btnCekSisruteAlasanRujuk.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekSisruteAlasanRujuk);
                jmlmenu++;
            }                
        }
        
        if(var.getsisrute_referensi_diagnosa()==true){
            if(btnCekSisruteDiagnosa.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCekSisruteDiagnosa);
                jmlmenu++;
            }                
        }
        
        if(var.getsisrute_rujukan_masuk()==true){
            if(btnRujukanMasukSisrute.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRujukanMasukSisrute);
                jmlmenu++;
            }                
        }
        
        if(var.getsisrute_rujukan_keluar()==true){
            if(btnRujukanKeluarSisrute.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRujukanKeluarSisrute);
                jmlmenu++;
            }                
        }
        
        if(var.getsiranap_ketersediaan_kamar()==true){
            if(btnSiranapKetersediaanKamar.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSiranapKetersediaanKamar);
                jmlmenu++;
            }                
        }

        if(var.getperusahaan_pasien()==true){
            if(btnPerusahaan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPerusahaan);
                jmlmenu++;
            }                
        }
        
        if(var.getsuku_bangsa()==true){
            if(btnSuku.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuku);
                jmlmenu++;
            }                
        }
        
        if(var.getbahasa_pasien()==true){
            if(btnBahasa.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnBahasa);
                jmlmenu++;
            }                
        }
        
        if(var.getcacat_fisik()==true){
            if(btnCacatFisik.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCacatFisik);
                jmlmenu++;
            }                
        }

        if(var.getpasien()==true){
            if(btnPasien.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPasien);
                jmlmenu++;
            }                
        }

        if(var.getkelahiran_bayi()==true){
            if(btnLahir.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnLahir);
                jmlmenu++;
            }                
        }
        
        if(var.getcatatan_pasien()==true){
            if(btnCatatanPasien.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnCatatanPasien);
                jmlmenu++;
            }                
        }

        if(var.getpasien_meninggal()==true){
            if(btnPasienMati.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPasienMati);
                Panelmenu.add(btnKerohanian);
                Panelmenu.add(btnPermintaanMPP);
                jmlmenu++;
            }
        }

        if(var.getdiagnosa_pasien()==true){
            if(btnDiagnosa.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDiagnosa); 
                jmlmenu++;
            }                
        }
        
        if(var.getinsiden_keselamatan()==true){
            if(btnInsidenKeselamatan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnInsidenKeselamatan);
                jmlmenu++;
            }                
        }
        
        if(var.getdata_HAIs()==true){
            if(btnDataHAIs.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDataHAIs); 
                jmlmenu++;
            }                
        }
        
        if(var.getinsiden_keselamatan_pasien()==true){
            if(btnInsidenKeselamatanPasien.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnInsidenKeselamatanPasien); 
                jmlmenu++;
            }                
        }

        if(var.getpeminjaman_berkas()==true){
            if(btnSirkulasiBerkas.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSirkulasiBerkas); 
                jmlmenu++;
            }                
        }

        if(var.getresume_pasien()==true){
            if(btnResume.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnResume);
                jmlmenu++;
            }                
        }   

        if(var.getretensi_rm()==true){
            if(btnRetensiRM.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnRetensiRM);
                jmlmenu++;
            }                
        }

        if(var.getmutasi_berkas()==true){
            if(btnMutasiBerkas.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnMutasiBerkas);
                jmlmenu++;
            }                
        } 
        
        if(var.getberkas_digital_perawatan()==true){
            if(btnBerkasDigitalPerawatan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnBerkasDigitalPerawatan);
                jmlmenu++;
            }                
        } 
        
        if(var.getpengaduan_pasien()==true){
            if(btnPengaduan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPengaduan);
                jmlmenu++;
            }                
        } 

        if(var.getpengambilan_utd2()==true){
            if(btnPengambilanUTD2.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPengambilanUTD2); 
                jmlmenu++;
            }                
        } 

        if(var.getutd_medis_rusak()==true){
            if(btnUTDMedisRusak.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnUTDMedisRusak); 
                jmlmenu++;
            }                
        } 

        if(var.getpengambilan_penunjang_utd2()==true){
            if(btnPengambilanPenunjangUTD2.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPengambilanPenunjangUTD2); 
                jmlmenu++;
            }                
        }

        if(var.getutd_penunjang_rusak()==true){
            if(btnUTDPenunjangRusak.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnUTDPenunjangRusak); 
                jmlmenu++;
            }                
        } 

        if(var.getutd_komponen_darah()==true){
            if(btnUTDKomponenDarah.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnUTDKomponenDarah); 
                jmlmenu++;
            }                
        } 

        if(var.getutd_donor()==true){
            if(btnUTDDonorDarah.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnUTDDonorDarah); 
                jmlmenu++;
            }                
        } 

        if(var.getutd_cekal_darah()==true){
            if(btnUTDCekalDarah.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnUTDCekalDarah); 
                jmlmenu++;
            }                
        } 

        if(var.getutd_pemisahan_darah()==true){
            if(btnUTDPemisahanDarah.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnUTDPemisahanDarah); 
                jmlmenu++;
            }                
        } 

        if(var.getutd_stok_darah()==true){
            if(btnUTDStokDarah.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnUTDStokDarah); 
                jmlmenu++;
            }                
        } 

        if(var.getutd_penyerahan_darah()==true){
            if(btnUTDPenyerahanDarah.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnUTDPenyerahanDarah); 
                Panelmenu.add(btnUTDPermintaan); 
                jmlmenu++;
            }                
        } 

        if(var.getsurat_indeks()==true){
            if(btnSuratIndeks.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuratIndeks);
                jmlmenu++;
            }                
        }

        if(var.getsurat_map()==true){
            if(btnSuratMap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuratMap);
                jmlmenu++;
            }                
        }
        
        if(var.getsurat_almari()==true){
            if(btnSuratAlmari.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuratAlmari);
                jmlmenu++;
            }                
        }
        
        if(var.getsurat_rak()==true){
            if(btnSuratRak.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuratRak);
                jmlmenu++;
            }                
        }
        
        if(var.getsurat_ruang()==true){
            if(btnSuratRuang.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuratRuang);
                jmlmenu++;
            }                
        }
        
        if(var.getsurat_klasifikasi()==true){
            if(btnSuratKlasifikasi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuratKlasifikasi);
                jmlmenu++;
            }                
        }
        
        if(var.getsurat_status()==true){
            if(btnSuratStatus.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuratStatus);
                jmlmenu++;
            }                
        }
        
        if(var.getsurat_sifat()==true){
            if(btnSuratSifat.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuratSifat);
                jmlmenu++;
            }                
        }
        
        if(var.getsurat_balas()==true){
            if(btnSuratBalas.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuratBalas);
                jmlmenu++;
            }                
        }
        
        if(var.getsurat_masuk()==true){
            if(btnSuratMasuk.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuratMasuk);
                jmlmenu++;
            }                
        }
        
        if(var.getsurat_keluar()==true){
            if(btnSuratKeluar.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSuratKeluar);
                jmlmenu++;
            }                
        }
        
        if(var.getaplikasi()==true){
            if(btnSetupAplikasi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetupAplikasi);
                jmlmenu++;
            }                
        }

        if(var.getadmin()==true){
            if(btnAdmin.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnAdmin);
                Panelmenu.add(btnSetHariLibur);
                Panelmenu.add(btnTrialSendWA);
                Panelmenu.add(btnSetPaketMCU);
                Panelmenu.add(btnPindahRiw);
                jmlmenu++;
            }                
        }

        if(var.getsetup_pjlab()==true){
            if(btnSetPenjab.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetPenjab);
                Panelmenu.add(btnSetPenjabBNM);
                Panelmenu.add(btnSetKompUtd);
                jmlmenu++;
            }                
        }

        if(var.getsetup_otolokasi()==true){
            if(btnSetupOtoLokasi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetupOtoLokasi);
                jmlmenu++;
            }                
        }

        if(var.getsetup_jam_kamin()==true){
            if(btnSetupJamInap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetupJamInap);
                jmlmenu++;
            }                
        }

        if(var.getset_harga_kamar()==true){
            if(btnSetHargaKamar.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetHargaKamar);
                jmlmenu++;
            }                
        }

        if(var.getsetup_embalase()==true){
            if(btnSetupEmbalase.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetupEmbalase);
                jmlmenu++;
            }                
        }                                

        if(var.getuser()==true){
            if(btnUser.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnUser);
                jmlmenu++;
            }                
        }

        if(var.gettracer_login()==true){
            if(btnTracker.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnTracker);
                jmlmenu++;
            }                
        }

        if(var.getvakum()==true){
            if(btnVakum.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnVakum);
                jmlmenu++;
            }                
        }

        if(var.getdisplay()==true){
            if(btnDisplay.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDisplay); 
                jmlmenu++;
            }                
        }
        
        if(var.getdisplay_apotek()==true){
            if(btnDisplayApotek.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnDisplayApotek); 
                jmlmenu++;
            }                
        }

        if(var.getset_harga_obat()==true){
            if(btnSetupHarga.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetupHarga);
                jmlmenu++;
            }                
        }

        if(var.getset_harga_obat_ralan()==true){
            if(btnSetObatRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetObatRalan);
                jmlmenu++;
            }                
        }

        if(var.getset_harga_obat_ranap()==true){
            if(btnSetObatRanap.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetObatRanap);
                jmlmenu++;
            }
        }

        if(var.getset_penggunaan_tarif()==true){
            if(btnSetupTarif.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetupTarif);
                jmlmenu++;
            }                
        }

        if(var.getset_oto_ralan()==true){
            if(btnSetOtoRalan.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetOtoRalan);
                jmlmenu++;
            }
        }

        if(var.getbiaya_harian()==true){
            if(btnSetBiayaHarian.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetBiayaHarian);
                jmlmenu++;
            }                
        }

        if(var.getbiaya_masuk_sekali()==true){
            if(btnSetBiayaMasukSekali.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetBiayaMasukSekali);
                jmlmenu++;
            }                
        }

        if(var.getset_no_rm()==true){
            if(btnSetupRM.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetupRM);
                jmlmenu++;
            }                
        }

        if(var.getset_nota()==true){
            if(btnSetupNota.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetupNota);
                jmlmenu++;
            }                
        }

        if(var.getclosing_kasir()==true){
            if(btnClosingKasir.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnClosingKasir);
                jmlmenu++;
            }                
        }

        if(var.getketerlambatan_presensi()==true){
            if(btnKeterlambatanPresensi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnKeterlambatanPresensi);
                jmlmenu++;
            }                
        }
        
        if(var.getset_input_parsial()==true){
            if(btnSetInputParsial.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnSetInputParsial);
                jmlmenu++;
            }                
        }
        
        if(var.getpassword_asuransi()==true){
            if(btnPasswordAsuransi.getText().toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())){
                Panelmenu.add(btnPasswordAsuransi);
                jmlmenu++;
            }                
        }
    }
}
