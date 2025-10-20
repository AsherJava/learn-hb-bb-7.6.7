/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.hypermodel.domain.enums.ModelType
 *  com.jiuqi.dc.base.common.definition.DcDefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV
 *  com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend
 *  com.jiuqi.gcreport.dimension.internal.utils.DimensionManagerUtil
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 */
package com.jiuqi.bde.plugin.common.cache.memcache.entity;

import com.jiuqi.budget.param.hypermodel.domain.enums.ModelType;
import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend;
import com.jiuqi.gcreport.dimension.internal.utils.DimensionManagerUtil;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import java.math.BigDecimal;
import java.util.List;

@DBTable(name="BDE_XJLLBALANCE", title="\u73b0\u6d41\u4f59\u989d\u8868", primaryRequired=false, kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000002", code="table_group_bde", title="BDE"), indexs={@DBIndex(name="IDX_BDE_XJLLBALANCE_BIZKEY", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"BIZCOMBID", "CFITEMCODE", "SUBJECTCODE", "CURRENCYCODE"})}, convertToBudModel=true, modelType=ModelType.OTHER, dataSource="jiuqi.gcreport.mdd.datasource", sourceTable="")
public class BdeMemoryXjllBalanceEO
extends DcDefaultTableEntity
implements ITableExtend {
    private static final long serialVersionUID = 1674398098667548165L;
    @DBColumn(nameInDB="BIZCOMBID", title="\u4e1a\u52a1\u7ec4\u5408\u6807\u8bc6", dbType=DBColumn.DBType.NVarchar, length=200, isRequired=true, order=2)
    private String bizCombId;
    @DBColumn(nameInDB="CFITEMCODE", title="\u73b0\u91d1\u6d41\u91cf\u9879\u76ee", dbType=DBColumn.DBType.NVarchar, length=60, defaultValue="'#'", order=3)
    private String cfItemCode;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, length=60, defaultValue="'#'", order=4)
    private String subjectCode;
    @DBColumn(nameInDB="CURRENCYCODE", title="\u5e01\u522b", dbType=DBColumn.DBType.NVarchar, length=60, defaultValue="'#'", order=5)
    private String currencyCode;
    @DBColumn(nameInDB="ORIENT", title="\u79d1\u76ee\u65b9\u5411", dbType=DBColumn.DBType.Int, length=2, isRequired=true, order=6)
    private Integer orient;
    @DBColumn(nameInDB="BQNUM", title="\u672c\u671f\u53d1\u751f\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=7)
    private BigDecimal bqnum;
    @DBColumn(nameInDB="LJNUM", title="\u7d2f\u8ba1\u53d1\u751f\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=8)
    private BigDecimal ljnum;
    @DBColumn(nameInDB="WBQNUM", title="\u5916\u5e01\u672c\u671f\u53d1\u751f\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=9)
    private BigDecimal wbqnum;
    @DBColumn(nameInDB="WLJNUM", title="\u5916\u5e01\u7d2f\u8ba1\u53d1\u751f\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=10)
    private BigDecimal WHXNC;

    public String getTableName() {
        return "BDE_XJLLBALANCE";
    }

    public String getBizCombId() {
        return this.bizCombId;
    }

    public void setBizCombId(String bizCombId) {
        this.bizCombId = bizCombId;
    }

    public String getCfItemCode() {
        return this.cfItemCode;
    }

    public void setCfItemCode(String cfItemCode) {
        this.cfItemCode = cfItemCode;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public BigDecimal getBqnum() {
        return this.bqnum;
    }

    public void setBqnum(BigDecimal bqnum) {
        this.bqnum = bqnum;
    }

    public BigDecimal getLjnum() {
        return this.ljnum;
    }

    public void setLjnum(BigDecimal ljnum) {
        this.ljnum = ljnum;
    }

    public BigDecimal getWbqnum() {
        return this.wbqnum;
    }

    public void setWbqnum(BigDecimal wbqnum) {
        this.wbqnum = wbqnum;
    }

    public BigDecimal getWHXNC() {
        return this.WHXNC;
    }

    public void setWHXNC(BigDecimal WHXNC) {
        this.WHXNC = WHXNC;
    }

    public List<DefinitionFieldV> getExtendFieldList(String param) {
        return DimensionManagerUtil.getExtendColumn((String)this.getTableName());
    }
}

