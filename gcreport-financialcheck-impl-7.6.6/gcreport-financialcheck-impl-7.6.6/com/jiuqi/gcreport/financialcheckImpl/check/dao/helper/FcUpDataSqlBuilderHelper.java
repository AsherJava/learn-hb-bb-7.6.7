/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.FinancialCheckQueryConditionDTO
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.financialcheckImpl.check.dao.helper;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.financialcheckImpl.check.dao.helper.FcBaseSqlBuilderHelper;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.item.dto.FinancialCheckQueryConditionDTO;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class FcUpDataSqlBuilderHelper
extends FcBaseSqlBuilderHelper {
    @Override
    public String buildCheckedConditionSql(FinancialCheckQueryConditionDTO condition, String orgType, List<Object> param, Set<String> tempGroupIds) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select CHECKID from ").append("GC_RELATED_ITEM").append(" item ").append(" join ").append(orgType).append("  bfdw on item.unitid = bfdw.code and bfdw.VALIDTIME <= ? and bfdw.INVALIDTIME >= ? ").append(" join ").append(orgType).append(" dfdw on item.oppunitid = dfdw.code and dfdw.VALIDTIME <= ? and dfdw.INVALIDTIME >= ?");
        Date date = this.buildBusinessDate(condition);
        param.add(date);
        param.add(date);
        param.add(date);
        param.add(date);
        sql.append(" where 1=1 ");
        this.buildCommonConditionSql(condition, sql, param, tempGroupIds, CheckStateEnum.CHECKED);
        this.buildBusinessRoleConditionSql(condition, sql, true);
        this.buildOtherCheckedConditionSql(condition, sql, param);
        this.buildLevelSql(condition, sql, orgType, "bfdw", "dfdw");
        sql.append(" group by CHECKID order by max(CHECKTIME) desc ");
        return sql.toString();
    }

    @Override
    public String buildUncheckedGroupByUnitSql(FinancialCheckQueryConditionDTO condition, String orgType, Map<String, String> sortField2WayMap, List<Object> param, Set<String> tempGroupIds) {
        StringBuilder sql = new StringBuilder();
        String columnsSql = SqlUtils.getColumnsSqlByTableDefine((String)"GC_RELATED_ITEM", (String)"item");
        sql.append(" select ").append(columnsSql).append(" from ").append("GC_RELATED_ITEM").append(" item ").append(" join ").append(orgType).append("  bfdw on item.unitid = bfdw.code and bfdw.VALIDTIME <= ? and bfdw.INVALIDTIME >= ? ").append(" join ").append(orgType).append(" dfdw on item.oppunitid = dfdw.code and dfdw.VALIDTIME <= ? and dfdw.INVALIDTIME >= ?").append(" where 1=1 ");
        Date date = this.buildBusinessDate(condition);
        param.add(date);
        param.add(date);
        param.add(date);
        param.add(date);
        this.buildCommonConditionSql(condition, sql, param, tempGroupIds, CheckStateEnum.UNCHECKED);
        this.buildBusinessRoleConditionSql(condition, sql, true);
        this.buildLevelSql(condition, sql, orgType, "bfdw", "dfdw");
        sql.append(" order by ");
        if (sortField2WayMap.isEmpty()) {
            sql.append(" item.UNITID asc, item.OPPUNITID asc, item.SUBJECTCODE asc, item.DEBITORIG asc, item.CREDITORIG asc ");
        } else {
            this.buildSortSql(sql, sortField2WayMap, true);
        }
        return sql.toString();
    }

    @Override
    public String buildUncheckedGroupByOppUnitSql(FinancialCheckQueryConditionDTO condition, String orgType, Map<String, String> sortField2WayMap, List<Object> param, Set<String> tempGroupIds) {
        StringBuilder sql = new StringBuilder();
        String columnsSql = SqlUtils.getColumnsSqlByTableDefine((String)"GC_RELATED_ITEM", (String)"item");
        sql.append(" select ").append(columnsSql).append(" from ").append("GC_RELATED_ITEM").append(" item ").append(" join ").append(orgType).append("  bfdw on item.unitid = bfdw.code and bfdw.VALIDTIME <= ? and bfdw.INVALIDTIME >= ? ").append(" join ").append(orgType).append(" dfdw on item.oppunitid = dfdw.code and dfdw.VALIDTIME <= ? and dfdw.INVALIDTIME >= ?").append(" where 1=1 ");
        Date date = this.buildBusinessDate(condition);
        param.add(date);
        param.add(date);
        param.add(date);
        param.add(date);
        this.buildUncheckedOppUnitSql(condition, orgType, sql, tempGroupIds);
        this.buildBusinessRoleConditionSql(condition, sql, false);
        this.buildLevelSql(condition, sql, orgType, "dfdw", "bfdw");
        this.buildUncheckedOppConditionSql(condition, sql, param, tempGroupIds);
        sql.append(" order by ");
        if (sortField2WayMap.isEmpty()) {
            sql.append(" UNITID asc, OPPUNITID asc, SUBJECTCODE asc, DEBITORIG asc, CREDITORIG asc");
        } else {
            this.buildSortSql(sql, sortField2WayMap, true);
        }
        return sql.toString();
    }

    @Override
    public String buildUncheckedSchemeSql(FinancialCheckQueryConditionDTO condition, String orgType, Map<String, String> sortField2WayMap, List<Object> param, Set<String> tempGroupIds) {
        StringBuilder sql = new StringBuilder();
        String columnsSql = SqlUtils.getColumnsSqlByTableDefine((String)"GC_RELATED_ITEM", (String)"item");
        sql.append(" select ").append(columnsSql).append(" from ").append("GC_RELATED_ITEM").append(" item ").append(" join ").append(orgType).append("  bfdw on item.unitid = bfdw.code and bfdw.VALIDTIME <= ? and bfdw.INVALIDTIME >= ? ").append(" join ").append(orgType).append(" dfdw on item.oppunitid = dfdw.code and dfdw.VALIDTIME <= ? and dfdw.INVALIDTIME >= ?").append(" where  1=1 ");
        Date date = this.buildBusinessDate(condition);
        param.add(date);
        param.add(date);
        param.add(date);
        param.add(date);
        if (Objects.nonNull(condition.getBusinessRole())) {
            this.buildUncheckedSchemeUnitConditionSql(condition, sql, orgType, tempGroupIds);
        }
        this.buildCommonConditionSql(condition, sql, param, tempGroupIds, CheckStateEnum.UNCHECKED);
        sql.append(" order by CHECKRULEID asc, (case when UNITID > OPPUNITID then concat(UNITID, OPPUNITID) else concat(OPPUNITID, UNITID) end) asc,  UNITID asc ");
        if (!sortField2WayMap.isEmpty()) {
            this.buildSortSql(sql, sortField2WayMap, false);
        }
        return sql.toString();
    }

    @Override
    public String buildQueryAmtSumSql(FinancialCheckQueryConditionDTO condition, String orgType, List<Object> param, Set<String> tempGroupIds) {
        StringBuilder sql = new StringBuilder(" select \n");
        sql.append("sum(DEBITORIG) DEBITORIGSUM, \n").append("sum(CREDITORIG) CREDITORIGSUM, \n").append("sum(CHKAMTD) CHKAMTDSUM, \n").append("sum(CHKAMTC) CHKAMTCSUM, \n").append("sum(CASE WHEN CHKSTATE = 'UNCHECKED' THEN DEBITORIG ELSE 0 end ) UNCHECKDEBITSUM, \n").append("sum(CASE WHEN CHKSTATE = 'UNCHECKED' THEN CREDITORIG ELSE 0 end ) UNCHECKCREDITSUM \n").append("from \n").append("GC_RELATED_ITEM").append("\n").append(" item \n").append(" join ").append(orgType).append("  bfdw on item.unitid = bfdw.code and bfdw.VALIDTIME <= ? and bfdw.INVALIDTIME >= ? ").append(" join ").append(orgType).append(" dfdw on item.oppunitid = dfdw.code and dfdw.VALIDTIME <= ? and dfdw.INVALIDTIME >= ?");
        Date date = this.buildBusinessDate(condition);
        param.add(date);
        param.add(date);
        param.add(date);
        param.add(date);
        sql.append(" where 1=1 ");
        sql.append(" and ACCTYEAR = ? ");
        param.add(condition.getAcctYear());
        if (ReconciliationModeEnum.ITEM.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            sql.append(" and ACCTPERIOD <= ? ");
        } else {
            sql.append(" and ACCTPERIOD = ? ");
        }
        param.add(condition.getAcctPeriod());
        if (Objects.nonNull(condition.getBusinessRole())) {
            this.buildUncheckedSchemeUnitConditionSql(condition, sql, orgType, tempGroupIds);
        }
        return sql.toString();
    }

    private void buildUncheckedSchemeUnitConditionSql(FinancialCheckQueryConditionDTO condition, StringBuilder sql, String orgType, Set<String> tempGroupIds) {
        sql.append(" and ( ").append("( 1 = 1 ");
        this.buildUnitConditionSql(condition, sql, orgType, tempGroupIds, true);
        this.buildBusinessRoleConditionSql(condition, sql, true);
        this.buildLevelSql(condition, sql, orgType, "bfdw", "dfdw");
        sql.append(") or ( 1 = 1 ");
        this.buildUnitConditionSql(condition, sql, orgType, tempGroupIds, false);
        this.buildBusinessRoleConditionSql(condition, sql, false);
        this.buildLevelSql(condition, sql, orgType, "dfdw", "bfdw");
        sql.append(") ) ");
    }

    private void buildLevelSql(FinancialCheckQueryConditionDTO condition, StringBuilder sql, String orgType, String unitAlias, String oppUnitAlias) {
        YearPeriodObject yp = new YearPeriodObject(condition.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)condition.getAcctPeriod()));
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO localUnit = tool.getOrgByCode(condition.getUnitId());
        String parentId = localUnit.getParentId();
        if ("-".equals(parentId)) {
            throw new BusinessRuntimeException("\u9009\u4e2d\u5355\u4f4d\u3010" + localUnit.getTitle() + "\u3011\u4e3a\u6839\u8282\u70b9\uff0c\u65e0\u4e0a\u7ea7\u8282\u70b9\u3002");
        }
        int codeLength = tool.getOrgCodeLength();
        GcOrgCacheVO parentUnit = tool.getOrgByCode(parentId);
        int parentLength = parentUnit.getGcParentStr().length();
        sql.append(" and ").append(unitAlias).append(".gcparents like '").append(localUnit.getGcParentStr()).append("%' and ").append(oppUnitAlias).append(".gcparents like '").append(parentUnit.getGcParentStr()).append("_%' and substr(").append(unitAlias).append(".gcparents, 1,").append(parentLength + codeLength + 1).append(") <> substr(").append(oppUnitAlias).append(".gcparents, 1,").append(parentLength + codeLength + 1).append(") ");
    }

    private void buildUnitConditionSql(FinancialCheckQueryConditionDTO condition, StringBuilder sql, String orgType, Set<String> tempGroupIds, boolean isLocal) {
        YearPeriodObject yp = new YearPeriodObject(condition.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)condition.getAcctPeriod()));
        GcOrgCenterService accessService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        List gcOrgCacheVOS = accessService.listAllOrgByParentIdContainsSelf(condition.getUnitId());
        Set allLevelsChildrenAndSelf = gcOrgCacheVOS.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toSet());
        TempTableCondition unitTempCond = ReltxSqlUtils.getConditionOfMulStr(allLevelsChildrenAndSelf, (String)(isLocal ? "item.UNITID" : "item.OPPUNITID"));
        sql.append(" and  ").append(unitTempCond.getCondition());
        if (!StringUtils.isEmpty((String)unitTempCond.getTempGroupId())) {
            tempGroupIds.add(unitTempCond.getTempGroupId());
        }
    }
}

