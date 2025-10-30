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
public class BorcBilgisiDTO {

    private Long id;
    
    @NotNull(message = "Borç türü ID boş olamaz")
    @Positive(message = "Borç türü ID pozitif olmalıdır")
    private Long borcTuruId;
    
    private String borcTuruAdi;
    
    @NotNull(message = "Borç tutarı boş olamaz")
    @Positive(message = "Borç tutarı pozitif olmalıdır")
    private Double borcTutari;
    
    @Size(max = 255, message = "Açıklama en fazla 255 karakter olabilir")
    private String aciklama;
}


