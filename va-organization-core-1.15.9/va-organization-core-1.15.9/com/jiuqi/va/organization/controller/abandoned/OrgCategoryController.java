/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.organization.controller.abandoned;

import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.common.OrgCategoryTransUtil;
import com.jiuqi.va.organization.domain.ZBDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/org/category"})
@Deprecated
@ConditionalOnProperty(name={"nvwa.organization.binary.compatible"}, havingValue="true", matchIfMissing=true)
public class OrgCategoryController {
    @Autowired
    private OrgCategoryService orgCatService;

    @PostMapping(value={"/list"})
    Object list(@RequestBody OrgCategoryDO orgCatDTO) {
        return MonoVO.just(this.orgCatService.list(orgCatDTO));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object add(@RequestBody OrgCategoryDO orgCategoryDO) {
        return MonoVO.just((Object)this.orgCatService.add(orgCategoryDO));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object update(@RequestBody OrgCategoryDO orgCategoryDO) {
        return MonoVO.just((Object)this.orgCatService.update(orgCategoryDO));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object remove(@RequestBody OrgCategoryDO orgCategoryDO) {
        return MonoVO.just((Object)this.orgCatService.remove(orgCategoryDO));
    }

    @PostMapping(value={"/move"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object moveCategory(@RequestBody List<OrgCategoryDO> params) {
        return MonoVO.just((Object)this.orgCatService.moveCategory(params));
    }

    @PostMapping(value={"/zb/list"})
    Object listZB(@RequestBody OrgCategoryDO param) {
        return MonoVO.just(this.orgCatService.listZB(param));
    }

    @PostMapping(value={"/zb/add"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object addZB(@RequestBody ZBDTO zbDTO) {
        return MonoVO.just((Object)this.orgCatService.addZB(zbDTO));
    }

    @PostMapping(value={"/zb/update"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object updateZB(@RequestBody ZBDTO param) {
        return MonoVO.just((Object)this.orgCatService.updateZB(param));
    }

    @PostMapping(value={"/zb/remove"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object removeZB(@RequestBody List<ZBDTO> objs) {
        return MonoVO.just((Object)this.orgCatService.removeZB(objs));
    }

    @PostMapping(value={"/zb/move"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object moveZB(@RequestBody List<ZBDTO> objs) {
        return MonoVO.just((Object)this.orgCatService.moveZB(objs));
    }

    @PostMapping(value={"/synchCache"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object synchCache(@RequestBody OrgCategoryDO orgCategoryDO) {
        return MonoVO.just((Object)this.orgCatService.syncCache(orgCategoryDO));
    }

    @PostMapping(value={"/language/trans"})
    Object defineTrans(@RequestBody VaI18nResourceDTO vaDataResourceDTO) {
        try {
            OrgCategoryTransUtil transUtil = (OrgCategoryTransUtil)ApplicationContextRegister.getBean(OrgCategoryTransUtil.class);
            return MonoVO.just(transUtil.transResource(vaDataResourceDTO));
        }
        catch (Exception e) {
            return MonoVO.just(null);
        }
    }
}

