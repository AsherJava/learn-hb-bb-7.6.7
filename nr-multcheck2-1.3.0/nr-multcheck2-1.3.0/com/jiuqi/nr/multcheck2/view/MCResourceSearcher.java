/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nvwa.resourceview.query.NodeType
 *  com.jiuqi.nvwa.resourceview.search.IResourceSearcher
 *  com.jiuqi.nvwa.resourceview.search.SearchResult
 */
package com.jiuqi.nr.multcheck2.view;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.GlobalType;
import com.jiuqi.nr.multcheck2.common.MultcheckUtil;
import com.jiuqi.nr.multcheck2.common.SchemeType;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nvwa.resourceview.query.NodeType;
import com.jiuqi.nvwa.resourceview.search.IResourceSearcher;
import com.jiuqi.nvwa.resourceview.search.SearchResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class MCResourceSearcher
implements IResourceSearcher {
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private RunTimeAuthViewController runTime;

    public List<SearchResult> search(String keyword) {
        ArrayList<SearchResult> res = new ArrayList<SearchResult>();
        List<MultcheckScheme> schemes = this.schemeService.fuzzySearchScheme(keyword);
        if (CollectionUtils.isEmpty(schemes)) {
            return res;
        }
        List allTask = this.runTime.getAllTaskDefines();
        if (CollectionUtils.isEmpty(allTask)) {
            return res;
        }
        HashMap<String, Object> taskMap = new HashMap<String, Object>();
        HashMap<String, FormSchemeDefine> formMap = new HashMap<String, FormSchemeDefine>();
        for (Object t : allTask) {
            if (taskMap.containsKey(t.getKey())) continue;
            taskMap.put(t.getKey(), t);
            try {
                List formSchemeDefines = this.runTime.queryFormSchemeByTask(t.getKey());
                if (CollectionUtils.isEmpty(formSchemeDefines)) continue;
                for (FormSchemeDefine f : formSchemeDefines) {
                    formMap.put(f.getKey(), f);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        String type = MultcheckUtil.getType();
        if (type.equals(GlobalType.EXIST.value())) {
            for (MultcheckScheme s : schemes) {
                SearchResult r = new SearchResult();
                r.setName(s.getCode());
                r.setTitle(s.getTitle());
                r.setType(NodeType.NODE_DATA);
                r.setResTypeId("com.jiuqi.nr.multcheck2");
                ArrayList<String> ids = new ArrayList<String>();
                TaskDefine task = (TaskDefine)taskMap.get(s.getTask());
                FormSchemeDefine fs = (FormSchemeDefine)formMap.get(s.getFormScheme());
                if (task == null || fs == null) continue;
                ids.add("T@" + task.getKey());
                ids.add("F@" + fs.getKey());
                ids.add(s.getKey());
                ArrayList<String> paths = new ArrayList<String>();
                paths.add(task.getTitle());
                paths.add(fs.getTitle());
                paths.add(s.getTitle());
                r.setPathIds(ids);
                r.setPathTitles(paths);
                if (s.getType().value() == SchemeType.COMMON.value()) {
                    r.setIcon("icon nr-iconfont icon-16_DH_A_NR_piliangshenhe");
                } else {
                    r.setIcon("icon nr-iconfont icon-16_DH_A_NR_piliangshangbao");
                }
                res.add(r);
            }
            return res;
        }
        HashMap<String, TaskGroupDefine> groups = new HashMap<String, TaskGroupDefine>();
        for (MultcheckScheme s : schemes) {
            TaskDefine task = (TaskDefine)taskMap.get(s.getTask());
            FormSchemeDefine fs = (FormSchemeDefine)formMap.get(s.getFormScheme());
            if (task == null || fs == null) continue;
            SearchResult r = new SearchResult();
            r.setName(s.getCode());
            r.setTitle(s.getTitle());
            r.setType(NodeType.NODE_DATA);
            r.setResTypeId("com.jiuqi.nr.multcheck2");
            ArrayList<String> ids = new ArrayList<String>();
            ArrayList<String> titles = new ArrayList<String>();
            try {
                ids.add("T@" + s.getTask());
                titles.add(task.getTitle());
                ids.add("F@" + s.getFormScheme());
                titles.add(fs.getTitle());
                ids.add(s.getKey());
                titles.add(s.getTitle());
                List parentGroups = this.runTime.getGroupByTask(s.getTask());
                if (!CollectionUtils.isEmpty(parentGroups)) {
                    for (TaskGroupDefine g : parentGroups) {
                        groups.put(g.getKey(), g);
                    }
                    this.getGroupPath(((TaskGroupDefine)parentGroups.get(0)).getKey(), groups, ids, titles);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            r.setPathIds(ids);
            r.setPathTitles(titles);
            res.add(r);
        }
        return res;
    }

    private void getGroupPath(String groupKey, Map<String, TaskGroupDefine> groups, List<String> ids, List<String> titles) {
        TaskGroupDefine group = groups.get(groupKey);
        if (group == null) {
            group = this.runTime.queryTaskGroupDefine(groupKey);
            groups.put(groupKey, group);
        }
        ids.add(0, "G@" + groupKey);
        titles.add(0, group.getTitle());
        if (StringUtils.hasText(group.getParentKey())) {
            this.getGroupPath(group.getParentKey(), groups, ids, titles);
        }
    }
}

