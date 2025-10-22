/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode
 *  com.jiuqi.nr.datascheme.api.exception.NotFindSchemeDataException
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO
 *  com.jiuqi.nr.datascheme.web.facade.BusinessError
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.system.check.datachange.controller;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode;
import com.jiuqi.nr.datascheme.api.exception.NotFindSchemeDataException;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.facade.BusinessError;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.system.check.common.Util;
import com.jiuqi.nr.system.check.datachange.asyntask.DoUnitCodeUpperCase;
import com.jiuqi.nr.system.check.datachange.asyntask.GetUnitMissInfo;
import com.jiuqi.nr.system.check.datachange.bean.DataChangeRecord;
import com.jiuqi.nr.system.check.datachange.bean.DataSchemeRepairRecord;
import com.jiuqi.nr.system.check.datachange.bean.RepairParam;
import com.jiuqi.nr.system.check.datachange.bean.UnitInfo;
import com.jiuqi.nr.system.check.datachange.dao.impl.UnitInfoDao;
import com.jiuqi.nr.system.check.datachange.service.DataSchemeRepairRecordService;
import com.jiuqi.nr.system.check.datachange.service.IDataChangeRecordService;
import com.jiuqi.nr.system.check.datachange.service.UnitMissInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/system-check/data-change"})
@Api(tags={"\u7cfb\u7edf\u68c0\u67e5"})
public class DataChangeController {
    private static final Logger logger = LoggerFactory.getLogger(DataChangeController.class);
    @Autowired
    private IDataChangeRecordService recordService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private DataSchemeRepairRecordService dataSchemeRepairRecordService;
    @Autowired
    private UnitMissInfoService unitMissInfoService;
    @Autowired
    private UnitInfoDao unitInfoDao;
    @Autowired
    private IDataSchemeTreeService<RuntimeDataSchemeNode> runTimeTreeService;

    @PostMapping(value={"/douppercase"})
    @ApiOperation(value="\u6570\u636e\u4e2d\u5355\u4f4d\u4ee3\u7801\u503c\u5c0f\u5199\u8f6c\u5927\u5199")
    public AsyncTaskInfo doUpperCase(@RequestBody RepairParam param) throws JQException {
        if (param == null) {
            throw Util.getError("15826", "\u83b7\u53d6\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199\u8bb0\u5f55\u5931\u8d25\uff0c\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        DataSchemeRepairRecord lastRepairRecord = this.dataSchemeRepairRecordService.getLastRepairRecord("UNIT_CODE_UPPER");
        if (lastRepairRecord != null && lastRepairRecord.getEndTime() == null) {
            throw Util.getError("15827", "\u5f53\u524d\u6709\u5176\u4ed6\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199\u7684\u4fee\u590d\u4efb\u52a1\u6b63\u5728\u6267\u884c\uff01");
        }
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)param));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new DoUnitCodeUpperCase());
        String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @GetMapping(value={"/getrepairinfo/{dataSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u8868\u4e2d\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199\u8bb0\u5f55")
    public DataSchemeRepairRecord getRepairInfo(@PathVariable String dataSchemeKey) throws JQException {
        try {
            return this.dataSchemeRepairRecordService.getDataSchemeRepairRecord(dataSchemeKey, "UNIT_CODE_UPPER");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw Util.getError("15828", String.format("\u83b7\u53d6\u6570\u636e\u65b9\u6848\u4fee\u590d\u8bb0\u5f55\u5931\u8d25\uff0c{%s}", e.getMessage()));
        }
    }

    @GetMapping(value={"/getexistinfo/{dataSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u8868\u4e2d\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199\u8bb0\u5f55")
    public DataSchemeRepairRecord getExistInfo(@PathVariable String dataSchemeKey) throws JQException {
        try {
            List<UnitInfo> missUnitInfo;
            DataSchemeRepairRecord dataSchemeRepairRecord = this.dataSchemeRepairRecordService.getDataSchemeRepairRecord(dataSchemeKey, "UNIT_EXIST");
            if (dataSchemeRepairRecord != null && !CollectionUtils.isEmpty(missUnitInfo = this.unitInfoDao.findByOrg(dataSchemeKey))) {
                dataSchemeRepairRecord.setHasDataExport(true);
            }
            return dataSchemeRepairRecord;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw Util.getError("15829", String.format("\u83b7\u53d6\u5355\u4f4d\u4ee3\u7801\u5b58\u5728\u4e0e\u5426\u7684\u6267\u884c\u8bb0\u5f55\u5931\u8d25\uff0c{%s}", e.getMessage()));
        }
    }

    @PostMapping(value={"/getchangerecords"})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u8868\u4e2d\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199\u8bb0\u5f55")
    public List<DataChangeRecord> getChangeRecords() throws JQException {
        List<DataChangeRecord> records;
        try {
            records = this.recordService.getRecordsByRecordType("dataTable");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw Util.getError("15830", String.format("\u83b7\u53d6\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199\u8bb0\u5f55\u5931\u8d25\uff0c{%s}", e.getMessage()));
        }
        return records;
    }

    @PostMapping(value={"/getunitmissinfo/{dataSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u8868\u4e2d\u5355\u4f4d\u4ee3\u7801\u4e0d\u5728\u5bf9\u5e94\u7ec4\u7ec7\u673a\u6784\u6811\u5f62\u4e0a\u7684\u5355\u4f4d\u4fe1\u606f")
    public AsyncTaskInfo getUnitMissInfo(@PathVariable String dataSchemeKey) throws JQException {
        if (StringUtils.isEmpty((String)dataSchemeKey)) {
            throw Util.getError("15831", "\u83b7\u53d6\u5355\u4f4d\u4ee3\u7801\u8f6c\u5927\u5199\u8bb0\u5f55\u5931\u8d25\uff0c\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        DataSchemeRepairRecord lastRepairRecord = this.dataSchemeRepairRecordService.getLastRepairRecord("UNIT_EXIST");
        if (lastRepairRecord != null && lastRepairRecord.getEndTime() == null) {
            throw Util.getError("15832", "\u5f53\u524d\u6709\u5176\u4ed6\u5224\u65ad\u5355\u4f4d\u4ee3\u7801\u5b58\u5728\u7684\u4efb\u52a1\u6b63\u5728\u6267\u884c\uff01");
        }
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(dataSchemeKey);
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new GetUnitMissInfo());
        String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ApiOperation(value="\u5bfc\u51fa\u8be5\u6570\u636e\u65b9\u6848\u7684\u6570\u636e\u8868\u4e2d\u4e0d\u5b58\u5728\u7684\u5355\u4f4d\u4fe1\u606f")
    @PostMapping(value={"/export/{dataSchemeKey}"})
    public void exportTable(HttpServletResponse response, @PathVariable String dataSchemeKey) throws IOException {
        ServletOutputStream outputStream = null;
        try (XSSFWorkbook wb = new XSSFWorkbook();){
            Sheet sheet = wb.createSheet();
            String fileName = this.unitMissInfoService.export(dataSchemeKey, sheet);
            outputStream = response.getOutputStream();
            DataChangeController.extracted(response, fileName);
            wb.write((OutputStream)outputStream);
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    protected static void extracted(HttpServletResponse response, String fileName) throws IOException {
        try {
            fileName = URLEncoder.encode(fileName, "utf-8").replace("\\+", "%20");
            fileName = "attachment;filename=" + fileName + "." + XSSFWorkbookType.XLSX.getExtension();
            HtmlUtils.validateHeaderValue((String)fileName);
            response.setHeader("Content-disposition", fileName);
            response.setContentType("application/octet-stream");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        }
        catch (Exception e) {
            throw new IOException(e);
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u8fd0\u884c\u671f\u6570\u636e\u65b9\u6848\u6811\u5f62\u6839\u8282\u70b9")
    @PostMapping(value={"r-tree/root"})
    public List<ITree<RuntimeDataSchemeNode>> queryResourceTreeRoot() throws JQException {
        logger.debug("\u83b7\u53d6\u8fd0\u884c\u671f\u6811\u5f62\u6839\u8282\u70b9");
        try {
            return this.runTimeTreeService.getSchemeGroupRootTree(NodeType.SCHEME_GROUP.getValue() + NodeType.SCHEME.getValue(), null);
        }
        catch (NotFindSchemeDataException | SchemeDataException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)new BusinessError((RuntimeException)e));
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u8fd0\u884c\u671f\u6570\u636e\u65b9\u6848\u67d0\u4e2aKEY\u4e0b\u6240\u6709\u76f4\u63a5\u5b50\u8282\u70b9")
    @PostMapping(value={"r-tree/children"})
    public List<ITree<RuntimeDataSchemeNode>> queryResourceTreeChildren(@RequestBody DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param) throws JQException {
        RuntimeDataSchemeNodeDTO parent = (RuntimeDataSchemeNodeDTO)param.getDataSchemeNode();
        Assert.notNull((Object)parent, "parent must not be null.");
        logger.debug("\u83b7\u53d6\u6811\u5f62\u4e0b\u7ea7\u8282\u70b9 \u8282\u70b9 {}", (Object)parent);
        try {
            return this.runTimeTreeService.getSchemeGroupChildTree((INode)parent, NodeType.SCHEME_GROUP.getValue() + NodeType.SCHEME.getValue(), null);
        }
        catch (NotFindSchemeDataException | SchemeDataException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)new BusinessError((RuntimeException)e));
        }
    }

    @ApiOperation(value="\u8fd0\u884c\u671f\u6570\u636e\u65b9\u6848\u5b9a\u4f4d,\u6570\u636e\u65b9\u6848\u5206\u7ec4\u6811\u5f62")
    @PostMapping(value={"r-tree/path"})
    public List<ITree<RuntimeDataSchemeNode>> queryResourceTreePath(@RequestBody DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param) throws JQException {
        RuntimeDataSchemeNodeDTO node = (RuntimeDataSchemeNodeDTO)param.getDataSchemeNode();
        if (node == null || "00000000-0000-0000-0000-000000000000".equals(node.getKey())) {
            return this.queryResourceTreeRoot();
        }
        try {
            return this.runTimeTreeService.getSchemeGroupSpecifiedTree((INode)node, NodeType.SCHEME_GROUP.getValue() + NodeType.SCHEME.getValue(), null);
        }
        catch (NotFindSchemeDataException | SchemeDataException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)new BusinessError((RuntimeException)e));
        }
    }
}

