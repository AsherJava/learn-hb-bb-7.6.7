/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.i18n.dto.ResultObject
 *  io.swagger.annotations.Api
 *  javax.validation.constraints.NotEmpty
 *  javax.validation.constraints.NotNull
 *  org.apache.shiro.authz.annotation.Logical
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.common.resource.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.i18n.dto.ResultObject;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.resource.bean.PrivilegeVO;
import com.jiuqi.nr.common.resource.bean.PrivilegeWebImpl;
import com.jiuqi.nr.common.resource.bean.ResourceVO;
import com.jiuqi.nr.common.resource.bean.ResourceWebImpl;
import com.jiuqi.nr.common.resource.service.IAuthService;
import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"api/v1/authmgr"})
@Api(tags={"\u7528\u6237/\u6743\u9650\u7ba1\u7406"})
public class AuthMgrController {
    private static final Logger logger = LoggerFactory.getLogger(AuthMgrController.class);
    @Autowired
    private IAuthService service;

    @PostMapping(value={"resource"})
    @ResponseBody
    public List<ITree<ResourceWebImpl>> getRoot(@RequestBody ResourceVO root) {
        if (root.getCategoryId() == null || root.getOwnerId() == null) {
            return null;
        }
        return this.service.getRootNode(root.getCategoryId(), root.getOwnerId(), root.getParam());
    }

    @PostMapping(value={"resource/children"})
    @ResponseBody
    public List<ITree<ResourceWebImpl>> getChildren(@RequestBody ResourceVO parent) {
        if (parent == null) {
            throw new IllegalArgumentException("\u53c2\u6570\u9519\u8bef");
        }
        ResourceWebImpl impl = new ResourceWebImpl();
        impl.setKey(parent.getKey());
        impl.setCategoryId(parent.getCategoryId());
        impl.setOwnerId(parent.getOwnerId());
        impl.setParam(parent.getParam());
        return this.service.getChildNode(impl);
    }

    @PostMapping(value={"resource/selected"})
    @ResponseBody
    public List<ITree<ResourceWebImpl>> treeLocalChild(@RequestBody @NotNull @NotEmpty List<ResourceWebImpl> impl) {
        try {
            return this.service.getSelectedNode(impl);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @PostMapping(value={"privilege-get"})
    public List<ResourceWebImpl> getPrivilege(@RequestBody List<ResourceWebImpl> impl) {
        return this.service.getPrivilege(impl);
    }

    @PostMapping(value={"privilege-save"})
    @ResponseBody
    @RequiresPermissions(value={"nvwa:user:list", "nvwa:role:list", "nvwa:role:auth"}, logical=Logical.OR)
    public ResultObject savePrivilege(@RequestBody PrivilegeVO vo) {
        ResultObject resultObject = new ResultObject();
        try {
            PrivilegeWebImpl impl = new PrivilegeWebImpl();
            impl.setOwnerId(vo.getOwnerId());
            impl.setAuthority(vo.getAuthority());
            impl.setIsRole(vo.getRole());
            impl.setDuty(vo.getDuty());
            impl.setResCategoryId(vo.getResCategoryId());
            impl.setResourceMapPrivilegeType(vo.getResourceMapPrivilegeType());
            this.service.savePrivilege(impl);
            resultObject.setState(true);
            resultObject.setMessage("\u4fdd\u5b58\u6210\u529f");
        }
        catch (JQException e) {
            resultObject.setState(false);
            resultObject.setMessage("\u4fdd\u5b58\u5931\u8d25" + e.getMessage());
        }
        return resultObject;
    }
}

