/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark;

import com.jiuqi.nvwa.sf.adapter.spring.watermark.IResourceTagProvider;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.ITagProvider;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.IWatermarkStorage;
import java.util.Map;

public abstract class WatermarkFactory {
    public abstract IWatermarkStorage getStorage();

    public abstract ITagProvider getTags();

    public abstract Map<String, IResourceTagProvider> getResourceTags();
}

