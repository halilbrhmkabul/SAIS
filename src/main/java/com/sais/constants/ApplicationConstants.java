package com.sais.constants;

/**
 * Uygulama genelinde kullanılan sabit değerler
 */
public final class ApplicationConstants {
    
    // Müracaat Durumları
    public static final String DURUM_BEKLEMEDE = "BEKLEMEDE";
    public static final String DURUM_SONUCLANDI = "SONUCLANDI";
    public static final String DURUM_TAHKIKATA_SEVK = "TAHKIKATA_SEVK";
    public static final String DURUM_TALEP_IPTAL_EDILDI = "TALEP_IPTAL_EDILDI";
    
    // Yardım Durumları
    public static final String YARDIM_DURUM_ONAYLANDI = "ONAYLANDI";
    public static final String YARDIM_DURUM_REDDEDILDI = "REDDEDILDI";
    public static final String YARDIM_DURUM_BEKLEMEDE = "BEKLEMEDE";
    
    // Yardım Tipleri
    public static final String YARDIM_TIPI_NAKDI = "NAKDI";
    public static final String YARDIM_TIPI_AYNI = "AYNI";
    
    // Default Değerler
    public static final Boolean DEFAULT_AKTIF = true;
    public static final Boolean DEFAULT_KOMISYON_KARARLI = false;
    public static final Boolean DEFAULT_KENDISI_BASVURDU = true;
    
    // Validation Mesajları
    public static final String MSG_GECERSIZ_TC_KIMLIK = "Geçersiz TC Kimlik Numarası!";
    public static final String MSG_KISI_BULUNAMADI = "Kişi bulunamadı";
    public static final String MSG_MURACAAT_BULUNAMADI = "Müracaat bulunamadı";
    
    // Cache Keys
    public static final String CACHE_MERNIS_DATA = "mernis_data";
    public static final String CACHE_YARDIM_TIPLERI = "yardim_tipleri";
    
    private ApplicationConstants() {
        
    }
}
