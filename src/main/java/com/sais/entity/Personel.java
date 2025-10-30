package com.sais.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "personel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Personel extends BaseEntity {

    @Id
    @SequenceGenerator(name = "personel_seq", sequenceName = "personel_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personel_seq")
    private Long id;

    @Column(name = "tc_kimlik_no", nullable = false, unique = true, length = 11)
    private String tcKimlikNo;

    @Column(name = "ad", nullable = false, length = 100)
    private String ad;

    @Column(name = "soyad", nullable = false, length = 100)
    private String soyad;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "telefon", length = 20)
    private String telefon;

    @Column(name = "unvan", length = 100)
    private String unvan;

    @Column(name = "departman", length = 100)
    private String departman;

    @Column(name = "tahkikat_yetkili", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    @Builder.Default
    private Boolean tahkikatYetkili = false;

    @Column(name = "komisyon_uyesi", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    @Builder.Default
    private Boolean komisyonUyesi = false;

    @Column(name = "aciklama", length = 500)
    private String aciklama;

    @Transient
    public String getAdSoyad() {
        return ad + " " + soyad;
    }
}


