/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nr.period.common.utils.TimeDimField
 *  com.jiuqi.nvwa.framework.parameter.ParameterCalculator
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.ParameterUtils
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.DataBusinessType
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.publicparam.datasource.timegranularity;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.timegranularity.filter.MemoryRowFilter;
import com.jiuqi.bi.publicparam.datasource.timegranularity.filter.TimegranularityFilter;
import com.jiuqi.bi.publicparam.util.ParamUtils;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.period.common.utils.TimeDimField;
import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.DataBusinessType;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class TimegranularityDataSourceModel
extends AbstractParameterDataSourceModel {
    public static final String TYPE = "com.jiuqi.publicparam.datasource.timegranularity";
    public static final String TITLE = "\u65f6\u95f4\u7c92\u5ea6";
    protected static final String TAG_PERIOD_START_END = "periodStartEnd";
    protected static final String TAG_TIMEKEY_PERIOD_TYPE = "timekeyPeriodType";
    protected static final String TAG_TIMEKEY_ENTITY_VIEW_ID = "timekeyEntityViewId";
    protected static final String TAG_DATA_PATTERN = "dataPattern";
    protected static final String TAG_MIN_FISCAL_MONTH_ID = "minFiscalMonth";
    protected static final String TAG_MAX_FISCAL_MONTH_ID = "maxFiscalMonth";
    protected String periodStartEnd;
    private Metadata<TimeDimField> metaData = new Metadata();
    private TimeDimField timeDimField;
    private PeriodType modelPeriodType;
    private int timekeyPeriodType;
    private String dataPattern;
    private String periodEntityId;
    private int minFiscalMonth = -1;
    private int maxFiscalMonth = -1;

    public TimegranularityDataSourceModel() {
        this.businessType = DataBusinessType.TIME_DIM;
        this.dataType = 6;
        this.timekey = false;
    }

    public String getTimegranularityTitle() {
        if (this.timegranularity == TimeGranularity.MONTH.value()) {
            return "\u6708";
        }
        return this.timeDimField.getTimeGranularity().title();
    }

    public String getType() {
        return TYPE;
    }

    public String getPeriodStartEnd() {
        return this.periodStartEnd;
    }

    public void setPeriodStartEnd(String periodStartEnd) {
        this.periodStartEnd = periodStartEnd;
    }

    protected void fromExtJson(JSONObject json) throws JSONException {
        this.periodStartEnd = json.optString(TAG_PERIOD_START_END);
        this.timekeyPeriodType = json.optInt(TAG_TIMEKEY_PERIOD_TYPE);
        this.dataPattern = json.optString(TAG_DATA_PATTERN);
        this.minFiscalMonth = json.optInt(TAG_MIN_FISCAL_MONTH_ID);
        this.maxFiscalMonth = json.optInt(TAG_MAX_FISCAL_MONTH_ID);
        if (this.timegranularity >= 0) {
            this.modelPeriodType = TimeDimUtils.timeGranularityToPeriodType((TimeGranularity)TimeGranularity.valueOf((int)this.timegranularity));
            if (this.modelPeriodType == PeriodType.MONTH && this.timekeyPeriodType == PeriodType.MONTH.type()) {
                this.periodEntityId = json.optString(TAG_TIMEKEY_ENTITY_VIEW_ID);
            }
            if (StringUtils.isEmpty((String)this.periodEntityId)) {
                this.periodEntityId = String.valueOf((char)this.modelPeriodType.code());
            }
            List fields = BqlTimeDimUtils.getTimeDimFields((String)this.periodEntityId, (PeriodType)this.modelPeriodType);
            this.timeDimField = (TimeDimField)fields.get(fields.size() - 1);
            for (int i = 0; i < fields.size(); ++i) {
                TimeDimField field = (TimeDimField)fields.get(i);
                this.metaData.addColumn(new Column(field.getName(), 6, (Object)field));
            }
        }
    }

    protected void toExtJson(JSONObject json) throws JSONException {
        json.put(TAG_PERIOD_START_END, (Object)this.periodStartEnd);
        json.put(TAG_TIMEKEY_PERIOD_TYPE, this.timekeyPeriodType);
        json.put(TAG_DATA_PATTERN, (Object)this.dataPattern);
        json.put(TAG_MIN_FISCAL_MONTH_ID, this.minFiscalMonth);
        json.put(TAG_MAX_FISCAL_MONTH_ID, this.maxFiscalMonth);
        json.put(TAG_TIMEKEY_ENTITY_VIEW_ID, (Object)this.periodEntityId);
    }

    public MemoryRowFilter getFilter(ParameterDataSourceContext context) {
        List depends = context.getModel().getValueConfig().getDepends();
        MemoryRowFilter rowFilter = this.getRowFilterByDepends(context, depends);
        if (this.timegranularity == TimeGranularity.YEAR.value()) {
            if (StringUtils.isNotEmpty((String)this.periodStartEnd)) {
                this.addFilterByPeriodStartEnd(rowFilter);
            } else if (rowFilter.isEmpty()) {
                PeriodWrapper pw = PeriodUtil.currentPeriod((int)this.modelPeriodType.type());
                this.addYearFilterByStartEnd(rowFilter, pw.getYear() - 5, pw.getYear());
            }
        }
        return rowFilter;
    }

    public MemoryRowFilter getRowFilterByDepends(ParameterDataSourceContext context, List<ParameterDependMember> depends) {
        MemoryRowFilter rowFilter = new MemoryRowFilter();
        ParameterCalculator calculator = context.getCalculator();
        TimegranularityFilter filter = null;
        if (depends != null && depends.size() > 0) {
            for (ParameterDependMember depend : depends) {
                try {
                    Column column;
                    IParameterValueFormat valueFormat;
                    boolean isRange;
                    ParameterModel parameterModel = calculator.findParameterModelByName(depend.getParameterName(), false);
                    AbstractParameterDataSourceModel datasource = parameterModel.getDatasource();
                    if (datasource == null) continue;
                    boolean bl = isRange = parameterModel.getSelectMode() == ParameterSelectMode.RANGE;
                    if (datasource instanceof NrPeriodDataSourceModel) {
                        List<String> timeKeys = this.getParaValues(calculator, parameterModel, datasource);
                        if (timeKeys == null || timeKeys.isEmpty()) continue;
                        filter = new TimegranularityFilter((Column<TimeDimField>)this.metaData.getColumn(0), timeKeys, isRange);
                        rowFilter.addFilter(filter);
                        continue;
                    }
                    if (datasource.getTimegranularity() == this.timegranularity || !(datasource instanceof TimegranularityDataSourceModel)) continue;
                    TimegranularityDataSourceModel otherTimegranularityModel = (TimegranularityDataSourceModel)datasource;
                    ParameterResultset value = calculator.getValue(parameterModel.getName());
                    List values = value.getValueAsString(valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)datasource));
                    if (values == null || values.isEmpty() || (column = this.metaData.find(otherTimegranularityModel.getTimeDimField().getName())) == null) continue;
                    filter = new TimegranularityFilter((Column<TimeDimField>)column, values, isRange);
                    rowFilter.addFilter(filter);
                }
                catch (Exception e) {
                    ParamUtils.log.error(e.getMessage(), e);
                }
            }
        }
        return rowFilter;
    }

    private void addFilterByPeriodStartEnd(MemoryRowFilter rowFilter) {
        String[] strs = this.periodStartEnd.split("-");
        int startYear = 0;
        int endYear = 0;
        if (strs.length == 2) {
            if (strs[0].length() == 9 && strs[1].length() == 9) {
                startYear = new PeriodWrapper(strs[0]).getYear();
                endYear = new PeriodWrapper(strs[1]).getYear();
            } else {
                startYear = Integer.parseInt(strs[0]);
                endYear = Integer.parseInt(strs[1]);
            }
        } else if (this.periodStartEnd.startsWith("-")) {
            PeriodWrapper pw = PeriodUtil.currentPeriod((int)this.modelPeriodType.type());
            startYear = pw.getYear() - 5;
            endYear = this.periodStartEnd.length() == 10 ? new PeriodWrapper(this.periodStartEnd.substring(1)).getYear() : Integer.parseInt(this.periodStartEnd.substring(1));
        } else if (this.periodStartEnd.endsWith("-")) {
            PeriodWrapper pw = PeriodUtil.currentPeriod((int)this.modelPeriodType.type());
            endYear = pw.getYear();
            startYear = this.periodStartEnd.length() == 10 ? new PeriodWrapper(this.periodStartEnd.substring(0, this.periodStartEnd.length() - 1)).getYear() : Integer.parseInt(this.periodStartEnd.substring(0, this.periodStartEnd.length() - 1));
        }
        this.addYearFilterByStartEnd(rowFilter, startYear, endYear);
    }

    private void addYearFilterByStartEnd(MemoryRowFilter rowFilter, int startYear, int endYear) {
        ArrayList<String> values = new ArrayList<String>();
        for (int i = startYear; i <= endYear; ++i) {
            values.add(String.valueOf(i));
        }
        TimegranularityFilter filter = new TimegranularityFilter((Column<TimeDimField>)this.metaData.getColumn(1), values, false);
        rowFilter.addFilter(filter);
    }

    private List<String> getParaValues(ParameterCalculator calculator, ParameterModel parameterModel, AbstractParameterDataSourceModel datasource) throws ParameterException {
        ParameterResultset value = calculator.getValue(parameterModel.getName());
        IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)datasource);
        List values = value.getValueAsString(valueFormat);
        return values;
    }

    public Metadata<TimeDimField> getMetaData() {
        return this.metaData;
    }

    public TimeDimField getTimeDimField() {
        return this.timeDimField;
    }

    public String getPeriodEntityId() {
        return this.periodEntityId;
    }

    public String getDataPattern() {
        return this.dataPattern;
    }

    public void setDataPattern(String dataPattern) {
        this.dataPattern = dataPattern;
    }

    public int getTimekeyPeriodType() {
        return this.timekeyPeriodType;
    }

    public void setTimekeyPeriodType(int timekeyPeriodType) {
        this.timekeyPeriodType = timekeyPeriodType;
    }

    public PeriodType getModelPeriodType() {
        return this.modelPeriodType;
    }

    public int getMinFiscalMonth() {
        return this.minFiscalMonth;
    }

    public void setMinFiscalMonth(int minFiscalMonth) {
        this.minFiscalMonth = minFiscalMonth;
    }

    public int getMaxFiscalMonth() {
        return this.maxFiscalMonth;
    }

    public void setMaxFiscalMonth(int maxFiscalMonth) {
        this.maxFiscalMonth = maxFiscalMonth;
    }
}

