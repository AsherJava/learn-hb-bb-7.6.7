/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeName
 */
package com.jiuqi.va.query.template.plugin;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.jiuqi.va.query.template.plugin.QueryPlugin;
import com.jiuqi.va.query.template.vo.print.QueryPrintScheme;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@JsonTypeName(value="queryPrint")
public class QueryPrintPlugin
implements QueryPlugin {
    private List<QueryPrintScheme> schemes = new ArrayList<QueryPrintScheme>();

    @Override
    public String getName() {
        return "queryPrint";
    }

    @Override
    public String getTitle() {
        return "\u6253\u5370";
    }

    @Override
    public int getSortNum() {
        return 8;
    }

    public List<QueryPrintScheme> getSchemes() {
        return this.schemes;
    }

    public void setSchemes(List<QueryPrintScheme> schemes) {
        this.schemes = schemes;
    }
}

