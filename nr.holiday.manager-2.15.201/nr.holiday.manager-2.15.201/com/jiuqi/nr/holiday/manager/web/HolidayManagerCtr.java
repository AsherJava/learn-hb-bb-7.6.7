/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.holiday.manager.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.holiday.manager.facade.HolidayObj;
import com.jiuqi.nr.holiday.manager.service.IHolidayManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(tags={"\u8282\u5047\u65e5\u7ba1\u7406"})
@JQRestController
@RequestMapping(value={"api/v1/holiday"})
public class HolidayManagerCtr {
    @Autowired
    private IHolidayManagerService iHolidayManagerService;

    @ApiOperation(value="\u67e5\u8be2")
    @RequestMapping(value={"query/{year}"}, method={RequestMethod.GET})
    public List<HolidayObj> doQuery(@PathVariable String year) {
        return this.iHolidayManagerService.doQuery(year);
    }

    @ApiOperation(value="\u4fdd\u5b58")
    @RequestMapping(value={"save/{year}"}, method={RequestMethod.POST})
    public void doSave(@PathVariable String year, @RequestBody List<HolidayObj> objs) throws JQException {
        this.iHolidayManagerService.doSave(year, objs);
    }

    @ApiOperation(value="\u5220\u9664")
    @RequestMapping(value={"delete/{year}"}, method={RequestMethod.GET})
    public void doDelete(@PathVariable String year) throws JQException {
        this.iHolidayManagerService.doDelete(year);
    }
}

