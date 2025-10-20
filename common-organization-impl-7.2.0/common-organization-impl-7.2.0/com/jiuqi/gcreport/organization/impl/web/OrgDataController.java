/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.organization.api.GcOrgDataClient
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataVO
 *  com.jiuqi.gcreport.organization.api.vo.tree.ITree
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.organization.impl.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.organization.api.GcOrgDataClient;
import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.api.vo.OrgDataVO;
import com.jiuqi.gcreport.organization.api.vo.tree.ITree;
import com.jiuqi.gcreport.organization.impl.bean.OrgDataDO;
import com.jiuqi.gcreport.organization.impl.service.GcOrgDataService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@Primary
@RestController
public class OrgDataController
implements GcOrgDataClient {
    @Autowired
    private GcOrgDataService gcOrgDataService;

    public BusinessResponseEntity<OrgDataVO> getOrgByCode(OrgDataParam param) {
        ArrayList<OrgDataDO> datas = this.gcOrgDataService.list(param);
        datas = CollectionUtils.isEmpty(datas) ? new ArrayList<OrgDataDO>() : datas;
        List<OrgDataVO> nodes = this.gcOrgDataService.buildListNode(datas, param);
        return BusinessResponseEntity.ok((Object)nodes.get(0));
    }

    public BusinessResponseEntity<Object> listOrgBySearch(OrgDataParam param) {
        ArrayList<OrgDataDO> datas = this.gcOrgDataService.listBySearch(param);
        datas = CollectionUtils.isEmpty(datas) ? new ArrayList<OrgDataDO>() : datas;
        List<OrgDataVO> nodes = this.gcOrgDataService.buildListNode(datas, param);
        return BusinessResponseEntity.ok(nodes);
    }

    public BusinessResponseEntity<Object> listOrgByParent(OrgDataParam param) {
        ArrayList<OrgDataDO> datas = this.gcOrgDataService.listDirectChildren(param);
        datas = CollectionUtils.isEmpty(datas) ? new ArrayList<OrgDataDO>() : datas;
        List<ITree<OrgDataVO>> nodes = this.gcOrgDataService.buildTreeNode(datas, param);
        return BusinessResponseEntity.ok(nodes);
    }

    public BusinessResponseEntity<Object> listAllChildrenByParent(OrgDataParam param) {
        ArrayList<OrgDataDO> datas = this.gcOrgDataService.listAllChildrenWithSelf(param);
        datas = CollectionUtils.isEmpty(datas) ? new ArrayList<OrgDataDO>() : datas;
        List<ITree<OrgDataVO>> nodes = this.gcOrgDataService.buildTreeNode(datas, param);
        return BusinessResponseEntity.ok(nodes);
    }

    public BusinessResponseEntity<Object> getParentOrgByOrg(OrgDataParam param) {
        ArrayList<OrgDataDO> datas = this.gcOrgDataService.listSuperior(param);
        datas = CollectionUtils.isEmpty(datas) ? new ArrayList<OrgDataDO>() : datas;
        List<OrgDataVO> nodes = this.gcOrgDataService.buildListNode(datas, param);
        return BusinessResponseEntity.ok(nodes);
    }

    public BusinessResponseEntity<Object> loadStaticResource(OrgDataParam param) {
        return BusinessResponseEntity.ok(this.gcOrgDataService.loadStaticResource(param));
    }
}

