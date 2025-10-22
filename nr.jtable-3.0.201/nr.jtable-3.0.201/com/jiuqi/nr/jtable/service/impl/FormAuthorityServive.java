/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.FormAccessResult;
import com.jiuqi.nr.jtable.service.IFormAuthorityServive;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormAuthorityServive
implements IFormAuthorityServive {
    private static final String accessCode = "dataentry";
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IDataAccessServiceProvider iDataAccessServiceProvider;
    private static final Logger logger = LoggerFactory.getLogger(FormAuthorityServive.class);

    @Override
    public String getAccessCode() {
        return accessCode;
    }

    @Override
    public FormAccessResult canWrite(JtableContext context) {
        EntityViewData dwEntity;
        String dwEntityName;
        FormAccessResult formAccessResult = new FormAccessResult();
        Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
        DimensionValue dwDimensionValue = dimensionSet.get(dwEntityName = (dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey())).getDimensionName());
        String dwCode = dwDimensionValue.getValue();
        if (!dwCode.equals("00000000-0000-0000-0000-000000000000")) {
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(context);
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
            DimensionCombination combination = dimensionCombinationBuilder.getCombination();
            IDataAccessService iDataAccessService = this.iDataAccessServiceProvider.getDataAccessService(context.getTaskKey(), context.getFormSchemeKey());
            IAccessResult writeable = iDataAccessService.writeable(combination, context.getFormKey());
            try {
                if (!writeable.haveAccess()) {
                    formAccessResult.setHaveAccess(false);
                    formAccessResult.setMessage(writeable.getMessage());
                    return formAccessResult;
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u4fdd\u5b58\u65f6\u6743\u9650\u5224\u65ad\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
        return FormAccessResult.formHaveAccess();
    }

    @Override
    public FormAccessResult caRead(JtableContext context) {
        EntityViewData dwEntity;
        String dwEntityName;
        FormAccessResult formAccessResult = new FormAccessResult();
        Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
        DimensionValue dwDimensionValue = dimensionSet.get(dwEntityName = (dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey())).getDimensionName());
        String dwCode = dwDimensionValue.getValue();
        if (!dwCode.equals("00000000-0000-0000-0000-000000000000")) {
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(context);
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
            DimensionCombination combination = dimensionCombinationBuilder.getCombination();
            IDataAccessService iDataAccessService = this.iDataAccessServiceProvider.getDataAccessService(context.getTaskKey(), context.getFormSchemeKey());
            IAccessResult readable = iDataAccessService.readable(combination, context.getFormKey());
            try {
                if (!readable.haveAccess()) {
                    formAccessResult.setHaveAccess(false);
                    formAccessResult.setMessage(readable.getMessage());
                    return formAccessResult;
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u4fdd\u5b58\u65f6\u6743\u9650\u5224\u65ad\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
        return FormAccessResult.formHaveAccess();
    }
}

