/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.obj;

import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import com.jiuqi.nr.data.checkdes.obj.FilterType;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class CKDImpFilter {
    private final FilterType filterType;
    private final Set<?> filterValues;

    public CKDImpFilter(Set<?> filterValues, FilterType filterType) {
        this.filterValues = filterValues;
        this.filterType = filterType;
    }

    public boolean filter(CKDTransObj ckdTransObj) {
        Object v;
        if (FilterType.DIMENSION == this.filterType) {
            v = ckdTransObj.getDimensionValueSet();
        } else if (FilterType.FORMULA_SCHEME == this.filterType) {
            v = ckdTransObj.getFormulaSchemeKey();
        } else if (FilterType.FORM == this.filterType) {
            v = ckdTransObj.getFormKey();
        } else if (FilterType.FORMULA == this.filterType) {
            v = ckdTransObj.getFormulaKey();
        } else {
            return true;
        }
        return CollectionUtils.isEmpty(this.filterValues) || this.filterValues.contains(v);
    }

    public FilterType getFilterType() {
        return this.filterType;
    }
}

