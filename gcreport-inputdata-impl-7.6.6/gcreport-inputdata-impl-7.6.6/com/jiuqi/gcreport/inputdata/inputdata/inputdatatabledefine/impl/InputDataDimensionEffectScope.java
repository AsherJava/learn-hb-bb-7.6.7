/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather
 *  com.jiuqi.gcreport.dimension.vo.EffectTableVO
 */
package com.jiuqi.gcreport.inputdata.inputdata.inputdatatabledefine.impl;

import com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather;
import com.jiuqi.gcreport.dimension.vo.EffectTableVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InputDataDimensionEffectScope
implements DimensionEffectScopeGather {
    public String getCode() {
        return "INPUTDATA";
    }

    public String getTitle() {
        return "\u5185\u90e8\u5f55\u5165\u8868\u62b5\u9500";
    }

    public String getDescription() {
        return "\u5305\u62ec\u5185\u90e8\u5f55\u5165\u8868\u548c\u5185\u90e8\u8868\u62b5\u9500\u5206\u5f55";
    }

    public List<String> getContainedScopes() {
        ArrayList<String> containScopes = new ArrayList<String>();
        containScopes.add("OFFSET_ITEM");
        return containScopes;
    }

    public List<EffectTableVO> getEffectTableVO() {
        ArrayList<EffectTableVO> effectTableVOS = new ArrayList<EffectTableVO>();
        effectTableVOS.add(new EffectTableVO("GC_INPUTDATATEMPLATE", "\u5185\u90e8\u5f55\u5165\u6a21\u677f\u8868"));
        return effectTableVOS;
    }
}

