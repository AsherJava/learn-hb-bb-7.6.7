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
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.calibre2.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.ICalibreGroupService;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.domain.CalibreGroupDTO;
import com.jiuqi.nr.calibre2.exception.CalibreGroupException;
import com.jiuqi.nr.calibre2.exception.CalibreGroupServiceException;
import com.jiuqi.nr.calibre2.service.ICalibreGroupManageService;
import com.jiuqi.nr.calibre2.vo.GroupNodeVO;
import com.jiuqi.nr.common.itree.ITree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/calibre2/"})
@Api(tags={"\u53e3\u5f84\u7ba1\u7406\uff1a\u53e3\u5f84\u5206\u7ec4\u670d\u52a1"})
public class CalibreGroupController {
    @Autowired
    private ICalibreGroupService calibreGroupService;
    @Autowired
    private ICalibreGroupManageService calibreGroupManageService;
    @Autowired
    private ICalibreDefineService calibreDefineService;

    @ApiOperation(value="\u65b0\u589e\u5206\u7ec4")
    @PostMapping(value={"insert_group"})
    public UpdateResult insertGroup(@RequestBody CalibreGroupDTO groupDTO) throws JQException {
        try {
            this.calibreGroupService.add(groupDTO);
        }
        catch (CalibreGroupServiceException e) {
            throw new JQException((ErrorEnum)CalibreGroupException.INSERT_CHILDREN_GROUP_ERROR, e.getMessage());
        }
        UpdateResult result = new UpdateResult();
        result.setKey(groupDTO.getKey());
        result.setCode(groupDTO.getName());
        return result;
    }

    @ApiOperation(value="\u5220\u9664\u5206\u7ec4")
    @PostMapping(value={"delete_group"})
    public UpdateResult deleteGroup(@RequestBody CalibreGroupDTO groupDTO) throws JQException {
        UpdateResult result;
        try {
            Result<UpdateResult> delete = this.calibreGroupService.delete(groupDTO);
            result = delete.getData();
        }
        catch (CalibreGroupServiceException e) {
            throw new JQException((ErrorEnum)CalibreGroupException.DELETE_CHILDREN_GROUP_ERROR, e.getMessage());
        }
        return result;
    }

    @ApiOperation(value="\u641c\u7d22\u786e\u5b9a\u5206\u7ec4")
    @GetMapping(value={"get_group/{groupId}"})
    public CalibreGroupDTO searchGroup(@PathVariable String groupId) {
        Result<CalibreGroupDTO> result = this.calibreGroupService.get(groupId);
        return result.getData();
    }

    @ApiOperation(value="\u67e5\u627e\u53e3\u5f84\u5206\u7ec4\u540d\u79f0")
    @PostMapping(value={"get_define_name"})
    public CalibreGroupDTO searchCalibreName(@RequestBody CalibreDefineDTO calibreDefineDTO) {
        Result<CalibreDefineDTO> result = this.calibreDefineService.get(calibreDefineDTO);
        Result<CalibreGroupDTO> calibreGroupDTOResult = this.calibreGroupService.get(result.getData().getGroup());
        return calibreGroupDTOResult.getData();
    }

    @ApiOperation(value="\u4fee\u6539\u5206\u7ec4")
    @PostMapping(value={"update_group"})
    public UpdateResult updateGroup(@RequestBody CalibreGroupDTO groupDTO) throws JQException {
        UpdateResult result;
        try {
            Result<UpdateResult> update = this.calibreGroupService.update(groupDTO);
            result = update.getData();
        }
        catch (CalibreGroupServiceException e) {
            throw new JQException((ErrorEnum)CalibreGroupException.UPDATE_GROUP_ERROR, e.getMessage());
        }
        return result;
    }

    @ApiOperation(value="\u6a21\u7cca\u67e5\u8be2\u5206\u7ec4")
    @PostMapping(value={"list_group"})
    public List<CalibreGroupDTO> listGroup(@RequestBody CalibreGroupDTO groupDTO) {
        Result<List<CalibreGroupDTO>> result = this.calibreGroupService.list(groupDTO);
        return result.getData();
    }

    @ApiOperation(value="\u521d\u59cb\u5316\u5206\u7ec4\u6811")
    @GetMapping(value={"init_group_tree"})
    public List<ITree<GroupNodeVO>> initTree() {
        return this.calibreGroupManageService.initTree();
    }

    @ApiOperation(value="\u83b7\u53d6\u8282\u70b9\u7684\u76f4\u63a5\u4e0b\u7ea7\u8282\u70b9")
    @PostMapping(value={"get_children_group_node"})
    public List<ITree<GroupNodeVO>> getChildrenNode(@RequestBody CalibreGroupDTO groupTreeParam) {
        return this.calibreGroupManageService.getChildrenNodes(groupTreeParam);
    }

    @ApiOperation(value="\u5b9a\u4f4d\u5206\u7ec4\u6811")
    @PostMapping(value={"location_group_tree"})
    public List<ITree<GroupNodeVO>> locationTree(@RequestBody CalibreGroupDTO groupTreeParam) {
        return this.calibreGroupManageService.locationTreeNode(groupTreeParam);
    }
}

