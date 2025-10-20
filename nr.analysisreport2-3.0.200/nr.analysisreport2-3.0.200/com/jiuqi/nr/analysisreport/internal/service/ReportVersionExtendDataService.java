/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  org.apache.commons.lang3.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.analysisreport.internal.service;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportVersionExtendData;
import com.jiuqi.nr.analysisreport.chapter.dao.IReportVersionExtendDataDao;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportVersionExtendDataService {
    @Autowired
    private IReportVersionExtendDataDao service;

    public int insert(ReportVersionExtendData chapterVersionDefine) throws DBParaException {
        return this.service.insert(chapterVersionDefine);
    }

    public void delete(String key) throws DBParaException {
        this.service.delete(key);
    }

    public ReportVersionExtendData get(String key) {
        return this.service.get(key);
    }

    public void update(ReportVersionExtendData reportVersionExtendData) throws DBParaException {
        this.service.update(reportVersionExtendData);
    }

    public Object getExtendFieldValue(String key, String fieldName) {
        ReportVersionExtendData versionExtendData = this.service.get(key);
        if (versionExtendData == null) {
            return null;
        }
        if (StringUtils.isEmpty((CharSequence)versionExtendData.getAvExtData())) {
            return null;
        }
        JSONObject extendJson = new JSONObject(versionExtendData.getAvExtData());
        if (!extendJson.has(fieldName)) {
            return null;
        }
        return extendJson.get(fieldName);
    }
}

