package com.sais.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GelirBilgisiDTO {

    private Long id;
    
    @NotNull(message = "Kişi ID boş olamaz")
    @Positive(message = "Kişi ID pozitif olmalıdır")
    private Long kisiId;
    
    @Pattern(regexp = "^[1-9][0-9]{10}$", message = "Geçersiz TC kimlik numarası formatı")
    private String tcKimlikNo;
    
    @NotBlank(message = "Ad soyad boş olamaz")
    @Size(max = 200, message = "Ad soyad en fazla 200 karakter olabilir")
    private String adSoyad;
    
    private String yakinlik;
    
    @NotNull(message = "Gelir türü ID boş olamaz")
    @Positive(message = "Gelir türü ID pozitif olmalıdır")
    private Long gelirTuruId;
    
    private String gelirTuruAdi;
    
    @NotNull(message = "Gelir tutarı boş olamaz")
    @Positive(message = "Gelir tutarı pozitif olmalıdır")
    private Double gelirTutari;
    
    @Size(max = 255, message = "Açıklama en fazla 255 karakter olabilir")
    private String aciklama;
}


