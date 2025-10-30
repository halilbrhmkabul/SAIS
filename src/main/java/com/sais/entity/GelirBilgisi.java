package com.sais.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "gelir_bilgisi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GelirBilgisi extends BaseEntity {

    @Id
    @SequenceGenerator(name = "gelir_bilgisi_seq", sequenceName = "gelir_bilgisi_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gelir_bilgisi_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aile_maddi_durum_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private AileMaddiDurum aileMaddiDurum;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kisi_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Kisi kisi;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gelir_turu_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private GelirTuru gelirTuru;

    @Column(name = "gelir_tutari", nullable = false)
    private Double gelirTutari;

    @Column(name = "aciklama", length = 255)
    private String aciklama;
}


