/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.analysisreport.chapter.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportChapterDefine;
import java.util.List;

public interface IChapterDao {
    public int inseartChapter(ReportChapterDefine var1) throws Exception;

    public boolean updateChapter(ReportChapterDefine var1) throws Exception;

    public ReportChapterDefine getBykey(String var1);

    public int[] batchDeleteByKeys(String[] var1) throws DBParaException;

    public List<ReportChapterDefine> getChapterByModuleId(String var1);

    public void delete(String var1) throws DBParaException;

    public int deleteByFiled(String[] var1, Object[] var2) throws DBParaException;

    public Integer checkChapterTitleRepeat(String var1, String var2, String var3);

    public List<ReportChapterDefine> list(List<String> var1) throws Exception;

    public int clearTemplateArcData(String var1);

    public List<ReportChapterDefine> listOnlyArcNameAndArcKey(String var1);

    public boolean checkGenCatalogCompleted(String var1);

    public void batchInsert(ReportChapterDefine[] var1) throws DBParaException;
}

