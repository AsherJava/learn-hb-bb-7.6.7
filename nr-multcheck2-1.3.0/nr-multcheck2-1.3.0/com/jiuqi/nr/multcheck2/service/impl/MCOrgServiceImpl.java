/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.xlib.utils.StringUtils
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckSchemeOrg;
import com.jiuqi.nr.multcheck2.common.OrgType;
import com.jiuqi.nr.multcheck2.service.IMCOrgService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.multcheck2.service.dto.MCOrgTreeDTO;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import com.jiuqi.nr.multcheck2.web.vo.OrgTreeNode;
import com.jiuqi.xlib.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MCOrgServiceImpl
implements IMCOrgService {
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController1;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;

    @Override
    public List<String> getOrgsBySchemePeriod(MultcheckScheme scheme, String period) throws Exception {
        IEntityTable entityTable = this.getEntityTableByScheme(scheme, period, false);
        return entityTable.getAllRows().stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
    }

    @Override
    public List<String> getOrgsByTaskPeriodOrg(String taskKey, String period, String org) throws Exception {
        IEntityTable entityTable = this.getEntityTable(taskKey, period, org, null, null, null, false);
        return entityTable.getAllRows().stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
    }

    @Override
    public List<MCLabel> getOrgLabels(String taskKey, String period, String org, List<String> orgList) throws Exception {
        IEntityTable entityTable = this.getEntityTable(taskKey, period, org, orgList, null, null, false);
        ArrayList<MCLabel> list = new ArrayList<MCLabel>();
        List rows = entityTable.getAllRows();
        for (IEntityRow row : rows) {
            list.add(new MCLabel(row.getEntityKeyData(), row.getTitle(), row.getCode()));
        }
        return list;
    }

    @Override
    public IEntityTable getTreeEntityTable(MultcheckScheme scheme, String period, List<String> orgList) throws Exception {
        return this.getEntityTable(scheme.getTask(), period, scheme.getOrg(), orgList, null, scheme.getFormScheme(), true);
    }

    @Override
    public IEntityTable getTreeEntityTable(String taskKey, String period, String org, String formSchemeKey) throws Exception {
        return this.getEntityTable(taskKey, period, org, null, null, formSchemeKey, true);
    }

    @Override
    public IEntityTable getTreeEntityTable(String taskKey, String period, String org, List<String> orgList, String formSchemeKey) throws Exception {
        return this.getEntityTable(taskKey, period, org, orgList, null, formSchemeKey, true);
    }

    @Override
    public MCOrgTreeDTO getOrgTreeBySchemePeriod(MultcheckScheme scheme, String period) throws Exception {
        IEntityTable entityTable = this.getEntityTableByScheme(scheme, period, true);
        return this.getOrgTree(entityTable);
    }

    @Override
    public MCOrgTreeDTO getOrgTreeByTaskPeriodOrg(String taskKey, String period, String org, List<String> orgList, String formSchemeKey) throws Exception {
        IEntityTable entityTable = this.getEntityTable(taskKey, period, org, orgList, null, formSchemeKey, true);
        return this.getOrgTree(entityTable);
    }

    @Override
    public List<String> getLeafOrgsByTaskPeriodOrg(String taskKey, String period, String org, List<String> orgList) throws Exception {
        IEntityTable entityTable = this.getEntityTable(taskKey, period, org, orgList, null, null, false);
        List rows = entityTable.getAllRows();
        ArrayList<String> list = new ArrayList<String>();
        for (IEntityRow row : rows) {
            if (!row.isLeaf()) continue;
            list.add(row.getEntityKeyData());
        }
        return list;
    }

    private MCOrgTreeDTO getOrgTree(IEntityTable entityTable) {
        MCOrgTreeDTO res = new MCOrgTreeDTO();
        ArrayList<ITree<OrgTreeNode>> list = new ArrayList<ITree<OrgTreeNode>>();
        Counter counter = new Counter();
        List rootRows = entityTable.getRootRows();
        boolean first = true;
        for (IEntityRow row : rootRows) {
            counter.increment();
            ITree treeNode = new ITree((INode)new OrgTreeNode(row));
            treeNode.setLeaf(false);
            treeNode.setExpanded(false);
            if (first) {
                treeNode.setExpanded(true);
                treeNode.setSelected(true);
                first = false;
            }
            treeNode.setChildren(this.getChildren(row.getEntityKeyData(), entityTable, counter));
            if (CollectionUtils.isEmpty(treeNode.getChildren())) {
                treeNode.setLeaf(true);
            }
            list.add((ITree<OrgTreeNode>)treeNode);
        }
        res.setTreeList(list);
        res.setSize(counter.getCount());
        return res;
    }

    private List<ITree<OrgTreeNode>> getChildren(String parentId, IEntityTable entityTable, Counter counter) {
        ArrayList<ITree<OrgTreeNode>> childList = new ArrayList<ITree<OrgTreeNode>>();
        List childRows = entityTable.getChildRows(parentId);
        for (IEntityRow childRow : childRows) {
            counter.increment();
            List<ITree<OrgTreeNode>> children = this.getChildren(childRow.getEntityKeyData(), entityTable, counter);
            ITree treeNode = new ITree((INode)new OrgTreeNode(childRow));
            if (CollectionUtils.isEmpty(children)) {
                treeNode.setLeaf(true);
                treeNode.setExpanded(false);
            } else {
                treeNode.setLeaf(false);
                treeNode.setExpanded(false);
                treeNode.setChildren(children);
            }
            childList.add((ITree<OrgTreeNode>)treeNode);
        }
        return childList;
    }

    private IEntityTable getEntityTable(String taskKey, String period, String org, List<String> orgList, String fml, String formSchemeKey, boolean tree) throws Exception {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        if (!CollectionUtils.isEmpty(orgList)) {
            String dimensionName = this.entityMetaService.queryEntity(org).getDimensionName();
            dimensionValueSet.setValue(dimensionName, orgList);
        }
        EntityViewDefine entityView = this.runTimeViewController.getViewByTaskDefineKey(taskKey);
        List links = this.runTimeViewController.listTaskOrgLinkByTask(taskKey);
        if (links.size() > 1) {
            entityView = this.entityViewRunTimeController.buildEntityView(org, entityView.getRowFilterExpression(), entityView.getFilterRowByAuthority());
        }
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.sorted(true);
        query.setEntityView(entityView);
        query.setMasterKeys(dimensionValueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        query.sortedByQuery(false);
        query.markLeaf();
        if (StringUtils.hasText((String)fml)) {
            query.setExpression(fml);
        }
        if (!StringUtils.hasText((String)formSchemeKey)) {
            SchemePeriodLinkDefine formScheme = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskKey);
            formSchemeKey = formScheme.getSchemeKey();
        }
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController1, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setEnv((IFmlExecEnvironment)environment);
        context.setVarDimensionValueSet(dimensionValueSet);
        context.setPeriodView(taskDefine.getDateTime());
        return tree ? query.executeFullBuild((IContext)context) : query.executeReader((IContext)context);
    }

    private IEntityTable getEntityTableByScheme(MultcheckScheme scheme, String period, boolean tree) throws Exception {
        String fml = null;
        List<String> orgList = new ArrayList<String>();
        if (scheme.getOrgType() == OrgType.SELECT) {
            List<MultcheckSchemeOrg> msOrgs = this.schemeService.getOrgListByScheme(scheme.getKey());
            orgList = msOrgs.stream().map(MultcheckSchemeOrg::getOrg).collect(Collectors.toList());
        } else if (scheme.getOrgType() == OrgType.FORMULA) {
            fml = scheme.getOrgFml();
        }
        return this.getEntityTable(scheme.getTask(), period, scheme.getOrg(), orgList, fml, scheme.getFormScheme(), tree);
    }

    private class Counter {
        private int count = 0;

        private Counter() {
        }

        public void increment() {
            ++this.count;
        }

        public int getCount() {
            return this.count;
        }
    }
}

