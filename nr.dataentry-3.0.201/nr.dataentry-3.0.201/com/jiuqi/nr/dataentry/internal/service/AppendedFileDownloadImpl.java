/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.service.AttachmentIOService
 *  com.jiuqi.nr.data.excel.extend.AppendedFileDownload
 *  com.jiuqi.nr.data.excel.obj.AppendedFileDownloadParam
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.data.excel.extend.AppendedFileDownload;
import com.jiuqi.nr.data.excel.obj.AppendedFileDownloadParam;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class AppendedFileDownloadImpl
implements AppendedFileDownload {
    @Autowired
    private AttachmentIOService attachmentIOService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private static final Logger logger = LoggerFactory.getLogger(AppendedFileDownloadImpl.class);

    public void download(String downloadPath, AppendedFileDownloadParam param) throws Exception {
        if (StringUtils.hasText(downloadPath) && !CollectionUtils.isEmpty(param.getFileGroupKeys())) {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(param.getFormSchemeKey());
            CommonParamsDTO params = new CommonParamsDTO();
            params.setDataSchemeKey(param.getDataSchemeKey());
            params.setTaskKey(formScheme.getTaskKey());
            HashMap groupByFileName = new HashMap();
            for (String string : param.getFileGroupKeys()) {
                List fileByGroup = this.attachmentIOService.getFileByGroup(string, params);
                for (FileInfo fileInfo : fileByGroup) {
                    if (!groupByFileName.containsKey(fileInfo.getName())) {
                        groupByFileName.put(fileInfo.getName(), new ArrayList());
                    }
                    ((List)groupByFileName.get(fileInfo.getName())).add(fileInfo);
                }
            }
            for (Map.Entry entry : groupByFileName.entrySet()) {
                String fileName = (String)entry.getKey();
                List fileInfos = (List)entry.getValue();
                int count = 0;
                for (FileInfo fileInfo : fileInfos) {
                    String expFileName = fileName;
                    if (count > 0) {
                        expFileName = fileName.substring(0, fileName.lastIndexOf(fileInfo.getExtension())) + "(" + count + ")" + fileInfo.getExtension();
                    }
                    String filePath = FilenameUtils.normalize(downloadPath + expFileName);
                    try (FileOutputStream fos = new FileOutputStream(filePath);){
                        this.attachmentIOService.download(fileInfo.getKey(), params, (OutputStream)fos);
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                    ++count;
                }
            }
        }
    }
}

