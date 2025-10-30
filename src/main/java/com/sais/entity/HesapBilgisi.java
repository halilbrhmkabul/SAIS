package com.sais.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "hesap_bilgisi", indexes = {
    @Index(name = "idx_hesap_kisi", columnList = "kisi_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HesapBilgisi extends BaseEntity {

    @Id
    @SequenceGenerator(name = "hesap_bilgisi_seq", sequenceName = "hesap_bilgisi_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hesap_bilgisi_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kisi_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Kisi kisi;

    @Column(name = "banka_adi", nullable = false, length = 100)
    private String bankaAdi;

    @Column(name = "sube_adi", length = 100)
    private String subeAdi;

    @Column(name = "iban", nullable = false, length = 32)
    private String iban;

    @Column(name = "hesap_sahibi_adi", nullable = false, length = 200)
    private String hesapSahibiAdi;

    @Column(name = "varsayilan", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    @Builder.Default
    private Boolean varsayilan = false;

    @Column(name = "aciklama", length = 255)
    private String aciklama;
}


