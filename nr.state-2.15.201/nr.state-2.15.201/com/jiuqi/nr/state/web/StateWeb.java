/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.state.web;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.state.pojo.StatePojo;
import com.jiuqi.nr.state.service.IStateSevice;
import com.jiuqi.nr.state.untils.NrResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/state"})
public class StateWeb {
    @Autowired
    private IStateSevice istateSevice;

    @RequestMapping(value={"/get-state"}, method={RequestMethod.GET})
    public NrResult getSystemOptions() {
        NrResult resp = new NrResult();
        resp.setStatus(200);
        return resp;
    }

    @RequestMapping(value={"/save-state"}, method={RequestMethod.POST})
    public NrResult saveSystemOptions() {
        NrResult resp = new NrResult();
        StatePojo sp = new StatePojo();
        DimensionValueSet dims = new DimensionValueSet();
        dims.setValue("DATATIME", (Object)"2019Y0001");
        dims.setValue("DEFAULT_DW_ID", (Object)22);
        sp.setDims(dims);
        sp.setState(0);
        sp.setFormSchemeKey("55425296-1a6d-4e4f-b591-c0ab05e8d062");
        String json = "";
        this.istateSevice.saveOrUpdateData(sp);
        resp.setStatus(200);
        return resp;
    }
}

