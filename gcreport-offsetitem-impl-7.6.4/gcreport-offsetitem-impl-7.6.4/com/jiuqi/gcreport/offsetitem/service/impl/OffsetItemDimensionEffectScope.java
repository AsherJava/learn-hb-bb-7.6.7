/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather
 *  com.jiuqi.gcreport.dimension.vo.EffectTableVO
 */
package com.jiuqi.gcreport.offsetitem.service.impl;

import com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather;
import com.jiuqi.gcreport.dimension.vo.EffectTableVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OffsetItemDimensionEffectScope
implements DimensionEffectScopeGather {
    public String getCode() {
        return "OFFSET_ITEM";
    }

    public String getTitle() {
        return "\u62b5\u9500\u5206\u5f55";
    }

    public String getDescription() {
        return "\u53ea\u5f71\u54cd\u62b5\u9500\u5206\u5f55";
    }

    public List<String> getContainedScopes() {
        return null;
    }

    public List<EffectTableVO> getEffectTableVO() {
        ArrayList<EffectTableVO> effectTableVOS = new ArrayList<EffectTableVO>();
        effectTableVOS.add(new EffectTableVO("GC_OFFSETVCHRITEM_INIT", "\u521d\u59cb\u62b5\u9500\u5206\u5f55\u8868"));
        effectTableVOS.add(new EffectTableVO("GC_OFFSETVCHRITEM", "\u62b5\u9500\u5206\u5f55\u8868"));
        effectTableVOS.add(new EffectTableVO("GC_SAMECTRLOFFSETITEM", "\u540c\u63a7\u62b5\u9500\u5206\u5f55\u8868"));
        return effectTableVOS;
    }
}

