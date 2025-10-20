/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Id
 *  javax.persistence.Transient
 *  tk.mybatis.mapper.entity.IDynamicTableName
 */
package com.jiuqi.dc.base.impl.rpunitmapping.entity;

import com.jiuqi.va.mapper.domain.TenantDO;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import tk.mybatis.mapper.entity.IDynamicTableName;

public class Org2RpunitMappingEntity
extends TenantDO
implements IDynamicTableName {
    private static final long serialVersionUID = -8485665096473368395L;
    public static final String TABLENAME = "DC_ORG_RPUNITMAPPING";
    @Transient
    private Integer acctYear;
    @Id
    @Column(name="ID")
    private String id;
    @Column(name="ORGCODE")
    private String orgCode;
    @Column(name="UNITCODE_1")
    private String unitCode1;
    @Column(name="UNITCODE_2")
    private String unitCode2;
    @Column(name="UNITCODE_3")
    private String unitCode3;
    @Column(name="UNITCODE_4")
    private String unitCode4;
    @Column(name="UNITCODE_5")
    private String unitCode5;
    @Column(name="UNITCODE_6")
    private String unitCode6;
    @Column(name="UNITCODE_7")
    private String unitCode7;
    @Column(name="UNITCODE_8")
    private String unitCode8;
    @Column(name="UNITCODE_9")
    private String unitCode9;
    @Column(name="UNITCODE_10")
    private String unitCode10;
    @Column(name="UNITCODE_11")
    private String unitCode11;
    @Column(name="UNITCODE_12")
    private String unitCode12;

    public Org2RpunitMappingEntity() {
    }

    public Org2RpunitMappingEntity(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public String getUnitCode1() {
        return this.unitCode1;
    }

    public void setUnitCode1(String unitCode1) {
        this.unitCode1 = unitCode1;
    }

    public String getUnitCode2() {
        return this.unitCode2;
    }

    public void setUnitCode2(String unitCode2) {
        this.unitCode2 = unitCode2;
    }

    public String getUnitCode3() {
        return this.unitCode3;
    }

    public void setUnitCode3(String unitCode3) {
        this.unitCode3 = unitCode3;
    }

    public String getUnitCode4() {
        return this.unitCode4;
    }

    public void setUnitCode4(String unitCode4) {
        this.unitCode4 = unitCode4;
    }

    public String getUnitCode5() {
        return this.unitCode5;
    }

    public void setUnitCode5(String unitCode5) {
        this.unitCode5 = unitCode5;
    }

    public String getUnitCode6() {
        return this.unitCode6;
    }

    public void setUnitCode6(String unitCode6) {
        this.unitCode6 = unitCode6;
    }

    public String getUnitCode7() {
        return this.unitCode7;
    }

    public void setUnitCode7(String unitCode7) {
        this.unitCode7 = unitCode7;
    }

    public String getUnitCode8() {
        return this.unitCode8;
    }

    public void setUnitCode8(String unitCode8) {
        this.unitCode8 = unitCode8;
    }

    public String getUnitCode9() {
        return this.unitCode9;
    }

    public void setUnitCode9(String unitCode9) {
        this.unitCode9 = unitCode9;
    }

    public String getUnitCode10() {
        return this.unitCode10;
    }

    public void setUnitCode10(String unitCode10) {
        this.unitCode10 = unitCode10;
    }

    public String getUnitCode11() {
        return this.unitCode11;
    }

    public void setUnitCode11(String unitCode11) {
        this.unitCode11 = unitCode11;
    }

    public String getUnitCode12() {
        return this.unitCode12;
    }

    public void setUnitCode12(String unitCode12) {
        this.unitCode12 = unitCode12;
    }

    public String getDynamicTableName() {
        return "DC_ORG_RPUNITMAPPING_" + this.acctYear;
    }
}

