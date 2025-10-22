/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.bpm.de.dataflow.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityQueryManager;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeEntityHelper
implements IEntityUpgrader {
    private static final Logger logger = LoggerFactory.getLogger(DeEntityHelper.class);
    @Autowired
    private DeEntityQueryManager entityManager;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    public IEntityTable getEntityTable(String entityViewKey, String formSchemeKey, String period) {
        try {
            return this.entityManager.entityQueryByViewKey(entityViewKey, formSchemeKey, period);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public IEntityTable getEntityTable(EntityViewDefine entityViewDefine, String formSchemeKey, DimensionValueSet dimensionValueSet) {
        try {
            return this.entityManager.buildEntityTable(entityViewDefine, dimensionValueSet, formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public IEntityTable getFullEntityTable(EntityViewDefine entityViewDefine, String formSchemeKey, DimensionValueSet dimensionValueSet) {
        try {
            return this.entityManager.buildFullEntityTable(entityViewDefine, dimensionValueSet, formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public IEntityTable getEntityTableByEntityView(EntityViewDefine entityViewDefine, String formSchemeKey, String period, AuthorityType authorityType) {
        try {
            DimensionValueSet valueSet = new DimensionValueSet();
            valueSet.setValue("DATATIME", (Object)period);
            return this.entityManager.entityQuerySetByEntityView(entityViewDefine, formSchemeKey, period, AuthorityType.None);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<IEntityRow> getRootData(String entityViewKey, String formSchemeKey, String period) throws Exception {
        IEntityTable entityTable = this.getEntityTable(entityViewKey, formSchemeKey, period);
        return entityTable.getRootRows();
    }

    public List<IEntityRow> getDirectChildrenData(String entityViewKey, String parentId, String formSchemeKey, String period) throws Exception {
        IEntityTable entityTable = this.getEntityTable(entityViewKey, formSchemeKey, period);
        return entityTable.getChildRows(parentId);
    }

    public String getParentId(String entityViewKey, String unitId, String formSchemeKey, String period) throws Exception {
        String parentEntityKey = null;
        IEntityTable entityData = this.getEntityTable(entityViewKey, formSchemeKey, period);
        parentEntityKey = entityData.findByEntityKey(unitId).getParentEntityKey();
        return parentEntityKey;
    }

    public String getParentId(String unitId, String formSchemeKey, String period) {
        IEntityRow findByEntityKey;
        IEntityTable entityData;
        String parentEntityKey = null;
        EntityViewDefine entityViewDefine = this.dimensionUtil.getDwEntityView(formSchemeKey);
        if (entityViewDefine != null && (entityData = this.getEntityTable(entityViewDefine.getEntityId(), formSchemeKey, period)) != null && (findByEntityKey = entityData.findByEntityKey(unitId)) != null) {
            parentEntityKey = findByEntityKey.getParentEntityKey();
        }
        return parentEntityKey;
    }

    public List<String> getDirectSubordinate(String entityViewKey, String entityDataKey, String formSchemeKey, String period) throws Exception {
        IEntityTable entityTable = this.getEntityTable(entityViewKey, formSchemeKey, period);
        return entityTable.getChildRows(entityDataKey).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
    }

    public List<String> getAllSubordinate(String entityViewKey, String entityDataKey, String formSchemeKey, String period) throws Exception {
        IEntityTable entityTable = this.getEntityTable(entityViewKey, formSchemeKey, period);
        return entityTable.getAllChildRows(entityDataKey).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
    }

    public List<IEntityRow> getEntityRow(String formSchemeKey, String period) {
        EntityViewDefine entityViewDefine = this.dimensionUtil.getDwEntityView(formSchemeKey);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        IEntityTable entityQuerySet = this.entityManager.buildEntityTable(entityViewDefine, dimensionValueSet, formSchemeKey);
        List allRows = entityQuerySet.getAllRows();
        return allRows;
    }

    public List<IEntityRow> getEntityRow(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        EntityViewDefine entityViewDefine = this.dimensionUtil.getDwEntityView(formSchemeKey);
        IEntityTable entityQuerySet = this.entityManager.buildEntityTable(entityViewDefine, dimensionValueSet, formSchemeKey);
        List allRows = entityQuerySet.getAllRows();
        return allRows;
    }

    public List<IEntityRow> getEntityRow(String formSchemeKey, DimensionValueSet dimensionValueSet, AuthorityType authorityType) {
        EntityViewDefine entityViewDefine = this.dimensionUtil.getDwEntityView(formSchemeKey);
        IEntityTable entityQuerySet = this.entityManager.entityQuerySet(entityViewDefine, dimensionValueSet, formSchemeKey, authorityType);
        List allRows = entityQuerySet.getAllRows();
        return allRows;
    }

    public List<IEntityRow> getEntityRow(String formSchemeKey, String unitId, String period) {
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        dimensionValueSet.setValue(dwMainDimName, (Object)unitId);
        EntityViewDefine entityViewDefine = this.dimensionUtil.getDwEntityView(formSchemeKey);
        IEntityTable entityQuerySet = this.entityManager.buildEntityTable(entityViewDefine, dimensionValueSet, formSchemeKey);
        List allRows = entityQuerySet.getAllRows();
        return allRows;
    }

    public List<String> getEntityIdRow(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        ArrayList<String> unitList = new ArrayList<String>();
        EntityViewDefine entityViewDefine = this.dimensionUtil.getDwEntityView(formSchemeKey);
        IEntityTable entityQuerySet = this.entityManager.buildEntityTable(entityViewDefine, dimensionValueSet, formSchemeKey);
        List allRows = entityQuerySet.getAllRows();
        if (allRows.size() > 0) {
            for (IEntityRow iEntityRow : allRows) {
                unitList.add(iEntityRow.getEntityKeyData());
            }
        }
        return unitList;
    }

    public List<IEntityRow> getRootData(DimensionValueSet dim, String formSchemeKey) {
        List<Object> rootRows = new ArrayList<IEntityRow>();
        try {
            EntityViewDefine entityView = this.dimensionUtil.getDwEntityView(formSchemeKey);
            DimensionValueSet rootDims = new DimensionValueSet();
            rootDims.setValue("DATATIME", dim.getValue("DATATIME"));
            IEntityTable entityQuerySet = this.entityManager.buildEntityTable(entityView, rootDims, formSchemeKey);
            rootRows = entityQuerySet.getRootRows();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return rootRows;
    }

    public List<IEntityRow> getDirectChildrenData(DimensionValueSet dim, String formSchemeKey) {
        List<Object> childrenRows = new ArrayList<IEntityRow>();
        try {
            EntityViewDefine entityView = this.dimensionUtil.getDwEntityView(formSchemeKey);
            DimensionValueSet childrenDims = new DimensionValueSet();
            childrenDims.setValue("DATATIME", dim.getValue("DATATIME"));
            IEntityTable entityQuerySet = this.entityManager.buildEntityTable(entityView, childrenDims, formSchemeKey);
            childrenRows = entityQuerySet.getChildRows(dim.getValue(this.dimensionUtil.getDimensionName(entityView)).toString());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return childrenRows;
    }

    public List<IEntityRow> getDirectChildrenData(DimensionValueSet dim, String formSchemeKey, IEntityTable entityQuerySet) {
        List<Object> childrenRows = new ArrayList<IEntityRow>();
        try {
            EntityViewDefine entityView = this.dimensionUtil.getDwEntityView(formSchemeKey);
            childrenRows = entityQuerySet.getChildRows(dim.getValue(this.dimensionUtil.getDimensionName(entityView)).toString());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return childrenRows;
    }

    public List<IEntityRow> getDirectChildrenData(IEntityTable entityQuerySet, String entityKeyData) {
        try {
            return entityQuerySet.getChildRows(entityKeyData);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<IEntityRow>();
        }
    }

    public List<String> getDirectChildrenId(DimensionValueSet dim, String formSchemeKey) {
        ArrayList<String> childrenIds = new ArrayList<String>();
        try {
            EntityViewDefine entityView = this.dimensionUtil.getDwEntityView(formSchemeKey);
            DimensionValueSet childrenDims = new DimensionValueSet();
            childrenDims.setValue("DATATIME", dim.getValue("DATATIME"));
            IEntityTable entityQuerySet = this.entityManager.buildEntityTable(entityView, childrenDims, formSchemeKey);
            List childrenRows = entityQuerySet.getChildRows(dim.getValue(this.dimensionUtil.getDimensionName(entityView)).toString());
            for (IEntityRow children : childrenRows) {
                childrenIds.add(children.getEntityKeyData());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return childrenIds;
    }

    public List<String> getDirectChildrenId(DimensionValueSet dim, String formSchemeKey, IEntityTable entityQuerySet) {
        ArrayList<String> childrenIds = new ArrayList<String>();
        try {
            EntityViewDefine entityView = this.dimensionUtil.getDwEntityView(formSchemeKey);
            List childrenRows = entityQuerySet.getChildRows(dim.getValue(this.dimensionUtil.getDimensionName(entityView)).toString());
            for (IEntityRow children : childrenRows) {
                childrenIds.add(children.getEntityKeyData());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return childrenIds;
    }

    public List<IEntityRow> getAllChildrenData(DimensionValueSet dim, String formSchemeKey) {
        List<Object> childrenRows = new ArrayList<IEntityRow>();
        try {
            EntityViewDefine entityView = this.dimensionUtil.getDwEntityView(formSchemeKey);
            DimensionValueSet childrenDims = new DimensionValueSet();
            childrenDims.setValue("DATATIME", dim.getValue("DATATIME"));
            IEntityTable entityQuerySet = this.entityManager.buildEntityTable(entityView, childrenDims, formSchemeKey);
            childrenRows = entityQuerySet.getAllChildRows(dim.getValue(this.dimensionUtil.getDimensionName(entityView)).toString());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return childrenRows;
    }

    public List<IEntityRow> getParentData(DimensionValueSet dim, FormSchemeDefine formScheme) {
        List<IEntityRow> parentRows = new ArrayList<IEntityRow>();
        try {
            EntityViewDefine entityView;
            IEntityTable entityData;
            String parentEntityKey;
            if (formScheme != null && (parentEntityKey = (entityData = this.entityManager.buildEntityTable(entityView = this.dimensionUtil.getDwEntityView(formScheme.getKey()), dim, formScheme.getKey())).findByEntityKey(dim.getValue(this.dimensionUtil.getDimensionName(entityView)).toString()).getParentEntityKey()) != null) {
                DimensionValueSet dimensionValue = new DimensionValueSet();
                dimensionValue.setValue("DATATIME", dim.getValue("DATATIME"));
                dimensionValue.setValue(this.dimensionUtil.getDimensionName(entityView), (Object)parentEntityKey);
                parentRows = this.getEntityRow(formScheme.getKey(), dimensionValue);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return parentRows;
    }

    public String getParent(DimensionValueSet dim, FormSchemeDefine formScheme) {
        String parentEntityKey = null;
        try {
            if (formScheme != null) {
                EntityViewDefine entityView = this.dimensionUtil.getDwEntityView(formScheme.getKey());
                IEntityTable entityData = this.entityManager.buildEntityTable(entityView, dim, formScheme.getKey());
                parentEntityKey = entityData.findByEntityKey(dim.getValue(this.dimensionUtil.getDimensionName(entityView)).toString()).getParentEntityKey();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return parentEntityKey;
    }

    public String getParent(DimensionValueSet dim, FormSchemeDefine formScheme, AuthorityType authorityType) {
        String parentEntityKey = null;
        try {
            if (formScheme != null) {
                EntityViewDefine entityView = this.dimensionUtil.getDwEntityView(formScheme.getKey());
                IEntityTable entityData = this.entityManager.entityQuerySet(entityView, dim, formScheme.getKey(), authorityType);
                parentEntityKey = entityData.findByEntityKey(dim.getValue(this.dimensionUtil.getDimensionName(entityView)).toString()).getParentEntityKey();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return parentEntityKey;
    }

    public String[] getAllParent(DimensionValueSet dim, FormSchemeDefine formScheme) {
        String[] parentEntityKey = null;
        try {
            if (formScheme != null) {
                EntityViewDefine entityView = this.dimensionUtil.getDwEntityView(formScheme.getKey());
                IEntityTable entityData = this.entityManager.buildEntityTable(entityView, dim, formScheme.getKey());
                parentEntityKey = entityData.findByEntityKey(dim.getValue(this.dimensionUtil.getDimensionName(entityView)).toString()).getParentsEntityKeyDataPath();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return parentEntityKey;
    }

    public String[] getAllParent(DimensionValueSet dim, FormSchemeDefine formScheme, AuthorityType authorityType) {
        String[] parentEntityKey = null;
        try {
            if (formScheme != null) {
                EntityViewDefine entityView = this.dimensionUtil.getDwEntityView(formScheme.getKey());
                IEntityTable entityData = this.entityManager.entityQuerySet(entityView, dim, formScheme.getKey(), authorityType);
                parentEntityKey = entityData.findByEntityKey(dim.getValue(this.dimensionUtil.getDimensionName(entityView)).toString()).getParentsEntityKeyDataPath();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return parentEntityKey;
    }

    public String getParent(IEntityTable entityData, String entityKeyData) {
        try {
            return entityData.findByEntityKey(entityKeyData).getParentEntityKey();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public String hasParent(String entityId, IEntityTable entityQuerySet) {
        String parentEntityKey = null;
        try {
            parentEntityKey = entityQuerySet.findByEntityKey(entityId).getParentEntityKey();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return parentEntityKey;
    }

    public List<IEntityRow> hasChildren(String entityId, IEntityTable entityQuerySet) {
        ArrayList<IEntityRow> childRows = new ArrayList();
        try {
            childRows = entityQuerySet.getChildRows(entityId);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return childRows;
    }

    public List<String> queryBaseDataList(String taskKey, String period) {
        ArrayList<String> unitList = new ArrayList<String>();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        IEntityDefine entityDefine = this.entityMetaService.queryEntityByCode("MD_WF_SEND_TODO_CONFIG");
        if (entityDefine == null) {
            logger.error("MD_WF_SEND_TODO_CONFIG\u6240\u5bf9\u5e94\u7684\u57fa\u7840\u6570\u636e\u4e0d\u5b58\u5728");
            return unitList;
        }
        StringBuffer filter = new StringBuffer();
        filter.append("[TASK] = '").append(taskDefine.getTaskCode()).append("' AND PERIOD = '").append(period).append("'");
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityDefine.getId(), filter.toString());
        try {
            IEntityTable entityQuerySet = this.entityManager.entityQuerySetByTask(entityViewDefine, taskKey, period);
            List allRows = entityQuerySet.getAllRows();
            if (allRows.size() > 0) {
                for (IEntityRow iEntityRow : allRows) {
                    String unit = iEntityRow.getAsString("UNIT");
                    unitList.add(unit);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return unitList;
    }
}

