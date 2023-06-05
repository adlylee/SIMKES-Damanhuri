/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fungsi;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author khanzasoft
 */
public final class koneksiDB {
    public koneksiDB(){}    
    private static Connection connection=null;
    private static final Properties prop = new Properties();  
    private static final MysqlDataSource dataSource=new MysqlDataSource();
    private static String caricepat="",var="";
    public static Connection condb(){      
        if(connection == null){
            try{
                prop.loadFromXML(new FileInputStream("setting/database.xml"));
                dataSource.setURL("jdbc:mysql://"+EnkripsiAES.decrypt(prop.getProperty("HOST"))+":"+EnkripsiAES.decrypt(prop.getProperty("PORT"))+"/"+EnkripsiAES.decrypt(prop.getProperty("DATABASE"))+"?zeroDateTimeBehavior=convertToNull&autoReconnect=true&useCompression=true");
                dataSource.setUser(EnkripsiAES.decrypt(prop.getProperty("USER")));
                dataSource.setPassword(EnkripsiAES.decrypt(prop.getProperty("PAS")));
                connection=dataSource.getConnection();       
                System.out.println("\n\n                                                        \n"+
                        "   Bismillahirrahmanirrahim ...                                        \n\n"+
                        "    ____  ___  __  __  ____   ____    _  __ _                              \n" +
                        "   / ___||_ _||  \\/  ||  _ \\ / ___|  | |/ /| |__    __ _  _ __   ____ __ _ \n" +
                        "   \\___ \\ | | | |\\/| || |_) |\\___ \\  | ' / | '_ \\  / _` || '_ \\ |_  // _` |\n" +
                        "    ___) || | | |  | ||  _ <  ___) | | . \\ | | | || (_| || | | | / /| (_| |\n" +
                        "   |____/|___||_|  |_||_| \\_\\|____/  |_|\\_\\|_| |_| \\__,_||_| |_|/___|\\__,_|\n" +
                        "                                                                           \n"+
                        "    PROUDLY POWERED BY               \n"+
                        "     _  ___  _____\n" +
                        "    | ||  _||_   _|   ____   ____   _   _  ____                           \n" +
                        "    | || |    | |    |  _ \\ / ___| | |_| ||    \\\n" +
                        "    | || |    | |    | |_) |\\___ \\ |  _  || |\\  \\\n" +
                        "    | || |_   | |    |  _ <  ___) || | | || |/  /\n" +
                        "    |_||___|  |_|    |_| \\_\\|____/ |_| |_||___ /\n" +
                        "                                                  \n" +
                        "    Version 04.06.2023 [ 1.0.16 ] [Activated] GO KLAIM DIGITAL \n"+
                        "                                                                           \n"+
                        "    RSUD H. Damanhuri Barabai                              \n"+
                        "                                                                           \n"+
                        "    Paripurna * * * * *                                  \n"+
                        "                                                                           \n"+
                        "       SMART , GREEN , AND FRIENDLY HOSPITAL                           \n"+
                        "                                                                           \n"+
                        "    * Known Bug :                                                           \n"+
                        "                                                                       \n"+
                        "                                                                       \n"+
                        "    * Changelog :                                                       \n"+
                        "       - Diagnosa pasien                                              \n"+
                        "       - Laporan Pelayanan Kefarmasian                                              \n"+
                        "       - Rujuk Masuk                                              \n"+
                        "       - Resep Rawat Gabung                                              \n");
            }catch(Exception e){
                System.out.println("Notif : "+e);
                try {
                    if(connection.isClosed()){
                        prop.loadFromXML(new FileInputStream("setting/database.xml"));
                        dataSource.setURL("jdbc:mysql://"+EnkripsiAES.decrypt(prop.getProperty("HOST"))+":"+EnkripsiAES.decrypt(prop.getProperty("PORT"))+"/"+EnkripsiAES.decrypt(prop.getProperty("DATABASE"))+"?zeroDateTimeBehavior=convertToNull&amp;autoReconnect=true&amp;cachePrepStmts=true");
                        dataSource.setUser(EnkripsiAES.decrypt(prop.getProperty("USER")));
                        dataSource.setPassword(EnkripsiAES.decrypt(prop.getProperty("PAS")));
                        connection=dataSource.getConnection();  
                        System.out.println("Berhasil Menghubungkan Kembali .");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"Koneksi Putus : "+e);
                }
            }
        }
        return connection;        
    }
    
    public static String cariCepat(){
        try{
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            caricepat=prop.getProperty("CARICEPAT");
        }catch(Exception e){
            caricepat="tidak aktif"; 
        }
        return caricepat;
    }
    
    public static String HOST(){
        try{
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var=EnkripsiAES.decrypt(prop.getProperty("HOSTHYBRIDWEB"));
        }catch(Exception e){
            var="localhost"; 
        }
        return var;
    }
    
    public static String PORT(){
        try{
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var=EnkripsiAES.decrypt(prop.getProperty("PORT"));
        }catch(Exception e){
            var="3306"; 
        }
        return var;
    }
    
    public static String DATABASE(){
        try{
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var=EnkripsiAES.decrypt(prop.getProperty("DATABASE"));
        }catch(Exception e){
            var="sik"; 
        }
        return var;
    }
    
    public static String AKTIFKANBATCHOBAT(){
        try{
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var=prop.getProperty("AKTIFKANBATCHOBAT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String ConsIdBpjs(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var = EnkripsiAES.decrypt(prop.getProperty("CONSIDAPIBPJS"));
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return var;
    }
    
    public static String SecretKeyBpjs(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var = EnkripsiAES.decrypt(prop.getProperty("SECRETKEYAPIBPJS"));
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return var;
    }
    
    public static String UserKeyBpjs(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var = EnkripsiAES.decrypt(prop.getProperty("USERKEYAPIBPJS"));
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return var;
    }
    
    public static String UrlBpjs(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var = prop.getProperty("URLAPIBPJS");
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return var;
    }
    
    public static String UrlMobileJKN(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var = prop.getProperty("URLAPIMOBILEJKN");
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return var;
    }
    
    public static String UrlSisrute(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var = prop.getProperty("URLAPISISRUTE");
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return var;
    }
    
    public static String ConsIdSisrute(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var = EnkripsiAES.decrypt(prop.getProperty("IDSISRUTE"));
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return var;
    }
    
    public static String PassSisrute(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var = EnkripsiAES.decrypt(prop.getProperty("PASSSISRUTE"));
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return var;
    }
    
    public static String UrlSirs(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var = prop.getProperty("URLAPISIRS");
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return var;
    }
    
    public static String IdSirs() {
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var = EnkripsiAES.decrypt(prop.getProperty("IDSIRS"));
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return var;
    }
    
    public static String PassSirs() {
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var = EnkripsiAES.decrypt(prop.getProperty("PASSSIRS"));
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return var;
    }
    
    public static String UrlAutoUpdate(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var = "192.168.0.52/updatesimrs";
//            var = EnkripsiAES.decrypt(prop.getProperty("URLUPDATESISTEM"));
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return var;
    }
    
    public static String USERORTHANC(){
        try{
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var=EnkripsiAES.decrypt(prop.getProperty("USERORTHANC"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PASSORTHANC(){
        try{
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var=EnkripsiAES.decrypt(prop.getProperty("PASSORTHANC"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PORTORTHANC(){
        try{
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var=EnkripsiAES.decrypt(prop.getProperty("PORTORTHANC"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLORTHANC(){
        try{
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var=prop.getProperty("URLORTHANC");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
}
