/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.table.io.json.DataFrameSerialize
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.dafafill.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.dafafill.exception.DataFillRuntimeException;
import com.jiuqi.nr.dafafill.model.DFDAddRowConfirmResult;
import com.jiuqi.nr.dafafill.model.DFDAddRowQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataResult;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveInfo;
import com.jiuqi.nr.dafafill.model.DataFillDimensionTitle;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataAnalysisInfo;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataResult;
import com.jiuqi.nr.dafafill.model.DataFillResult;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.dafafill.service.IDataFillDataEnvImportExportService;
import com.jiuqi.nr.dafafill.service.IDataFillDataEnvService;
import com.jiuqi.nr.dafafill.service.IDataFillEntityDataService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.table.io.json.DataFrameSerialize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/api/v1/datafill/data-env"})
@Api(value="\u81ea\u5b9a\u4e49\u5f55\u5165\u6570\u636e\u5f15\u64ce")
public class DataFillDataEnvController {
    private static final Logger logger = LoggerFactory.getLogger(DataFillDataEnvController.class);
    @Autowired
    private IDataFillDataEnvService dataFillDataEnvService;
    @Autowired
    private IDataFillEntityDataService dataFillEntityDataService;
    @Autowired
    private IDataFillDataEnvImportExportService dataFillDataEnvImportExportService;
    @Autowired
    private FileService fileService;
    @Autowired
    private IDFDimensionQueryFieldParser dimensionQueryFieldParser;
    private static final String CONTENT_DISPOSITION = "Content-disposition";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NRContextBuild
    @PostMapping(value={"/query"})
    @ApiOperation(value="\u67e5\u8be2\u6570\u636e")
    @RequiresPermissions(value={"nr:datafill:app"})
    public void query(@Valid @RequestBody DataFillDataQueryInfo queryInfo, HttpServletResponse response) {
        OutputStream output = null;
        try {
            DataFillDataResult dataFillDataResult = this.dataFillDataEnvService.query(queryInfo);
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer((JsonSerializer)new DataFrameSerialize());
            mapper.registerModule((Module)module);
            response.setContentType("application/json;charset=UTF-8");
            output = response.getOutputStream();
            output.write(mapper.writeValueAsString((Object)dataFillDataResult).getBytes("UTF-8"));
            output.flush();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            if (output != null) {
                try {
                    output.close();
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
    }

    @PostMapping(value={"/entitydata/query"})
    @ApiOperation(value="\u5355\u5143\u683c\u53ef\u9009\u503c\u67e5\u8be2")
    @RequiresPermissions(value={"nr:datafill:app"})
    public DataFillEntityDataResult entitydataQuery(@RequestBody DataFillEntityDataQueryInfo queryInfo) throws JQException {
        return this.dataFillEntityDataService.query(queryInfo);
    }

    @PostMapping(value={"/entitydata/path"})
    @ApiOperation(value="\u5355\u5143\u683c\u4e0b\u62c9path\u5b9a\u4f4d")
    @RequiresPermissions(value={"nr:datafill:app"})
    public Object entitydataPath(@RequestBody DataFillEntityDataQueryInfo queryInfo) throws JQException {
        if (queryInfo.getCode() == null || !queryInfo.getCode().contains(";")) {
            return this.dataFillEntityDataService.queryByIdOrCode(queryInfo);
        }
        return this.dataFillEntityDataService.queryMultiValByIdOrCode(queryInfo);
    }

    @PostMapping(value={"/entitydata/analysis"})
    @ApiOperation(value="\u7528\u4e8e\u590d\u5236\u7c98\u8d34\u7684\u679a\u4e3e\u89e3\u6790")
    @RequiresPermissions(value={"nr:datafill:app"})
    public List<Object> entitydataAnalysis(@RequestBody DataFillEntityDataAnalysisInfo queryInfo) throws JQException {
        return this.dataFillEntityDataService.queryByPrimaryOrSearch(queryInfo);
    }

    @NRContextBuild
    @PostMapping(value={"/save"})
    @ApiOperation(value="\u4fdd\u5b58\u6570\u636e")
    @RequiresPermissions(value={"nr:datafill:app"})
    public DataFillResult save(@RequestBody DataFillDataSaveInfo saveInfo) throws JQException {
        return this.dataFillDataEnvService.save(saveInfo);
    }

    @PostMapping(value={"/export"})
    @ApiOperation(value="\u5bfc\u51fa\u6570\u636e")
    @RequiresPermissions(value={"nr:datafill:app"})
    public AsyncTaskInfo export(@Valid @RequestBody DataFillDataQueryInfo queryInfo) throws JQException {
        return this.dataFillDataEnvImportExportService.export(queryInfo);
    }

    @GetMapping(value={"/export/download/{key}"})
    @ApiOperation(value="\u5bfc\u51fa\u6570\u636e")
    @RequiresPermissions(value={"nr:datafill:app"})
    public void export(@PathVariable String key, HttpServletResponse response) throws JQException {
        key = HtmlUtils.cleanUrlXSS((String)key);
        FileInfo fileInfo = this.fileService.area("DATA_FILL_EXPORT").getInfo(key);
        if (null != fileInfo) {
            try (BufferedOutputStream ous = new BufferedOutputStream((OutputStream)response.getOutputStream());){
                response.setContentType("application/octet-stream");
                response.setHeader("Access-Control-Expose-Headers", CONTENT_DISPOSITION);
                response.setHeader(CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + URLEncoder.encode(fileInfo.getName(), "UTF-8").replaceAll("\\+", "%20"));
                response.addHeader("Content-Length", String.valueOf(fileInfo.getSize()));
                this.fileService.area("DATA_FILL_EXPORT").download(fileInfo.getKey(), (OutputStream)ous);
                ((OutputStream)ous).flush();
                response.flushBuffer();
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        } else {
            throw new DataFillRuntimeException("\u4e0b\u8f7d\u6587\u4ef6\u51fa\u9519");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/import"})
    @ApiOperation(value="\u5bfc\u51fa\u6570\u636e")
    @RequiresPermissions(value={"nr:datafill:app"})
    public AsyncTaskInfo importData(@RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws JQException {
        ObjectMapper mapper = new ObjectMapper();
        String paramJson = request.getParameter("param");
        DataFillDataQueryInfo queryInfo = null;
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            queryInfo = (DataFillDataQueryInfo)mapper.readValue(paramJson, DataFillDataQueryInfo.class);
            DsContext context = DsContextHolder.getDsContext();
            DsContextImpl dsContext = (DsContextImpl)context;
            NRContext nrContextInfo = new NRContext((INRContext)queryInfo);
            dsContext.setEntityId(nrContextInfo.getContextEntityId());
            dsContext.setFilterExpression(nrContextInfo.getContextFilterExpression());
            AsyncTaskInfo asyncTaskInfo = this.dataFillDataEnvImportExportService.importData(queryInfo, file);
            return asyncTaskInfo;
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            DsContextHolder.clearContext();
        }
        return null;
    }

    @PostMapping(value={"/download-fail-file"})
    @ApiOperation(value="\u4e0b\u8f7d\u5931\u8d25\u7684\u5bfc\u5165\u6587\u4ef6")
    @RequiresPermissions(value={"nr:datafill:app"})
    public void downloadFailFile(@RequestBody Map<String, String> requestBody, HttpServletResponse response) {
        File file = new File(FilenameUtils.normalize(requestBody.get("filePath")));
        if (file.exists() && file.isFile()) {
            try (BufferedInputStream ins = new BufferedInputStream(Files.newInputStream(file.toPath(), new OpenOption[0]));
                 BufferedOutputStream ous = new BufferedOutputStream((OutputStream)response.getOutputStream());){
                byte[] buffer = new byte[((InputStream)ins).available()];
                ((InputStream)ins).read(buffer);
                response.setHeader("Access-Control-Expose-Headers", CONTENT_DISPOSITION);
                response.setHeader(CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + URLEncoder.encode(file.getName(), "UTF-8"));
                response.addHeader("Content-Length", "" + file.length());
                response.setContentType("application/octet-stream");
                ((OutputStream)ous).write(buffer);
                ((OutputStream)ous).flush();
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }

    @PostMapping(value={"/get-period-list"})
    @ApiOperation(value="\u83b7\u53d6\u65f6\u671f\u5217\u8868")
    @RequiresPermissions(value={"nr:datafill:app"})
    public List<DataFillDimensionTitle> getPeriodList(@RequestBody DataFillEntityDataQueryInfo queryInfo) {
        return this.dimensionQueryFieldParser.getPeriodList(queryInfo.getContext());
    }

    @PostMapping(value={"/add-row-confirm"})
    @ApiOperation(value="\u8868\u8ffd\u52a0\u884c\u6743\u9650\u786e\u8ba4")
    @RequiresPermissions(value={"nr:datafill:app"})
    public DFDAddRowConfirmResult floatAddRowConfirm(@Valid @RequestBody DFDAddRowQueryInfo queryInfo) {
        return this.dataFillDataEnvService.floatAddRowConfirm(queryInfo);
    }
}

