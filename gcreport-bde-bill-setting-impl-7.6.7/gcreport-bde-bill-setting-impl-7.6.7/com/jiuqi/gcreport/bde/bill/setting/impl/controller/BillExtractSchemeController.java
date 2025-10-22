/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.bill.setting.client.BillExtractSchemeClient
 *  com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.bill.setting.client.BillExtractSchemeClient;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillExtractSchemeService;
import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillExtractSchemeController
implements BillExtractSchemeClient {
    @Autowired
    private BillExtractSchemeService service;

    @GetMapping(value={"/api/gcreport/v1/fetch/scheme/list"})
    public BusinessResponseEntity<List<BillFetchSchemeDTO>> listScheme(@RequestParam(name="billType", required=true) String billType) {
        return BusinessResponseEntity.ok(this.service.listScheme(billType));
    }

    @PostMapping(value={"/api/gcreport/v1/fetch/scheme/save"})
    public BusinessResponseEntity<Boolean> save(@RequestBody BillFetchSchemeDTO scheme) {
        return BusinessResponseEntity.ok((Object)this.service.save(scheme));
    }

    @PostMapping(value={"/api/gcreport/v1/fetch/scheme/update"})
    public BusinessResponseEntity<Boolean> update(@RequestBody BillFetchSchemeDTO scheme) {
        return BusinessResponseEntity.ok((Object)this.service.update(scheme));
    }

    @GetMapping(value={"/api/gcreport/v1/fetch/scheme/delete"})
    public BusinessResponseEntity<Boolean> delete(@RequestParam(name="schemeId", required=true) String schemeId) {
        return BusinessResponseEntity.ok((Object)this.service.delete(schemeId));
    }

    @GetMapping(value={"/api/gcreport/v1/fetch/scheme/publish"})
    public BusinessResponseEntity<Boolean> publish(@RequestParam(name="schemeId", required=true) String schemeId) {
        return BusinessResponseEntity.ok((Object)this.service.publish(schemeId));
    }

    @GetMapping(value={"/api/gcreport/v1/fetch/scheme/copy"})
    public BusinessResponseEntity<Boolean> copy(@RequestParam(name="schemeId", required=true) String schemeId, @RequestParam(name="newSchemeName", required=true) String newSchemeName) {
        return BusinessResponseEntity.ok((Object)this.service.copy(schemeId, newSchemeName));
    }

    @GetMapping(value={"/api/gcreport/v1/fetch/scheme/exchangeOrdinal"})
    public BusinessResponseEntity<Boolean> exchangeOrdinal(@RequestParam(name="srcSchemeId", required=true) String srcSchemeId, @RequestParam(name="targetSchemeId", required=true) String targetSchemeId) {
        return BusinessResponseEntity.ok((Object)this.service.exchangeOrdinal(srcSchemeId, targetSchemeId));
    }
}

