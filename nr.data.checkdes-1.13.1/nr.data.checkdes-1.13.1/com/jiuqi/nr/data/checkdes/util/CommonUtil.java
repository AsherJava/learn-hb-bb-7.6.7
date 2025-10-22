/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckDescription
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.data.checkdes.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.checkdes.internal.helper.Helper;
import com.jiuqi.nr.data.checkdes.obj.BasePar;
import com.jiuqi.nr.data.checkdes.obj.CKDImpDetails;
import com.jiuqi.nr.data.checkdes.obj.CKDImpPar;
import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import com.jiuqi.nr.data.checkdes.obj.MapHandlePar;
import com.jiuqi.nr.data.checkdes.service.internal.IDataMappingHandler;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CommonUtil {
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private List<IDataMappingHandler> dataMappingHandlers;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private Helper helper;

    public CKDTransObj handleMapping(MapHandlePar par, CKDTransObj ckdTransObj) {
        block1: {
            IDataMappingHandler dataMappingHandler;
            if (CollectionUtils.isEmpty(this.dataMappingHandlers)) break block1;
            Iterator<IDataMappingHandler> iterator = this.dataMappingHandlers.iterator();
            while (iterator.hasNext() && (ckdTransObj = (dataMappingHandler = iterator.next()).handle(par, ckdTransObj)) != null) {
            }
        }
        return ckdTransObj;
    }

    public FormSchemeDefine getFormSchemeDefine(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            throw new IllegalArgumentException("\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728:" + formSchemeKey);
        }
        return formScheme;
    }

    public List<String> getFSAllForms(String formSchemeKey) {
        return this.runTimeViewController.queryAllFormKeysByFormScheme(formSchemeKey);
    }

    public List<FormulaSchemeDefine> getFSAllFLSs(String formSchemeKey) {
        return this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeKey);
    }

    public List<String> getFormSchemeEntityNames(String formSchemeKey) {
        FormSchemeDefine formScheme = this.getFormSchemeDefine(formSchemeKey);
        String periodEntityId = formScheme.getDateTime();
        String dwEntityId = formScheme.getDw();
        IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(periodEntityId);
        if (periodEntity == null) {
            throw new IllegalArgumentException("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848" + formSchemeKey + "\u7684\u65f6\u671f\u7ef4\u5ea6:" + periodEntityId);
        }
        IEntityDefine entity = this.entityMetaService.queryEntity(dwEntityId);
        if (entity == null) {
            throw new IllegalArgumentException("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848" + formSchemeKey + "\u7684\u4e3b\u7ef4\u5ea6:" + dwEntityId);
        }
        ArrayList<String> result = new ArrayList<String>();
        result.add(periodEntity.getDimensionName());
        result.add(entity.getDimensionName());
        result.addAll(this.getDimEntityNames(formSchemeKey));
        return result;
    }

    public void checkPar(BasePar param) {
        CKDImpPar ckdImpPar;
        if (StringUtils.isEmpty((String)param.getFormSchemeKey())) {
            throw new IllegalArgumentException("formSchemeKey must not be null or empty");
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(param.getFormSchemeKey());
        if (formScheme == null) {
            throw new IllegalArgumentException("unable to find formScheme by provided key: " + param.getFormSchemeKey());
        }
        if (param instanceof CKDImpPar && StringUtils.isEmpty((String)(ckdImpPar = (CKDImpPar)param).getFilePath())) {
            throw new IllegalArgumentException("import filePath must not be null or empty");
        }
        if (param.getDimensionCollection() == null || CollectionUtils.isEmpty(param.getDimensionCollection().getDimensionCombinations())) {
            throw new IllegalArgumentException("dimensionCollection must not be null or empty");
        }
        if (CollectionUtils.isEmpty(param.getFormulaSchemeKeys())) {
            List allFmlSchemes = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(param.getFormSchemeKey());
            if (CollectionUtils.isEmpty(allFmlSchemes)) {
                throw new IllegalArgumentException("formulaSchemeKeys must not be null or empty");
            }
            param.setFormulaSchemeKeys(allFmlSchemes.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        }
    }

    public List<String> getDimEntityNames(String formSchemeKey) {
        ArrayList<String> result = new ArrayList<String>();
        FormSchemeDefine formScheme = this.getFormSchemeDefine(formSchemeKey);
        String dims = formScheme.getDims();
        List<Object> dimEntityIds = new ArrayList();
        if (StringUtils.isNotEmpty((String)dims)) {
            dimEntityIds = Arrays.asList(dims.split(";"));
            for (String string : dimEntityIds) {
                if (AdjustUtils.isAdjust((String)string).booleanValue()) {
                    result.add("ADJUST");
                    continue;
                }
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(string);
                if (entityDefine == null) continue;
                result.add(entityDefine.getDimensionName());
            }
        }
        if (!dimEntityIds.contains("ADJUST") && this.formSchemeService.enableAdjustPeriod(formSchemeKey)) {
            result.add("ADJUST");
        }
        return result;
    }

    public CKDTransObj getCKDTransObj(CheckDesObj checkDesObj) {
        CKDTransObj ckdTransObj = new CKDTransObj();
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(checkDesObj.getFormulaSchemeKey());
        ckdTransObj.setFormulaSchemeKey(checkDesObj.getFormulaSchemeKey());
        if (formulaSchemeDefine != null) {
            ckdTransObj.setFormulaSchemeTitle(formulaSchemeDefine.getTitle());
        }
        ckdTransObj.setFormKey(checkDesObj.getFormKey());
        if (checkDesObj.getFormKey() == null || "00000000-0000-0000-0000-000000000000".equals(checkDesObj.getFormKey())) {
            ckdTransObj.setFormCode("00000000-0000-0000-0000-000000000000");
            ckdTransObj.setFormTitle("\u8868\u95f4");
        } else {
            FormDefine formDefine = this.runTimeViewController.queryFormById(checkDesObj.getFormKey());
            if (formDefine != null) {
                ckdTransObj.setFormCode(formDefine.getFormCode());
                ckdTransObj.setFormTitle(formDefine.getTitle());
            }
        }
        ckdTransObj.setFormulaCode(checkDesObj.getFormulaCode());
        ckdTransObj.setFormulaExpressionKey(checkDesObj.getFormulaExpressionKey());
        ckdTransObj.setFormulaKey(ckdTransObj.getFormulaExpressionKey().substring(36));
        ckdTransObj.setGlobRow(String.valueOf(checkDesObj.getGlobRow()));
        ckdTransObj.setGlobCol(String.valueOf(checkDesObj.getGlobCol()));
        ckdTransObj.setDimStr(checkDesObj.getFloatId());
        CheckDescription checkDescription = checkDesObj.getCheckDescription();
        if (checkDescription != null) {
            ckdTransObj.setUpdateTime(checkDescription.getUpdateTime().toEpochMilli());
            ckdTransObj.setDescription(checkDescription.getDescription());
            ckdTransObj.setUserId(checkDescription.getUserId());
            ckdTransObj.setUserNickName(checkDescription.getUserNickName());
        }
        Map dimensionSet = checkDesObj.getDimensionSet();
        HashSet<String> bnwdDimNames = new HashSet<String>();
        if (StringUtils.isNotEmpty((String)checkDesObj.getFloatId())) {
            String[] dims;
            for (String dim : dims = checkDesObj.getFloatId().split(";")) {
                String[] dimValues = dim.split(":");
                if (dimValues.length != 2) continue;
                bnwdDimNames.add(dimValues[0]);
            }
        }
        bnwdDimNames.forEach(dimensionSet::remove);
        HashMap<String, String> dimMap = new HashMap<String, String>();
        dimensionSet.forEach((key, value) -> dimMap.put((String)key, value.getValue()));
        ckdTransObj.setDimMap(dimMap);
        return ckdTransObj;
    }

    public CKDImpDetails getImpDetail(CKDTransObj ckdTransObj, String message) {
        CKDImpDetails detailItem = new CKDImpDetails();
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder(ckdTransObj.getDimensionValueSet());
        detailItem.setCombination(builder.getCombination());
        detailItem.setFormulaSchemeKey(ckdTransObj.getFormulaSchemeKey());
        detailItem.setFormulaSchemeTitle(ckdTransObj.getFormulaSchemeTitle());
        detailItem.setFormKey(ckdTransObj.getFormKey());
        detailItem.setFormCode(ckdTransObj.getFormCode());
        detailItem.setFormTitle(ckdTransObj.getFormTitle());
        detailItem.setFormulaKey(ckdTransObj.getFormulaKey());
        detailItem.setFormulaCode(ckdTransObj.getFormulaCode());
        detailItem.setCkdTransObj(ckdTransObj);
        detailItem.setErrorMessage(message);
        return detailItem;
    }

    public CheckDesObj generateCheckDesObj(CKDTransObj ckdTransObj) {
        CheckDesObj checkDesObj = new CheckDesObj();
        checkDesObj.setFormulaSchemeKey(ckdTransObj.getFormulaSchemeKey());
        checkDesObj.setFormulaExpressionKey(ckdTransObj.getFormulaExpressionKey());
        checkDesObj.setFormulaCode(ckdTransObj.getFormulaCode());
        checkDesObj.setFormKey(ckdTransObj.getFormKey());
        checkDesObj.setFloatId(ckdTransObj.getDimStr());
        checkDesObj.setGlobCol(new Double(ckdTransObj.getGlobCol()).intValue());
        checkDesObj.setGlobRow(new Double(ckdTransObj.getGlobRow()).intValue());
        CheckDescription des = new CheckDescription();
        des.setUserId(ckdTransObj.getUserId());
        des.setUserNickName(ckdTransObj.getUserNickName());
        des.setDescription(ckdTransObj.getDescription());
        Instant instant = Instant.ofEpochMilli(ckdTransObj.getUpdateTime());
        des.setUpdateTime(instant);
        checkDesObj.setCheckDescription(des);
        for (Map.Entry<String, String> entry : ckdTransObj.getDimMap().entrySet()) {
            DimensionValue dimensionValue = new DimensionValue();
            dimensionValue.setName(entry.getKey());
            dimensionValue.setValue(entry.getValue());
            checkDesObj.getDimensionSet().put(dimensionValue.getName(), dimensionValue);
        }
        if (StringUtils.isNotEmpty((String)checkDesObj.getFloatId())) {
            String[] dims;
            for (String dim : dims = checkDesObj.getFloatId().split(";")) {
                String[] dimValues = dim.split(":");
                if (dimValues.length != 2) continue;
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(dimValues[0]);
                dimensionValue.setValue(dimValues[1]);
                checkDesObj.getDimensionSet().put(dimValues[0], dimensionValue);
            }
        }
        return checkDesObj;
    }

    public DimensionAccessFormInfo getDimensionAccessFormInfo(BasePar param, AccessLevel.FormAccessLevel formAccessLevel) {
        IDataAccessFormService dataAccessFormService = this.dataAccessServiceProvider.getDataAccessFormService();
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(param.getDimensionCollection());
        accessFormParam.setTaskKey(this.runTimeViewController.getFormScheme(param.getFormSchemeKey()).getTaskKey());
        accessFormParam.setFormSchemeKey(param.getFormSchemeKey());
        accessFormParam.setFormKeys(param.getFormKeys() == null ? new ArrayList() : param.getFormKeys());
        accessFormParam.setFormAccessLevel(formAccessLevel);
        return dataAccessFormService.getBatchAccessForms(accessFormParam);
    }

    public Map<String, Set<DimensionValueSet>> mapAccessFormDim(DimensionAccessFormInfo accessFormInfo) {
        HashMap<String, Set<DimensionValueSet>> result = new HashMap<String, Set<DimensionValueSet>>();
        if (accessFormInfo != null) {
            for (DimensionAccessFormInfo.AccessFormInfo accessForm : accessFormInfo.getAccessForms()) {
                Map dimensions = accessForm.getDimensions();
                List<DimensionValueSet> dimensionValueSetList = this.getDimensionValueSetList(dimensions);
                List formKeys = accessForm.getFormKeys();
                for (String formKey : formKeys) {
                    if (result.containsKey(formKey)) {
                        ((Set)result.get(formKey)).addAll(dimensionValueSetList);
                        continue;
                    }
                    HashSet<DimensionValueSet> d = new HashSet<DimensionValueSet>(dimensionValueSetList);
                    result.put(formKey, d);
                }
            }
        }
        return result;
    }

    public List<DimensionValueSet> getDimensionValueSetList(Map<String, DimensionValue> dimensionSet) {
        List<DimensionValueSet> dimensionValueSetList = new ArrayList<DimensionValueSet>();
        if (dimensionSet == null) {
            return dimensionValueSetList;
        }
        ArrayList<String> dimensionNameList = new ArrayList<String>();
        ArrayList<List<Object>> dimensionValueList = new ArrayList<List<Object>>();
        for (DimensionValue value : dimensionSet.values()) {
            String[] values = value.getValue().split(";");
            List<String> valueList = Arrays.asList(values);
            dimensionValueList.add(valueList);
            dimensionNameList.add(value.getName());
        }
        if (dimensionNameList.size() > 0 && dimensionNameList.size() == dimensionValueList.size()) {
            dimensionValueSetList = this.getDimensionValueSetList(dimensionNameList, dimensionValueList, 0);
        }
        return dimensionValueSetList;
    }

    public DimensionValueSet mergeDimensionValueSet(Collection<DimensionValueSet> dimensionValueSets) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (!CollectionUtils.isEmpty(dimensionValueSets)) {
            HashMap<String, Set> map = new HashMap<String, Set>();
            for (DimensionValueSet valueSet : dimensionValueSets) {
                for (int i = 0; i < valueSet.size(); ++i) {
                    if (map.containsKey(valueSet.getName(i))) {
                        ((Set)map.get(valueSet.getName(i))).add((String)valueSet.getValue(i));
                        continue;
                    }
                    HashSet<String> v = new HashSet<String>();
                    v.add((String)valueSet.getValue(i));
                    map.put(valueSet.getName(i), v);
                }
            }
            map.forEach((key, value) -> {
                if (value.size() == 1) {
                    dimensionValueSet.setValue(key, value.stream().findAny().get());
                } else {
                    dimensionValueSet.setValue(key, new ArrayList(value));
                }
            });
        }
        return dimensionValueSet;
    }

    private List<DimensionValueSet> getDimensionValueSetList(List<String> dimensionNameList, List<List<Object>> dimensionValueList, int layer) {
        ArrayList<DimensionValueSet> dimensionValueSetList;
        block4: {
            block3: {
                dimensionValueSetList = new ArrayList<DimensionValueSet>();
                if (layer >= dimensionNameList.size() - 1) break block3;
                String dimensionName = dimensionNameList.get(layer);
                List<Object> valueList = dimensionValueList.get(layer);
                List<DimensionValueSet> subDimensionValueSetList = this.getDimensionValueSetList(dimensionNameList, dimensionValueList, layer + 1);
                for (Object dimensionValue : valueList) {
                    for (DimensionValueSet subDimensionValueSet : subDimensionValueSetList) {
                        DimensionValueSet dimensionValueSet = new DimensionValueSet(subDimensionValueSet);
                        dimensionValueSet.setValue(dimensionName, dimensionValue);
                        dimensionValueSetList.add(dimensionValueSet);
                    }
                }
                break block4;
            }
            if (layer != dimensionNameList.size() - 1) break block4;
            String dimensionName = dimensionNameList.get(layer);
            List<Object> valueList = dimensionValueList.get(layer);
            for (Object dimensionValue : valueList) {
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                dimensionValueSet.setValue(dimensionName, dimensionValue);
                dimensionValueSetList.add(dimensionValueSet);
            }
        }
        return dimensionValueSetList;
    }

    public String getUserNickNameById(String userId) {
        User user = this.userService.get(userId);
        return user == null ? "\u7cfb\u7edf\u7ba1\u7406\u5458" : user.getNickname();
    }

    public IFormulaRunTimeController getFormulaRunTimeController() {
        return this.formulaRunTimeController;
    }

    public IEntityMetaService getEntityMetaService() {
        return this.entityMetaService;
    }

    public Helper getHelper() {
        return this.helper;
    }
}

