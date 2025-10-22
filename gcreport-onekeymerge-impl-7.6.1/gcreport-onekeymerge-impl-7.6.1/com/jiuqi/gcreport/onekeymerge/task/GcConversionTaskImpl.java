/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.conversion.common.GcConversionContextEnv
 *  com.jiuqi.gcreport.conversion.service.ConversionService
 *  com.jiuqi.gcreport.onekeymerge.dto.FormTypeListDTO
 *  com.jiuqi.gcreport.onekeymerge.dto.UnitType
 *  com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcConversionVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.conversion.common.GcConversionContextEnv;
import com.jiuqi.gcreport.conversion.service.ConversionService;
import com.jiuqi.gcreport.onekeymerge.dto.FormTypeListDTO;
import com.jiuqi.gcreport.onekeymerge.dto.UnitType;
import com.jiuqi.gcreport.onekeymerge.entity.GcTaskResultEO;
import com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyProcessService;
import com.jiuqi.gcreport.onekeymerge.task.GcCenterTask;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcConversionVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcConversionTaskImpl
implements GcCenterTask {
    private static final Logger logger = LoggerFactory.getLogger(GcConversionTaskImpl.class);
    @Autowired
    private GcOnekeyMergeService onekeyMergeService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private GcOnekeyProcessService onekeyProcessService;
    @Autowired
    private ConversionService conversionService;

    @Override
    public ReturnObject doTask(GcActionParamsVO paramsVO) {
        return this.doConversion(paramsVO);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ReturnObject doConversion(GcActionParamsVO paramsVO) {
        TaskLog taskLog = new TaskLog(paramsVO.getOnekeyProgressData());
        TaskStateEnum state = TaskStateEnum.EXECUTING;
        GcTaskResultEO eo = new GcTaskResultEO();
        eo.setUserName(OneKeyMergeUtils.getUser().getName());
        eo.setTaskTime(DateUtils.now());
        eo.setTaskCode(TaskTypeEnum.CONVERSION.getCode());
        ReturnObject returnObject = this.onekeyMergeService.checkUploadState(paramsVO, paramsVO.getOrgId());
        if (returnObject.isSuccess()) {
            taskLog.writeErrorLog("\u5f53\u524d\u5355\u4f4d\u5df2\u7ecf" + returnObject.getErrorMessage(), Float.valueOf(0.0f));
            return new ReturnObject(true, null);
        }
        List<GcOrgCacheVO> childrenTree = OrgUtils.getChildrenTreeByParentId(paramsVO);
        String currencyId = StringUtils.toViewString((Object)paramsVO.getCurrency());
        Assert.isNotNull((Object)currencyId, (String)"\u8bf7\u9009\u62e9\u76ee\u6807\u5e01\u79cd", (Object[])new Object[0]);
        ArrayList<GcBaseTaskStateVO> conversionVOS = new ArrayList<GcBaseTaskStateVO>();
        try {
            List formDefines = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(paramsVO.getSchemeId());
            List formList = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            List baseFormList = formDefines.stream().filter(define -> !define.getTitle().startsWith("\u5185\u90e8")).map(IBaseMetaItem::getKey).collect(Collectors.toList());
            FormTypeListDTO formDto = new FormTypeListDTO(formList, baseFormList, null);
            if (paramsVO.getMergeType().equals((Object)MergeTypeEnum.ALL_LEVEL)) {
                List<GcOrgCacheVO> leafs = OrgUtils.getAllLeafUnitByParent(paramsVO);
                taskLog.setTotalNum(Integer.valueOf(leafs.size()));
            } else {
                int directLeaf = OrgUtils.getDirectLeafUnitByParent(childrenTree).size();
                taskLog.setTotalNum(Integer.valueOf(directLeaf));
            }
            this.executeConversion(taskLog, paramsVO, childrenTree.get(0).getChildren(), formDto, conversionVOS);
            state = TaskStateEnum.SUCCESS;
        }
        catch (Exception e) {
            state = TaskStateEnum.ERROR;
            logger.error(e.getMessage(), e);
        }
        finally {
            List message = taskLog.getCompleteMessage();
            if (CollectionUtils.isEmpty((Collection)message)) {
                taskLog.writeWarnLog("\u6ca1\u6709\u6ee1\u8db3\u6298\u7b97\u6761\u4ef6\u7684\u5355\u4f4d", null);
            }
            taskLog.setFinish(true);
            taskLog.setState(state);
            this.onekeyMergeService.saveTaskResult(paramsVO, eo, conversionVOS, state.getCode());
        }
        taskLog.endTask();
        return new ReturnObject(state.equals((Object)TaskStateEnum.SUCCESS), conversionVOS);
    }

    private void executeConversion(TaskLog taskLog, GcActionParamsVO paramsVO, List<GcOrgCacheVO> children, FormTypeListDTO formDto, List<GcBaseTaskStateVO> conversionVOS) {
        for (GcOrgCacheVO org : children) {
            if (this.onekeyMergeService.getStopOrNot(paramsVO.getTaskLogId().toString())) {
                taskLog.writeErrorLog("\u624b\u52a8\u505c\u6b62,\u670d\u52a1\u4e2d\u65ad", null);
                throw new BusinessRuntimeException("\u670d\u52a1\u7ec8\u6b62");
            }
            ReturnObject returnObject = this.onekeyMergeService.checkUploadState(paramsVO, org.getId());
            if (!CollectionUtils.isEmpty((Collection)org.getChildren())) {
                if (!paramsVO.getMergeType().equals((Object)MergeTypeEnum.ALL_LEVEL)) continue;
                if (returnObject.isSuccess()) {
                    conversionVOS.add(this.buildConversion(paramsVO, org, "\u5ffd\u7565:" + returnObject.getErrorMessage(), null));
                    ArrayList<GcOrgCacheVO> list = new ArrayList<GcOrgCacheVO>();
                    list.add(org);
                    int allLeaf = OrgUtils.getAllLeafUnitByParent(list).size();
                    taskLog.setDoneNum(Integer.valueOf(taskLog.getDoneNum() + allLeaf));
                    continue;
                }
                this.executeConversion(taskLog, paramsVO, org.getChildren(), formDto, conversionVOS);
                continue;
            }
            taskLog.setDoneNum(Integer.valueOf(taskLog.getDoneNum() + 1));
            taskLog.writeInfoLog("\u5f00\u59cb\u6267\u884c: " + org.getTitle(), Float.valueOf(taskLog.getProcessPercent()));
            if (returnObject.isSuccess()) {
                conversionVOS.add(this.buildConversion(paramsVO, org, "\u5ffd\u7565:" + returnObject.getErrorMessage(), null));
                taskLog.writeWarnLog("\u975e\u5408\u5e76\u5355\u4f4d:" + org.getTitle() + returnObject.getErrorMessage(), null);
                continue;
            }
            if (org.getOrgKind().equals((Object)GcOrgKindEnum.DIFFERENCE)) {
                this.executeConversionByOrgType(taskLog, paramsVO, UnitType.DIFFUNIT, org, formDto, conversionVOS);
                continue;
            }
            this.executeConversionByOrgType(taskLog, paramsVO, UnitType.NONESTATE, org, formDto, conversionVOS);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeConversionByOrgType(TaskLog taskLog, GcActionParamsVO paramsVO, UnitType unitType, GcOrgCacheVO org, FormTypeListDTO formDto, List<GcBaseTaskStateVO> conversionVOS) {
        String currencyid = StringUtils.toViewString((Object)org.getTypeFieldValue("CURRENCYID"));
        if (null == currencyid) {
            conversionVOS.add(this.buildConversion(paramsVO, org, "\u5ffd\u7565: \u672c\u4f4d\u5e01\u672a\u8bbe\u7f6e", null));
            return;
        }
        Boolean aBoolean = this.onekeyMergeService.checkDiffCurrency(paramsVO, org);
        if (aBoolean.booleanValue()) {
            conversionVOS.add(this.buildConversion(paramsVO, org, "\u5ffd\u7565: \u672c\u4f4d\u5e01\u548c\u76ee\u6807\u5e01\u79cd\u4e00\u81f4", null));
            return;
        }
        ReturnObject returnObject = this.onekeyMergeService.checkCurrencyDataAndUploadState(paramsVO, org);
        if (returnObject.isSuccess()) {
            conversionVOS.add(this.buildConversion(paramsVO, org, "\u5ffd\u7565:" + returnObject.getErrorMessage(), null));
            taskLog.writeWarnLog("\u975e\u5408\u5e76\u5355\u4f4d:" + org.getTitle() + "\u76ee\u6807\u5e01\u79cd\u6709\u6570\u636e,\u5ffd\u7565", null);
            return;
        }
        List formList = formDto.getFormsByOrgType(unitType);
        boolean conversionFlag = false;
        long start = System.currentTimeMillis();
        try {
            taskLog.writeInfoLog(org.getTitle() + "\u5f00\u59cb\u6298\u7b97", null);
            this.conversion(paramsVO, org, formList);
            taskLog.writeInfoLog(org.getTitle() + "\u6298\u7b97\u6210\u529f", null);
            conversionFlag = true;
            conversionVOS.add(this.buildConversion(paramsVO, org, conversionFlag ? "\u6210\u529f" : "\u5931\u8d25", start));
        }
        catch (Exception e) {
            try {
                taskLog.writeWarnLog(org.getTitle() + "\u6298\u7b97\u5931\u8d25:" + e.getMessage(), null);
                logger.error(e.getMessage(), e);
                conversionVOS.add(this.buildConversion(paramsVO, org, conversionFlag ? "\u6210\u529f" : "\u5931\u8d25", start));
            }
            catch (Throwable throwable) {
                conversionVOS.add(this.buildConversion(paramsVO, org, conversionFlag ? "\u6210\u529f" : "\u5931\u8d25", start));
                throw throwable;
            }
        }
    }

    private GcBaseTaskStateVO buildConversion(GcActionParamsVO paramsVO, GcOrgCacheVO org, String msg, Long start) {
        String currencyid = StringUtils.toViewString((Object)org.getTypeFieldValue("CURRENCYID"));
        String beforeCurrency = null == currencyid ? "\u672a\u8bbe\u7f6e\u672c\u4f4d\u5e01" : GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CURRENCY", currencyid).getTitle();
        GcBaseData afterCurrency = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CURRENCY", paramsVO.getCurrency());
        GcConversionVO vo = new GcConversionVO();
        vo.setId(UUIDUtils.newUUIDStr());
        vo.setOrgName(org.getTitle());
        vo.setCurrencyName(beforeCurrency);
        vo.setTarCurrencyName(afterCurrency.getTitle());
        vo.setState(msg);
        if (null != start) {
            String useTime = String.valueOf(System.currentTimeMillis() - start);
            vo.setUseTime(useTime);
        }
        return vo;
    }

    private void conversion(GcActionParamsVO paramsVO, GcOrgCacheVO org, List<String> formList) {
        GcConversionContextEnv env = this.onekeyMergeService.buildConversionEnv(paramsVO, org);
        env.setFormIds(formList);
        this.conversionService.conversion(env);
    }
}

