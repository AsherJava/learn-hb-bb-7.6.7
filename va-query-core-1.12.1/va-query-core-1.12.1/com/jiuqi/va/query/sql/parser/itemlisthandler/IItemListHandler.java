/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.expression.operators.relational.ItemsList
 */
package com.jiuqi.va.query.sql.parser.itemlisthandler;

import java.util.List;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;

public interface IItemListHandler {
    public ItemsList doParser(ItemsList var1, List<String> var2);

    public Class<? extends ItemsList> getClazzType();
}

