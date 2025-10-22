/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.dataentity_ext.internal;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataentity_ext.common.EntityDataException;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataRow;
import com.jiuqi.nr.dataentity_ext.dto.QueryParam;
import com.jiuqi.nr.dataentity_ext.dto.SaveParam;
import com.jiuqi.nr.dataentity_ext.internal.EntityQueryImpl;
import com.jiuqi.nr.dataentity_ext.internal.TreeBuilder;
import com.jiuqi.nr.dataentity_ext.internal.db.EntityDataDO;
import com.jiuqi.nr.dataentity_ext.internal.db.EntityDataDao;
import com.jiuqi.nr.dataentity_ext.internal.db.EntityDataTempTable;
import com.jiuqi.nr.dataentity_ext.internal.db.TempTableUtil;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class EntityDataServiceImpl
implements com.jiuqi.nr.dataentity_ext.api.IEntityDataService {
    private static final Logger log = LoggerFactory.getLogger(EntityDataServiceImpl.class);
    @Autowired
    private EntityDataDao entityDataDao;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;

    @Override
    public String save(SaveParam saveParam, List<IEntityDataRow> customRows) throws EntityDataException {
        List<EntityDataDO> data = null;
        try {
            data = this.getDOList(saveParam, customRows);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (CollectionUtils.isEmpty(data)) {
            throw new EntityDataException("\u81ea\u5b9a\u4e49\u5b9e\u4f53\u6570\u636e\u4e3a\u7a7a");
        }
        EntityDataTempTable table = new EntityDataTempTable(true);
        Connection conn = null;
        String tableName = table.getTableName();
        try {
            conn = DataSourceUtils.getConnection((DataSource)this.dataSource);
            this.entityDataDao.insertTableInfo(tableName);
            TempTableUtil.restructureTempTable(conn, table);
            this.entityDataDao.batchInsert(tableName, data);
        }
        catch (Throwable t) {
            log.error(t.getMessage(), t);
            throw new EntityDataException(t);
        }
        finally {
            if (conn != null) {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSource);
            }
        }
        return tableName;
    }

    @Override
    public com.jiuqi.nr.dataentity_ext.api.IEntityQuery getEntityQuery(QueryParam queryParam) throws EntityDataException {
        if (!StringUtils.hasText(queryParam.getResourceId())) {
            throw new IllegalArgumentException("resourceId must not be null");
        }
        return new EntityQueryImpl(queryParam, this.entityDataDao);
    }

    @Override
    public void drop(String resourceId) {
        this.entityDataDao.dropEntityTables(Collections.singletonList(resourceId));
        this.entityDataDao.deleteTableInfo(Collections.singletonList(resourceId));
    }

    @Override
    public void dropByTime(long time) {
        List<String> tableNames = this.entityDataDao.listTableNamesByTime(time);
        this.entityDataDao.dropEntityTables(tableNames);
        this.entityDataDao.deleteTableInfo(tableNames);
    }

    private List<EntityDataDO> getDOList(SaveParam saveParam, List<IEntityDataRow> customRows) throws Exception {
        if (CollectionUtils.isEmpty(customRows)) {
            return Collections.emptyList();
        }
        IEntityTable entityTable = this.getEntityTable(saveParam);
        List<TreeBuilder.TreeNode> treeNodes = TreeBuilder.buildTree(customRows, entityTable, saveParam.getCountType());
        return this.getDOSByTree(treeNodes);
    }

    private String getContextMainDimId(FormSchemeDefine formScheme) {
        String dw = formScheme.getDw();
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        return StringUtils.hasText(entityId) ? entityId : dw;
    }

    private Date period2Date(String periodEntityId, String period) {
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityId);
        Date versionDate = null;
        try {
            versionDate = periodProvider.getPeriodDateRegion(period)[1];
        }
        catch (ParseException e) {
            log.error("\u83b7\u53d6\u65f6\u671f\u5931\u8d25{}", (Object)e.getMessage());
        }
        return versionDate;
    }

    private EntityViewDefine getEntityViewDefine(FormSchemeDefine formScheme) {
        String entityId = this.getContextMainDimId(formScheme);
        TaskDefine task = this.runTimeViewController.getTask(formScheme.getTaskKey());
        if (task == null) {
            throw new IllegalArgumentException("\u672a\u67e5\u8be2\u5230\u4efb\u52a1");
        }
        return this.entityViewRunTimeController.buildEntityView(entityId, task.getFilterExpression(), true);
    }

    private IEntityTable getEntityTable(SaveParam saveParam) throws Exception {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(saveParam.getFormSchemeKey());
        IEntityQuery query = this.entityDataService.newEntityQuery();
        EntityViewDefine entityViewDefine = this.getEntityViewDefine(formScheme);
        query.setEntityView(entityViewDefine);
        Date queryVersionDate = this.period2Date(formScheme.getDateTime(), saveParam.getPeriod());
        query.setQueryVersionDate(queryVersionDate);
        query.setAuthorityOperations(AuthorityType.Read);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        return query.executeFullBuild((IContext)context);
    }

    private List<EntityDataDO> getDOSByTree(List<TreeBuilder.TreeNode> rootNodes) {
        ArrayList<EntityDataDO> data = new ArrayList<EntityDataDO>();
        for (TreeBuilder.TreeNode node : rootNodes) {
            this.traverseNodeDFS(node, data, true);
        }
        return data;
    }

    private void traverseNodeDFS(TreeBuilder.TreeNode node, List<EntityDataDO> collect, boolean root) {
        EntityDataDO entityDataDO = new EntityDataDO();
        entityDataDO.setKey(node.getData().getKey());
        entityDataDO.setCode(node.getData().getCode());
        entityDataDO.setTitle(node.getData().getTitle());
        String parent = node.getData().getParent();
        entityDataDO.setParent(!root && StringUtils.hasText(parent) ? parent : "-");
        entityDataDO.setOrder(node.getData().getOrder());
        entityDataDO.setType(node.getType().getCode());
        entityDataDO.setLeaf(node.isLeaf() ? 1 : 0);
        entityDataDO.setPath(StringUtils.hasText(node.getPath()) ? node.getPath() + "/" + node.getData().getKey() : node.getData().getKey());
        entityDataDO.setChildCount(node.getChildCount());
        entityDataDO.setAllChildCount(node.getAllChildCount());
        collect.add(entityDataDO);
        for (TreeBuilder.TreeNode child : node.getChildren()) {
            this.traverseNodeDFS(child, collect, false);
        }
    }
}

