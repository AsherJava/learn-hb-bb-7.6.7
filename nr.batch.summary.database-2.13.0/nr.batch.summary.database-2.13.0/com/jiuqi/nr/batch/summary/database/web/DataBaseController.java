/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.batch.summary.database.web;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.batch.summary.database.common.DataBaseErrorEnum;
import com.jiuqi.nr.batch.summary.database.service.GatherDataBaseService;
import com.jiuqi.nr.batch.summary.database.service.impl.GatherDataBaseServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/batch-summary/database"})
@Api(tags={"\u6c47\u603b\u5206\u5e93\u7269\u7406\u8868"})
public class DataBaseController {
    private static final Logger logger = LoggerFactory.getLogger(GatherDataBaseServiceImpl.class);
    @Autowired
    private GatherDataBaseService gatherDataBaseService;

    @GetMapping(value={"/isExistDataInGB/{dataSchemeKey}"})
    @ApiOperation(value="\u5224\u65ad\u8be5\u6570\u636e\u65b9\u6848\u7684\u6c47\u603b\u5206\u5e93\u4e2d\u662f\u5426\u6709\u6570\u636e")
    public boolean isExistDataInGB(@PathVariable(value="dataSchemeKey") String dataSchemeKey) throws JQException {
        if (!this.gatherDataBaseService.isExistGatherDataBase(dataSchemeKey)) {
            return false;
        }
        try {
            return this.gatherDataBaseService.isExistDataInGB(dataSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataBaseErrorEnum.NRSUMMARY_EXCEPTION_000, (Throwable)e);
        }
    }
}

