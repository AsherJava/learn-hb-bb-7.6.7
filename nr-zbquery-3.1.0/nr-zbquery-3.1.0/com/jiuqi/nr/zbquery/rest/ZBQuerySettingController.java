/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.zbquery.rest;

import com.jiuqi.nr.zbquery.bean.ZBQueryGroup;
import com.jiuqi.nr.zbquery.bean.ZBQueryInfo;
import com.jiuqi.nr.zbquery.service.ZBQueryGroupService;
import com.jiuqi.nr.zbquery.service.ZBQueryInfoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/zbquery/setting"})
public class ZBQuerySettingController {
    @Autowired
    private ZBQueryGroupService zbQueryGroupService;
    @Autowired
    private ZBQueryInfoService zbQueryInfoService;

    @GetMapping(value={"/allQueryGroups"})
    public List<ZBQueryGroup> initQueryInfoGroupTree() {
        Map<String, List<ZBQueryGroup>> groupMap = this.zbQueryGroupService.getParentIdMap(true);
        ArrayList<ZBQueryGroup> ZBQueryGroupResult = new ArrayList<ZBQueryGroup>();
        this.getChildNode(groupMap, ZBQueryGroupResult, "00000000-0000-0000-0000-000000000000", 0);
        return ZBQueryGroupResult;
    }

    private void getChildNode(Map<String, List<ZBQueryGroup>> groupMap, List<ZBQueryGroup> groupList, String parentNode, int Level2) {
        List<ZBQueryGroup> childList = groupMap.get(parentNode);
        if (childList == null || childList.size() == 0) {
            return;
        }
        ++Level2;
        int size = childList.size();
        for (int i = 0; i < size; ++i) {
            ZBQueryGroup g = childList.get(i);
            if (g == null) continue;
            g.setTitle(this.getTitle(g.getTitle(), Level2));
            groupList.add(g);
            this.getChildNode(groupMap, groupList, g.getId(), Level2);
        }
    }

    private String getTitle(String tile, int Level2) {
        StringBuilder sb = new StringBuilder();
        for (int j = 1; j < Level2; ++j) {
            sb.append("\u2014");
        }
        return sb.append(tile).toString();
    }

    @PostMapping(value={"/get_queryinfos"})
    public List<ZBQueryInfo> getQueryInfos(@RequestBody Map<String, String> params) {
        String groupId = params.get("queryGroup");
        return this.zbQueryInfoService.getQueryInfoByGroup(groupId);
    }
}

