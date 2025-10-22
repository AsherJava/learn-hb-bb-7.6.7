/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.entity.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.entity.component.fix.IFixService;
import com.jiuqi.nr.entity.component.fix.dto.OrgDataFixDTO;
import com.jiuqi.nr.entity.component.fix.dto.SimpleDefineDTO;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/fix-org"})
@Api(tags={"\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u4fee\u590d"})
public class OrgDataFixController {
    @Autowired
    private IFixService fixService;

    @GetMapping(value={"/list"})
    public List<SimpleDefineDTO> list() throws JQException {
        return this.fixService.listOrg();
    }

    @GetMapping(value={"/list-version/{category}"})
    public List<SimpleDefineDTO> listVersion(@PathVariable String category) throws JQException {
        return this.fixService.listOrgVersion(category);
    }

    @GetMapping(value={"/do-fix/{category}/{version}"})
    public List<OrgDataFixDTO> fixOrgData(@PathVariable String category, @PathVariable String version) throws JQException {
        return this.fixService.fixParents(category, version);
    }
}

