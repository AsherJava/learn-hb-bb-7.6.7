/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.task.form.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.task.form.dto.DragNodeDTO;
import com.jiuqi.nr.task.form.dto.DragResultDTO;
import com.jiuqi.nr.task.form.field.service.IDataFieldService;
import com.jiuqi.nr.task.form.form.exception.FormException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v2/table/"})
@Api(tags={"\u5b58\u50a8\u8868\u3001\u6307\u6807\u4ee5\u53ca\u5b58\u50a8\u8868\u8303\u56f4\u5185\u8d44\u6e90\u7684Controller"})
public class TableDataController {
    public static final String URL = "/api/v2/table/";
    @Autowired
    private IDataFieldService dataFieldService;

    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u6620\u5c04\u6811\u5f62")
    @PostMapping(value={"/field-tree/root/get"})
    public List<ITree<DataSchemeNode>> getFieldTreeRoot(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) throws JQException {
        try {
            return this.dataFieldService.getFieldTreeRoot(param);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_ERROE_005, e.getMessage());
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u6620\u5c04\u6811\u5f62")
    @PostMapping(value={"/field-tree/child/get"})
    public List<ITree<DataSchemeNode>> getFieldTreeChild(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) throws JQException {
        try {
            return this.dataFieldService.getFieldTreeChild(param);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_ERROE_005, e.getMessage());
        }
    }

    @ApiOperation(value="\u6307\u6807\u6620\u5c04\u6811\u5f62\u5b9a\u4f4d")
    @PostMapping(value={"/field-tree/path/get"})
    public List<ITree<DataSchemeNode>> getFieldTreePath(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) throws JQException {
        try {
            return this.dataFieldService.getFieldTreePath(param);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_ERROE_005, e.getMessage());
        }
    }

    @ApiOperation(value="\u6307\u6807\u6620\u5c04\u641c\u7d22")
    @PostMapping(value={"/field-tree/filter"})
    public List<DataSchemeNode> filterFieldTree(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) throws JQException {
        try {
            return this.dataFieldService.filterFieldTree(param);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_ERROE_005, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6307\u6807")
    @PostMapping(value={"/drag-field/query/"})
    public DragResultDTO queryDragResult(@RequestBody List<DragNodeDTO> dragNodes) throws JQException {
        try {
            return this.dataFieldService.queryDragResult(dragNodes);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormException.FORM_ERROE_006, e.getMessage());
        }
    }
}

