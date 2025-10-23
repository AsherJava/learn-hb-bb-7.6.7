/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  org.json.JSONObject
 */
package com.jiuqi.nr.param.transfer.definition.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.param.transfer.definition.dao.AttachmentDao;
import com.jiuqi.nr.param.transfer.definition.dao.AttachmentRuleDTO;
import com.jiuqi.nr.param.transfer.definition.dao.TemplateFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class AttachmentService {
    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FileService fileService;
    @Autowired
    private DesignFormDefineService formService;

    public List<AttachmentRuleDTO> getByFormKey(String formKey) {
        List<AttachmentRuleDTO> attachments = this.attachmentDao.getAttachments(formKey);
        Iterator<AttachmentRuleDTO> iterator = attachments.iterator();
        while (iterator.hasNext()) {
            String atta;
            AttachmentRuleDTO attachmentRule = iterator.next();
            boolean remove = true;
            if (attachmentRule.getAttachment() != null && !StringUtils.isEmpty((String)(atta = DesignFormDefineBigDataUtil.bytesToString((byte[])attachmentRule.getAttachment())))) {
                String groupKey;
                remove = false;
                JSONObject json = new JSONObject(atta);
                if (json.has("groupKey") && StringUtils.isNotEmpty((String)(groupKey = json.getString("groupKey")))) {
                    String[] split = groupKey.split("\\|");
                    List fileInfoByGroup = this.fileInfoService.getFileInfoByGroup(split[0], split[1], FileStatus.AVAILABLE);
                    ArrayList<TemplateFile> list = new ArrayList<TemplateFile>();
                    for (FileInfo fileInfo : fileInfoByGroup) {
                        FileAreaService service = this.fileService.area(fileInfo.getArea());
                        byte[] fileData = service.download(fileInfo.getKey());
                        TemplateFile file = new TemplateFile();
                        file.setFileData(fileData);
                        file.setFileName(fileInfo.getName());
                        file.setArea(fileInfo.getArea());
                        file.setGroupKey(fileInfo.getFileGroupKey());
                        list.add(file);
                    }
                    attachmentRule.setTemplateFiles(list);
                }
            }
            if (!remove) continue;
            iterator.remove();
        }
        if (attachments.isEmpty()) {
            return null;
        }
        return attachments;
    }

    public void setByAttachmentRuleDTO(List<AttachmentRuleDTO> attachmentRules) {
        if (attachmentRules == null) {
            return;
        }
        for (AttachmentRuleDTO attachment : attachmentRules) {
            try {
                this.formService.updateBigDataDefine(attachment.getDlKey(), "ATTACHMENT", attachment.getAttachment());
                if (CollectionUtils.isEmpty(attachment.getTemplateFiles())) continue;
                for (TemplateFile templateFile : attachment.getTemplateFiles()) {
                    FileAreaService fileAreaService = this.fileService.area(templateFile.getArea());
                    FileInfo fileInfo = fileAreaService.uploadByGroup(templateFile.getFileName(), templateFile.getGroupKey(), templateFile.getFileData());
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

