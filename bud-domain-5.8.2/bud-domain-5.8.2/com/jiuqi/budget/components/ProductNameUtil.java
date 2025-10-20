/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.budget.components;

import com.jiuqi.budget.autoconfigure.BudProductNameComponent;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;

public class ProductNameUtil {
    public static String getProductName() {
        return ((BudProductNameComponent)ApplicationContextRegister.getBean(BudProductNameComponent.class)).getProductName();
    }
}

