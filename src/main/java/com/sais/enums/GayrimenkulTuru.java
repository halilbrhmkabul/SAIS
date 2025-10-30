package com.sais.enums;

import lombok.Getter;

@Getter
public enum GayrimenkulTuru {
    
    ARSA("Arsa", "Arsa"),
    TARLA("Tarla", "Tarla"),
    BAHCE("Bahçe", "Bahçe"),
    HISSE("Hisse", "Mülk hissesi"),
    DIGER("Diğer", "Diğer gayrimenkul");
    
    private final String label;
    private final String description;
    
    GayrimenkulTuru(String label, String description) {
        this.label = label;
        this.description = description;
    }
}


