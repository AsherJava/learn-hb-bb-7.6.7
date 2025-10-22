/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.annotation.input.CellAnnotationQueryInfo
 *  com.jiuqi.nr.annotation.input.FormAnnotationDeleteInfo
 *  com.jiuqi.nr.annotation.input.FormAnnotationQueryInfo
 *  com.jiuqi.nr.annotation.input.FormCellAnnotationQueryInfo
 *  com.jiuqi.nr.annotation.input.SaveFormAnnotationCommentInfo
 *  com.jiuqi.nr.annotation.input.SaveFormAnnotationInfo
 *  com.jiuqi.nr.annotation.input.UpdateFormAnnotationCommentInfo
 *  com.jiuqi.nr.annotation.input.UpdateFormAnnotationInfo
 *  com.jiuqi.nr.annotation.output.CellAnnotationComment
 *  com.jiuqi.nr.annotation.output.CellAnnotationContent
 *  com.jiuqi.nr.annotation.output.CellAnnotationResult
 *  com.jiuqi.nr.annotation.output.FormAnnotationResult
 *  com.jiuqi.nr.annotation.service.IAnnotationService
 *  com.jiuqi.nr.annotation.service.IAnnotationTypeService
 *  com.jiuqi.nr.basedata.select.bean.BaseDataInfo
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.annotation.input.CellAnnotationQueryInfo;
import com.jiuqi.nr.annotation.input.FormAnnotationDeleteInfo;
import com.jiuqi.nr.annotation.input.FormAnnotationQueryInfo;
import com.jiuqi.nr.annotation.input.FormCellAnnotationQueryInfo;
import com.jiuqi.nr.annotation.input.SaveFormAnnotationCommentInfo;
import com.jiuqi.nr.annotation.input.SaveFormAnnotationInfo;
import com.jiuqi.nr.annotation.input.UpdateFormAnnotationCommentInfo;
import com.jiuqi.nr.annotation.input.UpdateFormAnnotationInfo;
import com.jiuqi.nr.annotation.output.CellAnnotationComment;
import com.jiuqi.nr.annotation.output.CellAnnotationContent;
import com.jiuqi.nr.annotation.output.CellAnnotationResult;
import com.jiuqi.nr.annotation.output.FormAnnotationResult;
import com.jiuqi.nr.annotation.service.IAnnotationService;
import com.jiuqi.nr.annotation.service.IAnnotationTypeService;
import com.jiuqi.nr.basedata.select.bean.BaseDataInfo;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.jtable.aop.JLoggerAspect;
import com.jiuqi.nr.jtable.params.base.JtableContext;
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
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnnotationApplyServiceImpl
implements IAnnotationApplyService {
    @Autowired
    private IAnnotationService annotationService;
    @Autowired
    private JLoggerAspect jLoggerAspect;
    @Autowired
    private IAnnotationTypeService annotationTypeService;

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public FormAnnotationResult queryFormAnnotation(JtableContext jtableContext, String regionKey) {
        FormCellAnnotationQueryInfo formCellAnnotationQueryInfo = new FormCellAnnotationQueryInfo();
        formCellAnnotationQueryInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
        formCellAnnotationQueryInfo.setFormKey(jtableContext.getFormKey());
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        Map<String, DimensionValue> dimensionSet = jtableContext.getDimensionSet();
        for (String key : dimensionSet.keySet()) {
            dimensionCombinationBuilder.setValue(key, (Object)dimensionSet.get(key).getValue());
        }
        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
        formCellAnnotationQueryInfo.setDimensionCombination(dimensionCombination);
        formCellAnnotationQueryInfo.setRegionKey(regionKey);
        return this.annotationService.queryFormAnnotation(formCellAnnotationQueryInfo);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<CellAnnotationContent> queryFormAnnotationDetailed(FormAnnotationQueryCondition formAnnotationQueryCondition) {
        FormAnnotationQueryInfo formAnnotationQueryInfo = new FormAnnotationQueryInfo();
        formAnnotationQueryInfo.setFormSchemeKey(formAnnotationQueryCondition.getContext().getFormSchemeKey());
        formAnnotationQueryInfo.setFormKey(formAnnotationQueryCondition.getContext().getFormKey());
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        Map<String, DimensionValue> dimensionSet = formAnnotationQueryCondition.getContext().getDimensionSet();
        for (String key : dimensionSet.keySet()) {
            dimensionCombinationBuilder.setValue(key, (Object)dimensionSet.get(key).getValue());
        }
        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
        formAnnotationQueryInfo.setDimensionCombination(dimensionCombination);
        String userName = NpContextHolder.getContext().getUserName();
        formAnnotationQueryInfo.setUserName(userName);
        if (null != formAnnotationQueryCondition.getTypes() && !formAnnotationQueryCondition.getTypes().isEmpty()) {
            formAnnotationQueryInfo.setTypes(formAnnotationQueryCondition.getTypes());
        }
        formAnnotationQueryInfo.setPagerInfo(formAnnotationQueryCondition.getPagerInfo());
        return this.annotationService.queryFormAnnotationDetailed(formAnnotationQueryInfo);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public CellAnnotationResult queryCellAnnotation(CellAnnotationQueryCondition cellAnnotationQueryCondition) {
        CellAnnotationQueryInfo cellAnnotationQueryInfo = new CellAnnotationQueryInfo();
        cellAnnotationQueryInfo.setFormSchemeKey(cellAnnotationQueryCondition.getContext().getFormSchemeKey());
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        Map<String, DimensionValue> dimensionSet = cellAnnotationQueryCondition.getContext().getDimensionSet();
        for (String key : dimensionSet.keySet()) {
            dimensionCombinationBuilder.setValue(key, (Object)dimensionSet.get(key).getValue());
        }
        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
        cellAnnotationQueryInfo.setDimensionCombination(dimensionCombination);
        cellAnnotationQueryInfo.setDataLinkKey(cellAnnotationQueryCondition.getDataLinkKey());
        cellAnnotationQueryInfo.setRowId(cellAnnotationQueryCondition.getRowId());
        String userName = NpContextHolder.getContext().getUserName();
        cellAnnotationQueryInfo.setUserName(userName);
        return this.annotationService.queryCellAnnotation(cellAnnotationQueryInfo);
    }

    @Override
    public CellAnnotationContent saveOrUpdateFormAnnotation(FormAnnotationCondition formAnnotationCondition) {
        CellAnnotationContent cellAnnotationContent = null;
        if (StringUtils.isEmpty((String)formAnnotationCondition.getId())) {
            SaveFormAnnotationInfo saveFormAnnotationInfo = new SaveFormAnnotationInfo();
            saveFormAnnotationInfo.setFormSchemeKey(formAnnotationCondition.getContext().getFormSchemeKey());
            saveFormAnnotationInfo.setFormKey(formAnnotationCondition.getContext().getFormKey());
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
            Map<String, DimensionValue> dimensionSet = formAnnotationCondition.getContext().getDimensionSet();
            for (String key : dimensionSet.keySet()) {
                dimensionCombinationBuilder.setValue(key, (Object)dimensionSet.get(key).getValue());
            }
            DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
            saveFormAnnotationInfo.setDimensionCombination(dimensionCombination);
            if (null != formAnnotationCondition.getTypes() && !formAnnotationCondition.getTypes().isEmpty()) {
                saveFormAnnotationInfo.setTypes(formAnnotationCondition.getTypes());
            }
            saveFormAnnotationInfo.setContent(formAnnotationCondition.getContent());
            saveFormAnnotationInfo.setCells(formAnnotationCondition.getCells());
            String userName = NpContextHolder.getContext().getUserName();
            saveFormAnnotationInfo.setUserName(userName);
            String fullname = NpContextHolder.getContext().getUser().getFullname();
            saveFormAnnotationInfo.setUserFullname(fullname);
            cellAnnotationContent = this.annotationService.saveFormAnnotation(saveFormAnnotationInfo);
            this.jLoggerAspect.log(formAnnotationCondition.getContext(), "\u6dfb\u52a0\u6279\u6ce8");
        } else {
            UpdateFormAnnotationInfo updateFormAnnotationInfo = new UpdateFormAnnotationInfo();
            updateFormAnnotationInfo.setFormSchemeKey(formAnnotationCondition.getContext().getFormSchemeKey());
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
            Map<String, DimensionValue> dimensionSet = formAnnotationCondition.getContext().getDimensionSet();
            for (String key : dimensionSet.keySet()) {
                dimensionCombinationBuilder.setValue(key, (Object)dimensionSet.get(key).getValue());
            }
            DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
            updateFormAnnotationInfo.setDimensionCombination(dimensionCombination);
            updateFormAnnotationInfo.setId(formAnnotationCondition.getId());
            if (null != formAnnotationCondition.getTypes() && !formAnnotationCondition.getTypes().isEmpty()) {
                updateFormAnnotationInfo.setTypes(formAnnotationCondition.getTypes());
            }
            updateFormAnnotationInfo.setContent(formAnnotationCondition.getContent());
            String userName = NpContextHolder.getContext().getUserName();
            updateFormAnnotationInfo.setUserName(userName);
            String fullname = NpContextHolder.getContext().getUser().getFullname();
            updateFormAnnotationInfo.setUserFullname(fullname);
            cellAnnotationContent = this.annotationService.updateFormAnnotation(updateFormAnnotationInfo);
            this.jLoggerAspect.log(formAnnotationCondition.getContext(), "\u4fee\u6539\u6279\u6ce8");
        }
        return cellAnnotationContent;
    }

    @Override
    public ReturnInfo removeFormAnnotation(FormAnnotationDeleteCondition formAnnotationDeleteCondition) {
        FormAnnotationDeleteInfo formAnnotationDeleteInfo = new FormAnnotationDeleteInfo();
        formAnnotationDeleteInfo.setFormSchemeKey(formAnnotationDeleteCondition.getContext().getFormSchemeKey());
        formAnnotationDeleteInfo.setIds(formAnnotationDeleteCondition.getIds());
        String userName = NpContextHolder.getContext().getUserName();
        formAnnotationDeleteInfo.setUserName(userName);
        ReturnInfo returnInfo = new ReturnInfo(this.annotationService.removeFormAnnotation(formAnnotationDeleteInfo));
        this.jLoggerAspect.log(formAnnotationDeleteCondition.getContext(), "\u5220\u9664\u6279\u6ce8");
        return returnInfo;
    }

    @Override
    public CellAnnotationComment saveOrUpdateFormAnnotationComment(FormAnnotationCommentCondition formAnnotationCommentCondition) {
        CellAnnotationComment cellAnnotationComment = null;
        if (StringUtils.isEmpty((String)formAnnotationCommentCondition.getId())) {
            SaveFormAnnotationCommentInfo saveFormAnnotationCommentInfo = new SaveFormAnnotationCommentInfo();
            saveFormAnnotationCommentInfo.setFormSchemeKey(formAnnotationCommentCondition.getContext().getFormSchemeKey());
            saveFormAnnotationCommentInfo.setAnnotationId(formAnnotationCommentCondition.getAnnotationId());
            saveFormAnnotationCommentInfo.setContent(formAnnotationCommentCondition.getContent());
            saveFormAnnotationCommentInfo.setRepyId(formAnnotationCommentCondition.getRepyId());
            String userName = NpContextHolder.getContext().getUserName();
            saveFormAnnotationCommentInfo.setUserName(userName);
            String fullname = NpContextHolder.getContext().getUser().getFullname();
            saveFormAnnotationCommentInfo.setUserFullname(fullname);
            cellAnnotationComment = this.annotationService.saveFormAnnotationComment(saveFormAnnotationCommentInfo);
            this.jLoggerAspect.log(formAnnotationCommentCondition.getContext(), "\u6dfb\u52a0\u6279\u6ce8\u8bc4\u8bba");
        } else {
            UpdateFormAnnotationCommentInfo updateFormAnnotationCommentInfo = new UpdateFormAnnotationCommentInfo();
            updateFormAnnotationCommentInfo.setFormSchemeKey(formAnnotationCommentCondition.getContext().getFormSchemeKey());
            updateFormAnnotationCommentInfo.setId(formAnnotationCommentCondition.getId());
            updateFormAnnotationCommentInfo.setContent(formAnnotationCommentCondition.getContent());
            updateFormAnnotationCommentInfo.setRepyId(formAnnotationCommentCondition.getRepyId());
            String userName = NpContextHolder.getContext().getUserName();
            updateFormAnnotationCommentInfo.setUserName(userName);
            String fullname = NpContextHolder.getContext().getUser().getFullname();
            updateFormAnnotationCommentInfo.setUserFullname(fullname);
            cellAnnotationComment = this.annotationService.updateFormAnnotationComment(updateFormAnnotationCommentInfo);
            this.jLoggerAspect.log(formAnnotationCommentCondition.getContext(), "\u4fee\u6539\u6279\u6ce8\u8bc4\u8bba");
        }
        return cellAnnotationComment;
    }

    @Override
    public ReturnInfo removeFormAnnotationComment(FormAnnotationDeleteCondition formAnnotationDeleteCondition) {
        FormAnnotationDeleteInfo formAnnotationDeleteInfo = new FormAnnotationDeleteInfo();
        formAnnotationDeleteInfo.setFormSchemeKey(formAnnotationDeleteCondition.getContext().getFormSchemeKey());
        String userName = NpContextHolder.getContext().getUserName();
        formAnnotationDeleteInfo.setUserName(userName);
        formAnnotationDeleteInfo.setIds(formAnnotationDeleteCondition.getIds());
        ReturnInfo returnInfo = new ReturnInfo(this.annotationService.removeFormAnnotationComment(formAnnotationDeleteInfo));
        this.jLoggerAspect.log(formAnnotationDeleteCondition.getContext(), "\u5220\u9664\u6279\u6ce8\u8bc4\u8bba");
        return returnInfo;
    }

    @Override
    public ITree<BaseDataInfo> queryAnnotationTypeTree(AnnotationTypeQueryCondition annotationTypeQueryCondition) {
        JtableContext context = annotationTypeQueryCondition.getContext();
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
        for (String key : dimensionSet.keySet()) {
            dimensionCombinationBuilder.setValue(key, (Object)dimensionSet.get(key).getValue());
        }
        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
        return this.annotationTypeService.queryTypeBaseData(dimensionCombination, context.getFormSchemeKey(), annotationTypeQueryCondition.getEntityCode());
    }

    @Override
    public ITree<BaseDataInfo> positioningAnnotationType(AnnotationTypePositionCondition annotationTypePositionCondition) {
        JtableContext context = annotationTypePositionCondition.getContext();
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
        for (String key : dimensionSet.keySet()) {
            dimensionCombinationBuilder.setValue(key, (Object)dimensionSet.get(key).getValue());
        }
        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
        return this.annotationTypeService.positioningTypebaseData(dimensionCombination, context.getFormSchemeKey(), annotationTypePositionCondition.getEntityKeyData());
    }

    @Override
    public ITree<BaseDataInfo> searchAnnotationType(AnnotationTypeSearchCondition annotationTypeSearchCondition) {
        JtableContext context = annotationTypeSearchCondition.getContext();
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
        for (String key : dimensionSet.keySet()) {
            dimensionCombinationBuilder.setValue(key, (Object)dimensionSet.get(key).getValue());
        }
        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
        return this.annotationTypeService.searchTypeBaseData(dimensionCombination, context.getFormSchemeKey(), annotationTypeSearchCondition.getSearchInfo());
    }
}

