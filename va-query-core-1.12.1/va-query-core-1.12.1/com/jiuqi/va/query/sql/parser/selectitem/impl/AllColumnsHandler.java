/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.statement.select.AllColumns
 *  net.sf.jsqlparser.statement.select.SelectItem
 */
package com.jiuqi.va.query.sql.parser.selectitem.impl;

import com.jiuqi.va.query.sql.parser.selectitem.ISelectItemHandler;
import java.util.List;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.springframework.stereotype.Component;

@Component
public class AllColumnsHandler
implements ISelectItemHandler {
    @Override
    public SelectItem doParser(SelectItem selectItem, List<String> params) {
        return selectItem;
    }

    @Override
    public Class<? extends SelectItem> getClazzType() {
        return AllColumns.class;
    }
}

