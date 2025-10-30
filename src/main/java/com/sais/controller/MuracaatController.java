package com.sais.controller;

import com.sais.entity.*;
import com.sais.enums.MuracaatDurum;
import com.sais.exception.BusinessException;
import com.sais.service.*;
import com.sais.util.MessageUtil;
import com.sais.constants.MuracaatConstants;
import com.sais.bean.RaporBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component("muracaatController")
@ViewScoped
@Getter
@Setter
@Slf4j
public class MuracaatController implements Serializable {

    private final MuracaatService muracaatService;
    private final KisiService kisiService;
    private final YardimService yardimService;
    private final MernisService mernisService;
    private final MuracaatDokumanService muracaatDokumanService;
    private final MuracaatDokumanController muracaatDokumanController;
    private final AileFertService aileFertService;
    private final AileMaddiDurumService aileMaddiDurumService;

    private Muracaat selectedMuracaat;
    private String tcKimlikNo;
    private String adinaBasvurulanTcNo;
    private Boolean komisyonKararli = false;  
    private Boolean kendisiBasvurdu = true;
    private String basvuruMetni;
    private String personelGorusNotu;
    private String dokumanListesi;

    private int activeTabIndex = 0;
    private boolean muracaatTamamlandi = false;
    private boolean aileFertGirildi = false;
    private boolean maddiDurumGirildi = false;
    private boolean tutanakGirildi = false;

    // Listeler
    private List<Muracaat> muracaatList;
    private List<Muracaat> tumMuracaatlar;
    private List<YardimAltTipi> yardimTipleri;
    private List<Long> selectedYardimTipleri = new ArrayList<>();
    
    // Dialog kontrolü
    private boolean tumMuracaatlarDialogAcik = false;
    
    // Arama için
    private String aramaMuracaatNo;
    
    
    public List<Long> getTalepEdilenYardimIdler() {
        return selectedYardimTipleri;
    }

    public MuracaatController(MuracaatService muracaatService, KisiService kisiService, 
                       YardimService yardimService, MernisService mernisService,
                       MuracaatDokumanService muracaatDokumanService,
                       MuracaatDokumanController muracaatDokumanController,
                       AileFertService aileFertService,
                       AileMaddiDurumService aileMaddiDurumService) {
        this.muracaatService = muracaatService;
        this.kisiService = kisiService;
        this.yardimService = yardimService;
        this.mernisService = mernisService;
        this.muracaatDokumanService = muracaatDokumanService;
        this.muracaatDokumanController = muracaatDokumanController;
        this.aileFertService = aileFertService;
        this.aileMaddiDurumService = aileMaddiDurumService;
    }

    @PostConstruct
    public void init() {
        loadYardimTipleri();
        checkRaporRequest();
        newMuracaat();
    }
    
    private void checkRaporRequest() {
        
        jakarta.faces.context.FacesContext context = jakarta.faces.context.FacesContext.getCurrentInstance();
        String raporAction = context.getExternalContext().getRequestParameterMap().get("raporAction");
        String muracaatIdStr = context.getExternalContext().getRequestParameterMap().get("muracaatId");
        
        if ("downloadRapor".equals(raporAction) && muracaatIdStr != null) {
            try {
                Long muracaatId = Long.parseLong(muracaatIdStr);
                // RaporBean'i çağır
                jakarta.faces.context.FacesContext facesContext = jakarta.faces.context.FacesContext.getCurrentInstance();
                RaporBean raporBean = facesContext.getApplication()
                    .evaluateExpressionGet(facesContext, "#{raporBean}", RaporBean.class);
                raporBean.muracaatRaporuIndir(muracaatId);
            } catch (Exception e) {
                log.error("Rapor indirme hatası", e);
            }
        }
    }

    public void newMuracaat() {
        try {
            selectedMuracaat = new Muracaat();
            
            Long sonMuracaatNo = muracaatService.getSonMuracaatNo();
            Long yeniMuracaatNo = sonMuracaatNo + 1;
            selectedMuracaat.setMuracaatNo(yeniMuracaatNo);
            
            selectedMuracaat.setMuracaatTarihi(LocalDate.now());
            selectedMuracaat.setKomisyonKararli(false);
            selectedMuracaat.setKendisiBasvurdu(true);
            selectedMuracaat.setDurum(MuracaatDurum.BEKLEMEDE);
            
            
            tcKimlikNo = null;
            adinaBasvurulanTcNo = null;
            komisyonKararli = false;
            kendisiBasvurdu = true;
            basvuruMetni = null;
            personelGorusNotu = null;
            dokumanListesi = null;
         
            activeTabIndex = 0;
            muracaatTamamlandi = false;
            aileFertGirildi = false;
            maddiDurumGirildi = false;
            tutanakGirildi = false;
            
           
            selectedYardimTipleri.clear();
            
            
            detayMuracaat = null;
            detayYardimKararlari = null;
            
           
            activeTabIndex = 0;
            
            log.info("Yeni müracaat başlatıldı - Müracaat No: {}, Active Tab: {}", yeniMuracaatNo, activeTabIndex);
            
            MessageUtil.showInfoMessage("Başarılı", "Yeni müracaat başlatıldı. Müracaat No: " + yeniMuracaatNo);
                
        } catch (Exception e) {
            log.error("Yeni müracaat başlatılırken hata", e);
            MessageUtil.showErrorMessage("Hata", "Yeni müracaat başlatılırken hata: " + e.getMessage());
        }
    }

    public void mernistenKisiSorgula() {
        try {
            if (tcKimlikNo == null || tcKimlikNo.isEmpty()) {
                MessageUtil.showWarningMessage("Uyarı", "TC Kimlik No giriniz");
                return;
            }

            Kisi kisi = kisiService.getOrCreateFromMernis(tcKimlikNo);
            selectedMuracaat.setBasvuruSahibi(kisi);

            if (!kisi.getGebzeIkameti()) {
                MessageUtil.showWarningMessage("Uyarı", 
                    "Başvuru sahibi Gebze dışında ikamet ediyor. Müracaat kabul edilmeyebilir.");
            }

            if (muracaatService.findByBasvuruSahibi(kisi.getId()).stream()
                    .anyMatch(m -> m.getDurum() != MuracaatDurum.SONUCLANDI)) {
                MessageUtil.showErrorMessage("Hata", 
                    "Bu kişinin sonuçlanmamış bir müracaatı bulunmaktadır!");
            }

            MessageUtil.showInfoMessage("Başarılı", "Kişi bilgileri getirildi");
            
        } catch (Exception e) {
            log.error("MERNİS sorgu hatası", e);
            MessageUtil.showErrorMessage("Hata", "MERNİS sorgusu sırasında hata oluştu: " + e.getMessage());
        }
    }

    public void saveMuracaat() {
        try {
            selectedMuracaat.setBasvuruMetnu(basvuruMetni);
            selectedMuracaat.setPersonelGorusNotu(personelGorusNotu);
            selectedMuracaat.setDokumanListesi(dokumanListesi);
            selectedMuracaat.setKomisyonKararli(komisyonKararli);
            selectedMuracaat.setKendisiBasvurdu(kendisiBasvurdu);
            
            if (!kendisiBasvurdu && adinaBasvurulanTcNo != null && !adinaBasvurulanTcNo.isEmpty()) {
                Kisi adinaBasvurulan = kisiService.getOrCreateFromMernis(adinaBasvurulanTcNo);
                selectedMuracaat.setAdinaBasvurulanKisi(adinaBasvurulan);
            }
            
            Muracaat saved;
            boolean isUpdate = selectedMuracaat.getId() != null;
            
            if (isUpdate) {
             
                muracaatDokumanService.updateMuracaatDokumanListesi(selectedMuracaat.getId());
                
                saved = muracaatService.update(selectedMuracaat);
                
                saved = muracaatService.findByIdWithBasvuruSahibi(saved.getId());
                MessageUtil.showInfoMessage("Başarılı", "Müracaat güncellendi. Müracaat No: " + saved.getMuracaatNo());
            } else {
                
                log.info("Yeni müracaat oluşturuluyor");
                saved = muracaatService.create(selectedMuracaat);
                
                // Yardım taleplerini ekle (sadece yeni kayıtta)
                for (Long yardimTipiId : selectedYardimTipleri) {
                    YardimAltTipi yardimTipi = yardimService.findYardimAltTipiById(yardimTipiId);
                    MuracaatYardimTalep talep = MuracaatYardimTalep.builder()
                            .muracaat(saved)
                            .yardimAltTipi(yardimTipi)
                            .build();
                    saved.addYardimTalep(talep);
                }
                
                // Yardım taleplerini kaydet
                if (!saved.getYardimTalepleri().isEmpty()) {
                    saved = muracaatService.update(saved);
                    log.info("✅ Yardım talepleri kaydedildi - Müracaat ID: {}, Talep Sayısı: {}", 
                        saved.getId(), saved.getYardimTalepleri().size());
                }
                
                // Geçici dosyaları yükle
                if (!muracaatDokumanController.getGeciciDosyalar().isEmpty()) {
                    log.info("🔄 {} geçici dosya yükleniyor - Müracaat ID: {}", 
                        muracaatDokumanController.getGeciciDosyalar().size(), saved.getId());
                    muracaatDokumanController.yukleGeciciDosyalar(saved.getId());
                    log.info("✅ Geçici dosyalar yüklendi");
                }
                
                muracaatDokumanService.updateMuracaatDokumanListesi(saved.getId());
                
               
                saved = muracaatService.findByIdWithBasvuruSahibi(saved.getId());
                MessageUtil.showInfoMessage("Başarılı", "Müracaat kaydedildi. Müracaat No: " + saved.getMuracaatNo());
                
                log.info("Yeni müracaat kaydedildi - ID: {}, Komisyon Kararlı: {}", saved.getId(), komisyonKararli);
                
                if (!komisyonKararli) {
                    activeTabIndex = com.sais.constants.MuracaatConstants.TAB_KOMISYONSUZ_KARAR;
                    log.info("Komisyonsuz müracaat - Tab index ayarlandı: {} (Komisyonsuz Karar)", activeTabIndex);
                    
                    // JavaScript ile tab değişimini zorla
                    org.primefaces.PrimeFaces.current().executeScript("PF('tabViewWidget').select(" + activeTabIndex + ");");
                    log.info("JavaScript tab değişimi tetiklendi: PF('tabViewWidget').select({})", activeTabIndex);
                } else {
                    activeTabIndex = com.sais.constants.MuracaatConstants.TAB_AILE_FERTLERI;
                    log.info("Komisyonlu müracaat - Tab index ayarlandı: {} (Aile Fertleri)", activeTabIndex);
                }
            }

            selectedMuracaat = saved;
            muracaatTamamlandi = true;

        } catch (BusinessException e) {
            log.error("İş kuralı hatası: {}", e.getMessage());
            MessageUtil.showErrorMessage("Hata", e.getMessage());
        } catch (Exception e) {
            log.error("Müracaat kaydetme hatası", e);
            MessageUtil.showErrorMessage("Hata", "Beklenmeyen bir hata oluştu: " + e.getMessage());
        }
    }

   
    private void loadYardimTipleri() {
        if (komisyonKararli) {
            yardimTipleri = yardimService.findAllYardimAltTipleri();
        } else {
            yardimTipleri = yardimService.findKomisyonsuzYardimlar();
        }
    }

 
    public void onKomisyonKararliChange() {
        loadYardimTipleri();
        selectedYardimTipleri.clear();
    }

  
    public void onTabChange(org.primefaces.event.TabChangeEvent<?> event) {
        try {
            int newIndex = event.getComponent().getChildren().indexOf(event.getTab());
            
            // Null kontrolü
            if (selectedMuracaat == null) {
                log.warn("selectedMuracaat null, tab değişimi iptal edildi");
                activeTabIndex = com.sais.constants.MuracaatConstants.TAB_MURACAAT_BILGILERI;
                return;
            }
            
            // Komisyonsuz müracaatlar için tab kontrolü
            if (!komisyonKararli) {
             
                if (newIndex != com.sais.constants.MuracaatConstants.TAB_MURACAAT_BILGILERI && 
                    newIndex != com.sais.constants.MuracaatConstants.TAB_KOMISYONSUZ_KARAR) {
                    log.warn("Komisyonsuz müracaat - sadece tab 0 (müracaat bilgileri) ve 5 (komisyonsuz karar) erişilebilir");
                    MessageUtil.showWarningMessage("Uyarı", "Komisyonsuz müracaatlarda sadece müracaat bilgileri ve komisyonsuz yardım kararları erişilebilir.");
                    return;
                }
            }
            
            
            if (komisyonKararli) {
                if (newIndex == com.sais.constants.MuracaatConstants.TAB_AILE_FERTLERI && !muracaatTamamlandi) {
                    MessageUtil.showWarningMessage("Uyarı", "Önce müracaat bilgilerini kaydedin");
                    activeTabIndex = com.sais.constants.MuracaatConstants.TAB_MURACAAT_BILGILERI;
                    return;
                }

                if (newIndex == com.sais.constants.MuracaatConstants.TAB_MADDI_DURUM && !aileFertGirildi) {
                    MessageUtil.showWarningMessage("Uyarı", "Önce aile fert bilgilerini girin");
                    activeTabIndex = com.sais.constants.MuracaatConstants.TAB_AILE_FERTLERI;
                    return;
                }

                if (newIndex == com.sais.constants.MuracaatConstants.TAB_TUTANAK && (!aileFertGirildi || !maddiDurumGirildi)) {
                    MessageUtil.showWarningMessage("Uyarı", "Önce aile fert ve maddi durum bilgilerini girin");
                    return;
                }

                if (newIndex == com.sais.constants.MuracaatConstants.TAB_TUTANAK && 
                    selectedMuracaat.getDurum() != MuracaatDurum.TAHKIKATA_SEVK) {
                    MessageUtil.showWarningMessage("Uyarı", "Tutanak için müracaat tahkikata sevk edilmelidir");
                    return;
                }

                if (newIndex == com.sais.constants.MuracaatConstants.TAB_KOMISYONLU_KARAR) {
                    if (selectedMuracaat.getDurum() != MuracaatDurum.DEGERLENDIRME_KOMISYONU) {
                        MessageUtil.showWarningMessage("Uyarı", "Karar için müracaat değerlendirme komisyonuna gönderilmelidir");
                        return;
                    }
                }
            }

            activeTabIndex = newIndex;
            log.info("Tab değişti: {} -> komisyonKararli={}", newIndex, komisyonKararli);
            
        } catch (Exception e) {
            log.error("Tab değiştirme hatası", e);
            MessageUtil.showErrorMessage("Hata", "Tab değiştirilirken hata oluştu");
        }
    }

    public void nextTab() {
        if (activeTabIndex >= com.sais.constants.MuracaatConstants.MAX_TAB_INDEX) {
            MessageUtil.showWarningMessage("Uyarı", "Son sekmedesiniz");
            return;
        }
        
        activeTabIndex++;
        MessageUtil.showInfoMessage("Başarılı", "Sonraki adıma geçildi");
    }

  
    public void tahkikataSevkEt() {
        try {
            muracaatService.tahkikataSevkEt(selectedMuracaat.getId());
            selectedMuracaat = muracaatService.findById(selectedMuracaat.getId());
            MessageUtil.showInfoMessage("Başarılı", "Müracaat tahkikata sevk edildi");
            activeTabIndex = com.sais.constants.MuracaatConstants.TAB_TUTANAK; // Tutanak sekmesine geç
        } catch (Exception e) {
            MessageUtil.showErrorMessage("Hata", e.getMessage());
        }
    }

  
    public void komisyonaGonder() {
        try {
            muracaatService.komisyonaGonder(selectedMuracaat.getId());
            selectedMuracaat = muracaatService.findById(selectedMuracaat.getId());
            MessageUtil.showInfoMessage("Başarılı", "Müracaat değerlendirme komisyonuna gönderildi");
            activeTabIndex = com.sais.constants.MuracaatConstants.TAB_KOMISYONLU_KARAR; // Yardım kararları sekmesine geç
        } catch (Exception e) {
            MessageUtil.showErrorMessage("Hata", e.getMessage());
        }
    }
  
    public void refreshSelectedMuracaat() {
        if (selectedMuracaat != null && selectedMuracaat.getId() != null) {
            selectedMuracaat = muracaatService.findByIdWithAllDetails(selectedMuracaat.getId());
            log.info("Müracaat yeniden yüklendi - Durum: {}", selectedMuracaat.getDurum());
        }
    }
    

    public void degerlendirmeKomisyonunaGonder() {
        try {
            muracaatService.komisyonaGonder(selectedMuracaat.getId());
            selectedMuracaat = muracaatService.findById(selectedMuracaat.getId());
            log.info("Müracaat değerlendirme komisyonuna gönderildi");
        } catch (Exception e) {
            log.error("Komisyona gönderme hatası", e);
        }
    }

    public void kesinlestir() {
        try {
            log.info("Komisyonlu müracaat kesinleştirme başlatıldı - Müracaat ID: {}", selectedMuracaat.getId());
            
            muracaatService.kesinlestir(selectedMuracaat.getId());
            // Müracaat'ı tam detaylarıyla yeniden yükle
            selectedMuracaat = muracaatService.findByIdWithAllDetails(selectedMuracaat.getId());
            
            MessageUtil.showInfoMessage("Başarılı", 
                "Müracaat kesinleştirildi ve sonuçlandırıldı. Durum: " + selectedMuracaat.getDurum());
            
            log.info("Müracaat sonuçlandı, yeni müracaat başlatılıyor...");
            
            // Müracaat sonuçlandı - yeni müracaat için formu temizle
            newMuracaat();
            
            log.info("Yeni müracaat başlatıldı - Active Tab: {}", activeTabIndex);
            
        } catch (Exception e) {
            log.error("Kesinleştirme hatası", e);
            MessageUtil.showErrorMessage("Hata", e.getMessage());
        }
    }

    public void komisyonsuzTamamla() {
        try {
            log.info("Komisyonsuz müracaat tamamlama başlatıldı - Müracaat ID: {}", selectedMuracaat.getId());
            
            muracaatService.komisyonsuzMuracaatiTamamla(selectedMuracaat.getId());
            selectedMuracaat = muracaatService.findByIdWithAllDetails(selectedMuracaat.getId());
            
            MessageUtil.showInfoMessage("Başarılı", 
                "Müracaat tamamlandı! Durum: " + selectedMuracaat.getDurum());
            
            log.info("Müracaat sonuçlandı, yeni müracaat başlatılıyor...");
            
          
            newMuracaat();
            
            log.info("Yeni müracaat başlatıldı - Active Tab: {}", activeTabIndex);
            
        } catch (Exception e) {
            log.error("Komisyonsuz müracaat tamamlama hatası", e);
            MessageUtil.showErrorMessage("Hata", e.getMessage());
        }
    }

    
    public void adinaBasvurulanKisiSorgula() {
        try {
            if (adinaBasvurulanTcNo == null || adinaBasvurulanTcNo.isEmpty()) {
                MessageUtil.showWarningMessage("Uyarı", "TC Kimlik No giriniz");
                return;
            }
            
            // Başvuran ile aynı kişi olamaz
            if (selectedMuracaat.getBasvuruSahibi() != null && 
                adinaBasvurulanTcNo.equals(selectedMuracaat.getBasvuruSahibi().getTcKimlikNo())) {
                MessageUtil.showErrorMessage("Hata", 
                    "Adına başvurulan kişi, başvuruyu yapan kişi ile aynı olamaz!");
                return;
            }
            
            Kisi kisi = kisiService.getOrCreateFromMernis(adinaBasvurulanTcNo);
            selectedMuracaat.setAdinaBasvurulanKisi(kisi);
            
            MessageUtil.showInfoMessage("Başarılı", 
                "Adına başvurulan kişi bilgileri getirildi: " + kisi.getAd() + " " + kisi.getSoyad());
            
        } catch (Exception e) {
            log.error("Adına başvurulan kişi sorgu hatası", e);
            MessageUtil.showErrorMessage("Hata", "Kişi sorgusu sırasında hata: " + e.getMessage());
        }
    }
    
   
    public void listMuracaatlar() {
        try {
            muracaatList = muracaatService.findAll();
            MessageUtil.showInfoMessage("Başarılı", muracaatList.size() + " adet müracaat listelendi");
        } catch (Exception e) {
            log.error("Müracaat listeleme hatası", e);
            MessageUtil.showErrorMessage("Hata", "Müracaatlar listelenirken hata oluştu");
        }
    }
    
   
    public void onMuracaatSelect() {
        try {
            if (selectedMuracaat == null) {
                return;
            }
            
            selectedMuracaat = muracaatService.findByIdWithAllDetails(selectedMuracaat.getId());
            Long muracaatId = selectedMuracaat.getId();
            
            muracaatTamamlandi = selectedMuracaat.getId() != null;
            komisyonKararli = selectedMuracaat.getKomisyonKararli();
            

            aileFertGirildi = aileFertService.countByMuracaatId(muracaatId) > 0;
            maddiDurumGirildi = aileMaddiDurumService.existsByMuracaatId(muracaatId);
            
            selectedYardimTipleri = selectedMuracaat.getYardimTalepleri().stream()
                .map(yt -> yt.getYardimAltTipi().getId())
                .collect(Collectors.toList());
            
            activeTabIndex = com.sais.constants.MuracaatConstants.TAB_MURACAAT_BILGILERI;
            
            MessageUtil.showInfoMessage("Başarılı", "Müracaat yüklendi. Tab'larda gezinebilirsiniz.");
        } catch (Exception e) {
            log.error("Müracaat seçme hatası", e);
            MessageUtil.showErrorMessage("Hata", "Müracaat yüklenemedi: " + e.getMessage());
        }
    }
    
    /* 
    public void refreshSelectedMuracaat() {
        if (selectedMuracaat != null && selectedMuracaat.getId() != null) {
            selectedMuracaat = muracaatService.findById(selectedMuracaat.getId());
            log.info("selectedMuracaat yenilendi - Durum: {}", selectedMuracaat.getDurum());
        }
    }*/
    
   
    public boolean isAileFertTabEnabled() {
        return muracaatTamamlandi;
    }

    public boolean isMaddiDurumTabEnabled() {
        return muracaatTamamlandi && aileFertGirildi;
    }
    
    // UI için getter - her çağrıda güncelle
    public boolean getAileFertGirildi() {
        if (selectedMuracaat != null && selectedMuracaat.getId() != null) {
            aileFertGirildi = aileFertService.countByMuracaatId(selectedMuracaat.getId()) > 0;
        }
        return aileFertGirildi;
    }
    
    public boolean getMaddiDurumGirildi() {
        if (selectedMuracaat != null && selectedMuracaat.getId() != null) {
            maddiDurumGirildi = aileMaddiDurumService.existsByMuracaatId(selectedMuracaat.getId());
        }
        return maddiDurumGirildi;
    }

    public boolean isTutanakTabEnabled() {
        
        if (selectedMuracaat != null && selectedMuracaat.getId() != null) {
            Muracaat freshMuracaat = muracaatService.findById(selectedMuracaat.getId());
            selectedMuracaat = freshMuracaat; // Cache'i güncelle
            
            boolean enabled = freshMuracaat.getDurum() == MuracaatDurum.TAHKIKATA_SEVK;
            log.info("Tutanak tab - Durum: {} - Enabled: {}", freshMuracaat.getDurum(), enabled);
            return enabled;
        }
        return false;
    }

    public boolean isYardimKararTabEnabled() {
        if (komisyonKararli) {
            return selectedMuracaat.getDurum() == MuracaatDurum.DEGERLENDIRME_KOMISYONU;
        } else {
            return muracaatTamamlandi;
        }
    }
    
   
    public void tumMuracaatlariGoster() {
        try {
            
            aramaMuracaatNo = "";
            tumMuracaatlar = muracaatService.findTamamlananVeBekleyenMuracaatlar();
            MessageUtil.showInfoMessage("Bilgi", tumMuracaatlar.size() + " adet müracaat bulundu");
        } catch (Exception e) {
            log.error("Müracaatlar yüklenirken hata", e);
            MessageUtil.showErrorMessage("Hata", "Müracaatlar yüklenirken hata oluştu: " + e.getMessage());
        }
    }
    
    public void tumMuracaatlariGetir() {
        aramaMuracaatNo = ""; // Arama temizle
        tumMuracaatlariGoster();
    }
    
    public void aramaInputTemizle() {
        aramaMuracaatNo = "";
    }
    
    public void aramaMuracaatNoIle() {
        try {
            if (aramaMuracaatNo == null || aramaMuracaatNo.trim().isEmpty()) {
                MessageUtil.showInfoMessage("Uyarı", "Lütfen aranacak müracaat numarasını giriniz");
                return;
            }
            
            String aramaNo = aramaMuracaatNo.trim();
            List<Muracaat> tumListe = muracaatService.findTamamlananVeBekleyenMuracaatlar();
            
            // Tam eşleşme arama
            tumMuracaatlar = tumListe.stream()
                .filter(m -> m.getMuracaatNo() != null && 
                           String.valueOf(m.getMuracaatNo()).equals(aramaNo))
                .collect(Collectors.toList());
            
            if (tumMuracaatlar.isEmpty()) {
                MessageUtil.showInfoMessage("Bilgi", "'" + aramaNo + "' numaralı müracaat bulunamadı");
            } else {
                MessageUtil.showInfoMessage("Bilgi", tumMuracaatlar.size() + " adet müracaat bulundu");
            }
            
        } catch (Exception e) {
            log.error("Müracaat aranırken hata", e);
            MessageUtil.showErrorMessage("Hata", "Müracaat aranırken hata oluştu: " + e.getMessage());
        }
    }
    
    
    public void muracaataDevamEt(Muracaat muracaat) {
        try {
           
            selectedMuracaat = muracaatService.findByIdWithAllDetails(muracaat.getId());
            
            
            muracaatTamamlandi = selectedMuracaat.getId() != null;
            komisyonKararli = selectedMuracaat.getKomisyonKararli();
            kendisiBasvurdu = selectedMuracaat.getKendisiBasvurdu();
            basvuruMetni = selectedMuracaat.getBasvuruMetnu();
            personelGorusNotu = selectedMuracaat.getPersonelGorusNotu();
            dokumanListesi = selectedMuracaat.getDokumanListesi();
            
            Long muracaatId = selectedMuracaat.getId();
            
            aileFertGirildi = aileFertService.countByMuracaatId(muracaatId) > 0;
            maddiDurumGirildi = aileMaddiDurumService.existsByMuracaatId(muracaatId);
            
            selectedYardimTipleri = selectedMuracaat.getYardimTalepleri().stream()
                .map(yt -> yt.getYardimAltTipi().getId())
                .collect(Collectors.toList());
            
          
            if (selectedMuracaat.getDurum() == MuracaatDurum.SONUCLANDI) {
                
                activeTabIndex = com.sais.constants.MuracaatConstants.TAB_MURACAAT_BILGILERI;
                
                
                aileFertGirildi = true;
                maddiDurumGirildi = true;
             
            } else if (selectedMuracaat.getDurum() == MuracaatDurum.BEKLEMEDE) {
               
                if (!komisyonKararli) {
                   
                    activeTabIndex = com.sais.constants.MuracaatConstants.TAB_KOMISYONSUZ_KARAR;
                } else {
                   
                    if (!aileFertGirildi) {
                        activeTabIndex = com.sais.constants.MuracaatConstants.TAB_AILE_FERTLERI;
                    } else if (!maddiDurumGirildi) {
                        activeTabIndex = com.sais.constants.MuracaatConstants.TAB_MADDI_DURUM;
                    } else if (selectedMuracaat.getDurum() == MuracaatDurum.TAHKIKATA_SEVK) {
                        activeTabIndex = com.sais.constants.MuracaatConstants.TAB_TUTANAK;
                    } else if (selectedMuracaat.getDurum() == MuracaatDurum.DEGERLENDIRME_KOMISYONU) {
                        activeTabIndex = com.sais.constants.MuracaatConstants.TAB_KOMISYONLU_KARAR;
                    } else {
                        activeTabIndex = com.sais.constants.MuracaatConstants.TAB_MURACAAT_BILGILERI;
                    }
                }
                MessageUtil.showInfoMessage("Başarılı", "Müracaat yüklendi. Kaldığınız yerden devam edebilirsiniz.");
            }
            
            muracaatTamamlandi = true;
            
        } catch (Exception e) {
            log.error("Müracaata devam edilirken hata", e);
            MessageUtil.showErrorMessage("Hata", "Müracaat yüklenirken hata oluştu: " + e.getMessage());
        }
    }
    
  
    public void muracaatiIptalEt(Muracaat muracaat) {
        try {
            muracaatService.yarimKalanMuracaatiIptalEt(muracaat.getId());
            tumMuracaatlar.remove(muracaat);
            MessageUtil.showInfoMessage("Başarılı", "Müracaat iptal edildi");
        } catch (Exception e) {
            log.error("Müracaat iptal edilirken hata", e);
            MessageUtil.showErrorMessage("Hata", "Müracaat iptal edilirken hata oluştu: " + e.getMessage());
        }
    }
    
    public void setAileFertGirildi(boolean aileFertGirildi) {
        this.aileFertGirildi = aileFertGirildi;
    }
    
    public void setMaddiDurumGirildi(boolean maddiDurumGirildi) {
        this.maddiDurumGirildi = maddiDurumGirildi;
    }
    
   
    public long getBekleyenMuracaatSayisi() {
        try {
            return muracaatService.findByDurum(MuracaatDurum.BEKLEMEDE).size();
        } catch (Exception e) {
            return 0;
        }
    }
    
    public long getSonuclananMuracaatSayisi() {
        try {
            return muracaatService.findByDurum(MuracaatDurum.SONUCLANDI).size();
        } catch (Exception e) {
            return 0;
        }
    }
    
    public long getTahkikattaMuracaatSayisi() {
        try {
            return muracaatService.findByDurum(MuracaatDurum.TAHKIKATA_SEVK).size();
        } catch (Exception e) {
            return 0;
        }
    }
    
    public long getRedEdilenMuracaatSayisi() {
        try {
            return muracaatService.findByDurum(MuracaatDurum.TALEP_IPTAL_EDILDI).size();
        } catch (Exception e) {
            return 0;
        }
    }
    
   
    private Muracaat detayMuracaat;
    private List<YardimKarar> detayYardimKararlari;
    
    public Muracaat getDetayMuracaat() {
        return detayMuracaat;
    }
    
    public void setDetayMuracaat(Muracaat detayMuracaat) {
        this.detayMuracaat = detayMuracaat;
    }
    
    public List<YardimKarar> getDetayYardimKararlari() {
        return detayYardimKararlari;
    }
    
    public void setDetayYardimKararlari(List<YardimKarar> detayYardimKararlari) {
        this.detayYardimKararlari = detayYardimKararlari;
    }
    
    /**
     * Müracaat detaylarını göster
     */
    public void showMuracaatDetay(Muracaat muracaat) {
        try {
            log.info("Müracaat detayları yükleniyor - ID: {}, Müracaat No: {}", 
                muracaat.getId(), muracaat.getMuracaatNo());
            
            detayMuracaat = null;
            detayYardimKararlari = null;
            
            // Yeni veriyi yükle
            detayMuracaat = muracaatService.findByIdWithAllDetails(muracaat.getId());
            detayYardimKararlari = yardimService.findYardimKararlariByMuracaat(muracaat.getId());
            
            log.info("Detay müracaat yüklendi - ID: {}, Müracaat No: {}", 
                detayMuracaat != null ? detayMuracaat.getId() : "null",
                detayMuracaat != null ? detayMuracaat.getMuracaatNo() : "null");
            log.info("Detay yardım kararları sayısı: {}", detayYardimKararlari != null ? detayYardimKararlari.size() : "null");
            
            // Veriyi kontrol et
            if (detayYardimKararlari != null && !detayYardimKararlari.isEmpty()) {
                log.info("Yardım kararları detay:");
                detayYardimKararlari.forEach(karar -> log.info("Karar: {}, Yardım Tipi: {}, Durum: {}", 
                    karar.getId(), karar.getYardimAltTipi().getAdi(), karar.getYardimDurum()));
            }
            
        } catch (Exception e) {
            log.error("Müracaat detayları yüklenirken hata: {}", e.getMessage(), e);
            MessageUtil.showErrorMessage("Hata", "Müracaat detayları yüklenemedi: " + e.getMessage());
        }
    }
}

