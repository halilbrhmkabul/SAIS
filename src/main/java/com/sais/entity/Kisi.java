package com.sais.entity;

import com.sais.enums.OgrenimDurum;
import com.sais.enums.SGKDurum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "kisi", indexes = {
    @Index(name = "idx_kisi_tc", columnList = "tc_kimlik_no"),
    @Index(name = "idx_kisi_son_mernis", columnList = "son_mernis_sorgu_tarihi")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kisi extends BaseEntity {

    @Id
    @SequenceGenerator(name = "kisi_seq", sequenceName = "kisi_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kisi_seq")
    private Long id;

    @Column(name = "tc_kimlik_no", nullable = false, unique = true, length = 11)
    private String tcKimlikNo;

    @Column(name = "ad", nullable = false, length = 100)
    private String ad;

    @Column(name = "soyad", nullable = false, length = 100)
    private String soyad;

    @Column(name = "baba_adi", length = 100)
    private String babaAdi;

    @Column(name = "anne_adi", length = 100)
    private String anneAdi;

    @Column(name = "dogum_tarihi")
    private LocalDate dogumTarihi;

    @Column(name = "dogum_yeri", length = 100)
    private String dogumYeri;

    @Column(name = "cinsiyet", length = 1)
    private String cinsiyet;

    @Column(name = "medeni_durum", length = 20)
    private String medeniDurum;

    @Column(name = "adres", columnDefinition = "CLOB")
    private String adres;

    @Column(name = "il", length = 50)
    private String il;

    @Column(name = "ilce", length = 50)
    private String ilce;

    @Column(name = "mahalle", length = 100)
    private String mahalle;

    @Column(name = "telefon", length = 20)
    private String telefon;

    @Column(name = "email", length = 150, nullable = true)
    private String email;

    @Column(name = "gebze_ikameti", columnDefinition = "NUMBER(1,0) DEFAULT 1")
    @Builder.Default
    private Boolean gebzeIkameti = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "sgk_durum")
    private SGKDurum sgkDurum;

    @Enumerated(EnumType.STRING)
    @Column(name = "ogrenim_durum")
    private OgrenimDurum ogrenimDurum;

    @Column(name = "son_mernis_sorgu_tarihi")
    private LocalDate sonMernisSorguTarihi;

    @Column(name = "mernis_guncelleme_sayisi")
    @Builder.Default
    private Integer mernisGuncellemeSayisi = 0;

    @Transient
    public String getAdSoyad() {
        return ad + " " + soyad;
    }

    @Transient
    public Integer getYas() {
        if (dogumTarihi != null) {
            return LocalDate.now().getYear() - dogumTarihi.getYear();
        }
        return null;
    }
    
    // Getter and Setter metotları
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTcKimlikNo() { return tcKimlikNo; }
    public void setTcKimlikNo(String tcKimlikNo) { this.tcKimlikNo = tcKimlikNo; }
    
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    
    public String getSoyad() { return soyad; }
    public void setSoyad(String soyad) { this.soyad = soyad; }
    
    public String getBabaAdi() { return babaAdi; }
    public void setBabaAdi(String babaAdi) { this.babaAdi = babaAdi; }
    
    public String getAnneAdi() { return anneAdi; }
    public void setAnneAdi(String anneAdi) { this.anneAdi = anneAdi; }
    
    public LocalDate getDogumTarihi() { return dogumTarihi; }
    public void setDogumTarihi(LocalDate dogumTarihi) { this.dogumTarihi = dogumTarihi; }
    
    public String getDogumYeri() { return dogumYeri; }
    public void setDogumYeri(String dogumYeri) { this.dogumYeri = dogumYeri; }
    
    public String getCinsiyet() { return cinsiyet; }
    public void setCinsiyet(String cinsiyet) { this.cinsiyet = cinsiyet; }
    
    public String getMedeniDurum() { return medeniDurum; }
    public void setMedeniDurum(String medeniDurum) { this.medeniDurum = medeniDurum; }
    
    public String getAdres() { return adres; }
    public void setAdres(String adres) { this.adres = adres; }
    
    public String getIl() { return il; }
    public void setIl(String il) { this.il = il; }
    
    public String getIlce() { return ilce; }
    public void setIlce(String ilce) { this.ilce = ilce; }
    
    public String getMahalle() { return mahalle; }
    public void setMahalle(String mahalle) { this.mahalle = mahalle; }
    
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    
    public LocalDate getSonMernisSorguTarihi() { return sonMernisSorguTarihi; }
    public void setSonMernisSorguTarihi(LocalDate sonMernisSorguTarihi) { this.sonMernisSorguTarihi = sonMernisSorguTarihi; }
    
    public OgrenimDurum getOgrenimDurum() { return ogrenimDurum; }
    public void setOgrenimDurum(OgrenimDurum ogrenimDurum) { this.ogrenimDurum = ogrenimDurum; }
    
    public SGKDurum getSgkDurum() { return sgkDurum; }
    public void setSgkDurum(SGKDurum sgkDurum) { this.sgkDurum = sgkDurum; }
}


