/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.va.query.sql.formula.QueryFormulaContext
 *  com.jiuqi.va.query.template.enumerate.ParamTypeEnum
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 */
package com.jiuqi.va.query.util;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.query.common.service.FormulaExecuteHandlerUtil;
import com.jiuqi.va.query.sql.formula.QueryFormulaContext;
import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.util.QueryUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class QueryExpressionUtil {
    private static final Logger logger = LoggerFactory.getLogger(QueryExpressionUtil.class);

    public static void calcSumFieldExpression(QueryTemplate template, Map<String, Object> originDataMap, List<TemplateFieldSettingVO> fields, Map<String, Object> row, Map<String, IExpression> fieldExpressionMap) {
        QueryFormulaContext queryFormulaContext = new QueryFormulaContext(originDataMap, template);
        for (TemplateFieldSettingVO fieldSettingVO : fields) {
            IExpression iExpression = fieldExpressionMap.get(fieldSettingVO.getName());
            if (Objects.isNull(iExpression)) continue;
            Object value = FormulaExecuteHandlerUtil.executeFormula(iExpression, ParamTypeEnum.NUMBER.getTypeName(), queryFormulaContext);
            if (ParamTypeEnum.NUMBER.getTypeName().equals(fieldSettingVO.getDataType())) {
                value = QueryUtils.getNumberValue(value, fieldSettingVO);
            }
            row.put(fieldSettingVO.getName(), value);
        }
    }

    public static Map<String, IExpression> getSumExpressionMap(List<TemplateFieldSettingVO> fields, QueryTemplate template) {
        if (CollectionUtils.isEmpty(fields)) {
            return Collections.emptyMap();
        }
        HashMap<String, IExpression> fieldExpressionMap = new HashMap<String, IExpression>();
        QueryFormulaContext queryFormulaContext = new QueryFormulaContext(null, template);
        for (TemplateFieldSettingVO fieldSettingVO : fields) {
            String expression = fieldSettingVO.getSumExpression();
            if (!StringUtils.hasText(expression)) {
                expression = fieldSettingVO.getExpression();
            }
            if (!StringUtils.hasText(expression)) continue;
            try {
                fieldExpressionMap.put(fieldSettingVO.getName(), FormulaExecuteHandlerUtil.parseIExpression(expression, queryFormulaContext));
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return fieldExpressionMap;
    }
}

