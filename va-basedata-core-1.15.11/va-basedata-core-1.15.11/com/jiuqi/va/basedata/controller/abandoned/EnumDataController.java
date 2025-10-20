/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller.abandoned;

import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.domain.EnumDataSyncCacheDTO;
import com.jiuqi.va.basedata.service.EnumDataService;
import com.jiuqi.va.basedata.service.impl.help.EnumDataCacheService;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Deprecated
@ConditionalOnProperty(name={"nvwa.basedata.binary.compatible"}, havingValue="true", matchIfMissing=true)
@RequestMapping(value={"/enumData"})
public class EnumDataController {
    private static final String VAL = "val";
    private static final String BIZTYPE = "biztype";
    @Autowired
    private EnumDataService enumDataService;
    @Autowired
    private EnumDataCacheService dataCacheService;

    @PostMapping(value={"/list"})
    Object list(@RequestBody EnumDataDTO param) {
        return MonoVO.just(this.enumDataService.list(param));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaBasedata:enumData:mgr"})
    Object add(@RequestBody EnumDataDO enumDataDO) {
        return MonoVO.just((Object)this.enumDataService.add(enumDataDO));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaBasedata:enumData:mgr"})
    Object update(@RequestBody EnumDataDO enumDataDO) {
        return MonoVO.just((Object)this.enumDataService.update(enumDataDO));
    }

    @PostMapping(value={"/stop"})
    @RequiresPermissions(value={"vaBasedata:enumData:mgr"})
    Object stop(@RequestBody List<EnumDataDO> objs) {
        if (objs != null) {
            for (EnumDataDO enumDataDO : objs) {
                if (enumDataDO.getId() == null || enumDataDO.getStatus() == null) continue;
                this.enumDataService.update(enumDataDO);
            }
        }
        return MonoVO.just((Object)R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg()));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaBasedata:enumData:mgr"})
    Object remove(@RequestBody List<EnumDataDTO> objs) {
        if (objs != null && this.enumDataService.remove(objs) > 0) {
            return MonoVO.just((Object)R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg()));
        }
        return MonoVO.just((Object)R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg()));
    }

    @PostMapping(value={"/biztype/list"})
    Object listBiztype(@RequestBody EnumDataDTO param) {
        return MonoVO.just(this.enumDataService.listBiztype(param));
    }

    @GetMapping(value={"/get/{biztype}/{val}"})
    Object getByTypeAndValue(EnumDataDTO param, @PathVariable(value="biztype") String biztype, @PathVariable(value="val") String val) {
        param.setBiztype(biztype);
        param.setVal(val);
        return MonoVO.just((Object)this.enumDataService.get(param));
    }

    @PostMapping(value={"/sync/cache"})
    @RequiresPermissions(value={"vaBasedata:enumData:mgr"})
    Object syncCache(EnumDataDTO param) {
        EnumDataSyncCacheDTO edsc = new EnumDataSyncCacheDTO();
        edsc.setTenantName(ShiroUtil.getTenantName());
        edsc.setForceUpdate(true);
        param.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        edsc.setEnumDataDO((EnumDataDO)param);
        this.dataCacheService.pushSyncMsg(edsc);
        return MonoVO.just((Object)R.ok((String)BaseDataCoreI18nUtil.getMessage("enum.info.cache.sync.waiting", new Object[0])));
    }
}

