/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.io.params.base.CSVRange
 *  com.jiuqi.nr.io.params.input.ExpViewFields
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.service.IoQualifier
 *  com.jiuqi.nr.io.service.MultistageUnitReplace
 */
package nr.midstore2.core.dataset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.io.params.base.CSVRange;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.service.IoQualifier;
import com.jiuqi.nr.io.service.MultistageUnitReplace;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MidsotreTableContext
implements Cloneable {
    private String taskKey;
    private String dataSchemeKey;
    private String formSchemeKey;
    private String dataTableKey;
    private DimensionValueSet dimensionSet;
    private Map<String, Object> variableMap;
    private OptTypes optType = OptTypes.FORM;
    private String fileType = ".txt";
    private String split = ",";
    private boolean isAttachment = false;
    private String attachmentArea = "default";
    private String syncTaskID;
    private ExpViewFields expEntryFields = ExpViewFields.CODE;
    private ExpViewFields expEnumFields = ExpViewFields.CODE;
    private int floatImpOpt = 2;
    private int entityImpOpt = 2;
    private String pwd;
    private boolean exportBizkeyorder = false;
    private boolean importBizkeyorder = false;
    private String dimension;
    private IoQualifier ioQualifier;
    private boolean validEntityExist = false;
    private boolean returnBizKeyValue;
    private Integer dataLineIndex = null;
    private String secretLevelTitle = "";
    private String secretLevelTitleHigher;
    private MultistageUnitReplace multistageUnitReplace;
    private boolean multistageEliminateBizKey = false;
    private List<Variable> variables = null;
    private CSVRange csvRange;
    private TempAssistantTable tempTable;
    private boolean isExportEntitys = true;
    private boolean isImportEntitys = false;
    private List<String> sortFields;
    private List<String> splitGather = new ArrayList<String>();
    private boolean isOrdered = true;

    public CSVRange getCsvRange() {
        return this.csvRange;
    }

    public void setCsvRange(CSVRange csvRange) {
        this.csvRange = csvRange;
    }

    public List<Variable> getVariables() {
        return this.variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public boolean isMultistageEliminateBizKey() {
        return this.multistageEliminateBizKey;
    }

    public void setMultistageEliminateBizKey(boolean multistageEliminateBizKey) {
        this.multistageEliminateBizKey = multistageEliminateBizKey;
    }

    public MultistageUnitReplace getMultistageUnitReplace() {
        return this.multistageUnitReplace;
    }

    public void setMultistageUnitReplace(MultistageUnitReplace multistageUnitReplace) {
        this.multistageUnitReplace = multistageUnitReplace;
    }

    public Integer getDataLineIndex() {
        return this.dataLineIndex;
    }

    public void setDataLineIndex(Integer dataLineIndex) {
        this.dataLineIndex = dataLineIndex;
    }

    public boolean isReturnBizKeyValue() {
        return this.returnBizKeyValue;
    }

    public void setReturnBizKeyValue(boolean returnBizKeyValue) {
        this.returnBizKeyValue = returnBizKeyValue;
    }

    public boolean isValidEntityExist() {
        return this.validEntityExist;
    }

    public void setValidEntityExist(boolean validEntityExist) {
        this.validEntityExist = validEntityExist;
    }

    public IoQualifier getIoQualifier() {
        return this.ioQualifier;
    }

    public void setIoQualifier(IoQualifier ioQualifier) {
        this.ioQualifier = ioQualifier;
    }

    public String getDimension() {
        return this.dimension;
    }

    public TempAssistantTable getTempTable() {
        return this.tempTable;
    }

    public boolean isImportEntitys() {
        return this.isImportEntitys;
    }

    public void setImportEntitys(boolean isImportEntitys) {
        this.isImportEntitys = isImportEntitys;
    }

    public boolean isExportEntitys() {
        return this.isExportEntitys;
    }

    public void setExportEntitys(boolean isExportEntitys) {
        this.isExportEntitys = isExportEntitys;
    }

    public boolean isExportBizkeyorder() {
        return this.exportBizkeyorder;
    }

    public void setExportBizkeyorder(boolean exportBizkeyorder) {
        this.exportBizkeyorder = exportBizkeyorder;
    }

    public boolean isOrdered() {
        return this.isOrdered;
    }

    public void setOrdered(boolean isOrdered) {
        this.isOrdered = isOrdered;
    }

    public List<String> getSplitGather() {
        return this.splitGather;
    }

    public void setSplitGather(List<String> splitGather) {
        this.splitGather = splitGather;
    }

    public List<String> getSortFields() {
        return this.sortFields;
    }

    public void setSortFields(List<String> sortFields) {
        this.sortFields = sortFields;
    }

    public MidsotreTableContext() {
    }

    public MidsotreTableContext(String taskKey, String dataSchemeKey, String dataTableKey, DimensionValueSet dimensionSet, OptTypes optType, String fileType) {
        this.taskKey = taskKey;
        this.dataSchemeKey = dataSchemeKey;
        this.dataTableKey = dataTableKey;
        this.dimensionSet = dimensionSet;
        this.optType = optType;
        this.fileType = fileType;
    }

    public MidsotreTableContext(String taskKey, String dataSchemeKey, String dataTableKey, DimensionValueSet dimensionSet, OptTypes optType, String fileType, String split, String syncTaskID) {
        this.taskKey = taskKey;
        this.dataSchemeKey = dataSchemeKey;
        this.dataTableKey = dataTableKey;
        this.dimensionSet = dimensionSet;
        this.optType = optType;
        this.fileType = fileType;
        this.split = split;
        this.syncTaskID = syncTaskID;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getEntityImpOpt() {
        return this.entityImpOpt;
    }

    public void setEntityImpOpt(int entityImpOpt) {
        this.entityImpOpt = entityImpOpt;
    }

    public int getFloatImpOpt() {
        return this.floatImpOpt;
    }

    public void setFloatImpOpt(int floatImpOpt) {
        this.floatImpOpt = floatImpOpt;
    }

    public ExpViewFields getExpEntryFields() {
        return this.expEntryFields;
    }

    public void setExpEntryFields(ExpViewFields expEntryFields) {
        this.expEntryFields = expEntryFields;
    }

    public ExpViewFields getExpEnumFields() {
        return this.expEnumFields;
    }

    public void setExpEnumFields(ExpViewFields expEnumFields) {
        this.expEnumFields = expEnumFields;
    }

    public String getSyncTaskID() {
        return this.syncTaskID;
    }

    public void setSyncTaskID(String syncTaskID) {
        this.syncTaskID = syncTaskID;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String formSchemeKey) {
        this.dataSchemeKey = formSchemeKey;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public DimensionValueSet getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(DimensionValueSet dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public OptTypes getOptType() {
        return this.optType;
    }

    public void setOptType(OptTypes optType) {
        this.optType = optType;
    }

    public String getSplit() {
        return this.split;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public boolean isAttachment() {
        return this.isAttachment;
    }

    public void setAttachment(boolean isAttachment) {
        this.isAttachment = isAttachment;
    }

    public String getAttachmentArea() {
        return this.attachmentArea;
    }

    public void setAttachmentArea(String attachmentArea) {
        this.attachmentArea = attachmentArea;
    }

    public void setTempAssistantTable(String dimension, TempAssistantTable tempTable) {
        this.dimension = dimension;
        this.tempTable = tempTable;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    public String getSecretLevelTitle() {
        return this.secretLevelTitle;
    }

    public void setSecretLevelTitle(String secretLevelTitle) {
        this.secretLevelTitle = secretLevelTitle;
    }

    public String getSecretLevelTitleHigher() {
        return this.secretLevelTitleHigher;
    }

    public void setSecretLevelTitleHigher(String secretLevelTitleHigher) {
        this.secretLevelTitleHigher = secretLevelTitleHigher;
    }

    public boolean isImportBizkeyorder() {
        return this.importBizkeyorder;
    }

    public void setImportBizkeyorder(boolean importBizkeyorder) {
        this.importBizkeyorder = importBizkeyorder;
    }

    public MidsotreTableContext clone() {
        MidsotreTableContext tableContext = null;
        try {
            tableContext = (MidsotreTableContext)super.clone();
            DimensionValueSet newDimensionSet = new DimensionValueSet(this.dimensionSet);
            tableContext.setDimensionSet(newDimensionSet);
        }
        catch (Exception e) {
            tableContext = new MidsotreTableContext();
        }
        return tableContext;
    }
}

