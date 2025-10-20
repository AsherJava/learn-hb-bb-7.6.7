/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.statement.select.SelectItem
 */
package com.jiuqi.va.query.sql.parser.selectitem;

import java.util.List;
import net.sf.jsqlparser.statement.select.SelectItem;

public interface ISelectItemHandler {
    public SelectItem doParser(SelectItem var1, List<String> var2);

    public Class<? extends SelectItem> getClazzType();
}

