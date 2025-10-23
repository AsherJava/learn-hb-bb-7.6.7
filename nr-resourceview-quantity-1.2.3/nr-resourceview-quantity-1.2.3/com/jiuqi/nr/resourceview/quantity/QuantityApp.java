/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.category.IResourceCategoryApp
 */
package com.jiuqi.nr.resourceview.quantity;

import com.jiuqi.nvwa.resourceview.category.IResourceCategoryApp;
import org.springframework.stereotype.Component;

@Component
public class QuantityApp
implements IResourceCategoryApp {
    public String getId() {
        return "nr-quantity-manage";
    }

    public String getTitle() {
        return "\u91cf\u7eb2\u7ba1\u7406";
    }

    public String getIcon() {
        return null;
    }

    public double getOrder() {
        return 0.0;
    }
}

