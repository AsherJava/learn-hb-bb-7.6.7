/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.nr.calibre2.common.Result
 *  com.jiuqi.nr.calibre2.domain.CalibreDataDTO
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDTO
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.summary.executor.sum.engine.model;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.summary.executor.sum.SumContext;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeFloatExpandInfo;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryCell;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryParam;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryReport;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryCondition;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryConditionJudger;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryDataSet;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryRow;
import com.jiuqi.nr.summary.model.caliber.CaliberApplyType;
import com.jiuqi.nr.summary.model.caliber.CaliberExpandMode;
import com.jiuqi.nr.summary.model.caliber.CaliberFloatInfo;
import com.jiuqi.nr.summary.model.caliber.CaliberInfo;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.cell.MainCell;
import com.jiuqi.nr.summary.model.report.FilterInfo;
import com.jiuqi.nr.summary.model.report.SummaryFloatRegion;
import com.jiuqi.nr.summary.utils.SummaryReportModelHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RuntimeSummaryRegion {
    private SummaryFloatRegion regionDefine;
    private List<RuntimeSummaryCell> cells = new ArrayList<RuntimeSummaryCell>();
    private RuntimeSummaryReport ownner;
    private HashSet<String> targetTableCodes = new HashSet();
    private SummaryConditionJudger judger;
    private Map<String, SummaryCondition> conditions = new HashMap<String, SummaryCondition>();
    private Map<String, List<String>> floatDims = new HashMap<String, List<String>>();

    public RuntimeSummaryRegion(RuntimeSummaryReport ownner) {
        this.ownner = ownner;
        this.judger = new SummaryConditionJudger();
    }

    public RuntimeSummaryRegion(SummaryFloatRegion regionDefine, RuntimeSummaryReport ownner) {
        this.ownner = ownner;
        this.judger = new SummaryConditionJudger();
        this.regionDefine = regionDefine;
    }

    public void doInit(SumContext sumContext) throws Exception {
        SummaryReportModelHelper helper = this.ownner.getHelper();
        List<DataCell> dataCells = this.isFloat() ? helper.getFloatDataCells(this.regionDefine) : helper.getFixDataCells();
        for (DataCell cellDefine : dataCells) {
            if (StringUtils.isEmpty((String)cellDefine.getExp())) continue;
            RuntimeSummaryCell cell = this.addCell(sumContext, cellDefine);
            this.addFilterToCell(sumContext, helper, cell);
            this.addRowCalibreToCell(sumContext, helper, cell);
            this.addColCalibreToCell(sumContext, helper, cell);
        }
    }

    public SummaryDataSet prepareSummaryDataSet(SumContext sumContext) throws Exception {
        SummaryDataSet dataSet = this.createSummaryDataSet(sumContext);
        if (this.isFloat()) {
            List<RuntimeFloatExpandInfo> expandInfos = this.getExpandInfos(sumContext);
            DimensionValueSet dim = new DimensionValueSet();
            HashSet rowKeys = new HashSet();
            int rowCount = 1;
            for (RuntimeFloatExpandInfo expandInfo : expandInfos) {
                List<String> dimKeyList = expandInfo.getDimKeyList();
                dim.setValue(expandInfo.getDimensionName(), dimKeyList);
                rowCount *= dimKeyList.size();
            }
            if (rowCount > 1000000) {
                throw new Exception("\u6d6e\u52a8\u884c\u8d85\u8fc7100\u4e07\u884c\uff0c\u4e0d\u652f\u6301\u5185\u5b58\u5c55\u5f00");
            }
            ExpressionUtils.expandDims((DimensionValueSet)dim, rowKeys);
            for (DimensionValueSet rowKey : rowKeys) {
                SummaryRow summaryRow = this.createFloatSummaryRow(sumContext, expandInfos, rowKey);
                dataSet.getRows().add(summaryRow);
            }
        } else {
            SummaryRow fixSummaryRow = new SummaryRow(new DimensionValueSet(), this.cells);
            dataSet.getRows().add(fixSummaryRow);
        }
        return dataSet;
    }

    public String getCaliberFormula(SumContext sumContext, CaliberInfo caliberInfo) {
        String caliberFormula = null;
        RuntimeSummaryParam param = sumContext.getParam();
        if ("CB".equals(caliberInfo.getType())) {
            String calibreCode = caliberInfo.getFieldName();
            String calibreDataCode = caliberInfo.getValue().getValue();
            CalibreDefineDTO calibreDefine = this.getCaliberDefine(sumContext, calibreCode);
            if (calibreDefine.getType() != null && calibreDefine.getType() == 0) {
                IEntityDefine targetMdEntity = param.getTargetMdEntity();
                IEntityModel targetMdEntityModel = sumContext.getBeanSet().entityMetaService.getEntityModel(targetMdEntity.getId());
                StringBuilder buff = new StringBuilder();
                buff.append("InCalibre(").append(targetMdEntity.getCode()).append("[").append(targetMdEntityModel.getBizKeyField().getCode()).append("],");
                buff.append("\"").append(calibreCode).append("\",");
                buff.append("\"").append(calibreDataCode).append("\")");
                caliberFormula = buff.toString();
            } else {
                CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
                calibreDataDTO.setCalibreCode(calibreCode);
                calibreDataDTO.setCode(calibreDataCode);
                Result result = sumContext.getBeanSet().calibreDataService.get(calibreDataDTO);
                CalibreDataDTO calibreData = (CalibreDataDTO)result.getData();
                caliberFormula = (String)calibreData.getValue().getExpression();
            }
        } else {
            String[] strs = caliberInfo.getFieldName().split("\\.");
            String entityId = strs[0];
            String entityFieldName = strs[1];
            StringBuilder buff = new StringBuilder();
            if (param.getSourceRefTargetField() != null && entityId.equals(param.getTargetMdEntity().getId())) {
                IEntityDefine targetMdEntity = param.getTargetMdEntity();
                IEntityModel targetMdEntityModel = sumContext.getBeanSet().entityMetaService.getEntityModel(targetMdEntity.getId());
                if (entityFieldName.equals(targetMdEntityModel.getBizKeyField().getCode())) {
                    buff.append(sumContext.getBeanSet().entityMetaService.getEntityCode(param.getSourceMdEntity().getId())).append("[").append(param.getSourceRefTargetField().getCode()).append("]");
                } else {
                    buff.append("GetMasterData(\"").append(sumContext.getBeanSet().entityMetaService.getEntityCode(entityId)).append("\",");
                    buff.append(sumContext.getBeanSet().entityMetaService.getEntityCode(param.getSourceMdEntity().getId())).append("[").append(param.getSourceRefTargetField().getCode()).append("],");
                    buff.append("\"").append(entityFieldName).append("\")");
                }
            } else {
                buff.append(sumContext.getBeanSet().entityMetaService.getEntityCode(entityId)).append("[").append(entityFieldName).append("]");
            }
            buff.append("=\"").append(caliberInfo.getValue().getValue()).append("\"");
            caliberFormula = buff.toString();
        }
        return caliberFormula;
    }

    private SummaryRow createFloatSummaryRow(SumContext sumContext, List<RuntimeFloatExpandInfo> expandInfos, DimensionValueSet rowKey) throws CloneNotSupportedException, ParseException {
        ArrayList<RuntimeSummaryCell> rowCells = new ArrayList<RuntimeSummaryCell>(this.cells.size());
        for (RuntimeSummaryCell cell : this.cells) {
            RuntimeSummaryCell rowCell = (RuntimeSummaryCell)cell.clone();
            for (RuntimeFloatExpandInfo expandInfo : expandInfos) {
                String dimensionName = expandInfo.getDimensionName();
                String dimensionValue = (String)rowKey.getValue(dimensionName);
                String conditionFormula = expandInfo.getConditionFormula(dimensionValue);
                this.addCellCondition(sumContext, rowCell, dimensionName + ":" + dimensionValue, conditionFormula);
            }
            rowCells.add(rowCell);
        }
        SummaryRow summaryRow = new SummaryRow(rowKey, rowCells);
        return summaryRow;
    }

    private SummaryDataSet createSummaryDataSet(SumContext sumContext) throws Exception {
        IDataQuery dataQuery = sumContext.getBeanSet().dataAccessProvider.newDataQuery();
        for (RuntimeSummaryCell cell : this.cells) {
            int index = dataQuery.addColumn(cell.getTargetColumn());
            cell.setTargetFieldIndex(index);
        }
        int floatOrderIndex = -1;
        if (this.isFloat()) {
            floatOrderIndex = dataQuery.addExpressionColumn(this.regionDefine.getTableName() + "[FLOATORDER]");
        }
        dataQuery.setMasterKeys(sumContext.getTargetDimValues());
        IDataUpdator updator = dataQuery.openForUpdate(sumContext.getExeContext());
        updator.needCheckDuplicateKeys(false);
        return new SummaryDataSet(updator, floatOrderIndex);
    }

    private List<RuntimeFloatExpandInfo> getExpandInfos(SumContext sumContext) throws ParseException {
        IEntityMetaService entityMetaService = sumContext.getBeanSet().entityMetaService;
        List<MainCell> mainCells = this.ownner.getHelper().getMainCellsByRow(this.cells.get(0).getRow());
        HashMap<String, RuntimeFloatExpandInfo> expandInfos = new HashMap<String, RuntimeFloatExpandInfo>();
        if (mainCells != null) {
            for (int i = 0; i < mainCells.size(); ++i) {
                MainCell mainCell = mainCells.get(i);
                Optional<CaliberInfo> caliberInfo = mainCell.getCaliberInfos().stream().filter(o -> o.getApplyType() == CaliberApplyType.FLOAT).findAny();
                if (!caliberInfo.isPresent()) continue;
                CaliberFloatInfo floatInfo = caliberInfo.get().getFloatInfo();
                String[] strs = caliberInfo.get().getFieldName().split("\\.");
                String entityId = strs[0];
                String entityFieldName = strs[1];
                StringBuilder buff = new StringBuilder();
                IEntityModel entityModel = entityMetaService.getEntityModel(entityId);
                IEntityDefine entityDefine = entityMetaService.queryEntity(entityId);
                IEntityAttribute sourceRefTargetField = sumContext.getParam().getSourceRefTargetField();
                if (sourceRefTargetField != null && entityId.equals(sumContext.getParam().getTargetMdEntity().getId())) {
                    String sourceMdEntityCode = entityMetaService.getEntityCode(sumContext.getParam().getSourceMdEntity().getId());
                    if (entityModel.getBizKeyField().getCode().equals(entityFieldName)) {
                        buff.append(sourceMdEntityCode).append("[").append(sourceRefTargetField.getCode()).append("]");
                    } else {
                        List refers = entityMetaService.getEntityRefer(entityId);
                        Iterator iterator = refers.iterator();
                        while (iterator.hasNext()) {
                            IEntityRefer refer = (IEntityRefer)iterator.next();
                            if (!refer.getOwnField().equals(entityFieldName)) continue;
                            entityDefine = entityMetaService.queryEntity(refer.getReferEntityId());
                        }
                        buff.append("GetMasterData(\"").append(entityMetaService.getEntityCode(entityId)).append("\",");
                        buff.append(sourceMdEntityCode).append("[").append(sourceRefTargetField.getCode()).append("],");
                        buff.append("\"").append(entityFieldName).append("\")");
                    }
                } else {
                    if (!entityModel.getBizKeyField().getCode().equals(entityFieldName)) {
                        List refers = entityMetaService.getEntityRefer(entityId);
                        for (IEntityRefer refer : refers) {
                            if (!refer.getOwnField().equals(entityFieldName)) continue;
                            entityDefine = entityMetaService.queryEntity(refer.getReferEntityId());
                        }
                    }
                    buff.append(entityMetaService.getEntityCode(entityId)).append("[").append(entityFieldName).append("]");
                    buff.append("=");
                }
                List<IEntityRow> entityRows = this.getExpandEntityRows(sumContext, entityDefine, floatInfo);
                TableModelRunInfo tableInfo = sumContext.getTableModelRunInfoByDataTable(this.regionDefine.getTableName());
                ColumnModelDefine dimField = tableInfo.parseSearchField(mainCell.getInnerDimZb().getName());
                String targetDimName = tableInfo.getDimensionName(dimField.getCode());
                expandInfos.put(targetDimName, new RuntimeFloatExpandInfo(targetDimName, entityRows, buff.toString()));
            }
        }
        return new ArrayList<RuntimeFloatExpandInfo>(expandInfos.values());
    }

    private List<IEntityRow> getExpandEntityRows(SumContext sumContext, IEntityDefine entityDefine, CaliberFloatInfo floatInfo) {
        ArrayList<IEntityRow> result = new ArrayList<IEntityRow>();
        DimensionValueSet masterKeys = new DimensionValueSet();
        masterKeys.setValue("DATATIME", (Object)sumContext.getPeriodWrapper().toString());
        String filter = floatInfo.getFilter();
        RuntimeSummaryParam param = sumContext.getParam();
        IEntityDefine targetEntity = param.getTargetMdEntity();
        IEntityDefine sourceEntity = param.getSourceMdEntity();
        boolean expandSubEntity = entityDefine.getId().equals(sourceEntity.getId()) && !entityDefine.getId().equals(targetEntity.getId());
        String targetKey = (String)sumContext.getTargetDimValues().getValue(targetEntity.getDimensionName());
        if (floatInfo.getExpandMode().value() <= CaliberExpandMode.SELF.value()) {
            int level = 0;
            switch (floatInfo.getExpandMode()) {
                case SELF: {
                    if (expandSubEntity) break;
                    masterKeys.setValue(entityDefine.getDimensionName(), (Object)targetKey);
                    break;
                }
                case LIST: {
                    masterKeys.setValue(entityDefine.getDimensionName(), floatInfo.getValues());
                    break;
                }
                case LEVEL: {
                    level = floatInfo.getLevel();
                    break;
                }
            }
            Set<String> keySet = null;
            if (entityDefine.getId().equals(param.getTargetMdEntity().getId()) || expandSubEntity) {
                DimensionValueSet targetFullEntityDimValues = new DimensionValueSet();
                targetFullEntityDimValues.setValue("DATATIME", (Object)sumContext.getPeriodWrapper().toString());
                IEntityTable fullEntityTable = param.getEntityTable(sumContext, param.getTargetMdEntity().getId(), targetFullEntityDimValues, null, AuthorityType.Read);
                List allChilds = fullEntityTable.getAllChildRows(targetKey);
                keySet = allChilds.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet());
                keySet.add(targetKey);
            }
            IEntityTable entityTable = param.getEntityTable(sumContext, entityDefine.getId(), masterKeys, filter, AuthorityType.Read);
            List allRows = entityTable.getAllRows();
            if (level > 0) {
                for (IEntityRow entityRow : allRows) {
                    if (keySet != null && !this.judgeByTargetKeySet(keySet, entityRow, param.getSourceRefTargetField()) || entityRow.getParentsEntityKeyDataPath().length + 1 > level) continue;
                    result.add(entityRow);
                }
            } else {
                for (IEntityRow entityRow : allRows) {
                    if (keySet != null && !this.judgeByTargetKeySet(keySet, entityRow, param.getSourceRefTargetField())) continue;
                    result.add(entityRow);
                }
            }
        } else if (!expandSubEntity) {
            IEntityTable entityTable = param.getEntityTable(sumContext, targetEntity.getId(), masterKeys, filter, AuthorityType.Read);
            IEntityRow self = entityTable.findByEntityKey(targetKey);
            if (self == null) {
                return result;
            }
            switch (floatInfo.getExpandMode()) {
                case SELF_DIRECT: {
                    result.add(self);
                    result.addAll(entityTable.getChildRows(self.getEntityKeyData()));
                    break;
                }
                case SELF_ALL: {
                    result.add(self);
                    result.addAll(entityTable.getAllChildRows(self.getEntityKeyData()));
                    break;
                }
                case DIRECT: {
                    result.addAll(entityTable.getChildRows(self.getEntityKeyData()));
                    break;
                }
                case ALL_CHILD: {
                    result.addAll(entityTable.getAllChildRows(self.getEntityKeyData()));
                    break;
                }
            }
        } else {
            IEntityTable targetEntityTable = param.getEntityTable(sumContext, entityDefine.getId(), masterKeys, null, AuthorityType.Read);
            IEntityRow self = targetEntityTable.findByEntityKey(targetKey);
            ArrayList<IEntityRow> targetRows = new ArrayList<IEntityRow>();
            if (self == null) {
                return result;
            }
            switch (floatInfo.getExpandMode()) {
                case SELF_DIRECT: {
                    targetRows.add(self);
                    targetRows.addAll(targetEntityTable.getChildRows(self.getEntityKeyData()));
                    break;
                }
                case SELF_ALL: {
                    targetRows.add(self);
                    targetRows.addAll(targetEntityTable.getAllChildRows(self.getEntityKeyData()));
                    break;
                }
                case DIRECT: {
                    targetRows.addAll(targetEntityTable.getChildRows(self.getEntityKeyData()));
                    break;
                }
                case ALL_CHILD: {
                    targetRows.addAll(targetEntityTable.getAllChildRows(self.getEntityKeyData()));
                    break;
                }
            }
            StringBuilder srcFilter = new StringBuilder();
            if (StringUtils.isNotEmpty((String)filter)) {
                srcFilter.append("(").append(filter).append(") and ");
            }
            srcFilter.append("(");
            srcFilter.append("[").append(param.getSourceRefTargetField().getCode()).append("]").append(" in {");
            targetRows.forEach(o -> srcFilter.append("'").append(o.getEntityKeyData()).append("',"));
            srcFilter.setLength(filter.length() - 1);
            srcFilter.append("}");
            srcFilter.append(")");
            IEntityTable srcEntityTable = param.getEntityTable(sumContext, entityDefine.getId(), masterKeys, srcFilter.toString(), AuthorityType.Read);
            result.addAll(srcEntityTable.getAllRows());
        }
        return result;
    }

    private boolean judgeByTargetKeySet(Set<String> keySet, IEntityRow entityRow, IEntityAttribute entityField) {
        if (entityField != null) {
            return keySet.contains(entityRow.getAsString(entityField.getCode()));
        }
        return keySet.contains(entityRow.getEntityKeyData());
    }

    private void addColCalibreToCell(SumContext sumContext, SummaryReportModelHelper helper, RuntimeSummaryCell cell) throws ParseException {
        List<CaliberInfo> colCaliberInfos = helper.getCaliberInfoByCol(cell.getCol());
        if (colCaliberInfos != null) {
            for (CaliberInfo caliberInfo : colCaliberInfos) {
                this.addCellCondition(sumContext, cell, "colCaliber_" + caliberInfo.toString(), this.getCaliberFormula(sumContext, caliberInfo));
            }
        }
    }

    private void addRowCalibreToCell(SumContext sumContext, SummaryReportModelHelper helper, RuntimeSummaryCell cell) throws ParseException {
        List<CaliberInfo> rowCaliberInfos = helper.getCaliberInfoByRow(cell.getRow());
        if (rowCaliberInfos != null) {
            for (CaliberInfo caliberInfo : rowCaliberInfos) {
                if (caliberInfo.getApplyType() != CaliberApplyType.FILTER) continue;
                this.addCellCondition(sumContext, cell, "rowCaliber_" + caliberInfo.toString(), this.getCaliberFormula(sumContext, caliberInfo));
            }
        }
    }

    private void addFilterToCell(SumContext sumContext, SummaryReportModelHelper helper, RuntimeSummaryCell cell) throws ParseException {
        FilterInfo colFilter;
        FilterInfo rowFilter;
        if (this.ownner.getCondition() != null) {
            String conditionKey = "report";
            SummaryCondition condition = this.conditions.get(conditionKey);
            if (condition == null) {
                condition = this.judger.addCondition(sumContext, conditionKey, helper.getReportFilter(), this.ownner.getCondition());
                this.conditions.put(conditionKey, condition);
            }
            cell.addCondition(condition);
        }
        if ((rowFilter = helper.getFilterInfoByRow(cell.getRow())) != null) {
            this.addCellCondition(sumContext, cell, "rowFilter_" + cell.getRow(), rowFilter.getExpression());
        }
        if ((colFilter = helper.getFilterInfoByCol(cell.getCol())) != null) {
            this.addCellCondition(sumContext, cell, "colFilter_" + cell.getCol(), colFilter.getExpression());
        }
    }

    private void addCellCondition(SumContext sumContext, RuntimeSummaryCell cell, String conditionKey, String conditionFormula) throws ParseException {
        if (conditionFormula != null && StringUtils.isNotEmpty((String)conditionFormula)) {
            SummaryCondition condition = this.conditions.get(conditionKey);
            if (condition == null) {
                condition = this.judger.addCondition(sumContext, conditionKey, conditionFormula);
                if (condition == null) {
                    return;
                }
                this.conditions.put(conditionKey, condition);
            }
            cell.addCondition(condition);
        }
    }

    private RuntimeSummaryCell addCell(SumContext sumContext, DataCell cellDefine) throws Exception {
        RuntimeSummaryCell cell = new RuntimeSummaryCell(cellDefine);
        cell.doInit(sumContext);
        this.cells.add(cell);
        this.targetTableCodes.add(cell.getTableCode());
        return cell;
    }

    public boolean supportSQLMode(SumContext sumContext) {
        return false;
    }

    private CalibreDefineDTO getCaliberDefine(SumContext sumContext, String calibreDefineCode) {
        CalibreDefineDTO defineDTO = new CalibreDefineDTO();
        defineDTO.setCode(calibreDefineCode);
        CalibreDefineDTO calibreDefine = (CalibreDefineDTO)sumContext.getBeanSet().calibreDefineService.get(defineDTO).getData();
        return calibreDefine;
    }

    public List<RuntimeSummaryCell> getCells() {
        return this.cells;
    }

    public boolean isFloat() {
        return this.regionDefine != null;
    }

    public RuntimeSummaryReport getOwnner() {
        return this.ownner;
    }

    public int getRowNum() {
        return this.regionDefine == null ? -1 : this.regionDefine.getPosition();
    }

    public Map<String, SummaryCondition> getConditions() {
        return this.conditions;
    }

    public SummaryFloatRegion getRegionDefine() {
        return this.regionDefine;
    }

    public Map<String, List<String>> getFloatDims() {
        return this.floatDims;
    }

    public HashSet<String> getTargetTableCodes() {
        return this.targetTableCodes;
    }

    public SummaryConditionJudger getJudger() {
        return this.judger;
    }

    public String toString() {
        return "RuntimeSummaryRegion [cells=" + this.cells + ", conditions=" + this.conditions + "]";
    }
}

