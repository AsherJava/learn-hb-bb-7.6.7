/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.summary.utils;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class EntityQueryUtil {
    private static final Logger logger = LoggerFactory.getLogger(EntityQueryUtil.class);

    public static IEntityTable getEntityTable(String entityId) throws SummaryCommonException {
        return EntityQueryUtil.getEntityTable(entityId, null);
    }

    public static IEntityTable getEntityTable(String entityId, String rowFilter) throws SummaryCommonException {
        return EntityQueryUtil.getEntityTable(entityId, rowFilter, null, null);
    }

    public static IEntityTable getEntityTable(String entityId, String rowFilter, String period, Date endDate) throws SummaryCommonException {
        IEntityQuery entityQuery = EntityQueryUtil.getEntityQuery(entityId, rowFilter, period, endDate);
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
        ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
        try {
            return entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SummaryCommonException(SummaryErrorEnum.ENTITY_QUERY_FAILED, entityId, e.getMessage());
        }
    }

    public static List<IEntityRow> getEntityRows(IEntityTable entityTable, String entityKeyData, boolean all) {
        if (all) {
            return entityTable.getAllRows();
        }
        if (StringUtils.hasLength(entityKeyData)) {
            return entityTable.getChildRows(entityKeyData);
        }
        return entityTable.getRootRows();
    }

    public static List<IEntityRow> getEntityRows(String entityId, String entityKeyData, String rowFilter, boolean all) throws SummaryCommonException {
        IEntityTable entityTable = EntityQueryUtil.getEntityTable(entityId, rowFilter);
        return EntityQueryUtil.getEntityRows(entityTable, entityKeyData, all);
    }

    public static List<IEntityRow> getEntityRows(String entityId, List<String> entityKeyDatas) throws SummaryCommonException {
        IEntityTable entityTable = EntityQueryUtil.getEntityTable(entityId);
        LinkedHashSet<String> entityKeySet = new LinkedHashSet<String>(entityKeyDatas);
        Map entityRowMap = entityTable.findByEntityKeys(entityKeySet);
        return entityRowMap.values().stream().sorted(Comparator.comparingInt(pre -> entityKeyDatas.indexOf(pre.getEntityKeyData()))).collect(Collectors.toList());
    }

    public static List<IEntityRow> getEntityRows(String entityId, List<String> entityKeyDatas, Date endDate) throws SummaryCommonException {
        IEntityTable entityTable = EntityQueryUtil.getEntityTable(entityId, null, null, endDate);
        LinkedHashSet<String> entityKeySet = new LinkedHashSet<String>(entityKeyDatas);
        Map entityRowMap = entityTable.findByEntityKeys(entityKeySet);
        return entityRowMap.values().stream().sorted(Comparator.comparingInt(pre -> entityKeyDatas.indexOf(pre.getEntityKeyData()))).collect(Collectors.toList());
    }

    public static IEntityTable getEntityTableByLevel(IEntityMetaService entityMetaService, IEntityViewRunTimeController viewAdapter, IEntityDataService dataService, IDataDefinitionRuntimeController tbRtCtl, String entityId, int level) {
        return null;
    }

    private static IEntityQuery getEntityQuery(String entityId, String rowFilter, String period, Date endDate) {
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringBeanUtils.getBean(IEntityViewRunTimeController.class);
        IEntityDataService entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
        IEntityDefine entityDefine = entityMetaService.queryEntity(entityId);
        EntityViewDefine entityView = entityViewRunTimeController.buildEntityView(entityDefine.getId(), rowFilter, true);
        IEntityQuery query = entityDataService.newEntityQuery();
        if (StringUtils.hasLength(period)) {
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue("DATATIME", (Object)period);
            query.setMasterKeys(dimensionValueSet);
        }
        query.sorted(true);
        query.setEntityView(entityView);
        query.setAuthorityOperations(AuthorityType.Read);
        query.setQueryVersionDate(endDate == null ? Consts.DATE_VERSION_FOR_ALL : endDate);
        return query;
    }
}

