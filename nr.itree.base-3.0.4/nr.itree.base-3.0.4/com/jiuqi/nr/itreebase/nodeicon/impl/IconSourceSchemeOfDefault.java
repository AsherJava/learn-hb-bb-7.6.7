/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.nodeicon.impl;

import com.jiuqi.nr.itreebase.nodeicon.IconCategory;
import com.jiuqi.nr.itreebase.nodeicon.IconSource;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceReader;
import java.io.File;
import java.util.Set;

public class IconSourceSchemeOfDefault
implements IconSourceScheme {
    public static final String SOURCE_ID = "def-icon";
    public static final String ICON_KEY = "deficon";
    private static final IconSourceScheme sourceReader;

    @Override
    public String getSchemeId() {
        return SOURCE_ID;
    }

    @Override
    public IconCategory getCategory() {
        return IconCategory.NODE_ICONS;
    }

    @Override
    public Set<String> getValues() {
        return sourceReader.getValues();
    }

    @Override
    public IconSource getIconSource(String key) {
        return sourceReader.getIconSource(ICON_KEY);
    }

    static {
        String filePath = "static" + File.separator + "default-icons";
        String properties = "zkeys.properties";
        sourceReader = new IconSourceReader(SOURCE_ID, filePath, properties);
    }
}

