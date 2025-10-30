package com.sais.controller;

import com.sais.dto.MuracaatDokumanDTO;
import com.sais.entity.Personel;
import com.sais.service.MuracaatDokumanService;
import com.sais.util.MessageUtil;
import com.sais.util.MultipartFileAdapter;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Component("muracaatDokumanController")
@ViewScoped
@Getter
@Setter
@Slf4j
public class MuracaatDokumanController implements Serializable {

    private final MuracaatDokumanService dokumanService;

    
    private Long selectedMuracaatId;
    private List<MuracaatDokumanDTO> dokumanList;
    private MuracaatDokumanDTO selectedDokuman;
    
    
    private List<GeciciDosya> geciciDosyalar = new ArrayList<>();
    
   
    @lombok.Data
    @lombok.AllArgsConstructor
    public static class GeciciDosya {
        private String fileName;
        private String contentType;
        private byte[] content;
        private long size;
    }
    
    public MuracaatDokumanController(MuracaatDokumanService dokumanService) {
        this.dokumanService = dokumanService;
    }

    @PostConstruct
    public void init() {
        dokumanList = new ArrayList<>();
        geciciDosyalar = new ArrayList<>();
    }

   
    public void handleFileUpload(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            
           
            if (selectedMuracaatId == null) {
              
                GeciciDosya geciciDosya = new GeciciDosya(
                    uploadedFile.getFileName(),
                    uploadedFile.getContentType(),
                    uploadedFile.getContent(), 
                    uploadedFile.getSize()
                );
                geciciDosyalar.add(geciciDosya);
                
                MessageUtil.showInfoMessage("Başarılı", 
                    "Dosya eklendi: " + uploadedFile.getFileName() + " (Müracaat kaydedilince yüklenecek)");
                log.info("Geçici dosya eklendi: {} ({} bytes)", 
                    uploadedFile.getFileName(), uploadedFile.getSize());
                return;
            }
            
            
            MultipartFile multipartFile = convertToMultipartFile(uploadedFile);
            
            
            Personel yukleyenPersonel = null;
            
            MuracaatDokumanDTO savedDokuman = dokumanService.uploadDokuman(
                multipartFile,
                selectedMuracaatId,
                yukleyenPersonel
            );

            loadDokumanlar();
            
            // Doküman listesini güncelle (REQUIRES_NEW transaction ile deadlock önlenir)
            log.info("🔄 Doküman listesi güncelleme başlatılıyor - Müracaat ID: {}", selectedMuracaatId);
            try {
                dokumanService.updateMuracaatDokumanListesi(selectedMuracaatId);
                log.info("✅ Doküman listesi güncelleme tamamlandı");
            } catch (Exception e) {
                log.warn("⚠️ Doküman listesi güncellenemedi (devam ediliyor): {}", e.getMessage());
            }

            MessageUtil.showInfoMessage("Başarılı", 
                "Doküman başarıyla yüklendi: " + uploadedFile.getFileName());
            
            log.info("Doküman yüklendi - ID: {}, Müracaat ID: {}", 
                savedDokuman.getId(), selectedMuracaatId);

        } catch (Exception e) {
            log.error("Doküman yükleme hatası", e);
            MessageUtil.showErrorMessage("Hata", 
                "Doküman yüklenirken hata oluştu: " + e.getMessage());
        }
    }
    
    
    public void yukleGeciciDosyalar(Long muracaatId) {
        if (geciciDosyalar.isEmpty()) {
            log.info("Yüklenecek geçici dosya yok");
            return;
        }
        
        this.selectedMuracaatId = muracaatId;
        
        int basariliSayisi = 0;
        int hataSayisi = 0;
        
        for (GeciciDosya geciciDosya : geciciDosyalar) {
            try {
                
                MultipartFile multipartFile = new org.springframework.mock.web.MockMultipartFile(
                    geciciDosya.getFileName(),
                    geciciDosya.getFileName(),
                    geciciDosya.getContentType(),
                    geciciDosya.getContent()
                );
                
                
                Personel yukleyenPersonel = null;
                
                dokumanService.uploadDokuman(
                    multipartFile,
                    muracaatId,
                    yukleyenPersonel
                );
                
                basariliSayisi++;
                log.info("Geçici dosya yüklendi: {} ({} bytes)", 
                    geciciDosya.getFileName(), geciciDosya.getSize());
                
            } catch (Exception e) {
                hataSayisi++;
                log.error("Geçici dosya yükleme hatası: {}", geciciDosya.getFileName(), e);
            }
        }
        
       
        geciciDosyalar.clear();
        
        
        loadDokumanlar();
        
       
        if (basariliSayisi > 0) {
            try {
                dokumanService.updateMuracaatDokumanListesi(muracaatId);
            } catch (Exception e) {
                log.warn("Doküman listesi güncellenemedi (devam ediliyor): {}", e.getMessage());
            }
        }
        
        if (basariliSayisi > 0) {
            MessageUtil.showInfoMessage("Başarılı", 
                basariliSayisi + " dosya başarıyla yüklendi" + 
                (hataSayisi > 0 ? " (" + hataSayisi + " dosya yüklenemedi)" : ""));
        }
        
        if (hataSayisi > 0 && basariliSayisi == 0) {
            MessageUtil.showErrorMessage("Hata", "Dosyalar yüklenemedi!");
        }
    }
    
   
    public void temizleGeciciDosyalar() {
        geciciDosyalar.clear();
        log.info("Geçici dosyalar temizlendi");
    }
    
   
    public int getGeciciDosyaSayisi() {
        return geciciDosyalar.size();
    }

   
    public void loadDokumanlar() {
        try {
            if (selectedMuracaatId != null) {
                dokumanList = dokumanService.getDokumanlarByMuracaatId(selectedMuracaatId);
                log.debug("Dokümanlar yüklendi - Müracaat ID: {}, Adet: {}", 
                    selectedMuracaatId, dokumanList.size());
            }
        } catch (Exception e) {
            log.error("Dokümanlar yüklenirken hata", e);
            MessageUtil.showErrorMessage("Hata", "Dokümanlar yüklenemedi");
            dokumanList = new ArrayList<>();
        }
    }

  
    public void deleteDokuman(Long dokumanId) {
        try {
            dokumanService.deleteDokuman(dokumanId);
            loadDokumanlar();
            
           
            if (selectedMuracaatId != null) {
                try {
                    dokumanService.updateMuracaatDokumanListesi(selectedMuracaatId);
                } catch (Exception e) {
                    log.warn("Doküman listesi güncellenemedi (devam ediliyor): {}", e.getMessage());
                }
            }
            
            MessageUtil.showInfoMessage("Başarılı", "Doküman silindi");
            log.info("Doküman silindi - ID: {}", dokumanId);
        } catch (Exception e) {
            log.error("Doküman silme hatası", e);
            MessageUtil.showErrorMessage("Hata", "Doküman silinemedi: " + e.getMessage());
        }
    }


    public void setMuracaatIdAndLoad(Long muracaatId) {
        this.selectedMuracaatId = muracaatId;
        loadDokumanlar();
    }
    
  
    public void syncDokumanListesi() {
        if (selectedMuracaatId != null) {
            log.info("🔄 Doküman listesi senkronize ediliyor - Müracaat ID: {}", selectedMuracaatId);
            dokumanService.updateMuracaatDokumanListesi(selectedMuracaatId);
        }
    }

   
    public long getDokumanCount() {
        if (selectedMuracaatId == null) {
            return 0;
        }
        return dokumanService.getDokumanCount(selectedMuracaatId);
    }

    private MultipartFile convertToMultipartFile(UploadedFile uploadedFile) {
        return new MultipartFileAdapter(uploadedFile);
    }
}

