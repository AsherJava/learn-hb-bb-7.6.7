/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.migration.transferdata.jqrmapping;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.migration.transferdata.jqrmapping.JQRResourceMapping2DO;
import com.jiuqi.nr.migration.transferdata.jqrmapping.JQRResourceMapping2Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/nr-mapping-jqr-resource"})
public class JQRResourceMapping2Controller {
    @Autowired
    private JQRResourceMapping2Service service;

    @PostMapping(value={"/batchAdd"})
    public String batchAddMapping(@RequestBody List<JQRResourceMapping2DO> dos) {
        this.service.batchInsertOrUpdateJqrCustomMappings(dos, "insert");
        return "\u4fdd\u5b58\u6210\u529f";
    }

    @PostMapping(value={"/batchUpdate"})
    public String batchUpdateMapping(@RequestBody List<JQRResourceMapping2DO> dos) {
        this.service.batchInsertOrUpdateJqrCustomMappings(dos, "update");
        return "\u4fee\u6539\u6210\u529f";
    }

    @GetMapping(value={"/findByKey/{mappingSchemeKey}"})
    public List<JQRResourceMapping2DO> findMapping(@PathVariable String mappingSchemeKey) {
        return this.service.findByMSJqrCustom(mappingSchemeKey);
    }
}

