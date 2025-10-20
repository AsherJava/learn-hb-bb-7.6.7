/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.paramsync.common;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.domain.TenantDO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VaParamDefaultController {
    @PostMapping(value={"/paramtransfer/{path1}/{path2}"})
    R registParamTransferModule(@RequestBody TenantDO tenantDO, @PathVariable(value="path1") String path1, @PathVariable(value="path2") String path2) {
        return R.error((String)"\u672a\u96c6\u6210\u53c2\u6570\u540c\u6b65\u6838\u5fc3\u7ec4\u4ef6");
    }
}

