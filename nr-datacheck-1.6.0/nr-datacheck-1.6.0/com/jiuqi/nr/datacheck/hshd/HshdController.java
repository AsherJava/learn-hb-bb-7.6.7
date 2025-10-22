/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.lwtree.response.INodeInfos
 *  com.jiuqi.nr.lwtree.response.LightNodeData
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.datacheck.hshd;

import com.jiuqi.nr.datacheck.hshd.service.IHshdService;
import com.jiuqi.nr.datacheck.hshd.vo.AssTaskVO;
import com.jiuqi.nr.datacheck.hshd.vo.HshdCheckPM;
import com.jiuqi.nr.datacheck.hshd.vo.HshdCheckResult;
import com.jiuqi.nr.datacheck.hshd.vo.HshdTreePM;
import com.jiuqi.nr.lwtree.response.INodeInfos;
import com.jiuqi.nr.lwtree.response.LightNodeData;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/datacheck/hshd"})
public class HshdController {
    @Autowired
    private IHshdService hshdService;

    @PostMapping(value={"/tree/root"})
    @ApiOperation(value="\u67e5\u8be2\u6811\u8ddf\u8282\u70b9")
    public IReturnObject<INodeInfos<LightNodeData>> queryRoots(@RequestBody HshdTreePM treePM) {
        return this.hshdService.queryRoots(treePM);
    }

    @PostMapping(value={"/tree/children"})
    @ApiOperation(value="\u67e5\u8be2\u6811\u5b50\u8282\u70b9")
    public IReturnObject<INodeInfos<LightNodeData>> queryChildren(@RequestBody HshdTreePM treePM) {
        return this.hshdService.queryChildren(treePM);
    }

    @PostMapping(value={"/tree/search"})
    @ApiOperation(value="\u641c\u7d22\u6811")
    public IReturnObject<INodeInfos<LightNodeData>> searchNodes(@RequestBody HshdTreePM treePM) {
        return this.hshdService.searchNodes(treePM);
    }

    @PostMapping(value={"/tree/locate"})
    @ApiOperation(value="\u6811\u5f62\u5b9a\u4f4d")
    public IReturnObject<INodeInfos<LightNodeData>> locateNode(@RequestBody HshdTreePM treePM) {
        return this.hshdService.locateNode(treePM);
    }

    @GetMapping(value={"/config"})
    @ApiOperation(value="\u67e5\u8be2\u5173\u8054\u4efb\u52a1\u7684\u914d\u7f6e")
    public Map<String, AssTaskVO> getAssTaskConfig(@RequestParam String formScheme) {
        return this.hshdService.getAssTaskConfig(formScheme);
    }

    @PostMapping(value={"/entityCheckUp"})
    @ApiOperation(value="\u6267\u884c\u6237\u6570\u6838\u5bf9\u5904\u7406\u903b\u8f91")
    public HshdCheckResult entityCheckUp(@RequestBody HshdCheckPM checkPM) {
        return this.hshdService.entityCheckUp(checkPM);
    }
}

