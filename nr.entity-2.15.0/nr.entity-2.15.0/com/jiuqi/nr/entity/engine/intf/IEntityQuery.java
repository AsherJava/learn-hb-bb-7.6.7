/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.entity.engine.intf;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.entity.engine.intf.ICommonQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.ReferRelation;

public interface IEntityQuery
extends ICommonQuery {
    public void addReferRelation(ReferRelation var1);

    public void setIsolateCondition(String var1);

    public void queryStopModel(int var1);

    public IEntityTable executeReader(IContext var1) throws Exception;

    public IEntityTable executeFullBuild(IContext var1) throws Exception;

    public IEntityTable executeRangeBuild(IContext var1, RangeQuery var2) throws Exception;
}

