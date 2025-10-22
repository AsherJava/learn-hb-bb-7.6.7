/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.datacheckcommon.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.datacheckcommon.helper.EntityQueryHelper;
import com.jiuqi.nr.datacheckcommon.param.DataCheckDimInfo;
import com.jiuqi.nr.datacheckcommon.param.QueryDimParam;
import com.jiuqi.nr.datacheckcommon.service.IDataCheckCommonService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DataCheckCommonServiceImpl
implements IDataCheckCommonService {
    private static final Logger logger = LoggerFactory.getLogger(DataCheckCommonServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    private IFormSchemeService formSchemeService;

    @Override
    public DataCheckDimInfo queryDims(QueryDimParam param) throws Exception {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskKey());
        String masterDimName = this.entityMetaService.queryEntity(taskDefine.getDw()).getDimensionName();
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue dwDimensionValue = new DimensionValue();
        dwDimensionValue.setName(masterDimName);
        dwDimensionValue.setValue(String.join((CharSequence)";", param.getOrgCode()));
        dimensionSet.put(masterDimName, dwDimensionValue);
        DimensionValue dataTimeDim = new DimensionValue();
        dataTimeDim.setName("DATATIME");
        dataTimeDim.setValue(param.getPeriod());
        dimensionSet.put("DATATIME", dataTimeDim);
        Map<String, String> dims = param.getDims();
        for (Map.Entry<String, String> dimNameValue : dims.entrySet()) {
            DimensionValue dimensionValue = new DimensionValue();
            dimensionValue.setName(dimNameValue.getKey());
            dimensionValue.setValue(dimNameValue.getValue());
            dimensionSet.put(dimNameValue.getKey(), dimensionValue);
        }
        DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(dimensionSet, param.getFormSchemeKey());
        return this.queryDims(param.getTaskKey(), param.getFormSchemeKey(), dimensionCollection);
    }

    @Override
    public DataCheckDimInfo queryDims(String taskKey, String formSchemeKey, DimensionCollection dims) throws Exception {
        DataCheckDimInfo result = new DataCheckDimInfo();
        try {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
            String dimsStr = taskDefine.getDims();
            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)dimsStr)) {
                String[] dimArrays;
                String masterDimName = this.entityMetaService.queryEntity(taskDefine.getDw()).getDimensionName();
                String periodDimValue = (String)dims.combineDim().getValue("DATATIME");
                List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
                HashMap<String, DataDimension> dataDimensionMap = new HashMap<String, DataDimension>();
                IEntityModel entityModel = null;
                for (DataDimension dataDimension : dataSchemeDimension) {
                    dataDimensionMap.put(dataDimension.getDimKey(), dataDimension);
                    if (DimensionType.UNIT != dataDimension.getDimensionType()) continue;
                    entityModel = this.entityMetaService.getEntityModel(dataDimension.getDimKey());
                }
                HashMap<String, Map<String, Boolean>> dimNameEntityIdExistCurrencyAttributeMap = new HashMap<String, Map<String, Boolean>>();
                HashMap<String, String> dimNameTitleMap = new HashMap<String, String>();
                HashMap<String, Boolean> dimNameIsShowMap = new HashMap<String, Boolean>();
                HashMap<String, IEntityTable> dimNameTableMap = new HashMap<String, IEntityTable>();
                for (String dimArra : dimArrays = dimsStr.split(";")) {
                    HashMap<String, Boolean> entityIdExistCurrencyAttributesMap;
                    String string;
                    IEntityDefine dimEntityDefine = this.entityMetaService.queryEntity(dimArra);
                    if (null == dimEntityDefine) continue;
                    String string2 = string = null == dataDimensionMap.get(dimArra) ? "" : ((DataDimension)dataDimensionMap.get(dimArra)).getDimAttribute();
                    if (null != entityModel && StringUtils.hasText(string)) {
                        IEntityAttribute attribute = entityModel.getAttribute(string);
                        dimNameIsShowMap.put(dimEntityDefine.getDimensionName(), null == attribute || attribute.isMultival());
                        if (null != attribute && !attribute.isMultival()) continue;
                    }
                    IEntityQuery query = this.entityQueryHelper.getDimEntityQuery(dimArra, periodDimValue);
                    IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(query, formSchemeKey, true);
                    if ("MD_CURRENCY@BASE".equals(dimArra)) {
                        entityIdExistCurrencyAttributesMap = new HashMap<String, Boolean>();
                        entityIdExistCurrencyAttributesMap.put(dimArra, this.formSchemeService.existCurrencyAttributes(formSchemeKey));
                        dimNameEntityIdExistCurrencyAttributeMap.put(dimEntityDefine.getDimensionName(), entityIdExistCurrencyAttributesMap);
                    } else {
                        entityIdExistCurrencyAttributesMap = new HashMap();
                        entityIdExistCurrencyAttributesMap.put(dimArra, false);
                        dimNameEntityIdExistCurrencyAttributeMap.put(dimEntityDefine.getDimensionName(), entityIdExistCurrencyAttributesMap);
                    }
                    dimNameTableMap.put(dimEntityDefine.getDimensionName(), entityTable);
                    dimNameTitleMap.put(dimEntityDefine.getDimensionName(), dimEntityDefine.getTitle());
                }
                result.setDimNameEntityIdExistCurrencyAttributeMap(dimNameEntityIdExistCurrencyAttributeMap);
                result.setDimNameTitleMap(dimNameTitleMap);
                result.setDimNameIsShowMap(dimNameIsShowMap);
                LinkedHashMap<String, LinkedHashMap<String, String>> dimRange = new LinkedHashMap<String, LinkedHashMap<String, String>>();
                boolean providerCurrency = false;
                if ("PROVIDER_BASECURRENCY".equals(dims.combineDim().getValue("MD_CURRENCY"))) {
                    providerCurrency = true;
                    LinkedHashMap<String, String> dimValues = new LinkedHashMap<String, String>();
                    dimValues.put("PROVIDER_BASECURRENCY", "\u672c\u4f4d\u5e01");
                    dimRange.put("MD_CURRENCY", dimValues);
                } else if ("PROVIDER_PBASECURRENCY".equals(dims.combineDim().getValue("MD_CURRENCY"))) {
                    providerCurrency = true;
                    LinkedHashMap<String, String> dimValues = new LinkedHashMap<String, String>();
                    dimValues.put("PROVIDER_PBASECURRENCY", "\u4e0a\u7ea7\u672c\u4f4d\u5e01");
                    dimRange.put("MD_CURRENCY", dimValues);
                }
                HashMap<String, Set> dimNameValues = new HashMap<String, Set>();
                List dimensionCombinations = dims.getDimensionCombinations();
                for (DimensionCombination dimensionCombination : dimensionCombinations) {
                    DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
                    for (int i = 0; i < dimensionValueSet.size(); ++i) {
                        String dimValue;
                        String dimName = dimensionValueSet.getName(i);
                        if ("MD_CURRENCY".equals(dimName) && providerCurrency || "DATATIME".equals(dimName) || masterDimName.equals(dimName) || "ADJUST".equals(dimName) || null == (dimValue = (String)dimensionValueSet.getValue(dimName))) continue;
                        Set dimValues = dimNameValues.computeIfAbsent(dimName, k -> new HashSet());
                        dimValues.add(dimValue);
                    }
                }
                for (Map.Entry entry : dimNameValues.entrySet()) {
                    if (null == dimNameTableMap.get(entry.getKey()) || null != dimNameIsShowMap.get(entry.getKey()) && !((Boolean)dimNameIsShowMap.get(entry.getKey())).booleanValue()) continue;
                    Map dimValueRowMap = ((IEntityTable)dimNameTableMap.get(entry.getKey())).findByEntityKeys((Set)entry.getValue());
                    List<Map.Entry<String, IEntityRow>> sortDimValueRowMap = this.sortDimValueRowMap(dimValueRowMap);
                    LinkedHashMap dimValues = dimRange.computeIfAbsent((String)entry.getKey(), (Function<String, LinkedHashMap<String, String>>)((Function<String, LinkedHashMap>)k -> new LinkedHashMap()));
                    for (Map.Entry<String, IEntityRow> dimValueRow : sortDimValueRowMap) {
                        dimValues.put(dimValueRow.getKey(), dimValueRow.getValue().getTitle());
                    }
                }
                result.setDimRange(dimRange);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return result;
    }

    public List<Map.Entry<String, IEntityRow>> sortDimValueRowMap(Map<String, IEntityRow> dimValueRowMap) {
        ArrayList<Map.Entry<String, IEntityRow>> entryList = new ArrayList<Map.Entry<String, IEntityRow>>(dimValueRowMap.entrySet());
        entryList.sort(new Comparator<Map.Entry<String, IEntityRow>>(){

            @Override
            public int compare(Map.Entry<String, IEntityRow> o1, Map.Entry<String, IEntityRow> o2) {
                return ((BigDecimal)o1.getValue().getEntityOrder()).compareTo((BigDecimal)o2.getValue().getEntityOrder());
            }
        });
        return entryList;
    }
}

