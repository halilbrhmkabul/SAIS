package com.sais.enums;

import lombok.Getter;

@Getter
public enum EvTipi {
    
    APARTMAN_DAIRESI("Apartman Dairesi", "Apartman dairesi"),
    MUSTAKIL_EV("Müstakil Ev", "Müstakil ev"),
    GECEKONDU("Gecekondu", "Gecekondu"),
    VILLA("Villa", "Villa"),
    DIGER("Diğer", "Diğer");
    
    private final String label;
    private final String description;
    
    EvTipi(String label, String description) {
        this.label = label;
        this.description = description;
    }
}


