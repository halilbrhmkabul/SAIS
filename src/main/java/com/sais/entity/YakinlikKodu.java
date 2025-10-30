package com.sais.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "yakinlik_kodu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YakinlikKodu extends BaseEntity {

    @Id
    @SequenceGenerator(name = "yakinlik_kodu_seq", sequenceName = "yakinlik_kodu_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "yakinlik_kodu_seq")
    private Long id;

    @Column(name = "kod", nullable = false, unique = true, length = 10)
    private String kod;

    @Column(name = "adi", nullable = false, length = 100)
    private String adi;

    @Column(name = "aciklama", length = 255)
    private String aciklama;

    @Column(name = "sira_no")
    private Integer siraNo;
}


