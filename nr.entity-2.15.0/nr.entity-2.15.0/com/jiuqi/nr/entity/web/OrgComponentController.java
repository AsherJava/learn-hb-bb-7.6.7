/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.entity.web;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.entity.component.currency.OrgCurrencyService;
import com.jiuqi.nr.entity.component.currency.dto.CurrencyCheckDTO;
import com.jiuqi.nr.entity.component.dwdm.EntityDWDMService;
import com.jiuqi.nr.entity.component.dwdm.OrgIDCAttributeDTO;
import com.jiuqi.nr.entity.component.idc.IDCUtils;
import io.swagger.annotations.Api;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/entity/org"})
@Api(tags={"\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2"})
public class OrgComponentController {
    @Autowired
    private EntityDWDMService entityDWDMService;
    @Autowired
    private OrgCurrencyService orgCurrencyService;

    @GetMapping(value={"/idc/{code}"})
    public String getIDCCode(@PathVariable String code) {
        return IDCUtils.getIDCCheckBit(code);
    }

    @GetMapping(value={"/idcl/{code}"})
    public String getIDCLCode(@PathVariable String code) {
        return IDCUtils.getIDCLCheckBit(code);
    }

    @GetMapping(value={"/attributes/{categoryName}"})
    public OrgIDCAttributeDTO getOrgAttributes(@PathVariable String categoryName) {
        return this.entityDWDMService.getDWDMAttributes(categoryName);
    }

    @PostMapping(value={"/saveIDCAttribute"})
    public void saveIDCAttribute(@RequestBody Map<String, String> saveParam) {
        String table = saveParam.get("TABLE");
        String attribute = saveParam.get("ATTRIBUTE");
        this.entityDWDMService.saveDWDMAttribute(table, attribute);
    }

    @GetMapping(value={"/judgement/{categoryName}"})
    public CurrencyCheckDTO judgementCurrency(@PathVariable String categoryName) {
        return this.orgCurrencyService.existCurrencyAttribute(categoryName);
    }

    @GetMapping(value={"/generatorCurrency/{categoryName}"})
    public void generatorCurrency(@PathVariable String categoryName) {
        this.orgCurrencyService.generatorCurrency(categoryName);
    }
}

