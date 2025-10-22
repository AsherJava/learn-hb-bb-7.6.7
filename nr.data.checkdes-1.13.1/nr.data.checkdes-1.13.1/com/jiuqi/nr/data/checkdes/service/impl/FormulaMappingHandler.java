/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 */
package com.jiuqi.nr.data.checkdes.service.impl;

import com.jiuqi.nr.data.checkdes.obj.CKDImpDetails;
import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import com.jiuqi.nr.data.checkdes.obj.MapHandlePar;
import com.jiuqi.nr.data.checkdes.service.internal.IDataMappingHandler;
import com.jiuqi.nr.data.checkdes.util.CommonUtil;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Order(value=4)
public class FormulaMappingHandler
implements IDataMappingHandler {
    @Autowired
    private CommonUtil util;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;

    @Override
    public CKDTransObj handle(MapHandlePar par, CKDTransObj ckdTransObj) {
        ParamsMapping paramsMapping = par.getParamsMapping();
        ArrayList<String> o = new ArrayList<String>();
        String srcFormulaCode = ckdTransObj.getFormulaCode();
        o.add(srcFormulaCode);
        Map originFormulaCode = paramsMapping.getOriginFormula(ckdTransObj.getFormulaSchemeKey(), o);
        if (!CollectionUtils.isEmpty(originFormulaCode) && originFormulaCode.containsKey(srcFormulaCode)) {
            String tarFormulaCode = (String)originFormulaCode.get(srcFormulaCode);
            FormulaDefine formulaDefine = this.formulaRunTimeController.findFormulaDefine(tarFormulaCode, ckdTransObj.getFormulaSchemeKey());
            if (formulaDefine == null) {
                if (!CollectionUtils.isEmpty(par.getCkdImpDetails())) {
                    CKDImpDetails impDetail = this.util.getImpDetail(ckdTransObj, "\u672a\u627e\u5230\u7cfb\u7edf\u6620\u5c04\u4e2d\u7684\u516c\u5f0f\uff1a" + tarFormulaCode);
                    par.getCkdImpDetails().add(impDetail);
                }
                return null;
            }
            ckdTransObj.setFormulaCode(tarFormulaCode);
            ckdTransObj.setFormulaKey(formulaDefine.getKey());
            String newKey = formulaDefine.getKey() + ckdTransObj.getFormulaExpressionKey().substring(36);
            ckdTransObj.setFormulaExpressionKey(newKey);
        }
        return ckdTransObj;
    }
}

