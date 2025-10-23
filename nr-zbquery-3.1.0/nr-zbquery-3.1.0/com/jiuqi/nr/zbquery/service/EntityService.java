/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.service;

import com.jiuqi.nr.zbquery.rest.vo.OrgInfoVO;
import java.util.List;
import java.util.Map;

public interface EntityService {
    public Map<String, List<String>> getUnitDirectChildren(List<OrgInfoVO> var1) throws Exception;

    public Map<String, List<String>> getUnitDirectChildrenOnePeriod(OrgInfoVO var1) throws Exception;
}

