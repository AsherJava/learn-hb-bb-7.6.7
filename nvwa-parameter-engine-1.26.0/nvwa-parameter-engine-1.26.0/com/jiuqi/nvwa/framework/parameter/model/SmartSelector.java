/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SmartSelector
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 1L;
    public static final String SELECT_MODE_FIXED = "fixed";
    public static final String SELECT_MODE_FUZZY = "fuzzy";
    public static final String SELECT_MODE_RANGE = "range";
    private String selectMode = "fixed";
    private List<Object> selectedValues = new Vector<Object>();
    private boolean exact = false;
    private String matchTxt = "";
    private List<RangeItem> ranges = new Vector<RangeItem>();
    private static final String SELECT_TYPE = "type";
    private static final String SELECTED_VALUES = "selectedValues";
    private static final String IS_EXACT = "exact";
    private static final String MATCH_TXT = "matchTxt";
    private static final String RANGE_ITEMS = "ranges";

    public void setSelectMode(String selectMode) {
        this.selectMode = selectMode;
    }

    public String getSelectMode() {
        return this.selectMode;
    }

    public boolean isFixedValueMode() {
        return SELECT_MODE_FIXED.equals(this.selectMode);
    }

    public List<Object> getSelectedValues() {
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

    public void fromJson(JSONObject json) throws JSONException {
        int i;
        JSONArray ary;
        this.selectMode = json.optString(SELECT_TYPE);
        Pattern pattern = Pattern.compile("[1-3]");
        if (pattern.matcher(this.selectMode).matches()) {
            if (this.selectMode.equals("1")) {
                this.selectMode = SELECT_MODE_FIXED;
            } else if (this.selectMode.equals("2")) {
                this.selectMode = SELECT_MODE_FUZZY;
            } else if (this.selectMode.equals("3")) {
                this.selectMode = SELECT_MODE_RANGE;
            }
            if (!json.isNull(SELECTED_VALUES)) {
                ary = json.getJSONArray(SELECTED_VALUES);
                for (i = 0; i < ary.length(); ++i) {
                    JSONObject selectValue = ary.getJSONObject(i);
                    this.selectedValues.add(selectValue.opt("value"));
                }
            }
        } else if (!json.isNull(SELECTED_VALUES)) {
            ary = json.getJSONArray(SELECTED_VALUES);
            for (i = 0; i < ary.length(); ++i) {
                this.selectedValues.add(ary.get(i));
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

    public void toJson(JSONObject json) throws JSONException {
        json.put(SELECT_TYPE, (Object)this.selectMode);
        JSONArray ary = new JSONArray();
        for (Object s : this.selectedValues) {
            ary.put(s);
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
    }

    public boolean match(Object value) {
        if (this.selectMode.equals(SELECT_MODE_FIXED) && !this.selectedValues.isEmpty()) {
            return this.matchFixValue(value);
        }
        if (this.selectMode.equals(SELECT_MODE_FUZZY)) {
            return this.matchTxtValue(value);
        }
        if (this.selectMode.equals(SELECT_MODE_RANGE)) {
            return this.matchRangeValue(value);
        }
        return true;
    }

    private boolean matchFixValue(Object currV) {
        for (Object sv : this.selectedValues) {
            boolean isEq = sv != null && sv.equals(currV);
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

    public String toBQL(String fieldName, int dataType) {
        if (this.selectMode.equals(SELECT_MODE_FIXED)) {
            return this.toSqlByFixType(fieldName, dataType, true);
        }
        if (this.selectMode.equals(SELECT_MODE_FUZZY)) {
            return this.toSqlByFuzzyType(fieldName, dataType, true);
        }
        if (this.selectMode.equals(SELECT_MODE_RANGE)) {
            return this.toSqlByRangeType(fieldName, dataType);
        }
        return "";
    }

    public String toSQL(String fieldName, int dataType) {
        if (this.selectMode.equals(SELECT_MODE_FIXED)) {
            return this.toSqlByFixType(fieldName, dataType, false);
        }
        if (this.selectMode.equals(SELECT_MODE_FUZZY)) {
            return this.toSqlByFuzzyType(fieldName, dataType, false);
        }
        if (this.selectMode.equals(SELECT_MODE_RANGE)) {
            return this.toSqlByRangeType(fieldName, dataType);
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
        for (Object sv : this.selectedValues) {
            if (n > 0) {
                builder.append(", ");
            }
            ++n;
            this.appendQuote(builder, dataType, sv);
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

    public static final class RangeItem
    implements Serializable {
        private static final long serialVersionUID = 1L;
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

