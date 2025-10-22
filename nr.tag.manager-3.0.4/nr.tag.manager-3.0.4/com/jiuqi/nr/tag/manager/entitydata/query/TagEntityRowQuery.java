/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 *  com.jiuqi.nr.entity.engine.var.TreeRangeQuery
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.tag.manager.entitydata.query;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.TreeRangeQuery;
import com.jiuqi.nr.tag.manager.entitydata.query.ITagEntityQueryTemplate;
import com.jiuqi.nr.tag.manager.entitydata.query.ITagEntityRowQuery;
import com.jiuqi.nr.tag.manager.entitydata.query.TagQueryParam;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TagEntityRowQuery
implements ITagEntityRowQuery {
    @Resource
    private ITagEntityQueryTemplate queryTemplate;

    @Override
    public IEntityTable makeIEntityTable(String entityId) {
        IEntityQuery query = this.queryTemplate.makeIEntityQuery(entityId);
        ExecutorContext executorContext = this.queryTemplate.makeExecuteContext();
        return this.makeIEntityTable(query, (IContext)executorContext);
    }

    @Override
    public IEntityTable makeIEntityTable(String entityId, List<String> rowKeys) {
        IEntityQuery query = this.queryTemplate.makeIEntityQuery(entityId, rowKeys);
        ExecutorContext executorContext = this.queryTemplate.makeExecuteContext();
        return this.makeIEntityTable(query, (IContext)executorContext);
    }

    @Override
    public IEntityTable makeIEntityTable(TagQueryParam context) {
        IEntityQuery query = this.queryTemplate.makeIEntityQuery(context);
        ExecutorContext executorContext = this.queryTemplate.makeExecuteContext(context, query);
        return this.makeIEntityTable(query, (IContext)executorContext);
    }

    @Override
    public IEntityTable makeIEntityTable(TagQueryParam context, List<String> rowKeys) {
        IEntityQuery query = this.queryTemplate.makeIEntityQuery(context, rowKeys);
        ExecutorContext executorContext = this.queryTemplate.makeExecuteContext(context, query);
        return this.makeIEntityTable(query, (IContext)executorContext);
    }

    @Override
    public IEntityTable makeIEntityTable(TagQueryParam context, String ... filters) {
        IEntityQuery query = this.queryTemplate.makeIEntityQuery(context, filters);
        ExecutorContext executorContext = this.queryTemplate.makeExecuteContext(context, query);
        return this.makeIEntityTable(query, (IContext)executorContext);
    }

    @Override
    public IEntityTable makeIEntityTable(TagQueryParam context, List<String> rowKeys, String ... filters) {
        IEntityQuery query = this.queryTemplate.makeIEntityQuery(context, rowKeys, filters);
        ExecutorContext executorContext = this.queryTemplate.makeExecuteContext(context, query);
        return this.makeIEntityTable(query, (IContext)executorContext);
    }

    @Override
    public IEntityTable makeFullTreeData(TagQueryParam context) {
        IEntityQuery query = this.queryTemplate.makeIEntityQuery(context);
        ExecutorContext executorContext = this.queryTemplate.makeExecuteContext(context, query);
        return this.makeRangeFullTreeData(query, (IContext)executorContext);
    }

    @Override
    public IEntityTable makeRangeFullTreeData(TagQueryParam context, List<String> parentKeys) {
        IEntityQuery query = this.queryTemplate.makeIEntityQuery(context);
        ExecutorContext executorContext = this.queryTemplate.makeExecuteContext(context, query);
        TreeRangeQuery treeRangeQuery = new TreeRangeQuery();
        treeRangeQuery.setParentKey(parentKeys);
        return this.makeRangeFullTreeData(query, (IContext)executorContext, (RangeQuery)treeRangeQuery);
    }

    private IEntityTable makeIEntityTable(IEntityQuery query, IContext executorContext) {
        try {
            return query.executeReader(executorContext);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }

    private IEntityTable makeRangeFullTreeData(IEntityQuery query, IContext executorContext) {
        try {
            return query.executeFullBuild(executorContext);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }

    private IEntityTable makeRangeFullTreeData(IEntityQuery query, IContext executorContext, RangeQuery rangeQuery) {
        try {
            return query.executeRangeBuild(executorContext, rangeQuery);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }
}

