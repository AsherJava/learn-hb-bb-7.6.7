/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.authz.web;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.nr.authz.ResultObject;
import com.jiuqi.nr.authz.bean.RoleSelectBO;
import com.jiuqi.nr.authz.bean.RoleWebImpl;
import com.jiuqi.nr.authz.service.IRoleMgrService;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import io.swagger.annotations.Api;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/rolemgr"})
@Api(tags={"\u7528\u6237/\u6743\u9650\u7ba1\u7406"})
public class RoleMgrController {
    @Autowired
    private IRoleMgrService service;

    @PostMapping(value={"query/child"})
    @ResponseBody
    public ResultObject queryChild(@RequestBody RoleWebImpl impl) {
        return new ResultObject().setData(this.service.getChildren(impl));
    }

    @PostMapping(value={"query/serarch-tree"})
    @ResponseBody
    public List<ITree<RoleWebImpl>> getSearchTree(@RequestBody RoleWebImpl impl) {
        return this.service.getSearchTree(impl);
    }

    @GetMapping(value={"tree-init"})
    @ResponseBody
    public List<ITree<RoleWebImpl>> treeInit() {
        return this.service.getRootNode();
    }

    @PostMapping(value={"tree-query-child"})
    @ResponseBody
    public List<ITree<RoleWebImpl>> treeQueryChild(@RequestBody RoleWebImpl impl) {
        return this.service.getChildNode(impl);
    }

    @PostMapping(value={"query/title"})
    @ResponseBody
    public ResultObject fuzzyQuery(@RequestBody RoleWebImpl impl) {
        return new ResultObject().setData(this.service.searchRoleByFuzzyQuery(impl));
    }

    @GetMapping(value={"queryall"})
    @ResponseBody
    public ResultObject queryAll() {
        return new ResultObject().setData(this.service.getAllRoles());
    }

    @PostMapping(value={"query/detail"})
    @ResponseBody
    public ResultObject queryDetail(@RequestBody RoleWebImpl impl) {
        return new ResultObject().setData(this.service.getRoleDetail(impl));
    }

    @PostMapping(value={"query/role-by-ids"})
    @ResponseBody
    public ResultObject getRolesByIds(@RequestBody List<String> roleids) {
        ResultObject resultObject = new ResultObject();
        List<Role> rolesByIds = this.service.getRolesByIds(roleids);
        resultObject.setData(rolesByIds);
        resultObject.setState(true);
        return resultObject;
    }

    @GetMapping(value={"query/visible-count"})
    @ResponseBody
    public int getVisAbleCount() {
        return this.service.getVisibleCount();
    }

    @PostMapping(value={"checked-list"})
    public RoleSelectBO getNodes(@RequestBody List<String> checked) {
        int visibleCount = this.service.getVisibleCount();
        RoleSelectBO roleSelectBO = new RoleSelectBO();
        roleSelectBO.setAll(visibleCount);
        if (CollectionUtils.isEmpty(checked)) {
            roleSelectBO.setNodes(Collections.emptyList());
            return roleSelectBO;
        }
        List<Role> rolesByIds = this.service.getRolesByIds(checked);
        List<ITree<RoleWebImpl>> collect = rolesByIds.stream().map(r -> new ITree((INode)new RoleWebImpl((Role)r))).collect(Collectors.toList());
        roleSelectBO.setNodes(collect);
        return roleSelectBO;
    }
}

