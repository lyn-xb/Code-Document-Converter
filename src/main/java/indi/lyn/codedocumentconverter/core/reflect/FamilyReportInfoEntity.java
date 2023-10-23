package indi.lyn.codedocumentconverter.core.reflect;

import javax.persistence.*;

@Table(name = "tbl_family_report_info")
@Entity
public class FamilyReportInfoEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "card_num")
    private String cardNum;
}

