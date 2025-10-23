/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.api.IDesignTimeReportController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignReportTagDefine
 *  com.jiuqi.nr.definition.facade.DesignReportTemplateDefine
 *  com.jiuqi.nr.definition.facade.ReportTemplateDefine
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.report.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.api.IDesignTimeReportController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import com.jiuqi.nr.report.common.DeletePM;
import com.jiuqi.nr.report.common.NrReportErrorEnum;
import com.jiuqi.nr.report.dto.ReportTemplateDTO;
import com.jiuqi.nr.report.service.IReportTemplateManageService;
import com.jiuqi.nr.report.web.vo.NameCheckVO;
import com.jiuqi.nr.report.web.vo.ReportTemplateVO;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ReportTemplateManageServiceImpl
implements IReportTemplateManageService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimeReportController reportDesignTimeController;

    @Override
    public ReportTemplateDTO initReportTemplateDefine() {
        DesignReportTemplateDefine reportTemplateDefine = this.reportDesignTimeController.initReportTemplate();
        return new ReportTemplateDTO(reportTemplateDefine);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void insertReportTemplate(MultipartFile file, String formSchemeKey) throws JQException {
        Throwable throwable;
        InputStream inputStream;
        DesignReportTemplateDefine define = this.reportDesignTimeController.initReportTemplate();
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
        define.setTaskKey(formScheme.getTaskKey());
        define.setFormSchemeKey(formSchemeKey);
        String originalFileName = file.getOriginalFilename();
        assert (originalFileName != null);
        String fileNameTemp = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        List templateList = this.reportDesignTimeController.listReportTemplateByFormScheme(formSchemeKey);
        String fileName = this.getFileName(templateList, fileNameTemp);
        define.setFileName(fileName);
        define.setFileNameExp(fileNameTemp);
        define.setOrder(OrderGenerator.newOrder());
        try {
            inputStream = file.getInputStream();
            throwable = null;
            try {
                this.reportDesignTimeController.insertReportTemplate(define, originalFileName, inputStream);
                this.updateFormSchemeDate(formSchemeKey);
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (inputStream != null) {
                    if (throwable != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrReportErrorEnum.REPORT_ERROR_001);
        }
        try {
            inputStream = file.getInputStream();
            throwable = null;
            try {
                List reportTagDefines = this.reportDesignTimeController.filterCustomTagsByReportTemplate(inputStream, define.getKey());
                for (DesignReportTagDefine reportTagDefine : reportTagDefines) {
                    reportTagDefine.setType(1);
                }
                this.reportDesignTimeController.insertReportTag(reportTagDefines);
            }
            catch (Throwable throwable4) {
                throwable = throwable4;
                throw throwable4;
            }
            finally {
                if (inputStream != null) {
                    if (throwable != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable throwable5) {
                            throwable.addSuppressed(throwable5);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrReportErrorEnum.REPORT_ERROR_002);
        }
    }

    @Override
    public ReportTemplateDTO getReportTemplate(String key) {
        DesignReportTemplateDefine reportTemplate = this.reportDesignTimeController.getReportTemplate(key);
        return new ReportTemplateDTO(reportTemplate);
    }

    @Override
    public List<ReportTemplateDTO> listReportTemplateByTask(String taskKey) {
        List designFormSchemeDefines = this.designTimeViewController.listFormSchemeByTask(taskKey);
        ArrayList<DesignReportTemplateDefine> reportTemplateByScheme = new ArrayList<DesignReportTemplateDefine>();
        for (DesignFormSchemeDefine designFormSchemeDefine : designFormSchemeDefines) {
            reportTemplateByScheme.addAll(this.reportDesignTimeController.listReportTemplateByFormScheme(designFormSchemeDefine.getKey()));
        }
        reportTemplateByScheme.sort(Comparator.comparing(ReportTemplateDefine::getOrder).thenComparing(ReportTemplateDefine::getUpdateTime));
        return reportTemplateByScheme.stream().map(ReportTemplateDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<ReportTemplateDTO> listReportTemplateByScheme(String formSchemeKey) {
        List reportTemplateByScheme = this.reportDesignTimeController.listReportTemplateByFormScheme(formSchemeKey);
        reportTemplateByScheme.sort(Comparator.comparing(ReportTemplateDefine::getOrder).thenComparing(ReportTemplateDefine::getUpdateTime));
        return reportTemplateByScheme.stream().map(ReportTemplateDTO::new).collect(Collectors.toList());
    }

    @Override
    public void updateReportTemplate(ReportTemplateVO template) {
        DesignReportTemplateDefine define = this.reportDesignTimeController.getReportTemplate(template.getKey());
        define.setFileName(template.getFileName());
        define.setFileNameExp(template.getFileNameExp());
        define.setCondition(template.getCondition());
        this.reportDesignTimeController.updateReportTemplate(define);
        this.updateFormSchemeDate(template.getFormSchemeKey());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateReportTemplateFile(MultipartFile file, String templateKey, String formSchemeKey) throws JQException {
        Throwable throwable;
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
            throwable = null;
            try {
                String originalFileName = file.getOriginalFilename();
                assert (originalFileName != null);
                String fileNameTemp = originalFileName.substring(0, originalFileName.lastIndexOf("."));
                List templateList = this.reportDesignTimeController.listReportTemplateByFormScheme(formSchemeKey);
                templateList.removeIf(o -> o.getKey().equals(templateKey));
                String fileName = this.getFileName(templateList, fileNameTemp);
                this.reportDesignTimeController.updateReportTemplateFile(templateKey, fileName, originalFileName, inputStream);
                this.updateFormSchemeDate(formSchemeKey);
            }
            catch (Throwable originalFileName) {
                throwable = originalFileName;
                throw originalFileName;
            }
            finally {
                if (inputStream != null) {
                    if (throwable != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable originalFileName) {
                            throwable.addSuppressed(originalFileName);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrReportErrorEnum.REPORT_ERROR_003, (Throwable)e);
        }
        try {
            inputStream = file.getInputStream();
            throwable = null;
            try {
                List reportTagDefines = this.reportDesignTimeController.filterCustomTagsByReportTemplate(inputStream, templateKey);
                List existTagDefines = this.reportDesignTimeController.listReportTagByReportTemplate(templateKey);
                for (DesignReportTagDefine existTagDefine : existTagDefines) {
                    reportTagDefines.removeIf(o -> o.getContent().equals(existTagDefine.getContent()));
                }
                for (DesignReportTagDefine reportTagDefine : reportTagDefines) {
                    reportTagDefine.setType(1);
                }
                this.reportDesignTimeController.insertReportTag(reportTagDefines);
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (inputStream != null) {
                    if (throwable != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrReportErrorEnum.REPORT_ERROR_004, (Throwable)e);
        }
    }

    private String getFileName(List<DesignReportTemplateDefine> templateList, String fileNameTemp) {
        Set templateListFileName = templateList.stream().map(ReportTemplateDefine::getFileName).collect(Collectors.toSet());
        String fileName = "";
        if (templateListFileName.contains(fileNameTemp)) {
            int idx = 1;
            while (templateListFileName.contains(fileName = fileNameTemp + idx++)) {
            }
        } else {
            fileName = fileNameTemp;
        }
        return fileName;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteReportTemplate(DeletePM deletePM) {
        this.reportDesignTimeController.deleteReportTemplate(deletePM.getTemplateKeys());
        for (String templateKey : deletePM.getTemplateKeys()) {
            this.reportDesignTimeController.deleteReportTagByReportTemplate(templateKey);
        }
        String formSchemeKey = deletePM.getFormSchemeKey();
        if (StringUtils.hasLength(formSchemeKey)) {
            this.updateFormSchemeDate(formSchemeKey);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteReportTemplateByScheme(String formSchemeKey) {
        List reportTemplateByScheme = this.reportDesignTimeController.listReportTemplateByFormScheme(formSchemeKey);
        for (DesignReportTemplateDefine designReportTemplateDefine : reportTemplateByScheme) {
            this.reportDesignTimeController.deleteReportTagByReportTemplate(designReportTemplateDefine.getKey());
        }
        this.reportDesignTimeController.deleteReportTemplateByFormScheme(formSchemeKey);
        this.updateFormSchemeDate(formSchemeKey);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void exportReportTemplateFile(HttpServletResponse response, String fileKey) throws IOException {
        ServletOutputStream outputStream = null;
        try {
            String originalFileName = this.reportDesignTimeController.getReportTemplateFileInfo(fileKey).getName();
            ReportTemplateManageServiceImpl.extracted(response, originalFileName);
            outputStream = response.getOutputStream();
            this.reportDesignTimeController.getReportTemplateFile(fileKey, (OutputStream)outputStream);
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    @Override
    public boolean nameCheck(NameCheckVO checkVO) {
        List designReportTemplateDefines = this.reportDesignTimeController.listReportTemplateByFormScheme(checkVO.getFormSchemeKey());
        return designReportTemplateDefines.stream().anyMatch(e -> e.getFileName().equalsIgnoreCase(checkVO.getNewTitle()) && !e.getKey().equals(checkVO.getTemplateKey()));
    }

    protected static void extracted(HttpServletResponse response, String fileName) throws IOException {
        fileName = URLEncoder.encode(fileName, "utf-8").replace("\\+", "%20");
        fileName = "attachment;filename=" + fileName;
        response.setHeader("Content-disposition", fileName);
        response.setContentType("application/octet-stream");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
    }

    private void updateFormSchemeDate(String formSchemeKey) {
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.getFormScheme(formSchemeKey);
        if (formSchemeDefine != null) {
            formSchemeDefine.setUpdateTime(new Date());
            this.designTimeViewController.updateFormScheme(formSchemeDefine);
        }
    }
}

