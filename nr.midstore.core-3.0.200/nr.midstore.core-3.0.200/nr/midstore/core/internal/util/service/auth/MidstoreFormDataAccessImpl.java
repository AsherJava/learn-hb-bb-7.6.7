/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
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
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package nr.midstore.core.internal.util.service.auth;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
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
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.midstore.core.definition.bean.MistoreWorkFormInfo;
import nr.midstore.core.definition.bean.MistoreWorkUnitInfo;
import nr.midstore.core.definition.common.FormAccessType;
import nr.midstore.core.util.auth.IMidstoreFormDataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreFormDataAccessImpl
implements IMidstoreFormDataAccess {
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    private static final Logger logger = LoggerFactory.getLogger(MidstoreFormDataAccessImpl.class);

    @Override
    public Map<DimensionValueSet, List<String>> getFormDataAccess(String formSchemeKey, List<String> unitCodes, String periodCode, Map<String, DimensionValue> otherDims, FormAccessType accessType) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        List formKeys = this.runTimeViewController.queryAllFormKeysByFormScheme(formSchemeKey);
        if (otherDims == null) {
            otherDims = new HashMap<String, DimensionValue>();
        }
        HashMap<String, DimensionValue> masterDims = new HashMap<String, DimensionValue>();
        masterDims.putAll(otherDims);
        String entityDimName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        DimensionValue unitValue = new DimensionValue();
        unitValue.setName(entityDimName);
        unitValue.setValue(StringUtils.join(unitCodes.iterator(), (String)";"));
        masterDims.put(entityDimName, unitValue);
        String periodDimName = this.periodAdapter.getPeriodDimensionName();
        DimensionValue periodValue = new DimensionValue();
        periodValue.setName(periodDimName);
        periodValue.setValue(periodCode);
        masterDims.put(periodDimName, periodValue);
        HashMap<DimensionValueSet, List<String>> mistoreWorkUnitInfoMap = new HashMap<DimensionValueSet, List<String>>();
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(formScheme.getTaskKey(), formSchemeKey);
        DimensionCollection masterKey = DimensionValueSetUtil.buildDimensionCollection(masterDims, (String)formSchemeKey);
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
    public Map<DimensionValueSet, MistoreWorkUnitInfo> getFormDataAccessByReason(String formSchemeKey, List<String> unitCodes, String periodCode, Map<String, DimensionValue> otherDims, FormAccessType accessType) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        List formKeys = this.runTimeViewController.queryAllFormKeysByFormScheme(formSchemeKey);
        if (otherDims == null) {
            otherDims = new HashMap<String, DimensionValue>();
        }
        HashMap<String, DimensionValue> masterDims = new HashMap<String, DimensionValue>();
        masterDims.putAll(otherDims);
        String entityDimName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        DimensionValue unitValue = new DimensionValue();
        unitValue.setName(entityDimName);
        unitValue.setValue(StringUtils.join(unitCodes.iterator(), (String)";"));
        masterDims.put(entityDimName, unitValue);
        String periodDimName = this.periodAdapter.getPeriodDimensionName();
        DimensionValue periodValue = new DimensionValue();
        periodValue.setName(periodDimName);
        periodValue.setValue(periodCode);
        masterDims.put(periodDimName, periodValue);
        HashMap<DimensionValueSet, MistoreWorkUnitInfo> mistoreWorkUnitInfoMap = new HashMap<DimensionValueSet, MistoreWorkUnitInfo>();
        IDataAccessFormService dataFormAccess = this.dataAccessServiceProvider.getDataAccessFormService();
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(DimensionValueSetUtil.buildDimensionCollection(masterDims, (String)formSchemeKey));
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
                MistoreWorkUnitInfo unitInfo = new MistoreWorkUnitInfo();
                unitInfo.setMessage(null);
                unitInfo.setUnitCode(unitCode);
                unitInfo.setSuccess(false);
                if (mistoreWorkUnitInfoMap.containsKey(dimValueSet)) {
                    unitInfo = (MistoreWorkUnitInfo)mistoreWorkUnitInfoMap.get(dimValueSet);
                } else {
                    mistoreWorkUnitInfoMap.put(dimValueSet, unitInfo);
                }
                Map<String, MistoreWorkFormInfo> formInfoMap = unitInfo.getFormInfos();
                for (String formKey : accessFormKeys) {
                    MistoreWorkFormInfo mistoreWorkFormInfo = new MistoreWorkFormInfo();
                    mistoreWorkFormInfo.setFormKey(formKey);
                    formInfoMap.put(formKey, mistoreWorkFormInfo);
                    unitInfo.setFormInfos(formInfoMap);
                    try {
                        mistoreWorkFormInfo.setSuccess(true);
                    }
                    catch (Exception e) {
                        mistoreWorkFormInfo.setSuccess(false);
                        mistoreWorkFormInfo.setMessage("\u83b7\u53d6\u8868\u5355\u6743\u9650\u5931\u8d25\uff0c" + e.getMessage());
                        logger.error("\u83b7\u53d6\u8868\u5355\u6743\u9650\u5931\u8d25\uff0c" + e.getMessage(), e);
                    }
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
                MistoreWorkUnitInfo unitInfo = new MistoreWorkUnitInfo();
                unitInfo.setMessage(null);
                unitInfo.setUnitCode(unitCode);
                unitInfo.setSuccess(false);
                if (mistoreWorkUnitInfoMap.containsKey(dimValueSet)) {
                    unitInfo = (MistoreWorkUnitInfo)mistoreWorkUnitInfoMap.get(dimValueSet);
                } else {
                    mistoreWorkUnitInfoMap.put(dimValueSet, unitInfo);
                }
                Map<String, MistoreWorkFormInfo> formInfoMap = unitInfo.getFormInfos();
                MistoreWorkFormInfo mistoreWorkFormInfo = new MistoreWorkFormInfo();
                mistoreWorkFormInfo.setFormKey(formKey);
                formInfoMap.put(formKey, mistoreWorkFormInfo);
                unitInfo.setFormInfos(formInfoMap);
                try {
                    mistoreWorkFormInfo.setSuccess(false);
                    mistoreWorkFormInfo.setMessage("\u83b7\u53d6\u8868\u5355\u6743\u9650\u5931\u8d25\uff0c" + noAccessFormInfo.getReason());
                }
                catch (Exception e) {
                    mistoreWorkFormInfo.setSuccess(false);
                    mistoreWorkFormInfo.setMessage("\u83b7\u53d6\u8868\u5355\u6743\u9650\u5931\u8d25\uff0c" + e.getMessage());
                    logger.error("\u83b7\u53d6\u8868\u5355\u6743\u9650\u5931\u8d25\uff0c" + e.getMessage(), e);
                }
            }
        }
        return mistoreWorkUnitInfoMap;
    }
}

