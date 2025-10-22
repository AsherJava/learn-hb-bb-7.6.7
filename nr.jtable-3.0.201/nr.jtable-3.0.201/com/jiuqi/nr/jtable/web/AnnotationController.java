/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.annotation.output.CellAnnotationComment
 *  com.jiuqi.nr.annotation.output.CellAnnotationContent
 *  com.jiuqi.nr.annotation.output.CellAnnotationResult
 *  com.jiuqi.nr.basedata.select.bean.BaseDataInfo
 *  com.jiuqi.nr.common.itree.ITree
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.jtable.web;

import com.jiuqi.nr.annotation.output.CellAnnotationComment;
import com.jiuqi.nr.annotation.output.CellAnnotationContent;
import com.jiuqi.nr.annotation.output.CellAnnotationResult;
import com.jiuqi.nr.basedata.select.bean.BaseDataInfo;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.jtable.params.input.AnnotationTypePositionCondition;
import com.jiuqi.nr.jtable.params.input.AnnotationTypeQueryCondition;
import com.jiuqi.nr.jtable.params.input.AnnotationTypeSearchCondition;
import com.jiuqi.nr.jtable.params.input.CellAnnotationQueryCondition;
import com.jiuqi.nr.jtable.params.input.FormAnnotationCommentCondition;
import com.jiuqi.nr.jtable.params.input.FormAnnotationCondition;
import com.jiuqi.nr.jtable.params.input.FormAnnotationDeleteCondition;
import com.jiuqi.nr.jtable.params.input.FormAnnotationQueryCondition;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.service.IAnnotationApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/jtable"})
@Api(value="/api/jtable", tags={"\u62a5\u8868\u7ec4\u4ef6\u6279\u6ce8\u529f\u80fdrest\u63a5\u53e3"})
public class AnnotationController {
    @Autowired
    private IAnnotationApplyService annotationApplyService;

    @RequestMapping(value={"/actions/query/annotation"}, method={RequestMethod.POST})
    @ApiOperation(value="\u67e5\u8be2\u4e00\u4e2a\u5355\u5143\u683c\u7684\u6279\u6ce8\u4fe1\u606f")
    public CellAnnotationResult queryAnnotation(@Valid @RequestBody CellAnnotationQueryCondition cellAnnotationQueryCondition) {
        return this.annotationApplyService.queryCellAnnotation(cellAnnotationQueryCondition);
    }

    @RequestMapping(value={"/actions/query/annotations"}, method={RequestMethod.POST})
    @ApiOperation(value="\u67e5\u8be2\u4e00\u4e2a\u8868\u683c\u7684\u6279\u6ce8\u4fe1\u606f")
    public List<CellAnnotationContent> queryAnnotation(@Valid @RequestBody FormAnnotationQueryCondition formAnnotationQueryCondition) {
        return this.annotationApplyService.queryFormAnnotationDetailed(formAnnotationQueryCondition);
    }

    @RequestMapping(value={"/actions/change/annotation"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6dfb\u52a0\u6216\u8005\u4fee\u6539\u6279\u6ce8")
    public CellAnnotationContent changeAnnotation(@Valid @RequestBody FormAnnotationCondition formAnnotationCondition) {
        return this.annotationApplyService.saveOrUpdateFormAnnotation(formAnnotationCondition);
    }

    @RequestMapping(value={"/actions/remove/annotation"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6279\u91cf\u5220\u9664\u6279\u6ce8")
    public ReturnInfo removeAnnotation(@Valid @RequestBody FormAnnotationDeleteCondition formAnnotationDeleteCondition) {
        return this.annotationApplyService.removeFormAnnotation(formAnnotationDeleteCondition);
    }

    @RequestMapping(value={"/actions/change/comment"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6dfb\u52a0\u6216\u8005\u4fee\u6539\u8bc4\u8bba")
    public CellAnnotationComment changeComment(@Valid @RequestBody FormAnnotationCommentCondition formAnnotationCommentCondition) {
        return this.annotationApplyService.saveOrUpdateFormAnnotationComment(formAnnotationCommentCondition);
    }

    @RequestMapping(value={"/actions/remove/comment"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6279\u91cf\u5220\u9664\u8bc4\u8bba")
    public ReturnInfo removeComment(@Valid @RequestBody FormAnnotationDeleteCondition formAnnotationDeleteCondition) {
        return this.annotationApplyService.removeFormAnnotationComment(formAnnotationDeleteCondition);
    }

    @RequestMapping(value={"/actions/query/type"}, method={RequestMethod.POST})
    @ApiOperation(value="\u67e5\u8be2\u6279\u6ce8\u7c7b\u578b\u6811\u5f62")
    public ITree<BaseDataInfo> queryAnnotationTypeTree(@Valid @RequestBody AnnotationTypeQueryCondition condition) {
        return this.annotationApplyService.queryAnnotationTypeTree(condition);
    }

    @RequestMapping(value={"/actions/positioning/type"}, method={RequestMethod.POST})
    @ApiOperation(value="\u5b9a\u4f4d\u6279\u6ce8\u7c7b\u578b")
    public ITree<BaseDataInfo> positioningAnnotationType(@Valid @RequestBody AnnotationTypePositionCondition condition) {
        return this.annotationApplyService.positioningAnnotationType(condition);
    }

    @RequestMapping(value={"/actions/search/type"}, method={RequestMethod.POST})
    @ApiOperation(value="\u641c\u7d22\u6279\u6ce8\u7c7b\u578b")
    public ITree<BaseDataInfo> searchAnnotationType(@Valid @RequestBody AnnotationTypeSearchCondition condition) {
        return this.annotationApplyService.searchAnnotationType(condition);
    }
}

