/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.amount.service;

import com.jiuqi.nr.designer.web.treebean.AmountObject;
import java.util.List;

public interface AmountService {
    public AmountObject queryById(String var1);

    public AmountObject queryByCode(String var1);

    public List<AmountObject> queryAllAmount();

    public List<AmountObject> queryAmountByParent(String var1);
}

