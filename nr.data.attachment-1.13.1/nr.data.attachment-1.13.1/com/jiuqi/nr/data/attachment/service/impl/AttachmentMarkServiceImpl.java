/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.service.AttachmentIOService
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.fielddatacrud.spi.AttachmentMarkService
 */
package com.jiuqi.nr.data.attachment.service.impl;

import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.fielddatacrud.spi.AttachmentMarkService;
import java.util.ArrayList;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentMarkServiceImpl
implements AttachmentMarkService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private AttachmentIOService attachmentIOService;
    @Autowired
    private FileOperationService fileOperationService;

    public void batchMarkDeletion(String formSchemeKey, Set<String> groupKeys) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        this.attachmentIOService.batchMarkDeletion(taskDefine.getDataScheme(), formSchemeKey, groupKeys);
    }

    public void batchMarkPictureDeletion(String formSchemeKey, Set<String> groupKeys) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(taskDefine.getDataScheme());
        this.fileOperationService.physicalDeletePicture(new ArrayList<String>(groupKeys), params);
    }
}

