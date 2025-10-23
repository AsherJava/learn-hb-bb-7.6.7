/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.filter;

import com.jiuqi.nr.data.access.filter.DimensionFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DimensionFilterProvider
implements InitializingBean {
    @Autowired(required=false)
    private List<DimensionFilter> dimensionFilters;
    private final Map<String, DimensionFilter> filterMap = new HashMap<String, DimensionFilter>();

    public DimensionFilter getFilterByName(String name) {
        if (CollectionUtils.isEmpty(this.dimensionFilters)) {
            return null;
        }
        DimensionFilter filter = this.filterMap.get(name);
        if (Objects.nonNull(filter)) {
            return filter;
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(this.dimensionFilters)) {
            Map map = this.dimensionFilters.stream().collect(Collectors.toMap(DimensionFilter::getAccessName, Function.identity(), (o, n) -> n));
            this.filterMap.putAll(map);
        }
    }
}

