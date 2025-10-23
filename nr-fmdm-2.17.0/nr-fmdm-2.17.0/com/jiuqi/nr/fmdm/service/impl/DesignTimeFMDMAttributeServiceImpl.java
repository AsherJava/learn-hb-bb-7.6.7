/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.fmdm.service.impl;

import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.fmdm.IDesignTimeFMDMAttributeService;
import com.jiuqi.nr.fmdm.common.Utils;
import com.jiuqi.nr.fmdm.service.AbstractFMDMAttributeQueryService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DesignTimeFMDMAttributeServiceImpl
extends AbstractFMDMAttributeQueryService
implements IDesignTimeFMDMAttributeService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDataDefinitionDesignTimeController dataDefinitionDesignTimeController;

    @Override
    protected String getEntityIdByFormSchemeKey(String formSchemeKey) {
        String entityId = Utils.getEntityId();
        if (StringUtils.hasText(entityId)) {
            return entityId;
        }
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(formSchemeKey);
        entityId = formSchemeDefine.getDw();
        if (!StringUtils.hasText(entityId)) {
            DesignTaskDefine designTaskDefine = this.designTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
            entityId = designTaskDefine.getDw();
        }
        return entityId;
    }

    @Override
    protected String getFMDMFormDefine(String formSchemeKey) {
        List formDefines = this.designTimeViewController.queryAllSoftFormDefinesByFormScheme(formSchemeKey);
        Optional<DesignFormDefine> findFMDMForm = formDefines.stream().filter(e -> FormType.FORM_TYPE_NEWFMDM.equals((Object)e.getFormType())).findFirst();
        if (findFMDMForm.isPresent()) {
            return findFMDMForm.get().getKey();
        }
        return null;
    }

    @Override
    protected List<? extends DataLinkDefine> getAllFMDMLinks(String formSchemeKey) {
        String formKey = this.getFMDMFormDefine(formSchemeKey);
        if (!StringUtils.hasText(formKey)) {
            return Collections.emptyList();
        }
        return this.designTimeViewController.getAllLinksInForm(formKey);
    }

    @Override
    protected FormSchemeDefine getFormSchemeDefine(String formSchemeKey) {
        return this.designTimeViewController.queryFormSchemeDefine(formSchemeKey);
    }

    @Override
    protected TaskDefine getTaskDefine(String taskKey) {
        return this.designTimeViewController.queryTaskDefine(taskKey);
    }

    @Override
    protected FieldDefine queryFieldInTable(String fieldCode, String tableKey) throws Exception {
        return this.dataDefinitionDesignTimeController.queryFieldDefineByCodeInTable(fieldCode, tableKey);
    }
}

