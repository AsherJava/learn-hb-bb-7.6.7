/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.query.transfer.QueryParamCenter
 */
package com.jiuqi.nr.dashboard.transfer;

import com.jiuqi.nr.query.transfer.QueryParamCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="dimensionQueryParam")
public class DimensionQueryParam {
    private static final Logger log = LoggerFactory.getLogger(DimensionQueryParam.class);
    @Autowired
    private QueryParamCenter queryParamCenter;
}

