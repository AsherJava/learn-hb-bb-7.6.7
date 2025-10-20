/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV
 *  com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.persistence.Column
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 */
package com.jiuqi.dc.mappingscheme.impl.entity;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.mappingscheme.impl.enums.SchemeBaseDataRefType;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@DBTable(name="REF", title="\u6570\u636e\u6620\u5c04\u56fa\u5316\u8868", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"), indexs={@DBIndex(name="I_REF_MD_DS", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"DATASCHEMECODE"}), @DBIndex(name="I_REF_MD_C", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"CODE"})}, dataSource="jiuqi.gcreport.mdd.datasource")
public class RefDimMdTableEO
extends ShardingBaseEntity
implements ITableExtend {
    private static final long serialVersionUID = -3782665626761321292L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    @DBColumn(title="\u884c\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, isRecid=true, isRequired=true, order=1)
    private String id;
    @DBColumn(nameInDB="DATASCHEMECODE", title="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=1)
    private String dataSchemeCode;
    @DBColumn(nameInDB="ODS_CODE", title="ODS_CODE", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=2)
    private String odsCode;
    @DBColumn(nameInDB="ODS_NAME", title="ODS_NAME", dbType=DBColumn.DBType.NVarchar, length=100, isRequired=true, order=3)
    private String odsName;
    @DBColumn(nameInDB="CODE", title="\u4e00\u672c\u8d26\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60, order=4)
    private String code;
    @DBColumn(nameInDB="AUTOMATCHFLAG", title="\u81ea\u52a8\u5339\u914d\u6807\u8bc6", dbType=DBColumn.DBType.Int, length=1, order=7)
    private Integer autoMatchFlag;
    @DBColumn(nameInDB="REMARK", title="\u5907\u6ce8", dbType=DBColumn.DBType.NVarchar, length=60, order=8)
    private String remark;
    @DBColumn(nameInDB="ODS_UNITCODE", title="ODS_UNITCODE", dbType=DBColumn.DBType.NVarchar, length=60, order=9)
    private String odsUnitCode;
    @DBColumn(nameInDB="ODS_ACCTYEAR", title="ODS_ACCTYEAR", dbType=DBColumn.DBType.NVarchar, length=60, order=10)
    private String odsAcctYear;
    @DBColumn(nameInDB="ODS_BOOKCODE", title="ODS_BOOKCODE", dbType=DBColumn.DBType.NVarchar, length=60, order=11)
    private String odsBookCode;
    @DBColumn(nameInDB="DC_UNITCODE", title="\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60, order=13)
    private String dcUnitCode;
    @DBColumn(nameInDB="HANDLESTATUS", title="\u5904\u7406\u72b6\u6001", dbType=DBColumn.DBType.Int, length=1, order=14)
    private Integer handleStatus;
    @DBColumn(nameInDB="OPERATOR", title="\u64cd\u4f5c\u4eba", dbType=DBColumn.DBType.NVarchar, length=60, order=15)
    private String operator;
    @DBColumn(nameInDB="OPERATETIME", title="\u64cd\u4f5c\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, order=16)
    private String operateTime;

    public List<String> getShardingList() {
        List<String> mainDim = SchemeBaseDataRefType.getSchemeBaseDataRefList();
        mainDim.remove("MD_CURRENCY");
        List dimensionVOS = ((DimensionService)ApplicationContextRegister.getBean(DimensionService.class)).loadAllDimensions();
        for (DimensionVO dimension : dimensionVOS) {
            if (!"dims".equals(dimension.getDimensionType())) continue;
            mainDim.add(dimension.getCode());
        }
        return mainDim;
    }

    public String getTableNamePrefix() {
        return "REF";
    }

    public String getId() {
        return null;
    }

    public void setId(String id) {
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getOdsCode() {
        return this.odsCode;
    }

    public void setOdsCode(String odsCode) {
        this.odsCode = odsCode;
    }

    public String getOdsName() {
        return this.odsName;
    }

    public void setOdsName(String odsName) {
        this.odsName = odsName;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAutoMatchFlag() {
        return this.autoMatchFlag;
    }

    public void setAutoMatchFlag(Integer autoMatchFlag) {
        this.autoMatchFlag = autoMatchFlag;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOdsUnitCode() {
        return this.odsUnitCode;
    }

    public void setOdsUnitCode(String odsUnitCode) {
        this.odsUnitCode = odsUnitCode;
    }

    public String getOdsAcctYear() {
        return this.odsAcctYear;
    }

    public void setOdsAcctYear(String odsAcctYear) {
        this.odsAcctYear = odsAcctYear;
    }

    public String getOdsBookCode() {
        return this.odsBookCode;
    }

    public void setOdsBookCode(String odsBookCode) {
        this.odsBookCode = odsBookCode;
    }

    public String getDcUnitCode() {
        return this.dcUnitCode;
    }

    public void setDcUnitCode(String dcUnitCode) {
        this.dcUnitCode = dcUnitCode;
    }

    public Integer getHandleStatus() {
        return this.handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperateTime() {
        return this.operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public List<DefinitionFieldV> getExtendFieldList(String param) {
        if (!"REF_MD_ORG".equals(param)) {
            return new ArrayList<DefinitionFieldV>();
        }
        ArrayList<DefinitionFieldV> fields = new ArrayList<DefinitionFieldV>();
        DefinitionFieldV assistCode = new DefinitionFieldV();
        assistCode.setKey(UUIDUtils.newUUIDStr());
        assistCode.setNullable(true);
        assistCode.setCode("ODS_ASSISTCODE");
        assistCode.setTitle("ODS_ASSISTCODE");
        assistCode.setFieldValueType(DBColumn.DBType.NVarchar.getType());
        assistCode.setDbType(DBColumn.DBType.NVarchar);
        assistCode.setSize(60);
        DefinitionFieldV assistName = new DefinitionFieldV();
        assistName.setKey(UUIDUtils.newUUIDStr());
        assistName.setNullable(true);
        assistName.setCode("ODS_ASSISTNAME");
        assistName.setTitle("ODS_ASSISTNAME");
        assistName.setFieldValueType(DBColumn.DBType.NVarchar.getType());
        assistName.setDbType(DBColumn.DBType.NVarchar);
        assistName.setSize(60);
        DefinitionFieldV customCode = new DefinitionFieldV();
        customCode.setKey(UUIDUtils.newUUIDStr());
        customCode.setNullable(true);
        customCode.setCode("ODS_CUSTOM_CODE");
        customCode.setTitle("ODS_CUSTOM_CODE");
        customCode.setFieldValueType(DBColumn.DBType.NVarchar.getType());
        customCode.setDbType(DBColumn.DBType.NVarchar);
        customCode.setSize(500);
        fields.add(assistCode);
        fields.add(assistName);
        fields.add(customCode);
        return fields;
    }
}

