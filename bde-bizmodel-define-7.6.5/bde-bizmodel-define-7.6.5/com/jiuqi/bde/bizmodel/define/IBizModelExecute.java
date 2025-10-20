/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.base.intf.FetchResultDim
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.intf.FetchSettingCacheKey
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.Pair
 */
package com.jiuqi.bde.bizmodel.define;

import com.jiuqi.bde.base.intf.FetchResultDim;
import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.intf.FetchSettingCacheKey;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.Pair;
import java.util.List;
import java.util.Map;

public interface IBizModelExecute {
    public String getComputationModelCode();

    public Pair<FetchResultDim, List<FetchResult>> doFixedExecute(FetchTaskContext var1, FetchSettingCacheKey var2, List<ExecuteSettingVO> var3);

    public Pair<FetchResultDim, List<FetchResult>> doFloatExecute(FetchTaskContext var1, FetchSettingCacheKey var2, List<Map<String, String>> var3, List<ExecuteSettingVO> var4);
}

