/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.ErrorType
 *  com.jiuqi.nr.datascheme.common.DataSchemeLoggerHelper
 *  com.jiuqi.nr.datascheme.internal.deploy.DataSchemeDeployFactory
 *  com.jiuqi.nr.datascheme.internal.deploy.IDataTableDeployObjGetter
 *  com.jiuqi.nr.datascheme.internal.deploy.ITableModelIndexBuilder
 *  com.jiuqi.nr.datascheme.internal.deploy.common.RuntimeDataTableDTO
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.datascheme.fix.web.rest;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.ErrorType;
import com.jiuqi.nr.datascheme.common.DataSchemeLoggerHelper;
import com.jiuqi.nr.datascheme.fix.web.facade.TableIndexFixVO;
import com.jiuqi.nr.datascheme.internal.deploy.DataSchemeDeployFactory;
import com.jiuqi.nr.datascheme.internal.deploy.IDataTableDeployObjGetter;
import com.jiuqi.nr.datascheme.internal.deploy.ITableModelIndexBuilder;
import com.jiuqi.nr.datascheme.internal.deploy.common.RuntimeDataTableDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u6570\u636e\u65b9\u6848\uff1a\u7d22\u5f15\u4fee\u590d"})
public class DataTableIndexFixRestController {
    @Autowired
    private IDataTableDeployObjGetter iDataTableDeployObjGetter;
    @Autowired
    private DataSchemeDeployFactory dataSchemeDeployFactory;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataTableIndexFixRestController.class);

    @ApiOperation(value="\u7d22\u5f15\u68c0\u67e5")
    @PostMapping(value={"fix/table/index/check/{dataSchemeKey}"})
    public List<TableIndexFixVO> check(@PathVariable String dataSchemeKey) throws JQException {
        int dataTypeType = DataTableType.DETAIL.getValue() + DataTableType.SUB_TABLE.getValue() + DataTableType.MULTI_DIM.getValue() + DataTableType.ACCOUNT.getValue();
        List runtimeDataTables = this.iDataTableDeployObjGetter.getRuntimeDataTable(dataSchemeKey, dataTypeType);
        ArrayList<TableIndexFixVO> result = new ArrayList<TableIndexFixVO>();
        LOGGER.info("\u6570\u636e\u65b9\u6848\uff1a\u68c0\u67e5\u6570\u636e\u8868\u7d22\u5f15");
        for (RuntimeDataTableDTO runtimeDataTable : runtimeDataTables) {
            DataTable dataTable = runtimeDataTable.getDataTable();
            int errorType = this.dataSchemeDeployFactory.getIndexBuilder(dataTable.getDataTableType()).doCheck(runtimeDataTable);
            result.add(new TableIndexFixVO(dataTable, errorType));
        }
        return result;
    }

    @ApiOperation(value="\u7d22\u5f15\u91cd\u5efa")
    @PostMapping(value={"fix/table/index/rebuild/{dataSchemeKey}/{errorType}"})
    public List<TableIndexFixVO> rebuild(@PathVariable String dataSchemeKey, @PathVariable int errorType, @RequestBody(required=false) List<String> dataTableKeys) throws JQException {
        if (0 == (ErrorType.NONE.getValue() ^ errorType)) {
            return this.check(dataSchemeKey);
        }
        List runtimeDataTables = !CollectionUtils.isEmpty(dataTableKeys) ? this.iDataTableDeployObjGetter.getRuntimeDataTable(dataTableKeys) : this.iDataTableDeployObjGetter.getRuntimeDataTable(dataSchemeKey);
        ArrayList<String> details = new ArrayList<String>();
        ErrorType[] errorTypes = ErrorType.interestType((int)errorType);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        LOGGER.info("\u6570\u636e\u65b9\u6848\uff1a\u4fee\u590d\u6570\u636e\u8868\u7d22\u5f15");
        this.logStart(dataScheme, errorTypes);
        StringBuilder builder = new StringBuilder();
        for (RuntimeDataTableDTO runtimeDataTable : runtimeDataTables) {
            DataTable dataTable = runtimeDataTable.getDataTable();
            ITableModelIndexBuilder indexBuilder = this.dataSchemeDeployFactory.getIndexBuilder(dataTable.getDataTableType());
            builder.setLength(0);
            builder.append(runtimeDataTable.getDataTable().getTitle()).append("[").append(runtimeDataTable.getDataTable().getCode()).append("]\uff0c");
            try {
                indexBuilder.doRebuild(runtimeDataTable, errorType);
                builder.append("\u4fee\u590d\u6210\u529f\u3002");
            }
            catch (JQException e) {
                LOGGER.error("\u6570\u636e\u65b9\u6848\uff1a\u4fee\u590d\u6570\u636e\u8868\u7d22\u5f15\u5931\u8d25", e);
                builder.append("\u4fee\u590d\u5931\u8d25\uff1a").append((Object)e);
            }
            details.add(builder.toString());
        }
        this.logEnd(dataScheme, errorTypes, details);
        return this.check(dataSchemeKey);
    }

    private StringBuilder log(DataScheme dataScheme, ErrorType[] errorTypes) {
        StringBuilder builder = new StringBuilder();
        builder.append("\u6570\u636e\u65b9\u6848 Key\uff1a").append(dataScheme.getKey());
        builder.append("\uff0c\n\u6570\u636e\u65b9\u6848 Code\uff1a").append(dataScheme.getCode());
        builder.append("\uff0c\n\u6570\u636e\u65b9\u6848 Title\uff1a").append(dataScheme.getTitle());
        builder.append("\uff0c\n\u4fee\u590d\u7c7b\u578b\uff1a").append(Arrays.stream(errorTypes).map(ErrorType::getDesc).collect(Collectors.toList()));
        return builder;
    }

    private void logStart(DataScheme dataScheme, ErrorType[] errorTypes) {
        StringBuilder builder = this.log(dataScheme, errorTypes);
        DataSchemeLoggerHelper.info((String)"\u7d22\u5f15\u4fee\u590d\u5f00\u59cb", (String)builder.toString());
    }

    private void logEnd(DataScheme dataScheme, ErrorType[] errorTypes, List<String> details) {
        StringBuilder builder = this.log(dataScheme, errorTypes);
        builder.append("\uff0c\n\u4fee\u590d\u8be6\u60c5\uff1a\n").append(String.join((CharSequence)"\n", details));
        DataSchemeLoggerHelper.info((String)"\u7d22\u5f15\u4fee\u590d\u7ed3\u675f", (String)builder.toString());
    }
}

