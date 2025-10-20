/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeName
 */
package com.jiuqi.va.query.template.plugin;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.jiuqi.va.query.sql.enumerate.QueryModeEnum;
import com.jiuqi.va.query.template.enumerate.MessageTypeEnum;
import com.jiuqi.va.query.template.plugin.QueryPlugin;
import com.jiuqi.va.query.template.vo.QueryCheckItemVO;
import com.jiuqi.va.query.template.vo.QueryPluginCheckVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
@JsonTypeName(value="dataSource")
public class DataSourcePlugin
implements QueryPlugin {
    private String defineSql;
    private List<TemplateParamsVO> params;
    private boolean encrypted;

    @Override
    public String getName() {
        return "dataSource";
    }

    @Override
    public String getTitle() {
        return "\u6570\u636e\u6e90";
    }

    @Override
    public int getSortNum() {
        return 1;
    }

    @Override
    public QueryPluginCheckVO checkPlugin(QueryPlugin queryPlugin, QueryTemplate queryTemplate) {
        DataSourcePlugin sourcePlugin = (DataSourcePlugin)queryPlugin;
        if (CollectionUtils.isEmpty(sourcePlugin.getParams())) {
            return null;
        }
        QueryPluginCheckVO pluginCheckVO = new QueryPluginCheckVO(this.getName());
        ArrayList<QueryCheckItemVO> result = new ArrayList<QueryCheckItemVO>();
        ArrayList<String> errorFields = new ArrayList<String>();
        for (TemplateParamsVO param : sourcePlugin.getParams()) {
            String refTableName = param.getRefTableName();
            String mode = param.getMode();
            boolean mustInput = param.isMustInput();
            Boolean enableAuth = param.getEnableAuth();
            if (QueryModeEnum.mutileData.getModeSign().equals(mode) || !StringUtils.hasText(refTableName) || !StringUtils.hasText(param.getFilterCondition()) && Boolean.TRUE.equals(enableAuth == false) || mustInput) continue;
            errorFields.add(param.getName());
        }
        if (!CollectionUtils.isEmpty(errorFields)) {
            QueryCheckItemVO queryCheckItemVO = new QueryCheckItemVO();
            queryCheckItemVO.setType(MessageTypeEnum.ERROR);
            queryCheckItemVO.setMessage("\u67e5\u8be2\u6761\u4ef6\u4e2d\u7684 " + String.join((CharSequence)",", errorFields) + " \u5b57\u6bb5\u914d\u7f6e\u4e0d\u7b26\u5408\u89c4\u8303\uff0c\u5728\u542f\u7528\u6743\u9650/\u8fc7\u6ee4\u6761\u4ef6\u6709\u503c/\u9ed8\u8ba4\u503c\u6709\u8fc7\u6ee4/\u65f6\uff0c\u8bf7\u914d\u7f6e\u201c\u5355\u503c+\u5fc5\u586b\u201c\u6216\u8005\u201c\u591a\u503c+\u975e\u5fc5\u586b/\u5fc5\u586b");
            result.add(queryCheckItemVO);
            pluginCheckVO.setResult(result);
            return pluginCheckVO;
        }
        return null;
    }

    public String getDefineSql() {
        return this.defineSql;
    }

    public void setDefineSql(String defineSql) {
        this.defineSql = defineSql;
    }

    public List<TemplateParamsVO> getParams() {
        return this.params;
    }

    public void setParams(List<TemplateParamsVO> params) {
        this.params = params;
    }

    public boolean isEncrypted() {
        return this.encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }
}

