package com.sais.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "borc_bilgisi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorcBilgisi extends BaseEntity {

    @Id
    @SequenceGenerator(name = "borc_bilgisi_seq", sequenceName = "borc_bilgisi_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borc_bilgisi_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aile_maddi_durum_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private AileMaddiDurum aileMaddiDurum;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "borc_turu_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BorcTuru borcTuru;

    @Column(name = "borc_tutari", nullable = false)
    private Double borcTutari;

    @Column(name = "aciklama", length = 255)
    private String aciklama;
}


