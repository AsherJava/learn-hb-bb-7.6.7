/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeName
 */
package com.jiuqi.va.query.template.plugin;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.jiuqi.va.query.template.plugin.QueryPlugin;
import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@JsonTypeName(value="queryRelate")
public class QueryRelatePlugin
implements QueryPlugin {
    private List<TemplateRelateQueryVO> relateQuerys;

    @Override
    public String getName() {
        return "queryRelate";
    }

    @Override
    public String getTitle() {
        return "\u7ea7\u8054\u67e5\u8be2";
    }

    @Override
    public int getSortNum() {
        return 3;
    }

    public List<TemplateRelateQueryVO> getRelateQuerys() {
        return this.relateQuerys;
    }

    public void setRelateQuerys(List<TemplateRelateQueryVO> relateQuerys) {
        this.relateQuerys = relateQuerys;
    }
}

