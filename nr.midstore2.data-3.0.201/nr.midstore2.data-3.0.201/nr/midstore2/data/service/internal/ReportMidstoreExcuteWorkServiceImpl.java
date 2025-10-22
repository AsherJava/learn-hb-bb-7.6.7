/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.time.setting.de.FillDataType
 *  com.jiuqi.nr.time.setting.de.FillTimeDataSetting
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreParam
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFailInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo
 *  com.jiuqi.nvwa.midstore.core.definition.common.ExchangeModeType
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeInfoDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSourceDTO
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreOrgDataService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeInfoService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSourceService
 *  com.jiuqi.nvwa.midstore.core.internal.definition.MidstoreOrgDataDO
 *  com.jiuqi.nvwa.midstore.core.monitor.MidstoreProgressMonitor
 *  com.jiuqi.nvwa.midstore.work.service.IMidstoreExcuteGetService
 *  com.jiuqi.nvwa.midstore.work.service.IMidstoreExcutePostService
 */
package nr.midstore2.data.service.internal;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.time.setting.de.FillDataType;
import com.jiuqi.nr.time.setting.de.FillTimeDataSetting;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreParam;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFailInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo;
import com.jiuqi.nvwa.midstore.core.definition.common.ExchangeModeType;
import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSourceDTO;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreOrgDataService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeInfoService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSourceService;
import com.jiuqi.nvwa.midstore.core.internal.definition.MidstoreOrgDataDO;
import com.jiuqi.nvwa.midstore.core.monitor.MidstoreProgressMonitor;
import com.jiuqi.nvwa.midstore.work.service.IMidstoreExcuteGetService;
import com.jiuqi.nvwa.midstore.work.service.IMidstoreExcutePostService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nr.midstore2.data.extension.bean.ReportDataSourceDTO;
import nr.midstore2.data.monitor.ReportMidstoreProgressMonitor;
import nr.midstore2.data.service.IReportMidstoreExcuteWorkService;
import nr.midstore2.data.util.IReportMidstoreDimensionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportMidstoreExcuteWorkServiceImpl
implements IReportMidstoreExcuteWorkService {
    private static final Logger logger = LoggerFactory.getLogger(ReportMidstoreExcuteWorkServiceImpl.class);
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreSchemeInfoService midstoreSchemeInfoSevice;
    @Autowired
    private IMidstoreExcutePostService postService;
    @Autowired
    private IMidstoreExcuteGetService getService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IMidstoreSourceService sourceService;
    @Autowired
    private IMidstoreOrgDataService orgDataService;
    @Autowired
    private IReportMidstoreDimensionService reportDimSerice;
    @Autowired
    private FillTimeDataSetting fillTimeDataSetting;

    @Override
    public List<MidstoreResultObject> excuteDataGetByCodes(String taskKey, String midstoreSchemeCode, Map<DimensionValueSet, List<String>> unitFormKeys, boolean isDeleteEmpty, AsyncTaskMonitor monitor) throws MidstoreException {
        this.info(monitor, "\u63d0\u53d6\u65b9\u6848CODE:" + midstoreSchemeCode);
        ArrayList<MidstoreResultObject> resultList = new ArrayList<MidstoreResultObject>();
        if (StringUtils.isEmpty((String)midstoreSchemeCode)) {
            this.info(monitor, "\u6570\u636e\u63d0\u53d6:\u4ea4\u6362\u65b9\u6848\u6807\u8bc6\u4e3a\u7a7a");
            resultList.add(new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u6807\u8bc6\u4e3a\u7a7a"));
            return resultList;
        }
        if (StringUtils.isEmpty((String)taskKey)) {
            this.info(monitor, "\u6570\u636e\u63d0\u53d6:\u4efb\u52a1Key\u4e3a\u7a7a");
            resultList.add(new MidstoreResultObject(false, "\u4efb\u52a1Key\u4e3a\u7a7a"));
            return resultList;
        }
        TaskDefine task = this.viewController.queryTaskDefine(taskKey);
        if (task == null) {
            this.info(monitor, "\u6570\u636e\u63d0\u53d6:\u4efb\u52a1\u4e0d\u5b58\u5728\uff1a" + taskKey);
            resultList.add(new MidstoreResultObject(false, "\u4efb\u52a1\u4e0d\u5b58\u5728\uff1a" + taskKey));
            return resultList;
        }
        if (StringUtils.isNotEmpty((String)midstoreSchemeCode)) {
            String[] subCodes = midstoreSchemeCode.split(",");
            ArrayList<MidstoreSchemeDTO> midstoreSchemes = new ArrayList<MidstoreSchemeDTO>();
            ArrayList<Object> midstoreSchemeCodes = new ArrayList<Object>();
            HashMap<Object, MidstoreSchemeDTO> midstoreSchemesMap = new HashMap<Object, MidstoreSchemeDTO>();
            String dateTimeDimName = null;
            String entityDimName = null;
            for (String subCode : subCodes) {
                MidstoreSchemeDTO scheme = this.midstoreSchemeSevice.getByCode(subCode);
                if (scheme == null) {
                    this.info(monitor, "\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728\uff0c" + (String)subCode);
                    resultList.add(new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728\uff0c" + (String)subCode));
                    continue;
                }
                if (scheme.getExchangeMode() != ExchangeModeType.EXCHANGE_GET) {
                    MidstoreResultObject subResult = new MidstoreResultObject(false, "\u672a\u914d\u7f6e\u53d6\u6570\u65b9\u6848\uff0c" + (String)subCode, null, scheme.getKey(), scheme.getCode(), scheme.getTitle());
                    resultList.add(subResult);
                    this.info(monitor, "\u672a\u914d\u7f6e\u53d6\u6570\u65b9\u6848\uff0c" + (String)subCode);
                    continue;
                }
                MidstoreSourceDTO oldSource = this.sourceService.getBySchemeAndSource(scheme.getKey(), "nr_midstore_field");
                if (oldSource == null) {
                    MidstoreResultObject subResult = new MidstoreResultObject(false, "\u672a\u914d\u7f6e\u65b9\u6848\u7684\u62a5\u8868\u6269\u5c55\u4fe1\u606f" + (String)subCode, null, scheme.getKey(), scheme.getCode(), scheme.getTitle());
                    resultList.add(subResult);
                    this.info(monitor, "\u672a\u914d\u7f6e\u65b9\u6848\u7684\u62a5\u8868\u6269\u5c55\u4fe1\u606f\uff0c" + scheme.getCode());
                    continue;
                }
                ReportDataSourceDTO reportSource = new ReportDataSourceDTO(oldSource);
                String curtaskKey = reportSource.getTaskKey();
                if (!taskKey.equalsIgnoreCase(curtaskKey)) {
                    MidstoreResultObject subResult = new MidstoreResultObject(false, "\u6240\u5173\u8054\u7684\u4efb\u52a1\u4e0e\u5f53\u524d\u4efb\u52a1\u4e0d\u4e00\u81f4\uff0c" + (String)subCode, null, scheme.getKey(), scheme.getCode(), scheme.getTitle());
                    resultList.add(subResult);
                    this.info(monitor, "\u6240\u5173\u8054\u7684\u4efb\u52a1\u4e0e\u5f53\u524d\u4efb\u52a1\u4e0d\u4e00\u81f4\uff0c" + scheme.getCode());
                    continue;
                }
                if (StringUtils.isEmpty(dateTimeDimName) && this.periodAdapter.isPeriodEntity(task.getDateTime())) {
                    IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(task.getDateTime());
                    dateTimeDimName = periodEntity.getDimensionName();
                }
                if (StringUtils.isEmpty(entityDimName) && StringUtils.isNotEmpty((String)task.getDw())) {
                    IEntityDefine entityDefine = this.entityMetaService.queryEntity(task.getDw());
                    entityDimName = entityDefine.getDimensionName();
                }
                midstoreSchemes.add(scheme);
                midstoreSchemeCodes.add(subCode);
                midstoreSchemesMap.put(subCode, scheme);
            }
            if (midstoreSchemes.isEmpty()) {
                this.info(monitor, "\u6570\u636e\u63d0\u53d6\uff0c\u4e0d\u5b58\u5728\u5339\u914d\u7684\u4e2d\u95f4\u65b9\u6848");
                return resultList;
            }
            if (unitFormKeys == null || unitFormKeys.isEmpty()) {
                for (MidstoreSchemeDTO scheme : midstoreSchemes) {
                    resultList.add(new MidstoreResultObject(false, "\u53c2\u6570\u6ca1\u6709\u8868\u5355\u4fe1\u606f", null, scheme.getKey(), scheme.getCode(), scheme.getTitle()));
                    this.info(monitor, "\u53c2\u6570\u6ca1\u6709\u8868\u5355\u4fe1\u606f\uff0c" + scheme.getCode());
                }
                return resultList;
            }
            ArrayList<String> oldOrgList = new ArrayList<String>();
            String dataTime = null;
            String deDataTime = null;
            for (DimensionValueSet unitDim : unitFormKeys.keySet()) {
                String unitCode = (String)unitDim.getValue(entityDimName);
                if (StringUtils.isEmpty(dataTime)) {
                    dataTime = (String)unitDim.getValue(dateTimeDimName);
                    deDataTime = this.reportDimSerice.getDePeriodFromNr(task.getDateTime(), dataTime);
                }
                oldOrgList.add(unitCode);
            }
            String nrPeriodTitle = this.reportDimSerice.getPeriodTitle(task.getDateTime(), dataTime);
            if (!FillDateType.NONE.equals((Object)task.getFillingDateType())) {
                Calendar instance = Calendar.getInstance();
                FillDataType fillTimeData = this.fillTimeDataSetting.fillTimeData(task.getKey(), dataTime, instance.getTime());
                if (!FillDataType.SUCCESS.equals((Object)fillTimeData)) {
                    for (MidstoreSchemeDTO scheme : midstoreSchemes) {
                        resultList.add(new MidstoreResultObject(false, fillTimeData.getMessage(), null, scheme.getKey(), scheme.getCode(), scheme.getTitle()));
                    }
                    this.info(monitor, "\u68c0\u67e5\u586b\u62a5\u671f:" + fillTimeData.getMessage() + "," + dataTime);
                    return resultList;
                }
            }
            Map<String, List<String>> midstoreMatchOrgs = null;
            midstoreMatchOrgs = this.preprocessOrgToMidstoreScheme2(taskKey, dataTime, deDataTime, midstoreSchemes, oldOrgList, resultList, monitor);
            if (midstoreMatchOrgs == null || midstoreMatchOrgs.isEmpty()) {
                this.info(monitor, "\u6570\u636e\u63d0\u53d6\uff0c\u4e0d\u5b58\u5728\u5339\u914d\u7684\u4e2d\u95f4\u65b9\u6848\uff0c\u65e0\u5bf9\u5e94\u7684\u7ec4\u7ec7\u673a\u6784\u5355\u4f4d");
                return resultList;
            }
            if (monitor != null) {
                monitor.progressAndMessage(0.1, "\u63a5\u6536\u6570\u636e\u5f00\u59cb");
            }
            double curProcess = 0.1;
            for (String schemeCode : midstoreMatchOrgs.keySet()) {
                MidstoreResultObject result = new MidstoreResultObject(true, "");
                if (!midstoreSchemesMap.containsKey(schemeCode)) {
                    this.info(monitor, "\u4ea4\u6362\u65b9\u6848\u6ca1\u6709\u5728\u81ea\u52a8\u5339\u914d\u7ed3\u679c\u4e2d\uff0c" + schemeCode);
                    continue;
                }
                MidstoreSchemeDTO scheme = (MidstoreSchemeDTO)midstoreSchemesMap.get(schemeCode);
                List<String> orgList = midstoreMatchOrgs.get(scheme.getCode());
                if (orgList == null || orgList.isEmpty()) {
                    this.info(monitor, "\u4ea4\u6362\u65b9\u6848\u6ca1\u6709\u5339\u914d\u5bf9\u5e94\u7684\u5355\u4f4d\uff0c" + scheme.getCode());
                    continue;
                }
                if (monitor != null) {
                    monitor.progressAndMessage(curProcess, "\u6570\u636e\u63a5\u6536\uff0c\u5f00\u59cb\u4e2d\u95f4\u5e93\u65b9\u6848\uff1a" + scheme.getTitle());
                }
                HashSet<String> orgSet = new HashSet<String>(orgList);
                HashMap<DimensionValueSet, List<String>> subUnitFormKeys = new HashMap<DimensionValueSet, List<String>>();
                for (DimensionValueSet dimensionValueSet : unitFormKeys.keySet()) {
                    String unitCode = (String)dimensionValueSet.getValue(entityDimName);
                    if (!orgSet.contains(unitCode)) continue;
                    subUnitFormKeys.put(dimensionValueSet, unitFormKeys.get(dimensionValueSet));
                }
                MidstoreResultObject subResult = this.excuteDataGetByScheme(scheme, deDataTime, orgList, subUnitFormKeys, isDeleteEmpty, monitor);
                if (subResult == null) {
                    this.info(monitor, "\u6570\u636e\u63d0\u53d6\uff0c\u8fd4\u56de\u7684\u5bf9\u8c61\u4e3a\u7a7a" + scheme.getCode() + ",\u5355\u4f4d\u6570=" + orgList.size() + "," + deDataTime);
                } else {
                    this.info(monitor, "\u6570\u636e\u63d0\u53d6\uff0c\u8fd4\u56de" + subResult.isSuccess() + "," + subResult.getMessage() + "," + scheme.getCode() + ",\u5355\u4f4d\u6570=" + orgList.size() + "," + deDataTime);
                    if (subResult.getWorkResults().size() > 0) {
                        for (MistoreWorkResultObject wrokdResult : subResult.getWorkResults()) {
                            wrokdResult.setSchemeKey(scheme.getKey());
                            wrokdResult.setSchemeCode(scheme.getCode());
                            wrokdResult.setSchemeTitle(scheme.getTitle());
                            wrokdResult.setPeriodCode(dataTime);
                            wrokdResult.setPeriodTitle(nrPeriodTitle);
                            wrokdResult.setMidstorePeriodCode(deDataTime);
                            result.getWorkResults().add(wrokdResult);
                        }
                    }
                }
                result.setSchemeKey(scheme.getKey());
                result.setSchemeCode(scheme.getCode());
                result.setSchemeTitle(scheme.getTitle());
                resultList.add(result);
                BigDecimal bigDecimal = new BigDecimal((double)orgList.size() * 0.8 / (double)oldOrgList.size());
                double f1 = bigDecimal.setScale(2, 4).doubleValue();
                curProcess += f1;
                if (monitor == null) continue;
                monitor.progressAndMessage(curProcess, "\u6570\u636e\u63a5\u6536\uff0c\u5b8c\u6210\u4e2d\u95f4\u5e93\u65b9\u6848\uff1a" + scheme.getTitle());
                if (!monitor.isCancel()) continue;
                return resultList;
            }
            if (monitor != null) {
                monitor.progressAndMessage(0.9, "\u63a5\u6536\u6570\u636e\u7ed3\u675f");
            }
            return resultList;
        }
        if (resultList.isEmpty()) {
            this.info(monitor, "\u6570\u636e\u63d0\u53d6\uff0c\u4ea4\u6362\u65b9\u6848\u7684\u6a21\u5f0f\u672a\u77e5\uff0c" + midstoreSchemeCode);
            resultList.add(new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u7684\u6a21\u5f0f\u672a\u77e5\uff0c" + midstoreSchemeCode));
        }
        return resultList;
    }

    @Override
    public List<MidstoreResultObject> excuteDataPostByCodes(String taskKey, String midstoreSchemeCode, Map<DimensionValueSet, List<String>> unitFormKeys, AsyncTaskMonitor monitor) throws MidstoreException {
        ArrayList<MidstoreResultObject> resultList = new ArrayList<MidstoreResultObject>();
        if (StringUtils.isEmpty((String)midstoreSchemeCode)) {
            this.info(monitor, "\u6570\u636e\u63d0\u4f9b\uff0c\u4ea4\u6362\u65b9\u6848\u6807\u8bc6\u4e3a\u7a7a");
            resultList.add(new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u6807\u8bc6\u4e3a\u7a7a"));
            return resultList;
        }
        if (StringUtils.isEmpty((String)taskKey)) {
            this.info(monitor, "\u6570\u636e\u63d0\u4f9b\uff0c\u4efb\u52a1Key\u4e3a\u7a7a");
            resultList.add(new MidstoreResultObject(false, "\u4efb\u52a1Key\u4e3a\u7a7a"));
            return resultList;
        }
        TaskDefine task = this.viewController.queryTaskDefine(taskKey);
        if (task == null) {
            this.info(monitor, "\u6570\u636e\u63d0\u4f9b\uff0c\u4efb\u52a1\u4e0d\u5b58\u5728\uff1a" + taskKey);
            resultList.add(new MidstoreResultObject(false, "\u4efb\u52a1\u4e0d\u5b58\u5728\uff1a" + taskKey));
            return resultList;
        }
        if (StringUtils.isNotEmpty((String)midstoreSchemeCode)) {
            String[] subCodes = midstoreSchemeCode.split(",");
            ArrayList<MidstoreSchemeDTO> midstoreSchemes = new ArrayList<MidstoreSchemeDTO>();
            ArrayList<Object> midstoreSchemeCodes = new ArrayList<Object>();
            HashMap<Object, MidstoreSchemeDTO> midstoreSchemesMap = new HashMap<Object, MidstoreSchemeDTO>();
            String dateTimeDimName = null;
            String entityDimName = null;
            for (String subCode : subCodes) {
                MidstoreSchemeDTO scheme = this.midstoreSchemeSevice.getByCode(subCode);
                if (scheme == null) {
                    this.info(monitor, "\u6570\u636e\u63d0\u4f9b\uff0c\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728\uff0c" + (String)subCode);
                    resultList.add(new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728\uff0c" + (String)subCode));
                    continue;
                }
                if (scheme.getExchangeMode() != ExchangeModeType.EXCHANGE_POST) {
                    this.info(monitor, "\u6570\u636e\u63d0\u4f9b\uff0c\u672a\u914d\u7f6e\u63a8\u6570\u65b9\u6848\uff0c" + (String)subCode);
                    MidstoreResultObject subResult = new MidstoreResultObject(false, "\u672a\u914d\u7f6e\u63a8\u6570\u65b9\u6848\uff0c" + (String)subCode, null, scheme.getKey(), scheme.getCode(), scheme.getTitle());
                    resultList.add(subResult);
                    continue;
                }
                MidstoreSourceDTO oldSource = this.sourceService.getBySchemeAndSource(scheme.getKey(), "nr_midstore_field");
                if (oldSource == null) {
                    this.info(monitor, "\u6570\u636e\u63d0\u4f9b\uff0c\u672a\u914d\u7f6e\u65b9\u6848\u7684\u62a5\u8868\u6269\u5c55\u4fe1\u606f\uff0c" + (String)subCode);
                    MidstoreResultObject subResult = new MidstoreResultObject(false, "\u672a\u914d\u7f6e\u65b9\u6848\u7684\u62a5\u8868\u6269\u5c55\u4fe1\u606f\uff0c" + (String)subCode, null, scheme.getKey(), scheme.getCode(), scheme.getTitle());
                    resultList.add(subResult);
                    continue;
                }
                ReportDataSourceDTO reportSource = new ReportDataSourceDTO(oldSource);
                String curtaskKey = reportSource.getTaskKey();
                if (!taskKey.equalsIgnoreCase(curtaskKey)) {
                    this.info(monitor, "\u6570\u636e\u63d0\u4f9b\uff0c\u6240\u5173\u8054\u7684\u4efb\u52a1\u4e0e\u5f53\u524d\u4efb\u52a1\u4e0d\u4e00\u81f4\uff0c" + (String)subCode);
                    MidstoreResultObject subResult = new MidstoreResultObject(false, "\u6240\u5173\u8054\u7684\u4efb\u52a1\u4e0e\u5f53\u524d\u4efb\u52a1\u4e0d\u4e00\u81f4\uff0c" + (String)subCode, null, scheme.getKey(), scheme.getCode(), scheme.getTitle());
                    resultList.add(subResult);
                    continue;
                }
                if (StringUtils.isEmpty(dateTimeDimName) && this.periodAdapter.isPeriodEntity(task.getDateTime())) {
                    IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(task.getDateTime());
                    dateTimeDimName = periodEntity.getDimensionName();
                }
                if (StringUtils.isEmpty(entityDimName) && StringUtils.isNotEmpty((String)task.getDw())) {
                    IEntityDefine entityDefine = this.entityMetaService.queryEntity(task.getDw());
                    entityDimName = entityDefine.getDimensionName();
                }
                midstoreSchemes.add(scheme);
                midstoreSchemeCodes.add(subCode);
                midstoreSchemesMap.put(subCode, scheme);
            }
            if (midstoreSchemes.isEmpty()) {
                return resultList;
            }
            if (unitFormKeys == null || unitFormKeys.isEmpty()) {
                for (MidstoreSchemeDTO scheme : midstoreSchemes) {
                    this.info(monitor, "\u6570\u636e\u63d0\u4f9b\uff0c\u53c2\u6570\u6ca1\u6709\u8868\u5355\u4fe1\u606f");
                    resultList.add(new MidstoreResultObject(false, "\u53c2\u6570\u6ca1\u6709\u8868\u5355\u4fe1\u606f", null, scheme.getKey(), scheme.getCode(), scheme.getTitle()));
                }
                return resultList;
            }
            ArrayList<String> oldOrgList = new ArrayList<String>();
            String dataTime = null;
            String deDataTime = null;
            for (DimensionValueSet unitDim : unitFormKeys.keySet()) {
                String unitCode = (String)unitDim.getValue(entityDimName);
                if (StringUtils.isEmpty(dataTime)) {
                    dataTime = (String)unitDim.getValue(dateTimeDimName);
                    deDataTime = this.reportDimSerice.getDePeriodFromNr(task.getDateTime(), dataTime);
                }
                oldOrgList.add(unitCode);
            }
            String nrPeriodTitle = this.reportDimSerice.getPeriodTitle(task.getDateTime(), dataTime);
            Map<String, List<String>> midstoreMatchOrgs = null;
            midstoreMatchOrgs = this.preprocessOrgToMidstoreScheme2(taskKey, dataTime, deDataTime, midstoreSchemes, oldOrgList, resultList, monitor);
            if (midstoreMatchOrgs == null || midstoreMatchOrgs.isEmpty()) {
                return resultList;
            }
            if (!midstoreMatchOrgs.isEmpty()) {
                if (monitor != null) {
                    monitor.progressAndMessage(0.1, "\u63d0\u4f9b\u6570\u636e\u5f00\u59cb");
                }
                double curProcess = 0.1;
                for (String schemeCode : midstoreMatchOrgs.keySet()) {
                    MidstoreResultObject result = new MidstoreResultObject(true, "");
                    if (!midstoreSchemesMap.containsKey(schemeCode)) {
                        this.info(monitor, "\u4ea4\u6362\u65b9\u6848\u6ca1\u6709\u5728\u81ea\u52a8\u5339\u914d\u7ed3\u679c\u4e2d\uff0c" + schemeCode);
                        continue;
                    }
                    MidstoreSchemeDTO scheme = (MidstoreSchemeDTO)midstoreSchemesMap.get(schemeCode);
                    List<String> orgList = midstoreMatchOrgs.get(scheme.getCode());
                    if (orgList == null || orgList.isEmpty()) {
                        this.info(monitor, "\u4ea4\u6362\u65b9\u6848\u6ca1\u6709\u5339\u914d\u5bf9\u5e94\u7684\u5355\u4f4d\uff0c" + scheme.getCode());
                        continue;
                    }
                    if (monitor != null) {
                        monitor.progressAndMessage(curProcess, "\u6570\u636e\u63d0\u4f9b\uff0c\u5f00\u59cb\u4e2d\u95f4\u5e93\u65b9\u6848\uff1a" + scheme.getTitle());
                    }
                    HashSet<String> orgSet = new HashSet<String>(orgList);
                    HashMap<DimensionValueSet, List<String>> subUnitFormKeys = new HashMap<DimensionValueSet, List<String>>();
                    for (DimensionValueSet dimensionValueSet : unitFormKeys.keySet()) {
                        String unitCode = (String)dimensionValueSet.getValue(entityDimName);
                        if (!orgSet.contains(unitCode)) continue;
                        subUnitFormKeys.put(dimensionValueSet, unitFormKeys.get(dimensionValueSet));
                    }
                    MidstoreResultObject subResult = this.excuteDataPostByScheme(scheme, deDataTime, orgList, subUnitFormKeys, false, monitor);
                    if (subResult == null) {
                        this.info(monitor, "\u6570\u636e\u63d0\u53d6\uff0c\u8fd4\u56de\u7684\u5bf9\u8c61\u4f4d\u7a7a" + scheme.getCode() + ",\u5355\u4f4d\u6570=" + orgList.size() + "," + deDataTime);
                    } else {
                        this.info(monitor, "\u6570\u636e\u63d0\u53d6\uff0c\u8fd4\u56de" + subResult.isSuccess() + "," + subResult.getMessage() + "," + scheme.getCode() + ",\u5355\u4f4d\u6570=" + orgList.size() + "," + deDataTime);
                    }
                    if (subResult != null && subResult.getWorkResults().size() > 0) {
                        for (MistoreWorkResultObject wrokdResult : subResult.getWorkResults()) {
                            wrokdResult.setSchemeKey(scheme.getKey());
                            wrokdResult.setSchemeCode(scheme.getCode());
                            wrokdResult.setSchemeTitle(scheme.getTitle());
                            wrokdResult.setPeriodCode(dataTime);
                            wrokdResult.setMidstorePeriodCode(deDataTime);
                            wrokdResult.setPeriodTitle(nrPeriodTitle);
                            result.getWorkResults().add(wrokdResult);
                        }
                    }
                    result.setSchemeKey(scheme.getKey());
                    result.setSchemeCode(scheme.getCode());
                    result.setSchemeTitle(scheme.getTitle());
                    resultList.add(result);
                    BigDecimal bigDecimal = new BigDecimal((double)orgList.size() * 0.8 / (double)oldOrgList.size());
                    double f1 = bigDecimal.setScale(2, 4).doubleValue();
                    curProcess += f1;
                    if (monitor == null) continue;
                    monitor.progressAndMessage(curProcess, "\u6570\u636e\u63d0\u4f9b\uff0c\u5b8c\u6210\u4e2d\u95f4\u5e93\u65b9\u6848\uff1a" + scheme.getTitle());
                    if (!monitor.isCancel()) continue;
                    return resultList;
                }
                if (monitor != null) {
                    monitor.progressAndMessage(0.9, "\u63d0\u4f9b\u6570\u636e\u7ed3\u675f");
                }
                return resultList;
            }
        }
        if (resultList.isEmpty()) {
            resultList.add(new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u7684\u6a21\u5f0f\u672a\u77e5\uff0c" + midstoreSchemeCode));
        }
        return resultList;
    }

    private MidstoreResultObject excuteDataGetByScheme(MidstoreSchemeDTO scheme, String deDataTime, List<String> orgList, Map<DimensionValueSet, List<String>> unitFormKeys, boolean isDeleteEmpty, AsyncTaskMonitor monitor) throws MidstoreException {
        ReportMidstoreProgressMonitor monitor2 = new ReportMidstoreProgressMonitor(monitor);
        MidstoreParam param = new MidstoreParam();
        if (orgList != null && !orgList.isEmpty()) {
            param.getExcuteParams().put("OrgData", String.join((CharSequence)",", orgList));
        }
        if (StringUtils.isNotEmpty((String)deDataTime)) {
            param.getExcuteParams().put("DataTime", deDataTime);
        }
        if (isDeleteEmpty) {
            param.getExcuteParams().put("EXCUTE_DELETEEMPTY", "TRUE");
        } else {
            param.getExcuteParams().put("EXCUTE_DELETEEMPTY", "FALSE");
        }
        if (unitFormKeys != null && !unitFormKeys.isEmpty()) {
            param.getExcuteParamObjects().put("UNITFORMKEYS", unitFormKeys);
        }
        param.getExcuteParams().put("EXCUTE_SOURCE", "EXCUTE_SOURCE_NR_INPUT");
        param.setMidstoreSchemeId(scheme.getKey());
        MidstoreResultObject result = this.getService.excuteGetData(param, (MidstoreProgressMonitor)monitor2);
        return result;
    }

    private MidstoreResultObject excuteDataPostByScheme(MidstoreSchemeDTO scheme, String deDataTime, List<String> orgList, Map<DimensionValueSet, List<String>> unitFormKeys, boolean isDeleteOther, AsyncTaskMonitor monitor) throws MidstoreException {
        ReportMidstoreProgressMonitor monitor2 = new ReportMidstoreProgressMonitor(monitor);
        MidstoreParam param = new MidstoreParam();
        if (orgList != null && !orgList.isEmpty()) {
            param.getExcuteParams().put("OrgData", String.join((CharSequence)",", orgList));
        }
        if (StringUtils.isNotEmpty((String)deDataTime)) {
            param.getExcuteParams().put("DataTime", deDataTime);
        }
        if (isDeleteOther) {
            param.getExcuteParams().put("DeleteOther", "TRUE");
        } else {
            param.getExcuteParams().put("DeleteOther", "FALSE");
        }
        if (unitFormKeys != null && !unitFormKeys.isEmpty()) {
            param.getExcuteParamObjects().put("UNITFORMKEYS", unitFormKeys);
        }
        param.getExcuteParams().put("DeleteByOrgCode", "TRUE");
        param.getExcuteParams().put("EXCUTE_SOURCE", "EXCUTE_SOURCE_NR_INPUT");
        param.setMidstoreSchemeId(scheme.getKey());
        MidstoreResultObject result = this.postService.excutePostData(param, (MidstoreProgressMonitor)monitor2);
        return result;
    }

    private Map<String, List<String>> preprocessOrgToMidstoreScheme2(String taskKey, String nrDataTime, String deDataTime, List<MidstoreSchemeDTO> midstoreSchemes, List<String> oldOrgList, List<MidstoreResultObject> resultList, AsyncTaskMonitor monitor) {
        HashMap<String, List<String>> resultMap = new HashMap<String, List<String>>();
        for (MidstoreSchemeDTO midScheme : midstoreSchemes) {
            MidstoreSchemeInfoDTO schemeInfo = this.midstoreSchemeInfoSevice.getBySchemeKey(midScheme.getKey());
            MidstoreResultObject result = new MidstoreResultObject(true, "");
            if (!schemeInfo.isAllPeriod()) {
                if (StringUtils.isNotEmpty((String)schemeInfo.getFromPeriod()) && schemeInfo.getFromPeriod().compareTo(deDataTime) > 0) {
                    this.info(monitor, "\u4ea4\u6362\u65b9\u6848\u65f6\u671f\u8303\u56f4\u4e0d\u6ee1\u8db3\uff1a" + midScheme.getCode() + ",\u5f00\u59cb\u65f6\u671f:" + schemeInfo.getFromPeriod() + ",\u4ea4\u6362\u65f6\u671f\uff1a" + deDataTime);
                    this.recordErrorInfo("\u4ea4\u6362\u65b9\u6848\u65f6\u671f\u8303\u56f4\u4e0d\u6ee1\u8db3", nrDataTime, deDataTime, oldOrgList, result, midScheme);
                    resultList.add(result);
                    continue;
                }
                if (StringUtils.isNotEmpty((String)schemeInfo.getToPeriod()) && schemeInfo.getToPeriod().compareTo(deDataTime) < 0) {
                    this.info(monitor, "\u4ea4\u6362\u65b9\u6848\u65f6\u671f\u8303\u56f4\u4e0d\u6ee1\u8db3\uff1a" + midScheme.getCode() + ",\u7ed3\u675f\u65f6\u671f:" + schemeInfo.getToPeriod() + ",\u4ea4\u6362\u65f6\u671f\uff1a" + deDataTime);
                    this.recordErrorInfo("\u4ea4\u6362\u65b9\u6848\u65f6\u671f\u8303\u56f4\u4e0d\u6ee1\u8db3", nrDataTime, deDataTime, oldOrgList, result, midScheme);
                    resultList.add(result);
                    continue;
                }
            }
            ArrayList<String> newOrgList = new ArrayList<String>();
            if (!schemeInfo.isAllOrgData()) {
                MidstoreOrgDataDTO queryParam = new MidstoreOrgDataDTO();
                queryParam.setSchemeKey(midScheme.getKey());
                List midOrgDatas = this.orgDataService.list(queryParam);
                Map<String, MidstoreOrgDataDTO> orgDataCodeDic = midOrgDatas.stream().collect(Collectors.toMap(MidstoreOrgDataDO::getCode, MidstoreOrgDataDTO2 -> MidstoreOrgDataDTO2));
                ArrayList<String> inorgeList = new ArrayList<String>();
                for (String orgCode : oldOrgList) {
                    if (orgDataCodeDic.containsKey(orgCode)) {
                        newOrgList.add(orgCode);
                        continue;
                    }
                    inorgeList.add(orgCode);
                }
                if (newOrgList.isEmpty()) {
                    this.info(monitor, "\u4ea4\u6362\u65b9\u6848\u7ec4\u7ec7\u673a\u6784\u8303\u56f4\u4e0d\u6ee1\u8db3\uff1a" + midScheme.getCode());
                    result.setSchemeKey(midScheme.getKey());
                    result.setSchemeCode(midScheme.getCode());
                    result.setSchemeTitle(midScheme.getTitle());
                    result.setSuccess(false);
                    result.setMessage("\u4ea4\u6362\u65b9\u6848\u7ec4\u7ec7\u673a\u6784\u8303\u56f4\u4e0d\u6ee1\u8db3");
                    resultList.add(result);
                    continue;
                }
                if (!inorgeList.isEmpty()) {
                    this.info(monitor, "\u4ea4\u6362\u65b9\u6848\u7ec4\u7ec7\u673a\u6784\u8303\u56f4\u90e8\u5206\u4e0d\u6ee1\u8db3\uff1a" + midScheme.getCode() + ",\u5355\u4f4d\u4e2a\u6570\uff1a" + inorgeList.size() + "");
                }
            } else {
                newOrgList.addAll(oldOrgList);
            }
            resultMap.put(midScheme.getCode(), newOrgList);
        }
        return resultMap;
    }

    private void recordErrorInfo(String failMsg, String nrDataTime, String deDataTime, List<String> oldOrgList, MidstoreResultObject result, MidstoreSchemeDTO midScheme) {
        MistoreWorkResultObject wrokdResult = new MistoreWorkResultObject();
        wrokdResult.setSuccess(false);
        wrokdResult.setMessage(failMsg);
        wrokdResult.setPeriodCode(nrDataTime);
        wrokdResult.setPeriodCode(nrDataTime);
        wrokdResult.setMidstorePeriodCode(deDataTime);
        wrokdResult.setSchemeKey(midScheme.getKey());
        wrokdResult.setSchemeCode(midScheme.getCode());
        wrokdResult.setSchemeTitle(midScheme.getTitle());
        wrokdResult.setUnitCount(oldOrgList.size());
        MistoreWorkFailInfo failInfo = new MistoreWorkFailInfo();
        failInfo.setMessage(failMsg);
        for (String orgCode : oldOrgList) {
            MistoreWorkUnitInfo unitInfo = new MistoreWorkUnitInfo();
            unitInfo.setUnitCode(orgCode);
            unitInfo.setSuccess(false);
            failInfo.getUnitInfos().put(unitInfo.getUnitCode(), unitInfo);
        }
        wrokdResult.addFailInfo(failInfo);
        result.getWorkResults().add(wrokdResult);
        result.setSuccess(true);
        result.setSchemeKey(midScheme.getKey());
        result.setSchemeCode(midScheme.getCode());
        result.setSchemeTitle(midScheme.getTitle());
    }

    private void info(AsyncTaskMonitor monitor, String msg) {
        if (monitor != null && monitor.getBILogger() != null) {
            monitor.getBILogger().info(msg);
        }
    }
}

