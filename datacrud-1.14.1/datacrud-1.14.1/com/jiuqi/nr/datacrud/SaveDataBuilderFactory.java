/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datacrud.SaveDataBuilder;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datacrud.i18n.CrudMessageSource;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.crud.inner.CheckSaveDataBuilder;
import com.jiuqi.nr.datacrud.impl.crud.inner.DefaultSaveDataBuilder;
import com.jiuqi.nr.datacrud.impl.loggger.DataServiceLogWrapper;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.util.TypeStrategyUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveDataBuilderFactory {
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    private DataEngineService dataEngineService;
    @Autowired
    private TypeStrategyUtil typeStrategyUtil;
    @Autowired
    private DataServiceLogWrapper dataServiceLogWrapper;
    @Autowired
    private IEntityTableFactory entityTableFactory;
    @Autowired
    private CrudMessageSource messageSource;
    @Autowired
    private IDataQueryService dataQueryService;
    @Autowired
    private IEntityMetaService entityMetaService;

    public SaveDataBuilder createSaveDataBuilder(String regionKey, DimensionCombination dimension) {
        return new DefaultSaveDataBuilder(regionKey, dimension, this);
    }

    public SaveDataBuilder createCheckSaveDataBuilder(String regionKey, DimensionCombination dimension) {
        return new CheckSaveDataBuilder(regionKey, dimension, this);
    }

    public RegionRelationFactory getRegionRelationFactory() {
        return this.regionRelationFactory;
    }

    public DataEngineService getDataEngineService() {
        return this.dataEngineService;
    }

    public TypeStrategyUtil getTypeStrategyUtil() {
        return this.typeStrategyUtil;
    }

    public DataServiceLogWrapper getDataServiceLogWrapper() {
        return this.dataServiceLogWrapper;
    }

    public IEntityTableFactory getEntityTableFactory() {
        return this.entityTableFactory;
    }

    public CrudMessageSource getMessageSource() {
        return this.messageSource;
    }

    public IDataQueryService getDataQueryService() {
        return this.dataQueryService;
    }

    public IEntityMetaService getEntityMetaService() {
        return this.entityMetaService;
    }
}

