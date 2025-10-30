package com.sais.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "engelli_tipi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EngelliTipi extends BaseEntity {

    @Id
    @SequenceGenerator(name = "engelli_tipi_seq", sequenceName = "engelli_tipi_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "engelli_tipi_seq")
    private Long id;

    @Column(name = "kod", unique = true, length = 20)
    private String kod;

    @Column(name = "adi", nullable = false, length = 100)
    private String adi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ust_tip_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private EngelliTipi ustTip;

    @Column(name = "aciklama", length = 255)
    private String aciklama;

    @Column(name = "sira_no")
    private Integer siraNo;

    @Transient
    public boolean isAltTip() {
        return ustTip != null;
    }
}


