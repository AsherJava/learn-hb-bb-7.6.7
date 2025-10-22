/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 */
package com.jiuqi.nr.entity.service;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.exception.DataTypeException;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import java.util.Map;
import java.util.Set;

public interface IEntityAssist {
    public String getMinusEntity(IContext var1, EntityViewDefine var2, String var3, String var4);

    public boolean isMinusRow(IEntityRow var1, IEntityAttribute var2) throws RuntimeException, DataTypeException;

    public boolean judgementExpression(IContext var1, EntityViewDefine var2, String var3);

    public Map<Boolean, Set<String>> filterExpression(IContext var1, EntityViewDefine var2, Set<String> var3);

    public boolean isDBModel(String var1, String var2, String var3);

    public boolean isEntity(String var1);
}

