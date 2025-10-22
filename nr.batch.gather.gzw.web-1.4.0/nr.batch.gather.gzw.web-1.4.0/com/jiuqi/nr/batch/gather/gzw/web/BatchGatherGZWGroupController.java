/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.batch.gather.gzw.web;

import com.jiuqi.nr.batch.gather.gzw.web.app.func.para.OpenGroupTreePagePara;
import com.jiuqi.nr.batch.gather.gzw.web.tree.selector.GroupSchemeTreeGZWProvider;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/batch-gather-GZW/group"})
@Api(tags={"\u6c47\u603b\u65b9\u6848-\u5206\u7ec4-\u901a\u7528API"})
public class BatchGatherGZWGroupController {
    @Resource
    private GroupSchemeTreeGZWProvider provider;

    @ResponseBody
    @ApiOperation(value="\u6c47\u603b\u65b9\u6848\u5206\u7ec4-\u6811\u5f62\u6784\u5efa")
    @PostMapping(value={"/made-group-tree"})
    public IReturnObject<List<ITree<IBaseNodeData>>> madeGroupTree(@RequestBody OpenGroupTreePagePara loadPara) {
        IReturnObject instance;
        List<ITree<IBaseNodeData>> tree = null;
        try {
            tree = this.provider.getGroupTree(loadPara);
            instance = IReturnObject.getSuccessInstance(tree);
        }
        catch (Exception e) {
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), tree);
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return instance;
    }
}

