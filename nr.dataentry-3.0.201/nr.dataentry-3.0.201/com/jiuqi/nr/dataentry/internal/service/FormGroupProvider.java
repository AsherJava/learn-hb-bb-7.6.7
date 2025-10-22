/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.service.SingleFormRejectService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$NoAccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.MeasureViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.service.SingleFormRejectService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentry.paramInfo.BatchDimensionValueFormInfo;
import com.jiuqi.nr.dataentry.paramInfo.DimensionValueFormInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.paramInfo.NoAccessFormInfo;
import com.jiuqi.nr.dataentry.paramInfo.NoAccessUtilInfo;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.readwrite.impl.WorkFlowReadWriteAccessImpl;
import com.jiuqi.nr.dataentry.service.IDataPublishService;
import com.jiuqi.nr.dataentry.service.IFormLockService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.MeasureViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormGroupProvider {
    private static final Logger logger = LoggerFactory.getLogger(FormGroupProvider.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private DimensionValueProvider dimensionValueProvider;
    @Autowired
    private ReadWriteAccessProvider accessProvider;
    @Autowired
    private IFormLockService formLockService;
    @Autowired
    private IDataPublishService dataPublishService;
    @Autowired
    private SingleFormRejectService singleFormRejectService;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private WorkFlowReadWriteAccessImpl workFlowReadWriteAccess;
    @Autowired
    private IDataAccessFormService dataAccessFormService;
    @Autowired
    private ObjectProvider<ReadWriteAccessCacheManager> readWriteAccessCacheManagerProvider;

    public BatchDimensionValueFormInfo getForms(JtableContext jtableContext, String formKeyStr, Consts.FormAccessLevel level) {
        return this.getForms(jtableContext, formKeyStr, level, false);
    }

    @Deprecated
    public BatchDimensionValueFormInfo getForms(JtableContext jtableContext, String formKeyStr, Consts.FormAccessLevel level, Set<String> ignoreItems) {
        ArrayList<String> formkeyList = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)formKeyStr)) {
            String[] formKeys;
            for (String formKey : formKeys = formKeyStr.split(";")) {
                formkeyList.add(formKey);
            }
        }
        BatchDimensionValueFormInfo batchDimensionValueFormInfo = new BatchDimensionValueFormInfo();
        batchDimensionValueFormInfo.setJtableContext(new JtableContext(jtableContext));
        batchDimensionValueFormInfo.setForms(formkeyList);
        batchDimensionValueFormInfo.setFormAccessLevel(level);
        this.getForms(batchDimensionValueFormInfo, ignoreItems);
        return batchDimensionValueFormInfo;
    }

    public BatchDimensionValueFormInfo getForms(JtableContext jtableContext, String formKeyStr, Consts.FormAccessLevel level, boolean ignoreWorkFlow) {
        ArrayList<String> formkeyList = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)formKeyStr)) {
            String[] formKeys;
            for (String formKey : formKeys = formKeyStr.split(";")) {
                formkeyList.add(formKey);
            }
        }
        BatchDimensionValueFormInfo batchDimensionValueFormInfo = new BatchDimensionValueFormInfo();
        batchDimensionValueFormInfo.setJtableContext(new JtableContext(jtableContext));
        batchDimensionValueFormInfo.setForms(formkeyList);
        batchDimensionValueFormInfo.setFormAccessLevel(level);
        HashSet<String> ignoreItems = new HashSet<String>();
        if (ignoreWorkFlow) {
            ignoreItems.add(this.workFlowReadWriteAccess.getName());
        }
        this.getForms(batchDimensionValueFormInfo, ignoreItems);
        return batchDimensionValueFormInfo;
    }

    private void getForms(BatchDimensionValueFormInfo formInfo, Set<String> ignoreItems) {
        JtableContext jtableContext = formInfo.getJtableContext();
        Map batchDimensionSet = jtableContext.getDimensionSet();
        List entityList = this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
        ArrayList errorDimensionList = new ArrayList();
        DimensionCollection dimCollection = DimensionValueSetUtil.buildDimensionCollection((Map)jtableContext.getDimensionSet(), (String)jtableContext.getFormSchemeKey());
        List dimCollectionList = dimCollection.getDimensionCombinations();
        ReadWriteAccessCacheManager readWriteAccessCacheManager = this.readWriteAccessCacheManagerProvider.getObject();
        ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(jtableContext, formInfo.getForms(), formInfo.getFormAccessLevel());
        readWriteAccessCacheParams.setIgnoreItems(ignoreItems);
        readWriteAccessCacheManager.initCache(readWriteAccessCacheParams);
        List<DimensionValueFormInfo> accessFormInfos = formInfo.getAccessFormInfos();
        List<NoAccessFormInfo> noAccessReasons = formInfo.getNoAccessReasons();
        List<NoAccessUtilInfo> noAccessUtilReasons = formInfo.getNoAccessUtilReasons();
        for (DimensionCombination dimensionComin : dimCollectionList) {
            Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionComin.toDimensionValueSet());
            JtableContext formAccessContext = new JtableContext(jtableContext);
            formAccessContext.setDimensionSet(dimensionSet);
            FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(formAccessContext, formInfo.getFormAccessLevel(), formInfo.getForms());
            FormReadWriteAccessData accessForms = this.accessProvider.getAccessForms(formReadWriteAccessData, readWriteAccessCacheManager);
            if (accessForms.getFormKeys() != null && !accessForms.getFormKeys().isEmpty()) {
                DimensionValueFormInfo dimensionValueFormInfo = new DimensionValueFormInfo(dimensionSet, accessForms.getFormKeys());
                accessFormInfos.add(dimensionValueFormInfo);
            }
            List<String> noAccessUnitKey = accessForms.getNoAccessUnitKey();
            for (String unitKey : noAccessUnitKey) {
                NoAccessUtilInfo noAccessUtilInfo = new NoAccessUtilInfo(dimensionSet, unitKey, accessForms.getNoAccessUnitKeyReason(unitKey));
                noAccessUtilReasons.add(noAccessUtilInfo);
            }
            List<String> noAccessnFormKeys = accessForms.getNoAccessnFormKeys();
            for (String formKey : noAccessnFormKeys) {
                NoAccessFormInfo noAccessFormInfo = new NoAccessFormInfo(dimensionSet, formKey, accessForms.getOneFormKeyReason(formKey));
                noAccessReasons.add(noAccessFormInfo);
            }
        }
        List<DimensionValueFormInfo> mergeDimensionValueList = this.dimensionValueProvider.mergeDimensionValueList(accessFormInfos, entityList);
        formInfo.setAccessFormInfos(mergeDimensionValueList);
    }

    public List<FormGroupData> getFormGroupList(JtableContext jtableContext, boolean writeAuth) {
        ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(jtableContext, null, writeAuth ? Consts.FormAccessLevel.FORM_DATA_WRITE : Consts.FormAccessLevel.FORM_READ);
        boolean hasDataSnapshot = false;
        DimensionValue dimensionValue = new DimensionValue();
        if (jtableContext.getDimensionSet().containsKey("DATASNAPSHOTID")) {
            hasDataSnapshot = true;
            dimensionValue = (DimensionValue)jtableContext.getDimensionSet().get("DATASNAPSHOTID");
            jtableContext.getDimensionSet().remove("DATASNAPSHOTID");
        }
        ReadWriteAccessCacheManager readWriteAccessCacheManager = this.readWriteAccessCacheManagerProvider.getObject();
        if (this.singleDim(jtableContext)) {
            readWriteAccessCacheManager.initSingleDimCache(readWriteAccessCacheParams);
        } else {
            readWriteAccessCacheManager.initCache(readWriteAccessCacheParams);
        }
        if (hasDataSnapshot) {
            jtableContext.getDimensionSet().put("DATASNAPSHOTID", dimensionValue);
        }
        FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(jtableContext, writeAuth ? Consts.FormAccessLevel.FORM_DATA_WRITE : Consts.FormAccessLevel.FORM_READ);
        FormReadWriteAccessData accessForms = null;
        HashSet<String> formKeySet = new HashSet<String>();
        HashMap<String, String> lockedFormKeysMap = new HashMap<String, String>();
        HashSet<String> publishedFormKeySet = new HashSet<String>();
        List<String> noAccessnFormKeys = null;
        HashSet<String> rejectFormKeys = new HashSet<String>();
        try {
            accessForms = this.accessProvider.getAccessForms(formReadWriteAccessData, readWriteAccessCacheManager, true);
            List<String> formKeys = accessForms.getFormKeys();
            noAccessnFormKeys = accessForms.getNoAccessnFormKeys();
            formKeySet.addAll(formKeys);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return this.getFormGroupDatas(jtableContext.getFormSchemeKey(), formKeySet, lockedFormKeysMap, publishedFormKeySet, noAccessnFormKeys, rejectFormKeys);
        }
        return this.getFormGroupDatas(jtableContext.getFormSchemeKey(), formKeySet, lockedFormKeysMap, publishedFormKeySet, noAccessnFormKeys, rejectFormKeys);
    }

    public List<FormGroupData> getFormGroupListByDWAndDatatime(JtableContext jtableContext, boolean writeAuth) {
        boolean hasDataSnapshot = false;
        DimensionValue dimensionValue = new DimensionValue();
        if (jtableContext.getDimensionSet().containsKey("DATASNAPSHOTID")) {
            hasDataSnapshot = true;
            dimensionValue = (DimensionValue)jtableContext.getDimensionSet().get("DATASNAPSHOTID");
            jtableContext.getDimensionSet().remove("DATASNAPSHOTID");
        }
        if (hasDataSnapshot) {
            jtableContext.getDimensionSet().put("DATASNAPSHOTID", dimensionValue);
        }
        HashSet<String> formKeySet = new HashSet<String>();
        HashMap<String, String> lockedFormKeysMap = new HashMap<String, String>();
        HashSet<String> publishedFormKeySet = new HashSet<String>();
        ArrayList<String> noAccessnFormKeys = new ArrayList<String>();
        HashSet<String> rejectFormKeys = new HashSet<String>();
        try {
            List allFormKeys = this.runtimeView.queryAllFormKeysByFormScheme(jtableContext.getFormSchemeKey());
            AccessFormParam accessFormParam = new AccessFormParam();
            accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_READ);
            accessFormParam.setFormKeys(allFormKeys);
            accessFormParam.setTaskKey(jtableContext.getTaskKey());
            accessFormParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
            Map dimensionSet1 = jtableContext.getDimensionSet();
            HashMap dimensionValueMap = new HashMap();
            for (Object key : jtableContext.getDimensionSet().keySet()) {
                DimensionValue dimension;
                String unitKey;
                dimensionValueMap.put((String)key, jtableContext.getDimensionSet().get(key));
                if (!((String)key).equals("MD_ORG") && !((String)key).equals("DATATIME")) {
                    ((DimensionValue)dimensionValueMap.get(key)).setValue("");
                }
                if (!((String)key).equals("MD_ORG") || !"all-unit".equals(unitKey = (dimension = (DimensionValue)jtableContext.getDimensionSet().get("MD_ORG")).getValue())) continue;
                ((DimensionValue)dimensionValueMap.get(key)).setValue("");
            }
            accessFormParam.setCollectionMasterKey(DimensionValueSetUtil.buildDimensionCollection((Map)jtableContext.getDimensionSet(), (String)jtableContext.getFormSchemeKey()));
            DimensionAccessFormInfo dimensionAccessFormInfo = this.dataAccessFormService.getBatchAccessForms(accessFormParam);
            for (DimensionAccessFormInfo.AccessFormInfo accessFormInfo : dimensionAccessFormInfo.getAccessForms()) {
                formKeySet.addAll(accessFormInfo.getFormKeys());
            }
            HashSet<String> noAccessforms = new HashSet<String>();
            for (DimensionAccessFormInfo.NoAccessFormInfo accessFormInfo : dimensionAccessFormInfo.getNoAccessForms()) {
                noAccessforms.add(accessFormInfo.getFormKey());
            }
            noAccessnFormKeys.addAll(noAccessforms);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return this.getFormGroupDatas(jtableContext.getFormSchemeKey(), formKeySet, lockedFormKeysMap, publishedFormKeySet, noAccessnFormKeys, rejectFormKeys);
        }
        return this.getFormGroupDatas(jtableContext.getFormSchemeKey(), formKeySet, lockedFormKeysMap, publishedFormKeySet, noAccessnFormKeys, rejectFormKeys);
    }

    private boolean singleDim(JtableContext jtableContext) {
        Map dimensionSet = jtableContext.getDimensionSet();
        for (DimensionValue value : dimensionSet.values()) {
            if (value != null && !StringUtils.isEmpty((String)value.getValue())) continue;
            return false;
        }
        return true;
    }

    public List<FormGroupData> getFormGroupList(String formSchemeKey) {
        return this.getFormGroupDatas(formSchemeKey, null, null, null, null, null);
    }

    public List<FormGroupData> getFormGroupListReadable(String formSchemeKey) {
        return this.getFormGroupDatasRadeable(formSchemeKey, null, null, null, null, null);
    }

    private List<FormGroupData> getFormGroupDatas(String formSchemeKey, Set<String> formKeySet, Map<String, String> lockedFormKeyMap, Set<String> publishedFormKeySet, List<String> noAccessnFormKeys, Set<String> rejectFormKeys) {
        ArrayList<FormGroupData> formGroupList = new ArrayList<FormGroupData>();
        List rootFormGroups = this.runtimeView.queryRootGroupsByFormScheme(formSchemeKey);
        if (rootFormGroups != null) {
            for (FormGroupDefine formGroupDefine : rootFormGroups) {
                FormGroupData formGroup;
                if (null != noAccessnFormKeys && noAccessnFormKeys.contains(formGroupDefine.getKey()) || (formGroup = this.getFormGroupData(formSchemeKey, formGroupDefine, formKeySet, lockedFormKeyMap, publishedFormKeySet, rejectFormKeys)) == null || formGroup.getGroups().isEmpty() && formGroup.getReports().isEmpty()) continue;
                formGroupList.add(formGroup);
            }
        }
        return formGroupList;
    }

    private List<FormGroupData> getFormGroupDatasRadeable(String formSchemeKey, Set<String> formKeySet, Set<String> lockedFormKeySet, Set<String> publishedFormKeySet, List<String> noAccessnFormKeys, Set<String> rejectFormKeys) {
        ArrayList<FormGroupData> formGroupList = new ArrayList<FormGroupData>();
        List rootFormGroups = this.runtimeView.queryRootGroupsByFormScheme(formSchemeKey);
        if (rootFormGroups != null) {
            for (FormGroupDefine formGroupDefine : rootFormGroups) {
                FormGroupData formGroup;
                if (null != noAccessnFormKeys && noAccessnFormKeys.contains(formGroupDefine.getKey()) || (formGroup = this.getFormGroupDataReadable(formSchemeKey, formGroupDefine, formKeySet, lockedFormKeySet, publishedFormKeySet, rejectFormKeys)) == null || formGroup.getGroups().isEmpty() && formGroup.getReports().isEmpty()) continue;
                formGroupList.add(formGroup);
            }
        }
        return formGroupList;
    }

    private FormGroupData getFormGroupData(String formSchemeKey, FormGroupDefine formGroupDefine, Set<String> formKeySet, Map<String, String> lockedFormKeyMap, Set<String> publishedFormKeySet, Set<String> rejectFormKeys) {
        FormGroupData formGroup = new FormGroupData();
        Set<String> lockedFormKeySet = null;
        if (lockedFormKeyMap != null) {
            lockedFormKeySet = lockedFormKeyMap.keySet();
        }
        formGroup.setKey(formGroupDefine.getKey());
        formGroup.setTitle(formGroupDefine.getTitle());
        formGroup.setCode(formGroupDefine.getCode());
        List formGroupDefines = this.runtimeView.getChildFormGroups(formGroupDefine.getKey());
        List<FormGroupData> groups = formGroup.getGroups();
        if (formGroupDefines != null) {
            for (FormGroupDefine childFormGroupDefine : formGroupDefines) {
                FormGroupData formGroupData = this.getFormGroupData(formSchemeKey, childFormGroupDefine, formKeySet, lockedFormKeyMap, publishedFormKeySet, rejectFormKeys);
                if (formGroupData == null || formGroupData.getGroups().isEmpty() && formGroupData.getReports().isEmpty()) continue;
                groups.add(formGroupData);
            }
        }
        List formDefines = null;
        try {
            formDefines = this.runtimeView.getAllFormsInGroup(formGroupDefine.getKey(), false);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (formDefines != null) {
            for (FormDefine formDefine : formDefines) {
                FormData formInfo = new FormData();
                formInfo.init(formDefine);
                List measures = formInfo.getMeasures();
                if (measures != null) {
                    for (MeasureViewData measure : measures) {
                        if (DataEntryUtil.isChinese() || !"\u91d1\u989d".equals(measure.getTitle())) continue;
                        measure.setTitle("Money");
                    }
                }
                if (formKeySet != null && !formKeySet.contains(formInfo.getKey())) continue;
                formGroup.addForm(formInfo);
            }
        }
        return formGroup;
    }

    private FormGroupData getFormGroupDataReadable(String formSchemeKey, FormGroupDefine formGroupDefine, Set<String> formKeySet, Set<String> lockedFormKeySet, Set<String> publishedFormKeySet, Set<String> rejectFormKeys) {
        FormGroupData formGroup = new FormGroupData();
        formGroup.setKey(formGroupDefine.getKey());
        formGroup.setTitle(formGroupDefine.getTitle());
        formGroup.setCode(formGroupDefine.getCode());
        List formGroupDefines = this.runtimeView.getChildFormGroups(formGroupDefine.getKey());
        List<FormGroupData> groups = formGroup.getGroups();
        if (formGroupDefines != null) {
            for (FormGroupDefine childFormGroupDefine : formGroupDefines) {
                FormGroupData formGroupData = this.getFormGroupDataReadable(formSchemeKey, childFormGroupDefine, formKeySet, lockedFormKeySet, publishedFormKeySet, rejectFormKeys);
                if (formGroupData == null || formGroupData.getGroups().isEmpty() && formGroupData.getReports().isEmpty()) continue;
                groups.add(formGroupData);
            }
        }
        List formDefines = null;
        String formSchemeEntity = "";
        try {
            formSchemeEntity = this.runtimeView.getFormSchemeEntity(formSchemeKey);
            formDefines = this.runtimeView.getAllFormsInGroup(formGroupDefine.getKey(), false);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (formDefines != null) {
            for (FormDefine formDefine : formDefines) {
                FormData formInfo = new FormData();
                formInfo.init(formDefine);
                if (!this.authorityProvider.canReadForm(formInfo.getKey())) continue;
                formGroup.addForm(formInfo);
            }
        }
        return formGroup;
    }
}

