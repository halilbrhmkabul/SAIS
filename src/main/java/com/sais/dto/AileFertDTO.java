package com.sais.dto;

import com.sais.enums.OgrenimDurum;
import com.sais.enums.SGKDurum;
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
public class AileFertDTO {

    private Long id;
    private Long muracaatId;
    
    // Kişi Bilgileri
    @NotNull(message = "Kişi ID boş olamaz")
    @Positive(message = "Kişi ID pozitif olmalıdır")
    private Long kisiId;
    
    @NotBlank(message = "TC kimlik no boş olamaz")
    @Pattern(regexp = "^[1-9][0-9]{10}$", message = "Geçersiz TC kimlik numarası formatı")
    private String tcKimlikNo;
    
    @NotBlank(message = "Ad soyad boş olamaz")
    @Size(max = 200, message = "Ad soyad en fazla 200 karakter olabilir")
    private String adSoyad;
    
    @NotNull(message = "Doğum tarihi boş olamaz")
    @Past(message = "Doğum tarihi geçmişte olmalıdır")
    private LocalDate dogumTarihi;
    
    @Min(value = 0, message = "Yaş negatif olamaz")
    @Max(value = 150, message = "Yaş 150'den büyük olamaz")
    private Integer yas;
    
    @NotBlank(message = "Cinsiyet boş olamaz")
    private String cinsiyet;
    
    // İlişki Bilgileri
    private Long yakinlikKoduId;
    private String yakinlikAdi;
    
    // Diğer Bilgiler
    private Long ozelStatuId;
    private String ozelStatuAdi;
    private Long meslekId;
    private String meslekAdi;
    private String yaptigiIs;
    private SGKDurum sgkDurum;
    private OgrenimDurum ogrenimDurum;
    private String aciklama;
    
    // Engel Bilgisi
    private boolean engelliMi;
    private Long engelliTipiId;
    private String engelliTipiAdi;
    private Integer engelOrani;
    
    // Hastalık Bilgisi
    private boolean hastalikVarMi;
    private Long hastalikId;
    private String hastalikAdi;
    private boolean kronik;
}


