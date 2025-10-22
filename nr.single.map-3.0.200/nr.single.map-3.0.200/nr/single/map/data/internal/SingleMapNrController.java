/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.internal;

import nr.single.map.data.facade.ISingleMapNrController;
import nr.single.map.data.facade.SingleMapFormSchemeDefine;
import nr.single.map.data.internal.SingleMapNrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SingleMapNrController
implements ISingleMapNrController {
    @Autowired
    private SingleMapNrService service;

    @Override
    public SingleMapFormSchemeDefine QuerySingleMapDefine(String taskKey, String formSchemeKey) {
        return this.service.QuerySingleMapDefine(taskKey, formSchemeKey);
    }

    @Override
    public SingleMapFormSchemeDefine CreateSingleMapDefine() {
        return this.service.CreateSingleMapDefine();
    }

    @Override
    public void UpdateSingleMapDefine(String taskKey, String formSchemeKey, SingleMapFormSchemeDefine define) {
        this.service.UpdateSingleMapDefine(taskKey, formSchemeKey, define);
    }

    @Override
    public SingleMapFormSchemeDefine QueryAndCreateSingleMapDefine(String taskKey, String formSchemeKey) {
        return this.service.QueryAndCreateSingleMapDefine(taskKey, formSchemeKey, true);
    }
}

