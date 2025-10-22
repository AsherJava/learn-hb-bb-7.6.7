/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMainDimFilter
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.calibre2.ICalibreDataRegionService
 *  com.jiuqi.nr.calibre2.ICalibreDefineService
 *  com.jiuqi.nr.calibre2.domain.CalibreDataRegion
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDO
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDTO
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.bi.dataset.report.tree;

import com.jiuqi.bi.dataset.report.model.ReportDSModel;
import com.jiuqi.bi.dataset.report.query.ReportQueryContext;
import com.jiuqi.bi.dataset.report.tree.SortedUnitTree;
import com.jiuqi.bi.dataset.report.tree.UnitRow;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMainDimFilter;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.calibre2.ICalibreDataRegionService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.domain.CalibreDataRegion;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnitDetailTreeService {
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private ICalibreDataRegionService calibreDataRegionService;
    @Autowired
    private ICalibreDefineService calibreDefineService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;

    public SortedUnitTree createSortedUnitTree(ReportQueryContext context, ReportDSModel dsModel) throws Exception {
        DimensionValueSet marsterKeys = new DimensionValueSet();
        marsterKeys.setValue("DATATIME", (Object)context.getEntityVeriosnPeriod());
        String unitDimeniosn = dsModel.getUnitEntityDefnie().getDimensionName();
        marsterKeys.setValue(unitDimeniosn, context.getMasterKeys().getValue(unitDimeniosn));
        IEntityTable mainEntityTable = this.getSortedEntityTable(dsModel.getUnitEntityId(), this.createEntityExecutorContext(context, dsModel, dsModel.getTaskDefine()), marsterKeys);
        SortedUnitTree sortedUnitTree = new SortedUnitTree(dsModel);
        if (dsModel.isShowDetail()) {
            ArrayList<UnitRow> allRowList = new ArrayList<UnitRow>();
            Map<String, List<String>> detailUnitsMap = this.buildDetailUnitsMap(context, dsModel);
            List rootRows = mainEntityTable.getRootRows();
            this.algorithmAddUnitRows(mainEntityTable, detailUnitsMap, allRowList, rootRows);
            for (int i = 0; i < allRowList.size(); ++i) {
                UnitRow unitRow = (UnitRow)allRowList.get(i);
                if (unitRow.isDetail()) {
                    sortedUnitTree.putDetailUnitToTree(unitRow.getUnitKey(), unitRow.getUnitParent(), i);
                    continue;
                }
                sortedUnitTree.putMainUnitToTree(unitRow.getUnitKey(), i);
            }
        } else {
            List allRows = mainEntityTable.getAllRows();
            for (int i = 0; i < allRows.size(); ++i) {
                sortedUnitTree.putMainUnitToTree(((IEntityRow)allRows.get(i)).getEntityKeyData(), i);
            }
        }
        return sortedUnitTree;
    }

    public ExecutorContext createEntityExecutorContext(ReportQueryContext context, ReportDSModel dsModel, TaskDefine taskDefine) {
        ExecutorContext entityExecutorContext = new ExecutorContext(this.dataDefinitionController);
        entityExecutorContext.setPeriodView(taskDefine.getDateTime());
        IFmlExecEnvironment env = context.getExeContext().getEnv();
        if (env != null) {
            entityExecutorContext.setEnv(env);
        }
        VariableManager variableManager = entityExecutorContext.getVariableManager();
        if (dsModel != null && StringUtils.isNotEmpty((String)dsModel.getReportDsModelDefine().getGatherSchemeCode())) {
            this.addGatherSchemeVariables(dsModel, variableManager);
        } else {
            this.clearGatherSchemeVariables(variableManager);
        }
        return entityExecutorContext;
    }

    public void addGatherSchemeVariables(ReportDSModel dsModel, VariableManager variableManager) {
        String varDataScheme = "NR.var.dataScheme";
        variableManager.add(new Variable(varDataScheme, "\u6e90\u6570\u636e\u65b9\u6848key", 6, (Object)dsModel.getTaskDefine().getDataScheme()));
        String varGatherScheme = "batchGatherSchemeCode";
        variableManager.add(new Variable(varGatherScheme, "\u6c47\u603b\u65b9\u6848\u4ee3\u7801", 6, (Object)dsModel.getGatherSchemeKey()));
        variableManager.add(new Variable("dimType", "\u7ef4\u5ea6\u7c7b\u578b", 4, (Object)dsModel.getGaterDimType()));
        variableManager.add(new Variable("dimValue", "\u7ef4\u5ea6\u6807\u8bc6", 6, (Object)dsModel.getGaterDimName()));
    }

    public void clearGatherSchemeVariables(VariableManager variableManager) {
        variableManager.remove("NR.var.dataScheme");
        variableManager.remove("batchGatherSchemeCode");
        variableManager.remove("dimType");
        variableManager.remove("dimValue");
    }

    private Map<String, List<String>> buildDetailUnitsMap(ReportQueryContext context, ReportDSModel dsModel) throws Exception {
        final Map<String, Integer> detailOrders = this.getDetailOrders(context, dsModel);
        HashMap<String, List<String>> detailUnitsMap = new HashMap<String, List<String>>();
        DimensionValueSet contextMasterKeys = context.getMasterKeys();
        DimensionValueSet masterKeys = this.createDetailMasterKeys(context, dsModel, detailOrders, contextMasterKeys);
        Comparator<String> unitOrderComparator = new Comparator<String>(){

            @Override
            public int compare(String o1, String o2) {
                return (Integer)detailOrders.get(o1) - (Integer)detailOrders.get(o2);
            }
        };
        if (dsModel.getGaterDimType() == 1) {
            IMainDimFilter mainDimFilter = this.dataAccessProvider.newMainDimFilter();
            List<Formula> filterFormulas = this.buildBDFilterFormulas(dsModel, contextMasterKeys);
            Map result = mainDimFilter.filterByFormulas(context.getExeContext(), masterKeys, filterFormulas, null);
            for (Map.Entry entry : result.entrySet()) {
                String gatherUnit = (String)entry.getKey();
                List detailUnits = (List)entry.getValue();
                if (detailUnits == null || detailUnits.isEmpty()) continue;
                Collections.sort(detailUnits, unitOrderComparator);
                detailUnitsMap.put(gatherUnit, detailUnits);
            }
        } else {
            CalibreDefineDTO calibreDefine = this.getCaliberDefine(dsModel.getGaterDimName());
            List list = this.calibreDataRegionService.getList(context.getExeContext(), (CalibreDefineDO)calibreDefine, masterKeys);
            for (CalibreDataRegion region : list) {
                String gatherUnit = region.getCalibreData().getCode();
                List detailUnits = region.getEntityKeys();
                if (detailUnits == null || detailUnits.isEmpty()) continue;
                Collections.sort(detailUnits, unitOrderComparator);
                detailUnitsMap.put(gatherUnit, detailUnits);
            }
        }
        return detailUnitsMap;
    }

    protected List<Formula> buildBDFilterFormulas(ReportDSModel dsModel, DimensionValueSet contextMasterKeys) {
        String bdEntityId = dsModel.getGaterDimName();
        IEntityRefer refer = null;
        List entityRefers = this.entityMetaService.getEntityRefer(dsModel.getUnitEntityId());
        for (IEntityRefer entityRefer : entityRefers) {
            if (!entityRefer.getReferEntityId().equals(bdEntityId)) continue;
            refer = entityRefer;
            break;
        }
        ArrayList<Formula> filterFormulas = new ArrayList<Formula>();
        if (refer != null) {
            List units = (List)contextMasterKeys.getValue(dsModel.getUnitEntityDefnie().getDimensionName());
            for (String unitKey : units) {
                Formula formula = new Formula();
                formula.setId(unitKey);
                formula.setCode(unitKey);
                formula.setReportName("FILTER");
                formula.setFormula(refer.getOwnField() + "=\"" + unitKey + "\"");
                filterFormulas.add(formula);
            }
        }
        return filterFormulas;
    }

    protected DimensionValueSet createDetailMasterKeys(ReportQueryContext context, ReportDSModel dsModel, Map<String, Integer> detailOrders, DimensionValueSet contextMasterKeys) {
        DimensionValueSet masterKeys = new DimensionValueSet(contextMasterKeys);
        for (int i = 0; i < contextMasterKeys.size(); ++i) {
            String dimName = contextMasterKeys.getName(i);
            Object dimValue = contextMasterKeys.getValue(i);
            if (dimName.equals(dsModel.getUnitEntityDefnie().getDimensionName())) {
                masterKeys.setValue(dimName, new ArrayList<String>(detailOrders.keySet()));
                continue;
            }
            if (dimName.equals("DATATIME")) {
                masterKeys.setValue(dimName, (Object)context.getEntityVeriosnPeriod());
                continue;
            }
            if (!(dimValue instanceof List)) continue;
            masterKeys.setValue(dimName, ((List)dimValue).get(0));
        }
        return masterKeys;
    }

    protected Map<String, Integer> getDetailOrders(ReportQueryContext context, ReportDSModel dsModel) throws Exception {
        DimensionValueSet detailEntityMasterKeys = new DimensionValueSet();
        detailEntityMasterKeys.setValue("DATATIME", (Object)context.getEntityVeriosnPeriod());
        IEntityTable detailEntityTable = this.getSortedEntityTable(dsModel.getUnitEntityId(), this.createEntityExecutorContext(context, null, dsModel.getTaskDefine()), detailEntityMasterKeys);
        HashMap<String, Integer> detailOrders = new HashMap<String, Integer>();
        List allDetailRows = detailEntityTable.getAllRows();
        for (int i = 0; i < allDetailRows.size(); ++i) {
            detailOrders.put(((IEntityRow)allDetailRows.get(i)).getEntityKeyData(), i);
        }
        return detailOrders;
    }

    private CalibreDefineDTO getCaliberDefine(String caliberDefineCode) {
        CalibreDefineDTO defineDTO = new CalibreDefineDTO();
        defineDTO.setCode(caliberDefineCode);
        CalibreDefineDTO calibreDefine = (CalibreDefineDTO)this.calibreDefineService.get(defineDTO).getData();
        return calibreDefine;
    }

    private void algorithmAddUnitRows(IEntityTable mainEntityTable, Map<String, List<String>> detailUnitsMap, List<UnitRow> allRowList, List<IEntityRow> rows) {
        for (IEntityRow row : rows) {
            List<String> detailUnits;
            String unitKey = row.getEntityKeyData();
            allRowList.add(new UnitRow(unitKey, row.getParentEntityKey(), false));
            List childRows = mainEntityTable.getChildRows(unitKey);
            if (childRows != null && !childRows.isEmpty()) {
                this.algorithmAddUnitRows(mainEntityTable, detailUnitsMap, allRowList, childRows);
            }
            if ((detailUnits = detailUnitsMap.get(unitKey)) == null || detailUnits.isEmpty()) continue;
            for (String detailUnit : detailUnits) {
                allRowList.add(new UnitRow(detailUnit, unitKey, true));
            }
        }
    }

    public IEntityTable getSortedEntityTable(String entityId, ExecutorContext entityExecutorContext, DimensionValueSet marsterKeys) throws Exception {
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setAuthorityOperations(AuthorityType.Read);
        iEntityQuery.setMasterKeys(marsterKeys);
        iEntityQuery.setEntityView(this.iEntityViewRunTimeController.buildEntityView(entityId));
        iEntityQuery.sorted(true);
        return iEntityQuery.executeFullBuild((IContext)entityExecutorContext);
    }
}

