/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.bde.penetrate.impl.intf.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.bde.penetrate.impl.intf.IBeforePenetrateDataEnable;
import com.jiuqi.gcreport.bde.penetrate.impl.intf.IBeforePenetrateEnableGather;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultBeforePenetrateCheckerGather
implements IBeforePenetrateEnableGather,
InitializingBean {
    @Autowired(required=false)
    private List<IBeforePenetrateDataEnable> orgnBeforeCheckList;
    @Autowired(required=false)
    private List<IBeforePenetrateDataEnable> beforeCheckList;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        if (CollectionUtils.isEmpty(this.orgnBeforeCheckList)) {
            this.beforeCheckList = new ArrayList<IBeforePenetrateDataEnable>();
            return;
        }
        this.beforeCheckList = this.orgnBeforeCheckList.stream().sorted(new Comparator<IBeforePenetrateDataEnable>(){

            @Override
            public int compare(IBeforePenetrateDataEnable o1, IBeforePenetrateDataEnable o2) {
                return o1.getOrder() - o2.getOrder();
            }
        }).collect(Collectors.toList());
    }

    @Override
    public List<IBeforePenetrateDataEnable> getBeforeEnablerList() {
        return this.beforeCheckList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        block2: {
            try {
                this.init();
            }
            catch (Exception e) {
                this.logger.error("\u9ed8\u8ba4\u900f\u89c6\u524d\u7f6e\u6821\u9a8c\u5668\u6536\u96c6\u5668\u6ce8\u518c\u65f6\u51fa\u73b0\u5f02\u5e38", e);
                if (!CollectionUtils.isEmpty(this.beforeCheckList)) break block2;
                this.beforeCheckList = new ArrayList<IBeforePenetrateDataEnable>();
            }
        }
    }
}

