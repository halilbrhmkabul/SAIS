package com.sais.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tutanak_bilgisi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutanakBilgisi extends BaseEntity {

    @Id
    @SequenceGenerator(name = "tutanak_bilgisi_seq", sequenceName = "tutanak_bilgisi_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tutanak_bilgisi_seq")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "muracaat_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Muracaat muracaat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tahkikat_personel_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Personel tahkikatPersonel;

    @Column(name = "tahkikat_tarihi")
    private LocalDate tahkikatTarihi;

    @Column(name = "tahkikat_metni", columnDefinition = "CLOB")
    private String tahkikatMetni;

    @Column(name = "ev_gorselleri", columnDefinition = "CLOB")
    @Deprecated // Artık TutanakGorsel entity'si kullanılıyor (OneToMany)
    private String evGorselleri;

    @OneToMany(mappedBy = "tutanakBilgisi", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TutanakGorsel> gorseller = new ArrayList<>();

    @Column(name = "tamamlandi")
    @Builder.Default
    private Boolean tamamlandi = false;

    // Helper metodlar
    public void addGorsel(TutanakGorsel gorsel) {
        gorseller.add(gorsel);
        gorsel.setTutanakBilgisi(this);
    }

    public void removeGorsel(TutanakGorsel gorsel) {
        gorseller.remove(gorsel);
        gorsel.setTutanakBilgisi(null);
    }
}


