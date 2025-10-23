/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.category.IResourceCategoryApp
 */
package com.jiuqi.nr.mapping.view;

import com.jiuqi.nvwa.resourceview.category.IResourceCategoryApp;
import org.springframework.stereotype.Component;

@Component
public class MappingSchemeApp
implements IResourceCategoryApp {
    public String getId() {
        return "com.jiuqi.nr.mappingScheme";
    }

    public String getTitle() {
        return null;
    }

    public String getIcon() {
        return null;
    }

    public double getOrder() {
        return 0.0;
    }
}

