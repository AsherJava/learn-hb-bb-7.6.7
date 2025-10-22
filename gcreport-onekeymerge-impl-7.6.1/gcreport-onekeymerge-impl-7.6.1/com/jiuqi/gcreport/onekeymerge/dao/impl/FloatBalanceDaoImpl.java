/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.entity.FloatBalanceEO
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.onekeymerge.dao.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.entity.FloatBalanceEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.onekeymerge.dao.FloatBalanceDao;
import com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FloatBalanceDaoImpl
extends GcDbSqlGenericDAO<FloatBalanceEO, String>
implements FloatBalanceDao {
    private Logger logger = LoggerFactory.getLogger(FloatBalanceDaoImpl.class);
    private final String AMT = "AMT";
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    private final String BATCH_CLEAR_AMT_SQL = "update GC_FLOATBALANCE  i \n set amt=0\n  where i.MDCODE=? and i.DATATIME=?\n %1s\n";
    private final String BATCH_DELETE_EMPTY_ROW_SQL = "\tdelete from GC_FLOATBALANCE i \n  where i.MDCODE=? and i.DATATIME=?\n %1s\n";

    public FloatBalanceDaoImpl() {
        super(FloatBalanceEO.class);
    }

    @Override
    public void batchClearAmt(String unitCode, GcActionParamsVO paramsVO) {
        StringBuilder whereSql = this.buildEntityTableWhere(unitCode, paramsVO);
        this.execute(String.format("update GC_FLOATBALANCE  i \n set amt=0\n  where i.MDCODE=? and i.DATATIME=?\n %1s\n", whereSql), new Object[]{unitCode, paramsVO.getPeriodStr()});
    }

    @Override
    public List<FloatBalanceEO> queryByMergeCode(boolean isFilterByOppUnit, GcActionParamsVO paramsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, paramsVO.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)paramsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        Date date = yp.formatYP().getEndDate();
        ArrayList<Object> paramList = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(this.getAllFieldsSQL()).append("\n");
        sql.append(" from ").append("GC_FLOATBALANCE").append("  i \n");
        sql.append(" join ").append(paramsVO.getOrgType()).append("  t on (i.MDCODE = t.code) \n");
        if (isFilterByOppUnit) {
            sql.append(" join ").append(paramsVO.getOrgType()).append("  oppUnit on (i.oppUnitCode = oppUnit.code) \n");
        }
        sql.append(" where t.validtime<? and t.invalidtime>=? \n");
        paramList.addAll(Arrays.asList(date, date));
        if (isFilterByOppUnit) {
            sql.append(" and  oppUnit.validtime<? and oppUnit.invalidtime>=? \n");
            paramList.addAll(Arrays.asList(date, date));
            GcOrgCacheVO mergeOrg = orgTool.getOrgByCode(paramsVO.getOrgId());
            sql.append(" and substr(to_char(oppUnit.parents), 1, ").append(mergeOrg.getParentStr().length()).append(") = '").append(mergeOrg.getParentStr()).append("' \n");
        }
        sql.append(" and  t.parentCode=? and i.DATATIME=?\n");
        paramList.addAll(Arrays.asList(paramsVO.getOrgId(), paramsVO.getPeriodStr()));
        sql.append(" %1s\n");
        StringBuilder whereSql = this.buildEntityTableWhere(paramsVO);
        return this.selectEntity(String.format(sql.toString(), whereSql), paramList);
    }

    @Override
    public void batchDeleteEmptyRow(String unitCode, GcActionParamsVO paramsVO) {
        Set fieldDefineNames = NrTool.getAllNumberFieldDefineNamesByTableName((String)"GC_FLOATBALANCE");
        fieldDefineNames.remove("FLOATORDER");
        fieldDefineNames.remove("AMT");
        StringBuilder whereSql = this.buildEntityTableWhere(unitCode, paramsVO);
        for (String fieldDefineName : fieldDefineNames) {
            whereSql.append(" and coalesce(i.").append(fieldDefineName).append(",0)=0").append("\n");
        }
        this.execute(String.format("\tdelete from GC_FLOATBALANCE i \n  where i.MDCODE=? and i.DATATIME=?\n %1s\n", whereSql), new Object[]{unitCode, paramsVO.getPeriodStr()});
    }

    @Override
    public List<FloatBalanceEO> list(String unitCode, GcActionParamsVO paramsVO) {
        String LIST_SQL = "\tselect " + this.getAllFieldsSQL() + "\nfrom " + "GC_FLOATBALANCE" + "  i \n  where i." + "MDCODE" + "=? and i." + "DATATIME" + "=?\n %1s\n";
        StringBuilder whereSql = this.buildEntityTableWhere(unitCode, paramsVO);
        return this.selectEntity(String.format(LIST_SQL, whereSql), new Object[]{unitCode, paramsVO.getPeriodStr()});
    }

    private String getAllFieldsSQL() {
        return SqlUtils.getColumnsSqlByEntity(FloatBalanceEO.class, (String)"i");
    }

    private StringBuilder buildEntityTableWhere(GcActionParamsVO paramsVO) {
        Set entityTableNames = NrTool.getEntityTableNames((String)paramsVO.getSchemeId());
        StringBuilder whereSql = new StringBuilder(128);
        if (entityTableNames.contains("MD_GCORGTYPE")) {
            whereSql.append(" and i.").append("MD_GCORGTYPE").append("='").append(paramsVO.getOrgType()).append("'\n");
        }
        if (entityTableNames.contains("MD_CURRENCY")) {
            whereSql.append(" and i.").append("MD_CURRENCY").append("='").append(paramsVO.getCurrency()).append("'\n");
        }
        return whereSql;
    }

    private StringBuilder buildEntityTableWhere(String unitCode, GcActionParamsVO paramsVO) {
        Set entityTableNames = NrTool.getEntityTableNames((String)paramsVO.getSchemeId());
        StringBuilder whereSql = new StringBuilder(128);
        if (entityTableNames.contains("MD_CURRENCY")) {
            whereSql.append(" and i.").append("MD_CURRENCY").append("='").append(paramsVO.getCurrency()).append("'\n");
        }
        if (entityTableNames.contains("MD_GCORGTYPE")) {
            GcOrgCacheVO currentUnit = OrgUtils.getCurrentUnit(paramsVO.getOrgType(), paramsVO.getPeriodStr(), unitCode);
            whereSql.append(" and i.").append("MD_GCORGTYPE").append("='").append(currentUnit.getOrgTypeId()).append("'\n");
        }
        return whereSql;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Map<String, Object>> queryByMergeCode(boolean isFilterByOppUnit, GcActionParamsVO paramsVO, String tableName, List<String> currTableAllFieldCodes) {
        YearPeriodObject yp = new YearPeriodObject(null, paramsVO.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)paramsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        Date date = yp.formatYP().getEndDate();
        String strDate = "to_date('" + FloatBalanceDaoImpl.dateToStr(date, "yyyy-MM-dd") + "', 'yyyy-MM-dd')";
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(this.getAllFieldsSQL(currTableAllFieldCodes)).append("\n");
        sql.append(" from ").append(tableName).append("  i \n");
        sql.append(" join ").append(paramsVO.getOrgType()).append("  t on (i.MDCODE = t.code) \n");
        if (isFilterByOppUnit) {
            sql.append(" join ").append(paramsVO.getOrgType()).append("  oppUnit on (i.oppUnitCode = oppUnit.code) \n");
        }
        sql.append(" where t.validtime<" + strDate + " and t.invalidtime>=" + strDate + " \n");
        if (isFilterByOppUnit) {
            sql.append(" and  oppUnit.validtime<" + strDate + " and oppUnit.invalidtime>=" + strDate + " \n");
            GcOrgCacheVO mergeOrg = orgTool.getOrgByCode(paramsVO.getOrgId());
            sql.append(" and substr(to_char(oppUnit.parents), 1, ").append(mergeOrg.getParentStr().length()).append(") = '").append(mergeOrg.getParentStr()).append("' \n");
        }
        List gcOrgCacheVOS = GcOrgPublicTool.getInstance((String)paramsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp).listAllOrgByParentIdContainsSelf(paramsVO.getOrgId());
        List orgCodes = gcOrgCacheVOS.stream().map(GcOrgCacheVO::getCode).filter(code -> !code.equals(paramsVO.getOrgId())).collect(Collectors.toList());
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(orgCodes, (String)"t.code");
        sql.append(" and ").append(tempTableCondition.getCondition()).append(" and i.DATATIME='" + paramsVO.getPeriodStr() + "'\n");
        sql.append(" %1s\n");
        StringBuilder whereSql = this.buildEntityTableWhere(paramsVO);
        this.logger.info("\u67e5\u8be2\u4e0b\u7ea7\u8282\u70b9\u6570\u636esql\uff1a{}\uff0c \u6240\u6709\u4e0b\u7ea7\u5355\u4f4dcode\uff1a{}", (Object)String.format(sql.toString(), whereSql), (Object)orgCodes);
        try {
            List list = EntNativeSqlDefaultDao.getInstance().selectMap(String.format(sql.toString(), whereSql), new Object[0]);
            return list;
        }
        finally {
            if (!StringUtils.isEmpty((String)tempTableCondition.getTempGroupId())) {
                IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
            }
        }
    }

    public static String dateToStr(Date date, String strFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(strFormat);
        String str = sf.format(date);
        return str;
    }

    private String getAllFieldsSQL(List<String> currTableAllFieldCodes) {
        StringBuilder allFields = new StringBuilder();
        for (int i = 0; i < currTableAllFieldCodes.size(); ++i) {
            allFields.append(" i.").append(currTableAllFieldCodes.get(i));
            if (i == currTableAllFieldCodes.size() - 1) continue;
            allFields.append(",");
        }
        return allFields.toString();
    }

    @Override
    public void batchDeleteAllBalance(String unitCode, String tableCode, GcActionParamsVO paramsVO) {
        StringBuilder whereSql = this.buildEntityTableWhere(unitCode, paramsVO);
        String sql = "delete from " + tableCode + "  i \n  where i." + "MDCODE" + "=? and i." + "DATATIME" + "=?\n %1s\n";
        EntNativeSqlDefaultDao.getInstance().execute(String.format(sql, whereSql), new Object[]{unitCode, paramsVO.getPeriodStr()});
    }

    @Override
    public List<Map<String, Object>> queryDifferenceIntermediateDatas(List<String> subjectCodes, GcActionParamsVO paramsVO, String tableName) {
        YearPeriodObject yp = new YearPeriodObject(null, paramsVO.getPeriodStr());
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)paramsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        Date date = yp.formatYP().getEndDate();
        String strDate = "to_date('" + FloatBalanceDaoImpl.dateToStr(date, "yyyy-MM-dd") + "', 'yyyy-MM-dd')";
        StringBuilder sql = new StringBuilder();
        GcOrgCacheVO mergeOrg = orgCenterTool.getOrgByID(paramsVO.getOrgId());
        String mergeOrgParents = mergeOrg.getParentStr();
        int len = orgCenterTool.getOrgCodeLength();
        int mergeOrgEndIndex = mergeOrgParents.length();
        int mergeOrgChildBeginIndex = mergeOrg.getGcParentStr().length() + 2;
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        ArrayList<String> subjectDictCodes = new ArrayList<String>();
        for (String subjectCode : subjectCodes) {
            subjectDictCodes.add(subjectCode + "||" + systemId);
        }
        sql.append("select i.MDCODE,i.acctOrgCode,unit.name as unitName,i.oppunitCode,oppUnit.name as oppUnitName,i.subjectcode,i.amt,subject.orient \n");
        sql.append(" from " + tableName + "  i \n");
        sql.append(" join " + paramsVO.getOrgType() + "  unit on (i.acctOrgCode = unit.code) \n");
        sql.append(" join " + paramsVO.getOrgType() + "  oppUnit on (i.oppUnitCode = oppUnit.code) \n");
        sql.append(" join " + paramsVO.getOrgType() + "  mdOrg on (i." + "MDCODE" + " = mdOrg.code) \n");
        sql.append(" join MD_GCSUBJECT  subject on (i.subjectcode = subject.objectcode) \n");
        sql.append(" where unit.validtime<" + strDate + " and unit.invalidtime>=" + strDate + "  \n");
        sql.append(" and oppUnit.validtime<" + strDate + "  and oppUnit.invalidtime>=" + strDate + "  \n");
        sql.append(" and mdOrg.validtime<" + strDate + "  and mdOrg.invalidtime>=" + strDate + "  \n");
        sql.append(" and mdOrg.parentCode ='").append(mergeOrg.getCode()).append("' \n");
        if (paramsVO.getMergeType() == MergeTypeEnum.SUB_LEVEL) {
            List directChildren = mergeOrg.getChildren();
            List directChildrens = directChildren.stream().filter(org -> org.getOrgKind() == GcOrgKindEnum.UNIONORG).collect(Collectors.toList());
            sql.append(" and (");
            for (int i = 0; i < directChildrens.size(); ++i) {
                String mergeParents = ((GcOrgCacheVO)directChildrens.get(i)).getParentStr();
                int directChildrenParentslen = ((GcOrgCacheVO)directChildrens.get(i)).getGcParentStr().length();
                if (i != 0) {
                    sql.append(" or ");
                }
                sql.append(" (");
                sql.append("unit.parents like '" + mergeParents + "%' \n");
                sql.append(" and oppUnit.parents like '" + mergeParents + "%' \n");
                sql.append(" and substr(unit.gcparents, 1, " + (directChildrenParentslen + len + 1) + ") <> substr(oppUnit.gcparents, 1," + (directChildrenParentslen + len + 1) + ")");
                sql.append(") \n");
            }
            sql.append(") \n");
        } else if (paramsVO.getMergeType() == MergeTypeEnum.CUR_LEVEL) {
            sql.append(" and substr(to_char(unit.parents), 1,").append(mergeOrgEndIndex).append(") = '").append(mergeOrgParents).append("' \n");
            sql.append(" and substr(to_char(oppunit.parents), 1, ").append(mergeOrgEndIndex).append(") = '").append(mergeOrgParents).append("' \n");
            sql.append(" and substr(to_char(unit.gcparents),").append(mergeOrgChildBeginIndex).append(", ").append(len).append(")<> substr(to_char(oppunit.gcparents), ").append(mergeOrgChildBeginIndex).append(", ").append(len).append(") \n");
        } else {
            sql.append(" and substr(to_char(unit.parents), 1,").append(mergeOrgEndIndex).append(") = '").append(mergeOrgParents).append("' \n");
            sql.append(" and substr(to_char(oppunit.parents), 1, ").append(mergeOrgEndIndex).append(") = '").append(mergeOrgParents).append("' \n");
        }
        sql.append(" and i.DATATIME='" + paramsVO.getPeriodStr() + "'\n");
        sql.append(" and subject.systemid='" + systemId + "'\n");
        sql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(subjectDictCodes, (String)"i.subjectCode")).append(" \n");
        sql.append((CharSequence)this.buildEntityTableWhere(paramsVO));
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql.toString(), new Object[0]);
    }

    @Override
    public void batchDeleteAllBalanceByRelateSubjectCodes(String unitCode, String tableName, GcActionParamsVO paramsVO, List<String> subjectCodes) {
        StringBuilder whereSql = this.buildEntityTableWhere(unitCode, paramsVO);
        whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(subjectCodes, (String)"i.subjectCode"));
        String sql = "\tdelete from " + tableName + " i \n  where i." + "MDCODE" + "=? and i." + "DATATIME" + "=?\n %1s\n";
        EntNativeSqlDefaultDao.getInstance().execute(String.format(sql, whereSql), new Object[]{unitCode, paramsVO.getPeriodStr()});
    }

    @Override
    public void intertFloatBalanceDetail(String tableName, List<String> currTableAllFieldCodes, Map<String, Object> fields) {
        StringBuilder insertSql = new StringBuilder();
        String insertFieldColumns = this.appendDimColumnInsertSql(currTableAllFieldCodes);
        insertSql.append("insert into ").append(tableName.toUpperCase()).append("(").append(insertFieldColumns).append(")");
        insertSql.append(" values(");
        for (int i = 0; i < currTableAllFieldCodes.size(); ++i) {
            insertSql.append("?");
            if (i == currTableAllFieldCodes.size() - 1) continue;
            insertSql.append(",");
        }
        insertSql.append(")");
        EntNativeSqlDefaultDao.getInstance().execute(insertSql.toString(), this.appendInsertFieldColumnValues(currTableAllFieldCodes, fields));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Map<String, Object>> querySumFieldByMergeCode(GcActionParamsVO paramsVO, String tableName, List<String> curSubjectRewriteFields, List<String> summaryColumnCodes) {
        YearPeriodObject yp = new YearPeriodObject(null, paramsVO.getPeriodStr());
        Date date = yp.formatYP().getEndDate();
        String strDate = "to_date('" + FloatBalanceDaoImpl.dateToStr(date, "yyyy-MM-dd") + "', 'yyyy-MM-dd')";
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(this.getAllFieldsSQL(summaryColumnCodes)).append(",").append(this.getAllSumFieldsSQL(curSubjectRewriteFields)).append("\n");
        sql.append(" from ").append(tableName).append("  i \n");
        sql.append(" join ").append(paramsVO.getOrgType()).append("  t on (i.MDCODE = t.code) \n");
        sql.append(" where t.validtime<" + strDate + " and t.invalidtime>=" + strDate + " \n");
        List gcOrgCacheVOS = GcOrgPublicTool.getInstance((String)paramsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp).listAllOrgByParentIdContainsSelf(paramsVO.getOrgId());
        List orgCodes = gcOrgCacheVOS.stream().map(GcOrgCacheVO::getCode).filter(code -> !code.equals(paramsVO.getOrgId())).collect(Collectors.toList());
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(orgCodes, (String)"t.code");
        sql.append(" and ").append(tempTableCondition.getCondition()).append(" and i.DATATIME='" + paramsVO.getPeriodStr() + "'\n");
        sql.append(" %1s\n");
        StringBuilder whereSql = this.buildEntityTableWhere(paramsVO);
        whereSql.append("group By ").append(this.getAllFieldsSQL(summaryColumnCodes));
        this.logger.info("\u67e5\u8be2\u4e0b\u7ea7\u8282\u70b9\u6570\u636esql\uff1a{}\uff0c \u6240\u6709\u4e0b\u7ea7\u5355\u4f4dcode\uff1a{}", (Object)String.format(sql.toString(), whereSql), (Object)orgCodes);
        try {
            List list = EntNativeSqlDefaultDao.getInstance().selectMap(String.format(sql.toString(), whereSql), new Object[0]);
            return list;
        }
        finally {
            if (!StringUtils.isEmpty((String)tempTableCondition.getTempGroupId())) {
                IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
            }
        }
    }

    @Override
    public List<Map<String, Object>> queryOneOtherFieldsData(GcActionParamsVO paramsVO, String tableName, List<String> currTableAllFieldCodes) {
        YearPeriodObject yp = new YearPeriodObject(null, paramsVO.getPeriodStr());
        Date date = yp.formatYP().getEndDate();
        String strDate = "to_date('" + FloatBalanceDaoImpl.dateToStr(date, "yyyy-MM-dd") + "', 'yyyy-MM-dd')";
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(this.getAllFieldsSQL(currTableAllFieldCodes)).append("\n");
        sql.append(" from ").append(tableName).append("  i \n");
        sql.append(" join ").append(paramsVO.getOrgType()).append("  t on (i.MDCODE = t.code) \n");
        sql.append(" where t.validtime<" + strDate + " and t.invalidtime>=" + strDate + " \n");
        List gcOrgCacheVOS = GcOrgPublicTool.getInstance((String)paramsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp).listAllOrgByParentIdContainsSelf(paramsVO.getOrgId());
        List orgCodes = gcOrgCacheVOS.stream().map(GcOrgCacheVO::getCode).filter(code -> !code.equals(paramsVO.getOrgId())).collect(Collectors.toList());
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(orgCodes, (String)"t.code");
        sql.append(" and ").append(tempTableCondition.getCondition()).append(" and i.DATATIME='" + paramsVO.getPeriodStr() + "'\n");
        StringBuilder whereSql = this.buildEntityTableWhere(paramsVO);
        return EntNativeSqlDefaultDao.getInstance().selectMap(String.format(sql.toString(), whereSql), new Object[0]);
    }

    private String getAllSumFieldsSQL(List<String> curSubjectRewriteFields) {
        StringBuilder allFields = new StringBuilder();
        for (int i = 0; i < curSubjectRewriteFields.size(); ++i) {
            allFields.append(" sum(i.").append(curSubjectRewriteFields.get(i)).append(") as ").append(curSubjectRewriteFields.get(i));
            if (i == curSubjectRewriteFields.size() - 1) continue;
            allFields.append(",");
        }
        return allFields.toString();
    }

    private String appendDimColumnInsertSql(List<String> currTableAllFieldCodes) {
        StringBuilder insertFieldColumns = new StringBuilder();
        for (int i = 0; i < currTableAllFieldCodes.size(); ++i) {
            insertFieldColumns.append(currTableAllFieldCodes.get(i));
            if (i == currTableAllFieldCodes.size() - 1) continue;
            insertFieldColumns.append(",");
        }
        return insertFieldColumns.toString();
    }

    private Object[] appendInsertFieldColumnValues(List<String> currTableAllFieldCodes, Map<String, Object> fields) {
        Object[] insertFieldColumnValues = new Object[currTableAllFieldCodes.size()];
        for (int i = 0; i < currTableAllFieldCodes.size(); ++i) {
            insertFieldColumnValues[i] = fields.get(currTableAllFieldCodes.get(i));
        }
        return insertFieldColumnValues;
    }
}

