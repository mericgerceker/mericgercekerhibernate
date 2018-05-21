/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Ogrenci;
import org.hibernate.Session;

/**
 *
 * @author mericgerceker
 */
public class OgrenciYonetici {
    private JTable ogrenciTablo;
    private final static String SORGU_KALIP = "from Ogrenci o";
    private final static String SILME_SORGU_KALIP = "delete Ogrenci o";
    private Session session;
    private Vector<String> sutunlar = new Vector<>();
    private Vector<Object> satir;
    private DefaultTableModel model;
    public OgrenciYonetici(JTable ogrenciTablo) {
        this.ogrenciTablo = ogrenciTablo;
        sutunlar.add("Öğrenci ID");
        sutunlar.add("Öğrenci No");
        sutunlar.add("Ad Soyad");
        sutunlar.add("Şehir");
        sutunlar.add("Tel No");
        model=(DefaultTableModel)ogrenciTablo.getModel();
        model.setColumnIdentifiers(sutunlar);
    }

    public void ogrenciGetir(String aranan, String filtre) {
        String sorguMetin = "";
        if (filtre.equalsIgnoreCase("adsoyad")) {
            sorguMetin = SORGU_KALIP + " where o.adsoyad like '%" + aranan + "%'";
        } else if (filtre.equalsIgnoreCase("sehir")) {
            sorguMetin = SORGU_KALIP + " where o.sehir like '%" + aranan + "%'";
        }
        session.beginTransaction();
        List ogrencilerList = session.createQuery(sorguMetin).list();
        session.getTransaction().commit();
        ogrenciGoster(ogrencilerList);

    }
    
    public void ogrenciListele() {
        session.beginTransaction();
        List ogrencilerList = session.createQuery(SORGU_KALIP).list();
        session.getTransaction().commit();
        ogrenciGoster(ogrencilerList);

    }
    
    public void ogrenciEkle(String ogrenciNo, String ogrenciAdSoyad, String ogrenciSehir, String ogrenciTelefon) {
        session.beginTransaction();
        Ogrenci yeniEklenenOgrenci = new Ogrenci();
        yeniEklenenOgrenci.setOgrencino(ogrenciNo);
        yeniEklenenOgrenci.setAdsoyad(ogrenciAdSoyad);
        yeniEklenenOgrenci.setSehir(ogrenciSehir);
        yeniEklenenOgrenci.setTelno(ogrenciTelefon);
        session.save(yeniEklenenOgrenci);
        session.getTransaction().commit();

    }
    
    public void ogrenciGuncelle(int secilenSatirID, String ogrenciNo, String ogrenciAdSoyad, String ogrenciSehir, String ogrenciTelefon) {
        session.beginTransaction();
        Ogrenci guncellenenOgrenci = (Ogrenci) session.get(Ogrenci.class, secilenSatirID);
        guncellenenOgrenci.setOgrencino(ogrenciNo);
        guncellenenOgrenci.setAdsoyad(ogrenciAdSoyad);
        guncellenenOgrenci.setSehir(ogrenciSehir);
        guncellenenOgrenci.setTelno(ogrenciTelefon);
        session.merge(guncellenenOgrenci);
        session.getTransaction().commit();

    }
    
    public void ogrenciSil(int secilenSatirID) {
        session.beginTransaction();
        Ogrenci silinecekOgrenci= (Ogrenci) session.get(Ogrenci.class, secilenSatirID);
        session.delete(silinecekOgrenci);
        session.getTransaction().commit();

    }
    
    public int baglantiKontrol(){
        int baglantiDurumu = 0;
        if(session.isConnected() == true){
            baglantiDurumu = 1;
        }
        else if(session.isConnected() == false){
            baglantiDurumu = 0;
        }
        return baglantiDurumu;
    }

    public void ac() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void kapat() {
        session.close();
    }

    private void ogrenciGoster(List<Ogrenci> ogrencilerList) {
        model.getDataVector().removeAllElements();
        for (Ogrenci gelenOgrenci : ogrencilerList) {
            satir=new Vector();
            satir.add(gelenOgrenci.getOgrenciid());
            satir.add(gelenOgrenci.getOgrencino());
            satir.add(gelenOgrenci.getAdsoyad());
            satir.add(gelenOgrenci.getSehir());
            satir.add(gelenOgrenci.getTelno());
            model.addRow(satir);
        }
    }
}
