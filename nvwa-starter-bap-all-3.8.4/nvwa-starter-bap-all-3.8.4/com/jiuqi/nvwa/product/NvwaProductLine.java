/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.adapter.spring.product.IProductLine
 */
package com.jiuqi.nvwa.product;

import com.jiuqi.nvwa.sf.adapter.spring.product.IProductLine;
import org.springframework.stereotype.Component;

@Component
public class NvwaProductLine
implements IProductLine {
    public String id() {
        return "nvwa";
    }

    public String title() {
        return "\u5973\u5a32\u5e73\u53f0";
    }
}

