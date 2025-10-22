/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 *  com.jiuqi.nr.entity.engine.var.TreeRangeQuery
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.tag.manager.entitydata.query;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.TreeRangeQuery;
import com.jiuqi.nr.tag.manager.entitydata.query.ITagEntityDataQuery;
import com.jiuqi.nr.tag.manager.entitydata.query.ITagEntityQueryTemplate;
import com.jiuqi.nr.tag.manager.entitydata.query.ITagEntityRowQuery;
import com.jiuqi.nr.tag.manager.entitydata.query.TagQueryParam;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TagEntityDataQuery
implements ITagEntityDataQuery {
    @Resource
    private ITagEntityQueryTemplate queryTemplate;
    @Resource(name="dataEntityFullService")
    private DataEntityFullService entityDataService;
    @Resource
    private ITagEntityRowQuery entityRowQuery;

    @Override
    public IEntityTable makeIEntityTable(TagQueryParam context) {
        FormSchemeDefine formScheme = context.getFormScheme();
        if (formScheme != null) {
            IEntityQuery query = this.queryTemplate.makeIEntityQuery(context);
            EntityViewDefine entityViewDefine = this.queryTemplate.buildEntityView(context);
            ExecutorContext executorContext = this.queryTemplate.makeExecuteContext(context, query);
            return this.executeEntityReader(query, entityViewDefine, executorContext, formScheme.getKey());
        }
        return this.entityRowQuery.makeIEntityTable(context);
    }

    @Override
    public IEntityTable makeIEntityTable(TagQueryParam context, List<String> rowKeys) {
        FormSchemeDefine formScheme = context.getFormScheme();
        if (formScheme != null) {
            IEntityQuery query = this.queryTemplate.makeIEntityQuery(context, rowKeys);
            EntityViewDefine entityViewDefine = this.queryTemplate.buildEntityView(context);
            ExecutorContext executorContext = this.queryTemplate.makeExecuteContext(context, query);
            return this.executeEntityReader(query, entityViewDefine, executorContext, formScheme.getKey());
        }
        return this.entityRowQuery.makeIEntityTable(context, rowKeys);
    }

    @Override
    public IEntityTable makeIEntityTable(TagQueryParam context, String ... filters) {
        FormSchemeDefine formScheme = context.getFormScheme();
        if (formScheme != null) {
            IEntityQuery query = this.queryTemplate.makeIEntityQuery(context, filters);
            EntityViewDefine entityViewDefine = this.queryTemplate.buildEntityView(context);
            ExecutorContext executorContext = this.queryTemplate.makeExecuteContext(context, query);
            return this.executeEntityReader(query, entityViewDefine, executorContext, formScheme.getKey());
        }
        return this.entityRowQuery.makeIEntityTable(context, filters);
    }

    @Override
    public IEntityTable makeIEntityTable(TagQueryParam context, List<String> rowKeys, String ... filters) {
        FormSchemeDefine formScheme = context.getFormScheme();
        if (formScheme != null) {
            IEntityQuery query = this.queryTemplate.makeIEntityQuery(context, rowKeys, filters);
            EntityViewDefine entityViewDefine = this.queryTemplate.buildEntityView(context);
            ExecutorContext executorContext = this.queryTemplate.makeExecuteContext(context, query);
            return this.executeEntityReader(query, entityViewDefine, executorContext, formScheme.getKey());
        }
        return this.entityRowQuery.makeIEntityTable(context, rowKeys, filters);
    }

    @Override
    public IEntityTable makeFullTreeData(TagQueryParam context) {
        FormSchemeDefine formScheme = context.getFormScheme();
        if (formScheme != null) {
            IEntityQuery query = this.queryTemplate.makeIEntityQuery(context);
            EntityViewDefine entityViewDefine = this.queryTemplate.buildEntityView(context);
            ExecutorContext executorContext = this.queryTemplate.makeExecuteContext(context, query);
            return this.executeEntityFullBuild(query, entityViewDefine, executorContext, formScheme.getKey());
        }
        return this.entityRowQuery.makeFullTreeData(context);
    }

    @Override
    public IEntityTable makeRangeFullTreeData(TagQueryParam context, List<String> parentKeys) {
        FormSchemeDefine formScheme = context.getFormScheme();
        if (formScheme != null) {
            IEntityQuery query = this.queryTemplate.makeIEntityQuery(context);
            EntityViewDefine entityViewDefine = this.queryTemplate.buildEntityView(context);
            TreeRangeQuery treeRangeQuery = new TreeRangeQuery();
            treeRangeQuery.setParentKey(parentKeys);
            ExecutorContext executorContext = this.queryTemplate.makeExecuteContext(context, query);
            return this.executeEntityRangeBuild(query, entityViewDefine, executorContext, formScheme.getKey(), (RangeQuery)treeRangeQuery);
        }
        return this.entityRowQuery.makeRangeFullTreeData(context, parentKeys);
    }

    private IEntityTable executeEntityReader(IEntityQuery entityQuery, EntityViewDefine entityView, ExecutorContext executorContext, String formSchemeKey) {
        try {
            return this.entityDataService.executeEntityReader(entityQuery, executorContext, entityView, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }

    private IEntityTable executeEntityFullBuild(IEntityQuery entityQuery, EntityViewDefine entityView, ExecutorContext executorContext, String formSchemeKey) {
        try {
            return this.entityDataService.executeEntityFullBuild(entityQuery, executorContext, entityView, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }

    private IEntityTable executeEntityRangeBuild(IEntityQuery entityQuery, EntityViewDefine entityView, ExecutorContext executorContext, String formSchemeKey, RangeQuery rangeQuery) {
        try {
            return this.entityDataService.executeEntityRangeBuild(entityQuery, executorContext, entityView, rangeQuery, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }
}

