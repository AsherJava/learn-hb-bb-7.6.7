/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import com.jiuqi.nr.definition.facade.ReportTagDefine;
import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import com.jiuqi.nr.definition.facade.report.TransformReportDefine;
import com.jiuqi.nr.definition.facade.report.TransportReportTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.dao.DesignReportTagDao;
import com.jiuqi.nr.definition.internal.dao.DesignReportTemplateDao;
import com.jiuqi.nr.definition.internal.impl.DesignReportTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.service.DesignReportTagService;
import com.jiuqi.nr.definition.internal.service.DesignReportTemplateService;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DesignTransformReportService {
    @Autowired
    private DesignReportTemplateService designReportTemplateService;
    @Autowired
    private DesignReportTagService designReportTagService;
    @Autowired
    private DesignReportTagDao designReportTagDao;
    @Autowired
    private DesignReportTemplateDao designReportTemplateDao;
    private final Logger logger = LoggerFactory.getLogger(DesignTransformReportService.class);

    public TransformReportDefine exportReport(String formSchemeKey) {
        TransformReportDefine transformReportDefine = new TransformReportDefine();
        ArrayList<DesignReportTagDefine> reportTagDefines = new ArrayList<DesignReportTagDefine>();
        List<DesignReportTemplateDefine> reportTemplateByScheme = this.designReportTemplateService.getReportTemplateByScheme(formSchemeKey);
        for (DesignReportTemplateDefine reportTemplate : reportTemplateByScheme) {
            List<DesignReportTagDefine> designReportTagDefines = this.designReportTagService.queryAllTagsByRptKey(reportTemplate.getKey());
            reportTagDefines.addAll(designReportTagDefines);
        }
        if (!reportTemplateByScheme.isEmpty()) {
            List<TransportReportTemplateDefineImpl> transportReportTemplateDefines = this.changeFormat(reportTemplateByScheme);
            transformReportDefine.setDesignReportTemplateDefines(transportReportTemplateDefines);
            transformReportDefine.setDesignReportTagDefines(reportTagDefines);
            return transformReportDefine;
        }
        return null;
    }

    @Transactional(rollbackFor={Exception.class})
    public void importReport(TransformReportDefine transformReportDefine, Boolean isFullAmountImport) throws JQException {
        if (!isFullAmountImport.booleanValue()) {
            this.importReport(transformReportDefine);
        } else {
            String formSchemeKey = transformReportDefine.getDesignReportTemplateDefines().get(0).getFormSchemeKey();
            this.deleteReportInfoByFormSchemeKey(formSchemeKey);
            this.importReportTemplateFile(transformReportDefine);
            List<TransportReportTemplateDefineImpl> importReportTemplateDefines = transformReportDefine.getDesignReportTemplateDefines();
            ArrayList<TransportReportTemplateDefineImpl> importTemplateData = new ArrayList<TransportReportTemplateDefineImpl>(importReportTemplateDefines);
            List<DesignReportTagDefine> designReportTagDefines = transformReportDefine.getDesignReportTagDefines();
            try {
                if (importTemplateData.size() != 0) {
                    this.designReportTemplateDao.insert(importTemplateData.toArray());
                }
                if (designReportTagDefines.size() != 0) {
                    this.designReportTagDao.insert(designReportTagDefines.toArray());
                }
            }
            catch (DBParaException e) {
                this.logger.error(e.getMessage());
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_069, (Throwable)e);
            }
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteReportInfoByFormSchemeKey(String formSchemeKey) throws JQException {
        List<DesignReportTemplateDefine> reportTemplateByScheme = this.designReportTemplateService.getReportTemplateByScheme(formSchemeKey);
        reportTemplateByScheme.stream().map(ReportTemplateDefine::getFileKey).collect(Collectors.toSet()).forEach(item -> this.designReportTemplateService.deleteReportTemplateFile((String)item));
        Set<String> collect = reportTemplateByScheme.stream().map(ReportTemplateDefine::getKey).collect(Collectors.toSet());
        this.designReportTagService.deleteTagsByRptKeys(collect);
        this.designReportTemplateService.deleteReportTemplateByScheme(formSchemeKey);
    }

    @Transactional(rollbackFor={Exception.class})
    public void importReport(TransformReportDefine transformReportDefine) throws JQException {
        this.importReportTemplateFile(transformReportDefine);
        List<TransportReportTemplateDefineImpl> transportReportTemplateDefines = transformReportDefine.getDesignReportTemplateDefines();
        ArrayList<TransportReportTemplateDefineImpl> needInsertDefines = new ArrayList<TransportReportTemplateDefineImpl>(transportReportTemplateDefines);
        ArrayList<TransportReportTemplateDefineImpl> needUpdateDefines = new ArrayList<TransportReportTemplateDefineImpl>(transportReportTemplateDefines);
        List<String> importKeys = transportReportTemplateDefines.stream().map(DesignReportTemplateDefineImpl::getKey).collect(Collectors.toList());
        List<DesignReportTemplateDefine> existed = this.designReportTemplateService.getReportTemplates(importKeys);
        needUpdateDefines.retainAll(existed);
        needInsertDefines.removeAll(existed);
        List<DesignReportTagDefine> reportTagDefines = transformReportDefine.getDesignReportTagDefines();
        Map<String, DesignReportTagDefine> TagDefines = reportTagDefines.stream().collect(Collectors.toMap(item -> item.getRptKey() + item.getContent(), item -> item));
        Set<String> needUpdateTags = TagDefines.keySet();
        HashSet<String> needInsertTags = new HashSet<String>(needUpdateTags);
        Set<String> collect = reportTagDefines.stream().map(ReportTagDefine::getRptKey).collect(Collectors.toSet());
        Map<String, DesignReportTagDefine> tagExisted = this.designReportTagService.queryTagsByRptKeys(collect).stream().collect(Collectors.toMap(item -> item.getRptKey() + item.getContent(), item -> item));
        Set<String> existedTagKey = tagExisted.keySet();
        needUpdateTags.retainAll(existedTagKey);
        List<DesignReportTagDefine> UpdateTags = this.getTagDefines(TagDefines, needUpdateTags);
        needInsertTags.removeAll(existedTagKey);
        List<DesignReportTagDefine> InsertTags = this.getTagDefines(TagDefines, needInsertTags);
        try {
            if (needUpdateDefines.size() != 0) {
                this.designReportTemplateDao.update(needUpdateDefines.toArray());
            }
            if (needInsertDefines.size() != 0) {
                this.designReportTemplateDao.insert(needInsertDefines.toArray());
            }
            if (needUpdateTags.size() != 0) {
                this.designReportTagDao.update(UpdateTags.toArray());
            }
            if (needInsertTags.size() != 0) {
                this.designReportTagDao.insert(InsertTags.toArray());
            }
        }
        catch (DBParaException e) {
            this.logger.error(e.getMessage());
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_069, (Throwable)e);
        }
    }

    private void importReportTemplateFile(TransformReportDefine transformReportDefine) throws JQException {
        List<TransportReportTemplateDefineImpl> transportReportTemplateDefines = transformReportDefine.getDesignReportTemplateDefines();
        for (TransportReportTemplateDefineImpl templateDefine : transportReportTemplateDefines) {
            String fileKey1 = templateDefine.getFileKey();
            int ext = fileKey1.lastIndexOf(".");
            String suffix = ext > 0 ? fileKey1.substring(ext) : "";
            String fileKey = this.designReportTemplateService.uploadReportTemplateFile(templateDefine.getFileName() + suffix, new ByteArrayInputStream(templateDefine.getTemplateFile()));
            templateDefine.setFileKey(fileKey);
        }
        transformReportDefine.setDesignReportTemplateDefines(transportReportTemplateDefines);
    }

    private List<DesignReportTagDefine> getTagDefines(Map<String, DesignReportTagDefine> resource, Set<String> keys) {
        ArrayList<DesignReportTagDefine> result = new ArrayList<DesignReportTagDefine>();
        for (String key : keys) {
            result.add(resource.get(key));
        }
        return result;
    }

    private List<TransportReportTemplateDefineImpl> changeFormat(List<DesignReportTemplateDefine> designReportTemplateDefines) {
        ArrayList<TransportReportTemplateDefineImpl> result = new ArrayList<TransportReportTemplateDefineImpl>();
        for (DesignReportTemplateDefine templateDefine : designReportTemplateDefines) {
            TransportReportTemplateDefineImpl item = new TransportReportTemplateDefineImpl();
            item.setKey(templateDefine.getKey());
            item.setTaskKey(templateDefine.getTaskKey());
            item.setFormSchemeKey(templateDefine.getFormSchemeKey());
            item.setFileKey(templateDefine.getFileKey());
            item.setFileName(templateDefine.getFileName());
            item.setFileNameExp(templateDefine.getFileNameExp());
            item.setCondition(templateDefine.getCondition());
            item.setOrder(templateDefine.getOrder());
            item.setUpdateTime(templateDefine.getUpdateTime());
            byte[] reportTemplateFile = this.designReportTemplateService.getReportTemplateFile(templateDefine.getFileKey());
            item.setTemplateFile(reportTemplateFile);
            result.add(item);
        }
        return result;
    }
}

