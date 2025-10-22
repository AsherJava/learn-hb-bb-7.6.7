/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.data.checkdes.service.impl;

import com.jiuqi.nr.data.checkdes.obj.CKDImpDetails;
import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import com.jiuqi.nr.data.checkdes.obj.MapHandlePar;
import com.jiuqi.nr.data.checkdes.service.internal.IDataMappingHandler;
import com.jiuqi.nr.data.checkdes.util.CommonUtil;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Order(value=2)
public class FmlSchemeMappingHandler
implements IDataMappingHandler {
    @Autowired
    private CommonUtil util;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;

    @Override
    public CKDTransObj handle(MapHandlePar par, CKDTransObj ckdTransObj) {
        ParamsMapping paramsMapping = par.getParamsMapping();
        ArrayList<String> o = new ArrayList<String>();
        String srcFormulaSchemeTitle = ckdTransObj.getFormulaSchemeTitle();
        o.add(srcFormulaSchemeTitle);
        Map originFormulaScheme = paramsMapping.getOriginFormulaScheme(o);
        if (!CollectionUtils.isEmpty(originFormulaScheme) && originFormulaScheme.containsKey(srcFormulaSchemeTitle)) {
            String tarFormulaSchemeTitle = (String)originFormulaScheme.get(srcFormulaSchemeTitle);
            Optional<FormulaSchemeDefine> any = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(par.getFormSchemeDefine().getKey()).stream().filter(a -> a.getTitle().equals(tarFormulaSchemeTitle)).findAny();
            if (any.isPresent()) {
                ckdTransObj.setFormulaSchemeTitle(tarFormulaSchemeTitle);
                ckdTransObj.setFormulaSchemeKey(any.get().getKey());
            } else {
                if (!CollectionUtils.isEmpty(par.getCkdImpDetails())) {
                    CKDImpDetails impDetail = this.util.getImpDetail(ckdTransObj, "\u672a\u627e\u5230\u7cfb\u7edf\u6620\u5c04\u4e2d\u7684\u516c\u5f0f\u65b9\u6848\uff1a" + tarFormulaSchemeTitle);
                    par.getCkdImpDetails().add(impDetail);
                }
                return null;
            }
        }
        return ckdTransObj;
    }
}

