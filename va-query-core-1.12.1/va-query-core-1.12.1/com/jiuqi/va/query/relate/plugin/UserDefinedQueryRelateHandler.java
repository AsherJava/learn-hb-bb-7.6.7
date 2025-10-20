/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.relate.handler.IQueryRelateHandler
 *  com.jiuqi.va.query.relate.vo.QueryRelateParamVO
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 */
package com.jiuqi.va.query.relate.plugin;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.relate.handler.IQueryRelateHandler;
import com.jiuqi.va.query.relate.vo.QueryRelateParamVO;
import com.jiuqi.va.query.template.service.TemplateContentService;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDefinedQueryRelateHandler
implements IQueryRelateHandler {
    public static final String PROCESSOR_NAME = "RelateQueryUserDefined";
    @Autowired
    private TemplateContentService templateContentService;

    public String getName() {
        return PROCESSOR_NAME;
    }

    public String getTitle() {
        return "\u8054\u67e5\u81ea\u5b9a\u4e49\u67e5\u8be2";
    }

    public List<QueryRelateParamVO> getQueryParams(String relateConfigStr) {
        Map stringObjectMap = JSONUtil.jsonToMap((String)relateConfigStr);
        String templateId = String.valueOf(stringObjectMap.get("id"));
        if (DCQueryStringHandle.isEmpty(templateId)) {
            new DefinedQueryRuntimeException("\u8054\u67e5\u5904\u7406\u5668\u914d\u7f6e\u7684\u67e5\u8be2\u5b9a\u4e49\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
        }
        List<TemplateParamsVO> templateParams = this.templateContentService.getTemplateParams(templateId);
        ArrayList<QueryRelateParamVO> results = new ArrayList<QueryRelateParamVO>();
        if (templateParams == null || templateParams.isEmpty()) {
            return results;
        }
        for (TemplateParamsVO templateParamsVO : templateParams) {
            QueryRelateParamVO relateQueryParamVO = new QueryRelateParamVO();
            relateQueryParamVO.setName(templateParamsVO.getName());
            relateQueryParamVO.setTitle(templateParamsVO.getTitle());
            relateQueryParamVO.setNeedFlag(templateParamsVO.isMustInput());
            results.add(relateQueryParamVO);
        }
        QueryRelateParamVO showQueryCondition = new QueryRelateParamVO();
        showQueryCondition.setName("showQueryCondition");
        showQueryCondition.setTitle("\u662f\u5426\u663e\u793a\u67e5\u8be2\u6761\u4ef6\uff08true\u3001false\uff09");
        showQueryCondition.setNeedFlag(false);
        results.add(showQueryCondition);
        return results;
    }
}

