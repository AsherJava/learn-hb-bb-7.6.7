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
package com.jiuqi.va.organization.controller;

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
import com.jiuqi.va.organization.domain.OrgDataRefAddDTO;
import com.jiuqi.va.organization.service.OrgDataService;
import java.util.List;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaOrgDataController")
@RequestMapping(value={"/org/data/binary"})
public class OrgDataController {
    @Autowired
    private OrgDataService orgService;

    @PostMapping(value={"/get"})
    Object get(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        try {
            this.checkDataMgrPermission(param);
        }
        catch (UnauthorizedException e) {
            return MonoVO.just((Object)JSONUtil.toBytes(null));
        }
        OrgDO rs = this.orgService.get(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/count"})
    Object count(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        int rs = this.orgService.count(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody byte[] binaryData) {
        PageVO<OrgDO> rs = null;
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        try {
            this.checkDataMgrPermission(param);
        }
        catch (UnauthorizedException e) {
            rs = new PageVO<OrgDO>(true);
            rs.getRs().setMsg(403, "Unsupported Operation");
            return MonoVO.just((Object)JSONUtil.toBytes(rs));
        }
        BaseDataContext.removeContext();
        OrgContext.removeContext();
        OrgContext.unbindColIndex();
        rs = this.orgService.list(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/code/list"})
    Object codeList(@RequestBody byte[] binaryData) {
        OrgContext.removeContext();
        BaseDataContext.removeContext();
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        PageVO<OrgDO> rs = this.orgService.list(param);
        String[] list = null;
        list = rs.getTotal() > 0 ? (String[])rs.getRows().stream().map(OrgDO::getCode).toArray(String[]::new) : new String[]{};
        return MonoVO.just((Object)JSONUtil.toBytes((Object)list));
    }

    @PostMapping(value={"/checkIsLeaf"})
    Object checkIsLeaf(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        R rs = this.orgService.checkIsLeaf(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaOrg:data:add"})
    Object add(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        R rs = this.orgService.add(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/reladd"})
    @RequiresPermissions(value={"vaOrg:data:add"})
    @Deprecated
    Object relAdd(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        R rs = this.orgService.relAdd(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/ref/add"})
    @RequiresPermissions(value={"vaOrg:data:add"})
    Object refAdd(@RequestBody byte[] binaryData) {
        OrgDataRefAddDTO param = (OrgDataRefAddDTO)((Object)JSONUtil.parseObject((byte[])binaryData, OrgDataRefAddDTO.class));
        R rs = this.orgService.refAdd(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object update(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        R rs = this.orgService.update(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/checkUnique"})
    Object checkUnique(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        R rs = this.orgService.checkUnique(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/changestate"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object changeState(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        R rs = this.orgService.changeState(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaOrg:data:remove"})
    Object remove(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        R rs = this.orgService.remove(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/batch/remove"})
    @RequiresPermissions(value={"vaOrg:data:remove"})
    Object batchRemove(@RequestBody byte[] binaryData) {
        R rs;
        boolean isForceDelete;
        OrgBatchOptDTO orgBatchOptDTO = (OrgBatchOptDTO)JSONUtil.parseObject((byte[])binaryData, OrgBatchOptDTO.class);
        Object forceDelete = orgBatchOptDTO.getExtInfo("forceDelete");
        boolean bl = isForceDelete = forceDelete != null && (Boolean)forceDelete != false;
        if (isForceDelete && (rs = this.checkAdminAuth()).getCode() != 0) {
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        rs = this.orgService.batchRemove(orgBatchOptDTO);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/recovery"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object recovery(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        R rs = this.orgService.recovery(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/up"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object up(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        R rs = this.orgService.upOrDown(param, OrgConstants.UpOrDown.UP);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/down"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object down(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        R rs = this.orgService.upOrDown(param, OrgConstants.UpOrDown.DOWN);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/move"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object move(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        R rs = this.orgService.move(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/init/cache"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object initCache(@RequestBody byte[] binaryData) {
        OrgCategoryDTO param = (OrgCategoryDTO)JSONUtil.parseObject((byte[])binaryData, OrgCategoryDTO.class);
        R rs = this.orgService.initCache(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/sync/cache"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object syncCache(@RequestBody byte[] binaryData) {
        OrgCategoryDTO param = (OrgCategoryDTO)JSONUtil.parseObject((byte[])binaryData, OrgCategoryDTO.class);
        R rs = this.orgService.syncCache(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/clean/cache"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object cleanCache(@RequestBody byte[] binaryData) {
        OrgCategoryDTO param = (OrgCategoryDTO)JSONUtil.parseObject((byte[])binaryData, OrgCategoryDTO.class);
        R rs = this.orgService.cleanCache(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/resetParents"})
    Object resetParents(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        R rs = this.orgService.resetParents(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/sync"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object sync(@RequestBody byte[] binaryData) {
        R rs = this.checkAdminAuth();
        if (rs.getCode() != 0) {
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        OrgBatchOptDTO orgBatchOptDTO = (OrgBatchOptDTO)JSONUtil.parseObject((byte[])binaryData, OrgBatchOptDTO.class);
        rs = this.orgService.sync(orgBatchOptDTO);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/fastupdown"})
    @RequiresPermissions(value={"vaOrg:data:update"})
    Object fastUpDown(@RequestBody byte[] binaryData) {
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        R rs = this.orgService.fastUpDown(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/verDiff/list"})
    Object verDiffList(@RequestBody byte[] binaryData) {
        R checkRs = this.checkAdminAuth();
        if (checkRs.getCode() != 0) {
            return MonoVO.just((Object)JSONUtil.toBytes((Object)checkRs));
        }
        OrgDTO param = (OrgDTO)JSONUtil.parseObject((byte[])binaryData, OrgDTO.class);
        List<OrgDO> rs = this.orgService.verDiffList(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
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

