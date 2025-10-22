/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.nr.api.GCFormTabSelectClient
 *  com.jiuqi.gcreport.nr.vo.FormTreeVo
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.nr.impl.web.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.nr.api.GCFormTabSelectClient;
import com.jiuqi.gcreport.nr.impl.service.GCFormTabSelectService;
import com.jiuqi.gcreport.nr.vo.FormTreeVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class GCFormTabSelectController
implements GCFormTabSelectClient {
    @Autowired
    private GCFormTabSelectService formTabSelectService;

    public BusinessResponseEntity<List<FormTreeVo>> queryFormTree(String formSchemeKey, String dataTime) throws Exception {
        return BusinessResponseEntity.ok(this.formTabSelectService.queryFormTree(formSchemeKey, dataTime));
    }

    public BusinessResponseEntity<List<FormTreeVo>> queryFormTree(String formSchemeKey) throws Exception {
        return BusinessResponseEntity.ok(this.formTabSelectService.queryFormTree(formSchemeKey, null));
    }

    public BusinessResponseEntity<String> queryFormData(String formKey) {
        return BusinessResponseEntity.ok((Object)this.formTabSelectService.queryFormData(formKey, null));
    }
}

