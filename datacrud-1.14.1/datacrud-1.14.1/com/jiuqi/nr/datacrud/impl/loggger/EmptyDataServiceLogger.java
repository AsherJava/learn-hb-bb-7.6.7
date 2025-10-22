/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 */
package com.jiuqi.nr.datacrud.impl.loggger;

import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.loggger.DataServiceLogger;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;

public class EmptyDataServiceLogger
extends DataServiceLogger {
    public EmptyDataServiceLogger() {
        super(null, null, null);
    }

    public EmptyDataServiceLogger(DataServiceLogHelper dataServiceLogHelper, RegionRelation relation, LogDimensionCollection logDimensionCollection) {
        super(dataServiceLogHelper, relation, logDimensionCollection);
    }

    @Override
    public void info(String title, String message) {
    }

    @Override
    public void error(String title, String message) {
    }

    @Override
    public void beginSaveData(String message) {
    }

    @Override
    public void saveSuccess(String message) {
    }

    @Override
    public void dataCheckFail(String message) {
    }

    @Override
    public void saveFail(String message) {
    }

    @Override
    public void beginClearData(String message) {
    }

    @Override
    public void clearSuccess(String message) {
    }

    @Override
    public void clearFail(String message) {
    }
}

