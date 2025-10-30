package com.sais.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "aile_maddi_durum")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AileMaddiDurum extends BaseEntity {

    @Id
    @SequenceGenerator(name = "aile_maddi_durum_seq", sequenceName = "aile_maddi_durum_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aile_maddi_durum_seq")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "muracaat_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Muracaat muracaat;

    @Column(name = "toplam_gelir")
    private Double toplamGelir;

    @Column(name = "toplam_borc")
    private Double toplamBorc;

    @Column(name = "aciklama", columnDefinition = "CLOB")
    private String aciklama;

    @OneToMany(mappedBy = "aileMaddiDurum", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<GelirBilgisi> gelirBilgileri = new ArrayList<>();

    @OneToMany(mappedBy = "aileMaddiDurum", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BorcBilgisi> borcBilgileri = new ArrayList<>();

    @OneToOne(mappedBy = "aileMaddiDurum", cascade = CascadeType.ALL, orphanRemoval = true)
    private GayrimenkulBilgisi gayrimenkulBilgisi;

    // Helper metodlar
    public void addGelirBilgisi(GelirBilgisi gelir) {
        gelirBilgileri.add(gelir);
        gelir.setAileMaddiDurum(this);
    }

    public void removeGelirBilgisi(GelirBilgisi gelir) {
        gelirBilgileri.remove(gelir);
        gelir.setAileMaddiDurum(null);
    }

    public void addBorcBilgisi(BorcBilgisi borc) {
        borcBilgileri.add(borc);
        borc.setAileMaddiDurum(this);
    }

    public void removeBorcBilgisi(BorcBilgisi borc) {
        borcBilgileri.remove(borc);
        borc.setAileMaddiDurum(null);
    }
}


