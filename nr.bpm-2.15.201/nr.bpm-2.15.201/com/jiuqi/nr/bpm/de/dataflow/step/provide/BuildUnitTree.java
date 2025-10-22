/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.bpm.de.dataflow.step.provide;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepTree;
import com.jiuqi.nr.bpm.de.dataflow.step.inter.IBuildUnitTree;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityHelper;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BuildUnitTree
implements IBuildUnitTree {
    private String formSchemeKey;
    private DimensionValueSet dim;
    private CommonUtil commonUtil;
    private DeEntityHelper deEntityHelper;
    private DimensionUtil dimensionUtil;

    public BuildUnitTree() {
    }

    public BuildUnitTree(String formSchemeKey, DimensionValueSet dim, DimensionUtil dimensionUtil, DeEntityHelper deEntityHelper, CommonUtil commonUtil) {
        this.formSchemeKey = formSchemeKey;
        this.dim = dim;
        this.dimensionUtil = dimensionUtil;
        this.deEntityHelper = deEntityHelper;
        this.commonUtil = commonUtil;
    }

    @Override
    public List<StepTree> buildUnitData() {
        int level = 1;
        EntityViewDefine entityViewDefine = this.dimensionUtil.getDwEntityView(this.formSchemeKey);
        DimensionValueSet dimValue = new DimensionValueSet();
        if (this.dim != null) {
            Set<String> selectUnitId = this.selectUnitId(this.formSchemeKey, this.dim);
            if (this.dim.getValue("DATATIME") != null) {
                String period = this.dim.getValue("DATATIME").toString();
                dimValue.setValue("DATATIME", (Object)period);
                IEntityTable entityTable = this.deEntityHelper.getFullEntityTable(entityViewDefine, this.formSchemeKey, dimValue);
                List<IEntityRow> allRows = this.deEntityHelper.getEntityRow(this.formSchemeKey, period);
                return this.getChildren(entityTable, allRows, level, selectUnitId);
            }
        }
        return new ArrayList<StepTree>();
    }

    public List<StepTree> getChildren(IEntityTable entityTable, List<IEntityRow> allRows, int level, Set<String> selectUnitId) {
        ArrayList<StepTree> stepTrees = new ArrayList<StepTree>();
        StepTree stepTree = null;
        for (IEntityRow rowData : allRows) {
            if (selectUnitId == null || !selectUnitId.contains(rowData.getEntityKeyData())) continue;
            stepTree = new StepTree();
            stepTree.setId(rowData.getEntityKeyData());
            stepTree.setCode(rowData.getCode());
            stepTree.setTitle(rowData.getTitle());
            List<IEntityRow> entityRows = this.deEntityHelper.getDirectChildrenData(entityTable, rowData.getEntityKeyData()).stream().filter(e -> selectUnitId.contains(e.getEntityKeyData())).collect(Collectors.toList());
            stepTree.setSelectChildren(this.getChildren(entityTable, entityRows, level++, selectUnitId));
            stepTree.setEntityTable(entityTable);
            stepTree.setDirectChildren(entityTable.getChildRows(rowData.getEntityKeyData()));
            String parentEntityKey = this.deEntityHelper.getParent(entityTable, rowData.getEntityKeyData());
            if (parentEntityKey != null) {
                stepTree.setParented(true);
                stepTree.setParentId(parentEntityKey);
            }
            if (selectUnitId.contains(parentEntityKey)) {
                stepTree.setSelectParentId(parentEntityKey);
            }
            stepTree.setResourceMap(this.formId());
            List children = this.deEntityHelper.getDirectChildrenData(entityTable, rowData.getEntityKeyData()).stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
            if (children == null || children.size() == 0) {
                stepTree.setLeafed(true);
            }
            String[] parentsDataPath = entityTable.getParentsEntityKeyDataPath(rowData.getEntityKeyData());
            int length = parentsDataPath.length;
            stepTree.setLevel(length);
            stepTrees.add(stepTree);
        }
        return stepTrees;
    }

    public Set<String> selectUnitId(String formSchemeKey, DimensionValueSet dim) {
        HashSet<String> unitIds = new HashSet<String>();
        Object value = dim.getValue(this.dimensionUtil.getDwMainDimName(formSchemeKey));
        if (value instanceof String) {
            unitIds.add(value.toString());
        } else {
            unitIds.addAll((List)value);
        }
        return unitIds;
    }

    public LinkedHashSet<String> formId() {
        LinkedHashSet<String> formIds = new LinkedHashSet<String>();
        String formId = this.commonUtil.formId(this.formSchemeKey);
        if (formId != null) {
            formIds.add(this.commonUtil.formId(this.formSchemeKey));
        }
        return formIds;
    }
}

