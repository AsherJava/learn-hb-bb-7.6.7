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
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.para.ini.Ini
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  nr.single.client.service.entitycheck.ISingleImportEntityCheckService
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
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.nr.single.extension.entitycheck.SingleQueryEntityCheckService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nr.single.client.service.entitycheck.ISingleImportEntityCheckService;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleImportEntityCheckServiceImpl
implements ISingleImportEntityCheckService {
    private static final Logger logger = LoggerFactory.getLogger(SingleImportEntityCheckServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityCheckController checkController;
    @Autowired
    private SingleQueryEntityCheckService queryEntityCheckService;

    public void importEntityCheckResult(TaskDataContext context, String taskDataPath, Map<String, DimensionValue> dimensionSet, AsyncTaskMonitor asyncTaskMonitor) throws SingleDataException {
        String jshdFileName = taskDataPath + "SYS_JSHDB.DBF";
        try {
            List<EntityCheckUpUnitInfo> unitInfos = this.loadEntityCheckInfos(context, jshdFileName);
            if (unitInfos.isEmpty()) {
                jshdFileName = taskDataPath + "SYS_SBJSHDB.DBF";
                unitInfos = this.loadEntityCheckInfos(context, jshdFileName);
            }
            if (!unitInfos.isEmpty()) {
                this.savetoNr(context, taskDataPath, dimensionSet, asyncTaskMonitor, unitInfos);
            }
        }
        catch (SingleFileException e) {
            logger.error(e.getMessage(), e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
    }

    private void savetoNr(TaskDataContext context, String taskDataPath, Map<String, DimensionValue> dimensionSet, AsyncTaskMonitor asyncTaskMonitor, List<EntityCheckUpUnitInfo> unitInfos) {
        String singleTaskFlag = this.getSingleLastTaskFlag(taskDataPath);
        if (StringUtils.isEmpty((String)singleTaskFlag)) {
            return;
        }
        EntityCheckContext entityCheckContext = new EntityCheckContext();
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(context.getFormSchemeKey());
        jtableContext.setDimensionSet(dimensionSet);
        jtableContext.setTaskKey(context.getTaskKey());
        entityCheckContext.setContext(jtableContext);
        entityCheckContext.setAssociatedFormSchemeKey(null);
        entityCheckContext.setAssociatedperiod(null);
        entityCheckContext.setUnitInfo(unitInfos);
        String period = ((DimensionValue)entityCheckContext.getContext().getDimensionSet().get("DATATIME")).getValue().toString();
        try {
            logger.info("\u6237\u6570\u6838\u5bf9\u5bfc\u5165\uff1a\u5339\u914d\u65f6\u671f=" + period + ",\u5355\u673a\u7248\u5173\u8054\u4efb\u52a1\u6807\u8bc6=" + singleTaskFlag);
            RelationTaskAndFormScheme linkInfo = this.getRelationTaskToFromScheme(context.getTaskKey(), context.getFormSchemeKey(), period, singleTaskFlag);
            if (linkInfo != null) {
                String periodStr = context.getNetPeriodCode() + "|" + linkInfo.getPeriod();
                entityCheckContext.setAssociatedFormSchemeKey(linkInfo.getFormSchemeKey());
                entityCheckContext.setAssociatedperiod(linkInfo.getPeriod());
                this.checkController.insertEntityCheckUp(entityCheckContext);
                logger.info("\u6237\u6570\u6838\u5bf9\u5bfc\u5165\uff1a\u8bb0\u5f55\u6570=" + unitInfos.size() + ",\u65f6\u671f=" + periodStr + ",\u5173\u8054\u65f6\u671f\uff1a" + linkInfo.getPeriod() + ",\u5173\u8054\u65b9\u6848\uff1a" + linkInfo.getFormSchemeKey() + "," + linkInfo.getFormSchemenTitle());
            } else {
                logger.info("\u6237\u6570\u6838\u5bf9\u5bfc\u5165\uff1a\u5339\u914d\u5931\u8d25" + singleTaskFlag);
            }
        }
        catch (Exception e) {
            logger.error("jio\u5bfc\u5165\u6237\u6570\u6838\u5bf9\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }

    public RelationTaskAndFormScheme getRelationTaskToFromScheme(String taskKey, String formSchemeKey, String period, String singleTaskFlag) throws SingleDataException {
        List<RelationTaskAndFormScheme> list = this.queryEntityCheckService.getRelationTaskToFromSchemes(taskKey, formSchemeKey, period);
        if (!list.isEmpty()) {
            for (RelationTaskAndFormScheme relationObj : list) {
                FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(relationObj.getFormSchemeKey());
                if (formSchemeDefine == null || StringUtils.isNotEmpty((String)singleTaskFlag) && !singleTaskFlag.equalsIgnoreCase(formSchemeDefine.getTaskPrefix())) continue;
                logger.info("\u6237\u6570\u6838\u5bf9\u5bfc\u5165\uff1a\u6709\u5bf9\u5e94" + singleTaskFlag + ",\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848\uff1a" + relationObj.getFormSchemenTitle() + ",\u65f6\u671f\uff1a" + relationObj.getPeriod());
                return relationObj;
            }
            DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
            PeriodWrapper periodWrapper = new PeriodWrapper(period);
            periodAdapter.priorPeriod(periodWrapper);
            String lastPeriod = periodWrapper.toString();
            for (RelationTaskAndFormScheme relationObj : list) {
                if (StringUtils.isNotEmpty((String)lastPeriod) && !lastPeriod.equalsIgnoreCase(relationObj.getPeriod())) continue;
                logger.info("\u6237\u6570\u6838\u5bf9\u5bfc\u5165\uff1a\u65e0\u5bf9\u5e94" + singleTaskFlag + ",\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848\uff1a" + relationObj.getFormSchemenTitle() + ",\u65f6\u671f\uff1a" + relationObj.getPeriod());
                return relationObj;
            }
            return list.get(0);
        }
        return null;
    }

    private String getSingleLastTaskFlag(String taskDataPath) {
        String file = taskDataPath + "SYS_Sign.DAT";
        Ini ini = null;
        try {
            ini = new Ini();
            ini.loadIniFile(file);
            return ini.ReadString("HSHD", "LastTaskFlag", "");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "";
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<EntityCheckUpUnitInfo> loadEntityCheckInfos(TaskDataContext context, String jshdFileName) throws SingleDataException, SingleFileException {
        ArrayList<EntityCheckUpUnitInfo> unitInfos = new ArrayList<EntityCheckUpUnitInfo>();
        if (SinglePathUtil.getFileExists((String)jshdFileName)) {
            logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a\u5bfc\u5165\u6237\u6570\u6838\u5bf9\uff0c\u51cf\u5c11\u539f\u56e0");
            try (IDbfTable checkDbf = DbfTableUtil.getDbfTable((String)jshdFileName);){
                for (int i = 0; i < checkDbf.getDataRowCount(); ++i) {
                    DataRow dbfRow = (DataRow)checkDbf.getTable().getRows().get(i);
                    if (!checkDbf.isHasLoadAllRec()) {
                        checkDbf.loadDataRow(dbfRow);
                    }
                    try {
                        String zdm = dbfRow.getValueString("QYDM");
                        String qymc = dbfRow.getValueString("QYMC");
                        String jsyy = dbfRow.getValueString("JSYY");
                        String zdmKey = this.getNetUnitCode(context, zdm);
                        EntityCheckUpUnitInfo unitInfo = new EntityCheckUpUnitInfo();
                        unitInfo.setDwmc(qymc);
                        unitInfo.setDwzdm(zdmKey);
                        unitInfo.setZjys(jsyy);
                        unitInfos.add(unitInfo);
                        continue;
                    }
                    finally {
                        if (!checkDbf.isHasLoadAllRec()) {
                            checkDbf.clearDataRow(dbfRow);
                        }
                    }
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new SingleDataException(e.getMessage(), (Throwable)e);
            }
        }
        return unitInfos;
    }

    private String getNetUnitCode(TaskDataContext context, String zdm) {
        String zdmKey = zdm;
        if (context.getUploadEntityZdmKeyMap().containsKey(zdm)) {
            zdmKey = (String)context.getUploadEntityZdmKeyMap().get(zdm);
        } else if (context.isNeedChangeUpper() && StringUtils.isNotEmpty((String)zdm)) {
            zdmKey = zdm.toUpperCase();
        }
        return zdmKey;
    }
}

