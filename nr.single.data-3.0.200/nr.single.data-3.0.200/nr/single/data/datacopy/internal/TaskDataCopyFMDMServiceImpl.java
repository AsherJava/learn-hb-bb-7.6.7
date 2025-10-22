/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.BatchFMDMDTO
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.IFMDMUpdateResult
 *  com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo
 *  com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo
 *  com.jiuqi.nr.jtable.service.IFormulaCheckDesService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  javax.annotation.Resource
 */
package nr.single.data.datacopy.internal;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.BatchFMDMDTO;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.IFMDMUpdateResult;
import com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo;
import com.jiuqi.nr.jtable.service.IFormulaCheckDesService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import nr.single.data.bean.TaskCopyContext;
import nr.single.data.datacopy.ITaskDataCopyFMDMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskDataCopyFMDMServiceImpl
implements ITaskDataCopyFMDMService {
    private static final Logger logger = LoggerFactory.getLogger(TaskDataCopyFMDMServiceImpl.class);
    @Autowired
    private IEntityMetaService entityMetaService;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private IFMDMDataService fmdmDataService;
    @Autowired
    private IFMDMAttributeService fmdmAttributeService;
    @Resource
    private IFormulaCheckDesService formulaCheckService;

    @Override
    public String copyFMDMData(TaskCopyContext context, String formSchemeKey, String periodCode, String oldformScheme, String oldPeriod, AsyncTaskMonitor monitor) throws Exception {
        String errorInfo = "";
        FMDMDataDTO queryParam = this.getQueryParam(formSchemeKey, periodCode);
        List queryRes = this.fmdmDataService.list(queryParam);
        HashMap<String, IFMDMData> dataMap = new HashMap<String, IFMDMData>();
        if (queryRes != null && queryRes.size() > 0) {
            for (IFMDMData data : queryRes) {
                dataMap.put(data.getValue("CODE").getAsString(), data);
            }
        }
        FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeKey);
        TaskDefine task = this.runTimeAuthViewController.queryTaskDefine(formScheme.getTaskKey());
        String entityId = this.getUnitEntityId(task.getDataScheme());
        String entityDwType = EntityUtils.getId((String)entityId);
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        fmdmAttributeDTO.setEntityId(entityId);
        fmdmAttributeDTO.setFormSchemeKey(formSchemeKey);
        List fieldList = this.fmdmAttributeService.list(fmdmAttributeDTO);
        FormSchemeDefine oldformSchemeDefine = this.runTimeAuthViewController.getFormScheme(oldformScheme);
        TaskDefine oldTask = this.runTimeAuthViewController.queryTaskDefine(oldformSchemeDefine.getTaskKey());
        FMDMDataDTO oldQueryParam = this.getQueryParam(oldformScheme, oldPeriod);
        List oldQueryRes = this.fmdmDataService.list(oldQueryParam);
        HashMap<String, IFMDMData> oldDataMap = new HashMap<String, IFMDMData>();
        if (oldQueryRes != null && oldQueryRes.size() > 0) {
            CheckNodeInfo error;
            FMDMCheckFailNodeInfo nodeerror;
            for (IFMDMData data : oldQueryRes) {
                String entityCode = data.getValue("CODE").getAsString();
                oldDataMap.put(entityCode, data);
            }
            FMDMAttributeDTO oldFmdmAttributeDTO = new FMDMAttributeDTO();
            oldFmdmAttributeDTO.setEntityId(this.getUnitEntityId(oldTask.getDataScheme()));
            oldFmdmAttributeDTO.setFormSchemeKey(oldformSchemeDefine.getKey());
            List oldFieldList = this.fmdmAttributeService.list(oldFmdmAttributeDTO);
            HashMap<String, IFMDMAttribute> oldFieldMap = new HashMap<String, IFMDMAttribute>();
            for (IFMDMAttribute fieldAttr : oldFieldList) {
                oldFieldMap.put(fieldAttr.getCode(), fieldAttr);
            }
            DimensionValueSet batchDimensionValueSet = new DimensionValueSet();
            batchDimensionValueSet.setValue("DATATIME", (Object)periodCode);
            BatchFMDMDTO addBatchOptDTO = new BatchFMDMDTO();
            BatchFMDMDTO updateBatchOptDTO = new BatchFMDMDTO();
            BatchFMDMDTO deleteBatchOptDTO = new BatchFMDMDTO();
            ArrayList addDataList = new ArrayList();
            ArrayList updateDataList = new ArrayList();
            ArrayList deleteDataList = new ArrayList();
            addBatchOptDTO.setFmdmList(addDataList);
            addBatchOptDTO.setFormSchemeKey(formSchemeKey);
            addBatchOptDTO.setDimensionValueSet(batchDimensionValueSet);
            updateBatchOptDTO.setFmdmList(updateDataList);
            updateBatchOptDTO.setFormSchemeKey(formSchemeKey);
            updateBatchOptDTO.setDimensionValueSet(batchDimensionValueSet);
            deleteBatchOptDTO.setFmdmList(deleteDataList);
            deleteBatchOptDTO.setFormSchemeKey(formSchemeKey);
            String[] str1 = new String[]{"ID", "VER", "CODE", "OBJECTCODE", "NAME", "SHORTNAME", "UNITCODE", "VALIDTIME", "INVALIDTIME", "STOPFLAG", "RECOVERYFLAG", "ORDINAL", "CREATEUSER", "CREATETIME", "PARENTS", "ICON"};
            List<String> systempChars = Arrays.asList(str1);
            for (int i = 0; i < oldQueryRes.size(); ++i) {
                DimensionValueSet rowDimensionValueSet;
                IFMDMData oldFmdmRow = (IFMDMData)oldQueryRes.get(i);
                String entityCode = oldFmdmRow.getValue("CODE").getAsString();
                String entityTitle = oldFmdmRow.getValue("NAME").getAsString();
                String parentCode = oldFmdmRow.getValue("PARENTCODE").getAsString();
                String shortName = oldFmdmRow.getValue("SHORTNAME").getAsString();
                context.getCopyUnitCodes().add(entityCode);
                FMDMDataDTO fmdmRow = new FMDMDataDTO();
                if (dataMap.containsKey(entityCode)) {
                    rowDimensionValueSet = new DimensionValueSet();
                    rowDimensionValueSet.setValue("DATATIME", (Object)periodCode);
                    rowDimensionValueSet.setValue(entityDwType, (Object)entityCode);
                    fmdmRow.setDimensionValueSet(rowDimensionValueSet);
                    updateBatchOptDTO.getFmdmList().add(fmdmRow);
                } else {
                    rowDimensionValueSet = new DimensionValueSet();
                    rowDimensionValueSet.setValue("DATATIME", (Object)periodCode);
                    fmdmRow.setDimensionValueSet(rowDimensionValueSet);
                    addBatchOptDTO.getFmdmList().add(fmdmRow);
                }
                fmdmRow.setValue("CODE", (Object)entityCode);
                fmdmRow.setValue("NAME", (Object)entityTitle);
                if (StringUtils.isNotEmpty((String)parentCode)) {
                    fmdmRow.setValue("PARENTCODE", (Object)parentCode);
                }
                if (StringUtils.isNotEmpty((String)shortName)) {
                    fmdmRow.setValue("SHORTNAME", (Object)shortName);
                }
                fmdmRow.setFormSchemeKey(formScheme.getKey());
                fmdmRow.setValue("ORDINAL", (Object)new BigDecimal(OrderGenerator.newOrderID()));
                for (IFMDMAttribute fieldAttr : fieldList) {
                    IFMDMAttribute oldFieldAttr;
                    String fieldValue = null;
                    if (systempChars.contains(fieldAttr.getCode().toUpperCase()) || !oldFieldMap.containsKey(fieldAttr.getCode()) || !StringUtils.isNotEmpty((String)(fieldValue = oldFmdmRow.getValue((oldFieldAttr = (IFMDMAttribute)oldFieldMap.get(fieldAttr.getCode())).getCode()).getAsString()))) continue;
                    if (fieldAttr.getColumnType() == ColumnModelType.STRING && fieldAttr.getPrecision() < fieldValue.length()) {
                        fieldValue = fieldValue.substring(0, fieldAttr.getPrecision());
                    }
                    fmdmRow.setValue(fieldAttr.getCode().toUpperCase(), (Object)fieldValue);
                }
            }
            if (addBatchOptDTO.getFmdmList().size() > 0) {
                try {
                    IFMDMUpdateResult addR = this.fmdmDataService.batchAddFMDM(addBatchOptDTO);
                    if (addR.getUpdateKeys() != null) {
                        logger.info("\u5b9e\u4f53\u6570\u636e\u65b0\u589e,\u6210\u529f\u4e2a\u6570\uff1a" + String.valueOf(addR.getUpdateKeys().size()));
                    }
                    if (addR != null && addR.getFMDMCheckResult() != null && addR.getFMDMCheckResult().getResults() != null) {
                        logger.info("\u5b9e\u4f53\u6570\u636e\u65b0\u589e,\u9519\u8bef\u4e2a\u6570\uff1a" + String.valueOf(addR.getFMDMCheckResult().getResults().size()));
                        for (int i = 0; i < addR.getFMDMCheckResult().getResults().size(); ++i) {
                            nodeerror = (FMDMCheckFailNodeInfo)addR.getFMDMCheckResult().getResults().get(i);
                            logger.info("\u5b9e\u4f53\u6570\u636e\u65b0\u589e,\u5931\u8d25\u5b57\u6bb5\u4fe1\u606f\uff1a" + nodeerror.getFieldCode() + "," + nodeerror.getFieldTitle());
                            for (int j = 0; j < nodeerror.getNodes().size(); ++j) {
                                error = (CheckNodeInfo)nodeerror.getNodes().get(j);
                                logger.info("\u5b9e\u4f53\u6570\u636e\u65b0\u589e,\u5931\u8d25\u8be6\u7ec6\u4fe1\u606f\uff1a" + error.getType() + "," + error.getContent());
                                if (i > 100) break;
                            }
                            if (i > 40) break;
                        }
                    }
                    logger.info("\u590d\u5236\u5b9e\u4f53\u6570\u636e\u65b0\u589e\uff1a," + String.valueOf(addBatchOptDTO.getFmdmList().size()));
                }
                catch (Exception ex) {
                    errorInfo = ex.getMessage();
                    logger.error(ex.getMessage(), ex);
                }
            }
            if (updateBatchOptDTO.getFmdmList().size() > 0) {
                try {
                    IFMDMUpdateResult updateR = this.fmdmDataService.batchUpdateFMDM(updateBatchOptDTO);
                    if (updateR != null && updateR.getFMDMCheckResult() != null && updateR.getFMDMCheckResult().getResults() != null) {
                        logger.info("\u5b9e\u4f53\u6570\u636e\u66f4\u65b0,\u9519\u8bef\u4e2a\u6570\uff1a" + String.valueOf(updateR.getFMDMCheckResult().getResults().size()));
                        for (int i = 0; i < updateR.getFMDMCheckResult().getResults().size(); ++i) {
                            nodeerror = (FMDMCheckFailNodeInfo)updateR.getFMDMCheckResult().getResults().get(i);
                            logger.info("\u5b9e\u4f53\u6570\u636e\u66f4\u65b0,\u5931\u8d25\u5b57\u6bb5\u4fe1\u606f\uff1a" + nodeerror.getFieldCode() + "," + nodeerror.getFieldTitle());
                            for (int j = 0; j < nodeerror.getNodes().size(); ++j) {
                                error = (CheckNodeInfo)nodeerror.getNodes().get(j);
                                logger.info("\u5b9e\u4f53\u6570\u636e\u66f4\u65b0,\u5931\u8d25\u8be6\u7ec6\u4fe1\u606f\uff1a" + error.getType() + "," + error.getContent());
                                if (i > 100) break;
                            }
                            if (i > 40) break;
                        }
                    }
                    logger.info("\u590d\u5236\u5b9e\u4f53\u6570\u636e\u66f4\u65b0\uff1a," + String.valueOf(updateBatchOptDTO.getFmdmList().size()));
                }
                catch (Exception ex) {
                    errorInfo = ex.getMessage();
                    logger.error(ex.getMessage(), ex);
                }
            }
            if (deleteBatchOptDTO.getFmdmList().size() > 0) {
                try {
                    for (FMDMDataDTO fmdmData : deleteBatchOptDTO.getFmdmList()) {
                        this.fmdmDataService.delete(fmdmData);
                    }
                    logger.info("\u5b9e\u4f53\u6570\u636e\u5220\u9664\uff1a," + String.valueOf(deleteBatchOptDTO.getFmdmList().size()));
                }
                catch (Exception ex) {
                    errorInfo = ex.getMessage();
                    logger.error(ex.getMessage(), ex);
                }
            }
        }
        return errorInfo;
    }

    private FMDMDataDTO getQueryParam(String formSchemeKey, String netPeriodCode) throws Exception {
        FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeKey);
        TaskDefine task = this.runTimeAuthViewController.queryTaskDefine(formScheme.getTaskKey());
        FMDMDataDTO queryParam = null;
        if (formScheme != null && task != null) {
            String[] masterKeyArray;
            Object entityViewDefine = null;
            String entityID = this.getUnitEntityId(task.getDataScheme());
            String entityDwType = EntityUtils.getId((String)entityID);
            String masterKeys = formScheme.getMasterEntitiesKey();
            String entityId = task.getDw();
            if (StringUtils.isEmpty((String)entityId) && StringUtils.isNotEmpty((String)masterKeys) && (masterKeyArray = masterKeys.split(",")).length > 0) {
                entityId = masterKeyArray[0];
            }
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
            entityDwType = entityDefine.getDimensionName();
            queryParam = new FMDMDataDTO();
            queryParam.setFormSchemeKey(formScheme.getKey());
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue("DATATIME", (Object)netPeriodCode);
            queryParam.setDimensionValueSet(dimensionValueSet);
        }
        return queryParam;
    }

    private String getUnitEntityId(String dataSchemekey) {
        List dimList = this.dataSchemeSevice.getDataSchemeDimension(dataSchemekey);
        for (DataDimension dim : dimList) {
            if (dim.getDimensionType() != DimensionType.UNIT) continue;
            return dim.getDimKey();
        }
        return null;
    }
}

