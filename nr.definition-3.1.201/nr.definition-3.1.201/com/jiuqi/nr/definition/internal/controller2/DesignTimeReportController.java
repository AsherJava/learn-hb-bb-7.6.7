/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.definition.internal.controller2;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.api.IDesignTimeReportController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import com.jiuqi.nr.definition.facade.report.TransformReportDefine;
import com.jiuqi.nr.definition.internal.impl.DesignReportTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.service.DesignReportTemplateService;
import com.jiuqi.nr.definition.internal.service.DesignTransformReportService;
import com.jiuqi.nr.definition.reportTag.service.IDesignReportTagService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.util.OrderGenerator;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DesignTimeReportController
implements IDesignTimeReportController {
    private static final Logger logger = LoggerFactory.getLogger(DesignTimeReportController.class);
    @Autowired
    private DesignReportTemplateService designReportTemplateService;
    @Autowired
    private IDesignReportTagService designReportTagService;
    @Autowired
    private DesignTransformReportService designTransformReportService;

    @Override
    public DesignReportTemplateDefine initReportTemplate() {
        DesignReportTemplateDefineImpl define = new DesignReportTemplateDefineImpl();
        define.setOrder(OrderGenerator.newOrder());
        define.setKey(UUIDUtils.getKey());
        define.setUpdateTime(new Date());
        return define;
    }

    @Override
    public void insertReportTemplate(DesignReportTemplateDefine template, String originalFileName, InputStream inputStream) {
        try {
            this.designReportTemplateService.insertReportTemplate(template, originalFileName, inputStream);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateReportTemplate(DesignReportTemplateDefine designReportTemplateDefines) {
        try {
            this.designReportTemplateService.updateReportTemplate(designReportTemplateDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void deleteReportTemplate(String ... keys) {
        try {
            this.designReportTemplateService.deleteReportTemplate(keys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_DELETE, (Throwable)e);
        }
    }

    @Override
    public void deleteReportTemplateByFormScheme(String formScheme) {
        try {
            this.designReportTemplateService.deleteReportTemplateByScheme(formScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_DELETE, (Throwable)e);
        }
    }

    @Override
    public List<DesignReportTemplateDefine> listReportTemplateByFormScheme(String formScheme) {
        try {
            return this.designReportTemplateService.getReportTemplateByScheme(formScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_QUERY, (Throwable)e);
        }
    }

    @Override
    public void updateReportTemplateFile(String templateKey, String fileName, String originalFileName, InputStream inputStream) {
        try {
            this.designReportTemplateService.updateReportTemplate(templateKey, fileName, originalFileName, inputStream);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void getReportTemplateFile(String fileKey, OutputStream outputStream) {
        try {
            this.designReportTemplateService.getReportTemplateFile(fileKey, outputStream);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_QUERY, (Throwable)e);
        }
    }

    @Override
    public byte[] getReportTemplateFile(String fileKey) {
        try {
            return this.designReportTemplateService.getReportTemplateFile(fileKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignReportTemplateDefine getReportTemplate(String reportKey) {
        try {
            return this.designReportTemplateService.getReportTemplate(reportKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_QUERY, (Throwable)e);
        }
    }

    @Override
    public FileInfo getReportTemplateFileInfo(String fileKey) {
        try {
            return this.designReportTemplateService.getReportTemplateFileInfo(fileKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_QUERY, (Throwable)e);
        }
    }

    @Override
    public void insertReportTag(List<DesignReportTagDefine> designReportTagDefines) {
        try {
            this.designReportTagService.insertTags(designReportTagDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_TAG_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_TAG_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateReportTag(List<DesignReportTagDefine> designReportTagDefines) {
        try {
            this.designReportTagService.updateTags(designReportTagDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_TAG_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_TAG_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void deleteReportTag(List<String> keys) {
        try {
            this.designReportTagService.deleteTagByKeys(keys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_TAG_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_TAG_DELETE, (Throwable)e);
        }
    }

    @Override
    public void deleteReportTagByReportTemplate(String reportTemplate) {
        try {
            this.designReportTagService.deleteTagsByRptKey(reportTemplate);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_TAG_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_TAG_DELETE, (Throwable)e);
        }
    }

    @Override
    public List<DesignReportTagDefine> listReportTagByReportTemplate(String reportTemplate) {
        try {
            return this.designReportTagService.queryAllTagsByRptKey(reportTemplate);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_TAG_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_TAG_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignReportTagDefine> filterCustomTagsByReportTemplate(InputStream is, String rptKey) {
        try {
            return this.designReportTagService.filterCustomTagsInRpt(is, rptKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_TAG_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_TAG_QUERY, (Throwable)e);
        }
    }

    @Override
    public TransformReportDefine exportReportTemplate(String formSchemeKey) {
        try {
            return this.designTransformReportService.exportReport(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_EXPORT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_EXPORT, (Throwable)e);
        }
    }

    @Override
    public void importReportTemplate(TransformReportDefine transformReportDefine, Boolean isFullAmountImport) {
        try {
            this.designTransformReportService.importReport(transformReportDefine, isFullAmountImport);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.REPORT_IMPORT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.REPORT_IMPORT, (Throwable)e);
        }
    }
}

