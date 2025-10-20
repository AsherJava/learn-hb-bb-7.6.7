/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.client.intf.IBizDataModel
 */
package com.jiuqi.bde.bizmodel.define.gather;

import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.client.intf.IBizDataModel;
import java.util.List;

public interface IBizModelGather {
    public List<IBizDataModel> listBizDataModel();

    public List<IBizDataModel> listBizDataModelByCategory(String var1);

    public IBizDataModel getBizDataModel(String var1);

    public IBizDataModel getBizDataModelByComputationModel(String var1);

    public IBizComputationModel getComputationModelByCode(String var1);

    public IBizComputationModel findComputationModelByCode(String var1);

    public List<IBizComputationModel> listComputationModel();

    public List<IBizComputationModel> listComputationModelByCategory(String var1);

    public List<IBizComputationModel> listComputationModelByDataModel(String var1);

    public void reload() throws Exception;
}

