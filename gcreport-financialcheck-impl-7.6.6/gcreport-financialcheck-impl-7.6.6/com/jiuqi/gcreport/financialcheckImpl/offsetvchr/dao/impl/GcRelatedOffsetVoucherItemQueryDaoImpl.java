/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition
 *  com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.financialcheckImpl.offsetvchr.dao.impl;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckImpl.offsetvchr.dao.GcRelatedOffsetVoucherItemQueryDao;
import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class GcRelatedOffsetVoucherItemQueryDaoImpl
extends AbstractEntDbSqlGenericDAO<GcRelatedOffsetVoucherItemEO>
implements GcRelatedOffsetVoucherItemQueryDao {
    @Autowired
    private DimensionService dimensionService;

    public GcRelatedOffsetVoucherItemQueryDaoImpl() {
        super(GcRelatedOffsetVoucherItemEO.class, (FEntSqlTemplate)new FcNativeSqlTemplate());
        this.setTableName("GC_RELATEDOFFSETVCHRITEM");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public List<GcRelatedOffsetVoucherItemEO> queryByOffsetCondition(BalanceCondition queryCondition) {
        List vchrBalanceEOS;
        String orgType = queryCondition.getOrgType();
        int month = PeriodUtils.getMonth((int)queryCondition.getAcctYear(), (int)queryCondition.getPeriodType(), (int)queryCondition.getAcctPeriod());
        String sqlTemplate = "  select %2$s \n    from GC_RELATEDOFFSETVCHRITEM  vb \n    join " + orgType + "  unit on vb.unitId = unit.code and unit.validTime <= ? and unit.invalidTime >= ? \n    join " + orgType + "  oppunit on vb.oppUnitId = oppunit.code and oppunit.validTime <= ? and oppunit.invalidTime >= ? \n   where unit.gcparents like ?  \n     and oppunit.gcparents like ? \n     and substr(unit.gcparents, %1$s, %3$s) <> substr(oppunit.gcparents, %1$s, %3$s) \n     and vb.acctYear = ? and vb.OFFSETPERIOD <= ? and OFFSETMETHOD != 'NOOFFSET'\n";
        YearPeriodObject yp = new YearPeriodObject(null, queryCondition.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO gcOrg = tool.getOrgByCode(queryCondition.getMergeOrg());
        String mergeOrgParents = gcOrg.getGcParentStr();
        int mergeOrgChildBeginIndex = gcOrg.getGcParentStr().length() + 2;
        String sql = String.format(sqlTemplate, mergeOrgChildBeginIndex, SqlUtils.getColumnsSqlByTableDefine((String)"GC_RELATEDOFFSETVCHRITEM", (String)"vb"), tool.getOrgCodeLength());
        String tempGroupId = "";
        Set boundSubjects = queryCondition.getBoundSubjects();
        Date versionDate = yp.formatYP().getEndDate();
        try {
            if (!CollectionUtils.isEmpty(boundSubjects)) {
                TempTableCondition conditionOfMulStr = ReltxSqlUtils.getConditionOfMulStr((Collection)boundSubjects, (String)"vb.OFFSETSUBJECT");
                sql = sql + "and " + conditionOfMulStr.getCondition();
                tempGroupId = conditionOfMulStr.getTempGroupId();
            }
            vchrBalanceEOS = this.selectEntity(sql, new Object[]{versionDate, versionDate, versionDate, versionDate, mergeOrgParents + "%", mergeOrgParents + "%", queryCondition.getAcctYear(), month});
        }
        finally {
            ReltxSqlUtils.deteteByGroupId((String)tempGroupId);
        }
        return vchrBalanceEOS;
    }

    @Override
    public List<Map<String, Object>> queryByCheckGroupId(String groupId) {
        List dimensions = this.dimensionService.findDimFieldsByTableName("GC_RELATEDOFFSETVCHRITEM");
        StringBuilder builder = new StringBuilder("select o.ID, o.UNITID , o.OFFSETSUBJECT,   o.offsetMethod, o.DEBITOFFSET , o.CREDITOFFSET , o.REMARK,");
        if (!CollectionUtils.isEmpty(dimensions)) {
            dimensions.forEach(dim -> builder.append("o.").append(dim.getCode()).append(","));
        }
        builder.append("r.UNITID AS \"org\", r.ACCTPERIOD , r.VCHRNUM, r.subjectCode, r.DEBITORIG AS \"debit\" , r.CREDITORIG AS \"credit\" ").append("from GC_RELATEDOFFSETVCHRITEM o  LEFT JOIN GC_RELATED_ITEM r ON o.srcItemId = r.id WHERE o.CHECKID = ?");
        return this.selectMap(builder.toString(), new Object[]{groupId});
    }
}

