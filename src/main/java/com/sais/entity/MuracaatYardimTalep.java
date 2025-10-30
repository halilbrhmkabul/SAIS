package com.sais.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "muracaat_yardim_talep")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MuracaatYardimTalep extends BaseEntity {

    @Id
    @SequenceGenerator(name = "muracaat_yardim_talep_seq", sequenceName = "muracaat_yardim_talep_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "muracaat_yardim_talep_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "muracaat_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Muracaat muracaat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "yardim_alt_tipi_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private YardimAltTipi yardimAltTipi;

    @Column(name = "talep_metni", columnDefinition = "CLOB")
    private String talepMetni;

    @Column(name = "aciklama", length = 500)
    private String aciklama;

    @Column(name = "sira_no")
    private Integer siraNo;
}


