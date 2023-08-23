/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package perlap;

import inventory.*;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author khanzamedia
 */
public class riwayatRBA {

    private final Connection koneksi = koneksiDB.condb();
    private ResultSet rs, rsawal;
    private PreparedStatement ps, psawal;
    private double saldoawal=0, saldoberjalan = 0, saldoakhir = 0;

    public void catatRiwayat(String tanggal, String tahun, String kdrek, double masuk, double keluar, String petugas, String status, String keterangan) {
        try {
            saldoakhir = 0;
            saldoberjalan = 0;
            saldoawal=0;
            psawal = koneksi.prepareStatement("select saldo_awal, saldo_berjalan from rekeningtahun where thn=? and kd_rek=?");
            try {
                psawal.setString(1, tahun);
                psawal.setString(2, kdrek);
                rs = psawal.executeQuery();
                if (rs.next()) {
                    saldoawal = rs.getDouble("saldo_awal");
                    saldoberjalan = rs.getDouble("saldo_berjalan");
                    saldoakhir = saldoberjalan + masuk - keluar;
                } else {
                    saldoawal = 0;
                    saldoberjalan = 0;
                    saldoakhir = saldoberjalan + masuk - keluar;
                }
            } catch (Exception e) {
                System.out.println("Notif Stok : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (psawal != null) {
                    psawal.close();
                }
            }

            ps = koneksi.prepareStatement("insert into rba_perlap values(?,?,?,?,?,?,current_date(),current_time(),?,?,?)");
            try {
                ps.setString(1, tanggal);
                ps.setDouble(2, saldoberjalan);
                ps.setDouble(3, masuk);
                ps.setDouble(4, keluar);
                ps.setDouble(5, saldoakhir);
                ps.setString(6, kdrek);
                ps.setString(7, petugas);
                ps.setString(8, status);
                ps.setString(9, keterangan);
                ps.executeUpdate();
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
        }
    }
}
