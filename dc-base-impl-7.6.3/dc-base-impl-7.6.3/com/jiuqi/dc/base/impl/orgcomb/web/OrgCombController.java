/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupTreeNodeVO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupVO
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.base.impl.orgcomb.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupTreeNodeVO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupVO;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.base.impl.orgcomb.service.OrgCombDefineService;
import com.jiuqi.dc.base.impl.orgcomb.service.OrgCombGroupService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrgCombController {
    public static final String API_BASE_PATH = "/api/datacenter/v1/orggroup/scheme";
    @Autowired
    private OrgCombGroupService orgCombGroupService;
    @Autowired
    private OrgCombDefineService orgCombDefineService;

    @PostMapping(value={"/api/datacenter/v1/orggroup/scheme/getTreeData"})
    public BusinessResponseEntity<List<OrgCombGroupTreeNodeVO>> getTreeData() {
        return BusinessResponseEntity.ok(this.orgCombGroupService.getTreeData());
    }

    @PostMapping(value={"/api/datacenter/v1/orggroup/scheme/updateTreeNode"})
    public BusinessResponseEntity<Boolean> updateTreeNode(@RequestBody OrgCombGroupVO orgCombGroupVO) {
        return BusinessResponseEntity.ok((Object)this.orgCombGroupService.updateTreeNode(orgCombGroupVO));
    }

    @PostMapping(value={"/api/datacenter/v1/orggroup/scheme/deleteTreeNode"})
    public BusinessResponseEntity<Boolean> deleteTreeNode(@RequestBody OrgCombGroupTreeNodeVO treeNodeVO) {
        return BusinessResponseEntity.ok((Object)this.orgCombGroupService.deleteTreeNode(treeNodeVO));
    }

    @PostMapping(value={"/api/datacenter/v1/orggroup/scheme/addTreeNode"})
    public BusinessResponseEntity<Boolean> addTreeNode(@RequestBody String title) {
        return BusinessResponseEntity.ok((Object)this.orgCombGroupService.addTreeNode(title));
    }

    @PostMapping(value={"/api/datacenter/v1/orggroup/scheme/searchUnitData"})
    public BusinessResponseEntity<List<String>> searchUnitData(@RequestBody List<String> codes) {
        return BusinessResponseEntity.ok(this.orgCombGroupService.searchUnitData(codes));
    }

    @GetMapping(value={"/api/datacenter/v1//accountage/scheme/orggroup/list"})
    public BusinessResponseEntity<List<SelectOptionVO>> listOrgGroup() {
        ArrayList<SelectOptionVO> resultList = new ArrayList<SelectOptionVO>();
        resultList.add(new SelectOptionVO("ALL", "\u6240\u6709\u5355\u4f4d"));
        List<OrgCombDefineVO> orgGroupList = this.orgCombDefineService.listData();
        if (orgGroupList != null && orgGroupList.size() > 0) {
            orgGroupList.forEach(orgGroupSchemeVO -> resultList.add(new SelectOptionVO(orgGroupSchemeVO.getCode(), orgGroupSchemeVO.getName())));
        }
        return BusinessResponseEntity.ok(resultList);
    }
}

