/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.consolidatedsystem.api.InputDataSchemeClient
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.consolidatedsystem.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.consolidatedsystem.api.InputDataSchemeClient;
import com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeService;
import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class InputDataSchemeControll
implements InputDataSchemeClient {
    @Autowired
    private InputDataSchemeService inputDataSchemeService;

    public BusinessResponseEntity<List<InputDataSchemeVO>> listInputDataScheme() {
        return BusinessResponseEntity.ok(this.inputDataSchemeService.listInputDataScheme());
    }

    public BusinessResponseEntity<InputDataSchemeVO> getInputDataSchemeByDataSchemeKey(String dataSchemeKey) {
        return BusinessResponseEntity.ok((Object)this.inputDataSchemeService.getInputDataSchemeByDataSchemeKey(dataSchemeKey));
    }
}

