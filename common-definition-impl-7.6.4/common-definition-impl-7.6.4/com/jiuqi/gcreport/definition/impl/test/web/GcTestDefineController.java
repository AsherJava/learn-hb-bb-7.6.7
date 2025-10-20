/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.definition.impl.test.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.definition.impl.test.eo.GcTestDefineEO;
import com.jiuqi.gcreport.definition.impl.test.service.GcTestDefineService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class GcTestDefineController {
    @Autowired
    private GcTestDefineService service;
    static final String API_PATH = "/api/gcreport/v1/gctestdefine";

    @PostMapping(value={"/api/gcreport/v1/gctestdefine/add"})
    public BusinessResponseEntity<Integer> add(@RequestBody GcTestDefineEO eo) {
        return BusinessResponseEntity.ok((Object)this.service.add(eo));
    }

    @PostMapping(value={"/api/gcreport/v1/gctestdefine/update"})
    public BusinessResponseEntity<Integer> update(@RequestBody GcTestDefineEO eo) {
        return BusinessResponseEntity.ok((Object)this.service.update(eo));
    }

    @GetMapping(value={"/api/gcreport/v1/gctestdefine/delete/{id}"})
    public BusinessResponseEntity<Integer> delete(@PathVariable(value="id") String id) {
        return BusinessResponseEntity.ok((Object)this.service.delete(id));
    }

    @GetMapping(value={"/api/gcreport/v1/gctestdefine/get/{name}"})
    public BusinessResponseEntity<GcTestDefineEO> get(@PathVariable(value="name") String name) {
        return BusinessResponseEntity.ok((Object)this.service.get(name));
    }

    @GetMapping(value={"/api/gcreport/v1/gctestdefine/list"})
    public BusinessResponseEntity<List<GcTestDefineEO>> list() {
        return BusinessResponseEntity.ok(this.service.list());
    }
}

