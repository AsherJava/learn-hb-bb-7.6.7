/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.common.log.UnitReportLog
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.dataentry.bean.JIOFormImportResult
 *  com.jiuqi.nr.dataentry.bean.JIOUnitImportResult
 *  com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.RunTimeFormDefineImpl
 *  com.jiuqi.nr.jtable.exception.JTableException
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  javax.annotation.Resource
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.data.DataEntityInfo
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.SingleDataError
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.util.SingleMapEntityUtil
 */
package nr.single.client.internal.service.upload;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.common.log.UnitReportLog;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataentry.bean.JIOFormImportResult;
import com.jiuqi.nr.dataentry.bean.JIOUnitImportResult;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormDefineImpl;
import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.client.internal.service.upload.UploadJioDataUtil;
import nr.single.client.service.upload.IUploadDataLogService;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.PathUtil;
import nr.single.map.data.SingleDataError;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.util.SingleMapEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadDataLogServiceImpl
implements IUploadDataLogService {
    private static final Logger logger = LoggerFactory.getLogger(UploadDataLogServiceImpl.class);
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private SingleMapEntityUtil mapEntityUtil;
    @Resource
    private IRunTimeViewController runtimeView;
    private static final String LOG_TITLE = "JIO\u6570\u636e\u5bfc\u5165";
    private static final String FMDM_FORMCODE = "FMDM";

    @Override
    public void recordFmdmErrors(TaskDataContext context, JIOImportResultObject result) {
        this.recordFormErrors(context, result, FMDM_FORMCODE);
    }

    @Override
    public void recordFormErrors(TaskDataContext context, JIOImportResultObject result, String formCode) {
        List logList;
        if (context.getLogs().containsKey(formCode) && (logList = (List)context.getLogs().get(formCode)).size() > 0) {
            HashMap<String, JIOUnitImportResult> errorUnits = new HashMap<String, JIOUnitImportResult>();
            LinkedHashMap<String, JIOUnitImportResult> deleteUnits = new LinkedHashMap<String, JIOUnitImportResult>();
            if (FMDM_FORMCODE.equalsIgnoreCase(formCode)) {
                this.recordErorr(result, logList, errorUnits, deleteUnits);
            } else if ("importSecordForm".equalsIgnoreCase(formCode)) {
                this.recordErorr2(result, logList, errorUnits);
            } else {
                this.recordErorr2(result, logList, errorUnits);
            }
            HashMap<String, JIOUnitImportResult> sucessMap = new HashMap<String, JIOUnitImportResult>();
            for (JIOUnitImportResult jIOUnitImportResult : result.getSuccessUnits()) {
                sucessMap.put(jIOUnitImportResult.getUnitKey(), jIOUnitImportResult);
            }
            for (Map.Entry entry : errorUnits.entrySet()) {
                result.getErrorUnits().add(entry.getValue());
                if (!sucessMap.containsKey(((JIOUnitImportResult)entry.getValue()).getUnitKey())) continue;
                JIOUnitImportResult oldInfo = (JIOUnitImportResult)sucessMap.get(((JIOUnitImportResult)entry.getValue()).getUnitKey());
                result.getSuccessUnits().remove(oldInfo);
            }
            for (Map.Entry entry : deleteUnits.entrySet()) {
                result.getDeleteUnits().add(entry.getValue());
            }
            if (result.isSuccess()) {
                result.setSuccess(errorUnits.isEmpty());
            }
            result.setUploadUnitNum(context.getSingleCorpCount());
            result.setAllSuccesssUnitNum(context.getSingleCorpCount() - result.getErrorUnits().size());
            result.setDeleteUnitNum(deleteUnits.size());
        }
    }

    private void recordErorr(JIOImportResultObject result, List<SingleDataError> logList, Map<String, JIOUnitImportResult> errorUnits, Map<String, JIOUnitImportResult> deleteUnits) {
        List<SingleDataError> tempList;
        HashMap errorLogDic = new HashMap();
        for (JIOUnitImportResult unitInfo : result.getErrorUnits()) {
            errorUnits.put(unitInfo.getUnitKey(), unitInfo);
        }
        for (JIOUnitImportResult unitInfo : result.getDeleteUnits()) {
            deleteUnits.put(unitInfo.getUnitKey(), unitInfo);
        }
        for (SingleDataError info : logList) {
            JIOUnitImportResult unitImportResult;
            tempList = null;
            if (errorLogDic.containsKey(info.getErrorCode())) {
                tempList = (List)errorLogDic.get(info.getErrorCode());
            } else {
                tempList = new ArrayList();
                errorLogDic.put(info.getErrorCode(), tempList);
            }
            tempList.add(info);
            if ("notAdd".equals(info.getErrorCode()) || "filterNotUpdate".equalsIgnoreCase(info.getErrorCode()) || "filterNotAdd".equalsIgnoreCase(info.getErrorCode())) {
                if (errorUnits.containsKey(info.getCompanyName())) continue;
                unitImportResult = this.getNewUnitResult(info);
                unitImportResult.setMessage("\u5355\u4f4d\u5339\u914d\u5931\u8d25");
                errorUnits.put(info.getCompanyKey(), unitImportResult);
                continue;
            }
            if ("checkTreeNotAdd".equals(info.getErrorCode()) || "checkTreeNotFJD".equals(info.getErrorCode()) || "checkTreeNotExist".equalsIgnoreCase(info.getErrorCode())) {
                if (errorUnits.containsKey(info.getCompanyKey())) continue;
                unitImportResult = this.getNewUnitResult(info);
                errorUnits.put(info.getCompanyKey(), unitImportResult);
                continue;
            }
            if ("checkZdmError".equals(info.getErrorCode()) || "checkLinkChainError".equalsIgnoreCase(info.getErrorCode()) || "repeatCode".equalsIgnoreCase(info.getErrorCode()) || "checkRootEntityCount".equalsIgnoreCase(info.getErrorCode()) || "exist_otherNode".equalsIgnoreCase(info.getErrorCode()) || "noPower_add".equalsIgnoreCase(info.getErrorCode()) || "noPower_modify".equalsIgnoreCase(info.getErrorCode()) || "noPower_modifyParent".equalsIgnoreCase(info.getErrorCode()) || "noPower_delete".equalsIgnoreCase(info.getErrorCode())) {
                if (errorUnits.containsKey(info.getCompanyCode() + info.getCompanyName())) continue;
                unitImportResult = this.getNewUnitResult(info);
                errorUnits.put(info.getCompanyCode() + info.getCompanyName(), unitImportResult);
                continue;
            }
            if ("notMap".equals(info.getErrorCode()) || "notJcm".equals(info.getErrorCode()) || "repeatNotUpdate".equals(info.getErrorCode())) {
                if (errorUnits.containsKey(info.getCompanyKey())) continue;
                unitImportResult = this.getNewUnitResult(info);
                errorUnits.put(info.getCompanyKey(), unitImportResult);
                continue;
            }
            if ("fileRepeatNotUpdate".equalsIgnoreCase(info.getErrorCode())) {
                if (errorUnits.containsKey(info.getCompanyCode() + info.getCompanyName())) continue;
                unitImportResult = this.getNewUnitResult(info);
                errorUnits.put(info.getCompanyCode() + info.getCompanyName(), unitImportResult);
                continue;
            }
            if ("addEntityError".equalsIgnoreCase(info.getErrorCode()) || "updateEntityError".equalsIgnoreCase(info.getErrorCode())) {
                if (errorUnits.containsKey(info.getCompanyKey())) continue;
                unitImportResult = this.getNewUnitResult(info);
                unitImportResult.setMessage("");
                errorUnits.put(info.getCompanyKey(), unitImportResult);
                continue;
            }
            if (!"delete_unit".equalsIgnoreCase(info.getErrorCode()) || deleteUnits.containsKey(info.getCompanyKey())) continue;
            unitImportResult = this.getNewUnitResult(info);
            deleteUnits.put(info.getCompanyKey(), unitImportResult);
        }
        for (String errorCode : errorLogDic.keySet()) {
            tempList = (List)errorLogDic.get(errorCode);
            logger.info("\u5b9e\u4f53\u6570\u636e\uff1a" + errorCode + ",\u6709" + tempList.size() + "\u4e2a\u5355\u4f4d");
        }
    }

    private void recordErorr2(JIOImportResultObject result, List<SingleDataError> logList, Map<String, JIOUnitImportResult> errorUnits) {
        List<SingleDataError> tempList;
        HashMap errorLogDic = new HashMap();
        for (JIOUnitImportResult unitInfo : result.getErrorUnits()) {
            errorUnits.put(unitInfo.getUnitKey(), unitInfo);
        }
        for (SingleDataError info : logList) {
            tempList = null;
            if (errorLogDic.containsKey(info.getErrorCode())) {
                tempList = (List)errorLogDic.get(info.getErrorCode());
            } else {
                tempList = new ArrayList();
                errorLogDic.put(info.getErrorCode(), tempList);
            }
            tempList.add(info);
            if (!"importSecordFileFail".equals(info.getErrorCode()) || errorUnits.containsKey(info.getCompanyKey())) continue;
            JIOUnitImportResult unitImportResult = this.getNewUnitResult(info);
            errorUnits.put(info.getCompanyKey(), unitImportResult);
        }
        for (String errorCode : errorLogDic.keySet()) {
            tempList = (List)errorLogDic.get(errorCode);
            logger.info("\u62a5\u8868\u6570\u636e\uff1a" + errorCode + ",\u6709" + tempList.size() + "\u4e2a\u5355\u4f4d");
        }
    }

    @Override
    public void clearSuccessForms(TaskDataContext context, JIOImportResultObject result) {
        if (result.getSuccessUnits() != null) {
            for (JIOUnitImportResult unitInfo : result.getSuccessUnits()) {
                if (unitInfo.getFormResults() != null) {
                    unitInfo.getFormResults().clear();
                }
                if (unitInfo.getFormMapResults() == null) continue;
                unitInfo.getFormMapResults().clear();
            }
        }
    }

    private JIOUnitImportResult getNewUnitResult(SingleDataError info) {
        JIOUnitImportResult unitImportResult = new JIOUnitImportResult();
        unitImportResult.setUnitKey(info.getCompanyKey());
        unitImportResult.setMessage(info.getErrorInfo());
        unitImportResult.setUnitTitle(info.getCompanyName());
        unitImportResult.setUnitCode(info.getCompanyCode());
        Map formMapResults = unitImportResult.getFormMapResults();
        JIOFormImportResult jioFormImportResult = new JIOFormImportResult();
        jioFormImportResult.setFormCode(info.getFormCode());
        jioFormImportResult.setFormTitle(info.getFormName());
        jioFormImportResult.setMessage(info.getErrorInfo());
        formMapResults.put(info.getFormCode(), jioFormImportResult);
        return unitImportResult;
    }

    @Override
    public void recordFmdmSuccess(TaskDataContext context, JIOImportResultObject result) {
        Map uploadEntityZdmKeyMap = context.getUploadEntityZdmKeyMap();
        if (uploadEntityZdmKeyMap.size() > 0) {
            for (Map.Entry entry : uploadEntityZdmKeyMap.entrySet()) {
                String unitKey = (String)entry.getValue();
                String unitCode = "";
                String unitTitle = "";
                DataEntityInfo entinty = context.getEntityCache().findEntityByKey(unitKey);
                if (entinty == null) {
                    entinty = context.getEntityCache().findEntityByCode(unitKey);
                }
                if (entinty != null) {
                    unitCode = entinty.getEntityCode();
                    unitTitle = entinty.getEntityTitle();
                } else {
                    unitCode = unitKey;
                }
                JIOUnitImportResult unitImportResult = new JIOUnitImportResult();
                unitImportResult.setUnitKey(unitKey);
                unitImportResult.setMessage(null);
                unitImportResult.setUnitCode(unitCode);
                unitImportResult.setUnitTitle(unitTitle);
                Map formMapResults = unitImportResult.getFormMapResults();
                JIOFormImportResult jioFormImportResult = new JIOFormImportResult();
                jioFormImportResult.setFormCode(FMDM_FORMCODE);
                jioFormImportResult.setFormTitle("\u5c01\u9762\u4ee3\u7801");
                jioFormImportResult.setMessage("");
                formMapResults.put(jioFormImportResult.getFormCode(), jioFormImportResult);
                result.getSuccessUnits().add(unitImportResult);
            }
            result.setSuccess(true);
            result.setUploadUnitNum(context.getSingleCorpCount());
            result.setAllSuccesssUnitNum(result.getSuccessUnits().size());
        }
    }

    @Override
    public void setAfterImportSingleReportLog(TaskDataContext importContext, JIOImportResultObject resObject, String dataPath, Map<String, JIOUnitImportResult> successMap, Map<String, JIOUnitImportResult> errorMap, Map<String, DimensionValue> dimensionSet) {
        this.checkSingleMapConfig(importContext, resObject, dataPath, successMap, errorMap, dimensionSet);
        resObject.setSuccesssUnitNum(successMap.size());
        resObject.setErrorUnitNum(errorMap.size());
        HashMap<String, JIOUnitImportResult> allUnitMap = new HashMap<String, JIOUnitImportResult>();
        HashMap<String, JIOUnitImportResult> allSuccessMap = new HashMap<String, JIOUnitImportResult>();
        for (Map.Entry<String, JIOUnitImportResult> entry : successMap.entrySet()) {
            allUnitMap.put(entry.getKey(), entry.getValue());
            if (errorMap.containsKey(entry.getKey())) continue;
            allSuccessMap.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, JIOUnitImportResult> entry : errorMap.entrySet()) {
            if (allUnitMap.containsKey(entry.getKey()) || "\u8be5\u6279\u6b21\u5355\u4f4d\u4e2d".equalsIgnoreCase(entry.getKey())) continue;
            allUnitMap.put(entry.getKey(), entry.getValue());
        }
        resObject.setAllSuccesssUnitNum(allSuccessMap.size());
        resObject.setUploadUnitNum(importContext.getSingleCorpCount());
        ArrayList<JIOUnitImportResult> successUnits = new ArrayList<JIOUnitImportResult>();
        ArrayList<JIOUnitImportResult> errorUnits = new ArrayList<JIOUnitImportResult>();
        this.resetSuccessAndFailUnits(resObject, successMap, errorMap, successUnits, errorUnits);
        Map<String, DataEntityInfo> queryCodeEntitys = this.getQueryCodeEntitys(importContext, successUnits, errorUnits);
        HashMap<String, String> unitTitleMap = new HashMap<String, String>();
        HashMap<String, String> unitCodeMap = new HashMap<String, String>();
        StringBuilder unitStrings = new StringBuilder();
        for (JIOUnitImportResult jioUnitImportResult : successUnits) {
            unitStrings.append(jioUnitImportResult.getUnitKey()).append(";");
            this.resetUnitInfo(importContext, jioUnitImportResult, unitTitleMap, unitCodeMap, queryCodeEntitys);
            resObject.setSuccesssReportNum(resObject.getSuccesssReportNum() + jioUnitImportResult.getFormResults().size());
        }
        if (unitStrings.length() > 0) {
            unitStrings.deleteCharAt(unitStrings.length() - 1);
        }
        for (JIOUnitImportResult jioUnitImportResult : errorUnits) {
            resObject.setErrorReportNum(resObject.getErrorReportNum() + jioUnitImportResult.getFormResults().size());
            if (!StringUtils.isEmpty((String)jioUnitImportResult.getUnitTitle())) continue;
            if (!unitTitleMap.containsKey(jioUnitImportResult.getUnitKey())) {
                this.resetUnitInfo2(importContext, jioUnitImportResult, unitTitleMap, unitCodeMap, queryCodeEntitys);
                continue;
            }
            jioUnitImportResult.setUnitTitle((String)unitTitleMap.get(jioUnitImportResult.getUnitKey()));
            jioUnitImportResult.setUnitCode((String)unitCodeMap.get(jioUnitImportResult.getUnitKey()));
        }
        if (resObject.getErrorUnitNum() > 0) {
            resObject.setSuccess(false);
        }
        Map<String, DimensionValue> dimensionValueSet2 = UploadJioDataUtil.getNewDimensionSet(dimensionSet);
        DimensionValue entityDim = dimensionValueSet2.get(importContext.getEntityCompanyType());
        entityDim.setValue(unitStrings.toString());
        resObject.setDimensionSet(dimensionValueSet2);
    }

    private void resetSuccessAndFailUnits(JIOImportResultObject resObject, Map<String, JIOUnitImportResult> successMap, Map<String, JIOUnitImportResult> errorMap, List<JIOUnitImportResult> successUnits, List<JIOUnitImportResult> errorUnits) {
        JIOFormImportResult valueForm;
        Map formMapResults;
        List formResults;
        JIOUnitImportResult value;
        for (Map.Entry<String, JIOUnitImportResult> entry : successMap.entrySet()) {
            value = entry.getValue();
            formResults = value.getFormResults();
            formMapResults = value.getFormMapResults();
            for (Map.Entry entryForm : formMapResults.entrySet()) {
                valueForm = (JIOFormImportResult)entryForm.getValue();
                formResults.add(valueForm);
            }
            value.setFormMapResults(new HashMap());
            successUnits.add(value);
        }
        resObject.setSuccessUnits(successUnits);
        for (Map.Entry<String, JIOUnitImportResult> entry : errorMap.entrySet()) {
            value = entry.getValue();
            formResults = value.getFormResults();
            formMapResults = value.getFormMapResults();
            for (Map.Entry entryForm : formMapResults.entrySet()) {
                valueForm = (JIOFormImportResult)entryForm.getValue();
                formResults.add(valueForm);
            }
            value.setFormMapResults(new HashMap());
            errorUnits.add(value);
        }
        resObject.setErrorUnits(errorUnits);
    }

    private void checkSingleMapConfig(TaskDataContext importContext, JIOImportResultObject resObject, String dataPath, Map<String, JIOUnitImportResult> successMap, Map<String, JIOUnitImportResult> errorMap, Map<String, DimensionValue> dimensionSet) {
        Map singleMapTables = importContext.getMapingCache().getSingleFieldMap();
        ISingleMappingConfig mapConfig = importContext.getMapingCache().getMapConfig();
        if (null != mapConfig && null != singleMapTables) {
            for (SingleFileTableInfo singeTable : mapConfig.getTableInfos()) {
                boolean isError = false;
                isError = this.checkFieldMaping(singleMapTables, singeTable.getSingleTableCode());
                if (isError) {
                    String fileFlag = importContext.getFileFlag();
                    String fileName = dataPath + fileFlag + singeTable.getSingleTableCode() + ".DBF";
                    try {
                        if (!PathUtil.getFileExists((String)fileName)) {
                            isError = false;
                        }
                    }
                    catch (SingleFileException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                if (!isError) continue;
                RunTimeFormDefineImpl formDefine = new RunTimeFormDefineImpl();
                formDefine.setKey(singeTable.getSingleTableCode());
                formDefine.setFormCode(singeTable.getSingleTableCode());
                formDefine.setTitle(singeTable.getSingleTableTitle());
                logger.info("\u7cfb\u7edf\u627e\u4e0d\u5230\u8be5\u8868\uff1a" + formDefine.getFormCode() + "," + formDefine.getTitle());
            }
        }
    }

    private boolean checkFieldMaping(Map<String, Map<String, SingleFileFieldInfo>> singleMapTables, String singleTableCode) {
        boolean isEmptyMap = true;
        if (singleMapTables.containsKey(singleTableCode)) {
            Map<String, SingleFileFieldInfo> tableFields = singleMapTables.get(singleTableCode);
            for (SingleFileFieldInfo singleField : tableFields.values()) {
                if (!StringUtils.isNotEmpty((String)singleField.getNetFormCode())) continue;
                isEmptyMap = false;
                break;
            }
        }
        return isEmptyMap;
    }

    private Map<String, DataEntityInfo> getQueryCodeEntitys(TaskDataContext importContext, List<JIOUnitImportResult> successUnits, List<JIOUnitImportResult> errorUnits) {
        DataEntityInfo entiyInfo;
        ArrayList<String> queryCodes = new ArrayList<String>();
        HashSet<String> queryCodeSet = new HashSet<String>();
        HashMap<String, DataEntityInfo> queryCodeEntitys = new HashMap<String, DataEntityInfo>();
        for (JIOUnitImportResult jioUnitImportResult : successUnits) {
            entiyInfo = this.findEntityInfo(importContext, jioUnitImportResult.getUnitKey(), queryCodeEntitys);
            if (entiyInfo != null || queryCodeSet.contains(jioUnitImportResult.getUnitKey())) continue;
            queryCodes.add(jioUnitImportResult.getUnitKey());
            queryCodeSet.add(jioUnitImportResult.getUnitKey());
        }
        for (JIOUnitImportResult jioUnitImportResult : errorUnits) {
            entiyInfo = this.findEntityInfo(importContext, jioUnitImportResult.getUnitKey(), queryCodeEntitys);
            if (entiyInfo != null || queryCodeSet.contains(jioUnitImportResult.getUnitKey())) continue;
            queryCodes.add(jioUnitImportResult.getUnitKey());
            queryCodeSet.add(jioUnitImportResult.getUnitKey());
        }
        if (queryCodes.size() > 0) {
            List queryRs;
            String dwId = importContext.getDwEntityId();
            DsContext dsContext = DsContextHolder.getDsContext();
            String contextId = dsContext.getContextEntityId();
            if (StringUtils.isNotEmpty((String)contextId)) {
                dwId = contextId;
            }
            DimensionValueSet masterSet = new DimensionValueSet();
            masterSet.setValue(importContext.getEntityCompanyType(), queryCodes);
            if (StringUtils.isNotEmpty((String)importContext.getMapNetPeriodCode())) {
                masterSet.setValue(importContext.getEntityDateType(), (Object)importContext.getMapNetPeriodCode());
            }
            if ((queryRs = this.mapEntityUtil.queryEntityDataRowsByDims(dwId, masterSet)) != null && !queryRs.isEmpty()) {
                for (DataEntityInfo entiyInfo2 : queryRs) {
                    queryCodeEntitys.put(entiyInfo2.getEntityKey(), entiyInfo2);
                }
            }
        }
        return queryCodeEntitys;
    }

    private void resetUnitInfo(TaskDataContext importContext, JIOUnitImportResult jioUnitImportResult, Map<String, String> unitTitleMap, Map<String, String> unitCodeMap, Map<String, DataEntityInfo> queryCodeEntitys) {
        String comPanyName = "";
        String comPanyCode = "";
        DataEntityInfo entiyInfo = this.findEntityInfo(importContext, jioUnitImportResult.getUnitKey(), queryCodeEntitys);
        if (entiyInfo != null) {
            comPanyCode = entiyInfo.getEntityCode();
            comPanyName = entiyInfo.getEntityTitle();
        }
        if (StringUtils.isNotEmpty((String)comPanyName)) {
            jioUnitImportResult.setUnitCode(comPanyCode);
        } else {
            comPanyName = "\u672a\u67e5\u5230\u5355\u4f4d\u540d\u79f0\uff08" + jioUnitImportResult.getUnitKey() + ")";
            jioUnitImportResult.setUnitCode(comPanyName);
        }
        unitTitleMap.put(jioUnitImportResult.getUnitKey(), comPanyName);
        unitCodeMap.put(jioUnitImportResult.getUnitKey(), jioUnitImportResult.getUnitCode());
        jioUnitImportResult.setUnitTitle(comPanyName);
    }

    private void resetUnitInfo2(TaskDataContext importContext, JIOUnitImportResult jioUnitImportResult, Map<String, String> unitTitleMap, Map<String, String> unitCodeMap, Map<String, DataEntityInfo> queryCodeEntitys) {
        String comPanyName = "";
        String comPanyCode = "";
        DataEntityInfo entiyInfo = this.findEntityInfo(importContext, jioUnitImportResult.getUnitKey(), queryCodeEntitys);
        if (entiyInfo != null) {
            comPanyCode = entiyInfo.getEntityCode();
            comPanyName = entiyInfo.getEntityTitle();
        }
        if (StringUtils.isNotEmpty((String)comPanyCode)) {
            jioUnitImportResult.setUnitCode(comPanyCode);
        }
        jioUnitImportResult.setUnitTitle(comPanyName);
    }

    private DataEntityInfo findEntityInfo(TaskDataContext importContext, String findUnitKey, Map<String, DataEntityInfo> queryCodeEntitys) {
        DataEntityInfo entiyInfo = null;
        if (StringUtils.isNotEmpty((String)findUnitKey)) {
            entiyInfo = importContext.getEntityCache().findEntityByKey(findUnitKey);
            if (importContext.getEntityFieldIsCode() && entiyInfo == null) {
                entiyInfo = importContext.getEntityCache().findEntityByCode(findUnitKey);
            }
            if (entiyInfo == null && queryCodeEntitys != null && !queryCodeEntitys.isEmpty() && queryCodeEntitys.containsKey(findUnitKey)) {
                entiyInfo = queryCodeEntitys.get(findUnitKey);
            }
        }
        return entiyInfo;
    }

    @Override
    public void addSuccessForm(Map<String, JIOUnitImportResult> successUnits, Map<String, JIOUnitImportResult> errorUnits, FormDefine formDefine, String netEntityKey) {
        Map formResults;
        JIOFormImportResult formImportResult;
        Map formMapResults;
        JIOFormImportResult errorFormResult;
        JIOUnitImportResult errorUnitImportResult = errorUnits.get(netEntityKey);
        if (null != errorUnitImportResult && null != (errorFormResult = (JIOFormImportResult)(formMapResults = errorUnitImportResult.getFormMapResults()).get(formDefine.getKey()))) {
            return;
        }
        JIOUnitImportResult successUnitImportResult = successUnits.get(netEntityKey);
        if (null == successUnitImportResult) {
            successUnitImportResult = new JIOUnitImportResult();
            successUnitImportResult.setUnitKey(netEntityKey);
            successUnits.put(netEntityKey, successUnitImportResult);
        }
        if (null == (formImportResult = (JIOFormImportResult)(formResults = successUnitImportResult.getFormMapResults()).get(formDefine.getKey()))) {
            formImportResult = new JIOFormImportResult();
            formImportResult.setFormCode(formDefine.getFormCode());
            formImportResult.setFormKey(formDefine.getKey());
            formImportResult.setFormTitle(formDefine.getTitle());
            formResults.put(formDefine.getKey(), formImportResult);
        }
    }

    @Override
    public String handException(Exception e) {
        String message;
        if (e instanceof JTableException) {
            JTableException exception = (JTableException)e;
            Object[] datas = exception.getDatas();
            if (null != datas && datas.length > 0) {
                message = Arrays.toString(datas);
                if (null != message && (message.contains("ORA-01438") || message.contains("ORA-12899") || message.contains("1406") || message.contains("1264"))) {
                    message = "\u6570\u503c\u8d85\u957f";
                }
            } else {
                message = exception.getMessage() + " " + exception.getErrorCode();
            }
        } else {
            message = e.getMessage();
            if (null != message && (message.contains("ORA-01438") || message.contains("ORA-12899") || message.contains("1406") || message.contains("1264"))) {
                message = "\u6570\u503c\u8d85\u957f";
            }
        }
        return message;
    }

    @Override
    public void addErrorForm(Map<String, JIOUnitImportResult> successUnits, Map<String, JIOUnitImportResult> errorUnits, FormDefine formDefine, String netEntityKey, FormReadWriteAccessData result, String message) {
        Map successformResults;
        JIOUnitImportResult successUnitImportResult;
        Map formResults;
        JIOFormImportResult errorForm;
        JIOUnitImportResult errorUnitImportResult = errorUnits.get(netEntityKey);
        if (null == errorUnitImportResult) {
            errorUnitImportResult = new JIOUnitImportResult();
            errorUnitImportResult.setUnitKey(netEntityKey);
            errorUnits.put(netEntityKey, errorUnitImportResult);
        }
        if (null == (errorForm = (JIOFormImportResult)(formResults = errorUnitImportResult.getFormMapResults()).get(formDefine.getKey()))) {
            errorForm = new JIOFormImportResult();
            errorForm.setFormCode(formDefine.getFormCode());
            errorForm.setFormKey(formDefine.getKey());
            errorForm.setFormTitle(formDefine.getTitle());
            if (null != result) {
                errorForm.setMessage(result.getOneFormKeyReason(formDefine.getKey()));
            } else {
                errorForm.setMessage(message);
            }
            formResults.put(formDefine.getKey(), errorForm);
        }
        if (null != (successUnitImportResult = successUnits.get(netEntityKey)) && (successformResults = successUnitImportResult.getFormMapResults()).containsKey(formDefine.getKey())) {
            successformResults.remove(formDefine.getKey());
        }
    }

    @Override
    public void addErrorForm(Map<String, JIOUnitImportResult> successUnits, Map<String, JIOUnitImportResult> errorUnits, FormDefine formDefine, String netEntityKey, String message) {
        this.addErrorForm(successUnits, errorUnits, formDefine, netEntityKey, null, message);
    }

    @Override
    public JIOUnitImportResult getErorrItem(TaskDataContext context, String netEntityKey, String errorMessage) {
        String unitCode = "";
        String unitTitle = "";
        DataEntityInfo entinty = context.getEntityCache().findEntityByKey(netEntityKey);
        if (entinty == null) {
            entinty = context.getEntityCache().findEntityByCode(netEntityKey);
        }
        if (entinty != null) {
            unitCode = entinty.getEntityCode();
            unitTitle = entinty.getEntityTitle();
        } else {
            unitCode = netEntityKey;
        }
        JIOUnitImportResult unitImportResult = new JIOUnitImportResult();
        unitImportResult.setUnitKey(netEntityKey);
        unitImportResult.setMessage(errorMessage);
        unitImportResult.setUnitCode(unitCode);
        unitImportResult.setUnitTitle(unitTitle);
        unitImportResult.setFormMapResults(new HashMap());
        return unitImportResult;
    }

    @Override
    public void recordImportFormsToLog(TaskDataContext context, JIOImportResultObject result) {
        String netFormKey;
        UnitReportLog unitReportLog = this.dataServiceLoggerFactory.getUnitReportLog();
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger(LOG_TITLE, OperLevel.USER_OPER);
        if (result.getSuccessUnits() != null) {
            for (JIOUnitImportResult unitInfo : result.getSuccessUnits()) {
                if (unitInfo.getFormResults() != null) {
                    for (JIOFormImportResult formInfo : unitInfo.getFormResults()) {
                        netFormKey = formInfo.getFormKey();
                        if (StringUtils.isEmpty((String)netFormKey) && FMDM_FORMCODE.equalsIgnoreCase(formInfo.getFormCode()) && StringUtils.isNotEmpty((String)context.getFmdmFormKey())) {
                            netFormKey = context.getFmdmFormKey();
                        }
                        unitReportLog.addFormToUnit(unitInfo.getUnitCode(), netFormKey);
                    }
                }
                if (unitInfo.getFormMapResults() == null) continue;
                for (JIOFormImportResult formInfo : unitInfo.getFormMapResults().values()) {
                    netFormKey = formInfo.getFormKey();
                    if (StringUtils.isEmpty((String)netFormKey) && FMDM_FORMCODE.equalsIgnoreCase(formInfo.getFormCode()) && StringUtils.isNotEmpty((String)context.getFmdmFormKey())) {
                        netFormKey = context.getFmdmFormKey();
                    }
                    unitReportLog.addFormToUnit(unitInfo.getUnitCode(), netFormKey);
                }
            }
        }
        if (result.getFails() != null) {
            for (JIOUnitImportResult unitInfo : result.getErrorUnits()) {
                if (unitInfo.getFormResults() != null) {
                    for (JIOFormImportResult formInfo : unitInfo.getFormResults()) {
                        netFormKey = formInfo.getFormKey();
                        if (StringUtils.isEmpty((String)netFormKey) && FMDM_FORMCODE.equalsIgnoreCase(formInfo.getFormCode()) && StringUtils.isNotEmpty((String)context.getFmdmFormKey())) {
                            netFormKey = context.getFmdmFormKey();
                        }
                        unitReportLog.addFormToUnit(unitInfo.getUnitCode(), netFormKey);
                    }
                }
                if (unitInfo.getFormMapResults() == null) continue;
                for (JIOFormImportResult formInfo : unitInfo.getFormMapResults().values()) {
                    netFormKey = formInfo.getFormKey();
                    if (StringUtils.isEmpty((String)netFormKey) && FMDM_FORMCODE.equalsIgnoreCase(formInfo.getFormCode()) && StringUtils.isNotEmpty((String)context.getFmdmFormKey())) {
                        netFormKey = context.getFmdmFormKey();
                    }
                    unitReportLog.addFormToUnit(unitInfo.getUnitCode(), netFormKey);
                }
            }
        }
        LogDimensionCollection dimensionCollection = null;
        if (context.getUploadEntityZdmKeyMap() != null && StringUtils.isNotEmpty((String)context.getMapNetPeriodCode())) {
            Object[] dwArr = new String[context.getUploadEntityZdmKeyMap().size()];
            int i = 0;
            for (String entityCode : context.getUploadEntityZdmKeyMap().values()) {
                dwArr[i] = entityCode;
                ++i;
            }
            Arrays.sort(dwArr);
            TaskDefine task = this.runtimeView.queryTaskDefine(context.getTaskKey());
            if (task != null) {
                dimensionCollection = new LogDimensionCollection();
                dimensionCollection.setPeriod(task.getDateTime(), context.getMapNetPeriodCode());
                dimensionCollection.setDw(context.getDwEntityId(), (String[])dwArr);
            }
        }
        logHelper.info(context.getTaskKey(), dimensionCollection, unitReportLog, "JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88", "JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88\uff0c\u5df2\u5bfc\u5165" + context.getImportedFormNum() + "\u4e2a\u8868\u5355");
    }
}

