/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 */
package com.jiuqi.va.query.sql.parser.model;

import java.util.List;
import net.sf.jsqlparser.Model;

public interface IModelHandler {
    public Class<? extends Model> getClazzType();

    public Model doParser(Model var1, List<String> var2);
}

