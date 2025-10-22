/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gc.financialcubes.query.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gc.financialcubes.query.extend.FinancialCubesPenetrateCacheManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FinancialCubesQueryPenetrateWeb {
    @Autowired
    FinancialCubesPenetrateCacheManage financialCubesPenetrateCacheManage;

    @GetMapping(value={"/api/gcreport/v1/financialcubes/penetrate/cache/get/{id}"})
    public BusinessResponseEntity<String> getPenetrateContext(@PathVariable(value="id") String id) {
        return BusinessResponseEntity.ok((Object)this.financialCubesPenetrateCacheManage.getPenetrateContext(id));
    }
}

