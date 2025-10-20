/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.invest.investbill.bill.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class InvestMetaDesignSpecialUpdateTask {
    private Logger logger = LoggerFactory.getLogger(InvestMetaDesignSpecialUpdateTask.class);

    public void merge(JsonNode pluginsFromDB) throws IOException {
        JsonNode pluginsFromFile = this.loadFromFile("bill/bill_design_special_upgrade.json");
        for (int i = 0; i < pluginsFromFile.size(); ++i) {
            JsonNode pluginFromFile = pluginsFromFile.get(i);
            this.merge(pluginFromFile, pluginsFromDB);
        }
    }

    private void merge(JsonNode pluginFromFile, JsonNode pluginsFromDBResult) {
        String type = this.asText(pluginFromFile.get("type"));
        for (int i = 0; i < pluginsFromDBResult.size(); ++i) {
            String action;
            JsonNode pluginFromDBResult = pluginsFromDBResult.get(i);
            if (!type.equals(this.asText(pluginFromDBResult.get("type")))) continue;
            this.logger.info("\u5339\u914d\u7c7b\u578b\uff1a" + type);
            String posStr = this.asText(pluginFromFile.get("pos"));
            JsonNode destNodeFromDB = this.findNodeByPos(posStr, pluginFromDBResult);
            if (null == destNodeFromDB) continue;
            String matchBy = this.asText(pluginFromFile.get("matchBy"));
            ObjectNode jsonFromFile = (ObjectNode)pluginFromFile.get("json");
            if (!StringUtils.isEmpty((String)matchBy)) {
                destNodeFromDB = this.findByMatchBy(matchBy, (JsonNode)jsonFromFile, destNodeFromDB);
            }
            if (null == destNodeFromDB || !"merge".equals(action = this.asText(pluginFromFile.get("action")))) continue;
            ObjectNode jsonFromDb = (ObjectNode)destNodeFromDB;
            jsonFromDb.setAll(jsonFromFile);
        }
    }

    private JsonNode findByMatchBy(String matchBy, JsonNode pluginFromFile, JsonNode destNodeFromDB) {
        String matchVal = this.asText(pluginFromFile.get(matchBy));
        if (StringUtils.isEmpty((String)matchVal)) {
            this.logger.warn("\u8282\u70b9\u5f02\u5e38matchVal\uff1a" + pluginFromFile);
        }
        for (int j = 0; j < destNodeFromDB.size(); ++j) {
            JsonNode node = destNodeFromDB.get(j);
            if (!matchVal.equals(this.asText(node.get(matchBy)))) continue;
            return node;
        }
        this.logger.info("\u5339\u914d\u5931\u8d25findByMatchBy\uff1a" + destNodeFromDB);
        return null;
    }

    private JsonNode findNodeByPos(String posStr, JsonNode pluginFromDBResult) {
        String[] posArr = posStr.split(",");
        JsonNode lastDestNode = pluginFromDBResult;
        JsonNode destNode = null;
        for (int j = 0; j < posArr.length; ++j) {
            String pos = posArr[j];
            destNode = lastDestNode.get(pos);
            if (null == destNode && pos.matches("[0-9]+")) {
                destNode = lastDestNode.get(Integer.parseInt(pos));
            }
            if (null == destNode) {
                this.logger.info("\u672a\u627e\u5230\u5339\u914d\u8282\u70b9,\u7d22\u5f15-index:" + j + ",pos:" + pos + "\u3002" + lastDestNode.toString());
                break;
            }
            lastDestNode = destNode;
        }
        return destNode;
    }

    private String asText(JsonNode jsonNode) {
        return null == jsonNode ? null : jsonNode.asText();
    }

    private JsonNode loadFromFile(String path) throws IOException {
        String billDesignStr = this.parseJson(path);
        return JsonUtils.readTree((String)billDesignStr);
    }

    private String parseJson(String path) throws IOException {
        ClassPathResource templateResource = new ClassPathResource(path);
        InputStream inputStream = templateResource.getInputStream();
        return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
    }
}

