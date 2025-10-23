/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.calibre2.ICalibreDataService
 *  com.jiuqi.nr.calibre2.ICalibreDefineService
 *  com.jiuqi.nr.calibre2.domain.CalibreDataDTO
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDTO
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.summary.service.impl;

import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.service.SummaryParamService;
import com.jiuqi.nr.summary.utils.EntityQueryUtil;
import com.jiuqi.nr.summary.vo.EntityQueryParam;
import com.jiuqi.nr.summary.vo.EntityTitleQueryParam;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class SummaryParamServiceImpl
implements SummaryParamService {
    private static final Logger logger = LoggerFactory.getLogger(SummaryParamServiceImpl.class);
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private ICalibreDefineService calibreDefineService;
    @Autowired
    private ICalibreDataService calibreDataService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRuntimeSummarySolutionService runtimeSummarySolutionService;

    @Override
    public List<TaskDefine> getAllTaskDefines() {
        return this.runTimeAuthViewController.getAllTaskDefines();
    }

    @Override
    public List<TaskDefine> getRelTaskDefines(String taskKey) throws SummaryCommonException {
        List<TaskDefine> allTaskDefines = this.getAllTaskDefines();
        TaskDefine mainTaskDefine = this.getTaskDefine(taskKey);
        return allTaskDefines.stream().filter(taskDefine -> {
            if (!mainTaskDefine.getTaskCode().equals(taskDefine.getTaskCode()) && mainTaskDefine.getDw().equals(taskDefine.getDw()) && mainTaskDefine.getDateTime().equals(taskDefine.getDateTime())) {
                Set relDimKeys;
                String dims = mainTaskDefine.getDims();
                if (!StringUtils.hasLength(dims)) {
                    return !StringUtils.hasLength(taskDefine.getDims());
                }
                if (dims.equals(taskDefine.getDims())) {
                    return true;
                }
                if (!StringUtils.hasLength(taskDefine.getDims())) {
                    return false;
                }
                Set mianDimKeys = Arrays.stream(dims.split(";")).collect(Collectors.toSet());
                return mianDimKeys.containsAll(relDimKeys = Arrays.stream(taskDefine.getDims().split(";")).collect(Collectors.toSet())) && relDimKeys.containsAll(mianDimKeys);
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public TaskDefine getTaskDefine(String taskKey) throws SummaryCommonException {
        try {
            return this.runTimeAuthViewController.queryTaskDefine(taskKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SummaryCommonException(SummaryErrorEnum.PARAM_GETTASK_FAILED, e.getMessage());
        }
    }

    @Override
    public List<IEntityDefine> getSceneDimensions(String taskKey) throws SummaryCommonException {
        String dims;
        ArrayList<IEntityDefine> entityDefines = new ArrayList<IEntityDefine>();
        TaskDefine taskDefine = this.getTaskDefine(taskKey);
        if (!ObjectUtils.isEmpty(taskDefine) && StringUtils.hasLength(dims = taskDefine.getDims())) {
            String[] scheneDimKeys;
            for (String scheneDimKey : scheneDimKeys = dims.split(";")) {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(scheneDimKey);
                entityDefines.add(entityDefine);
            }
        }
        return entityDefines;
    }

    @Override
    public IEntityDefine getDimension(String taskKey) throws SummaryCommonException {
        TaskDefine taskDefine = this.getTaskDefine(taskKey);
        if (!ObjectUtils.isEmpty(taskDefine)) {
            String dw = taskDefine.getDw();
            return this.entityMetaService.queryEntity(dw);
        }
        return null;
    }

    @Override
    public List<IEntityDefine> getTargetDimensions(String taskKey) throws SummaryCommonException {
        ArrayList<IEntityDefine> entityDefines = new ArrayList<IEntityDefine>();
        TaskDefine taskDefine = this.getTaskDefine(taskKey);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        entityDefines.add(entityDefine);
        return entityDefines;
    }

    @Override
    public List<IEntityAttribute> getDimensionAttributes(String dimKey) {
        ArrayList<IEntityAttribute> entityAttributes = new ArrayList<IEntityAttribute>();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(dimKey);
        if (!ObjectUtils.isEmpty(entityModel)) {
            Iterator attributes = entityModel.getAttributes();
            while (attributes.hasNext()) {
                IEntityAttribute attribute = (IEntityAttribute)attributes.next();
                entityAttributes.add(attribute);
            }
        }
        return entityAttributes;
    }

    @Override
    public List<IEntityRow> getEntityRows(EntityQueryParam entityQueryParam) throws Exception {
        return EntityQueryUtil.getEntityRows(entityQueryParam.getEntityId(), entityQueryParam.getEntityKeyData(), entityQueryParam.getRowFilter(), entityQueryParam.isAll());
    }

    @Override
    public List<IEntityRow> getEntityRows(EntityTitleQueryParam entityTitleQueryParam) throws Exception {
        return EntityQueryUtil.getEntityRows(entityTitleQueryParam.getEntityId(), entityTitleQueryParam.getEntityKeyDatas());
    }

    @Override
    public List<CalibreDefineDTO> getCalibreDefines(String dimKey) {
        CalibreDefineDTO defineDTO = new CalibreDefineDTO();
        defineDTO.setEntityId(dimKey);
        return (List)this.calibreDefineService.getByRefer(defineDTO).getData();
    }

    @Override
    public List<CalibreDataDTO> getCalibreDatas(String calibreDefineKey) {
        CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
        calibreDataDTO.setDefineKey(calibreDefineKey);
        return (List)this.calibreDataService.list(calibreDataDTO).getData();
    }

    @Override
    public List<IEntityDefine> getRelDimensions(String dimKey) {
        IEntityModel entityModel = this.entityMetaService.getEntityModel(dimKey);
        return null;
    }

    @Override
    public List<TableModelDefine> getTableModelDefines(String dimKey) {
        ArrayList<TableModelDefine> tableModelDefines = new ArrayList<TableModelDefine>();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(dimKey);
        if (!ObjectUtils.isEmpty(entityModel)) {
            Iterator attributes = entityModel.getAttributes();
            while (attributes.hasNext()) {
                IEntityAttribute attribute = (IEntityAttribute)attributes.next();
                String referTableID = attribute.getReferTableID();
                if (!StringUtils.hasLength(referTableID)) continue;
                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(referTableID);
                if (ObjectUtils.isEmpty(entityModel)) continue;
                tableModelDefines.add(tableModelDefine);
            }
        }
        return tableModelDefines;
    }

    @Override
    public List<DataField> getTableDimensions(List<String> taskKeys) throws SummaryCommonException {
        ArrayList<DataField> dataFields = new ArrayList<DataField>();
        for (String taskKey : taskKeys) {
            TaskDefine taskDefine = this.getTaskDefine(taskKey);
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            List allDataFieldByKind = this.runtimeDataSchemeService.getAllDataFieldByKind(dataScheme.getKey(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
            dataFields.addAll(allDataFieldByKind);
        }
        return dataFields;
    }

    @Override
    public List<DataScheme> getDataSchemes(String sumSchemkey) throws SummaryCommonException {
        ArrayList<DataScheme> dataSchemes = new ArrayList<DataScheme>();
        SummarySolutionModel solutionModel = this.runtimeSummarySolutionService.getSummarySolutionModel(sumSchemkey);
        if (!ObjectUtils.isEmpty(solutionModel)) {
            DataScheme mainDataScheme = this.getDataSchemeByTask(solutionModel.getMainTask());
            dataSchemes.add(mainDataScheme);
            List<String> relationTasks = solutionModel.getRelationTasks();
            if (!CollectionUtils.isEmpty(relationTasks)) {
                dataSchemes.addAll(relationTasks.stream().map(taskKey -> {
                    try {
                        return this.getDataSchemeByTask((String)taskKey);
                    }
                    catch (SummaryCommonException e) {
                        logger.error(e.getMessage(), (Throwable)((Object)e));
                        throw new RuntimeException((Throwable)((Object)e));
                    }
                }).collect(Collectors.toList()));
            }
        }
        return dataSchemes;
    }

    private DataScheme getDataSchemeByTask(String taskKey) throws SummaryCommonException {
        TaskDefine mainTask = this.getTaskDefine(taskKey);
        return this.runtimeDataSchemeService.getDataScheme(mainTask.getDataScheme());
    }

    @Override
    public List<DataTable> getAllDataTable(String dataSchemeKey) {
        return this.runtimeDataSchemeService.getAllDataTable(dataSchemeKey);
    }

    @Override
    public List<DataField> getAllDataField(String dataSchemeKey) {
        return this.runtimeDataSchemeService.getAllDataField(dataSchemeKey);
    }

    @Override
    public DataScheme getSummaryDataScheme(String sumSchemkey) {
        return this.runtimeDataSchemeService.getDataScheme(sumSchemkey);
    }
}

