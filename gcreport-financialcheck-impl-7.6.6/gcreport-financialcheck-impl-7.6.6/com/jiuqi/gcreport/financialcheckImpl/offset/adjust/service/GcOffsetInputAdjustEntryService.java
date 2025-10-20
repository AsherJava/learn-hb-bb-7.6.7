/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckapi.offset.SubjectInfoVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service;

import com.jiuqi.gcreport.financialcheckapi.offset.SubjectInfoVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.List;
import java.util.Map;

public interface GcOffsetInputAdjustEntryService {
    public List<SubjectInfoVO> listSubjectInfo(String var1);

    public void setTitles(Pagination<Map<String, Object>> var1, QueryParamsVO var2, String var3);
}

