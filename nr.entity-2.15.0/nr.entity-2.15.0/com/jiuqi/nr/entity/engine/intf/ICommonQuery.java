/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 */
package com.jiuqi.nr.entity.engine.intf;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.setting.OrderType;
import java.util.Date;

public interface ICommonQuery {
    public DimensionValueSet getMasterKeys();

    public void setMasterKeys(DimensionValueSet var1);

    public void setQueryVersionDate(Date var1);

    public void setEntityView(EntityViewDefine var1);

    @Deprecated
    public void setRowFilter(String var1);

    public void setExpression(String var1);

    public void setAuthorityOperations(AuthorityType var1);

    public void setIgnoreViewFilter(boolean var1);

    public void addOrderAttribute(String var1, OrderType var2);

    public void sorted(boolean var1);

    public void sortedByQuery(boolean var1);

    public void lazyQuery();

    public void markLeaf();

    public void maskedData();
}

