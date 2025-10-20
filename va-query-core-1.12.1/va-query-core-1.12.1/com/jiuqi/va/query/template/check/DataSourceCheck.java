/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.sql.enumerate.QueryModeEnum
 *  com.jiuqi.va.query.sql.formula.QueryFormulaHandler
 *  com.jiuqi.va.query.template.enumerate.MessageTypeEnum
 *  com.jiuqi.va.query.template.plugin.DataSourcePlugin
 *  com.jiuqi.va.query.template.vo.QueryCheckItemVO
 *  com.jiuqi.va.query.template.vo.QueryPluginCheckVO
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 */
package com.jiuqi.va.query.template.check;

import com.jiuqi.va.query.sql.enumerate.QueryModeEnum;
import com.jiuqi.va.query.sql.formula.QueryFormulaHandler;
import com.jiuqi.va.query.sql.parser.QuerySqlParser;
import com.jiuqi.va.query.template.check.QueryDesignCheck;
import com.jiuqi.va.query.template.enumerate.MessageTypeEnum;
import com.jiuqi.va.query.template.plugin.DataSourcePlugin;
import com.jiuqi.va.query.template.vo.QueryCheckItemVO;
import com.jiuqi.va.query.template.vo.QueryPluginCheckVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="dataSourceCheck")
public class DataSourceCheck
implements QueryDesignCheck {
    @Override
    public QueryPluginCheckVO checkPlugin(QueryTemplate queryTemplate) {
        QueryPluginCheckVO result = new QueryPluginCheckVO("dataSource");
        DataSourcePlugin dataSourcePlugin = (DataSourcePlugin)queryTemplate.getPluginByClass(DataSourcePlugin.class);
        String defineSql = dataSourcePlugin.getDefineSql();
        List params = dataSourcePlugin.getParams();
        if (!StringUtils.hasText(defineSql)) {
            result.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, "sql\u4e0d\u80fd\u4e3a\u7a7a", ""));
            return result;
        }
        List<Object> userVariables = new ArrayList();
        Map<Object, Object> userVarOperatorMap = new HashMap();
        try {
            userVariables = QuerySqlParser.getUserVariables(defineSql);
            userVarOperatorMap = QuerySqlParser.getUserVarOperator(defineSql);
        }
        catch (Exception e) {
            result.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, "sql\u8bed\u6cd5\u9519\u8bef", ""));
        }
        params.stream().map(TemplateParamsVO::getName).collect(Collectors.groupingBy(o -> o, Collectors.counting())).entrySet().stream().filter(o -> (Long)o.getValue() > 1L).map(Map.Entry::getKey).forEach(name -> result.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, "\u53c2\u6570" + name + "\u91cd\u590d\u5b9a\u4e49", "")));
        Map<String, TemplateParamsVO> stringTMap = params.stream().collect(Collectors.toMap(TemplateParamsVO::getName, o -> o, (o, o2) -> o2));
        List<Object> finalUserVariables = userVariables;
        Map<Object, Object> finalUserVarOperatorMap = userVarOperatorMap;
        params.forEach(paramsVO -> {
            if (!finalUserVariables.contains(paramsVO.getName())) {
                result.getResult().add(new QueryCheckItemVO(MessageTypeEnum.WARN, "\u53c2\u6570" + paramsVO.getName() + "\u5728sql\u4e2d\u672a\u4f7f\u7528", ""));
            }
            String refTableName = paramsVO.getRefTableName();
            Boolean unitCodeFlag = paramsVO.getUnitCodeFlag();
            if (Boolean.TRUE.equals(unitCodeFlag) && !refTableName.startsWith("MD_ORG")) {
                result.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, "\u53c2\u6570" + paramsVO.getName() + "\u662f\u7ec4\u7ec7\u673a\u6784\u9a71\u52a8\u5217\uff0c\u5173\u8054\u8868\u5fc5\u987b\u662f\u7ec4\u7ec7\u673a\u6784", ""));
            }
            QueryModeEnum queryModeEnum = QueryModeEnum.valueOf((String)paramsVO.getMode());
            ((List)Optional.ofNullable(finalUserVarOperatorMap.get("@" + paramsVO.getName())).orElse(new ArrayList())).forEach(modeEnum -> {
                if (modeEnum != queryModeEnum && modeEnum.equals((Object)QueryModeEnum.singleData)) {
                    result.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, "\u53c2\u6570" + paramsVO.getName() + "\u5728sql\u4e2d\u5b9a\u4e49\u4e86" + modeEnum.getModeName() + "\u6a21\u5f0f\uff0c\u4f46\u67e5\u8be2\u53c2\u6570\u4e2d\u4e3a" + queryModeEnum.getModeName() + "\u6a21\u5f0f", ""));
                }
            });
            if (!paramsVO.isMustInput() && StringUtils.hasText(paramsVO.getRefTableName()) && queryModeEnum.equals((Object)QueryModeEnum.singleData) && (StringUtils.hasText(paramsVO.getFilterCondition()) || paramsVO.getEnableAuth().booleanValue())) {
                result.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, "\u67e5\u8be2\u6761\u4ef6\u4e2d\u7684 " + paramsVO.getName() + " \u5b57\u6bb5\u914d\u7f6e\u4e0d\u7b26\u5408\u89c4\u8303\uff0c\u5728\u542f\u7528\u6743\u9650/\u8fc7\u6ee4\u6761\u4ef6\u6709\u503c/\u9ed8\u8ba4\u503c\u6709\u8fc7\u6ee4/\u65f6\uff0c\u8bf7\u914d\u7f6e\u201c\u5355\u503c+\u5fc5\u586b\u201c\u6216\u8005\u201c\u591a\u503c+\u975e\u5fc5\u586b/\u5fc5\u586b", ""));
            }
        });
        finalUserVariables.forEach(name -> {
            if (!stringTMap.containsKey(name)) {
                result.getResult().add(new QueryCheckItemVO(MessageTypeEnum.WARN, "\u7f3a\u5c11\u7684\u53c2\u6570\u5b9a\u4e49\uff1a" + name, ""));
            }
        });
        for (TemplateParamsVO param : params) {
            boolean check;
            QueryFormulaHandler formulaHandler = DCQuerySpringContextUtils.getBean(QueryFormulaHandler.class);
            String filterCondition = param.getFilterCondition();
            String defaultValue = param.getDefaultValue();
            if (!StringUtils.hasText(param.getRefTableName()) && StringUtils.hasText(filterCondition)) {
                result.getResult().add(new QueryCheckItemVO(MessageTypeEnum.WARN, "\u6761\u4ef6\u65e0\u5173\u8054\u8868\uff0c\u8fc7\u6ee4\u6761\u4ef6\u4e0d\u751f\u6548\uff1a" + param.getName(), ""));
            } else if (StringUtils.hasText(filterCondition) && !(check = formulaHandler.check(filterCondition))) {
                result.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, "\u8fc7\u6ee4\u6761\u4ef6\u8bed\u6cd5\u9519\u8bef\uff1a" + param.getName(), ""));
            }
            if (!StringUtils.hasText(defaultValue) || (check = formulaHandler.check(defaultValue))) continue;
            result.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, "\u9ed8\u8ba4\u503c\u516c\u5f0f\u8bed\u6cd5\u9519\u8bef\uff1a" + param.getName(), ""));
        }
        return result;
    }

    @Override
    public int order() {
        return 1;
    }
}

