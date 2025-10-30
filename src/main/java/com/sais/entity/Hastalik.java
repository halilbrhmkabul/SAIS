package com.sais.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "hastalik")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hastalik extends BaseEntity {

    @Id
    @SequenceGenerator(name = "hastalik_seq", sequenceName = "hastalik_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hastalik_seq")
    private Long id;

    @Column(name = "kod", unique = true, length = 20)
    private String kod;

    @Column(name = "adi", nullable = false, length = 150)
    private String adi;

    @Column(name = "kronik")
    @Builder.Default
    private Boolean kronik = false;

    @Column(name = "aciklama", length = 255)
    private String aciklama;
}


