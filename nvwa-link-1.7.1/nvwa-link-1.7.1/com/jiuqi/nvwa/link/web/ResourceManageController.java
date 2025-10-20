/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nvwa.link.web;

import com.jiuqi.nvwa.link.collector.LinkResourceCollecter;
import com.jiuqi.nvwa.link.provider.CheckResult;
import com.jiuqi.nvwa.link.provider.ILinkResourceProvider;
import com.jiuqi.nvwa.link.provider.ResourceAppConfig;
import com.jiuqi.nvwa.link.provider.ResourceNode;
import com.jiuqi.nvwa.link.provider.SearchItem;
import com.jiuqi.nvwa.link.web.vo.AppInfoVO2;
import com.jiuqi.nvwa.link.web.vo.LinkItem;
import com.jiuqi.nvwa.link.web.vo.TreeNodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags={"\u5973\u5a32\u7a7f\u900f\u6846\u67b6\u63a5\u53e3"})
@RequestMapping(value={"/nvwa-link"})
@RestController
public class ResourceManageController {
    @Autowired
    private LinkResourceCollecter collecter;

    @ApiOperation(value="\u521d\u59cb\u5316\u8ddf\u8282\u70b9")
    @GetMapping(value={"/tree/initRoot"})
    public List<TreeNodeVO> getRootTreeNodes() {
        ArrayList<TreeNodeVO> nodes = new ArrayList<TreeNodeVO>();
        List<ILinkResourceProvider> providerList = this.collecter.getAllLinkResource();
        if (!CollectionUtils.isEmpty(providerList)) {
            nodes.addAll(providerList.stream().filter(ILinkResourceProvider::isVisible).map(provider -> this.buildRootTreeNode((ILinkResourceProvider)provider)).collect(Collectors.toList()));
        }
        return nodes;
    }

    private TreeNodeVO buildRootTreeNode(ILinkResourceProvider provider) {
        TreeNodeVO node = new TreeNodeVO();
        node.setKey(provider.getType());
        node.setCode(provider.getType());
        node.setTitle(provider.getTitle());
        node.setGroup(provider.getGroup());
        node.setIcons(provider.getIcon());
        node.setType(provider.getType());
        if ("TYPE_URLRESOURCE".equals(provider.getType())) {
            node.setIsLeaf(true);
            node.setLinkResource(true);
        } else if (provider.linkResource()) {
            node.setIsLeaf(true);
            node.setLinkResource(true);
            node.setParamList(provider.getParamsByResource(null));
        }
        return node;
    }

    @ApiOperation(value="\u83b7\u53d6\u6811\u5f62\u4e0b\u7ea7\u8282\u70b9")
    @GetMapping(value={"/tree/query-tree-child"})
    public List<TreeNodeVO> getChildNode(String type, String key, String extData) {
        ILinkResourceProvider provider = this.collecter.getLinkResourceProvider(type);
        ArrayList<TreeNodeVO> nodes = new ArrayList<TreeNodeVO>();
        if (provider != null) {
            String parentId = null;
            if (!provider.getType().equals(key)) {
                parentId = key;
            }
            List<ResourceNode> childResource = provider.getChildNodes(parentId, extData);
            nodes.addAll(childResource.stream().map(r -> this.buildTreeNode((ResourceNode)r, provider)).collect(Collectors.toList()));
        }
        return nodes;
    }

    private TreeNodeVO buildTreeNode(ResourceNode rs, ILinkResourceProvider provider) {
        TreeNodeVO tree = new TreeNodeVO();
        tree.setKey(rs.getId());
        tree.setTitle(rs.getTitle());
        tree.setIsLeaf(rs.isLeaf());
        tree.setType(provider.getType());
        tree.setGroup(provider.getGroup());
        tree.setCode(rs.getId());
        tree.setLinkResource(rs.isLinkResource());
        if (tree.isLinkResource()) {
            tree.setParamList(provider.getParamsByResource(rs));
        }
        if (StringUtils.hasText(rs.getIcon())) {
            tree.setIcons(rs.getIcon());
        }
        tree.setExtData(rs.getExtData());
        return tree;
    }

    @ApiOperation(value="\u5b9a\u4f4d\u6811\u8282\u70b9")
    @GetMapping(value={"/tree/locate-tree-node"})
    public List<String> locateTreeNode(String type, String key, String extData) {
        ArrayList<String> paths = new ArrayList<String>();
        ILinkResourceProvider provider = this.collecter.getLinkResourceProvider(type);
        if (provider != null) {
            paths.add(type);
            List<String> resourcePaths = provider.getPaths(key, extData);
            if (!CollectionUtils.isEmpty(resourcePaths)) {
                paths.addAll(resourcePaths);
            }
        }
        return paths;
    }

    @ApiOperation(value="\u83b7\u53d6\u8d44\u6e90\u7684\u524d\u7aefAPP\u4fe1\u606f")
    @PostMapping(value={"/resource/get-appInfo"})
    public AppInfoVO2 getAppInfo(@RequestBody LinkItem item) {
        ILinkResourceProvider provider = this.collecter.getLinkResourceProvider(item.getType());
        if (provider != null) {
            return new AppInfoVO2(provider.getAppInfoVO(item.getKey(), item.getExtData()));
        }
        return null;
    }

    @ApiOperation(value="\u68c0\u67e5\u8d44\u6e90\u5408\u6cd5\u6027\uff1a\u662f\u5426\u5b58\u5728\u3001\u76ee\u6807\u6743\u9650\u3001\u53c2\u6570\u6743\u9650\u7b49")
    @PostMapping(value={"/resource/check"})
    public CheckResult check(@RequestBody LinkItem item) {
        ResourceNode resource = null;
        ILinkResourceProvider provider = this.collecter.getLinkResourceProvider(item.getType());
        if (provider != null) {
            if (!provider.isVisible() && !provider.checkHidden()) {
                return new CheckResult(true, null, null);
            }
            CheckResult resourceCheck = provider.checkResource(item.getKey(), item.getExtData(), item.getLinkMsg());
            if (resourceCheck != null) {
                return resourceCheck;
            }
            resource = provider.getResource(item.getKey(), item.getExtData());
        }
        if (resource != null) {
            return new CheckResult(true, null, resource);
        }
        return new CheckResult(false, "\u76ee\u6807\u8d44\u6e90\u4e0d\u5b58\u5728\uff01", null);
    }

    @ApiOperation(value="\u7ec4\u7ec7\u8d44\u6e90\u7684\u524d\u7aefAPP\u7684\u53c2\u6570config")
    @PostMapping(value={"/resource/build-appConfig"})
    public ResourceAppConfig buildAppConfig(@RequestBody LinkItem item) {
        ILinkResourceProvider provider = this.collecter.getLinkResourceProvider(item.getType());
        if (provider != null) {
            return provider.buildAppConfig(item.getKey(), item.getExtData(), item.getLinkMsg());
        }
        return null;
    }

    @ApiOperation(value="\u8d44\u6e90\u6811\u641c\u7d22")
    @GetMapping(value={"/tree/search"})
    public List<SearchItem> search(String fuzzyKey) {
        if (!StringUtils.hasText(fuzzyKey)) {
            return null;
        }
        ArrayList<SearchItem> res = new ArrayList<SearchItem>();
        List<ILinkResourceProvider> providerList = this.collecter.getAllLinkResource();
        if (!CollectionUtils.isEmpty(providerList)) {
            providerList.stream().forEach(provider -> this.buildSearchItem((List<SearchItem>)res, (ILinkResourceProvider)provider, fuzzyKey));
        }
        return res;
    }

    private void buildSearchItem(List<SearchItem> res, ILinkResourceProvider provider, String keyword) {
        List<SearchItem> searchItems = provider.search(keyword);
        if (!CollectionUtils.isEmpty(searchItems)) {
            for (SearchItem item : searchItems) {
                item.getTitlePaths().add(0, provider.getTitle());
                item.getKeyPaths().add(0, provider.getType());
                res.add(item);
            }
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u8d44\u6e90\u5bf9\u8c61")
    @GetMapping(value={"/resource/find"})
    public ResourceNode getResource(String type, String key, String extData) {
        ILinkResourceProvider provider = this.collecter.getLinkResourceProvider(type);
        return provider.getResource(key, extData);
    }
}

