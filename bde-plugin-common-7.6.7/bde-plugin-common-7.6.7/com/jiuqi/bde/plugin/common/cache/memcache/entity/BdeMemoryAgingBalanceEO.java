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

@DBTable(name="BDE_ASSAGINGBALANCE", title="\u8d26\u9f84\u4f59\u989d\u8868", primaryRequired=false, kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000002", code="table_group_bde", title="BDE"), indexs={@DBIndex(name="IDX_BDE_AGBALANCE_BIZKEY", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"BIZCOMBID", "SUBJECTCODE", "CURRENCYCODE"})}, convertToBudModel=true, modelType=ModelType.OTHER, dataSource="jiuqi.gcreport.mdd.datasource", sourceTable="")
public class BdeMemoryAgingBalanceEO
extends DcDefaultTableEntity
implements ITableExtend {
    private static final long serialVersionUID = -8571985312392571877L;
    @DBColumn(nameInDB="BIZCOMBID", title="\u4e1a\u52a1\u7ec4\u5408\u6807\u8bc6", dbType=DBColumn.DBType.NVarchar, length=200, isRequired=true, order=2)
    private String bizCombId;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, length=60, defaultValue="'#'", order=3)
    private String subjectCode;
    @DBColumn(nameInDB="CURRENCYCODE", title="\u5e01\u522b", dbType=DBColumn.DBType.NVarchar, length=60, defaultValue="'#'", order=4)
    private String currencyCode;
    @DBColumn(nameInDB="ORIENT", title="\u79d1\u76ee\u65b9\u5411", dbType=DBColumn.DBType.Int, length=2, isRequired=true, order=5)
    private Integer orient;
    @DBColumn(nameInDB="HXYE", title="\u672c\u5e01\u6838\u9500\u4f59\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=6)
    private BigDecimal hxye;
    @DBColumn(nameInDB="HXNC", title="\u672c\u5e01\u6838\u9500\u5e74\u521d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=7)
    private BigDecimal hxnc;
    @DBColumn(nameInDB="WHXYE", title="\u539f\u5e01\u6838\u9500\u4f59\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=8)
    private BigDecimal whxye;
    @DBColumn(nameInDB="WHXNC", title="\u539f\u5e01\u6838\u9500\u5e74\u521d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=9)
    private BigDecimal whxnc;

    public String getTableName() {
        return "BDE_ASSAGINGBALANCE";
    }

    public String getBizCombId() {
        return this.bizCombId;
    }

    public void setBizCombId(String bizCombId) {
        this.bizCombId = bizCombId;
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

    public BigDecimal getHxye() {
        return this.hxye;
    }

    public void setHxye(BigDecimal hxye) {
        this.hxye = hxye;
    }

    public BigDecimal getHxnc() {
        return this.hxnc;
    }

    public void setHxnc(BigDecimal hxnc) {
        this.hxnc = hxnc;
    }

    public BigDecimal getWhxye() {
        return this.whxye;
    }

    public void setWhxye(BigDecimal whxye) {
        this.whxye = whxye;
    }

    public BigDecimal getWhxnc() {
        return this.whxnc;
    }

    public void setWhxnc(BigDecimal whxnc) {
        this.whxnc = whxnc;
    }

    public List<DefinitionFieldV> getExtendFieldList(String param) {
        return DimensionManagerUtil.getExtendColumn((String)this.getTableName());
    }
}

