/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  com.jiuqi.va.query.fetch.web.FetchQueryClient
 *  com.jiuqi.va.query.tree.vo.MenuTreeVO
 *  com.jiuqi.va.query.tree.web.QueryTreeClient
 *  org.springframework.http.HttpMethod
 */
package com.jiuqi.gcreport.sdk.rest;

import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.sdk.rest.AbstractBdeRestRequest;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import com.jiuqi.va.query.fetch.web.FetchQueryClient;
import com.jiuqi.va.query.tree.vo.MenuTreeVO;
import com.jiuqi.va.query.tree.web.QueryTreeClient;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class FloatRegionConfigRequest
extends AbstractBdeRestRequest {
    private static final String TREE_INIT_API = "/api/datacenter/v1/userDefined/tree/init";
    private static final String QUERY_PARAM_LIST_API = "/api/gcreport/v1/fetch/userDefined/getQueryParams/{templateId}";
    @Autowired
    private QueryTreeClient queryTreeClient;
    @Autowired
    private FetchQueryClient fetchQueryClient;

    public List<MenuTreeVO> treeInit() {
        if (BdeCommonUtil.isStandaloneServer()) {
            BusinessResponseEntity treeInitResponse = this.queryTreeClient.treeInit();
            if (treeInitResponse.isSuccess()) {
                return (List)treeInitResponse.getData();
            }
            throw new BusinessRuntimeException(treeInitResponse.getErrorMessage());
        }
        String result = this.exchangeJSON(TREE_INIT_API, null, HttpMethod.GET);
        if (null == result) {
            return new ArrayList<MenuTreeVO>();
        }
        return JSONUtil.parseArray((String)result, MenuTreeVO.class);
    }

    public List<FetchQueryFiledVO> getQueryParams(String templateCode) {
        if (BdeCommonUtil.isStandaloneServer()) {
            BusinessResponseEntity queryFieldsResponse = this.fetchQueryClient.getQueryParams(templateCode);
            if (queryFieldsResponse.isSuccess()) {
                return (List)queryFieldsResponse.getData();
            }
            throw new BusinessRuntimeException(queryFieldsResponse.getErrorMessage());
        }
        String result = this.exchangeJSON(QUERY_PARAM_LIST_API.replace("{templateId}", templateCode), null, HttpMethod.GET);
        if (null == result) {
            return new ArrayList<FetchQueryFiledVO>();
        }
        return JSONUtil.parseArray((String)result, FetchQueryFiledVO.class);
    }
}

