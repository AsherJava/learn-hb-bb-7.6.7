/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather
 *  com.jiuqi.gcreport.dimension.vo.EffectTableVO
 *  com.jiuqi.gcreport.offsetitem.service.impl.OffsetItemDimensionEffectScope
 */
package com.jiuqi.gcreport.lease.leasebill.service.impl;

import com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather;
import com.jiuqi.gcreport.dimension.vo.EffectTableVO;
import com.jiuqi.gcreport.offsetitem.service.impl.OffsetItemDimensionEffectScope;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaseDimensionEffectScope
implements DimensionEffectScopeGather {
    @Autowired
    private OffsetItemDimensionEffectScope offsetItemDimensionEffectScope;

    public String getCode() {
        return "LEASE";
    }

    public String getTitle() {
        return "\u79df\u8d41\u53f0\u8d26\u62b5\u9500";
    }

    public String getDescription() {
        return "\u5305\u62ec\u79df\u8d41\u53f0\u8d26\u5f55\u5165\u548c\u79df\u8d41\u62b5\u9500\u5206\u5f55";
    }

    public List<String> getContainedScopes() {
        ArrayList<String> containScopes = new ArrayList<String>();
        containScopes.add(this.offsetItemDimensionEffectScope.getCode());
        return containScopes;
    }

    public List<EffectTableVO> getEffectTableVO() {
        ArrayList<EffectTableVO> effectTableVOS = new ArrayList<EffectTableVO>();
        effectTableVOS.add(new EffectTableVO("GC_LESSORBILL", "\u51fa\u79df\u65b9\u5355\u636e\u4e3b\u8868"));
        effectTableVOS.add(new EffectTableVO("GC_TENANTRYBILL", "\u627f\u79df\u65b9\u5355\u636e\u4e3b\u8868"));
        return effectTableVOS;
    }
}

