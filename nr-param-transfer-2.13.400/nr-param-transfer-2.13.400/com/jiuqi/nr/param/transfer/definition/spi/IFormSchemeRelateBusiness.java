/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ResItem
 */
package com.jiuqi.nr.param.transfer.definition.spi;

import com.jiuqi.bi.transfer.engine.ResItem;
import java.util.List;

public interface IFormSchemeRelateBusiness {
    public String getCode();

    public String getTitle();

    public List<ResItem> getRelatedBusinessForFormScheme(String var1);
}

