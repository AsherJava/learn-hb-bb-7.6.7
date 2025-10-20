/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.QueryConfigureImportVO$ImportResult
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.QueryTemplateDesignVO
 *  com.jiuqi.va.query.template.vo.TemplateContentVO
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 */
package com.jiuqi.va.query.template.service;

import com.jiuqi.va.query.template.vo.QueryConfigureImportVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.QueryTemplateDesignVO;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import java.util.List;

public interface TemplateDesignService {
    public QueryTemplateDesignVO getTemplateDesignData(String var1);

    public QueryTemplate getTemplate(String var1);

    public String getTemplateCodeByTemplateId(String var1);

    public QueryTemplate getBizTemplate(String var1);

    public QueryTemplate getBizTemplate(QueryTemplate var1);

    public TemplateContentVO getTemplateContentVO(String var1);

    public QueryTemplate getTemplateByCode(String var1);

    public void saveTemplate(QueryTemplate var1, boolean var2);

    public void saveTemplateDesign(QueryTemplateDesignVO var1);

    public QueryConfigureImportVO.ImportResult importQueryTemplate(QueryTemplate var1, TemplateInfoVO var2);

    public void removeTemplate(String var1);

    public QueryConfigureImportVO.ImportResult importQueryTemplateUseStrategy(QueryTemplate var1, String var2, TemplateInfoVO var3, List<String> var4);
}

