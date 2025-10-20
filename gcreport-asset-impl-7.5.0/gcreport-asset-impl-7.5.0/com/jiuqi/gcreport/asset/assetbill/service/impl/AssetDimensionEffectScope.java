/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather
 *  com.jiuqi.gcreport.dimension.vo.EffectTableVO
 *  com.jiuqi.gcreport.offsetitem.service.impl.OffsetItemDimensionEffectScope
 */
package com.jiuqi.gcreport.asset.assetbill.service.impl;

import com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather;
import com.jiuqi.gcreport.dimension.vo.EffectTableVO;
import com.jiuqi.gcreport.offsetitem.service.impl.OffsetItemDimensionEffectScope;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetDimensionEffectScope
implements DimensionEffectScopeGather {
    @Autowired
    private OffsetItemDimensionEffectScope offsetItemDimensionEffectScope;

    public String getCode() {
        return "ASSET";
    }

    public String getTitle() {
        return "\u8d44\u4ea7\u53f0\u8d26\u62b5\u9500";
    }

    public String getDescription() {
        return "\u5305\u62ec\u8d44\u4ea7\u53f0\u8d26\u5f55\u5165\u548c\u8d44\u4ea7\u62b5\u9500\u5206\u5f55";
    }

    public List<String> getContainedScopes() {
        ArrayList<String> containScopes = new ArrayList<String>();
        containScopes.add(this.offsetItemDimensionEffectScope.getCode());
        return containScopes;
    }

    public List<EffectTableVO> getEffectTableVO() {
        ArrayList<EffectTableVO> effectTableVOS = new ArrayList<EffectTableVO>();
        effectTableVOS.add(new EffectTableVO("GC_COMMONASSETBILL", "\u5e38\u89c4\u8d44\u4ea7\u5355\u636e\u4e3b\u8868"));
        return effectTableVOS;
    }
}

