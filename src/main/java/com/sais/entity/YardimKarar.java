package com.sais.entity;

import com.sais.enums.YardimDurum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "yardim_karar", indexes = {
    @Index(name = "idx_yardim_karar_muracaat", columnList = "muracaat_id"),
    @Index(name = "idx_yardim_karar_durum", columnList = "yardim_durum")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YardimKarar extends BaseEntity {

    @Id
    @SequenceGenerator(name = "yardim_karar_seq", sequenceName = "yardim_karar_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "yardim_karar_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "muracaat_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Muracaat muracaat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "yardim_alt_tipi_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private YardimAltTipi yardimAltTipi;

    @Column(name = "talep_edilmis_mi")
    @Builder.Default
    private Boolean talepEdilmisMi = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "yardim_durum", nullable = false)
    private YardimDurum yardimDurum;

    @Column(name = "komisyon_kararli")
    @Builder.Default
    private Boolean komisyonKararli = true;

    @Column(name = "toplanti_tarihi")
    private LocalDate toplantiTarihi;

    // Nakdi Yardım Alanları
    @Column(name = "verilen_tutar")
    private Double verilenTutar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yardim_dilimi_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private YardimDilimi yardimDilimi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "yardim_donemi_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private YardimDonemi yardimDonemi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hesap_bilgisi_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private HesapBilgisi hesapBilgisi;

    // Ayni Yardım Alanları
    @Column(name = "adet_sayi")
    private Integer adetSayi;

    // Red Bilgileri
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "red_sebebi_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private YardimRedSebebi redSebebi;

    @Column(name = "aciklama", columnDefinition = "CLOB")
    private String aciklama;

    @Column(name = "kesinlesti")
    @Builder.Default
    private Boolean kesinlesti = false;

    @Column(name = "kesinlesme_tarihi")
    private LocalDate kesinlesmeTarihi;
    
    @Column(name = "karar_sayisi")
    private Integer kararSayisi; 
    
    @Column(name = "karar_tarihi")
    private LocalDate kararTarihi; 
    
   
    public String getKararNumarasi() {
        if (kararTarihi != null && kararSayisi != null) {
            return kararTarihi.getYear() + "/" + kararSayisi;
        }
        return "Belirtilmemiş";
    }
}


