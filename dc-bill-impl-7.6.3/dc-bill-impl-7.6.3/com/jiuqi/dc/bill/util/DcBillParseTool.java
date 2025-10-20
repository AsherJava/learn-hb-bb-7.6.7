/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO
 *  com.jiuqi.va.bizmeta.service.impl.MetaDataService
 *  com.jiuqi.va.bizmeta.service.impl.MetaInfoService
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 */
package com.jiuqi.dc.bill.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.dc.bill.vo.BillInfoVo;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.service.impl.MetaDataService;
import com.jiuqi.va.bizmeta.service.impl.MetaInfoService;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import java.util.LinkedHashSet;

public class DcBillParseTool {
    public static BillInfoVo parseBillInfo(String defineCode) {
        MetaInfoService metaInfoService = (MetaInfoService)SpringContextUtils.getBean(MetaInfoService.class);
        MetaDataService metaDataService = (MetaDataService)SpringContextUtils.getBean(MetaDataService.class);
        MetaInfoDTO infoDTO = metaInfoService.getMetaInfoByUniqueCode(defineCode);
        BillInfoVo billInfoVo = new BillInfoVo();
        if (infoDTO != null) {
            MetaDataDTO dataDTO = metaDataService.getMetaDataVById(infoDTO.getId());
            JsonNode designData = JsonUtils.readTree((String)dataDTO.getDesignData());
            JsonNode pluginsData = designData.get("plugins");
            for (int i = 0; i < pluginsData.size(); ++i) {
                JsonNode typeDatas = pluginsData.get(i);
                if ("data".equals(typeDatas.get("type").asText())) {
                    JsonNode tablesData = typeDatas.get("tables");
                    for (int j = 0; j < tablesData.size(); ++j) {
                        JsonNode tableData = tablesData.get(j);
                        if (null == tableData.get("parentId")) {
                            billInfoVo.setMasterTableName(tableData.get("name").asText());
                            continue;
                        }
                        billInfoVo.addSubTableName(tableData.get("name").asText());
                    }
                    continue;
                }
                if (!"view".equals(typeDatas.get("type").asText())) continue;
                JsonNode templateData = typeDatas.get("template");
                JsonNode childrenNode = templateData.get("children");
                DcBillParseTool.findBindingNode(childrenNode, billInfoVo);
            }
        }
        return billInfoVo;
    }

    private static void findBindingNode(JsonNode childrenNodes, BillInfoVo billInfoVo) {
        if (null == childrenNodes) {
            return;
        }
        for (int i = 0; i < childrenNodes.size(); ++i) {
            JsonNode childrenNode = childrenNodes.get(i);
            if ("v-panel".equals(childrenNode.get("type")) || "v-collapse".equals(childrenNode.get("type"))) {
                billInfoVo.setLastParsePanelTitleTemp(childrenNode.get("title").asText());
            }
            if (null != childrenNode.get("binding")) {
                DcBillParseTool.parseColumnCode(childrenNodes, billInfoVo);
                break;
            }
            JsonNode newChildrenNodes = childrenNode.get("children");
            DcBillParseTool.findBindingNode(newChildrenNodes, billInfoVo);
        }
    }

    private static void parseColumnCode(JsonNode childrenNodes, BillInfoVo billInfoVo) {
        LinkedHashSet<String> masterColumnCodeSet = new LinkedHashSet<String>(16);
        LinkedHashSet<String> subColumnCodeSet = new LinkedHashSet<String>(16);
        for (int i = 0; i < childrenNodes.size(); ++i) {
            JsonNode childrenNode = childrenNodes.get(i);
            JsonNode binding = childrenNode.get("binding");
            if (null == binding) continue;
            if (null != binding.get("fieldName")) {
                masterColumnCodeSet.add(binding.get("fieldName").asText());
                continue;
            }
            JsonNode fields = binding.get("fields");
            if (null == fields) continue;
            for (int j = 0; j < fields.size(); ++j) {
                subColumnCodeSet.add(fields.get(j).get("name").asText());
            }
        }
        if (!masterColumnCodeSet.isEmpty()) {
            if (billInfoVo.getMasterColumnCodes() == null) {
                billInfoVo.setMasterColumnCodes(new LinkedHashSet<String>(16));
            }
            billInfoVo.getMasterColumnCodes().addAll(masterColumnCodeSet);
        }
        if (!subColumnCodeSet.isEmpty()) {
            billInfoVo.addSubPanelTitles(billInfoVo.getLastParsePanelTitleTemp());
            billInfoVo.addSubColumnCodes(subColumnCodeSet);
        }
    }
}

