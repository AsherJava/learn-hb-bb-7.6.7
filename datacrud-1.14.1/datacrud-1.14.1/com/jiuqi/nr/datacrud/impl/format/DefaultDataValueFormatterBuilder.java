/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.datacrud.impl.format;

import com.jiuqi.nr.datacrud.DataValueFormatterBuilder;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.format.DataValueFormatterImpl;
import com.jiuqi.nr.datacrud.impl.format.strategy.BooleanTypeStrategy;
import com.jiuqi.nr.datacrud.impl.format.strategy.DateTimeTypeStrategy;
import com.jiuqi.nr.datacrud.impl.format.strategy.DateTypeStrategy;
import com.jiuqi.nr.datacrud.impl.format.strategy.EnumTypeStrategy;
import com.jiuqi.nr.datacrud.impl.format.strategy.SysNumberTypeStrategy;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.spi.TypeFormatStrategy;
import com.jiuqi.nr.datacrud.util.TypeStrategyUtil;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.EnumMap;
import org.springframework.util.Assert;

public class DefaultDataValueFormatterBuilder
implements DataValueFormatterBuilder {
    private final EnumMap<DataFieldType, TypeFormatStrategy> typeFormatStrategyEnumMap = new EnumMap(DataFieldType.class);
    private TypeFormatStrategy defaultTypeStrategy;
    private final RegionRelationFactory regionRelationFactory;
    private final IEntityMetaService entityMetaService;
    private final IEntityTableFactory entityTableFactory;
    private final TypeStrategyUtil typeStrategyUtil;

    public DefaultDataValueFormatterBuilder(RegionRelationFactory regionRelationFactory, IEntityTableFactory entityTableFactory, IEntityMetaService entityMetaService, TypeStrategyUtil typeStrategyUtil) {
        this.regionRelationFactory = regionRelationFactory;
        this.entityMetaService = entityMetaService;
        this.entityTableFactory = entityTableFactory;
        this.typeStrategyUtil = typeStrategyUtil;
        this.installFormatStrategy();
    }

    @Override
    public DataValueFormatter build() {
        return new DataValueFormatterImpl(this.typeFormatStrategyEnumMap, this.defaultTypeStrategy, this.regionRelationFactory);
    }

    @Override
    public DefaultDataValueFormatterBuilder registerFormatStrategy(int type, TypeFormatStrategy formatStrategy) {
        DataFieldType dataFieldType = DataFieldType.valueOf((int)type);
        Assert.notNull((Object)dataFieldType, "\u6ce8\u518c\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)formatStrategy, "\u7c7b\u578b\u683c\u5f0f\u5316\u5668\u4e0d\u80fd\u4e3a\u7a7a");
        this.typeFormatStrategyEnumMap.put(dataFieldType, formatStrategy);
        return this;
    }

    @Override
    public DefaultDataValueFormatterBuilder installFormatStrategy() {
        SysNumberTypeStrategy sysNumberTypeStrategy = this.typeStrategyUtil.initSysNumberTypeStrategy();
        this.typeFormatStrategyEnumMap.put(DataFieldType.BIGDECIMAL, sysNumberTypeStrategy);
        this.typeFormatStrategyEnumMap.put(DataFieldType.INTEGER, sysNumberTypeStrategy);
        this.typeFormatStrategyEnumMap.put(DataFieldType.DATE, new DateTypeStrategy());
        this.typeFormatStrategyEnumMap.put(DataFieldType.DATE_TIME, new DateTimeTypeStrategy());
        this.typeFormatStrategyEnumMap.put(DataFieldType.STRING, new EnumTypeStrategy(this.entityMetaService, this.entityTableFactory, this.regionRelationFactory));
        this.typeFormatStrategyEnumMap.put(DataFieldType.BOOLEAN, new BooleanTypeStrategy());
        return this;
    }

    @Override
    public DefaultDataValueFormatterBuilder unInstallFormatStrategy() {
        this.typeFormatStrategyEnumMap.clear();
        return this;
    }

    @Override
    public void setDefaultTypeStrategy(TypeFormatStrategy defaultTypeStrategy) {
        this.defaultTypeStrategy = defaultTypeStrategy;
    }
}

