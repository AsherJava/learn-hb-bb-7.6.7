/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.constraints.NotNull
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.authz.web;

import com.jiuqi.nr.authz.ResultObject;
import com.jiuqi.nr.authz.bean.RoleQueryParam;
import com.jiuqi.nr.authz.bean.RoleTreeNode;
import com.jiuqi.nr.authz.service.IRoleTreeService;
import com.jiuqi.nr.common.itree.ITree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"api/v1/itree-common/role"})
@Api(tags={"\u89d2\u8272\u6811\u5f62\u670d\u52a1"})
public class RoleTreeController {
    @Autowired
    private IRoleTreeService iRoleTreeService;

    @PostMapping(value={"query/by-ids"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u7528\u6237\u53ef\u89c1\u7684\u89d2\u8272\u5217\u8868", notes="\u6839\u636e\u89d2\u8272id\u5217\u8868")
    public ResultObject getRolesByIds(@RequestBody List<String> roleids) {
        ResultObject resultObject = new ResultObject();
        List<RoleTreeNode> rolesByIds = this.iRoleTreeService.getRolesByIds(roleids);
        resultObject.setData(rolesByIds);
        resultObject.setState(true);
        return resultObject;
    }

    @GetMapping(value={"get/root"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u7528\u6237\u53ef\u89c1\u7684\u89d2\u8272\u6811\u5f62\u6839\u8282\u70b9")
    public ResultObject getRootNode(@RequestParam(required=false) boolean getGroup) {
        ResultObject resultObject = new ResultObject();
        try {
            List<ITree<RoleTreeNode>> rootNode = this.iRoleTreeService.getRootNode(getGroup);
            resultObject.setState(true);
            resultObject.setData(rootNode);
        }
        catch (Exception e) {
            resultObject.setState(false);
            resultObject.setMessage(e.getMessage());
        }
        return resultObject;
    }

    @PostMapping(value={"get/children"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u7528\u6237\u53ef\u89c1\u7684\u89d2\u8272\u6811\u5f62\u5b50\u8282\u70b9")
    public ResultObject getChildNode(@NotNull @RequestParam(required=false) boolean getGroup, @RequestBody RoleTreeNode parent) {
        ResultObject resultObject = new ResultObject();
        try {
            List<ITree<RoleTreeNode>> childNode = this.iRoleTreeService.getChildNode(parent, getGroup);
            resultObject.setState(true);
            resultObject.setData(childNode);
        }
        catch (Exception e) {
            resultObject.setState(false);
            resultObject.setMessage(e.getMessage());
        }
        return resultObject;
    }

    @PostMapping(value={"get/role-children"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u7528\u6237\u53ef\u89c1\u7684\u89d2\u8272\uff08\u4ec5\u89d2\u8272\uff09")
    public ResultObject getRoleNode(@NotNull @RequestBody RoleTreeNode parent) {
        ResultObject resultObject = new ResultObject();
        try {
            List<ITree<RoleTreeNode>> childNode = this.iRoleTreeService.getChildNode(parent);
            ArrayList<ITree<RoleTreeNode>> res = new ArrayList<ITree<RoleTreeNode>>();
            for (ITree<RoleTreeNode> roleTreeNodeITree : childNode) {
                if (((RoleTreeNode)roleTreeNodeITree.getData()).getGroupFlag().booleanValue()) continue;
                res.add(roleTreeNodeITree);
            }
            resultObject.setState(true);
            resultObject.setData(res);
        }
        catch (Exception e) {
            resultObject.setState(false);
            resultObject.setMessage(e.getMessage());
        }
        return resultObject;
    }

    @PostMapping(value={"search/tree"})
    @ResponseBody
    @ApiOperation(value="\u641c\u7d22\u540e\u5b9a\u4f4d\u6811\u5f62")
    public ResultObject getSearchTree(@NotNull @RequestParam(required=false) boolean getGroup, @RequestBody RoleTreeNode node) {
        ResultObject resultObject = new ResultObject();
        try {
            List<ITree<RoleTreeNode>> childNode = this.iRoleTreeService.getSearchTree(node, getGroup);
            resultObject.setState(true);
            resultObject.setData(childNode);
        }
        catch (Exception e) {
            resultObject.setState(false);
            resultObject.setMessage(e.getMessage());
        }
        return resultObject;
    }

    @GetMapping(value={"search/{searchKeyWord}"})
    @ResponseBody
    @ApiOperation(value="\u641c\u7d22\u540e\u5b9a\u4f4d\u6811\u5f62")
    public ResultObject searchRoleByFuzzyQuery(@PathVariable(value="searchKeyWord") String searchKeyWord) {
        ResultObject resultObject = new ResultObject();
        try {
            List<RoleTreeNode> roleTreeNodes = this.iRoleTreeService.searchRoleByFuzzyQuery(new RoleQueryParam(searchKeyWord));
            resultObject.setState(true);
            resultObject.setData(roleTreeNodes);
        }
        catch (Exception e) {
            resultObject.setState(false);
            resultObject.setMessage(e.getMessage());
        }
        return resultObject;
    }

    @PostMapping(value={"search"})
    @ApiOperation(value="\u641c\u7d22\u540e\u5b9a\u4f4d\u6811\u5f62")
    public ResultObject searchRoleByParam(@RequestBody RoleQueryParam param) {
        ResultObject resultObject = new ResultObject();
        try {
            List<RoleTreeNode> roleTreeNodes = this.iRoleTreeService.searchRoleByFuzzyQuery(param);
            resultObject.setState(true);
            resultObject.setData(roleTreeNodes);
        }
        catch (Exception e) {
            resultObject.setState(false);
            resultObject.setMessage(e.getMessage());
        }
        return resultObject;
    }
}

