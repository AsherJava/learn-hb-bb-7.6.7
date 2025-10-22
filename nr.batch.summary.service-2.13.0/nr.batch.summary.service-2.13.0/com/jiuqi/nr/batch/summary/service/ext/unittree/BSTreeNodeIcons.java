/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.nodeicon.IconCategory
 *  com.jiuqi.nr.itreebase.nodeicon.IconSource
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme
 *  com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceReader
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuqi.nr.itreebase.nodeicon.IconCategory;
import com.jiuqi.nr.itreebase.nodeicon.IconSource;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceReader;
import java.io.File;
import java.util.Set;

public class BSTreeNodeIcons
implements IconSourceScheme {
    public static final String SOURCE_ID = "summary.tree.node.icons";
    private static final String FILE_PATH = "static" + File.separator + "node-icons";
    private static final String PROPERTIES = "zkeys.properties";
    private static final IconSourceScheme sourceReader = new IconSourceReader("summary.tree.node.icons", FILE_PATH, "zkeys.properties");

    public String getSchemeId() {
        return SOURCE_ID;
    }

    public IconCategory getCategory() {
        return IconCategory.NODE_ICONS;
    }

    public Set<String> getValues() {
        return sourceReader.getValues();
    }

    public IconSource getIconSource(String key) {
        return sourceReader.getIconSource(key);
    }
}

