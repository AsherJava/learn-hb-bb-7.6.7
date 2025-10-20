/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.feign.client.BillVerifyClient
 *  com.jiuqi.va.query.template.vo.TemplateRelateQueryVO
 */
package com.jiuqi.va.query.sql.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.feign.client.BillVerifyClient;
import com.jiuqi.va.query.sql.service.VaQueryRelateInterceptHelper;
import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import com.jiuqi.va.query.util.QueryUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class VaQueryRelateInterceptHelperImpl
implements VaQueryRelateInterceptHelper {
    @Autowired
    private BillVerifyClient billVerifyClient;

    @Override
    public Map<String, Object> dealBillVerifyCode(String processorName, Map<String, Object> jsonObjects, TemplateRelateQueryVO templateRelateQueryVO) {
        String billCode;
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        String triggerField = templateRelateQueryVO.getTriggerField();
        String billCodeKey = "";
        String queryParam = templateRelateQueryVO.getQueryParam();
        ArrayNode jsonNodes = JSONUtil.parseArray((String)queryParam);
        for (JsonNode jsonNode : jsonNodes) {
            String text = jsonNode.get("name").asText();
            if (!"billcode".equalsIgnoreCase(text)) continue;
            billCodeKey = jsonNode.get("value").asText();
            break;
        }
        if (!StringUtils.hasText(billCodeKey)) {
            billCodeKey = triggerField;
        }
        if (!StringUtils.hasText(billCode = (String)jsonObjects.getOrDefault(billCodeKey, ""))) {
            return resultMap;
        }
        BillVerifyDTO billVerifyDTO = new BillVerifyDTO();
        billVerifyDTO.setBillCode(billCode);
        billVerifyDTO.setAuth(1);
        String userName = ShiroUtil.getUser().getUsername();
        if ("SSORelate".equalsIgnoreCase(processorName)) {
            ArrayList<String> list = new ArrayList<String>();
            list.add(userName);
            billVerifyDTO.setUserIds(list);
        }
        R r = this.billVerifyClient.encodeBillCode(billVerifyDTO);
        String verifyCode = "";
        if (r.get((Object)"data") != null) {
            if ("SSORelate".equalsIgnoreCase(processorName)) {
                Map map = QueryUtils.getMap(r.get((Object)"data"));
                verifyCode = (String)map.get(userName);
            } else {
                verifyCode = String.valueOf(r.get((Object)"data"));
            }
        }
        resultMap.put(triggerField + "_verifyCode", verifyCode);
        return resultMap;
    }
}

