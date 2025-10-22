/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.datacrud.util;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.nr.datacrud.DataValueBalanceActuatorFactory;
import com.jiuqi.nr.datacrud.api.IEntityRowSearcher;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.format.strategy.MeasureNumberTypeStrategy;
import com.jiuqi.nr.datacrud.impl.format.strategy.SysNumberTypeStrategy;
import com.jiuqi.nr.datacrud.impl.measure.MeasureService;
import com.jiuqi.nr.datacrud.impl.parse.strategy.DecimalParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.FormatEnumParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.IntParseStrategy;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TypeStrategyUtil {
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IExecutorContextFactory executorContextFactory;
    @Autowired
    private MeasureService measureService;
    @Autowired
    private IEntityTableFactory entityTableFactory;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityRowSearcher entityRowSearcher;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataValueBalanceActuatorFactory dataValueBalanceActuatorFactory;
    public static final String NUMBER_ZERO_SHOW = "NUMBER_ZERO_SHOW";

    public SysNumberTypeStrategy initSysNumberTypeStrategy() {
        SysNumberTypeStrategy sysNumberTypeStrategy = new SysNumberTypeStrategy();
        sysNumberTypeStrategy.setTaskOptionController(this.taskOptionController);
        sysNumberTypeStrategy.setRegionRelationFactory(this.regionRelationFactory);
        sysNumberTypeStrategy.setDataAccessProvider(this.dataAccessProvider);
        sysNumberTypeStrategy.setExecutorContextFactory(this.executorContextFactory);
        sysNumberTypeStrategy.setMeasureService(this.measureService);
        sysNumberTypeStrategy.setDataValueBalanceActuatorFactory(this.dataValueBalanceActuatorFactory);
        sysNumberTypeStrategy.setRunTimeViewController(this.runTimeViewController);
        return sysNumberTypeStrategy;
    }

    public MeasureNumberTypeStrategy initMeasureNumberTypeStrategy() {
        MeasureNumberTypeStrategy sysNumberTypeStrategy = new MeasureNumberTypeStrategy();
        sysNumberTypeStrategy.setRegionRelationFactory(this.regionRelationFactory);
        sysNumberTypeStrategy.setDataAccessProvider(this.dataAccessProvider);
        sysNumberTypeStrategy.setExecutorContextFactory(this.executorContextFactory);
        sysNumberTypeStrategy.setMeasureService(this.measureService);
        sysNumberTypeStrategy.setDataValueBalanceActuatorFactory(this.dataValueBalanceActuatorFactory);
        sysNumberTypeStrategy.setRunTimeViewController(this.runTimeViewController);
        return sysNumberTypeStrategy;
    }

    public DecimalParseStrategy initFloatTypeParseStrategy(String regionKey) {
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionKey);
        return this.initFloatTypeParseStrategy(regionRelation);
    }

    public DecimalParseStrategy initIntTypeParseStrategy(String regionKey) {
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionKey);
        return this.initIntTypeParseStrategy(regionRelation);
    }

    public DecimalParseStrategy initFloatTypeParseStrategy(RegionRelation regionRelation) {
        String taskKey = regionRelation.getTaskDefine().getKey();
        String numberZeroShow = this.taskOptionController.getValue(taskKey, NUMBER_ZERO_SHOW);
        if (numberZeroShow == null) {
            numberZeroShow = "";
        }
        return new DecimalParseStrategy().setNumberZeroShow(numberZeroShow);
    }

    public IntParseStrategy initIntTypeParseStrategy(RegionRelation regionRelation) {
        String taskKey = regionRelation.getTaskDefine().getKey();
        String numberZeroShow = this.taskOptionController.getValue(taskKey, NUMBER_ZERO_SHOW);
        if (numberZeroShow == null) {
            numberZeroShow = "";
        }
        IntParseStrategy intParseStrategy = new IntParseStrategy();
        intParseStrategy.setNumberZeroShow(numberZeroShow);
        return intParseStrategy;
    }

    public FormatEnumParseStrategy initFormatEnumParseStrategy(String regionKey) {
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionKey);
        return this.initFormatEnumParseStrategy(regionRelation);
    }

    public FormatEnumParseStrategy initFormatEnumParseStrategy(RegionRelation regionRelation) {
        return new FormatEnumParseStrategy(regionRelation, this.entityTableFactory, this.entityMetaService, this.entityRowSearcher);
    }
}

