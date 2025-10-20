/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.bde.fetch.impl.intf.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.bde.fetch.impl.intf.IBeforeBatchFetchDataHandler;
import com.jiuqi.gcreport.bde.fetch.impl.intf.IBeforeBatchFetchDataHandlerGather;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultBeforeBatchFetchDataHandlerGather
implements IBeforeBatchFetchDataHandlerGather {
    @Autowired(required=false)
    private List<IBeforeBatchFetchDataHandler> orgnBeforeHandlerList;
    @Autowired(required=false)
    private List<IBeforeBatchFetchDataHandler> beforeHandlerList;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        if (CollectionUtils.isEmpty(this.orgnBeforeHandlerList)) {
            this.beforeHandlerList = new ArrayList<IBeforeBatchFetchDataHandler>();
            return;
        }
        this.beforeHandlerList = this.orgnBeforeHandlerList.stream().sorted(new Comparator<IBeforeBatchFetchDataHandler>(){

            @Override
            public int compare(IBeforeBatchFetchDataHandler o1, IBeforeBatchFetchDataHandler o2) {
                return o1.getOrder() - o2.getOrder();
            }
        }).collect(Collectors.toList());
    }

    @Override
    public List<IBeforeBatchFetchDataHandler> getHandlerList() {
        return this.beforeHandlerList;
    }

    public void afterPropertiesSet() throws Exception {
        block2: {
            try {
                this.init();
            }
            catch (Exception e) {
                this.logger.error("\u9ed8\u8ba4\u6279\u91cf\u524d\u7f6e\u5904\u7406\u5668\u6536\u96c6\u5668\u6ce8\u518c\u65f6\u51fa\u73b0\u5f02\u5e38", e);
                if (!CollectionUtils.isEmpty(this.beforeHandlerList)) break block2;
                this.beforeHandlerList = new ArrayList<IBeforeBatchFetchDataHandler>();
            }
        }
    }
}

