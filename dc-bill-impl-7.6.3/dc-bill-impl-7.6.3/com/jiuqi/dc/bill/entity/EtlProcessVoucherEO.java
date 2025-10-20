/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.definition.DcDefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV
 *  com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.persistence.Column
 */
package com.jiuqi.dc.bill.entity;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import org.springframework.core.env.Environment;

@DBTable(name="ETL_PROCESS_VOUCHER", title="\u51ed\u8bc1\u8fc7\u7a0b\u8868", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"), indexs={@DBIndex(name="IDX_ETL_PROCESS_VCHR_ALL", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"UNITCODE", "ACCTYEAR", "ACCTPERIOD", "HANDLESTATE"}), @DBIndex(name="IDX_ETL_PROCESS_VCHR_UNITSTATE", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"UNITCODE", "VCHRSTATE"})})
public class EtlProcessVoucherEO
extends DcDefaultTableEntity
implements ITableExtend {
    private static final long serialVersionUID = 7564742841532584792L;
    @Column(name="VER")
    @DBColumn(title="\u884c\u7248\u672c", dbType=DBColumn.DBType.Long, isRecver=true, order=1)
    private Long ver;
    @DBColumn(nameInDB="VCHRID", title="\u51ed\u8bc1ID", dbType=DBColumn.DBType.NVarchar, length=36, order=2)
    private String vchrId;
    @DBColumn(nameInDB="BOOKCODE", title="\u8d26\u7c3f", dbType=DBColumn.DBType.NVarchar, length=60, order=3)
    private String bookCode;
    @DBColumn(nameInDB="UNITCODE", title="\u5355\u4f4d\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=4)
    private String unitCode;
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int, length=4, isRequired=true, order=5)
    private Integer acctYear;
    @DBColumn(nameInDB="ACCTPERIOD", title="\u4f1a\u8ba1\u671f\u95f4", dbType=DBColumn.DBType.NVarchar, length=10, isRequired=true, order=6)
    private String acctPeriod;
    @DBColumn(nameInDB="VCHRSTATE", title="\u51ed\u8bc1\u72b6\u6001", dbType=DBColumn.DBType.Int, length=1, isRequired=true, order=7)
    private String vchrState;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, length=6, isRequired=true, order=8)
    private String createTime;
    @DBColumn(nameInDB="HANDLESTATE", title="\u5904\u7406\u72b6\u6001", dbType=DBColumn.DBType.Int, length=1, order=9)
    private String handleState;
    @DBColumn(nameInDB="DELETESTATE", title="\u5220\u9664\u72b6\u6001", dbType=DBColumn.DBType.Int, length=1, order=10)
    private String deleteState;
    @DBColumn(nameInDB="VCHRNUM", title="\u51ed\u8bc1\u53f7", dbType=DBColumn.DBType.NVarchar, length=20, order=11)
    private String vchrNum;
    @DBColumn(nameInDB="VCHRTYPECODE", title="\u51ed\u8bc1\u7c7b\u522b", dbType=DBColumn.DBType.NVarchar, length=60, order=12)
    private String vchrTypeCode;
    @DBColumn(nameInDB="CREATEDATE", title="\u51ed\u8bc1\u65e5\u671f", dbType=DBColumn.DBType.Date, order=13)
    private Date createDate;

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getVchrId() {
        return this.vchrId;
    }

    public void setVchrId(String vchrId) {
        this.vchrId = vchrId;
    }

    public String getBookCode() {
        return this.bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public String getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(String acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getVchrState() {
        return this.vchrState;
    }

    public void setVchrState(String vchrState) {
        this.vchrState = vchrState;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHandleState() {
        return this.handleState;
    }

    public void setHandleState(String handleState) {
        this.handleState = handleState;
    }

    public String getDeleteState() {
        return this.deleteState;
    }

    public void setDeleteState(String deleteState) {
        this.deleteState = deleteState;
    }

    public String getVchrNum() {
        return this.vchrNum;
    }

    public void setVchrNum(String vchrNum) {
        this.vchrNum = vchrNum;
    }

    public String getVchrTypeCode() {
        return this.vchrTypeCode;
    }

    public void setVchrTypeCode(String vchrTypeCode) {
        this.vchrTypeCode = vchrTypeCode;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<DefinitionFieldV> getExtendFieldList(String param) {
        Environment environment = (Environment)ApplicationContextRegister.getBean(Environment.class);
        String extNum = environment.getProperty("jiuqi.dc.etl.extNum", "3");
        ArrayList fields = CollectionUtils.newArrayList();
        for (int i = 1; i <= Integer.parseInt(extNum); ++i) {
            DefinitionFieldV definitionFieldV = new DefinitionFieldV();
            definitionFieldV.setKey(UUIDUtils.newHalfGUIDStr());
            definitionFieldV.setCode("EXT_" + i);
            definitionFieldV.setFieldName("EXT_" + i);
            definitionFieldV.setEntityFieldName("EXT_" + i);
            definitionFieldV.setTitle("\u6269\u5c55\u5b57\u6bb5" + i);
            definitionFieldV.setNullable(true);
            definitionFieldV.setFieldValueType(DBColumn.DBType.NVarchar.getType());
            definitionFieldV.setDbType(DBColumn.DBType.NVarchar);
            definitionFieldV.setSize(60);
            definitionFieldV.setDescription("\u6269\u5c55\u5b57\u6bb5");
            definitionFieldV.setOrder((double)(13 + i));
            definitionFieldV.setDefaultValue(null);
            fields.add(definitionFieldV);
        }
        return fields;
    }
}

