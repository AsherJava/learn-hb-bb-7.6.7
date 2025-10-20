/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.organization.controller;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.common.OrgCategoryTransUtil;
import com.jiuqi.va.organization.domain.ZBDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaOrgCategoryController")
@RequestMapping(value={"/org/category/binary"})
public class OrgCategoryController {
    private static Logger logger = LoggerFactory.getLogger(OrgCategoryController.class);
    @Autowired
    private OrgCategoryService orgCatService;
    private OrgCategoryTransUtil orgCategoryTransUtil;

    @PostMapping(value={"/list"})
    Object list(@RequestBody byte[] binaryData) {
        OrgCategoryDO param = (OrgCategoryDO)JSONUtil.parseObject((byte[])binaryData, OrgCategoryDO.class);
        PageVO<OrgCategoryDO> rs = this.orgCatService.list(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object add(@RequestBody byte[] binaryData) {
        OrgCategoryDO param = (OrgCategoryDO)JSONUtil.parseObject((byte[])binaryData, OrgCategoryDO.class);
        R rs = this.orgCatService.add(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object update(@RequestBody byte[] binaryData) {
        OrgCategoryDO param = (OrgCategoryDO)JSONUtil.parseObject((byte[])binaryData, OrgCategoryDO.class);
        R rs = this.orgCatService.update(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object remove(@RequestBody byte[] binaryData) {
        OrgCategoryDO param = (OrgCategoryDO)JSONUtil.parseObject((byte[])binaryData, OrgCategoryDO.class);
        R rs = this.orgCatService.remove(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/move"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object moveCategory(@RequestBody byte[] binaryData) {
        List param = JSONUtil.parseArray((byte[])binaryData, OrgCategoryDO.class);
        R rs = this.orgCatService.moveCategory(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/zb/list"})
    Object binaryListZB(@RequestBody byte[] binaryData) {
        OrgCategoryDO param = (OrgCategoryDO)JSONUtil.parseObject((byte[])binaryData, OrgCategoryDO.class);
        List<ZBDTO> rs = this.orgCatService.listZB(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/zb/add"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object addZB(@RequestBody byte[] binaryData) {
        ZBDTO param = (ZBDTO)((Object)JSONUtil.parseObject((byte[])binaryData, ZBDTO.class));
        R rs = this.orgCatService.addZB(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/zb/update"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object updateZB(@RequestBody byte[] binaryData) {
        ZBDTO param = (ZBDTO)((Object)JSONUtil.parseObject((byte[])binaryData, ZBDTO.class));
        R rs = this.orgCatService.updateZB(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/zb/remove"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object removeZB(@RequestBody byte[] binaryData) {
        List objs = JSONUtil.parseArray((byte[])binaryData, ZBDTO.class);
        R rs = this.orgCatService.removeZB(objs);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/zb/move"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object moveZB(@RequestBody byte[] binaryData) {
        List objs = JSONUtil.parseArray((byte[])binaryData, ZBDTO.class);
        R rs = this.orgCatService.moveZB(objs);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/synchCache"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object synchCache(@RequestBody byte[] binaryData) {
        OrgCategoryDO param = (OrgCategoryDO)JSONUtil.parseObject((byte[])binaryData, OrgCategoryDO.class);
        R rs = this.orgCatService.syncCache(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/language/trans"})
    Object defineTrans(@RequestBody byte[] binaryData) {
        try {
            if (this.orgCategoryTransUtil == null) {
                this.orgCategoryTransUtil = (OrgCategoryTransUtil)ApplicationContextRegister.getBean(OrgCategoryTransUtil.class);
            }
            VaI18nResourceDTO param = (VaI18nResourceDTO)JSONUtil.parseObject((byte[])binaryData, VaI18nResourceDTO.class);
            List<String> rs = this.orgCategoryTransUtil.transResource(param);
            return MonoVO.just((Object)JSONUtil.toBytes(rs));
        }
        catch (Exception e) {
            logger.info("\u83b7\u53d6\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u56fd\u9645\u5316\u8d44\u6e90\u5f02\u5e38", e);
            return MonoVO.just(null);
        }
    }
}

