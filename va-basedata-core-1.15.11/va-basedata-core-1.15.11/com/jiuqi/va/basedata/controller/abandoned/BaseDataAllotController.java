/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.handle.BaseDataAllotDTO
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.feign.util.LogUtil
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller.abandoned;

import com.jiuqi.va.basedata.service.impl.help.BaseDataAllotService;
import com.jiuqi.va.domain.basedata.handle.BaseDataAllotDTO;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.feign.util.LogUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Deprecated
@ConditionalOnProperty(name={"nvwa.basedata.binary.compatible"}, havingValue="true", matchIfMissing=true)
@RequestMapping(value={"/baseData/allot"})
public class BaseDataAllotController {
    @Autowired
    private BaseDataAllotService baseDataAllotService;

    @PostMapping(value={"/execute"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object allot(@RequestBody BaseDataAllotDTO baseDataAllotDTO) {
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u5206\u914d", (String)baseDataAllotDTO.getDefineCode(), (String)"", (String)"");
        return MonoVO.just((Object)this.baseDataAllotService.allot(baseDataAllotDTO));
    }

    @PostMapping(value={"/result"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object result(@RequestBody BaseDataAllotDTO baseDataAllotDTO) {
        return MonoVO.just((Object)this.baseDataAllotService.getAllotResult(baseDataAllotDTO));
    }
}

