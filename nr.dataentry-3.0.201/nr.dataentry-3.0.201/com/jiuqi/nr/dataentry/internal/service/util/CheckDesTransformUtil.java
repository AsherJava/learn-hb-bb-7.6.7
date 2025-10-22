/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.facade.param.input.ActionEnum
 *  com.jiuqi.nr.data.logic.facade.param.input.BatchSaveCheckDesParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckDescription
 *  com.jiuqi.nr.data.logic.facade.param.output.DesCheckData
 *  com.jiuqi.nr.data.logic.facade.param.output.DesCheckResult
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.input.BatchSaveFormulaCheckDesInfo
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesBatchSaveInfo
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo
 *  com.jiuqi.nr.jtable.params.output.DescriptionInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.nr.dataentry.internal.service.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import com.jiuqi.nr.data.logic.facade.param.input.BatchSaveCheckDesParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.data.logic.facade.param.output.DesCheckData;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataentry.bean.DesCheckResult;
import com.jiuqi.nr.dataentry.bean.DesCheckReturnInfo;
import com.jiuqi.nr.dataentry.service.IBatchCheckResultService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.input.BatchSaveFormulaCheckDesInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesBatchSaveInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo;
import com.jiuqi.nr.jtable.params.output.DescriptionInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckDesTransformUtil {
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IBatchCheckResultService iBatchCheckResultService;

    public CheckDesQueryParam getCheckDesQueryParam(FormulaCheckDesQueryInfo formulaCheckDesQueryInfo) {
        String desKey;
        String formulaKeyStr;
        List<String> formulaSchemeKeys;
        CheckDesQueryParam checkDesQueryParam = new CheckDesQueryParam();
        String formulaSchemeKeyStr = formulaCheckDesQueryInfo.getFormulaSchemeKey();
        if (StringUtils.isEmpty((String)formulaSchemeKeyStr)) {
            String formSchemeKey = formulaCheckDesQueryInfo.getFormSchemeKey();
            formulaSchemeKeys = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        } else {
            formulaSchemeKeys = new ArrayList<String>(Arrays.asList(formulaSchemeKeyStr.split(";")));
        }
        checkDesQueryParam.setFormulaSchemeKey(formulaSchemeKeys);
        String formKeyStr = formulaCheckDesQueryInfo.getFormKey();
        if (StringUtils.isNotEmpty((String)formKeyStr)) {
            checkDesQueryParam.setFormKey(new ArrayList<String>(Arrays.asList(formKeyStr.split(";"))));
        }
        if (StringUtils.isNotEmpty((String)(formulaKeyStr = formulaCheckDesQueryInfo.getFormulaKey()))) {
            checkDesQueryParam.setFormulaKey(new ArrayList<String>(Arrays.asList(formulaKeyStr.split(";"))));
        }
        if (StringUtils.isNotEmpty((String)(desKey = formulaCheckDesQueryInfo.getDesKey()))) {
            checkDesQueryParam.setRecordId(new ArrayList<String>(Arrays.asList(desKey.split(";"))));
        }
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(formulaCheckDesQueryInfo.getDimensionSet(), formulaCheckDesQueryInfo.getFormSchemeKey());
        checkDesQueryParam.setDimensionCollection(dimensionCollection);
        return checkDesQueryParam;
    }

    public FormulaCheckDesInfo getFormulaCheckDesInfo(CheckDesObj checkDesObj) {
        FormulaCheckDesInfo formulaCheckDesInfo = new FormulaCheckDesInfo();
        formulaCheckDesInfo.setDesKey(checkDesObj.getRecordId());
        formulaCheckDesInfo.setDimensionSet(checkDesObj.getDimensionSet());
        formulaCheckDesInfo.setFormulaKey(checkDesObj.getFormulaExpressionKey());
        formulaCheckDesInfo.setFormulaCode(checkDesObj.getFormulaCode());
        formulaCheckDesInfo.setGlobCol(checkDesObj.getGlobCol());
        formulaCheckDesInfo.setGlobRow(checkDesObj.getGlobRow());
        formulaCheckDesInfo.setFormulaSchemeKey(checkDesObj.getFormulaSchemeKey());
        formulaCheckDesInfo.setFormKey(checkDesObj.getFormKey());
        formulaCheckDesInfo.setFloatId(checkDesObj.getFloatId());
        CheckDescription checkDescription = checkDesObj.getCheckDescription();
        DescriptionInfo descriptionInfo = this.getDescriptionInfo(checkDescription);
        formulaCheckDesInfo.setDescriptionInfo(descriptionInfo);
        return formulaCheckDesInfo;
    }

    public List<FormulaCheckDesInfo> getFormulaCheckDesInfoList(List<CheckDesObj> checkDesObjs) {
        ArrayList<FormulaCheckDesInfo> result = new ArrayList<FormulaCheckDesInfo>();
        if (checkDesObjs != null && !checkDesObjs.isEmpty()) {
            for (CheckDesObj checkDesObj : checkDesObjs) {
                result.add(this.getFormulaCheckDesInfo(checkDesObj));
            }
        }
        return result;
    }

    public CheckDesObj getCheckDesObj(FormulaCheckDesInfo formulaCheckDesInfo) {
        CheckDesObj checkDesObj = new CheckDesObj();
        checkDesObj.setRecordId(formulaCheckDesInfo.getDesKey());
        checkDesObj.setDimensionSet(formulaCheckDesInfo.getDimensionSet());
        checkDesObj.setGlobCol(formulaCheckDesInfo.getGlobCol());
        checkDesObj.setGlobRow(formulaCheckDesInfo.getGlobRow());
        checkDesObj.setFormKey(formulaCheckDesInfo.getFormKey());
        checkDesObj.setFormulaSchemeKey(formulaCheckDesInfo.getFormulaSchemeKey());
        checkDesObj.setFloatId(formulaCheckDesInfo.getFloatId());
        checkDesObj.setFormulaCode(formulaCheckDesInfo.getFormulaCode());
        checkDesObj.setFormulaExpressionKey(formulaCheckDesInfo.getFormulaKey());
        checkDesObj.setCheckDescription(this.getCheckDescription(formulaCheckDesInfo.getDescriptionInfo()));
        return checkDesObj;
    }

    public List<CheckDesObj> getCheckDesObjList(List<FormulaCheckDesInfo> formulaCheckDesInfos) {
        ArrayList<CheckDesObj> result = new ArrayList<CheckDesObj>();
        if (formulaCheckDesInfos != null && !formulaCheckDesInfos.isEmpty()) {
            List userIds = formulaCheckDesInfos.stream().distinct().map(o -> o.getDescriptionInfo().getUserId()).collect(Collectors.toList());
            for (FormulaCheckDesInfo formulaCheckDesInfo : formulaCheckDesInfos) {
                result.add(this.getCheckDesObj(formulaCheckDesInfo));
            }
        }
        return result;
    }

    public DescriptionInfo getDescriptionInfo(CheckDescription checkDescription) {
        DescriptionInfo descriptionInfo = new DescriptionInfo();
        descriptionInfo.setDescription(checkDescription.getDescription());
        descriptionInfo.setUserId(checkDescription.getUserId());
        descriptionInfo.setUserTitle(checkDescription.getUserNickName());
        descriptionInfo.setUpdateTime(checkDescription.getUpdateTime());
        return descriptionInfo;
    }

    public List<DescriptionInfo> getDescriptionInfoList(List<CheckDescription> checkDescriptionList) {
        ArrayList<DescriptionInfo> result = new ArrayList<DescriptionInfo>();
        if (checkDescriptionList != null && !checkDescriptionList.isEmpty()) {
            for (CheckDescription checkDescription : checkDescriptionList) {
                result.add(this.getDescriptionInfo(checkDescription));
            }
        }
        return result;
    }

    public CheckDescription getCheckDescription(DescriptionInfo descriptionInfo) {
        CheckDescription checkDescription = new CheckDescription();
        checkDescription.setDescription(descriptionInfo.getDescription());
        checkDescription.setUserId(descriptionInfo.getUserId());
        checkDescription.setUserNickName(descriptionInfo.getUserTitle());
        checkDescription.setUpdateTime(descriptionInfo.getUpdateTime());
        return checkDescription;
    }

    public List<CheckDescription> getCheckDescriptionList(List<DescriptionInfo> checkDescriptionList) {
        ArrayList<CheckDescription> result = new ArrayList<CheckDescription>();
        if (checkDescriptionList != null && !checkDescriptionList.isEmpty()) {
            for (DescriptionInfo descriptionInfo : checkDescriptionList) {
                result.add(this.getCheckDescription(descriptionInfo));
            }
        }
        return result;
    }

    public CheckDesBatchSaveObj getCheckDesBatchSaveObj(FormulaCheckDesBatchSaveInfo formulaCheckDesBatchSaveInfo) {
        CheckDesBatchSaveObj checkDesBatchSaveObj = new CheckDesBatchSaveObj();
        checkDesBatchSaveObj.setCheckDesObjs(this.getCheckDesObjList(formulaCheckDesBatchSaveInfo.getDesInfos()));
        checkDesBatchSaveObj.setCheckDesQueryParam(this.getCheckDesQueryParam(formulaCheckDesBatchSaveInfo.getQueryInfo()));
        return checkDesBatchSaveObj;
    }

    public BatchSaveCheckDesParam getBatchSaveCheckDesParam(BatchSaveFormulaCheckDesInfo batchSaveFormulaCheckDesInfo) {
        Iterator defaultFormulaSchemeKeys;
        List<String> formulaSchemeKeys;
        String formSchemeKey;
        BatchSaveCheckDesParam batchSaveCheckDesParam = new BatchSaveCheckDesParam();
        batchSaveCheckDesParam.setActionEnum(ActionEnum.getByName((String)batchSaveFormulaCheckDesInfo.getActionName()));
        batchSaveCheckDesParam.setDescription(batchSaveFormulaCheckDesInfo.getDescription());
        batchSaveCheckDesParam.setActionId(batchSaveFormulaCheckDesInfo.getAsyncTaskKey());
        CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
        String formulaSchemeKeyStr = StringUtils.isEmpty((String)batchSaveFormulaCheckDesInfo.getFormulaSchemeKeys()) ? batchSaveFormulaCheckDesInfo.getContext().getFormulaSchemeKey() : batchSaveFormulaCheckDesInfo.getFormulaSchemeKeys();
        if (StringUtils.isEmpty((String)formulaSchemeKeyStr)) {
            formSchemeKey = batchSaveFormulaCheckDesInfo.getContext().getFormSchemeKey();
            formulaSchemeKeys = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        } else {
            formulaSchemeKeys = new ArrayList<String>(Arrays.asList(formulaSchemeKeyStr.split(";")));
            formSchemeKey = batchSaveFormulaCheckDesInfo.getContext().getFormSchemeKey();
            defaultFormulaSchemeKeys = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            formulaSchemeKeys = formulaSchemeKeys.stream().filter(formulaSchemeKey -> defaultFormulaSchemeKeys.contains(formulaSchemeKey)).collect(Collectors.toList());
        }
        checkResultQueryParam.setFormulaSchemeKeys(formulaSchemeKeys);
        Map formulas = batchSaveFormulaCheckDesInfo.getFormulas();
        if (formulas.isEmpty()) {
            checkResultQueryParam.setMode(Mode.FORM);
            checkResultQueryParam.setRangeKeys(new ArrayList());
        } else {
            defaultFormulaSchemeKeys = formulas.entrySet().iterator();
            if (defaultFormulaSchemeKeys.hasNext()) {
                Map.Entry entry = (Map.Entry)defaultFormulaSchemeKeys.next();
                if (((List)entry.getValue()).isEmpty()) {
                    checkResultQueryParam.setMode(Mode.FORM);
                    checkResultQueryParam.setRangeKeys(new ArrayList(formulas.keySet()));
                } else if ("null".equals(entry.getKey())) {
                    checkResultQueryParam.setMode(Mode.FORM);
                    checkResultQueryParam.setRangeKeys(new ArrayList((Collection)entry.getValue()));
                } else {
                    checkResultQueryParam.setMode(Mode.FORMULA);
                    Iterator formulaKeys = new ArrayList();
                    for (List value : formulas.values()) {
                        formulaKeys.addAll(value);
                    }
                    checkResultQueryParam.setRangeKeys(formulaKeys);
                }
            }
        }
        checkResultQueryParam.setVariableMap(batchSaveFormulaCheckDesInfo.getContext().getVariableMap());
        List checkTypes = batchSaveFormulaCheckDesInfo.getCheckTypes();
        if (checkTypes != null && !checkTypes.isEmpty()) {
            HashMap<Integer, Object> checkTypeArg = new HashMap<Integer, Object>();
            for (Integer checkType : checkTypes) {
                checkTypeArg.put(checkType, null);
            }
            checkResultQueryParam.setCheckTypes(checkTypeArg);
        }
        batchSaveCheckDesParam.setCoverOriginalDes(batchSaveFormulaCheckDesInfo.isCoverOriginalDes());
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        for (String key : batchSaveFormulaCheckDesInfo.getContext().getDimensionSet().keySet()) {
            dimensionSet.put(key, new DimensionValue((DimensionValue)batchSaveFormulaCheckDesInfo.getContext().getDimensionSet().get(key)));
        }
        if (batchSaveFormulaCheckDesInfo.getSelectedUnits() != null && batchSaveFormulaCheckDesInfo.getSelectedUnits().size() > 0) {
            checkResultQueryParam.setQueryByDim(true);
            EntityViewData masterEntity = this.jtableParamService.getDwEntity(batchSaveFormulaCheckDesInfo.getContext().getFormSchemeKey());
            String mainDimName = masterEntity.getDimensionName();
            StringBuilder mdOrg = new StringBuilder();
            for (String dw : batchSaveFormulaCheckDesInfo.getSelectedUnits()) {
                mdOrg.append(dw).append(";");
            }
            mdOrg.deleteCharAt(mdOrg.length() - 1);
            ((DimensionValue)dimensionSet.get(mainDimName)).setValue(mdOrg.toString());
        }
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionSet, batchSaveFormulaCheckDesInfo.getContext().getFormSchemeKey());
        checkResultQueryParam.setDimensionCollection(dimensionCollection);
        batchSaveCheckDesParam.setCheckResultQueryParam(checkResultQueryParam);
        return batchSaveCheckDesParam;
    }

    public DesCheckResult getDesCheckResult(com.jiuqi.nr.data.logic.facade.param.output.DesCheckResult newObj) {
        DesCheckResult desCheckResult = new DesCheckResult();
        desCheckResult.setCheckCharNum(newObj.getCheckCharMinNum());
        desCheckResult.setCheckCharMaxNum(newObj.getCheckCharMaxNum());
        ArrayList<DesCheckReturnInfo> a = new ArrayList<DesCheckReturnInfo>();
        List desCheckData = newObj.getDesCheckData();
        if (desCheckData != null && !desCheckData.isEmpty()) {
            for (DesCheckData desCheckDatum : desCheckData) {
                DesCheckReturnInfo desCheckReturnInfo = new DesCheckReturnInfo();
                desCheckReturnInfo.setUnitCode(desCheckDatum.getUnitCode());
                desCheckReturnInfo.setUnitTitle(desCheckDatum.getUnitTitle());
                a.add(desCheckReturnInfo);
            }
        }
        desCheckResult.setCheckReturnInfos(a);
        return desCheckResult;
    }
}

