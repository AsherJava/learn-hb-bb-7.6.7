/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.link.provider.ILinkResourceProvider
 *  com.jiuqi.nvwa.link.provider.ResourceAppConfig
 *  com.jiuqi.nvwa.link.provider.ResourceAppInfo
 *  com.jiuqi.nvwa.link.provider.ResourceNode
 *  com.jiuqi.nvwa.link.provider.SearchItem
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gc.financialcubes.query.extend;

import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;
import com.jiuqi.gc.financialcubes.query.enums.PenetrationType;
import com.jiuqi.gc.financialcubes.query.enums.UnitType;
import com.jiuqi.gc.financialcubes.query.factory.ParseLayerPenetrationHandlerFactory;
import com.jiuqi.gc.financialcubes.query.factory.TransfromLayerPenetrationHandlerFactory;
import com.jiuqi.gc.financialcubes.query.plugin.ParseLayerPenetrationPlugin;
import com.jiuqi.gc.financialcubes.query.plugin.StandardizationLayerPenetrationPlugin;
import com.jiuqi.gc.financialcubes.query.plugin.TransformLayerPenetrationPlugin;
import com.jiuqi.nvwa.link.provider.ILinkResourceProvider;
import com.jiuqi.nvwa.link.provider.ResourceAppConfig;
import com.jiuqi.nvwa.link.provider.ResourceAppInfo;
import com.jiuqi.nvwa.link.provider.ResourceNode;
import com.jiuqi.nvwa.link.provider.SearchItem;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialCubesLinkResourceProviderImpl
implements ILinkResourceProvider {
    private static final Logger logger = LoggerFactory.getLogger(FinancialCubesLinkResourceProviderImpl.class);
    @Autowired
    private ParseLayerPenetrationHandlerFactory penetrationHandlerFactory;
    @Autowired
    private StandardizationLayerPenetrationPlugin standardizationLayerPenetrationPlugin;
    @Autowired
    private TransfromLayerPenetrationHandlerFactory transfromLayerPenetrationHandlerFactory;

    public String getType() {
        return "com.jiuqi.gc.financialcubes";
    }

    public String getTitle() {
        return "\u5408\u5e76\u591a\u7ef4\u7a7f\u900f";
    }

    public double getOrder() {
        return 0.0;
    }

    public String getIcon() {
        return null;
    }

    public ResourceAppInfo getAppInfo(String resourceId, String extData) {
        return new ResourceAppInfo("@gc", "gcreport-financialcubes", "FinancialCubesPenetrate");
    }

    public List<ResourceNode> getChildNodes(String resourceId, String extData) {
        ArrayList<ResourceNode> list = new ArrayList<ResourceNode>();
        ResourceNode resourceNode = new ResourceNode();
        resourceNode.setId("default");
        resourceNode.setTitle("\u591a\u7ef4\u7a7f\u900f\u67e5\u8be2");
        resourceNode.setLeaf(true);
        resourceNode.setLinkResource(true);
        list.add(resourceNode);
        return list;
    }

    public List<String> getPaths(String resourceId, String extData) {
        return Collections.emptyList();
    }

    public ResourceAppConfig buildAppConfig(String resourceId, String extData, String linkMsg) {
        ResourceAppConfig appConfig = new ResourceAppConfig();
        PenetrationContextInfo context = new PenetrationContextInfo();
        PenetrationType type = this.determinePenetrationType(linkMsg);
        ParseLayerPenetrationPlugin handler = this.penetrationHandlerFactory.getParsePlugin(type);
        Map<String, String> parsedData = handler.handle(linkMsg, context);
        PenetrationParamDTO penetrationParamDTO = this.standardizationLayerPenetrationPlugin.convert(parsedData, context);
        UnitType unitType = UnitType.getUnitTypeByValue(penetrationParamDTO.getUnitType());
        TransformLayerPenetrationPlugin convertPlugin = this.transfromLayerPenetrationHandlerFactory.getConvertPlugin(unitType);
        String cacheId = convertPlugin.convert(penetrationParamDTO, context);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("ID", cacheId);
        appConfig.setConfig(JSONUtil.toJSONString(map));
        return appConfig;
    }

    private PenetrationType determinePenetrationType(String linkMsg) {
        if (linkMsg.contains("_reportName")) {
            return PenetrationType.ANALYSIS_TABLE;
        }
        if (linkMsg.contains("__dhbGuid") && linkMsg.contains("__widgetId")) {
            return PenetrationType.DASH_BOARD;
        }
        return PenetrationType.QUERY_TEMPLATE;
    }

    public List<SearchItem> search(String key) {
        return Collections.emptyList();
    }

    public ResourceNode getResource(String resourceId, String extData) {
        return new ResourceNode();
    }
}

