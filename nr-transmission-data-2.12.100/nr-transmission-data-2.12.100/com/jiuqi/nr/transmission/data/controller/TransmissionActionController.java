/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
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
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.exception.SchemeExportException;
import com.jiuqi.nr.transmission.data.exception.SchemeImportException;
import com.jiuqi.nr.transmission.data.intf.DeploySchemeType;
import com.jiuqi.nr.transmission.data.intf.SyncFileParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionResult;
import com.jiuqi.nr.transmission.data.service.IExecuteSyncService;
import com.jiuqi.nr.transmission.data.service.IReportParamService;
import com.jiuqi.nr.transmission.data.vo.ImportOtherVO;
import com.jiuqi.nr.transmission.data.vo.SyncSchemeParamVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"api/v1/sync/scheme/sync/"})
@Api(tags={"\u591a\u7ea7\u90e8\u7f72\uff0c\u540c\u6b65\u670d\u52a1"})
public class TransmissionActionController {
    private static final Logger logger = LoggerFactory.getLogger(TransmissionActionController.class);
    @Autowired
    private IExecuteSyncService executeSyncService;
    @Autowired
    private IReportParamService reportParamService;

    @ApiOperation(value="\u6267\u884c\u540c\u6b65")
    @PostMapping(value={"execute"})
    public TransmissionResult executeSync(@RequestBody SyncSchemeParamVO syncSchemeParam) throws JQException {
        try {
            SyncSchemeParamDTO syncSchemeParamDTO = SyncSchemeParamVO.syncSchemeParamVoToDto(syncSchemeParam);
            return this.executeSyncService.executeSync(syncSchemeParamDTO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SchemeExportException.EXPORT_ERROR, e.getMessage());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @ApiOperation(value="\u63a5\u6536\u6570\u636e\u5305\u5e76\u5f02\u6b65\u4efb\u52a1\u88c5\u5165")
    @PostMapping(value={"accept"})
    public TransmissionResult acceptFile(@RequestParam(value="file") MultipartFile file, @RequestParam(value="syncFileParam", required=false) String syncFileParams) throws JQException {
        SyncFileParam syncFileParam = new SyncFileParam();
        try (InputStream inputStream = file.getInputStream();){
            syncFileParam = (SyncFileParam)JacksonUtils.jsonToObject((String)syncFileParams, SyncFileParam.class);
            TransmissionResult throwable3 = this.executeSyncService.onlineImport(inputStream, syncFileParam);
            return throwable3;
        }
        catch (Exception e) {
            TransmissionResult transmissionResult2 = new TransmissionResult();
            logger.error(e.getMessage(), e);
            transmissionResult2.setSuccess(false);
            transmissionResult2.setMessage(e.getMessage());
            try {
                String s = this.reportParamService.doLogHelperMessage(syncFileParam.getSyncSchemeParamDTO());
                LogHelper.info((String)"\u591a\u7ea7\u90e8\u7f72", (String)"\u4e0a\u7ea7\u540c\u6b65\u524d\u4fe1\u606f", (String)(s + "\u3002\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()), (int)0);
                return transmissionResult2;
            }
            catch (Exception e1) {
                logger.info(e1.getMessage(), e1);
            }
            return transmissionResult2;
        }
    }

    @ApiOperation(value="\u5bfc\u51fa\u6570\u636e")
    @PostMapping(value={"export_data"})
    public void exportData(@RequestBody SyncSchemeParamVO syncSchemeParamVO, HttpServletResponse res) throws JQException {
        try {
            SyncSchemeParamDTO syncSchemeParamDTO = SyncSchemeParamVO.syncSchemeParamVoToDto(syncSchemeParamVO);
            this.executeSyncService.offLineExport(syncSchemeParamDTO, res);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SchemeExportException.EXPORT_ERROR, e.getMessage());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @ApiOperation(value="\u88c5\u5165\u53c2\u6570\u5305")
    @PostMapping(value={"import"})
    public TransmissionResult importFile(@RequestParam(value="file") MultipartFile file, @RequestBody ImportOtherVO importOtherVO) throws JQException {
        try (InputStream inputStream = file.getInputStream();){
            TransmissionResult transmissionResult = this.executeSyncService.offLineImport(inputStream, importOtherVO);
            return transmissionResult;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SchemeImportException.IMPORT_PROCESS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u591a\u7ea7\u90e8\u7f72\u7c7b\u578b")
    @GetMapping(value={"level_deploy_scheme_type"})
    public List<DeploySchemeType> getDeployType() {
        ArrayList<DeploySchemeType> deploySchemeTypes = new ArrayList<DeploySchemeType>();
        deploySchemeTypes.add(new DeploySchemeType("LevelDeployScheme", "\u65b9\u6848\u7ba1\u7406"));
        deploySchemeTypes.add(new DeploySchemeType("LevelDeploySync", "\u540c\u6b65\u7ba1\u7406"));
        return deploySchemeTypes;
    }
}

