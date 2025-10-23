/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u68c0\u67e5\u662f\u5426\u5df2\u7ecf\u5f55\u5165\u6570\u636e"})
public class DataCheckRestController {
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;

    @ApiOperation(value="\u68c0\u6d4b\u6307\u6807\u6240\u5728\u7269\u7406\u8868\u662f\u5426\u5f55\u5165\u6570\u636e")
    @PostMapping(value={"field/check/data/{key}"})
    public ResultVO checkDataField(@PathVariable String key) {
        try {
            boolean checkData = this.runtimeDataSchemeService.dataFieldCheckData(new String[]{key});
            return new ResultVO(!checkData);
        }
        catch (Exception e) {
            return new ResultVO(false, e.getMessage());
        }
    }

    @ApiOperation(value="\u68c0\u6d4b\u6307\u6807\u6240\u5728\u7269\u7406\u8868\u662f\u5426\u5f55\u5165\u6570\u636e")
    @PostMapping(value={"field/check/data/batch"})
    public ResultVO checkDataFields(@RequestBody List<String> keys) {
        if (keys.isEmpty()) {
            return new ResultVO();
        }
        try {
            boolean checkData = this.runtimeDataSchemeService.dataFieldCheckData(keys.toArray(new String[0]));
            return new ResultVO(!checkData);
        }
        catch (Exception e) {
            return new ResultVO(false, e.getMessage());
        }
    }

    @ApiOperation(value="\u68c0\u67e5\u6570\u636e\u8868\u662f\u5426\u5f55\u5165\u6570\u636e")
    @PostMapping(value={"table/check/data/{key}"})
    public ResultVO checkDataTable(@PathVariable String key) {
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(key);
        if (null == deployInfos || deployInfos.isEmpty()) {
            return new ResultVO();
        }
        try {
            boolean checkData = this.runtimeDataSchemeService.dataTableCheckData(new String[]{key});
            return new ResultVO(!checkData);
        }
        catch (Exception e) {
            return new ResultVO(false, e.getMessage());
        }
    }

    @ApiOperation(value="\u68c0\u67e5\u5206\u7ec4\u662f\u5426\u5f55\u5165\u6570\u636e")
    @PostMapping(value={"group/check/data/{key}"})
    public ResultVO checkDataGroup(@PathVariable String key) {
        List<String> dataTableKeys = this.getDataTableKeysByGroup(key);
        try {
            boolean checkData = this.runtimeDataSchemeService.dataTableCheckData(dataTableKeys.toArray(new String[0]));
            return new ResultVO(!checkData);
        }
        catch (Exception e) {
            return new ResultVO(false, e.getMessage());
        }
    }

    private List<String> getDataTableKeysByGroup(String groupKey) {
        List dataGroupByParent;
        ArrayList<String> dataTableKeys = new ArrayList<String>();
        List dataTableByGroup = this.designDataSchemeService.getDataTableByGroup(groupKey);
        if (!dataTableByGroup.isEmpty()) {
            for (DesignDataTable dataTable : dataTableByGroup) {
                dataTableKeys.add(dataTable.getKey());
            }
        }
        if (!(dataGroupByParent = this.designDataSchemeService.getDataGroupByParent(groupKey)).isEmpty()) {
            for (DesignDataGroup dataGroup : dataGroupByParent) {
                List<String> dataTableKeysByGroup = this.getDataTableKeysByGroup(dataGroup.getKey());
                if (dataTableKeysByGroup.isEmpty()) continue;
                dataTableKeys.addAll(dataTableKeysByGroup);
            }
        }
        return dataTableKeys;
    }

    @ApiOperation(value="\u68c0\u67e5\u6570\u636e\u65b9\u6848\u662f\u5426\u5df2\u7ecf\u5f55\u5165\u6570\u636e")
    @PostMapping(value={"scheme/check/data/{key}"})
    public ResultVO checkDataScheme(@PathVariable String key) {
        try {
            boolean checkData = this.runtimeDataSchemeService.dataSchemeCheckData(key);
            return new ResultVO(!checkData);
        }
        catch (Exception e) {
            return new ResultVO(false, e.getMessage());
        }
    }

    public static class ResultVO {
        private final boolean success;
        private String message;

        public ResultVO() {
            this.success = true;
        }

        public ResultVO(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public ResultVO(boolean success) {
            this.success = success;
        }

        public boolean isSuccess() {
            return this.success;
        }

        public boolean isError() {
            return !this.success;
        }

        public String getMessage() {
            return this.message;
        }
    }
}

