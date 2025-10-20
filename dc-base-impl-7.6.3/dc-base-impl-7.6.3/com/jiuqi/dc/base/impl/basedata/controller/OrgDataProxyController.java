/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataVO
 *  com.jiuqi.gcreport.organization.api.vo.tree.INode
 *  com.jiuqi.gcreport.organization.api.vo.tree.ITree
 *  com.jiuqi.gcreport.organization.impl.service.GcOrgDataService
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.base.impl.basedata.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.api.vo.OrgDataVO;
import com.jiuqi.gcreport.organization.api.vo.tree.INode;
import com.jiuqi.gcreport.organization.api.vo.tree.ITree;
import com.jiuqi.gcreport.organization.impl.service.GcOrgDataService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrgDataProxyController {
    public static final String ORG_DATA_API_BASE_PATH = "/api/datacenter/v1/orgdata";
    @Autowired
    private GcOrgDataService gcOrgDataService;

    @PostMapping(value={"/api/datacenter/v1/orgdata/get"})
    public BusinessResponseEntity<OrgDataVO> getOrgByCode(@RequestBody OrgDataParam param) {
        List data = this.gcOrgDataService.list(param);
        List nodes = data.stream().map(v -> this.gcOrgDataService.convert(v)).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes.get(0));
    }

    @PostMapping(value={"/api/datacenter/v1/orgdata/orgsWithCondition"})
    public BusinessResponseEntity<Object> listOrgBySearch(@RequestBody OrgDataParam param) {
        List data = this.gcOrgDataService.list(param);
        List nodes = data.stream().map(v -> this.gcOrgDataService.convert(v)).limit(param.getPageSize().intValue()).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }

    @PostMapping(value={"/api/datacenter/v1/orgdata/orgLevelTree"})
    public BusinessResponseEntity<Object> listOrgByParent(@RequestBody OrgDataParam param) {
        List data = this.gcOrgDataService.listDirectChildren(param);
        data = CollectionUtils.isEmpty((Collection)data) ? new ArrayList() : data;
        List nodes = data.stream().map(v -> new ITree((INode)this.gcOrgDataService.convert(v))).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }

    @PostMapping(value={"/api/datacenter/v1/orgdata/allOrgLevelTree"})
    public BusinessResponseEntity<Object> listAllChildrenByParent(@RequestBody OrgDataParam param) {
        List data = this.gcOrgDataService.listAllChildrenWithSelf(param);
        data = CollectionUtils.isEmpty((Collection)data) ? new ArrayList() : data;
        List nodes = data.stream().map(v -> new ITree((INode)this.gcOrgDataService.convert(v))).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }

    @PostMapping(value={"/api/datacenter/v1/orgdata/getParentOrg"})
    public BusinessResponseEntity<Object> getParentOrgByOrg(@RequestBody OrgDataParam param) {
        List data = this.gcOrgDataService.listSuperior(param);
        List nodes = data.stream().map(v -> this.gcOrgDataService.convert(v)).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }
}

