/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.util.ServeCodeService
 */
package com.jiuqi.nr.designer.helper;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.web.treebean.FormGroupObject;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveFormGroupHelper {
    @Autowired
    private ServeCodeService serveCodeService;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;

    public void saveFormGroupObject(Map<String, FormGroupObject> groups) throws Exception {
        if (groups == null) {
            return;
        }
        Collection<FormGroupObject> values = groups.values();
        for (FormGroupObject groupObject : values) {
            if (groupObject == null) continue;
            String groupKey = groupObject.getID();
            DesignFormGroupDefine formGroup = this.nrDesignTimeController.queryFormGroup(groupKey);
            if (groupObject.isIsDeleted()) {
                this.nrDesignTimeController.deleteFormGroup(groupKey);
                continue;
            }
            if (groupObject.isIsNew() && formGroup == null) {
                formGroup = this.nrDesignTimeController.createFormGroup();
                groupObject.setOwnerLevelAndId(this.serveCodeService.getServeCode());
                this.initFormGroup(groupObject, groupKey, formGroup);
                this.nrDesignTimeController.insertFormGroup(formGroup);
                continue;
            }
            if (!groupObject.isIsDirty() || formGroup == null) continue;
            this.initFormGroup(groupObject, groupKey, formGroup);
            formGroup.setUpdateTime(new Date());
            this.nrDesignTimeController.updateFormGroup(formGroup);
        }
    }

    public void saveFormGroup(FormGroupObject formGroupObject) throws Exception {
        String groupKey = formGroupObject.getID();
        DesignFormGroupDefine formGroup = this.nrDesignTimeController.queryFormGroup(groupKey);
        if (formGroupObject.isIsDeleted()) {
            this.nrDesignTimeController.deleteFormGroup(groupKey);
        } else if (formGroupObject.isIsNew() && formGroup == null) {
            formGroup = this.nrDesignTimeController.createFormGroup();
            formGroupObject.setOwnerLevelAndId(this.serveCodeService.getServeCode());
            this.initFormGroup(formGroupObject, groupKey, formGroup);
            this.nrDesignTimeController.insertFormGroup(formGroup);
        } else if (formGroupObject.isIsDirty() && formGroup != null) {
            this.initFormGroup(formGroupObject, groupKey, formGroup);
            formGroup.setUpdateTime(new Date());
            this.nrDesignTimeController.updateFormGroup(formGroup);
        }
    }

    private void initFormGroup(FormGroupObject groupObject, String groupKey, DesignFormGroupDefine formGroup) {
        formGroup.setKey(groupKey);
        formGroup.setCode(groupObject.getCode() == null ? OrderGenerator.newOrder() : groupObject.getCode());
        formGroup.setOrder(groupObject.getOrder() == null ? OrderGenerator.newOrder() : groupObject.getOrder());
        formGroup.setTitle(groupObject.getTitle());
        formGroup.setCondition(groupObject.getCondition());
        formGroup.setFormSchemeKey(groupObject.getFormSchemeKey());
        formGroup.setOwnerLevelAndId(groupObject.getOwnerLevelAndId());
        if (groupObject.getMeasureUnitIsExtend()) {
            formGroup.setMeasureUnit(null);
        } else {
            formGroup.setMeasureUnit(groupObject.getMeasureUnit());
        }
    }
}

