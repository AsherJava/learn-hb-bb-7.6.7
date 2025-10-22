/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.etl.service.internal;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.etl.service.internal.ETLFilterParam;
import com.jiuqi.nr.etl.service.internal.EtlAsyncTaskErrorException;
import com.jiuqi.nr.etl.service.internal.QueryEntity;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParamFilter {
    private static final Logger log = LoggerFactory.getLogger(ParamFilter.class);
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private QueryEntity queryEntity;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private IJtableParamService jtableParamService;

    public ETLFilterParam paramFiltration(String period, String taskKey, String[] unitIds, String[] formKeys) {
        String entityKeyData;
        String code;
        List<IEntityRow> entityData;
        List schemePeriodLinkDefineList;
        ETLFilterParam etlFilterParam = new ETLFilterParam(period);
        try {
            schemePeriodLinkDefineList = this.runTimeViewController.querySchemePeriodLinkByTask(taskKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        String formSchemeKey = new String();
        if (schemePeriodLinkDefineList != null && !schemePeriodLinkDefineList.isEmpty()) {
            for (SchemePeriodLinkDefine schemePeriodLinkDefine : schemePeriodLinkDefineList) {
                if (!schemePeriodLinkDefine.getPeriodKey().equals(period)) continue;
                formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
            }
        }
        LinkedHashSet<String> unitidParam = new LinkedHashSet<String>();
        LinkedHashSet<String> unidCodeParam = new LinkedHashSet<String>();
        LinkedHashSet<String> unitIdList = new LinkedHashSet<String>();
        if (unitIds == null || unitIds.length == 0 || unitIds.length == 1 && unitIds[0].equals("")) {
            entityData = this.queryEntity.getEntityData(formSchemeKey, period);
            for (IEntityRow iEntityRow : entityData) {
                code = iEntityRow.getCode();
                entityKeyData = iEntityRow.getEntityKeyData();
                unitidParam.add(entityKeyData);
                unidCodeParam.add(code);
            }
        } else {
            unitIdList.addAll(Arrays.asList(unitIds));
            entityData = this.queryEntity.getEntityData(formSchemeKey, period);
            for (IEntityRow iEntityRow : entityData) {
                code = iEntityRow.getCode();
                entityKeyData = iEntityRow.getEntityKeyData();
                if (!unitIdList.contains(entityKeyData)) continue;
                unitidParam.add(entityKeyData);
                unidCodeParam.add(code);
            }
        }
        etlFilterParam.setUnitid(unitidParam);
        etlFilterParam.setUnidCode(unidCodeParam);
        StringBuffer unitid = new StringBuffer();
        for (String str : unitIds) {
            unitid.append(str).append(",");
        }
        String unitidStr = unitid.toString();
        if (unitidStr.endsWith(",")) {
            unitidStr = unitidStr.substring(0, unitidStr.length() - 1);
        }
        HashSet<Object> formSet = new HashSet<Object>();
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(formSchemeKey);
        String entityName = targetEntityInfo.getDimensionName();
        List formKeyList = this.runTimeViewController.queryAllFormKeysByFormScheme(formSchemeKey);
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue periodDimensionValue = new DimensionValue();
        periodDimensionValue.setName("DATATIME");
        periodDimensionValue.setType(0);
        periodDimensionValue.setValue(period);
        dimensionSet.put("DATATIME", periodDimensionValue);
        DimensionValue dwDimensionValue = new DimensionValue();
        dwDimensionValue.setName(entityName);
        dwDimensionValue.setType(0);
        dwDimensionValue.setValue(unitidStr);
        dimensionSet.put(entityName, dwDimensionValue);
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(taskKey, formSchemeKey);
        DimensionCollection dimCollection = DimensionValueSetUtil.buildDimensionCollection(dimensionSet, (String)formSchemeKey);
        IBatchAccessResult batchAccessResult = dataAccessService.getVisitAccess(dimCollection, formKeyList);
        List dimCollectionList = dimCollection.getDimensionCombinations();
        for (DimensionCombination dimensionComin : dimCollectionList) {
            for (Object key : formKeyList) {
                IAccessResult accessResult = batchAccessResult.getAccess(dimensionComin, (String)key);
                try {
                    if (!accessResult.haveAccess()) continue;
                    formSet.add(key);
                }
                catch (Exception e) {
                    throw new EtlAsyncTaskErrorException(e.getMessage());
                }
            }
        }
        List formList = this.runTimeViewController.queryFormsById(new ArrayList(formSet));
        LinkedHashSet<String> formCode = new LinkedHashSet<String>();
        LinkedHashSet<String> formKeySet = new LinkedHashSet<String>();
        if (formKeys == null || formKeys.length == 0 || formKeys.length == 1 && formKeys[0].equals("")) {
            for (FormDefine formDefine : formList) {
                formCode.add(formDefine.getFormCode());
                formKeySet.add(formDefine.getKey());
            }
        } else {
            LinkedHashSet<String> formKeyLinkedSet = new LinkedHashSet<String>();
            for (String key : formKeys) {
                formKeyLinkedSet.add(key);
            }
            for (FormDefine formDefine : formList) {
                if (!formKeyLinkedSet.contains(formDefine.getKey())) continue;
                formCode.add(formDefine.getFormCode());
                formKeySet.add(formDefine.getKey());
            }
        }
        etlFilterParam.setFormCode(formCode);
        etlFilterParam.setFormKeySet(formKeySet);
        return etlFilterParam;
    }

    private DimensionValueSet getEntity(String formSchemeKey, List<String> unitIdList) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            String mainDimName = this.entityMetaService.getDimensionName(formScheme.getDw());
            dimensionValueSet.setValue(mainDimName, unitIdList);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dimensionValueSet;
    }

    public HashSet<FormData> getFormList(String formSchemeKey, String taskKey, String period, String unitid) {
        HashMap<String, DimensionValue> dim = new HashMap<String, DimensionValue>();
        DimensionValue dimensionValue = null;
        String unitStr = "";
        if (unitid != null && unitid.length() > 0) {
            String[] unitSplit;
            dimensionValue = new DimensionValue();
            for (String unit : unitSplit = unitid.split(",")) {
                unitStr = unitStr + unit + ",";
            }
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            String mainDimName = "";
            mainDimName = this.entityMetaService.getDimensionName(formScheme.getDw());
            dimensionValue.setName(mainDimName);
            dimensionValue.setValue(unitStr);
            dim.put(mainDimName, dimensionValue);
        }
        if (period != null) {
            dimensionValue = new DimensionValue();
            dimensionValue.setName("DATATIME");
            dimensionValue.setValue(period);
            dim.put("DATATIME", dimensionValue);
        }
        return this.queryEntity.getFormList(formSchemeKey, taskKey, dim, "formdefine");
    }
}

