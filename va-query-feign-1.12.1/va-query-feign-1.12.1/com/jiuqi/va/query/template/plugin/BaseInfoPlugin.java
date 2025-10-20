/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeName
 */
package com.jiuqi.va.query.template.plugin;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.jiuqi.va.query.template.plugin.QueryPlugin;
import com.jiuqi.va.query.template.vo.QueryPluginCheckVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import org.springframework.stereotype.Component;

@Component
@JsonTypeName(value="baseInfo")
public class BaseInfoPlugin
implements QueryPlugin {
    private TemplateInfoVO baseInfo = new TemplateInfoVO();

    @Override
    public int getSortNum() {
        return 0;
    }

    @Override
    public String getName() {
        return "baseInfo";
    }

    @Override
    public String getTitle() {
        return "\u57fa\u672c\u4fe1\u606f";
    }

    @Override
    public QueryPluginCheckVO checkPlugin(QueryPlugin queryPlugin, QueryTemplate queryTemplate) {
        return null;
    }

    public TemplateInfoVO getBaseInfo() {
        return this.baseInfo;
    }

    public void setBaseInfo(TemplateInfoVO baseInfo) {
        this.baseInfo = baseInfo;
    }
}

