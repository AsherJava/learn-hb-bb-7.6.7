/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.feign.util.LogUtil
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller.abandoned;

import com.jiuqi.va.basedata.service.BaseDataGroupService;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
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
@RequestMapping(value={"/baseData/defineGroup"})
public class BaseDataGroupController {
    @Autowired
    private BaseDataGroupService baseDataService;

    @PostMapping(value={"/exist"})
    Object exist(@RequestBody BaseDataGroupDTO baseDataDTO) {
        return MonoVO.just((Object)this.baseDataService.exist(baseDataDTO));
    }

    @PostMapping(value={"/get"})
    Object get(@RequestBody BaseDataGroupDTO param) {
        return MonoVO.just((Object)this.baseDataService.get(param));
    }

    @PostMapping(value={"list"})
    Object list(@RequestBody BaseDataGroupDTO param) {
        return MonoVO.just(this.baseDataService.list(param));
    }

    @PostMapping(value={"/tree"})
    Object tree(@RequestBody BaseDataGroupDTO param) {
        return MonoVO.just(this.baseDataService.tree(param));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object add(@RequestBody BaseDataGroupDTO baseDataDTO) {
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4", (String)"\u65b0\u589e", (String)"", (String)baseDataDTO.getName(), (String)baseDataDTO.getTitle());
        return MonoVO.just((Object)this.baseDataService.add(baseDataDTO));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object update(@RequestBody BaseDataGroupDTO baseDataDTO) {
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4", (String)"\u66f4\u65b0", (String)"", (String)baseDataDTO.getName(), (String)baseDataDTO.getTitle());
        return MonoVO.just((Object)this.baseDataService.update(baseDataDTO));
    }

    @PostMapping(value={"/delete"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object removeOne(@RequestBody BaseDataGroupDTO basedata) {
        if (basedata.getName() == null) {
            throw new RuntimeException("\u5220\u9664\u9879\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4", (String)"\u5220\u9664", (String)"", (String)basedata.getName(), (String)"");
        return MonoVO.just((Object)this.baseDataService.delete(basedata));
    }
}

