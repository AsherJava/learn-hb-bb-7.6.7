/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataType
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.text.DateFormatEx
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.time.TimeHelper
 */
package com.jiuqi.bi.quickreport.engine.parser.restriction;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.FilterAnalyzer;
import com.jiuqi.bi.quickreport.engine.context.filter.FilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValueFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValuesFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.restriction.DSFieldInfo;
import com.jiuqi.bi.quickreport.engine.parser.restriction.DSFilterBuilder;
import com.jiuqi.bi.quickreport.engine.parser.restriction.RestrictionDescriptor;
import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.time.TimeHelper;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class DSTimeOffstBuilder {
    private final ReportContext context;
    private Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps;

    public DSTimeOffstBuilder(ReportContext context, Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps) {
        this.context = context;
        this.filterMaps = filterMaps;
    }

    public DSTimeOffstBuilder(ReportContext context) {
        this.context = context;
        this.filterMaps = new HashMap<DSFieldInfo, List<IFilterDescriptor>>();
    }

    public Map<DSFieldInfo, List<IFilterDescriptor>> getFilters() {
        return this.filterMaps;
    }

    public Locator addFilter(IFilterDescriptor filter) {
        DSFieldInfo fieldInfo = new DSFieldInfo(filter.getDataSetName(), filter.getField());
        List<IFilterDescriptor> filters = this.filterMaps.get(fieldInfo);
        if (filters == null) {
            filters = new ArrayList<IFilterDescriptor>();
            this.filterMaps.put(fieldInfo, filters);
        }
        filters.add(filter);
        return new Locator(fieldInfo, filters.size() - 1);
    }

    public IFilterDescriptor getFilter(Locator locator) {
        List<IFilterDescriptor> filters = this.filterMaps.get(locator.fieldInfo);
        return filters == null || filters.size() <= locator.index ? null : filters.get(locator.index);
    }

    public void build(List<RestrictionDescriptor> offsets) throws ReportExpressionException {
        for (RestrictionDescriptor offset : offsets) {
            this.build(offset);
        }
    }

    public void build(RestrictionDescriptor offset) throws ReportExpressionException {
        switch (offset.getMode()) {
            case 2: {
                this.offsetTime(offset);
                break;
            }
            case 3: {
                this.fixedTime(offset);
                break;
            }
            default: {
                throw new ReportExpressionException("\u9519\u8bef\u7684\u65f6\u95f4\u504f\u79fb\u6a21\u5f0f\uff1a" + offset.getMode());
            }
        }
    }

    private void offsetTime(RestrictionDescriptor descr) throws ReportExpressionException {
        DSField offsetField = descr.getField().field;
        if (offsetField.getFieldType() != FieldType.TIME_DIM) {
            throw new ReportExpressionException("\u5b57\u6bb5[" + descr.getField().toString() + "]\u4e0d\u662f\u65f6\u95f4\u7ef4\u5ea6\uff0c\u65e0\u6cd5\u8fdb\u884c\u504f\u79fb\u3002");
        }
        if (offsetField.isTimekey()) {
            this.offsetByTimeKey(descr, offsetField);
        } else if (offsetField.getTimegranularity() == TimeGranularity.YEAR) {
            this.offsetByYear(descr, offsetField);
        } else {
            this.offsetNonYear(descr, descr.getField());
        }
    }

    private void fixedTime(RestrictionDescriptor descr) throws ReportExpressionException {
        boolean fixed = false;
        for (Map.Entry<DSFieldInfo, List<IFilterDescriptor>> e : this.filterMaps.entrySet()) {
            DSField field = e.getKey().field;
            if (!descr.restrictOn(descr.getField().dataSetName) || field.getTimegranularity() != descr.getField().field.getTimegranularity()) continue;
            if (field.isTimekey()) {
                this.setTimeKey(e.getKey(), e.getValue(), descr.getField().field, descr.getInfo());
            } else {
                e.getValue().clear();
                e.getValue().add(new ValueFilterDescriptor(e.getKey().dataSetName, field, descr.getInfo()));
            }
            fixed = true;
        }
        if (!fixed) {
            ValueFilterDescriptor filter = new ValueFilterDescriptor(descr.getField().dataSetName, descr.getField().field, descr.getInfo());
            DSFilterBuilder.putFilter(this.filterMaps, descr.getField(), filter);
        }
    }

    private void offsetByTimeKey(RestrictionDescriptor descr, DSField offsetField) throws ReportExpressionException {
        int delta = ((Number)descr.getInfo()).intValue();
        boolean processed = false;
        for (Map.Entry<DSFieldInfo, List<IFilterDescriptor>> e : this.filterMaps.entrySet()) {
            DSField field;
            if (e.getKey() == null || (field = e.getKey().field) == null || !descr.restrictOn(e.getKey().dataSetName)) continue;
            if (field.isTimekey()) {
                this.offsetTimeKey(e.getKey(), e.getValue(), delta);
            } else {
                if (field.getTimegranularity() != offsetField.getTimegranularity()) continue;
                if (field.getTimegranularity() == TimeGranularity.YEAR) {
                    this.offsetYearByYear(field, e.getValue(), delta);
                } else {
                    this.offsetTimeNonYear(e.getKey(), delta);
                }
            }
            processed = true;
        }
        if (!processed) {
            throw new ReportExpressionException("\u57fa\u4e8e\u5b57\u6bb5[" + descr.getField() + "]\u7684\u504f\u79fb\u65e0\u6548\uff0c\u672a\u5bf9\u8be5\u5b57\u6bb5\u8fdb\u884c\u4efb\u4f55\u9650\u5b9a\u3002");
        }
    }

    private void offsetNonYear(RestrictionDescriptor descr, DSFieldInfo offsetField) throws ReportExpressionException {
        int delta = ((Number)descr.getInfo()).intValue();
        List<IFilterDescriptor> filters = this.filterMaps.get(offsetField);
        if (filters == null) {
            if (!this.offsetTimeKeys(descr, offsetField, delta)) {
                throw new ReportExpressionException("\u504f\u79fb\u5b57\u6bb5[" + descr.getField() + "]\u672a\u8fdb\u884c\u4efb\u4f55\u9650\u5b9a\uff0c\u65e0\u6cd5\u8fdb\u884c\u504f\u79fb\u3002");
            }
        } else if (offsetField.field.isTimekey()) {
            this.offsetTimeKey(offsetField, filters, delta);
        } else {
            this.offsetTimeNonYear(offsetField, delta);
            this.offsetTimeKeys(descr, offsetField, delta);
        }
    }

    private boolean offsetTimeKeys(RestrictionDescriptor descr, DSFieldInfo offsetField, int delta) throws ReportExpressionException {
        boolean processed = false;
        for (Map.Entry<DSFieldInfo, List<IFilterDescriptor>> e : this.filterMaps.entrySet()) {
            DSField field;
            if (e.getKey() == null || !descr.restrictOn(e.getKey().dataSetName) || !(field = e.getKey().field).isTimekey()) continue;
            int[] timeOffsets = this.getTimeOffsetInfos(offsetField.field, delta);
            this.offsetTimeKey(e.getKey(), e.getValue(), timeOffsets[0], timeOffsets[1]);
            processed = true;
        }
        return processed;
    }

    private void offsetTimeKey(DSFieldInfo offsetField, List<IFilterDescriptor> filters, int delta) throws ReportExpressionException {
        int[] timeOffsets = this.getTimeOffsetInfos(offsetField.field, delta);
        this.offsetTimeKey(offsetField, filters, timeOffsets[0], timeOffsets[1]);
    }

    private void offsetTimeKey(DSFieldInfo offsetField, List<IFilterDescriptor> filters, int timeField, int timeOffset) throws ReportExpressionException {
        DateFormatEx formatter = this.createDateFormat(offsetField);
        if (offsetField.field.getTimegranularity() == TimeGranularity.DAY && timeField != 5) {
            int dayStart = offsetField.field.getDataPattern().indexOf(100);
            int dayStop = offsetField.field.getDataPattern().lastIndexOf(100);
            if (dayStart == -1 || dayStop == -1) {
                throw new ReportExpressionException("TIMEKEY\u5b57\u6bb5[" + offsetField.field.getName() + "]\u8bbe\u7f6e\u4e86\u9519\u8bef\u7684\u6570\u636e\u683c\u5f0f\u201c" + offsetField.field.getDataPattern() + "\u201d");
            }
            this.offsetTimeKey(formatter, filters, timeField, timeOffset, dayStart, dayStop + 1);
        } else {
            this.offsetTimeKey(formatter, filters, timeField, timeOffset, -1, -1);
        }
    }

    private void offsetTimeKey(DateFormatEx formatter, List<IFilterDescriptor> filters, int timeField, int timeOffset, int dayStart, int dayStop) throws ReportExpressionException {
        ListIterator<IFilterDescriptor> i = filters.listIterator();
        while (i.hasNext()) {
            FilterDescriptor newFilter;
            IFilterDescriptor filter = i.next();
            if (filter instanceof ValueFilterDescriptor) {
                String timeKeyValue = (String)((ValueFilterDescriptor)filter).getValue();
                String newValue = this.offsetTimeKeyValue(formatter, timeField, timeOffset, timeKeyValue, dayStart, dayStop);
                newFilter = new ValueFilterDescriptor(filter.getDataSetName(), filter.getField(), newValue);
            } else if (filter instanceof ValuesFilterDescriptor) {
                ArrayList<Object> newValues = new ArrayList<Object>();
                for (Object timeKeyValue : ((ValuesFilterDescriptor)filter).getValues()) {
                    String newValue = this.offsetTimeKeyValue(formatter, timeField, timeOffset, (String)timeKeyValue, dayStart, dayStop);
                    newValues.add(newValue);
                }
                newFilter = new ValuesFilterDescriptor(filter.getDataSetName(), filter.getField(), newValues);
            } else {
                throw new ReportExpressionException("\u5b57\u6bb5[" + filter.toString() + "]\u8fdb\u884c\u4e86\u590d\u6742\u9650\u5b9a\uff0c\u65e0\u6cd5\u8fdb\u884c\u504f\u79fb\u64cd\u4f5c\u3002");
            }
            i.set(newFilter);
        }
    }

    private String offsetTimeKeyValue(DateFormatEx formatter, int timeField, int timeOffset, String rawValue, int dayStart, int dayStop) throws ReportExpressionException {
        Calendar date;
        if (rawValue == null) {
            return null;
        }
        try {
            date = formatter.parseCalendar(rawValue);
        }
        catch (ParseException e) {
            throw new ReportExpressionException(e);
        }
        date.add(timeField, timeOffset);
        String newValue = formatter.format(date);
        if (dayStart >= 0 && dayStop >= 0) {
            return newValue.substring(0, dayStart) + rawValue.substring(dayStart, dayStop) + newValue.substring(dayStop);
        }
        return newValue;
    }

    private void offsetTimeKeyByYear(DSFieldInfo field, List<IFilterDescriptor> filters, int delta) throws ReportExpressionException {
        this.offsetTimeKey(field, filters, 1, delta);
    }

    private void offsetYearByYear(DSField field, List<IFilterDescriptor> filters, int delta) throws ReportExpressionException {
        if (field.getValType() == DataType.INTEGER.value()) {
            ListIterator<IFilterDescriptor> i = filters.listIterator();
            while (i.hasNext()) {
                IFilterDescriptor filter = i.next();
                this.checkOffsetRestriction(filter);
                Number rawValue = (Number)((ValueFilterDescriptor)filter).getValue();
                int newValue = rawValue.intValue() + delta;
                ValueFilterDescriptor newFilter = new ValueFilterDescriptor(filter.getDataSetName(), filter.getField(), newValue);
                i.set(newFilter);
            }
        } else if (field.getValType() == DataType.STRING.value()) {
            ListIterator<IFilterDescriptor> i = filters.listIterator();
            while (i.hasNext()) {
                IFilterDescriptor filter = i.next();
                this.checkOffsetRestriction(filter);
                int rawValue = Integer.parseInt((String)((ValueFilterDescriptor)filter).getValue());
                int newValue = rawValue + delta;
                ValueFilterDescriptor newFilter = new ValueFilterDescriptor(filter.getDataSetName(), filter.getField(), Integer.toString(newValue));
                i.set(newFilter);
            }
        } else {
            throw new ReportExpressionException();
        }
    }

    private void offsetByYear(RestrictionDescriptor descr, DSField offsetField) throws ReportExpressionException {
        int delta = ((Number)descr.getInfo()).intValue();
        boolean processed = false;
        for (Map.Entry<DSFieldInfo, List<IFilterDescriptor>> e : this.filterMaps.entrySet()) {
            if (e.getKey() == null || !descr.restrictOn(e.getKey().dataSetName)) continue;
            DSField field = e.getKey().field;
            if (field.isTimekey()) {
                this.offsetTimeKeyByYear(e.getKey(), e.getValue(), delta);
            } else {
                if (field.getTimegranularity() != TimeGranularity.YEAR) continue;
                this.offsetYearByYear(field, e.getValue(), delta);
            }
            processed = true;
        }
        if (!processed) {
            throw new ReportExpressionException("\u57fa\u4e8e\u5b57\u6bb5[" + descr.getField() + "]\u7684\u504f\u79fb\u65e0\u6548\uff0c\u672a\u5bf9\u8be5\u5b57\u6bb5\u8fdb\u884c\u4efb\u4f55\u9650\u5b9a\u3002");
        }
    }

    private void offsetTimeNonYear(DSFieldInfo field, int delta) throws ReportExpressionException {
        DSFieldInfo nextField;
        DSField dsField = field.field;
        TimeGranularity timeGranu = dsField.getTimegranularity();
        if (timeGranu == TimeGranularity.XUN) {
            throw new ReportExpressionException("\u4e0d\u652f\u6301\u5bf9\u65ec\u7684\u504f\u79fb\u64cd\u4f5c\u3002");
        }
        List<IFilterDescriptor> filters = this.filterMaps.get(field);
        if (filters == null || filters.isEmpty()) {
            throw new ReportExpressionException("\u504f\u79fb\u5b57\u6bb5[" + field + "]\u4e0d\u662f\u4e3b\u63a7\u5355\u5143\u683c\u6216\u6709\u6548\u7684\u9650\u5b9a\u6761\u4ef6\u3002");
        }
        if (filters.size() > 1) {
            FilterAnalyzer.distinctFilters(filters);
        }
        if (filters.size() != 1) {
            throw new ReportExpressionException("\u504f\u79fb\u5b57\u6bb5[" + field + "]\u7684\u9650\u5b9a\u6761\u4ef6\u8fc7\u591a\uff0c\u65e0\u6cd5\u8fdb\u884c\u504f\u79fb\u64cd\u4f5c\u3002");
        }
        IFilterDescriptor filter = filters.get(0);
        this.checkOffsetRestriction(filter);
        int rawValue = this.parseTimeValue(dsField, filter);
        int newValue = rawValue + delta;
        int realValue = this.adjustTimeValue(newValue, timeGranu);
        Object valObj = this.formatFieldValue(dsField, realValue);
        ValueFilterDescriptor newFilter = new ValueFilterDescriptor(filter.getDataSetName(), filter.getField(), valObj);
        filters.set(0, newFilter);
        if ((newValue - rawValue >= timeGranu.getMax() || newValue <= 0) && (nextField = this.findNextTimeField(timeGranu)) != null) {
            int nextDelta;
            DSField dsNextField = nextField.field;
            if (newValue <= 0) {
                int cycleCount = dsNextField.getTimegranularity().days() / timeGranu.days();
                nextDelta = -1;
                while (Math.abs(nextDelta * cycleCount) + newValue < 0) {
                    --nextDelta;
                }
            } else {
                nextDelta = (newValue - rawValue) * timeGranu.days() / dsNextField.getTimegranularity().days();
            }
            this.offsetTimeNonYear(nextField, nextDelta);
        }
    }

    private int parseTimeValue(DSField dsField, IFilterDescriptor filter) throws ReportExpressionException {
        if (dsField.getValType() == DataType.INTEGER.value()) {
            return ((Number)((ValueFilterDescriptor)filter).getValue()).intValue();
        }
        if (dsField.getValType() == DataType.STRING.value()) {
            return Integer.parseInt((String)((ValueFilterDescriptor)filter).getValue());
        }
        throw new ReportExpressionException("\u65f6\u671f\u5b57\u6bb5\u9650\u5b9a\u7684\u6570\u636e\u7c7b\u578b\u9519\u8bef\uff1a" + filter);
    }

    private int adjustTimeValue(int newValue, TimeGranularity timeGranu) {
        if (newValue > 0 && newValue <= timeGranu.getMax()) {
            return newValue;
        }
        if (newValue > timeGranu.getMax()) {
            return newValue % timeGranu.getMax();
        }
        return newValue % timeGranu.getMax() + timeGranu.getMax();
    }

    private void setTimeKey(DSFieldInfo timeKey, List<IFilterDescriptor> timeKeyValues, DSField field, Object valueObj) throws ReportExpressionException {
        int dateField;
        int value = field.getValType() == DataType.STRING.value() ? Integer.parseInt((String)valueObj) : ((Number)valueObj).intValue();
        switch (field.getTimegranularity()) {
            case QUARTER: {
                dateField = 2;
                value = (value - 1) * 3;
                break;
            }
            case MONTH: {
                dateField = 2;
                --value;
                break;
            }
            default: {
                throw new ReportExpressionException("\u9519\u8bef\u7684\u5b57\u6bb5\u9650\u5b9a\uff1a" + field.toString());
            }
        }
        DateFormatEx formatter = this.createDateFormat(timeKey);
        ListIterator<IFilterDescriptor> i = timeKeyValues.listIterator();
        while (i.hasNext()) {
            Calendar date;
            IFilterDescriptor filter = i.next();
            this.checkOffsetRestriction(filter);
            try {
                date = formatter.parseCalendar((String)((ValueFilterDescriptor)filter).getValue());
            }
            catch (ParseException e) {
                throw new ReportExpressionException(e);
            }
            date.set(dateField, value);
            String newValue = formatter.format(date);
            ValueFilterDescriptor newFilter = new ValueFilterDescriptor(filter.getDataSetName(), filter.getField(), newValue);
            i.set(newFilter);
        }
    }

    private int[] getTimeOffsetInfos(DSField offsetField, int delta) throws ReportExpressionException {
        int timeOffset;
        int timeField;
        switch (offsetField.getTimegranularity()) {
            case YEAR: {
                timeField = 1;
                timeOffset = delta;
                break;
            }
            case MONTH: {
                timeField = 2;
                timeOffset = delta;
                break;
            }
            case DAY: {
                timeField = 5;
                timeOffset = delta;
                break;
            }
            case QUARTER: {
                timeField = 2;
                timeOffset = delta * 3;
                break;
            }
            case HALFYEAR: {
                timeField = 2;
                timeOffset = delta * 6;
                break;
            }
            case WEEK: {
                timeField = 6;
                timeOffset = delta * 7;
                break;
            }
            case XUN: {
                throw new ReportExpressionException("\u5c1a\u672a\u652f\u6301\u5bf9\u65ec\u7c7b\u578b\u7684\u504f\u79fb\u3002");
            }
            default: {
                throw new ReportExpressionException("\u504f\u79fb\u65f6\u9047\u5230\u672a\u77e5\u7684\u65f6\u671f\u7c7b\u578b\uff1a" + offsetField.getTimegranularity());
            }
        }
        return new int[]{timeField, timeOffset};
    }

    private Object formatFieldValue(DSField field, int value) {
        if (field.getValType() == DataType.INTEGER.value()) {
            return value;
        }
        String rawStr = Integer.toString(value);
        if (StringUtils.isEmpty((String)field.getDataPattern())) {
            return rawStr;
        }
        if (field.getDataPattern().length() > rawStr.length()) {
            StringBuilder buffer = new StringBuilder(field.getDataPattern().length());
            for (int i = rawStr.length(); i < field.getDataPattern().length(); ++i) {
                buffer.append('0');
            }
            buffer.append(rawStr);
            return buffer.toString();
        }
        return rawStr;
    }

    private DSFieldInfo findNextTimeField(TimeGranularity timeGranu) throws ReportExpressionException {
        DSFieldInfo lastField = null;
        TimeGranularity lastTimeGranu = null;
        for (DSFieldInfo field : this.filterMaps.keySet()) {
            DSField dsField = field.field;
            if (dsField.getFieldType() != FieldType.TIME_DIM || dsField.isTimekey() || dsField.getTimegranularity().days() <= timeGranu.days() || lastTimeGranu != null && dsField.getTimegranularity().days() >= lastTimeGranu.days()) continue;
            lastField = field;
            lastTimeGranu = dsField.getTimegranularity();
        }
        return lastField;
    }

    private void checkOffsetRestriction(IFilterDescriptor filter) throws ReportExpressionException {
        if (!(filter instanceof ValueFilterDescriptor)) {
            throw new ReportExpressionException("\u5b57\u6bb5[" + filter.toString() + "]\u8fdb\u884c\u4e86\u590d\u6742\u9650\u5b9a\uff0c\u65e0\u6cd5\u8fdb\u884c\u504f\u79fb\u64cd\u4f5c\u3002");
        }
        if (((ValueFilterDescriptor)filter).getValue() == null) {
            throw new ReportExpressionException("\u5b57\u6bb5[" + filter.toString() + "]\u672a\u9650\u5b9a\u5177\u4f53\u503c\uff0c\u65e0\u6cd5\u8fdb\u884c\u504f\u79fb\u64cd\u4f5c\u3002");
        }
    }

    private DateFormatEx createDateFormat(DSFieldInfo dsField) throws ReportExpressionException {
        DSModel dsModel;
        DateFormatEx format = new DateFormatEx(dsField.field.getDataPattern(), this.context.getLocale());
        try {
            dsModel = this.context.openDataSetModel(dsField.dataSetName);
        }
        catch (ReportContextException e) {
            throw new ReportExpressionException(e);
        }
        if (dsModel.getMinFiscalMonth() >= 0 && dsModel.getMaxFiscalMonth() >= 12) {
            format.setCalendar(TimeHelper.newCalendar((int)dsModel.getMinFiscalMonth(), (int)dsModel.getMaxFiscalMonth()));
        }
        return format;
    }

    public static final class Locator {
        protected final DSFieldInfo fieldInfo;
        protected final int index;

        protected Locator(DSFieldInfo fieldInfo, int index) {
            this.fieldInfo = fieldInfo;
            this.index = index;
        }
    }
}

