/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.transfer;

import com.jiuqi.nr.query.transfer.QueryParamCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="dashboardParam")
public class DashboardParam {
    private static final Logger log = LoggerFactory.getLogger(DashboardParam.class);
    @Autowired
    private QueryParamCenter queryParamCenter;
}

