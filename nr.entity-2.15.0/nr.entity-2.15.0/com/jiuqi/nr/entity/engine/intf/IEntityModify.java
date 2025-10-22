/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.entity.engine.intf;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.entity.engine.intf.ICommonQuery;
import com.jiuqi.nr.entity.engine.intf.IModifyTable;

public interface IEntityModify
extends ICommonQuery {
    public IModifyTable executeUpdate(IContext var1) throws JQException;

    public void batchUpdateModel(boolean var1);

    public void ignoreCheckErrorData(boolean var1);
}

