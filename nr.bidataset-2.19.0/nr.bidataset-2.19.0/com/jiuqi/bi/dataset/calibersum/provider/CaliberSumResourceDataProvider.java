/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.nvwa.dataanalysis.dataset.dataanalysis.DatasetResourceDataProvider
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResource
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 */
package com.jiuqi.bi.dataset.calibersum.provider;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.nvwa.dataanalysis.dataset.dataanalysis.DatasetResourceDataProvider;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResource;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import org.springframework.stereotype.Component;

@Component
@DataAnalyzeResource(type="CaliberSumDataSet", title="\u53e3\u5f84\u6570\u636e\u96c6", pluginName="nr-calibersum-dataset", group="\u6570\u636e\u96c6", order=10, createWinSize={472, 168}, editWinSize={472, 168}, copyWinSize={472, 168}, icon="nr-iconfont icon-_GJYhuizongleixing", supportQuery=false)
public class CaliberSumResourceDataProvider
extends DatasetResourceDataProvider {
    protected void fillDSModelExtData(DSModel dsModel, ResourceTreeNode node) throws DataAnalyzeResourceException {
        dsModel.setDescr(node.getExtData());
    }

    protected void fillResourceNodeExtData(DSModel dsModel, ResourceTreeNode node) throws DataAnalyzeResourceException {
        node.setExtData(dsModel.getDescr());
    }
}

