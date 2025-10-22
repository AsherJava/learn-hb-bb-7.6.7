/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.batch.summary.service.targetdim.ConditionTargetDimProvider
 *  com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProviderFactoryImpl
 *  com.jiuqi.nr.batch.summary.service.targetdim.TargetRangeUnitProvider
 *  com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRow
 *  com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowProvider
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.enumeration.ConditionValueType
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.batch.gather.gzw.service.provider;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.batch.gather.gzw.service.dao.IGatherEntityCodeMappingDao;
import com.jiuqi.nr.batch.gather.gzw.service.entity.GatherEntityCodeMapping;
import com.jiuqi.nr.batch.gather.gzw.service.executor.EntityExecutor;
import com.jiuqi.nr.batch.summary.service.targetdim.ConditionTargetDimProvider;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProviderFactoryImpl;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetRangeUnitProvider;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRow;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowProvider;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.enumeration.ConditionValueType;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ConditionTargetDimGZWProvider
extends ConditionTargetDimProvider {
    private IGatherEntityCodeMappingDao gatherEntityCodeMappingDao;
    private IDataAccessServiceProvider dataAccessServiceProvider;

    public ConditionTargetDimGZWProvider(SummaryScheme summaryScheme, CustomConditionRowProvider conditionRowProvider, TargetRangeUnitProvider rangeUnitProvider, TargetDimProviderFactoryImpl wrapper, IGatherEntityCodeMappingDao gatherEntityCodeMappingDao) {
        super(summaryScheme, conditionRowProvider, rangeUnitProvider, wrapper);
        this.gatherEntityCodeMappingDao = gatherEntityCodeMappingDao;
        this.dataAccessServiceProvider = (IDataAccessServiceProvider)SpringBeanUtils.getBean(IDataAccessServiceProvider.class);
    }

    public List<String> getTargetDims(String period) {
        return this.conditionRowProvider.getAllRows().stream().map(row -> EntityExecutor.getGatherEntityCode(this.summaryScheme, row.getCode())).filter(gatherEntityCode -> this.isWriteable((String)gatherEntityCode, period)).collect(Collectors.toList());
    }

    public List<String> getEntityRowKeys(String period, String targetDimKey) {
        String filterExpression = this.summaryScheme.getRangeUnit().getExpression();
        Optional<GatherEntityCodeMapping> entityCodeMapping = this.gatherEntityCodeMappingDao.queryCodeMapping(this.summaryScheme.getKey(), targetDimKey, this.summaryScheme.getTask(), period).stream().filter(mapping -> mapping.getGatherSchemeKey().equals(this.summaryScheme.getKey())).findFirst();
        if (!entityCodeMapping.isPresent()) {
            return new ArrayList<String>();
        }
        CustomConditionRow targetDimRow = this.conditionRowProvider.findRow(entityCodeMapping.get().getCustomizedConditionCode());
        ConditionValueType valueType = targetDimRow.getValue().getValueType();
        List<String> customConditionEntities = new ArrayList<String>();
        switch (valueType) {
            case UNITS: {
                customConditionEntities = targetDimRow.getValue().getCheckList();
                break;
            }
            case EXPRESSION: {
                customConditionEntities = this.getEntityRow(targetDimRow.getValue().getExpression(), period).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            }
        }
        if (filterExpression == null || filterExpression.isEmpty()) {
            return customConditionEntities;
        }
        Set filterEntities = this.getEntityRow(filterExpression, period).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet());
        return customConditionEntities.stream().filter(filterEntities::contains).collect(Collectors.toList());
    }

    public List<IEntityRow> getEntityRow(String expression, String period) {
        if (StringUtils.isNotEmpty((String)expression)) {
            IEntityQuery query = this.wrapper.newEntityQuery(this.summaryScheme.getTask(), period);
            query.setExpression(expression);
            ExecutorContext queryContext = this.wrapper.newEntityQueryContext(this.summaryScheme.getTask(), period);
            queryContext.setVarDimensionValueSet(query.getMasterKeys());
            return this.wrapper.executeWithReader(query, (IContext)queryContext).getAllRows();
        }
        return new ArrayList<IEntityRow>();
    }

    public boolean isWriteable(String mdCode, String period) {
        HashSet<String> ignoreItems = new HashSet<String>();
        ignoreItems.add("BATCH_GATHER_GZW_FORM_RWA");
        ignoreItems.add("formCondition");
        FormSchemeDefine formSchemeDefine = this.wrapper.queryFormSchemeDefine(this.summaryScheme.getTask(), period);
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(this.summaryScheme.getTask(), formSchemeDefine.getKey(), ignoreItems);
        Optional formDefine = this.wrapper.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeDefine.getKey()).stream().findFirst();
        if (!formDefine.isPresent()) {
            throw new RuntimeException("\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e0b\u8868\u5355\u4e0d\u5b58\u5728!");
        }
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
        builder.setDWValue("MD_ORG", formSchemeDefine.getDw(), (Object)mdCode);
        builder.setValue("DATATIME", formSchemeDefine.getDateTime(), (Object)period);
        DimensionCombination masterKey = builder.getCombination();
        IAccessResult writeable = dataAccessService.writeable(masterKey, ((FormDefine)formDefine.get()).getKey());
        try {
            return writeable.haveAccess();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

