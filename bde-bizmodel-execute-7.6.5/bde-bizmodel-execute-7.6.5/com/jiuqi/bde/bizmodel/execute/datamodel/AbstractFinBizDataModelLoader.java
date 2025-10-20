/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO
 *  com.jiuqi.bde.bizmodel.define.IBizDataModelLoader
 *  com.jiuqi.bde.bizmodel.define.config.BdeBizModelConfig
 *  com.jiuqi.bde.bizmodel.impl.model.service.impl.FinBizModelManageServiceImpl
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.constant.DataBaseTypeEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.MatchRuleEnum
 *  com.jiuqi.bde.common.dto.DimensionValue
 *  com.jiuqi.bde.common.dto.SimpleComposeDateDTO
 *  com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO
 *  com.jiuqi.bde.common.intf.ConditionMatchRule
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.common.util.LogUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringSetResultSetExtractor
 *  com.jiuqi.dc.base.common.jdbc.service.SqlRecordService
 *  com.jiuqi.dc.base.common.utils.SqlUtil
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  org.apache.commons.collections.MapUtils
 *  org.apache.shiro.util.ThreadContext
 *  org.springframework.jdbc.BadSqlGrammarException
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.bizmodel.execute.datamodel;

import com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO;
import com.jiuqi.bde.bizmodel.define.IBizDataModelLoader;
import com.jiuqi.bde.bizmodel.define.config.BdeBizModelConfig;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataAssQueryCondi;
import com.jiuqi.bde.bizmodel.execute.intf.FetchFieldAndWhereSql;
import com.jiuqi.bde.bizmodel.impl.model.service.impl.FinBizModelManageServiceImpl;
import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.constant.DataBaseTypeEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.MatchRuleEnum;
import com.jiuqi.bde.common.dto.DimensionValue;
import com.jiuqi.bde.common.dto.SimpleComposeDateDTO;
import com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO;
import com.jiuqi.bde.common.intf.ConditionMatchRule;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.common.util.LogUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.jdbc.extractor.StringSetResultSetExtractor;
import com.jiuqi.dc.base.common.jdbc.service.SqlRecordService;
import com.jiuqi.dc.base.common.utils.SqlUtil;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public abstract class AbstractFinBizDataModelLoader<C extends BalanceCondition, R>
implements IBizDataModelLoader<C, R> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private SqlRecordService sqlRecordService;
    private static final String DELETE_TEMP_TABLE_SQL = "DELETE FROM %1$s ";
    private static final String INSERT_TEMP_TABLE_SQL = "INSERT INTO %1$s(CODE) VALUES(?)";

    public FetchFloatRowResult simpleFloatQuery(BalanceCondition balanceCondition, SimpleComposeDateDTO simpleComposeDateDTO) {
        return null;
    }

    public FetchFloatRowResult seniorFloatQuery(BalanceCondition condi, String seniorSql) {
        return null;
    }

    public void writeLog(String msg) {
        this.logger.info(msg);
    }

    public void writeLog(String format, Object ... arguments) {
        this.logger.info(format, arguments);
    }

    public void writeLog(String msg, Throwable t) {
        this.logger.info(msg, t);
    }

    protected <T> List<T> query(String dataSourceCode, String sql, Object[] params, RowMapper<T> rowMapper) {
        Assert.isNotEmpty((String)sql);
        Assert.isNotNull(rowMapper);
        List rs = this.recordSql(sql, JsonUtils.writeValueAsString((Object)params), () -> {
            try {
                return this.dataSourceService.query(dataSourceCode, sql, params, rowMapper);
            }
            catch (BadSqlGrammarException e) {
                throw new RuntimeException(e.getCause().getMessage(), e);
            }
        });
        return rs;
    }

    protected <T> T query(String dataSourceCode, String sql, Object[] params, ResultSetExtractor<T> rse) {
        Assert.isNotEmpty((String)sql);
        Assert.isNotNull(rse);
        Object rs = this.recordSql(sql, JsonUtils.writeValueAsString((Object)params), () -> {
            try {
                return this.dataSourceService.query(dataSourceCode, sql, params, rse);
            }
            catch (BadSqlGrammarException e) {
                throw new RuntimeException(e.getCause().getMessage(), e);
            }
        });
        return (T)rs;
    }

    protected <T> T recordSql(String sql, String argsJson, Supplier<T> func) {
        String sqlLogId = UUIDUtils.newHalfGUIDStr();
        Date startTime = new Date();
        try {
            if (!Objects.isNull(ThreadContext.get((Object)"SQLLOGID_KEY"))) {
                String taskLogId = String.valueOf(ThreadContext.get((Object)"SQLLOGID_KEY"));
                this.sqlRecordService.recordSql(sqlLogId, taskLogId, sql, argsJson, startTime);
            }
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u6267\u884cSQL\u65e5\u5fd7\u51fa\u73b0\u5f02\u5e38", e);
        }
        T rs = func.get();
        Date endTime = new Date();
        try {
            if (!Objects.isNull(sqlLogId)) {
                this.sqlRecordService.recordEndTime(sqlLogId, endTime);
            }
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u6267\u884cSQL\u7ed3\u675f\u65f6\u95f4\u51fa\u73b0\u5f02\u5e38", e);
        }
        return rs;
    }

    protected void buildTempTable(BalanceCondition condi) {
        if (!condi.isEnableDirectFilter()) {
            return;
        }
        if (CollectionUtils.isEmpty((Collection)condi.getConditionMatchRule().getSubjectCodes()) || condi.getConditionMatchRule().getMatchRule() == MatchRuleEnum.RANGE || !condi.isEnableTransTable()) {
            return;
        }
        String requestTaskId = condi.getRequestTaskId();
        String dataSourceCode = condi.getOrgMapping().getDataSourceCode();
        ConditionMatchRule conditionMatchRule = condi.getConditionMatchRule();
        StringBuffer log = new StringBuffer();
        String tableName = conditionMatchRule.getTbName();
        String createSql = this.buildCreateTempTableSql(dataSourceCode, tableName);
        if (!StringUtils.isEmpty((String)createSql)) {
            log.append("-- \u521b\u5efa\u4e34\u65f6\u8868\uff1a").append(BdeLogUtil.NEXT_LINE);
            log.append(createSql);
            this.dataSourceService.execute(dataSourceCode, createSql);
        }
        log.append("-- \u6e05\u9664\u4e34\u65f6\u8868\u8bb0\u5f55\uff1a").append(BdeLogUtil.NEXT_LINE);
        log.append(String.format(DELETE_TEMP_TABLE_SQL, tableName)).append(";").append(BdeLogUtil.NEXT_LINE);
        this.dataSourceService.execute(dataSourceCode, String.format(DELETE_TEMP_TABLE_SQL, tableName));
        log.append("-- \u63d2\u5165\u4e34\u65f6\u8868\u8bb0\u5f55\uff1a").append(BdeLogUtil.NEXT_LINE);
        log.append("-- \u63d2\u5165SQL\uff1a").append(BdeLogUtil.NEXT_LINE).append(String.format(INSERT_TEMP_TABLE_SQL, tableName)).append(";").append(BdeLogUtil.NEXT_LINE);
        log.append("-- \u63d2\u5165\u53c2\u6570\uff1a").append(conditionMatchRule.getSubjectCodes()).append(BdeLogUtil.NEXT_LINE);
        this.dataSourceService.batchUpdate(dataSourceCode, String.format(INSERT_TEMP_TABLE_SQL, tableName), AbstractFinBizDataModelLoader.buildBatchArgs(conditionMatchRule.getSubjectCodes()));
        if (BdeLogUtil.isDebug()) {
            try {
                Set result = (Set)this.dataSourceService.query(dataSourceCode, "SELECT * FROM BDE_TEMP_MAINCODE", null, (ResultSetExtractor)new StringSetResultSetExtractor());
                log.append("-- \u4e34\u65f6\u8868\u7ed3\u679c\uff1a").append(result).append(BdeLogUtil.NEXT_LINE);
            }
            catch (Exception e) {
                log.append("\u4e34\u65f6\u8868\u6570\u636e\u9a8c\u8bc1\u51fa\u73b0\u9519\u8bef").append(LogUtil.getExceptionStackStr((Throwable)e));
            }
        }
        BdeLogUtil.recordLog((String)requestTaskId, (String)"\u521d\u59cb\u4e34\u65f6\u8868\u6570\u636e", (Object)new Object[0], (String)log.toString());
    }

    protected void buildTempTable(String requestTaskId, String dataSourceCode, String tbName, Collection<String> values) {
        if (CollectionUtils.isEmpty(values)) {
            return;
        }
        StringBuffer log = new StringBuffer();
        String createSql = this.buildCreateTempTableSql(dataSourceCode, tbName);
        if (!StringUtils.isEmpty((String)createSql)) {
            log.append("-- \u521b\u5efa\u4e34\u65f6\u8868\uff1a").append(BdeLogUtil.NEXT_LINE);
            log.append(createSql);
            this.dataSourceService.execute(dataSourceCode, createSql);
        }
        String DELETE_TEMP_TABLE_SQL = "DELETE FROM " + tbName;
        String INSERT_TEMP_TABLE_SQL = "INSERT INTO " + tbName + "(CODE) VALUES(?)";
        log.append("-- \u6e05\u9664\u4e34\u65f6\u8868\u8bb0\u5f55\uff1a").append(BdeLogUtil.NEXT_LINE);
        log.append(DELETE_TEMP_TABLE_SQL).append(";").append(BdeLogUtil.NEXT_LINE);
        this.dataSourceService.execute(dataSourceCode, DELETE_TEMP_TABLE_SQL);
        log.append("-- \u63d2\u5165\u4e34\u65f6\u8868\u8bb0\u5f55\uff1a").append(BdeLogUtil.NEXT_LINE);
        log.append("-- \u63d2\u5165SQL\uff1a").append(BdeLogUtil.NEXT_LINE).append(INSERT_TEMP_TABLE_SQL).append(";").append(BdeLogUtil.NEXT_LINE);
        log.append("-- \u63d2\u5165\u53c2\u6570\uff1a").append(values).append(BdeLogUtil.NEXT_LINE);
        this.dataSourceService.batchUpdate(dataSourceCode, INSERT_TEMP_TABLE_SQL, AbstractFinBizDataModelLoader.buildBatchArgs(values));
        if (BdeLogUtil.isDebug()) {
            try {
                Set result = (Set)this.dataSourceService.query(dataSourceCode, "SELECT * FROM " + tbName, null, (ResultSetExtractor)new StringSetResultSetExtractor());
                log.append("-- \u4e34\u65f6\u8868\u7ed3\u679c\uff1a").append(result).append(BdeLogUtil.NEXT_LINE);
            }
            catch (Exception e) {
                log.append("\u4e34\u65f6\u8868\u6570\u636e\u9a8c\u8bc1\u51fa\u73b0\u9519\u8bef").append(LogUtil.getExceptionStackStr((Throwable)e));
            }
        }
        BdeLogUtil.recordLog((String)requestTaskId, (String)"\u521d\u59cb\u4e34\u65f6\u8868\u6570\u636e", (Object)new Object[0], (String)log.toString());
    }

    public static List<Object[]> buildBatchArgs(Collection<String> values) {
        if (CollectionUtils.isEmpty(values)) {
            return CollectionUtils.newArrayList();
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(values.size());
        for (String value : values) {
            batchArgs.add(new Object[]{value});
        }
        return batchArgs;
    }

    private String buildCreateTempTableSql(String dataSourceCode, String tempTableName) {
        String dbType = this.dataSourceService.getDbType(dataSourceCode);
        DataBaseTypeEnum dataBaseTypeEnum = DataBaseTypeEnum.getDataBaseTypeEnumBydbType((String)dbType);
        if (dataBaseTypeEnum == null) {
            return "";
        }
        String createSql = "";
        switch (dataBaseTypeEnum) {
            case MYSQL: 
            case POSTGRESQL: 
            case GAUSS: 
            case POLARDB: 
            case UXDB: 
            case KINGBASE: 
            case HIGHGO: {
                createSql = String.format(" create temporary table if not exists %1$s ( Code varchar(60));", tempTableName);
                break;
            }
            case SQL_SERVER: {
                createSql = String.format("IF Object_id('tempdb.." + tempTableName + "') is null\nBegin\n\tCREATE TABLE " + tempTableName + " (CODE varchar(60) COLLATE %1$s); End;Truncate Table " + tempTableName + ";", BdeBizModelConfig.getSqlServerCollation());
                break;
            }
        }
        return createSql;
    }

    protected FetchDataAssQueryCondi buildSimpleFloatQueryCondi(SimpleComposeDateDTO simpleComposeDateDTO) {
        ArrayList<String> sumFields = new ArrayList<String>();
        ArrayList<String> groupFields = new ArrayList<String>();
        SimpleCustomComposePluginDataVO simpleCustomComposePluginDataVO = simpleComposeDateDTO.getSimpleCustomComposePluginDataVO();
        FinBizModelDTO bizModel = ((FinBizModelManageServiceImpl)SpringContextUtils.getBean(FinBizModelManageServiceImpl.class)).getByCode(simpleComposeDateDTO.getSimpleCustomComposePluginDataVO().getFetchSourceCode());
        HashMap<String, ColumnTypeEnum> floatColumnsType = new HashMap<String, ColumnTypeEnum>();
        for (String name : simpleComposeDateDTO.getUsedFields()) {
            if (bizModel.getFetchTypes().contains(name)) {
                sumFields.add(name);
                floatColumnsType.put(name, ColumnTypeEnum.NUMBER);
                continue;
            }
            groupFields.add(name);
            floatColumnsType.put(name, ColumnTypeEnum.STRING);
        }
        ArrayList<Dimension> whereFields = new ArrayList<Dimension>(simpleCustomComposePluginDataVO.getDimensionMapping());
        FetchDataAssQueryCondi fetchDataAssQueryCondi = new FetchDataAssQueryCondi(sumFields, groupFields, whereFields);
        fetchDataAssQueryCondi.setFloatColumnsType(floatColumnsType);
        fetchDataAssQueryCondi.setFetchTypesFields(bizModel.getFetchTypes().stream().filter(item -> !FetchTypeEnum.ZSC.getCode().equals(item)).collect(Collectors.toList()));
        return fetchDataAssQueryCondi;
    }

    protected FetchFieldAndWhereSql buildCacheFetchFieldAndWhereSql(BalanceCondition condi, FetchDataAssQueryCondi queryCondi) {
        Set dimensions = ((DimensionService)SpringContextUtils.getBean(DimensionService.class)).loadAllDimensions().stream().map(DimensionVO::getCode).collect(Collectors.toSet());
        ArrayList<String> selectFieldSql = new ArrayList<String>(queryCondi.getSumFields().size() + queryCondi.getGroupFields().size());
        ArrayList<String> groupFieldSql = new ArrayList<String>(queryCondi.getGroupFields().size());
        ArrayList<String> whereFieldSql = new ArrayList<String>(queryCondi.getWhereFields().size());
        selectFieldSql.addAll(queryCondi.getSumFields().stream().map(item -> "T." + item).collect(Collectors.toList()));
        selectFieldSql.addAll(queryCondi.getGroupFields().stream().map(item -> "T." + item).collect(Collectors.toList()));
        for (String groupField : queryCondi.getGroupFields()) {
            groupFieldSql.add(String.format(" FLOATRESULT.%s", groupField));
            if (!dimensions.contains(groupField)) continue;
            whereFieldSql.add(String.format("AND FLOATRESULT.%1$s IS NOT NULL", groupField));
        }
        for (Dimension whereField : queryCondi.getWhereFields()) {
            if (!StringUtils.isEmpty((String)whereField.getDimValue())) {
                whereFieldSql.add(String.format(this.buildWhereSql(whereField.getDimValue(), whereField.getDimCode()), whereField.getDimCode()));
            }
            if (StringUtils.isEmpty((String)whereField.getExcludeValue())) continue;
            whereFieldSql.add(String.format(this.buildExcludeSql(whereField.getExcludeValue(), whereField.getDimCode()), whereField.getDimCode()));
        }
        FetchFieldAndWhereSql fetchFieldAndWhereSql = new FetchFieldAndWhereSql(selectFieldSql, groupFieldSql, whereFieldSql);
        fetchFieldAndWhereSql.setFetchDataAssQueryCondi(queryCondi);
        fetchFieldAndWhereSql.setFetchTypes(queryCondi.getFetchTypesFields());
        return fetchFieldAndWhereSql;
    }

    protected FetchFieldAndWhereSql buildFetchFieldAndWhereSql(BalanceCondition condi, FetchDataAssQueryCondi queryCondi, List<Dimension> dimensions) {
        ArrayList<String> selectFieldSql = new ArrayList<String>(queryCondi.getSumFields().size() + queryCondi.getGroupFields().size());
        ArrayList<String> groupFieldSql = new ArrayList<String>(queryCondi.getGroupFields().size());
        ArrayList<String> whereFieldSql = new ArrayList<String>(queryCondi.getWhereFields().size());
        selectFieldSql.addAll(queryCondi.getSumFields());
        selectFieldSql.addAll(queryCondi.getGroupFields());
        groupFieldSql.addAll(queryCondi.getGroupFields());
        for (Dimension whereField : queryCondi.getWhereFields()) {
            if (!StringUtils.isEmpty((String)whereField.getDimValue())) {
                whereFieldSql.add(String.format(this.buildWhereSql(whereField.getDimValue(), whereField.getDimCode()), whereField.getDimCode()));
            }
            if (StringUtils.isEmpty((String)whereField.getExcludeValue())) continue;
            whereFieldSql.add(String.format(this.buildExcludeSql(whereField.getExcludeValue(), whereField.getDimCode()), whereField.getDimCode()));
        }
        FetchFieldAndWhereSql fetchFieldAndWhereSql = new FetchFieldAndWhereSql(selectFieldSql, groupFieldSql, whereFieldSql);
        fetchFieldAndWhereSql.setFetchTypes(queryCondi.getFetchTypesFields());
        return fetchFieldAndWhereSql;
    }

    protected String buildWhereSql(String whereValue, String dimCode) {
        if (whereValue.contains(",")) {
            String[] dimValues = whereValue.split(",");
            ArrayList<String> likeSqlList = new ArrayList<String>();
            for (String likeSql : dimValues) {
                likeSqlList.add(" FLOATRESULT.%1$s LIKE '" + likeSql + "%%' ");
            }
            return SqlUtil.concatCondi(likeSqlList, (boolean)false);
        }
        if (whereValue.contains(":")) {
            String[] values = whereValue.split(":");
            return " AND FLOATRESULT.%1$s >='" + values[0] + "' AND FLOATRESULT.%1$s <='" + values[1] + "ZZ' ";
        }
        return " AND FLOATRESULT.%1$s LIKE '" + whereValue + "%%' ";
    }

    protected String buildExcludeSql(String excludeValue, String excludeDimCode) {
        if (excludeValue.contains(",")) {
            String[] dimValues = excludeValue.split(",");
            StringBuilder stringBuilder = new StringBuilder();
            for (String value : dimValues) {
                stringBuilder.append(" AND FLOATRESULT.%1$s NOT LIKE '" + value + "%%' ");
            }
            return stringBuilder.toString();
        }
        if (excludeValue.contains(":")) {
            String[] values = excludeValue.split(":");
            return " AND ( FLOATRESULT.%1$s <'" + values[0] + "' OR FLOATRESULT.%1$s >'" + values[1] + "ZZ' )";
        }
        return " AND FLOATRESULT.%1$s NOT LIKE '" + excludeValue + "%%' ";
    }

    protected String buildCleanZeroSql(FetchFieldAndWhereSql fetchFiledAndWhereSql) {
        StringBuilder cleanZeroSql = new StringBuilder();
        if (fetchFiledAndWhereSql.isCleanZeroRecords() && !fetchFiledAndWhereSql.getGroupFieldSql().isEmpty()) {
            cleanZeroSql.append(" HAVING ( 1<>1");
            for (String fetchType : fetchFiledAndWhereSql.getFetchTypes()) {
                cleanZeroSql.append(String.format(" OR SUM(FLOATRESULT.%1$s) <>0", fetchType));
            }
            cleanZeroSql.append(")");
            return cleanZeroSql.toString();
        }
        if (fetchFiledAndWhereSql.isCleanZeroRecords() && fetchFiledAndWhereSql.getGroupFieldSql().isEmpty()) {
            cleanZeroSql.append(" AND ( 1<>1");
            for (String fetchType : fetchFiledAndWhereSql.getFetchTypes()) {
                cleanZeroSql.append(String.format(" OR FLOATRESULT.%1$s <>0", fetchType));
            }
            cleanZeroSql.append(")");
            return cleanZeroSql.toString();
        }
        return "";
    }

    protected boolean fetchTypeCalcOrient(String fetchType) {
        return FetchTypeEnum.NC.getCode().equals(fetchType) || FetchTypeEnum.WNC.getCode().equals(fetchType) || FetchTypeEnum.C.getCode().equals(fetchType) || FetchTypeEnum.WC.getCode().equals(fetchType) || FetchTypeEnum.YE.getCode().equals(fetchType) || FetchTypeEnum.WYE.getCode().equals(fetchType);
    }

    public String getOrgVersionCode(C balanceCondition) {
        Map<String, DimensionValue> dimensionValueMap = ((BalanceCondition)balanceCondition).getDimensionValueMap();
        if (!(MapUtils.isEmpty(dimensionValueMap) || Objects.isNull(dimensionValueMap.get("DATATIME")) || Objects.isNull(dimensionValueMap.get("DATATIME").getValue()))) {
            return dimensionValueMap.get("DATATIME").getValue();
        }
        return ((BalanceCondition)balanceCondition).getAcctYear() + "Y" + String.format("00%02d", ((BalanceCondition)balanceCondition).getEndPeriod());
    }
}

