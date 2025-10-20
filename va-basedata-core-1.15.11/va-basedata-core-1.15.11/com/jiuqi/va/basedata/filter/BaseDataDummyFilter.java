/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataFilter
 *  com.jiuqi.va.domain.basedata.BaseDataOption$FilterType
 */
package com.jiuqi.va.basedata.filter;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataFilter;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import java.math.BigDecimal;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class BaseDataDummyFilter
implements BaseDataFilter {
    public static final String FILTER_FLAG = "dummyBaseDataFilter";
    public static final String FILTER_COLUMN = "dummyColumnCode";
    public static final String FILTER_CONDITION = "dummyFilterCondition";
    public static final String FILTER_VALUE = "dummyFilterValue";

    public Boolean checkData(BaseDataDTO param, BaseDataDO e) {
        String columnCode = (String)param.get((Object)FILTER_COLUMN);
        String filterCondition = (String)param.get((Object)FILTER_CONDITION);
        String filterValue = (String)param.get((Object)FILTER_VALUE);
        Object val = e.get((Object)columnCode);
        boolean isEmpty = this.isEmpty(val);
        if (isEmpty && "NOTEQUAL".equals(filterCondition)) {
            return true;
        }
        if (isEmpty && "EQUALS".equals(filterCondition)) {
            return "null".equalsIgnoreCase(filterValue);
        }
        if (isEmpty && "FALSE".equals(filterCondition)) {
            return true;
        }
        if (isEmpty) {
            return false;
        }
        switch (filterCondition) {
            case "GREATERTHEN": 
            case "LESSTHAN": 
            case "EQUAL": 
            case "NOTEQUAL": {
                if (val instanceof Integer) {
                    val = new BigDecimal((Integer)val);
                    break;
                }
                if (val instanceof BigDecimal) break;
                val = new BigDecimal(val.toString());
                break;
            }
            default: {
                val = val.toString();
            }
        }
        Boolean flag = null;
        switch (filterCondition) {
            case "CONTAIN": {
                flag = ((String)val).contains(filterValue);
                break;
            }
            case "EQUALS": {
                flag = ((String)val).equals(filterValue);
                break;
            }
            case "PREFIX": {
                flag = ((String)val).startsWith(filterValue);
                break;
            }
            case "GREATERTHEN": {
                flag = ((BigDecimal)val).compareTo(new BigDecimal(filterValue)) > 0;
                break;
            }
            case "LESSTHAN": {
                flag = ((BigDecimal)val).compareTo(new BigDecimal(filterValue)) < 0;
                break;
            }
            case "EQUAL": {
                flag = ((BigDecimal)val).compareTo(new BigDecimal(filterValue)) == 0;
                break;
            }
            case "NOTEQUAL": {
                flag = ((BigDecimal)val).compareTo(new BigDecimal(filterValue)) != 0;
                break;
            }
            case "TRUE": {
                flag = "1".equals(val);
                break;
            }
            case "FALSE": {
                flag = "0".equals(val);
            }
        }
        return flag;
    }

    public BaseDataOption.FilterType getFilterType() {
        return BaseDataOption.FilterType.NORMAL;
    }

    public boolean isEnable(BaseDataDTO param) {
        return param.containsKey((Object)FILTER_FLAG);
    }

    private boolean isEmpty(@Nullable Object str) {
        return str == null || "".equals(str);
    }
}

