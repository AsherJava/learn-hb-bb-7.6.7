/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.ExtInfoParamVO
 *  com.jiuqi.bde.bizmodel.client.vo.ExtInfoResultVO
 */
package com.jiuqi.bde.bizmodel.impl.model.service;

import com.jiuqi.bde.bizmodel.client.vo.ExtInfoParamVO;
import com.jiuqi.bde.bizmodel.client.vo.ExtInfoResultVO;
import java.util.List;
import java.util.Map;

public interface BizModelSettingService {
    public Map<String, List<ExtInfoResultVO>> queryExtInfo(ExtInfoParamVO var1);
}

