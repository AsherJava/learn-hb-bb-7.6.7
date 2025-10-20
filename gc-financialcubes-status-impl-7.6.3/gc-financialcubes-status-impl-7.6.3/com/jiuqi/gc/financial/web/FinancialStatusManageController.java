/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gc.financial.financialstatus.api.FinancialStatusManageClient
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusManageConfigVo
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusManageVo
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gc.financial.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gc.financial.financialstatus.api.FinancialStatusManageClient;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusManageConfigVo;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusManageVo;
import com.jiuqi.gc.financial.service.FinancialStatusManageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FinancialStatusManageController
implements FinancialStatusManageClient {
    @Autowired
    private FinancialStatusManageService service;

    public BusinessResponseEntity<List<FinancialStatusManageVo>> listAllPlugin() {
        return BusinessResponseEntity.ok(this.service.listAllPlugin());
    }

    public BusinessResponseEntity<List<FinancialStatusManageConfigVo>> listPluginConfig() {
        return BusinessResponseEntity.ok(this.service.listPluginConfig());
    }
}

