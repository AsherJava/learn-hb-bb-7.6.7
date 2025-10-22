/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext$CountModel
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 *  com.jiuqi.nr.entity.engine.var.TreeRangeQuery
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 *  javax.annotation.Resource
 */
package com.jiuiqi.nr.unit.treebase.entity.query;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.query.NormalEntityRowQuery;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.TreeRangeQuery;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component(value="scenes-entity-row-query")
public class ScenesEntityRowQuery
implements IUnitTreeEntityDataQuery {
    @Resource
    private IEntityDataService dataService;
    @Resource
    private IRunTimeViewController viewController;
    @Resource
    private IEntityViewRunTimeController viewAdapter;
    @Resource
    private IDataDefinitionRuntimeController tbRtCtl;
    @Resource
    private NormalEntityRowQuery entityRowQuery;

    @Override
    public IEntityTable makeIEntityTable(IUnitTreeContext context) {
        if (IEntityQueryPloy.SCENES_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            return this.makeIEntityTable(query, (IContext)executorContext);
        }
        return this.entityRowQuery.makeIEntityTable(context);
    }

    @Override
    public IEntityTable makeIEntityTable(IUnitTreeContext context, boolean isCountChaE, boolean isOnlyCountLeaf) {
        if (IEntityQueryPloy.SCENES_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context);
            if (!isCountChaE) {
                query.setExpression(IUnitTreeEntityDataQuery.buildFilters(context, "BBLX != '1' OR BBLX IS NULL"));
            }
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            executorContext.setCountModel(isOnlyCountLeaf ? ExecutorContext.CountModel.LEAF_ONLY : ExecutorContext.CountModel.ALL);
            return this.makeIEntityTable(query, (IContext)executorContext);
        }
        return this.entityRowQuery.makeIEntityTable(context, isCountChaE, isOnlyCountLeaf);
    }

    @Override
    public IEntityTable makeIEntityTable(IUnitTreeContext context, List<String> rowKeys) {
        if (IEntityQueryPloy.SCENES_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context, rowKeys);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            return this.makeIEntityTable(query, (IContext)executorContext);
        }
        return this.entityRowQuery.makeIEntityTable(context, rowKeys);
    }

    @Override
    public IEntityTable makeIEntityTable(IUnitTreeContext context, String ... filters) {
        if (IEntityQueryPloy.SCENES_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context, filters);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            return this.makeIEntityTable(query, (IContext)executorContext);
        }
        return this.entityRowQuery.makeIEntityTable(context, filters);
    }

    @Override
    public IEntityTable makeIEntityTable(IUnitTreeContext context, List<String> rowKeys, String ... filters) {
        if (IEntityQueryPloy.SCENES_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context, rowKeys, filters);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            return this.makeIEntityTable(query, (IContext)executorContext);
        }
        return this.entityRowQuery.makeIEntityTable(context, rowKeys, filters);
    }

    @Override
    public IEntityTable makeFullTreeData(IUnitTreeContext context) {
        if (IEntityQueryPloy.SCENES_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            return this.makeRangeFullTreeData(query, (IContext)executorContext);
        }
        return this.entityRowQuery.makeFullTreeData(context);
    }

    @Override
    public IEntityTable makeRangeFullTreeData(IUnitTreeContext context, List<String> parentKeys) {
        if (IEntityQueryPloy.SCENES_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            TreeRangeQuery treeRangeQuery = new TreeRangeQuery();
            treeRangeQuery.setParentKey(parentKeys);
            return this.makeRangeFullTreeData(query, (IContext)executorContext, (RangeQuery)treeRangeQuery);
        }
        return this.entityRowQuery.makeRangeFullTreeData(context, parentKeys);
    }

    @Override
    public IEntityTable makeRangeFullTreeData(IUnitTreeContext context, List<String> parentKeys, boolean sorted) {
        if (IEntityQueryPloy.SCENES_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context);
            query.sorted(sorted);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            TreeRangeQuery treeRangeQuery = new TreeRangeQuery();
            treeRangeQuery.setParentKey(parentKeys);
            return this.makeRangeFullTreeData(query, (IContext)executorContext, (RangeQuery)treeRangeQuery);
        }
        return this.entityRowQuery.makeRangeFullTreeData(context, parentKeys);
    }

    private IEntityQuery makeIEntityQuery(IUnitTreeContext context) {
        IEntityQuery query = this.dataService.newEntityQuery();
        query.sorted(true);
        query.setMasterKeys(new DimensionValueSet());
        query.setAuthorityOperations(AuthorityType.Read);
        query.setEntityView(this.buildEntityView(context));
        query.setExpression(context.getRowFilterExpression());
        query.lazyQuery();
        query.markLeaf();
        return query;
    }

    private IEntityQuery makeIEntityQuery(IUnitTreeContext context, List<String> rowKeys) {
        IEntityQuery query = this.makeIEntityQuery(context);
        query.getMasterKeys().setValue(context.getEntityDefine().getDimensionName(), rowKeys);
        return query;
    }

    private IEntityQuery makeIEntityQuery(IUnitTreeContext context, String ... filters) {
        IEntityQuery query = this.makeIEntityQuery(context);
        query.setExpression(IUnitTreeEntityDataQuery.buildFilters(context, filters));
        return query;
    }

    private IEntityQuery makeIEntityQuery(IUnitTreeContext context, List<String> rowKeys, String ... filters) {
        IEntityQuery query = this.makeIEntityQuery(context);
        query.getMasterKeys().setValue(context.getEntityDefine().getDimensionName(), rowKeys);
        query.setExpression(IUnitTreeEntityDataQuery.buildFilters(context, filters));
        return query;
    }

    private ExecutorContext makeExecuteContext(IUnitTreeContext context, IEntityQuery query) {
        return new ExecutorContext(this.tbRtCtl);
    }

    private EntityViewDefine buildEntityView(IUnitTreeContext context) {
        IDimensionFilter dimensionFilter;
        IEntityDefine entityDefine = context.getEntityDefine();
        FormSchemeDefine formScheme = context.getFormScheme();
        if (null != formScheme && (dimensionFilter = this.viewController.getDimensionFilter(formScheme.getKey(), entityDefine.getId())) != null) {
            return this.viewAdapter.buildEntityView(dimensionFilter);
        }
        return this.viewAdapter.buildEntityView(entityDefine.getId());
    }

    private IEntityTable makeIEntityTable(IEntityQuery query, IContext executorContext) {
        try {
            return query.executeReader(executorContext);
        }
        catch (Exception e) {
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }

    private IEntityTable makeRangeFullTreeData(IEntityQuery query, IContext executorContext) {
        try {
            return query.executeFullBuild(executorContext);
        }
        catch (Exception e) {
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }

    private IEntityTable makeRangeFullTreeData(IEntityQuery query, IContext executorContext, RangeQuery rangeQuery) {
        try {
            return query.executeRangeBuild(executorContext, rangeQuery);
        }
        catch (Exception e) {
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }
}

