/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.var.ReferRelation
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.basedata.select.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.basedata.select.bean.BaseDataAttribute;
import com.jiuqi.nr.basedata.select.bean.BaseDataLinkage;
import com.jiuqi.nr.basedata.select.bean.BaseDataSelectFilterInfo;
import com.jiuqi.nr.basedata.select.bean.BaseDataView;
import com.jiuqi.nr.basedata.select.cache.BaseDataReferMap;
import com.jiuqi.nr.basedata.select.exception.BaseDataException;
import com.jiuqi.nr.basedata.select.param.BaseDataQueryInfo;
import com.jiuqi.nr.basedata.select.service.IBaseDataIsolateConditionProvider;
import com.jiuqi.nr.basedata.select.service.IBaseDataSelectFilter;
import com.jiuqi.nr.basedata.select.service.IBaseDataSelectParamService;
import com.jiuqi.nr.basedata.select.util.BaseDataUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseDataSelectParamServiceImpl
implements IBaseDataSelectParamService {
    private static final Logger logger = LoggerFactory.getLogger(BaseDataSelectParamServiceImpl.class);
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired(required=false)
    private List<IBaseDataIsolateConditionProvider> baseDataIsolateConditionProviders;
    @Autowired(required=false)
    private List<IBaseDataSelectFilter> baseDataSelectFilters;
    @Autowired
    private BaseDataReferMap baseDataReferMap;

    /*
     * WARNING - void declaration
     */
    @Override
    public IEntityTable buildEntityTable(BaseDataQueryInfo baseDataQueryInfo, boolean ignoreIsolate, boolean readerQuery) throws BaseDataException {
        void var13_22;
        Map<String, DimensionValue> dimensionSet;
        BaseDataView baseDataView = this.getBaseDataView(baseDataQueryInfo.getEntityKey(), baseDataQueryInfo.getRowFilter(), baseDataQueryInfo.isEntityAuth());
        if (baseDataView == null) {
            throw new BaseDataException(new String[]{"\u672a\u83b7\u5f97\u5b9e\u4f53\u5b9a\u4e49"});
        }
        if (this.periodAdapter.isPeriodEntity(baseDataView.getKey())) {
            throw new BaseDataException(new String[]{"\u4e0d\u652f\u6301\u65f6\u671f\u4e3b\u4f53\u67e5\u8be2"});
        }
        BaseDataView dwBaseDataView = this.getDwBaseDataView(baseDataQueryInfo.getFormSchemeKey(), baseDataQueryInfo.getRowFilter(), baseDataQueryInfo.isEntityAuth());
        if (dwBaseDataView != null && baseDataView.getKey().equals(dwBaseDataView.getKey())) {
            baseDataView = dwBaseDataView;
        }
        ArrayList<ReferRelation> referRelations = new ArrayList<ReferRelation>();
        this.setLinkage(baseDataQueryInfo, referRelations);
        ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.iDataDefinitionRuntimeController, this.entityViewRunTimeController, baseDataQueryInfo.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setVarDimensionValueSet(BaseDataUtil.getVarDimensionValueSet(baseDataQueryInfo.getDimensionSet()));
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(baseDataView.getKey(), baseDataView.getRowFilter(), baseDataView.isEntityAuth());
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        if (baseDataQueryInfo.isDesensitized()) {
            entityQuery.maskedData();
        }
        entityQuery.sorted(baseDataQueryInfo.isSorted());
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setAuthorityOperations(baseDataQueryInfo.isReadAuth() ? AuthorityType.Read : AuthorityType.None);
        entityQuery.setMasterKeys(new DimensionValueSet());
        if (StringUtils.isNotEmpty((String)baseDataView.getRowFilter())) {
            entityQuery.setRowFilter(baseDataView.getRowFilter());
        }
        String unitKey = "";
        if (StringUtils.isNotEmpty((String)baseDataQueryInfo.getFormSchemeKey()) && baseDataQueryInfo.getDimensionSet().containsKey(dwBaseDataView.getDimensionName())) {
            unitKey = baseDataQueryInfo.getDimensionSet().get(dwBaseDataView.getDimensionName()).getValue();
        }
        if (referRelations != null && !referRelations.isEmpty()) {
            for (ReferRelation referRelation : referRelations) {
                entityQuery.addReferRelation(referRelation);
            }
        }
        if (!ignoreIsolate) {
            boolean hasExtIsolation = false;
            if (this.baseDataIsolateConditionProviders != null && this.baseDataIsolateConditionProviders.size() > 0) {
                for (IBaseDataIsolateConditionProvider baseDataIsolateConditionProvider : this.baseDataIsolateConditionProviders) {
                    String baseDataIsolateCondition = baseDataIsolateConditionProvider.getBaseDataIsolateCondition(baseDataView.getKey(), baseDataQueryInfo.getTaskKey(), baseDataQueryInfo.getDimensionSet());
                    if (!StringUtils.isNotEmpty((String)baseDataIsolateCondition)) continue;
                    entityQuery.setIsolateCondition(baseDataIsolateCondition);
                    hasExtIsolation = true;
                    break;
                }
            }
            if (StringUtils.isNotEmpty((String)unitKey) && StringUtils.isEmpty((String)executorContext.getIsolateCondition()) && !hasExtIsolation) {
                entityQuery.setIsolateCondition(unitKey);
            }
        }
        if ((dimensionSet = baseDataQueryInfo.getDimensionSet()) != null && dimensionSet.containsKey("DATATIME")) {
            String string = dimensionSet.get("DATATIME").getValue();
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(baseDataQueryInfo.getFormSchemeKey());
            String dateTime = formSchemeDefine.getDateTime();
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dateTime);
            Date versionDate = null;
            try {
                versionDate = periodProvider.getPeriodDateRegion(string)[1];
            }
            catch (ParseException e) {
                logger.error("\u57fa\u7840\u6570\u636e\u5f39\u51fa\u67e5\u8be2\u65f6\u671f\u7248\u672c\u51fa\uff01\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            entityQuery.setQueryVersionDate(versionDate);
        }
        Object var13_19 = null;
        try {
            if (readerQuery) {
                IEntityTable iEntityTable = entityQuery.executeReader((IContext)executorContext);
            } else {
                IEntityTable iEntityTable = entityQuery.executeFullBuild((IContext)executorContext);
            }
        }
        catch (Exception e) {
            logger.error("\u57fa\u7840\u6570\u636e\u5f39\u51fa\u67e5\u8be2\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new BaseDataException(new String[]{"\u83b7\u53d6\u5b9e\u4f53\u7ed3\u679c\u96c6\u51fa\u9519"});
        }
        return var13_22;
    }

    private void setLinkage(BaseDataQueryInfo baseDataQueryInfo, List<ReferRelation> referRelations) {
        List<BaseDataLinkage> baseDataLinkageList = baseDataQueryInfo.getBaseDataLinkageList();
        if (baseDataLinkageList != null && baseDataLinkageList.size() > 0) {
            String entityKey = baseDataQueryInfo.getEntityKey();
            if (baseDataLinkageList.size() == 1) {
                String preValue;
                BaseDataLinkage baseDataLinkage = baseDataLinkageList.get(0);
                if (baseDataLinkage != null && StringUtils.isNotEmpty((String)(preValue = baseDataLinkage.getPreValue()))) {
                    String preEntityKey = baseDataLinkage.getPreEntityKey();
                    if (preEntityKey.equals(entityKey)) {
                        baseDataQueryInfo.setParentKey(preValue);
                    } else {
                        if (baseDataQueryInfo.getLinkageType().equals("2")) {
                            baseDataQueryInfo.setLinkageType("3");
                        }
                        EntityViewDefine preEntityView = this.entityViewRunTimeController.buildEntityView(preEntityKey, baseDataLinkage.getPreRowFilter(), baseDataLinkage.isPreEntityAuth());
                        ReferRelation referRelation = new ReferRelation();
                        referRelation.setViewDefine(preEntityView);
                        ArrayList<String> range = new ArrayList<String>();
                        if (baseDataLinkage.isMultipleSelect() && preValue.contains(";")) {
                            String[] preValues = preValue.split(";");
                            if (preValues != null && preValues.length > 0) {
                                Collections.addAll(range, preValues);
                            }
                        } else {
                            range.add(preValue);
                        }
                        referRelation.setRange(range);
                        referRelations.add(referRelation);
                    }
                }
            } else if (baseDataLinkageList.size() > 1) {
                for (BaseDataLinkage baseDataLinkage : baseDataLinkageList) {
                    String preValue;
                    String preEntityKey;
                    if (baseDataLinkage == null || (preEntityKey = baseDataLinkage.getPreEntityKey()).equals(entityKey) || !StringUtils.isNotEmpty((String)(preValue = baseDataLinkage.getPreValue()))) continue;
                    EntityViewDefine preEntityView = this.entityViewRunTimeController.buildEntityView(preEntityKey, baseDataLinkage.getPreRowFilter(), baseDataLinkage.isPreEntityAuth());
                    ReferRelation referRelation = new ReferRelation();
                    referRelation.setViewDefine(preEntityView);
                    ArrayList<String> range = new ArrayList<String>();
                    if (baseDataLinkage.isMultipleSelect() && preValue.contains(";")) {
                        String[] preValues = preValue.split(";");
                        if (preValues != null && preValues.length > 0) {
                            Collections.addAll(range, preValues);
                        }
                    } else {
                        range.add(preValue);
                    }
                    referRelation.setRange(range);
                    referRelations.add(referRelation);
                }
            }
        }
    }

    private BaseDataView getBaseDataView(String entityKey, String rowFilter, boolean entityAuth) {
        BaseDataView baseDataView = new BaseDataView();
        if (this.periodAdapter.isPeriodEntity(entityKey)) {
            IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(entityKey);
            baseDataView.initialize(periodEntity);
        } else {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityKey);
            baseDataView.initialize(entityDefine);
        }
        baseDataView.setRowFilter(rowFilter);
        baseDataView.setEntityAuth(entityAuth);
        return baseDataView;
    }

    private BaseDataView getDwBaseDataView(String formSchemeKey, String rowFilter, boolean entityAuth) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formSchemeDefine == null) {
            return null;
        }
        BaseDataView unitBaseDataView = this.getBaseDataView(formSchemeDefine.getDw(), rowFilter, entityAuth);
        unitBaseDataView.setMasterEntity(true);
        unitBaseDataView.setKind("TABLE_KIND_ENTITY");
        IEntityModel entityModel = this.entityMetaService.getEntityModel(unitBaseDataView.getKey());
        IEntityAttribute bblxField = null;
        if (entityModel != null) {
            bblxField = entityModel.getBblxField();
        }
        unitBaseDataView.setMinusSum(bblxField != null);
        return unitBaseDataView;
    }

    @Override
    public List<String> getFilterFields(String entityKey, List<String> fieldList) {
        ArrayList<String> filterFields = new ArrayList<String>();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityKey);
        if (fieldList != null && fieldList.size() > 0) {
            fieldList = fieldList.stream().distinct().collect(Collectors.toList());
            Iterator attributes = entityModel.getAttributes();
            HashMap<String, IEntityAttribute> attributeMap = new HashMap<String, IEntityAttribute>();
            while (attributes.hasNext()) {
                IEntityAttribute attribute = (IEntityAttribute)attributes.next();
                attributeMap.put(attribute.getCode(), attribute);
            }
            if (fieldList != null && fieldList.size() > 0) {
                for (String code : fieldList) {
                    if (!attributeMap.containsKey(code)) continue;
                    filterFields.add(code);
                }
            }
            if (filterFields == null || filterFields.size() == 0) {
                return this.getDefaultFields(entityModel);
            }
        } else {
            return this.getDefaultFields(entityModel);
        }
        return filterFields;
    }

    @Override
    public List<BaseDataAttribute> getBaseDataAttributes(String entityKey, List<String> dropDownFields) {
        ArrayList<BaseDataAttribute> baseDataAttributes = new ArrayList<BaseDataAttribute>();
        ArrayList<BaseDataAttribute> sortBaseDataAttributes = new ArrayList<BaseDataAttribute>();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityKey);
        if (entityModel != null) {
            List<String> filterFieldList = this.getFilterFields(entityKey, dropDownFields);
            if (filterFieldList == null || filterFieldList.size() == 0) {
                filterFieldList = this.getDefaultFields(entityModel);
            }
            List showFields = entityModel.getShowFields();
            for (IEntityAttribute attribute : showFields) {
                BaseDataAttribute baseDataAttribute = new BaseDataAttribute();
                baseDataAttribute.setKey(attribute.getCode());
                baseDataAttribute.setTitle(attribute.getTitle());
                baseDataAttributes.add(baseDataAttribute);
            }
            if (baseDataAttributes != null && baseDataAttributes.size() > 0) {
                BaseDataAttribute baseDataAttribute;
                Map baseDataAttributesMap = baseDataAttributes.stream().collect(Collectors.toMap(BaseDataAttribute::getKey, Function.identity(), (key1, key2) -> key2, LinkedHashMap::new));
                if (filterFieldList != null && filterFieldList.size() > 0) {
                    for (int i = 0; i < filterFieldList.size(); ++i) {
                        String fieldCode = filterFieldList.get(i);
                        if (!baseDataAttributesMap.containsKey(fieldCode)) continue;
                        baseDataAttribute = (BaseDataAttribute)baseDataAttributesMap.get(fieldCode);
                        sortBaseDataAttributes.add(baseDataAttribute);
                        baseDataAttributesMap.remove(fieldCode);
                    }
                }
                for (String key : baseDataAttributesMap.keySet()) {
                    baseDataAttribute = (BaseDataAttribute)baseDataAttributesMap.get(key);
                    sortBaseDataAttributes.add(baseDataAttribute);
                }
            }
        }
        return sortBaseDataAttributes;
    }

    private List<String> getDefaultFields(IEntityModel entityModel) {
        ArrayList<String> fieldList = new ArrayList<String>();
        if (entityModel != null) {
            IEntityAttribute nameField = entityModel.getNameField();
            fieldList.add(nameField.getCode());
        }
        return fieldList;
    }

    @Override
    public Map<String, String> getReferEntityIdMap(String entityKey, List<String> fieldList) {
        List<IEntityRefer> entityRefer;
        HashMap<String, String> referEntityIdMap = new HashMap<String, String>();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityKey);
        boolean existReferEntity = false;
        for (String code : fieldList) {
            IEntityAttribute attribute = entityModel.getAttribute(code);
            if (attribute == null || !StringUtils.isNotEmpty((String)attribute.getReferTableID())) continue;
            existReferEntity = true;
            break;
        }
        if (existReferEntity && (entityRefer = this.baseDataReferMap.getEntityReferList(entityKey)) != null && entityRefer.size() > 0) {
            for (String code : fieldList) {
                entityRefer.forEach(refer -> {
                    if (refer.getOwnField().equals(code)) {
                        referEntityIdMap.put(code, refer.getReferEntityId());
                    }
                });
            }
        }
        return referEntityIdMap;
    }

    @Override
    public List<IBaseDataSelectFilter> getEnableBaseDataFilterList(List<BaseDataSelectFilterInfo> baseDataSelectFilterInfos) {
        ArrayList<IBaseDataSelectFilter> baseDataSelectFilterList = new ArrayList<IBaseDataSelectFilter>();
        for (BaseDataSelectFilterInfo baseDataSelectFilterInfo : baseDataSelectFilterInfos) {
            String filterName = baseDataSelectFilterInfo.getFilterName();
            if (!StringUtils.isNotEmpty((String)filterName)) continue;
            for (IBaseDataSelectFilter baseDataSelectFilter : this.baseDataSelectFilters) {
                String filterName2 = baseDataSelectFilter.getFilterName();
                if (!StringUtils.isNotEmpty((String)filterName) || !filterName.equals(filterName2)) continue;
                baseDataSelectFilter.initFilterParams(baseDataSelectFilterInfo.getFilterParams());
                baseDataSelectFilterList.add(baseDataSelectFilter);
            }
        }
        return baseDataSelectFilterList;
    }
}

