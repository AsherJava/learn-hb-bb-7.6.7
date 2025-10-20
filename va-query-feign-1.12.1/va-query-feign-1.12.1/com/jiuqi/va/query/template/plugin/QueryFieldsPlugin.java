/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeName
 */
package com.jiuqi.va.query.template.plugin;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.jiuqi.va.query.template.plugin.QueryPlugin;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@JsonTypeName(value="queryFields")
public class QueryFieldsPlugin
implements QueryPlugin {
    public static final String PROCESSOR_NAME = "queryFields";
    private List<TemplateFieldSettingVO> fields;

    @Override
    public String getName() {
        return PROCESSOR_NAME;
    }

    @Override
    public String getTitle() {
        return "\u67e5\u8be2\u5217";
    }

    @Override
    public int getSortNum() {
        return 2;
    }

    public List<TemplateFieldSettingVO> getFields() {
        return this.fields;
    }

    public void setFields(List<TemplateFieldSettingVO> fields) {
        this.fields = fields;
    }
}

