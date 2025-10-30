package com.sais.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "aile_fert_hastalik_bilgisi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AileFertHastalikBilgisi extends BaseEntity {

    @Id
    @SequenceGenerator(name = "aile_fert_hastalik_bilgisi_seq", sequenceName = "aile_fert_hastalik_bilgisi_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aile_fert_hastalik_bilgisi_seq")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aile_fert_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private AileFert aileFert;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hastalik_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Hastalik hastalik;

    @Column(name = "hastalik_adi", length = 150)
    private String hastalikAdi;

    @Column(name = "teshis_tarihi")
    private java.time.LocalDate teshisTarihi;

    @Column(name = "aciklama", columnDefinition = "CLOB")
    private String aciklama;
}


