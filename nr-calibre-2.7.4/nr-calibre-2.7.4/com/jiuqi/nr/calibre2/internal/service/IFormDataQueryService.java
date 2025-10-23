/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.internal.service;

import com.jiuqi.nr.calibre2.vo.CommonReportFormVO;
import java.util.List;

public interface IFormDataQueryService {
    public List<CommonReportFormVO> getTaskList(String var1);

    public List<CommonReportFormVO> getFormSchemeList(String var1, String var2);

    public List<CommonReportFormVO> getFormList(String var1);
}

