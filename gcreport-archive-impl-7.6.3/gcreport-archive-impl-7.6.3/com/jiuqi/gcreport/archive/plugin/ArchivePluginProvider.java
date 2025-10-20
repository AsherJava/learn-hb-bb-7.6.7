/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.archive.plugin;

import com.jiuqi.gcreport.archive.plugin.ArchivePlugin;
import java.util.List;

public interface ArchivePluginProvider {
    public ArchivePlugin getArchivePluginByCode(String var1);

    public List<ArchivePlugin> listArchivePlugin();
}

