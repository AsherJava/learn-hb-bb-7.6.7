/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.zbquery.rest;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.zbquery.bean.facade.IReportAddExtendProvider;
import com.jiuqi.nr.zbquery.bean.facade.IResourceTreeNode;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTreeNodeDTO;
import com.jiuqi.nr.zbquery.bean.impl.SearchTreeNodeDTO;
import com.jiuqi.nr.zbquery.extend.ZBQueryExtendProviderManager;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.rest.param.FieldSelectParam;
import com.jiuqi.nr.zbquery.rest.param.NodePathQueryParam;
import com.jiuqi.nr.zbquery.rest.param.ResourceTreeQueryParam;
import com.jiuqi.nr.zbquery.rest.param.SearchNodeQueryParam;
import com.jiuqi.nr.zbquery.service.ZBQueryResourceTreeService;
import com.jiuqi.nr.zbquery.util.DataChangeUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/zbquery/design/tree"})
public class ZBQueryResourceTreeController {
    @Autowired
    private ZBQueryResourceTreeService<IResourceTreeNode> zbTreeService;
    @Autowired
    private ZBQueryExtendProviderManager zbQueryExtendProviderManager;
    @Autowired(required=false)
    private List<IReportAddExtendProvider> reportAddExtendProviders;

    @PostMapping(value={"positioning"})
    @RequiresPermissions(value={"nr:zbquery:resourcetree"})
    public List<ITree<IResourceTreeNode>> queryResourceTreePath(@RequestBody ResourceTreeQueryParam param) {
        ResourceTreeNodeDTO dataTreeNode = param.getDataTreeNode();
        Assert.notNull((Object)dataTreeNode, "parent must not be null");
        return this.zbTreeService.queryResourceTreePath(dataTreeNode);
    }

    @PostMapping(value={"children"})
    public List<ITree<IResourceTreeNode>> queryResourceTreeChildren(@RequestBody ResourceTreeQueryParam param) {
        ResourceTreeNodeDTO dataTreeNode = param.getDataTreeNode();
        Assert.notNull((Object)dataTreeNode, "parent must not be null");
        List<ITree<IResourceTreeNode>> iTrees = param.isAllChildren() ? this.zbTreeService.queryAllResourceTreeChildren(dataTreeNode) : this.zbTreeService.queryResourceTreeChildren(dataTreeNode);
        return DataChangeUtils.hiddenDimension(iTrees, param.getHiddenDimensions());
    }

    @PostMapping(value={"search"})
    public List<SearchTreeNodeDTO> filterResourceTree(@RequestBody SearchNodeQueryParam param) {
        return this.zbTreeService.filterResourceTree(param);
    }

    @PostMapping(value={"dimAttributes"})
    @RequiresPermissions(value={"nr:zbquery:resourcetree"})
    public ITree<IResourceTreeNode> queryDimensionAttributeField(@RequestBody ResourceTreeQueryParam param) {
        QueryObject queryObject = param.getQueryObject();
        Assert.notNull((Object)queryObject, "queryObject must not be null");
        return this.zbTreeService.queryDimensionAttributeField(queryObject);
    }

    @PostMapping(value={"dimensions"})
    public List<ITree<IResourceTreeNode>> queryDimsByFieldNode(@RequestBody ResourceTreeQueryParam param) {
        ResourceTreeNodeDTO dataTreeNode = param.getDataTreeNode();
        Assert.notNull((Object)dataTreeNode, "fieldNode must not be null");
        List<ITree<IResourceTreeNode>> iTrees = this.zbTreeService.queryDimsByFieldNode(param);
        return DataChangeUtils.hiddenDimension(iTrees, param.getHiddenDimensions());
    }

    @PostMapping(value={"nodepath"})
    public List<String> queryTreeNodePath(@RequestBody NodePathQueryParam param) {
        QueryObject queryObject = param.getQueryObject();
        Assert.notNull((Object)queryObject, "queryObject must not be null");
        return this.zbTreeService.queryTreeNodePath(queryObject);
    }

    @PostMapping(value={"scenegrouping"})
    @RequiresPermissions(value={"nr:zbquery:resourcetree"})
    public Map<String, List<QueryObject>> handleSceneGrouping(@RequestBody ResourceTreeQueryParam param) {
        ZBQueryModel zbQueryModel = param.getZbQueryModel();
        Assert.notNull((Object)zbQueryModel, "ZBQueryModel must not be null");
        return this.zbTreeService.handleSceneGrouping(zbQueryModel);
    }

    @PostMapping(value={"fieldselect"})
    @RequiresPermissions(value={"nr:zbquery:resourcetree"})
    public List<IResourceTreeNode> handleFieldSelect(@RequestBody FieldSelectParam param) {
        List<IResourceTreeNode> resourceTreeNodes = this.zbTreeService.handleFieldSelect(param.getZbs(), param.getExtendedDatas());
        return DataChangeUtils.hiddenDimension4Node(resourceTreeNodes, param.getHiddenDimensions());
    }

    @PostMapping(value={"fieldselect/get_reporttasks"})
    public List<String> getReportTasks(@RequestBody FieldSelectParam param) {
        if (this.zbQueryExtendProviderManager.getExtendProvider() != null) {
            return this.zbQueryExtendProviderManager.getExtendProvider().getReportTasks(param.getExtendedDatas());
        }
        return Collections.emptyList();
    }

    @GetMapping(value={"getReportAddExtend"})
    public IReportAddExtendProvider getReportAddExtend() {
        Collections.sort(this.reportAddExtendProviders, (o1, o2) -> Double.compare(o2.getOrder(), o1.getOrder()));
        return this.reportAddExtendProviders.get(0);
    }
}

