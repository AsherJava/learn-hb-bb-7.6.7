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

@DBTable(name="BDE_CEDXBALANCE", title="\u5dee\u989d\u62b5\u9500\u8868", primaryRequired=false, kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000002", code="table_group_bde", title="BDE"), indexs={@DBIndex(name="IDX_BDE_CEDXBALANCE_BIZKEY", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"BIZCOMBID", "SUBJECTCODE"})}, convertToBudModel=true, modelType=ModelType.OTHER, dataSource="jiuqi.gcreport.mdd.datasource", sourceTable="")
public class BdeMemoryCedxBalanceEO
extends DcDefaultTableEntity
implements ITableExtend {
    private static final long serialVersionUID = -2444822692608386900L;
    @DBColumn(nameInDB="BIZCOMBID", title="\u4e1a\u52a1\u7ec4\u5408\u6807\u8bc6", dbType=DBColumn.DBType.NVarchar, length=200, isRequired=true, order=2)
    private String bizCombId;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, length=60, defaultValue="'#'", order=3)
    private String subjectCode;
    @DBColumn(nameInDB="ORIENT", title="\u79d1\u76ee\u65b9\u5411", dbType=DBColumn.DBType.Int, length=2, isRequired=true, order=4)
    private Integer orient;
    @DBColumn(nameInDB="JF", title="\u501f\u65b9", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=5)
    private BigDecimal jf;
    @DBColumn(nameInDB="DF", title="\u8d37\u65b9", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true, defaultValue="0.00", order=6)
    private BigDecimal df;

    public String getTableName() {
        return "BDE_CEDXBALANCE";
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

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public BigDecimal getJf() {
        return this.jf;
    }

    public void setJf(BigDecimal jf) {
        this.jf = jf;
    }

    public BigDecimal getDf() {
        return this.df;
    }

    public void setDf(BigDecimal df) {
        this.df = df;
    }

    public List<DefinitionFieldV> getExtendFieldList(String param) {
        return DimensionManagerUtil.getExtendColumn((String)this.getTableName());
    }
}

