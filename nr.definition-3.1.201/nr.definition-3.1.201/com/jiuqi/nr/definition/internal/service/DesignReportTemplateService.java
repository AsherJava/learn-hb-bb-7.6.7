/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.nr.file.FileAreaConfig
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.xlib.utils.CollectionUtils
 *  com.jiuqi.xlib.utils.StringUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.definition.deploy.DeployDefinitionDao;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import com.jiuqi.nr.definition.internal.dao.DesignReportTemplateDao;
import com.jiuqi.nr.definition.internal.dao.RuntimeReportTemplateDao;
import com.jiuqi.nr.definition.internal.impl.DesignReportTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.impl.ReportTemplateDefineImpl;
import com.jiuqi.nr.file.FileAreaConfig;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.xlib.utils.CollectionUtils;
import com.jiuqi.xlib.utils.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DesignReportTemplateService {
    @Autowired
    private DesignReportTemplateDao reportTemplateDao;
    @Autowired
    private RuntimeReportTemplateDao runtimeReportTemplateDao;
    @Autowired
    private DeployDefinitionDao deployDefinitionDao;
    private FileAreaService fileAreaService;
    private static final String FILE_AREA_NAME = "REPORTFIEL";
    protected static final FileAreaConfig FILE_ARER_CONFIG = new FileAreaConfig(){

        public String getName() {
            return DesignReportTemplateService.FILE_AREA_NAME;
        }

        public long getMaxFileSize() {
            return 0x6400000L;
        }

        public long getExpirationTime() {
            return super.getExpirationTime();
        }

        public boolean isEnableRecycleBin() {
            return false;
        }

        public boolean isEnableEncrypt() {
            return false;
        }

        public boolean isEnableFastDownload() {
            return false;
        }

        public boolean isHashFileDate() {
            return super.isHashFileDate();
        }

        public String getDesc() {
            return "\u5206\u6790\u62a5\u544a\u6a21\u677f-\u4e0a\u4f20\u7684\u6587\u4ef6";
        }
    };

    @Autowired
    private void getFileArea(FileService fileService) {
        this.fileAreaService = fileService.area(FILE_ARER_CONFIG);
    }

    public DesignReportTemplateDefine getReportTemplate(String key) {
        return (DesignReportTemplateDefine)this.reportTemplateDao.getByKey(key);
    }

    public List<DesignReportTemplateDefine> getReportTemplates(List<String> key) {
        return this.toInterfaceList(this.reportTemplateDao.getByKey(key));
    }

    private <I> List<I> toInterfaceList(List<? extends I> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return new ArrayList<I>(list);
    }

    public List<DesignReportTemplateDefine> getReportTemplateByTask(String taskKey) {
        return this.toInterfaceList(this.reportTemplateDao.getByTask(taskKey));
    }

    public List<DesignReportTemplateDefine> getReportTemplateByScheme(String formSchemeKey) {
        return this.toInterfaceList(this.reportTemplateDao.getByFormScheme(formSchemeKey));
    }

    public DesignReportTemplateDefine createReportTemplateDefine() {
        DesignReportTemplateDefineImpl impl = new DesignReportTemplateDefineImpl();
        impl.setKey(UUIDUtils.getKey());
        return impl;
    }

    public void checkTemplate(DesignReportTemplateDefine template) throws JQException {
        if (!StringUtils.hasText((String)template.getFileKey())) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_3001);
        }
    }

    public void insertReportTemplate(DesignReportTemplateDefine template) throws JQException {
        if (null == template) {
            return;
        }
        this.checkTemplate(template);
        try {
            this.reportTemplateDao.insert(template);
        }
        catch (BeanParaException | DBParaException e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_3002, e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void insertReportTemplate(DesignReportTemplateDefine template, String originalFileName, InputStream inputStream) throws JQException {
        if (null == template) {
            return;
        }
        String fileKey = this.uploadReportTemplateFile(originalFileName, inputStream);
        template.setFileKey(fileKey);
        try {
            this.reportTemplateDao.insert(template);
        }
        catch (BeanParaException | DBParaException e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_3002, e);
        }
    }

    public void updateReportTemplate(DesignReportTemplateDefine template) throws JQException {
        if (null == template) {
            return;
        }
        this.checkTemplate(template);
        try {
            this.reportTemplateDao.update(template);
        }
        catch (BeanParaException | DBParaException e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_3002, e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void updateReportTemplate(String templateKey, String fileName, String originalFileName, InputStream inputStream) throws JQException {
        if (null == inputStream) {
            return;
        }
        String fileKey = this.uploadReportTemplateFile(originalFileName, inputStream);
        DesignReportTemplateDefineImpl template = (DesignReportTemplateDefineImpl)this.reportTemplateDao.getByKey(templateKey);
        template.setFileName(fileName);
        template.setFileKey(fileKey);
        try {
            this.reportTemplateDao.update(template);
        }
        catch (BeanParaException | DBParaException e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_3002, e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteReportTemplate(String ... keys) throws JQException {
        if (null == keys || 0 == keys.length) {
            return;
        }
        try {
            List<String> keyList = Arrays.asList(keys);
            List defines = this.reportTemplateDao.getByKey(keyList);
            List runtimeDefines = this.runtimeReportTemplateDao.getByKey(keyList);
            this.deleteReportTemplateFiles(defines, runtimeDefines);
            this.reportTemplateDao.delete(keys);
        }
        catch (BeanParaException | DBParaException e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_3003, e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteReportTemplateByScheme(String formSchemeKey) throws JQException {
        try {
            List defines = this.reportTemplateDao.getByFormScheme(formSchemeKey);
            List runtimeDefines = this.runtimeReportTemplateDao.getByFormScheme(formSchemeKey);
            this.deleteReportTemplateFiles(defines, runtimeDefines);
            this.reportTemplateDao.deleteByFormScheme(formSchemeKey);
        }
        catch (BeanParaException | DBParaException e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_3003, e);
        }
    }

    private void deleteReportTemplateFiles(List<? extends DesignReportTemplateDefine> defines, List<? extends ReportTemplateDefine> runtimeDefines) {
        if (CollectionUtils.isEmpty(defines)) {
            return;
        }
        HashSet<String> runtimeFileKeys = new HashSet<String>();
        if (!CollectionUtils.isEmpty(runtimeDefines)) {
            for (ReportTemplateDefine reportTemplateDefine : runtimeDefines) {
                runtimeFileKeys.add(reportTemplateDefine.getFileKey());
            }
        }
        for (DesignReportTemplateDefine designReportTemplateDefine : defines) {
            if (runtimeFileKeys.contains(designReportTemplateDefine.getFileKey())) continue;
            this.deleteReportTemplateFile(designReportTemplateDefine.getFileKey());
        }
    }

    public void deleteReportTemplateFile(String fileKey) {
        String fileKeyWithoutExtension = this.getFileKeyWithoutExtension(fileKey);
        FileInfo reportTemplateFileInfo = this.getReportTemplateFileInfo(fileKeyWithoutExtension);
        if (reportTemplateFileInfo != null) {
            this.fileAreaService.delete(fileKeyWithoutExtension);
        }
    }

    public String uploadReportTemplateFile(String fileName, InputStream inputStream) throws JQException {
        try {
            FileInfo upload = this.fileAreaService.upload(fileName, inputStream);
            String extension = upload.getExtension();
            if (extension == null) {
                extension = "";
            }
            return upload.getKey() + extension;
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_3002, (Throwable)e);
        }
    }

    public FileInfo getReportTemplateFileInfo(String fileKey) {
        String fileKeyWithoutExtension = this.getFileKeyWithoutExtension(fileKey);
        return this.fileAreaService.getInfo(fileKeyWithoutExtension);
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

    @Transactional(rollbackFor={Exception.class})
    public void doDeploy(String taskKey) {
        List runTemplates = this.runtimeReportTemplateDao.getByTask(taskKey);
        HashSet<String> templateKeys = new HashSet<String>();
        HashSet<String> runtimeTemplateFileKeys = new HashSet<String>();
        if (!CollectionUtils.isEmpty(runTemplates)) {
            for (ReportTemplateDefineImpl define : runTemplates) {
                templateKeys.add(define.getKey());
                runtimeTemplateFileKeys.add(define.getFileKey());
            }
            this.deployDefinitionDao.deleteReportTemplateDefines(templateKeys, false);
            this.deployDefinitionDao.deleteReportTagDefine(templateKeys, false);
        }
        HashSet<String> designTemplateFileKeys = new HashSet<String>();
        List desTemplates = this.reportTemplateDao.getByTask(taskKey);
        if (!CollectionUtils.isEmpty(desTemplates)) {
            templateKeys.clear();
            for (DesignReportTemplateDefineImpl define : desTemplates) {
                templateKeys.add(define.getKey());
                designTemplateFileKeys.add(define.getFileKey());
            }
            this.deployDefinitionDao.insertReportTemplateDefines(templateKeys, true);
            this.deployDefinitionDao.insertReportTagDefine(templateKeys, true);
        }
        runtimeTemplateFileKeys.removeAll(designTemplateFileKeys);
        if (!CollectionUtils.isEmpty(runtimeTemplateFileKeys)) {
            for (String fileKey : runtimeTemplateFileKeys) {
                this.deleteReportTemplateFile(fileKey);
            }
        }
    }
}

