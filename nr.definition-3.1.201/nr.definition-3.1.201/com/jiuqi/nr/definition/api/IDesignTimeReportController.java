/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileInfo
 */
package com.jiuqi.nr.definition.api;

import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import com.jiuqi.nr.definition.facade.report.TransformReportDefine;
import com.jiuqi.nr.file.FileInfo;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface IDesignTimeReportController {
    public DesignReportTemplateDefine initReportTemplate();

    public void insertReportTemplate(DesignReportTemplateDefine var1, String var2, InputStream var3);

    public void updateReportTemplate(DesignReportTemplateDefine var1);

    public void deleteReportTemplate(String ... var1);

    public void deleteReportTemplateByFormScheme(String var1);

    public List<DesignReportTemplateDefine> listReportTemplateByFormScheme(String var1);

    public void updateReportTemplateFile(String var1, String var2, String var3, InputStream var4);

    public void getReportTemplateFile(String var1, OutputStream var2);

    public DesignReportTemplateDefine getReportTemplate(String var1);

    public FileInfo getReportTemplateFileInfo(String var1);

    public byte[] getReportTemplateFile(String var1);

    public void insertReportTag(List<DesignReportTagDefine> var1);

    public void updateReportTag(List<DesignReportTagDefine> var1);

    public void deleteReportTag(List<String> var1);

    public void deleteReportTagByReportTemplate(String var1);

    public List<DesignReportTagDefine> listReportTagByReportTemplate(String var1);

    public List<DesignReportTagDefine> filterCustomTagsByReportTemplate(InputStream var1, String var2);

    public TransformReportDefine exportReportTemplate(String var1);

    public void importReportTemplate(TransformReportDefine var1, Boolean var2);
}

