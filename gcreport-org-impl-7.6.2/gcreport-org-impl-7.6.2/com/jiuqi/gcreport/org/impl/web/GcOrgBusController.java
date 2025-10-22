/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.intf.GcOrgPublicClient
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgPublicApiParam
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.org.api.vo.tree.INode
 *  com.jiuqi.gcreport.org.api.vo.tree.ITree
 *  org.apache.shiro.util.ThreadContext
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.org.impl.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.intf.GcOrgPublicClient;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgPublicApiParam;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.org.api.vo.tree.INode;
import com.jiuqi.gcreport.org.api.vo.tree.ITree;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgCacheInnerVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
class GcOrgBusController
implements GcOrgPublicClient {
    GcOrgBusController() {
    }

    private GcOrgCenterService getTool(GcOrgPublicApiParam param) {
        ThreadContext.put((Object)"SECURITY_TENANT_KEY", (Object)"__default_tenant__");
        ThreadContext.put((Object)"NONE_AUTH_KEY", (Object)"true");
        GcAuthorityType type = GcAuthorityType.ACCESS;
        YearPeriodObject yp = new YearPeriodObject(null, param.getOrgVerCode());
        return GcOrgPublicTool.getInstance((String)param.getOrgType(), (GcAuthorityType)type, (YearPeriodObject)yp);
    }

    public BusinessResponseEntity<GcOrgCacheVO> getOrgByCode(GcOrgPublicApiParam param) {
        try {
            GcOrgCenterService tool = this.getTool(param);
            return BusinessResponseEntity.ok((Object)tool.getOrgByCode(param.getOrgCode()));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BusinessResponseEntity<GcOrgCacheVO> getSingleOrgByCode(GcOrgPublicApiParam param) {
        GcOrgCenterService tool = this.getTool(param);
        GcOrgCacheInnerVO orgByCode = (GcOrgCacheInnerVO)tool.getOrgByCode(param.getOrgCode());
        if (orgByCode == null) {
            return BusinessResponseEntity.ok(null);
        }
        GcOrgCacheInnerVO retOrg = new GcOrgCacheInnerVO();
        BeanUtils.copyProperties((Object)orgByCode, (Object)retOrg);
        retOrg.setChildren(CollectionUtils.newArrayList());
        return BusinessResponseEntity.ok((Object)((Object)retOrg));
    }

    public BusinessResponseEntity<List<GcOrgCacheVO>> listOrg(GcOrgPublicApiParam param) {
        GcOrgCenterService tool = this.getTool(param);
        return BusinessResponseEntity.ok((Object)tool.listAllOrgByParentId(null));
    }

    public BusinessResponseEntity<Object> listOrgBySearch(GcOrgPublicApiParam param) {
        GcOrgCenterService tool = this.getTool(param);
        List data = tool.listOrgBySearch(param.getSearchText(), param.getOrgParentCode());
        List nodes = data.stream().limit(param.getPageSize().intValue()).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }

    public BusinessResponseEntity<List<GcOrgCacheVO>> getOrgTree(GcOrgPublicApiParam param) {
        GcOrgCenterService tool = this.getTool(param);
        if (StringUtils.isEmpty(param.getOrgParentCode())) {
            List orgTree = tool.getOrgTree();
            return BusinessResponseEntity.ok((Object)orgTree);
        }
        List orgTree = tool.getOrgChildrenTree(param.getOrgParentCode());
        return BusinessResponseEntity.ok((Object)orgTree);
    }

    public BusinessResponseEntity<Object> listOrgByParent(GcOrgPublicApiParam param) {
        GcOrgCenterService tool = this.getTool(param);
        List data = tool.getOrgChildrenTree(param.getOrgParentCode());
        data = CollectionUtils.isEmpty((Collection)data) ? new ArrayList() : data;
        List nodes = data.stream().map(ITree::new).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }

    public BusinessResponseEntity<Object> listAllChildrenByParent(GcOrgPublicApiParam param) {
        GcOrgCenterService tool = this.getTool(param);
        List data = tool.listAllOrgByParentIdContainsSelf(param.getOrgParentCode());
        data = CollectionUtils.isEmpty((Collection)data) ? new ArrayList() : data;
        List nodes = data.stream().map(ITree::new).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }

    public BusinessResponseEntity<Object> getParentOrgByOrg(GcOrgPublicApiParam param) {
        GcOrgCenterService tool = this.getTool(param);
        List data = tool.getParentOrg(param.getOrgCode());
        List nodes = data.stream().map(v -> new ITree((INode)v)).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }

    public BusinessResponseEntity<Map<String, String>> getSingleOrgMapByCode(GcOrgPublicApiParam param) {
        GcOrgCenterService tool = this.getTool(param);
        GcOrgCacheInnerVO orgByCode = (GcOrgCacheInnerVO)tool.getOrgByCode(param.getOrgCode());
        if (orgByCode == null) {
            return BusinessResponseEntity.ok(null);
        }
        HashMap<String, String> retOrg = new HashMap<String, String>();
        retOrg.put("code", orgByCode.getCode());
        retOrg.put("title", orgByCode.getTitle());
        retOrg.put("key", orgByCode.getKey());
        retOrg.put("diffUnitId", orgByCode.getDiffUnitId());
        return BusinessResponseEntity.ok(retOrg);
    }
}

