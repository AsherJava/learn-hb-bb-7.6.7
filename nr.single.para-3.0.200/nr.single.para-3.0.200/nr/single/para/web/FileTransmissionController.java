/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.annotation.Loggable
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.single.core.util.SingleSecurityUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  nr.single.map.common.SingleResult
 *  nr.single.map.configurations.bean.AutoAppendCode
 *  nr.single.map.configurations.bean.SingleFileInfo
 *  nr.single.map.configurations.file.bean.FileAnalysisPojo
 *  nr.single.map.configurations.file.bean.UploadingParam
 *  nr.single.map.configurations.service.MappingFileService
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package nr.single.para.web;

import com.jiuqi.np.log.annotation.Loggable;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.single.core.util.SingleSecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import nr.single.map.common.SingleResult;
import nr.single.map.configurations.bean.AutoAppendCode;
import nr.single.map.configurations.bean.SingleFileInfo;
import nr.single.map.configurations.file.bean.FileAnalysisPojo;
import nr.single.map.configurations.file.bean.UploadingParam;
import nr.single.map.configurations.service.MappingFileService;
import nr.single.para.configurations.service.FileAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/single/mapping"})
@Loggable(value="\u914d\u7f6e\u6587\u4ef6\u4f20\u8f93")
@Api(tags={"JIO\u6620\u5c04\u65b9\u6848\u914d\u7f6e"})
public class FileTransmissionController {
    private static final Logger log = LoggerFactory.getLogger(FileTransmissionController.class);
    @Autowired
    private FileAnalysisService fileAnalysisService;
    @Autowired
    private FileService fileService;
    @Autowired
    private MappingFileService mappingFileService;

    @PostMapping(value={"/jio-upload"})
    @ApiOperation(value="JIO\u6620\u5c04\u6587\u4ef6\u4e0a\u4f20")
    public SingleResult getMappingJIOFile(@RequestParam(value="file") MultipartFile jio, HttpServletRequest request) {
        ImportParamter paramters = new ImportParamter(request).invoke();
        String fileName = jio.getOriginalFilename();
        SingleResult result = SingleResult.ok();
        try {
            FileAnalysisPojo fileAnalysisPojo = this.fileAnalysisService.getMappingConfig(paramters.getTaskId(), paramters.getMapping(), paramters.getScheme(), fileName, jio.getBytes());
            result.setData((Object)fileAnalysisPojo);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @PostMapping(value={"/jio-reload"})
    @ApiOperation(value="JIO\u6620\u5c04\u6587\u4ef6\u91cd\u65b0\u4e0a\u4f20")
    public SingleResult reloadMappingJIOFile(@RequestParam(value="file") MultipartFile jio, HttpServletRequest request) {
        ImportParamter paramters = new ImportParamter(request).invoke();
        String fileName = jio.getOriginalFilename();
        SingleResult result = SingleResult.ok();
        try {
            FileAnalysisPojo fileAnalysisPojo = this.fileAnalysisService.reloadMappingConfig(paramters.getTaskId(), paramters.getMapping(), paramters.getScheme(), fileName, jio.getBytes());
            result.setData((Object)fileAnalysisPojo);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @PostMapping(value={"/entity-upload"})
    @ApiOperation(value="\u5355\u4f4d\u6620\u5c04\u6587\u4ef6\u5bfc\u5165")
    public SingleResult getMappingEntityFile(@RequestParam(value="file") MultipartFile entity, HttpServletRequest request) {
        ImportParamter paramters = new ImportParamter(request).invoke();
        String fileName = entity.getOriginalFilename();
        SingleResult result = SingleResult.ok();
        try {
            FileAnalysisPojo entityFile = this.fileAnalysisService.getEntityFile(paramters.getMapping(), fileName, entity.getBytes());
            result.setData((Object)entityFile);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @PostMapping(value={"/zb-upload"})
    @ApiOperation(value="\u6307\u6807\u6620\u5c04\u6587\u4ef6\u5bfc\u5165")
    public SingleResult getMappingZbFile(@RequestParam(value="file") MultipartFile zb, HttpServletRequest request) {
        ImportParamter paramters = new ImportParamter(request).invoke();
        String fileName = zb.getOriginalFilename();
        SingleResult result = SingleResult.ok();
        try {
            FileAnalysisPojo zbFile = this.fileAnalysisService.getZbFile(paramters.getMapping(), fileName, zb.getBytes());
            result.setData((Object)zbFile);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @CrossOrigin(value={"*"})
    @GetMapping(value={"/formula-export/{mappingKey}/{formulaSchemeKey}"})
    @ApiOperation(value="\u516c\u5f0f\u6620\u5c04\u6587\u4ef6\u5bfc\u51fa")
    public void exportFormula(@PathVariable(value="mappingKey") String mappingKey, @PathVariable(value="formulaSchemeKey") String formulaSchemeKey, HttpServletResponse response) throws IOException {
        this.fileAnalysisService.exportFormulaMapping(mappingKey, formulaSchemeKey, response);
        response.flushBuffer();
    }

    @CrossOrigin(value={"*"})
    @GetMapping(value={"/entity-export/{mappingKey}"})
    @ApiOperation(value="\u5355\u4f4d\u6620\u5c04\u6587\u4ef6\u5bfc\u51fa")
    public void exportEntity(@PathVariable(value="mappingKey") String mappingKey, HttpServletResponse response) throws IOException {
        this.fileAnalysisService.exportEntityMapping(mappingKey, response);
        response.flushBuffer();
    }

    @PostMapping(value={"/formula-upload"})
    @ApiOperation(value="\u516c\u5f0f\u6620\u5c04\u6587\u4ef6\u5bfc\u5165")
    public SingleResult getMappingFormula(@RequestParam(value="file") MultipartFile formlua, HttpServletRequest request) {
        ImportParamter paramters = new ImportParamter(request).invoke();
        String fileName = formlua.getOriginalFilename();
        SingleResult result = SingleResult.ok();
        try {
            FileAnalysisPojo formulaFile = this.fileAnalysisService.getFormulaFile(paramters.getMapping(), fileName, formlua.getBytes(), paramters.getFormulaScheme());
            result.setData((Object)formulaFile);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @GetMapping(value={"/file-download/{fileKey}"})
    @ApiOperation(value="\u6620\u5c04\u6587\u4ef6\u4e0b\u8f7d")
    public void downloadMappingFile(@PathVariable(value="fileKey") String fileKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
        FileAreaService singleFile = this.fileService.area("single");
        FileInfo info = singleFile.getInfo(fileKey);
        if (info == null) {
            throw new FileNotFoundException(fileKey);
        }
        String resultFileName = SingleSecurityUtils.cleanHeaderValue((String)info.getName());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + resultFileName);
        singleFile.download(fileKey, (OutputStream)response.getOutputStream());
        response.flushBuffer();
    }

    @GetMapping(value={"/query-file/{mappingKey}"})
    @ApiOperation(value="\u67e5\u8be2\u6620\u5c04\u6587\u4ef6\u4e0b\u8f7d\u5730\u5740")
    public SingleResult getFileAddress(@PathVariable(value="mappingKey") String mappingKey) {
        SingleFileInfo singleFileInfo = this.mappingFileService.queryFileInfo(mappingKey);
        return SingleResult.build((Integer)200, (String)"", (Object)singleFileInfo);
    }

    @PostMapping(value={"/insert-fileData"})
    @ApiOperation(value="\u5ffd\u7565\u9519\u8bef\u7ee7\u7eed\u4e0a\u4f20")
    public SingleResult uploadFileWithoutError(@Valid @RequestBody UploadingParam param) {
        SingleResult result = SingleResult.ok();
        try {
            FileInfo info = this.fileAnalysisService.uploadCacheData(param);
            result.setData((Object)info);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setStatus(Integer.valueOf(201));
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @PostMapping(value={"/append-code-import"})
    @ApiOperation(value="\u5bfc\u5165\u52a0\u957f\u7801\u914d\u7f6e")
    public SingleResult importAppendCode(@RequestParam(value="file") MultipartFile file, HttpServletRequest request) {
        ImportParamter paramters = new ImportParamter(request).invoke();
        SingleResult result = SingleResult.ok();
        try {
            AutoAppendCode autoAppendCode = this.fileAnalysisService.uploadAppendCodeMapping(paramters.getMapping(), file.getBytes());
            result.setData((Object)autoAppendCode);
        }
        catch (Exception e) {
            result.setStatus(Integer.valueOf(201));
            result.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @CrossOrigin(value={"*"})
    @GetMapping(value={"/append-code-export/{mappingKey}"})
    @ApiOperation(value="\u52a0\u957f\u7801\u914d\u7f6e\u5bfc\u51fa")
    public void exportAppendCode(@PathVariable(value="mappingKey") String mappingKey, HttpServletResponse response) throws IOException {
        this.fileAnalysisService.exportAppendCode(mappingKey, response);
        response.flushBuffer();
    }

    private class ImportParamter {
        private HttpServletRequest request;
        private String mapping;
        private String taskId;
        private String scheme;
        private String formulaScheme;

        public ImportParamter(HttpServletRequest request) {
            this.request = request;
        }

        public String getMapping() {
            return this.mapping;
        }

        public String getTaskId() {
            return this.taskId;
        }

        public String getScheme() {
            return this.scheme;
        }

        public String getFormulaScheme() {
            return this.formulaScheme;
        }

        public ImportParamter invoke() {
            this.mapping = this.request.getParameter("mapping");
            this.taskId = this.request.getParameter("task");
            this.scheme = this.request.getParameter("scheme");
            this.formulaScheme = this.request.getParameter("formulaScheme");
            return this;
        }
    }
}

