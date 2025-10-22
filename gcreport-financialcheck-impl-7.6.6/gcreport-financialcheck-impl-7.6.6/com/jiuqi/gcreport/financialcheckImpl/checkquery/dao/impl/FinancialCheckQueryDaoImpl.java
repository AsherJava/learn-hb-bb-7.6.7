/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.checkquery.enums.CheckQueryLevelEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.BusinessRoleEnum
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.financialcheckImpl.checkquery.dao.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.dao.FinancialCheckQueryDao;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.dto.FinancialCheckQueryDTO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.checkquery.enums.CheckQueryLevelEnum;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.BusinessRoleEnum;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class FinancialCheckQueryDaoImpl
extends AbstractEntDbSqlGenericDAO<GcRelatedItemEO>
implements FinancialCheckQueryDao {
    public FinancialCheckQueryDaoImpl() {
        super(GcRelatedItemEO.class, (FEntSqlTemplate)new FcNativeSqlTemplate());
        this.setTableName("GC_RELATED_ITEM");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public PageInfo<Map<String, Object>> queryBothCheck(FinancialCheckQueryDTO financialCheckQueryDTO, List<String> checkProjectCodes, ReconciliationModeEnum checkWay, String orgType) {
        List result;
        int count;
        String debtUnitSql;
        String assetUnitSql;
        Date time = this.buildBusinessDate(financialCheckQueryDTO);
        BusinessRoleEnum businessRoleEnum = BusinessRoleEnum.getEnumByCode((Integer)financialCheckQueryDTO.getBusinessRole());
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select ");
        if (BusinessRoleEnum.ASSET.equals((Object)businessRoleEnum)) {
            sql.append(" max(case when record.BUSINESSROLE = 1 then record.UNITID else record.OPPUNITID end ) assetCheckUnit,  ");
            sql.append(" max(case when record.BUSINESSROLE = 1 then record.OPPUNITID else record.UNITID end) debtCheckUnit,  ");
        } else {
            sql.append(" max(case when record.BUSINESSROLE = -1 then record.UNITID else record.OPPUNITID end ) debtCheckUnit,  ");
            sql.append(" max(case when record.BUSINESSROLE = -1 then record.OPPUNITID else record.UNITID end) assetCheckUnit,  ");
        }
        sql.append(" record.ORIGINALCURR originalCurr, ");
        sql.append(" sum(case when record.BUSINESSROLE = 1 then (case when record.CHECkPROJECTDIRECTION = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end) assetTotal, ");
        sql.append(" sum(case when record.BUSINESSROLE = -1 then (case when record.CHECkPROJECTDIRECTION = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end) debtTotal, ");
        checkProjectCodes.forEach(checkProjectCode -> sql.append(" sum(case when record.CHECKPROJECT = '").append((String)checkProjectCode).append("' then (case when record.CHECkPROJECTDIRECTION = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end) ").append((String)checkProjectCode).append(" , "));
        sql.append(" sum(CASE WHEN record.CHKSTATE = 'UNCHECKED' THEN 1 ELSE 0 END) uncheckedCount ,");
        sql.append(" abs(sum(case when record.BUSINESSROLE = 1 then (case when record.CHECkPROJECTDIRECTION = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end) - sum(case when record.BUSINESSROLE = -1 then (case when record.CHECkPROJECTDIRECTION = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end)) diffAmount , ");
        sql.append(" (case when sum(CASE WHEN record.CHKSTATE = 'UNCHECKED' THEN 1 ELSE 0 END) > 0 then 0  else 2  end) checkStates ");
        sql.append(" from GC_RELATED_ITEM record ");
        sql.append(" left join ").append(orgType).append(" bfdw on record.UNITID = bfdw.code and bfdw.VALIDTIME <= ? and bfdw.INVALIDTIME >= ? ");
        params.add(time);
        params.add(time);
        sql.append(" left join ").append(orgType).append(" dfdw on record.OPPUNITID = dfdw.code and dfdw.VALIDTIME <= ? and dfdw.INVALIDTIME >= ?");
        params.add(time);
        params.add(time);
        if (BusinessRoleEnum.ALL.getCode().equals(financialCheckQueryDTO.getBusinessRole())) {
            sql.append(" where (({assetUnitCond} ) or ({debtUnitCond} ) ) ");
        } else {
            sql.append(" where (({assetUnitCond} and record.BUSINESSROLE = 1 ) or ({debtUnitCond} and record.BUSINESSROLE = -1))  ");
        }
        sql.append(" and record.CHECKRULEID = ? ");
        params.add(financialCheckQueryDTO.getSchemeId());
        sql.append(" and record.ACCTYEAR = ? ");
        params.add(financialCheckQueryDTO.getAcctYear());
        if (ReconciliationModeEnum.BALANCE.equals((Object)checkWay)) {
            sql.append(" and record.ACCTPERIOD = ? ");
        } else {
            sql.append(" and record.ACCTPERIOD <= ? ");
        }
        params.add(financialCheckQueryDTO.getAcctPeriod());
        if (!CollectionUtils.isEmpty(financialCheckQueryDTO.getCurrency())) {
            String conditionOfIdsUseOr = SqlUtils.getConditionOfIdsUseOr(financialCheckQueryDTO.getCurrency(), (String)"record.ORIGINALCURR");
            sql.append(" and  ").append(conditionOfIdsUseOr);
        }
        sql.append(" group by record.UNITCOMBINE, record.ORIGINALCURR having  1=1 ");
        if (Objects.nonNull(financialCheckQueryDTO.getMinDiffAmount())) {
            sql.append(" and abs(sum(case when record.BUSINESSROLE = 1 then (case when record.CHECkPROJECTDIRECTION = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end)  - sum(case when record.BUSINESSROLE = -1 then (case when record.CHECkPROJECTDIRECTION = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end)) >= ? ");
            params.add(financialCheckQueryDTO.getMinDiffAmount());
        }
        if (Objects.nonNull(financialCheckQueryDTO.getMaxDiffAmount())) {
            sql.append(" and abs(sum(case when record.BUSINESSROLE = 1 then (case when record.CHECkPROJECTDIRECTION = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end)  - sum(case when record.BUSINESSROLE = -1 then (case when record.CHECkPROJECTDIRECTION = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end)) <= ? ");
            params.add(financialCheckQueryDTO.getMaxDiffAmount());
        }
        if (!CollectionUtils.isEmpty(financialCheckQueryDTO.getCheckStates())) {
            StringJoiner stringJoiner = new StringJoiner(",", "(", ")");
            financialCheckQueryDTO.getCheckStates().forEach(states -> stringJoiner.add(states.toString()));
            sql.append(" and (case when sum(CASE WHEN record.CHKSTATE = 'UNCHECKED' THEN 1 ELSE 0 END) > 0 then 0  else 2 end) in ").append(stringJoiner);
        }
        sql.append(" order by record.UNITCOMBINE, record.ORIGINALCURR ");
        HashSet<String> tempGroupIds = new HashSet<String>();
        String unitSql = this.buildUnitCondSql(financialCheckQueryDTO, orgType, tempGroupIds);
        if (BusinessRoleEnum.ASSET.equals((Object)businessRoleEnum)) {
            assetUnitSql = unitSql.replace("{bfpla}", "bfdw").replace("{dfpla}", "dfdw");
            debtUnitSql = unitSql.replace("{dfpla}", "bfdw").replace("{bfpla}", "dfdw");
        } else {
            assetUnitSql = unitSql.replace("{dfpla}", "bfdw").replace("{bfpla}", "dfdw");
            debtUnitSql = unitSql.replace("{bfpla}", "bfdw").replace("{dfpla}", "dfdw");
        }
        try {
            String finalSql = sql.toString().replace("{assetUnitCond}", assetUnitSql).replace("{debtUnitCond}", debtUnitSql);
            String countSql = " select count(1) from (%1$s) T";
            count = this.count(String.format(countSql, finalSql), params);
            result = this.selectMapByPaging(finalSql, financialCheckQueryDTO.getPageSize() * (financialCheckQueryDTO.getPageNum() - 1), financialCheckQueryDTO.getPageSize() * financialCheckQueryDTO.getPageNum(), params);
        }
        finally {
            ReltxSqlUtils.deleteByGroupIds(tempGroupIds);
        }
        return PageInfo.of((List)result, (int)financialCheckQueryDTO.getPageNum(), (int)financialCheckQueryDTO.getPageSize(), (int)count);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public PageInfo<Map<String, Object>> querySingleCheck(FinancialCheckQueryDTO financialCheckQueryDTO, List<String> checkProjectCodes, ReconciliationModeEnum checkWay, String orgType) {
        List result;
        int count;
        Date time = this.buildBusinessDate(financialCheckQueryDTO);
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select ");
        sql.append(" record.UNITID unit, ");
        sql.append(" record.OPPUNITID oppUnit, ");
        sql.append(" record.ORIGINALCURR originalCurr, ");
        sql.append(" sum(case when record.CHECkPROJECTDIRECTION = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) total, ");
        checkProjectCodes.forEach(checkProjectCode -> sql.append(" sum(case when record.CHECKPROJECT = '").append((String)checkProjectCode).append("' then (case when record.CHECkPROJECTDIRECTION = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end) ").append((String)checkProjectCode).append(" , "));
        sql.append(" count(1) count ,");
        sql.append(" (case when sum(CASE WHEN record.CHKSTATE = 'UNCHECKED' THEN 1 ELSE 0 END) > 0 then 0  else 2  end) checkStates ");
        sql.append(" from GC_RELATED_ITEM record ");
        sql.append(" left join ").append(orgType).append("  bfdw on record.unitid = bfdw.code and bfdw.VALIDTIME <= ? and bfdw.INVALIDTIME >= ? ");
        params.add(time);
        params.add(time);
        sql.append(" left join ").append(orgType).append(" dfdw on record.oppunitid = dfdw.code and dfdw.VALIDTIME <= ? and dfdw.INVALIDTIME >= ?");
        params.add(time);
        params.add(time);
        sql.append(" where {assetUnitCond} ");
        sql.append(" and record.CHECKRULEID = ? ");
        params.add(financialCheckQueryDTO.getSchemeId());
        sql.append(" and record.ACCTYEAR = ? ");
        params.add(financialCheckQueryDTO.getAcctYear());
        if (ReconciliationModeEnum.BALANCE.equals((Object)checkWay)) {
            sql.append(" and record.ACCTPERIOD = ? ");
        } else {
            sql.append(" and record.ACCTPERIOD <= ? ");
        }
        params.add(financialCheckQueryDTO.getAcctPeriod());
        if (!CollectionUtils.isEmpty(financialCheckQueryDTO.getCurrency())) {
            String conditionOfIdsUseOr = SqlUtils.getConditionOfIdsUseOr(financialCheckQueryDTO.getCurrency(), (String)"record.ORIGINALCURR");
            sql.append(" and  ").append(conditionOfIdsUseOr);
        }
        sql.append(" group by record.UNITID, record.OPPUNITID, record.ORIGINALCURR having 1=1 ");
        if (!CollectionUtils.isEmpty(financialCheckQueryDTO.getCheckStates())) {
            StringJoiner stringJoiner = new StringJoiner(",", "(", ")");
            financialCheckQueryDTO.getCheckStates().forEach(states -> stringJoiner.add(states.toString()));
            sql.append(" and (case when sum(CASE WHEN record.CHKSTATE = 'UNCHECKED' THEN 1 ELSE 0 END) > 0 then 0  else 2  end) in ").append(stringJoiner);
        }
        sql.append(" order by record.UNITID, record.OPPUNITID, record.ORIGINALCURR ");
        HashSet<String> tempGroupIds = new HashSet<String>();
        String unitSql = this.buildUnitCondSql(financialCheckQueryDTO, orgType, tempGroupIds);
        try {
            String assetUnitSql = unitSql.replace("{bfpla}", "bfdw").replace("{dfpla}", "dfdw");
            String finalSql = sql.toString().replace("{assetUnitCond}", assetUnitSql);
            String countSql = " select count(1) from (%1$s) T";
            count = this.count(String.format(countSql, finalSql), params);
            result = this.selectMapByPaging(finalSql, financialCheckQueryDTO.getPageSize() * (financialCheckQueryDTO.getPageNum() - 1), financialCheckQueryDTO.getPageSize() * financialCheckQueryDTO.getPageNum(), params);
        }
        finally {
            ReltxSqlUtils.deleteByGroupIds(tempGroupIds);
        }
        return PageInfo.of((List)result, (int)financialCheckQueryDTO.getPageNum(), (int)financialCheckQueryDTO.getPageSize(), (int)count);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public PageInfo<Map<String, Object>> queryOffsetVoucherModeCheck(FinancialCheckQueryDTO financialCheckQueryDTO, List<String> oneLevelSubjects, String orgType) {
        List result;
        int count;
        String debtUnitSql;
        String assetUnitSql;
        Date time = this.buildBusinessDate(financialCheckQueryDTO);
        BusinessRoleEnum businessRoleEnum = BusinessRoleEnum.getEnumByCode((Integer)financialCheckQueryDTO.getBusinessRole());
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select ");
        if (BusinessRoleEnum.ASSET.equals((Object)businessRoleEnum)) {
            sql.append(" max(case when record.BUSINESSROLE = 1 then record.UNITID else record.OPPUNITID end ) assetCheckUnit,  ");
            sql.append(" max(case when record.BUSINESSROLE = 1 then record.OPPUNITID else record.UNITID end) debtCheckUnit,  ");
        } else {
            sql.append(" max(case when record.BUSINESSROLE = -1 then record.UNITID else record.OPPUNITID end ) debtCheckUnit,  ");
            sql.append(" max(case when record.BUSINESSROLE = -1 then record.OPPUNITID else record.UNITID end) assetCheckUnit,  ");
        }
        sql.append(" record.ORIGINALCURR originalCurr, ");
        sql.append(" sum(case when record.BUSINESSROLE = 1 then (case when SUB.orient = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end) assetTotal, ");
        sql.append(" sum(case when record.BUSINESSROLE = -1 then (case when SUB.orient = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end) debtTotal, ");
        oneLevelSubjects.forEach(oneLevelSubjectCode -> sql.append(" sum(case when SUB.code = '").append((String)oneLevelSubjectCode).append("' then (case when SUB.orient = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end) ").append("ONELEVELSUBJECT").append((String)oneLevelSubjectCode).append(" , "));
        sql.append(" sum(CASE WHEN record.CHKSTATE = 'UNCHECKED' THEN 1 ELSE 0 END) uncheckedCount ,");
        sql.append(" abs(sum(case when record.BUSINESSROLE = 1 then (case when SUB.orient = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end) - sum(case when record.BUSINESSROLE = -1 then (case when SUB.orient = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end)) diffAmount , ");
        sql.append(" (case when sum(CASE WHEN record.CHKSTATE = 'UNCHECKED' THEN 1 ELSE 0 END) > 0 then 0 else 2  end) checkStates ");
        sql.append(" from GC_RELATED_ITEM record ");
        sql.append("JOIN MD_ACCTSUBJECT SUB  on record.CHECKPROJECT = SUB.code");
        sql.append("  join ").append(orgType).append(" bfdw on record.UNITID = bfdw.code and bfdw.VALIDTIME <= ? and bfdw.INVALIDTIME >= ? ");
        params.add(time);
        params.add(time);
        sql.append("  join ").append(orgType).append(" dfdw on record.OPPUNITID = dfdw.code and dfdw.VALIDTIME <= ? and dfdw.INVALIDTIME >= ?");
        params.add(time);
        params.add(time);
        if (BusinessRoleEnum.ALL.getCode().equals(financialCheckQueryDTO.getBusinessRole())) {
            sql.append(" where (({assetUnitCond} ) or ({debtUnitCond} ) ) ");
        } else {
            sql.append(" where (({assetUnitCond} and record.BUSINESSROLE = 1 ) or ({debtUnitCond} and record.BUSINESSROLE = -1))  ");
        }
        sql.append(" and record.CHECKRULEID = ? ");
        params.add(financialCheckQueryDTO.getSchemeId());
        sql.append(" and record.ACCTYEAR = ? ");
        params.add(financialCheckQueryDTO.getAcctYear());
        sql.append(" and record.ACCTPERIOD <= ? ");
        params.add(financialCheckQueryDTO.getAcctPeriod());
        sql.append(" and checkAttribute = ?");
        params.add(financialCheckQueryDTO.getCheckAttribute());
        if (!CollectionUtils.isEmpty(financialCheckQueryDTO.getCurrency())) {
            String conditionOfIdsUseOr = SqlUtils.getConditionOfIdsUseOr(financialCheckQueryDTO.getCurrency(), (String)"record.ORIGINALCURR");
            sql.append(" and  ").append(conditionOfIdsUseOr);
        }
        sql.append(" group by record.UNITCOMBINE, record.ORIGINALCURR having  1=1 ");
        if (Objects.nonNull(financialCheckQueryDTO.getMinDiffAmount())) {
            sql.append(" and abs(sum(case when record.BUSINESSROLE = 1 then (case when SUB.orient = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end)  - sum(case when record.BUSINESSROLE = -1 then (case when SUB.orient = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end)) >= ? ");
            params.add(financialCheckQueryDTO.getMinDiffAmount());
        }
        if (Objects.nonNull(financialCheckQueryDTO.getMaxDiffAmount())) {
            sql.append(" and abs(sum(case when record.BUSINESSROLE = 1 then (case when SUB.orient = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end)  - sum(case when record.BUSINESSROLE = -1 then (case when SUB.orient = 1 then record.DEBITORIG - record.CREDITORIG else record.CREDITORIG - record.DEBITORIG end) else 0 end)) <= ? ");
            params.add(financialCheckQueryDTO.getMaxDiffAmount());
        }
        if (!CollectionUtils.isEmpty(financialCheckQueryDTO.getCheckStates())) {
            StringJoiner stringJoiner = new StringJoiner(",", "(", ")");
            financialCheckQueryDTO.getCheckStates().forEach(states -> stringJoiner.add(states.toString()));
            sql.append(" and (case when sum(CASE WHEN record.CHKSTATE = 'UNCHECKED' THEN 1 ELSE 0 END) > 0 then 0  else 2  end) in ").append(stringJoiner);
        }
        sql.append(" order by record.UNITCOMBINE, record.ORIGINALCURR ");
        HashSet<String> tempGroupIds = new HashSet<String>();
        String unitSql = this.buildUnitCondSql(financialCheckQueryDTO, orgType, tempGroupIds);
        if (!BusinessRoleEnum.DEBT.equals((Object)businessRoleEnum)) {
            assetUnitSql = unitSql.replace("{bfpla}", "bfdw").replace("{dfpla}", "dfdw");
            debtUnitSql = unitSql.replace("{dfpla}", "bfdw").replace("{bfpla}", "dfdw");
        } else {
            assetUnitSql = unitSql.replace("{dfpla}", "bfdw").replace("{bfpla}", "dfdw");
            debtUnitSql = unitSql.replace("{bfpla}", "bfdw").replace("{dfpla}", "dfdw");
        }
        try {
            String finalSql = sql.toString().replace("{assetUnitCond}", assetUnitSql).replace("{debtUnitCond}", debtUnitSql);
            String countSql = " select count(1) from (%1$s) T";
            count = this.count(String.format(countSql, finalSql), params);
            result = this.selectMapByPaging(finalSql, financialCheckQueryDTO.getPageSize() * (financialCheckQueryDTO.getPageNum() - 1), financialCheckQueryDTO.getPageSize() * financialCheckQueryDTO.getPageNum(), params);
        }
        finally {
            ReltxSqlUtils.deleteByGroupIds(tempGroupIds);
        }
        return PageInfo.of((List)result, (int)financialCheckQueryDTO.getPageNum(), (int)financialCheckQueryDTO.getPageSize(), (int)count);
    }

    private String buildUnitCondSql(FinancialCheckQueryDTO financialCheckQueryDTO, String orgType, Set<String> tempGroupIds) {
        String localUnitCode = financialCheckQueryDTO.getLocalUnit();
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)new YearPeriodObject(financialCheckQueryDTO.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)financialCheckQueryDTO.getAcctPeriod())));
        GcOrgCacheVO localUnit = tool.getOrgByCode(localUnitCode);
        if (localUnit.isLeaf()) {
            financialCheckQueryDTO.setCheckLevel(CheckQueryLevelEnum.CUSTOM.getCode());
        }
        List gcOrgCacheVOS = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)new YearPeriodObject(financialCheckQueryDTO.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)financialCheckQueryDTO.getAcctPeriod()))).listAllOrgByParentIdContainsSelf(localUnitCode);
        List unitCodes = gcOrgCacheVOS.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
        TempTableCondition conditionOfIds = ReltxSqlUtils.getConditionOfMulStr(unitCodes, (String)"{bfpla}.code");
        StringBuilder unitSql = new StringBuilder();
        unitSql.append(conditionOfIds.getCondition());
        List allOrg = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(financialCheckQueryDTO.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)financialCheckQueryDTO.getAcctPeriod()))).listOrgBySearch(null);
        List allUnitCodes = allOrg.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
        TempTableCondition allUnitCond = ReltxSqlUtils.getConditionOfMulStr(allUnitCodes, (String)"{dfpla}.code");
        unitSql.append(" and ").append(allUnitCond.getCondition());
        int codeLength = tool.getOrgCodeLength();
        if (!StringUtils.isEmpty((String)financialCheckQueryDTO.getCheckLevel())) {
            CheckQueryLevelEnum checkQueryLevelEnum = CheckQueryLevelEnum.getEnumByCode((String)financialCheckQueryDTO.getCheckLevel());
            switch (Objects.requireNonNull(checkQueryLevelEnum)) {
                case UP: {
                    String parentId = localUnit.getParentId();
                    if ("-".equals(parentId)) {
                        throw new BusinessRuntimeException("\u9009\u4e2d\u5355\u4f4d\u3010" + localUnit.getTitle() + "\u3011\u4e3a\u6839\u8282\u70b9\uff0c\u65e0\u4e0a\u7ea7\u8282\u70b9\u3002");
                    }
                    GcOrgCacheVO parentUnit = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(financialCheckQueryDTO.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)financialCheckQueryDTO.getAcctPeriod()))).getOrgByCode(parentId);
                    int parentLength = parentUnit.getGcParentStr().length();
                    unitSql.append(" and ").append(" {bfpla}.parents like '").append(localUnit.getParentStr()).append("%' and {dfpla}.parents like '").append(parentUnit.getParentStr()).append("_%' and substr({bfpla}.gcparents, 1,").append(parentLength + codeLength + 1).append(") <> substr({dfpla}.gcparents, 1,").append(parentLength + codeLength + 1).append(") ");
                    break;
                }
                case CURRENT: {
                    int currentLength = localUnit.getGcParentStr().length();
                    unitSql.append(" and ").append(" {bfpla}.parents like '").append(localUnit.getParentStr()).append("%' and {dfpla}.parents like '").append(localUnit.getParentStr()).append("%' and substr({bfpla}.gcparents, 1,").append(currentLength + codeLength + 1).append(") <> substr({dfpla}.gcparents, 1,").append(currentLength + codeLength + 1).append(") ");
                    break;
                }
                case DOWN: {
                    int length = localUnit.getGcParentStr().length();
                    unitSql.append(" and ").append(" {bfpla}.parents like '").append(localUnit.getParentStr()).append("_%' and {dfpla}.parents like '").append(localUnit.getParentStr()).append("_%' and substr({bfpla}.gcparents, 1,").append(length + codeLength + 1).append(") = substr({dfpla}.gcparents, 1,").append(length + codeLength + 1).append(") ");
                    break;
                }
                case CUSTOM: {
                    String oppUnitCode = financialCheckQueryDTO.getOppUnit();
                    List oppUnits = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(financialCheckQueryDTO.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)financialCheckQueryDTO.getAcctPeriod()))).listAllOrgByParentIdContainsSelf(oppUnitCode);
                    List oppUnitCodes = oppUnits.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
                    TempTableCondition oppUnitCond = ReltxSqlUtils.getConditionOfMulStr(oppUnitCodes, (String)"{dfpla}.code");
                    unitSql.append(" and ").append(oppUnitCond.getCondition());
                    if (StringUtils.isEmpty((String)oppUnitCond.getTempGroupId())) break;
                    tempGroupIds.add(oppUnitCond.getTempGroupId());
                }
            }
        }
        if (!StringUtils.isEmpty((String)conditionOfIds.getTempGroupId())) {
            tempGroupIds.add(conditionOfIds.getTempGroupId());
        }
        if (!StringUtils.isEmpty((String)allUnitCond.getTempGroupId())) {
            tempGroupIds.add(allUnitCond.getTempGroupId());
        }
        return unitSql.toString();
    }

    private Date buildBusinessDate(FinancialCheckQueryDTO financialCheckQueryDTO) {
        Calendar calendar = Calendar.getInstance();
        Integer acctPeriod = financialCheckQueryDTO.getAcctPeriod();
        if (acctPeriod == 0) {
            acctPeriod = 1;
        } else if (acctPeriod == 13) {
            acctPeriod = 12;
        }
        calendar.set(1, financialCheckQueryDTO.getAcctYear());
        calendar.set(2, acctPeriod - 1);
        calendar.set(5, 1);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 0);
        return calendar.getTime();
    }
}

