/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.summary.tree.core;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.summary.tree.core.ITreeService;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeQueryParamVO;
import com.jiuqi.nr.summary.tree.core.TreeServiceHolder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/summary_report/tree"})
public class TreeController {
    @Autowired
    private TreeServiceHolder treeServiceHolder;

    @PostMapping(value={"/roots"})
    public List<TreeNode> getTreeRoots(@RequestBody TreeQueryParamVO treeQueryParam) throws Exception {
        ITreeService treeService = this.treeServiceHolder.getTreeService(treeQueryParam.getTreeId());
        return treeService.getRoots(treeQueryParam.getQueryParam());
    }

    @PostMapping(value={"/childs"})
    public List<TreeNode> getTreeChilds(@RequestBody TreeQueryParamVO treeQueryParam) throws Exception {
        ITreeService treeService = this.treeServiceHolder.getTreeService(treeQueryParam.getTreeId());
        return treeService.getChilds(treeQueryParam.getQueryParam());
    }
}

