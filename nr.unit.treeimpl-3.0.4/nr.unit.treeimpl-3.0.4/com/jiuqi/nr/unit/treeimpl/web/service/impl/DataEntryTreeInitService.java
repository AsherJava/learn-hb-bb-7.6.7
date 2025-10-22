/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 *  com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.enumeration.UnitTreeNodeCountPloy
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject
 *  com.jiuiqi.nr.unit.treebase.web.response.GroupDimConfig
 *  com.jiuiqi.nr.unit.treebase.web.response.SystemConfig
 *  com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeDataService
 *  com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeMenuService
 *  com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeStaticSourceService
 *  com.jiuqi.nr.bpm.de.dataflow.service.ITreeNodeIconColorService
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityAssist
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.treeimpl.web.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.enumeration.UnitTreeNodeCountPloy;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuiqi.nr.unit.treebase.web.response.GroupDimConfig;
import com.jiuiqi.nr.unit.treebase.web.response.SystemConfig;
import com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeDataService;
import com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeMenuService;
import com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeStaticSourceService;
import com.jiuqi.nr.bpm.de.dataflow.service.ITreeNodeIconColorService;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityAssist;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys;
import com.jiuqi.nr.unit.treeimpl.web.service.IDataEntryTreeInitService;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataEntryTreeInitService
implements IDataEntryTreeInitService {
    static final String KEY_OF_ENTITY_ID = "entityId";
    private static final String KEY_OF_TASK_ID = "taskId";
    private static final String KEY_OF_NODE_KEY = "nodeKey";
    @Resource
    private IUnitTreeMenuService menuService;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    @Resource
    private ITreeNodeIconColorService flowWorkStateIcon;
    @Resource
    private UnitTreeSystemConfig unitTreeSystemConfig;
    @Resource
    private IUnitTreeContextBuilder contextBuilder;
    @Resource
    private UnitTreeI18nHelper unitTreeI18nHelper;
    @Resource
    private UnitTreeEntityDataQuery entityDataQuery;
    @Resource
    private IEntityAssist entityAssist;
    @Resource(name="unit-tree-data-service")
    private IUnitTreeDataService unitTreeDataService;
    @Value(value="${jiuqi.nr.dataentry.unittree.treeNodeCanDrag:false}")
    private boolean treeNodeCanDrag = false;
    @Resource
    private IUnitTreeStaticSourceService staticSourceService;

    @Override
    public Map<String, Object> getEnvironment(UnitTreeContextData contextData) {
        IUnitTreeContext ctx = this.contextBuilder.createTreeContext(contextData);
        HashMap<String, Object> sourceMap = new HashMap<String, Object>();
        sourceMap.put(KEY_OF_ENTITY_ID, ctx.getEntityDefine().getId());
        sourceMap.put(KEY_OF_TASK_ID, ctx.getTaskDefine().getKey());
        this.putSystemConfig(sourceMap, ctx);
        sourceMap.put("dataStatusSources", this.staticSourceService.createWorkflowStatusSource(ctx));
        sourceMap.put("treeNodeCanDrag", this.treeNodeCanDrag);
        return sourceMap;
    }

    @Override
    public List<IMenuItemObject> getFilterMenusItems(UnitTreeContextData contextData) {
        return this.menuService.getFilterMenusItems(contextData);
    }

    @Override
    public int getNodeAllChildrenCount(UnitTreeContextData contextData) {
        int count;
        IUnitTreeContext context = this.contextBuilder.createTreeContext(contextData);
        if (this.entityAssist.isDBModel(context.getEntityDefine().getId(), context.getPeriod(), context.getTaskDefine().getDateTime())) {
            IEntityTable readDataTable = this.entityDataQuery.makeIEntityTable(context, this.unitTreeSystemConfig.isCountOfDiffUnit(), this.unitTreeSystemConfig.isCountOfLeaves());
            return readDataTable.getAllChildCount(contextData.getCustomVariable().get(KEY_OF_NODE_KEY).toString());
        }
        IEntityTable readDataTable = this.entityDataQuery.makeIEntityTable(context);
        UnitTreeNodeCountPloy nodeCountPloy = UnitTreeNodeCountPloy.translatePloy((boolean)this.unitTreeSystemConfig.isCountOfDiffUnit(), (boolean)this.unitTreeSystemConfig.isCountOfLeaves());
        IEntityRefer referBBLXEntity = this.contextWrapper.getBBLXEntityRefer(context.getEntityDefine());
        switch (nodeCountPloy) {
            case COUNT_OF_LEAF: {
                List allChildRows = readDataTable.getAllChildRows(contextData.getCustomVariable().get(KEY_OF_NODE_KEY).toString());
                count = this.getOnlyLeafEntityRowsCount(readDataTable, allChildRows);
                break;
            }
            case COUNT_OF_LEAF_AND_NOT_CHA_E: {
                List allChildRows = readDataTable.getAllChildRows(contextData.getCustomVariable().get(KEY_OF_NODE_KEY).toString());
                count = referBBLXEntity != null ? this.getLeafAndNotDiffUnitEntityRowsCount(readDataTable, allChildRows, referBBLXEntity) : this.getOnlyLeafEntityRowsCount(readDataTable, allChildRows);
                break;
            }
            case COUNT_OF_ALL_CHILD_AND_NOT_CHA_E: {
                List allChildRows = readDataTable.getAllChildRows(contextData.getCustomVariable().get(KEY_OF_NODE_KEY).toString());
                count = referBBLXEntity != null ? this.getAllAndNotDiffUnitEntityRowsCount(allChildRows, referBBLXEntity) : allChildRows.size();
                break;
            }
            default: {
                count = readDataTable.getAllChildCount(contextData.getCustomVariable().get(KEY_OF_NODE_KEY).toString());
            }
        }
        return count;
    }

    private void putSystemConfig(Map<String, Object> sourceMap, IUnitTreeContext unitTreeContext) {
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setCanAddDimension(Boolean.valueOf(this.contextWrapper.canAddDimension(unitTreeContext.getTaskDefine())));
        systemConfig.setNameOfChildCount(this.unitTreeI18nHelper.getMessage(UnitTreeI18nKeys.ALL_CHILDREN_COUNT.key, this.unitTreeSystemConfig.nameOfLeaves()));
        systemConfig.setShowFilterMenu(Boolean.valueOf(true));
        systemConfig.setShowContextMenu(Boolean.valueOf(true));
        systemConfig.setShowCountOfAllChildrenQuantities(Boolean.valueOf(this.unitTreeSystemConfig.isCountOfAllChildrenQuantities()));
        if (this.unitTreeSystemConfig.isShowNodeVline()) {
            systemConfig.setNodeDisplay("the-node-line");
        }
        sourceMap.put("systemConfig", systemConfig);
    }

    private void putGroupDimConfig(Map<String, Object> sourceMap, IUnitTreeContext unitTreeContext) {
        boolean hasConfig = this.contextWrapper.hasDimGroupConfig(unitTreeContext.getTaskDefine());
        if (hasConfig) {
            GroupDimConfig groupDimConfig = new GroupDimConfig();
            groupDimConfig.setDimGroupConfig(true);
            sourceMap.put("groupDimConfig", groupDimConfig);
        }
    }

    private int getOnlyLeafEntityRowsCount(IEntityTable readDataTable, List<IEntityRow> allChildRows) {
        int count = 0;
        for (IEntityRow row : allChildRows) {
            if (readDataTable.getDirectChildCount(row.getEntityKeyData()) != 0) continue;
            ++count;
        }
        return count;
    }

    private int getLeafAndNotDiffUnitEntityRowsCount(IEntityTable readDataTable, List<IEntityRow> allChildRows, IEntityRefer referBBLXEntity) {
        int count = 0;
        for (IEntityRow row : allChildRows) {
            if (readDataTable.getDirectChildCount(row.getEntityKeyData()) != 0 || "1".equals(row.getAsString(referBBLXEntity.getOwnField()))) continue;
            ++count;
        }
        return count;
    }

    private int getAllAndNotDiffUnitEntityRowsCount(List<IEntityRow> allChildRows, IEntityRefer referBBLXEntity) {
        int count = 0;
        for (IEntityRow row : allChildRows) {
            if ("1".equals(row.getAsString(referBBLXEntity.getOwnField()))) continue;
            ++count;
        }
        return count;
    }
}

