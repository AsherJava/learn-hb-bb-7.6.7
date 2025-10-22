/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultItem
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.ParameterUtils
 *  com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 */
package com.jiuqi.bi.publicparam.datasource.period;

import com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.period.PeriodParameterRenderer;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultItem;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class NrPeriodDataSourceProvider
implements IParameterDataSourceProvider {
    protected NrPeriodDataSourceModel dataSourceModel;
    protected PeriodEngineService periodEngineService;
    protected IDataDefinitionRuntimeController dataDefinitionController;
    protected PeriodParameterRenderer renderer = new PeriodParameterRenderer();

    public NrPeriodDataSourceProvider(NrPeriodDataSourceModel dataSourceModel, PeriodEngineService periodEngineService, IDataDefinitionRuntimeController dataDefinitionController) {
        this.dataSourceModel = dataSourceModel;
        this.periodEngineService = periodEngineService;
        this.dataDefinitionController = dataDefinitionController;
    }

    public ParameterResultset getDefaultValue(ParameterDataSourceContext context) throws ParameterException {
        try {
            AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
            String mode = cfg.getDefaultValueMode();
            if (mode.equals("none")) {
                return new ParameterResultset();
            }
            List values = null;
            int periodType = this.dataSourceModel.getPeriodType();
            IPeriodEntityAdapter periodEntityAdapter = this.periodEngineService.getPeriodAdapter();
            String periodEntityId = String.valueOf((char)PeriodType.fromType((int)periodType).code());
            if (periodType == PeriodType.CUSTOM.type()) {
                return this.getDeaultCustomPeirod(context, cfg, mode, values);
            }
            if (periodType == PeriodType.MONTH.type() && StringUtils.isNotEmpty((String)this.dataSourceModel.getEntityViewId())) {
                periodEntityId = this.getPeriodEntityId(this.dataSourceModel.getEntityViewId());
            }
            IPeriodProvider periodProvider = periodEntityAdapter.getPeriodProvider(periodEntityId);
            ArrayList<PeriodWrapper> defaultPeriods = new ArrayList<PeriodWrapper>();
            if (mode.equals("appoint")) {
                IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)context.getModel().getDatasource());
                values = cfg.getDefaultValue().getKeysAsString(null, valueFormat);
                if (values == null || values.isEmpty()) {
                    return new ParameterResultset();
                }
                List<PeriodWrapper> periods = this.getCandidatePeriodWrappers(context, periodType, values);
                defaultPeriods.addAll(periods);
            } else if (mode.equals("first")) {
                List<PeriodWrapper> allPeriods;
                values = cfg.getCandidateValue();
                if (values != null && values.isEmpty()) {
                    values = null;
                }
                if ((allPeriods = this.getCandidatePeriodWrappers(context, periodType, values)).size() > 0) {
                    defaultPeriods.add(allPeriods.get(0));
                }
            } else if (mode.equals("expr")) {
                String formula = (String)cfg.getDefaultValue().getValue();
                PeriodWrapper pw = this.getPeriodWrapperbyFormula(formula, periodProvider);
                defaultPeriods.add(pw);
            } else if (mode.equals("priorPeriod")) {
                IPeriodRow periodRow = periodProvider.getCurPeriod();
                PeriodWrapper pw = new PeriodWrapper(periodRow.getCode());
                periodProvider.priorPeriod(pw);
                defaultPeriods.add(pw);
            } else if (mode.equals("currPeriod")) {
                IPeriodRow periodRow = periodProvider.getCurPeriod();
                PeriodWrapper pw = new PeriodWrapper(periodRow.getCode());
                defaultPeriods.add(pw);
            }
            return this.getParameterResultsetByPW(defaultPeriods);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public ParameterResultset getCandidateValue(ParameterDataSourceContext context, ParameterHierarchyFilterItem hierarchyFilter) throws ParameterException {
        try {
            AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
            int periodType = this.dataSourceModel.getPeriodType();
            List values = null;
            if (cfg.getCandidateMode() == ParameterCandidateValueMode.APPOINT) {
                values = cfg.getCandidateValue();
            }
            if (periodType == PeriodType.CUSTOM.type()) {
                List<IPeriodRow> periodRows = this.getCandidatePeriodRows(values);
                int widgetType = this.getWidgetType(context);
                if (this.dataSourceModel.hasHierarchy() && hierarchyFilter != null) {
                    String parent = hierarchyFilter.getParent();
                    if (parent.length() > 4) {
                        parent = parent.substring(0, 4);
                    }
                    if (StringUtils.isNotEmpty((String)parent)) {
                        int year = Integer.parseInt(parent);
                        for (int i = periodRows.size() - 1; i >= 0; --i) {
                            IPeriodRow row = periodRows.get(i);
                            if (row.getYear() == year) continue;
                            periodRows.remove(i);
                        }
                    } else if (widgetType != ParameterWidgetType.DATEPICKER.value() && widgetType != ParameterWidgetType.BUTTON.value() && !hierarchyFilter.isAllSub()) {
                        return this.getGroupParameterResultSet(periodRows, widgetType);
                    }
                }
                return this.getParameterResultset(periodRows, widgetType);
            }
            List<PeriodWrapper> periods = this.getCandidatePeriodWrappers(context, periodType, values);
            return this.getParameterResultsetByPW(periods);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public ParameterResultset compute(ParameterDataSourceContext context, AbstractParameterValue value) throws ParameterException {
        try {
            AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
            ParameterCandidateValueMode mode = cfg.getCandidateMode();
            int periodType = this.dataSourceModel.getPeriodType();
            IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)context.getModel().getDatasource());
            List values = value.getKeysAsString(null, valueFormat);
            if (mode == ParameterCandidateValueMode.APPOINT) {
                List keys = cfg.getCandidateValue();
                if (values != null && keys != null) {
                    for (int i = values.size() - 1; i >= 0; --i) {
                        if (keys.contains(values.get(i))) continue;
                        values.remove(i);
                    }
                }
            }
            if (periodType == PeriodType.CUSTOM.type()) {
                List<IPeriodRow> candidatePeriodRows = this.getCandidatePeriodRows(values);
                return this.getParameterResultset(candidatePeriodRows, this.getWidgetType(context));
            }
            List<PeriodWrapper> candidatePeriodWrappers = this.getCandidatePeriodWrappers(context, periodType, values);
            return this.getParameterResultsetByPW(candidatePeriodWrappers);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public ParameterResultset search(ParameterDataSourceContext context, List<String> searchValues) throws ParameterException {
        AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
        int periodType = this.dataSourceModel.getPeriodType();
        List values = null;
        if (cfg.getCandidateMode() == ParameterCandidateValueMode.APPOINT) {
            values = cfg.getCandidateValue();
        }
        if (periodType == PeriodType.CUSTOM.type()) {
            ArrayList<IPeriodRow> periodRows = new ArrayList<IPeriodRow>();
            List<IPeriodRow> allPeriodRows = this.getCandidatePeriodRows(values);
            if (allPeriodRows != null) {
                for (String filterValue : searchValues) {
                    for (IPeriodRow periodRow : allPeriodRows) {
                        if (!periodRow.getCode().contains(filterValue) && !periodRow.getTitle().contains(filterValue)) continue;
                        periodRows.add(periodRow);
                    }
                }
            }
            return this.getParameterResultset(periodRows, this.getWidgetType(context));
        }
        ArrayList<PeriodWrapper> filterPeriods = new ArrayList<PeriodWrapper>();
        List<PeriodWrapper> periodList = this.getCandidatePeriodWrappers(context, periodType, values);
        if (periodList != null) {
            PeriodType pt = PeriodType.fromType((int)this.dataSourceModel.getPeriodType());
            for (String filterValue : searchValues) {
                filterValue = TimeDimUtils.timeKeyToPeriod((String)filterValue, (PeriodType)pt);
                for (PeriodWrapper period : periodList) {
                    if (!period.toString().contains(filterValue) && !period.toTitleString().contains(filterValue)) continue;
                    filterPeriods.add(period);
                }
            }
        }
        return this.getParameterResultsetByPW(filterPeriods);
    }

    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(AbstractParameterDataSourceModel datasourceModel) throws ParameterException {
        try {
            PeriodTableColumn[] columns = PeriodTableColumn.values();
            ArrayList<DataSourceCandidateFieldInfo> list = new ArrayList<DataSourceCandidateFieldInfo>(columns.length);
            for (PeriodTableColumn column : columns) {
                DataSourceCandidateFieldInfo info = new DataSourceCandidateFieldInfo(column.getCode(), column.getTitle());
                list.add(info);
            }
            return list;
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    private List<IPeriodRow> getCandidatePeriodRows(List<String> values) {
        IPeriodEntityAdapter periodEntityAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodProvider periodProvider = periodEntityAdapter.getPeriodProvider(this.getPeriodEntityId(this.dataSourceModel.getEntityViewId()));
        List periodRows = periodProvider.getPeriodItems();
        if (values != null && values.size() > 0) {
            Iterator iterator = periodRows.iterator();
            while (iterator.hasNext()) {
                IPeriodRow row = (IPeriodRow)iterator.next();
                if (values.contains(row.getCode())) continue;
                iterator.remove();
            }
        }
        return periodRows;
    }

    private ParameterResultset getDeaultCustomPeirod(ParameterDataSourceContext context, AbstractParameterValueConfig cfg, String mode, List<String> values) throws ParameterException {
        try {
            IPeriodEntityAdapter periodEntityAdapter = this.periodEngineService.getPeriodAdapter();
            IPeriodProvider periodProvider = periodEntityAdapter.getPeriodProvider(this.getPeriodEntityId(this.dataSourceModel.getEntityViewId()));
            ArrayList<IPeriodRow> periodRows = new ArrayList<IPeriodRow>();
            List<IPeriodRow> allPeriodRows = this.getCandidatePeriodRows(values);
            if (mode.equals("first")) {
                periodRows.add(allPeriodRows.get(0));
            } else if (mode.equals("appoint")) {
                IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)context.getModel().getDatasource());
                List keys = cfg.getDefaultValue().getKeysAsString(null, valueFormat);
                for (String periodCode : keys) {
                    for (IPeriodRow periodRow : allPeriodRows) {
                        if (!periodRow.getCode().equals(periodCode)) continue;
                        periodRows.add(periodRow);
                    }
                }
            } else if (mode.equals("expr")) {
                String formula = (String)cfg.getDefaultValue().getValue();
                PeriodWrapper pw = this.getPeriodWrapperbyFormula(formula, periodProvider);
                String periodCode = pw.toString();
                String periodTitle = periodProvider.getPeriodTitle(pw);
                ParameterResultItem item = new ParameterResultItem((Object)periodCode, periodTitle);
                return new ParameterResultset(item);
            }
            return this.getParameterResultset(periodRows, this.getWidgetType(context));
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    protected List<PeriodWrapper> getCandidatePeriodWrappers(ParameterDataSourceContext context, int periodType, List<String> periods) {
        IPeriodEntity periodEntity;
        PeriodType pt = PeriodType.fromType((int)this.dataSourceModel.getPeriodType());
        String startandend = this.dataSourceModel.getPeriodStartEnd();
        HashSet<String> values = null;
        if (periods != null) {
            values = new HashSet<String>(periods.size());
            for (String period : periods) {
                String value = TimeDimUtils.timeKeyToPeriod((String)period, (PeriodType)pt);
                values.add(value);
            }
        }
        int minPeriodOfYear = 1;
        int maxYear = PeriodUtil.getCurrentCalendar().get(1);
        int minYear = maxYear - 5;
        if (StringUtils.isNotEmpty((String)startandend)) {
            String[] strs = startandend.split("-");
            if (strs.length == 2) {
                if (strs[0].length() == 9 && strs[1].length() == 9) {
                    minYear = new PeriodWrapper(strs[0]).getYear();
                    maxYear = new PeriodWrapper(strs[1]).getYear();
                } else {
                    minYear = Integer.parseInt(strs[0]);
                    maxYear = Integer.parseInt(strs[1]);
                }
            } else if (startandend.startsWith("-")) {
                maxYear = startandend.length() == 10 ? new PeriodWrapper(startandend.substring(1)).getYear() : Integer.parseInt(startandend.substring(1));
            } else if (startandend.endsWith("-")) {
                minYear = startandend.length() == 10 ? new PeriodWrapper(startandend.substring(0, startandend.length() - 1)).getYear() : Integer.parseInt(startandend.substring(0, startandend.length() - 1));
            }
        } else if (this.isLocalParameter(context) && values != null && values.size() > 0) {
            ArrayList<PeriodWrapper> periodWrappers = new ArrayList<PeriodWrapper>();
            for (String value : values) {
                PeriodWrapper periodWrapper = this.getParsedPeriod(value);
                if (periodWrapper == null) continue;
                periodWrappers.add(periodWrapper);
            }
            return periodWrappers;
        }
        IPeriodEntityAdapter periodEntityAdapter = this.periodEngineService.getPeriodAdapter();
        String periodEntityId = String.valueOf((char)pt.code());
        if (periodType == PeriodType.MONTH.type() && StringUtils.isNotEmpty((String)this.dataSourceModel.getEntityViewId())) {
            periodEntityId = this.getPeriodEntityId(this.dataSourceModel.getEntityViewId());
            periodEntity = periodEntityAdapter.getPeriodEntity(periodEntityId);
            minPeriodOfYear = periodEntity.getMinFiscalMonth();
        }
        if (minYear < (periodEntity = periodEntityAdapter.getPeriodEntity(periodEntityId)).getMinYear()) {
            minYear = periodEntity.getMinYear();
        }
        if (maxYear > periodEntity.getMaxYear()) {
            maxYear = periodEntity.getMaxYear();
        }
        IPeriodProvider periodAdapter = periodEntityAdapter.getPeriodProvider(periodEntityId);
        PeriodWrapper minPeriod = new PeriodWrapper(minYear, periodType, minPeriodOfYear);
        PeriodWrapper endPeriod = new PeriodWrapper(maxYear + 1, periodType, minPeriodOfYear);
        ArrayList<PeriodWrapper> periodWrappers = new ArrayList<PeriodWrapper>();
        while (minPeriod.compareTo((Object)endPeriod) < 0 && minPeriod.getYear() != 1900) {
            if (values == null || values.contains(minPeriod.toString())) {
                periodWrappers.add(new PeriodWrapper(minPeriod));
            }
            if (periodAdapter.nextPeriod(minPeriod)) continue;
            break;
        }
        return periodWrappers;
    }

    protected PeriodWrapper getPeriodWrapperbyFormula(String formula, IPeriodProvider periodProvider) throws ParseException {
        IPeriodRow periodRow;
        PeriodWrapper pw;
        PeriodModifier modifier;
        block16: {
            modifier = null;
            pw = null;
            periodRow = periodProvider.getCurPeriod();
            try {
                ExecutorContext eContext = new ExecutorContext(this.dataDefinitionController);
                QueryContext qContext = new QueryContext(eContext, null);
                ReportFormulaParser parser = eContext.getCache().getFormulaParser(eContext);
                IExpression exp = parser.parseEval(formula, (IContext)qContext);
                if (exp != null) {
                    DimensionValueSet dim = new DimensionValueSet();
                    dim.setValue("DATATIME", (Object)periodRow.getCode());
                    String expValue = exp.evalAsString((IContext)qContext);
                    if (expValue != null && (modifier = this.getPeriodModifier(expValue)) == null) {
                        Optional<IPeriodRow> optional;
                        if (expValue.length() == 9) {
                            Optional<IPeriodRow> optional2 = periodProvider.getPeriodItems().stream().filter(row -> row.getCode().equals(expValue)).findAny();
                            if (optional2.isPresent()) {
                                pw = new PeriodWrapper(expValue);
                            }
                        } else if (expValue.length() == 8 && (optional = periodProvider.getPeriodItems().stream().filter(row -> row.getTimeKey().equals(expValue)).findAny()).isPresent()) {
                            pw = new PeriodWrapper(optional.get().getCode());
                        }
                    }
                }
            }
            catch (Exception e) {
                if (pw != null || !formula.equals("-0N")) break block16;
                pw = new PeriodWrapper(periodRow.getCode());
            }
        }
        if (pw != null) {
            return pw;
        }
        if (modifier != null) {
            pw = new PeriodWrapper(periodRow.getCode());
            modifier.modify(pw);
            return pw;
        }
        modifier = this.getPeriodModifier(formula);
        if (modifier != null) {
            pw = new PeriodWrapper(periodRow.getCode());
            modifier.modify(pw);
        } else {
            Optional<IPeriodRow> optional;
            String period = formula;
            if (formula.length() == 9) {
                optional = periodProvider.getPeriodItems().stream().filter(row -> row.getCode().equals(period)).findAny();
                if (optional.isPresent()) {
                    pw = new PeriodWrapper(period);
                }
            } else if (formula.length() == 8 && (optional = periodProvider.getPeriodItems().stream().filter(row -> row.getTimeKey().equals(period)).findAny()).isPresent()) {
                pw = new PeriodWrapper(optional.get().getCode());
            }
        }
        return pw;
    }

    private PeriodModifier getPeriodModifier(String formula) {
        String[] offsets;
        PeriodModifier modifier = null;
        if (formula.startsWith("\"")) {
            formula = formula.substring(1, formula.length() - 1);
        }
        for (String offset : offsets = formula.split(",")) {
            if (modifier == null) {
                modifier = PeriodModifier.parse((String)offset);
                continue;
            }
            modifier.union(PeriodModifier.parse((String)offset));
        }
        if (modifier != null && modifier.isEmpty()) {
            modifier = null;
        }
        return modifier;
    }

    private ParameterResultset getParameterResultsetByPW(List<PeriodWrapper> periods) {
        ArrayList<ParameterResultItem> items = new ArrayList<ParameterResultItem>();
        IPeriodEntityAdapter periodEntityAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodProvider periodProvider = periodEntityAdapter.getPeriodProvider(this.getPeriodEntityId(this.dataSourceModel.getEntityViewId()));
        Map<String, IPeriodRow> periodRows = periodProvider.getPeriodItems().stream().collect(Collectors.toMap(IPeriodRow::getCode, value -> value, (v1, v2) -> v2));
        for (PeriodWrapper pw : periods) {
            ParameterResultItem item;
            String itemCode = pw.toString();
            IPeriodRow row = periodRows.get(itemCode);
            if (row != null) {
                itemCode = row.getTimeKey();
                item = new ParameterResultItem((Object)itemCode, row.getTitle());
                items.add(item);
                continue;
            }
            itemCode = TimeDimUtils.periodWrapperToTimeKey((PeriodWrapper)pw);
            item = new ParameterResultItem((Object)itemCode, pw.toTitleString());
            items.add(item);
        }
        return new ParameterResultset(items);
    }

    private ParameterResultset getParameterResultset(List<IPeriodRow> periods, int widgetType) {
        ArrayList<ParameterResultItem> items = new ArrayList<ParameterResultItem>();
        for (IPeriodRow periodRow : periods) {
            ParameterResultItem item = new ParameterResultItem((Object)periodRow.getCode(), periodRow.getTitle());
            String parent = this.getGroupCode(widgetType, periodRow);
            item.setParent(parent);
            items.add(item);
        }
        return new ParameterResultset(items);
    }

    private ParameterResultset getGroupParameterResultSet(List<IPeriodRow> periodRows, int widgetType) {
        HashMap<String, ParameterResultItem> groups = new HashMap<String, ParameterResultItem>();
        for (IPeriodRow periodRow : periodRows) {
            String groupCode = this.getGroupCode(widgetType, periodRow);
            if (groups.containsKey(groupCode)) continue;
            ParameterResultItem item = new ParameterResultItem((Object)groupCode, String.valueOf(periodRow.getYear()) + "\u5e74");
            item.setLeaf(false);
            groups.put(groupCode, item);
        }
        ArrayList items = new ArrayList(groups.values());
        Collections.sort(items, new Comparator<ParameterResultItem>(){

            @Override
            public int compare(ParameterResultItem o1, ParameterResultItem o2) {
                return ((String)o1.getValue()).compareTo((String)o2.getValue());
            }
        });
        return new ParameterResultset(items);
    }

    private String getGroupCode(int widgetType, IPeriodRow periodRow) {
        String parent = String.valueOf(periodRow.getYear());
        if (widgetType == ParameterWidgetType.DATEPICKER.value()) {
            parent = parent + "\u5e74";
        }
        return parent;
    }

    private PeriodWrapper getParsedPeriod(String periodStr) {
        PeriodWrapper period = new PeriodWrapper();
        try {
            period.parseString(periodStr);
        }
        catch (Exception e) {
            period = null;
        }
        return period;
    }

    private String getPeriodEntityId(String periodTableName) {
        if (StringUtils.isEmpty((String)periodTableName)) {
            return String.valueOf((char)PeriodType.fromType((int)this.dataSourceModel.getPeriodType()).code());
        }
        if (periodTableName.startsWith("NR_PERIOD_")) {
            return periodTableName.substring("NR_PERIOD_".length());
        }
        if (StringUtils.isEmpty((String)periodTableName)) {
            int periodType = this.dataSourceModel.getPeriodType();
            return String.valueOf((char)PeriodType.fromType((int)periodType).code());
        }
        return periodTableName;
    }

    private boolean isLocalParameter(ParameterDataSourceContext context) {
        return !this.dataSourceModel.isRemote();
    }

    private int getWidgetType(ParameterDataSourceContext context) {
        ParameterModel model = context.getModel();
        int widgetType = model.getWidgetType();
        if (widgetType == ParameterWidgetType.DEFAULT.value()) {
            widgetType = this.renderer.getDefaultWidgetType(this.dataSourceModel, model.getSelectMode());
        }
        return widgetType;
    }
}

