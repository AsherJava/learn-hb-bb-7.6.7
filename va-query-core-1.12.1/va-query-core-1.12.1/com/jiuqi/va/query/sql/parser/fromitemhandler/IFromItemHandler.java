/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 */
package com.jiuqi.va.query.sql.parser.fromitemhandler;

import java.util.List;
import net.sf.jsqlparser.Model;

public interface IFromItemHandler {
    public Model doParser(Model var1, List<String> var2);

    public Class<? extends Model> getClazzType();
}

