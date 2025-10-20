/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.common.BaseDataContext
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.OrgContext
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryParentType
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  org.apache.shiro.authz.UnauthorizedException
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.apache.shiro.subject.Subject
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.organization.controller.abandoned;

import com.jiuqi.va.basedata.common.BaseDataContext;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.OrgContext;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgCategoryDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.organization.common.OrgConstants;
import com.jiuqi.va.organization.service.OrgDataService;
import java.util.Collections;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/org/data"})
@Deprecated
@ConditionalOnProperty(name={"nvwa.organization.binary.compatible"}, havingValue="true", matchIfMissing=true)
public class OrgDataController {
    @Autowired
    private OrgDataService orgService;

    @PostMapping(value={"/get"})
    Object get(@RequestBody OrgDTO param) {
        try {
            this.checkDataMgrPermission(param);
        }
        catch (UnauthorizedException e) {
            return MonoVO.just(null);
        }
        return MonoVO.just((Object)JSONUtil.toJSONString((Object)this.orgService.get(param), (String)"yyyy-MM-dd HH:mm:ss"));
    }

    @PostMapping(value={"/count"})
    Object count(@RequestBody OrgDTO orgDTO) {
        return MonoVO.just((Object)this.orgService.count(orgDTO));
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody OrgDTO param) {
        PageVO<OrgDO> rs = null;
        try {
            this.checkDataMgrPermission(param);
        }
        catch (UnauthorizedException e) {
            rs = new PageVO<OrgDO>(true);
            rs.getRs().setMsg(403, "Unsupported Operation");
            return MonoVO.just((Object)JSONUtil.toJSONString(rs));
        }
        BaseDataContext.removeContext();
        OrgContext.removeContext();
        OrgContext.unbindColIndex();
        rs = this.orgService.list(param);
        return MonoVO.just((Object)JSONUtil.toJSONString(rs, (String)"yyyy-MM-dd HH:mm:ss"));
    }

    @PostMapping(value={"/checkIsLeaf"})
    Object checkIsLeaf(@RequestBody OrgDTO orgDTO) {
        return MonoVO.just((Object)this.orgService.checkIsLeaf(orgDTO));
    }

    @PostMapping(value={"/listSuperior"})
    Object listSuperior(@RequestBody OrgDTO param) {
        OrgContext.removeContext((String)param.getCategoryname());
        param.setQueryParentType(OrgDataOption.QueryParentType.ALL_PARENT_WITH_SELF);
        PageVO<OrgDO> page = this.orgService.list(param);
        if (page != null && page.getTotal() > 0) {
            Collections.reverse(page.getRows());
        }
        return MonoVO.just((Object)JSONUtil.toJSONString(page, (String)"yyyy-MM-dd HH:mm:ss"));
    }

    @PostMapping(value={"/listSubordinate"})
    Object listSubordinate(@RequestBody OrgDTO param) {
        OrgContext.removeContext((String)param.getCategoryname());
        param.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
        PageVO<OrgDO> page = this.orgService.list(param);
        return MonoVO.just((Object)JSONUtil.toJSONString(page, (String)"yyyy-MM-dd HH:mm:ss"));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaOrg:data:add"})
    Object add(@RequestBody OrgDTO orgDTO) {
        return MonoVO.just((Object)this.orgService.add(orgDTO));
    }

    @PostMapping(value={"/reladd"})
    @RequiresPermissions(value={"vaOrg:data:add"})
    Object relAdd(@RequestBody OrgDTO orgDTO) {
        return MonoVO.just((Object)this.orgService.relAdd(orgDTO));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object update(@RequestBody OrgDTO orgDTO) {
        return MonoVO.just((Object)this.orgService.update(orgDTO));
    }

    @PostMapping(value={"/checkUnique"})
    Object checkUnique(@RequestBody OrgDTO orgDTO) {
        return MonoVO.just((Object)this.orgService.checkUnique(orgDTO));
    }

    @PostMapping(value={"/changestate"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object changeState(@RequestBody OrgDTO orgDTO) {
        return MonoVO.just((Object)this.orgService.changeState(orgDTO));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaOrg:data:remove"})
    Object remove(@RequestBody OrgDTO orgDTO) {
        return MonoVO.just((Object)this.orgService.remove(orgDTO));
    }

    @PostMapping(value={"/batch/remove"})
    @RequiresPermissions(value={"vaOrg:data:remove"})
    Object batchRemove(@RequestBody OrgBatchOptDTO orgBatchOptDTO) {
        R checkRs;
        boolean isForceDelete;
        Object forceDelete = orgBatchOptDTO.getExtInfo("forceDelete");
        boolean bl = isForceDelete = forceDelete != null && (Boolean)forceDelete != false;
        if (isForceDelete && (checkRs = this.checkAdminAuth()).getCode() != 0) {
            return MonoVO.just((Object)checkRs);
        }
        return MonoVO.just((Object)this.orgService.batchRemove(orgBatchOptDTO));
    }

    @PostMapping(value={"/recovery"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object recovery(@RequestBody OrgDTO orgDataDTO) {
        return MonoVO.just((Object)this.orgService.recovery(orgDataDTO));
    }

    @PostMapping(value={"/up"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object up(@RequestBody OrgDTO orgDTO) {
        return MonoVO.just((Object)this.orgService.upOrDown(orgDTO, OrgConstants.UpOrDown.UP));
    }

    @PostMapping(value={"/down"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object down(@RequestBody OrgDTO orgDTO) {
        return MonoVO.just((Object)this.orgService.upOrDown(orgDTO, OrgConstants.UpOrDown.DOWN));
    }

    @PostMapping(value={"/move"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object move(@RequestBody OrgDTO orgDTO) {
        return MonoVO.just((Object)this.orgService.move(orgDTO));
    }

    @PostMapping(value={"/init/cache"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object initCache(@RequestBody OrgCategoryDTO orgCatDTO) {
        return MonoVO.just((Object)this.orgService.initCache(orgCatDTO));
    }

    @PostMapping(value={"/sync/cache"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object syncCache(@RequestBody OrgCategoryDTO orgCatDTO) {
        return MonoVO.just((Object)this.orgService.syncCache(orgCatDTO));
    }

    @PostMapping(value={"/clean/cache"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object cleanCache(@RequestBody OrgCategoryDTO orgCatDTO) {
        return MonoVO.just((Object)this.orgService.cleanCache(orgCatDTO));
    }

    @PostMapping(value={"/resetParents"})
    Object resetParents(@RequestBody OrgDTO orgDTO) {
        return MonoVO.just((Object)this.orgService.resetParents(orgDTO));
    }

    @PostMapping(value={"/sync"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object sync(@RequestBody OrgBatchOptDTO orgBatchOptDTO) {
        R checkRs = this.checkAdminAuth();
        if (checkRs.getCode() != 0) {
            return MonoVO.just((Object)checkRs);
        }
        return MonoVO.just((Object)this.orgService.sync(orgBatchOptDTO));
    }

    @PostMapping(value={"/fastupdown"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object fastUpDown(@RequestBody OrgDTO orgDTO) {
        return MonoVO.just((Object)this.orgService.fastUpDown(orgDTO));
    }

    @PostMapping(value={"/verDiff/list"})
    Object verDiffList(@RequestBody OrgDTO orgDTO) {
        R checkRs = this.checkAdminAuth();
        if (checkRs.getCode() != 0) {
            return MonoVO.just((Object)checkRs);
        }
        return MonoVO.just(this.orgService.verDiffList(orgDTO));
    }

    private R checkAdminAuth() {
        UserLoginDTO user;
        boolean isfromFeign = "true".equals(RequestContextUtil.getHeader((String)"FeignClient"));
        if (!(isfromFeign || (user = ShiroUtil.getUser()) != null && "super".equalsIgnoreCase(user.getMgrFlag()))) {
            return R.error((String)"\u65e0\u6267\u884c\u6743\u9650");
        }
        return R.ok();
    }

    private void checkDataMgrPermission(OrgDTO param) {
        Subject subject;
        boolean isfromFeign = "true".equals(RequestContextUtil.getHeader((String)"FeignClient"));
        if (!isfromFeign && (param.getAuthType() == OrgDataOption.AuthType.NONE || param.isOnlyMarkAuth()) && (subject = ShiroUtil.getSubjct()) != null) {
            subject.checkPermissions(new String[]{"vaOrg:unauthorized.query"});
        }
    }
}

