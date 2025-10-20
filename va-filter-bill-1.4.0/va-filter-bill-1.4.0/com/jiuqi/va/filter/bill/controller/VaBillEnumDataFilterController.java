/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.filter.bill.controller;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.filter.bill.formula.EnumDataFormulaContext;
import com.jiuqi.va.filter.bill.formula.EnumDataFormulaHandle;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/enumData"})
public class VaBillEnumDataFilterController {
    private static final String LANGUAGE_TRANS_FLAG = "languageTransFlag";
    private static final Logger logger = LoggerFactory.getLogger(VaBillEnumDataFilterController.class);
    @Autowired
    private EnumDataClient enumDataClient;

    @PostMapping(value={"/filter"})
    public List<EnumDataDO> list(@RequestBody Map<String, Object> param) {
        EnumDataDTO enumDataDTO = new EnumDataDTO();
        enumDataDTO.setBiztype((String)param.get("biztype"));
        enumDataDTO.setExtInfo((Map)param.get("extInfo"));
        enumDataDTO.addExtInfo(LANGUAGE_TRANS_FLAG, (Object)true);
        List list = this.enumDataClient.list(enumDataDTO);
        String expression = (String)param.get("expression");
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            EnumDataDO obj = (EnumDataDO)iter.next();
            if (obj.getStatus() != 1) continue;
            iter.remove();
        }
        if (expression == null) {
            return list;
        }
        EnumDataFormulaHandle enumDataFormulaHandle = new EnumDataFormulaHandle();
        for (int i = list.size() - 1; i >= 0; --i) {
            EnumDataFormulaContext context = new EnumDataFormulaContext();
            context.setTitle(((EnumDataDO)list.get(i)).getTitle());
            context.setVal(((EnumDataDO)list.get(i)).getVal());
            try {
                if (((Boolean)enumDataFormulaHandle.parse(context, expression).evaluate((IContext)context)).booleanValue()) continue;
                list.remove(i);
                continue;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return list;
    }
}

