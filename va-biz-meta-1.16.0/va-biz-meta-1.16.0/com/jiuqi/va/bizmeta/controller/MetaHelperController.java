/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bizmeta.controller;

import com.jiuqi.va.bizmeta.domain.helper.BizViewTemplateDO;
import com.jiuqi.va.bizmeta.service.MetaHelperService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/biz"})
public class MetaHelperController {
    @Autowired
    private MetaHelperService metaHelperService;

    @PostMapping(value={"/template/save"})
    public R saveBillTemplate(@RequestBody BizViewTemplateDO bizViewTemplateDO) {
        return this.metaHelperService.saveBillTemplate(bizViewTemplateDO);
    }

    @GetMapping(value={"/template/list"})
    public R listBillTemplate(@RequestParam(value="searchkey") String searchKey, @RequestParam(value="bizType", required=false) String bizType) {
        return this.metaHelperService.listBillTemplate(searchKey, bizType);
    }

    @PostMapping(value={"/template/delete"})
    public R deleteBillTemplate(@RequestBody BizViewTemplateDO bizViewTemplateDO) {
        return this.metaHelperService.deleteBillTemplate(bizViewTemplateDO);
    }

    @PostMapping(value={"/metahelper/list/workflowversionitem"})
    public List<VaI18nResourceItem> listWorkFlowVersionItemResourceList(@RequestBody TenantDO tenant) {
        return this.metaHelperService.listWorkFlowVersionItemResourceList(tenant);
    }

    @PostMapping(value={"/metahelper/list/workflowversion"})
    public List<VaI18nResourceItem> listWorkFlowVersionResourceList(@RequestBody TenantDO tenant) {
        return this.metaHelperService.listWorkFlowVersionResourceList(tenant);
    }
}

