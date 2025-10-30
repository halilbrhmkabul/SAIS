package com.sais.entity;

import com.sais.enums.OgrenimDurum;
import com.sais.enums.SGKDurum;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "aile_fert", indexes = {
    @Index(name = "idx_aile_fert_muracaat", columnList = "muracaat_id"),
    @Index(name = "idx_aile_fert_kisi", columnList = "kisi_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AileFert extends BaseEntity {

    @Id
    @SequenceGenerator(name = "aile_fert_seq", sequenceName = "aile_fert_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aile_fert_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "muracaat_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Muracaat muracaat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kisi_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Kisi kisi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yakinlik_kodu_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private YakinlikKodu yakinlikKodu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ozel_statu_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OzelStatu ozelStatu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meslek_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Meslek meslek;

    @Enumerated(EnumType.STRING)
    @Column(name = "sgk_durum", length = 50)
    private SGKDurum sgkDurum;

    @Enumerated(EnumType.STRING)
    @Column(name = "ogrenim_durumu", length = 50)
    private OgrenimDurum ogrenimDurumu;

    @Column(name = "aciklama", columnDefinition = "CLOB")
    private String aciklama;

    @OneToOne(mappedBy = "aileFert", cascade = CascadeType.ALL, orphanRemoval = true)
    private AileFertEngelBilgisi engelBilgisi;

    @OneToOne(mappedBy = "aileFert", cascade = CascadeType.ALL, orphanRemoval = true)
    private AileFertHastalikBilgisi hastalikBilgisi;
}


