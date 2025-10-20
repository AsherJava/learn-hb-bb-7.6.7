/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller;

import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.domain.EnumDataSyncCacheDTO;
import com.jiuqi.va.basedata.service.EnumDataService;
import com.jiuqi.va.basedata.service.impl.help.EnumDataCacheService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaEnumDataController")
@RequestMapping(value={"/enumData/binary"})
public class EnumDataController {
    @Autowired
    private EnumDataService enumDataService;
    @Autowired
    private EnumDataCacheService dataCacheService;

    @PostMapping(value={"/get"})
    Object get(@RequestBody byte[] binaryData) {
        EnumDataDTO param = (EnumDataDTO)JSONUtil.parseObject((byte[])binaryData, EnumDataDTO.class);
        param.setDeepClone(Boolean.valueOf(false));
        EnumDataDO rs = this.enumDataService.get(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody byte[] binaryData) {
        EnumDataDTO param = (EnumDataDTO)JSONUtil.parseObject((byte[])binaryData, EnumDataDTO.class);
        param.setDeepClone(Boolean.valueOf(false));
        List<EnumDataDO> rs = this.enumDataService.list(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaBasedata:enumData:mgr"})
    Object add(@RequestBody byte[] binaryData) {
        EnumDataDO param = (EnumDataDO)JSONUtil.parseObject((byte[])binaryData, EnumDataDO.class);
        R rs = this.enumDataService.add(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaBasedata:enumData:mgr"})
    Object update(@RequestBody byte[] binaryData) {
        EnumDataDO param = (EnumDataDO)JSONUtil.parseObject((byte[])binaryData, EnumDataDO.class);
        R rs = this.enumDataService.update(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/stop"})
    @RequiresPermissions(value={"vaBasedata:enumData:mgr"})
    Object stop(@RequestBody byte[] binaryData) {
        List objs = JSONUtil.parseArray((byte[])binaryData, EnumDataDO.class);
        if (objs != null) {
            for (EnumDataDO enumDataDO : objs) {
                if (enumDataDO.getId() == null || enumDataDO.getStatus() == null) continue;
                this.enumDataService.update(enumDataDO);
            }
        }
        R rs = R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaBasedata:enumData:mgr"})
    Object remove(@RequestBody byte[] binaryData) {
        List objs = JSONUtil.parseArray((byte[])binaryData, EnumDataDTO.class);
        R rs = null;
        rs = objs != null && this.enumDataService.remove(objs) > 0 ? R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg()) : R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/biztype/list"})
    Object listBiztype(@RequestBody byte[] binaryData) {
        EnumDataDTO param = (EnumDataDTO)JSONUtil.parseObject((byte[])binaryData, EnumDataDTO.class);
        param.setDeepClone(Boolean.valueOf(false));
        List<EnumDataDO> rs = this.enumDataService.listBiztype(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
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

