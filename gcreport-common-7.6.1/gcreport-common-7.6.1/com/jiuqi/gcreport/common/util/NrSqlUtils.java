/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.GCAdjTypeEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.ReflectionUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class NrSqlUtils {
    private static final Logger log = LoggerFactory.getLogger(NrSqlUtils.class);
    private static final String INSERT_SQL = "insert into %1s(%2s) values(%3s)";
    private static final String UPDATE_SQL = "update %1s set %2s where MDCODE=? and DATATIME=? %3s";
    private static final String SELECT_SQL = "select %1s from %2s t where MDCODE=? and DATATIME=? %3s";

    public static StringBuilder buildEntityTableWhere(Object condition) {
        return NrSqlUtils.buildEntityTableWhere(condition, null);
    }

    public static StringBuilder buildEntityTableWhere(Object condition, String alias) {
        if (null == alias) {
            alias = "";
        }
        String schemeId = (String)ReflectionUtils.getFieldValue(condition, "schemeId");
        Set<String> entityTableNames = NrSqlUtils.getDimTableNames(schemeId);
        StringBuilder whereSql = new StringBuilder(128);
        if (entityTableNames.contains("MD_CURRENCY")) {
            String currency = (String)ReflectionUtils.getFieldValue(condition, "currency");
            whereSql.append(" and ").append(alias).append("MD_CURRENCY").append("='").append(currency).append("'\n");
        }
        if (entityTableNames.contains("MD_GCORGTYPE")) {
            String orgType = (String)ReflectionUtils.getFieldValue(condition, "orgType");
            whereSql.append(" and ").append(alias).append("MD_GCORGTYPE").append("='").append(orgType).append("'\n");
        }
        return whereSql;
    }

    private static StringBuilder buildEntityTableInsert(Object condition, StringBuffer fieldPart, StringBuffer valuePart, List params) {
        String taskId;
        fieldPart.append("MDCODE,DATATIME,");
        valuePart.append("?,?,");
        String orgId = (String)ReflectionUtils.getFieldValue(condition, "orgId");
        String periodStr = (String)ReflectionUtils.getFieldValue(condition, "periodStr");
        params.add(orgId);
        params.add(periodStr);
        String schemeId = (String)ReflectionUtils.getFieldValue(condition, "schemeId");
        Set<String> entityTableNames = NrSqlUtils.getDimTableNames(schemeId);
        StringBuilder whereSql = new StringBuilder(128);
        if (entityTableNames.contains("MD_CURRENCY")) {
            fieldPart.append("MD_CURRENCY").append(",");
            String currency = (String)ReflectionUtils.getFieldValue(condition, "currency");
            valuePart.append("'").append(currency).append("',");
        }
        if (entityTableNames.contains("MD_GCORGTYPE")) {
            fieldPart.append("MD_GCORGTYPE").append(",");
            String orgType = (String)ReflectionUtils.getFieldValue(condition, "orgType");
            valuePart.append("'").append(orgType).append("',");
        }
        if (DimensionUtils.isExisAdjType(taskId = (String)ReflectionUtils.getFieldValue(condition, "taskId"))) {
            fieldPart.append("MD_GCADJTYPE").append(",");
            valuePart.append("'").append(GCAdjTypeEnum.BEFOREADJ.getCode()).append("',");
        }
        return whereSql;
    }

    public static void merge(Object condition, String tableName, Map<String, Double> fieldCode2OffsetValueMap) {
        int updateCount = NrSqlUtils.update(condition, tableName, fieldCode2OffsetValueMap);
        if (updateCount > 0) {
            return;
        }
        NrSqlUtils.insert(condition, tableName, fieldCode2OffsetValueMap);
    }

    public static int insert(Object condition, String tableName, Map<String, Double> fieldCode2AmtMap) {
        StringBuffer fieldPart = new StringBuffer(128);
        StringBuffer valuePart = new StringBuffer(128);
        ArrayList params = new ArrayList(16);
        NrSqlUtils.buildEntityTableInsert(condition, fieldPart, valuePart, params);
        Set<String> columnCodeSet = NrSqlUtils.getColumnCodes(tableName);
        for (Map.Entry<String, Double> fieldCode2AmtEntry : fieldCode2AmtMap.entrySet()) {
            if (!columnCodeSet.contains(fieldCode2AmtEntry.getKey())) continue;
            fieldPart.append((Object)fieldCode2AmtEntry.getKey()).append(",");
            valuePart.append(fieldCode2AmtEntry.getValue()).append(",");
        }
        if (fieldPart.length() == 0) {
            return 0;
        }
        if (DimensionUtils.isExistAdjust(((Condition)condition).getTaskId())) {
            fieldPart.append("ADJUST").append(",");
            valuePart.append("0").append(",");
        }
        fieldPart.setLength(fieldPart.length() - 1);
        valuePart.setLength(valuePart.length() - 1);
        String sql = String.format(INSERT_SQL, tableName, fieldPart, valuePart);
        log.debug(sql);
        return EntNativeSqlDefaultDao.getInstance().execute(sql, params);
    }

    public static int update(Object condition, String tableName, Map<String, Double> fieldCode2AmtMap) {
        StringBuilder where = NrSqlUtils.buildEntityTableWhere(condition);
        StringBuffer setPart = new StringBuffer(128);
        Set<String> columnCodeSet = NrSqlUtils.getColumnCodes(tableName);
        for (Map.Entry<String, Double> fieldCode2AmtEntry : fieldCode2AmtMap.entrySet()) {
            if (!columnCodeSet.contains(fieldCode2AmtEntry.getKey())) continue;
            setPart.append((Object)fieldCode2AmtEntry.getKey()).append("=").append(fieldCode2AmtEntry.getValue()).append(",");
        }
        if (setPart.length() == 0) {
            return 0;
        }
        setPart.setLength(setPart.length() - 1);
        String orgId = (String)ReflectionUtils.getFieldValue(condition, "orgId");
        String periodStr = (String)ReflectionUtils.getFieldValue(condition, "periodStr");
        String sql = String.format(UPDATE_SQL, tableName, setPart, where);
        log.debug(sql);
        return EntNativeSqlDefaultDao.getInstance().execute(sql, Arrays.asList(orgId, periodStr));
    }

    public static List<Map<String, Object>> select(Object condition, String tableName) {
        String SELECT_SQL = SELECT_SQL;
        String columnStr = NrSqlUtils.getColumnsSqlByTableDefineNr(tableName, "t");
        StringBuilder where = NrSqlUtils.buildEntityTableWhere(condition);
        String orgId = (String)ReflectionUtils.getFieldValue(condition, "orgId");
        String periodStr = (String)ReflectionUtils.getFieldValue(condition, "periodStr");
        String sql = String.format(SELECT_SQL, columnStr, tableName, where);
        log.debug(sql);
        try {
            return EntNativeSqlDefaultDao.getInstance().selectMap(sql, Arrays.asList(orgId, periodStr));
        }
        catch (Exception e) {
            log.error(sql);
            throw e;
        }
    }

    public static List<Map<String, Object>> select(Object condition, String tableName, Collection<String> orgCodes) {
        if (CollectionUtils.isEmpty(orgCodes)) {
            return Collections.EMPTY_LIST;
        }
        if (orgCodes.size() <= 1) {
            ReflectionUtils.setFieldValue(condition, "orgId", orgCodes.iterator().next());
            return NrSqlUtils.select(condition, tableName);
        }
        String orgSqlStr = SqlUtils.getConditionOfIdsUseOr(orgCodes, (String)"t.MDCODE");
        String SELECT_SQL = "select %1s from %2s t where " + orgSqlStr + " and " + "DATATIME" + "=? %3s";
        String columnStr = NrSqlUtils.getColumnsSqlByTableDefineNr(tableName, "t");
        StringBuilder where = NrSqlUtils.buildEntityTableWhere(condition);
        String periodStr = (String)ReflectionUtils.getFieldValue(condition, "periodStr");
        String sql = String.format(SELECT_SQL, columnStr, tableName, where);
        log.debug(sql);
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, Arrays.asList(periodStr));
    }

    private static Set<String> getDimTableNames(String formSchemeId) {
        try {
            HashSet<String> result = new HashSet<String>(8);
            IRunTimeViewController runtimeController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            IEntityMetaService entityMetaService = (IEntityMetaService)SpringContextUtils.getBean(IEntityMetaService.class);
            FormSchemeDefine schemeDefine = runtimeController.getFormScheme(formSchemeId);
            for (String entityId : schemeDefine.getDims().split(";")) {
                TableModelDefine tableDefine = entityMetaService.getTableModel(entityId);
                result.add(tableDefine.getName());
            }
            return result;
        }
        catch (Exception e) {
            log.error(GcI18nUtil.getMessage((String)"gc.common.util.nrsql.entityerror"), e);
            return Collections.emptySet();
        }
    }

    public static String getColumnsSqlByTableDefineNr(String tableName, String alias) {
        try {
            Set<String> fields = NrSqlUtils.getColumnCodes(tableName);
            StringBuilder buf = new StringBuilder();
            String tableAlias = StringUtils.isEmpty((String)alias) ? tableName : alias;
            for (String code : fields) {
                buf.append("\n ").append(tableAlias).append(".").append(code).append(" as ").append(code).append(",");
            }
            buf.setLength(buf.length() - 1);
            return buf.toString();
        }
        catch (Exception e) {
            Object[] i18Args = new String[]{tableName};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.common.util.nrsql.columnerror", (Object[])i18Args), (Throwable)e);
        }
    }

    public static Set<String> getColumnCodes(String tableName) {
        try {
            DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
            TableModelDefine tableDefine = dataModelService.getTableModelDefineByName(tableName);
            List fields = dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
            HashSet<String> columnCodeSet = new HashSet<String>();
            for (ColumnModelDefine f : fields) {
                columnCodeSet.add(f.getName());
            }
            return columnCodeSet;
        }
        catch (Exception e) {
            Object[] i18Args = new String[]{tableName};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.common.util.nrsql.columnerror", (Object[])i18Args), (Throwable)e);
        }
    }

    public static Condition getCondition(Object originCondition, String orgId, String orgTypeId) {
        Condition condition = NrSqlUtils.getCondition(originCondition, orgId);
        condition.setOrgType(orgTypeId);
        return condition;
    }

    public static Condition getCondition(Object originCondition, String orgId) {
        Condition condition = new Condition();
        BeanUtils.copyProperties(originCondition, condition);
        condition.setOrgId(orgId);
        return condition;
    }

    public static class Condition {
        private String taskId;
        private String schemeId;
        private String currency;
        private String periodStr;
        private String orgType;
        private String adjTypeId;
        private String orgId;

        public String getTaskId() {
            return this.taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getSchemeId() {
            return this.schemeId;
        }

        public void setSchemeId(String schemeId) {
            this.schemeId = schemeId;
        }

        public String getCurrency() {
            return this.currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPeriodStr() {
            return this.periodStr;
        }

        public void setPeriodStr(String periodStr) {
            this.periodStr = periodStr;
        }

        public String getOrgType() {
            return this.orgType;
        }

        public void setOrgType(String orgType) {
            this.orgType = orgType;
        }

        public String getAdjTypeId() {
            return this.adjTypeId;
        }

        public void setAdjTypeId(String adjTypeId) {
            this.adjTypeId = adjTypeId;
        }

        public String getOrgId() {
            return this.orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }
    }
}

