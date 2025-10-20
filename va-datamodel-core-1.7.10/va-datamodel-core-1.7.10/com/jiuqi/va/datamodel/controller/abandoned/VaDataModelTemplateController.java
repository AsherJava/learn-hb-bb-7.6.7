/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.extend.DataModelTemplateDTO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.datamodel.controller.abandoned;

import com.jiuqi.va.datamodel.service.VaDataModelTemplateService;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.extend.DataModelTemplateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="deprecatedDataModelTemplateController")
@Deprecated
@ConditionalOnProperty(name={"va.datamodel.binary.compatible"}, havingValue="true", matchIfMissing=true)
@RequestMapping(value={"/dataModel/template"})
public class VaDataModelTemplateController {
    @Autowired
    private VaDataModelTemplateService vaDataModelTemplateService;

    @PostMapping(value={"/get"})
    Object getDataModelTemplate(@RequestBody DataModelTemplateDTO param) {
        return MonoVO.just((Object)this.vaDataModelTemplateService.getTemplateEntity(param));
    }
}

