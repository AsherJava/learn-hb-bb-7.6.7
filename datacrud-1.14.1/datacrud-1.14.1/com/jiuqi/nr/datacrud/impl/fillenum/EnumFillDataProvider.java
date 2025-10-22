/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl.fillenum;

import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.impl.service.EntityDataService;
import com.jiuqi.nr.datacrud.spi.FillDataProvider;
import com.jiuqi.nr.datacrud.spi.IEnumFillNode;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class EnumFillDataProvider
implements FillDataProvider {
    @Autowired
    protected EntityDataService entityDataService;
    @Autowired
    protected DataEngineService dataEngineService;
    @Autowired
    protected IExecutorContextFactory executorContextFactory;

    @Override
    public List<List<String>> fillData(IQueryInfo queryInfo, RegionRelation relation) {
        List<MetaData> filledEnumLinks = relation.getFilledEnumLinks();
        if (CollectionUtils.isEmpty(filledEnumLinks)) {
            return Collections.emptyList();
        }
        List<List<String>> enumValues = this.entityDataService.fillEnum(relation, queryInfo.getDimensionCombination());
        if (CollectionUtils.isEmpty(enumValues)) {
            return Collections.emptyList();
        }
        return enumValues;
    }

    @Override
    public List<List<String>> fillData(IQueryInfo queryInfo, RegionRelation relation, List<IEnumFillNode> filterNodes) {
        List<MetaData> filledEnumLinks = relation.getFilledEnumLinks();
        if (CollectionUtils.isEmpty(filledEnumLinks)) {
            return Collections.emptyList();
        }
        List<List<String>> enumValues = this.entityDataService.fillEnum(relation, queryInfo.getDimensionCombination(), filterNodes);
        if (CollectionUtils.isEmpty(enumValues)) {
            return Collections.emptyList();
        }
        return enumValues;
    }
}

