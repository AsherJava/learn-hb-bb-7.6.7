/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.common.itree.ITree
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.transmission.data.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeGroupDTO;
import com.jiuqi.nr.transmission.data.exception.SchemeGroupException;
import com.jiuqi.nr.transmission.data.exception.SchemeGroupServiceException;
import com.jiuqi.nr.transmission.data.service.ISchemeGroupService;
import com.jiuqi.nr.transmission.data.vo.SchemeGroupNodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/sync/scheme/"})
@Api(tags={"\u591a\u7ea7\u90e8\u7f72\uff0c\u65b9\u6848\u5206\u7ec4\u670d\u52a1"})
public class SyncSchemeGroupController {
    @Autowired
    private ISchemeGroupService schemGroupService;

    @ApiOperation(value="\u65b0\u589e\u5206\u7ec4")
    @PostMapping(value={"insert_group"})
    public boolean insertGroup(@RequestBody SyncSchemeGroupDTO groupDTO) throws JQException {
        try {
            return this.schemGroupService.insert(groupDTO);
        }
        catch (SchemeGroupServiceException e) {
            throw new JQException((ErrorEnum)SchemeGroupException.INSERT_CHILDREN_GROUP_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u5206\u7ec4")
    @PostMapping(value={"delete_group"})
    public boolean deleteGroup(@RequestBody SyncSchemeGroupDTO groupDTO) throws Exception {
        try {
            return this.schemGroupService.delete(groupDTO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SchemeGroupException.DELETE_CHILDREN_GROUP_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u641c\u7d22\u786e\u5b9a\u5206\u7ec4")
    @PostMapping(value={"get_group"})
    public SyncSchemeGroupDTO getGroup(@RequestBody SyncSchemeGroupDTO groupDTO) {
        return this.schemGroupService.get(groupDTO);
    }

    @ApiOperation(value="\u4fee\u6539\u5206\u7ec4")
    @PostMapping(value={"update_group"})
    public boolean updateGroup(@RequestBody SyncSchemeGroupDTO groupDTO) throws JQException {
        try {
            return this.schemGroupService.update(groupDTO);
        }
        catch (SchemeGroupServiceException e) {
            throw new JQException((ErrorEnum)SchemeGroupException.UPDATE_GROUP_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u5224\u65ad\u662f\u5426\u5b58\u5728\u76f8\u540ctitle\u7684\u5206\u7ec4")
    @PostMapping(value={"is_same_title"})
    public boolean isSameTitle(@RequestBody SyncSchemeGroupDTO groupDTO) {
        SyncSchemeGroupDTO byTitle = this.schemGroupService.getByTitle(groupDTO);
        return byTitle == null;
    }

    @ApiOperation(value="\u6a21\u7cca\u67e5\u8be2\u5206\u7ec4")
    @PostMapping(value={"list_group"})
    public List<SyncSchemeGroupDTO> listGroup(@RequestBody SyncSchemeGroupDTO groupDTO) {
        List<SyncSchemeGroupDTO> search = this.schemGroupService.search(groupDTO);
        return search;
    }

    @ApiOperation(value="\u521d\u59cb\u5316\u5206\u7ec4\u6811")
    @GetMapping(value={"init_group_tree"})
    public List<ITree<SchemeGroupNodeVO>> initTree() {
        List<ITree<SchemeGroupNodeVO>> root = this.schemGroupService.getRoot();
        return root;
    }

    @ApiOperation(value="\u83b7\u53d6\u8282\u70b9\u7684\u76f4\u63a5\u4e0b\u7ea7\u8282\u70b9")
    @PostMapping(value={"get_children_group_node"})
    public List<ITree<SchemeGroupNodeVO>> getChildrenNode(@RequestBody SyncSchemeGroupDTO groupTreeParam) {
        return this.schemGroupService.getChildren(groupTreeParam);
    }

    @ApiOperation(value="\u5b9a\u4f4d\u5206\u7ec4\u6811")
    @PostMapping(value={"location_group_tree"})
    public List<ITree<SchemeGroupNodeVO>> locationTree(@RequestBody SyncSchemeGroupDTO groupTreeParam) {
        List<ITree<SchemeGroupNodeVO>> location = this.schemGroupService.location(groupTreeParam);
        return location;
    }
}

