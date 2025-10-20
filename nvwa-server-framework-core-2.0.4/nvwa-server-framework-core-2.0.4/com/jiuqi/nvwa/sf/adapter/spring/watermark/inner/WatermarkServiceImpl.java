/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark.inner;

import com.jiuqi.nvwa.sf.adapter.spring.watermark.Watermark;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.WatermarkContext;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.WatermarkException;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.WatermarkService;
import com.jiuqi.nvwa.sf.adapter.spring.watermark.inner.WatermarkGetter;
import org.springframework.stereotype.Component;

@Component
public class WatermarkServiceImpl
implements WatermarkService {
    @Override
    public Watermark getWaterMarkConfigInfo() throws WatermarkException {
        return new WatermarkGetter().get(new WatermarkContext());
    }

    @Override
    public Watermark getWaterMarkConfigInfo(WatermarkContext watermarkContext) throws WatermarkException {
        return new WatermarkGetter().get(watermarkContext);
    }
}

