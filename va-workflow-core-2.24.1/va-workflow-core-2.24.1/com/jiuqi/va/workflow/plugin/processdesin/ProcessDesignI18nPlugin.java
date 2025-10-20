/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.biz.intf.model.I18nPlugin
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 */
package com.jiuqi.va.workflow.plugin.processdesin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.biz.intf.model.I18nPlugin;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProcessDesignI18nPlugin
implements I18nPlugin {
    public static final String CHILD_SHAPES = "childShapes";
    public static final String PROPERTIES = "properties";
    public static final String NAME = "name";

    public String getTitle() {
        return "\u6d41\u7a0b\u8bbe\u8ba1\u5668";
    }

    public String getName() {
        return "processDesignPlugin";
    }

    public Class<? extends PluginDefine> getPluginDefine() {
        return ProcessDesignPluginDefine.class;
    }

    public List<VaI18nResourceItem> getI18nResource(PluginDefine pluginDefine, ModelDefine modelDefine) {
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)pluginDefine;
        ArrayNode arrayNode = (ArrayNode)processDesignPluginDefine.getData().get(CHILD_SHAPES);
        ArrayList<VaI18nResourceItem> list = new ArrayList<VaI18nResourceItem>();
        String nullStr = "\"\"";
        for (JsonNode jsonNode : arrayNode) {
            String nodeTitle;
            JsonNode node = jsonNode.get(PROPERTIES).get(NAME);
            JsonNode resourceIdNode = jsonNode.get("resourceId");
            if (nullStr.equals(String.valueOf(node)) || "null".equals(String.valueOf(node)) || VaWorkFlowI18nUtils.DICT_MAP.containsKey(nodeTitle = node.asText())) continue;
            VaI18nResourceItem vaI18nResourceItem = new VaI18nResourceItem();
            vaI18nResourceItem.setTitle(nodeTitle);
            vaI18nResourceItem.setName(resourceIdNode.asText());
            list.add(vaI18nResourceItem);
            JsonNode stencil = jsonNode.get("stencil");
            if (!"SubProcess".equals(stencil.get("id").asText())) continue;
            ArrayNode childArrayNode = (ArrayNode)jsonNode.get(CHILD_SHAPES);
            for (JsonNode child : childArrayNode) {
                String childNodeTitle;
                JsonNode childName = child.get(PROPERTIES).get(NAME);
                JsonNode childResourceId = child.get("resourceId");
                if (nullStr.equals(String.valueOf(childName)) || "null".equals(String.valueOf(childName)) || VaWorkFlowI18nUtils.DICT_MAP.containsKey(childNodeTitle = childName.asText())) continue;
                VaI18nResourceItem item = new VaI18nResourceItem();
                item.setTitle(childNodeTitle);
                item.setName(childResourceId.asText());
                list.add(item);
            }
        }
        return list;
    }
}

