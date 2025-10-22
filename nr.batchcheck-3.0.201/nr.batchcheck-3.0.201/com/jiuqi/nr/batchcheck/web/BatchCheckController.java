/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.batchcheck.web;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.batchcheck.bean.CheckFromInfo;
import com.jiuqi.nr.batchcheck.bean.CheckParamImpl;
import com.jiuqi.nr.batchcheck.service.IBatchCheckParamService;
import io.swagger.annotations.Api;
import java.time.Instant;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/BatchCheck/"})
@Api(tags={"\u6279\u91cf\u5ba1\u6838\u4fe1\u606f"})
public class BatchCheckController {
    @Resource
    private IBatchCheckParamService checkParamservice;

    @GetMapping(value={"getFormsInfo"})
    public List<CheckFromInfo> getAllFormInfoBySchemeKey(String formSchemeKey) {
        return this.checkParamservice.getAllFormsByScheme(formSchemeKey);
    }

    @PostMapping(value={"updataBatchCheckParam"})
    public Instant addAndUpdataBatchCheckParam(@RequestBody CheckParamImpl impl) throws Exception {
        return this.checkParamservice.updataBatchCheckParam(impl);
    }

    @GetMapping(value={"getBatchCheckResult"})
    public CheckParamImpl getBatchCheckResult() {
        return this.checkParamservice.getBatchCheckParam();
    }
}

