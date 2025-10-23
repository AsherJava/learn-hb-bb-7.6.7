/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.report.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.report.common.DeletePM;
import com.jiuqi.nr.report.dto.ReportTemplateDTO;
import com.jiuqi.nr.report.web.vo.NameCheckVO;
import com.jiuqi.nr.report.web.vo.ReportTemplateVO;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IReportTemplateManageService {
    public ReportTemplateDTO initReportTemplateDefine();

    public void insertReportTemplate(MultipartFile var1, String var2) throws JQException;

    public ReportTemplateDTO getReportTemplate(String var1);

    public List<ReportTemplateDTO> listReportTemplateByTask(String var1);

    public List<ReportTemplateDTO> listReportTemplateByScheme(String var1);

    public void updateReportTemplate(ReportTemplateVO var1);

    public void updateReportTemplateFile(MultipartFile var1, String var2, String var3) throws JQException;

    public void deleteReportTemplate(DeletePM var1);

    public void deleteReportTemplateByScheme(String var1);

    public void exportReportTemplateFile(HttpServletResponse var1, String var2) throws IOException;

    public boolean nameCheck(NameCheckVO var1);
}

