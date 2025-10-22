/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.nr.datacrud.api.IDataValueBalanceActuator;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.calc.RowBalanceServiceImpl;
import com.jiuqi.nr.datacrud.impl.measure.MeasureService;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataValueBalanceActuatorFactory {
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IExecutorContextFactory executorContextFactory;
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private MeasureService measureService;

    public IDataValueBalanceActuator getDataValueBalanceActuator() {
        RowBalanceServiceImpl rowBalanceService = new RowBalanceServiceImpl();
        rowBalanceService.setDataAccessProvider(this.dataAccessProvider);
        rowBalanceService.setExecutorContextFactory(this.executorContextFactory);
        rowBalanceService.setRegionRelationFactory(this.regionRelationFactory);
        rowBalanceService.setRunTimeViewController(this.runTimeViewController);
        rowBalanceService.setMeasureService(this.measureService);
        rowBalanceService.setDataValueBalanceActuatorFactory(this);
        return rowBalanceService;
    }
}

