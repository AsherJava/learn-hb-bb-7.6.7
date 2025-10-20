/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.bill.dao.SublistImportTemplateDAO;
import com.jiuqi.va.bill.domain.SublistImportTemplateDTO;
import com.jiuqi.va.biz.utils.R;
import com.jiuqi.va.domain.common.OrderNumUtil;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RequestMapping(value={"/bill/sublist/import"})
@RestController
public class SublistImportTemplateController {
    @Autowired
    SublistImportTemplateDAO sublistTemplateDAO;

    @PostMapping(value={"/template/query"})
    public R<String> getTemplate(@RequestBody SublistImportTemplateDTO params) {
        List<SublistImportTemplateDTO> selectSublistTemplate = this.sublistTemplateDAO.selectSublistTemplate(params);
        if (selectSublistTemplate.size() == 0) {
            return R.ok();
        }
        String templateData = selectSublistTemplate.get(0).getTemplateData();
        return R.ok((Object)templateData);
    }

    @PostMapping(value={"/template/edit"})
    public R<String> editTemplate(@RequestBody SublistImportTemplateDTO params) {
        List<SublistImportTemplateDTO> selectSublistTemplate = this.sublistTemplateDAO.selectSublistTemplate(params);
        int flag = 0;
        if (selectSublistTemplate.size() == 0) {
            params.setID(UUID.randomUUID().toString());
            params.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            flag = this.sublistTemplateDAO.insertSublistTemplate(params);
        } else {
            flag = this.sublistTemplateDAO.updateSublistTemplate(params);
        }
        if (flag == 0) {
            return R.error((String)"\u5355\u636e\u5b50\u8868\u5bfc\u5165\u6a21\u677f\u4fdd\u5b58\u5931\u8d25");
        }
        return R.ok((Object)"\u5355\u636e\u5b50\u8868\u5bfc\u5165\u6a21\u677f\u4fdd\u5b58\u6210\u529f");
    }
}

