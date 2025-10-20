/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.ContextVariableParseUtil
 *  com.jiuqi.bde.floatmodel.client.vo.CustomPluginDataVO
 *  com.jiuqi.bde.floatmodel.impl.gather.FloatConfigHandler
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  net.sf.jsqlparser.JSQLParserException
 *  net.sf.jsqlparser.expression.Alias
 *  net.sf.jsqlparser.parser.CCJSqlParserUtil
 *  net.sf.jsqlparser.schema.Column
 *  net.sf.jsqlparser.statement.Statement
 *  net.sf.jsqlparser.statement.select.PlainSelect
 *  net.sf.jsqlparser.statement.select.Select
 *  net.sf.jsqlparser.statement.select.SelectExpressionItem
 *  net.sf.jsqlparser.statement.select.SetOperationList
 */
package com.jiuqi.bde.floatmodel.plugin.handler;

import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.ContextVariableParseUtil;
import com.jiuqi.bde.floatmodel.client.vo.CustomPluginDataVO;
import com.jiuqi.bde.floatmodel.impl.gather.FloatConfigHandler;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.springframework.beans.factory.annotation.Autowired;

public class DefinedFloatRegionHandler
implements FloatConfigHandler {
    private static final String PRECAST_PARAM_PLACEHOLDER_STR = "#";
    @Autowired
    private DataSourceService dataSourceService;

    public String getCode() {
        return "CUSTOM_SQL";
    }

    public String getTitle() {
        return "\u81ea\u5b9a\u4e49SQL";
    }

    public String getProdLine() {
        return "@bde";
    }

    public String getAppName() {
        return "bde-floatmodel";
    }

    public Integer getOrder() {
        return 4;
    }

    public FetchFloatRowResult queryFloatRowDatas(FetchTaskContext fetchTaskContext, QueryConfigInfo queryConfigInfo) {
        if (queryConfigInfo == null) {
            return null;
        }
        CustomPluginDataVO customPluginDataVO = this.getCustomPluginDataVO(queryConfigInfo.getPluginData());
        if (customPluginDataVO == null || queryConfigInfo.getUsedFields() == null) {
            return null;
        }
        if (CollectionUtils.isEmpty((Collection)queryConfigInfo.getUsedFields())) {
            throw new BusinessRuntimeException("\u6d6e\u52a8\u884c\u53d6\u6570\u914d\u7f6e\u4e2d\u6ca1\u6709\u7528\u5230\u4efb\u4f55\u7ed3\u679c\u5217\u5b57\u6bb5,\u8bf7\u68c0\u67e5\u914d\u7f6e\u4fe1\u606f\uff01");
        }
        String definedSql = customPluginDataVO.getDefinedSql();
        if (StringUtils.isEmpty((String)definedSql)) {
            throw new BusinessRuntimeException("\u6d6e\u52a8\u884c\u53d6\u6570\u914d\u7f6e\u4e2d\u81ea\u5b9a\u4e49SQL\u4e3a\u7a7a,\u8bf7\u68c0\u67e5\u914d\u7f6e\u4fe1\u606f\uff01");
        }
        List queryFields = queryConfigInfo.getQueryFields();
        definedSql = this.replacePrecastParam(definedSql, fetchTaskContext);
        String dataSourceCode = fetchTaskContext.getOrgMapping().getDataSourceCode();
        BdeLogUtil.recordLog((String)fetchTaskContext.getRequestTaskId(), (String)"\u6d6e\u52a8\u884c\u89e3\u6790-\u81ea\u5b9a\u4e49SQL", (Object)new Object[]{dataSourceCode, queryFields}, (String)definedSql);
        FetchFloatRowResult resultVO = this.getRowDataList(definedSql, queryFields, dataSourceCode);
        LinkedHashMap<String, Integer> floatColumns = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < queryFields.size(); ++i) {
            floatColumns.put(((FloatQueryFieldVO)queryFields.get(i)).getName(), i);
        }
        resultVO.setFloatColumns(floatColumns);
        return resultVO;
    }

    private FetchFloatRowResult getRowDataList(String definedSql, List<FloatQueryFieldVO> queryFields, String dataSourceCode) {
        FetchFloatRowResult fetchFloatRowResult = new FetchFloatRowResult();
        HashMap<String, Integer> fieldTypeNoMap = new HashMap<String, Integer>();
        List<String[]> rowDataList = this.getResult(definedSql, queryFields, dataSourceCode, fieldTypeNoMap);
        HashMap fieldTypeMap = new HashMap();
        block4: for (Map.Entry entry : fieldTypeNoMap.entrySet()) {
            switch ((Integer)entry.getValue()) {
                case 4: {
                    fieldTypeMap.put(entry.getKey(), ColumnTypeEnum.INT);
                    continue block4;
                }
                case 2: 
                case 3: 
                case 6: 
                case 8: {
                    fieldTypeMap.put(entry.getKey(), ColumnTypeEnum.NUMBER);
                    continue block4;
                }
            }
            fieldTypeMap.put(entry.getKey(), ColumnTypeEnum.STRING);
        }
        fetchFloatRowResult.setFloatColumnsType(fieldTypeMap);
        fetchFloatRowResult.setRowDatas(rowDataList);
        return fetchFloatRowResult;
    }

    protected List<String[]> getResult(String definedSql, List<FloatQueryFieldVO> queryFields, String dataSourceCode, Map<String, Integer> fieldTypeNoMap) {
        List rowDataList = this.dataSourceService.query(dataSourceCode, definedSql, null, (rs, row) -> {
            String[] rowData = new String[queryFields.size()];
            for (int i = 0; i < queryFields.size(); ++i) {
                FloatQueryFieldVO field = (FloatQueryFieldVO)queryFields.get(i);
                rowData[i] = rs.getString(field.getName());
                fieldTypeNoMap.put(((FloatQueryFieldVO)queryFields.get(i)).getName(), rs.getMetaData().getColumnType(i + 1));
            }
            return rowData;
        });
        return rowDataList;
    }

    private String replacePrecastParam(String srcDefinedSql, FetchTaskContext fetchTaskContext) {
        if (!srcDefinedSql.contains(PRECAST_PARAM_PLACEHOLDER_STR)) {
            return srcDefinedSql;
        }
        return ContextVariableParseUtil.parse((String)srcDefinedSql, (FetchTaskContext)fetchTaskContext);
    }

    public List<FetchQueryFiledVO> parseFloatRowFields(QueryConfigInfo queryConfigInfo) {
        ArrayList<FetchQueryFiledVO> result = new ArrayList<FetchQueryFiledVO>();
        CustomPluginDataVO customPluginDataVO = this.getCustomPluginDataVO(queryConfigInfo.getPluginData());
        if (customPluginDataVO == null || StringUtils.isEmpty((String)customPluginDataVO.getDefinedSql())) {
            return Collections.emptyList();
        }
        try {
            Statement parse = CCJSqlParserUtil.parse((String)customPluginDataVO.getDefinedSql());
            Select select = (Select)parse;
            List selectItems = null;
            if (select.getSelectBody() instanceof SetOperationList) {
                SetOperationList operationList = (SetOperationList)select.getSelectBody();
                if (CollectionUtils.isEmpty((Collection)operationList.getSelects())) {
                    throw new BusinessRuntimeException("\u8fd0\u7b97\u7c7b\u578bSQL\u903b\u8f91\u6ca1\u6709\u5305\u542b\u67e5\u8be2\u4e3b\u4f53");
                }
                PlainSelect plainSelect = (PlainSelect)operationList.getSelects().get(0);
                selectItems = plainSelect.getSelectItems();
            } else {
                PlainSelect plainSelect = (PlainSelect)select.getSelectBody();
                selectItems = plainSelect.getSelectItems();
            }
            if (CollectionUtils.isEmpty((Collection)selectItems)) {
                return result;
            }
            return selectItems.stream().map(item -> {
                if (!(item instanceof SelectExpressionItem)) {
                    throw new BusinessRuntimeException("\u89e3\u6790\u7ed3\u679c\u5217\u5931\u8d25\uff1a\u672a\u80fd\u89e3\u6790\u5b57\u6bb5\u3010" + item + "\u3011");
                }
                SelectExpressionItem expressionItem = (SelectExpressionItem)item;
                String columnName = DefinedFloatRegionHandler.parseColumnName(expressionItem);
                FetchQueryFiledVO fetchQueryFiledVO = new FetchQueryFiledVO();
                fetchQueryFiledVO.setName(columnName);
                fetchQueryFiledVO.setTitle(columnName);
                fetchQueryFiledVO.setNeedFlag(true);
                return fetchQueryFiledVO;
            }).collect(Collectors.toList());
        }
        catch (JSQLParserException e) {
            if (e.getMessage().contains("Was")) {
                throw new BusinessRuntimeException("SQL\u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u8bf7\u68c0\u67e5:" + e.getMessage().substring(0, e.getMessage().indexOf("Was")), (Throwable)e);
            }
            throw new BusinessRuntimeException("SQL\u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u8bf7\u68c0\u67e5:" + e.getMessage(), (Throwable)e);
        }
    }

    private CustomPluginDataVO getCustomPluginDataVO(String pluginDataStr) {
        if (StringUtils.isEmpty((String)pluginDataStr)) {
            return null;
        }
        return (CustomPluginDataVO)JSONUtil.parseObject((String)pluginDataStr, CustomPluginDataVO.class);
    }

    private static String parseColumnName(SelectExpressionItem expressionItem) {
        Alias alias = expressionItem.getAlias();
        if (alias != null) {
            return alias.getName().contains("\"") ? alias.getName().replace("\"", "") : alias.getName();
        }
        if (expressionItem.getExpression() instanceof Column) {
            Column expression = (Column)expressionItem.getExpression();
            return expression.getColumnName();
        }
        return expressionItem.getExpression().toString();
    }
}

