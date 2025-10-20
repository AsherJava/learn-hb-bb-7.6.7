/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.bde.bizmodel.execute.datamodel.FinBizModelLoaderGather
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.impl.dimension.service.AssistExtendDimService
 *  com.jiuqi.bde.bizmodel.impl.model.service.impl.FinBizModelManageServiceImpl
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.DimensionValue
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.ContextVariableParseUtil
 *  com.jiuqi.bde.floatmodel.client.vo.SeniorCustomPluginDataVO
 *  com.jiuqi.bde.floatmodel.impl.gather.FloatConfigHandler
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateCommonFormatEnum
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
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
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.floatmodel.plugin.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO;
import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.bde.bizmodel.execute.datamodel.FinBizModelLoaderGather;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.impl.dimension.service.AssistExtendDimService;
import com.jiuqi.bde.bizmodel.impl.model.service.impl.FinBizModelManageServiceImpl;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.DimensionValue;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.ContextVariableParseUtil;
import com.jiuqi.bde.floatmodel.client.vo.SeniorCustomPluginDataVO;
import com.jiuqi.bde.floatmodel.impl.gather.FloatConfigHandler;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateCommonFormatEnum;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

public class SeniorDataComposeFloatConfigHandler
implements FloatConfigHandler {
    @Autowired
    private FinBizModelManageServiceImpl bizModelService;
    @Autowired
    @Lazy
    private FinBizModelLoaderGather loaderGather;
    @Autowired
    private AssistExtendDimService assistExtendDimService;
    public static final String TMPL = "#%1$s#";

    public String getCode() {
        return "SENIOR_FETCHSOURCE";
    }

    public String getTitle() {
        return "\u9ad8\u7ea7\u4e1a\u52a1\u6a21\u578b";
    }

    public String getProdLine() {
        return "@bde";
    }

    public String getAppName() {
        return "bde-floatmodel";
    }

    public Integer getOrder() {
        return 2;
    }

    @Transactional(rollbackFor={Exception.class})
    public FetchFloatRowResult queryFloatRowDatas(FetchTaskContext fetchTaskContext, QueryConfigInfo queryConfigInfo) {
        if (queryConfigInfo == null) {
            return null;
        }
        SeniorCustomPluginDataVO customPluginDataVO = this.getCustomPluginDataVO(queryConfigInfo.getPluginData());
        if (customPluginDataVO == null || queryConfigInfo.getUsedFields() == null) {
            return null;
        }
        if (CollectionUtils.isEmpty((Collection)queryConfigInfo.getUsedFields())) {
            throw new BusinessRuntimeException("\u6d6e\u52a8\u884c\u53d6\u6570\u914d\u7f6e\u4e2d\u6ca1\u6709\u7528\u5230\u4efb\u4f55\u7ed3\u679c\u5217\u5b57\u6bb5,\u8bf7\u68c0\u67e5\u914d\u7f6e\u4fe1\u606f\uff01");
        }
        String seniorSql = customPluginDataVO.getSeniorSql();
        if (StringUtils.isEmpty((String)seniorSql)) {
            throw new BusinessRuntimeException("\u6d6e\u52a8\u884c\u53d6\u6570\u914d\u7f6e\u4e2d\u81ea\u5b9a\u4e49SQL\u4e3a\u7a7a,\u8bf7\u68c0\u67e5\u914d\u7f6e\u4fe1\u606f\uff01");
        }
        FinBizModelDTO bizModel = this.bizModelService.getByCode(customPluginDataVO.getFetchSourceCode());
        BalanceCondition condi = null;
        try {
            condi = new BalanceCondition(fetchTaskContext.getRequestTaskId(), fetchTaskContext.getUnitCode(), DateUtils.createFormatter((DateCommonFormatEnum)DateCommonFormatEnum.FULL_DIGIT_BY_DASH).parse(fetchTaskContext.getStartDateStr()), DateUtils.createFormatter((DateCommonFormatEnum)DateCommonFormatEnum.FULL_DIGIT_BY_DASH).parse(fetchTaskContext.getEndDateStr()), fetchTaskContext.getOrgMapping(), Boolean.valueOf(fetchTaskContext.getIncludeUncharged() == null || fetchTaskContext.getIncludeUncharged() != false));
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException("\u65e5\u671f\u683c\u5f0f\u8f6c\u5316\u5931\u8d25", (Throwable)e);
        }
        condi.setIncludeAdjustVchr(fetchTaskContext.getIncludeAdjustVchr());
        condi.setStartAdjustPeriod(fetchTaskContext.getStartAdjustPeriod());
        condi.setEndAdjustPeriod(fetchTaskContext.getEndAdjustPeriod());
        condi.setCallBackIp(fetchTaskContext.getCallBackAddress());
        condi.setComputationModel(bizModel.getComputationModelCode());
        ArrayList assTypeList = CollectionUtils.newArrayList();
        Map<String, String> dimensionMap = bizModel.getDimensionMap();
        Set assistExtendDimSet = this.assistExtendDimService.getAllStartAssistExtendDim().stream().map(AssistExtendDimVO::getCode).collect(Collectors.toSet());
        if (!bizModel.getComputationModelCode().equals(ComputationModelEnum.BALANCE.getCode())) {
            if (!bizModel.getComputationModelCode().equals(ComputationModelEnum.ASSBALANCE.getCode())) {
                dimensionMap = bizModel.getDimensionMap().entrySet().stream().filter(entry -> !assistExtendDimSet.contains(entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
            for (Map.Entry entry2 : dimensionMap.entrySet()) {
                Dimension dimension = new Dimension();
                dimension.setDimCode((String)entry2.getKey());
                dimension.setDimName((String)entry2.getValue());
                assTypeList.add(dimension);
            }
            condi.setAssTypeList((List)assTypeList);
        }
        if (!bizModel.getComputationModelCode().equals(ComputationModelEnum.VOUCHER.getCode())) {
            condi.setOtherEntity(fetchTaskContext.getOtherEntity());
            condi.setDimensionValueMap(StringUtils.isEmpty((String)fetchTaskContext.getDimensionSetStr()) ? new HashMap() : (Map)JsonUtils.readValue((String)fetchTaskContext.getDimensionSetStr(), (TypeReference)new TypeReference<Map<String, DimensionValue>>(){}));
        }
        condi.setBblX(fetchTaskContext.getBblx());
        condi.setRpUnitType(fetchTaskContext.getRpUnitType());
        seniorSql = seniorSql.replace(this.getTableName(bizModel.getName()), "#TABLENAME#");
        if (fetchTaskContext.getExtParam() != null && !fetchTaskContext.getExtParam().isEmpty()) {
            HashMap variableMap = CollectionUtils.newHashMap();
            for (Map.Entry extParamEntry : fetchTaskContext.getExtParam().entrySet()) {
                variableMap.put(ContextVariableParseUtil.getVariable((String)((String)extParamEntry.getKey())), extParamEntry.getValue());
            }
            seniorSql = ContextVariableParseUtil.parse((String)seniorSql, (Map)variableMap);
        }
        FetchFloatRowResult fetchFloatRowResult = this.loaderGather.getLoader(condi.getOrgMapping().getPluginType(), bizModel.getComputationModelCode()).seniorFloatQuery(condi, seniorSql);
        return fetchFloatRowResult;
    }

    public List<FetchQueryFiledVO> parseFloatRowFields(QueryConfigInfo queryConfigInfo) {
        ArrayList result = CollectionUtils.newArrayList();
        SeniorCustomPluginDataVO seniorcustomPluginDataVO = this.getCustomPluginDataVO(queryConfigInfo.getPluginData());
        if (seniorcustomPluginDataVO == null || StringUtils.isEmpty((String)seniorcustomPluginDataVO.getSeniorSql())) {
            return Collections.emptyList();
        }
        try {
            Statement parse = CCJSqlParserUtil.parse((String)seniorcustomPluginDataVO.getSeniorSql());
            Select select = (Select)parse;
            List selectItems = null;
            FinBizModelDTO bizModel = this.bizModelService.getByCode(seniorcustomPluginDataVO.getFetchSourceCode());
            Pattern pattern = Pattern.compile("#(.*?)#");
            Matcher matcher = pattern.matcher(seniorcustomPluginDataVO.getSeniorSql());
            Boolean flag = false;
            while (matcher.find()) {
                if (!bizModel.getName().equals(matcher.group(1))) {
                    throw new BusinessRuntimeException("\u8fd0\u7b97\u7c7b\u578bSQL\u903b\u8f91\u4e1a\u52a1\u6a21\u578b\u9519\u8bef\u6216\u4e0d\u552f\u4e00");
                }
                flag = true;
            }
            if (!flag.booleanValue()) {
                throw new BusinessRuntimeException("\u8fd0\u7b97\u7c7b\u578bSQL\u903b\u8f91\u4e1a\u52a1\u6a21\u578b\u4e3a\u7a7a");
            }
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
            Map fieldMap = this.bizModelService.getFetchTypesByCode(seniorcustomPluginDataVO.getFetchSourceCode());
            return selectItems.stream().map(item -> {
                if (!(item instanceof SelectExpressionItem)) {
                    throw new BusinessRuntimeException("\u89e3\u6790\u7ed3\u679c\u5217\u5931\u8d25\uff1a\u672a\u80fd\u89e3\u6790\u5b57\u6bb5\u3010" + item + "\u3011");
                }
                SelectExpressionItem expressionItem = (SelectExpressionItem)item;
                String columnName = SeniorDataComposeFloatConfigHandler.parseColumnName(expressionItem).toUpperCase();
                FetchQueryFiledVO fetchQueryFiledVO = new FetchQueryFiledVO();
                fetchQueryFiledVO.setName(columnName);
                fetchQueryFiledVO.setTitle(this.convertFieldTitle(fieldMap, columnName));
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

    private SeniorCustomPluginDataVO getCustomPluginDataVO(String pluginDataStr) {
        if (StringUtils.isEmpty((String)pluginDataStr)) {
            return null;
        }
        return (SeniorCustomPluginDataVO)JSONUtil.parseObject((String)pluginDataStr, SeniorCustomPluginDataVO.class);
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

    private String convertFieldTitle(Map<String, String> fieldMap, String columnName) {
        if (fieldMap.containsKey(columnName)) {
            return fieldMap.get(columnName);
        }
        if ("SUBJECTCODE".equals(columnName)) {
            return "\u79d1\u76ee";
        }
        if ("CURRENCYCODE".equals(columnName)) {
            return "\u5e01\u79cd";
        }
        if ("ORIENT".equals(columnName)) {
            return "\u79d1\u76ee\u65b9\u5411";
        }
        if ("CFITEMCODE".equals(columnName)) {
            return "\u73b0\u91d1\u6d41\u91cf\u9879\u76ee";
        }
        return columnName;
    }

    private String getTableName(String fetchSourceName) {
        return String.format(TMPL, fetchSourceName);
    }
}

