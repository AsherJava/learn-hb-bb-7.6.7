/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.nvwa.jobmanager.api.vo.JobLogDetailVO
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.common.plantask.extend.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.plantask.extend.service.PlanTaskSettingPageService;
import com.jiuqi.nvwa.jobmanager.api.vo.JobLogDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class PlanTaskSettingPageColltroller {
    private static final String BASE_API = "/api/v2/plantask/settingpage/";
    @Autowired
    private PlanTaskSettingPageService planTaskSettingPageService;

    @GetMapping(value={"/api/v2/plantask/settingpage//{code}"})
    public BusinessResponseEntity<Object> getTemplateByCode(@PathVariable(value="code") String code) {
        return BusinessResponseEntity.ok((Object)this.planTaskSettingPageService.getTemplateByCode(code));
    }

    @GetMapping(value={"/api/v2/plantask/log/query/detail/temp/{id}"})
    JobLogDetailVO queryPlanTaskLogDetail(@PathVariable String id) {
        return this.planTaskSettingPageService.queryPlanTaskLogDetailById(id);
    }
}

