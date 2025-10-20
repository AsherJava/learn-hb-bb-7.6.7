/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller;

import com.jiuqi.va.basedata.domain.BaseDataVersionDO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDTO;
import com.jiuqi.va.basedata.service.BaseDataVersionService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaBaseDataVersionController")
@RequestMapping(value={"/baseData/version/binary"})
public class BaseDataVersionController {
    @Autowired
    private BaseDataVersionService baseDataVersionService;

    @PostMapping(value={"/get"})
    Object get(@RequestBody byte[] binaryData) {
        BaseDataVersionDTO param = (BaseDataVersionDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataVersionDTO.class);
        BaseDataVersionDO rs = this.baseDataVersionService.get(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody byte[] binaryData) {
        BaseDataVersionDTO param = (BaseDataVersionDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataVersionDTO.class);
        PageVO<BaseDataVersionDO> rs = this.baseDataVersionService.list(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object add(@RequestBody byte[] binaryData) {
        BaseDataVersionDO param = (BaseDataVersionDO)JSONUtil.parseObject((byte[])binaryData, BaseDataVersionDO.class);
        R rs = this.baseDataVersionService.add(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/split"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object split(@RequestBody byte[] binaryData) {
        BaseDataVersionDO param = (BaseDataVersionDO)JSONUtil.parseObject((byte[])binaryData, BaseDataVersionDO.class);
        R rs = this.baseDataVersionService.split(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object update(@RequestBody byte[] binaryData) {
        BaseDataVersionDO param = (BaseDataVersionDO)JSONUtil.parseObject((byte[])binaryData, BaseDataVersionDO.class);
        R rs = this.baseDataVersionService.update(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object remove(@RequestBody byte[] binaryData) {
        BaseDataVersionDO param = (BaseDataVersionDO)JSONUtil.parseObject((byte[])binaryData, BaseDataVersionDO.class);
        R rs = this.baseDataVersionService.remove(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/status/change"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object changeStatus(@RequestBody byte[] binaryData) {
        List dataList = JSONUtil.parseArray((byte[])binaryData, BaseDataVersionDO.class);
        R rs = this.baseDataVersionService.changeStatus(dataList);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/cache/sync"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object syncCache(@RequestBody byte[] binaryData) {
        BaseDataVersionDO param = (BaseDataVersionDO)JSONUtil.parseObject((byte[])binaryData, BaseDataVersionDO.class);
        R rs = this.baseDataVersionService.syncCache(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }
}

