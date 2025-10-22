/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.query.transfer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import com.jiuqi.nr.query.transfer.query.service.IQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryParamCenter {
    private static final String ERROR_CREATE_FILE = "\u751f\u6210\u6587\u4ef6\u9519\u8bef";
    private static final Logger log = LoggerFactory.getLogger(QueryParamCenter.class);
    private QueryModelType type = QueryModelType.PUBLIC;
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private IQueryService iQueryService;
}

