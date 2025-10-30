package com.sais.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "yardim_donemi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YardimDonemi extends BaseEntity {

    @Id
    @SequenceGenerator(name = "yardim_donemi_seq", sequenceName = "yardim_donemi_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "yardim_donemi_seq")
    private Long id;

    @Column(name = "ay_sayisi", nullable = false, unique = true)
    private Integer aySayisi;

    @Column(name = "adi", nullable = false, length = 50)
    private String adi;

    @Column(name = "aciklama", length = 255)
    private String aciklama;
}


