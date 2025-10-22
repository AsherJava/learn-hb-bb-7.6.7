/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 *  com.jiuqi.nr.itreebase.nodeicon.IconCategory
 *  com.jiuqi.nr.itreebase.nodeicon.IconSource
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme
 *  com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceImpl
 */
package com.jiuiqi.nr.unit.treebase.node.icon;

import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.itreebase.nodeicon.IconCategory;
import com.jiuqi.nr.itreebase.nodeicon.IconSource;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceImpl;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IconSourceSchemeOfBBLX
implements IconSourceScheme {
    public static final String SOURCE_ID = "bblx-icons";
    private Map<String, String> iconsMap;

    public IconSourceSchemeOfBBLX(IFormTypeApplyService formTypeApplyService, String bblxDefineCode) {
        this.iconsMap = formTypeApplyService.getIcon(bblxDefineCode);
    }

    public String getSchemeId() {
        return SOURCE_ID;
    }

    public IconCategory getCategory() {
        return IconCategory.NODE_ICONS;
    }

    public boolean canBeCached() {
        return false;
    }

    public Set<String> getValues() {
        if (this.iconsMap != null) {
            return this.iconsMap.keySet();
        }
        return new HashSet<String>();
    }

    public IconSource getIconSource(String key) {
        return new IconSourceImpl(SOURCE_ID, key, this.iconsMap.get(key));
    }
}

