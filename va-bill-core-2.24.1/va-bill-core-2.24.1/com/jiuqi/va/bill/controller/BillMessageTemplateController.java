/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.message.template.VaMessageTemplate
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.message.template.VaMessageTemplate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/message/template"})
public class BillMessageTemplateController {
    private static final Logger log = LoggerFactory.getLogger(BillMessageTemplateController.class);
    @Autowired(required=false)
    private List<VaMessageTemplate> templateList;

    @PostMapping(value={"/list"})
    public PageVO<VaMessageTemplate> listTemplate(@RequestBody TenantDO tenantDO) {
        PageVO result = new PageVO(true);
        if (ObjectUtils.isEmpty(tenantDO.getExtInfo("function"))) {
            result.setRs(R.error());
            return result;
        }
        try {
            String funtion = String.valueOf(tenantDO.getExtInfo("function"));
            if (this.templateList == null) {
                result.setRows(Collections.emptyList());
            } else {
                List list = this.templateList.stream().filter(o -> funtion.equals(o.getFunction())).sorted(Comparator.comparing(VaMessageTemplate::getOrder)).collect(Collectors.toList());
                result.setRows(list);
            }
        }
        catch (Exception e) {
            log.error("\u83b7\u53d6\u6d88\u606f\u6a21\u677f\u5217\u8868\u5931\u8d25", e);
            result.setRs(R.error((String)"\u83b7\u53d6\u6d88\u606f\u6a21\u677f\u5217\u8868\u5f02\u5e38"));
        }
        return result;
    }
}

