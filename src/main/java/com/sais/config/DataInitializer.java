package com.sais.config;

import com.sais.entity.*;
import com.sais.enums.OgrenimDurum;
import com.sais.enums.SGKDurum;
import com.sais.enums.YardimTipi;
import com.sais.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
@Order(100) // Spring Boot SQL init'ten sonra çalışacak
public class DataInitializer implements CommandLineRunner {

    private final YakinlikKoduRepository yakinlikKoduRepository;
    private final YardimAltTipiRepository yardimAltTipiRepository;
    private final KisiRepository kisiRepository;
    private final MeslekRepository meslekRepository;
    private final OzelStatuRepository ozelStatuRepository;
    private final GelirTuruRepository gelirTuruRepository;
    private final BorcTuruRepository borcTuruRepository;
    private final PersonelRepository personelRepository;
    private final YardimDilimiRepository yardimDilimiRepository;
    private final YardimDonemiRepository yardimDonemiRepository;
    private final HesapBilgisiRepository hesapBilgisiRepository;
    private final EngelliTipiRepository engelliTipiRepository;
    private final HastalikRepository hastalikRepository;
    private final YardimRedSebebiRepository yardimRedSebebiRepository;

    @Override
    public void run(String... args) {
        log.info("=".repeat(70));
        log.info("📦 VERİTABANI VERİ KONTROLÜ VE YÜKLEME");
        log.info("=".repeat(70));

        try {
            printDataSummary();
            
            if (isDataMissing()) {
                log.warn("");
                log.warn("⚠️  EKSİK VERİ TESPİT EDİLDİ - OTOMATİK YÜKLEME BAŞLATILIYOR...");
                log.warn("");
                loadMasterData();
                log.info("✅ VERİ YÜKLEME TAMAMLANDI!");
            } else {
                log.info("");
                log.info("✅ Tüm referans veriler hazır!");
            }
            
            log.info("=".repeat(70));

        } catch (Exception e) {
            log.error("❌ VERİ KONTROLÜ HATASI: {}", e.getMessage(), e);
        }
    }

    
    private boolean isDataMissing() {
        try {
            long yakinlikCount = yakinlikKoduRepository.count();
            long yardimCount = yardimAltTipiRepository.count();
            long kisiCount = kisiRepository.count();
            long engelliTipiCount = engelliTipiRepository.count();
            long hastalikCount = hastalikRepository.count();
            long redSebebiCount = yardimRedSebebiRepository.count();
            
            return yakinlikCount < 12 || yardimCount < 17 || kisiCount < 10 || 
                   engelliTipiCount < 17 || hastalikCount < 15 || redSebebiCount < 8;
        } catch (Exception e) {
            log.warn("Veri kontrolü sırasında hata: {}", e.getMessage());
            return true; 
        }
    }

    
    private void printDataSummary() {
        try {
            long yakinlikCount = yakinlikKoduRepository.count();
            long yardimCount = yardimAltTipiRepository.count();
            long kisiCount = kisiRepository.count();
            long engelliTipiCount = engelliTipiRepository.count();
            long hastalikCount = hastalikRepository.count();
            long redSebebiCount = yardimRedSebebiRepository.count();
            
            log.info("");
            log.info("MEVCUT VERİ DURUMU:");
            log.info("   Yakınlık Kodları      : {} / 12", yakinlikCount);
            log.info("   Yardım Alt Tipleri    : {} / 17", yardimCount);
            log.info("   Engelli Tipleri       : {} / 17", engelliTipiCount);
            log.info("   Hastalık Tanımları    : {} / 15", hastalikCount);
            log.info("   Yardım Red Sebepleri  : {} / 8", redSebebiCount);
            log.info("   Kişi Kayıtları        : {} / 10+", kisiCount);
            log.info("");
        } catch (Exception e) {
            log.warn("Veri özeti yazdırma hatası: {}", e.getMessage());
        }
    }
    
    
    private void loadMasterData() {
        try {
            log.info("📦 MASTER DATA YÜKLEME BAŞLADI...");
            
            loadYakınlıkKodları();
            loadMeslekler();
            loadOzelStatuler();
            loadEngelliTipleri();
            loadHastaliklar();
            loadGelirTurleri();
            loadBorçTurleri();
            loadPersoneller();
            loadYardimDilimleri();
            loadYardimDonemleri();
            loadYardimRedSebepleri();
            loadYardimAltTipleri();
            loadKisiVerileri();
            loadHesapBilgileri();
            
            log.info("✅ MASTER DATA YÜKLEME TAMAMLANDI!");
        } catch (Exception e) {
            log.error("❌ Master data yükleme hatası", e);
        }
    }
    
    @Transactional
    private void loadYardimAltTipleri() {
        if (yardimAltTipiRepository.count() == 0) {
            log.info("Yardım alt tipleri yükleniyor...");
            
            List<YardimAltTipiData> yardimTipleri = Arrays.asList(
                // Komisyonlu Nakdi Yardımlar
                new YardimAltTipiData("NAKDI-001", "Genel Ekonomik Destek", true, YardimTipi.NAKDI, "Komisyonlu nakdi yardım", "TL", 1),
                new YardimAltTipiData("NAKDI-002", "Kira Yardımı", true, YardimTipi.NAKDI, "Komisyonlu nakdi yardım", "TL", 2),
                new YardimAltTipiData("NAKDI-003", "Eğitim Yardımı", true, YardimTipi.NAKDI, "Komisyonlu nakdi yardım", "TL", 3),
                new YardimAltTipiData("NAKDI-004", "Sağlık Yardımı", true, YardimTipi.NAKDI, "Komisyonlu nakdi yardım", "TL", 4),
                
                // Komisyonlu Ayni Yardımlar
                new YardimAltTipiData("AYNI-001", "Gıda Kolisi", true, YardimTipi.AYNI, "Komisyonlu ayni yardım", "Adet", 7),
                new YardimAltTipiData("AYNI-002", "Kömür Yardımı", true, YardimTipi.AYNI, "Komisyonlu ayni yardım", "Adet", 8),
                new YardimAltTipiData("AYNI-003", "Beyaz Eşya", true, YardimTipi.AYNI, "Komisyonlu ayni yardım", "Adet", 9),
                new YardimAltTipiData("AYNI-004", "Mobilya", true, YardimTipi.AYNI, "Komisyonlu ayni yardım", "Adet", 10),
                new YardimAltTipiData("AYNI-005", "Kırtasiye Yardımı", true, YardimTipi.AYNI, "Komisyonlu ayni yardım", "Adet", 11),
                new YardimAltTipiData("AYNI-006", "Giyim Yardımı", true, YardimTipi.AYNI, "Komisyonlu ayni yardım", "Adet", 12),
                
                // Komisyonsuz Ayni Yardımlar
                new YardimAltTipiData("AYNI-K001", "Acil Gıda Paketi", false, YardimTipi.AYNI, "Komisyonsuz ayni yardım", "Adet", 13),
                new YardimAltTipiData("AYNI-K002", "Hijyen Paketi", false, YardimTipi.AYNI, "Komisyonsuz ayni yardım", "Adet", 14),
                new YardimAltTipiData("AYNI-K003", "Bebek Paketi", false, YardimTipi.AYNI, "Komisyonsuz ayni yardım", "Adet", 15),
                new YardimAltTipiData("AYNI-K004", "İlaç Kuponu", false, YardimTipi.AYNI, "Komisyonsuz ayni yardım", "Adet", 16),
                new YardimAltTipiData("AYNI-K005", "Ekmek Kuponu", false, YardimTipi.AYNI, "Komisyonsuz ayni yardım", "Adet", 17)
            );
            
            for (YardimAltTipiData data : yardimTipleri) {
                var yardimTipi = YardimAltTipi.builder()
                    .kod(data.kod)
                    .adi(data.adi)
                    .komisyonKararli(data.komisyonKararli)
                    .yardimTipi(data.yardimTipi)
                    .aciklama(data.aciklama)
                    .birim(data.birim)
                    .siraNo(data.siraNo)
                    .build();
                yardimTipi.setAktif(true);
                
                yardimAltTipiRepository.save(yardimTipi);
            }
            
            log.info("✓ {} yardım tipi yüklendi", yardimTipleri.size());
        } else {
            log.info("Yardım alt tipleri zaten mevcut ({})", yardimAltTipiRepository.count());
        }
    }
    
    /**
     * Yardım Alt Tipi data structure (DTO)
     */
    private static class YardimAltTipiData {
        final String kod;
        final String adi;
        final Boolean komisyonKararli;
        final YardimTipi yardimTipi;
        final String aciklama;
        final String birim;
        final Integer siraNo;
        
        YardimAltTipiData(String kod, String adi, Boolean komisyonKararli, YardimTipi yardimTipi, 
                          String aciklama, String birim, Integer siraNo) {
            this.kod = kod;
            this.adi = adi;
            this.komisyonKararli = komisyonKararli;
            this.yardimTipi = yardimTipi;
            this.aciklama = aciklama;
            this.birim = birim;
            this.siraNo = siraNo;
        }
    }
    
    @Transactional
    private void loadKisiVerileri() {
        if (kisiRepository.count() == 0) {
            log.info("Kişi verileri yükleniyor (örnek veriler)...");
            
            // Gebze içi örnek kişiler (TC son hanesi 0-5)
            createKisi("24151827460", "Fatma", "YILMAZ", "Mehmet", "Ayşe", 
                      LocalDate.of(1985, 3, 15), "İstanbul", 'K', 
                      "Güzeller Mahallesi No:45 Gebze", "Kocaeli", "Gebze", 
                      "5551112233", true, SGKDurum.SSK, OgrenimDurum.LISE);
            
            createKisi("35678912340", "Ali", "KAYA", "Hasan", "Zeynep", 
                      LocalDate.of(1978, 7, 22), "Ankara", 'E', 
                      "Aydıntepe Mahallesi No:12 Gebze", "Kocaeli", "Gebze", 
                      "5552223344", true, SGKDurum.EMEKLI_SANDIGI, OgrenimDurum.LISANS);
            
            createKisi("18956234520", "Zeynep", "ÇELİK", "Ahmet", "Hatice", 
                      LocalDate.of(1990, 11, 8), "İzmir", 'K', 
                      "Cumhuriyet Mahallesi No:78 Gebze", "Kocaeli", "Gebze", 
                      "5553334455", true, SGKDurum.BAG_KUR, OgrenimDurum.ORTAOKUL);
            
            createKisi("42789654310", "Mustafa", "ŞAHİN", "İbrahim", "Emine", 
                      LocalDate.of(1965, 5, 30), "Kocaeli", 'E', 
                      "Eskihisar Mahallesi No:23 Gebze", "Kocaeli", "Gebze", 
                      "5554445566", true, SGKDurum.EMEKLI_SANDIGI, OgrenimDurum.ILKOKUL);
            
            createKisi("56234789120", "Emine", "YILDIZ", "Ömer", "Fatma", 
                      LocalDate.of(1995, 9, 12), "Bursa", 'K', 
                      "Kirazpınar Mahallesi No:56 Gebze", "Kocaeli", "Gebze", 
                      "5555556677", true, SGKDurum.YOK, OgrenimDurum.LISE);
            
            log.info("✓ {} kişi yüklendi (Gebze içi)", kisiRepository.count());
        } else {
            log.info("Kişi verileri zaten mevcut ({})", kisiRepository.count());
        }
    }
    
    private void createKisi(String tcKimlikNo, String ad, String soyad, String babaAdi, String anneAdi,
                           LocalDate dogumTarihi, String dogumYeri, char cinsiyet, String adres, 
                           String il, String ilce, String telefon, Boolean gebzeIkameti, 
                           SGKDurum sgkDurum, OgrenimDurum ogrenimDurum) {
        try {
            Kisi kisi = Kisi.builder()
                .tcKimlikNo(tcKimlikNo)
                .ad(ad)
                .soyad(soyad)
                .babaAdi(babaAdi)
                .anneAdi(anneAdi)
                .dogumTarihi(dogumTarihi)
                .dogumYeri(dogumYeri)
                .cinsiyet(String.valueOf(cinsiyet))
                .adres(adres)
                .il(il)
                .ilce(ilce)
                .telefon(telefon)
                .gebzeIkameti(gebzeIkameti)
                .sgkDurum(sgkDurum)
                .ogrenimDurum(ogrenimDurum)
                .sonMernisSorguTarihi(LocalDate.now())
                .mernisGuncellemeSayisi(1)
                .build();
            kisi.setAktif(true);
            
            kisiRepository.save(kisi);
        } catch (Exception e) {
            log.warn("Kişi oluşturulamadı (muhtemelen zaten var): {}", tcKimlikNo);
        }
    }
    
    @Transactional
    private void loadYakınlıkKodları() {
        if (yakinlikKoduRepository.count() == 0) {
            log.info("Yakınlık kodları yükleniyor...");
            String[][] data = {
                {"01", "Kendisi", "Kendisi", "1"},
                {"02", "Eşi", "Eşi", "2"},
                {"03", "Annesi", "Annesi", "3"},
                {"04", "Babası", "Babası", "4"},
                {"05", "Oğlu", "Oğlu", "5"},
                {"06", "Kızı", "Kızı", "6"},
                {"07", "Kardeşi", "Kardeşi", "7"},
                {"08", "Büyükanne", "Büyükanne", "8"},
                {"09", "Büyükbaba", "Büyükbaba", "9"},
                {"10", "Torun", "Torun", "10"},
                {"11", "Gelin", "Gelin", "11"},
                {"12", "Damat", "Damat", "12"}
            };
            for (String[] d : data) {
                var yk = YakinlikKodu.builder().kod(d[0]).adi(d[1]).aciklama(d[2]).siraNo(Integer.parseInt(d[3])).build();
                yk.setAktif(true);
                yakinlikKoduRepository.save(yk);
            }
           
        }
    }
    
    @Transactional
    private void loadMeslekler() {
        if (meslekRepository.count() == 0) {
            log.info("Meslekler yükleniyor...");
            String[] meslekler = {"İşsiz", "İşçi", "Memur", "Öğretmen", "Doktor", "Hemşire", "Mühendis", "Serbest Meslek", "Esnaf", "Emekli", "Ev Hanımı", "Öğrenci", "Çiftçi", "Şoför", "Güvenlik Görevlisi"};
            int i = 1;
            for (String ad : meslekler) {
                var m = Meslek.builder().kod("M" + String.format("%03d", i)).adi(ad).build();
                m.setAktif(true);
                meslekRepository.save(m);
                i++;
            }
            
        }
    }
    
    @Transactional
    private void loadOzelStatuler() {
        if (ozelStatuRepository.count() == 0) {
            log.info("Özel statüler yükleniyor...");
            String[][] data = {
                {"Şehit Yakını", "Şehit ailesi", "100"},
                {"Gazi Yakını", "Gazi ailesi", "90"},
                {"Yetim", "Anne veya baba kaybı", "80"},
                {"Öksüz", "Anne ve baba kaybı", "85"},
                {"Dul", "Eş kaybı", "70"},
                {"Koruma Altında", "ASPB koruma altında", "60"},
                {"Mülteci", "Geçici koruma statüsü", "50"}
            };
            int i = 1;
            for (String[] d : data) {
                var os = OzelStatu.builder().kod("OS" + String.format("%03d", i)).adi(d[0]).aciklama(d[1]).oncelikPuani(Integer.parseInt(d[2])).build();
                os.setAktif(true);
                ozelStatuRepository.save(os);
                i++;
            }
            
        }
    }
    
    @Transactional
    private void loadGelirTurleri() {
        if (gelirTuruRepository.count() == 0) {
            log.info("Gelir türleri yükleniyor...");
            String[][] data = {
                {"Maaş", "Aylık maaş geliri", "1"},
                {"Emekli Maaşı", "Emeklilik maaşı", "2"},
                {"Kira Geliri", "Gayrimenkulden kira geliri", "3"},
                {"Ticari Kazanç", "Ticari faaliyetten gelir", "4"},
                {"Sosyal Yardım", "Devlet/kuruluşlardan sosyal yardım", "5"},
                {"Nafaka", "Nafaka geliri", "6"},
                {"Engelli Maaşı", "Engelliler için aylık", "7"},
                {"Yaşlılık Aylığı", "65 yaş üstü aylık", "8"},
                {"Diğer", "Diğer gelir kaynakları", "9"}
            };
            int i = 1;
            for (String[] d : data) {
                var gt = GelirTuru.builder().kod("GT" + String.format("%03d", i)).adi(d[0]).aciklama(d[1]).siraNo(Integer.parseInt(d[2])).build();
                gt.setAktif(true);
                gelirTuruRepository.save(gt);
                i++;
            }
           
        }
    }
    
    @Transactional
    private void loadBorçTurleri() {
        if (borcTuruRepository.count() == 0) {
            log.info("Borç türleri yükleniyor...");
            String[] borçlar = {"Elektrik", "Su", "Doğalgaz", "Kira", "Kredi Kartı", "Banka Kredisi", "İnternet", "Telefon", "Eğitim Masrafı", "Sağlık Masrafı", "Diğer"};
            int i = 1;
            for (String ad : borçlar) {
                var bt = BorcTuru.builder().kod("BT" + String.format("%03d", i)).adi(ad).siraNo(i).build();
                bt.setAktif(true);
                borcTuruRepository.save(bt);
                i++;
            }
            
        }
    }
    
    @Transactional
    private void loadPersoneller() {
        if (personelRepository.count() == 0) {
            log.info("Personel kayıtları yükleniyor...");
            String[][] personeller = {
                {"11111111110", "Ahmet", "YILMAZ", "5551234567", "ahmet.yilmaz@gebze.bel.tr", "Sosyal Yardım Uzmanı", "Sosyal Hizmetler", "true", "false"},
                {"22222222220", "Ayşe", "KAYA", "5559876543", "ayse.kaya@gebze.bel.tr", "Sosyal Çalışmacı", "Sosyal Hizmetler", "true", "false"},
                {"33333333330", "Mehmet", "DEMİR", "5551112233", "mehmet.demir@gebze.bel.tr", "Müdür", "Sosyal Hizmetler", "false", "true"}
            };
            for (String[] p : personeller) {
                var personel = Personel.builder()
                    .tcKimlikNo(p[0])
                    .ad(p[1])
                    .soyad(p[2])
                    .telefon(p[3])
                    .email(p[4])
                    .unvan(p[5])
                    .departman(p[6])
                    .tahkikatYetkili(Boolean.parseBoolean(p[7]))
                    .komisyonUyesi(Boolean.parseBoolean(p[8]))
                    .build();
                personel.setAktif(true);
                personelRepository.save(personel);
            }
            
        }
    }
    
    @Transactional
    private void loadYardimDilimleri() {
        if (yardimDilimiRepository.count() == 0) {
            log.info("Yardım dilimleri yükleniyor...");
            String[] dilimler = {"Birinci Dilim", "İkinci Dilim", "Üçüncü Dilim", "Dördüncü Dilim"};
            int i = 1;
            for (String ad : dilimler) {
                var yd = YardimDilimi.builder().kod("D0" + i).adi(ad).siraNo(i).build();
                yd.setAktif(true);
                yardimDilimiRepository.save(yd);
                i++;
            }
            
        }
    }
    
    @Transactional
    private void loadYardimDonemleri() {
        if (yardimDonemiRepository.count() == 0) {
            log.info("Yardım dönemleri yükleniyor...");
            for (int i = 1; i <= 12; i++) {
                var yd = YardimDonemi.builder().adi(i + " Aylık").aySayisi(i).build();
                yd.setAktif(true);
                yardimDonemiRepository.save(yd);
            }
        }
    }
    
    @Transactional
    private void saveEngelliTipi(EngelliTipiData data) {
        try {
            EngelliTipi.EngelliTipiBuilder builder = EngelliTipi.builder()
                .kod(data.kod)
                .adi(data.adi)
                .siraNo(data.siraNo);
            
            // Üst tip varsa bul ve ata
            if (data.ustTipKod != null) {
                engelliTipiRepository.findByKod(data.ustTipKod)
                    .ifPresent(builder::ustTip);
            }
            
            EngelliTipi engelliTipi = builder.build();
            engelliTipi.setAktif(true);
            
            engelliTipiRepository.save(engelliTipi);
        } catch (Exception e) {
            log.warn("Engelli tipi oluşturulamadı: {} - {}", data.kod, e.getMessage());
        }
    }
    
    private static class EngelliTipiData {
        final String kod;
        final String adi;
        final String ustTipKod;
        final Integer siraNo;
        
        EngelliTipiData(String kod, String adi, String ustTipKod, Integer siraNo) {
            this.kod = kod;
            this.adi = adi;
            this.ustTipKod = ustTipKod;
            this.siraNo = siraNo;
        }
    }
    
    @Transactional
    private void loadHastaliklar() {
        if (hastalikRepository.count() == 0) {
            log.info("Hastalık tanımları yükleniyor...");
            String[][] data = {
                {"H001", "Şeker Hastalığı (Diyabet)", "true"},
                {"H002", "Tansiyon (Hipertansiyon)", "true"},
                {"H003", "Kalp Hastalığı", "true"},
                {"H004", "Astım", "true"},
                {"H005", "KOAH", "true"},
                {"H006", "Böbrek Yetmezliği", "true"},
                {"H007", "Kanser", "true"},
                {"H008", "Epilepsi", "true"},
                {"H009", "Multiple Skleroz (MS)", "true"},
                {"H010", "Parkinson", "true"},
                {"H011", "Alzheimer", "true"},
                {"H012", "Talasemi", "true"},
                {"H013", "Hemofili", "true"},
                {"H014", "Sedef Hastalığı", "true"},
                {"H015", "Romatizma", "true"}
            };
            
            for (String[] d : data) {
                var hastalik = Hastalik.builder()
                    .kod(d[0])
                    .adi(d[1])
                    .kronik(Boolean.parseBoolean(d[2]))
                    .build();
                hastalik.setAktif(true);
                hastalikRepository.save(hastalik);
            }
            
            log.info("✓ {} hastalık tanımı yüklendi", data.length);
        } else {
            log.info("Hastalık tanımları zaten mevcut ({})", hastalikRepository.count());
        }
    }
    
    @Transactional
    private void loadHesapBilgileri() {
        if (hesapBilgisiRepository.count() == 0 && kisiRepository.count() > 0) {
            log.info("Hesap bilgileri yükleniyor...");

            String[][] kisiHesapMap = {
                {"24151827460", "Ziraat Bankası", "TR330006100519786457841326"},
                {"35678912340", "Halkbank", "TR120001200934800019850016"},
                {"18956234520", "Vakıfbank", "TR150001500158007301234567"},
                {"42789654310", "İş Bankası", "TR640006400000112340056789"},
                {"56234789120", "Akbank", "TR460004600232888001298765"}
            };
            
            for (String[] hesaplar : kisiHesapMap) {
                var kisiOptional = kisiRepository.findByTcKimlikNo(hesaplar[0]);
                if (kisiOptional.isPresent()) {
                    var kisi = kisiOptional.get();
                    var hb = HesapBilgisi.builder()
                        .kisi(kisi)
                        .bankaAdi(hesaplar[1])
                        .iban(hesaplar[2])
                        .hesapSahibiAdi(kisi.getAd() + " " + kisi.getSoyad())
                        .varsayilan(true)
                        .build();
                    hb.setAktif(true);
                    hesapBilgisiRepository.save(hb);
                }
            }
        }
    }
    
    @Transactional
    private void loadEngelliTipleri() {
        if (engelliTipiRepository.count() < 17) {
            log.info("Engelli tipleri yükleniyor...");
            
            // Ana Tipler
            String[][] anaTipler = {
                {"ET001", "Bedensel Engelli", "1"},
                {"ET002", "Görme Engelli", "2"},
                {"ET003", "İşitme Engelli", "3"},
                {"ET004", "Zihinsel Engelli", "4"},
                {"ET005", "Ruhsal/Psikolojik Engelli", "5"},
                {"ET006", "Süreğen Hastalık", "6"}
            };
            
            for (String[] tip : anaTipler) {
                if (engelliTipiRepository.findByKod(tip[0]).isEmpty()) {
                    var engelliTipi = EngelliTipi.builder()
                        .kod(tip[0])
                        .adi(tip[1])
                        .siraNo(Integer.parseInt(tip[2]))
                        .build();
                    engelliTipi.setAktif(true);
                    engelliTipiRepository.save(engelliTipi);
                }
            }
            
            // Alt Tipler
            String[][] altTipler = {
                {"ET001-01", "Ortopedik Engelli", "7", "ET001"},
                {"ET001-02", "Omurilik Felci", "8", "ET001"},
                {"ET002-01", "Az Gören", "9", "ET002"},
                {"ET002-02", "Tamamen Görme Engelli", "10", "ET002"},
                {"ET003-01", "Az İşiten", "11", "ET003"},
                {"ET003-02", "Tamamen İşitme Engelli", "12", "ET003"},
                {"ET004-01", "Hafif Zihinsel Engelli", "13", "ET004"},
                {"ET004-02", "Orta Zihinsel Engelli", "14", "ET004"},
                {"ET004-03", "Ağır Zihinsel Engelli", "15", "ET004"},
                {"ET005-01", "Otizm", "16", "ET005"},
                {"ET005-02", "Down Sendromu", "17", "ET005"}
            };
            
            for (String[] tip : altTipler) {
                if (engelliTipiRepository.findByKod(tip[0]).isEmpty()) {
                    var ustTip = engelliTipiRepository.findByKod(tip[3]).orElse(null);
                    var engelliTipi = EngelliTipi.builder()
                        .kod(tip[0])
                        .adi(tip[1])
                        .siraNo(Integer.parseInt(tip[2]))
                        .ustTip(ustTip)
                        .build();
                    engelliTipi.setAktif(true);
                    engelliTipiRepository.save(engelliTipi);
                }
            }
            
            log.info("✓ {} engelli tipi yüklendi", engelliTipiRepository.count());
        } else {
            log.info("Engelli tipleri zaten mevcut ({})", engelliTipiRepository.count());
        }
    }
    
    @Transactional
    private void loadYardimRedSebepleri() {
        if (yardimRedSebebiRepository.count() == 0) {
            log.info("Yardım red sebepleri yükleniyor...");
            
            String[][] redSebepleri = {
                {"RS001", "Gelir Düzeyi Yüksek", "Gelir seviyesi yardım almak için uygun değil", "1"},
                {"RS002", "Evrak Eksikliği", "Gerekli belgeler eksik", "2"},
                {"RS003", "Gebze Dışı İkamet", "Başvuru sahibi Gebze dışında ikamet ediyor", "3"},
                {"RS004", "Sahte Beyan", "Yanlış veya eksik bilgi verme", "4"},
                {"RS005", "Başka Kuruluştan Alıyor", "Başka bir yerden benzer yardım alıyor", "5"},
                {"RS006", "Kriterlere Uymuyor", "Yardım kriterlere uygun değil", "6"},
                {"RS007", "Bütçe Yetersizliği", "Yardım bütçesi tükendi", "7"},
                {"RS008", "Diğer", "Diğer sebepler", "8"}
            };
            
            for (String[] sebebi : redSebepleri) {
                var redSebebi = YardimRedSebebi.builder()
                    .kod(sebebi[0])
                    .adi(sebebi[1])
                    .aciklama(sebebi[2])
                    .siraNo(Integer.parseInt(sebebi[3]))
                    .build();
                redSebebi.setAktif(true);
                yardimRedSebebiRepository.save(redSebebi);
            }
            
            log.info("✓ {} yardım red sebebi yüklendi", yardimRedSebebiRepository.count());
        } else {
            log.info("Yardım red sebepleri zaten mevcut ({})", yardimRedSebebiRepository.count());
        }
    }

    // İç sınıflar - Veri tanımlamaları
    private static class HastalikData {
        final String kod;
        final String adi;
        final Boolean kronik;
        
        HastalikData(String kod, String adi, Boolean kronik) {
            this.kod = kod;
            this.adi = adi;
            this.kronik = kronik;
        }
    }
}
