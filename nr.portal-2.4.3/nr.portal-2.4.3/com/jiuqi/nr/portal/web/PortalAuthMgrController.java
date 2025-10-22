/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.common.resource.bean.PrivilegeWebImpl
 *  com.jiuqi.nr.common.resource.exception.AuthErrorEnum
 *  com.jiuqi.nr.common.resource.service.IAuthService
 *  io.swagger.annotations.Api
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.portal.web;

import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.common.resource.bean.PrivilegeWebImpl;
import com.jiuqi.nr.common.resource.exception.AuthErrorEnum;
import com.jiuqi.nr.common.resource.service.IAuthService;
import com.jiuqi.nr.portal.BatchPrivilegeWebImpl;
import com.jiuqi.nr.portal.PortalBatchPrivilegeWebImpl;
import com.jiuqi.nr.portal.news2.vo.ResultObject;
import io.swagger.annotations.Api;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"api/v1/authmgr"})
@Api(tags={"\u7528\u6237/\u6743\u9650\u7ba1\u7406"})
public class PortalAuthMgrController {
    private static final Logger logger = LogFactory.getLogger(PortalAuthMgrController.class);
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private IAuthService service;

    @PostMapping(value={"batch-privilege-save"})
    @ResponseBody
    public ResultObject saveBatchPrivilege(@RequestBody BatchPrivilegeWebImpl impl) {
        ResultObject resultObject = new ResultObject();
        try {
            if (impl != null && impl.getOwnerList() != null) {
                String currUserID = NpContextHolder.getContext().getIdentityId();
                for (String owner : impl.getOwnerList()) {
                    if (currUserID.equals(owner)) {
                        logger.info("\u7528\u6237\u4e0d\u5141\u8bb8\u5bf9\u81ea\u8eab\u6388\u6743\uff01");
                        continue;
                    }
                    PrivilegeWebImpl webImpl = impl.getWebImpl();
                    Map resource = webImpl.getAuthority();
                    for (Map.Entry resourceEntry : resource.entrySet()) {
                        Map privilegeIdMapAuthTypes = (Map)resourceEntry.getValue();
                        for (Map.Entry privilegeIdMapAuthType : privilegeIdMapAuthTypes.entrySet()) {
                            Boolean canOperate;
                            String privilegeId = (String)privilegeIdMapAuthType.getKey();
                            if (!this.isPortalResource(privilegeId).booleanValue() || (canOperate = this.hasAuthOperate(privilegeId, (String)resourceEntry.getKey())).booleanValue()) continue;
                            resultObject.setState(false);
                            resultObject.setMessage("\u4fdd\u5b58\u5931\u8d25\uff0c\u4fdd\u5b58\u7684\u6743\u9650\u4e2d\u6709\u7684\u672a\u62e5\u6709\u6388\u6743\u6743\u9650\uff01");
                            return resultObject;
                        }
                    }
                    webImpl.setOwnerId(owner);
                    this.service.savePrivilege(webImpl);
                }
                resultObject.setState(true);
                resultObject.setMessage("\u4fdd\u5b58\u6210\u529f");
            }
        }
        catch (JQException e) {
            logger.error(e.getMessage(), (Throwable)e);
            resultObject.setState(false);
            resultObject.setMessage("\u4fdd\u5b58\u5931\u8d25" + e.getMessage());
        }
        return resultObject;
    }

    @PostMapping(value={"portal-batch-privilege-save"})
    @ResponseBody
    public ResultObject saveBatchPrivilege(@RequestBody PortalBatchPrivilegeWebImpl impl) {
        ResultObject resultObject = new ResultObject();
        try {
            this.portalSave(impl);
        }
        catch (Exception e) {
            resultObject.setState(false);
            resultObject.setMessage(e.getMessage());
            return resultObject;
        }
        resultObject.setState(true);
        resultObject.setMessage("\u4fdd\u5b58\u6210\u529f");
        return resultObject;
    }

    @Transactional(rollbackFor={Exception.class})
    public void portalSave(PortalBatchPrivilegeWebImpl impl) throws JQException {
        String currUserID = NpContextHolder.getContext().getIdentityId();
        if (impl != null) {
            Map<String, Boolean> noneOwner;
            Map<String, Boolean> allowOwner = impl.getAllowOwner();
            if (allowOwner != null) {
                for (Map.Entry<String, Boolean> owner : allowOwner.entrySet()) {
                    if (currUserID.equals(owner.getKey())) continue;
                    PrivilegeWebImpl allow = impl.getAllow();
                    this.check(owner, allow);
                }
            }
            if ((noneOwner = impl.getNoneOwner()) != null) {
                for (Map.Entry<String, Boolean> owner : noneOwner.entrySet()) {
                    if (currUserID.equals(owner.getKey())) continue;
                    PrivilegeWebImpl none = impl.getNone();
                    this.check(owner, none);
                }
            }
        }
    }

    public void check(Map.Entry<String, Boolean> owner, PrivilegeWebImpl none) throws JQException {
        none.setIsRole(owner.getValue());
        Map authority = none.getAuthority();
        for (Map.Entry resourceEntry : authority.entrySet()) {
            Map privilegeIdMapAuthTypes = (Map)resourceEntry.getValue();
            for (Map.Entry privilegeIdMapAuthType : privilegeIdMapAuthTypes.entrySet()) {
                Boolean canOperate;
                String privilegeId = (String)privilegeIdMapAuthType.getKey();
                if (!this.isPortalResource(privilegeId).booleanValue() || (canOperate = this.hasAuthOperate(privilegeId, (String)resourceEntry.getKey())).booleanValue()) continue;
                throw new JQException((ErrorEnum)AuthErrorEnum.AUTH_ROLE_005);
            }
        }
        none.setOwnerId(owner.getKey());
        this.service.savePrivilege(none);
    }

    private Boolean hasAuthOperate(String privilegeId, String resource) {
        String identityId = NpContextHolder.getContext().getIdentityId();
        return this.privilegeService.hasDelegateAuth(privilegeId, identityId, (Object)resource);
    }

    private Boolean isPortalResource(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        return id.equals("portal_resource_read") || id.equals("portal_resource_write");
    }
}

