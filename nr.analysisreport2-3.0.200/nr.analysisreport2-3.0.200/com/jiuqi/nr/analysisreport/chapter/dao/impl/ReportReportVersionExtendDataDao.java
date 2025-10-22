/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.analysisreport.chapter.dao.impl;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportVersionExtendData;
import com.jiuqi.nr.analysisreport.chapter.dao.IReportVersionExtendDataDao;
import org.springframework.stereotype.Component;

@Component
public class ReportReportVersionExtendDataDao
extends BaseDao
implements IReportVersionExtendDataDao {
    private Class<ReportVersionExtendData> implClass = ReportVersionExtendData.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    @Override
    public int insert(ReportVersionExtendData chapterVersionDefine) throws DBParaException {
        return super.insert((Object)chapterVersionDefine);
    }

    @Override
    public void delete(String key) throws DBParaException {
        super.delete((Object)key);
    }

    @Override
    public ReportVersionExtendData get(String key) {
        return (ReportVersionExtendData)super.getByKey((Object)key, this.implClass);
    }

    @Override
    public void update(ReportVersionExtendData reportVersionExtendData) throws DBParaException {
        super.update((Object)reportVersionExtendData);
    }
}

