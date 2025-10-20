/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeName
 */
package com.jiuqi.va.query.template.plugin;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.jiuqi.va.query.template.plugin.QueryPlugin;
import org.springframework.stereotype.Component;

@Component
@JsonTypeName(value="viewDesign")
public class ViewDesignPlugin
implements QueryPlugin {
    private String designSets = "";

    @Override
    public String getName() {
        return "viewDesign";
    }

    @Override
    public String getTitle() {
        return "\u754c\u9762\u8bbe\u8ba1";
    }

    @Override
    public int getSortNum() {
        return 7;
    }

    public String getDesignSets() {
        return this.designSets;
    }

    public void setDesignSets(String designSets) {
        this.designSets = designSets;
    }
}

