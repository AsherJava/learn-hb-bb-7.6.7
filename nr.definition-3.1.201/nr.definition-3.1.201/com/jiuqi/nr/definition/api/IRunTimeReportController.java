/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.api;

import com.jiuqi.nr.definition.facade.ReportTagDefine;
import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import java.io.OutputStream;
import java.util.List;

public interface IRunTimeReportController {
    public ReportTemplateDefine getReportTemplate(String var1);

    public List<ReportTemplateDefine> listReportTemplateByFormScheme(String var1);

    public void getReportTemplateFile(String var1, OutputStream var2);

    public byte[] getReportTemplateFile(String var1);

    public List<ReportTagDefine> listReportTagByReportTemplate(String var1);
}

