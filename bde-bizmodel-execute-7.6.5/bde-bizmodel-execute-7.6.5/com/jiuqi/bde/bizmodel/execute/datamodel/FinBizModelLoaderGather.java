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
package com.jiuqi.bde.bizmodel.execute.datamodel;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.execute.datamodel.AbstractFinBizDataModelLoader;
import com.jiuqi.bde.bizmodel.execute.datamodel.IFinBizModelLoaderGather;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class FinBizModelLoaderGather
implements IFinBizModelLoaderGather {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private IBizModelGather bizModelGather;
    private List<AbstractFinBizDataModelLoader> loaderList;
    private final Table<String, String, AbstractFinBizDataModelLoader> dataModelLoaderTable = TreeBasedTable.create();
    private final Table<String, String, AbstractFinBizDataModelLoader> bizModelLoaderTable = TreeBasedTable.create();

    public FinBizModelLoaderGather(@Autowired(required=false) IBizModelGather bizModelGather, @Autowired(required=false) List<AbstractFinBizDataModelLoader> loaderList) {
        this.bizModelGather = bizModelGather;
        this.loaderList = loaderList;
        try {
            this.init();
        }
        catch (Exception e) {
            this.logger.error("\u670d\u52a1\u542f\u52a8\u8fc7\u7a0b\u4e2d\u8d22\u52a1\u6a21\u578b\u52a0\u8f7d\u5668\u6536\u96c6\u51fa\u73b0\u9519\u8bef", e);
        }
    }

    private void init() {
        if (this.loaderList == null) {
            this.loaderList = new ArrayList<AbstractFinBizDataModelLoader>();
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
    public AbstractFinBizDataModelLoader getLoader(String pluginType, String computationModelCode) {
        Assert.isNotEmpty((String)pluginType);
        Assert.isNotEmpty((String)computationModelCode);
        IBizComputationModel modelDefineByCode = this.bizModelGather.getComputationModelByCode(computationModelCode);
        AbstractFinBizDataModelLoader loaderByDataModel = (AbstractFinBizDataModelLoader)this.dataModelLoaderTable.get((Object)pluginType, (Object)modelDefineByCode.getBizDataCode());
        if (loaderByDataModel != null) {
            return loaderByDataModel;
        }
        loaderByDataModel = (AbstractFinBizDataModelLoader)this.bizModelLoaderTable.get((Object)pluginType, (Object)computationModelCode);
        if (loaderByDataModel != null) {
            return loaderByDataModel;
        }
        throw new BusinessRuntimeException(String.format("\u63d2\u4ef6\u3010%1$s\u3011\u6839\u636e\u6570\u636e\u6a21\u578b\u3010%2$s\u3011\u4e1a\u52a1\u6a21\u578b\u3010%3$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u6570\u636e\u52a0\u8f7d\u5668\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5\u7a0b\u5e8f\u7248\u672c", pluginType, modelDefineByCode.getBizDataCode(), computationModelCode));
    }
}

