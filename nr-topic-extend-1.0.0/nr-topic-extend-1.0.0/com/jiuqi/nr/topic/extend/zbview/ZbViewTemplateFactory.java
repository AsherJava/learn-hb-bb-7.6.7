/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.topicnavigator.TopicNavigatorException
 *  com.jiuqi.nvwa.topicnavigator.adapter.bean.RelatedTemplateItem
 *  com.jiuqi.nvwa.topicnavigator.adapter.bean.TemplateNodeItem
 *  com.jiuqi.nvwa.topicnavigator.adapter.templateFactory.ExactQueryDTO
 *  com.jiuqi.nvwa.topicnavigator.adapter.templateFactory.FuzzyQueryDTO
 *  com.jiuqi.nvwa.topicnavigator.adapter.templateFactory.ITemplateFactory
 *  com.jiuqi.nvwa.topicnavigator.adapter.templateFactory.QueryNodeChildrenDTO
 */
package com.jiuqi.nr.topic.extend.zbview;

import com.jiuqi.nr.topic.extend.zbview.ZbViewTreeService;
import com.jiuqi.nvwa.topicnavigator.TopicNavigatorException;
import com.jiuqi.nvwa.topicnavigator.adapter.bean.RelatedTemplateItem;
import com.jiuqi.nvwa.topicnavigator.adapter.bean.TemplateNodeItem;
import com.jiuqi.nvwa.topicnavigator.adapter.templateFactory.ExactQueryDTO;
import com.jiuqi.nvwa.topicnavigator.adapter.templateFactory.FuzzyQueryDTO;
import com.jiuqi.nvwa.topicnavigator.adapter.templateFactory.ITemplateFactory;
import com.jiuqi.nvwa.topicnavigator.adapter.templateFactory.QueryNodeChildrenDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZbViewTemplateFactory
implements ITemplateFactory {
    private static final Logger logger = LoggerFactory.getLogger(ZbViewTemplateFactory.class);
    protected static final String ZBVIEW_ID = "com.jiuqi.nr.topic.extend.zbview";
    protected static final String ZBVIEW_TYPE = "topicZbView";
    protected static final String ZBVIEW_TITLE = "\u6307\u6807\u89c6\u56fe";
    @Autowired
    private ZbViewTreeService treeService;

    public String getFactoryId() {
        return ZBVIEW_ID;
    }

    public String getTemplateConfigType() {
        return ZBVIEW_TYPE;
    }

    public String getConfigTypeTitle() {
        return ZBVIEW_TITLE;
    }

    public boolean supportShowNodesConfig() {
        return false;
    }

    public boolean supportMsgAlias() {
        return true;
    }

    public int getOrder() {
        return 40;
    }

    public String getIcon() {
        return "nvwa-iconfont icon14_SHU_A_NW_chucunbiao";
    }

    public List<RelatedTemplateItem> getRelatedTemplateChildren(String pid) throws TopicNavigatorException {
        return this.treeService.getByParent(pid);
    }

    public RelatedTemplateItem exactQueryRelatedTemplate(String relatedTemplateId) throws TopicNavigatorException {
        return this.treeService.exactQueryRelatedTemplate(relatedTemplateId);
    }

    public List<RelatedTemplateItem> fuzzyQueryRelatedTemplates(String searchKey) throws TopicNavigatorException {
        return this.treeService.fuzzyQueryRelatedTemplates(searchKey);
    }

    public List<TemplateNodeItem> getTempNodeChildren(QueryNodeChildrenDTO queryNodeChildrenDTO) throws TopicNavigatorException {
        return this.treeService.getTempNodeChildren(queryNodeChildrenDTO);
    }

    public TemplateNodeItem exactQueryTempNode(ExactQueryDTO exactQueryDTO) throws TopicNavigatorException {
        return this.treeService.exactQueryTempNode(exactQueryDTO);
    }

    public List<TemplateNodeItem> fuzzyQueryTempNodes(FuzzyQueryDTO fuzzyQueryDTO) throws TopicNavigatorException {
        return this.treeService.fuzzyQueryTempNodes(fuzzyQueryDTO);
    }
}

