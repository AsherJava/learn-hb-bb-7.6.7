/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.consts.CommonConst$ProductIdentificationEnum
 */
package com.jiuqi.gcreport.dimension.internal.service;

import com.jiuqi.common.base.consts.CommonConst;
import com.jiuqi.gcreport.dimension.vo.EffectTableVO;
import java.util.List;

public interface DimensionEffectScopeGather {
    public String getCode();

    public String getTitle();

    public String getDescription();

    public List<String> getContainedScopes();

    default public String getSysCode() {
        return CommonConst.ProductIdentificationEnum.GCREPORT.getCode();
    }

    default public String getSysTitle() {
        return CommonConst.ProductIdentificationEnum.GCREPORT.getTitle();
    }

    public List<EffectTableVO> getEffectTableVO();
}

