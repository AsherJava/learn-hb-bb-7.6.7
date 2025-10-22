/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.check.service;

import com.jiuqi.gcreport.inputdata.inputdata.dao.InputWriteNecLimitCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InputDataCheckOffsetService {
    public void cancelInputOffset(Collection<String> var1, InputWriteNecLimitCondition var2);

    public Map<String, Set<String>> doCheckAfterOffset(List<InputDataEO> var1);
}

