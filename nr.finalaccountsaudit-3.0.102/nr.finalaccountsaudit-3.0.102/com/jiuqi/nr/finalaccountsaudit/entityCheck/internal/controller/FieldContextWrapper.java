/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller;

import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.common.FmdmHelper;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.CheckConfigurationContent;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckVersionObjectInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.FieldContext;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value="prototype")
public class FieldContextWrapper {
    @Autowired
    private IEntityMetaService metaService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private FieldContext curfieldContext = new FieldContext();
    private FieldContext lstfieldContext = new FieldContext();
    private TaskLinkDefine taskLink;

    public FieldContext getCurfieldContext() {
        return this.curfieldContext;
    }

    public FieldContext getLstfieldContext() {
        return this.lstfieldContext;
    }

    public TaskLinkDefine getTaskLink() {
        return this.taskLink;
    }

    public void init(EntityCheckVersionObjectInfo currentVersionObjectInfo, CheckConfigurationContent configurationContent) throws Exception {
        this.initMaskFieds(this.curfieldContext, currentVersionObjectInfo, configurationContent);
    }

    private void initMaskFieds(FieldContext fieldContext, EntityCheckVersionObjectInfo currentVersionObjectInfo) throws Exception {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(currentVersionObjectInfo.getFromSchemeKey());
        String dwView = formSchemeDefine.getDw();
        IEntityDefine entityDefine = this.metaService.queryEntity(dwView);
        IEntityModel entityModel = this.metaService.getEntityModel(dwView);
        IEntityAttribute codeField = entityModel.getCodeField();
        IEntityAttribute bizkeyField = entityModel.getBizKeyField();
        if (codeField == null) {
            throw new Exception(String.format("%s\u672a\u8bbe\u7f6e\u5b9e\u4f53\u4ee3\u7801", entityDefine.getCode()));
        }
        fieldContext.setMasterCode(codeField);
        if (bizkeyField == null) {
            throw new Exception(String.format("%s\u672a\u8bbe\u7f6e\u4e1a\u52a1\u4e3b\u952e", entityDefine.getCode()));
        }
        fieldContext.setMasterBizKey(bizkeyField);
    }

    private void initMaskFieds(FieldContext fieldContext, EntityCheckVersionObjectInfo currentVersionObjectInfo, CheckConfigurationContent configurationContent) throws Exception {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(currentVersionObjectInfo.getFromSchemeKey());
        String dwView = formSchemeDefine.getDw();
        IEntityDefine entityDefine = this.metaService.queryEntity(dwView);
        IEntityModel entityModel = this.metaService.getEntityModel(dwView);
        IEntityAttribute codeField = entityModel.getCodeField();
        IEntityAttribute bizkeyField = entityModel.getBizKeyField();
        FmdmHelper fmdmHelper = FmdmHelper.newHelper(currentVersionObjectInfo.getFromSchemeKey());
        IFMDMAttribute bblxFieldDefine = configurationContent.getBblx();
        if (bblxFieldDefine != null) {
            fieldContext.setDefBBLXField(bblxFieldDefine);
        } else {
            fieldContext.setDefBBLXField(fmdmHelper.queryByCode("BBLX"));
        }
        IFMDMAttribute dwdm = configurationContent.getDwdm();
        if (dwdm != null) {
            fieldContext.setDWDM(dwdm);
        }
        if (codeField == null) {
            throw new Exception(String.format("%s\u672a\u8bbe\u7f6e\u5b9e\u4f53\u4ee3\u7801", entityDefine.getCode()));
        }
        fieldContext.setMasterCode(codeField);
        if (bizkeyField == null) {
            throw new Exception(String.format("%s\u672a\u8bbe\u7f6e\u4e1a\u52a1\u4e3b\u952e", entityDefine.getCode()));
        }
        fieldContext.setMasterBizKey(bizkeyField);
    }

    private void prepareFieldContext(FieldContext result, CheckConfigurationContent configurationContent, EntityCheckVersionObjectInfo versionObjectInfo, IFMDMAttribute bblxFieldDefine) throws Exception {
        String fromSchemeKey = versionObjectInfo.getFromSchemeKey();
        FmdmHelper fmdmHelper = FmdmHelper.newHelper(fromSchemeKey);
        if (configurationContent.getDwdm() != null) {
            result.setDWDM(configurationContent.getDwdm());
        }
        if (configurationContent.getSndm() != null) {
            result.setSNDM(configurationContent.getSndm());
        }
        if (configurationContent.getXbys() != null) {
            result.setXBYS(configurationContent.getXbys());
        }
        if (bblxFieldDefine != null) {
            result.setDefBBLXField(bblxFieldDefine);
        } else {
            result.setDefBBLXField(fmdmHelper.queryByCode("BBLX"));
        }
    }

    public void prepareFieldContextWrapper(CheckConfigurationContent configurationContent, EntityCheckVersionObjectInfo currentVersionObjectInfo, EntityCheckVersionObjectInfo contrastVersionObjectInfo) throws Exception {
        this.prepareFieldContext(this.curfieldContext, configurationContent, currentVersionObjectInfo, configurationContent.getBblx());
        this.prepareFieldContext(this.lstfieldContext, configurationContent, contrastVersionObjectInfo, configurationContent.getSnbblx());
        this.initMaskFieds(this.lstfieldContext, contrastVersionObjectInfo);
    }
}

