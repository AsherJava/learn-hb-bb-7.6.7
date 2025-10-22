/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.dataentry.service.IUploadService
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.util.Ini
 *  com.jiuqi.nr.single.core.util.SingleSecurityUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  nr.single.map.data.PathUtil
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package nr.single.client.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.service.IUploadService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.Ini;
import com.jiuqi.nr.single.core.util.SingleSecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nr.single.client.bean.SingleExportData;
import nr.single.client.bean.SingleExportParam;
import nr.single.client.bean.SingleUploadParam;
import nr.single.client.bean.SingleUploadResult;
import nr.single.client.service.ISingleFuncExecuteService;
import nr.single.map.data.PathUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"single/client/"})
@Api(tags={"\u5355\u673a\u7248\u5ba2\u6237\u7aef\u5904\u7406"})
public class SingleClientController {
    private static final Logger logger = LoggerFactory.getLogger(SingleClientController.class);
    @Autowired
    private IUploadService iUploadService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private ISingleFuncExecuteService funcService;

    @RequestMapping(value={"/{name}"}, method={RequestMethod.GET})
    public String getContractByContractNo(@PathVariable String name) throws Exception {
        String text = "hello world " + name;
        text = SingleSecurityUtils.cleanUrlXSS((String)text);
        return text;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        SingleSecurityUtils.validatePathManipulation((String)filePath);
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(filePath + fileName);){
            out.write(file);
            out.flush();
        }
    }

    @ApiOperation(value="\u5ba2\u6237\u7aef\u5bfc\u5165JIO\u6587\u4ef6")
    @RequestMapping(value={"/JioData/UploadFile"}, method={RequestMethod.POST})
    public String uploadFile(@RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws Exception {
        return "";
    }

    @PostMapping(value={"/JioData/import"})
    @ApiOperation(value="\u4e0a\u4f20\u6570\u636e")
    public AsyncTaskInfo importFormData(@RequestParam(value="file") MultipartFile file, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        String paramJson = request.getParameter("param");
        UploadParam param = null;
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            param = (UploadParam)mapper.readValue(paramJson, UploadParam.class);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(param.getDimensionSet());
        jtableContext.setFormSchemeKey(param.getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
        jtableContext.setTaskKey(param.getTaskKey());
        return this.iUploadService.upload(file, param);
    }

    @PostMapping(value={"/JioPara/ImportPara"})
    @ApiOperation(value="\u4e0a\u4f20\u53c2\u6570")
    public AsyncTaskInfo upLoadPara(@RequestParam(value="file") MultipartFile file, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        String paramJson = request.getParameter("param");
        SingleUploadParam param = null;
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            param = (SingleUploadParam)((Object)mapper.readValue(paramJson, SingleUploadParam.class));
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return new AsyncTaskInfo();
    }

    @PostMapping(value={"/JioData/ImportDataPart"})
    @ApiOperation(value="\u5ba2\u6237\u7aef\u4e0a\u5206\u7247\u4e0a\u4f20")
    public SingleUploadResult upLoadDataPart(@RequestParam(value="file") MultipartFile multipartFile, HttpServletRequest request) {
        SingleUploadResult result = new SingleUploadResult(true);
        ObjectMapper mapper = new ObjectMapper();
        String paramJson = request.getParameter("param");
        SingleUploadParam singleParam = null;
        UploadParam param = null;
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            singleParam = (SingleUploadParam)((Object)mapper.readValue(paramJson, SingleUploadParam.class));
            param = (UploadParam)mapper.readValue(paramJson, UploadParam.class);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            result.setSuccess(false);
            result.setMessage("\u4e0a\u4f20\u6587\u4ef6\u5931\u8d25\uff1a" + e.getMessage());
            return result;
        }
        String fileLocation = null;
        if (singleParam.getFileCount() < 1 || singleParam.getFileIndex() >= singleParam.getFileCount()) {
            result.setSuccess(false);
            result.setMessage("\u4e0a\u4f20\u6587\u4ef6\u5931\u8d25\uff1a\u5206\u7247\u6587\u4ef6\u6570\u91cf\u4e0d\u5bf9");
            return result;
        }
        if (singleParam.getFileCount() == 1) {
            AsyncTaskInfo task = this.iUploadService.upload(multipartFile, param);
            result.setId(task.getId());
        } else {
            try {
                String fileName = multipartFile.getOriginalFilename();
                SingleSecurityUtils.validatePathManipulation((String)fileName);
                File sourcefile = new File(fileName);
                fileName = sourcefile.getName();
                String[] split = fileName.split("\\.");
                String suffix = split[split.length - 1];
                if (singleParam.getFileIndex() > 0) {
                    fileLocation = singleParam.getFileLocation();
                    if (StringUtils.isEmpty((CharSequence)fileLocation)) {
                        logger.info("\u4e0a\u4f20\u8def\u5f84\u51fa\u9519\uff01");
                    }
                } else {
                    SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                    fileLocation = sfDate.format(new Date()) + OrderGenerator.newOrder();
                }
                String path = BatchExportConsts.UPLOADDIR + BatchExportConsts.SEPARATOR + fileLocation + BatchExportConsts.SEPARATOR;
                path = FilenameUtils.normalize(path);
                SingleSecurityUtils.validatePathManipulation((String)fileName);
                File pathFile = new File(path);
                if (!pathFile.exists()) {
                    pathFile.mkdirs();
                }
                String saveFile = FilenameUtils.normalize(path + BatchExportConsts.SEPARATOR + fileName);
                SingleSecurityUtils.validatePathManipulation((String)saveFile);
                File file = new File(saveFile);
                try {
                    multipartFile.transferTo(file);
                }
                catch (IllegalStateException e1) {
                    logger.error(e1.getMessage(), e1);
                }
                catch (IOException e1) {
                    logger.error(e1.getMessage(), e1);
                }
                param.setFileLocation(fileLocation);
                Ini ini = new Ini();
                try {
                    String fileIni = path + BatchExportConsts.SEPARATOR + "jioFiles.ini";
                    if (PathUtil.getFileExists((String)fileIni)) {
                        ini.loadIniContent(fileIni);
                    }
                    ini.writeString("files", "fileCount", String.valueOf(singleParam.getFileCount()));
                    ini.writeString("files", "count", String.valueOf(singleParam.getFileIndex() + 1));
                    ini.writeString("files", "filename_" + singleParam.getFileIndex(), fileName);
                    ini.writeString("files", "filesize_" + singleParam.getFileIndex(), String.valueOf(file.length()));
                    ini.saveToFile(fileIni);
                }
                catch (Exception e) {
                    result.setSuccess(false);
                    result.setMessage(e.getMessage());
                    logger.error(e.getMessage(), e);
                }
                logger.info("JIO\u6570\u636e\u5bfc\u5165\uff1a\u63a5\u6536\u6587\u4ef6\uff1a" + fileLocation + BatchExportConsts.SEPARATOR + fileName);
                if (singleParam.getFileIndex() == singleParam.getFileCount() - 1) {
                    String newJioFile = FilenameUtils.normalize(path + BatchExportConsts.SEPARATOR + OrderGenerator.newOrder() + ".jio");
                    SingleSecurityUtils.validatePathManipulation((String)newJioFile);
                    File AllFile = new File(newJioFile);
                    ArrayList<String> files = new ArrayList<String>();
                    for (int i = 0; i < singleParam.getFileCount(); ++i) {
                        String subFileName = ini.readString("files", "filename_" + i, "");
                        String subFileName1 = FilenameUtils.normalize(path + BatchExportConsts.SEPARATOR + subFileName);
                        SingleSecurityUtils.validatePathManipulation((String)subFileName1);
                        File subFile = new File(subFileName1);
                        if (subFile.exists()) {
                            files.add(subFileName);
                            continue;
                        }
                        logger.info("\u6587\u4ef6\u4e0d\u5b58\u5728\uff1a" + subFileName);
                    }
                    PathUtil.mergeFiles(files, (String)path, (File)AllFile);
                    logger.info("JIO\u6570\u636e\u5bfc\u5165\uff1a\u5408\u5e76\u6587\u4ef6\uff1a" + fileLocation + BatchExportConsts.SEPARATOR + AllFile);
                    AsyncTaskInfo task = this.iUploadService.uploadFile(param, suffix, AllFile);
                    result.setId(task.getId());
                }
            }
            catch (SingleFileException e2) {
                logger.error(e2.getMessage(), e2);
            }
        }
        result.setFileIndex(singleParam.getFileIndex());
        result.setFileCount(singleParam.getFileCount());
        result.setFileLocation(fileLocation);
        return result;
    }

    @PostMapping(value={"/JioPara/exportParaByMap"})
    @ApiOperation(value="\u5bfc\u51faJIO\u53c2\u6570\u6587\u4ef6\uff1a")
    public void exportParaByMap(@RequestBody SingleExportParam param, HttpServletResponse response, HttpServletRequest request) throws Exception {
        String agent = request.getHeader("User-Agent").toLowerCase();
        try {
            SingleExportData result = this.funcService.export(param);
            String suffix = ".jio";
            String fileName = "";
            fileName = result == null ? param.getSheetName() + suffix : result.getFileName() + suffix;
            if (agent.indexOf("firefox") >= 0) {
                fileName.replace(" ", "_");
            }
            String resultFileName = new String(fileName.getBytes(), "iso8859-1");
            resultFileName = SingleSecurityUtils.cleanHeaderValue((String)resultFileName);
            response.setContentType("application/octet-stream");
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + resultFileName);
            ServletOutputStream outputStream = response.getOutputStream();
            if (result == null) {
                outputStream.write(new byte[0]);
            } else {
                outputStream.write(result.getData());
            }
            response.flushBuffer();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @GetMapping(value={"/query-fmdm-form/{reportSchemeKey}"})
    @ApiOperation(value="\u67e5\u8be2\u5c01\u9762\u4ee3\u7801\u8868\u5355")
    public String queryFMDMFormKey(@PathVariable(value="reportSchemeKey") String reportSchemeKey) {
        FormDefine form = this.runtimeView.queryFmdmFormDefineByFormScheme(reportSchemeKey);
        if (form != null) {
            return form.getKey();
        }
        return "";
    }
}

