/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSCalcField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.tuples.KeyValue
 */
package com.jiuqi.bi.quickreport.engine.interaction;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSCalcField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.quickreport.engine.IEngineListener;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.area.GridArea;
import com.jiuqi.bi.quickreport.engine.area.ReportAreaExpcetion;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.interaction.ReportInteractionException;
import com.jiuqi.bi.quickreport.engine.layer.CalcLayer;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFormulaInfo;
import com.jiuqi.bi.quickreport.engine.result.CellFilterInfo;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorkbook;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.tuples.KeyValue;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

final class FilterQueryListener
implements IEngineListener {
    private final CellFilterInfo filterInfo;
    private ReportContext context;
    private MemoryDataSet<Object> result;
    private String nameField;
    private String titleField;
    private Format titleFormat;
    private String parentField;
    private static final String FIELD_PREFIX = "CF_";

    public FilterQueryListener(CellFilterInfo filterInfo) {
        this.filterInfo = filterInfo;
    }

    @Override
    public boolean begin(ReportContext context) throws ReportEngineException {
        this.context = context;
        return true;
    }

    @Override
    public boolean analyse(List<GridArea> areas, List<CalcLayer> layers) throws ReportEngineException {
        ExpandingArea expandingArea = areas.stream().filter(area -> area.getSheetName().equals(this.context.getReport().getPrimarySheetName())).filter(ExpandingArea.class::isInstance).map(ExpandingArea.class::cast).filter(area -> area.getRegion().contains(this.filterInfo.getPosition())).findAny().orElseThrow(() -> new ReportInteractionException("\u65e0\u6cd5\u5b9a\u4f4d\u8fc7\u6ee4\u6570\u636e\u7684\u533a\u57df\u4f4d\u7f6e\uff1a" + this.filterInfo.getPosition()));
        CellBindingInfo filterCell = expandingArea.getCells().stream().filter(cell -> cell.getPosition().getPosition().equals((Object)this.filterInfo.getPosition())).findAny().orElseThrow(() -> new ReportInteractionException("\u65e0\u6cd5\u5b9a\u4f4d\u8fc7\u6ee4\u6570\u636e\u7684\u5355\u5143\u683c\u4fe1\u606f\uff1a" + this.filterInfo.getPosition()));
        this.query(expandingArea, filterCell);
        return false;
    }

    @Override
    public boolean end(EngineWorkbook workbook) throws ReportEngineException {
        this.context.clearCache(this.filterInfo.getDataSetName());
        return false;
    }

    public MemoryDataSet<Object> getResult() {
        return Objects.requireNonNull(this.result);
    }

    private void query(ExpandingArea expandingArea, CellBindingInfo filterCell) throws ReportEngineException {
        DSModel model = this.openQueryModel(filterCell);
        MemoryDataSet<BIDataSetFieldInfo> dataSet = this.queryDataSet(expandingArea, model);
        this.result = this.createResultSet(dataSet);
    }

    private DSModel openQueryModel(CellBindingInfo filterCell) throws ReportEngineException {
        DSModel dsModel;
        KeyValue<String, String> nameInfo = FilterQueryListener.resolveDataSet(filterCell.getValue());
        KeyValue<String, String> titleInfo = null;
        if (filterCell.getDisplay() != null) {
            titleInfo = FilterQueryListener.resolveDataSet(filterCell.getDisplay());
            if (!((String)nameInfo.getKey()).equals(titleInfo.getKey())) {
                throw new ReportInteractionException("\u5355\u5143\u683c\u201c" + filterCell.getPosition() + "\u201d\u503c\u8868\u8fbe\u5f0f\u4e0e\u663e\u793a\u8868\u8fbe\u5f0f\u5173\u8054\u4e86\u4e0d\u540c\u7684\u6570\u636e\u96c6");
            }
        }
        try {
            dsModel = this.context.openDataSetModel((String)nameInfo.getKey());
        }
        catch (ReportContextException e) {
            throw new ReportInteractionException(e);
        }
        if (nameInfo.getValue() == null || titleInfo != null && titleInfo.getValue() == null) {
            dsModel = dsModel.clone();
        }
        this.nameField = this.resolveDataField(dsModel, (String)nameInfo.getValue(), filterCell.getValue());
        this.titleField = titleInfo == null ? this.nameField : this.resolveDataField(dsModel, (String)titleInfo.getValue(), filterCell.getDisplay());
        this.titleFormat = filterCell.getFormat();
        this.parentField = null;
        return dsModel;
    }

    private static KeyValue<String, String> resolveDataSet(IReportExpression expression) throws ReportInteractionException {
        DSFieldNode fieldNode;
        if (expression.getRootNode() instanceof DSFieldNode && (fieldNode = (DSFieldNode)expression.getRootNode()).getRestrictions().isEmpty()) {
            return KeyValue.with((Object)fieldNode.getDataSet().getName(), (Object)fieldNode.getField().getName());
        }
        HashSet<String> dsNames = new HashSet<String>();
        for (IASTNode node : expression) {
            if (!(node instanceof DSFieldNode)) continue;
            dsNames.add(((DSFieldNode)node).getDataSet().getName());
        }
        if (dsNames.isEmpty()) {
            throw new ReportInteractionException("\u8868\u8fbe\u5f0f\u672a\u5f15\u7528\u4efb\u4f55\u6570\u636e\u96c6\uff1a" + expression);
        }
        if (dsNames.size() > 1) {
            throw new ReportInteractionException("\u8868\u8fbe\u5f0f\u5173\u8054\u4e86\u591a\u4e2a\u6570\u636e\u96c6\uff1a" + expression);
        }
        return KeyValue.with(dsNames.iterator().next(), null);
    }

    private String resolveDataField(DSModel dsModel, String fieldName, IReportExpression expression) throws ReportInteractionException {
        int dataType;
        String formula;
        if (fieldName != null) {
            return fieldName;
        }
        try {
            formula = expression.getRootNode().interpret((IContext)this.context, Language.FORMULA, (Object)new DSFormulaInfo(dsModel.getName(), true));
            dataType = expression.getDataType(this.context);
        }
        catch (ReportExpressionException | InterpretException e) {
            throw new ReportInteractionException(e);
        }
        for (DSCalcField field : dsModel.getCalcFields()) {
            if (!field.getFormula().equals(formula)) continue;
            return field.getName();
        }
        DSCalcField calcField = new DSCalcField();
        calcField.setName(this.createFieldName(dsModel));
        calcField.setFormula(formula);
        calcField.setValType(dataType);
        calcField.setFieldType(DataType.isNumberType((int)dataType) ? FieldType.MEASURE : FieldType.DESCRIPTION);
        dsModel.getCalcFields().add(calcField);
        return calcField.getName();
    }

    private String createFieldName(DSModel dsModel) throws ReportInteractionException {
        for (int i = 1; i <= Integer.MAX_VALUE; ++i) {
            String fieldName = FIELD_PREFIX + i;
            if (dsModel.findField(fieldName) != null) continue;
            return fieldName;
        }
        throw new ReportInteractionException("\u65e0\u6cd5\u751f\u6210\u8ba1\u7b97\u5b57\u6bb5\u540d\u79f0");
    }

    private MemoryDataSet<BIDataSetFieldInfo> queryDataSet(ExpandingArea area, DSModel model) throws ReportInteractionException {
        ArrayList<IFilterDescriptor> filters = new ArrayList<IFilterDescriptor>();
        try {
            if (!area.getColAxis().isEmpty()) {
                filters.addAll(area.getColAxis().getFilters(this.context));
            }
            if (!area.getRowAxis().isEmpty()) {
                filters.addAll(area.getRowAxis().getFilters(this.context));
            }
        }
        catch (ReportAreaExpcetion e) {
            throw new ReportInteractionException(e);
        }
        ArrayList<String> fieldNames = new ArrayList<String>();
        fieldNames.add(this.nameField);
        if (!this.nameField.equals(this.titleField)) {
            fieldNames.add(this.titleField);
        }
        if (this.parentField != null) {
            fieldNames.add(this.parentField);
        }
        try {
            return this.context.distinct(model, fieldNames, filters);
        }
        catch (ReportContextException e) {
            throw new ReportInteractionException(e);
        }
    }

    private MemoryDataSet<Object> createResultSet(MemoryDataSet<BIDataSetFieldInfo> dataSet) throws ReportInteractionException {
        MemoryDataSet result = new MemoryDataSet();
        Column nameCol = dataSet.getMetadata().find(this.nameField);
        Column titleCol = dataSet.getMetadata().find(this.titleField);
        Column parentCol = this.parentField == null ? null : dataSet.getMetadata().find(this.parentField);
        result.getMetadata().addColumn(new Column("ItemName", nameCol.getDataType(), "\u4ee3\u7801", null));
        result.getMetadata().addColumn(new Column("ItemTitle", 6, "\u540d\u79f0", null));
        if (parentCol != null) {
            result.getMetadata().addColumn(new Column("ItemParent", 6, "\u7236\u4ee3\u7801", null));
        }
        for (DataRow rawRow : dataSet) {
            DataRow newRow = result.add();
            Object name = rawRow.getValue(nameCol.getIndex());
            newRow.setValue(0, name);
            Object title = rawRow.getValue(titleCol.getIndex());
            if (title == null) {
                newRow.setString(1, FIELD_PREFIX);
            } else {
                newRow.setString(1, this.titleFormat == null ? title.toString() : this.titleFormat.format(title));
            }
            if (parentCol != null) {
                String parent = rawRow.getString(parentCol.getIndex());
                newRow.setString(2, parent);
            }
            try {
                newRow.commit();
            }
            catch (DataSetException e) {
                throw new ReportInteractionException(e);
            }
        }
        return result;
    }
}

