/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Table
 *  com.google.common.collect.TreeBasedTable
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelGather
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 */
package com.jiuqi.bde.bizmodel.execute.datamodel.assbalance;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.execute.datamodel.assbalance.AbstractAssBalanceDataLoader;
import com.jiuqi.bde.bizmodel.execute.datamodel.gather.IAssBalanceDataLoaderGather;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssBalanceDataLoaderGather
implements IAssBalanceDataLoaderGather {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private IBizModelGather bizModelGather;
    private List<AbstractAssBalanceDataLoader> loaderList;
    private final Table<String, String, AbstractAssBalanceDataLoader> dataModelLoaderTable = TreeBasedTable.create();
    private final Table<String, String, AbstractAssBalanceDataLoader> bizModelLoaderTable = TreeBasedTable.create();

    public AssBalanceDataLoaderGather(@Autowired(required=false) IBizModelGather bizModelGather, @Autowired(required=false) List<AbstractAssBalanceDataLoader> loaderList) {
        this.bizModelGather = bizModelGather;
        this.loaderList = loaderList;
        this.afterPropertiesSet();
    }

    private void init() {
        if (this.loaderList == null) {
            this.loaderList = new ArrayList<AbstractAssBalanceDataLoader>();
        }
        this.loaderList.forEach(item -> {
            if ("#".equals(item.getComputationModelCode())) {
                this.dataModelLoaderTable.put((Object)item.getPluginType().getSymbol(), (Object)item.getBizDataModelCode(), item);
            } else {
                this.bizModelLoaderTable.put((Object)item.getPluginType().getSymbol(), (Object)item.getComputationModelCode(), item);
            }
        });
    }

    @Override
    public AbstractAssBalanceDataLoader getLoader(String pluginType, String computationModelCode) {
        Assert.isNotEmpty((String)pluginType);
        Assert.isNotEmpty((String)computationModelCode);
        IBizComputationModel modelDefineByCode = this.bizModelGather.getComputationModelByCode(computationModelCode);
        AbstractAssBalanceDataLoader loaderByDataModel = (AbstractAssBalanceDataLoader)this.dataModelLoaderTable.get((Object)pluginType, (Object)modelDefineByCode.getBizDataCode());
        if (loaderByDataModel != null) {
            return loaderByDataModel;
        }
        loaderByDataModel = (AbstractAssBalanceDataLoader)this.bizModelLoaderTable.get((Object)pluginType, (Object)computationModelCode);
        if (loaderByDataModel != null) {
            return loaderByDataModel;
        }
        throw new BusinessRuntimeException(String.format("\u63d2\u4ef6\u3010%1$s\u3011\u6839\u636e\u6570\u636e\u6a21\u578b\u3010%2$s\u3011\u4e1a\u52a1\u6a21\u578b\u3010%3$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u6570\u636e\u52a0\u8f7d\u5668\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5\u7a0b\u5e8f\u7248\u672c", pluginType, modelDefineByCode.getBizDataCode(), computationModelCode));
    }

    public void afterPropertiesSet() {
        try {
            this.init();
        }
        catch (Exception e) {
            this.logger.error("\u670d\u52a1\u542f\u52a8\u8fc7\u7a0b\u4e2d\u8f85\u52a9\u4f59\u989d\u6a21\u578b\u52a0\u8f7d\u5668\u6536\u96c6\u51fa\u73b0\u9519\u8bef", e);
        }
    }
}

