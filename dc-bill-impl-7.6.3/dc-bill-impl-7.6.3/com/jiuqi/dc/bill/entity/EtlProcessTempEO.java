/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.bill.entity;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.core.env.Environment;

@DBTable(name="ETL_TEMP_VOUCHER", title="\u51ed\u8bc1\u4e34\u65f6\u8868", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"), bizkeyfields={"VCHRID"}, indexs={@DBIndex(name="IDX_ETL_TEMP_VCHR", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"VCHRID"}), @DBIndex(name="IDX_ETL_TEMP_UNIT", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"UNITCODE"})})
public class EtlProcessTempEO
extends BaseEntity
implements ITableExtend {
    private static final long serialVersionUID = -1465492368043019558L;
    @DBColumn(nameInDB="VCHRID", title="\u51ed\u8bc1ID", dbType=DBColumn.DBType.NVarchar, length=36, isRecid=true, isRequired=true, order=1)
    private String vchrId;
    @DBColumn(nameInDB="UNITCODE", title="\u5355\u4f4d\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=2)
    private String unitCode;
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int, length=4, isRequired=true, order=3)
    private Integer acctYear;
    @DBColumn(nameInDB="ACCTPERIOD", title="\u4f1a\u8ba1\u671f\u95f4", dbType=DBColumn.DBType.NVarchar, length=10, isRequired=true, order=4)
    private String acctPeriod;
    @DBColumn(nameInDB="VCHRNUM", title="\u51ed\u8bc1\u53f7", dbType=DBColumn.DBType.NVarchar, length=20, order=5)
    private String vchrNum;
    @DBColumn(nameInDB="VCHRTYPECODE", title="\u51ed\u8bc1\u7c7b\u522b", dbType=DBColumn.DBType.NVarchar, length=60, order=6)
    private String vchrTypeCode;
    @DBColumn(nameInDB="CREATEDATE", title="\u51ed\u8bc1\u65e5\u671f", dbType=DBColumn.DBType.Date, order=7)
    private Date createDate;

    public String getId() {
        return this.vchrId;
    }

    public void setId(String id) {
        this.vchrId = id;
    }

    public String getTableName() {
        return "ETL_TEMP_VOUCHER";
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getVchrId() {
        return this.vchrId;
    }

    public void setVchrId(String vchrId) {
        this.vchrId = vchrId;
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
            definitionFieldV.setOrder((double)(7 + i));
            definitionFieldV.setDefaultValue(null);
            fields.add(definitionFieldV);
        }
        return fields;
    }
}

