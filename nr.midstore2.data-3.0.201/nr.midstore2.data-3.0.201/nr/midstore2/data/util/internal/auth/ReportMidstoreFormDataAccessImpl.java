/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$NoAccessFormInfo
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFormInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo
 *  com.jiuqi.nvwa.midstore.core.definition.common.FormAccessType
 */
package nr.midstore2.data.util.internal.auth;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFormInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo;
import com.jiuqi.nvwa.midstore.core.definition.common.FormAccessType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import nr.midstore2.data.util.auth.IReportMidstoreFormDataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportMidstoreFormDataAccessImpl
implements IReportMidstoreFormDataAccess {
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private TreeState treeState;
    @Autowired
    private DimensionBuildUtil dimBuildUtil;
    private static final Logger logger = LoggerFactory.getLogger(ReportMidstoreFormDataAccessImpl.class);

    @Override
    public Map<DimensionValueSet, List<String>> getFormDataAccess(String formSchemeKey, List<String> unitCodes, String nrPeriodCode, Map<String, DimensionValue> otherDims, FormAccessType accessType) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        List formKeys = this.runTimeViewController.queryAllFormKeysByFormScheme(formSchemeKey);
        if (otherDims == null) {
            otherDims = new HashMap<String, DimensionValue>();
        }
        HashMap<String, DimensionValue> masterDims = new HashMap<String, DimensionValue>();
        for (Map.Entry<String, DimensionValue> entry : otherDims.entrySet()) {
            String dimName = entry.getKey();
            DimensionValue dimValue = entry.getValue();
            String newValueCode = dimValue.getValue();
            newValueCode = StringUtils.isNotEmpty((String)newValueCode) ? newValueCode.replace(",", ";") : "";
            DimensionValue otherValue = new DimensionValue();
            otherValue.setName(dimName);
            otherValue.setValue(newValueCode);
            masterDims.put(dimName, otherValue);
        }
        String entityDimName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        DimensionValue unitValue = new DimensionValue();
        unitValue.setName(entityDimName);
        unitValue.setValue(StringUtils.join(unitCodes.iterator(), (String)";"));
        masterDims.put(entityDimName, unitValue);
        String periodDimName = this.periodAdapter.getPeriodDimensionName();
        DimensionValue periodValue = new DimensionValue();
        periodValue.setName(periodDimName);
        periodValue.setValue(nrPeriodCode);
        masterDims.put(periodDimName, periodValue);
        DimensionCollection masterKey = this.dimBuildUtil.getDimensionCollection(masterDims, formSchemeKey);
        HashMap<DimensionValueSet, List<String>> mistoreWorkUnitInfoMap = new HashMap<DimensionValueSet, List<String>>();
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(formScheme.getTaskKey(), formSchemeKey);
        IBatchAccessResult batchAccess = dataAccessService.getWriteAccess(masterKey, formKeys);
        for (String unitCode : unitCodes) {
            DimensionValue unitDim = new DimensionValue();
            unitDim.setName(entityDimName);
            unitDim.setValue(unitCode);
            masterDims.put(entityDimName, unitDim);
            DimensionValueSet dimValueSet = DimensionValueSetUtil.getDimensionValueSet(masterDims);
            DimensionCombination unitDimCombination = DimensionValueSetUtil.buildDimensionCombination((DimensionValueSet)dimValueSet, (String)formSchemeKey);
            ArrayList<String> accessFormKeys = new ArrayList<String>();
            mistoreWorkUnitInfoMap.put(dimValueSet, accessFormKeys);
            for (String formKey : formKeys) {
                IAccessResult accessResult = batchAccess.getAccess(unitDimCombination, formKey);
                try {
                    if (!accessResult.haveAccess()) continue;
                    accessFormKeys.add(formKey);
                }
                catch (Exception e) {
                    logger.error("\u83b7\u53d6\u8868\u5355\u6743\u9650\u5931\u8d25\uff0c" + e.getMessage(), e);
                }
            }
        }
        return mistoreWorkUnitInfoMap;
    }

    @Override
    public Map<DimensionValueSet, MistoreWorkUnitInfo> getFormDataAccessByReason(String formSchemeKey, List<String> unitCodes, String nrPeriodCode, Map<String, DimensionValue> otherDims, FormAccessType accessType) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        List formKeys = this.runTimeViewController.queryAllFormKeysByFormScheme(formSchemeKey);
        if (otherDims == null) {
            otherDims = new HashMap<String, DimensionValue>();
        }
        HashMap<String, DimensionValue> masterDims = new HashMap<String, DimensionValue>();
        for (Map.Entry<String, DimensionValue> entry : otherDims.entrySet()) {
            String dimName = entry.getKey();
            DimensionValue dimValue = entry.getValue();
            String newValueCode = dimValue.getValue();
            newValueCode = StringUtils.isNotEmpty((String)newValueCode) ? newValueCode.replace(",", ";") : "";
            DimensionValue otherValue = new DimensionValue();
            otherValue.setName(dimName);
            otherValue.setValue(newValueCode);
            masterDims.put(dimName, otherValue);
        }
        String entityDimName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        DimensionValue unitValue = new DimensionValue();
        unitValue.setName(entityDimName);
        unitValue.setValue(StringUtils.join(unitCodes.iterator(), (String)";"));
        masterDims.put(entityDimName, unitValue);
        String periodDimName = this.periodAdapter.getPeriodDimensionName();
        DimensionValue periodValue = new DimensionValue();
        periodValue.setName(periodDimName);
        periodValue.setValue(nrPeriodCode);
        masterDims.put(periodDimName, periodValue);
        DimensionCollection masterKey = this.dimBuildUtil.getDimensionCollection(masterDims, formSchemeKey);
        HashMap<DimensionValueSet, MistoreWorkUnitInfo> mistoreWorkUnitInfoMap = new HashMap<DimensionValueSet, MistoreWorkUnitInfo>();
        IDataAccessFormService dataFormAccess = this.dataAccessServiceProvider.getDataAccessFormService();
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(masterKey);
        accessFormParam.setTaskKey(formScheme.getTaskKey());
        accessFormParam.setFormSchemeKey(formSchemeKey);
        accessFormParam.setFormKeys(formKeys);
        if (accessType == FormAccessType.FORMACCESS_READ) {
            accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_READ);
        } else if (accessType == FormAccessType.FORMACCESS_WRITE) {
            accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_WRITE);
        }
        DimensionAccessFormInfo batchDimensionValueFormInfo = dataFormAccess.getBatchAccessForms(accessFormParam);
        List acessFormInfos = batchDimensionValueFormInfo.getAccessForms();
        for (DimensionAccessFormInfo.AccessFormInfo accessFormInfo : acessFormInfos) {
            Map dimensions = accessFormInfo.getDimensions();
            List accessFormKeys = accessFormInfo.getFormKeys();
            List dimensionValueSetList = DimensionValueSetUtil.getDimensionValueSetList((Map)dimensions);
            for (DimensionValueSet dimValueSet : dimensionValueSetList) {
                String unitCode = dimValueSet.getValue(entityDimName).toString();
                MistoreWorkUnitInfo unitInfo = null;
                if (mistoreWorkUnitInfoMap.containsKey(dimValueSet)) {
                    unitInfo = (MistoreWorkUnitInfo)mistoreWorkUnitInfoMap.get(dimValueSet);
                } else {
                    unitInfo = new MistoreWorkUnitInfo();
                    unitInfo.setMessage(null);
                    unitInfo.setUnitCode(unitCode);
                    unitInfo.setSuccess(true);
                    mistoreWorkUnitInfoMap.put(dimValueSet, unitInfo);
                }
                Map formInfoMap = unitInfo.getFormInfos();
                for (String formKey : accessFormKeys) {
                    MistoreWorkFormInfo mistoreWorkFormInfo = new MistoreWorkFormInfo();
                    mistoreWorkFormInfo.setFormKey(formKey);
                    mistoreWorkFormInfo.setSuccess(true);
                    formInfoMap.put(formKey, mistoreWorkFormInfo);
                }
            }
        }
        List noAcessFormInfos = batchDimensionValueFormInfo.getNoAccessForms();
        for (DimensionAccessFormInfo.NoAccessFormInfo noAccessFormInfo : noAcessFormInfos) {
            Map dimensions = noAccessFormInfo.getDimensions();
            String formKey = noAccessFormInfo.getFormKey();
            List dimensionValueSetList = DimensionValueSetUtil.getDimensionValueSetList((Map)dimensions);
            for (DimensionValueSet dimValueSet : dimensionValueSetList) {
                String unitCode = dimValueSet.getValue(entityDimName).toString();
                MistoreWorkUnitInfo unitInfo = null;
                if (mistoreWorkUnitInfoMap.containsKey(dimValueSet)) {
                    unitInfo = (MistoreWorkUnitInfo)mistoreWorkUnitInfoMap.get(dimValueSet);
                } else {
                    unitInfo = new MistoreWorkUnitInfo();
                    unitInfo.setMessage(null);
                    unitInfo.setUnitCode(unitCode);
                    mistoreWorkUnitInfoMap.put(dimValueSet, unitInfo);
                }
                unitInfo.setSuccess(false);
                Map formInfoMap = unitInfo.getFormInfos();
                MistoreWorkFormInfo mistoreWorkFormInfo = null;
                if (formInfoMap.containsKey(formKey)) {
                    mistoreWorkFormInfo = (MistoreWorkFormInfo)formInfoMap.get(formKey);
                } else {
                    mistoreWorkFormInfo = new MistoreWorkFormInfo();
                    mistoreWorkFormInfo.setFormKey(formKey);
                    formInfoMap.put(formKey, mistoreWorkFormInfo);
                }
                mistoreWorkFormInfo.setSuccess(false);
                mistoreWorkFormInfo.setMessage(noAccessFormInfo.getReason());
            }
        }
        return mistoreWorkUnitInfoMap;
    }

    @Override
    public List<String> getUnitListByfiterUnitState(String formSchemeKey, List<String> unitCodes, String nrPeriodCode, Map<String, DimensionValue> otherDims, List<String> filterStates) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        if (otherDims == null) {
            otherDims = new HashMap<String, DimensionValue>();
        }
        HashMap<String, DimensionValue> masterDims = new HashMap<String, DimensionValue>();
        for (Map.Entry<String, DimensionValue> entry : otherDims.entrySet()) {
            String dimName = entry.getKey();
            DimensionValue dimValue = entry.getValue();
            String newValueCode = dimValue.getValue();
            newValueCode = StringUtils.isNotEmpty((String)newValueCode) ? newValueCode.replace(",", ";") : "";
            DimensionValue otherValue = new DimensionValue();
            otherValue.setName(dimName);
            otherValue.setValue(newValueCode);
            masterDims.put(dimName, otherValue);
        }
        String entityDimName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        DimensionValue unitValue = new DimensionValue();
        unitValue.setName(entityDimName);
        unitValue.setValue(StringUtils.join(unitCodes.iterator(), (String)";"));
        masterDims.put(entityDimName, unitValue);
        String periodDimName = this.periodAdapter.getPeriodDimensionName();
        DimensionValue periodValue = new DimensionValue();
        periodValue.setName(periodDimName);
        periodValue.setValue(nrPeriodCode);
        masterDims.put(periodDimName, periodValue);
        DimensionCollection masterKey = this.dimBuildUtil.getDimensionCollection(masterDims, formSchemeKey);
        DimensionValueSet dataSets = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)masterKey);
        HashSet<String> filterStates2 = new HashSet<String>();
        for (String stateCode : filterStates) {
            if ("UN_UPLOAD".equalsIgnoreCase(stateCode)) {
                filterStates2.add(UploadState.ORIGINAL_UPLOAD.toString());
                continue;
            }
            if ("SUBMIT".equalsIgnoreCase(stateCode)) {
                filterStates2.add(UploadState.SUBMITED.toString());
                continue;
            }
            if ("UPLOAD".equalsIgnoreCase(stateCode)) {
                filterStates2.add(UploadState.UPLOADED.toString());
                continue;
            }
            if ("REJECT".equalsIgnoreCase(stateCode)) {
                filterStates2.add(UploadState.REJECTED.toString());
                continue;
            }
            if (!"CONFIRM".equalsIgnoreCase(stateCode)) continue;
            filterStates2.add(UploadState.CONFIRMED.toString());
        }
        ArrayList<String> unitList = new ArrayList<String>();
        Map stateMap = this.treeState.getWorkflowUploadState(dataSets, null, formSchemeKey);
        for (Map.Entry entry : stateMap.entrySet()) {
            DimensionValueSet dimValueSet = (DimensionValueSet)entry.getKey();
            ActionStateBean sateBean = (ActionStateBean)entry.getValue();
            String unitCode = dimValueSet.getValue(entityDimName).toString();
            if (StringUtils.isEmpty((String)sateBean.getCode()) || !filterStates2.contains(sateBean.getCode())) continue;
            unitList.add(unitCode);
        }
        return unitList;
    }
}

