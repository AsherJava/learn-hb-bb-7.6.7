/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.anno.intf;

import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionGroupV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;

public interface ITableGroupDefine {
    public static final String id = "11000000-0000-0000-0000-000000000001";
    public static final String code = "table_group_default";
    public static final String title = "\u5408\u5e76";

    default public String getDescription() {
        return this.getTitle();
    }

    default public String getID() {
        return id;
    }

    default public String getCode() {
        return code;
    }

    default public String getTitle() {
        return title;
    }

    default public DefinitionGroupV initGroup(DefinitionTableV table) {
        DefinitionGroupV group = new DefinitionGroupV();
        group.setId(this.getID());
        group.setCode(this.getCode());
        group.setTitle(this.getTitle());
        group.setDescription(this.getDescription());
        group.setTable(table);
        return group;
    }
}

