/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.reportTag.common.ReportTagType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService
 *  com.jiuqi.nvwa.datav.dashboard.adapter.DashboardAdapterException
 *  com.jiuqi.nvwa.datav.dashboard.domain.DashboardItem
 *  com.jiuqi.nvwa.datav.dashboard.domain.Widget
 *  com.jiuqi.nvwa.datav.dashboard.domain.WidgetTreeNode
 *  com.jiuqi.nvwa.datav.dashboard.exception.DashboardException
 *  com.jiuqi.nvwa.datav.dashboard.manager.DashboardManager
 */
package com.jiuqi.nr.report.helper;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.reportTag.common.ReportTagType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.report.dto.ReportTemplateDTO;
import com.jiuqi.nr.report.service.IReportTemplateManageService;
import com.jiuqi.nr.report.web.vo.ChartTreeNode;
import com.jiuqi.nr.report.web.vo.CustomTagVO;
import com.jiuqi.nr.report.web.vo.EntityAttributeVO;
import com.jiuqi.nr.report.web.vo.FormDataVO;
import com.jiuqi.nr.report.web.vo.QuickReportNode;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService;
import com.jiuqi.nvwa.datav.dashboard.adapter.DashboardAdapterException;
import com.jiuqi.nvwa.datav.dashboard.domain.DashboardItem;
import com.jiuqi.nvwa.datav.dashboard.domain.Widget;
import com.jiuqi.nvwa.datav.dashboard.domain.WidgetTreeNode;
import com.jiuqi.nvwa.datav.dashboard.exception.DashboardException;
import com.jiuqi.nvwa.datav.dashboard.manager.DashboardManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class QuickReportHelper {
    private static final Logger logger = LoggerFactory.getLogger(QuickReportHelper.class);
    @Autowired
    private DashboardManager dashboardManager;
    @Autowired
    private ResourceTreeNodeService resourceTreeNodeService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IReportTemplateManageService reportTemplateManageService;
    public static final String FOLDER_NODE_TYPE = "com.jiuqi.nvwa.dataanalyze";
    public static final String FOLDER_NODE_ICON = "#icon16_DH_A_NW_gongnengfenzushouqi";
    public static final String QR_NODE_ICON = "#icon16_SHU_A_NW_kuaisubaobiao";

    public List<UITreeNode<QuickReportNode>> getChildren(String parent) {
        List<String> types = QuickReportHelper.getTypes();
        ArrayList<UITreeNode<QuickReportNode>> result = new ArrayList<UITreeNode<QuickReportNode>>();
        try {
            List children = this.resourceTreeNodeService.getChildren(parent, types);
            for (ResourceTreeNode resource : children) {
                QuickReportNode quickReportNode = new QuickReportNode(resource);
                UITreeNode uiTreeNode = new UITreeNode();
                uiTreeNode.setKey(quickReportNode.getKey());
                uiTreeNode.setTitle(quickReportNode.getTitle());
                uiTreeNode.setParent(quickReportNode.getParent());
                uiTreeNode.setData((TreeData)quickReportNode);
                uiTreeNode.setIcon(quickReportNode.getIcon());
                if (FOLDER_NODE_TYPE.equals(resource.getType())) {
                    uiTreeNode.setDisabled(true);
                }
                uiTreeNode.setLeaf(resource.getType().equals("com.jiuqi.nvwa.quickreport.business"));
                result.add((UITreeNode<QuickReportNode>)uiTreeNode);
            }
        }
        catch (DataAnalyzeResourceException e) {
            logger.error("\u5206\u6790\u8868\u8d44\u6e90\u83b7\u53d6\u5f02\u5e38:" + e.getMessage(), e);
        }
        return result;
    }

    private static List<String> getTypes() {
        ArrayList<String> types = new ArrayList<String>();
        types.add("com.jiuqi.nvwa.quickreport.business");
        return types;
    }

    public List<UITreeNode<QuickReportNode>> locate(String guid) {
        List<UITreeNode<QuickReportNode>> rootChildren = null;
        try {
            if (!StringUtils.isEmpty(guid)) {
                rootChildren = this.getRootChildren();
                List path = this.resourceTreeNodeService.getPath(guid);
                this.appendPathNode(rootChildren, path, guid);
            }
        }
        catch (DataAnalyzeResourceException e) {
            logger.error("\u5206\u6790\u8868\u8282\u70b9\u8def\u5f84\u83b7\u53d6\u5f02\u5e38:" + e.getMessage(), e);
        }
        return rootChildren;
    }

    private void appendPathNode(List<UITreeNode<QuickReportNode>> rootChildren, List<ResourceTreeNode> path, String target) {
        if (!CollectionUtils.isEmpty(path)) {
            List<UITreeNode<QuickReportNode>> cur = rootChildren;
            for (ResourceTreeNode resourceTreeNode : path) {
                cur = this.loadNext(cur, resourceTreeNode.getGuid(), target);
            }
        }
    }

    private List<UITreeNode<QuickReportNode>> loadNext(List<UITreeNode<QuickReportNode>> cur, String curPathNode, String target) {
        List<UITreeNode<QuickReportNode>> children = this.getChildren(curPathNode);
        if (target.equals(curPathNode)) {
            cur.forEach(o -> {
                if (o.getKey().equals(target)) {
                    o.setSelected(true);
                }
            });
        }
        if (!target.equals(curPathNode)) {
            for (UITreeNode<QuickReportNode> c : cur) {
                if (!c.getKey().equals(curPathNode)) continue;
                c.setExpand(true);
                c.setChildren(children);
                return children;
            }
        }
        return cur;
    }

    private List<UITreeNode<QuickReportNode>> getRootChildren() {
        return this.getChildren("root");
    }

    public Map<String, String> getAllNodes() {
        HashMap<String, String> quickReportKeyToTitle = new HashMap<String, String>();
        try {
            List allResourceTreeNodes = this.resourceTreeNodeService.getAllResourceTreeNodes(QuickReportHelper.getTypes());
            allResourceTreeNodes.forEach(o -> quickReportKeyToTitle.put(o.getGuid(), o.getTitle()));
        }
        catch (DataAnalyzeResourceException e) {
            logger.error("\u83b7\u53d6\u6240\u6709\u5206\u6790\u8868\u8282\u70b9\u6570\u636e\u5f02\u5e38:" + e.getMessage(), e);
        }
        return quickReportKeyToTitle;
    }

    public void setQuickReportTitle(List<CustomTagVO> customTagVOs) throws Exception {
        List<CustomTagVO> collect = customTagVOs.stream().filter(a -> StringUtils.hasLength(a.getExpression()) && a.getType().equals(ReportTagType.QUICK_REPORT.getValue())).collect(Collectors.toList());
        try {
            List allResourceTreeNodes = this.resourceTreeNodeService.getAllResourceTreeNodes(QuickReportHelper.getTypes());
            Map<String, String> resourceMap = allResourceTreeNodes.stream().collect(Collectors.toMap(ResourceTreeNode::getGuid, ResourceTreeNode::getTitle));
            collect.forEach(o -> {
                String title = (String)resourceMap.get(o.getExpression());
                if (StringUtils.hasLength(title)) {
                    o.getExpressionNode().setTitle(title);
                }
            });
        }
        catch (DataAnalyzeResourceException e) {
            logger.error("\u83b7\u53d6\u6240\u6709\u5206\u6790\u8868\u8282\u70b9\u6570\u636e\u5f02\u5e38:" + e.getMessage(), e);
        }
    }

    public List<UITreeNode<ChartTreeNode>> getChartChildern(String pId, String type, boolean all) throws DataAnalyzeResourceException, DashboardException {
        String widgetType = "ChartWidget";
        List widgetTreeNodes = null;
        try {
            widgetTreeNodes = this.dashboardManager.getWidgetTreeNodes(pId, type, all);
        }
        catch (DashboardAdapterException e) {
            throw new RuntimeException(e);
        }
        ArrayList<UITreeNode<ChartTreeNode>> result = new ArrayList<UITreeNode<ChartTreeNode>>();
        for (WidgetTreeNode widgetTreeNode : widgetTreeNodes) {
            if ("widget".equals(widgetTreeNode.getType()) && !widgetTreeNode.getWidgetType().equals(widgetType)) continue;
            UITreeNode uiTreeNode = new UITreeNode();
            ChartTreeNode chartTreeNode = new ChartTreeNode(widgetTreeNode);
            chartTreeNode.setParentId(pId);
            uiTreeNode.setData((TreeData)chartTreeNode);
            uiTreeNode.setLeaf(!chartTreeNode.isParent());
            uiTreeNode.setKey(chartTreeNode.getKey());
            uiTreeNode.setParent(pId);
            uiTreeNode.setTitle(chartTreeNode.getTitle());
            if (!widgetTreeNode.getType().equals("widget")) {
                uiTreeNode.setDisabled(true);
            }
            result.add((UITreeNode<ChartTreeNode>)uiTreeNode);
        }
        return result;
    }

    public List<UITreeNode<ChartTreeNode>> locationChartTrees(String expression) throws DataAnalyzeResourceException, DashboardException {
        List<UITreeNode<ChartTreeNode>> chartChildern = this.getChartChildern("root", "root", false);
        String[] split = expression.split("@");
        String dashboardId = split[0];
        String chartId = split[1];
        List path = this.resourceTreeNodeService.getPath(dashboardId);
        this.doLocationChartTree(chartChildern, 0, path, dashboardId, chartId);
        return chartChildern;
    }

    private void doLocationChartTree(List<UITreeNode<ChartTreeNode>> chartTree, int pathIndex, List<ResourceTreeNode> path, String dashboardId, String chartId) throws DataAnalyzeResourceException, DashboardException {
        if (pathIndex == path.size()) {
            ResourceTreeNode pathDataParentForChart = path.get(pathIndex - 1);
            for (UITreeNode<ChartTreeNode> chartTreeNodeITree : chartTree) {
                ChartTreeNode chartTreeNodeData = (ChartTreeNode)chartTreeNodeITree.getData();
                chartTreeNodeData.setParentTitle(pathDataParentForChart.getTitle());
                if (!chartTreeNodeData.getWidgetId().equals(chartId)) continue;
                chartTreeNodeITree.setSelected(true);
            }
        } else {
            ResourceTreeNode pathData = path.get(pathIndex);
            for (UITreeNode<ChartTreeNode> chartTreeNodeITree : chartTree) {
                ChartTreeNode chartTreeNodeData = (ChartTreeNode)chartTreeNodeITree.getData();
                if (!chartTreeNodeData.getKey().equals(pathData.getGuid()) && !chartTreeNodeData.getKey().equals(dashboardId)) continue;
                chartTreeNodeITree.setExpand(true);
                List<UITreeNode<ChartTreeNode>> pathDataChildern = this.getChartChildern(pathData.getGuid(), chartTreeNodeData.getType(), false);
                chartTreeNodeITree.setChildren(pathDataChildern);
                this.doLocationChartTree(pathDataChildern, ++pathIndex, path, dashboardId, chartId);
            }
        }
    }

    public void setChartTitle(List<CustomTagVO> customTagVOs) throws Exception {
        List<CustomTagVO> customTagVOStream = customTagVOs.stream().filter(a -> StringUtils.hasLength(a.getExpression()) && a.getType().equals(ReportTagType.CHART.getValue())).collect(Collectors.toList());
        Set chartExpression = customTagVOStream.stream().map(CustomTagVO::getExpression).filter(a -> a.contains("@")).collect(Collectors.toSet());
        if (chartExpression.size() == 0) {
            return;
        }
        Set<String> dashboardItemGuid = chartExpression.stream().map(a -> a.substring(0, a.indexOf("@"))).collect(Collectors.toSet());
        HashMap dashboardTitleMap = new HashMap();
        dashboardItemGuid.forEach(a -> {
            try {
                DashboardItem dashboardItem = this.dashboardManager.getDashboardItem(a);
                if (dashboardItem != null) {
                    dashboardTitleMap.put(a, dashboardItem.getTitle());
                }
            }
            catch (DashboardException e) {
                logger.info("\u4eea\u8868\u76d8{}\u67e5\u8be2\u5931\u8d25", a);
            }
        });
        if (dashboardTitleMap.size() == 0) {
            return;
        }
        HashMap<String, String> chartExpressionTitleMap = new HashMap<String, String>();
        for (String expression : chartExpression) {
            String[] split = expression.split("@");
            String dashboardTitle = (String)dashboardTitleMap.get(split[0]);
            if (!StringUtils.hasText(dashboardTitle)) continue;
            try {
                Widget widget = this.dashboardManager.getWidget(split[0], split[1]);
                if (widget == null) continue;
                chartExpressionTitleMap.put(expression, dashboardTitle + "|" + widget.getTitle());
            }
            catch (DashboardException e) {
                logger.info("\u4eea\u8868\u76d8{}\u67e5\u8be2\u5931\u8d25", (Object)expression);
            }
        }
        customTagVOStream.forEach(a -> {
            String chartExpressionTitle = (String)chartExpressionTitleMap.get(a.getExpression());
            if (StringUtils.hasText(chartExpressionTitle)) {
                a.getExpressionNode().setTitle(chartExpressionTitle);
            }
        });
    }

    public void setFormAndEntityTitle(List<CustomTagVO> customTagVOs, String rptKey) throws Exception {
        List<CustomTagVO> dimensionType = customTagVOs.stream().filter(a -> StringUtils.hasLength(a.getExpression()) && a.getType().equals(ReportTagType.DIMENSION.getValue())).collect(Collectors.toList());
        List<CustomTagVO> formType = customTagVOs.stream().filter(a -> StringUtils.hasLength(a.getExpression()) && a.getType().equals(ReportTagType.FORM.getValue())).collect(Collectors.toList());
        ReportTemplateDTO reportTemplate = null;
        if (!dimensionType.isEmpty() || !formType.isEmpty()) {
            reportTemplate = this.reportTemplateManageService.getReportTemplate(rptKey);
        }
        if (reportTemplate == null) {
            return;
        }
        if (!dimensionType.isEmpty()) {
            List<EntityAttributeVO> entityAttribute = this.getEntityAttribute(reportTemplate.getTaskKey());
            Map<String, String> dimMap = entityAttribute.stream().collect(Collectors.toMap(EntityAttributeVO::getCode, o -> o.getCode() + " | " + o.getTitle()));
            dimensionType.forEach(o -> {
                String title = (String)dimMap.get(o.getExpression());
                if (StringUtils.hasLength(title)) {
                    o.getExpressionNode().setTitle(title);
                }
            });
        }
        if (!formType.isEmpty()) {
            List<FormDataVO> form = this.getForm(reportTemplate.getFormSchemeKey());
            Map<String, String> formMap = form.stream().collect(Collectors.toMap(FormDataVO::getCode, o -> o.getCode() + " | " + o.getTitle()));
            formType.forEach(o -> {
                String formTitle = (String)formMap.get(o.getExpression());
                if (StringUtils.hasLength(formTitle)) {
                    o.getExpressionNode().setTitle(formTitle);
                }
            });
        }
    }

    public List<EntityAttributeVO> getEntityAttribute(String taskKey) {
        DesignTaskDefine designTaskDefine = this.designTimeViewController.getTask(taskKey);
        IEntityModel entityModel = this.entityMetaService.getEntityModel(designTaskDefine.getDw());
        List showFields = entityModel.getShowFields();
        ArrayList<EntityAttributeVO> result = new ArrayList<EntityAttributeVO>();
        for (IEntityAttribute showField : showFields) {
            result.add(new EntityAttributeVO(showField.getCode(), showField.getTitle()));
        }
        return result;
    }

    public List<FormDataVO> getForm(String formSchemeKey) {
        List forms = this.designTimeViewController.listFormByFormSchemeAndType(formSchemeKey, FormType.FORM_TYPE_NEWFMDM);
        forms.addAll(this.designTimeViewController.listFormByFormSchemeAndType(formSchemeKey, FormType.FORM_TYPE_FIX));
        forms.addAll(this.designTimeViewController.listFormByFormSchemeAndType(formSchemeKey, FormType.FORM_TYPE_FLOAT));
        forms.addAll(this.designTimeViewController.listFormByFormSchemeAndType(formSchemeKey, FormType.FORM_TYPE_ACCOUNT));
        return FormDataVO.toFormDataVO(forms);
    }
}

