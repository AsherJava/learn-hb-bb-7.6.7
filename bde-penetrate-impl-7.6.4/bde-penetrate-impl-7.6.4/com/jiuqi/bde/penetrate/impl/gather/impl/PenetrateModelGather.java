/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.penetrate.impl.gather.impl;

import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.gather.IPenetrateModelGather;
import com.jiuqi.bde.penetrate.impl.model.IBdePenetrateModel;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class PenetrateModelGather
implements IPenetrateModelGather,
InitializingBean {
    @Autowired(required=false)
    private List<IBdePenetrateModel> providerList;
    private final Map<PenetrateTypeEnum, IBdePenetrateModel> providerMap = new ConcurrentHashMap<PenetrateTypeEnum, IBdePenetrateModel>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        if (CollectionUtils.isEmpty(this.providerList)) {
            this.providerMap.clear();
            return;
        }
        this.providerList.forEach(item -> {
            if (item.getPenetrateType() == null) {
                this.logger.warn("\u67e5\u8be2\u5217\u63d0\u4f9b\u5668\u6ca1\u6709\u900f\u89c6\u7c7b\u578b\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.providerMap.containsKey((Object)item.getPenetrateType())) {
                this.providerMap.put(item.getPenetrateType(), (IBdePenetrateModel)item);
            }
        });
    }

    @Override
    public IBdePenetrateModel getModel(PenetrateTypeEnum penetrateType) {
        Assert.isNotNull((Object)((Object)penetrateType));
        IBdePenetrateModel provider = this.providerMap.get((Object)penetrateType);
        if (provider == null) {
            throw new BusinessRuntimeException(String.format("\u900f\u89c6\u7c7b\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6a21\u578b", penetrateType.getCode()));
        }
        return provider;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}

