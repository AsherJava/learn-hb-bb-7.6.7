/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.TreeVO
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller;

import com.jiuqi.va.basedata.service.BaseDataGroupService;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.TreeVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaBaseDataGroupController")
@RequestMapping(value={"/baseData/defineGroup/binary"})
public class BaseDataGroupController {
    @Autowired
    private BaseDataGroupService baseDataService;

    @PostMapping(value={"/exist"})
    Object exist(@RequestBody byte[] binaryData) {
        BaseDataGroupDTO param = (BaseDataGroupDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataGroupDTO.class);
        R rs = this.baseDataService.exist(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/get"})
    Object get(@RequestBody byte[] binaryData) {
        BaseDataGroupDTO param = (BaseDataGroupDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataGroupDTO.class);
        BaseDataGroupDO rs = this.baseDataService.get(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"list"})
    Object list(@RequestBody byte[] binaryData) {
        BaseDataGroupDTO param = (BaseDataGroupDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataGroupDTO.class);
        PageVO<BaseDataGroupDO> rs = this.baseDataService.list(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/tree"})
    Object tree(@RequestBody byte[] binaryData) {
        BaseDataGroupDTO param = (BaseDataGroupDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataGroupDTO.class);
        PageVO<TreeVO<BaseDataGroupDO>> rs = this.baseDataService.tree(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object add(@RequestBody byte[] binaryData) {
        BaseDataGroupDTO param = (BaseDataGroupDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataGroupDTO.class);
        R rs = this.baseDataService.add(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object update(@RequestBody byte[] binaryData) {
        BaseDataGroupDTO param = (BaseDataGroupDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataGroupDTO.class);
        R rs = this.baseDataService.update(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/delete"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object remove(@RequestBody byte[] binaryData) {
        BaseDataGroupDTO param = (BaseDataGroupDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataGroupDTO.class);
        R rs = this.baseDataService.delete(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }
}

