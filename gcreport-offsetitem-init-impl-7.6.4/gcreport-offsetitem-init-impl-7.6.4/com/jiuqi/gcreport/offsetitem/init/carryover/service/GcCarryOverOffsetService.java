/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectMappingVO
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.service;

import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectMappingVO;
import java.util.List;
import java.util.Map;

public interface GcCarryOverOffsetService {
    public String checkSubjectMapping(String var1, Map<String, List<CarryOverOffsetSubjectMappingVO>> var2);
}

