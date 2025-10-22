/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckContext
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpUnitInfo
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.common.RelationTaskAndFormScheme
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.controller.IEntityCheckController
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.single.core.data.SingleEntityCheckUtil
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.para.ini.Ini
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  nr.single.client.service.entitycheck.ISingleExportEntityCheckService
 *  nr.single.map.configurations.bean.UnitCustomMapping
 *  nr.single.map.data.DataEntityInfo
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.exception.SingleDataException
 */
package com.jiuqi.nr.single.extension.entitycheck.internal;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckContext;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpUnitInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.RelationTaskAndFormScheme;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.controller.IEntityCheckController;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.single.core.data.SingleEntityCheckUtil;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.nr.single.extension.entitycheck.SingleQueryEntityCheckService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.client.service.entitycheck.ISingleExportEntityCheckService;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleExportEntityCheckServiceImpl
implements ISingleExportEntityCheckService {
    private static final Logger logger = LoggerFactory.getLogger(SingleExportEntityCheckServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityCheckController checkController;
    @Autowired
    private SingleQueryEntityCheckService queryEntityCheckService;

    public void exportEntityCheckResult(TaskDataContext context, String taskDataPath, Map<String, DimensionValue> dimensionSet, AsyncTaskMonitor asyncTaskMonitor) throws SingleDataException {
        block4: {
            try {
                String period = dimensionSet.get("DATATIME").getValue().toString();
                List<RelationTaskAndFormScheme> linkInfos = this.queryEntityCheckService.getRelationTaskToFromSchemes(context.getTaskKey(), context.getFormSchemeKey(), period);
                if (linkInfos == null || linkInfos.isEmpty()) break block4;
                List<RelationTaskAndFormScheme> list = this.getRelationTaskByPriorPeriod(linkInfos, period);
                if (list.isEmpty()) {
                    list.addAll(linkInfos);
                }
                for (RelationTaskAndFormScheme linkInfo : list) {
                    List<EntityCheckUpUnitInfo> unitInfos = this.loadFromNr(context, taskDataPath, dimensionSet, asyncTaskMonitor, linkInfo);
                    if (unitInfos == null || unitInfos.isEmpty()) continue;
                    logger.info("\u6237\u6570\u6838\u5bf9\u5bfc\u51fa\uff1a\u8bb0\u5f55\u6570=" + unitInfos.size() + ",\u6765\u81ea\u5173\u8054\u62a5\u8868\u65b9\u6848\uff1a" + linkInfo.getFormSchemeKey() + "," + linkInfo.getFormSchemenTitle() + "," + linkInfo.getPeriod());
                    context.setHasEntityCheck(true);
                    String jshdFileName = taskDataPath + "SYS_SBJSHDB.DBF";
                    FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(linkInfo.getFormSchemeKey());
                    this.setSingleLastTaskFlag(taskDataPath, formSchemeDefine.getTaskPrefix());
                    SingleEntityCheckUtil.CreateSBCheckDBFFile((boolean)true, (String)taskDataPath, (int)context.getMapingCache().getZdmLength());
                    this.saveEntityCheckInfos(context, jshdFileName, unitInfos);
                    break;
                }
            }
            catch (Exception e) {
                logger.error("jio\u5bfc\u51fa\u6237\u6570\u6838\u5bf9\u5931\u8d25\uff1a" + e.getMessage(), e);
            }
        }
    }

    public List<RelationTaskAndFormScheme> getRelationTaskByPriorPeriod(List<RelationTaskAndFormScheme> list, String period) throws SingleDataException {
        ArrayList<RelationTaskAndFormScheme> list2 = new ArrayList<RelationTaskAndFormScheme>();
        if (!list.isEmpty()) {
            DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
            PeriodWrapper periodWrapper = new PeriodWrapper(period);
            periodAdapter.priorPeriod(periodWrapper);
            String lastPeriod = periodWrapper.toString();
            for (RelationTaskAndFormScheme relationObj : list) {
                if (!StringUtils.isNotEmpty((String)lastPeriod) || !lastPeriod.equalsIgnoreCase(relationObj.getPeriod())) continue;
                list2.add(relationObj);
            }
        }
        return list2;
    }

    private List<EntityCheckUpUnitInfo> loadFromNr(TaskDataContext context, String taskDataPath, Map<String, DimensionValue> dimensionSet, AsyncTaskMonitor asyncTaskMonitor, RelationTaskAndFormScheme linkInfo) {
        EntityCheckContext entityCheckContext = new EntityCheckContext();
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(context.getFormSchemeKey());
        jtableContext.setDimensionSet(dimensionSet);
        jtableContext.setTaskKey(context.getTaskKey());
        entityCheckContext.setContext(jtableContext);
        try {
            entityCheckContext.setAssociatedFormSchemeKey(linkInfo.getFormSchemeKey());
            entityCheckContext.setAssociatedperiod(linkInfo.getPeriod());
            return this.checkController.queryEntityCheckUp(entityCheckContext);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private void setSingleLastTaskFlag(String taskDataPath, String singleTaskFlag) {
        String file = taskDataPath + "SYS_Sign.DAT";
        Ini ini = null;
        try {
            ini = new Ini();
            ini.loadIniFile(file);
            ini.WriteString("HSHD", "LastTaskFlag", singleTaskFlag);
            ini.saveIni(file);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveEntityCheckInfos(TaskDataContext context, String jshdFileName, List<EntityCheckUpUnitInfo> unitInfos) throws SingleDataException, SingleFileException {
        if (SinglePathUtil.getFileExists((String)jshdFileName)) {
            logger.info("\u5bfc\u51faJIO\u6570\u636e\uff1a\u5bfc\u51fa\u6237\u6570\u6838\u5bf9\uff0c\u51cf\u5c11\u539f\u56e0");
            try {
                Map<String, UnitCustomMapping> unitMapByCodes = this.getUnitMapByCodes(context);
                if (!unitMapByCodes.isEmpty()) {
                    logger.info("\u5bfc\u51faJIO\u6570\u636e\uff1a\u5bfc\u51fa\u6237\u6570\u6838\u5bf9\uff0c\u4e2a\u6027\u6620\u5c04\u5355\u4f4d\u6570\uff1a" + unitMapByCodes.size());
                }
                try (IDbfTable checkDbf = DbfTableUtil.getDbfTable((String)jshdFileName);){
                    for (int i = 0; i < unitInfos.size(); ++i) {
                        EntityCheckUpUnitInfo unitInfo = unitInfos.get(i);
                        DataRow dbfRow = checkDbf.getTable().newRow();
                        String zdm = this.getSingleZdmByUnitInfo(context, unitInfo, unitMapByCodes);
                        dbfRow.setValue("QYDM".toUpperCase(), (Object)zdm);
                        dbfRow.setValue("QYMC".toUpperCase(), (Object)unitInfo.getDwmc());
                        dbfRow.setValue("JSYY".toUpperCase(), (Object)unitInfo.getZjys());
                        checkDbf.getTable().getRows().add((Object)dbfRow);
                    }
                    checkDbf.saveData();
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new SingleDataException(e.getMessage(), (Throwable)e);
            }
        }
    }

    private Map<String, UnitCustomMapping> getUnitMapByCodes(TaskDataContext context) {
        HashMap<String, UnitCustomMapping> unitMapByCodes = new HashMap<String, UnitCustomMapping>();
        if (context.getMapingCache().getMapConfig().getMapping() != null && context.getMapingCache().getMapConfig().getMapping().getUnitInfos() != null) {
            List unitList = context.getMapingCache().getMapConfig().getMapping().getUnitInfos();
            for (UnitCustomMapping unit : unitList) {
                unitMapByCodes.put(unit.getNetUnitKey(), unit);
            }
        }
        return unitMapByCodes;
    }

    private String getSingleZdmByUnitInfo(TaskDataContext context, EntityCheckUpUnitInfo unitInfo, Map<String, UnitCustomMapping> unitMapByCodes) {
        UnitCustomMapping unit;
        String zdmKey;
        String zdm = zdmKey = unitInfo.getDwzdm();
        DataEntityInfo entity = context.getEntityCache().findEntityByCode(zdmKey);
        if (null == entity) {
            entity = context.getEntityCache().findEntityByKey(zdmKey);
        }
        if (context.getEntityKeyZdmMap().containsKey(zdmKey)) {
            zdm = (String)context.getEntityKeyZdmMap().get(zdmKey);
        } else if (null != entity) {
            zdm = entity.getSingleZdm();
        } else if (!unitMapByCodes.isEmpty() && unitMapByCodes.containsKey(zdmKey) && (unit = unitMapByCodes.get(zdmKey)) != null && StringUtils.isNotEmpty((String)unit.getSingleUnitCode())) {
            zdm = unit.getSingleUnitCode();
        }
        return zdm;
    }
}

