/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 *  com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum
 *  com.jiuqi.common.base.consts.CommonConst$ProductIdentificationEnum
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather
 *  com.jiuqi.gcreport.dimension.vo.EffectTableVO
 */
package com.jiuqi.bde.plugin.gcreport.dimension;

import com.jiuqi.bde.common.constant.BizDataModelEnum;
import com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum;
import com.jiuqi.common.base.consts.CommonConst;
import com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather;
import com.jiuqi.gcreport.dimension.vo.EffectTableVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CedxBalanceDataModelDimensionEffectScopeGather
implements DimensionEffectScopeGather {
    public String getCode() {
        return BizDataModelEnum.CEDXMODEL.getCode();
    }

    public String getTitle() {
        return BizDataModelEnum.CEDXMODEL.getName();
    }

    public String getDescription() {
        return BizDataModelEnum.CEDXMODEL.getName();
    }

    public List<String> getContainedScopes() {
        return null;
    }

    public List<EffectTableVO> getEffectTableVO() {
        ArrayList<EffectTableVO> effectTableVOList = new ArrayList<EffectTableVO>();
        effectTableVOList.add(new EffectTableVO(MemoryBalanceTypeEnum.CEDXBALANCE.getCode(), MemoryBalanceTypeEnum.CEDXBALANCE.getName()));
        return effectTableVOList;
    }

    public String getSysCode() {
        return CommonConst.ProductIdentificationEnum.BDE.getCode();
    }

    public String getSysTitle() {
        return CommonConst.ProductIdentificationEnum.BDE.getTitle();
    }
}

