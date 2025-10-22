/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.tree.Tree
 *  com.jiuqi.nvwa.framework.nros.bean.RouteParam
 *  com.jiuqi.nvwa.framework.nros.service.IRouteParamService
 *  com.jiuqi.nvwa.framework.nros.service.IRouteService
 *  com.jiuqi.nvwa.framework.nros.tree.RouteItem
 *  com.jiuqi.nvwa.framework.nros.tree.RouteTree
 *  io.swagger.annotations.ApiOperation
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.common.web;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nvwa.framework.nros.bean.RouteParam;
import com.jiuqi.nvwa.framework.nros.service.IRouteParamService;
import com.jiuqi.nvwa.framework.nros.service.IRouteService;
import com.jiuqi.nvwa.framework.nros.tree.RouteItem;
import com.jiuqi.nvwa.framework.nros.tree.RouteTree;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/common/dataentryDefineCode"})
public class DataentryDefineCodeController {
    @Autowired
    private IRouteService routeService;
    @Autowired
    private IRouteParamService routeParamService;

    @GetMapping
    @ApiOperation(value="\u8054\u52a8portal\u67e5\u8be2\u6a21\u677f", notes="\u8054\u52a8portal\u67e5\u8be2\u6a21\u677f")
    public String getDataentryDefineCode(String taskId) {
        RouteTree tree = this.routeService.getUserTree();
        Tree nodeTree = tree.getNodeTree();
        List gatherLeaves = nodeTree.gatherLeaves();
        ArrayList<JSONObject> dataentryRoute = new ArrayList<JSONObject>();
        for (Tree item : gatherLeaves) {
            RouteParam routeParam;
            RouteItem routeItem = (RouteItem)item.getData();
            if (!"dataentry".equals(routeItem.getAppName()) || (routeParam = this.routeParamService.searchByRouteRunTimeId(routeItem.getId())) == null) continue;
            String runTimeRouteParam = routeParam.getConfigJson();
            JSONObject jsonObject = new JSONObject(runTimeRouteParam);
            dataentryRoute.add(jsonObject);
        }
        for (JSONObject json : dataentryRoute) {
            if (!json.has("taskId") || !(json.opt("taskId") instanceof ArrayList) || json.getJSONArray("taskId").length() != 1 || !taskId.equals(json.getJSONArray("taskId").get(0)) || !json.has("dataentryDefineCode")) continue;
            return json.getString("dataentryDefineCode");
        }
        for (JSONObject json : dataentryRoute) {
            if (!json.has("dataentryDefineCode")) continue;
            return json.getString("dataentryDefineCode");
        }
        return "";
    }
}

