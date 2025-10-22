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
package com.jiuqi.bi.dataset.report.provider;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.report.model.ReportDSModel;
import com.jiuqi.nvwa.dataanalysis.dataset.dataanalysis.DatasetResourceDataProvider;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResource;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@DataAnalyzeResource(type="ReportDataSet", title="\u62a5\u8868\u6570\u636e\u96c6", pluginName="nr-report-dataset", group="\u6570\u636e\u96c6", order=10, createWinSize={472, 305}, editWinSize={472, 305}, copyWinSize={472, 305}, icon="nr-iconfont icon-16_SHU_A_NR_baobiaoshujuji", supportQuery=false)
public class ReportResourceDataProvider
extends DatasetResourceDataProvider {
    protected void fillDSModelExtData(DSModel dsModel, ResourceTreeNode node) throws DataAnalyzeResourceException {
        String extData = node.getExtData();
        if (StringUtils.hasLength(extData)) {
            ReportDSModel reportDSModel = (ReportDSModel)dsModel;
            JSONObject extJson = new JSONObject(extData);
            try {
                reportDSModel.loadExtFromJSON(extJson);
            }
            catch (Exception e) {
                throw new DataAnalyzeResourceException(e.getMessage());
            }
        }
    }

    protected void fillResourceNodeExtData(DSModel dsModel, ResourceTreeNode node) throws DataAnalyzeResourceException {
        ReportDSModel reportDSModel = (ReportDSModel)dsModel;
        try {
            JSONObject json = new JSONObject();
            reportDSModel.saveExtToJSON(json);
            node.setExtData(json.toString());
        }
        catch (Exception e) {
            throw new DataAnalyzeResourceException(e.getMessage());
        }
    }
}

