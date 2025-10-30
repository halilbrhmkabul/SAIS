package com.sais.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "borc_turu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorcTuru extends BaseEntity {

    @Id
    @SequenceGenerator(name = "borc_turu_seq", sequenceName = "borc_turu_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borc_turu_seq")
    private Long id;

    @Column(name = "kod", unique = true, length = 20)
    private String kod;

    @Column(name = "adi", nullable = false, length = 100)
    private String adi;

    @Column(name = "aciklama", length = 255)
    private String aciklama;

    @Column(name = "sira_no")
    private Integer siraNo;
}


