/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeName
 */
package com.jiuqi.va.query.template.plugin;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.jiuqi.va.query.template.plugin.QueryPlugin;
import com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
@JsonTypeName(value="toolBar")
public class ToolBarPlugin
implements QueryPlugin {
    private List<TemplateToolbarInfoVO> tools = new ArrayList<TemplateToolbarInfoVO>();

    @Override
    public String getName() {
        return "toolBar";
    }

    @Override
    public String getTitle() {
        return "\u5de5\u5177\u680f";
    }

    @Override
    public void initPlugin() {
        TemplateToolbarInfoVO tool = new TemplateToolbarInfoVO();
        this.tools = new ArrayList<TemplateToolbarInfoVO>();
        tool.setId(UUID.randomUUID().toString());
        tool.setAction("query");
        tool.setTitle("\u67e5\u8be2");
        tool.setSortOrder(0);
        this.tools.add(tool);
    }

    @Override
    public int getSortNum() {
        return 4;
    }

    public List<TemplateToolbarInfoVO> getTools() {
        return this.tools;
    }

    public void setTools(List<TemplateToolbarInfoVO> tools) {
        this.tools = tools;
    }
}

