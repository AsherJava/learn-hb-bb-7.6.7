/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.internal.controller.DesignTimeViewController
 *  com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineData
 *  com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignDataRegionDefineImpl
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.designer.formcopy.service;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.internal.controller.DesignTimeViewController;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineData;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDataRegionDefineImpl;
import com.jiuqi.nr.designer.formcopy.common.DesignDataRegion;
import com.jiuqi.nr.designer.formcopy.common.FormCopyContext;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import com.jiuqi.util.OrderGenerator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DesignFormCopyHelper {
    @Autowired
    private DesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;

    public DesignFormGroupDefine copyFormGroup(DesignFormGroupDefine srcFormGroup) {
        DesignFormGroupDefine newFormGroup = this.designTimeViewController.createFormGroup();
        BeanUtils.copyProperties(srcFormGroup, newFormGroup);
        newFormGroup.setKey(UUIDUtils.getKey());
        newFormGroup.setOrder(OrderGenerator.newOrder());
        return newFormGroup;
    }

    public DesignFormGroupDefine copyFormGroupTo(DesignFormGroupDefine srcFormGroup, String formSchemeKey) {
        DesignFormGroupDefine newFormGroup = this.copyFormGroup(srcFormGroup);
        newFormGroup.setFormSchemeKey(formSchemeKey);
        return newFormGroup;
    }

    public DesignFormGroupDefine copyFormGroupTo(String srcFormGroupKey, String formSchemeKey) {
        DesignFormGroupDefine srcFormGroup = this.designTimeViewController.queryFormGroup(srcFormGroupKey);
        return this.copyFormGroupTo(srcFormGroup, formSchemeKey);
    }

    public DesignFormDefine copyForm(DesignFormDefine srcForm) {
        DesignFormDefine newForm = this.designTimeViewController.createFormDefine();
        BeanUtils.copyProperties(srcForm, newForm);
        if (srcForm.getFormType() == FormType.FORM_TYPE_INSERTANALYSIS && null != srcForm.getFormExtension()) {
            Map formExtension = srcForm.getFormExtension();
            for (String anaKey : formExtension.keySet()) {
                newForm.addExtensions(anaKey, formExtension.get(anaKey));
            }
        }
        newForm.setKey(UUIDUtils.getKey());
        if (null == newForm.getUpdateTime()) {
            newForm.setUpdateTime(new Date());
        }
        newForm.setOrder(OrderGenerator.newOrder());
        return newForm;
    }

    public DesignFormDefine copyFormTo(DesignFormDefine srcForm, String formSchemeKey) {
        DesignFormDefine newForm = this.copyForm(srcForm);
        newForm.setFormScheme(formSchemeKey);
        return newForm;
    }

    public DesignFormDefine copyFormTo(String srcFormKey, String formSchemeKey) {
        DesignFormDefine srcForm = this.designTimeViewController.queryFormById(srcFormKey);
        return this.copyFormTo(srcForm, formSchemeKey);
    }

    public DesignDataRegion copyDataRegionTo(DesignDataRegionDefine srcRegion, DesignRegionSettingDefine srcRegionSetting, String fromKey) {
        DesignDataRegionDefine newDataRegion;
        if (srcRegion instanceof DesignDataRegionDefineImpl) {
            newDataRegion = new DesignDataRegionDefineImpl((DesignDataRegionDefineImpl)srcRegion);
        } else {
            newDataRegion = this.designTimeViewController.createDataRegionDefine();
            BeanUtils.copyProperties(srcRegion, newDataRegion);
        }
        newDataRegion.setKey(UUIDUtils.getKey());
        newDataRegion.setFormKey(fromKey);
        newDataRegion.setCardInputFormKey(fromKey);
        DesignRegionSettingDefine regionSetting = null;
        if (null != srcRegionSetting) {
            regionSetting = this.designTimeViewController.createRegionSetting();
            BeanUtils.copyProperties(srcRegionSetting, regionSetting);
            regionSetting.setKey(UUIDUtils.getKey());
            newDataRegion.setRegionSettingKey(regionSetting.getKey());
        }
        return new DesignDataRegion(newDataRegion, regionSetting);
    }

    public DesignDataLinkDefine copyDataLinkTo(DesignDataLinkDefine srcLink, String regionKey) {
        DesignDataLinkDefine newLink;
        if (srcLink instanceof DesignDataLinkDefineData) {
            srcLink = ((DesignDataLinkDefineData)srcLink).getDesignDataLinkDefine();
            return this.copyDataLinkTo(srcLink, regionKey);
        }
        if (srcLink instanceof DesignDataLinkDefineImpl) {
            DesignDataLinkDefineImpl srcLinkImpl = (DesignDataLinkDefineImpl)srcLink;
            newLink = new DesignDataLinkDefineImpl(srcLinkImpl);
        } else {
            newLink = this.designTimeViewController.createDataLinkDefine();
            BeanUtils.copyProperties(srcLink, newLink);
        }
        newLink.setKey(UUIDUtils.getKey());
        newLink.setRegionKey(regionKey);
        newLink.setUniqueCode(OrderGenerator.newOrder());
        return newLink;
    }

    public DesignDataTable copyDataTableTo(DesignDataTable srcDataTable, String dataSchemeKey, String dataGroupKey, String srcCodePrefix, String codePrefix) {
        DesignDataTable newDataTable = this.iDesignDataSchemeService.initDataTable();
        BeanUtils.copyProperties(srcDataTable, newDataTable);
        newDataTable.setKey(UUIDUtils.getKey());
        newDataTable.setDataSchemeKey(dataSchemeKey);
        newDataTable.setDataGroupKey(dataGroupKey);
        newDataTable.setBizKeys(null);
        String code = srcDataTable.getCode();
        code = code.replaceFirst(srcCodePrefix, "");
        newDataTable.setCode(codePrefix + code);
        newDataTable.setCode(DesignFormCopyHelper.getCode(newDataTable.getCode(), c -> null == this.iDesignDataSchemeService.getDataTableByCode(c)));
        return newDataTable;
    }

    public static String getCode(String code, Predicate<String> codeChecker) {
        String newcode = code;
        int i = 0;
        while (!codeChecker.test(newcode)) {
            newcode = code + "_" + ++i;
        }
        return newcode;
    }

    public DesignDataField copyDataFieldTo(DesignDataField srcDataField, String dataSchemeKey, String dataTableKey, Consumer<DesignDataField> codeSetter) {
        DesignDataField newDataField = this.iDesignDataSchemeService.initDataField();
        BeanUtils.copyProperties(srcDataField, newDataField);
        newDataField.setKey(UUIDUtils.getKey());
        newDataField.setDataSchemeKey(dataSchemeKey);
        newDataField.setDataTableKey(dataTableKey);
        codeSetter.accept(newDataField);
        return newDataField;
    }

    public Map<DesignDataField, DesignDataField> copyAllDataFieldTo(FormCopyContext context, String srcDataTableKey, String dataSchemeKey, String dataTableKey, boolean setCopiedFiledKey, Consumer<DesignDataField> codeSetter) {
        List srcDataFields = this.iDesignDataSchemeService.getDataFieldByTable(srcDataTableKey);
        HashMap<DesignDataField, DesignDataField> result = new HashMap<DesignDataField, DesignDataField>();
        for (DesignDataField designDataField : srcDataFields) {
            if (DataFieldKind.PUBLIC_FIELD_DIM == designDataField.getDataFieldKind() || DataFieldKind.BUILT_IN_FIELD == designDataField.getDataFieldKind()) continue;
            result.put(designDataField, this.copyDataFieldTo(designDataField, dataSchemeKey, dataTableKey, codeSetter));
        }
        if (setCopiedFiledKey) {
            for (Map.Entry entry : result.entrySet()) {
                String newDataFieldKey = context.getCopiedDataFieldInfo(((DesignDataField)entry.getKey()).getKey());
                DesignDataField newDataField = (DesignDataField)entry.getValue();
                if (StringUtils.hasLength(newDataFieldKey)) {
                    newDataField.setKey(newDataFieldKey);
                }
                context.addDataFieldInfo((DesignDataField)entry.getKey(), newDataField);
            }
        } else {
            context.addDataFieldInfos(result);
        }
        return result;
    }

    public DesignDataTable copySameCodeZB(FormCopyContext context, DesignDataField srcDataField, Consumer<DesignDataField> codeSetter) {
        DesignDataField sameCodeDataField = context.getDataFieldByCode(srcDataField.getCode());
        context.addCopiedDataFieldInfo(srcDataField.getKey(), sameCodeDataField.getKey());
        this.syncDataFieldTo(srcDataField, sameCodeDataField, sameCodeDataField.getDataTableKey(), codeSetter);
        context.addDataFieldCopyRecordInfo(srcDataField, sameCodeDataField);
        context.addUpdateDataField(sameCodeDataField, null);
        DesignDataTable sameCodeDataFieldTable = context.getDataTable(sameCodeDataField.getDataTableKey());
        if (sameCodeDataFieldTable != null && sameCodeDataFieldTable.getDataTableType() == DataTableType.TABLE) {
            return sameCodeDataFieldTable;
        }
        return null;
    }

    public boolean hasSameZBCode(FormCopyContext context, DesignDataField srcDataField) {
        Set<String> zbFieldCodeSet = context.getZbFieldCodeSet();
        return zbFieldCodeSet.contains(srcDataField.getCode());
    }

    public void syncDataTableTo(DesignDataTable srcDataTable, DesignDataTable dataTable, String srcCodePrefix, String codePrefix) {
        String oldKey = dataTable.getKey();
        String oldDataSchemeKey = dataTable.getDataSchemeKey();
        String oldDataGroupKey = dataTable.getDataGroupKey();
        String oldCode = dataTable.getCode();
        String code = srcDataTable.getCode();
        code = code.replaceFirst(srcCodePrefix, "");
        code = codePrefix + code;
        BeanUtils.copyProperties(srcDataTable, dataTable);
        dataTable.setKey(oldKey);
        dataTable.setDataSchemeKey(oldDataSchemeKey);
        dataTable.setDataGroupKey(oldDataGroupKey);
        dataTable.setCode(code);
        if (!oldCode.equals(code)) {
            dataTable.setCode(DesignFormCopyHelper.getCode(dataTable.getCode(), c -> {
                DesignDataTable dataTableByCode = this.iDesignDataSchemeService.getDataTableByCode(c);
                return null == dataTableByCode || dataTableByCode.getKey().equals(dataTable.getKey());
            }));
        }
        dataTable.setBizKeys(null);
    }

    public String syncDataFieldTo(DesignDataField srcDataField, DesignDataField dataField, String dataTableKey, Consumer<DesignDataField> codeSetter) {
        String oldKey = dataField.getKey();
        String oldCode = dataField.getCode();
        String oldDataSchemeKey = dataField.getDataSchemeKey();
        BeanUtils.copyProperties(srcDataField, dataField);
        dataField.setKey(oldKey);
        dataField.setDataSchemeKey(oldDataSchemeKey);
        dataField.setDataTableKey(dataTableKey);
        if (!oldCode.equals(dataField.getCode())) {
            codeSetter.accept(dataField);
            return oldCode;
        }
        return null;
    }

    public DesignFormDefine syncForm(String srcFormKey, String formKey) {
        DesignFormDefine srcForm = this.designTimeViewController.queryFormById(srcFormKey);
        DesignFormDefine form = this.designTimeViewController.queryFormById(formKey);
        this.syncForm(srcForm, form);
        return form;
    }

    public DesignFormDefine syncForm(DesignFormDefine srcForm, DesignFormDefine form) {
        DesignFormDefine copyForm = this.copyForm(srcForm);
        copyForm.setKey(form.getKey());
        copyForm.setTitle(form.getTitle());
        copyForm.setFormCode(form.getFormCode());
        copyForm.setFormScheme(form.getFormScheme());
        return copyForm;
    }
}

