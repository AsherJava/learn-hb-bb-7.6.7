/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"api/v1/datascheme/menu/"})
public class DataSchemeMenuRestController {
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;

    @GetMapping(value={"/all-scheme/{type}"})
    public List<DesignDataScheme> allDataScheme(@PathVariable(required=false) Integer type) {
        List allDataSchemes = this.designDataSchemeService.getAllDataScheme();
        if (null == type) {
            return allDataSchemes;
        }
        return allDataSchemes.stream().filter(d -> type.intValue() == d.getType().getValue() && this.dataSchemeAuthService.canReadScheme(d.getKey())).collect(Collectors.toList());
    }
}

