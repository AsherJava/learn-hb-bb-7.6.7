/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.lwtree.web;

import com.jiuqi.nr.lwtree.provider.impl.LightTreeProvider;
import com.jiuqi.nr.lwtree.request.LightTreeLoadParam;
import com.jiuqi.nr.lwtree.response.INodeInfos;
import com.jiuqi.nr.lwtree.response.LightNodeData;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/itree/lwtree"})
@Api(tags={"\u8f7b\u91cf\u7ea7\u4e3b\u4f53\u6811\u7ec4\u4ef6"})
public class LightTreeController {
    private static final Logger log = LoggerFactory.getLogger(LightTreeController.class);

    @ApiOperation(value="\u52a0\u8f7d\u8ddf\u8282\u70b9")
    @RequestMapping(value={"/loading-tree"}, method={RequestMethod.POST})
    public IReturnObject<INodeInfos<LightNodeData>> queryRoots(@Valid @RequestBody LightTreeLoadParam loadParam) {
        IReturnObject instance = null;
        INodeInfos roots = null;
        try {
            loadParam.checkPara();
            LightTreeProvider provider = new LightTreeProvider(loadParam);
            roots = provider.loadingTree();
            instance = IReturnObject.getSuccessInstance(roots);
        }
        catch (Exception e) {
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), roots);
            log.error(e.getMessage(), e);
        }
        return instance;
    }

    @ApiOperation(value="\u52a0\u8f7d\u5b50\u8282\u70b9")
    @RequestMapping(value={"/loading-children"}, method={RequestMethod.POST})
    public IReturnObject<INodeInfos<LightNodeData>> queryChildren(@Valid @RequestBody LightTreeLoadParam loadParam) {
        IReturnObject instance = null;
        INodeInfos nodesInfo = null;
        try {
            loadParam.checkPara();
            LightTreeProvider provider = new LightTreeProvider(loadParam);
            nodesInfo = provider.getChildren();
            instance = IReturnObject.getSuccessInstance(nodesInfo);
        }
        catch (Exception e) {
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), nodesInfo);
            log.error(e.getMessage(), e);
        }
        return instance;
    }

    @ApiOperation(value="\u6811\u8282\u70b9\u641c\u7d22")
    @RequestMapping(value={"/loading-search-nodes"}, method={RequestMethod.POST})
    public IReturnObject<INodeInfos<LightNodeData>> searchNodes(@Valid @RequestBody LightTreeLoadParam loadParam) {
        IReturnObject instance = null;
        INodeInfos searchlist = null;
        try {
            loadParam.checkPara();
            LightTreeProvider provider = new LightTreeProvider(loadParam);
            searchlist = provider.searchNode();
            instance = IReturnObject.getSuccessInstance(searchlist);
        }
        catch (Exception e) {
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), searchlist);
            log.error(e.getMessage(), e);
        }
        return instance;
    }

    @ApiOperation(value="\u6811\u5f62\u8282\u70b9\u5b9a\u4f4d")
    @RequestMapping(value={"/loading-locate-tree"}, method={RequestMethod.POST})
    public IReturnObject<INodeInfos<LightNodeData>> LocateNode(@Valid @RequestBody LightTreeLoadParam loadParam) {
        IReturnObject instance = null;
        INodeInfos locate = null;
        try {
            loadParam.checkPara();
            LightTreeProvider provider = new LightTreeProvider(loadParam);
            locate = provider.locateTree();
            instance = IReturnObject.getSuccessInstance(locate);
        }
        catch (Exception e) {
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), locate);
            log.error(e.getMessage(), e);
        }
        return instance;
    }
}

