package com.sais.enums;

import lombok.Getter;

@Getter
public enum YardimTipi {
    
    NAKDI("Nakdi", "Nakit para yardımı"),
    AYNI("Ayni", "Mal/Eşya yardımı"),
    SAGLIK("Sağlık", "Sağlık yardımı"),
    EGITIM("Eğitim", "Eğitim yardımı"),
    BARIS("Barış", "Barınma yardımı"),
    DIGER("Diğer", "Diğer yardım türleri");
    
    private final String label;
    private final String description;
    
    YardimTipi(String label, String description) {
        this.label = label;
        this.description = description;
    }
}


