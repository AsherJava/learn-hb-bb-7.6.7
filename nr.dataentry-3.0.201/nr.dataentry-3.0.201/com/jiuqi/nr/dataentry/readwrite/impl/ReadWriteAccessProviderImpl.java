/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.common.BatchSummaryConst
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.dataentry.paramInfo.FieldReadWriteAccessResult;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.readwrite.IFieldReadWriteAccessResult;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.common.BatchSummaryConst;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ReadWriteAccessProviderImpl
implements ReadWriteAccessProvider {
    private static final Logger logger = LoggerFactory.getLogger(ReadWriteAccessProviderImpl.class);
    @Autowired
    private IRunTimeViewController runtimeView;

    @Override
    public FormReadWriteAccessData getAccessForms(FormReadWriteAccessData inputData, ReadWriteAccessCacheManager accessCacheManager) {
        return this.getAccessForms(inputData, accessCacheManager, false);
    }

    @Override
    public FormReadWriteAccessData getAccessForms(FormReadWriteAccessData inputData, ReadWriteAccessCacheManager accessCacheManager, boolean haveGroup) {
        if (null == accessCacheManager) {
            throw new RuntimeException("\u8bf7\u5148\u6784\u5efa\u6743\u9650\u7f13\u5b58\uff01");
        }
        JtableContext jtableContext = inputData.getJtableContext();
        FormReadWriteAccessData formReadWriteAccessDataRes = new FormReadWriteAccessData(jtableContext, inputData.getFormAccessLevel());
        ArrayList<String> tempFormKeys = new ArrayList<String>();
        tempFormKeys.addAll(inputData.getFormKeys());
        formReadWriteAccessDataRes.setFormKeys(tempFormKeys);
        Consts.FormAccessLevel formAccessLevel = inputData.getFormAccessLevel();
        if (null == formAccessLevel || 0 == formReadWriteAccessDataRes.getFormKeys().size()) {
            try {
                List<String> allSeeformKeys = accessCacheManager.getAllFormkeys();
                formReadWriteAccessDataRes.setFormKeys(allSeeformKeys);
                if (null == formAccessLevel) {
                    return formReadWriteAccessDataRes;
                }
            }
            catch (Exception e) {
                throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey()});
            }
        }
        List<String> formKeysByUser = formReadWriteAccessDataRes.getFormKeys();
        if (haveGroup) {
            formKeysByUser.addAll(accessCacheManager.getAllGroupKeys());
            formReadWriteAccessDataRes.setFormKeys(formKeysByUser);
        }
        Map dimensionSet = jtableContext.getDimensionSet();
        dimensionSet.remove("VERSIONID");
        dimensionSet.remove("DATASNAPSHOTID");
        List<EntityViewData> entityList = accessCacheManager.getEntityList();
        boolean adjustDimension = ReadWriteAccessCacheManager.checkAdjustDimension(dimensionSet, entityList);
        DimensionValueSet currDimensionValue = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
        IBatchAccessResult batchResult = accessCacheManager.getBatchResult();
        if (null == batchResult) {
            throw new RuntimeException("\u6279\u91cf\u6743\u9650\u7f13\u5b58\u672a\u521d\u59cb\u5316\uff01");
        }
        ArrayList<String> formKeys = new ArrayList<String>(formKeysByUser);
        DimensionCombination collection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCombination((DimensionValueSet)currDimensionValue, (String)jtableContext.getFormSchemeKey());
        for (String formKey : formKeys) {
            IAccessResult access = batchResult.getAccess(collection, formKey);
            try {
                if (access.haveAccess() || BatchSummaryConst.isBatchSummaryEntry((Map)jtableContext.getVariableMap())) continue;
                formReadWriteAccessDataRes.setOneFormKeyNoAccess(formKey, access.getMessage());
            }
            catch (Exception e) {
                logger.error("\u62a5\u8868\u6743\u9650\u5224\u65ad\u5f02\u5e38!", e);
            }
        }
        return formReadWriteAccessDataRes;
    }

    @Override
    public List<String> getAllFormKeys(String formSchemeKey) {
        ArrayList<String> allformKeys = new ArrayList<String>();
        ArrayList queryAllFormDefinesByFormScheme = null;
        try {
            List queryRootGroupsByFormScheme = this.runtimeView.queryRootGroupsByFormScheme(formSchemeKey);
            if (null == queryRootGroupsByFormScheme || queryRootGroupsByFormScheme.isEmpty()) {
                queryAllFormDefinesByFormScheme = this.runtimeView.queryAllFormDefinesByFormScheme(formSchemeKey);
            } else {
                queryAllFormDefinesByFormScheme = new ArrayList();
                for (FormGroupDefine formGroupDefine : queryRootGroupsByFormScheme) {
                    List allFormsInGroup = this.runtimeView.getAllFormsInGroupWithoutOrder(formGroupDefine.getKey());
                    if (allFormsInGroup == null) continue;
                    queryAllFormDefinesByFormScheme.addAll(allFormsInGroup);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (null != queryAllFormDefinesByFormScheme) {
            for (FormDefine formDefine : queryAllFormDefinesByFormScheme) {
                if (allformKeys.contains(formDefine.getKey())) continue;
                allformKeys.add(formDefine.getKey());
            }
        }
        return allformKeys;
    }

    @Override
    public IFieldReadWriteAccessResult getZBWriteAccess(List<DimensionValueSet> dimensionValueSetList, List<String> filedKeys, String dwKey) {
        HashMap<String, Set<String>> filedFormKeysMap = new HashMap<String, Set<String>>();
        HashMap formSchemeFormKeysMap = new HashMap();
        HashMap<Map, HashSet<String>> dimUnAccessKeysMap = new HashMap<Map, HashSet<String>>();
        HashMap<Map, HashMap<List<String>, FormReadWriteAccessData>> dimFormSchemeAccessMap = new HashMap<Map, HashMap<List<String>, FormReadWriteAccessData>>();
        HashMap<DimensionValueSet, Map<String, String>> dimNoAccessFromReasonsMap = new HashMap<DimensionValueSet, Map<String, String>>();
        for (String filedKey : filedKeys) {
            Collection formKeys = this.runtimeView.getFormKeysByField(filedKey);
            if (!CollectionUtils.isNotEmpty(formKeys)) continue;
            for (String formKey : formKeys) {
                HashSet<String> filedFormKeys;
                List formGroups = this.runtimeView.getFormGroupsByFormKey(formKey);
                if (formGroups.size() > 0) {
                    for (FormGroupDefine group : formGroups) {
                        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(group.getFormSchemeKey());
                        if (!dwKey.equals(formScheme.getDw())) continue;
                        ArrayList<String> formSchemeFormKeys = (ArrayList<String>)formSchemeFormKeysMap.get(group.getFormSchemeKey());
                        if (null == formSchemeFormKeys) {
                            formSchemeFormKeys = new ArrayList<String>();
                            formSchemeFormKeysMap.put(group.getFormSchemeKey(), formSchemeFormKeys);
                        }
                        if (formSchemeFormKeys.contains(formKey)) continue;
                        formSchemeFormKeys.add(formKey);
                    }
                }
                if (null == (filedFormKeys = (HashSet<String>)filedFormKeysMap.get(filedKey))) {
                    filedFormKeys = new HashSet<String>();
                    filedFormKeysMap.put(filedKey, filedFormKeys);
                }
                filedFormKeys.add(formKey);
            }
        }
        for (DimensionValueSet dimensionValueSet : dimensionValueSetList) {
            Map dimensionSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
            for (String formSchemeKey : formSchemeFormKeysMap.keySet()) {
                FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
                JtableContext jtableContext = new JtableContext();
                jtableContext.setDimensionSet(dimensionSet);
                jtableContext.setFormSchemeKey(formSchemeKey);
                jtableContext.setTaskKey(formScheme.getTaskKey());
                List formKeys = (List)formSchemeFormKeysMap.get(formSchemeKey);
                ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(jtableContext, formKeys, Consts.FormAccessLevel.FORM_DATA_WRITE);
                ReadWriteAccessCacheManager readWriteAccessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);
                readWriteAccessCacheManager.initCache(readWriteAccessCacheParams);
                List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((Map)dimensionSet);
                for (Map valueSet : dimensionSetList) {
                    JtableContext accessContext = new JtableContext(jtableContext);
                    accessContext.setDimensionSet(valueSet);
                    FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(accessContext, Consts.FormAccessLevel.FORM_DATA_WRITE, formKeys);
                    FormReadWriteAccessData accessForms = this.getAccessForms(formReadWriteAccessData, readWriteAccessCacheManager);
                    List<String> noAccessnFormKeys = accessForms.getNoAccessnFormKeys();
                    HashSet<String> unAccessKeys = (HashSet<String>)dimUnAccessKeysMap.get(valueSet);
                    if (null == unAccessKeys) {
                        unAccessKeys = new HashSet<String>();
                        dimUnAccessKeysMap.put(valueSet, unAccessKeys);
                    }
                    unAccessKeys.addAll(noAccessnFormKeys);
                    HashMap<List<String>, FormReadWriteAccessData> accessDataMap = (HashMap<List<String>, FormReadWriteAccessData>)dimFormSchemeAccessMap.get(valueSet);
                    if (null == accessDataMap) {
                        accessDataMap = new HashMap<List<String>, FormReadWriteAccessData>();
                        dimFormSchemeAccessMap.put(valueSet, accessDataMap);
                    }
                    accessDataMap.put(noAccessnFormKeys, accessForms);
                }
            }
        }
        for (Map dimensionValueMap : dimUnAccessKeysMap.keySet()) {
            HashMap<String, String> formReasonsMap = new HashMap<String, String>();
            Set unAccessFroms = (Set)dimUnAccessKeysMap.get(dimensionValueMap);
            Map accessDataMap = (Map)dimFormSchemeAccessMap.get(dimensionValueMap);
            for (List forms : accessDataMap.keySet()) {
                HashSet unAccessFromKeys = new HashSet();
                unAccessFromKeys.addAll(forms);
                unAccessFromKeys.retainAll(unAccessFroms);
                for (String unAccessFrom : unAccessFromKeys) {
                    if (formReasonsMap.containsKey(unAccessFrom)) continue;
                    FormReadWriteAccessData formReadWriteAccessData = (FormReadWriteAccessData)accessDataMap.get(forms);
                    String reason = formReadWriteAccessData.getOneFormKeyReason(unAccessFrom);
                    formReasonsMap.put(unAccessFrom, reason);
                }
            }
            DimensionValueSet dimensionValue = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionValueMap);
            dimNoAccessFromReasonsMap.put(dimensionValue, formReasonsMap);
        }
        FieldReadWriteAccessResult fieldReadWriteAccessResult = new FieldReadWriteAccessResult(dimNoAccessFromReasonsMap, filedFormKeysMap);
        return fieldReadWriteAccessResult;
    }

    private void clearBatchDimension(Map<String, DimensionValue> batchDimension) {
        for (Map.Entry<String, DimensionValue> entry : batchDimension.entrySet()) {
            DimensionValue dimensionValue = entry.getValue();
            String value = dimensionValue.getValue();
            if (!StringUtils.hasLength(value) || !value.contains(";")) continue;
            value = value.substring(0, value.indexOf(";"));
            dimensionValue.setValue(value);
        }
    }
}

