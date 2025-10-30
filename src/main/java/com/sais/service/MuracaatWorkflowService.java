package com.sais.service;

import com.sais.enums.MuracaatDurum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MuracaatWorkflowService {

    private final MuracaatService muracaatService;

    /**
     * Aile fertleri tamamlandığında çağrılır
     */
    @Transactional
    public void markAileFertCompleted(Long muracaatId) {
        log.info("Aile fertleri tamamlandı işaretlendi: {}", muracaatId);
      
    }

    /**
     * Maddi durum tamamlandığında çağrılır
     */
    @Transactional
    public void markMaddiDurumCompleted(Long muracaatId) {
        log.info("Maddi durum tamamlandı işaretlendi: {}", muracaatId);
        
    }

    /**
     * Müracaatı tahkikata sevk eder
     */
    @Transactional
    public void sevkTahkikata(Long muracaatId) {
        log.info("Müracaat tahkikata sevk ediliyor: {}", muracaatId);
        try {
            muracaatService.updateDurum(muracaatId, MuracaatDurum.TAHKIKATA_SEVK);
            log.info("Müracaat durumu TAHKIKATA_SEVK olarak güncellendi");
        } catch (Exception e) {
            log.error("Tahkikata sevk hatası: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Müracaat durumunu günceller
     */
    @Transactional
    public void updateMuracaatDurum(Long muracaatId, MuracaatDurum durum) {
        log.info("Müracaat durumu güncelleniyor: {} -> {}", muracaatId, durum);
        muracaatService.updateDurum(muracaatId, durum);
    }
}
