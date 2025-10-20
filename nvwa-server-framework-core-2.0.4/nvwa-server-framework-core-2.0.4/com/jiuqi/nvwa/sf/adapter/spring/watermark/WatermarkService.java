/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark;

import com.jiuqi.nvwa.sf.adapter.spring.watermark.Watermark;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.WatermarkContext;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.WatermarkException;
import org.springframework.stereotype.Component;

@Component
public interface WatermarkService {
    public Watermark getWaterMarkConfigInfo() throws WatermarkException;

    public Watermark getWaterMarkConfigInfo(WatermarkContext var1) throws WatermarkException;
}

