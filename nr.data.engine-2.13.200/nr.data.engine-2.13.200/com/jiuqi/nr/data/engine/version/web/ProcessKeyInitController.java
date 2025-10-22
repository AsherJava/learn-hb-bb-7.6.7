/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.definition.exception.CreateSystemTableException
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.service.DesignFormSchemeDefineService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  org.json.JSONObject
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.data.engine.version.web;

import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.data.engine.version.impl.DataInitUtil;
import com.jiuqi.nr.definition.exception.CreateSystemTableException;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.service.DesignFormSchemeDefineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u5386\u53f2\u6570\u636e\u6539\u9020"})
public class ProcessKeyInitController {
    private static final Logger logger = LogFactory.getLogger(ProcessKeyInitController.class);
    @Resource
    IDataDefinitionDesignTimeController dataController;
    @Resource
    DesignFormSchemeDefineService designFormSchemeService;
    @Resource
    IDataDefinitionRuntimeController runtimeController;

    @ApiOperation(value="\u6d41\u7a0b\u5b9a\u4e49\u521d\u59cb\u5316")
    @RequestMapping(value={"/historydata/processkey"}, method={RequestMethod.GET})
    public String initVersion() {
        JSONObject jsonResult = new JSONObject();
        try {
            ArrayList<String> tableCodeList = new ArrayList<String>();
            ArrayList<String> runtimeList = new ArrayList<String>();
            List formSchemeDefines = this.designFormSchemeService.queryAllFormSchemeDefine();
            for (DesignFormSchemeDefine formSchemeDefine : formSchemeDefines) {
                tableCodeList.add(DataInitUtil.getSysHistotyTableName((FormSchemeDefine)formSchemeDefine));
                tableCodeList.add(DataInitUtil.getSysStateTableName((FormSchemeDefine)formSchemeDefine));
            }
            if (tableCodeList.size() > 0) {
                for (String tableCode : tableCodeList) {
                    DesignTableDefine tableDefine = null;
                    try {
                        tableDefine = this.dataController.queryTableDefinesByCode(tableCode);
                    }
                    catch (Exception e) {
                        throw new CreateSystemTableException(String.format("query table %s error.", tableCode), (Throwable)e);
                    }
                    if (null == tableDefine) continue;
                    String tableKey = tableDefine.getKey();
                    DesignFieldDefine fieldDefine = this.dataController.queryFieldDefineByCodeInTable("PROCESSKEY", tableKey);
                    TableDefine runTimeTableDefine = this.runtimeController.queryTableDefine(tableKey);
                    if (null == runTimeTableDefine) continue;
                    runtimeList.add(tableKey);
                }
            }
            if (runtimeList.size() > 0) {
                for (String string : runtimeList) {
                }
            }
            jsonResult.put("status", (Object)HttpStatus.OK);
        }
        catch (Exception e) {
            Log.error((Exception)e);
            jsonResult.put("status", (Object)HttpStatus.INTERNAL_SERVER_ERROR);
            jsonResult.put("errorMsg", (Object)e.getMessage());
        }
        return jsonResult.toString();
    }
}

