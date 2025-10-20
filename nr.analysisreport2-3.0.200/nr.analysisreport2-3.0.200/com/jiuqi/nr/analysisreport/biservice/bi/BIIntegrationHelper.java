/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.services.BIIntegrationConfig
 *  io.netty.util.internal.StringUtil
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.analysisreport.biservice.bi;

import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.analysisreport.biservice.bi.BIApiFactory;
import com.jiuqi.nr.analysisreport.biservice.bi.BiChartDefine;
import com.jiuqi.nr.analysisreport.biservice.bi.HttpUtils;
import com.jiuqi.nr.analysisreport.biservice.chart.ChartTreeNode;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
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
import java.util.List;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BIIntegrationHelper {
    private static final Logger log = LoggerFactory.getLogger(BIIntegrationHelper.class);
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

    public String getBiAddress() {
        return this.biConfig.getBiAddress();
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
}

