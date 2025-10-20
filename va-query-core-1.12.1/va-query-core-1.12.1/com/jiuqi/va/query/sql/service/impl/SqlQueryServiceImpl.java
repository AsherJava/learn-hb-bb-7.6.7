/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.config.VaMapperConfig
 *  com.jiuqi.va.query.basedata.dto.DCBaseDataDTO
 *  com.jiuqi.va.query.common.FormatNumberEnum
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.fetch.vo.FetchQueryResultVO
 *  com.jiuqi.va.query.sql.enumerate.DisplayFormatEnum
 *  com.jiuqi.va.query.sql.enumerate.ModeOperatorEnum
 *  com.jiuqi.va.query.sql.enumerate.QueryModeEnum
 *  com.jiuqi.va.query.sql.formula.QueryFormulaContext
 *  com.jiuqi.va.query.sql.formula.QueryFormulaHandler
 *  com.jiuqi.va.query.sql.interceptor.QueryResultInterceptor
 *  com.jiuqi.va.query.sql.vo.Column
 *  com.jiuqi.va.query.sql.vo.QueryExpandField
 *  com.jiuqi.va.query.sql.vo.QueryGroupField
 *  com.jiuqi.va.query.sql.vo.QueryParamVO
 *  com.jiuqi.va.query.sql.vo.QueryParseResultVO
 *  com.jiuqi.va.query.sql.vo.QuerySortVO
 *  com.jiuqi.va.query.sql.vo.ResultVO
 *  com.jiuqi.va.query.sql.vo.TreeRow
 *  com.jiuqi.va.query.template.enumerate.GatherTypeEnum
 *  com.jiuqi.va.query.template.enumerate.ParamTypeEnum
 *  com.jiuqi.va.query.template.enumerate.PluginEnum
 *  com.jiuqi.va.query.template.plugin.BaseInfoPlugin
 *  com.jiuqi.va.query.template.plugin.FormulaPlugin
 *  com.jiuqi.va.query.template.plugin.QueryFieldsPlugin
 *  com.jiuqi.va.query.template.plugin.QueryFormulaImpl
 *  com.jiuqi.va.query.template.plugin.QueryRelatePlugin
 *  com.jiuqi.va.query.template.plugin.ViewSetPlugin
 *  com.jiuqi.va.query.template.vo.QueryResultVO
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateContentVO
 *  com.jiuqi.va.query.template.vo.TemplateDataSourceSetVO
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 *  com.jiuqi.va.query.template.vo.TemplateRelateQueryVO
 *  com.jiuqi.va.query.tree.vo.TableHeaderVO
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.ibatis.jdbc.SQL
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 */
package com.jiuqi.va.query.sql.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.config.VaMapperConfig;
import com.jiuqi.va.query.basedata.dto.DCBaseDataDTO;
import com.jiuqi.va.query.common.DefinedQueryConfigProperties;
import com.jiuqi.va.query.common.FormatNumberEnum;
import com.jiuqi.va.query.common.QueryConstant;
import com.jiuqi.va.query.common.SQLKeywordCheck;
import com.jiuqi.va.query.common.service.DCQueryBaseDataUtil;
import com.jiuqi.va.query.common.service.FormulaExecuteHandlerUtil;
import com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil;
import com.jiuqi.va.query.datasource.dao.DataSourceInfoDao;
import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.fetch.vo.FetchQueryResultVO;
import com.jiuqi.va.query.sql.dao.UserDefinedDao;
import com.jiuqi.va.query.sql.dto.PageSqlExecConditionDTO;
import com.jiuqi.va.query.sql.dto.QueryExportCellDTO;
import com.jiuqi.va.query.sql.dto.QueryExportGroupStyleEnum;
import com.jiuqi.va.query.sql.dto.SqlExecConditionDTO;
import com.jiuqi.va.query.sql.dto.SqlQueryOldTemplateInfo;
import com.jiuqi.va.query.sql.dto.SqlQueryOldTemplateInfoBuilder;
import com.jiuqi.va.query.sql.dto.TempTableParamDTO;
import com.jiuqi.va.query.sql.enumerate.DisplayFormatEnum;
import com.jiuqi.va.query.sql.enumerate.ModeOperatorEnum;
import com.jiuqi.va.query.sql.enumerate.QueryModeEnum;
import com.jiuqi.va.query.sql.formula.QueryFormulaContext;
import com.jiuqi.va.query.sql.formula.QueryFormulaHandler;
import com.jiuqi.va.query.sql.interceptor.QueryResultInterceptor;
import com.jiuqi.va.query.sql.io.QueryExcelExportExecutor;
import com.jiuqi.va.query.sql.parser.QuerySqlParser;
import com.jiuqi.va.query.sql.parser.QueryVariableParser;
import com.jiuqi.va.query.sql.parser.SqlColumnParser;
import com.jiuqi.va.query.sql.service.QueryResultInterceptGather;
import com.jiuqi.va.query.sql.service.SqlQueryService;
import com.jiuqi.va.query.sql.transform.TreeBuilder;
import com.jiuqi.va.query.sql.vo.Column;
import com.jiuqi.va.query.sql.vo.QueryExpandField;
import com.jiuqi.va.query.sql.vo.QueryGroupField;
import com.jiuqi.va.query.sql.vo.QueryParamVO;
import com.jiuqi.va.query.sql.vo.QueryParseResultVO;
import com.jiuqi.va.query.sql.vo.QuerySortVO;
import com.jiuqi.va.query.sql.vo.ResultVO;
import com.jiuqi.va.query.sql.vo.TreeRow;
import com.jiuqi.va.query.template.dao.QueryTemplateInfoDao;
import com.jiuqi.va.query.template.enumerate.GatherTypeEnum;
import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;
import com.jiuqi.va.query.template.enumerate.PluginEnum;
import com.jiuqi.va.query.template.plugin.BaseInfoPlugin;
import com.jiuqi.va.query.template.plugin.FormulaPlugin;
import com.jiuqi.va.query.template.plugin.QueryFieldsPlugin;
import com.jiuqi.va.query.template.plugin.QueryFormulaImpl;
import com.jiuqi.va.query.template.plugin.QueryRelatePlugin;
import com.jiuqi.va.query.template.plugin.ViewSetPlugin;
import com.jiuqi.va.query.template.service.TemplateContentService;
import com.jiuqi.va.query.template.service.TemplateDesignService;
import com.jiuqi.va.query.template.vo.QueryResultVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.vo.TemplateDataSourceSetVO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import com.jiuqi.va.query.tree.vo.TableHeaderVO;
import com.jiuqi.va.query.util.DCQueryExcelTool;
import com.jiuqi.va.query.util.DCQuerySqlBuildUtil;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import com.jiuqi.va.query.util.DCQueryUUIDUtil;
import com.jiuqi.va.query.util.DateUtils;
import com.jiuqi.va.query.util.QueryExpressionUtil;
import com.jiuqi.va.query.util.QuerySqlTransactionUtils;
import com.jiuqi.va.query.util.QueryUtils;
import com.jiuqi.va.query.util.export.DCQueryExportUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class SqlQueryServiceImpl
implements SqlQueryService {
    private static final Logger logger = LoggerFactory.getLogger(SqlQueryServiceImpl.class);
    @Autowired
    private QueryTemplateInfoDao queryTemplateInfoDao;
    @Autowired
    private UserDefinedDao userDefinedDao;
    @Autowired
    private TemplateContentService templateContentService;
    @Autowired
    private DefinedQueryConfigProperties queryConfig;
    @Autowired
    private DataSourceInfoDao dataSourceInfoDao;
    @Autowired
    private QueryResultInterceptGather queryResultInterceptGather;
    @Autowired
    private TemplateDesignService templateDesignService;
    @Autowired
    private QueryFormulaHandler queryFormulaHandler;
    @Autowired
    private SqlQueryServiceImpl sqlQueryService;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private OrgDataClient orgDataClient;
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");
    private static final Map<String, String> DB_TYPE_MAP = new ConcurrentHashMap<String, String>();

    @Override
    public QueryParseResultVO parseSql(QueryTemplate queryTemplate) {
        TemplateContentVO templateContent = new TemplateContentVO();
        templateContent.setTemplate(((BaseInfoPlugin)queryTemplate.getPluginByClass(BaseInfoPlugin.class)).getBaseInfo());
        TemplateDataSourceSetVO dataSourceSetVO = new TemplateDataSourceSetVO();
        dataSourceSetVO.setDefineSql(queryTemplate.getDataSourceSet().getDefineSql());
        templateContent.setDataSourceSet(dataSourceSetVO);
        templateContent.setParams(queryTemplate.getDataSourceSet().getParams());
        templateContent.setFields(((QueryFieldsPlugin)queryTemplate.getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class)).getFields());
        SqlQueryOldTemplateInfo sqlOldTemplateInfo = new SqlQueryOldTemplateInfoBuilder().params(templateContent.getParams()).fields(templateContent.getFields()).build();
        QueryParseResultVO queryParseResultVO = new QueryParseResultVO();
        List<String> userVariables = QuerySqlParser.getUserVariablesWithException(dataSourceSetVO.getDefineSql());
        ArrayList<TemplateParamsVO> params = new ArrayList<TemplateParamsVO>(queryTemplate.getDataSourceSet().getParams());
        Map<String, TemplateParamsVO> paramsVOMap = params.stream().collect(Collectors.toMap(TemplateParamsVO::getName, o -> o, (o1, o2) -> o1));
        for (String userVariable : userVariables) {
            if (paramsVOMap.containsKey(userVariable)) continue;
            TemplateParamsVO paramsVO = new TemplateParamsVO();
            paramsVO.setId(DCQueryUUIDUtil.getUUIDStr());
            paramsVO.setName(userVariable);
            paramsVO.setTitle(userVariable);
            paramsVO.setParamType(ParamTypeEnum.STRING.getTypeName());
            paramsVO.setMode(QueryModeEnum.singleData.getModeSign());
            paramsVO.setVisibleFlag(Boolean.valueOf(true));
            paramsVO.setMustInput(false);
            paramsVO.setEnableAuth(Boolean.valueOf(false));
            params.add(paramsVO);
            queryTemplate.getDataSourceSet().getParams().add(paramsVO);
            paramsVOMap.put(userVariable, paramsVO);
        }
        List<TemplateFieldSettingVO> templateFieldSettingVOS = this.parsingSql(templateContent);
        queryParseResultVO.setFields(templateFieldSettingVOS);
        queryParseResultVO.setParams(params);
        HashMap<String, DataModelDO> tableDefineMap = new HashMap<String, DataModelDO>();
        HashMap<String, TemplateFieldSettingVO> columnMap = new HashMap<String, TemplateFieldSettingVO>();
        this.generateColumnMap(dataSourceSetVO, templateFieldSettingVOS, params, tableDefineMap, columnMap, sqlOldTemplateInfo);
        queryParseResultVO.setColumnMap(columnMap);
        return queryParseResultVO;
    }

    private void generateColumnMap(TemplateDataSourceSetVO dataSourceSetVO, List<TemplateFieldSettingVO> templateFieldSettingVOS, List<TemplateParamsVO> params, Map<String, DataModelDO> tableDefineMap, Map<String, TemplateFieldSettingVO> columnMap, SqlQueryOldTemplateInfo oldTemplateInfo) {
        try {
            Map<String, SqlColumnParser.SelectColumn> columnTableMapper = new SqlColumnParser().parse(dataSourceSetVO.getDefineSql(), templateFieldSettingVOS);
            Map<String, QueryVariableParser.TableColumnInfo> paramsMapper = new QueryVariableParser().parse(dataSourceSetVO.getDefineSql());
            if (!columnTableMapper.isEmpty()) {
                this.processFieldSettings(templateFieldSettingVOS, columnTableMapper, tableDefineMap, columnMap, oldTemplateInfo);
            }
            if (!paramsMapper.isEmpty()) {
                this.processParams(params, paramsMapper, tableDefineMap, oldTemplateInfo);
            }
        }
        catch (Exception e) {
            logger.error("\u89e3\u6790\u5217\u62a5\u9519\uff0c\u53ef\u80fd\u662f\u6ca1\u6709\u652f\u6301\u7684sql\u8bed\u6cd5\u89e3\u6790, \u4e0d\u5f71\u54cdsql\u89e3\u6790", e);
        }
    }

    private void processFieldSettings(List<TemplateFieldSettingVO> fieldSettings, Map<String, SqlColumnParser.SelectColumn> columnTableMapper, Map<String, DataModelDO> tableDefineMap, Map<String, TemplateFieldSettingVO> columnMap, SqlQueryOldTemplateInfo oldTemplateInfo) {
        fieldSettings.forEach(fieldSettingVO -> {
            if (!StringUtils.hasText(fieldSettingVO.getBaseDataTable()) && columnTableMapper.containsKey(fieldSettingVO.getName().toUpperCase())) {
                DataModelDO dataModelDO;
                SqlColumnParser.SelectColumn selectColumn = (SqlColumnParser.SelectColumn)columnTableMapper.get(fieldSettingVO.getName().toUpperCase());
                String tableAndField = selectColumn.getField();
                String[] tableAndFieldParts = tableAndField.split("\\.");
                String tableName = tableAndFieldParts[0];
                String columnName = tableAndFieldParts[1];
                boolean hasOldValue = oldTemplateInfo.containsKey(SqlQueryOldTemplateInfo.PluginEnum.FIELDS, fieldSettingVO.getName());
                if (!hasOldValue && selectColumn.getUseAs().booleanValue()) {
                    fieldSettingVO.setTitle(selectColumn.getRealName());
                }
                if ((dataModelDO = this.getDataModelDO(tableName, tableDefineMap)) != null) {
                    dataModelDO.getColumns().stream().filter(dataModelColumn -> dataModelColumn.getColumnName().equalsIgnoreCase(columnName)).findFirst().ifPresent(dataModelColumn -> this.processDataModelColumn((TemplateFieldSettingVO)fieldSettingVO, (DataModelColumn)dataModelColumn, columnMap, selectColumn, hasOldValue));
                }
            }
        });
    }

    private void processParams(List<TemplateParamsVO> params, Map<String, QueryVariableParser.TableColumnInfo> paramsMapper, Map<String, DataModelDO> tableDefineMap, SqlQueryOldTemplateInfo oldTemplateInfo) {
        params.forEach(paramsVO -> {
            QueryVariableParser.TableColumnInfo tableColumnInfo = (QueryVariableParser.TableColumnInfo)paramsMapper.get(paramsVO.getName().toLowerCase());
            boolean hasOldValue = oldTemplateInfo.containsKey(SqlQueryOldTemplateInfo.PluginEnum.PARAMS, paramsVO.getName());
            if (tableColumnInfo != null && !tableColumnInfo.getTableName().equals("unknown")) {
                this.processTableColumnInfo((TemplateParamsVO)paramsVO, tableColumnInfo, tableDefineMap, hasOldValue);
            }
        });
    }

    private DataModelDO getDataModelDO(String tableName, Map<String, DataModelDO> tableDefineMap) {
        DataModelDTO dto = new DataModelDTO();
        dto.setName(tableName);
        return tableDefineMap.computeIfAbsent(tableName, k -> this.dataModelClient.get(dto));
    }

    private void processDataModelColumn(TemplateFieldSettingVO fieldSettingVO, DataModelColumn dataModelColumn, Map<String, TemplateFieldSettingVO> columnMap, SqlColumnParser.SelectColumn selectColumn, boolean hasOldValue) {
        DataModelType.ColumnType columnType;
        if (!hasOldValue && !selectColumn.getUseAs().booleanValue()) {
            fieldSettingVO.setTitle(dataModelColumn.getColumnTitle());
        }
        if ((columnType = dataModelColumn.getColumnType()).equals((Object)DataModelType.ColumnType.DATE) || columnType.equals((Object)DataModelType.ColumnType.TIMESTAMP)) {
            fieldSettingVO.setDataType(ParamTypeEnum.DATE.getTypeName());
        } else if (columnType.equals((Object)DataModelType.ColumnType.INTEGER)) {
            fieldSettingVO.setDataType(dataModelColumn.getLengths()[0] == 1 ? ParamTypeEnum.BOOL.getTypeName() : ParamTypeEnum.INTEGER.getTypeName());
        } else if (columnType.equals((Object)DataModelType.ColumnType.NUMERIC)) {
            fieldSettingVO.setDataType(ParamTypeEnum.NUMBER.getTypeName());
        }
        if (StringUtils.hasText(dataModelColumn.getMapping())) {
            String refTableName = dataModelColumn.getMapping().split("\\.")[0];
            if (refTableName.equalsIgnoreCase("AUTH_USER")) {
                refTableName = "NP_USER";
            }
            TemplateFieldSettingVO newFieldSettingVO = new TemplateFieldSettingVO();
            newFieldSettingVO.setBaseDataTable(refTableName);
            newFieldSettingVO.setDisplayFormat(DisplayFormatEnum.TITLE.getTypeName());
            newFieldSettingVO.setName(fieldSettingVO.getName());
            newFieldSettingVO.setTitle(fieldSettingVO.getTitle());
            fieldSettingVO.setDataType(ParamTypeEnum.STRING.getTypeName());
            columnMap.put(fieldSettingVO.getName(), newFieldSettingVO);
        }
    }

    private void processTableColumnInfo(TemplateParamsVO paramsVO, QueryVariableParser.TableColumnInfo tableColumnInfo, Map<String, DataModelDO> tableDefineMap, boolean hasOldValue) {
        String tableName = tableColumnInfo.getTableName();
        String columnName = tableColumnInfo.getColumnName();
        if (tableColumnInfo.isIn()) {
            paramsVO.setMode(QueryModeEnum.mutileData.getModeSign());
        } else {
            paramsVO.setMode(QueryModeEnum.singleData.getModeSign());
        }
        DataModelDO dataModelDO = this.getDataModelDO(tableName, tableDefineMap);
        if (dataModelDO != null) {
            dataModelDO.getColumns().stream().filter(dataModelColumn -> dataModelColumn.getColumnName().equalsIgnoreCase(columnName)).findFirst().ifPresent(dataModelColumn -> this.updateParamsVO(paramsVO, (DataModelColumn)dataModelColumn, hasOldValue));
        }
    }

    private void updateParamsVO(TemplateParamsVO paramsVO, DataModelColumn dataModelColumn, boolean hasOldValue) {
        DataModelType.ColumnType columnType;
        if (!hasOldValue) {
            paramsVO.setTitle(dataModelColumn.getColumnTitle());
        }
        if ((columnType = dataModelColumn.getColumnType()).equals((Object)DataModelType.ColumnType.DATE)) {
            paramsVO.setParamType(ParamTypeEnum.DATE.getTypeName());
        } else if (columnType.equals((Object)DataModelType.ColumnType.TIMESTAMP)) {
            paramsVO.setParamType(ParamTypeEnum.DATE_TIME.getTypeName());
        } else if (columnType.equals((Object)DataModelType.ColumnType.INTEGER)) {
            paramsVO.setParamType(dataModelColumn.getLengths()[0] == 1 ? ParamTypeEnum.BOOL.getTypeName() : ParamTypeEnum.INTEGER.getTypeName());
        } else if (columnType.equals((Object)DataModelType.ColumnType.NUMERIC)) {
            paramsVO.setParamType(ParamTypeEnum.NUMBER.getTypeName());
        }
        if (StringUtils.hasText(dataModelColumn.getMapping())) {
            String refTableName = dataModelColumn.getMapping().split("\\.")[0];
            if (!refTableName.equals("AUTH_USER")) {
                paramsVO.setRefTableName(refTableName);
            }
            paramsVO.setName(paramsVO.getName());
            paramsVO.setParamType(ParamTypeEnum.STRING.getTypeName());
        }
    }

    @Override
    @Deprecated
    public List<TemplateFieldSettingVO> parsingSql(TemplateContentVO templateContent) {
        String sql = templateContent.getDataSourceSet().getDefineSql();
        if (DCQueryStringHandle.isEmpty(sql)) {
            return null;
        }
        this.verifySpecialSQL(sql);
        Map<String, TemplateParamsVO> paramsVOMap = templateContent.getParams().stream().collect(Collectors.toMap(TemplateParamsVO::getName, o -> o, (o1, o2) -> o1));
        List<String> userVariables = QuerySqlParser.getUserVariables(sql);
        ArrayList<String> emptyParams = new ArrayList<String>();
        for (String userVariable : userVariables) {
            if (paramsVOMap.containsKey(userVariable)) continue;
            emptyParams.add(userVariable);
        }
        if (!CollectionUtils.isEmpty(emptyParams)) {
            throw new DefinedQueryRuntimeException(String.join((CharSequence)",", emptyParams) + " :\u672a\u5728\u67e5\u8be2\u6761\u4ef6\u4e2d\u5b9a\u4e49");
        }
        this.checkSqlUseCustomParam(sql, templateContent);
        String dataSourceCode = templateContent.getTemplate().getDatasourceCode();
        if (templateContent.getParams() != null) {
            List params = templateContent.getParams();
            for (TemplateParamsVO templateParamsVO : params) {
                if (DCQueryStringHandle.isEmpty(templateParamsVO.getName()) || DCQueryStringHandle.isEmpty(templateParamsVO.getDefaultValue())) continue;
                sql = sql.replaceAll("#" + templateParamsVO.getName() + "#", this.evaluateFormulaValue(templateParamsVO.getDefaultValue()));
            }
            sql = QuerySqlParser.parserSql(sql, params.stream().map(TemplateParamsVO::getName).collect(Collectors.toList()));
        } else {
            sql = QuerySqlParser.parserSql(sql, new ArrayList<String>());
        }
        List<TemplateFieldSettingVO> fields = this.userDefinedDao.parsingSql(dataSourceCode, new SqlExecConditionDTO(sql, null, null));
        if (!CollectionUtils.isEmpty(templateContent.getFields())) {
            Map oldFields = templateContent.getFields().stream().collect(Collectors.toMap(TemplateFieldSettingVO::getName, Function.identity(), (key1, key2) -> key2));
            ArrayList<TemplateFieldSettingVO> newFields = new ArrayList<TemplateFieldSettingVO>();
            for (TemplateFieldSettingVO vo : fields) {
                newFields.add(oldFields.getOrDefault(vo.getName(), vo));
            }
            return newFields;
        }
        return fields;
    }

    private void checkSqlUseCustomParam(String sql, TemplateContentVO templateContent) {
        Matcher matcher = QueryConstant.PATTERN_ONE.matcher(sql);
        ArrayList<String> tempList = new ArrayList<String>();
        while (matcher.find()) {
            tempList.add(matcher.group());
        }
        for (String tempStr : tempList) {
            String[] nameArray = tempStr.split("#");
            if (nameArray.length != 2) continue;
            String tempName = nameArray[1];
            List templateParamsVoList = Optional.ofNullable(templateContent.getParams()).orElse(Collections.emptyList());
            boolean flag = false;
            for (TemplateParamsVO templateParamsVO : templateParamsVoList) {
                String name = templateParamsVO.getName();
                if (!tempName.equals(name) || ObjectUtils.isEmpty(this.evaluateFormulaValue(templateParamsVO.getDefaultValue()))) continue;
                flag = true;
                break;
            }
            Assert.isTrue(flag, "Sql\u4e2d\u53d8\u91cf\uff1a" + tempStr + " \u6ca1\u6709\u5728\u67e5\u8be2\u6761\u4ef6\u4e2d\u914d\u7f6e\u6216\u8005\u9ed8\u8ba4\u503c\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5");
        }
    }

    private String evaluateFormulaValue(String formulaString) {
        if (DCQueryStringHandle.isEmpty(formulaString)) {
            return "";
        }
        if (this.isJsonStr(formulaString)) {
            return "";
        }
        Object defaultValueObj = FormulaExecuteHandlerUtil.getFormulaEvaluateData(formulaString, ParamTypeEnum.STRING.getTypeName());
        return defaultValueObj == null ? "" : defaultValueObj.toString();
    }

    private boolean isJsonStr(String defaultValue) {
        if (!defaultValue.contains("{")) {
            return false;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(defaultValue);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public void verifySpecialSQL(String sql) {
        if (!StringUtils.hasText(sql)) {
            return;
        }
        String lowerSql = sql.toLowerCase();
        for (SQLKeywordCheck check : SQLKeywordCheck.values()) {
            if (check.isAllowed(this.queryConfig) || !check.matches(lowerSql)) continue;
            throw new DefinedQueryRuntimeException(check.getErrorMessage());
        }
    }

    @Override
    public ResultVO previewSql(String templateId) {
        TemplateContentVO templateContentVO = this.templateDesignService.getTemplateContentVO(templateId);
        TemplateDataSourceSetVO dataSourceSet = templateContentVO.getDataSourceSet();
        List params = templateContentVO.getParams();
        List fields = templateContentVO.getFields();
        TemplateInfoVO templateInfo = templateContentVO.getTemplate();
        String dataSourceCode = templateInfo.getDatasourceCode();
        String sql = dataSourceSet.getDefineSql();
        LinkedList args = new LinkedList();
        if (params != null) {
            for (TemplateParamsVO templateParamsVO : params) {
                if (DCQueryStringHandle.isEmpty(templateParamsVO.getDefaultValue())) continue;
                sql = sql.replaceAll("#" + templateParamsVO.getName() + "#", this.evaluateFormulaValue(templateParamsVO.getDefaultValue()));
            }
            sql = QuerySqlParser.parserSql(sql, params.stream().map(TemplateParamsVO::getName).collect(Collectors.toList()));
        }
        ResultVO resultVo = new ResultVO();
        PageSqlExecConditionDTO sqlExecCondition = new PageSqlExecConditionDTO();
        sqlExecCondition.setSql(sql);
        sqlExecCondition.setArgs(args.toArray());
        sqlExecCondition.setFields(fields);
        sqlExecCondition.setIsPageQuery(true);
        sqlExecCondition.setPageNumber(1);
        sqlExecCondition.setPageSize(10);
        resultVo.setDataList(DCQueryBaseDataUtil.resultCodeToTitle(this.userDefinedDao.execSql(dataSourceCode, sqlExecCondition), fields));
        return resultVo;
    }

    @Override
    public QueryResultVO nativeExecSql(QueryTemplate queryTemplate, int pageNum, int pageSize, Map<String, Object> params) {
        QueryResultVO queryResultVO = new QueryResultVO();
        List fields = ((QueryFieldsPlugin)queryTemplate.getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class)).getFields();
        if (CollectionUtils.isEmpty(fields)) {
            fields = this.parseSql(queryTemplate).getFields();
        }
        if (CollectionUtils.isEmpty(fields)) {
            throw new DefinedQueryRuntimeException("\u65e0\u6cd5\u83b7\u53d6\u67e5\u8be2\u5217\uff0c\u811a\u672c\u65e0\u6cd5\u6267\u884c");
        }
        String dataSourceCode = ((BaseInfoPlugin)queryTemplate.getPluginByClass(BaseInfoPlugin.class)).getBaseInfo().getDatasourceCode();
        this.verifySpecialSQL(queryTemplate.getDataSourceSet().getDefineSql());
        PageSqlExecConditionDTO sqlExecCondition = this.getSqlExecConditionDTO(queryTemplate, params, fields, dataSourceCode);
        ResultVO resultVo = new ResultVO();
        int count = this.userDefinedDao.getTotalCount(dataSourceCode, sqlExecCondition);
        resultVo.setTotalCount(Integer.valueOf(count));
        if (count == 0) {
            queryResultVO.setResultVO(resultVo);
        } else {
            sqlExecCondition.setPageNumber(pageNum);
            sqlExecCondition.setPageSize(pageSize);
            sqlExecCondition.setIsPageQuery(true);
            List<Map<String, Object>> resultList = DCQueryBaseDataUtil.resultCodeToTitle(this.userDefinedDao.execSql(dataSourceCode, sqlExecCondition), fields);
            resultVo.setDataList(resultList);
            queryResultVO.setResultVO(resultVo);
        }
        this.clearTempTable(dataSourceCode, sqlExecCondition.getSn());
        List header = fields.stream().filter(TemplateFieldSettingVO::isVisibleFlag).map(QueryUtils::fieldToTableHeaderVO).collect(Collectors.toList());
        queryResultVO.setColumns(header);
        return queryResultVO;
    }

    @Override
    public Map<String, Object> setValue(QueryParamVO queryParamVO) {
        QueryUtils.transformFrontQueryTemplate(queryParamVO);
        HashMap<String, Object> formulaFiledValue = new HashMap<String, Object>();
        Map queryParams = queryParamVO.getParams();
        QueryTemplate queryTemplate = queryParamVO.getQueryTemplate();
        FormulaPlugin formulaPlugin = (FormulaPlugin)queryTemplate.getPluginByClass(FormulaPlugin.class);
        List params = queryTemplate.getDataSourceSet().getParams();
        QueryFormulaContext queryFormulaContext = new QueryFormulaContext(queryParams, queryTemplate);
        for (TemplateParamsVO param : params) {
            Map configParamMap = param.getConfigParamMap();
            if (!configParamMap.containsKey("versionDate")) continue;
            String formulaId = String.valueOf(configParamMap.get("versionDate"));
            try {
                QueryFormulaImpl formula = formulaPlugin.getFormula(formulaId);
                Object object = this.queryFormulaHandler.evaluateData(formula.getExpression(), ParamTypeEnum.STRING.getTypeName(), queryFormulaContext);
                formulaFiledValue.put(formula.getId(), object);
            }
            catch (Exception e) {
                logger.error(param.getName() + " \u7ed1\u5b9a\u7684\u81ea\u5b9a\u4e49\u516c\u5f0f\u516c\u5f0f\u6267\u884c\u5931\u8d25", e);
                throw new DefinedQueryRuntimeException("\u81ea\u5b9a\u4e49\u516c\u5f0f\u6267\u884c\u5931\u8d25");
            }
        }
        return formulaFiledValue;
    }

    @Override
    public void export(String templateId, Map<String, Object> params) {
        QuerySqlTransactionUtils.executeWithTransaction(() -> {
            TemplateContentVO templateContentVO = this.templateDesignService.getTemplateContentVO(templateId);
            ResultVO resultVo = this.execSql(templateId, 0, 0, params, false);
            List<Map<String, Object>> dataList = CollectionUtils.isEmpty(resultVo.getDataList()) ? new ArrayList() : resultVo.getDataList();
            List fields = templateContentVO.getFields();
            Object showSumRow = templateContentVO.getViewSets().get("showSumRow");
            if ((showSumRow == null || Boolean.TRUE.equals(showSumRow)) && this.hasGatherTypeColumn(fields)) {
                Map<String, Object> gatherRow = this.getSumRowData(templateId, params, false);
                gatherRow.put(((TemplateFieldSettingVO)fields.get(0)).getName(), "\u5408\u8ba1");
                dataList.add(gatherRow);
            }
            List<TableHeaderVO> tableHeader = this.templateContentService.getTableHeader(templateId);
            DCQueryExcelTool<Map<String, Object>> excelTool = new DCQueryExcelTool<Map<String, Object>>();
            RequestContextUtil.setResponseContentType((String)"application/octet-stream");
            try {
                Workbook workbook = excelTool.exportWorkbook(tableHeader, dataList, true);
                RequestContextUtil.getOutputStream().flush();
                workbook.write(RequestContextUtil.getOutputStream());
            }
            catch (Exception e) {
                logger.error("\u5bfc\u51fa\u5f02\u5e38", e);
            }
        });
    }

    @Override
    public void exportPreview(QueryParamVO queryParamVO) {
        QuerySqlTransactionUtils.executeWithTransaction(() -> {
            QueryUtils.transformFrontQueryTemplate(queryParamVO);
            ObjectMapper objectMapper = new ObjectMapper();
            QueryParamVO cloneQueryParams = this.deepCloneQueryParams(queryParamVO, objectMapper);
            QueryParamVO cloneQueryParams2 = this.deepCloneQueryParams(queryParamVO, objectMapper);
            QueryTemplate template = queryParamVO.getQueryTemplate();
            this.dealExportExcelResponse(template);
            List groupFields = queryParamVO.getGroupFields();
            List<TemplateFieldSettingVO> fields = DCQueryExportUtils.getExportFields(template, groupFields);
            Map<String, TemplateFieldSettingVO> fieldSettingVOMap = ((QueryFieldsPlugin)this.templateDesignService.getBizTemplate(template.getId()).getPluginByClass(QueryFieldsPlugin.class)).getFields().stream().collect(Collectors.toMap(TemplateFieldSettingVO::getId, o -> o));
            fields.forEach(field -> field.setTitle(((TemplateFieldSettingVO)fieldSettingVOMap.get(field.getId())).getTitle()));
            DCQueryExcelTool<Map<String, Object>> excelTool = new DCQueryExcelTool<Map<String, Object>>();
            List<TableHeaderVO> tableHeaderList = this.templateContentService.getPreviewTableHeader(fields);
            tableHeaderList.add(0, DCQueryExportUtils.getExcelIndexHeader());
            List<Column> columns = excelTool.columnTransformer(tableHeaderList);
            QueryExcelExportExecutor exportExecutor = new QueryExcelExportExecutor(queryParamVO, excelTool, columns, Optional.ofNullable(queryParamVO.getGroupFields()).orElse(Collections.emptyList()));
            String dataSourceCode = ((BaseInfoPlugin)template.getPluginByClass(BaseInfoPlugin.class)).getBaseInfo().getDatasourceCode();
            Map params = queryParamVO.getParams();
            PageSqlExecConditionDTO sqlExecCondition = this.getSqlExecConditionDTO(template, params, fields, dataSourceCode);
            this.initSort(queryParamVO, sqlExecCondition, dataSourceCode);
            Map<String, IExpression> fieldExpressionMap = QueryExpressionUtil.getSumExpressionMap(fields, template);
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            if (CollectionUtils.isEmpty(groupFields)) {
                HashMap<String, Map<String, DCBaseDataDTO>> cacheMap = new HashMap<String, Map<String, DCBaseDataDTO>>();
                int maxSize = 50000;
                int pageNum = 0;
                do {
                    sqlExecCondition.setPageNumber(pageNum + 1);
                    sqlExecCondition.setPageSize(maxSize);
                    sqlExecCondition.setIsPageQuery(true);
                    sqlExecCondition.setFormatNumber(FormatNumberEnum.NOT);
                    resultList = DCQueryBaseDataUtil.resultCodeToTitle(this.userDefinedDao.execSql(dataSourceCode, sqlExecCondition), fields, cacheMap);
                    for (int i1 = 0; i1 < resultList.size(); ++i1) {
                        Map<String, Object> stringObjectMap = resultList.get(i1);
                        stringObjectMap.putIfAbsent("excel_index", pageNum * maxSize + i1 + 1);
                    }
                    this.calculateGatherExpression(resultList, fields, template, groupFields, fieldExpressionMap);
                    exportExecutor.appendDataList(resultList);
                    ++pageNum;
                } while (resultList.size() >= maxSize);
            } else {
                List treeRowList = this.sqlQueryService.expandAll(cloneQueryParams2).getTreeRowList();
                this.treeToList(resultList, treeRowList, template, queryParamVO.getActionID());
                for (int i = 0; i < resultList.size(); ++i) {
                    Map stringObjectMap = (Map)resultList.get(i);
                    stringObjectMap.putIfAbsent("excel_index", i + 1);
                }
                this.calculateGatherExpression(resultList, fields, template, groupFields, fieldExpressionMap);
                exportExecutor.appendDataList(resultList);
            }
            Object showSumRow = ((ViewSetPlugin)template.getPluginByClass(ViewSetPlugin.class)).getViewSet("showSumRow");
            if ((showSumRow == null || Boolean.TRUE.equals(showSumRow)) && this.hasGatherTypeColumn(fields)) {
                Map<String, Object> gatherRow = this.getSumRowData(cloneQueryParams, false);
                gatherRow.put("excel_index", "\u5408\u8ba1");
                ArrayList<Map<String, Object>> sumRowList = new ArrayList<Map<String, Object>>();
                sumRowList.add(gatherRow);
                exportExecutor.appendDataList(sumRowList);
            }
            exportExecutor.flushWorkbook(RequestContextUtil.getOutputStream());
        });
    }

    private void dealExportExcelResponse(QueryTemplate template) {
        RequestContextUtil.setResponseContentType((String)"application/octet-stream");
        String title = QueryUtils.translateTemplateTitle(template);
        try {
            RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename=" + URLEncoder.encode(title, "utf-8")));
            RequestContextUtil.setResponseHeader((String)"Access-Control-Expose-Headers", (String)"Content-Disposition");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private QueryParamVO deepCloneQueryParams(QueryParamVO queryParamVO, ObjectMapper objectMapper) {
        try {
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
            return (QueryParamVO)objectMapper.readValue(objectMapper.writeValueAsString((Object)queryParamVO), QueryParamVO.class);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DefinedQueryRuntimeException("\u8f6c\u6362\u67e5\u8be2\u53c2\u6570\u5f02\u5e38");
        }
    }

    private Map<String, IExpression> getFieldExpressionMap(List<TemplateFieldSettingVO> fields, QueryTemplate template) {
        if (CollectionUtils.isEmpty(fields)) {
            return Collections.emptyMap();
        }
        HashMap<String, IExpression> fieldExpressionMap = new HashMap<String, IExpression>();
        QueryFormulaContext queryFormulaContext = new QueryFormulaContext(null, template);
        for (TemplateFieldSettingVO fieldSettingVO : fields) {
            if (!StringUtils.hasText(fieldSettingVO.getExpression())) continue;
            try {
                fieldExpressionMap.put(fieldSettingVO.getName(), FormulaExecuteHandlerUtil.parseIExpression(fieldSettingVO.getExpression(), queryFormulaContext));
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return fieldExpressionMap;
    }

    private List<TemplateFieldSettingVO> getExportCalcFields(List<TemplateFieldSettingVO> fields, List<QueryGroupField> groupFields) {
        if (CollectionUtils.isEmpty(fields)) {
            return Collections.emptyList();
        }
        List<TemplateFieldSettingVO> calcFields = CollectionUtils.isEmpty(groupFields) ? this.getCalcFields(fields) : new ArrayList<TemplateFieldSettingVO>();
        fields.forEach(fieldSettingVO -> {
            if (StringUtils.hasText(fieldSettingVO.getExpression()) && StringUtils.hasText(fieldSettingVO.getGatherType()) && fieldSettingVO.getGatherType().equals(GatherTypeEnum.NO_AGREGATE.getTypeName())) {
                calcFields.add((TemplateFieldSettingVO)fieldSettingVO);
            }
        });
        return calcFields;
    }

    private void calculateGatherExpression(List<Map<String, Object>> resultList, List<TemplateFieldSettingVO> fields, QueryTemplate template, List<QueryGroupField> groupFields, Map<String, IExpression> fieldExpressionMap) {
        if (CollectionUtils.isEmpty(resultList) || CollectionUtils.isEmpty(fields) || Objects.isNull(template)) {
            return;
        }
        List<TemplateFieldSettingVO> calcFields = this.getExportCalcFields(fields, groupFields);
        if (CollectionUtils.isEmpty(calcFields)) {
            return;
        }
        for (Map<String, Object> row : resultList) {
            Map<String, Object> originDataMap = QueryUtils.getMap(row.get("__ORIGIN_DATA_MAP__"));
            QueryExpressionUtil.calcSumFieldExpression(template, originDataMap, calcFields, row, fieldExpressionMap);
        }
    }

    private List<Map<String, Object>> treeToList(List<Map<String, Object>> dataList, List<TreeRow> treeRowList, QueryTemplate template, String actionID) {
        if (CollectionUtils.isEmpty(treeRowList)) {
            return dataList;
        }
        QueryExportGroupStyleEnum groupCellType = DCQueryExportUtils.getGroupStyle(template, actionID);
        if (QueryExportGroupStyleEnum.EXCEL_GROUP == groupCellType) {
            this.treeToListWithInfoGroup(0, treeRowList, dataList);
        } else {
            this.treeToListWithMergeRowCellInfo(dataList, treeRowList, groupCellType);
        }
        return dataList;
    }

    private void treeToListWithInfoGroup(int rootLevel, List<TreeRow> treeRowList, List<Map<String, Object>> dataList) {
        if (CollectionUtils.isEmpty(treeRowList)) {
            return;
        }
        for (TreeRow treeRow : treeRowList) {
            List children = treeRow.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                this.treeToListWithInfoGroup(rootLevel + 1, children, dataList);
            }
            QueryExportCellDTO cellDTO = new QueryExportCellDTO();
            cellDTO.setLevel(rootLevel);
            cellDTO.setGroupFlag(!CollectionUtils.isEmpty(children));
            Map rowValues = treeRow.getRowValues();
            rowValues.put("__CELL_INFO__", cellDTO);
            dataList.add(rowValues);
        }
    }

    private void treeToListWithMergeRowCellInfo(List<Map<String, Object>> dataList, List<TreeRow> treeRowList, QueryExportGroupStyleEnum groupCellType) {
        if (CollectionUtils.isEmpty(treeRowList)) {
            return;
        }
        Collections.reverse(treeRowList);
        for (TreeRow treeRow : treeRowList) {
            Map rowValues = treeRow.getRowValues();
            dataList.add(rowValues);
            List children = treeRow.getChildren();
            if (CollectionUtils.isEmpty(children)) continue;
            if (QueryExportGroupStyleEnum.NO_MERGE != groupCellType) {
                QueryExportCellDTO queryExportCellDTO = new QueryExportCellDTO();
                int crossRowNum = this.getCurrentRowCrossRowNum(treeRow);
                queryExportCellDTO.setChildrenSize(crossRowNum);
                rowValues.put("__CELL_INFO__", queryExportCellDTO);
            }
            this.treeToListWithMergeRowCellInfo(dataList, children, groupCellType);
        }
    }

    private int getCurrentRowCrossRowNum(TreeRow treeRow) {
        int num = 0;
        if (Objects.isNull(treeRow)) {
            return num;
        }
        List children = treeRow.getChildren();
        if (!Objects.nonNull(children)) {
            return num;
        }
        num = children.size();
        for (TreeRow child : children) {
            num += this.getCurrentRowCrossRowNum(child);
        }
        return num;
    }

    private boolean hasGatherTypeColumn(List<TemplateFieldSettingVO> fields) {
        if (fields == null || fields.size() == 0) {
            return false;
        }
        for (TemplateFieldSettingVO field : fields) {
            if (field.getGatherType().equals(GatherTypeEnum.AVG.getTypeName())) {
                return true;
            }
            if (field.getGatherType().equals(GatherTypeEnum.SUM.getTypeName())) {
                return true;
            }
            if (field.getGatherType().equals(GatherTypeEnum.MAXIMUM.getTypeName())) {
                return true;
            }
            if (!field.getGatherType().equals(GatherTypeEnum.MINIMUM.getTypeName())) continue;
            return true;
        }
        return false;
    }

    @Override
    public ResultVO expandAll(QueryParamVO queryParamVO) {
        boolean subExpandAll;
        QueryUtils.transformFrontQueryTemplate(queryParamVO);
        ResultVO resultVO = new ResultVO();
        Integer pageNum = queryParamVO.getPageNum();
        Integer pageSize = queryParamVO.getPageSize();
        QueryTemplate queryTemplate = queryParamVO.getQueryTemplate();
        TemplateInfoVO templateInfo = ((BaseInfoPlugin)queryTemplate.getPluginByClass(BaseInfoPlugin.class)).getBaseInfo();
        String dataSourceCode = templateInfo.getDatasourceCode();
        List groupFields = queryParamVO.getGroupFields();
        List expandFields = queryParamVO.getExpandFields();
        int level = queryParamVO.getExpandLevel();
        level = Math.max(level, expandFields.size());
        boolean bl = subExpandAll = !CollectionUtils.isEmpty(expandFields);
        if (CollectionUtils.isEmpty(groupFields)) {
            throw new DefinedQueryRuntimeException("no group fields");
        }
        PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager)ApplicationContextRegister.getBean(PlatformTransactionManager.class);
        TransactionDefinition transactionDefinition = (TransactionDefinition)ApplicationContextRegister.getBean(TransactionDefinition.class);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        try {
            QueryGroupField queryGroupField = (QueryGroupField)groupFields.get(CollectionUtils.isEmpty(expandFields) ? 0 : expandFields.size() - 1);
            StringBuilder distinctSql = new StringBuilder("select distinct query.%1$s AS distinct_count from (%2$s) query where 1=1");
            LinkedList<Object> newArgs = new LinkedList<Object>();
            StringBuilder paramSql = new StringBuilder();
            for (int i = 0; i < groupFields.size() && subExpandAll && i <= expandFields.size() - 1; ++i) {
                paramSql.append(" and query.").append(this.getDoubleQuotesString(((QueryGroupField)groupFields.get(i)).getFieldName(), dataSourceCode)).append(" = ? ");
                newArgs.add(((QueryExpandField)expandFields.get(i)).getFieldValue());
            }
            distinctSql.append((CharSequence)paramSql);
            ArrayList<TemplateFieldSettingVO> fields = new ArrayList<TemplateFieldSettingVO>();
            TemplateFieldSettingVO settingVO = new TemplateFieldSettingVO();
            settingVO.setDataType(ParamTypeEnum.STRING.getTypeName());
            settingVO.setName("distinct_count");
            fields.add(settingVO);
            PageSqlExecConditionDTO sqlExecCondition = this.getSqlExecConditionDTO(queryTemplate, queryParamVO.getParams(), fields, dataSourceCode);
            String parseSql = sqlExecCondition.getSql();
            sqlExecCondition.setPageSize(pageSize);
            sqlExecCondition.setPageNumber(pageNum);
            if (subExpandAll) {
                sqlExecCondition.setArgs(QueryUtils.mergeArrays(sqlExecCondition.getArgs(), newArgs.toArray()));
            }
            List querySortVOList = Optional.ofNullable(queryParamVO.getSortFields()).orElse(new ArrayList());
            Optional<QuerySortVO> querySortVO = querySortVOList.stream().filter(o -> o.getName().equalsIgnoreCase(queryGroupField.getFieldName())).findFirst();
            QuerySortVO defaultSortVO = new QuerySortVO();
            if (!CollectionUtils.isEmpty(querySortVOList) && querySortVO.isPresent()) {
                defaultSortVO = querySortVO.get();
            } else {
                defaultSortVO.setName(queryGroupField.getFieldName());
                defaultSortVO.setSort("asc");
            }
            querySortVOList.add(defaultSortVO);
            sqlExecCondition.setSql(String.format(distinctSql.toString(), this.getDoubleQuotesString(queryGroupField.getFieldName(), dataSourceCode), parseSql));
            int totalCount = this.userDefinedDao.getTotalCount(dataSourceCode, sqlExecCondition);
            if (totalCount == 0) {
                resultVO.setTotalCount(Integer.valueOf(0));
                ResultVO resultVO2 = resultVO;
                return resultVO2;
            }
            if (Objects.nonNull(pageNum) && Objects.nonNull(pageSize)) {
                sqlExecCondition.setIsPageQuery(true);
            }
            List<Map<String, Object>> list = this.userDefinedDao.execSql(dataSourceCode, sqlExecCondition);
            ArrayList inParams = new ArrayList();
            list.forEach(map -> inParams.add(String.valueOf(map.get("distinct_count"))));
            List queryFields = ((QueryFieldsPlugin)queryTemplate.getPluginByClass(QueryFieldsPlugin.class)).getFields();
            String strInCondition = DCQuerySqlBuildUtil.getStrInCondi("query." + this.getDoubleQuotesString(queryGroupField.getFieldName(), dataSourceCode), inParams.size());
            String sql = String.format("select * from (%1$s) query where 1=1 %2$s and %3$s", parseSql, paramSql, strInCondition);
            sqlExecCondition.setFields(queryFields);
            sqlExecCondition.setSql(sql);
            sqlExecCondition.setArgs(QueryUtils.mergeArrays(sqlExecCondition.getArgs(), inParams.toArray()));
            int detailRowCount = this.userDefinedDao.getTotalCount(dataSourceCode, sqlExecCondition);
            if (detailRowCount > 300000) {
                throw new DefinedQueryRuntimeException("\u5f53\u524d\u5206\u7ec4\u6761\u4ef6\u4e0b\u7684\u6570\u636e\u91cf\u8fc7\u5927\uff0c\u65e0\u6cd5\u4e00\u952e\u5c55\u5f00\uff0c\u8bf7\u8c03\u6574\u6761\u4ef6\u8fdb\u884c\u7b5b\u9009");
            }
            sqlExecCondition.setIsPageQuery(false);
            sqlExecCondition.setFormatNumber(FormatNumberEnum.YES);
            this.initSort(queryParamVO, sqlExecCondition, dataSourceCode);
            List<Map<String, Object>> groupDetailList = DCQueryBaseDataUtil.resultCodeToTitle(this.userDefinedDao.execSql(dataSourceCode, sqlExecCondition), queryFields);
            this.getInterceptorResult(((QueryRelatePlugin)queryTemplate.getPluginByName(PluginEnum.queryRelate.name(), QueryRelatePlugin.class)).getRelateQuerys(), groupDetailList);
            List<TreeRow> treeRows = new TreeBuilder(queryParamVO).generateTree(groupDetailList, level);
            resultVO.setTreeRowList(treeRows);
            resultVO.setDataList(groupDetailList);
            resultVO.setTotalCount(Integer.valueOf(totalCount));
            ResultVO resultVO3 = resultVO;
            return resultVO3;
        }
        catch (DefinedQueryRuntimeException e) {
            throw new DefinedQueryRuntimeException(e.getMessage());
        }
        finally {
            try {
                platformTransactionManager.rollback(transactionStatus);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public ResultVO execSql(String templateId, int pageNum, int pageSize, Map<String, Object> paramMap, boolean isPage) {
        ResultVO resultVo;
        PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager)ApplicationContextRegister.getBean(PlatformTransactionManager.class);
        TransactionDefinition transactionDefinition = (TransactionDefinition)ApplicationContextRegister.getBean(TransactionDefinition.class);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        try {
            QueryTemplate template = this.templateDesignService.getTemplate(templateId);
            List fields = ((QueryFieldsPlugin)template.getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class)).getFields();
            String dataSourceCode = ((BaseInfoPlugin)template.getPluginByClass(BaseInfoPlugin.class)).getBaseInfo().getDatasourceCode();
            PageSqlExecConditionDTO sqlExecCondition = this.getSqlExecConditionDTO(template, paramMap, fields, dataSourceCode);
            resultVo = new ResultVO();
            if (isPage) {
                int count = this.userDefinedDao.getTotalCount(dataSourceCode, sqlExecCondition);
                resultVo.setTotalCount(Integer.valueOf(count));
                if (count == 0) {
                    ResultVO resultVO = resultVo;
                    return resultVO;
                }
                sqlExecCondition.setPageNumber(pageNum);
                sqlExecCondition.setPageSize(pageSize);
                sqlExecCondition.setIsPageQuery(true);
            } else {
                sqlExecCondition.setIsPageQuery(false);
            }
            List<Map<String, Object>> resultList = DCQueryBaseDataUtil.resultCodeToTitle(this.userDefinedDao.execSql(dataSourceCode, sqlExecCondition), fields);
            resultVo.setDataList(this.getInterceptorResult(((QueryRelatePlugin)template.getPluginByName(PluginEnum.queryRelate.name(), QueryRelatePlugin.class)).getRelateQuerys(), resultList));
        }
        catch (Exception e) {
            throw new DefinedQueryRuntimeException((Throwable)e);
        }
        finally {
            try {
                platformTransactionManager.rollback(transactionStatus);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return resultVo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ResultVO execSql(QueryParamVO queryParamVO) {
        ResultVO resultVo = new ResultVO();
        PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager)ApplicationContextRegister.getBean(PlatformTransactionManager.class);
        TransactionDefinition transactionDefinition = (TransactionDefinition)ApplicationContextRegister.getBean(TransactionDefinition.class);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        try {
            QueryUtils.transformFrontQueryTemplate(queryParamVO);
            QueryTemplate template = queryParamVO.getQueryTemplate();
            Map paramMap = queryParamVO.getParams();
            List fields = ((QueryFieldsPlugin)template.getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class)).getFields();
            String dataSourceCode = ((BaseInfoPlugin)template.getPluginByClass(BaseInfoPlugin.class)).getBaseInfo().getDatasourceCode();
            PageSqlExecConditionDTO sqlExecCondition = this.getSqlExecConditionDTO(template, paramMap, fields, dataSourceCode);
            PageSqlExecConditionDTO countSqlCondition = new PageSqlExecConditionDTO();
            countSqlCondition.setArgs(sqlExecCondition.getArgs());
            countSqlCondition.setSql(sqlExecCondition.getSql());
            this.initGroup(queryParamVO, countSqlCondition, dataSourceCode);
            int count = this.userDefinedDao.getTotalCount(dataSourceCode, countSqlCondition);
            resultVo.setTotalCount(Integer.valueOf(count));
            if (count == 0) {
                this.clearTempTable(dataSourceCode, sqlExecCondition.getSn());
                ResultVO resultVO = resultVo;
                return resultVO;
            }
            List<TemplateFieldSettingVO> calcFields = this.initGroup(queryParamVO, sqlExecCondition, dataSourceCode);
            this.initSort(queryParamVO, sqlExecCondition, dataSourceCode);
            if (Objects.nonNull(queryParamVO.getPageNum()) && Objects.nonNull(queryParamVO.getPageSize())) {
                sqlExecCondition.setPageNumber(queryParamVO.getPageNum());
                sqlExecCondition.setPageSize(queryParamVO.getPageSize());
                sqlExecCondition.setIsPageQuery(true);
            } else {
                sqlExecCondition.setIsPageQuery(false);
            }
            List<Map<String, Object>> resultList = DCQueryBaseDataUtil.resultCodeToTitle(this.userDefinedDao.execSql(dataSourceCode, sqlExecCondition), fields);
            fields.forEach(fieldSettingVO -> {
                if (StringUtils.hasText(fieldSettingVO.getExpression()) && StringUtils.hasText(fieldSettingVO.getGatherType()) && fieldSettingVO.getGatherType().equals(GatherTypeEnum.NO_AGREGATE.getTypeName())) {
                    calcFields.add((TemplateFieldSettingVO)fieldSettingVO);
                }
            });
            if (!CollectionUtils.isEmpty(calcFields)) {
                Map<String, IExpression> fieldExpressionMap = this.getFieldExpressionMap(calcFields, template);
                for (Map<String, Object> row : resultList) {
                    Map<String, Object> originDataMap = QueryUtils.getMap(row.get("__ORIGIN_DATA_MAP__"));
                    SqlQueryServiceImpl.calcSumExpression(template, originDataMap, calcFields, row, fieldExpressionMap);
                }
            }
            resultVo.setDataList(this.getInterceptorResult(((QueryRelatePlugin)template.getPluginByName(PluginEnum.queryRelate.name(), QueryRelatePlugin.class)).getRelateQuerys(), resultList));
        }
        finally {
            try {
                platformTransactionManager.rollback(transactionStatus);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return resultVo;
    }

    @Override
    public ResultVO execSqlForTotal(String templateId, Map<String, Object> paramMap) {
        PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager)ApplicationContextRegister.getBean(PlatformTransactionManager.class);
        TransactionDefinition transactionDefinition = (TransactionDefinition)ApplicationContextRegister.getBean(TransactionDefinition.class);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        try {
            QueryTemplate template = this.templateDesignService.getTemplate(templateId);
            List fields = ((QueryFieldsPlugin)template.getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class)).getFields();
            String dataSourceCode = ((BaseInfoPlugin)template.getPluginByClass(BaseInfoPlugin.class)).getBaseInfo().getDatasourceCode();
            PageSqlExecConditionDTO sqlExecCondition = this.getSqlExecConditionDTO(template, paramMap, fields, dataSourceCode);
            ResultVO resultVo = new ResultVO();
            int count = this.userDefinedDao.getTotalCount(dataSourceCode, sqlExecCondition);
            resultVo.setTotalCount(Integer.valueOf(count));
            ResultVO resultVO = resultVo;
            return resultVO;
        }
        catch (Exception e) {
            throw new DefinedQueryRuntimeException((Throwable)e);
        }
        finally {
            try {
                platformTransactionManager.rollback(transactionStatus);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void initSort(QueryParamVO queryParamVO, PageSqlExecConditionDTO sqlExecCondition, String dataSourceCode) {
        if (!CollectionUtils.isEmpty(queryParamVO.getSortFields())) {
            List<String> sorts = queryParamVO.getSortFields().stream().filter(querySortVO -> querySortVO.getSort().equalsIgnoreCase("asc") || querySortVO.getSort().equalsIgnoreCase("desc")).map(querySortVO -> "sortTableName." + this.getDoubleQuotesString(querySortVO.getName(), dataSourceCode) + " " + querySortVO.getSort()).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(sorts)) {
                return;
            }
            SQL newSql = new SQL();
            ((SQL)newSql.SELECT("*")).FROM("(" + sqlExecCondition.getSql() + ") sortTableName");
            sorts.forEach(arg_0 -> ((SQL)newSql).ORDER_BY(arg_0));
            sqlExecCondition.setSql(newSql.toString());
        }
    }

    private List<TemplateFieldSettingVO> initGroup(QueryParamVO queryParamVO, PageSqlExecConditionDTO sqlExecCondition, String dataSourceCode) {
        QueryFieldsPlugin fieldsPlugin = (QueryFieldsPlugin)queryParamVO.getQueryTemplate().getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class);
        if (CollectionUtils.isEmpty(queryParamVO.getGroupFields())) {
            return this.getCalcFields(fieldsPlugin.getFields());
        }
        List expandFields = queryParamVO.getExpandFields().stream().map(QueryExpandField::getFieldName).collect(Collectors.toList());
        List groupFields = queryParamVO.getGroupFields().stream().map(QueryGroupField::getFieldName).collect(Collectors.toList());
        Map<String, Object> expandFieldValeMap = queryParamVO.getExpandFields().stream().collect(Collectors.toMap(QueryExpandField::getFieldName, o -> {
            if (ObjectUtils.isEmpty(o.getFieldValue())) {
                return "";
            }
            return o.getFieldValue();
        }));
        Map<String, TemplateFieldSettingVO> fieldSettingVOMap = fieldsPlugin.getFields().stream().collect(Collectors.toMap(TemplateFieldSettingVO::getName, o -> o));
        List<TemplateFieldSettingVO> sortQueryFields = fieldsPlugin.getFields().stream().sorted((o1, o2) -> {
            int index1 = groupFields.indexOf(o1.getName());
            int index2 = groupFields.indexOf(o2.getName());
            if (index1 == -1) {
                index1 = Integer.MAX_VALUE;
            }
            if (index2 == -1) {
                index2 = Integer.MAX_VALUE;
            }
            return Integer.compare(index1, index2);
        }).collect(Collectors.toList());
        List<TemplateFieldSettingVO> calcFields = this.getCalcFields(sortQueryFields);
        String useGroupField = "";
        int groupFieldIndex = CollectionUtils.isEmpty(expandFields) ? 0 : expandFields.size();
        boolean allExpand = expandFields.size() == groupFields.size();
        LinkedList<String> emptyFields = new LinkedList<String>();
        LinkedList<TemplateFieldSettingVO> collSpanFields = new LinkedList<TemplateFieldSettingVO>();
        LinkedList<String> useParamFields = new LinkedList<String>();
        for (int i = 0; i < sortQueryFields.size(); ++i) {
            if (i < groupFieldIndex) {
                useParamFields.add(sortQueryFields.get(i).getName());
                continue;
            }
            if (i == groupFieldIndex && i < groupFields.size()) {
                useGroupField = sortQueryFields.get(i).getName();
                continue;
            }
            if (calcFields.contains(sortQueryFields.get(i))) continue;
            emptyFields.add(sortQueryFields.get(i).getName());
            collSpanFields.add(sortQueryFields.get(i));
        }
        SQL newSql = new SQL();
        ArrayList<TemplateFieldSettingVO> useCalcFields = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if (allExpand) {
            List fields = calcFields.stream().map(TemplateFieldSettingVO::getName).filter(col -> !useParamFields.contains(col)).collect(Collectors.toList());
            fields.addAll(emptyFields);
            sb.append(fields.stream().map(columnName -> this.getDoubleQuotesString((String)columnName, dataSourceCode)).collect(Collectors.joining(", ")));
        } else {
            sb.append(emptyFields.stream().map(s -> " ''  as " + this.getDoubleQuotesString((String)s, dataSourceCode)).collect(Collectors.joining(", ")));
            String finalUseGroupField = useGroupField;
            useCalcFields = calcFields.stream().filter(col -> !useParamFields.contains(col.getName()) && !finalUseGroupField.equalsIgnoreCase(col.getName())).collect(Collectors.toList());
            String calcColSql = this.getCalcColSql(useCalcFields, "autogroup", dataSourceCode);
            if (StringUtils.hasText(calcColSql)) {
                newSql.SELECT(calcColSql);
            }
            sqlExecCondition.setCollSpanColumns(collSpanFields);
        }
        if (!CollectionUtils.isEmpty(useParamFields)) {
            useParamFields.forEach(columnKey -> {
                SQL cfr_ignored_0 = (SQL)newSql.SELECT(" '" + expandFieldValeMap.get(columnKey) + "' as " + this.getDoubleQuotesString((String)columnKey, dataSourceCode) + " ");
            });
        }
        if (StringUtils.hasText(useGroupField) && !this.existInCalcField(useCalcFields, useGroupField)) {
            newSql.SELECT(this.getDoubleQuotesString(useGroupField, dataSourceCode));
            newSql.GROUP_BY(this.getDoubleQuotesString(useGroupField, dataSourceCode));
        }
        if (StringUtils.hasText(sb.toString())) {
            newSql.SELECT(sb.toString());
        }
        newSql.FROM("(" + sqlExecCondition.getSql() + ") autogroup ");
        LinkedList<Object> newArgs = new LinkedList<Object>();
        Object[] oldArgs = sqlExecCondition.getArgs();
        for (String paramField : useParamFields) {
            Optional<QueryExpandField> first = queryParamVO.getExpandFields().stream().filter(queryExpandField -> queryExpandField.getFieldName().equalsIgnoreCase(paramField)).findFirst();
            if (first.isPresent()) {
                if (first.get().getFieldValue() == null) {
                    ((SQL)newSql.AND()).WHERE(this.getDoubleQuotesString(paramField, dataSourceCode) + " is null ");
                    continue;
                }
                TemplateFieldSettingVO fieldSettingVO = fieldSettingVOMap.get(first.get().getFieldName());
                if (fieldSettingVO.getDataType().equalsIgnoreCase(ParamTypeEnum.DATE.getTypeName())) {
                    newArgs.add(DateUtils.parse(String.valueOf(first.get().getFieldValue()), "yyyy-MM-dd HH:mm:ss"));
                } else {
                    newArgs.add(first.get().getFieldValue());
                }
                ((SQL)newSql.AND()).WHERE(this.getDoubleQuotesString(paramField, dataSourceCode) + " = ? ");
                continue;
            }
            throw new DefinedQueryRuntimeException("group field value can not be null");
        }
        if (!CollectionUtils.isEmpty(newArgs)) {
            Object[] combinedArr = Stream.concat(Arrays.stream(oldArgs), Arrays.stream(newArgs.toArray())).toArray();
            sqlExecCondition.setArgs(combinedArr);
        }
        sqlExecCondition.setSql(newSql.toString());
        return useCalcFields;
    }

    private String getDoubleQuotesString(String columnName, String dataSourceCode) {
        String dbType = DB_TYPE_MAP.get(dataSourceCode);
        if (!StringUtils.hasText(dbType)) {
            if (dataSourceCode.equalsIgnoreCase("current")) {
                dbType = VaMapperConfig.getDbType();
            } else {
                DataSourceInfoVO dataSourceInfoByCode = this.dataSourceInfoDao.getDataSourceInfoByCode(dataSourceCode);
                dbType = dataSourceInfoByCode.getDataBaseType();
            }
            DB_TYPE_MAP.put(dataSourceCode, dbType);
        }
        if (this.isContainChinese(columnName) && !"mysql".equalsIgnoreCase(dbType)) {
            return "\"" + columnName + "\"";
        }
        return columnName;
    }

    private boolean existInCalcField(List<TemplateFieldSettingVO> fields, String useGroupField) {
        Optional<TemplateFieldSettingVO> any = fields.stream().filter(templateFieldSettingVO -> templateFieldSettingVO.getGatherType().equals(GatherTypeEnum.AVG.getTypeName()) || templateFieldSettingVO.getGatherType().equals(GatherTypeEnum.SUM.getTypeName()) || templateFieldSettingVO.getGatherType().equals(GatherTypeEnum.MAXIMUM.getTypeName()) || templateFieldSettingVO.getGatherType().equals(GatherTypeEnum.MINIMUM.getTypeName())).filter(templateFieldSettingVO -> templateFieldSettingVO.getName().equalsIgnoreCase(useGroupField)).findAny();
        return any.isPresent();
    }

    private void clearTempTable(String dataSourceCode, String sn) {
        DataSourceInfoVO dataSourceInfoByCode;
        if (!DataSourceEnum.CURRENT.getName().equals(dataSourceCode) && !Boolean.TRUE.equals((dataSourceInfoByCode = this.dataSourceInfoDao.getDataSourceInfoByCode(dataSourceCode)).getEnableTempTable())) {
            return;
        }
        this.userDefinedDao.clearTempTable(dataSourceCode, sn);
    }

    public List<Map<String, Object>> getInterceptorResult(List<TemplateRelateQueryVO> relateQueryVOList, List<Map<String, Object>> resultList) {
        QueryResultInterceptor queryResultInterceptor;
        if (resultList == null || resultList.isEmpty()) {
            return resultList;
        }
        if (relateQueryVOList == null || relateQueryVOList.isEmpty()) {
            return resultList;
        }
        HashMap<TemplateRelateQueryVO, QueryResultInterceptor> tempInterceptor = new HashMap<TemplateRelateQueryVO, QueryResultInterceptor>();
        for (TemplateRelateQueryVO relateQueryVO : relateQueryVOList) {
            queryResultInterceptor = this.queryResultInterceptGather.getQueryResultInterceptor(relateQueryVO.getProcessorName());
            if (queryResultInterceptor == null || !queryResultInterceptor.isNeedIntercept(relateQueryVO).booleanValue()) continue;
            TemplateRelateQueryVO tempRelateQuery = new TemplateRelateQueryVO();
            BeanUtils.copyProperties(relateQueryVO, tempRelateQuery);
            tempInterceptor.put(tempRelateQuery, queryResultInterceptor);
        }
        if (tempInterceptor.isEmpty()) {
            return resultList;
        }
        for (TemplateRelateQueryVO relateQueryVO : tempInterceptor.keySet()) {
            queryResultInterceptor = (QueryResultInterceptor)tempInterceptor.get(relateQueryVO);
            for (Map<String, Object> result : resultList) {
                HashMap<String, Object> tempResult = new HashMap<String, Object>(result);
                Map intercept = queryResultInterceptor.intercept(tempResult, relateQueryVO);
                if (intercept == null || intercept.isEmpty()) continue;
                for (String fieldName : intercept.keySet()) {
                    if (result.containsKey(fieldName)) continue;
                    result.put(fieldName, intercept.get(fieldName));
                }
            }
        }
        return resultList;
    }

    @Override
    public FetchQueryResultVO getFetchResult(String templateCode, Map<String, Object> paramMap) {
        TemplateInfoVO templateInfo = this.queryTemplateInfoDao.getTemplatesByCode(templateCode);
        QueryTemplate template = this.templateDesignService.getTemplate(templateInfo.getId());
        String dataSourceCode = templateInfo.getDatasourceCode();
        PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager)ApplicationContextRegister.getBean(PlatformTransactionManager.class);
        TransactionDefinition transactionDefinition = (TransactionDefinition)ApplicationContextRegister.getBean(TransactionDefinition.class);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        FetchQueryResultVO resultVO = null;
        try {
            List fields = ((QueryFieldsPlugin)template.getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class)).getFields();
            PageSqlExecConditionDTO sqlExecCondition = this.getSqlExecConditionDTO(template, paramMap, fields, dataSourceCode);
            sqlExecCondition.setIsPageQuery(false);
            resultVO = new FetchQueryResultVO();
            LinkedHashMap<String, Integer> floatColumns = new LinkedHashMap<String, Integer>();
            for (int i = 0; i < fields.size(); ++i) {
                TemplateFieldSettingVO fieldSettingVO = (TemplateFieldSettingVO)fields.get(i);
                floatColumns.put(fieldSettingVO.getName(), i);
            }
            resultVO.setFloatColumns(floatColumns);
            resultVO.setRowDatas(this.userDefinedDao.getFetchResultRowDatas(dataSourceCode, sqlExecCondition));
        }
        catch (Exception e) {
            throw new DefinedQueryRuntimeException((Throwable)e);
        }
        finally {
            try {
                platformTransactionManager.rollback(transactionStatus);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return resultVO;
    }

    private PageSqlExecConditionDTO getSqlExecConditionDTO(QueryTemplate template, Map<String, Object> paramMap, List<TemplateFieldSettingVO> fields, String dataSourceCode) {
        List params = template.getDataSourceSet().getParams();
        Map<String, TemplateParamsVO> defineParamMap = this.templateDesignService.getTemplate(template.getId()).getDataSourceSet().getParams().stream().collect(Collectors.toMap(TemplateParamsVO::getName, o -> o, (v1, v2) -> v1));
        QueryTemplate bizTemplate = this.templateDesignService.getBizTemplate(template.getId());
        Map<String, TemplateParamsVO> bizParamMap = bizTemplate.getDataSourceSet().getParams().stream().collect(Collectors.toMap(TemplateParamsVO::getName, o -> o, (v1, v2) -> v1));
        Iterator<Map.Entry<String, Object>> iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            if (!defineParamMap.containsKey(entry.getKey())) {
                iterator.remove();
                continue;
            }
            TemplateParamsVO defineParam = defineParamMap.get(entry.getKey());
            if (defineParam.getVisibleFlag().booleanValue() || !StringUtils.hasText(defineParam.getDefaultValue())) continue;
            paramMap.put(entry.getKey(), bizParamMap.get(entry.getKey()).getDefaultValue());
        }
        this.transformDriverItems(params);
        this.checkRequiredParams(params, paramMap);
        String sql = template.getDataSourceSet().getDefineSql();
        Map<String, TemplateParamsVO> allDefineParams = params.stream().collect(Collectors.toMap(TemplateParamsVO::getName, o -> o, (v1, v2) -> v1));
        ArrayList<String> emptyParams = new ArrayList<String>();
        for (String key : allDefineParams.keySet()) {
            if (paramMap.containsKey(key) && !this.paramObjectIsNull(paramMap.get(key))) continue;
            emptyParams.add(key);
        }
        sql = QuerySqlParser.parserSql(sql, emptyParams);
        this.paramListSortBySqlParam(params, sql);
        LinkedList<Object> args = new LinkedList<Object>();
        this.paramsPretreatment(params, paramMap);
        boolean enableTempTable = true;
        int inParamValueMaxCount = 10;
        String sn = DCQueryUUIDUtil.getUUIDStr();
        if (!DataSourceEnum.CURRENT.getName().equals(dataSourceCode)) {
            DataSourceInfoVO dataSourceInfoByCode = this.dataSourceInfoDao.getDataSourceInfoByCode(dataSourceCode);
            enableTempTable = dataSourceInfoByCode.getEnableTempTable();
            inParamValueMaxCount = dataSourceInfoByCode.getInParamValueMaxCount();
        }
        TempTableParamDTO tempTableParamDTO = new TempTableParamDTO(sn, enableTempTable, inParamValueMaxCount);
        sql = this.argumentPreparedStatement(sql, params, paramMap, args, tempTableParamDTO, dataSourceCode);
        PageSqlExecConditionDTO sqlExecCondition = new PageSqlExecConditionDTO();
        sqlExecCondition.setSql(sql);
        sqlExecCondition.setArgs(args.toArray());
        sqlExecCondition.setFields(fields);
        sqlExecCondition.setSn(sn);
        sqlExecCondition.setEnableTempTable(enableTempTable);
        return sqlExecCondition;
    }

    private String argumentPreparedStatement(String srcSql, List<TemplateParamsVO> params, Map<String, Object> paramMap, List<Object> args, TempTableParamDTO tempTableParamDTO, String dataSourceCode) {
        if (CollectionUtils.isEmpty(params)) {
            return srcSql;
        }
        int paramSize = params.size();
        StringBuffer prepareSql = new StringBuffer(srcSql.length());
        for (int sqlIndex = 0; sqlIndex < srcSql.length(); ++sqlIndex) {
            char ch = srcSql.charAt(sqlIndex);
            if (!this.isStartChar(srcSql, sqlIndex)) {
                prepareSql.append(ch);
                continue;
            }
            boolean findParam = false;
            for (int paramIndex = 0; paramIndex < paramSize; ++paramIndex) {
                TemplateParamsVO templateParamsVO = params.get(paramIndex);
                String paramName = templateParamsVO.getName();
                if ("#".charAt(0) == ch) {
                    String paramValue;
                    if (!this.contains(srcSql, sqlIndex + 1, paramName = paramName + "#")) continue;
                    findParam = true;
                    String string = paramValue = paramMap.get(templateParamsVO.getName()) == null ? "" : paramMap.get(templateParamsVO.getName()).toString();
                    if (QuerySqlParser.checkInputInject(paramValue)) {
                        throw new DefinedQueryRuntimeException();
                    }
                    prepareSql.append(paramValue);
                    sqlIndex += paramName.length();
                    break;
                }
                if ("@".charAt(0) != ch || !this.checkTokenIsParamName(srcSql, sqlIndex + 1, paramName)) continue;
                findParam = true;
                try {
                    prepareSql.append(this.getQuerySqlByMode(templateParamsVO, paramMap, args, tempTableParamDTO, dataSourceCode));
                }
                catch (ParseException e) {
                    throw new DefinedQueryRuntimeException("\u5904\u7406\u67e5\u8be2\u6761\u4ef6\u51fa\u9519 !\n" + e);
                }
                sqlIndex += paramName.length();
                break;
            }
            if (findParam) continue;
            prepareSql.append(ch);
        }
        return prepareSql.toString();
    }

    private boolean contains(String srcSql, int beginIndex, String paramName) {
        if (beginIndex + paramName.length() > srcSql.length()) {
            return false;
        }
        return paramName.equalsIgnoreCase(srcSql.substring(beginIndex, beginIndex + paramName.length()));
    }

    private boolean checkTokenIsParamName(String srcSql, int beginIndex, String paramName) {
        if (beginIndex + paramName.length() > srcSql.length()) {
            return false;
        }
        String[] result = srcSql.substring(beginIndex).split(" ", 2);
        return paramName.equalsIgnoreCase(this.getFirstWord(result[0]));
    }

    private String getFirstWord(String string) {
        char c;
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < string.length() && !Character.isWhitespace(c = string.charAt(i)) && c != '\'' && c != '\"' && c != '}' && c != ')' && c != ',' && c != '|'; ++i) {
            word.append(c);
        }
        return word.toString();
    }

    private boolean isStartChar(String srcSql, int sqlIndex) {
        char ch = srcSql.charAt(sqlIndex);
        if ("#".charAt(0) == ch) {
            return true;
        }
        if ("@".charAt(0) != ch) {
            return false;
        }
        if (sqlIndex + 1 > srcSql.length()) {
            return false;
        }
        return "@".charAt(0) == srcSql.charAt(sqlIndex);
    }

    private void transformDriverItems(List<TemplateParamsVO> params) {
        if (CollectionUtils.isEmpty(params)) {
            return;
        }
        params.forEach(conditionColumn -> {
            ArrayList<String> driverIterms = new ArrayList<String>();
            String filterCondition = conditionColumn.getFilterCondition();
            if (StringUtils.hasText(filterCondition)) {
                String reg = "(\\[PARAM_\\w+\\])";
                Pattern p = Pattern.compile(reg);
                Matcher m = p.matcher(filterCondition.toUpperCase());
                while (m.find()) {
                    driverIterms.add(m.group().substring(9, m.group().length() - 1));
                }
                List<Object> driverCol = new ArrayList();
                if (!CollectionUtils.isEmpty(driverIterms)) {
                    driverCol = params.stream().filter(condition -> condition.getName().equalsIgnoreCase((String)driverIterms.get(0))).collect(Collectors.toList());
                }
                conditionColumn.setDriverItems(driverCol);
            }
        });
    }

    @Override
    public Map<String, Object> getSumRowData(QueryParamVO queryParamVO, boolean formatNumber) {
        QueryUtils.transformFrontQueryTemplate(queryParamVO);
        QueryTemplate template = queryParamVO.getQueryTemplate();
        TemplateInfoVO templateInfo = ((BaseInfoPlugin)template.getPluginByClass(BaseInfoPlugin.class)).getBaseInfo();
        Map paramMap = queryParamVO.getParams();
        return this.getSumRowDataByTemplate(paramMap, formatNumber, template, templateInfo);
    }

    @Override
    public Map<String, Object> getSumRowData(String templateId, Map<String, Object> paramMap, boolean formatNumber) {
        TemplateInfoVO templateInfo = this.queryTemplateInfoDao.getTemplatesById(templateId);
        QueryTemplate template = this.templateDesignService.getTemplate(templateInfo.getId());
        return this.getSumRowDataByTemplate(paramMap, formatNumber, template, templateInfo);
    }

    @Override
    public Map<String, Object> getSumRowDataUseQueryTemplate(String templateId, Map<String, Object> params) {
        QueryTemplate queryTemplate = (QueryTemplate)params.get("queryTemplate");
        params.remove("queryTemplate");
        Map paramMap = (Map)params.get("params");
        TemplateInfoVO templateInfo = this.queryTemplateInfoDao.getTemplatesById(templateId);
        QueryTemplate newQueryTemplate = this.templateDesignService.getTemplate(templateInfo.getId());
        this.dealQueryColumn(newQueryTemplate, queryTemplate);
        return this.getSumRowDataByTemplate(paramMap, false, newQueryTemplate, templateInfo);
    }

    private void dealQueryColumn(QueryTemplate newQueryTemplate, QueryTemplate queryTemplate) {
        if (Objects.isNull(newQueryTemplate) || Objects.isNull(queryTemplate)) {
            return;
        }
        QueryFieldsPlugin newQueryFieldsPlugin = (QueryFieldsPlugin)newQueryTemplate.getPluginByClass(QueryFieldsPlugin.class);
        QueryFieldsPlugin oldQueryFieldsPlugin = (QueryFieldsPlugin)queryTemplate.getPluginByClass(QueryFieldsPlugin.class);
        if (Objects.isNull(newQueryFieldsPlugin) || Objects.isNull(oldQueryFieldsPlugin)) {
            return;
        }
        List newFields = Optional.ofNullable(newQueryFieldsPlugin.getFields()).orElse(Collections.emptyList());
        List oldFields = Optional.ofNullable(oldQueryFieldsPlugin.getFields()).orElse(Collections.emptyList());
        block0: for (TemplateFieldSettingVO newField : newFields) {
            for (TemplateFieldSettingVO oldField : oldFields) {
                if (!Objects.equals(newField.getName(), oldField.getName())) continue;
                newField.setGatherType(oldField.getGatherType());
                continue block0;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Map<String, Object> getSumRowDataByTemplate(Map<String, Object> paramMap, boolean formatNumber, QueryTemplate template, TemplateInfoVO templateInfo) {
        Map<String, Object> ret;
        PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager)ApplicationContextRegister.getBean(PlatformTransactionManager.class);
        TransactionDefinition transactionDefinition = (TransactionDefinition)ApplicationContextRegister.getBean(TransactionDefinition.class);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        try {
            List fields = ((QueryFieldsPlugin)template.getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class)).getFields();
            List<TemplateFieldSettingVO> calcCol = fields.stream().filter(x -> Objects.nonNull(x.getGatherType())).filter(templateFieldSettingVO -> templateFieldSettingVO.getGatherType().equals(GatherTypeEnum.AVG.getTypeName()) || templateFieldSettingVO.getGatherType().equals(GatherTypeEnum.SUM.getTypeName()) || templateFieldSettingVO.getGatherType().equals(GatherTypeEnum.MAXIMUM.getTypeName()) || templateFieldSettingVO.getGatherType().equals(GatherTypeEnum.MINIMUM.getTypeName())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(calcCol)) {
                HashMap<String, Object> hashMap = new HashMap<String, Object>(0);
                return hashMap;
            }
            String alias = "caltable";
            String calColSql = this.getCalcColSql(calcCol, alias, templateInfo.getDatasourceCode());
            if (DCQueryStringHandle.isEmpty(calColSql)) {
                HashMap<String, Object> hashMap = new HashMap<String, Object>(0);
                return hashMap;
            }
            PageSqlExecConditionDTO sqlExecCondition = this.getSqlExecConditionDTO(template, paramMap, fields, templateInfo.getDatasourceCode());
            String finalSql = "select " + calColSql + " from ( " + sqlExecCondition.getSql() + " ) caltable";
            sqlExecCondition.setSql(QuerySqlInterceptorUtil.getInterceptorSqlString(finalSql));
            sqlExecCondition.setFields(calcCol);
            sqlExecCondition.setIsPageQuery(formatNumber);
            ret = this.userDefinedDao.calcTotalLine(templateInfo.getDatasourceCode(), sqlExecCondition);
            Map<String, Object> originDataMap = QueryUtils.getMap(ret.get("__ORIGIN_DATA_MAP__"));
            Map<String, IExpression> fieldExpressionMap = QueryExpressionUtil.getSumExpressionMap(fields, template);
            QueryExpressionUtil.calcSumFieldExpression(template, originDataMap, fields, ret, fieldExpressionMap);
        }
        finally {
            try {
                platformTransactionManager.rollback(transactionStatus);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return ret;
    }

    private static void calcSumExpression(QueryTemplate template, Map<String, Object> originDataMap, List<TemplateFieldSettingVO> fields, Map<String, Object> row, Map<String, IExpression> fieldExpressionMap) {
        QueryFormulaContext queryFormulaContext = new QueryFormulaContext(originDataMap, template);
        for (TemplateFieldSettingVO fieldSettingVO : fields) {
            if (!StringUtils.hasText(fieldSettingVO.getExpression())) continue;
            IExpression iExpression = fieldExpressionMap.get(fieldSettingVO.getName());
            Object value = Objects.isNull(iExpression) ? FormulaExecuteHandlerUtil.executeFormula(fieldSettingVO.getExpression(), ParamTypeEnum.NUMBER.getTypeName(), queryFormulaContext) : FormulaExecuteHandlerUtil.executeFormula(iExpression, ParamTypeEnum.NUMBER.getTypeName(), queryFormulaContext);
            if (ParamTypeEnum.NUMBER.getTypeName().equals(fieldSettingVO.getDataType())) {
                value = QueryUtils.getNumberValue(value, fieldSettingVO);
            }
            row.put(fieldSettingVO.getName(), value);
        }
    }

    private String getCalcColSql(List<TemplateFieldSettingVO> calcCol, String alias, String dbCode) {
        Assert.hasText(alias, "alias can not be empty");
        if (CollectionUtils.isEmpty(calcCol)) {
            return "";
        }
        StringBuilder sql = new StringBuilder();
        List<TemplateFieldSettingVO> fields = this.getCalcFields(calcCol);
        if (CollectionUtils.isEmpty(fields)) {
            return "";
        }
        for (int i = 0; i < fields.size(); ++i) {
            TemplateFieldSettingVO field = fields.get(i);
            String fieldName = field.getName();
            fieldName = this.getDoubleQuotesString(fieldName, dbCode);
            String queryFieldName = alias + "." + fieldName;
            if (field.getGatherType().equals(GatherTypeEnum.SUM.getTypeName())) {
                sql.append(" sum(").append(queryFieldName).append(") ").append(fieldName);
            } else if (field.getGatherType().equals(GatherTypeEnum.AVG.getTypeName())) {
                sql.append(" avg(COALESCE(").append(queryFieldName).append(",0)) ").append(fieldName);
            } else if (field.getGatherType().equals(GatherTypeEnum.MINIMUM.getTypeName())) {
                sql.append(" min(").append(queryFieldName).append(") ").append(fieldName);
            } else if (field.getGatherType().equals(GatherTypeEnum.MAXIMUM.getTypeName())) {
                sql.append(" max(").append(queryFieldName).append(") ").append(fieldName);
            }
            if (i >= fields.size() - 1) continue;
            sql.append(" , ");
        }
        return sql.toString();
    }

    private List<TemplateFieldSettingVO> getCalcFields(List<TemplateFieldSettingVO> fieldSettingVOS) {
        return fieldSettingVOS.stream().filter(templateFieldSettingVO -> templateFieldSettingVO.getGatherType().equals(GatherTypeEnum.AVG.getTypeName()) || templateFieldSettingVO.getGatherType().equals(GatherTypeEnum.SUM.getTypeName()) || templateFieldSettingVO.getGatherType().equals(GatherTypeEnum.MAXIMUM.getTypeName()) || templateFieldSettingVO.getGatherType().equals(GatherTypeEnum.MINIMUM.getTypeName())).collect(Collectors.toList());
    }

    private boolean isContainChinese(String str) {
        Matcher m = CHINESE_PATTERN.matcher(str);
        return m.find();
    }

    private void paramsPretreatment(List<TemplateParamsVO> params, Map<String, Object> paramMap) {
    }

    private void paramListSortBySqlParam(List<TemplateParamsVO> params, String sql) {
        int size = params.size();
        for (int i = 0; i < size; ++i) {
            TemplateParamsVO paramVO = params.get(i);
            String paramName = "@" + params.get(i).getName();
            List<Integer> paramIndexs = this.getStartIndexs(sql, paramName);
            for (int j = 0; j < paramIndexs.size(); ++j) {
                if (j == 0) {
                    params.get(i).setSortOrder(paramIndexs.get(0));
                    continue;
                }
                paramVO.setSortOrder(paramIndexs.get(j));
                params.add(paramVO);
            }
            if (paramVO.getSortOrder() != null) continue;
            paramVO.setSortOrder(Integer.valueOf(i));
        }
        Collections.sort(params, Comparator.comparing(TemplateParamsVO::getSortOrder));
    }

    private void checkRequiredParams(List<TemplateParamsVO> params, Map<String, Object> paramMap) {
        ArrayList<String> errorNullParamList = new ArrayList<String>();
        for (TemplateParamsVO param : params) {
            ParamTypeEnum paramTypeEnum;
            Object object = paramMap.get(param.getName());
            if ("AdvancedSelect".equals(param.getShowType()) && !ObjectUtils.isEmpty(object)) {
                String expression;
                List<String> queryParamsValue;
                String after;
                String before;
                List range;
                Map flterParam;
                StringBuilder modifiedObjectcode;
                String objectcode;
                List value;
                List advanceFilterParam = (List)object;
                ArrayList<Map> equalParam = new ArrayList<Map>();
                ArrayList<Map> notEqualParam = new ArrayList<Map>();
                ArrayList<Map> rangeParam = new ArrayList<Map>();
                ArrayList<Map> rangeExcludeParam = new ArrayList<Map>();
                for (Map filterParam : advanceFilterParam) {
                    String filter = (String)filterParam.get("filter");
                    if ("equal".equals(filter)) {
                        equalParam.add(filterParam);
                        continue;
                    }
                    if ("notEqual".equals(filter)) {
                        notEqualParam.add(filterParam);
                        continue;
                    }
                    if ("range".equals(filter)) {
                        rangeParam.add(filterParam);
                        continue;
                    }
                    if (!"rangeExclude".equals(filter)) continue;
                    rangeExcludeParam.add(filterParam);
                }
                StringBuilder reFDataFilter = new StringBuilder();
                if (!equalParam.isEmpty() || !rangeParam.isEmpty()) {
                    reFDataFilter.append("(");
                }
                if (!equalParam.isEmpty()) {
                    reFDataFilter.append("(");
                    for (int j = 0; j < equalParam.size(); ++j) {
                        Map filterParam = (Map)equalParam.get(j);
                        value = (List)filterParam.get("value");
                        objectcode = (String)value.get(0);
                        if (StringUtils.hasText(objectcode) && (objectcode.charAt(0) == '*' || objectcode.charAt(objectcode.length() - 1) == '*')) {
                            modifiedObjectcode = new StringBuilder(objectcode);
                            if (modifiedObjectcode.charAt(0) == '*') {
                                modifiedObjectcode.setCharAt(0, '%');
                            }
                            if (modifiedObjectcode.charAt(modifiedObjectcode.length() - 1) == '*') {
                                modifiedObjectcode.setCharAt(modifiedObjectcode.length() - 1, '%');
                            }
                            reFDataFilter.append("[OBJECTCODE] like '");
                            reFDataFilter.append((CharSequence)modifiedObjectcode);
                        } else {
                            reFDataFilter.append("[OBJECTCODE] = '");
                            reFDataFilter.append(objectcode);
                        }
                        reFDataFilter.append("'");
                        if (j >= equalParam.size() - 1) continue;
                        reFDataFilter.append(" or ");
                    }
                    reFDataFilter.append(")");
                }
                if (!rangeParam.isEmpty()) {
                    if (!equalParam.isEmpty()) {
                        reFDataFilter.append(" or ");
                    }
                    reFDataFilter.append("(");
                    for (int j = 0; j < rangeParam.size(); ++j) {
                        flterParam = (Map)rangeParam.get(j);
                        range = (List)flterParam.get("value");
                        before = (String)range.get(0);
                        after = (String)range.get(1);
                        if (StringUtils.hasText(before)) {
                            reFDataFilter.append("'").append(before).append("' <=[OBJECTCODE]");
                        }
                        if (StringUtils.hasText(after)) {
                            if (StringUtils.hasText(before)) {
                                reFDataFilter.append(" and ");
                            }
                            reFDataFilter.append("[OBJECTCODE] <= '").append(after).append("'");
                        }
                        if (j >= rangeParam.size() - 1) continue;
                        reFDataFilter.append(" or ");
                    }
                    reFDataFilter.append(")");
                }
                if (!equalParam.isEmpty() || !rangeParam.isEmpty()) {
                    reFDataFilter.append(")");
                }
                if (!notEqualParam.isEmpty() || !rangeExcludeParam.isEmpty()) {
                    if (!equalParam.isEmpty() || !rangeParam.isEmpty()) {
                        reFDataFilter.append(" and ");
                    }
                    reFDataFilter.append("(");
                }
                if (!notEqualParam.isEmpty()) {
                    reFDataFilter.append("(");
                    for (int j = 0; j < notEqualParam.size(); ++j) {
                        flterParam = (Map)notEqualParam.get(j);
                        value = (List)flterParam.get("value");
                        objectcode = (String)value.get(0);
                        if (StringUtils.hasText(objectcode) && (objectcode.charAt(0) == '*' || objectcode.charAt(objectcode.length() - 1) == '*')) {
                            modifiedObjectcode = new StringBuilder(objectcode);
                            if (modifiedObjectcode.charAt(0) == '*') {
                                modifiedObjectcode.setCharAt(0, '%');
                            }
                            if (modifiedObjectcode.charAt(modifiedObjectcode.length() - 1) == '*') {
                                modifiedObjectcode.setCharAt(modifiedObjectcode.length() - 1, '%');
                            }
                            reFDataFilter.append("!([OBJECTCODE] like '");
                            reFDataFilter.append((CharSequence)modifiedObjectcode);
                            reFDataFilter.append("')");
                        } else {
                            reFDataFilter.append("[OBJECTCODE] != '");
                            reFDataFilter.append(objectcode);
                            reFDataFilter.append("'");
                        }
                        if (j >= notEqualParam.size() - 1) continue;
                        reFDataFilter.append(" and ");
                    }
                    reFDataFilter.append(")");
                }
                if (!rangeExcludeParam.isEmpty()) {
                    if (!notEqualParam.isEmpty()) {
                        reFDataFilter.append(" and ");
                    }
                    reFDataFilter.append("(");
                    for (int j = 0; j < rangeExcludeParam.size(); ++j) {
                        flterParam = (Map)rangeExcludeParam.get(j);
                        range = (List)flterParam.get("value");
                        before = (String)range.get(0);
                        after = (String)range.get(1);
                        if (StringUtils.hasText(before)) {
                            reFDataFilter.append("'").append(before).append("' > [OBJECTCODE]");
                        }
                        if (StringUtils.hasText(after)) {
                            if (StringUtils.hasText(before)) {
                                reFDataFilter.append(" or ");
                            }
                            reFDataFilter.append("[OBJECTCODE] > '").append(after).append("'");
                        }
                        if (j >= rangeExcludeParam.size() - 1) continue;
                        reFDataFilter.append(" and ");
                    }
                    reFDataFilter.append(")");
                }
                if (!notEqualParam.isEmpty() || !rangeExcludeParam.isEmpty()) {
                    reFDataFilter.append(")");
                }
                if (StringUtils.hasText(param.getFilterCondition())) {
                    reFDataFilter.append(" and ").append("(").append(param.getFilterCondition()).append(")");
                }
                if (!(queryParamsValue = this.queryRefDataByFilter(expression = param.getRefTableName().toUpperCase().startsWith("MD_ORG") ? reFDataFilter.toString().replace("[OBJECTCODE]", "[ORGCODE]") : reFDataFilter.toString(), param)).isEmpty()) {
                    paramMap.put(param.getName(), queryParamsValue);
                } else {
                    paramMap.put(param.getName(), DCQueryUUIDUtil.getUUIDStr());
                }
                object = paramMap.get(param.getName());
            }
            if (this.paramObjectIsNull(object)) {
                if (param.isMustInput()) {
                    errorNullParamList.add(param.getTitle());
                    continue;
                }
                this.nullParamHandle(paramMap, param, params);
                continue;
            }
            if (StringUtils.hasText(param.getRefTableName())) {
                this.checkExistInAuth(object, paramMap, param, params);
            }
            if ((paramTypeEnum = ParamTypeEnum.val((String)param.getParamType())) == null) {
                throw new DefinedQueryRuntimeException("\u6682\u4e0d\u652f\u6301\u67e5\u8be2\u53c2\u6570[" + param.getTitle() + "]\u7684\u53c2\u6570\u7c7b\u578b[" + param.getParamType() + "]");
            }
            switch (paramTypeEnum) {
                case INTEGER: {
                    this.intParamFormat(paramMap, param, object);
                    break;
                }
                case BOOL: {
                    Boolean booleanValue = BooleanUtils.toBoolean((String)String.valueOf(object));
                    paramMap.put(param.getName(), this.toInt(booleanValue));
                    break;
                }
                case UUID: {
                    this.uuidParamFormat(paramMap, param, object);
                    break;
                }
                case DATE: 
                case DATE_TIME: {
                    paramMap.put(param.getName(), QueryUtils.convertTimeToShanghaiTime(String.valueOf(object)));
                    break;
                }
            }
        }
        if (!errorNullParamList.isEmpty()) {
            StringBuilder errorInfo = new StringBuilder();
            for (String errorNullParam : errorNullParamList) {
                errorInfo.append("\u201c").append(errorNullParam).append("\u201d").append("\uff0c");
            }
            errorInfo.deleteCharAt(errorInfo.length() - 1);
            throw new DefinedQueryRuntimeException("\u67e5\u8be2\u6761\u4ef6\u4e2d" + errorInfo + "\u5fc5\u586b");
        }
    }

    private List<String> queryRefDataByFilter(String filter, TemplateParamsVO templateParamsVO) {
        if (templateParamsVO.getRefTableName().startsWith("MD_ORG")) {
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.put("vaBizFormula", (Object)true);
            orgDTO.setCategoryname(templateParamsVO.getRefTableName());
            orgDTO.setAuthType(Boolean.TRUE.equals(templateParamsVO.getEnableAuth()) ? OrgDataOption.AuthType.ACCESS : OrgDataOption.AuthType.NONE);
            orgDTO.setExpression(filter);
            PageVO list = this.orgDataClient.list(orgDTO);
            if (list == null) {
                return new ArrayList<String>();
            }
            List rows = list.getRows();
            if (CollectionUtils.isEmpty(rows)) {
                return new ArrayList<String>();
            }
            return rows.stream().map(OrgDO::getOrgcode).collect(Collectors.toList());
        }
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName(templateParamsVO.getRefTableName());
        param.setExpression(filter);
        param.setAuthType(Boolean.TRUE.equals(templateParamsVO.getEnableAuth()) ? BaseDataOption.AuthType.ACCESS : BaseDataOption.AuthType.NONE);
        param.setStopflag(Integer.valueOf(-1));
        param.setTenantName(ShiroUtil.getTenantName());
        param.put("vaBizFormula", (Object)true);
        PageVO list = this.baseDataClient.list(param);
        if (list == null) {
            return new ArrayList<String>();
        }
        List rows = list.getRows();
        if (CollectionUtils.isEmpty(rows)) {
            return new ArrayList<String>();
        }
        return rows.stream().map(BaseDataDO::getObjectcode).collect(Collectors.toList());
    }

    private Object getPararmDefaultValueByParam(TemplateParamsVO param) {
        Object formulaEvaluateData = FormulaExecuteHandlerUtil.getFormulaEvaluateData(param.getDefaultValue(), param.getParamType());
        if (!QueryModeEnum.scope.getModeSign().equals(param.getMode())) {
            return formulaEvaluateData;
        }
        HashMap<String, Object> defaultMap = new HashMap<String, Object>();
        defaultMap.put("start", formulaEvaluateData);
        defaultMap.put("end", formulaEvaluateData);
        return defaultMap;
    }

    private int toInt(Object value) {
        if (value == null) {
            return 0;
        }
        if (value instanceof Integer) {
            return (Integer)value;
        }
        if (value instanceof Number) {
            return this.toInt(((Number)value).doubleValue());
        }
        if (value instanceof String) {
            return Integer.parseInt(value.toString());
        }
        if (value instanceof Character) {
            return ((Character)value).charValue();
        }
        if (value instanceof Boolean) {
            return (Boolean)value != false ? 1 : 0;
        }
        return 0;
    }

    private void nullParamHandle(Map<String, Object> paramMap, TemplateParamsVO param, List<TemplateParamsVO> params) {
        if (!StringUtils.hasText(param.getRefTableName())) {
            return;
        }
        if (StringUtils.hasText(param.getFilterCondition()) || Boolean.TRUE.equals(param.getEnableAuth())) {
            if (!QueryModeEnum.mutileData.getModeSign().equalsIgnoreCase(param.getMode())) {
                throw new DefinedQueryRuntimeException("\u67e5\u8be2\u6761\u4ef6\u4e2d\u7684 " + param.getName() + " \u5b57\u6bb5\u914d\u7f6e\u4e0d\u7b26\u5408\u89c4\u8303\uff0c\u5728\u542f\u7528\u6743\u9650/\u8fc7\u6ee4\u6761\u4ef6\u6709\u503c/\u9ed8\u8ba4\u503c\u6709\u8fc7\u6ee4/\u65f6\uff0c\u8bf7\u914d\u7f6e\u201c\u5355\u503c+\u5fc5\u586b\u201c\u6216\u8005\u201c\u591a\u503c+\u975e\u5fc5\u586b/\u5fc5\u586b");
            }
            List<String> paramValueList = DCQueryBaseDataUtil.listObjectCodeByTableNameAndAuth(paramMap, param, params);
            if (CollectionUtils.isEmpty(paramValueList)) {
                paramValueList = new ArrayList<String>();
                paramValueList.add(DCQueryUUIDUtil.getUUIDStr());
            }
            paramMap.put(param.getName(), paramValueList);
        }
    }

    private void checkExistInAuth(Object paramValue, Map<String, Object> paramMap, TemplateParamsVO templateParamsVO, List<TemplateParamsVO> params) {
        List<String> paramValueList;
        if (!StringUtils.hasText(templateParamsVO.getRefTableName())) {
            return;
        }
        if (!StringUtils.hasText(templateParamsVO.getFilterCondition()) && !Boolean.TRUE.equals(templateParamsVO.getEnableAuth())) {
            return;
        }
        QueryModeEnum queryModeEnum = QueryModeEnum.valueOf((String)templateParamsVO.getMode());
        if (queryModeEnum == QueryModeEnum.singleData) {
            ArrayList<String> list = new ArrayList<String>();
            list.add((String)paramValue);
            paramValueList = DCQueryBaseDataUtil.checkValuesExistByAuth(list, paramMap, templateParamsVO, params);
            if (!paramValueList.contains(paramValue)) {
                paramMap.put(templateParamsVO.getName(), DCQueryUUIDUtil.getUUIDStr());
            }
        }
        if (queryModeEnum == QueryModeEnum.mutileData) {
            ArrayList<String> valueList = new ArrayList<String>(this.valueToStringList(paramValue));
            paramValueList = DCQueryBaseDataUtil.checkValuesExistByAuth(valueList, paramMap, templateParamsVO, params);
            ArrayList finalList = new ArrayList();
            valueList.forEach(node -> {
                if (paramValueList.contains(node)) {
                    finalList.add(node);
                } else {
                    finalList.add(DCQueryUUIDUtil.getUUIDStr());
                }
            });
            paramMap.put(templateParamsVO.getName(), finalList);
        }
    }

    private void uuidParamFormat(Map<String, Object> paramMap, TemplateParamsVO param, Object object) {
        if (!QueryModeEnum.mutileData.getModeSign().equals(param.getMode().trim())) {
            try {
                paramMap.put(param.getName(), object.toString());
            }
            catch (IllegalArgumentException e) {
                logger.error("\u67e5\u8be2\u53c2\u6570" + param.getTitle() + "\u7684\u7c7b\u578b\u4e3aUUID\uff0c\u4f46\u662f\u8f93\u5165\u7684\u4e0d\u662fUUID\uff01", e);
                throw new DefinedQueryRuntimeException("\u67e5\u8be2\u53c2\u6570" + param.getTitle() + "\u7684\u7c7b\u578b\u4e3aUUID\uff0c\u4f46\u662f\u8f93\u5165\u7684\u4e0d\u662fUUID\uff01");
            }
        }
    }

    private void intParamFormat(Map<String, Object> paramMap, TemplateParamsVO param, Object object) {
        if (QueryModeEnum.singleData.getModeSign().equals(param.getMode().trim())) {
            try {
                paramMap.put(param.getName(), String.valueOf(Integer.parseInt(object.toString().trim())));
            }
            catch (NumberFormatException e) {
                throw new DefinedQueryRuntimeException("\u67e5\u8be2\u53c2\u6570" + param.getTitle() + "\u7684\u7c7b\u578b\u4e3a\u6574\u6570\uff0c\u4f46\u662f\u8f93\u5165\u7684\u4e0d\u662f\u6570\u5b57\uff01", (Throwable)e);
            }
        }
    }

    private boolean paramObjectIsNull(Object object) {
        if (object == null) {
            return true;
        }
        if (DCQueryStringHandle.isEmpty(object.toString())) {
            return true;
        }
        if (object instanceof List) {
            return ((List)object).isEmpty();
        }
        return false;
    }

    private String getQuerySqlByMode(TemplateParamsVO templateParamsVO, Map<String, Object> paramMap, List<Object> args, TempTableParamDTO tempTableParamDTO, String dataSourceCode) throws ParseException {
        String paramMapKey = templateParamsVO.getName();
        String mode = templateParamsVO.getMode().trim();
        Object value = paramMap.get(paramMapKey);
        if (value == null || DCQueryStringHandle.isEmpty(value.toString())) {
            return "";
        }
        if (QueryModeEnum.singleData.getModeSign().equals(mode)) {
            return this.singleDataQueryMode(templateParamsVO, args, value);
        }
        if (QueryModeEnum.mutileData.getModeSign().equals(mode)) {
            return this.mutileDataQueryMode(args, templateParamsVO, value, tempTableParamDTO, dataSourceCode);
        }
        return "";
    }

    private String singleDataQueryMode(TemplateParamsVO templateParamsVO, List<Object> args, Object srcValue) {
        String modeOperator = templateParamsVO.getModeOperator();
        ModeOperatorEnum modeOperatorEnum = ModeOperatorEnum.getByCode((String)modeOperator);
        Object value = srcValue;
        if (ParamTypeEnum.DATE.getTypeName().equals(templateParamsVO.getParamType()) || ParamTypeEnum.DATE_TIME.getTypeName().equals(templateParamsVO.getParamType())) {
            SimpleDateFormat dateFormat = this.getSimpleDateFormatByParamType(templateParamsVO.getParamType());
            try {
                if (Objects.nonNull(dateFormat)) {
                    value = new Timestamp(dateFormat.parse(value.toString()).getTime());
                }
            }
            catch (ParseException e) {
                throw new DefinedQueryRuntimeException("\u67e5\u8be2\u53c2\u6570" + srcValue + "\u683c\u5f0f\u5316\u65f6\u95f4\u5f02\u5e38", (Throwable)e);
            }
        }
        args.add(value);
        return " ? ";
    }

    private String mutileDataQueryMode(List<Object> args, TemplateParamsVO templateParamsVO, Object value, TempTableParamDTO tempTableParamDTO, String dataSourceCode) {
        String paramName = templateParamsVO.getName();
        List<String> valueList = this.valueToStringList(value);
        boolean isTempTable = tempTableParamDTO.isEnableTempTable() && valueList.size() > tempTableParamDTO.getInParamValueMaxCount();
        String queryKey = tempTableParamDTO.getSn() + paramName;
        String filedName = this.getTempFiledNameByParamType(templateParamsVO.getParamType());
        this.userDefinedDao.batchInsertTempTable(dataSourceCode, queryKey, filedName, valueList);
        if (!isTempTable) {
            args.addAll(valueList);
            return DCQuerySqlBuildUtil.getValueListStr(valueList.size());
        }
        return " ( select " + filedName + " from " + "DC_QUERY_TEMPTABLE" + " where queryKey = '" + queryKey + "' )";
    }

    private List<String> valueToStringList(Object value) {
        ArrayList<String> list;
        if (value instanceof List) {
            list = new ArrayList();
            for (Object o : (List)value) {
                list.add((String)o);
            }
        } else {
            String listString = value.toString();
            String[] split = listString.split(",");
            list = Arrays.asList(split);
        }
        return list;
    }

    private String getTempFiledNameByParamType(String paramType) {
        ParamTypeEnum paramTypeEnum = ParamTypeEnum.val((String)paramType);
        if (paramTypeEnum == null) {
            throw new DefinedQueryRuntimeException("\u4e0d\u652f\u6301\u7684\u53c2\u6570\u7c7b\u578b\uff1a" + paramType);
        }
        return paramTypeEnum.getTempTableField();
    }

    private Object getSqlParamValueByParamType(TemplateFieldSettingVO fieldSettingVO, Object srcValue) {
        Object retValue;
        String dataType = fieldSettingVO.getDataType();
        SimpleDateFormat dateFormat = this.getSimpleDateFormatByParamType(dataType);
        try {
            retValue = Objects.nonNull(dateFormat) ? new Timestamp(dateFormat.parse((String)srcValue).getTime()) : srcValue;
        }
        catch (ParseException e) {
            throw new DefinedQueryRuntimeException("\u67e5\u8be2\u53c2\u6570" + srcValue + "\u683c\u5f0f\u5316\u65f6\u95f4\u5f02\u5e38", (Throwable)e);
        }
        return retValue;
    }

    private SimpleDateFormat getSimpleDateFormatByParamType(String paramType) {
        SimpleDateFormat dateFormat = null;
        if (ParamTypeEnum.DATE.getTypeName().equals(paramType)) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        if (ParamTypeEnum.DATE_TIME.getTypeName().equals(paramType)) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return dateFormat;
    }

    public List<Integer> getStartIndexs(String text, String regexStr) {
        if (text == null || regexStr == null) {
            return null;
        }
        Pattern p = Pattern.compile(regexStr);
        Matcher m = p.matcher(text);
        ArrayList<Integer> result = new ArrayList<Integer>();
        while (m.find()) {
            result.add(m.start());
        }
        return result;
    }
}

