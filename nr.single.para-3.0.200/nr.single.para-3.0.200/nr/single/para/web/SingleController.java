/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.zip.ZipHelper
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.single.core.dbf.DbfTable
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.file.SingleFile
 *  com.jiuqi.nr.single.core.para.parser.JIOParamParser
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  nr.single.map.data.PathUtil
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package nr.single.para.web;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.zip.ZipHelper;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.single.core.dbf.DbfTable;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.para.parser.JIOParamParser;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nr.single.map.data.PathUtil;
import nr.single.para.common.NrSingleErrorEnum;
import nr.single.para.parain.controller.ISingleParaImportController;
import nr.single.para.parain.controller.SingleParaImportOption;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"single/"})
@Api(tags={"JIO\u6587\u4ef6\u5904\u7406"})
public class SingleController {
    private static final Logger logger = LoggerFactory.getLogger(SingleController.class);
    @Autowired
    private ISingleParaImportController paraImportController;

    @RequestMapping(value={"/{name}"}, method={RequestMethod.GET})
    public String getContractByContractNo(@PathVariable String name) throws Exception {
        if (name.equals("jioImport")) {
            Date a1 = new Date();
            this.paraImportController.ImportSingeTask("D:\\test.jio");
            Date a2 = new Date();
            return "test" + a1.toString() + "_" + a2.toString();
        }
        if (name.equals("jio2")) {
            Date a1 = new Date();
            this.paraImportController.ImportSingeTask("D:\\2017bumen.jio");
            Date a2 = new Date();
            return "test" + a1.toString() + "_" + a2.toString();
        }
        if (name.equals("jio3")) {
            Date a1 = new Date();
            this.paraImportController.ImportSingeTask("D:\\2017bumen3.jio");
            Date a2 = new Date();
            return "test" + a1.toString() + "_" + a2.toString();
        }
        if (name.equals("jio")) {
            SingleFile file = this.paraImportController.GetSingleFile("D:\\test.jio");
            file.infoLoad("D:\\test.jio");
            file.writeTaskSign("D:\\");
            return "test";
        }
        if (name.equals("dbf")) {
            IDbfTable dbf2 = DbfTableUtil.getDbfTable((String)"D:\\YSCS12_F7.DBF", (String)"GB2312", (boolean)false);
            dbf2.setFileName("D:\\YSCS12_F7_temp.DBF");
            dbf2.saveData();
            return "test:f7:" + dbf2.getRecordCount();
        }
        if (name.equals("dbfm")) {
            DbfTable dbf = new DbfTable("D:\\YSFMDM.DBF", "GB2312", false);
            dbf.setFileName("D:\\YSFMDM_temp.DBF");
            dbf.saveData();
            return "test:fmdm:" + dbf.getRecordCount();
        }
        if (name.equals("dbfr")) {
            DbfTable dbf = new DbfTable("D:\\YSFMDM.DBF", "GB2312", false);
            return "test:fmdm:" + dbf.getRecordCount();
        }
        if (name.equals("unzip")) {
            try (FileInputStream inStream = new FileInputStream("D:\\111.zip");){
                ZipHelper.unzipFile((String)"D:\\temp\\jio\\unzip\\test.TSK\\", (InputStream)inStream);
            }
        }
        if (name.equals("readjio")) {
            JIOParamParser jio = new JIOParamParser("D:\\test3.jio.TSK\\", false);
            jio.parse();
        }
        if (name.equals("test")) {
            SingleFile file = this.paraImportController.GetSingleFile("D:\\test3.jio");
            this.paraImportController.ImportSingeTask("D:\\test3.jio");
            return "test" + file.getInfo().getCombineText();
        }
        return "hello world " + name;
    }

    private void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(SinglePathUtil.normalize((String)filePath));
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(SinglePathUtil.normalize((String)(filePath + fileName)));){
            out.write(file);
            out.flush();
        }
    }

    private String uploadJioParaFile(MultipartFile file, HttpServletRequest request, int paraType) throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        JSONObject jsonObject = new JSONObject();
        String taskId = request.getParameter("taskId");
        String schemeId = request.getParameter("schemeId");
        String isImportPrint = request.getParameter("isImportPrint");
        String syncTaskIDCode = request.getParameter("syncTaskID");
        String cHisMatchByFiledCode = request.getParameter("matchFieldCode");
        String chistoryFormSchemes = request.getParameter("historyFormSchemes");
        String chistoryUpdateEnumRef = request.getParameter("isHistoryUpdateEnumRef");
        String filePrefix = request.getParameter("filePrefix");
        boolean HistoryMatchCode = false;
        if (StringUtils.isNotEmpty((String)cHisMatchByFiledCode)) {
            HistoryMatchCode = Boolean.parseBoolean(cHisMatchByFiledCode);
        }
        boolean isUploadPrint = Boolean.parseBoolean(isImportPrint);
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = file.getOriginalFilename();
        String filePath = System.getProperty("java.io.tmpdir");
        filePath = PathUtil.createNewPath((String)filePath, (String)".nr");
        filePath = PathUtil.createNewPath((String)filePath, (String)"AppData");
        filePath = PathUtil.createNewPath((String)filePath, (String)"uploadPara");
        filePath = PathUtil.createNewPath((String)filePath, (String)sfDate.format(new Date()));
        logger.info("JIO\u6587\u4ef6\u540d(\u542b\u5b8c\u6574\u8def\u5f84)-->" + filePath + fileName);
        logger.info("\u4efb\u52a1Key-->" + taskId);
        try {
            this.uploadFile(file.getBytes(), filePath, fileName);
        }
        catch (Exception e) {
            logger.info("JIO\u6587\u4ef6\u4e0a\u4f20\u51fa\u9519");
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_001, e.toString());
        }
        String message = "\u5bfc\u5165\u6210\u529f\uff01";
        boolean result = true;
        try {
            boolean isHistoryUpdateEnumRef = false;
            if (StringUtils.isNotEmpty((String)chistoryUpdateEnumRef)) {
                isHistoryUpdateEnumRef = Boolean.parseBoolean(chistoryUpdateEnumRef);
            }
            SingleParaImportOption option = new SingleParaImportOption();
            if (paraType == 0) {
                if (isUploadPrint) {
                    option.NotSelectAll();
                    option.setUploadPrint(isUploadPrint);
                } else {
                    option.SelectAll();
                }
                option.setAnalPara(true);
                option.setHistoryPara(false);
            } else if (paraType == 1) {
                option.NotSelectAll();
                option.setAnalPara(true);
                option.setHistoryPara(false);
            } else if (paraType == 2) {
                if (isUploadPrint) {
                    option.NotSelectAll();
                    option.setUploadPrint(isUploadPrint);
                } else {
                    option.SelectAll();
                }
                option.setAnalPara(false);
                option.setHistoryPara(true);
                if (HistoryMatchCode) {
                    option.setHistoryMatchType(1);
                }
            }
            option.setFilePrefix(filePrefix);
            option.setAnalPara(paraType <= 1);
            option.setHistoryPara(paraType == 2);
            option.setHistoryFormSchemes(chistoryFormSchemes);
            option.setHistoryUpdateEnumRef(isHistoryUpdateEnumRef);
            if (StringUtils.isEmpty((String)syncTaskIDCode)) {
                option.setSyncTaskID(UUID.randomUUID().toString());
            } else {
                option.setSyncTaskID(syncTaskIDCode);
            }
            this.paraImportController.ImportSingleToFormScheme(taskId, schemeId, filePath + fileName, option);
            PathUtil.deleteFile((String)(filePath + fileName));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_002, e.toString());
        }
        logger.info("\u5bfc\u5165\u603b\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
        jsonObject.put("success", result);
        jsonObject.put("message", (Object)message);
        return jsonObject.toString();
    }

    @ApiOperation(value="\u5efa\u6a21\u8bbe\u8ba1\u5bfc\u5165JIO\u6587\u4ef6")
    @RequestMapping(value={"/JioSercice/UploadFile"}, method={RequestMethod.POST})
    public String uploadImg(@RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws Exception {
        return this.uploadJioParaFile(file, request, 0);
    }

    @ApiOperation(value="\u5efa\u6a21\u8bbe\u8ba1\u5bfc\u5165JIO\u5386\u53f2\u53c2\u6570\u6587\u4ef6")
    @RequestMapping(value={"/JioSercice/UploadHistoryParaFile"}, method={RequestMethod.POST})
    public String uploadHistoryImg(@RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws Exception {
        return this.uploadJioParaFile(file, request, 2);
    }

    @ApiOperation(value="\u5efa\u6a21\u8bbe\u8ba1\u5bfc\u5165JIO\u5206\u6790\u53c2\u6570\u6587\u4ef6")
    @RequestMapping(value={"/JioSercice/UploadAnalParaFile"}, method={RequestMethod.POST})
    public String uploadJioAnalImg(@RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws Exception {
        return this.uploadJioParaFile(file, request, 1);
    }

    @RequestMapping(value={"/JioSercice/DownloadFile"}, method={RequestMethod.POST})
    public void downloadImg(HttpServletResponse response, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String taskId = request.getParameter("taskId");
        String fileName = "temp.jio";
        String filePath = System.getProperty("java.io.tmpdir");
        String temp = filePath.substring(filePath.length() - 1, filePath.length());
        if (!temp.equals(File.separator)) {
            filePath = filePath + File.separator;
        }
        logger.info("JIO\u6587\u4ef6\u540d(\u542b\u5b8c\u6574\u8def\u5f84)-->" + filePath + fileName);
        logger.info("\u4efb\u52a1Key-->" + taskId);
        String message = "\u5bfc\u51fa\u6210\u529f\uff01";
        boolean result = true;
        try {
            jsonObject.put("success", result);
            jsonObject.put("message", (Object)message);
            String resultFileName = new String(fileName.getBytes(), "iso8859-1");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + resultFileName);
            ServletOutputStream outputStream = response.getOutputStream();
            MemStream stream = new MemStream();
            stream.loadFromFile(filePath + fileName);
            outputStream.write(stream.getBytes());
            response.flushBuffer();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            message = e.toString();
            result = false;
            jsonObject.put("success", result);
        }
    }
}

