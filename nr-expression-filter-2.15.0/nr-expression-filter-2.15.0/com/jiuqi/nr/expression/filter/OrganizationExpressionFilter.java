/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataFilter
 *  com.jiuqi.va.domain.org.OrgDataOption$FilterType
 */
package com.jiuqi.nr.expression.filter;

import com.jiuqi.nr.expression.filter.NrExpressionFilter;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataFilter;
import com.jiuqi.va.domain.org.OrgDataOption;
import java.util.List;

public class OrganizationExpressionFilter
extends NrExpressionFilter<OrgDO, OrgDTO>
implements OrgDataFilter {
    public static boolean isApplied(OrgDTO data) {
        return data.containsKey((Object)"NR_EXPRESSION_FILTER");
    }

    public OrgDataOption.FilterType getFilterType() {
        return OrgDataOption.FilterType.FORMULA;
    }

    public boolean isEnable(OrgDTO param) {
        return OrganizationExpressionFilter.isApplied(param);
    }

    public void filterList(OrgDTO param, List<OrgDO> dataList) {
        if (!OrganizationExpressionFilter.isApplied(param)) {
            return;
        }
        super.filterData(param, dataList);
    }

    @Override
    String getCategoryName(OrgDTO orgDTO) {
        return orgDTO.getCategoryname();
    }

    @Override
    String getKey(OrgDO data) {
        return data.getCode();
    }

    @Override
    String getTypeName() {
        return "\u7ec4\u7ec7\u673a\u6784";
    }
}

