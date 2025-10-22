/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 *  com.jiuqi.nr.entity.engine.var.TreeRangeQuery
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 *  javax.annotation.Resource
 */
package com.jiuiqi.nr.unit.treebase.entity.query;

import com.jiuiqi.nr.unit.treebase.entity.query.ICommonEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.TreeRangeQuery;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component(value="common-entity-row-query")
public class CommonEntityRowQuery
implements ICommonEntityDataQuery {
    @Resource
    private IEntityDataService dataService;
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private IEntityViewRunTimeController viewAdapter;
    @Resource
    private IDataDefinitionRuntimeController tbRtCtl;

    @Override
    public IEntityTable makeIEntityTable(String entityId) {
        IEntityQuery query = this.makeIEntityQuery(entityId);
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        return this.makeIEntityTable(query, (IContext)executorContext);
    }

    @Override
    public IEntityTable makeIEntityTable(String entityId, String ... filters) {
        IEntityQuery query = this.makeIEntityQuery(entityId);
        query.setExpression(IUnitTreeEntityDataQuery.buildFilters(filters));
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        return this.makeIEntityTable(query, (IContext)executorContext);
    }

    @Override
    public IEntityTable makeIEntityTable(String entityId, List<String> rowKeys) {
        IEntityQuery query = this.makeIEntityQuery(entityId, rowKeys);
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        return this.makeIEntityTable(query, (IContext)executorContext);
    }

    @Override
    public IEntityTable makeIEntityTable(String entityId, List<String> rowKeys, String ... filters) {
        IEntityDefine entityDefine = this.metaService.queryEntity(entityId);
        IEntityQuery query = this.makeIEntityQuery(entityId, rowKeys);
        query.getMasterKeys().setValue(entityDefine.getDimensionName(), rowKeys);
        query.setExpression(IUnitTreeEntityDataQuery.buildFilters(filters));
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        return this.makeIEntityTable(query, (IContext)executorContext);
    }

    @Override
    public IEntityTable makeIEntityTable(String entityId, List<String> rowKeys, AuthorityType authorityType) {
        IEntityQuery query = this.makeIEntityQuery(entityId, rowKeys, authorityType);
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        return this.makeIEntityTable(query, (IContext)executorContext);
    }

    @Override
    public IEntityTable makeFullTreeData(String entityId) {
        IEntityQuery query = this.makeIEntityQuery(entityId);
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        return this.makeRangeFullTreeData(query, (IContext)executorContext);
    }

    @Override
    public IEntityTable makeRangeFullTreeData(String entityId, List<String> parentKeys) {
        IEntityQuery query = this.makeIEntityQuery(entityId);
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        TreeRangeQuery treeRangeQuery = new TreeRangeQuery();
        treeRangeQuery.setParentKey(parentKeys);
        return this.makeRangeFullTreeData(query, (IContext)executorContext, (RangeQuery)treeRangeQuery);
    }

    private IEntityQuery makeIEntityQuery(String entityId) {
        IEntityDefine entityDefine = this.metaService.queryEntity(entityId);
        EntityViewDefine entityView = this.viewAdapter.buildEntityView(entityDefine.getId());
        IEntityQuery query = this.dataService.newEntityQuery();
        query.sorted(true);
        query.setEntityView(entityView);
        query.setAuthorityOperations(AuthorityType.Read);
        return query;
    }

    private IEntityQuery makeIEntityQuery(String entityId, List<String> rowKeys) {
        IEntityDefine entityDefine = this.metaService.queryEntity(entityId);
        DimensionValueSet masterKeys = new DimensionValueSet();
        masterKeys.setValue(entityDefine.getDimensionName(), rowKeys);
        IEntityQuery query = this.makeIEntityQuery(entityId);
        query.setMasterKeys(masterKeys);
        return query;
    }

    private IEntityQuery makeIEntityQuery(String entityId, List<String> rowKeys, AuthorityType authorityType) {
        IEntityDefine entityDefine = this.metaService.queryEntity(entityId);
        EntityViewDefine entityView = this.viewAdapter.buildEntityView(entityDefine.getId());
        IEntityQuery query = this.dataService.newEntityQuery();
        DimensionValueSet masterKeys = new DimensionValueSet();
        masterKeys.setValue(entityDefine.getDimensionName(), rowKeys);
        query.setEntityView(entityView);
        query.setAuthorityOperations(authorityType);
        query.setMasterKeys(masterKeys);
        return query;
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

