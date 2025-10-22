/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckAbleResult
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckColumnVO
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckInitCondition
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckInitVO
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckLevelEnum
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckUpdateMemoVO
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.check.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckAbleResult;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckColumnVO;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckInitCondition;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckInitVO;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckLevelEnum;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckUpdateMemoVO;
import com.jiuqi.gcreport.inputdata.check.dao.InputDataCheckDao;
import com.jiuqi.gcreport.inputdata.check.dao.InputDataCheckGatherDao;
import com.jiuqi.gcreport.inputdata.check.env.impl.InputDataCheckEnvContextImpl;
import com.jiuqi.gcreport.inputdata.check.service.InputDataCheckOffsetService;
import com.jiuqi.gcreport.inputdata.check.service.InputDataCheckService;
import com.jiuqi.gcreport.inputdata.check.utils.InputDataCheckUploadUtil;
import com.jiuqi.gcreport.inputdata.flexible.utils.CorporateConvertUtils;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputWriteNecLimitCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckTabEnum;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckTypeEnum;
import com.jiuqi.gcreport.inputdata.inputdata.enums.ReportOffsetStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.service.InputDataLockService;
import com.jiuqi.gcreport.inputdata.util.I18nTableUtils;
import com.jiuqi.gcreport.inputdata.util.InputDataCheckLevelOptionI18Const;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class InputDataCheckServiceImpl
implements InputDataCheckService {
    private Logger logger = LoggerFactory.getLogger(InputDataCheckServiceImpl.class);
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private InputDataCheckDao inputDataCheckDao;
    @Autowired(required=false)
    private InputDataCheckOffsetService inputDataCheckOffsetService;
    @Autowired
    private InputDataLockService inputDataLockService;
    @Autowired
    private ProgressService<InputDataCheckEnvContextImpl, List<String>> progressService;
    @Autowired
    private InputDataDao inputDataDao;
    @Autowired
    private InputDataCheckGatherDao inputDataCheckGatherDao;
    @Autowired
    private I18nTableUtils i18nTableUtils;

    @Override
    public Pagination<Map<String, Object>> checkTabDatas(InputDataCheckCondition inputDataCheckCondition) {
        if (CollectionUtils.isEmpty((Collection)inputDataCheckCondition.getCheckGatherColumns())) {
            return this.inputDataCheckDao.checkTabDatas(inputDataCheckCondition);
        }
        return this.inputDataCheckGatherDao.getSumCheckTabData(inputDataCheckCondition, InputDataCheckTabEnum.CHECKTAB);
    }

    @Override
    public Pagination<Map<String, Object>> unCheckTabDatas(InputDataCheckCondition inputDataCheckCondition) {
        if (CollectionUtils.isEmpty((Collection)inputDataCheckCondition.getCheckGatherColumns())) {
            return this.inputDataCheckDao.unCheckTabDatas(inputDataCheckCondition);
        }
        return this.inputDataCheckGatherDao.getSumCheckTabData(inputDataCheckCondition, InputDataCheckTabEnum.UNCHECKTAB);
    }

    @Override
    public Pagination<Map<String, Object>> allCheckTabDatas(InputDataCheckCondition inputDataCheckCondition) {
        if (CollectionUtils.isEmpty((Collection)inputDataCheckCondition.getCheckGatherColumns())) {
            return this.inputDataCheckDao.allCheckTabDatas(inputDataCheckCondition);
        }
        return this.inputDataCheckGatherDao.getSumCheckTabData(inputDataCheckCondition, InputDataCheckTabEnum.AllDATA);
    }

    @Override
    public InputDataCheckInitVO initData(InputDataCheckInitCondition inputDataCheckInitCondition) {
        InputDataCheckInitVO inputDataCheckInitVO = new InputDataCheckInitVO();
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(inputDataCheckInitCondition.getTaskId(), inputDataCheckInitCondition.getDataTime());
        if (StringUtils.isEmpty((String)systemId)) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.nonesystemid"));
        }
        String orgCategory = DimensionUtils.getDwEntitieTableByTaskKey((String)inputDataCheckInitCondition.getTaskId());
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputDataCheckInitCondition.getTaskId());
        if (StringUtils.isEmpty((String)tableName)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.taskisnonemsg"));
        }
        List<DesignFieldDefineVO> designFieldDefines = this.inputDataDao.queryUnOffsetColumnSelect(tableName);
        inputDataCheckInitVO.setSystemId(systemId);
        inputDataCheckInitVO.setOrgType(orgCategory);
        inputDataCheckInitVO.setInputDataColumnSelect(this.listInputDataCheckColumns(designFieldDefines));
        ArrayList<SelectOptionVO> selectOptions = new ArrayList<SelectOptionVO>();
        for (InputDataCheckLevelEnum inputDataCheckLevelEnum : InputDataCheckLevelEnum.values()) {
            String I18n = InputDataCheckLevelOptionI18Const.getI18nForCode(inputDataCheckLevelEnum.getCode());
            SelectOptionVO selectOption = new SelectOptionVO((Object)inputDataCheckLevelEnum.getCode(), GcI18nUtil.getMessage((String)I18n));
            selectOptions.add(selectOption);
        }
        List<DesignFieldDefineVO> designAllFieldDefines = this.i18nTableUtils.getAllFieldsByTableName(tableName, new HashSet<String>());
        List<InputDataCheckColumnVO> checkTableColumns = this.listInputDataCheckColumn(InputDataCheckTabEnum.CHECKTAB, designAllFieldDefines);
        List<InputDataCheckColumnVO> unCheckTableColumns = this.listInputDataCheckColumn(InputDataCheckTabEnum.UNCHECKTAB, designAllFieldDefines);
        List<InputDataCheckColumnVO> allTableColumns = this.listInputDataCheckColumn(InputDataCheckTabEnum.AllDATA, designAllFieldDefines);
        inputDataCheckInitVO.setCheckTableColumns(checkTableColumns);
        inputDataCheckInitVO.setUnCheckTableColumns(unCheckTableColumns);
        inputDataCheckInitVO.setAllTableColumns(allTableColumns);
        inputDataCheckInitVO.setInputDataCheckLevel(selectOptions);
        return inputDataCheckInitVO;
    }

    @Override
    public Object autoCheck(InputDataCheckCondition inputDataCheckCondition) {
        if (null == this.inputDataCheckOffsetService) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.autocheckoffsetmsg"));
        }
        InputDataCheckEnvContextImpl inputDataCheckEnvContext = new InputDataCheckEnvContextImpl(inputDataCheckCondition.getSn());
        this.progressService.createProgressData((ProgressData)inputDataCheckEnvContext);
        inputDataCheckEnvContext.addResultItem("\u83b7\u53d6\u672a\u5bf9\u8d26\u6570\u636e");
        inputDataCheckEnvContext.addProgressValue(0.15);
        List<InputDataEO> inputDatas = this.inputDataCheckDao.listUnCheckData(inputDataCheckCondition);
        if (CollectionUtils.isEmpty(inputDatas)) {
            inputDataCheckEnvContext.addResultItem("\u67e5\u8be2\u6570\u636e\u4e3a\u7a7a\uff0c\u4e0d\u8fdb\u884c\u81ea\u52a8\u5bf9\u8d26\u64cd\u4f5c");
            inputDataCheckEnvContext.setSuccessFlag(false);
            inputDataCheckEnvContext.setProgressValueAndRefresh(1.0);
            return GcI18nUtil.getMessage((String)"gc.inputdata.check.noitemmsg");
        }
        inputDatas.forEach(CorporateConvertUtils::convertToOffsetUnit);
        inputDataCheckEnvContext.addResultItem("\u5b8c\u6210\u672a\u5bf9\u8d26\u6570\u636e\u83b7\u53d6");
        inputDataCheckEnvContext.addProgressValue(0.25);
        this.inputDataCheckOffsetService.doCheckAfterOffset(inputDatas);
        inputDataCheckEnvContext.addResultItem("\u81ea\u52a8\u5bf9\u8d26\u5df2\u5b8c\u6210");
        inputDataCheckEnvContext.setSuccessFlag(true);
        inputDataCheckEnvContext.setProgressValueAndRefresh(1.0);
        return inputDataCheckEnvContext;
    }

    @Override
    public Object manualCheck(InputDataCheckCondition inputDataCheckCondition) {
        return this.inputDataCheckDao.manualCheck(inputDataCheckCondition);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void manualCheckSave(InputDataCheckCondition inputDataCheckCondition) {
        String taskId = inputDataCheckCondition.getTaskId();
        Assert.notNull((Object)taskId, GcI18nUtil.getMessage((String)"gc.inputdata.check.taskisnullmsg"));
        List recordDatas = inputDataCheckCondition.getRecordDatas();
        ArrayList<InputDataEO> inputDatas = new ArrayList<InputDataEO>();
        ArrayList<String> ids = new ArrayList<String>();
        BigDecimal debitSum = BigDecimal.ZERO;
        BigDecimal creditSum = BigDecimal.ZERO;
        String checkGroupId = UUIDUtils.newUUIDStr();
        String userName = NpContextHolder.getContext().getUserName();
        Date checkTime = new Date();
        for (Map recordData : recordDatas) {
            InputDataEO inputData = new InputDataEO();
            inputData.resetFields(recordData);
            inputData.setCheckType(InputDataCheckTypeEnum.MANUAL_ITEM.getValue());
            inputData.setCheckState(InputDataCheckStateEnum.CHECK.getValue());
            inputData.setCheckTime(checkTime);
            inputData.setCheckUser(userName);
            inputData.setCheckGroupId(checkGroupId);
            Integer dc = (Integer)recordData.get("DC");
            if (Objects.isNull(recordData.get("CHECKAMT"))) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.amtisnullmsg"));
            }
            String checkAmtStr = String.valueOf(recordData.get("CHECKAMT"));
            BigDecimal checkAmt = BigDecimal.valueOf(Double.valueOf(checkAmtStr));
            if (OrientEnum.D.getValue().equals(dc)) {
                debitSum = debitSum.add(checkAmt);
            } else {
                creditSum = creditSum.add(checkAmt);
            }
            inputData.setCheckAmt(checkAmt.doubleValue());
            inputData.setId(String.valueOf(recordData.get("ID")));
            inputData.addFieldValue("BIZKEYORDER", inputData.getId());
            String memo = (String)recordData.get("MEMO");
            if (!StringUtils.isEmpty((String)memo)) {
                inputData.setMemo(memo);
            }
            inputDatas.add(inputData);
            ids.add(String.valueOf(recordData.get("ID")));
        }
        if (debitSum.compareTo(creditSum) != 0) {
            double diffAmt = creditSum.subtract(debitSum).doubleValue();
            Object[] args = new String[]{NumberUtils.doubleToString((double)creditSum.doubleValue()), NumberUtils.doubleToString((double)debitSum.doubleValue()), NumberUtils.doubleToString((double)diffAmt)};
            String message = GcI18nUtil.getMessage((String)"gc.inputdata.check.sumamtmsg", (Object[])args);
            throw new RuntimeException(message);
        }
        this.saveInputDataCheckInfo(taskId, inputDataCheckCondition.getDataTime(), inputDatas);
    }

    @Override
    public String cancelCheck(InputDataCheckCondition inputDataCheckCondition) {
        String taskId = inputDataCheckCondition.getTaskId();
        Assert.notNull((Object)taskId, GcI18nUtil.getMessage((String)"gc.inputdata.check.taskisnullmsg"));
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        List recordDataIds = inputDataCheckCondition.getRecordDataIds();
        if (CollectionUtils.isEmpty((Collection)recordDataIds)) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.itemnonemsg"));
        }
        List<InputDataEO> inputDatas = this.inputDataCheckDao.queryInputDataByCheckGroupIds(recordDataIds, tableName);
        if (CollectionUtils.isEmpty(inputDatas)) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.itemquerynonemsg"));
        }
        Map<String, List<InputDataEO>> inputDataGroups = inputDatas.stream().collect(Collectors.groupingBy(InputDataEO::getCheckGroupId));
        ArrayList<InputDataEO> inputList = new ArrayList<InputDataEO>();
        InputDataCheckUploadUtil inputDataCheckUploadUtil = new InputDataCheckUploadUtil();
        Integer ableCount = 0;
        for (Map.Entry<String, List<InputDataEO>> entry : inputDataGroups.entrySet()) {
            List<InputDataEO> inputDataEOs = entry.getValue();
            Set<String> unitSet = inputDataEOs.stream().flatMap(inputDataEO -> Stream.of(inputDataEO.getUnitId(), inputDataEO.getOppUnitId())).filter(Objects::nonNull).collect(Collectors.toSet());
            String[] orgs = unitSet.toArray(new String[2]);
            InputDataCheckAbleResult inputDataCheckAbleResult = inputDataCheckUploadUtil.checkUpload(inputDataCheckCondition, orgs[0], orgs[1]);
            if (Boolean.TRUE.equals(inputDataCheckAbleResult.getWriteAble())) {
                inputList.addAll(inputDataEOs);
                ableCount = ableCount + 1;
                continue;
            }
            this.logger.warn("{}\u4e0e{}\u5355\u4f4d\u5bf9\u8d26\u6743\u9650:{}", orgs[0], orgs[1], inputDataCheckAbleResult.getMsg());
        }
        String resMsg = GcI18nUtil.getMessage((String)"gc.inputdata.check.cancelsuccess");
        if (ableCount.equals(0)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.cancelnonsuccess"));
        }
        if (ableCount < inputDataGroups.size()) {
            resMsg = GcI18nUtil.getMessage((String)"gc.inputdata.check.cancelpartsuccess");
        }
        ((InputDataCheckService)SpringContextUtils.getBean(InputDataCheckService.class)).cancelCheckData(taskId, inputDataCheckCondition.getDataTime(), inputList);
        this.cancelOffset(taskId, inputDataCheckCondition.getDataTime(), inputList, tableName);
        return resMsg;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(propagation=Propagation.NESTED, rollbackFor={Exception.class})
    public void saveInputDataCheckInfo(String taskId, String dataTime, List<InputDataEO> inputDatas) {
        if (CollectionUtils.isEmpty(inputDatas)) {
            return;
        }
        List<String> ids = inputDatas.stream().map(InputDataEO::getId).collect(Collectors.toList());
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        InputWriteNecLimitCondition limitCondition = InputWriteNecLimitCondition.newMergeOrgLimit(taskId, dataTime, "CNY");
        String lockId = this.inputDataLockService.tryLock(ids, limitCondition, "\u5185\u90e8\u8868\u5bf9\u8d26");
        if (StringUtils.isEmpty((String)lockId)) {
            String lockUserName = this.inputDataLockService.queryUserNameByInputItemId(ids);
            Object[] args = new String[]{lockUserName};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.checklockmsg", (Object[])args));
        }
        try {
            this.inputDataCheckDao.updateInputDataCheckInfos(inputDatas, tableName);
        }
        finally {
            this.inputDataLockService.unlock(lockId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public String cancelCheckData(String taskId, String dataTime, List<InputDataEO> inputDatas) {
        if (CollectionUtils.isEmpty(inputDatas)) {
            return null;
        }
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        InputWriteNecLimitCondition limitCondition = InputWriteNecLimitCondition.newMergeOrgLimit(taskId, dataTime, "CNY");
        List<String> inputDataIds = inputDatas.stream().map(InputDataEO::getId).collect(Collectors.toList());
        String lockId = this.inputDataLockService.tryLock(inputDataIds, limitCondition, "\u53d6\u6d88\u5bf9\u8d26");
        if (StringUtils.isEmpty((String)lockId)) {
            String lockUserName = this.inputDataLockService.queryUserNameByInputItemId(inputDataIds);
            Object[] args = new String[]{lockUserName};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.checklockmsg", (Object[])args));
        }
        try {
            this.inputDataCheckDao.cancelLockedCheck(lockId, tableName);
        }
        finally {
            this.inputDataLockService.unlock(lockId);
        }
        return lockId;
    }

    private void cancelOffset(String taskId, String dataTime, List<InputDataEO> inputDatas, String tableName) {
        if (null == this.inputDataCheckOffsetService) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.autocheckoffsetmsg"));
        }
        List<String> offsetgroupids = inputDatas.stream().filter(inputDataEO -> !StringUtils.isEmpty((String)inputDataEO.getCheckGroupId()) && ReportOffsetStateEnum.OFFSET.getValue().equals(inputDataEO.getOffsetState())).map(InputDataEO::getOffsetGroupId).collect(Collectors.toList());
        Set<String> manualOffsetgroupids = ((InputDataDao)SpringContextUtils.getBean(InputDataDao.class)).queryByElmModeAndSrcOffsetGroupId(offsetgroupids, tableName);
        List<String> offsetInputItemIds = inputDatas.stream().filter(inputItem -> !manualOffsetgroupids.contains(inputItem.getOffsetGroupId())).map(InputDataEO::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(offsetInputItemIds)) {
            return;
        }
        this.inputDataCheckOffsetService.cancelInputOffset(offsetInputItemIds, InputWriteNecLimitCondition.newMergeOrgLimit(taskId, dataTime, "CNY"));
    }

    private List<InputDataCheckColumnVO> listInputDataCheckColumn(InputDataCheckTabEnum inputDataCheckTabEnum, List<DesignFieldDefineVO> designFieldDefines) {
        ArrayList<InputDataCheckColumnVO> tableColumns = new ArrayList<InputDataCheckColumnVO>();
        Map<String, String> i18nTitleGroupByCode = designFieldDefines.stream().collect(Collectors.toMap(DesignFieldDefineVO::getKey, DesignFieldDefineVO::getLabel, (v1, v2) -> v1));
        if (InputDataCheckTabEnum.AllDATA.equals((Object)inputDataCheckTabEnum)) {
            tableColumns.add(new InputDataCheckColumnVO("CHECKSTATETITLE", i18nTitleGroupByCode.get("CHECKSTATE"), ColumnModelType.STRING, 125));
        }
        if (InputDataCheckTabEnum.CHECKTAB.equals((Object)inputDataCheckTabEnum) || InputDataCheckTabEnum.AllDATA.equals((Object)inputDataCheckTabEnum)) {
            tableColumns.add(new InputDataCheckColumnVO("CHECKTYPETITLE", GcI18nUtil.getMessage((String)"gc.inputdata.check.checktype"), ColumnModelType.STRING, 125));
        }
        tableColumns.add(new InputDataCheckColumnVO("RULETITLE", GcI18nUtil.getMessage((String)"gc.inputdata.check.rule"), ColumnModelType.STRING, 200));
        tableColumns.add(new InputDataCheckColumnVO("UNITTITLE", GcI18nUtil.getMessage((String)"gc.inputdata.check.unit"), ColumnModelType.STRING, 200));
        tableColumns.add(new InputDataCheckColumnVO("OPPUNITTITLE", GcI18nUtil.getMessage((String)"gc.inputdata.check.oppunit"), ColumnModelType.STRING, 200));
        tableColumns.add(new InputDataCheckColumnVO("SUBJECTTITLE", GcI18nUtil.getMessage((String)"gc.inputdata.check.subject"), ColumnModelType.STRING, 150));
        tableColumns.add(new InputDataCheckColumnVO("CHECKDEBIT", GcI18nUtil.getMessage((String)"gc.inputdata.check.checkdebit"), ColumnModelType.BIGDECIMAL, 150));
        tableColumns.add(new InputDataCheckColumnVO("CHECKCREDIT", GcI18nUtil.getMessage((String)"gc.inputdata.check.checkcredit"), ColumnModelType.BIGDECIMAL, 150));
        if (InputDataCheckTabEnum.CHECKTAB.equals((Object)inputDataCheckTabEnum) || InputDataCheckTabEnum.AllDATA.equals((Object)inputDataCheckTabEnum)) {
            tableColumns.add(new InputDataCheckColumnVO("CHECKAMT", i18nTitleGroupByCode.get("CHECKAMT"), ColumnModelType.BIGDECIMAL, 150));
        }
        tableColumns.add(new InputDataCheckColumnVO("CHECKDIFF", GcI18nUtil.getMessage((String)"gc.inputdata.check.checkdiff"), ColumnModelType.BIGDECIMAL, 150));
        tableColumns.add(new InputDataCheckColumnVO("OFFSETSTATETITLE", i18nTitleGroupByCode.get("OFFSETSTATE"), ColumnModelType.STRING, 125));
        tableColumns.add(new InputDataCheckColumnVO("MEMO", GcI18nUtil.getMessage((String)"gc.inputdata.check.memo"), ColumnModelType.STRING, 160));
        return tableColumns;
    }

    @Override
    public void updateMemo(InputDataCheckUpdateMemoVO dataCheckUpdateMemoVO) {
        if (Objects.isNull(dataCheckUpdateMemoVO)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.updateparammsg"));
        }
        if (StringUtils.isEmpty((String)dataCheckUpdateMemoVO.getId())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.updateidparammsg"));
        }
        if (StringUtils.isEmpty((String)dataCheckUpdateMemoVO.getMemo())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.updatememoparammsg"));
        }
        if (StringUtils.isEmpty((String)dataCheckUpdateMemoVO.getTaskId())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.updatetaskparammsg"));
        }
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(dataCheckUpdateMemoVO.getTaskId());
        this.inputDataDao.updateMemoById(dataCheckUpdateMemoVO.getId(), dataCheckUpdateMemoVO.getMemo(), tableName);
    }

    private List<InputDataCheckColumnVO> listInputDataCheckColumns(List<DesignFieldDefineVO> designFieldDefines) {
        ArrayList<InputDataCheckColumnVO> checkColumns = new ArrayList<InputDataCheckColumnVO>();
        for (DesignFieldDefineVO designFieldDefine : designFieldDefines) {
            InputDataCheckColumnVO checkColumn = new InputDataCheckColumnVO();
            BeanUtils.copyProperties(designFieldDefine, checkColumn);
            int length = designFieldDefine.getLabel().length();
            checkColumn.setMinWidth(150 + length * 10);
            checkColumns.add(checkColumn);
        }
        return checkColumns;
    }

    @Override
    public Object sumAmt(String taskId, List<String> inputDataIds) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        if (CollectionUtils.isEmpty(inputDataIds)) {
            return "\u9009\u4e2d\u6570\u636e\u4e0d\u80fd\u4e3a\u7a7a\u3002";
        }
        List<InputDataEO> inputDatas = this.inputDataDao.queryByIds(inputDataIds, tableName);
        if (CollectionUtils.isEmpty(inputDatas)) {
            return "\u67e5\u8be2\u6570\u636e\u4e3a\u7a7a\u3002";
        }
        HashMap<String, String> sumAmt = new HashMap<String, String>();
        BigDecimal debitSum = BigDecimal.ZERO;
        BigDecimal creditSum = BigDecimal.ZERO;
        for (InputDataEO inputData : inputDatas) {
            BigDecimal unCheckAmt;
            BigDecimal bigDecimal = unCheckAmt = Objects.isNull(inputData.getUnCheckAmt()) ? BigDecimal.ZERO : BigDecimal.valueOf(inputData.getUnCheckAmt());
            if (OrientEnum.D.getValue().equals(inputData.getDc())) {
                debitSum = debitSum.add(unCheckAmt);
                continue;
            }
            creditSum = creditSum.add(unCheckAmt);
        }
        double diffAmt = creditSum.subtract(debitSum).doubleValue();
        sumAmt.put("DEBITSUM", NumberUtils.doubleToString((double)debitSum.doubleValue()));
        sumAmt.put("CREDITSUM", NumberUtils.doubleToString((double)creditSum.doubleValue()));
        sumAmt.put("DIFFAMT", NumberUtils.doubleToString((double)diffAmt));
        return sumAmt;
    }
}

