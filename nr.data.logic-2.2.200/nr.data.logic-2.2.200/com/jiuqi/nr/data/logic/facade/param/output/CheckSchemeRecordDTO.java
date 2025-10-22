/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.internal.entity.CheckSchemeRecordDO;
import com.jiuqi.nr.data.logic.internal.util.SerializationUtil;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckSchemeRecordDTO {
    private String key;
    private String userID;
    private long checkTime;
    private String formSchemeKey;
    private CheckResultQueryParam checkResultQueryParam;
    private static final Logger logger = LoggerFactory.getLogger(CheckSchemeRecordDTO.class);

    public CheckSchemeRecordDTO() {
    }

    public CheckSchemeRecordDTO(CheckSchemeRecordDO checkSchemeRecordDO) {
        this.key = checkSchemeRecordDO.getKey();
        this.userID = checkSchemeRecordDO.getUserId();
        this.checkTime = checkSchemeRecordDO.getCheckTime();
        this.formSchemeKey = checkSchemeRecordDO.getFormSchemeKey();
        CheckResultQueryParam param = new CheckResultQueryParam();
        try {
            byte[] bytes = Base64.getDecoder().decode(checkSchemeRecordDO.getCkrParJson());
            param = (CheckResultQueryParam)SerializationUtil.deserialize(bytes);
        }
        catch (Exception e) {
            logger.error("\u5ba1\u6838\u53c2\u6570\u53cd\u5e8f\u5217\u5316\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
        this.checkResultQueryParam = param;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getCheckTime() {
        return this.checkTime;
    }

    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public CheckResultQueryParam getCheckResultQueryParam() {
        return this.checkResultQueryParam;
    }

    public void setCheckResultQueryParam(CheckResultQueryParam checkResultQueryParam) {
        this.checkResultQueryParam = checkResultQueryParam;
    }
}

