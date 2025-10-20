/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 */
package com.jiuqi.bde.bizmodel.impl.dimension.service;

import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import java.util.List;

public interface AssistExtendDimService {
    public AssistExtendDimVO getAssistExtendDimByCode(String var1);

    public void save(AssistExtendDimVO var1);

    public void update(AssistExtendDimVO var1);

    public List<AssistExtendDimVO> getAllStartAssistExtendDim();

    public List<AssistExtendDimVO> getAllAssistExtendDim();

    public void stopAssistExtendDimById(String var1);

    public void startAssistExtendDimById(String var1);

    public List<DataModelColumn> getBaseDataColumns(String var1);
}

