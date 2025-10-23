/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dafafill.web;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dafafill.model.enums.ModelType;
import com.jiuqi.nr.dafafill.tree.DataFillSchemeTree;
import com.jiuqi.nr.dafafill.tree.DataFillTaskTree;
import com.jiuqi.nr.dafafill.tree.ResourceNode;
import com.jiuqi.nr.dafafill.tree.SearchTreeNode;
import com.jiuqi.nr.dafafill.tree.ZBSelector;
import com.jiuqi.nr.dafafill.web.vo.ReturnInfoVO;
import com.jiuqi.nr.dafafill.web.vo.ZBSelectorParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/datafill/resource"})
@Api(value="\u81ea\u5b9a\u4e49\u5f55\u5165\u8d44\u6e90\u6811\u5f62\u52a0\u8f7d")
public class DataFillResourceTreeController {
    @Autowired
    private DataFillSchemeTree schemeTree;
    @Autowired
    private DataFillTaskTree taskTree;
    @Autowired
    private ZBSelector zBSelector;

    @GetMapping(value={"/root-node"})
    @ApiOperation(value="\u52a0\u8f7d\u4e0b\u7ea7\u8282\u70b9")
    public List<ITree<ResourceNode>> getRootNode(@Valid ModelType modelType, String taskCode) {
        ArrayList<ITree<ResourceNode>> nodes = new ArrayList<ITree<ResourceNode>>();
        if (ModelType.SCHEME == modelType) {
            nodes.add(this.schemeTree.buildRootNode());
        } else {
            nodes.add(this.taskTree.buildRootNode(taskCode));
        }
        return nodes;
    }

    @GetMapping(value={"/children"})
    @ApiOperation(value="\u52a0\u8f7d\u4e0b\u7ea7\u8282\u70b9")
    public List<ITree<ResourceNode>> getChildrenNodes(@Valid ModelType modelType, String key, int nodeType, String code) {
        if (ModelType.SCHEME == modelType) {
            return this.schemeTree.buildChildrenNodes(key, nodeType, code);
        }
        return this.taskTree.buildChildrenNodes(key, nodeType, code);
    }

    @GetMapping(value={"/locate"})
    @ApiOperation(value="\u5df2\u9009\u62e9\u5b57\u6bb5\u5b9a\u4f4d")
    public List<String> locateResourceTree(@Valid ModelType modelType, String id) {
        if (ModelType.SCHEME == modelType) {
            return this.schemeTree.locate(id);
        }
        return this.taskTree.locate(id);
    }

    @GetMapping(value={"/search"})
    @ApiOperation(value="\u6a21\u7cca\u641c\u7d22")
    public List<SearchTreeNode> filterResourceTree(@Valid ModelType modelType, String fuzzyKey) {
        if (ModelType.SCHEME == modelType) {
            return this.schemeTree.search(fuzzyKey);
        }
        return this.taskTree.search(fuzzyKey);
    }

    @PostMapping(value={"/getQueryFiled"})
    @ApiOperation(value="\u5c06\u6307\u6807\u9009\u62e9\u5668\u9009\u62e9\u7684\u6307\u6807\u8f6c\u5316\u4e3aqueryField")
    public ReturnInfoVO getQueryFiled(@Valid @RequestBody ZBSelectorParam param) {
        return this.zBSelector.getQueryFiled(param);
    }
}

