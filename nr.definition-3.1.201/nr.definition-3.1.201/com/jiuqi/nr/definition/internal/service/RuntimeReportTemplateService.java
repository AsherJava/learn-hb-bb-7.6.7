/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.xlib.utils.CollectionUtils
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import com.jiuqi.nr.definition.internal.dao.RuntimeReportTemplateDao;
import com.jiuqi.nr.definition.internal.service.DesignReportTemplateService;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.xlib.utils.CollectionUtils;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuntimeReportTemplateService {
    @Autowired
    private RuntimeReportTemplateDao reportTemplateDao;
    private FileAreaService fileAreaService;

    @Autowired
    private void getFileArea(FileService fileService) {
        this.fileAreaService = fileService.area(DesignReportTemplateService.FILE_ARER_CONFIG);
    }

    public ReportTemplateDefine getReportTemplate(String key) {
        return this.reportTemplateDao.getByKey(key);
    }

    private <I> List<I> toInterfaceList(List<? extends I> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return new ArrayList<I>(list);
    }

    public List<ReportTemplateDefine> getReportTemplateByTask(String taskKey) {
        return this.toInterfaceList(this.reportTemplateDao.getByTask(taskKey));
    }

    public List<ReportTemplateDefine> getReportTemplateByScheme(String formSchemeKey) {
        return this.toInterfaceList(this.reportTemplateDao.getByFormScheme(formSchemeKey));
    }

    public byte[] getReportTemplateFile(String fileKey) {
        String fileKeyWithoutExtension = this.getFileKeyWithoutExtension(fileKey);
        return this.fileAreaService.download(fileKeyWithoutExtension);
    }

    public void getReportTemplateFile(String fileKey, OutputStream outputStream) {
        String fileKeyWithoutExtension = this.getFileKeyWithoutExtension(fileKey);
        this.fileAreaService.download(fileKeyWithoutExtension, outputStream);
    }

    private String getFileKeyWithoutExtension(String fileKey) {
        int ext = fileKey.lastIndexOf(".");
        return ext > 0 ? fileKey.substring(0, ext) : fileKey;
    }
}

