/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.datacrud.impl.loggger;

import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.facade.FormDefine;

public class DataServiceLogger {
    private DataServiceLogHelper dataServiceLogHelper;
    private RegionRelation relation;
    private LogDimensionCollection logDimensionCollection;

    private DataServiceLogger() {
    }

    public DataServiceLogger(DataServiceLogHelper dataServiceLogHelper, RegionRelation relation, LogDimensionCollection logDimensionCollection) {
        this.dataServiceLogHelper = dataServiceLogHelper;
        this.relation = relation;
        this.logDimensionCollection = logDimensionCollection;
    }

    public void info(String title, String message) {
        try {
            FormDefine formDefine = this.relation.getFormDefine();
            if (formDefine != null) {
                if (message == null) {
                    message = title;
                }
                message = formDefine.getTitle() + "\u3010" + formDefine.getFormCode() + "\u3011" + message;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.dataServiceLogHelper.info(this.relation.getTaskDefine().getKey(), this.logDimensionCollection, title, message);
    }

    public void error(String title, String message) {
        try {
            FormDefine formDefine = this.relation.getFormDefine();
            if (formDefine != null) {
                if (message == null) {
                    message = title;
                }
                message = formDefine.getTitle() + "\u3010" + formDefine.getFormCode() + "\u3011" + message;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.dataServiceLogHelper.error(this.relation.getTaskDefine().getKey(), this.logDimensionCollection, title, message);
    }

    public void beginSaveData(String message) {
    }

    public void saveSuccess(String message) {
        this.info("\u6570\u636e\u4fdd\u5b58\u6210\u529f", message);
    }

    public void dataCheckFail(String message) {
        this.error("\u6570\u636e\u6821\u9a8c\u5931\u8d25", message);
    }

    public void saveFail(String message) {
        this.error("\u6570\u636e\u4fdd\u5b58\u5931\u8d25", message);
    }

    public void beginClearData(String message) {
    }

    public void clearSuccess(String message) {
        this.info("\u6570\u636e\u5220\u9664\u6210\u529f", message);
    }

    public void clearFail(String message) {
        this.error("\u6570\u636e\u5220\u9664\u5931\u8d25", message);
    }
}

