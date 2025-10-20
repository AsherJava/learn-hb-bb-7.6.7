/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.EasyExcel
 *  com.alibaba.excel.support.ExcelTypeEnum
 *  com.alibaba.excel.write.builder.ExcelWriterBuilder
 *  com.alibaba.excel.write.builder.ExcelWriterSheetBuilder
 *  com.alibaba.excel.write.handler.WriteHandler
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.intf.impl.SimpleHorizontalCellStyleStrategy
 *  com.jiuqi.dc.base.common.utils.CollectionUtil
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.taskscheduling.core.util.TaskHandlerManager
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DimType
 *  com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO
 *  com.jiuqi.dc.taskscheduling.logquery.client.dto.LogManagerDTO
 *  com.jiuqi.dc.taskscheduling.logquery.client.vo.ExecuteStateVO
 *  com.jiuqi.dc.taskscheduling.logquery.client.vo.LogManagerVO
 *  com.jiuqi.va.domain.common.PageVO
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.collections4.MapUtils
 */
package com.jiuqi.dc.taskscheduling.logquery.impl.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.intf.impl.SimpleHorizontalCellStyleStrategy;
import com.jiuqi.dc.base.common.utils.CollectionUtil;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.taskscheduling.core.util.TaskHandlerManager;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DimType;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import com.jiuqi.dc.taskscheduling.logquery.client.dto.LogManagerDTO;
import com.jiuqi.dc.taskscheduling.logquery.client.vo.ExecuteStateVO;
import com.jiuqi.dc.taskscheduling.logquery.client.vo.LogManagerVO;
import com.jiuqi.dc.taskscheduling.logquery.impl.dao.TaskLogQueryDao;
import com.jiuqi.dc.taskscheduling.logquery.impl.exp.AutoMatchColumnWidthStyleStrategy;
import com.jiuqi.dc.taskscheduling.logquery.impl.exp.ExecuteErrorRecordColumn;
import com.jiuqi.dc.taskscheduling.logquery.impl.exp.ExecuteRecordColumn;
import com.jiuqi.dc.taskscheduling.logquery.impl.exp.SqlRecordColumn;
import com.jiuqi.dc.taskscheduling.logquery.impl.service.LogManagerService;
import com.jiuqi.va.domain.common.PageVO;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogManagerServiceImpl
implements LogManagerService {
    @Autowired
    private TaskLogQueryDao taskLogDao;

    @Override
    public PageVO<LogManagerVO> getOverViewList(LogManagerDTO dto) {
        PageVO pageVO = new PageVO();
        Integer total = this.taskLogDao.listOverTotal(dto);
        if (total == 0) {
            pageVO.setRows(Collections.emptyList());
            return pageVO;
        }
        List<LogManagerVO> logList = this.taskLogDao.listOverView(dto);
        for (LogManagerVO logManagerVO : logList) {
            logManagerVO.setTaskType(TaskHandlerManager.getTaskNameByCode((String)logManagerVO.getTaskType()));
        }
        pageVO.setRows(logList);
        pageVO.setTotal(total.intValue());
        return pageVO;
    }

    @Override
    public PageVO<LogManagerDetailVO> getDetailList(LogManagerDTO dto) {
        PageVO pageVO = new PageVO();
        Integer total = this.taskLogDao.listDetailTotal(dto);
        if (total == 0) {
            pageVO.setRows(Collections.emptyList());
            return pageVO;
        }
        List<LogManagerDetailVO> logDetailList = this.taskLogDao.listDetail(dto);
        for (LogManagerDetailVO logManagerDetailVO : logDetailList) {
            String taskType = logManagerDetailVO.getTaskType();
            logManagerDetailVO.setTaskType(TaskHandlerManager.getTaskNameByCode((String)taskType));
            logManagerDetailVO.setDimType(TaskHandlerManager.getDimTypeTitleByName((String)taskType));
        }
        pageVO.setTotal(total.intValue());
        pageVO.setRows(logDetailList);
        return pageVO;
    }

    @Override
    public LogManagerVO getOverViewProgress(String id) {
        LogManagerVO logManagerVO = this.taskLogDao.listOverRefresh(id);
        ExecuteStateVO stateVO = this.taskLogDao.listOverExecute(id);
        logManagerVO.setTotal(stateVO.getTotal());
        logManagerVO.setFailed(stateVO.getFailed());
        logManagerVO.setSuccess(stateVO.getSuccess());
        logManagerVO.setExecuting(stateVO.getExecuting());
        logManagerVO.setUnExecute(stateVO.getUnExecute());
        logManagerVO.setCanceled(stateVO.getCanceled());
        logManagerVO.setHasCancel(stateVO.getHasCancel());
        return logManagerVO;
    }

    @Override
    public LogManagerDetailVO getDetailProgress(String id) {
        return this.taskLogDao.getHandleStateByItemId(id);
    }

    @Override
    public LogManagerDetailVO getResultLog(String id) {
        LogManagerDetailVO detail = this.taskLogDao.getResultLog(id);
        detail.setTaskType(TaskHandlerManager.getTaskNameByCode((String)detail.getTaskType()));
        return detail;
    }

    @Override
    public List<SelectOptionVO> getOverTaskType() {
        ArrayList<SelectOptionVO> optionVOS = new ArrayList<SelectOptionVO>();
        List<String> taskTypes = this.taskLogDao.getOverTaskType();
        for (String taskType : taskTypes) {
            optionVOS.add(new SelectOptionVO(taskType, TaskHandlerManager.getTaskNameByCode((String)taskType)));
        }
        Collator titleCompartor = Collator.getInstance(Locale.CHINA);
        return optionVOS.stream().sorted((o1, o2) -> titleCompartor.compare(o1.getName(), o2.getName())).collect(Collectors.toList());
    }

    @Override
    public List<SelectOptionVO> getDetailTaskType(LogManagerDTO dto) {
        ArrayList<SelectOptionVO> optionVOS = new ArrayList<SelectOptionVO>();
        List<String> taskTypes = this.taskLogDao.getDetailTaskType(dto);
        for (String taskType : taskTypes) {
            optionVOS.add(new SelectOptionVO(taskType, TaskHandlerManager.getTaskNameByCode((String)taskType)));
        }
        Collator titleCompartor = Collator.getInstance(Locale.CHINA);
        return optionVOS.stream().sorted((o1, o2) -> titleCompartor.compare(o1.getName(), o2.getName())).collect(Collectors.toList());
    }

    @Override
    public List<SelectOptionVO> getDetailDimType() {
        ArrayList<SelectOptionVO> optionVOS = new ArrayList<SelectOptionVO>();
        for (DimType dimType : DimType.values()) {
            optionVOS.add(new SelectOptionVO(dimType.toString(), dimType.getTitle()));
        }
        return optionVOS;
    }

    @Override
    public void exportExecuteRecordByCondition(HttpServletResponse response, LogManagerDTO dto) {
        String taskId = dto.getRunnerId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = "";
        try {
            fileName = URLEncoder.encode(String.format("\u6570\u636e\u9884\u5904\u7406\u4efb\u52a1\u6267\u884c\u8bb0\u5f55%1$s", sdf.format(new Date())), StandardCharsets.UTF_8.name());
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            ArrayList<List> headerColumns = new ArrayList<List>();
            ArrayList exportDataList = CollectionUtils.newArrayList();
            if (!dto.getErrorLog().booleanValue()) {
                for (ExecuteRecordColumn column : ExecuteRecordColumn.values()) {
                    if (!dto.getContainSql().booleanValue() && column.isSqlColumn().booleanValue()) continue;
                    headerColumns.add(CollectionUtil.newArrayList((Object[])new String[]{column.getTitle()}));
                }
                List<Map<String, Object>> recordList = this.taskLogDao.getExecuteRecordByCondition(dto);
                for (Map<String, Object> record : recordList) {
                    ArrayList exportData = CollectionUtils.newArrayList();
                    for (ExecuteRecordColumn column : ExecuteRecordColumn.values()) {
                        if (!dto.getContainSql().booleanValue() && column.isSqlColumn().booleanValue()) continue;
                        exportData.add(record.get(column.name()));
                    }
                    exportDataList.add(exportData);
                }
            } else {
                for (ExecuteErrorRecordColumn column : ExecuteErrorRecordColumn.values()) {
                    headerColumns.add(CollectionUtil.newArrayList((Object[])new String[]{column.getTitle()}));
                }
                List<Map<String, Object>> recordList = this.taskLogDao.getExecuteErrorRecord(taskId);
                HashMap recordMap = CollectionUtils.newHashMap();
                Iterator iterator = recordList.iterator();
                while (iterator.hasNext()) {
                    Map record = (Map)iterator.next();
                    String errorReason = this.getErrorReason(MapUtils.getString((Map)record, (Object)ExecuteErrorRecordColumn.RESULTLOG.name()));
                    String key = MapUtils.getString((Map)record, (Object)ExecuteErrorRecordColumn.TASKTYPE.name()) + "-" + errorReason;
                    List exportData = (List)recordMap.get(key);
                    if (Objects.isNull(exportData)) {
                        exportData = CollectionUtils.newArrayList();
                        recordMap.put(key, exportData);
                        exportData.add(MapUtils.getString((Map)record, (Object)ExecuteErrorRecordColumn.TASKTYPE.name()));
                        exportData.add(MapUtils.getString((Map)record, (Object)ExecuteErrorRecordColumn.DIMCODE.name()));
                        exportData.add(record.get(ExecuteErrorRecordColumn.STARTTIME.name()));
                        exportData.add(record.get(ExecuteErrorRecordColumn.ENDTIME.name()));
                        exportData.add(record.get(ExecuteErrorRecordColumn.DURATION.name()));
                        exportData.add(record.get(ExecuteErrorRecordColumn.RESULTLOG.name()));
                        continue;
                    }
                    ArrayList exportNewData = CollectionUtils.newArrayList();
                    recordMap.put(key, exportNewData);
                    exportNewData.add(exportData.get(0));
                    exportNewData.add(exportData.get(1) + "," + MapUtils.getString((Map)record, (Object)ExecuteErrorRecordColumn.DIMCODE.name()));
                    Date startTime = (Date)record.get(ExecuteErrorRecordColumn.STARTTIME.name());
                    Date endTime = (Date)record.get(ExecuteErrorRecordColumn.ENDTIME.name());
                    Date minStartTime = DateUtils.compare((Date)startTime, (Date)((Date)exportData.get(2)), (boolean)Boolean.TRUE) < 0 ? startTime : (Date)exportData.get(2);
                    Date maxEndTime = DateUtils.compare((Date)endTime, (Date)((Date)exportData.get(3)), (boolean)Boolean.TRUE) > 0 ? endTime : (Date)exportData.get(3);
                    exportNewData.add(minStartTime);
                    exportNewData.add(maxEndTime);
                    exportNewData.add((maxEndTime.getTime() - minStartTime.getTime()) / 1000L);
                    exportNewData.add(exportData.get(5));
                }
                exportDataList.addAll(recordMap.values());
            }
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8.name()), StandardCharsets.ISO_8859_1.name()) + ExcelTypeEnum.XLS.getValue());
            ((ExcelWriterSheetBuilder)((ExcelWriterBuilder)((ExcelWriterBuilder)EasyExcel.write((OutputStream)response.getOutputStream()).registerWriteHandler((WriteHandler)new SimpleHorizontalCellStyleStrategy())).registerWriteHandler((WriteHandler)new AutoMatchColumnWidthStyleStrategy(100))).sheet("\u6267\u884c\u8bb0\u5f55").head(headerColumns)).doWrite((Collection)exportDataList);
        }
        catch (Exception e) {
            throw new RuntimeException("\u5bfc\u51fa\u5931\u8d25", e);
        }
    }

    private String getErrorReason(String resultLog) {
        String pattern = ".*Exception\\(.*\\.java:[0-9]*\\)";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(resultLog);
        if (matcher.find()) {
            return matcher.group();
        }
        if (resultLog.contains("\u5f53\u524d\u5355\u4f4d\u672a\u914d\u7f6e\u4e0a\u7ebf\u671f\u95f4")) {
            return UUIDUtils.emptyUUIDStr();
        }
        return resultLog;
    }

    @Override
    public void exportSqlRecordByTaskItemId(HttpServletResponse response, String taskItemId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = "";
        try {
            fileName = URLEncoder.encode(String.format("%1$sSQL\u6267\u884c\u8bb0\u5f55_%2$s", sdf.format(new Date())), StandardCharsets.UTF_8.name());
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            ArrayList<List> headerColumns = new ArrayList<List>();
            for (SqlRecordColumn column : SqlRecordColumn.values()) {
                headerColumns.add(CollectionUtil.newArrayList((Object[])new String[]{column.getTitle()}));
            }
            List<Map<String, Object>> recordList = this.taskLogDao.getSqlRecord(taskItemId);
            ArrayList exportDataList = CollectionUtils.newArrayList();
            for (Map<String, Object> record : recordList) {
                ArrayList exportData = CollectionUtils.newArrayList();
                for (SqlRecordColumn column : SqlRecordColumn.values()) {
                    exportData.add(record.get(column.name()));
                }
                exportDataList.add(exportData);
            }
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8.name()), StandardCharsets.ISO_8859_1.name()) + ExcelTypeEnum.XLS.getValue());
            ((ExcelWriterSheetBuilder)((ExcelWriterBuilder)((ExcelWriterBuilder)EasyExcel.write((OutputStream)response.getOutputStream()).registerWriteHandler((WriteHandler)new SimpleHorizontalCellStyleStrategy())).registerWriteHandler((WriteHandler)new AutoMatchColumnWidthStyleStrategy(100))).sheet("\u6267\u884c\u8bb0\u5f55").head(headerColumns)).doWrite((Collection)exportDataList);
        }
        catch (Exception e) {
            throw new RuntimeException("\u5bfc\u51fa\u5931\u8d25", e);
        }
    }
}

