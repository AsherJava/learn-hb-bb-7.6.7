/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.common.service.ImpSettings
 *  com.jiuqi.nr.data.common.service.dto.CompletionDim
 *  com.jiuqi.nr.data.common.service.dto.CompletionDimFinder
 *  com.jiuqi.nr.data.common.service.dto.FilterDim
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 */
package com.jiuqi.nr.data.checkdes.internal.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.checkdes.api.ICKDParamMapping;
import com.jiuqi.nr.data.checkdes.exception.CKDIOException;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpFailType;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpFailedInfo;
import com.jiuqi.nr.data.checkdes.internal.ctxt.ImpContext;
import com.jiuqi.nr.data.checkdes.internal.io.CKDExpEntity;
import com.jiuqi.nr.data.checkdes.internal.util.IOUtils;
import com.jiuqi.nr.data.checkdes.obj.CKDImpFilter;
import com.jiuqi.nr.data.checkdes.obj.CKDImpPar;
import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import com.jiuqi.nr.data.checkdes.obj.InfoCollection;
import com.jiuqi.nr.data.common.service.ImpSettings;
import com.jiuqi.nr.data.common.service.dto.CompletionDim;
import com.jiuqi.nr.data.common.service.dto.CompletionDimFinder;
import com.jiuqi.nr.data.common.service.dto.FilterDim;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

public class ImpDataConsumer
implements Consumer<CKDExpEntity> {
    private final ImpContext context;
    private final DataPermissionEvaluator dataPermissionEvaluator;
    private final List<CKDImpFilter> filters;
    private final List<CheckDesObj> effectiveData = new ArrayList<CheckDesObj>();
    private final InfoCollection infoCollection = new InfoCollection();

    public ImpDataConsumer(ImpContext context, DataPermissionEvaluator dataPermissionEvaluator, List<CKDImpFilter> filters) {
        this.context = context;
        this.dataPermissionEvaluator = dataPermissionEvaluator;
        this.filters = filters;
    }

    @Override
    public void accept(CKDExpEntity ckdExpEntity) {
        CKDTransObj ckdTransObj;
        CKDImpPar param = this.context.getImpPar();
        ICKDParamMapping paramMapping = param.getCkdParamMapping();
        if (paramMapping != null) {
            ckdExpEntity = IOUtils.handleMapping(paramMapping, ckdExpEntity);
        }
        try {
            ckdTransObj = this.context.getCommonUtil().getHelper().getValidCKDTransObj(this.context, ckdExpEntity);
        }
        catch (Exception e) {
            throw new CKDIOException(e.getMessage(), e);
        }
        if (ckdTransObj == null) {
            return;
        }
        ImpSettings impSettings = this.context.getImpSettings();
        FilterDim filterDims = impSettings.getFilterDims();
        if ((ckdTransObj = this.filterAndRemoveDim(ckdTransObj, filterDims)) == null) {
            return;
        }
        CompletionDim completionDims = impSettings.getCompletionDims();
        CKDTransObj filteredData = ImpDataConsumer.filter(this.filters, ckdTransObj = this.completeDim(ckdTransObj, completionDims), this.context);
        if (filteredData == null) {
            return;
        }
        String formKey = ckdTransObj.getFormKey();
        DimensionValueSet dimensionValueSet = ckdTransObj.getDimensionValueSet();
        DimensionCombination combination = new DimensionCombinationBuilder(dimensionValueSet).getCombination();
        if (ImpDataConsumer.formWriteAccess(this.dataPermissionEvaluator, combination, formKey, this.context)) {
            CKDTransObj validatedData = param.getCkdImpValidator().validate(filteredData, this.context.getCkdImpMes());
            if (validatedData != null) {
                this.infoCollection.getDwSet().add(validatedData.getDimMap().get(this.context.getDimName(this.context.getFormSchemeDefine().getDw())));
                this.infoCollection.getDimensionValueSets().add(validatedData.getDimensionValueSet());
                this.infoCollection.getForms().add(validatedData.getFormKey());
                this.infoCollection.getFormulaSchemes().add(validatedData.getFormulaSchemeKey());
                this.effectiveData.add(this.context.getCommonUtil().generateCheckDesObj(validatedData));
            }
        } else {
            ImpFailedInfo failedInfo = this.context.getCommonUtil().getHelper().getImpFailedInfo(filteredData, String.format("\u8be5\u5355\u4f4d%s\u5bf9\u62a5\u8868%s\u65e0\u6570\u636e\u5199\u5165\u6743\u9650", dimensionValueSet, formKey), ImpFailType.NO_ACCESS);
            this.context.getCkdImpMes().getFailedInfos().add(failedInfo);
        }
    }

    @Nullable
    private CKDTransObj filterAndRemoveDim(CKDTransObj ckdTransObj, FilterDim filterDims) {
        List dynamicsFilterDims;
        if (filterDims == null || !filterDims.isFilterDim()) {
            return ckdTransObj;
        }
        DimensionValueSet fixedFilterDims = filterDims.getFixedFilterDims();
        if (fixedFilterDims != null) {
            for (int i = 0; i < fixedFilterDims.size(); ++i) {
                String ckdDimValue;
                String filterName2 = fixedFilterDims.getName(i);
                String filterValue = String.valueOf(fixedFilterDims.getValue(i));
                if (!filterValue.equals(ckdDimValue = ckdTransObj.getDimMap().get(filterName2))) {
                    List<ImpFailedInfo> failedInfos = this.context.getCkdImpMes().getFailedInfos();
                    ImpFailedInfo impFailedInfo = this.context.getCommonUtil().getHelper().getImpFailedInfo(ckdTransObj, "\u4e0d\u6ee1\u8db3\u8fc7\u6ee4\u6761\u4ef6\uff1a\u6307\u5b9a\u60c5\u666f" + filterName2 + ":" + filterValue, ImpFailType.OUT_OF_RANGE);
                    failedInfos.add(impFailedInfo);
                    return null;
                }
                ckdTransObj.getDimMap().remove(filterName2);
            }
        }
        if (!CollectionUtils.isEmpty(dynamicsFilterDims = filterDims.getDynamicsFilterDims())) {
            dynamicsFilterDims.forEach(filterName -> ckdTransObj.getDimMap().remove(filterName));
        }
        return ckdTransObj;
    }

    @NonNull
    private CKDTransObj completeDim(CKDTransObj ckdTransObj, CompletionDim completionDims) {
        List dynamicsCompletionDims;
        if (completionDims == null || !completionDims.isCompletionDim()) {
            return ckdTransObj;
        }
        DimensionValueSet fixedCompletionDims = completionDims.getFixedCompletionDims();
        if (fixedCompletionDims != null) {
            for (int i = 0; i < fixedCompletionDims.size(); ++i) {
                String completeName = fixedCompletionDims.getName(i);
                String completeValue = String.valueOf(fixedCompletionDims.getValue(i));
                ckdTransObj.getDimMap().put(completeName, completeValue);
            }
        }
        if (!CollectionUtils.isEmpty(dynamicsCompletionDims = completionDims.getDynamicsCompletionDims())) {
            CompletionDimFinder finder = completionDims.getFinder();
            String dw = ckdTransObj.getDimMap().get(this.context.getDimName(this.context.getFormSchemeDefine().getDw()));
            for (String dynamicsCompletionDim : dynamicsCompletionDims) {
                String dimByDw = finder.findByDw(dw, dynamicsCompletionDim);
                ckdTransObj.getDimMap().put(dynamicsCompletionDim, dimByDw);
            }
        }
        return ckdTransObj;
    }

    public List<CheckDesObj> getEffectiveData() {
        return this.effectiveData;
    }

    public InfoCollection getInfoCollection() {
        return this.infoCollection;
    }

    private static CKDTransObj filter(List<CKDImpFilter> filters, CKDTransObj ckdDetailObj, ImpContext impContext) {
        if (!CollectionUtils.isEmpty(filters)) {
            for (CKDImpFilter filter : filters) {
                boolean r = filter.filter(ckdDetailObj);
                if (r) continue;
                List<ImpFailedInfo> failedInfos = impContext.getCkdImpMes().getFailedInfos();
                ImpFailedInfo impFailedInfo = impContext.getCommonUtil().getHelper().getImpFailedInfo(ckdDetailObj, "\u4e0d\u6ee1\u8db3\u8fc7\u6ee4\u6761\u4ef6\uff1a" + filter.getFilterType().getDesc(), ImpFailType.OUT_OF_RANGE);
                failedInfos.add(impFailedInfo);
                return null;
            }
        }
        return ckdDetailObj;
    }

    private static boolean formWriteAccess(DataPermissionEvaluator desAccessFormInfos, DimensionCombination masterKey, String formKey, ImpContext context) {
        if (formKey == null || "00000000-0000-0000-0000-000000000000".equals(formKey)) {
            for (String fsAllForm : context.getFsAllForms()) {
                if (!desAccessFormInfos.haveAccess(masterKey, fsAllForm, AuthType.SYS_WRITEABLE)) continue;
                return true;
            }
        } else {
            return desAccessFormInfos.haveAccess(masterKey, formKey, AuthType.SYS_WRITEABLE);
        }
        return false;
    }
}

