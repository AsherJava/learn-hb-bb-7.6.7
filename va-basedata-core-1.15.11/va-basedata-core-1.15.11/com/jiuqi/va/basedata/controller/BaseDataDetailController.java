/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDetailDO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.R
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller;

import com.jiuqi.va.basedata.service.BaseDataDetailService;
import com.jiuqi.va.domain.basedata.BaseDataDetailDO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaBaseDataDetailController")
@RequestMapping(value={"/baseData/detail/binary"})
public class BaseDataDetailController {
    @Autowired
    private BaseDataDetailService baseDataDetailService;

    @PostMapping(value={"/get"})
    Object get(@RequestBody byte[] binaryData) {
        BaseDataDetailDO param = (BaseDataDetailDO)JSONUtil.parseObject((byte[])binaryData, BaseDataDetailDO.class);
        List<BaseDataDetailDO> rs = this.baseDataDetailService.get(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody byte[] binaryData) {
        BaseDataDetailDO param = (BaseDataDetailDO)JSONUtil.parseObject((byte[])binaryData, BaseDataDetailDO.class);
        List<BaseDataDetailDO> rs = this.baseDataDetailService.list(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object add(@RequestBody byte[] binaryData) {
        BaseDataDetailDO param = (BaseDataDetailDO)JSONUtil.parseObject((byte[])binaryData, BaseDataDetailDO.class);
        R rs = this.baseDataDetailService.add(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/delete"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object delete(@RequestBody byte[] binaryData) {
        BaseDataDetailDO param = (BaseDataDetailDO)JSONUtil.parseObject((byte[])binaryData, BaseDataDetailDO.class);
        R rs = this.baseDataDetailService.delete(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }
}

