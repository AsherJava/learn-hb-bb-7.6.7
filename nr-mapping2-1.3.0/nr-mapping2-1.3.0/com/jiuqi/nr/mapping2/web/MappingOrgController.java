/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.mapping2.web;

import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/nr/mapping2/org"})
@Api(value="NR\u6620\u5c04\u6269\u5c55\uff1a\u5355\u4f4d\u9009\u62e9\u5668\u63a5\u53e3")
public class MappingOrgController {
    @Autowired
    private IRunTimeViewController runTime;
    @Autowired
    private USelectorResultSet uSelectorResultSet;

    @GetMapping(value={"/get-selector/{formSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u6811\u548c\u62a5\u8868\u65b9\u6848\u5217\u8868")
    public Map<String, String> getOrgSelector(@PathVariable String formSchemeKey) throws Exception {
        HashMap<String, String> res = new HashMap<String, String>();
        FormSchemeDefine formScheme = this.runTime.getFormScheme(formSchemeKey);
        res.put("datetime", formScheme.getDateTime());
        res.put("period", formScheme.getToPeriod());
        List periodLinkList = this.runTime.querySchemePeriodLinkBySchemeSort(formSchemeKey);
        if (!CollectionUtils.isEmpty(periodLinkList)) {
            res.put("period", ((SchemePeriodLinkDefine)periodLinkList.get(periodLinkList.size() - 1)).getPeriodKey());
        }
        return res;
    }

    @GetMapping(value={"/get-title/{selectorKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5355\u4f4d\u9009\u62e9\u5668\u9009\u7684\u5355\u4f4d\u5217\u8868")
    public List<Map<String, String>> getOrgTitle(@PathVariable String selectorKey) {
        ArrayList<Map<String, String>> res = new ArrayList<Map<String, String>>();
        List allRows = this.uSelectorResultSet.getFilterEntityRows(selectorKey);
        for (IEntityRow r : allRows) {
            HashMap<String, String> m = new HashMap<String, String>();
            m.put("code", r.getEntityKeyData());
            m.put("orgcode", r.getCode());
            m.put("name", r.getTitle());
            res.add(m);
        }
        return res;
    }
}

