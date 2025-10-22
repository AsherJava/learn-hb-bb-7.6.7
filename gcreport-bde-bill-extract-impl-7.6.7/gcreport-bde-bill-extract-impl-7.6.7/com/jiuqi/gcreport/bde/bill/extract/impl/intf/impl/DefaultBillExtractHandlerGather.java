/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.IBillExtractHandler
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.IBillExtractHandlerGather
 */
package com.jiuqi.gcreport.bde.bill.extract.impl.intf.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.IBillExtractHandler;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.IBillExtractHandlerGather;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultBillExtractHandlerGather
implements IBillExtractHandlerGather,
InitializingBean {
    @Autowired(required=false)
    private List<IBillExtractHandler> orgnModelHandlerList;
    private Map<String, IBillExtractHandler> modelHandlerMap;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        this.modelHandlerMap = CollectionUtils.newHashMap();
        for (IBillExtractHandler handler : this.orgnModelHandlerList) {
            if (StringUtils.isEmpty((String)handler.getModelCode())) {
                this.logger.error("\u5355\u636e\u53d6\u6570\u5904\u7406\u5668\u3010{}\u3011\u6a21\u578b\u6807\u8bc6\u4e3a\u7a7a\uff0c\u5df2\u81ea\u52a8\u8df3\u8fc7\u6ce8\u518c", (Object)handler.getClass());
                continue;
            }
            if (this.modelHandlerMap.get(handler.getModelCode()) != null) {
                this.logger.error("\u5355\u636e\u53d6\u6570\u5904\u7406\u5668\u3010{}\u3011\u4e0e\u5df2\u6ce8\u518c\u7684\u3010%2$s\u3011\u6807\u8bc6\u91cd\u590d\uff0c\u5df2\u81ea\u52a8\u8df3\u8fc7\u6ce8\u518c", (Object)handler.getClass(), (Object)this.modelHandlerMap.get(handler.getModelCode()).getClass());
                continue;
            }
            this.modelHandlerMap.put(handler.getModelCode(), handler);
        }
    }

    public IBillExtractHandler getHandlerByModel(String modelCode) {
        return this.modelHandlerMap.get(modelCode) == null ? this.modelHandlerMap.get("DEFAULT") : this.modelHandlerMap.get(modelCode);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        block2: {
            try {
                this.init();
            }
            catch (Exception e) {
                this.logger.error("\u5355\u636e\u53d6\u6570\u5904\u7406\u5668\u5668\u6536\u96c6\u5668\u6ce8\u518c\u65f6\u51fa\u73b0\u5f02\u5e38", e);
                if (this.modelHandlerMap != null) break block2;
                this.modelHandlerMap = CollectionUtils.newHashMap();
            }
        }
    }
}

