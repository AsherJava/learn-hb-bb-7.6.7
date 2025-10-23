/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.instance.service;

import com.jiuqi.nr.workflow2.instance.context.InstanceDetailDataContext;
import com.jiuqi.nr.workflow2.instance.context.InstanceTableDataContext;
import com.jiuqi.nr.workflow2.instance.entity.InstanceFormDetailData;
import com.jiuqi.nr.workflow2.instance.vo.InstanceInitDataVO;
import com.jiuqi.nr.workflow2.instance.vo.InstanceTableDataVO;
import java.util.List;

public interface Workflow2InstanceQueryService {
    public InstanceTableDataVO queryTableData(InstanceTableDataContext var1);

    public List<InstanceFormDetailData> queryFormDetailData(InstanceDetailDataContext var1);

    public InstanceInitDataVO initInstanceData(String var1, String var2);
}

