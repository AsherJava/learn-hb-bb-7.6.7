/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider
 *  com.jiuqi.nr.fielddatacrud.spi.ParamProvider
 */
package com.jiuqi.nr.migration.transferdata.dbservice;

import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;
import com.jiuqi.nr.migration.transferdata.bean.FetchSaveDataParam;
import com.jiuqi.nr.migration.transferdata.dbservice.TransferParamProviderImpl;

public class TransferParamDataProviderImpl
implements IParamDataProvider {
    private FetchSaveDataParam vo;

    public TransferParamDataProviderImpl(FetchSaveDataParam vo) {
        this.vo = vo;
    }

    public ParamProvider getParamProvider() {
        return new TransferParamProviderImpl(this.vo);
    }
}

