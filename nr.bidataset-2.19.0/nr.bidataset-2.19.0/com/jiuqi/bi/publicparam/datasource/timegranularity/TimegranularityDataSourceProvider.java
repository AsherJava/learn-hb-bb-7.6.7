/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.IDataRowFilter
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.util.time.TimeCalcException
 *  com.jiuqi.bi.util.time.TimeCalculator
 *  com.jiuqi.bi.util.time.TimeFieldInfo
 *  com.jiuqi.bi.util.time.TimeHelper
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nr.period.common.utils.TimeDimField
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
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 */
package com.jiuqi.bi.publicparam.datasource.timegranularity;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.IDataRowFilter;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.publicparam.datasource.timegranularity.TimegranularityDataSourceModel;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeCalculator;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import com.jiuqi.bi.util.time.TimeHelper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.period.common.utils.TimeDimField;
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
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TimegranularityDataSourceProvider
implements IParameterDataSourceProvider {
    protected TimegranularityDataSourceModel dataSourceModel;
    protected PeriodEngineService periodEngineService;

    public TimegranularityDataSourceProvider(TimegranularityDataSourceModel dataSourceModel, PeriodEngineService periodEngineService) {
        this.dataSourceModel = dataSourceModel;
        this.periodEngineService = periodEngineService;
    }

    public ParameterResultset getDefaultValue(ParameterDataSourceContext context) throws ParameterException {
        try {
            AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
            String mode = cfg.getDefaultValueMode();
            if (mode.equals("none")) {
                return new ParameterResultset();
            }
            HashSet<String> selectedValues = null;
            ArrayList<Integer> defaultValues = new ArrayList<Integer>();
            if (mode.equals("appoint")) {
                IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)context.getModel().getDatasource());
                List appointValues = cfg.getDefaultValue().getKeysAsString(null, valueFormat);
                if (appointValues == null || appointValues.isEmpty()) {
                    return new ParameterResultset();
                }
                selectedValues = new HashSet(appointValues);
                defaultValues.addAll(this.getCandidateValues(context, selectedValues));
            } else if (mode.equals("first")) {
                List<Integer> allValues;
                if (cfg.getCandidateValue() != null && !cfg.getCandidateValue().isEmpty()) {
                    selectedValues = new HashSet<String>(cfg.getCandidateValue());
                }
                if ((allValues = this.getCandidateValues(context, selectedValues)).size() > 0) {
                    defaultValues.add(allValues.get(0));
                }
            } else {
                int timegranularity = this.dataSourceModel.getTimegranularity();
                if (mode.equals("priorPeriod")) {
                    IPeriodEntityAdapter periodEntityAdapter = this.periodEngineService.getPeriodAdapter();
                    PeriodType globalPeriodPeriodType = PeriodType.fromType((int)this.dataSourceModel.getTimekeyPeriodType());
                    String globalPeriodEntityId = ((IPeriodEntity)periodEntityAdapter.getPeriodEntityByPeriodType(globalPeriodPeriodType).get(0)).getKey();
                    IPeriodProvider periodProvider = periodEntityAdapter.getPeriodProvider(globalPeriodEntityId);
                    IPeriodRow periodRow = periodProvider.getCurPeriod();
                    String priorPeriod = periodProvider.priorPeriod(periodRow.getCode());
                    TimeFieldInfo[] timeFieldInfos = new TimeFieldInfo[]{new TimeFieldInfo(PeriodTableColumn.TIMEKEY.getCode(), timegranularity, TimeHelper.getDefaultDataPattern((int)timegranularity, (boolean)true), true), new TimeFieldInfo(this.dataSourceModel.getTimeDimField().getName(), timegranularity, TimeHelper.getDefaultDataPattern((int)timegranularity, (boolean)false))};
                    TimeCalculator timeCalculator = TimeCalculator.createCalculator((TimeFieldInfo[])timeFieldInfos);
                    if (this.dataSourceModel.getMinFiscalMonth() >= 0) {
                        timeCalculator.setFiscalMonth(this.dataSourceModel.getMinFiscalMonth(), this.dataSourceModel.getMaxFiscalMonth());
                    }
                    timeCalculator.setValue(0, (Object)TimeDimUtils.periodToTimeKey((String)priorPeriod));
                    defaultValues.add(Integer.parseInt((String)timeCalculator.calculate().getValue(1)));
                } else if (mode.equals("currPeriod")) {
                    IPeriodEntityAdapter periodEntityAdapter = this.periodEngineService.getPeriodAdapter();
                    IPeriodProvider periodProvider = periodEntityAdapter.getPeriodProvider(this.dataSourceModel.getPeriodEntityId());
                    IPeriodRow periodRow = periodProvider.getCurPeriod();
                    defaultValues.add(this.getValue(periodRow, timegranularity));
                }
            }
            return this.getParameterResultset(defaultValues);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public ParameterResultset getCandidateValue(ParameterDataSourceContext context, ParameterHierarchyFilterItem hierarchyFilter) throws ParameterException {
        try {
            AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
            HashSet selectedValues = null;
            if (cfg.getCandidateMode() == ParameterCandidateValueMode.APPOINT) {
                selectedValues = new HashSet(cfg.getCandidateValue());
            }
            List<Integer> values = this.getCandidateValues(context, selectedValues);
            return this.getParameterResultset(values);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public ParameterResultset compute(ParameterDataSourceContext context, AbstractParameterValue value) throws ParameterException {
        try {
            AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
            ParameterCandidateValueMode mode = cfg.getCandidateMode();
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
            HashSet<String> selectedValues = null;
            if (values != null) {
                selectedValues = new HashSet<String>();
                for (String v : values) {
                    if (this.dataSourceModel.getTimegranularity() == TimeGranularity.YEAR.value() && v.length() > 4) {
                        v = v.substring(0, 4);
                    }
                    selectedValues.add(v);
                }
            }
            List<Integer> candidateValues = this.getCandidateValues(context, selectedValues);
            return this.getParameterResultset(candidateValues);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    private List<Integer> getCandidateValues(ParameterDataSourceContext context, Set<String> selectedValues) throws DataSetException {
        HashSet<Integer> values = new HashSet<Integer>();
        MemoryDataSet<TimeDimField> dataSet = this.getPeriodDataSet(this.dataSourceModel.getPeriodEntityId());
        DataSet filteredDataSet = dataSet.filter((IDataRowFilter)this.dataSourceModel.getFilter(context));
        int columnIndex = filteredDataSet.getMetadata().getColumnCount() - 1;
        for (DataRow row : filteredDataSet) {
            int value = row.getInt(columnIndex);
            if (selectedValues != null && !selectedValues.isEmpty() && !selectedValues.contains(String.valueOf(value))) continue;
            values.add(value);
        }
        ArrayList<Integer> list = new ArrayList<Integer>(values);
        Collections.sort(list);
        return list;
    }

    public MemoryDataSet<TimeDimField> getPeriodDataSet(String periodEntityId) throws DataSetException {
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityId);
        Metadata<TimeDimField> metaData = this.dataSourceModel.getMetaData();
        MemoryDataSet dataSet = new MemoryDataSet(TimeDimField.class, metaData);
        List periodRows = periodProvider.getPeriodItems();
        for (IPeriodRow period : periodRows) {
            DataRow row = dataSet.add();
            for (int i = 0; i < metaData.size(); ++i) {
                Column column = metaData.getColumn(i);
                TimeDimField fieldInfo = (TimeDimField)column.getInfo();
                if (fieldInfo.isTimeKey()) {
                    row.setValue(i, (Object)period.getTimeKey());
                    continue;
                }
                row.setValue(i, (Object)this.getValue(period, fieldInfo.getTimeGranularity().value()));
            }
            row.commit();
        }
        return dataSet;
    }

    private int getValue(IPeriodRow periodRow, int timegranularity) {
        if (timegranularity == TimeGranularity.YEAR.value()) {
            return periodRow.getYear();
        }
        if (timegranularity == TimeGranularity.QUARTER.value()) {
            return periodRow.getQuarter();
        }
        if (timegranularity == TimeGranularity.MONTH.value()) {
            return periodRow.getMonth();
        }
        if (timegranularity == TimeGranularity.DAY.value()) {
            return periodRow.getDay();
        }
        return 0;
    }

    private ParameterResultset getParameterResultset(List<Integer> values) throws TimeCalcException {
        ArrayList<ParameterResultItem> items = new ArrayList<ParameterResultItem>();
        if (values == null) {
            return new ParameterResultset();
        }
        for (int value : values) {
            ParameterResultItem item = new ParameterResultItem((Object)value, value + this.dataSourceModel.getTimegranularityTitle());
            items.add(item);
        }
        return new ParameterResultset(items);
    }

    public ParameterResultset search(ParameterDataSourceContext context, List<String> searchValues) throws ParameterException {
        try {
            List<Integer> candidateValues = this.getCandidateValues(context, new HashSet<String>(searchValues));
            return this.getParameterResultset(candidateValues);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(AbstractParameterDataSourceModel datasourceModel) throws ParameterException {
        try {
            ArrayList<DataSourceCandidateFieldInfo> list = new ArrayList<DataSourceCandidateFieldInfo>();
            List fields = BqlTimeDimUtils.getTimeDimFields((String)this.dataSourceModel.getPeriodEntityId(), (PeriodType)this.dataSourceModel.getModelPeriodType());
            for (TimeDimField field : fields) {
                DataSourceCandidateFieldInfo info = new DataSourceCandidateFieldInfo(field.getName(), field.getTitle());
                list.add(info);
            }
            return list;
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }
}

