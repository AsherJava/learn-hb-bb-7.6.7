/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.VariableDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.np.definition.provider.DimensionTable
 *  com.jiuqi.nr.definition.formulatracking.ExpressionVariable
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.IFieldsInfo
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.function.func.cache;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.nr.definition.formulatracking.ExpressionVariable;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UnitTreeCacheInfo {
    public static final String NAME = "UnitTreeCacheInfo";
    public static final Set<String> VALID_BBLX = new HashSet<String>(Arrays.asList("0", "2", "3", "4", "5"));
    private String bblxFieldCode;
    private Map<String, String> zdmToKeyMap = null;
    private Map<String, String> keyToZDMMap = null;
    private IEntityTable dimTable;
    private Integer dwxz = null;
    private HashMap<String, HashMap<String, Integer>> unitCountLevels = new HashMap();
    private HashMap<String, Integer> unitLevels = new HashMap();

    public String getZdmByKey(String key) {
        String zdm;
        if (this.keyToZDMMap != null && (zdm = this.keyToZDMMap.get(key)) != null) {
            return zdm;
        }
        return key;
    }

    public String getKeyByZdm(String zdm) {
        String key;
        if (this.zdmToKeyMap != null && (key = this.zdmToKeyMap.get(zdm)) != null) {
            return key;
        }
        return zdm;
    }

    public void doInit(QueryContext qContext, IASTNode unitParam, String offSetPeriod, String cacheKey, boolean byZdm) {
        String unitDimension = qContext.getExeContext().getUnitDimension();
        this.dimTable = this.getDimTable(qContext, unitDimension, offSetPeriod);
        IEntityAttribute bblxField = this.dimTable.getEntityModel().getBblxField();
        if (bblxField != null) {
            this.bblxFieldCode = bblxField.getCode();
        }
        if (byZdm) {
            this.buildZdmCache(qContext, unitParam, cacheKey);
        }
    }

    public HashMap<String, Integer> getUnitCountLevels(IEntityRow root) {
        String rootKey = root.getEntityKeyData();
        HashMap<String, Integer> unitLevels = this.getUnitCountLevels().get(rootKey);
        if (unitLevels == null) {
            unitLevels = new HashMap();
            this.getUnitCountLevels().put(rootKey, unitLevels);
            List allRows = this.dimTable.getAllChildRows(rootKey);
            allRows.add(root);
            for (IEntityRow entityRow : allRows) {
                Integer rowLevel = unitLevels.get(entityRow.getEntityKeyData());
                if (rowLevel != null) continue;
                rowLevel = this.getRowLevel(root, entityRow, this.bblxFieldCode, this.dimTable, unitLevels);
                unitLevels.put(entityRow.getEntityKeyData(), rowLevel);
            }
        }
        return unitLevels;
    }

    public IEntityRow getRoot(IEntityRow currentEntityRow) {
        IEntityRow parent;
        IEntityRow root = this.dimTable.findByEntityKey(currentEntityRow.getParentEntityKey());
        if (root == null) {
            root = currentEntityRow;
        }
        String rootBblx = root.getAsString(this.bblxFieldCode);
        while (!rootBblx.equals("7") && !rootBblx.equals("H") && (parent = this.dimTable.findByEntityKey(root.getParentEntityKey())) != null) {
            root = parent;
            rootBblx = parent.getAsString(this.bblxFieldCode);
        }
        return root;
    }

    private int getRowLevel(IEntityRow rootRow, IEntityRow entityRow, String bblxCode, IEntityTable dimTable, Map<String, Integer> unitLevels) {
        int rowLevel = 1;
        String rowBblx = entityRow.getAsString(bblxCode);
        if (entityRow == rootRow) {
            if (rowBblx.equals("7") || rowBblx.equals("H")) {
                rowLevel = 0;
            }
            unitLevels.put(entityRow.getEntityKeyData(), rowLevel);
            return rowLevel;
        }
        String parent = entityRow.getParentEntityKey();
        IEntityRow parentRow = dimTable.findByEntityKey(parent);
        Integer parentLevel = unitLevels.get(parent);
        if (parentLevel == null) {
            if (parentRow != null) {
                parentLevel = this.getRowLevel(rootRow, parentRow, bblxCode, dimTable, unitLevels);
            }
            if (parentLevel == null) {
                parentLevel = 0;
            }
        }
        if ((rowBblx.equals("7") || rowBblx.equals("H")) && (parentRow == null || parentLevel == 0)) {
            rowLevel = 0;
        }
        rowLevel += parentLevel.intValue();
        if (parentRow != null) {
            String rowZDM = this.getZdmByKey(entityRow.getEntityKeyData());
            String parentZDM = this.getZdmByKey(parentRow.getEntityKeyData());
            if (VALID_BBLX.contains(rowBblx) && rowZDM.substring(0, rowZDM.length() - 1).equals(parentZDM.substring(0, parentZDM.length() - 1)) && !"7".equals(parentRow.getAsString(this.bblxFieldCode))) {
                --rowLevel;
            }
        }
        unitLevels.put(entityRow.getEntityKeyData(), rowLevel);
        return rowLevel;
    }

    public IEntityTable getDimTable() {
        return this.dimTable;
    }

    public HashMap<String, HashMap<String, Integer>> getUnitCountLevels() {
        return this.unitCountLevels;
    }

    public String getBblxFieldCode() {
        return this.bblxFieldCode;
    }

    private void putToMap(String zdm, String key) {
        if (this.zdmToKeyMap == null) {
            this.zdmToKeyMap = new HashMap<String, String>();
        }
        if (this.keyToZDMMap == null) {
            this.keyToZDMMap = new HashMap<String, String>();
        }
        this.zdmToKeyMap.put(zdm, key);
        this.keyToZDMMap.put(key, zdm);
    }

    private void buildZdmCache(QueryContext qContext, IASTNode unitParam, String cacheKey) {
        VariableDataNode variableDataNode;
        ExpressionVariable var = null;
        if (unitParam instanceof VariableDataNode && (variableDataNode = (VariableDataNode)unitParam).getVariable().getVarName().equals("SYS_ZDM")) {
            var = (ExpressionVariable)variableDataNode.getVariable();
        }
        if (var == null) {
            try {
                IExpression zdmExp = qContext.getExeContext().getCache().getFormulaParser(true).parseEval("[SYS_ZDM]", (IContext)qContext);
                IASTNode root = zdmExp.getChild(0);
                if (root instanceof VariableDataNode) {
                    VariableDataNode variableDataNode2 = (VariableDataNode)root;
                    var = (ExpressionVariable)variableDataNode2.getVariable();
                }
            }
            catch (Exception zdmExp) {
                // empty catch block
            }
        }
        if (var != null) {
            String zdmEvalFormula = var.getExpression();
            zdmEvalFormula = zdmEvalFormula.replace("DWDM", "CODE");
            IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
            MemoryDataSet dataSet = new MemoryDataSet();
            dataSet.getMetadata().addColumn(new Column("ENTITY_KEY", 6));
            IFieldsInfo fieldsInfo = this.dimTable.getFieldsInfo();
            for (int i = 0; i < fieldsInfo.getFieldCount(); ++i) {
                IEntityAttribute attribute = fieldsInfo.getFieldByIndex(i);
                int dataType = attribute.getColumnType().getValue();
                if (dataType == ColumnModelType.INTEGER.getValue()) {
                    dataType = 3;
                }
                dataSet.getMetadata().addColumn(new Column(attribute.getCode(), dataType));
            }
            List allRows = this.dimTable.getAllRows();
            for (IEntityRow entityRow : allRows) {
                DataRow row = dataSet.add();
                row.setValue(0, (Object)entityRow.getEntityKeyData());
                for (int i = 0; i < fieldsInfo.getFieldCount(); ++i) {
                    row.setValue(i + 1, entityRow.getValue(i).getAsObject());
                }
                try {
                    row.commit();
                }
                catch (DataSetException e) {
                    qContext.getMonitor().exception((Exception)((Object)e));
                }
            }
            IDataSetExprEvaluator evaluator = dataAccessProvider.newDataSetExprEvaluator((DataSet)dataSet);
            try {
                DimensionValueSet marsterKeys = new DimensionValueSet(qContext.getCurrentMasterKey());
                evaluator.prepare(qContext.getExeContext(), marsterKeys, zdmEvalFormula);
                for (DataRow row : dataSet) {
                    String entityDataKey = row.getString(0);
                    AbstractData zdmValue = evaluator.evaluate(row);
                    String zdm = zdmValue.getAsString();
                    this.putToMap(zdm, entityDataKey);
                }
            }
            catch (Exception e) {
                qContext.getMonitor().exception(e);
            }
        }
        qContext.getCache().put(cacheKey, this);
    }

    private IEntityTable getDimTable(QueryContext qContext, String dimensionName, String offSetPeriod) {
        if (this.dimTable == null) {
            IEntityDataService entityDataService = (IEntityDataService)SpringBeanProvider.getBean(IEntityDataService.class);
            ReportFmlExecEnvironment rEnv = null;
            IFmlExecEnvironment env = qContext.getExeContext().getEnv();
            if (env instanceof ReportFmlExecEnvironment) {
                rEnv = (ReportFmlExecEnvironment)env;
            }
            if (rEnv == null) {
                return null;
            }
            EntityViewDefine entityViewDefine = rEnv.getEntityViewDefine(qContext.getExeContext(), dimensionName);
            IEntityQuery iEntityQuery = entityDataService.newEntityQuery();
            DimensionValueSet marsterKeys = new DimensionValueSet(qContext.getMasterKeys());
            if (offSetPeriod != null) {
                marsterKeys.setValue("DATATIME", (Object)offSetPeriod);
            }
            marsterKeys.clearValue(dimensionName);
            marsterKeys.clearValue("VERSIONID");
            iEntityQuery.setEntityView(entityViewDefine);
            iEntityQuery.setMasterKeys(marsterKeys);
            try {
                return iEntityQuery.executeFullBuild(null);
            }
            catch (Exception e) {
                qContext.getMonitor().exception(e);
            }
        }
        return this.dimTable;
    }

    public int getDwxz(QueryContext qContext, String bblx) {
        if (this.dwxz == null) {
            ReportFmlExecEnvironment rEnv = null;
            IFmlExecEnvironment env = qContext.getExeContext().getEnv();
            if (env instanceof ReportFmlExecEnvironment) {
                rEnv = (ReportFmlExecEnvironment)env;
            }
            if (rEnv == null) {
                this.dwxz = -1;
                return this.dwxz;
            }
            try {
                DimensionRow row;
                DimensionTable bblxTable = qContext.getFullDimTable(rEnv.findEntityTableCode("BBLX"));
                if (bblxTable != null && (row = bblxTable.findRowByKey(bblx)) != null) {
                    this.dwxz = (Integer)row.getValue("UNIT_NATURE");
                }
            }
            catch (Exception e) {
                qContext.getMonitor().exception(e);
            }
            if (this.dwxz == null) {
                this.dwxz = -1;
            }
        }
        return this.dwxz;
    }

    public int getUnitLevel(QueryContext qContext, String unitKey) {
        Integer level = this.unitLevels.get(unitKey);
        if (level == null) {
            IEntityRow row = this.dimTable.findByEntityKey(unitKey);
            if (row != null) {
                String[] parents = row.getParentsEntityKeyDataPath();
                level = 1;
                String bblx = row.getAsString(this.bblxFieldCode);
                if (parents.length == 0) {
                    if ("9".equals(bblx) || "7".equals(bblx)) {
                        level = 0;
                    }
                } else {
                    IEntityRow parentRow;
                    int rootLevel = this.getUnitLevel(qContext, parents[0]);
                    level = parents.length + rootLevel;
                    String rowZDM = this.getZdmByKey(unitKey);
                    String parentZDM = this.getZdmByKey(row.getParentEntityKey());
                    if (VALID_BBLX.contains(bblx) && rowZDM.substring(0, rowZDM.length() - 1).equals(parentZDM.substring(0, parentZDM.length() - 1)) && !"7".equals((parentRow = this.dimTable.findByEntityKey(row.getParentEntityKey())).getAsString(this.bblxFieldCode))) {
                        Integer n = level;
                        Integer n2 = level = Integer.valueOf(level - 1);
                    }
                }
            }
            if (level == null) {
                level = 0;
            }
            this.unitLevels.put(unitKey, level);
        }
        return level;
    }
}

