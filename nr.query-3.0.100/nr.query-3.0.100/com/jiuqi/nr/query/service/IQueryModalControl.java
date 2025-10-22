/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.query.service;

import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/routes"})
@Api(tags={"\u67e5\u8be2\u6a21\u677f"})
public class IQueryModalControl {
    @Autowired
    IQueryModalDefineDao modalDefineDao;

    @RequestMapping(value={"/bound"}, method={RequestMethod.GET})
    public boolean isModal(String appname) {
        String[] split = appname.split("/");
        String modelType = split[0];
        QueryModelType type = this.getType(modelType);
        if (type == null) {
            return false;
        }
        List<QueryModalDefine> allModalsByModelType = this.modalDefineDao.getAllModalsByModelType(type);
        return allModalsByModelType != null && allModalsByModelType.size() > 0;
    }

    private QueryModelType getType(String subject) {
        switch (subject) {
            case "customentry": {
                return QueryModelType.CUSTOMINPUT;
            }
            case "simplequery": {
                return QueryModelType.SIMPLEOWER;
            }
        }
        return null;
    }
}

