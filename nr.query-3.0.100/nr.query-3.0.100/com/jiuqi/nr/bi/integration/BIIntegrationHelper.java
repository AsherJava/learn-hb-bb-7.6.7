/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.services.BIIntegrationConfig
 *  io.netty.util.internal.StringUtil
 *  javax.annotation.Resource
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.bi.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bi.integration.BIApiFactory;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.chart.BiChartDefine;
import com.jiuqi.nr.query.chart.ChartTreeNode;
import com.jiuqi.nr.query.chart.HttpUtils;
import com.jiuqi.nr.query.common.QueryBlockType;
import com.jiuqi.nr.query.dao.IQueryBlockDefineDao;
import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import com.jiuqi.nr.query.service.impl.QueryAuthorityImpl;
import com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.services.BIIntegrationConfig;
import io.netty.util.internal.StringUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BIIntegrationHelper {
    private static final Logger log = LoggerFactory.getLogger(BIIntegrationHelper.class);
    private QueryAuthorityImpl authority;
    @Autowired
    private IQueryModalDefineDao modelDao;
    @Autowired
    private IQueryBlockDefineDao blockDao;
    @Autowired
    BIApiFactory biApi;
    @Autowired
    BIIntegrationConfig biConfig;
    @Resource
    private ISecretLevelService secretLevelService;

    public List<ITree<ChartTreeNode>> getRootGroup(String type) {
        ArrayList<ITree<ChartTreeNode>> groups = new ArrayList<ITree<ChartTreeNode>>();
        if (type == "bireport") {
            ChartTreeNode reportNode = ChartTreeNode.buildTreeNodeData("report" + HttpUtils.splitchart + "06079ac0-dc15-11e8-969b-64006a6432d7", "\u5feb\u901f\u5206\u6790\u8868", true, "report");
            reportNode.setCode("06079ac0-dc15-11e8-969b-64006a6432d7");
            ITree root = new ITree((INode)reportNode);
            root.setLeaf(false);
            groups.add(root);
            return groups;
        }
        ChartTreeNode reportNode = ChartTreeNode.buildTreeNodeData("report" + HttpUtils.splitchart + "b8079ac0-dc15-11e8-969b-64006a6432d8", "\u4eea\u8868\u76d8\u56fe\u8868", true, "report");
        reportNode.setCode("b8079ac0-dc15-11e8-969b-64006a6432d8");
        ITree root = new ITree((INode)reportNode);
        root.setLeaf(false);
        groups.add(root);
        ChartTreeNode biNode = ChartTreeNode.buildTreeNodeData("bi" + HttpUtils.splitchart + "08079ac0-dc15-11e8-969b-64006a6432d6", "BI\u56fe\u8868", true, "bi");
        biNode.setCode("08079ac0-dc15-11e8-969b-64006a6432d6");
        ITree biroot = new ITree((INode)biNode);
        biroot.setLeaf(false);
        groups.add((ITree<ChartTreeNode>)biroot);
        return groups;
    }

    private boolean hasChartBlock(String id, boolean isGroup, QueryModelType type) {
        List<Object> modals = new ArrayList();
        List<Object> groups = new ArrayList();
        List<Object> blocks = new ArrayList();
        if (isGroup) {
            modals = this.authority.getModalsByGroupId(id, type, "query_model_resource_read");
            groups = this.authority.getModalGroupByParentId(id, type, "query_model_resource_read");
        } else {
            QueryModalDefine modal = this.modelDao.queryModalDefineById(id);
            if (modal != null) {
                modals.add(modal);
            }
        }
        for (QueryModalGroup queryModalGroup : groups) {
            if (!this.hasChartBlock(queryModalGroup.getGroupId(), true, type)) continue;
            return true;
        }
        for (QueryModalDefine queryModalDefine : modals) {
            blocks = this.blockDao.GetQueryBlockDefinesByModelId(queryModalDefine.getId());
            for (QueryBlockDefine queryBlockDefine : blocks) {
                queryBlockDefine.setBlockInfoStr(new String(queryBlockDefine.getBlockInfoBlob()));
                if (queryBlockDefine.getBlockType() != QueryBlockType.QBT_CHART) continue;
                return true;
            }
        }
        return false;
    }

    public List<ITree<ChartTreeNode>> getBiTree(String groupId, String type) {
        String address = this.biConfig.getBiAddress();
        String ticket = this.biApi.getTicket("query-bidashboardtree");
        String rootGroupId = "08079ac0-dc15-11e8-969b-64006a6432d6";
        String rootFolderUrl = address + "/api/ms/dashboard/chart/folders";
        String itemUrl = address + "/api/ms/dashboard/chart/items";
        if (type == "bireport") {
            rootFolderUrl = address + "/api/ms/dashboard/report/folders";
            rootGroupId = "06079ac0-dc15-11e8-969b-64006a6432d7";
            itemUrl = address + "/api/ms/dashboard/report/items";
        }
        List<Object> chartFolderList = new ArrayList();
        List<Object> chartItemList = new ArrayList();
        try {
            ITree tree;
            ChartTreeNode node;
            String key;
            String curGroupId = HttpUtils.getCurrentId(groupId);
            ArrayList<ITree<ChartTreeNode>> groups = new ArrayList<ITree<ChartTreeNode>>();
            if (StringUtil.isNullOrEmpty((String)groupId) || "null".equals(groupId) || "{}".equals(groupId) || curGroupId.equals(rootGroupId) || groupId.equals("06079ac0-dc15-11e8-969b-64006a6432d7")) {
                String rootGroup = HttpUtils.doRequestForString(rootFolderUrl + ticket, null, "GET");
                chartFolderList = HttpUtils.jsonToList(rootGroup, BiChartDefine.class);
            } else {
                String url = "";
                ticket = this.biApi.getTicket("query-bidashboardtree");
                url = rootFolderUrl + "/" + curGroupId + ticket;
                String string = HttpUtils.doRequestForString(url, null, "GET");
                chartFolderList = HttpUtils.jsonToList(string, BiChartDefine.class);
                ticket = this.biApi.getTicket("query-bidashboardtree");
                url = itemUrl + "/" + curGroupId + ticket;
                String items = HttpUtils.doRequestForString(url, null, "GET");
                chartItemList = HttpUtils.jsonToList(items, BiChartDefine.class);
            }
            if (chartFolderList != null) {
                for (BiChartDefine biChartDefine : chartFolderList) {
                    key = groupId + HttpUtils.splitchart + biChartDefine.getGuid();
                    node = ChartTreeNode.buildTreeNodeData(key, biChartDefine.getTitle(), false, type);
                    node.setCode(biChartDefine.getGuid());
                    tree = new ITree((INode)node);
                    tree.setIcons(new String[]{"#icon-_ModalTfujiedian"});
                    tree.setLeaf(false);
                    groups.add((ITree<ChartTreeNode>)tree);
                }
            }
            if (chartItemList != null) {
                for (BiChartDefine biChartDefine : chartItemList) {
                    key = groupId + HttpUtils.splitchart + biChartDefine.getGuid();
                    node = ChartTreeNode.buildTreeNodeData(key, biChartDefine.getTitle(), true, type);
                    node.setCode(biChartDefine.getGuid());
                    tree = new ITree((INode)node);
                    tree.setIcons(new String[]{"#icon-_ModalTfujiedian"});
                    tree.setLeaf(true);
                    groups.add((ITree<ChartTreeNode>)tree);
                }
            }
            return groups;
        }
        catch (Exception jE) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u9996\u9875\u56fe\u8868\uff0c\u83b7\u53d6BI\u56fe\u8868\u5f02\u5e38", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + jE.getMessage()));
            log.error(jE.getMessage(), jE);
            return null;
        }
    }

    public List<ITree<ChartTreeNode>> getBiTree(String groupId, String type, Integer minSercurityLevel) {
        String address = this.biConfig.getBiAddress();
        String ticket = this.biApi.getTicket("query-bidashboardtree");
        String rootGroupId = "08079ac0-dc15-11e8-969b-64006a6432d6";
        String rootFolderUrl = address + "/api/ms/dashboard/chart/folders";
        String itemUrl = address + "/api/ms/dashboard/chart/items";
        if (type == "bireport") {
            rootFolderUrl = address + "/api/ms/dashboard/report/folders";
            rootGroupId = "06079ac0-dc15-11e8-969b-64006a6432d7";
            itemUrl = address + "/api/ms/dashboard/report/items";
        }
        List<Object> chartFolderList = new ArrayList();
        List<Object> chartItemList = new ArrayList();
        try {
            String curGroupId = HttpUtils.getCurrentId(groupId);
            ArrayList<ITree<ChartTreeNode>> groups = new ArrayList<ITree<ChartTreeNode>>();
            if (StringUtil.isNullOrEmpty((String)groupId) || "null".equals(groupId) || "{}".equals(groupId) || curGroupId.equals(rootGroupId) || groupId.equals("06079ac0-dc15-11e8-969b-64006a6432d7")) {
                String rootGroup = HttpUtils.doRequestForString(rootFolderUrl + ticket, null, "GET");
                chartFolderList = HttpUtils.jsonToList(rootGroup, BiChartDefine.class);
            } else {
                String url = "";
                ticket = this.biApi.getTicket("query-bidashboardtree");
                url = rootFolderUrl + "/" + curGroupId + ticket;
                String string = HttpUtils.doRequestForString(url, null, "GET");
                chartFolderList = HttpUtils.jsonToList(string, BiChartDefine.class);
                ticket = this.biApi.getTicket("query-bidashboardtree");
                url = itemUrl + "/" + curGroupId + ticket;
                String items = HttpUtils.doRequestForString(url, null, "GET");
                chartItemList = HttpUtils.jsonToList(items, BiChartDefine.class);
            }
            if (chartFolderList != null && chartFolderList.size() > 0) {
                for (BiChartDefine biChartDefine : chartFolderList) {
                    String key = groupId + HttpUtils.splitchart + biChartDefine.getGuid();
                    ChartTreeNode node = ChartTreeNode.buildTreeNodeData(key, biChartDefine.getTitle(), false, type);
                    node.setCode(biChartDefine.getGuid());
                    ITree tree = new ITree((INode)node);
                    tree.setIcons(new String[]{"#icon-_ModalTfujiedian"});
                    tree.setLeaf(false);
                    groups.add((ITree<ChartTreeNode>)tree);
                }
            }
            if (chartItemList != null && chartItemList.size() > 0) {
                for (BiChartDefine biChartDefine : chartItemList) {
                    String securityLevel = biChartDefine.getSecurityLevel();
                    if (!StringUtil.isNullOrEmpty((String)securityLevel) && Integer.valueOf(biChartDefine.getSecurityLevel()) > minSercurityLevel) continue;
                    String securityTitle = this.secretLevelService.getSecretLevelItem(securityLevel).getTitle();
                    String key = groupId + HttpUtils.splitchart + biChartDefine.getGuid();
                    ChartTreeNode node = ChartTreeNode.buildTreeNodeData(key, biChartDefine.getTitle() + "[" + securityTitle + "]", true, type);
                    node.setCode(biChartDefine.getGuid());
                    ITree tree = new ITree((INode)node);
                    tree.setIcons(new String[]{"#icon-_ModalTfujiedian"});
                    tree.setLeaf(true);
                    groups.add((ITree<ChartTreeNode>)tree);
                }
            }
            return groups;
        }
        catch (Exception jE) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u9996\u9875\u56fe\u8868\uff0c\u83b7\u53d6BI\u56fe\u8868\u5f02\u5e38", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + jE.getMessage()));
            log.error(jE.getMessage(), jE);
            return null;
        }
    }

    public List<ITree<ChartTreeNode>> getTree(String groupId, QueryModelType type) {
        ArrayList<ITree<ChartTreeNode>> groups = new ArrayList<ITree<ChartTreeNode>>();
        try {
            ITree tree;
            ChartTreeNode node;
            String key;
            String curGroupId = HttpUtils.getCurrentId(groupId);
            List<QueryModalDefine> modals = this.authority.getModalsByGroupId(curGroupId, type, "query_model_resource_read");
            List<QueryModalGroup> group = this.authority.getModalGroupByParentId(curGroupId, type, "query_model_resource_read");
            List<QueryBlockDefine> blocks = this.blockDao.GetQueryBlockDefinesByModelId(curGroupId);
            if (group != null) {
                for (QueryModalGroup group1 : group) {
                    if (!this.hasChartBlock(group1.getGroupId(), true, type)) continue;
                    key = groupId + HttpUtils.splitchart + group1.getGroupId();
                    node = ChartTreeNode.buildTreeNodeData(key, group1.getGroupName(), false, "report");
                    node.setCode(group1.getGroupId());
                    tree = new ITree((INode)node);
                    tree.setIcons(new String[]{"#icon-_ModalTfujiedian"});
                    tree.setLeaf(false);
                    groups.add((ITree<ChartTreeNode>)tree);
                }
            }
            if (modals != null) {
                for (QueryModalDefine modal : modals) {
                    if (!this.hasChartBlock(modal.getId(), false, type)) continue;
                    key = groupId + HttpUtils.splitchart + modal.getId();
                    node = ChartTreeNode.buildTreeNodeData(key, modal.getTitle(), true, "report");
                    node.setCode(modal.getId());
                    tree = new ITree((INode)node);
                    tree.setIcons(new String[]{"#icon-_GJZshuxingzhongji"});
                    tree.setLeaf(false);
                    groups.add((ITree<ChartTreeNode>)tree);
                }
            }
            if (blocks != null) {
                for (QueryBlockDefine block : blocks) {
                    block.setBlockInfoStr(new String(block.getBlockInfoBlob()));
                    if (block.getBlockType() != QueryBlockType.QBT_CHART) continue;
                    key = groupId + HttpUtils.splitchart + block.getId();
                    node = ChartTreeNode.buildTreeNodeData(key, block.getTitle(), true, "report");
                    node.setCode(block.getId());
                    tree = new ITree((INode)node);
                    tree.setIcons(new String[]{"#icon-_GJZshuxingzhongji"});
                    tree.setLeaf(true);
                    groups.add((ITree<ChartTreeNode>)tree);
                }
            }
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u9996\u9875\u56fe\u8868\uff0c\u83b7\u53d6\u4eea\u8868\u76d8\u56fe\u8868\u5f02\u5e38", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + ex.getMessage()));
            log.error(ex.getMessage(), ex);
        }
        return groups;
    }

    public List<ITree<ChartTreeNode>> loadDefaultTree(List<ITree<ChartTreeNode>> parentNodes, String[] defaultKeys, int index) {
        String chartType = defaultKeys[0];
        Iterator<ITree<ChartTreeNode>> iterator = parentNodes.iterator();
        while (iterator.hasNext()) {
            String defauKey = defaultKeys[index];
            ITree<ChartTreeNode> node = iterator.next();
            String curKey = HttpUtils.getCurrentId(node.getKey());
            if (!defauKey.equals(curKey)) continue;
            List<ITree<ChartTreeNode>> chileds = null;
            chileds = chartType.equals("report") ? this.getTree(node.getKey(), QueryModelType.DASHOWNER) : this.getBiTree(node.getKey(), chartType);
            if (index + 1 >= defaultKeys.length - 1) {
                node.setChildren(chileds);
                return parentNodes;
            }
            List<ITree<ChartTreeNode>> all = this.loadDefaultTree(chileds, defaultKeys, index + 1);
            node.setChildren(all);
        }
        return parentNodes;
    }

    public String initTree(String group, String type, String defaultKey) {
        try {
            List<ITree<ChartTreeNode>> bitree;
            List<ITree<ChartTreeNode>> reporttree;
            ArrayList<ITree<ChartTreeNode>> charttree = new ArrayList<ITree<ChartTreeNode>>();
            ObjectMapper objectMapper = new ObjectMapper();
            if (StringUtil.isNullOrEmpty((String)group) || "null".equals(group)) {
                if (!StringUtil.isNullOrEmpty((String)defaultKey)) {
                    String[] curGroupId = defaultKey.split(HttpUtils.splitchart);
                    if (StringUtil.isNullOrEmpty((String)type)) {
                        type = curGroupId[0];
                    }
                    List<ITree<ChartTreeNode>> roottree = this.getRootGroup(type);
                    for (ITree<ChartTreeNode> treeNode : roottree) {
                        ChartTreeNode node = (ChartTreeNode)treeNode.getData();
                        if (curGroupId.length == 1 || !node.getType().equals(curGroupId[0])) {
                            charttree.add(treeNode);
                            continue;
                        }
                        List<Object> nodes = new ArrayList();
                        nodes = node.getType() == "report" ? this.getTree(node.getKey(), QueryModelType.DASHOWNER) : this.getBiTree(node.getKey(), type);
                        nodes = this.loadDefaultTree(nodes, curGroupId, 2);
                        treeNode.setChildren(nodes);
                        charttree.add(treeNode);
                    }
                    return objectMapper.writeValueAsString(charttree);
                }
                return objectMapper.writeValueAsString(this.getRootGroup(type));
            }
            if (StringUtil.isNullOrEmpty((String)type)) {
                return "";
            }
            if (type.equals("report") && (reporttree = this.getTree(group, QueryModelType.DASHOWNER)).size() > 0) {
                charttree.addAll(reporttree);
            }
            if (type.equals("bi") && (bitree = this.getBiTree(group, type)).size() > 0) {
                charttree.addAll(bitree);
            }
            if (type.equals("bireport") && (bitree = this.getBiTree(group, "bireport")).size() > 0) {
                charttree.addAll(bitree);
            }
            return objectMapper.writeValueAsString(charttree);
        }
        catch (Exception e) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u6811\u5f62\u7ed3\u6784\u751f\u6210\u5f02\u5e38", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + e.getMessage()));
            return null;
        }
    }

    public String getItemUrl(String type, String key) {
        String address = this.biConfig.getBiAddress();
        String ticket = this.biApi.getTicket("query-bidashboardtree");
        String url = address + "/api/ms/dashboard/chart/item" + "/" + key + ticket;
        if (type == "bireport") {
            url = address + "/api/ms/dashboard/report/item" + "/" + key + ticket;
        }
        return url;
    }

    public String getPageUrl(String type, String key) {
        String address = this.biConfig.getBiAddress();
        String ticket = this.biApi.getTicket("query-bidashboardtree");
        String url = address + "/ms/dashboard/chart.jsp" + "/" + key + ticket;
        if (type == "bireport") {
            url = address + "/ms/dashboard/report.jsp" + "/" + key + ticket;
        }
        return url;
    }

    public static byte[] getErrorMessage(String error) {
        BufferedImage bufferedImage = new BufferedImage(300, 300, 1);
        Graphics paint = bufferedImage.getGraphics();
        paint.setColor(Color.WHITE);
        paint.fillRect(0, 0, 300, 300);
        paint.setColor(Color.GRAY);
        paint.drawLine(1, 1, 1, 299);
        paint.drawLine(1, 299, 299, 299);
        paint.drawLine(299, 299, 299, 1);
        paint.drawLine(299, 1, 1, 1);
        paint.setColor(Color.RED);
        paint.setFont(new Font("Microsoft YaHei", 0, 13));
        BIIntegrationHelper.drawLongString(paint, "\u56fe\u8868\u751f\u6210\u5931\u8d25\uff1a" + error, 5, 15, 290);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write((RenderedImage)bufferedImage, "png", out);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return out.toByteArray();
    }

    static void drawLongString(Graphics g, String text, int x, int y, int maxWidth) {
        JLabel label = new JLabel(text);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int textH = metrics.getHeight();
        int textW = metrics.stringWidth(label.getText());
        String tempText = text;
        while (textW > maxWidth) {
            int n = textW / maxWidth;
            int subPos = tempText.length() / n;
            String drawText = tempText.substring(0, subPos);
            int subTxtW = metrics.stringWidth(drawText);
            while (subTxtW > maxWidth) {
                drawText = tempText.substring(0, --subPos);
                subTxtW = metrics.stringWidth(drawText);
            }
            g.drawString(drawText, x, y);
            y += textH;
            textW -= subTxtW;
            tempText = tempText.substring(subPos);
        }
        g.drawString(tempText, x, y);
    }

    public String selectAndExpendTreeNode(String group, String type) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ITree<ChartTreeNode>> rootNodeList = this.getRootGroup(type);
        ITree<ChartTreeNode> rootNode = rootNodeList.get(0);
        if (!StringUtils.isEmpty((CharSequence)group)) {
            rootNode.setExpanded(true);
            if (group.equals(rootNode.getKey())) {
                rootNode.setSelected(true);
            }
            List<ITree<ChartTreeNode>> secondFloorTrees = this.getBiTree(rootNode.getKey(), type);
            for (ITree<ChartTreeNode> secondFloorNode : secondFloorTrees) {
                this.isQueryNode(secondFloorNode, group, type);
            }
            secondFloorTrees.forEach(node -> rootNode.appendChild(node));
            try {
                return objectMapper.writeValueAsString(rootNodeList);
            }
            catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
            }
        }
        LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u6811\u5f62\u7ed3\u6784\u751f\u6210\u5f02\u5e38", (String)"\u9519\u8bef\u4fe1\u606f\uff1a\u6ca1\u6709\u4f20\u5165\u8981\u67e5\u8be2\u6811\u8282\u70b9\u7684key");
        return null;
    }

    public boolean isQueryNode(ITree<ChartTreeNode> parent, String group, String type) {
        if (parent.getKey().indexOf(group) > -1) {
            parent.setSelected(true);
            return true;
        }
        List<ITree<ChartTreeNode>> childNodes = this.getBiTree(parent.getKey(), type);
        boolean isFinded = false;
        if (childNodes != null && childNodes.size() > 0) {
            for (int i = 0; i < childNodes.size(); ++i) {
                parent.appendChild(childNodes.get(i));
                if (!this.isQueryNode(childNodes.get(i), group, type)) continue;
                isFinded = true;
                parent.setExpanded(true);
            }
            if (!isFinded) {
                parent.setChildren(null);
            }
        }
        return isFinded;
    }

    public String getBiAddress() {
        return this.biConfig.getBiAddress();
    }
}

