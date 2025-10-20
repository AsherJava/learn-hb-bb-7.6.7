/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.shiro.config;

import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.shiro.config.MyShiroAfterFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyShiroAfterFilterRunner {
    private static Logger logger = LoggerFactory.getLogger(MyShiroAfterFilterRunner.class);
    private List<MyShiroAfterFilter> filterList = null;

    private synchronized void init() {
        if (this.filterList != null) {
            return;
        }
        Map jtMap = ApplicationContextRegister.getBeansOfType(MyShiroAfterFilter.class);
        this.filterList = new ArrayList(jtMap.values());
        if (this.filterList.size() > 1) {
            Collections.sort(this.filterList, (o1, o2) -> {
                if (o1.getOrderNum() == o2.getOrderNum()) {
                    return 0;
                }
                return o1.getOrderNum() < o2.getOrderNum() ? -1 : 1;
            });
        }
    }

    public boolean execute() {
        return this.execute(true);
    }

    public boolean execute(boolean flag) {
        if (this.filterList == null) {
            this.init();
        }
        if (this.filterList.isEmpty()) {
            return flag;
        }
        if (!flag) {
            this.executeForFailure();
            return flag;
        }
        for (MyShiroAfterFilter filter : this.filterList) {
            try {
                if (filter.execute()) continue;
                flag = false;
                break;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (!flag) {
            this.executeForFailure();
        }
        return flag;
    }

    private void executeForFailure() {
        for (MyShiroAfterFilter filter : this.filterList) {
            try {
                filter.executeForFailure();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

