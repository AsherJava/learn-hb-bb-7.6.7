/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.schema.Table
 *  net.sf.jsqlparser.statement.select.FromItem
 */
package com.jiuqi.va.query.sql.parser.fromitemhandler.impl;

import com.jiuqi.va.query.sql.parser.fromitemhandler.IFromItemHandler;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import org.springframework.stereotype.Component;

@Component
public class TableHandler
implements IFromItemHandler {
    public FromItem doParser(Model model, List<String> params) {
        return (Table)model;
    }

    @Override
    public Class<? extends Model> getClazzType() {
        return Table.class;
    }
}

