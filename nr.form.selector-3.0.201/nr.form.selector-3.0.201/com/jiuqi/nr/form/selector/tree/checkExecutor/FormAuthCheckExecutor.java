/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 */
package com.jiuqi.nr.form.selector.tree.checkExecutor;

import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FormAuthCheckExecutor
implements IFormCheckExecutor {
    protected IDataAccessServiceProvider dataAccessServiceProvider;
    protected List<String> accessGroupAndFormKeys;

    public FormAuthCheckExecutor(IFormQueryHelper formQueryHelper, IDataAccessServiceProvider dataAccessServiceProvider) {
        this.dataAccessServiceProvider = dataAccessServiceProvider;
        this.accessGroupAndFormKeys = this.accessKeys(formQueryHelper);
    }

    @Override
    public List<FormGroupDefine> checkGroupList(List<FormGroupDefine> formGroups) {
        if (formGroups != null && !formGroups.isEmpty()) {
            return formGroups.stream().filter(e -> this.accessGroupAndFormKeys.contains(e.getKey())).collect(Collectors.toList());
        }
        return new ArrayList<FormGroupDefine>();
    }

    @Override
    public List<FormDefine> checkFormList(List<FormDefine> forms) {
        if (forms != null && !forms.isEmpty()) {
            return forms.stream().filter(e -> this.accessGroupAndFormKeys.contains(e.getKey())).collect(Collectors.toList());
        }
        return new ArrayList<FormDefine>();
    }

    private List<String> accessKeys(IFormQueryHelper formQueryHelper) {
        AccessFormParam accessFormParam = this.getAccessFormParam(formQueryHelper);
        IDataAccessFormService dataAccessFormService = this.dataAccessServiceProvider.getDataAccessFormService();
        DimensionAccessFormInfo dimensionAccessFormInfo = dataAccessFormService.getBatchAccessForms(accessFormParam);
        List accessForms = dimensionAccessFormInfo.getAccessForms();
        ArrayList<String> accessKeys = new ArrayList<String>();
        if (accessForms != null && !accessForms.isEmpty()) {
            accessForms.forEach(accessFormInfo -> accessKeys.addAll(accessFormInfo.getFormKeys()));
        }
        return accessKeys;
    }

    private AccessFormParam getAccessFormParam(IFormQueryHelper formQueryHelper) {
        String formSchemeKey = formQueryHelper.getFormSchemeKey();
        String taskKey = formQueryHelper.getTaskKey();
        DimensionCollection dimensionCollection = DimensionValueSetUtil.buildDimensionCollection(formQueryHelper.getDimValueSet(), (String)formSchemeKey);
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(dimensionCollection);
        accessFormParam.setTaskKey(taskKey);
        accessFormParam.setFormSchemeKey(formSchemeKey);
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_READ);
        accessFormParam.setContainGroup(Boolean.valueOf(true));
        return accessFormParam;
    }
}

