package com.sais.entity;

import com.sais.enums.EvMulkiyet;
import com.sais.enums.EvTipi;
import com.sais.enums.EvYakacakTipi;
import com.sais.enums.GayrimenkulTuru;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "gayrimenkul_bilgisi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GayrimenkulBilgisi extends BaseEntity {

    @Id
    @SequenceGenerator(name = "gayrimenkul_bilgisi_seq", sequenceName = "gayrimenkul_bilgisi_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gayrimenkul_bilgisi_seq")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aile_maddi_durum_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private AileMaddiDurum aileMaddiDurum;

    // Ev Bilgileri
    @Builder.Default
    @Column(name = "evi_var", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    private Boolean eviVar = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "ev_tipi")
    private EvTipi evTipi;

    @Enumerated(EnumType.STRING)
    @Column(name = "ev_yakacak_tipi")
    private EvYakacakTipi evYakacakTipi;

    @Enumerated(EnumType.STRING)
    @Column(name = "ev_mulkiyet")
    private EvMulkiyet evMulkiyet;

    @Column(name = "kira_tutari")
    private Double kiraTutari;

    // Kirada Ev Bilgileri
    @Builder.Default
    @Column(name = "kirada_evi_var", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    private Boolean kiradaEviVar = false;

    @Column(name = "kirada_ev_sayisi")
    private Integer kiradaEvSayisi;

    @Column(name = "toplam_kira_geliri")
    private Double toplamKiraGeliri;

    // Araba Bilgileri
    @Builder.Default
    @Column(name = "araba_var", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    private Boolean arabaVar = false;

    @Column(name = "araba_modeli", length = 150)
    private String arabaModeli;

    // Gayrimenkul Bilgileri
    @Builder.Default
    @Column(name = "gayrimenkul_var", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    private Boolean gayrimenkulVar = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "gayrimenkul_turu")
    private GayrimenkulTuru gayrimenkulTuru;

    @Column(name = "gayrimenkul_aciklama", length = 500)
    private String gayrimenkulAciklama;
    
    /**
     * Getter metodları - null değerleri false'a çevir
     */
    public Boolean getEviVar() {
        return eviVar != null ? eviVar : false;
    }
    
    public Boolean getKiradaEviVar() {
        return kiradaEviVar != null ? kiradaEviVar : false;
    }
    
    public Boolean getArabaVar() {
        return arabaVar != null ? arabaVar : false;
    }
    
    public Boolean getGayrimenkulVar() {
        return gayrimenkulVar != null ? gayrimenkulVar : false;
    }
}


