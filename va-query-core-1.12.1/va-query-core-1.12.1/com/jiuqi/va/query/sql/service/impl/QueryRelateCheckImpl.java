/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.sql.formula.QueryFormulaContext
 *  com.jiuqi.va.query.sql.vo.QueryRelationCheckResultVO
 *  com.jiuqi.va.query.sql.vo.QueryRelationDTO
 *  com.jiuqi.va.query.template.enumerate.ParamTypeEnum
 *  com.jiuqi.va.query.template.plugin.QueryRelatePlugin
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateRelateQueryVO
 *  org.apache.shiro.util.StringUtils
 */
package com.jiuqi.va.query.sql.service.impl;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.query.common.service.FormulaExecuteHandlerUtil;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.sql.formula.QueryFormulaContext;
import com.jiuqi.va.query.sql.service.QueryRelateCheckService;
import com.jiuqi.va.query.sql.vo.QueryRelationCheckResultVO;
import com.jiuqi.va.query.sql.vo.QueryRelationDTO;
import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;
import com.jiuqi.va.query.template.plugin.QueryRelatePlugin;
import com.jiuqi.va.query.template.service.TemplateDesignService;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class QueryRelateCheckImpl
implements QueryRelateCheckService {
    private static final Logger logger = LoggerFactory.getLogger(QueryRelateCheckImpl.class);
    private TemplateDesignService templateDesignService;

    @Autowired
    public void setTemplateDesignService(TemplateDesignService templateDesignService) {
        this.templateDesignService = templateDesignService;
    }

    @Override
    public QueryRelationCheckResultVO checkRelateEnable(QueryRelationDTO relationVO, String templateId) {
        List relationIds = relationVO.getRelationIds();
        Map row = relationVO.getRow();
        QueryTemplate template = this.templateDesignService.getTemplate(templateId);
        QueryRelatePlugin relatePlugin = (QueryRelatePlugin)template.getPluginByClass(QueryRelatePlugin.class);
        List relateQuerys = relatePlugin.getRelateQuerys();
        if (CollectionUtils.isEmpty(relateQuerys)) {
            return new QueryRelationCheckResultVO(null, "\u672a\u627e\u5230\u8054\u67e5\u5904\u7406\u5668");
        }
        HashSet relationIdSet = new HashSet(relationIds);
        Map<String, TemplateRelateQueryVO> queryMap = relateQuerys.stream().filter(item -> relationIdSet.contains(item.getId())).collect(Collectors.toMap(TemplateRelateQueryVO::getId, item -> item, (a, b) -> a));
        if (CollectionUtils.isEmpty(queryMap)) {
            return new QueryRelationCheckResultVO(null, "\u672a\u627e\u5230\u8054\u67e5\u5904\u7406\u5668");
        }
        String warningText = null;
        Map prototypeDataMap = (Map)row.get("__ORIGIN_DATA_MAP__");
        String prototypeJsonStr = JSONUtil.toJSONString((Object)prototypeDataMap);
        for (String relationId : relationIdSet) {
            TemplateRelateQueryVO relateQueryVO = queryMap.get(relationId);
            String checkExpression = relateQueryVO.getCheckExpression();
            if (!StringUtils.hasText((String)checkExpression)) {
                return new QueryRelationCheckResultVO(relationId, null);
            }
            Map copiedMap = JSONUtil.parseMap((String)prototypeJsonStr);
            boolean result = this.executeExpression(copiedMap, template, checkExpression);
            if (result) {
                return new QueryRelationCheckResultVO(relationId, null);
            }
            warningText = StringUtils.hasText((String)relateQueryVO.getRelateCheckMessage()) ? relateQueryVO.getRelateCheckMessage() : "\u6761\u4ef6\u4e0d\u6ee1\u8db3\uff0c\u65e0\u6cd5\u7a7f\u900f";
        }
        if (relationIdSet.size() > 1 || warningText == null) {
            warningText = "\u5f53\u524d\u884c\u6570\u636e\u4e0d\u6ee1\u8db3\u6761\u4ef6\uff0c\u65e0\u6cd5\u7a7f\u900f";
        }
        return new QueryRelationCheckResultVO(null, warningText);
    }

    private boolean executeExpression(Map<String, Object> dataMap, QueryTemplate template, String checkExpression) {
        QueryFormulaContext queryFormulaContext = new QueryFormulaContext(dataMap, template);
        try {
            return FormulaExecuteHandlerUtil.judgeFormula(checkExpression, ParamTypeEnum.BOOL.getTypeName(), queryFormulaContext);
        }
        catch (DefinedQueryRuntimeException e) {
            logger.error("\u6267\u884c\u7ea7\u8054\u67e5\u8be2\u6761\u4ef6({})\u65f6\u51fa\u73b0\u5f02\u5e38: {}", new Object[]{checkExpression, e.getMessage(), e});
            return false;
        }
    }
}

