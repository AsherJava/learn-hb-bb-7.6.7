/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.transfer.query.query.impl;

import com.jiuqi.nr.query.transfer.query.query.IQueryTreeService;
import com.jiuqi.nr.query.transfer.query.service.IQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryTreeServiceImpl
implements IQueryTreeService {
    @Autowired
    private IQueryService iQueryService;
}

