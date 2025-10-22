/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.access.inner.DimResources
 *  com.jiuqi.nr.dataservice.core.access.inner.UnitDimensionMerger
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.access.inner.DimResources;
import com.jiuqi.nr.dataservice.core.access.inner.UnitDimensionMerger;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DataAccessFormServiceImpl
implements IDataAccessFormService {
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private IRunTimeViewController runTimeController;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    private static final Logger logger = LoggerFactory.getLogger(DataAccessFormServiceImpl.class);

    @Override
    public DimensionAccessFormInfo getBatchAccessForms(AccessFormParam param) {
        String taskKey = param.getTaskKey();
        String formSchemeKey = param.getFormSchemeKey();
        DimensionCollection masterKey = param.getCollectionMasterKey();
        boolean containGroup = param.isContainGroup();
        List<String> formKeys = param.getFormKeys();
        AccessLevel.FormAccessLevel formAccessLevel = param.getFormAccessLevel();
        this.checkFormKeys(formKeys, formSchemeKey);
        ArrayList<String> formKeyOrGroup = new ArrayList<String>(formKeys);
        if (containGroup) {
            List<String> groups = this.allGroups(formSchemeKey);
            formKeyOrGroup.addAll(groups);
        }
        DimensionAccessFormInfo dimensionAccessFormInfo = new DimensionAccessFormInfo(formKeys);
        try {
            IBatchAccessResult batchAccessResult;
            IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(taskKey, formSchemeKey, param.getIgnoreAccessItems());
            switch (formAccessLevel) {
                case FORM_READ: {
                    batchAccessResult = dataAccessService.getVisitAccess(masterKey, formKeys);
                    break;
                }
                case FORM_DATA_READ: {
                    batchAccessResult = dataAccessService.getReadAccess(masterKey, formKeys);
                    break;
                }
                case FORM_DATA_WRITE: {
                    batchAccessResult = dataAccessService.getWriteAccess(masterKey, formKeys);
                    break;
                }
                case FORM_DATA_SYSTEM_WRITE: {
                    batchAccessResult = dataAccessService.getSysWriteAccess(masterKey, formKeys);
                    break;
                }
                default: {
                    throw new AccessException("\u4e0d\u652f\u6301\u7684\u7c7b\u578b\uff01");
                }
            }
            List dimensions = masterKey.getDimensionCombinations();
            if (CollectionUtils.isEmpty(dimensions)) {
                return dimensionAccessFormInfo;
            }
            FormSchemeDefine formScheme = this.runTimeController.getFormScheme(formSchemeKey);
            String dimensionName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
            ENameSet eNameSet = ((DimensionCombination)dimensions.get(0)).toDimensionValueSet().getDimensionSet().getDimensions();
            UnitDimensionMerger unitAccessDimensionMerger = new UnitDimensionMerger(formKeyOrGroup, eNameSet, dimensionName);
            for (DimensionCombination dimension : dimensions) {
                DimensionValueSet dimensionValueSet = dimension.toDimensionValueSet();
                Map<String, DimensionValue> dimensonMap = null;
                ArrayList<String> accessFormKeys = new ArrayList<String>();
                for (String formKey : formKeyOrGroup) {
                    IAccessResult access = batchAccessResult.getAccess(dimension, formKey);
                    if (access.haveAccess()) {
                        accessFormKeys.add(formKey);
                        continue;
                    }
                    if (dimensonMap == null) {
                        dimensonMap = DimensionValueSetUtil.getDimensionSet(dimensionValueSet);
                    }
                    DimensionAccessFormInfo.NoAccessFormInfo noAccessFormInfo = new DimensionAccessFormInfo.NoAccessFormInfo(dimensonMap, formKey);
                    noAccessFormInfo.setReason(access.getMessage());
                    dimensionAccessFormInfo.getNoAccessForms().add(noAccessFormInfo);
                }
                unitAccessDimensionMerger.addUnitDimension(dimensionValueSet, accessFormKeys);
            }
            ArrayList<DimensionAccessFormInfo.AccessFormInfo> accessForms = new ArrayList<DimensionAccessFormInfo.AccessFormInfo>();
            List accessMerger = unitAccessDimensionMerger.getDimForms();
            eNameSet.remove(dimensionName);
            for (DimResources dimForm : accessMerger) {
                HashMap<String, DimensionValue> masterKeys = new HashMap<String, DimensionValue>();
                List mergeValues = dimForm.getMergeValues();
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(dimensionName);
                dimensionValue.setValue(String.join((CharSequence)";", mergeValues));
                masterKeys.put(dimensionName, dimensionValue);
                for (int i = 0; i < dimForm.getOtherValues().size(); ++i) {
                    String otherDimensionName = eNameSet.get(i);
                    String otherValue = (String)dimForm.getOtherValues().get(i);
                    DimensionValue otherDimValue = new DimensionValue();
                    otherDimValue.setName(otherDimensionName);
                    otherDimValue.setValue(otherValue);
                    masterKeys.put(otherDimensionName, otherDimValue);
                }
                DimensionAccessFormInfo.AccessFormInfo accessFormInfo = new DimensionAccessFormInfo.AccessFormInfo(masterKeys, dimForm.getForms());
                accessForms.add(accessFormInfo);
            }
            dimensionAccessFormInfo.setAccessForms(accessForms);
        }
        catch (Exception e) {
            throw new AccessException("\u62a5\u8868\u6279\u91cf\u6743\u9650\u5224\u65ad\u5f02\u5e38!", e);
        }
        return dimensionAccessFormInfo;
    }

    private void checkFormKeys(List<String> formKeys, String formSchemeKey) {
        boolean empty = CollectionUtils.isEmpty(formKeys);
        if (empty || formKeys.stream().anyMatch(formKey -> formKey == null || formKey.isEmpty())) {
            formKeys.clear();
            formKeys.addAll(this.getAllFormKeys(formSchemeKey));
        }
    }

    private List<String> allGroups(String formSchemeKey) {
        ArrayList<String> groupKeys = new ArrayList<String>();
        List queryRootGroupsByFormScheme = this.runTimeController.queryRootGroupsByFormScheme(formSchemeKey);
        if (!CollectionUtils.isEmpty(queryRootGroupsByFormScheme)) {
            groupKeys.addAll(queryRootGroupsByFormScheme.stream().map(e -> e.getKey()).collect(Collectors.toList()));
        }
        return groupKeys;
    }

    private List<String> getAllFormKeys(String formSchemeKey) {
        List<String> allformKeys = new ArrayList<String>();
        List queryAllFormDefinesByFormScheme = null;
        try {
            List queryRootGroupsByFormScheme = this.runTimeController.queryRootGroupsByFormScheme(formSchemeKey);
            if (CollectionUtils.isEmpty(queryRootGroupsByFormScheme)) {
                queryAllFormDefinesByFormScheme = this.runTimeController.queryAllFormDefinesByFormScheme(formSchemeKey);
            } else {
                queryAllFormDefinesByFormScheme = new ArrayList();
                for (FormGroupDefine formGroupDefine : queryRootGroupsByFormScheme) {
                    List allFormsInGroup = this.runTimeController.getAllFormsInGroupWithoutOrder(formGroupDefine.getKey());
                    if (allFormsInGroup == null) continue;
                    queryAllFormDefinesByFormScheme.addAll(allFormsInGroup);
                }
            }
            if (null != queryAllFormDefinesByFormScheme) {
                allformKeys = queryAllFormDefinesByFormScheme.stream().map(form -> form.getKey()).distinct().collect(Collectors.toList());
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return allformKeys;
    }
}

