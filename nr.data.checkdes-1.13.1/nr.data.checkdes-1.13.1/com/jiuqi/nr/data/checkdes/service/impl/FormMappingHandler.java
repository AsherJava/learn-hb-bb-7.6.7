/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.data.checkdes.service.impl;

import com.jiuqi.nr.data.checkdes.obj.CKDImpDetails;
import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import com.jiuqi.nr.data.checkdes.obj.MapHandlePar;
import com.jiuqi.nr.data.checkdes.service.internal.IDataMappingHandler;
import com.jiuqi.nr.data.checkdes.util.CommonUtil;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Order(value=3)
public class FormMappingHandler
implements IDataMappingHandler {
    @Autowired
    private CommonUtil util;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public CKDTransObj handle(MapHandlePar par, CKDTransObj ckdTransObj) {
        ParamsMapping paramsMapping = par.getParamsMapping();
        String srcFormCode = ckdTransObj.getFormCode();
        if ("00000000-0000-0000-0000-000000000000".equals(srcFormCode)) {
            ckdTransObj.setFormCode("00000000-0000-0000-0000-000000000000");
            ckdTransObj.setFormKey("00000000-0000-0000-0000-000000000000");
            ckdTransObj.setFormTitle("\u8868\u95f4");
        } else {
            ArrayList<String> o = new ArrayList<String>();
            o.add(srcFormCode);
            Map originFormCode = paramsMapping.getOriginFormCode(o);
            if (!CollectionUtils.isEmpty(originFormCode) && originFormCode.containsKey(srcFormCode)) {
                String tarFormCode = (String)originFormCode.get(srcFormCode);
                try {
                    FormDefine formDefine = this.runTimeViewController.queryFormByCodeInScheme(par.getFormSchemeDefine().getKey(), tarFormCode);
                    ckdTransObj.setFormCode(tarFormCode);
                    ckdTransObj.setFormKey(formDefine.getKey());
                    ckdTransObj.setFormTitle(formDefine.getTitle());
                }
                catch (Exception ex) {
                    if (!CollectionUtils.isEmpty(par.getCkdImpDetails())) {
                        CKDImpDetails impDetail = this.util.getImpDetail(ckdTransObj, "\u672a\u627e\u5230\u7cfb\u7edf\u6620\u5c04\u4e2d\u7684\u62a5\u8868\uff1a" + tarFormCode);
                        par.getCkdImpDetails().add(impDetail);
                    }
                    return null;
                }
            }
        }
        return ckdTransObj;
    }
}

