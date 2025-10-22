/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.GCAdjTypeEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.intermediatelibrary.dto.DataDockingBlockDTO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingBlockVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingDataRowVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingFormVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingQueryVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingTaskDataVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingTaskVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.facade.UniversalFieldDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.intermediatelibrary.task;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.GCAdjTypeEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.intermediatelibrary.dao.IntermediateLibraryDao;
import com.jiuqi.gcreport.intermediatelibrary.dto.DataDockingBlockDTO;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILOrgEntity;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateProgrammeService;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingBlockVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingDataRowVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingFormVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingQueryVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingTaskDataVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingTaskVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.facade.UniversalFieldDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataDockingProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DataDockingProcessor.class);
    private static final String LOGGER_PREFIX = "\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-";
    private DataDockingQueryVO dataDockingQueryVO;
    private TaskDefine taskDefine;
    private String dataTime;
    private ILVO ilvo;
    private Map<String, FieldDefine> fieldDefineMap = new HashMap<String, FieldDefine>();
    private Map<String, List<DataDockingBlockDTO>> needQueryFormBlockMap;
    private Set<String> needOrgCodeSet;
    private SchemePeriodLinkDefine schemePeriodLinkDefine;
    private DimensionValueSet dimensionValueSet;
    private List<DataDockingDataRowVO> allDataDockingDataRowVOS = new ArrayList<DataDockingDataRowVO>();
    private DataDockingVO dataDockingVO;
    private IRunTimeViewController iRunTimeViewController;
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    private IntermediateProgrammeService intermediateProgrammeService;
    private IntermediateLibraryDao intermediateLibraryDao;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IEntityMetaService entityMetaService;
    private IDataAccessProvider dataAccessProvider;

    private DataDockingProcessor() {
    }

    public static DataDockingProcessor newInstance(DataDockingQueryVO dataDockingQueryVO) {
        DataDockingProcessor dataDockingProcessor = new DataDockingProcessor();
        dataDockingProcessor.initBean();
        dataDockingProcessor.initParam(dataDockingQueryVO);
        return dataDockingProcessor;
    }

    private void initBean() {
        this.iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        this.iRuntimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        this.intermediateProgrammeService = (IntermediateProgrammeService)SpringContextUtils.getBean(IntermediateProgrammeService.class);
        this.intermediateLibraryDao = (IntermediateLibraryDao)SpringContextUtils.getBean(IntermediateLibraryDao.class);
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        this.entityMetaService = (IEntityMetaService)SpringContextUtils.getBean(IEntityMetaService.class);
        this.dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
    }

    private void initParam(DataDockingQueryVO dataDockingQueryVO) {
        SchemePeriodLinkDefine schemePeriodLinkDefine;
        String dataTime;
        this.dataDockingQueryVO = dataDockingQueryVO;
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefineByCode(dataDockingQueryVO.getTaskCode());
        if (Objects.isNull(taskDefine)) {
            LogHelper.info((String)LOGGER_PREFIX, (String)("\u83b7\u53d6\u6570\u636e[\u4efb\u52a1\u5b9a\u4e49]\u4e3a\u7a7a\uff0c[taskCode]:" + dataDockingQueryVO.getTaskCode()));
            throw new BusinessRuntimeException("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u83b7\u53d6\u6570\u636e[\u4efb\u52a1\u5b9a\u4e49]\u4e3a\u7a7a\uff0c[taskCode]:" + dataDockingQueryVO.getTaskCode());
        }
        this.taskDefine = taskDefine;
        this.dataTime = dataTime = this.getPeriodStr(dataDockingQueryVO.getYear(), dataDockingQueryVO.getPeriod(), taskDefine.getDataScheme());
        try {
            schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(dataTime, taskDefine.getKey());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u6570\u636e[\u62a5\u8868\u65b9\u6848]\u4e3a\u7a7a\uff1a [taskKey]:" + taskDefine.getKey() + " [dataTime]:" + dataTime);
        }
        this.schemePeriodLinkDefine = schemePeriodLinkDefine;
        this.ilvo = this.getILVO(taskDefine, dataDockingQueryVO);
    }

    private String getPeriodStr(int year, int period, String dataSchemeKey) {
        List dataDimensions = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.PERIOD);
        PeriodType periodType = ((DataDimension)dataDimensions.get(0)).getPeriodType();
        YearPeriodDO periodStr = YearPeriodUtil.transform(null, (int)year, (int)periodType.type(), (int)period);
        return periodStr.toString();
    }

    private ILVO getILVO(TaskDefine taskDefine, DataDockingQueryVO dataDockingQueryVO) {
        Optional<ILVO> ilvoOptional;
        List<ILVO> allProgramme = this.intermediateProgrammeService.getAllProgramme();
        if (!StringUtils.isEmpty((String)dataDockingQueryVO.getSysCode())) {
            String sysCode = dataDockingQueryVO.getSysCode();
            ilvoOptional = allProgramme.stream().filter(ilvo -> ilvo.getProgrammeName().equals(sysCode)).findFirst();
            if (!ilvoOptional.isPresent()) {
                LogHelper.info((String)LOGGER_PREFIX, (String)("\u672a\u627e\u5230\u6307\u5b9a\u7684\u4e2d\u95f4\u5e93\u65b9\u6848\uff0c[\u4e2d\u95f4\u5e93\u65b9\u6848\u540d\u79f0]:" + sysCode));
                throw new BusinessRuntimeException("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u672a\u627e\u5230\u6307\u5b9a\u7684\u4e2d\u95f4\u5e93\u65b9\u6848\uff0c[\u4e2d\u95f4\u5e93\u65b9\u6848\u540d\u79f0]:" + sysCode);
            }
        } else {
            ilvoOptional = allProgramme.stream().filter(ilvo -> ilvo.getTaskId().contains(taskDefine.getKey())).findFirst();
            if (!ilvoOptional.isPresent()) {
                LogHelper.info((String)LOGGER_PREFIX, (String)("\u672a\u627e\u5230\u4e0e\u62a5\u8868\u4efb\u52a1\u5173\u8054\u7684\u4e2d\u95f4\u5e93\u65b9\u6848\uff0c[taskCode]:" + taskDefine.getTaskCode()));
                throw new BusinessRuntimeException("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u672a\u627e\u5230\u4e0e\u62a5\u8868\u4efb\u52a1\u5173\u8054\u7684\u4e2d\u95f4\u5e93\u65b9\u6848\uff0c[taskCode]:" + taskDefine.getTaskCode());
            }
        }
        logger.info("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u4e2d\u95f4\u5e93\u65b9\u6848\uff1a" + ilvoOptional.get().getProgrammeName());
        return ilvoOptional.get();
    }

    private void buildFormBlock() {
        List<ILFieldVO> fieldVoList = this.intermediateLibraryDao.getFieldOfProgrammeId(this.ilvo.getId());
        List programmeFileIdList = fieldVoList.stream().map(ILFieldVO::getFieldId).collect(Collectors.toList());
        if (Boolean.TRUE.equals(this.dataDockingQueryVO.getFormAllSelect())) {
            List formDefines = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(this.schemePeriodLinkDefine.getSchemeKey());
            this.dataDockingQueryVO.setForms(formDefines.stream().map(FormDefine::getFormCode).collect(Collectors.toList()));
        }
        HashMap<String, List<DataDockingBlockDTO>> formCode2RegionMap = new HashMap<String, List<DataDockingBlockDTO>>();
        for (String formCode : this.dataDockingQueryVO.getForms()) {
            FormDefine formDefine;
            try {
                formDefine = this.iRunTimeViewController.queryFormByCodeInScheme(this.schemePeriodLinkDefine.getSchemeKey(), formCode);
            }
            catch (Exception e) {
                LogHelper.info((String)LOGGER_PREFIX, (String)("\u62a5\u8868\u65b9\u6848\u4e2d\u8868\u5355\u4e0d\u5b58\u5728\uff0c[formCode]:" + formCode));
                throw new BusinessRuntimeException("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u62a5\u8868\u65b9\u6848\u4e2d\u8868\u5355\u4e0d\u5b58\u5728\uff0c[formCode]:" + formCode);
            }
            List dataRegionDefines = this.iRunTimeViewController.getAllRegionsInForm(formDefine.getKey());
            for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
                List<DataDockingBlockDTO> dataDockingBlockDTOS;
                List fieldIdList = this.iRunTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
                HashSet fieldIdSet = new HashSet(fieldIdList);
                List accountIdList = programmeFileIdList.stream().filter(fieldIdSet::contains).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(accountIdList)) continue;
                ArrayList<String> fieldCodes = new ArrayList<String>(accountIdList.size());
                for (String fieldId : accountIdList) {
                    try {
                        FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldId);
                        TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                        fieldCodes.add(tableDefine.getCode() + "[" + fieldDefine.getCode() + "]");
                        this.fieldDefineMap.put(tableDefine.getCode() + "[" + fieldDefine.getCode() + "]", fieldDefine);
                    }
                    catch (Exception e) {
                        LogHelper.info((String)LOGGER_PREFIX, (String)("\u67e5\u8be2\u7269\u7406\u8868\u5931\u8d25\uff0c[\u6307\u6807id]:" + fieldId));
                        logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u67e5\u8be2\u7269\u7406\u8868\u5931\u8d25\uff0c[\u6307\u6807id]:" + fieldId);
                    }
                }
                if (CollectionUtils.isEmpty((Collection)((Collection)formCode2RegionMap.get(formDefine.getFormCode())))) {
                    dataDockingBlockDTOS = new ArrayList();
                    formCode2RegionMap.put(formDefine.getFormCode(), dataDockingBlockDTOS);
                }
                dataDockingBlockDTOS = (List)formCode2RegionMap.get(formDefine.getFormCode());
                DataDockingBlockDTO dataDockingBlockDTO = new DataDockingBlockDTO();
                DataDockingBlockVO dataDockingBlockVO = new DataDockingBlockVO();
                dataDockingBlockVO.setFloatBlock(Boolean.valueOf(!DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind())));
                dataDockingBlockVO.setFieldCodes(fieldCodes);
                dataDockingBlockDTO.setDataDockingBlockVO(dataDockingBlockVO);
                dataDockingBlockDTO.setDataRegionDefine(dataRegionDefine);
                dataDockingBlockDTO.setFormDefine(formDefine);
                dataDockingBlockDTOS.add(dataDockingBlockDTO);
            }
        }
        logger.info("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u67e5\u8be2\u7684\u8868\u5355\uff1a" + JsonUtils.writeValueAsString(formCode2RegionMap.keySet()));
        this.needQueryFormBlockMap = formCode2RegionMap;
    }

    private void buildQueryOrgCodes() {
        String orgCategory = DimensionUtils.getDwEntitieTableByTaskKey((String)this.taskDefine.getKey());
        List<ILOrgEntity> iLOrgEntityList = this.intermediateLibraryDao.getAllOrgIdForProgrammeId(this.ilvo.getId());
        Set ilOrgIdList = iLOrgEntityList.stream().map(ILOrgEntity::getOrgId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(ilOrgIdList)) {
            LogHelper.info((String)LOGGER_PREFIX, (String)("\u4e2d\u95f4\u5e93\u672a\u914d\u7f6e\u5355\u4f4d\uff1a [\u65b9\u6848\u540d\u79f0]:" + this.ilvo.getProgrammeName()));
            throw new BusinessRuntimeException("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u4e2d\u95f4\u5e93\u672a\u914d\u7f6e\u5355\u4f4d\uff1a [\u65b9\u6848\u540d\u79f0]:" + this.ilvo.getProgrammeName());
        }
        HashSet<String> orgIds = new HashSet<String>();
        if (Boolean.TRUE.equals(this.dataDockingQueryVO.getUnitAllSelect())) {
            YearPeriodObject yp = new YearPeriodObject(this.schemePeriodLinkDefine.getSchemeKey(), this.dataTime);
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)Objects.requireNonNull(orgCategory), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            List gcOrgCacheVOS = tool.listOrgBySearch(null);
            orgIds.addAll(gcOrgCacheVOS.stream().map(GcOrgCacheVO::getCode).filter(ilOrgIdList::contains).collect(Collectors.toSet()));
        } else {
            orgIds.addAll(this.dataDockingQueryVO.getUnitCodes().stream().filter(ilOrgIdList::contains).collect(Collectors.toSet()));
        }
        logger.info("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u67e5\u8be2\u7684\u5355\u4f4d\uff1a" + JsonUtils.writeValueAsString(orgIds));
        this.needOrgCodeSet = orgIds;
    }

    private void buildDimensionValueSet() {
        Map dimension = this.dataDockingQueryVO.getDimension();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("MD_ORG", new ArrayList<String>(this.needOrgCodeSet));
        dimensionValueSet.setValue("DATATIME", (Object)this.dataTime);
        if (!StringUtils.isEmpty((String)this.taskDefine.getDims())) {
            String[] dimEntityIds;
            for (String dimEntityId : dimEntityIds = this.taskDefine.getDims().split(";")) {
                Object dimValue;
                TableModelDefine dimTableModelDefine = this.entityMetaService.getTableModel(dimEntityId);
                if (dimTableModelDefine == null || StringUtils.isEmpty((String)dimTableModelDefine.getName())) continue;
                String dimCode = dimTableModelDefine.getName();
                Object v0 = dimValue = Objects.isNull(dimension) || dimension.isEmpty() || Objects.isNull(dimension.get(dimCode)) ? null : dimension.get(dimCode);
                if (Objects.isNull(dimValue)) {
                    if ("MD_CURRENCY".equals(dimCode)) {
                        dimensionValueSet.setValue(dimCode, (Object)"CNY");
                        continue;
                    }
                    if ("MD_GCORGTYPE".equals(dimCode)) {
                        String mdCodeStr = new ArrayList<String>(this.needOrgCodeSet).get(0);
                        String orgType = this.getOrgTypeByOrgCode(mdCodeStr, this.taskDefine.getDw(), this.dataTime);
                        dimensionValueSet.setValue(dimCode, (Object)orgType);
                        continue;
                    }
                    if (!"MD_GCADJTYPE".equals(dimCode)) continue;
                    dimensionValueSet.setValue(dimCode, (Object)GCAdjTypeEnum.BEFOREADJ.getCode());
                    continue;
                }
                dimensionValueSet.setValue(dimCode, dimValue);
            }
        }
        if (DimensionUtils.isExistAdjust((String)this.taskDefine.getKey())) {
            if (Objects.isNull(dimension) || Objects.isNull(dimension.get("ADJUST"))) {
                dimensionValueSet.setValue("ADJUST", (Object)"0");
            } else {
                dimensionValueSet.setValue("ADJUST", dimension.get("ADJUST"));
            }
        }
        this.dimensionValueSet = dimensionValueSet;
    }

    private String getOrgTypeByOrgCode(String orgCode, String entityId, String dataTime) {
        YearPeriodObject yp;
        String orgTableName = this.entityMetaService.getTableModel(entityId).getName();
        GcOrgCacheVO cacheVO = GcOrgPublicTool.getInstance((String)orgTableName, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)(yp = new YearPeriodObject(null, dataTime))).getOrgByCode(orgCode);
        if (cacheVO != null && !StringUtils.isEmpty((String)cacheVO.getOrgTypeId())) {
            return cacheVO.getOrgTypeId();
        }
        throw new RuntimeException("\u83b7\u53d6\u6570\u636e\u5355\u4f4d\u4fe1\u606f\u4e3a\u7a7a\uff0c[unitCode]\u7684\u503c[" + orgCode + "]\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5355\u4f4d\u4fe1\u606f\uff0c[orgType]:[" + orgTableName + "]\uff0c[\u65f6\u671f]:[" + dataTime + "]\u3002");
    }

    private List<DataDockingDataRowVO> queryRowData(DataRegionDefine dataRegionDefine, List<FieldDefine> fieldDefines) {
        ArrayList<FieldDefine> withDimFieldDefines = new ArrayList<FieldDefine>(fieldDefines);
        HashMap<String, FieldDefine> dimFieldMap = new HashMap<String, FieldDefine>();
        FormDefine formDefine = this.iRunTimeViewController.queryFormById(dataRegionDefine.getFormKey());
        for (int i = 0; i < this.dimensionValueSet.size(); ++i) {
            String name;
            String finalName = name = this.dimensionValueSet.getName(i);
            if ("MD_ORG".equals(name)) {
                finalName = "MDCODE";
            }
            try {
                FieldDefine mdFieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(finalName, fieldDefines.get(0).getOwnerTableKey());
                dimFieldMap.put(name, mdFieldDefine);
                continue;
            }
            catch (Exception e) {
                logger.error("\u7b2c\u4e09\u65b9\u6570\u636e\u5bf9\u63a5-\u8868\u5355\uff1a" + formDefine.getFormCode() + "\u67e5\u8be2" + name + "\u6307\u6807\u5931\u8d25", e);
            }
        }
        withDimFieldDefines.addAll(dimFieldMap.values());
        IDataQuery dataQuery = this.getDataQuery(dataRegionDefine, withDimFieldDefines);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setUseDnaSql(false);
        ArrayList<DataDockingDataRowVO> dataDockingDataRowVOS = new ArrayList<DataDockingDataRowVO>();
        try {
            IDataTable iDataTable = dataQuery.executeQuery(context);
            int rowCount = iDataTable.getCount();
            for (int count = 0; count < rowCount; ++count) {
                ArrayList<String> fieldValues = new ArrayList<String>();
                IDataRow dataRow = iDataTable.getItem(count);
                for (FieldDefine fieldDefine : fieldDefines) {
                    fieldValues.add(dataRow.getValue(fieldDefine).getAsString());
                }
                DataDockingDataRowVO dataDockingDataRowVO = new DataDockingDataRowVO();
                dataDockingDataRowVO.setFieldValues(fieldValues);
                dataDockingDataRowVO.setDataRegionDefine(dataRegionDefine);
                dataDockingDataRowVO.setFormDefine(formDefine);
                dataDockingDataRowVO.setFieldCodes(fieldDefines.stream().map(UniversalFieldDefine::getCode).collect(Collectors.toList()));
                dataDockingDataRowVO.setRowNum(Integer.valueOf(count));
                HashMap dimension = new HashMap();
                dimFieldMap.forEach((dimName, fieldDef) -> dimension.put(dimName, dataRow.getValue(fieldDef).getAsString()));
                dataDockingDataRowVO.setDimension(dimension);
                dataDockingDataRowVOS.add(dataDockingDataRowVO);
            }
        }
        catch (Exception e) {
            LogHelper.info((String)LOGGER_PREFIX, (String)("\u8868\u5355\uff1a" + formDefine.getFormCode() + "\u5b58\u5728\u67e5\u8be2\u5931\u8d25\u7684\u533a\u57df"));
            throw new BusinessRuntimeException("\u8868\u5355\uff1a" + formDefine.getFormCode() + "\u5b58\u5728\u67e5\u8be2\u5931\u8d25\u7684\u533a\u57df", (Throwable)e);
        }
        return dataDockingDataRowVOS;
    }

    public IDataQuery getDataQuery(DataRegionDefine dataRegionDefine, List<FieldDefine> fieldDefines) {
        try {
            FormDefine formDefine = this.iRunTimeViewController.queryFormById(dataRegionDefine.getFormKey());
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            queryEnvironment.setFormSchemeKey(formDefine.getFormScheme());
            queryEnvironment.setRegionKey(dataRegionDefine.getKey());
            queryEnvironment.setFormKey(formDefine.getKey());
            queryEnvironment.setFormCode(formDefine.getFormCode());
            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
            dataQuery.setMasterKeys(this.dimensionValueSet);
            if (!CollectionUtils.isEmpty(fieldDefines)) {
                for (FieldDefine fieldDefine : fieldDefines) {
                    dataQuery.addColumn(fieldDefine);
                }
            }
            return dataQuery;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException("\u6839\u636e\u67e5\u8be2\u73af\u5883\u83b7\u53d6\u67e5\u8be2\u63a5\u53e3\u5b9e\u4f8b\u5f02\u5e38", ex);
        }
    }

    public void buildParam() {
        this.buildFormBlock();
        this.buildQueryOrgCodes();
        this.buildDimensionValueSet();
    }

    public void queryAllRowData() {
        this.needQueryFormBlockMap.forEach((formCode, regionVOs) -> {
            for (DataDockingBlockDTO regionVO : regionVOs) {
                DataDockingBlockVO dataDockingBlockVO = regionVO.getDataDockingBlockVO();
                List fieldCodes = dataDockingBlockVO.getFieldCodes();
                List<FieldDefine> fieldDefines = fieldCodes.stream().map(this.fieldDefineMap::get).collect(Collectors.toList());
                List<DataDockingDataRowVO> dataDockingDataRowVOS = this.queryRowData(regionVO.getDataRegionDefine(), fieldDefines);
                logger.info("\u8868\u5355\uff1a" + formCode + "\u4e2d\u7684\u533a\u57df\u67e5\u8be2\u4e86:" + dataDockingDataRowVOS.size() + "\u6761\u6570\u636e\u3002");
                this.allDataDockingDataRowVOS.addAll(dataDockingDataRowVOS);
            }
        });
    }

    public void buildQueryDataRes() {
        HashSet<String> isoDim = new HashSet<String>();
        for (int i = 0; i < this.dimensionValueSet.size(); ++i) {
            String name = this.dimensionValueSet.getName(i);
            Object value = this.dimensionValueSet.getValue(i);
            if (!(value instanceof ArrayList)) continue;
            isoDim.add(name);
        }
        Map<String, List<DataDockingDataRowVO>> isoDim2DataRowMap = this.allDataDockingDataRowVOS.stream().collect(Collectors.groupingBy(dataDockingDataRowVO -> {
            StringBuilder groupKey = new StringBuilder();
            isoDim.forEach(dim -> groupKey.append(dataDockingDataRowVO.getDimension().get(dim).toString()));
            return groupKey.toString();
        }));
        DataDockingVO dataDockingVO = new DataDockingVO();
        dataDockingVO.setRequestTime(String.valueOf(System.currentTimeMillis()));
        dataDockingVO.setSn(UUIDUtils.newUUIDStr());
        ArrayList<DataDockingTaskVO> dataDockingTaskVOS = new ArrayList<DataDockingTaskVO>();
        dataDockingVO.setData(dataDockingTaskVOS);
        DataDockingTaskVO dataDockingTaskVO = new DataDockingTaskVO();
        dataDockingTaskVOS.add(dataDockingTaskVO);
        dataDockingTaskVO.setTaskCode(this.dataDockingQueryVO.getTaskCode());
        ArrayList dataDockingTaskDataVOS = new ArrayList();
        dataDockingTaskVO.setTaskData(dataDockingTaskDataVOS);
        isoDim2DataRowMap.forEach((dim, taskData) -> {
            DataDockingTaskDataVO dataDockingTaskDataVO = new DataDockingTaskDataVO();
            dataDockingTaskDataVOS.add(dataDockingTaskDataVO);
            dataDockingTaskDataVO.setYear(this.dataDockingQueryVO.getYear().toString());
            dataDockingTaskDataVO.setPeriod(this.dataDockingQueryVO.getPeriod().toString());
            dataDockingTaskDataVO.setDimension(((DataDockingDataRowVO)taskData.get(0)).getDimension());
            dataDockingTaskDataVO.setUnitCode(((DataDockingDataRowVO)taskData.get(0)).getDimension().get("MD_ORG").toString());
            ArrayList dataDockingFormVOS = new ArrayList();
            dataDockingTaskDataVO.setForms(dataDockingFormVOS);
            Map<String, List<DataDockingDataRowVO>> form2DataRowMap = taskData.stream().collect(Collectors.groupingBy(dataDockingDataRowVO -> dataDockingDataRowVO.getFormDefine().getKey()));
            form2DataRowMap.forEach((formKey, formData) -> {
                DataDockingFormVO dataDockingFormVO = new DataDockingFormVO();
                dataDockingFormVOS.add(dataDockingFormVO);
                dataDockingFormVO.setFormCode(((DataDockingDataRowVO)formData.get(0)).getFormDefine().getFormCode());
                ArrayList dataDockingBlockVOS = new ArrayList();
                dataDockingFormVO.setDataBlocks(dataDockingBlockVOS);
                Map<String, List<DataDockingDataRowVO>> reg2DataRowMap = formData.stream().collect(Collectors.groupingBy(dataDockingDataRowVO -> dataDockingDataRowVO.getDataRegionDefine().getKey()));
                reg2DataRowMap.forEach((regKey, blockData) -> {
                    blockData.sort(Comparator.comparing(DataDockingDataRowVO::getRowNum));
                    ArrayList regRowData = new ArrayList();
                    blockData.forEach(rowData -> regRowData.add(rowData.getFieldValues()));
                    DataDockingBlockVO dataDockingBlockVO = new DataDockingBlockVO();
                    dataDockingBlockVOS.add(dataDockingBlockVO);
                    dataDockingBlockVO.setFloatBlock(Boolean.valueOf(!DataRegionKind.DATA_REGION_SIMPLE.equals((Object)((DataDockingDataRowVO)blockData.get(0)).getDataRegionDefine().getRegionKind())));
                    dataDockingBlockVO.setFieldCodes(((DataDockingDataRowVO)blockData.get(0)).getFieldCodes());
                    dataDockingBlockVO.setFieldValues(regRowData);
                });
            });
        });
        this.dataDockingVO = dataDockingVO;
    }

    public DataDockingVO process() {
        this.buildParam();
        this.queryAllRowData();
        this.buildQueryDataRes();
        return this.dataDockingVO;
    }
}

