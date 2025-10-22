/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceHelper
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 *  com.jiuqi.nr.unit.treecommon.utils.DateUtils
 *  javax.annotation.Resource
 */
package com.jiuiqi.nr.unit.treebase.context;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.context.UnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceID;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceHelper;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import com.jiuqi.nr.unit.treecommon.utils.DateUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UnitTreeContextBuilder
implements IUnitTreeContextBuilder {
    @Resource
    public IEntityMetaService metaService;
    @Resource
    public IPeriodEntityAdapter periodAdapter;
    @Resource
    public com.jiuqi.nr.definition.controller.IRunTimeViewController rtViewService;
    @Resource
    public IUnitTreeDataSourceID unitTreeDataSourceID;
    @Resource
    private IconSourceHelper iconSourceHelper;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    @Resource
    private IRunTimeViewController apiRunTimeCtrl;

    @Override
    public UnitTreeContext createTreeContext(UnitTreeContextData contextData) {
        FormSchemeDefine formScheme = this.getFormScheme(contextData);
        UnitTreeContext context = new UnitTreeContext(contextData);
        context.setFormScheme(formScheme);
        context.setVersionDate(DateUtils.stringToDate((String)contextData.getVersionDateStr()));
        context.setTaskDefine(this.getTaskDefine(context, contextData.getTaskId()));
        context.setPeriodEntity(this.getPeriodEntity(context, contextData.getPeriodEntityId()));
        this.setQueryEntityInfo(context, contextData);
        context.setDataSourceId(this.unitTreeDataSourceID.getDataSourceID(context));
        return context;
    }

    @Override
    public IUnitTreeContext createTreeContext(IUnitTreeContext oriContext, IUnitTreeDataSource dataSource) {
        UnitTreeContext context = (UnitTreeContext)oriContext;
        IUnitTreeEntityRowProvider entityRowProvider = dataSource.getUnitTreeEntityRowProvider(context);
        IconSourceProvider iconSourceProvider = this.iconSourceHelper.getIconProvider(dataSource.getSourceId(), dataSource.getNodeIconSource(context));
        context.setIconProvider(iconSourceProvider);
        context.setActionNode(this.getActionNode(context, entityRowProvider));
        return context;
    }

    private IBaseNodeData getActionNode(IUnitTreeContext ctx, IUnitTreeEntityRowProvider entityRowProvider) {
        IBaseNodeData actionNode = ctx.getITreeContext().getActionNode();
        if (this.contextWrapper.hasDimGroupConfig(ctx.getTaskDefine())) {
            return actionNode;
        }
        if (actionNode != null && StringUtils.isNotEmpty((String)actionNode.getKey()) && entityRowProvider != null && entityRowProvider.findEntityRow(actionNode) != null) {
            return actionNode;
        }
        return this.getActionNodeFromDims(ctx, entityRowProvider);
    }

    private IBaseNodeData getActionNodeFromDims(IUnitTreeContext ctx, IUnitTreeEntityRowProvider entityRowProvider) {
        DimensionValue dimensionValue;
        BaseNodeDataImpl actionNode = null;
        Map<String, DimensionValue> dimValueSet = ctx.getDimValueSet();
        if (dimValueSet != null && (dimensionValue = dimValueSet.get(ctx.getEntityDefine().getDimensionName())) != null && StringUtils.isNotEmpty((String)dimensionValue.getValue())) {
            actionNode = new BaseNodeDataImpl();
            actionNode.setKey(dimensionValue.getValue());
            IEntityRow entityRow = entityRowProvider.findEntityRow((IBaseNodeData)actionNode);
            if (entityRow == null) {
                actionNode = null;
            } else {
                actionNode.setCode(entityRow.getCode());
                actionNode.setTitle(entityRow.getTitle());
            }
        }
        return actionNode;
    }

    private void setQueryEntityInfo(UnitTreeContext context, UnitTreeContextData contextData) {
        String entityId = contextData.getEntityId();
        if (contextData.isMainDimQuery() && StringUtils.isNotEmpty((String)entityId)) {
            IEntityDefine entityDefine = this.metaService.queryEntity(entityId);
            context.setEntityDefine(entityDefine);
            context.setEntityQueryPloy(IEntityQueryPloy.MAIN_DIM_QUERY);
            return;
        }
        FormSchemeDefine formScheme = context.getFormScheme();
        if (formScheme != null) {
            Set<Object> entityIds = new HashSet();
            List taskOrgLinkDefines = this.apiRunTimeCtrl.listTaskOrgLinkByTask(formScheme.getTaskKey());
            if (taskOrgLinkDefines != null) {
                entityIds = taskOrgLinkDefines.stream().map(TaskOrgLinkDefine::getEntity).collect(Collectors.toSet());
            }
            String dims = formScheme.getDims();
            if (StringUtils.isNotEmpty((String)entityId) && dims != null && dims.contains(entityId) && !entityIds.contains(entityId)) {
                IEntityDefine entityDefine = this.metaService.queryEntity(entityId);
                context.setEntityDefine(entityDefine);
                context.setEntityQueryPloy(IEntityQueryPloy.SCENES_QUERY);
                return;
            }
            DsContext dsContext = DsContextHolder.getDsContext();
            if (StringUtils.isEmpty((String)entityId) || entityIds.contains(entityId) || entityIds.contains(dsContext.getContextEntityId())) {
                IEntityDefine entityDefine = StringUtils.isNotEmpty((String)dsContext.getContextEntityId()) ? this.metaService.queryEntity(dsContext.getContextEntityId()) : this.metaService.queryEntity(formScheme.getDw());
                context.setEntityDefine(entityDefine);
                context.setEntityQueryPloy(IEntityQueryPloy.MAIN_DIM_QUERY);
                return;
            }
        }
        if (StringUtils.isNotEmpty((String)entityId)) {
            IEntityDefine entityDefine = this.metaService.queryEntity(entityId);
            context.setEntityDefine(entityDefine);
            context.setEntityQueryPloy(IEntityQueryPloy.ENTITY_QUERY);
            return;
        }
        if (StringUtils.isNotEmpty((String)contextData.getTaskId())) {
            TaskDefine taskDefine = this.rtViewService.queryTaskDefine(contextData.getTaskId());
            IEntityDefine entityDefine = this.metaService.queryEntity(taskDefine.getDw());
            context.setEntityDefine(entityDefine);
            context.setEntityQueryPloy(IEntityQueryPloy.ENTITY_QUERY);
            return;
        }
        throw new UnitTreeRuntimeException("\u65e0\u6548\u7684\u5b9e\u4f53ID!");
    }

    private FormSchemeDefine getFormScheme(UnitTreeContextData contextData) {
        FormSchemeDefine formSchemeDefine = null;
        if (StringUtils.isNotEmpty((String)contextData.getFormScheme())) {
            formSchemeDefine = this.rtViewService.getFormScheme(contextData.getFormScheme());
        }
        if (formSchemeDefine == null && StringUtils.isNotEmpty((String)contextData.getTaskId()) && StringUtils.isNotEmpty((String)contextData.getPeriod())) {
            try {
                SchemePeriodLinkDefine schemePeriodLinkDefine = this.rtViewService.querySchemePeriodLinkByPeriodAndTask(contextData.getPeriod(), contextData.getTaskId());
                if (schemePeriodLinkDefine != null) {
                    formSchemeDefine = this.rtViewService.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
                }
            }
            catch (Exception e) {
                LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            }
        }
        return formSchemeDefine;
    }

    private TaskDefine getTaskDefine(UnitTreeContext context, String taskKey) {
        if (context.getFormScheme() != null) {
            return this.rtViewService.queryTaskDefine(context.getFormScheme().getTaskKey());
        }
        if (StringUtils.isNotEmpty((String)taskKey)) {
            return this.rtViewService.queryTaskDefine(taskKey);
        }
        return null;
    }

    private IPeriodEntity getPeriodEntity(UnitTreeContext context, String periodEntityId) {
        FormSchemeDefine formScheme = context.getFormScheme();
        if (formScheme != null) {
            return this.periodAdapter.getPeriodEntity(formScheme.getDateTime());
        }
        TaskDefine taskDefine = context.getTaskDefine();
        if (taskDefine != null) {
            return this.periodAdapter.getPeriodEntity(taskDefine.getDateTime());
        }
        if (StringUtils.isNotEmpty((String)periodEntityId)) {
            return this.periodAdapter.getPeriodEntity(periodEntityId);
        }
        return null;
    }
}

