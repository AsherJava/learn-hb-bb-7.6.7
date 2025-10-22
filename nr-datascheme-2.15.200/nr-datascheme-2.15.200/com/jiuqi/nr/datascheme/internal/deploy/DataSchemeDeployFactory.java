/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.internal.deploy;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.deploy.ITableModelIndexBuilder;
import java.util.EnumMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DataSchemeDeployFactory {
    private final EnumMap<DataTableType, ITableModelIndexBuilder> indexBuilderMap = new EnumMap(DataTableType.class);

    @Autowired
    private void registerIndexBuilder(List<ITableModelIndexBuilder> indexBuilders) {
        if (!CollectionUtils.isEmpty(indexBuilders)) {
            for (ITableModelIndexBuilder indexBuilder : indexBuilders) {
                for (DataTableType doForTableType : indexBuilder.getDoForTableTypes()) {
                    this.indexBuilderMap.put(doForTableType, indexBuilder);
                }
            }
        }
    }

    public ITableModelIndexBuilder getIndexBuilder(DataTableType type) {
        return this.indexBuilderMap.get(type);
    }
}

