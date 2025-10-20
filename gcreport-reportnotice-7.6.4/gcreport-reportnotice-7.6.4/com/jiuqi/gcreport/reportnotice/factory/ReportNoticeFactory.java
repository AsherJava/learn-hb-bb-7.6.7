/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportnotice.factory;

import com.jiuqi.gcreport.reportnotice.service.ReportNoticeService;
import java.util.HashMap;
import java.util.Map;

public class ReportNoticeFactory {
    private static final ReportNoticeFactory instance = new ReportNoticeFactory();
    private Map<String, ReportNoticeService> code2Class = new HashMap<String, ReportNoticeService>();

    public static ReportNoticeFactory getInstance() {
        return instance;
    }

    public void setCode2Class(String code, ReportNoticeService reportNoticeService) {
        this.code2Class.put(code, reportNoticeService);
    }

    public ReportNoticeService getClassOfCode(String code) {
        return this.code2Class.get(code);
    }
}

