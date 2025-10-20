/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.test.service;

import com.jiuqi.gcreport.definition.impl.test.dao.GcTestDefineDao;
import com.jiuqi.gcreport.definition.impl.test.eo.GcTestDefineEO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class GcTestDefineService {
    @Autowired
    private GcTestDefineDao dao;

    public int add(GcTestDefineEO eo) {
        return this.dao.add(eo);
    }

    public int update(GcTestDefineEO eo) {
        return this.dao.update(eo);
    }

    public int delete(String id) {
        GcTestDefineEO eo = new GcTestDefineEO();
        eo.setId(id);
        return this.dao.delete(eo);
    }

    public GcTestDefineEO get(String name) {
        GcTestDefineEO eo = new GcTestDefineEO();
        eo.setName(name);
        return this.dao.selectByEntity(eo);
    }

    public List<GcTestDefineEO> list() {
        return this.dao.selectList(new GcTestDefineEO());
    }
}

