/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather
 *  com.jiuqi.gcreport.dimension.vo.EffectTableVO
 *  com.jiuqi.gcreport.offsetitem.service.impl.OffsetItemDimensionEffectScope
 */
package com.jiuqi.gcreport.invest.common;

import com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather;
import com.jiuqi.gcreport.dimension.vo.EffectTableVO;
import com.jiuqi.gcreport.offsetitem.service.impl.OffsetItemDimensionEffectScope;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvestDimensionEffectScope
implements DimensionEffectScopeGather {
    @Autowired
    private OffsetItemDimensionEffectScope offsetItemDimensionEffectScope;

    public String getCode() {
        return "INVEST";
    }

    public String getTitle() {
        return "\u6295\u8d44\u53f0\u8d26\u62b5\u9500";
    }

    public String getDescription() {
        return "\u5305\u62ec\u6295\u8d44\u53f0\u8d26\u5f55\u5165\u548c\u6295\u8d44\u62b5\u9500\u5206\u5f55";
    }

    public List<String> getContainedScopes() {
        ArrayList<String> containScopes = new ArrayList<String>();
        containScopes.add(this.offsetItemDimensionEffectScope.getCode());
        return containScopes;
    }

    public List<EffectTableVO> getEffectTableVO() {
        ArrayList<EffectTableVO> effectTableVOS = new ArrayList<EffectTableVO>();
        effectTableVOS.add(new EffectTableVO("GC_FVCHBILL", "\u516c\u5141\u4ef7\u503c\u5355\u636e\u4e3b\u8868"));
        effectTableVOS.add(new EffectTableVO("GC_FVCH_FIXEDITEM", "\u516c\u5141\u4ef7\u503c\u56fa\u5b9a/\u65e0\u5f62\u8d44\u4ea7\u8868"));
        effectTableVOS.add(new EffectTableVO("GC_FVCH_OTHERITEM", "\u516c\u5141\u4ef7\u503c\u5176\u4ed6\u8d44\u4ea7\u7c7b\u8868"));
        effectTableVOS.add(new EffectTableVO("GC_INVESTBILL", "\u6295\u8d44\u5355\u636e\u4e3b\u8868"));
        return effectTableVOS;
    }
}

