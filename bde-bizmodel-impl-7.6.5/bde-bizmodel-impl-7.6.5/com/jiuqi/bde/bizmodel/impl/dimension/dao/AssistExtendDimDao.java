/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.dc.base.common.enums.StopFlagEnum
 */
package com.jiuqi.bde.bizmodel.impl.dimension.dao;

import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.dc.base.common.enums.StopFlagEnum;
import java.util.List;

public interface AssistExtendDimDao {
    public AssistExtendDimVO getAssistExtendDimByCode(String var1);

    public void save(AssistExtendDimVO var1);

    public void update(AssistExtendDimVO var1);

    public List<AssistExtendDimVO> getAllAssistExtendDim();

    public List<AssistExtendDimVO> getAllStartAssistExtendDim();

    public void updateAssistExtendDimStopFlag(String var1, StopFlagEnum var2);
}

