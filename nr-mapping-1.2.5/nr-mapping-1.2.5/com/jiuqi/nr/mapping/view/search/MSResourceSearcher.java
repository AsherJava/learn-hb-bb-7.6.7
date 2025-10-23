/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink
 *  com.jiuqi.nvwa.resourceview.query.NodeType
 *  com.jiuqi.nvwa.resourceview.search.IResourceSearcher
 *  com.jiuqi.nvwa.resourceview.search.SearchResult
 */
package com.jiuqi.nr.mapping.view.search;

import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.mapping.bean.MappingScheme;
import com.jiuqi.nr.mapping.service.MappingSchemeService;
import com.jiuqi.nvwa.resourceview.query.NodeType;
import com.jiuqi.nvwa.resourceview.search.IResourceSearcher;
import com.jiuqi.nvwa.resourceview.search.SearchResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class MSResourceSearcher
implements IResourceSearcher {
    protected final Logger logger = LoggerFactory.getLogger(MSResourceSearcher.class);
    @Autowired
    private MappingSchemeService mapping;
    @Autowired
    private IDesignTimeViewController designTime;
    @Autowired
    private RunTimeAuthViewController runTime;

    public List<SearchResult> search(String keyword) {
        ArrayList<SearchResult> res = new ArrayList<SearchResult>();
        List<MappingScheme> mappings = this.mapping.fuzzySearch(keyword);
        HashMap groups = new HashMap();
        if (!CollectionUtils.isEmpty(mappings)) {
            mappings.forEach(m -> {
                SearchResult rs = new SearchResult();
                rs.setName(m.getCode());
                rs.setTitle(m.getTitle());
                rs.setType(NodeType.NODE_DATA);
                rs.setResTypeId("com.jiuqi.nr.mappingScheme");
                ArrayList<String> ids = new ArrayList<String>();
                ArrayList<String> titles = new ArrayList<String>();
                try {
                    ids.add("T@" + m.getTask());
                    titles.add(this.runTime.queryTaskDefine(m.getTask()).getTitle());
                    ids.add("F@" + m.getFormScheme());
                    titles.add(this.runTime.getFormScheme(m.getFormScheme()).getTitle());
                    ids.add(m.getKey());
                    titles.add(m.getTitle());
                    List links = this.designTime.getGroupLinkByTaskKey(m.getTask());
                    if (!CollectionUtils.isEmpty(links)) {
                        this.getGroupPath(((DesignTaskGroupLink)links.get(0)).getGroupKey(), groups, ids, titles);
                    }
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
                rs.setPathIds(ids);
                rs.setPathTitles(titles);
                res.add(rs);
            });
        }
        return res;
    }

    private void getGroupPath(String groupKey, Map<String, DesignTaskGroupDefine> groups, List<String> ids, List<String> titles) {
        DesignTaskGroupDefine group = groups.get(groupKey);
        if (group == null) {
            group = this.designTime.queryTaskGroupDefine(groupKey);
            groups.put(groupKey, group);
        }
        ids.add(0, "G@" + groupKey);
        titles.add(0, group.getTitle());
        if (StringUtils.hasText(group.getParentKey())) {
            this.getGroupPath(group.getParentKey(), groups, ids, titles);
        }
    }
}

