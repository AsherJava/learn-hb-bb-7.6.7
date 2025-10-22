/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.jiuqi.nr.bi.integration.BIApiFactory
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.query.chart.BIChartConfig
 *  com.jiuqi.nr.query.chart.HttpUtils
 *  com.jiuqi.nr.query.querymodal.QueryModalDefine
 *  com.jiuqi.nr.query.service.QueryModalServiceHelper
 *  com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.services.BIIntegrationConfig
 *  com.jiuqi.nvwa.intergration.sdk.ticket.bean.Ticket
 *  com.jiuqi.nvwa.ticket.service.TicketService
 *  io.swagger.annotations.Api
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dashboard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jiuqi.nr.bi.integration.BIApiFactory;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dashboard.defines.Dashboard;
import com.jiuqi.nr.dashboard.defines.DashboardTreeNode;
import com.jiuqi.nr.query.chart.BIChartConfig;
import com.jiuqi.nr.query.chart.HttpUtils;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.service.QueryModalServiceHelper;
import com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.services.BIIntegrationConfig;
import com.jiuqi.nvwa.intergration.sdk.ticket.bean.Ticket;
import com.jiuqi.nvwa.ticket.service.TicketService;
import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/dashboard"})
@Api(value="\u4eea\u8868\u76d8\u63a5\u53e3")
public class IDashboardTemplateController {
    @Autowired
    TicketService tokenServices;
    @Autowired
    private QueryModalServiceHelper servicesHelper;
    @Autowired
    Environment environment;
    @Autowired
    BIApiFactory biApi;
    @Autowired
    BIIntegrationConfig biConfig;
    @Autowired
    BIChartConfig biChartConfig;
    private static final Logger logger = LoggerFactory.getLogger(IDashboardTemplateController.class);

    @RequestMapping(value={"/query-all-addQueryModal"}, method={RequestMethod.POST})
    public String addQueryModal(@RequestBody QueryModalDefine modal) {
        return this.servicesHelper.insertModal(modal);
    }

    @RequestMapping(value={"/get-bidashboardtree"}, method={RequestMethod.POST})
    public String loadTree(@RequestBody String groupId, HttpServletRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        String address = this.biConfig.getBiAddress();
        String ticket = this.biApi.getTicket("query-bidashboardtree");
        String rootFolderUrl = address + "/api/ms/dashboard/panel/folders" + ticket;
        try {
            ArrayList<Object> groups = new ArrayList<Object>();
            if (groupId == null || StringUtils.isEmpty(groupId) || "null".equals(groupId) || "{}".equals(groupId)) {
                String rootGroup = HttpUtils.doRequestForString((String)rootFolderUrl, null, (String)"GET");
                List dashboardList = HttpUtils.jsonToList((String)rootGroup, Dashboard.class);
                for (Dashboard group1 : dashboardList) {
                    String nextFolderUrl = address + "/api/ms/dashboard/panel/folders" + "/" + group1.getGuid() + "?ticket=" + ticket;
                    List<ITree<DashboardTreeNode>> childgroups = this.getGroups(nextFolderUrl, "GET");
                    String dashboradItemsUrl = address + "/api/ms/dashboard/panel/items" + "/" + group1.getGuid() + "?ticket=" + ticket;
                    List<ITree<DashboardTreeNode>> childmodels = this.getModels(dashboradItemsUrl, "GET");
                    DashboardTreeNode node1 = DashboardTreeNode.buildTreeNodeData(group1, true);
                    ITree tree1 = new ITree((INode)node1);
                    tree1.setIcons(new String[]{"#icon-_ModalTfujiedian"});
                    tree1.setLeaf(false);
                    this.setChild((ITree<DashboardTreeNode>)tree1, childgroups);
                    this.setChild((ITree<DashboardTreeNode>)tree1, childmodels);
                    groups.add(tree1);
                }
            } else if (groupId.equals("FirstLevel")) {
                String rootGroup = HttpUtils.doRequestForString((String)rootFolderUrl, null, (String)"GET");
                List dashboardList = HttpUtils.jsonToList((String)rootGroup, Dashboard.class);
                for (Dashboard group1 : dashboardList) {
                    DashboardTreeNode node1 = DashboardTreeNode.buildTreeNodeData(group1, true);
                    ITree tree1 = new ITree((INode)node1);
                    tree1.setIcons(new String[]{"#icon-_ModalTfujiedian"});
                    tree1.setLeaf(false);
                    groups.add(tree1);
                }
            } else {
                String nextFolderUrl = address + "/api/ms/dashboard/panel/folders" + "/" + groupId + "?ticket=" + ticket;
                List<ITree<DashboardTreeNode>> groupChilds = this.getGroups(nextFolderUrl, "GET");
                groups.addAll(groupChilds);
                String dashboradItemsUrl = address + "/api/ms/dashboard/panel/items" + "/" + groupId + "?ticket=" + ticket;
                List<ITree<DashboardTreeNode>> modelChilds = this.getModels(dashboradItemsUrl, "GET");
                groups.addAll(modelChilds);
            }
            return objectMapper.writeValueAsString(groups);
        }
        catch (Exception jE) {
            logger.error(jE.getMessage(), jE);
            return null;
        }
    }

    private List<ITree<DashboardTreeNode>> getModels(String dashboradItemsUrl, String RequestMethod2) {
        ArrayList<ITree<DashboardTreeNode>> groups = new ArrayList<ITree<DashboardTreeNode>>();
        String dashboards = HttpUtils.doRequestForString((String)(dashboradItemsUrl + this.biApi.getTicket("get-bidashboardmodels")), null, (String)RequestMethod2);
        List dashboardList = HttpUtils.jsonToList((String)dashboards, Dashboard.class);
        if (dashboardList != null) {
            for (Dashboard dashboard : dashboardList) {
                DashboardTreeNode node = DashboardTreeNode.buildTreeNodeData(dashboard, false);
                ITree tree = new ITree((INode)node);
                tree.setIcons(new String[]{"#icon-_GJZshuxingzhongji"});
                tree.setLeaf(true);
                groups.add((ITree<DashboardTreeNode>)tree);
            }
        }
        return groups;
    }

    private List<ITree<DashboardTreeNode>> getGroups(String nextFolderUrl, String RequestMethod2) {
        ArrayList<ITree<DashboardTreeNode>> groups = new ArrayList<ITree<DashboardTreeNode>>();
        String next = HttpUtils.doRequestForString((String)nextFolderUrl, null, (String)RequestMethod2);
        List group = HttpUtils.jsonToList((String)next, Dashboard.class);
        if (group != null) {
            for (Dashboard group1 : group) {
                DashboardTreeNode node1 = DashboardTreeNode.buildTreeNodeData(group1, true);
                ITree tree1 = new ITree((INode)node1);
                tree1.setIcons(new String[]{"#icon-_ModalTfujiedian"});
                tree1.setLeaf(false);
                groups.add((ITree<DashboardTreeNode>)tree1);
            }
        }
        return groups;
    }

    private void setChild(ITree<DashboardTreeNode> tree, List<ITree<DashboardTreeNode>> childs) {
        for (ITree<DashboardTreeNode> childNode : childs) {
            tree.appendChild(childNode);
        }
    }

    private void queryAllChildGroups(String address, Dashboard group, String authorization, List<ITree<DashboardTreeNode>> groups, String RequestMethod2, String title) {
        String nextFolderUrl = address + "/api/ms/dashboard/panel/folders" + "/" + group.getGuid();
        String next = HttpUtils.doRequestForString((String)(nextFolderUrl + this.biApi.getTicket("queryAllChildGroups")), null, (String)RequestMethod2);
        List nextGroups = HttpUtils.jsonToList((String)next, Dashboard.class);
        String dashboradItemsUrl = address + "/api/ms/dashboard/panel/items" + "/" + group.getGuid();
        String dashboards = HttpUtils.doRequestForString((String)(dashboradItemsUrl + this.biApi.getTicket("queryAllChildGroups")), null, (String)RequestMethod2);
        List dashboardList = HttpUtils.jsonToList((String)dashboards, Dashboard.class);
        if (dashboardList != null && !dashboardList.isEmpty()) {
            group.setTitle(title);
            DashboardTreeNode node1 = DashboardTreeNode.buildTreeNodeData(group, true);
            ITree tree1 = new ITree((INode)node1);
            tree1.setIcons(new String[]{"#icon-_ModalTfujiedian"});
            tree1.setLeaf(false);
            groups.add((ITree<DashboardTreeNode>)tree1);
        }
        if (nextGroups != null && !nextGroups.isEmpty()) {
            for (Dashboard group1 : nextGroups) {
                title = title + "/" + group1.getTitle();
                this.queryAllChildGroups(address, group1, authorization, groups, RequestMethod2, title);
            }
        }
    }

    @RequestMapping(value={"/get-bidashboardgroups"}, method={RequestMethod.GET})
    public String loadTreeGroups(String groupId, HttpServletRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        String address = this.biConfig.getBiAddress();
        String ticket = this.biApi.getTicket("get-bidashboardgroups");
        String rootFolderUrl = address + "/api/ms/dashboard/panel/folders";
        try {
            ArrayList<ITree<DashboardTreeNode>> groups = new ArrayList<ITree<DashboardTreeNode>>();
            if (groupId == null || StringUtils.isEmpty(groupId) || "null".equals(groupId) || "{}".equals(groupId)) {
                String rootGroup = HttpUtils.doRequestForString((String)(rootFolderUrl + this.biApi.getTicket("get-bidashboardgroups")), null, (String)"GET");
                List dashboardList = HttpUtils.jsonToList((String)rootGroup, Dashboard.class);
                for (Dashboard group1 : dashboardList) {
                    this.queryAllChildGroups(address, group1, ticket, groups, "GET", group1.getTitle());
                }
            }
            return objectMapper.writeValueAsString(groups);
        }
        catch (Exception jE) {
            logger.error(jE.getMessage(), jE);
            return null;
        }
    }

    private String getTicket(String fromApiName) {
        try {
            Ticket ticket = this.tokenServices.apply();
            return ticket.getId();
        }
        catch (Exception ex) {
            logger.error(fromApiName + "\u83b7\u53d6ticket\u5931\u8d25", ex);
            return null;
        }
    }

    @RequestMapping(value={"/get-bidashboardmodels"}, method={RequestMethod.POST})
    public String loadTreeModels(@RequestBody Map<String, String> param, HttpServletRequest request) {
        String groupId = param.get("groupId");
        ObjectMapper objectMapper = new ObjectMapper();
        String address = this.biConfig.getBiAddress();
        try {
            ArrayList<ITree<DashboardTreeNode>> groups = new ArrayList<ITree<DashboardTreeNode>>();
            if (groupId == null || StringUtils.isEmpty(groupId) || "null".equals(groupId) || "{}".equals(groupId)) {
                String dashboradItemsUrl = address + "/api/ms/dashboard/panel/items";
                List<ITree<DashboardTreeNode>> rootModels = this.getModels(dashboradItemsUrl, "GET");
                groups.addAll(rootModels);
            } else {
                String dashboradItemsUrl = address + "/api/ms/dashboard/panel/items" + "/" + groupId;
                List<ITree<DashboardTreeNode>> modelChilds = this.getModels(dashboradItemsUrl, "GET");
                groups.addAll(modelChilds);
            }
            return objectMapper.writeValueAsString(groups);
        }
        catch (Exception jE) {
            logger.error(jE.getMessage(), jE);
            return null;
        }
    }

    @RequestMapping(value={"/get-ticket"}, method={RequestMethod.GET})
    public String getTickets() {
        return this.biApi.getTicketNoPreFix("get-ticket");
    }

    @RequestMapping(value={"/get-ticketcs"}, method={RequestMethod.GET})
    public String getTicketCs() {
        Gson gson = new Gson();
        JsonObject temp = new JsonObject();
        temp.addProperty("cs", this.biConfig.getCAIdentify());
        temp.addProperty("ticket", this.biChartConfig.getTicket("get-ticket", this.biConfig.getCAIdentify()));
        temp.addProperty("as", this.biConfig.getServerIdentify());
        return gson.toJson((JsonElement)temp);
    }
}

