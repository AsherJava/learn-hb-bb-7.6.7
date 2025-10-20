/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.parser.restriction;

import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.filter.ExpressionFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.FilterAnalyzer;
import com.jiuqi.bi.quickreport.engine.context.filter.FixedFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.MappingFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValueFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValuesFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.restriction.DSFieldInfo;
import com.jiuqi.bi.quickreport.engine.parser.restriction.DSTimeOffstBuilder;
import com.jiuqi.bi.quickreport.engine.parser.restriction.RestrictionAnalyzer;
import com.jiuqi.bi.quickreport.engine.parser.restriction.RestrictionDescriptor;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.util.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public final class DSFilterBuilder {
    private String curDataSet;
    private boolean ignoreContext;
    private boolean inlineMapping;
    private ReportContext context;
    private List<IASTNode> restrictions;
    private List<IFilterDescriptor> filters;

    public DSFilterBuilder(String curDataSet) {
        this.curDataSet = curDataSet;
        this.restrictions = new ArrayList<IASTNode>();
        this.filters = new ArrayList<IFilterDescriptor>();
    }

    public ReportContext getContext() {
        return this.context;
    }

    public void setContext(ReportContext context) {
        this.context = context;
    }

    public boolean ignoreContext() {
        return this.ignoreContext;
    }

    public void setIgnoreContext(boolean ignoreContext) {
        this.ignoreContext = ignoreContext;
    }

    public boolean inlineMapping() {
        return this.inlineMapping;
    }

    public void setInlineMapping(boolean inlineMapping) {
        this.inlineMapping = inlineMapping;
    }

    public List<IASTNode> getRestrictions() {
        return this.restrictions;
    }

    public void build() throws ReportExpressionException {
        if (this.ignoreContext) {
            this.convertRestrictions();
        } else {
            this.analyseRestrictions();
        }
        FilterAnalyzer.distinctFilters(this.filters);
        if (this.inlineMapping && this.curDataSet != null) {
            this.inlineMappings();
        }
        this.rowNumFirst();
    }

    private void convertRestrictions() throws ReportExpressionException {
        for (IASTNode restriction : this.restrictions) {
            IFilterDescriptor filterDescr = this.convertToDescriptor(restriction);
            this.filters.add(filterDescr);
        }
    }

    private IFilterDescriptor convertToDescriptor(IASTNode restriction) throws ReportExpressionException {
        RestrictionDescriptor descr = RestrictionAnalyzer.toDescriptor(this.context, restriction, this.curDataSet);
        if (descr.getMode() == 3) {
            return new ValueFilterDescriptor(descr.getField().dataSetName, descr.getField().field, descr.getInfo());
        }
        if (descr.getMode() == 4) {
            return new ValuesFilterDescriptor(descr.getField().dataSetName, descr.getField().field, (List)descr.getInfo());
        }
        if (descr.getMode() == 5) {
            IReportExpression expr = (IReportExpression)descr.getInfo();
            String dsFilter = expr.toDSFormula(this.context, this.curDataSet);
            return new ExpressionFilterDescriptor(descr.getField().dataSetName, descr.getField().field, dsFilter, expr);
        }
        if (descr.getMode() == 6) {
            return new MappingFilterDescriptor(descr.getField().dataSetName, descr.getField().field, (DSFieldInfo)descr.getInfo());
        }
        throw new ReportExpressionException("\u9650\u5b9a\u6a21\u5f0f\u9519\u8bef\uff0c\u5f53\u524d\u4ec5\u5141\u8bb8\u4f7f\u7528\u503c\u6216\u8868\u8fbe\u5f0f\u9650\u5b9a\uff1a" + restriction.toString());
    }

    private void analyseRestrictions() throws ReportExpressionException {
        ArrayList<MappingFilterDescriptor> mappingFilters = new ArrayList<MappingFilterDescriptor>();
        Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps = this.scanContext(mappingFilters);
        List<RestrictionDescriptor> offsets = this.analyseRestrictions(filterMaps, mappingFilters);
        this.offsetTimes(offsets, filterMaps);
        this.mergeFilters(filterMaps, mappingFilters);
    }

    private Map<DSFieldInfo, List<IFilterDescriptor>> scanContext(List<MappingFilterDescriptor> mappingFilters) throws ReportExpressionException {
        HashMap<DSFieldInfo, List<IFilterDescriptor>> filterMaps = new HashMap<DSFieldInfo, List<IFilterDescriptor>>();
        for (IFilterDescriptor filter : this.context.getCurrentFilters()) {
            if (filter instanceof FixedFilterDescriptor) {
                if (((FixedFilterDescriptor)filter).getValue()) continue;
                DSFieldInfo key = new DSFieldInfo(this.curDataSet, null);
                ArrayList<IFilterDescriptor> filters = new ArrayList<IFilterDescriptor>(1);
                filters.add(filter);
                filterMaps.put(key, filters);
                continue;
            }
            if (filter instanceof MappingFilterDescriptor) {
                mappingFilters.add((MappingFilterDescriptor)filter);
                continue;
            }
            this.putFilter(filterMaps, filter);
        }
        return filterMaps;
    }

    private void putFilter(Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps, IFilterDescriptor filter) {
        DSFieldInfo key = new DSFieldInfo(filter.getDataSetName(), filter.getField());
        List<IFilterDescriptor> filters = filterMaps.get(key);
        if (filters == null) {
            filters = new ArrayList<IFilterDescriptor>();
            filterMaps.put(key, filters);
        }
        filters.add(filter);
    }

    private List<RestrictionDescriptor> analyseRestrictions(Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps, List<MappingFilterDescriptor> mappingFilters) throws ReportExpressionException {
        if (this.restrictions.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<RestrictionDescriptor> offsets = new ArrayList<RestrictionDescriptor>();
        ArrayList<RestrictionDescriptor> fixedTimes = new ArrayList<RestrictionDescriptor>();
        for (IASTNode restriction : this.restrictions) {
            RestrictionDescriptor filterDescr = RestrictionAnalyzer.toDescriptor(this.context, restriction, this.curDataSet);
            if (filterDescr.getMode() == 6) {
                MappingFilterDescriptor mapping = new MappingFilterDescriptor(filterDescr.getField().dataSetName, filterDescr.getField().field, (DSFieldInfo)filterDescr.getInfo());
                mappingFilters.add(mapping);
                continue;
            }
            this.analyseRestriction(offsets, fixedTimes, filterDescr, filterMaps);
        }
        return this.mergeOffsets(offsets, fixedTimes, filterMaps);
    }

    private void analyseRestriction(List<RestrictionDescriptor> offsets, List<RestrictionDescriptor> fixedTimes, RestrictionDescriptor filterDescr, Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps) throws ReportExpressionException {
        switch (filterDescr.getMode()) {
            case 1: {
                this.removeFilters(filterMaps, filterDescr.getField());
                this.expandTimeKeyFilters(filterMaps, filterDescr.getField());
                break;
            }
            case 3: {
                if (filterDescr.getField().field.getFieldType() == FieldType.TIME_DIM && !filterDescr.getField().field.isTimekey()) {
                    fixedTimes.add(filterDescr);
                    break;
                }
                ValueFilterDescriptor filter = new ValueFilterDescriptor(filterDescr.getField().dataSetName, filterDescr.getField().field, filterDescr.getInfo());
                this.setFieldFilter(filterMaps, filterDescr.getField(), filter);
                break;
            }
            case 4: {
                ValuesFilterDescriptor filter = new ValuesFilterDescriptor(filterDescr.getField().dataSetName, filterDescr.getField().field, (List)filterDescr.getInfo());
                this.setFieldFilter(filterMaps, filterDescr.getField(), filter);
                break;
            }
            case 5: {
                IReportExpression expr = (IReportExpression)filterDescr.getInfo();
                String dsFilter = expr.toDSFormula(this.context, this.curDataSet);
                if (filterDescr.getField() == null) {
                    this.filters.add(new ExpressionFilterDescriptor(this.curDataSet, null, dsFilter, expr));
                    break;
                }
                ExpressionFilterDescriptor filter = new ExpressionFilterDescriptor(filterDescr.getField().dataSetName, filterDescr.getField().field, dsFilter, expr);
                this.setFieldFilter(filterMaps, filterDescr.getField(), filter);
                break;
            }
            case 2: {
                offsets.add(filterDescr);
                break;
            }
            default: {
                throw new ReportExpressionException("\u65e0\u6cd5\u8bc6\u522b\u7684\u9650\u5b9a\u6a21\u5f0f\uff1a" + filterDescr.getMode());
            }
        }
    }

    private List<RestrictionDescriptor> mergeOffsets(List<RestrictionDescriptor> offsets, List<RestrictionDescriptor> fixedTimes, Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps) {
        for (RestrictionDescriptor descr : fixedTimes) {
            if (this.removeFilters(filterMaps, descr.getField())) {
                ValueFilterDescriptor filter = new ValueFilterDescriptor(descr.getField().dataSetName, descr.getField().field, descr.getInfo());
                DSFilterBuilder.putFilter(filterMaps, descr.getField(), filter);
                continue;
            }
            offsets.add(descr);
        }
        return offsets;
    }

    private void setFieldFilter(Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps, DSFieldInfo field, IFilterDescriptor filter) {
        this.removeFilters(filterMaps, field);
        DSFilterBuilder.putFilter(filterMaps, field, filter);
    }

    private boolean removeFilters(Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps, DSFieldInfo field) {
        if (StringUtils.isEmpty((String)field.field.getKeyField())) {
            return filterMaps.remove(field) != null;
        }
        if (field.field.isTimekey()) {
            return this.removeTimeFilters(filterMaps, field);
        }
        return this.removeDimFilters(filterMaps, field);
    }

    private boolean removeTimeFilters(Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps, DSFieldInfo field) {
        boolean removed = false;
        Iterator<Map.Entry<DSFieldInfo, List<IFilterDescriptor>>> i = filterMaps.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<DSFieldInfo, List<IFilterDescriptor>> e = i.next();
            if (e.getKey().field == null || !e.getKey().dataSetName.equalsIgnoreCase(field.dataSetName) || e.getKey().field.getFieldType() != FieldType.TIME_DIM) continue;
            i.remove();
            removed = true;
        }
        return removed;
    }

    private boolean removeDimFilters(Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps, DSFieldInfo field) {
        boolean removed = false;
        Iterator<Map.Entry<DSFieldInfo, List<IFilterDescriptor>>> i = filterMaps.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<DSFieldInfo, List<IFilterDescriptor>> e = i.next();
            if (e.getKey().field == null || !e.getKey().dataSetName.equalsIgnoreCase(field.dataSetName) || !field.field.getName().equalsIgnoreCase(e.getKey().field.getName()) && !field.field.getKeyField().equalsIgnoreCase(e.getKey().field.getKeyField()) && !field.field.getNameField().equalsIgnoreCase(e.getKey().field.getNameField())) continue;
            i.remove();
            removed = true;
        }
        return removed;
    }

    static void putFilter(Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps, DSFieldInfo field, IFilterDescriptor filter) {
        ArrayList<IFilterDescriptor> filters = new ArrayList<IFilterDescriptor>(1);
        filters.add(filter);
        filterMaps.put(field, filters);
    }

    private boolean expandTimeKeyFilters(Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps, DSFieldInfo field) throws ReportExpressionException {
        if (field.field.isTimekey() || field.field.getTimegranularity() == null) {
            return false;
        }
        boolean expaned = false;
        for (Map.Entry<DSFieldInfo, List<IFilterDescriptor>> e : filterMaps.entrySet()) {
            if (!e.getKey().dataSetName.equalsIgnoreCase(field.dataSetName) || !e.getKey().field.isTimekey()) continue;
            if (e.getKey().field.getTimegranularity() != field.field.getTimegranularity()) {
                if (field.field.getTimegranularity() != TimeGranularity.YEAR) break;
                throw new ReportExpressionException("\u5728\u9650\u5b9a\u65f6\u95f4TIMEKEY\u4e4b\u540e\uff0c\u7981\u6b62\u518d\u5bf9\u5e74\u5ea6\u8fdb\u884cMB/ALL\u9650\u5b9a\u3002");
            }
            ListIterator<IFilterDescriptor> i = e.getValue().listIterator();
            while (i.hasNext()) {
                IFilterDescriptor filter = i.next();
                IFilterDescriptor newFilter = this.expandTimeKeyFilter(filter);
                i.set(newFilter);
                expaned = true;
            }
        }
        return expaned;
    }

    private IFilterDescriptor expandTimeKeyFilter(IFilterDescriptor filter) throws ReportExpressionException {
        SimpleDateFormat formatter = new SimpleDateFormat(filter.getField().getDataPattern());
        HashSet<Integer> years = new HashSet<Integer>();
        if (filter instanceof ValueFilterDescriptor) {
            String value = (String)((ValueFilterDescriptor)filter).getValue();
            int year = this.parseYearValue(filter, formatter, value);
            years.add(year);
        } else if (filter instanceof ValuesFilterDescriptor) {
            for (Object value : ((ValuesFilterDescriptor)filter).getValues()) {
                int year = this.parseYearValue(filter, formatter, (String)value);
                years.add(year);
            }
        } else {
            return filter;
        }
        switch (filter.getField().getTimegranularity()) {
            case MONTH: 
            case HALFYEAR: 
            case QUARTER: {
                return this.expandTimekeyValues(filter, years, formatter);
            }
        }
        return filter;
    }

    private int parseYearValue(IFilterDescriptor filter, SimpleDateFormat formatter, String value) throws ReportExpressionException {
        Date date;
        try {
            date = formatter.parse(value);
        }
        catch (ParseException e) {
            throw new ReportExpressionException("\u89e3\u6790\u5b57\u6bb5" + filter.getDataSetName() + "." + filter.getFieldName() + "\u9650\u5b9a\u503c\u683c\u5f0f\u9519\u8bef\uff1a" + value, e);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(1);
    }

    private IFilterDescriptor expandTimekeyValues(IFilterDescriptor filter, Set<Integer> years, SimpleDateFormat formatter) {
        ArrayList<Object> values = new ArrayList<Object>(years.size() * 2);
        TimeGranularity timeGranu = filter.getField().getTimegranularity();
        int monthCount = timeGranu.days() / 30;
        Calendar cal = Calendar.getInstance();
        cal.set(5, 1);
        for (Integer year : years) {
            cal.set(1, year);
            for (int i = 0; i < timeGranu.getMax(); ++i) {
                cal.set(2, i * monthCount);
                String timeKey = formatter.format(cal.getTime());
                values.add(timeKey);
            }
        }
        return new ValuesFilterDescriptor(filter.getDataSetName(), filter.getField(), values);
    }

    private void offsetTimes(List<RestrictionDescriptor> offsets, Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps) throws ReportExpressionException {
        if (!offsets.isEmpty()) {
            DSTimeOffstBuilder builder = new DSTimeOffstBuilder(this.context, filterMaps);
            builder.build(offsets);
        }
    }

    private void mergeFilters(Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps, List<MappingFilterDescriptor> mappingFilters) throws ReportExpressionException {
        Set<String> filterDSNames = this.scanMappings(mappingFilters);
        this.scanFilters(filterMaps, filterDSNames);
    }

    private Set<String> scanMappings(List<MappingFilterDescriptor> mappingFilters) throws ReportExpressionException {
        HashSet<String> dsNames = new HashSet<String>();
        dsNames.add(this.curDataSet);
        while (!mappingFilters.isEmpty()) {
            ArrayList<String> nextDSNames = new ArrayList<String>();
            Iterator<MappingFilterDescriptor> i = mappingFilters.iterator();
            while (i.hasNext()) {
                MappingFilterDescriptor mappingFilter = i.next();
                if (dsNames.contains(mappingFilter.getDataSetName())) {
                    this.filters.add(mappingFilter);
                    nextDSNames.add(mappingFilter.getMappingField().dataSetName);
                } else {
                    if (!dsNames.contains(mappingFilter.getMappingField().dataSetName)) continue;
                    this.filters.add(mappingFilter.reverse());
                    nextDSNames.add(mappingFilter.getDataSetName());
                }
                i.remove();
            }
            if (nextDSNames.isEmpty()) break;
            dsNames.addAll(nextDSNames);
        }
        if (!mappingFilters.isEmpty()) {
            throw new ReportExpressionException("\u65e0\u6548\u7684\u6570\u636e\u96c6\u5173\u8054\u6620\u5c04\uff1a" + mappingFilters);
        }
        return dsNames;
    }

    private void scanFilters(Map<DSFieldInfo, List<IFilterDescriptor>> filterMaps, Set<String> filterDSNames) {
        for (Map.Entry<DSFieldInfo, List<IFilterDescriptor>> entry : filterMaps.entrySet()) {
            if (entry.getKey().dataSetName != null && !filterDSNames.contains(entry.getKey().dataSetName)) continue;
            this.filters.addAll((Collection<IFilterDescriptor>)entry.getValue());
        }
    }

    private void inlineMappings() throws ReportExpressionException {
        HashMap<DSFieldInfo, DSFieldInfo> mappings = new HashMap<DSFieldInfo, DSFieldInfo>();
        for (IFilterDescriptor filter : this.filters) {
            if (!(filter instanceof MappingFilterDescriptor)) continue;
            MappingFilterDescriptor mapping = (MappingFilterDescriptor)filter;
            if (this.curDataSet.equalsIgnoreCase(mapping.getDataSetName())) {
                mappings.put(mapping.getMappingField(), new DSFieldInfo(mapping.getDataSetName(), mapping.getField()));
                continue;
            }
            if (!this.curDataSet.equalsIgnoreCase(mapping.getMappingField().dataSetName)) continue;
            mappings.put(new DSFieldInfo(mapping.getDataSetName(), mapping.getField()), mapping.getMappingField());
        }
        if (mappings.isEmpty()) {
            return;
        }
        Iterator<IFilterDescriptor> i = this.filters.listIterator();
        while (i.hasNext()) {
            DSFieldInfo mapField;
            IFilterDescriptor filter;
            filter = (IFilterDescriptor)i.next();
            if (filter instanceof MappingFilterDescriptor || filter instanceof ExpressionFilterDescriptor || filter.getDataSetName() == null || filter.getField() == null || (mapField = (DSFieldInfo)mappings.get(new DSFieldInfo(filter.getDataSetName(), filter.getField()))) == null) continue;
            IFilterDescriptor newFilter = filter.mapTo(mapField.dataSetName, mapField.field);
            i.set(newFilter);
        }
    }

    private void rowNumFirst() {
        int rowNumIndex = -1;
        for (int i = 0; i < this.filters.size(); ++i) {
            if (!"SYS_ROWNUM".equals(this.filters.get(i).getFieldName())) continue;
            rowNumIndex = i;
            break;
        }
        if (rowNumIndex <= 0) {
            return;
        }
        IFilterDescriptor rowNumFilter = this.filters.get(rowNumIndex);
        for (int i = rowNumIndex; i > 0; --i) {
            IFilterDescriptor filter = this.filters.get(i - 1);
            this.filters.set(i, filter);
        }
        this.filters.set(0, rowNumFilter);
    }

    public List<IFilterDescriptor> getFilters() {
        return this.filters;
    }
}

