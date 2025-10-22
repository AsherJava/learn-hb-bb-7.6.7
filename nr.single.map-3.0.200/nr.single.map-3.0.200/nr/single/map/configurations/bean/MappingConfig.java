/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package nr.single.map.configurations.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.List;
import nr.single.map.configurations.bean.AutoAppendCode;
import nr.single.map.configurations.bean.CompleteUser;
import nr.single.map.configurations.bean.DataImportRule;
import nr.single.map.configurations.bean.SkipUnit;
import nr.single.map.configurations.bean.UpdateWay;
import nr.single.map.configurations.deserializer.MappingConfigDeserializer;

@JsonDeserialize(using=MappingConfigDeserializer.class)
public class MappingConfig
implements Serializable {
    private static final long serialVersionUID = 4842203790701786898L;
    public static final String UPLOAD_ARITHMETIC = "arithmetic";
    public static final String UPLOAD_EXAMINE = "examine";
    public static final String UPLOAD_STATUS = "uploadStatus";
    public static final String FORCE_UPLOAD = "forceUpload";
    public static final String CHECK_PARENT = "checkParent";
    public static final String UPLOAD_ENTITY_AND_DATA = "uploadEntityAndData";
    public static final String UNIT_UPDATE_WAY = "unitUpdateWay";
    public static final String SKIP_UNIT = "skipUnit";
    public static final String AUTO_APPEND_CODE = "autoAppendCode";
    public static final String DATA_IMPORT_RULE = "dataImportRule";
    public static final String COMPLETE_USER = "completeUser";
    private List<String> arithmetic;
    private List<String> examine;
    private boolean uploadStatus;
    private boolean forceUpload;
    private boolean checkParent;
    private boolean uploadEntityAndData;
    private UpdateWay unitUpdateWay;
    private SkipUnit skipUnit;
    private AutoAppendCode autoAppendCode;
    private DataImportRule dataImportRule;
    private CompleteUser completeUser = CompleteUser.IMPORTUSER;
    private boolean configParentNode;
    private boolean impByUnitAllCover;

    public MappingConfig() {
    }

    public MappingConfig(List<String> arithmetic, List<String> examine, boolean uploadStatus, boolean checkParent, boolean uploadEntityAndData, UpdateWay unitUpdateWay, SkipUnit skipUnit, AutoAppendCode appendCodeConfig, CompleteUser completeUser) {
        this.arithmetic = arithmetic;
        this.examine = examine;
        this.uploadStatus = uploadStatus;
        this.checkParent = checkParent;
        this.uploadEntityAndData = uploadEntityAndData;
        this.unitUpdateWay = unitUpdateWay;
        this.skipUnit = skipUnit;
        this.autoAppendCode = appendCodeConfig;
        this.completeUser = completeUser;
        this.configParentNode = false;
        this.impByUnitAllCover = false;
    }

    public List<String> getArithmetic() {
        return this.arithmetic;
    }

    public void setArithmetic(List<String> arithmetic) {
        this.arithmetic = arithmetic;
    }

    public List<String> getExamine() {
        return this.examine;
    }

    public void setExamine(List<String> examine) {
        this.examine = examine;
    }

    public boolean isUploadStatus() {
        return this.uploadStatus;
    }

    public void setUploadStatus(boolean uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public boolean isCheckParent() {
        return this.checkParent;
    }

    public void setCheckParent(boolean checkParent) {
        this.checkParent = checkParent;
    }

    public boolean isUploadEntityAndData() {
        return this.uploadEntityAndData;
    }

    public void setUploadEntityAndData(boolean uploadEntityAndData) {
        this.uploadEntityAndData = uploadEntityAndData;
    }

    public static long getSerialVersionUID() {
        return 4842203790701786898L;
    }

    public UpdateWay getUnitUpdateWay() {
        return this.unitUpdateWay;
    }

    public void setUnitUpdateWay(UpdateWay unitUpdateWay) {
        this.unitUpdateWay = unitUpdateWay;
    }

    public SkipUnit getSkipUnit() {
        return this.skipUnit;
    }

    public void setSkipUnit(SkipUnit skipUnit) {
        this.skipUnit = skipUnit;
    }

    public AutoAppendCode getAutoAppendCode() {
        return this.autoAppendCode;
    }

    public void setAutoAppendCode(AutoAppendCode autoAppendCode) {
        this.autoAppendCode = autoAppendCode;
    }

    public DataImportRule getDataImportRule() {
        return this.dataImportRule;
    }

    public void setDataImportRule(DataImportRule dataImportRule) {
        this.dataImportRule = dataImportRule;
    }

    public boolean isForceUpload() {
        return this.forceUpload;
    }

    public void setForceUpload(boolean forceUpload) {
        this.forceUpload = forceUpload;
    }

    public CompleteUser getCompleteUser() {
        return this.completeUser;
    }

    public void setCompleteUser(CompleteUser completeUser) {
        this.completeUser = completeUser;
    }

    public static MappingConfig getDefaultConfig() {
        MappingConfig config = new MappingConfig();
        config.uploadStatus = false;
        config.checkParent = false;
        config.uploadEntityAndData = false;
        config.setUnitUpdateWay(UpdateWay.buildDefault());
        config.setDataImportRule(DataImportRule.getInstance());
        config.setCompleteUser(CompleteUser.IMPORTUSER);
        config.setConfigParentNode(false);
        config.setImpByUnitAllCover(false);
        return config;
    }

    public boolean isConfigParentNode() {
        return this.configParentNode;
    }

    public void setConfigParentNode(boolean configParentNode) {
        this.configParentNode = configParentNode;
    }

    public boolean isImpByUnitAllCover() {
        return this.impByUnitAllCover;
    }

    public void setImpByUnitAllCover(boolean impByUnitAllCover) {
        this.impByUnitAllCover = impByUnitAllCover;
    }
}

