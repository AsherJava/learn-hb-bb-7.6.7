/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.common.expimp.progress.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/progress"})
public class ProgressController {
    @Autowired
    private ProgressService<ProgressData<Object>, Object> progressService;

    @GetMapping(value={"query/{sn}"})
    public BusinessResponseEntity<ProgressData<Object>> querySnProgress(@PathVariable(value="sn") String sn) {
        ProgressData<Object> progressData = this.progressService.queryProgressData(sn);
        return BusinessResponseEntity.ok(progressData);
    }

    @PostMapping(value={"delete/{sn}"})
    public BusinessResponseEntity<Object> deleteSnProgress(@PathVariable(value="sn") String sn) {
        this.progressService.removeProgressData(sn);
        return BusinessResponseEntity.ok();
    }
}

