/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datacrud.DataValueFormatterBuilder;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.format.DefaultDataValueFormatterBuilder;
import com.jiuqi.nr.datacrud.impl.format.strategy.DefaultTypeStrategy;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.util.TypeStrategyUtil;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataValueFormatterBuilderFactory {
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityTableFactory entityTableFactory;
    @Autowired
    private TypeStrategyUtil typeStrategyUtil;

    public DataValueFormatterBuilder createFormatterBuilder() {
        DefaultDataValueFormatterBuilder builder = new DefaultDataValueFormatterBuilder(this.regionRelationFactory, this.entityTableFactory, this.entityMetaService, this.typeStrategyUtil);
        builder.setDefaultTypeStrategy(new DefaultTypeStrategy());
        return builder;
    }
}

