/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.filter;

import com.jiuqi.np.office.excel2.filter.FilterOperator;
import java.io.Serializable;

public class FilterColCondition
implements Serializable {
    private static final long serialVersionUID = 1L;
    private FilterOperator filterOperator;
    private String value;
    private Boolean isAnd;

    public FilterOperator getFilterOperator() {
        return this.filterOperator;
    }

    public void setFilterOperator(FilterOperator filterOperator) {
        this.filterOperator = filterOperator;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean isAnd() {
        return this.isAnd;
    }

    public void setAnd(Boolean and) {
        this.isAnd = and;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.filterOperator == null ? 0 : this.filterOperator.hashCode());
        result = 31 * result + (this.isAnd != false ? 1231 : 1237);
        result = 31 * result + (this.value == null ? 0 : this.value.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        FilterColCondition other = (FilterColCondition)obj;
        if (this.filterOperator != other.filterOperator) {
            return false;
        }
        if (this.isAnd != other.isAnd) {
            return false;
        }
        return !(this.value == null ? other.value != null : !this.value.equals(other.value));
    }
}

