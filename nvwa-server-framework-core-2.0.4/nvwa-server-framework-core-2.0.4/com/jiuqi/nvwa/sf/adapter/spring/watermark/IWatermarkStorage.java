/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark;

import com.jiuqi.nvwa.sf.adapter.spring.watermark.Watermark;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.WatermarkException;

public interface IWatermarkStorage {
    public void saveWatermarkInfo(Watermark var1) throws WatermarkException;

    public Watermark getWatermarkInfo() throws WatermarkException;
}

