/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.query.transfer.QueryParamCenter
 */
package com.jiuqi.nr.dashboard.transfer;

import com.jiuqi.nr.query.transfer.QueryParamCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="simpleQueryParam")
public class SimpleQueryParam {
    @Autowired
    private QueryParamCenter queryParamCenter;
}

