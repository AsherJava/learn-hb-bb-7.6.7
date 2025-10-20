/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.model;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.DataSourceUtils;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.manager.DataSourceAttrBean;
import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.manager.IDataSourceDataProvider;
import com.jiuqi.bi.parameter.manager.IDataSourceDataProviderEx;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SmartSelector
implements Cloneable {
    private SelectType type;
    private List<SelectedValue> selectedValues = new Vector<SelectedValue>();
    private boolean exact = false;
    private String matchTxt = "";
    private List<RangeItem> ranges = new Vector<RangeItem>();
    private Object defaultValue;
    private static final String SELECT_TYPE = "type";
    private static final String SELECTED_VALUES = "selectedValues";
    private static final String IS_EXACT = "exact";
    private static final String MATCH_TXT = "matchTxt";
    private static final String RANGE_ITEMS = "ranges";
    public static final String TAG_DEFAULT_VALUE = "defaultValue";

    public void load(JSONObject json) throws JSONException {
        int i;
        JSONArray ary;
        int tv = json.optInt(SELECT_TYPE, 0);
        this.type = SelectType.typeOf(tv);
        if (!json.isNull(SELECTED_VALUES)) {
            ary = json.getJSONArray(SELECTED_VALUES);
            for (i = 0; i < ary.length(); ++i) {
                JSONObject jo = ary.getJSONObject(i);
                SelectedValue kv = new SelectedValue();
                kv.fromJson(jo);
                this.selectedValues.add(kv);
            }
        }
        this.exact = json.optBoolean(IS_EXACT, false);
        this.matchTxt = json.optString(MATCH_TXT);
        if (!json.isNull(RANGE_ITEMS)) {
            ary = json.getJSONArray(RANGE_ITEMS);
            for (i = 0; i < ary.length(); ++i) {
                RangeItem item = new RangeItem();
                item.fromJson(ary.getJSONObject(i));
                this.ranges.add(item);
            }
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        if (this.type != null) {
            json.put(SELECT_TYPE, this.type.value);
        }
        JSONArray ary = new JSONArray();
        for (SelectedValue s : this.selectedValues) {
            ary.put((Object)s.toJson());
        }
        json.put(SELECTED_VALUES, (Object)ary);
        json.put(IS_EXACT, this.exact);
        if (this.matchTxt != null) {
            json.put(MATCH_TXT, (Object)this.matchTxt);
        }
        JSONArray rangeArray = new JSONArray();
        for (RangeItem item : this.ranges) {
            rangeArray.put((Object)item.toJson());
        }
        json.put(RANGE_ITEMS, (Object)rangeArray);
        return json;
    }

    public void setType(SelectType type) {
        this.type = type;
    }

    public SelectType getType() {
        return this.type;
    }

    public List<SelectedValue> getSelectedValues() {
        return this.selectedValues;
    }

    public void setExact(boolean exact) {
        this.exact = exact;
    }

    public boolean isExact() {
        return this.exact;
    }

    public void setMatchTxt(String matchTxt) {
        this.matchTxt = matchTxt;
    }

    public String getMatchTxt() {
        return this.matchTxt;
    }

    public List<RangeItem> getRanges() {
        return this.ranges;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public void updateSelectedValueByParamValue(MemoryDataSet<?> dataset) {
        if (this.getType() == SelectType.FIXED) {
            this.selectedValues.clear();
            for (DataRow row : dataset) {
                SelectedValue sv = new SelectedValue(row.getValue(0), row.getValue(1) == null ? null : row.getValue(1).toString());
                this.selectedValues.add(sv);
            }
        }
    }

    public String toBQL(String fieldName, int dataType) {
        if (this.type == SelectType.FIXED) {
            return this.toSqlByFixType(fieldName, dataType, true);
        }
        if (this.type == SelectType.FUZZY) {
            return this.toSqlByFuzzyType(fieldName, dataType, true);
        }
        if (this.type == SelectType.RANGE) {
            return this.toSqlByRangeType(fieldName, dataType);
        }
        if (this.defaultValue != null) {
            if (this.defaultValue instanceof MemoryDataSet) {
                return this.toSqlByMemoryDataset(fieldName, dataType, true);
            }
            if (this.defaultValue instanceof List) {
                return this.toSqlByListValues(fieldName, dataType, true);
            }
            return "";
        }
        return "";
    }

    public String toSQL(String fieldName, int dataType) {
        if (this.type == SelectType.FIXED) {
            return this.toSqlByFixType(fieldName, dataType, false);
        }
        if (this.type == SelectType.FUZZY) {
            return this.toSqlByFuzzyType(fieldName, dataType, false);
        }
        if (this.type == SelectType.RANGE) {
            return this.toSqlByRangeType(fieldName, dataType);
        }
        if (this.defaultValue != null) {
            if (this.defaultValue instanceof MemoryDataSet) {
                return this.toSqlByMemoryDataset(fieldName, dataType, false);
            }
            if (this.defaultValue instanceof List) {
                return this.toSqlByListValues(fieldName, dataType, false);
            }
            return "";
        }
        return "";
    }

    private String toSqlByFixType(String fieldName, int dataType, boolean isBql) {
        if (this.selectedValues.isEmpty()) {
            return " 1=1";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(fieldName).append(" in ");
        builder.append(isBql ? "{" : "(");
        int n = 0;
        for (SelectedValue sv : this.selectedValues) {
            if (n > 0) {
                builder.append(", ");
            }
            ++n;
            this.appendQuote(builder, dataType, sv.value);
        }
        builder.append(isBql ? "}" : ")");
        return builder.toString();
    }

    private String toSqlByFuzzyType(String fieldName, int dataType, boolean isBql) {
        StringBuilder builder = new StringBuilder();
        String[] keys = this.matchTxt.split("[,|.|\uff0c|\u3002|\uff1b|;|\n| ]");
        if (this.isExact()) {
            builder.append(fieldName).append(" in ").append(isBql ? "{" : "(");
            for (int i = 0; i < keys.length; ++i) {
                if (i > 0) {
                    builder.append(",");
                }
                builder.append("'").append(keys[i].trim()).append("'");
            }
            builder.append(isBql ? "}" : ")");
        } else {
            for (int i = 0; i < keys.length; ++i) {
                if (i > 0) {
                    builder.append(" or ");
                }
                builder.append(fieldName).append(" like '%").append(keys[i].trim()).append("%' ");
            }
        }
        return builder.toString();
    }

    private String toSqlByRangeType(String fieldName, int dataType) {
        StringBuilder builder = new StringBuilder();
        StringBuilder b1 = new StringBuilder();
        StringBuilder b2 = new StringBuilder();
        for (RangeItem range : this.ranges) {
            if (range.rtype == RangeType.STARTWITH) {
                if (b1.length() > 0) {
                    b1.append(" or ");
                }
                b1.append(fieldName).append(" like '").append(range.value).append("%' ");
                continue;
            }
            if (range.rtype == RangeType.INCLUDERANGE) {
                if (b1.length() > 0) {
                    b1.append(" or ");
                }
                b1.append("(");
                if (StringUtils.isNotEmpty((String)this.formatValue(dataType, range.minValue))) {
                    b1.append(fieldName).append(">=");
                    this.appendQuote(b1, dataType, range.minValue);
                }
                if (StringUtils.isNotEmpty((String)this.formatValue(dataType, range.maxValue))) {
                    if (StringUtils.isNotEmpty((String)this.formatValue(dataType, range.minValue))) {
                        b1.append(" and ");
                    }
                    b1.append(fieldName).append("<=");
                    this.appendQuote(b1, dataType, range.maxValue);
                }
                b1.append(")");
                continue;
            }
            if (range.rtype == RangeType.EXCLUDERANGE) {
                if (b2.length() > 0) {
                    b2.append(" and ");
                }
                b2.append("(");
                if (StringUtils.isNotEmpty((String)this.formatValue(dataType, range.minValue))) {
                    b2.append(fieldName).append("<");
                    this.appendQuote(b2, dataType, range.minValue);
                }
                if (StringUtils.isNotEmpty((String)this.formatValue(dataType, range.maxValue))) {
                    if (StringUtils.isNotEmpty((String)this.formatValue(dataType, range.minValue))) {
                        b2.append(" or ");
                    }
                    b2.append(fieldName).append(">");
                    this.appendQuote(b2, dataType, range.maxValue);
                }
                b2.append(")");
                continue;
            }
            if (range.rtype != RangeType.EXCLUDEVALUE) continue;
            if (b2.length() > 0) {
                b2.append(" and ");
            }
            b2.append(fieldName).append("<>");
            this.appendQuote(b2, dataType, range.value);
        }
        if (b1.length() > 0) {
            builder.append("(").append((CharSequence)b1).append(")");
        }
        if (b2.length() > 0) {
            if (b1.length() > 0) {
                builder.append(" and ");
            }
            builder.append("(").append((CharSequence)b2).append(")");
        }
        return builder.toString();
    }

    private String toSqlByMemoryDataset(String fieldName, int dataType, boolean isBql) {
        StringBuilder builder = new StringBuilder();
        MemoryDataSet dv = (MemoryDataSet)this.defaultValue;
        if (dv.isEmpty()) {
            return " 1=1";
        }
        Iterator itor = dv.iterator();
        int n = 0;
        builder.append(fieldName).append(" in ").append(isBql ? "{" : "(");
        while (itor.hasNext()) {
            if (n > 0) {
                builder.append(", ");
            }
            ++n;
            DataRow row = (DataRow)itor.next();
            this.appendQuote(builder, dataType, row.getValue(0));
        }
        builder.append(isBql ? "}" : ")");
        return builder.toString();
    }

    private String toSqlByListValues(String fieldName, int dataType, boolean isBql) {
        StringBuilder builder = new StringBuilder();
        List l = (List)this.defaultValue;
        int n = 0;
        builder.append(fieldName).append(" in ").append(isBql ? "{" : "(");
        for (Object v : l) {
            if (n > 0) {
                builder.append(", ");
            }
            ++n;
            this.appendQuote(builder, dataType, v);
        }
        builder.append(isBql ? "}" : ")");
        return builder.toString();
    }

    private void appendQuote(StringBuilder buffer, int dataType, Object value) {
        boolean quote = false;
        if (dataType == 0) {
            if (value instanceof String || value instanceof Calendar) {
                quote = true;
            }
        } else if (dataType == 6 || dataType == 2) {
            quote = true;
        }
        if (quote) {
            buffer.append("'");
        }
        if (dataType == 3 && value instanceof String) {
            buffer.append(value);
        } else {
            buffer.append(DataType.formatValue((int)dataType, (Object)value));
        }
        if (quote) {
            buffer.append("'");
        }
    }

    private String formatValue(int dataType, Object value) {
        if (value == null) {
            return null;
        }
        if (dataType == 3 && value instanceof String) {
            return value.toString();
        }
        return DataType.formatValue((int)dataType, (Object)value);
    }

    public void validate() throws ParameterException {
        if (this.type == null) {
            throw new ParameterException("\u672a\u8bbe\u7f6e\u9009\u62e9\u5668\u9009\u62e9\u7c7b\u578b");
        }
        if (this.type == SelectType.FIXED) {
            if (this.selectedValues.isEmpty()) {
                throw new ParameterException("\u5f53\u524d\u6a21\u5f0f\u4e3a\u6307\u5b9a\u53d6\u503c\uff0c\u4f46\u53ef\u9009\u503c\u5217\u8868\u4e3a\u7a7a");
            }
        } else if (this.type == SelectType.FUZZY) {
            if (this.matchTxt == null || this.matchTxt.trim().length() == 0) {
                throw new ParameterException("\u5f53\u524d\u6a21\u5f0f\u4e3a\u6a21\u7cca\u5339\u914d\uff0c\u4f46\u5173\u952e\u5b57\u5217\u8868\u4e3a\u7a7a");
            }
        } else if (this.type == SelectType.RANGE && this.ranges.isEmpty()) {
            throw new ParameterException("\u5f53\u524d\u6a21\u5f0f\u4e3a\u8303\u56f4\u5339\u914d\uff0c\u4f46\u8303\u56f4\u5339\u914d\u5217\u8868\u4e3a\u7a7a");
        }
    }

    public boolean isAvailable(Object value) {
        if (this.type == SelectType.FIXED && !this.selectedValues.isEmpty()) {
            return this.matchFixValue(value);
        }
        if (this.type == SelectType.FUZZY) {
            return this.matchTxtValue(value);
        }
        if (this.type == SelectType.RANGE) {
            return this.matchRangeValue(value);
        }
        return true;
    }

    public boolean isEmptyCondition() {
        if (this.type == SelectType.FIXED) {
            return this.selectedValues.isEmpty();
        }
        if (this.type == SelectType.FUZZY) {
            return this.matchTxt == null || this.matchTxt.trim().length() == 0;
        }
        if (this.type == SelectType.RANGE) {
            return this.ranges.isEmpty();
        }
        return true;
    }

    public Object getFilterValueInMemory(ParameterEngineEnv env, String parameterName) throws ParameterException {
        return this.getFilterValueInMemory(env, parameterName, false);
    }

    public Object getFilterValueInMemory(ParameterEngineEnv env, String parameterName, boolean canUseDefaultValue) throws ParameterException {
        ParameterModel parameterModel = env.getParameterModel(parameterName);
        if (this.isEmptyCondition()) {
            SmartSelector ss = (SmartSelector)env.getValue(parameterName);
            if (canUseDefaultValue) {
                if (ss.getDefaultValue() != null) {
                    return ss.getDefaultValue();
                }
            } else {
                if (parameterModel.getDataSourceModel() == null) {
                    return this.getFilterValueNoneDataSourceModel(env, parameterModel);
                }
                DataSourceMetaInfo dataSourceMetaInfo = env.getDataSourceMetaInfo(parameterName);
                if (dataSourceMetaInfo == null) {
                    return null;
                }
                return DataSourceUtils.getMemoryDataSet(dataSourceMetaInfo);
            }
        }
        MemoryDataSet<ParameterColumnInfo> allValues = null;
        if (parameterModel.getDataSourceModel() == null) {
            return this.getFilterValueNoneDataSourceModel(env, parameterModel);
        }
        IDataSourceDataProvider dataProvider = env.getDataSourceDataProvider(parameterName);
        try {
            allValues = dataProvider instanceof IDataSourceDataProviderEx ? ((IDataSourceDataProviderEx)dataProvider).filterAllChoiceValues(parameterModel, env) : dataProvider.filterChoiceValues(parameterModel, env);
        }
        catch (DataSourceException e1) {
            throw new ParameterException(e1.getMessage(), e1);
        }
        if (allValues == null) {
            return null;
        }
        DataSourceMetaInfo dataSourceMetaInfo = env.getDataSourceMetaInfo(parameterName);
        DataSourceAttrBean bean = dataSourceMetaInfo.getAttrBeans().get(0);
        int col = allValues.getMetadata().indexOf(bean.getKeyColName());
        if (col < 0) {
            col = allValues.getMetadata().indexOf(bean.getNameColName());
        }
        if (col == -1) {
            throw new ParameterException("\u6570\u636e\u96c6\u4e2d\u672a\u627e\u5230\u952e\u5217\u3001\u540d\u79f0\u5217\u5b57\u6bb5");
        }
        MemoryDataSet<ParameterColumnInfo> newDs = new MemoryDataSet<ParameterColumnInfo>(ParameterColumnInfo.class, allValues.getMetadata());
        for (DataRow row : allValues) {
            Object currV = row.getValue(col);
            if (!this.isAvailable(currV)) continue;
            try {
                newDs.add(row.getBuffer());
            }
            catch (DataSetException dataSetException) {}
        }
        try {
            newDs = DataSourceUtils.sortByAppointValues(this, newDs, dataSourceMetaInfo);
        }
        catch (DataSourceException e) {
            throw new ParameterException(e);
        }
        return newDs;
    }

    private Object getFilterValueNoneDataSourceModel(ParameterEngineEnv env, ParameterModel parameterModel) throws ParameterException {
        SmartSelector ss = (SmartSelector)env.getValue(parameterModel.getName());
        if (parameterModel.getMinValue() == null && parameterModel.getMaxValue() == null) {
            return ss;
        }
        switch (parameterModel.getDataType()) {
            case INTEGER: 
            case DOUBLE: 
            case DATETIME: {
                String paramMaxValue;
                String paramMinValue;
                try {
                    ss = (SmartSelector)ss.clone();
                }
                catch (CloneNotSupportedException e) {
                    throw new ParameterException(e);
                }
                String string = parameterModel.getMinValue() == null ? null : (paramMinValue = StringUtils.isNotEmpty((String)parameterModel.getMinValue().toString()) ? parameterModel.getMinValue().toString() : null);
                String string2 = parameterModel.getMaxValue() == null ? null : (paramMaxValue = StringUtils.isNotEmpty((String)parameterModel.getMaxValue().toString()) ? parameterModel.getMaxValue().toString() : null);
                if (ss.getRanges().isEmpty()) {
                    ss.type = SelectType.RANGE;
                    RangeItem range = new RangeItem();
                    range.rtype = RangeType.INCLUDERANGE;
                    range.minValue = paramMinValue;
                    range.maxValue = paramMaxValue;
                    ss.getRanges().add(range);
                    return ss;
                }
                ArrayList<RangeItem> addRanges = new ArrayList<RangeItem>();
                for (RangeItem range : ss.getRanges()) {
                    if (range.rtype == RangeType.INCLUDERANGE) {
                        if (paramMaxValue != null && DataType.compareObject((Object)range.minValue, (Object)paramMaxValue) > 0) {
                            range.minValue = "1";
                            range.maxValue = "0";
                            continue;
                        }
                        if (paramMinValue != null && DataType.compareObject((Object)range.maxValue, (Object)paramMinValue) < 0) {
                            range.minValue = "1";
                            range.maxValue = "0";
                            continue;
                        }
                        if (DataType.compareObject((Object)range.minValue, (Object)paramMinValue) < 0) {
                            range.minValue = paramMinValue;
                        }
                        if (paramMaxValue == null || DataType.compareObject((Object)range.maxValue, (Object)paramMaxValue) <= 0) continue;
                        range.maxValue = paramMaxValue;
                        continue;
                    }
                    if (range.rtype != RangeType.EXCLUDERANGE) continue;
                    if (range.minValue == null || range.maxValue == null) {
                        throw new ParameterException("\u6392\u9664\u8303\u56f4\u6700\u5927\u503c\u6700\u5c0f\u503c\u4e0d\u80fd\u4e3a\u7a7a");
                    }
                    if (DataType.compareObject((Object)range.minValue, (Object)paramMaxValue) > 0) {
                        range.rtype = RangeType.INCLUDERANGE;
                        range.minValue = paramMinValue;
                        range.maxValue = paramMaxValue;
                        continue;
                    }
                    if (DataType.compareObject((Object)range.minValue, (Object)paramMaxValue) <= 0 && DataType.compareObject((Object)range.maxValue, (Object)paramMaxValue) >= 0) {
                        range.rtype = RangeType.INCLUDERANGE;
                        range.maxValue = range.minValue;
                        range.minValue = paramMinValue;
                        continue;
                    }
                    if (paramMaxValue == null && DataType.compareObject((Object)range.maxValue, (Object)paramMaxValue) >= 0) continue;
                    if (DataType.compareObject((Object)range.maxValue, (Object)paramMinValue) < 0) {
                        range.rtype = RangeType.INCLUDERANGE;
                        range.minValue = paramMinValue;
                        range.maxValue = paramMaxValue;
                        continue;
                    }
                    if (DataType.compareObject((Object)range.maxValue, (Object)paramMinValue) >= 0 && DataType.compareObject((Object)range.minValue, (Object)paramMinValue) <= 0) {
                        range.rtype = RangeType.INCLUDERANGE;
                        range.minValue = range.maxValue;
                        range.maxValue = paramMaxValue;
                        continue;
                    }
                    range.rtype = RangeType.INCLUDERANGE;
                    range.minValue = range.maxValue;
                    range.maxValue = paramMaxValue;
                    RangeItem addRange = new RangeItem();
                    addRange.rtype = RangeType.INCLUDERANGE;
                    addRange.maxValue = range.minValue;
                    addRange.minValue = paramMinValue;
                    addRanges.add(addRange);
                }
                ss.getRanges().addAll(addRanges);
                break;
            }
        }
        return ss;
    }

    private boolean matchFixValue(Object currV) {
        for (SelectedValue sv : this.selectedValues) {
            boolean isEq;
            boolean bl = isEq = sv.value != null && sv.value.equals(currV);
            if (isEq) {
                return true;
            }
            isEq = sv.title != null && sv.title.equals(currV);
            if (!isEq) continue;
            return true;
        }
        return false;
    }

    private boolean matchTxtValue(Object currV) {
        if (this.matchTxt == null) {
            return false;
        }
        String[] keyList = this.matchTxt.split("[,|;|\u3001|\u3002|\uff0c|\uff1b|\n]");
        String dest = currV.toString();
        for (String key : keyList) {
            if (!(this.isExact() ? dest.equals(key) : dest.contains(key))) continue;
            return true;
        }
        return false;
    }

    private boolean matchRangeValue(Object currV) {
        if (currV == null) {
            return false;
        }
        boolean match = false;
        boolean exclude = true;
        boolean hasInclude = false;
        boolean hasExclude = false;
        currV = currV.toString();
        for (RangeItem range : this.ranges) {
            boolean isTrue;
            if (!exclude) break;
            if (range.rtype == RangeType.STARTWITH) {
                hasInclude = true;
                match |= ((String)currV).startsWith(range.value.toString());
                continue;
            }
            if (range.rtype == RangeType.INCLUDERANGE) {
                hasInclude = true;
                boolean bl = isTrue = DataType.compareObject((Object)range.minValue, (Object)currV) <= 0;
                if (isTrue) {
                    isTrue = DataType.compareObject((Object)range.maxValue, (Object)currV) >= 0;
                }
                match |= isTrue;
                continue;
            }
            if (range.rtype == RangeType.EXCLUDERANGE) {
                hasExclude = true;
                boolean bl = isTrue = DataType.compareObject((Object)range.minValue, (Object)currV) > 0;
                if (!isTrue) {
                    isTrue = DataType.compareObject((Object)range.maxValue, (Object)currV) < 0;
                }
                exclude &= isTrue;
                continue;
            }
            if (range.rtype != RangeType.EXCLUDEVALUE) continue;
            hasExclude = true;
            isTrue = DataType.compareObject((Object)currV, (Object)range.value) != 0;
            exclude &= isTrue;
        }
        if (hasInclude && hasExclude) {
            return match && exclude;
        }
        if (hasInclude && !hasExclude) {
            return match;
        }
        if (!hasInclude && hasExclude) {
            return exclude;
        }
        return true;
    }

    public String toString() {
        return super.toString();
    }

    public static enum SelectType {
        FIXED(1, "\u56fa\u5b9a\u53d6\u503c"),
        FUZZY(2, "\u6587\u672c\u5339\u914d"),
        RANGE(3, "\u9009\u62e9\u8303\u56f4");

        private int value;
        private String name;

        private SelectType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public static final SelectType typeOf(int type) {
            if (type == 1) {
                return FIXED;
            }
            if (type == 2) {
                return FUZZY;
            }
            if (type == 3) {
                return RANGE;
            }
            return null;
        }
    }

    public static enum RangeType {
        STARTWITH(1, "\u5f00\u5934\u662f"),
        INCLUDERANGE(2, "\u5305\u542b\u8303\u56f4"),
        EXCLUDERANGE(3, "\u6392\u9664\u8303\u56f4"),
        EXCLUDEVALUE(4, "\u6392\u9664\u503c");

        private int value;
        private String name;

        private RangeType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public static final RangeType typeOf(int type) {
            if (type == 1) {
                return STARTWITH;
            }
            if (type == 2) {
                return INCLUDERANGE;
            }
            if (type == 3) {
                return EXCLUDERANGE;
            }
            if (type == 4) {
                return EXCLUDEVALUE;
            }
            return null;
        }
    }

    public static final class SelectedValue {
        public Object value;
        public String title;
        public String path;

        public SelectedValue() {
        }

        public SelectedValue(Object value) {
            this.value = value;
        }

        public SelectedValue(Object value, String title) {
            this.value = value;
            this.title = title;
        }

        public void fromJson(JSONObject json) throws JSONException {
            this.value = json.opt("value");
            this.title = json.optString("title");
            this.path = json.optString("path");
        }

        public JSONObject toJson() throws JSONException {
            JSONObject jo = new JSONObject();
            if (this.value != null) {
                jo.put("value", this.value);
            }
            if (this.title != null) {
                jo.put("title", (Object)this.title);
            }
            if (this.path != null) {
                jo.put("path", (Object)this.path);
            }
            return jo;
        }
    }

    public static final class RangeItem {
        public RangeType rtype;
        public Object value;
        public Object minValue;
        public Object maxValue;
        private static final String RANGE_TYPE = "rtype";
        private static final String RANGE_VALUE = "value";
        private static final String RANGE_MIN = "minValue";
        private static final String RANGE_MAX = "maxValue";

        public void fromJson(JSONObject json) throws JSONException {
            this.rtype = RangeType.typeOf(json.optInt(RANGE_TYPE, 0));
            this.value = json.opt(RANGE_VALUE);
            this.minValue = json.opt(RANGE_MIN);
            this.maxValue = json.opt(RANGE_MAX);
        }

        public JSONObject toJson() throws JSONException {
            JSONObject jo = new JSONObject();
            jo.put(RANGE_TYPE, this.rtype.value);
            if (this.value != null) {
                jo.put(RANGE_VALUE, this.value);
            }
            if (this.minValue != null) {
                jo.put(RANGE_MIN, this.minValue);
            }
            if (this.maxValue != null) {
                jo.put(RANGE_MAX, this.maxValue);
            }
            return jo;
        }
    }
}

