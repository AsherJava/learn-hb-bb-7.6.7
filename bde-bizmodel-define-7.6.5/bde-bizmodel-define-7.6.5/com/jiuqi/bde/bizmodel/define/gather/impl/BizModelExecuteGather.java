/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.bizmodel.define.gather.impl;

import com.jiuqi.bde.bizmodel.define.IBizModelExecute;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelExecuteGather;
import com.jiuqi.bde.bizmodel.define.gather.impl.BdeBizModelGather;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BizModelExecuteGather
implements IBizModelExecuteGather {
    private List<IBizModelExecute> registeredModelExecuteList;
    private final Map<String, IBizModelExecute> modelExecuteMap = new ConcurrentHashMap<String, IBizModelExecute>();
    private static final Logger logger = LoggerFactory.getLogger(BdeBizModelGather.class);

    public BizModelExecuteGather(@Autowired(required=false) List<IBizModelExecute> registeredModelExecuteList) {
        this.registeredModelExecuteList = registeredModelExecuteList;
        this.init();
    }

    @Override
    public IBizModelExecute getModelExecuteByCode(String bizModelCode) {
        Assert.isNotEmpty((String)bizModelCode);
        IBizModelExecute modelExecute = this.modelExecuteMap.get(bizModelCode);
        Assert.isNotNull((Object)modelExecute, (String)String.format("\u6ca1\u6709\u4ee3\u7801\u4e3a\u3010%s\u3011\u7684\u4e1a\u52a1\u6a21\u578b\u6267\u884c\u5668\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5\u7cfb\u7edf\u914d\u7f6e", bizModelCode), (Object[])new Object[0]);
        return modelExecute;
    }

    private void init() {
        try {
            this.reload();
        }
        catch (Exception e) {
            logger.error("\u4e1a\u52a1\u6a21\u578b\u52a0\u8f7d\u5668\u521d\u59cb\u5316\u51fa\u73b0\u9519\u8bef", e);
        }
    }

    @Override
    public void reload() throws Exception {
        this.modelExecuteMap.clear();
        if (CollectionUtils.isEmpty(this.registeredModelExecuteList)) {
            this.registeredModelExecuteList = new ArrayList<IBizModelExecute>();
        }
        this.registeredModelExecuteList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getComputationModelCode())) {
                logger.warn("\u4e1a\u52a1\u6a21\u578b\u6267\u884c\u63d2\u4ef6{}\uff0c\u4e1a\u52a1\u6a21\u578b\u4ee3\u7801\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.modelExecuteMap.containsKey(item.getComputationModelCode())) {
                this.modelExecuteMap.put(item.getComputationModelCode(), (IBizModelExecute)item);
            }
        });
    }
}

