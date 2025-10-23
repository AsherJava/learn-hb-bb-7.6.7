/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataFilter
 *  com.jiuqi.va.domain.basedata.BaseDataOption$FilterType
 */
package com.jiuqi.nr.expression.filter;

import com.jiuqi.nr.expression.filter.NrExpressionFilter;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataFilter;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import java.util.List;

public class BaseDataExpressionFilter
extends NrExpressionFilter<BaseDataDO, BaseDataDTO>
implements BaseDataFilter {
    public static boolean isApplied(BaseDataDTO data) {
        return data.containsKey((Object)"NR_EXPRESSION_FILTER");
    }

    public BaseDataOption.FilterType getFilterType() {
        return BaseDataOption.FilterType.FORMULA;
    }

    public boolean isEnable(BaseDataDTO param) {
        return BaseDataExpressionFilter.isApplied(param);
    }

    public void filterList(BaseDataDTO param, List<BaseDataDO> dataList) {
        if (!BaseDataExpressionFilter.isApplied(param)) {
            return;
        }
        super.filterData(param, dataList);
    }

    @Override
    String getCategoryName(BaseDataDTO baseDataDO) {
        return baseDataDO.getTableName();
    }

    @Override
    String getKey(BaseDataDO data) {
        return data.getObjectcode();
    }

    @Override
    String getTypeName() {
        return "\u57fa\u7840\u6570\u636e";
    }
}

