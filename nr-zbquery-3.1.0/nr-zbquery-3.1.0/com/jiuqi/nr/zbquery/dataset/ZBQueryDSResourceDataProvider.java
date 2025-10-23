/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.nvwa.dataanalysis.dataset.dataanalysis.DatasetResourceDataProvider
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResource
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.dataset;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.nr.zbquery.dataset.ZBQueryDSModel;
import com.jiuqi.nvwa.dataanalysis.dataset.dataanalysis.DatasetResourceDataProvider;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResource;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@DataAnalyzeResource(type="com.jiuqi.nr.dataset.zbquery", title="\u67e5\u8be2\u6570\u636e\u96c6", pluginName="nr-zbquery-dataset", group="\u6570\u636e\u96c6", order=10, createWinSize={472, 168}, editWinSize={472, 168}, copyWinSize={472, 168}, icon="nr-iconfont icon-_GJZchakanshangbao", supperQuery=false)
public class ZBQueryDSResourceDataProvider
extends DatasetResourceDataProvider {
    protected void fillDSModelExtData(DSModel dsModel, ResourceTreeNode node) throws DataAnalyzeResourceException {
        String extData = node.getExtData();
        if (StringUtils.hasLength(extData)) {
            ZBQueryDSModel zbQueryDSModel = (ZBQueryDSModel)dsModel;
            JSONObject define = new JSONObject(extData);
            try {
                zbQueryDSModel.loadExtFromJSON(define);
            }
            catch (Exception e) {
                throw new DataAnalyzeResourceException(e.getMessage());
            }
        }
    }

    protected void fillResourceNodeExtData(DSModel dsModel, ResourceTreeNode node) throws DataAnalyzeResourceException {
        ZBQueryDSModel zbQueryDSModel = (ZBQueryDSModel)dsModel;
        try {
            JSONObject define = new JSONObject();
            zbQueryDSModel.saveExtToJSON(define);
            node.setExtData(define.toString());
        }
        catch (Exception e) {
            throw new DataAnalyzeResourceException(e.getMessage());
        }
    }
}

