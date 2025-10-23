/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.fmdm.service.impl;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.common.Utils;
import com.jiuqi.nr.fmdm.service.AbstractFMDMAttributeQueryService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RunTimeFMDMAttributeServiceImpl
extends AbstractFMDMAttributeQueryService
implements IFMDMAttributeService {
    @Autowired
    private IRunTimeViewController runTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    @Override
    protected String getEntityIdByFormSchemeKey(String formSchemeKey) {
        String entityId = Utils.getEntityId();
        if (StringUtils.hasText(entityId)) {
            return entityId;
        }
        FormSchemeDefine formScheme = this.runTimeController.getFormScheme(formSchemeKey);
        return formScheme.getDw();
    }

    @Override
    protected String getFMDMFormDefine(String formSchemeKey) {
        List formDefines = this.runTimeController.queryAllFormDefinesByFormScheme(formSchemeKey);
        Optional<FormDefine> findFMDM = formDefines.stream().filter(e -> FormType.FORM_TYPE_NEWFMDM.equals((Object)e.getFormType())).findFirst();
        if (findFMDM.isPresent()) {
            return findFMDM.get().getKey();
        }
        return null;
    }

    @Override
    protected List<? extends DataLinkDefine> getAllFMDMLinks(String formSchemeKey) {
        String fmdmFormKey = this.getFMDMFormDefine(formSchemeKey);
        if (!StringUtils.hasText(fmdmFormKey)) {
            return Collections.emptyList();
        }
        return this.runTimeController.getAllLinksInForm(fmdmFormKey);
    }

    @Override
    protected FormSchemeDefine getFormSchemeDefine(String formSchemeKey) {
        return this.runTimeController.getFormScheme(formSchemeKey);
    }

    @Override
    protected TaskDefine getTaskDefine(String taskKey) {
        return this.runTimeController.queryTaskDefine(taskKey);
    }

    @Override
    protected FieldDefine queryFieldInTable(String fieldCode, String tableKey) throws Exception {
        return this.dataDefinitionRuntimeController.queryFieldByCodeInTable(fieldCode, tableKey);
    }
}

