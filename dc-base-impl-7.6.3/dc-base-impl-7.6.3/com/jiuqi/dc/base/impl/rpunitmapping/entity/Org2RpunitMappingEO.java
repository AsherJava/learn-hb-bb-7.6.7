/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.persistence.Column
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 */
package com.jiuqi.dc.base.impl.rpunitmapping.entity;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.dc.base.impl.acctperiod.service.AcctPeriodService;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@DBTable(name="DC_ORG_RPUNITMAPPING", title="\u4e00\u672c\u8d26\u5355\u4f4d\u4e0e\u62a5\u8868\u5355\u4f4d\u6620\u5c04\u8868", kind=TableModelKind.SYSTEM_EXTEND, dataSource="jiuqi.gcreport.mdd.datasource", ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"))
public class Org2RpunitMappingEO
extends ShardingBaseEntity {
    private static final long serialVersionUID = -132305388486792315L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    @DBColumn(title="\u884c\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, isRecid=true, isRequired=true)
    private String id;
    @DBColumn(nameInDB="ORGCODE", title="\u62a5\u8868\u5355\u4f4d\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60)
    private String orgCode;
    @DBColumn(nameInDB="UNITCODE_1", title="\u7b2c\u4e00\u671f\u4e00\u672c\u8d26\u6620\u5c04", dbType=DBColumn.DBType.NVarchar, length=60)
    private String unitCode1;
    @DBColumn(nameInDB="UNITCODE_2", title="\u7b2c\u4e00\u671f\u4e00\u672c\u8d26\u6620\u5c04", dbType=DBColumn.DBType.NVarchar, length=60)
    private String unitCode2;
    @DBColumn(nameInDB="UNITCODE_3", title="\u7b2c\u4e00\u671f\u4e00\u672c\u8d26\u6620\u5c04", dbType=DBColumn.DBType.NVarchar, length=60)
    private String unitCode3;
    @DBColumn(nameInDB="UNITCODE_4", title="\u7b2c\u4e00\u671f\u4e00\u672c\u8d26\u6620\u5c04", dbType=DBColumn.DBType.NVarchar, length=60)
    private String unitCode4;
    @DBColumn(nameInDB="UNITCODE_5", title="\u7b2c\u4e00\u671f\u4e00\u672c\u8d26\u6620\u5c04", dbType=DBColumn.DBType.NVarchar, length=60)
    private String unitCode5;
    @DBColumn(nameInDB="UNITCODE_6", title="\u7b2c\u4e00\u671f\u4e00\u672c\u8d26\u6620\u5c04", dbType=DBColumn.DBType.NVarchar, length=60)
    private String unitCode6;
    @DBColumn(nameInDB="UNITCODE_7", title="\u7b2c\u4e00\u671f\u4e00\u672c\u8d26\u6620\u5c04", dbType=DBColumn.DBType.NVarchar, length=60)
    private String unitCode7;
    @DBColumn(nameInDB="UNITCODE_8", title="\u7b2c\u4e00\u671f\u4e00\u672c\u8d26\u6620\u5c04", dbType=DBColumn.DBType.NVarchar, length=60)
    private String unitCode8;
    @DBColumn(nameInDB="UNITCODE_9", title="\u7b2c\u4e00\u671f\u4e00\u672c\u8d26\u6620\u5c04", dbType=DBColumn.DBType.NVarchar, length=60)
    private String unitCode9;
    @DBColumn(nameInDB="UNITCODE_10", title="\u7b2c\u4e00\u671f\u4e00\u672c\u8d26\u6620\u5c04", dbType=DBColumn.DBType.NVarchar, length=60)
    private String unitCode10;
    @DBColumn(nameInDB="UNITCODE_11", title="\u7b2c\u4e00\u671f\u4e00\u672c\u8d26\u6620\u5c04", dbType=DBColumn.DBType.NVarchar, length=60)
    private String unitCode11;
    @DBColumn(nameInDB="UNITCODE_12", title="\u7b2c\u4e00\u671f\u4e00\u672c\u8d26\u6620\u5c04", dbType=DBColumn.DBType.NVarchar, length=60)
    private String unitCode12;

    public List<String> getShardingList() {
        List<Object> yearList;
        try {
            yearList = ((AcctPeriodService)ApplicationContextRegister.getBean(AcctPeriodService.class)).listYear().stream().map(String::valueOf).collect(Collectors.toList());
        }
        catch (Exception e) {
            yearList = CollectionUtils.newArrayList();
        }
        if (CollectionUtils.isEmpty((Collection)yearList)) {
            yearList = CollectionUtils.newArrayList();
            yearList.add(String.valueOf(DateUtils.getYearOfDate((Date)new Date())));
        }
        yearList.add(String.valueOf(DateUtils.getYearOfDate((Date)new Date()) + 1));
        return yearList;
    }

    public String getTableNamePrefix() {
        return "DC_ORG_RPUNITMAPPING";
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
}

