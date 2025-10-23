/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.transmission.data.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import com.jiuqi.nr.transmission.data.exception.SchemeExportException;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.intf.ImportParam;
import com.jiuqi.nr.transmission.data.intf.ImportParamVO;
import com.jiuqi.nr.transmission.data.intf.ImportReturnParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionResult;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor;
import com.jiuqi.nr.transmission.data.service.IExecuteSyncIOService;
import com.jiuqi.nr.transmission.data.vo.SyncHistoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"api/v1/sync/scheme/sync/"})
@Api(tags={"\u591a\u7ea7\u90e8\u7f72\uff0c\u540c\u6b65\u670d\u52a1"})
public class TransmissionIOController {
    private static final Logger logger = LoggerFactory.getLogger(TransmissionIOController.class);
    @Autowired
    private IExecuteSyncIOService executeSyncIOService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;

    @ApiOperation(value="\u63a5\u6536\u6570\u636e\u5305\u5e76\u5224\u65ad\u662f\u5426\u5f00\u542f\u6d41\u7a0b")
    @PostMapping(value={"flow_type"})
    public ResultObj<ImportReturnParam> beforeImportFile(@RequestParam(value="file") MultipartFile file) throws JQException {
        ResultObj<ImportReturnParam> resultObj;
        try (InputStream inputStream = file.getInputStream();){
            ImportReturnParam importReturnParam = this.executeSyncIOService.checkFlowType(inputStream);
            resultObj = new ResultObj<ImportReturnParam>(true, importReturnParam);
        }
        catch (Exception e) {
            resultObj = new ResultObj<ImportReturnParam>(false, e.getMessage());
        }
        return resultObj;
    }

    @ApiOperation(value="\u5224\u65ad\u5b8c\u5f00\u542f\u6d41\u7a0b\u540e\u6267\u884c\u88c5\u5165")
    @PostMapping(value={"do_import"})
    public List<DataImportResult> doImportFile(@RequestBody ImportParamVO importParamVO) throws Exception {
        ArrayList<String> fileKeyLists = new ArrayList<String>(importParamVO.getFileKeyLists());
        ArrayList<String> allfileKeyList = new ArrayList<String>(importParamVO.getAllFileKeyList());
        double processSize = 1.0 / (double)fileKeyLists.size();
        String executeKey = importParamVO.getExecuteKey();
        TransmissionMonitor parentMonitor = new TransmissionMonitor(executeKey, this.cacheObjectResourceRemote);
        ArrayList<DataImportResult> transmissionDataImportResult = new ArrayList<DataImportResult>();
        for (String fileKey : fileKeyLists) {
            importParamVO.setFileKey(fileKey);
            importParamVO.setExecuteKey(fileKey);
            try {
                ImportParam importParam = ImportParamVO.importParamVoTo(importParamVO);
                TransmissionResult result = this.executeSyncIOService.executeOffLineImport(importParam, parentMonitor, processSize);
                if (!(result.getData() instanceof DataImportResult)) continue;
                transmissionDataImportResult.add((DataImportResult)result.getData());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        this.executeSyncIOService.deleteFileServer(allfileKeyList);
        parentMonitor.finish("\u540c\u6b65\u6210\u529f", "\u540c\u6b65\u5b8c\u6210");
        return transmissionDataImportResult;
    }

    @ApiOperation(value="\u5bfc\u51fa\u53c2\u6570\u5305")
    @PostMapping(value={"export"})
    public void exportByHistory(@RequestBody SyncHistoryVO syncHistoryVO, HttpServletResponse res) throws JQException {
        try {
            SyncHistoryDTO syncHistoryDTO = SyncHistoryVO.syncHistoryVoToDto(syncHistoryVO);
            this.executeSyncIOService.exportByHistory(syncHistoryDTO, res);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SchemeExportException.EXPORT_NULL_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u4ece\u6587\u4ef6\u670d\u52a1\u5668\u5220\u9664\u6587\u4ef6")
    @PostMapping(value={"delete_file"})
    public List<String> deleteFileFormServer(@RequestBody List<String> fileKeys) {
        return this.executeSyncIOService.deleteFileServer(fileKeys);
    }

    class ResultObj<T> {
        private boolean success;
        private T data;
        private String message;

        public ResultObj() {
        }

        public ResultObj(boolean success, T data) {
            this.success = success;
            this.data = data;
        }

        public ResultObj(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return this.success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public T getData() {
            return this.data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

