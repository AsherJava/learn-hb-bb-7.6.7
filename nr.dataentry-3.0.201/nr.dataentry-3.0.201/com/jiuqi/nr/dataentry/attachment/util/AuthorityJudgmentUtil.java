/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.nr.dataentry.attachment.util;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.attachment.message.JurisdictionResult;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.paramInfo.NoAccessFormInfo;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuthorityJudgmentUtil {
    private ReadWriteAccessCacheManager visibleAccessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);
    private ReadWriteAccessCacheManager readableAccessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);
    private ReadWriteAccessCacheManager writeableAccessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);

    public AuthorityJudgmentUtil(JtableContext jtableContext, List<FormDefine> formDefineList, List<Consts.FormAccessLevel> formAccessLevels) {
        ArrayList<String> formKeys = new ArrayList<String>();
        for (FormDefine formDefine : formDefineList) {
            formKeys.add(formDefine.getKey());
        }
        if (!formKeys.isEmpty()) {
            for (Consts.FormAccessLevel formAccessLevel : formAccessLevels) {
                ReadWriteAccessCacheParams readWriteAccessCacheParams;
                if (formAccessLevel.equals((Object)Consts.FormAccessLevel.FORM_READ)) {
                    readWriteAccessCacheParams = new ReadWriteAccessCacheParams(jtableContext, formKeys, Consts.FormAccessLevel.FORM_READ);
                    this.visibleAccessCacheManager.initCache(readWriteAccessCacheParams);
                    continue;
                }
                if (formAccessLevel.equals((Object)Consts.FormAccessLevel.FORM_DATA_READ)) {
                    readWriteAccessCacheParams = new ReadWriteAccessCacheParams(jtableContext, formKeys, Consts.FormAccessLevel.FORM_DATA_READ);
                    this.readableAccessCacheManager.initCache(readWriteAccessCacheParams);
                    continue;
                }
                if (!formAccessLevel.equals((Object)Consts.FormAccessLevel.FORM_DATA_WRITE)) continue;
                readWriteAccessCacheParams = new ReadWriteAccessCacheParams(jtableContext, formKeys, Consts.FormAccessLevel.FORM_DATA_WRITE);
                this.writeableAccessCacheManager.initCache(readWriteAccessCacheParams);
            }
        }
    }

    public JurisdictionResult visible(String formKey, JtableContext jtableContext, IJtableParamService jtableParamService, DimensionValueProvider dimensionValueProvider, ReadWriteAccessProvider accessProvider) {
        JurisdictionResult result = new JurisdictionResult();
        ArrayList<String> formKeys = new ArrayList<String>();
        formKeys.add(formKey);
        Map batchDimensionSet = jtableContext.getDimensionSet();
        ArrayList<String> errorDimensionList = new ArrayList<String>();
        JtableContext jtableContextInfo = new JtableContext();
        jtableContextInfo.setDimensionSet(batchDimensionSet);
        jtableContextInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
        jtableContextInfo.setTaskKey(jtableContext.getTaskKey());
        List<Map<String, DimensionValue>> splitDimensionValueList = dimensionValueProvider.splitDimensionValueList(jtableContextInfo, errorDimensionList, true);
        for (Map<String, DimensionValue> dimensionSet : splitDimensionValueList) {
            JtableContext formAccessContext = new JtableContext(jtableContext);
            formAccessContext.setDimensionSet(dimensionSet);
            FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(formAccessContext, Consts.FormAccessLevel.FORM_READ, formKeys);
            FormReadWriteAccessData accessForms = accessProvider.getAccessForms(formReadWriteAccessData, this.visibleAccessCacheManager);
            if (accessForms.getFormKeys() != null && !accessForms.getFormKeys().isEmpty()) {
                result.setHaveAccess(true);
            } else {
                result.setHaveAccess(false);
            }
            List<String> noAccessnFormKeys = accessForms.getNoAccessnFormKeys();
            for (String noAccessnFormKey : noAccessnFormKeys) {
                NoAccessFormInfo noAccessFormInfo = new NoAccessFormInfo(dimensionSet, noAccessnFormKey, accessForms.getOneFormKeyReason(noAccessnFormKey));
                result.setMessage(noAccessFormInfo.getReason());
            }
        }
        return result;
    }

    public FormReadWriteAccessData batchVisible(List<String> formKeys, JtableContext jtableContext, ReadWriteAccessProvider accessProvider) {
        FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(jtableContext, Consts.FormAccessLevel.FORM_READ, formKeys);
        FormReadWriteAccessData accessForms = accessProvider.getAccessForms(formReadWriteAccessData, this.visibleAccessCacheManager);
        return accessForms;
    }

    public JurisdictionResult readable(String formKey, JtableContext jtableContext, IJtableParamService jtableParamService, DimensionValueProvider dimensionValueProvider, ReadWriteAccessProvider accessProvider) {
        JurisdictionResult result = new JurisdictionResult();
        ArrayList<String> formKeys = new ArrayList<String>();
        formKeys.add(formKey);
        Map batchDimensionSet = jtableContext.getDimensionSet();
        ArrayList<String> errorDimensionList = new ArrayList<String>();
        JtableContext jtableContextInfo = new JtableContext();
        jtableContextInfo.setDimensionSet(batchDimensionSet);
        jtableContextInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
        jtableContextInfo.setTaskKey(jtableContext.getTaskKey());
        List<Map<String, DimensionValue>> splitDimensionValueList = dimensionValueProvider.splitDimensionValueList(jtableContextInfo, errorDimensionList, true);
        for (Map<String, DimensionValue> dimensionSet : splitDimensionValueList) {
            JtableContext formAccessContext = new JtableContext(jtableContext);
            formAccessContext.setDimensionSet(dimensionSet);
            FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(formAccessContext, Consts.FormAccessLevel.FORM_DATA_READ, formKeys);
            FormReadWriteAccessData accessForms = accessProvider.getAccessForms(formReadWriteAccessData, this.readableAccessCacheManager);
            if (accessForms.getFormKeys() != null && !accessForms.getFormKeys().isEmpty()) {
                result.setHaveAccess(true);
            } else {
                result.setHaveAccess(false);
            }
            List<String> noAccessnFormKeys = accessForms.getNoAccessnFormKeys();
            for (String noAccessnFormKey : noAccessnFormKeys) {
                NoAccessFormInfo noAccessFormInfo = new NoAccessFormInfo(dimensionSet, noAccessnFormKey, accessForms.getOneFormKeyReason(noAccessnFormKey));
                result.setMessage(noAccessFormInfo.getReason());
            }
        }
        return result;
    }

    public JurisdictionResult writeable(String formKey, JtableContext jtableContext, IJtableParamService jtableParamService, DimensionValueProvider dimensionValueProvider, ReadWriteAccessProvider accessProvider) {
        JurisdictionResult result = new JurisdictionResult();
        ArrayList<String> formKeys = new ArrayList<String>();
        formKeys.add(formKey);
        Map batchDimensionSet = jtableContext.getDimensionSet();
        ArrayList<String> errorDimensionList = new ArrayList<String>();
        JtableContext jtableContextInfo = new JtableContext();
        jtableContextInfo.setDimensionSet(batchDimensionSet);
        jtableContextInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
        jtableContextInfo.setTaskKey(jtableContext.getTaskKey());
        List<Map<String, DimensionValue>> splitDimensionValueList = dimensionValueProvider.splitDimensionValueList(jtableContextInfo, errorDimensionList, true);
        for (Map<String, DimensionValue> dimensionSet : splitDimensionValueList) {
            JtableContext formAccessContext = new JtableContext(jtableContext);
            formAccessContext.setDimensionSet(dimensionSet);
            FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(formAccessContext, Consts.FormAccessLevel.FORM_DATA_WRITE, formKeys);
            FormReadWriteAccessData accessForms = accessProvider.getAccessForms(formReadWriteAccessData, this.writeableAccessCacheManager);
            if (accessForms.getFormKeys() != null && !accessForms.getFormKeys().isEmpty()) {
                result.setHaveAccess(true);
            } else {
                result.setHaveAccess(false);
            }
            List<String> noAccessnFormKeys = accessForms.getNoAccessnFormKeys();
            for (String noAccessnFormKey : noAccessnFormKeys) {
                NoAccessFormInfo noAccessFormInfo = new NoAccessFormInfo(dimensionSet, noAccessnFormKey, accessForms.getOneFormKeyReason(noAccessnFormKey));
                result.setMessage(noAccessFormInfo.getReason());
            }
        }
        return result;
    }

    public JurisdictionResult zbWriteable(String formKey, JtableContext jtableContext, IJtableParamService jtableParamService, DimensionValueProvider dimensionValueProvider, ReadWriteAccessProvider accessProvider) {
        return this.writeable(formKey, jtableContext, jtableParamService, dimensionValueProvider, accessProvider);
    }
}

