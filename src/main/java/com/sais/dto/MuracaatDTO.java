package com.sais.dto;

import com.sais.enums.MuracaatDurum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MuracaatDTO {

    private Long id;
    
    @NotNull(message = "Müracaat numarası boş olamaz")
    @Positive(message = "Müracaat numarası pozitif olmalıdır")
    private Long muracaatNo;
    
    @NotBlank(message = "Başvuru sahibi ad soyad boş olamaz")
    @Size(max = 200, message = "Başvuru sahibi ad soyad en fazla 200 karakter olabilir")
    private String basvuruSahibiAdSoyad;
    
    @NotBlank(message = "Başvuru sahibi TC kimlik no boş olamaz")
    @Pattern(regexp = "^[1-9][0-9]{10}$", message = "Geçersiz TC kimlik numarası formatı")
    private String basvuruSahibiTcNo;
    
    @NotNull(message = "Komisyon kararlı bilgisi boş olamaz")
    private Boolean komisyonKararli;
    
    @NotNull(message = "Müracaat tarihi boş olamaz")
    @PastOrPresent(message = "Müracaat tarihi bugünden ileri olamaz")
    private LocalDate muracaatTarihi;
    
    @PastOrPresent(message = "İnceleme tarihi bugünden ileri olamaz")
    private LocalDate incelemeTarihi;
    
    @NotNull(message = "Durum boş olamaz")
    private MuracaatDurum durum;
    
    @Size(max = 2000, message = "Başvuru metni en fazla 2000 karakter olabilir")
    private String basvuruMetnu;
    
    @Size(max = 2000, message = "Personel görüş notu en fazla 2000 karakter olabilir")
    private String personelGorusNotu;
    
    @Positive(message = "Karar numarası pozitif olmalıdır")
    private Long kararNo;
    
    @PastOrPresent(message = "Karar tarihi bugünden ileri olamaz")
    private LocalDate kararTarihi;
}


