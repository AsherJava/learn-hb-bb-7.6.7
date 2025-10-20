/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.common.init;

import com.jiuqi.bde.common.init.IBdeModuleItemInitiator;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeModuleItemInitiatorGather {
    @Autowired(required=false)
    private List<IBdeModuleItemInitiator> registeredBdeModuleInitiatorList;
    private final List<IBdeModuleItemInitiator> bdeModuleInitiatorList = new ArrayList<IBdeModuleItemInitiator>();
    private static final Logger logger = LoggerFactory.getLogger(BdeModuleItemInitiatorGather.class);

    public BdeModuleItemInitiatorGather(@Autowired(required=false) List<IBdeModuleItemInitiator> registeredBdeModuleInitiatorList) {
        this.registeredBdeModuleInitiatorList = registeredBdeModuleInitiatorList;
        this.init();
    }

    public List<IBdeModuleItemInitiator> listModuleInitiators() {
        return this.bdeModuleInitiatorList;
    }

    private void init() {
        try {
            this.reload();
        }
        catch (Exception e) {
            logger.error("\u5bfc\u5165\u5bfc\u51fa\u62d3\u5c55\u6536\u96c6\u5668\u521d\u59cb\u5316\u51fa\u73b0\u9519\u8bef", e);
        }
    }

    public void reload() {
        this.bdeModuleInitiatorList.clear();
        if (CollectionUtils.isEmpty(this.registeredBdeModuleInitiatorList)) {
            this.registeredBdeModuleInitiatorList = new ArrayList<IBdeModuleItemInitiator>();
        }
        this.bdeModuleInitiatorList.addAll(this.registeredBdeModuleInitiatorList.stream().sorted((a, b) -> a.getOrder() - b.getOrder()).collect(Collectors.toList()));
    }
}

