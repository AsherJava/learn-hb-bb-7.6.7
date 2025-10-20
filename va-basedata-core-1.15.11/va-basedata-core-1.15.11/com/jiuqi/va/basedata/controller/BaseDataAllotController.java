/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.handle.BaseDataAllotDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.util.LogUtil
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller;

import com.jiuqi.va.basedata.service.impl.help.BaseDataAllotService;
import com.jiuqi.va.domain.basedata.handle.BaseDataAllotDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.util.LogUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaBaseDataAllotController")
@RequestMapping(value={"/baseData/allot/binary"})
public class BaseDataAllotController {
    @Autowired
    private BaseDataAllotService baseDataAllotService;

    @PostMapping(value={"/execute"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object allot(@RequestBody byte[] binaryData) {
        BaseDataAllotDTO param = (BaseDataAllotDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataAllotDTO.class);
        R rs = this.baseDataAllotService.allot(param);
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u5206\u914d", (String)param.getDefineCode(), (String)"", (String)"");
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/result"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object result(@RequestBody byte[] binaryData) {
        BaseDataAllotDTO param = (BaseDataAllotDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataAllotDTO.class);
        R rs = this.baseDataAllotService.getAllotResult(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }
}

