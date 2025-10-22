/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.common.exportprocess.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.common.exportprocess.dto.ExportProcess;
import com.jiuqi.gcreport.common.exportprocess.service.GcExportProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/export"})
public class ExportProcessController {
    @Autowired
    private GcExportProcessService gcExportProcessService;

    @PostMapping(value={"/progress/{sn}"})
    BusinessResponseEntity<ExportProcess> querySnExportProgress(@PathVariable(value="sn") String sn) {
        ExportProcess exportProcess = this.gcExportProcessService.getProcess(sn);
        return BusinessResponseEntity.ok((Object)exportProcess);
    }
}

