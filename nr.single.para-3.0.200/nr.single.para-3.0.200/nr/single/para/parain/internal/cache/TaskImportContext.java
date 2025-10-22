/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.definition.facade.Context
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.impl.ContextImpl
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.JIOParamParser
 *  com.jiuqi.nr.single.core.para.parser.anal.AnalParaInfo
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 *  nr.single.map.data.facade.SingleMapFormSchemeDefine
 */
package nr.single.para.parain.internal.cache;

import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.definition.facade.Context;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.impl.ContextImpl;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.JIOParamParser;
import com.jiuqi.nr.single.core.para.parser.anal.AnalParaInfo;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.data.facade.SingleMapFormSchemeDefine;
import nr.single.para.compare.bean.ParaImportResult;
import nr.single.para.compare.definition.CompareDataEnumDTO;
import nr.single.para.compare.definition.CompareDataFormDTO;
import nr.single.para.compare.definition.CompareInfoDTO;
import nr.single.para.parain.controller.SingleParaImportOption;
import nr.single.para.parain.internal.cache.FieldInfoDefine;
import nr.single.para.parain.internal.cache.FormInfoCache;
import nr.single.para.parain.internal.cache.SchemeInfoCache;
import nr.single.para.parain.internal.cache.TableInfoDefine;
import org.slf4j.Logger;

public class TaskImportContext {
    private JIOParamParser jioParser;
    private ParaInfo paraInfo;
    private String baseTaskKey;
    private String baseformSchemeKey;
    private TaskImportContext baseImportContext;
    private AnalParaInfo analParaInfo;
    private String taskKey;
    private String tableGroupKey;
    private String taskFieldGroupKey;
    private String formFieldGroupKey;
    private DesignTaskDefine taskDefine;
    private Map<String, List<DesignFormGroupDefine>> groupFormMap = null;
    private List<DesignFormGroupDefine> groupFormList = null;
    private Map<String, FieldInfoDefine> entityFieldMaps = null;
    private Map<String, TableInfoDefine> entityTableMaps = null;
    private Map<String, IEntityAttribute> entityFieldRunTimeMaps = null;
    private Map<String, DesignFormDefine> formCache = null;
    private List<RepInfo> sortRepList = null;
    private Context curContext;
    private String periodFieldKey = null;
    private String periodTableKey = null;
    private String periodEntityId = null;
    private String entityTableKey = null;
    private String entityRunTimeTableKey = null;
    private TableInfoDefine entityTable = null;
    private String entityId = null;
    private String onlyEntityCode = null;
    private String formSchemeKey = null;
    private Boolean isNewTask = true;
    private Boolean isNewScheme = true;
    private Boolean isNewEntity = false;
    private Boolean fmdmIsData = false;
    private boolean uniqueField = false;
    private String dataSchemeKey = null;
    private DesignDataScheme dataScheme = null;
    private FormInfoCache formInfoCahche = new FormInfoCache();
    private SchemeInfoCache schemeInfoCache = new SchemeInfoCache();
    private SingleParaImportOption importOption;
    private SingleMapFormSchemeDefine mapScheme;
    private String mapSchemeKey;
    private AsyncTaskMonitor asyncMonitor;
    private double curProgress;
    private double curProgressLength;
    private String curServerCode;
    private Map<String, Object> variableMap;
    CompareInfoDTO compareInfo;
    Map<String, CompareDataEnumDTO> compareEnumDic;
    Map<String, CompareDataFormDTO> compareFormDic;
    Map<String, String> compareEnumMap;
    ParaImportResult importResult;

    public SingleMapFormSchemeDefine getMapScheme() {
        return this.mapScheme;
    }

    public void setMapScheme(SingleMapFormSchemeDefine mapScheme) {
        this.mapScheme = mapScheme;
    }

    public SingleParaImportOption getImportOption() {
        return this.importOption;
    }

    public void setImportOption(SingleParaImportOption importOption) {
        this.importOption = importOption;
    }

    public Boolean getIsNewTask() {
        return this.isNewTask;
    }

    public void setIsNewTask(Boolean isNewTask) {
        this.isNewTask = isNewTask;
    }

    public Boolean getIsNewScheme() {
        return this.isNewScheme;
    }

    public void setIsNewScheme(Boolean isNewScheme) {
        this.isNewScheme = isNewScheme;
    }

    public Boolean getIsNewEntity() {
        return this.isNewEntity;
    }

    public void setIsNewEntity(Boolean isNewEntity) {
        this.isNewEntity = isNewEntity;
    }

    public List<DesignFormGroupDefine> getGroupFormList() {
        if (this.groupFormList == null) {
            this.groupFormList = new ArrayList<DesignFormGroupDefine>();
        }
        return this.groupFormList;
    }

    public void setGroupFormList(List<DesignFormGroupDefine> groupFormList) {
        this.groupFormList = groupFormList;
    }

    public SchemeInfoCache getSchemeInfoCache() {
        if (this.schemeInfoCache == null) {
            this.schemeInfoCache = new SchemeInfoCache();
        }
        return this.schemeInfoCache;
    }

    public void setSchemeInfoCache(SchemeInfoCache schemeInfoCache) {
        this.schemeInfoCache = schemeInfoCache;
    }

    public FormInfoCache getFormInfoCahche() {
        return this.formInfoCahche;
    }

    public void setFormInfoCahche(FormInfoCache formCahche) {
        this.formInfoCahche = formCahche;
    }

    public String getTaskFieldGroupKey() {
        return this.taskFieldGroupKey;
    }

    public void setTaskFieldGroupKey(String taskFieldGroupKey) {
        this.taskFieldGroupKey = taskFieldGroupKey;
    }

    public String getFormFieldGroupKey() {
        return this.formFieldGroupKey;
    }

    public void setFormFieldGroupKey(String formFieldGroupKey) {
        this.formFieldGroupKey = formFieldGroupKey;
    }

    public JIOParamParser getJioParser() {
        return this.jioParser;
    }

    public void setJioParser(JIOParamParser value) {
        this.jioParser = value;
        if (value != null) {
            this.paraInfo = value.getParaInfo();
        }
    }

    public void setTaskKey(String value) {
        this.taskKey = value;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public String getTableGroupKey() {
        return this.tableGroupKey;
    }

    public void setTableGroupKey(String value) {
        this.tableGroupKey = value;
    }

    public DesignTaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(DesignTaskDefine value) {
        this.taskDefine = value;
    }

    public List<RepInfo> getSortRepList() {
        if (this.sortRepList == null) {
            this.sortRepList = new ArrayList<RepInfo>();
        }
        return this.sortRepList;
    }

    public Context getCurContext() {
        if (null == this.curContext) {
            this.curContext = new ContextImpl();
        }
        this.curContext.setTaskKey(this.taskKey);
        return this.curContext;
    }

    public Map<String, List<DesignFormGroupDefine>> getGroupFormMap() {
        if (this.groupFormMap == null) {
            this.groupFormMap = new HashMap<String, List<DesignFormGroupDefine>>();
        }
        return this.groupFormMap;
    }

    public Map<String, FieldInfoDefine> getEntityFieldMaps() {
        if (this.entityFieldMaps == null) {
            this.entityFieldMaps = new LinkedHashMap<String, FieldInfoDefine>();
        }
        return this.entityFieldMaps;
    }

    public Map<String, TableInfoDefine> getEntityTableMaps() {
        if (null == this.entityTableMaps) {
            this.entityTableMaps = new LinkedHashMap<String, TableInfoDefine>();
        }
        return this.entityTableMaps;
    }

    public void setEntityTableMaps(Map<String, TableInfoDefine> entityTableMaps) {
        this.entityTableMaps = entityTableMaps;
    }

    public Map<String, DesignFormDefine> getFormCache() {
        if (this.formCache == null) {
            this.formCache = new HashMap<String, DesignFormDefine>();
        }
        return this.formCache;
    }

    public String getPeriodFieldKey() {
        return this.periodFieldKey;
    }

    public void setPeriodFieldKey(String value) {
        this.periodFieldKey = value;
    }

    public String getPeriodTableKey() {
        return this.periodTableKey;
    }

    public void setPeriodTableKey(String periodTableKey) {
        this.periodTableKey = periodTableKey;
    }

    public String getPeriodEntityId() {
        return this.periodEntityId;
    }

    public void setPeriodEntityId(String periodEntityId) {
        this.periodEntityId = periodEntityId;
    }

    public String getEntityTableKey() {
        return this.entityTableKey;
    }

    public void setEntityTableKey(String value) {
        this.entityTableKey = value;
    }

    public TableInfoDefine getEntityTable() {
        return this.entityTable;
    }

    public void setEntityTable(TableInfoDefine entityTable) {
        this.entityTable = entityTable;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getOnlyEntityCode() {
        return this.onlyEntityCode;
    }

    public void setOnlyEntityCode(String onlyEntityCode) {
        this.onlyEntityCode = onlyEntityCode;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public AsyncTaskMonitor getAsyncMonitor() {
        return this.asyncMonitor;
    }

    public void setAsyncMonitor(AsyncTaskMonitor asyncMonitor) {
        this.asyncMonitor = asyncMonitor;
    }

    public void onProgress(double progress) {
        if (this.curProgress >= progress) {
            return;
        }
        this.curProgress = progress;
        if (null != this.asyncMonitor) {
            this.asyncMonitor.progressAndMessage(progress, "");
        }
    }

    public void onProgress(double progress, String msg) {
        if (this.curProgress >= progress) {
            return;
        }
        this.curProgress = progress;
        if (null != this.asyncMonitor) {
            this.asyncMonitor.progressAndMessage(progress, msg);
        }
    }

    public double getCurProgress() {
        return this.curProgress;
    }

    public void setCurProgress(double curProgress) {
        this.curProgress = curProgress;
    }

    public String getEntityRunTimeTableKey() {
        return this.entityRunTimeTableKey;
    }

    public void setEntityRunTimeTableKey(String entityRunTimeTableKey) {
        this.entityRunTimeTableKey = entityRunTimeTableKey;
    }

    public Map<String, IEntityAttribute> getEntityFieldRunTimeMaps() {
        if (this.entityFieldRunTimeMaps == null) {
            this.entityFieldRunTimeMaps = new HashMap<String, IEntityAttribute>();
        }
        return this.entityFieldRunTimeMaps;
    }

    public void setEntityFieldRunTimeMaps(Map<String, IEntityAttribute> entityFieldRunTimeMaps) {
        this.entityFieldRunTimeMaps = entityFieldRunTimeMaps;
    }

    public ParaInfo getParaInfo() {
        return this.paraInfo;
    }

    public void setParaInfo(ParaInfo paraInfo) {
        this.paraInfo = paraInfo;
    }

    public String getBaseTaskKey() {
        return this.baseTaskKey;
    }

    public void setBaseTaskKey(String baseTaskKey) {
        this.baseTaskKey = baseTaskKey;
    }

    public String getBaseformSchemeKey() {
        return this.baseformSchemeKey;
    }

    public void setBaseformSchemeKeyKey(String baseformSchemeKey) {
        this.baseformSchemeKey = baseformSchemeKey;
    }

    public TaskImportContext getBaseImportContext() {
        return this.baseImportContext;
    }

    public void setBaseImportContext(TaskImportContext baseImportContext) {
        this.baseImportContext = baseImportContext;
    }

    public boolean getIsAnalTask() {
        return this.getBaseImportContext() != null && this.getBaseImportContext().getTaskDefine() != null;
    }

    public AnalParaInfo getAnalParaInfo() {
        return this.analParaInfo;
    }

    public void setAnalParaInfo(AnalParaInfo analParaInfo) {
        this.analParaInfo = analParaInfo;
    }

    public String getCurServerCode() {
        return this.curServerCode;
    }

    public void setCurServerCode(String curServerCode) {
        this.curServerCode = curServerCode;
    }

    public Boolean getFmdmIsData() {
        return this.fmdmIsData;
    }

    public void setFmdmIsData(Boolean fmdmIsData) {
        this.fmdmIsData = fmdmIsData;
    }

    public boolean isUniqueField() {
        return this.uniqueField;
    }

    public void setUniqueField(boolean uniqueField) {
        this.uniqueField = uniqueField;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public DesignDataScheme getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(DesignDataScheme dataScheme) {
        this.dataScheme = dataScheme;
    }

    public Map<String, Object> getVariableMap() {
        if (this.variableMap == null) {
            this.variableMap = new HashMap<String, Object>();
        }
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    public double getCurProgressLength() {
        return this.curProgressLength;
    }

    public void setCurProgressLength(double curProgressLength) {
        this.curProgressLength = curProgressLength;
    }

    public CompareInfoDTO getCompareInfo() {
        return this.compareInfo;
    }

    public void setCompareInfo(CompareInfoDTO compareInfo) {
        this.compareInfo = compareInfo;
    }

    public Map<String, CompareDataEnumDTO> getCompareEnumDic() {
        if (this.compareEnumDic == null) {
            this.compareEnumDic = new HashMap<String, CompareDataEnumDTO>();
        }
        return this.compareEnumDic;
    }

    public void setCompareEnumDic(Map<String, CompareDataEnumDTO> compareEnumDic) {
        this.compareEnumDic = compareEnumDic;
    }

    public Map<String, String> getCompareEnumMap() {
        if (this.compareEnumMap == null) {
            this.compareEnumMap = new HashMap<String, String>();
        }
        return this.compareEnumMap;
    }

    public void setCompareEnumMap(Map<String, String> compareEnumMap) {
        this.compareEnumMap = compareEnumMap;
    }

    public ParaImportResult getImportResult() {
        return this.importResult;
    }

    public void setImportResult(ParaImportResult importResult) {
        this.importResult = importResult;
    }

    public Map<String, CompareDataFormDTO> getCompareFormDic() {
        if (this.compareFormDic == null) {
            this.compareFormDic = new HashMap<String, CompareDataFormDTO>();
        }
        return this.compareFormDic;
    }

    public void setCompareFormDic(Map<String, CompareDataFormDTO> compareFormDic) {
        this.compareFormDic = compareFormDic;
    }

    public String getMapSchemeKey() {
        return this.mapSchemeKey;
    }

    public void setMapSchemeKey(String mapSchemeKey) {
        this.mapSchemeKey = mapSchemeKey;
    }

    public void info(Logger logger, String message) {
        this.info(message);
        if (logger != null) {
            logger.info(message);
        }
    }

    public void info(String message) {
        ILogger biLogger;
        if (this.asyncMonitor != null && (biLogger = this.asyncMonitor.getBILogger()) != null) {
            biLogger.info(message);
        }
    }

    public void error(Logger logger, String message, Throwable t) {
        this.error(message, t);
        if (logger != null) {
            logger.error(message, t);
        }
    }

    public void error(String message, Throwable t) {
        ILogger biLogger;
        if (this.asyncMonitor != null && (biLogger = this.asyncMonitor.getBILogger()) != null) {
            biLogger.error(message);
        }
    }
}

