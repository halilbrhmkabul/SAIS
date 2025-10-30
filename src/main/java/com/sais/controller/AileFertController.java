package com.sais.controller;

import com.sais.dto.MernisYakinDTO;
import com.sais.entity.*;
import com.sais.enums.MuracaatDurum;
import com.sais.enums.OgrenimDurum;
import com.sais.enums.SGKDurum;
import com.sais.exception.BusinessException;
import com.sais.service.AileFertService;
import com.sais.service.LookupService;
import com.sais.service.KisiService;
import com.sais.service.MuracaatService;
import com.sais.service.MernisService;
import com.sais.service.MuracaatWorkflowService;
import com.sais.util.MessageUtil;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Component("aileFertController")
@ViewScoped
@Getter
@Setter
@Slf4j
public class AileFertController implements Serializable {

    private final AileFertService aileFertService;
    private final LookupService lookupService;
    private final MernisService mernisService;
    private final KisiService kisiService;
    private final MuracaatService muracaatService;
    private final MuracaatWorkflowService muracaatWorkflowService;

    
    private Long muracaatId;
    private String tcKimlikNo;
    private AileFert selectedAileFert;
    private AileFert newAileFert;
    private AileFert detayFert; // Detay modal için seçili fert

  
    private List<AileFert> aileFertList = new ArrayList<>();
    private List<YakinlikKodu> yakinlikKodlari;
    private List<Meslek> meslekler;
    private List<OzelStatu> ozelStatuler;
    private List<EngelliTipi> anaEngelliTipleri;
    private List<EngelliTipi> altEngelliTipleri = new ArrayList<>();
    private SGKDurum[] sgkDurumlari;
    private OgrenimDurum[] ogrenimDurumlari;

    // Engel bilgisi için seçili ana tip
    private EngelliTipi selectedAnaEngelTipi;
   
    private AileFertEngelBilgisi engelBilgisi;
    private AileFertHastalikBilgisi hastalikBilgisi;

    public AileFertController(AileFertService aileFertService, 
                             LookupService lookupService,
                             com.sais.service.MernisService mernisService,
                             com.sais.service.KisiService kisiService,
                             com.sais.service.MuracaatService muracaatService,
                             MuracaatWorkflowService muracaatWorkflowService) {
        this.aileFertService = aileFertService;
        this.lookupService = lookupService;
        this.mernisService = mernisService;
        this.kisiService = kisiService;
        this.muracaatService = muracaatService;
        this.muracaatWorkflowService = muracaatWorkflowService;
    }

    @PostConstruct
    public void init() {
        loadLookupData();
        prepareNewAileFert();
    }

    private void loadLookupData() {
        yakinlikKodlari = lookupService.getAllYakinlikKodlari();
        meslekler = lookupService.getAllMeslekler();
        ozelStatuler = lookupService.getAllOzelStatuler();
        anaEngelliTipleri = lookupService.getAnaEngelliTipleri();
        sgkDurumlari = SGKDurum.values();
        ogrenimDurumlari = OgrenimDurum.values();
    }

    public void loadAileFertleri(Long muracaatId) {
        if (yakinlikKodlari == null || yakinlikKodlari.isEmpty()) {
            loadLookupData();
        }
        
        this.muracaatId = muracaatId;
        if (muracaatId != null) {
            aileFertList = aileFertService.findByMuracaatId(muracaatId);
        } else {
            aileFertList = new ArrayList<>();
        }
    }

 
    public void prepareNewAileFert() {
        newAileFert = new AileFert();
        engelBilgisi = new AileFertEngelBilgisi();
        hastalikBilgisi = new AileFertHastalikBilgisi();
        selectedAnaEngelTipi = null;
        altEngelliTipleri = new ArrayList<>();
    }

    /**
     * Ana engel tipi seçildiğinde alt tipleri yükler
     */
    public void onAnaEngelTipiChange() {
        try {
            if (selectedAnaEngelTipi != null && selectedAnaEngelTipi.getId() != null) {
                altEngelliTipleri = lookupService.getAltEngelliTipleri(selectedAnaEngelTipi.getId());
                // Alt tip seçimini temizle
                engelBilgisi.setEngelliTipi(null);
                log.info("Ana engel tipi seçildi: {} - {} alt tip bulundu", 
                    selectedAnaEngelTipi.getAdi(), altEngelliTipleri.size());
            } else {
                altEngelliTipleri = new ArrayList<>();
                engelBilgisi.setEngelliTipi(null);
            }
        } catch (Exception e) {
            log.error("Alt engel tipleri yüklenirken hata", e);
            MessageUtil.showErrorMessage("Hata", "Alt engel tipleri yüklenemedi");
            altEngelliTipleri = new ArrayList<>();
        }
    }

 
    public void sorgulaMernis() {
        try {
            if (tcKimlikNo == null || tcKimlikNo.isEmpty()) {
                MessageUtil.showWarningMessage("Uyarı", "TC Kimlik No giriniz");
                return;
            }

            Kisi kisi = kisiService.getOrCreateFromMernis(tcKimlikNo);
            newAileFert.setKisi(kisi);
            if (!kisi.getGebzeIkameti()) {
                MessageUtil.showInfoMessage("Bilgi", 
                    "Kişi Gebze dışında ikamet ediyor. Yakın olarak eklenebilir.");
            } else {
                MessageUtil.showInfoMessage("Başarılı", 
                    "Kişi bilgileri getirildi: " + kisi.getAd() + " " + kisi.getSoyad());
            }

        } catch (Exception e) {
            log.error("MERNİS sorgu hatası", e);
            MessageUtil.showErrorMessage("Hata", "MERNİS sorgusu sırasında hata: " + e.getMessage());
        }
    }

  
    public void getirYakinlari() {
        log.info("👨‍👩‍👧‍👦 Yakınlık servisi sorgusu başlatılıyor...");
        try {
            if (muracaatId == null) {
                MessageUtil.showErrorMessage("Hata", "Müracaat bilgisi bulunamadı!");
                return;
            }

           
            Muracaat muracaat = muracaatService.findByIdWithBasvuruSahibi(muracaatId);
            if (muracaat.getBasvuruSahibi() == null) {
                MessageUtil.showErrorMessage("Hata", "Başvuru sahibi bilgisi bulunamadı!");
                return;
            }
            
            String basvuruSahibiTcNo = muracaat.getBasvuruSahibi().getTcKimlikNo();

            List<MernisYakinDTO> yakinlar = mernisService.yakinlariSorgula(basvuruSahibiTcNo);
            
            if (yakinlar == null || yakinlar.isEmpty()) {
                MessageUtil.showWarningMessage("Uyarı", "Yakın bilgisi bulunamadı");
                return;
            }

            // Her yakını DataTable'a ekle
            int eklenenSayisi = 0;
            for (MernisYakinDTO yakinDTO : yakinlar) {
                try {
                    // Kişiyi oluştur veya getir
                    Kisi kisi = kisiService.getOrCreateFromMernis(yakinDTO.getTcKimlikNo());
                    
                    // Yakınlık kodunu bul
                    YakinlikKodu yakinlikKodu = yakinlikKodlari.stream()
                        .filter(yk -> yk.getKod().equals(yakinDTO.getYakinlikKodu()))
                        .findFirst()
                        .orElse(null);
                    
                    if (yakinlikKodu == null) {
                        log.warn("Yakınlık kodu bulunamadı: {}", yakinDTO.getYakinlikKodu());
                        continue;
                    }
                    
                   
                    AileFert aileFert = AileFert.builder()
                        .kisi(kisi)
                        .yakinlikKodu(yakinlikKodu)
                        .build();
                    
                   
                    aileFert.setMuracaat(muracaat);
                    
              
                    aileFertService.create(aileFert);
                    eklenenSayisi++;
                    
                    log.info("      ├─ Eklendi: {} {} ({})", 
                        kisi.getAd(), kisi.getSoyad(), yakinlikKodu.getAdi());
                    
                } catch (Exception ex) {
                    log.error("Yakın eklenirken hata: {}", yakinDTO.getTcKimlikNo(), ex);
                }
            }
            
            loadAileFertleri(muracaatId);
            
            MessageUtil.showInfoMessage("Başarılı", 
                eklenenSayisi + " adet yakın bilgisi otomatik olarak eklendi");
            
            // Aile fertleri eklendi, workflow service'e bildir
            if (eklenenSayisi > 0) {
                muracaatWorkflowService.markAileFertCompleted(muracaatId);
            }

        } catch (Exception e) {
            log.error("Yakınlık servisi hatası", e);
            MessageUtil.showErrorMessage("Hata", 
                "Yakınlık servisi sorgusu sırasında hata: " + e.getMessage());
        }
    }

   
    public void saveAileFert() {
        try {
            if (newAileFert.getId() != null) {
               
                aileFertService.update(newAileFert);
                
                if (engelBilgisi.getEngelliTipi() != null) {
                    aileFertService.updateEngelBilgisi(newAileFert.getId(), engelBilgisi);
                }

                
                if (hastalikBilgisi.getHastalikAdi() != null && !hastalikBilgisi.getHastalikAdi().trim().isEmpty()) {
                    aileFertService.updateHastalikBilgisi(newAileFert.getId(), hastalikBilgisi);
                }
                
                MessageUtil.showInfoMessage("Başarılı", "Aile ferdi güncellendi");
                log.info("Aile ferdi güncellendi: ID={}", newAileFert.getId());
                
            } else {
              
                Muracaat muracaat = new Muracaat();
                muracaat.setId(muracaatId);
                newAileFert.setMuracaat(muracaat);

                AileFert saved = aileFertService.create(newAileFert);

                if (engelBilgisi.getEngelliTipi() != null) {
                    aileFertService.updateEngelBilgisi(saved.getId(), engelBilgisi);
                }

                // Hastalık bilgisi - manuel giriş (hastalikAdi)
                if (hastalikBilgisi.getHastalikAdi() != null && !hastalikBilgisi.getHastalikAdi().trim().isEmpty()) {
                    aileFertService.updateHastalikBilgisi(saved.getId(), hastalikBilgisi);
                }

                MessageUtil.showInfoMessage("Başarılı", "Aile ferdi eklendi");
                log.info("Yeni aile ferdi eklendi: ID={}", saved.getId());
            }

            loadAileFertleri(muracaatId);
            prepareNewAileFert();
            
            // Aile ferdi başarıyla kaydedildi, workflow service'e bildir
            muracaatWorkflowService.markAileFertCompleted(muracaatId);
            // UI navigation - tab otomatik geçecek

        } catch (BusinessException e) {
            MessageUtil.showErrorMessage("Hata", e.getMessage());
        } catch (Exception e) {
            log.error("Aile ferdi kaydetme hatası", e);
            MessageUtil.showErrorMessage("Hata", "Aile ferdi kaydedilemedi: " + e.getMessage());
        }
    }

    public void updateAileFert() {
        try {
            aileFertService.update(selectedAileFert);
            MessageUtil.showInfoMessage("Başarılı", "Aile ferdi güncellendi");

            loadAileFertleri(muracaatId);

        } catch (Exception e) {
            log.error("Aile ferdi güncelleme hatası", e);
            MessageUtil.showErrorMessage("Hata", "Aile ferdi güncellenemedi: " + e.getMessage());
        }
    }

   
    public void edit(AileFert fert) {
        try {
            this.selectedAileFert = fert;
            this.newAileFert = fert;
            this.tcKimlikNo = fert.getKisi().getTcKimlikNo();
            
            // Engel bilgisi varsa yükle
            if (fert.getEngelBilgisi() != null) {
                this.engelBilgisi = fert.getEngelBilgisi();
                
                // Eğer engel tipi seçiliyse, ana tip ve alt tipleri yükle
                try {
                    if (engelBilgisi.getEngelliTipi() != null && engelBilgisi.getEngelliTipi().getUstTip() != null) {
                        this.selectedAnaEngelTipi = engelBilgisi.getEngelliTipi().getUstTip();
                        this.altEngelliTipleri = lookupService.getAltEngelliTipleri(selectedAnaEngelTipi.getId());
                    } else {
                        this.selectedAnaEngelTipi = null;
                        this.altEngelliTipleri = new ArrayList<>();
                    }
                } catch (Exception e) {
                    log.warn("Engel tipi bilgileri yüklenirken hata oluştu: {}", e.getMessage());
                    this.selectedAnaEngelTipi = null;
                    this.altEngelliTipleri = new ArrayList<>();
                }
            } else {
                this.engelBilgisi = new AileFertEngelBilgisi();
                this.selectedAnaEngelTipi = null;
                this.altEngelliTipleri = new ArrayList<>();
            }
            
            if (fert.getHastalikBilgisi() != null) {
                this.hastalikBilgisi = fert.getHastalikBilgisi();
            } else {
                this.hastalikBilgisi = new AileFertHastalikBilgisi();
            }
            
            MessageUtil.showInfoMessage("Bilgi", 
                "Düzenleme modu: " + fert.getKisi().getAd() + " " + fert.getKisi().getSoyad());
            log.info("Aile ferdi düzenleniyor: ID={}, {} {}", 
                fert.getId(), fert.getKisi().getAd(), fert.getKisi().getSoyad());
            
        } catch (Exception e) {
            log.error("Aile ferdi düzenleme hatası", e);
            MessageUtil.showErrorMessage("Hata", "Aile ferdi düzenlenemedi: " + e.getMessage());
        }
    }
    
    
    public void delete(AileFert fert) {
        try {
            aileFertService.delete(fert.getId());
            MessageUtil.showInfoMessage("Başarılı", 
                fert.getKisi().getAd() + " " + fert.getKisi().getSoyad() + " silindi");
            loadAileFertleri(muracaatId);
            
            log.info("Aile ferdi silindi: ID={}, Kişi={} {}", 
                fert.getId(), fert.getKisi().getAd(), fert.getKisi().getSoyad());
            
        } catch (Exception e) {
            log.error("Aile ferdi silme hatası", e);
            MessageUtil.showErrorMessage("Hata", "Aile ferdi silinemedi: " + e.getMessage());
        }
    }
    
    public void deleteAileFert(Long id) {
        try {
            aileFertService.delete(id);
            MessageUtil.showInfoMessage("Başarılı", "Aile ferdi silindi");
            loadAileFertleri(muracaatId);
        } catch (Exception e) {
            log.error("Aile ferdi silme hatası", e);
            MessageUtil.showErrorMessage("Hata", "Aile ferdi silinemedi: " + e.getMessage());
        }
    }

   
    public long getAileFertSayisi() {
        if (muracaatId != null) {
            return aileFertService.countByMuracaatId(muracaatId);
        }
        return 0;
    }

   
    public void oncekiMuracaatFertleriniGetir() {
        try {
            
            if (muracaatId == null) {
                MessageUtil.showWarningMessage("Uyarı", "Önce müracaat kaydı oluşturulmalıdır");
                return;
            }
            
            Muracaat muracaat = muracaatService.findById(muracaatId);
            if (muracaat == null || muracaat.getBasvuruSahibi() == null) {
                MessageUtil.showWarningMessage("Uyarı", "Başvuru sahibi bilgisi bulunamadı");
                return;
            }
            
            
            List<Muracaat> oncekiMuracaatlar = muracaatService.findByBasvuruSahibi(muracaat.getBasvuruSahibi().getId())
                .stream()
                .filter(m -> m.getDurum() == MuracaatDurum.SONUCLANDI)
                .sorted((m1, m2) -> m2.getMuracaatTarihi().compareTo(m1.getMuracaatTarihi()))
                .limit(3)
                .toList();
            
            if (oncekiMuracaatlar.isEmpty()) {
                MessageUtil.showInfoMessage("Bilgi", "Önceki sonuçlanmış müracaat bulunamadı");
                return;
            }
            
           
            Long oncekiMuracaatId = oncekiMuracaatlar.get(0).getId();
            List<AileFert> oncekiFertler = aileFertService.findByMuracaatId(oncekiMuracaatId);
            
            if (oncekiFertler.isEmpty()) {
                MessageUtil.showInfoMessage("Bilgi", "Önceki müracaatta aile ferdi kaydı bulunamadı");
                return;
            }
            
           
            int eklenenSayisi = 0;
            for (AileFert oncekiFert : oncekiFertler) {
                AileFert yeniFert = AileFert.builder()
                    .muracaat(muracaat)
                    .kisi(oncekiFert.getKisi())
                    .yakinlikKodu(oncekiFert.getYakinlikKodu())
                    .meslek(oncekiFert.getMeslek())
                    .sgkDurum(oncekiFert.getSgkDurum())
                    .ogrenimDurumu(oncekiFert.getOgrenimDurumu())
                    .ozelStatu(oncekiFert.getOzelStatu())
                    .aciklama(oncekiFert.getAciklama())
                    .build();
                
                aileFertService.create(yeniFert);
                eklenenSayisi++;
            }
            
            MessageUtil.showInfoMessage("Başarılı", 
                eklenenSayisi + " aile ferdi önceki müracaattan getirildi");
            
            loadAileFertleri(muracaatId);
            
        } catch (Exception e) {
            log.error("Önceki aile fertlerini getirme hatası", e);
            MessageUtil.showErrorMessage("Hata", "Önceki aile fertleri getirilemedi: " + e.getMessage());
        }
    }

   
    public void clearForm() {
        prepareNewAileFert();
        tcKimlikNo = null;
    }
    
 
    public void showDetail(AileFert fert) {
        try {
            this.detayFert = aileFertService.findByIdWithAllDetails(fert.getId());
            log.info("Aile ferdi detayı gösteriliyor: ID={}, {} {}", 
                fert.getId(), fert.getKisi().getAd(), fert.getKisi().getSoyad());
            
            // PrimeFaces API ile dialog içeriğini güncelle
            org.primefaces.PrimeFaces.current().ajax().update("detayForm:detayDialog");
        } catch (Exception e) {
            log.error("Aile ferdi detay gösterme hatası", e);
            MessageUtil.showErrorMessage("Hata", "Detaylar yüklenemedi: " + e.getMessage());
        }
    }
}

