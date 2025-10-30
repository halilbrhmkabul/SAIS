package com.sais.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "yardim_red_sebebi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YardimRedSebebi extends BaseEntity {

    @Id
    @SequenceGenerator(name = "yardim_red_sebebi_seq", sequenceName = "yardim_red_sebebi_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "yardim_red_sebebi_seq")
    private Long id;

    @Column(name = "kod", unique = true, length = 20)
    private String kod;

    @Column(name = "adi", nullable = false, length = 150)
    private String adi;

    @Column(name = "aciklama", length = 500)
    private String aciklama;

    @Column(name = "sira_no")
    private Integer siraNo;
}


