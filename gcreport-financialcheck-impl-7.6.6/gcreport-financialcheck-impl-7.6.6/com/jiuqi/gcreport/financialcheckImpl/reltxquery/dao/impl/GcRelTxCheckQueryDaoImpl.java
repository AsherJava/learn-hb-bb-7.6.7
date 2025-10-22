/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4DataV0
 *  com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryParamVO
 *  com.jiuqi.gcreport.financialcheckcore.checkquery.enums.CheckQueryLevelEnum
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.period.PeriodWrapper
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.gcreport.financialcheckImpl.reltxquery.dao.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.financialcheckImpl.reltxquery.dao.GcRelTxCheckQueryDao;
import com.jiuqi.gcreport.financialcheckImpl.reltxquery.domain.RelTxCheckQueryLevel4ParamDO;
import com.jiuqi.gcreport.financialcheckImpl.reltxquery.domain.RelTxCheckQueryTableDataDO;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4DataV0;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryParamVO;
import com.jiuqi.gcreport.financialcheckcore.checkquery.enums.CheckQueryLevelEnum;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GcRelTxCheckQueryDaoImpl
implements GcRelTxCheckQueryDao {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<RelTxCheckQueryTableDataDO> queryData(RelTxCheckQueryParamVO param, boolean isCF) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<Object>();
        Date time = this.buildBusinessDate(param);
        String orgType = param.getOrgType();
        PeriodWrapper periodWrapper = new PeriodWrapper(param.getAcctYear().intValue(), 4, param.getAcctPeriod().intValue());
        String dataTime = periodWrapper.toString();
        sql.append("SELECT  V.UNITCODE , V.OPPUNITCODE, V.ORGNCURRENCY, SUB.CHECKPROJECT, sum( V.ORGNCF  * SUB.CHECkPROJECTDIRECTION ) as AMT,  ").append(" max(SUB.BUSINESSROLE) as BUSINESSROLE, max(SUB.CHECKATTRIBUTE) as CHECKATTRIBUTE FROM ").append(isCF ? "GC_FINCUBES_CF_Y" : "GC_FINCUBES_DIM_Y").append(" V ");
        sql.append("  join ").append(orgType).append(" bfdw on V.UNITCODE = bfdw.code and bfdw.VALIDTIME <= ? and bfdw.INVALIDTIME >= ? ");
        params.add(time);
        params.add(time);
        sql.append("  join ").append(orgType).append(" dfdw on V.OPPUNITCODE = dfdw.code and dfdw.VALIDTIME <= ? and dfdw.INVALIDTIME >= ?");
        params.add(time);
        params.add(time);
        sql.append(" join ").append("GC_RELTX_SUBJECT_MAPPING").append(isCF ? " SUB on V.CFITEMCODE = SUB.SUBJECTCODE" : " SUB on V.SUBJECTCODE = SUB.SUBJECTCODE");
        sql.append("  where V.MD_AUDITTRAIL LIKE '11%' ");
        sql.append("  and V.DATATIME = ? ");
        params.add(dataTime);
        sql.append(" AND v.MD_GCORGTYPE = 'NONE' ").append(" AND  V.OPPUNITCODE IS NOT NULL ");
        if (!CollectionUtils.isEmpty((Collection)param.getCurrency())) {
            String conditionOfIdsUseOr = SqlUtils.getConditionOfIdsUseOr((Collection)param.getCurrency(), (String)"V.ORGNCURRENCY");
            sql.append(" and  ").append(conditionOfIdsUseOr);
        }
        HashSet<String> tempGroupIds = new HashSet<String>();
        sql.append(this.buildUnitCondSql(param, tempGroupIds));
        sql.append(" group by  V.UNITCODE , V.OPPUNITCODE, V.ORGNCURRENCY, SUB.CHECKPROJECT");
        try {
            String finalSql = sql.toString();
            List list = OuterDataSourceUtils.getJdbcTemplate().query(finalSql, (RowMapper)new BeanPropertyRowMapper(RelTxCheckQueryTableDataDO.class), new Object[]{time, time, time, time, dataTime});
            return list;
        }
        finally {
            ReltxSqlUtils.deleteByGroupIds(tempGroupIds);
        }
    }

    @Override
    public RelTxCheckQueryLevel4DataV0 queryLevel4Data(RelTxCheckQueryLevel4ParamDO param) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select sum(case when chkstate = 'UNCHECKED' then 0 else  creditorig + debitorig end) checkAmt, ");
        sql.append(" sum(case when chkstate = 'UNCHECKED' then creditorig + debitorig else 0 end) unCheckAmt ");
        sql.append(" from gc_related_item ");
        sql.append(" where unitid = ? and oppunitid = ?");
        sql.append(" and acctyear = ? and acctperiod <= ? ");
        sql.append(" and checkproject = ? and originalcurr = ?");
        return (RelTxCheckQueryLevel4DataV0)OuterDataSourceUtils.getJdbcTemplate().queryForObject(sql.toString(), (RowMapper)new BeanPropertyRowMapper(RelTxCheckQueryLevel4DataV0.class), new Object[]{param.getUnit(), param.getOppUnit(), param.getAcctYear(), param.getAcctPeriod(), param.getCheckProject(), param.getOriginalCurr()});
    }

    private String buildUnitCondSql(RelTxCheckQueryParamVO param, Set<String> tempGroupIds) {
        String orgType = param.getOrgType();
        String localUnitCode = param.getLocalUnit();
        YearPeriodObject yp = new YearPeriodObject(param.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)param.getAcctPeriod()));
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO localUnit = tool.getOrgByCode(localUnitCode);
        if (localUnit.isLeaf()) {
            param.setCheckLevel(CheckQueryLevelEnum.CUSTOM.getCode());
        }
        List gcOrgCacheVOS = tool.listAllOrgByParentIdContainsSelf(localUnitCode);
        List<String> unitCodes = gcOrgCacheVOS.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
        StringBuilder unitSql = new StringBuilder();
        int codeLength = tool.getOrgCodeLength();
        if (!StringUtils.isEmpty((String)param.getCheckLevel())) {
            CheckQueryLevelEnum checkQueryLevelEnum = CheckQueryLevelEnum.getEnumByCode((String)param.getCheckLevel());
            switch (Objects.requireNonNull(checkQueryLevelEnum)) {
                case ALL: {
                    unitSql.append("and ( ");
                    this.appendUnitSql(unitSql, unitCodes, tempGroupIds, "V.UNITCODE");
                    unitSql.append(" or ");
                    this.appendUnitSql(unitSql, unitCodes, tempGroupIds, "V.OPPUNITCODE");
                    unitSql.append(" )");
                    break;
                }
                case UP: {
                    unitSql.append("and ( ");
                    this.appendUnitSql(unitSql, unitCodes, tempGroupIds, "V.UNITCODE");
                    unitSql.append(" or ");
                    this.appendUnitSql(unitSql, unitCodes, tempGroupIds, "V.OPPUNITCODE");
                    unitSql.append(" )");
                    String parentId = localUnit.getParentId();
                    if ("-".equals(parentId)) {
                        throw new BusinessRuntimeException("\u9009\u4e2d\u5355\u4f4d\u3010" + localUnit.getTitle() + "\u3011\u4e3a\u6839\u8282\u70b9\uff0c\u65e0\u4e0a\u7ea7\u8282\u70b9\u3002");
                    }
                    GcOrgCacheVO parentUnit = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp).getOrgByCode(parentId);
                    int parentLength = parentUnit.getGcParentStr().length();
                    unitSql.append(" and ").append(" bfdw.parents like '").append(localUnit.getParentStr()).append("%' and dfdw.parents like '").append(parentUnit.getParentStr()).append("%' and substr(bfdw.gcparents, 1,").append(parentLength + codeLength + 1).append(") <> substr(dfdw.gcparents, 1,").append(parentLength + codeLength + 1).append(") ");
                    break;
                }
                case CURRENT: {
                    int currentLength = localUnit.getGcParentStr().length();
                    unitSql.append(" and ").append(" bfdw.parents like '").append(localUnit.getParentStr()).append("%' and dfdw.parents like '").append(localUnit.getParentStr()).append("%' and substr(bfdw.gcparents, 1,").append(currentLength + codeLength + 1).append(") <> substr(dfdw.gcparents, 1,").append(currentLength + codeLength + 1).append(") ");
                    break;
                }
                case DOWN: {
                    int length = localUnit.getGcParentStr().length();
                    unitSql.append(" and ").append(" bfdw.parents like '").append(localUnit.getParentStr()).append("_%' and dfdw.parents like '").append(localUnit.getParentStr()).append("_%' and substr(bfdw.gcparents, 1,").append(length + codeLength + 1).append(") = substr(dfdw.gcparents, 1,").append(length + codeLength + 1).append(") ");
                    break;
                }
                case CUSTOM: {
                    String oppUnitCode = param.getOppUnit();
                    List oppUnits = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp).listAllOrgByParentIdContainsSelf(oppUnitCode);
                    List<String> oppUnitCodes = oppUnits.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
                    unitSql.append("and ((");
                    this.appendUnitSql(unitSql, unitCodes, tempGroupIds, "V.UNITCODE");
                    unitSql.append(" and ");
                    this.appendUnitSql(unitSql, oppUnitCodes, tempGroupIds, "V.OPPUNITCODE");
                    unitSql.append(" )");
                    unitSql.append(" or (");
                    this.appendUnitSql(unitSql, unitCodes, tempGroupIds, "V.OPPUNITCODE");
                    unitSql.append(" and ");
                    this.appendUnitSql(unitSql, oppUnitCodes, tempGroupIds, "V.UNITCODE");
                    unitSql.append(" ))");
                }
            }
        }
        return unitSql.toString();
    }

    private void appendUnitSql(StringBuilder unitSql, List<String> unitCodes, Set<String> tempGroupIds, String fieldName) {
        TempTableCondition conditionOfIds = ReltxSqlUtils.getConditionOfMulStr(unitCodes, (String)fieldName);
        unitSql.append(conditionOfIds.getCondition());
        if (!StringUtils.isEmpty((String)conditionOfIds.getTempGroupId())) {
            tempGroupIds.add(conditionOfIds.getTempGroupId());
        }
    }

    private Date buildBusinessDate(RelTxCheckQueryParamVO param) {
        Calendar calendar = Calendar.getInstance();
        Integer acctPeriod = param.getAcctPeriod();
        if (acctPeriod == 0) {
            acctPeriod = 1;
        } else if (acctPeriod == 13) {
            acctPeriod = 12;
        }
        calendar.set(1, param.getAcctYear());
        calendar.set(2, acctPeriod - 1);
        calendar.set(5, 1);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 0);
        return calendar.getTime();
    }
}

