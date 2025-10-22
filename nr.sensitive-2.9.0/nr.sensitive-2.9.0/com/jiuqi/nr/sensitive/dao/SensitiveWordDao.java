/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.sensitive.dao;

import com.jiuqi.nr.sensitive.bean.daoObject.SensitiveWordDaoObject;
import java.util.List;

public interface SensitiveWordDao {
    public static final String TABLE_NAME = "NR_SENSITIVE_WORD";
    public static final String SENSITIVE_WORD_KEY = "SENSITIVE_WORD_KEY";
    public static final String SENSITIVE_CODE = "SENSITIVE_CODE";
    public static final String SENSITIVE_TYPE = "SENSITIVE_TYPE";
    public static final String SENSITIVE_WORD_TYPE = "SENSITIVE_WORD_TYPE";
    public static final String SENSITIVE_WORD_INFO = "SENSITIVE_WORD_INFO";
    public static final String SENSITIVE_DESCRIPTION = "SENSITIVE_DESCRIPTION";
    public static final String IS_EFFECTIVE = "IS_EFFECTIVE";
    public static final String MODIFY_TIME = "MODIFY_TIME";
    public static final String MODIFY_USER = "MODIFY_USER";

    public int insertSensitiveWord(SensitiveWordDaoObject var1);

    public void batchInsertSensitiveWord(List<SensitiveWordDaoObject> var1);

    public int updateSensitiveWord(SensitiveWordDaoObject var1);

    public List<SensitiveWordDaoObject> getSensitiveWordWithType(String var1, Integer var2, Integer var3, Integer var4);

    public List<SensitiveWordDaoObject> queryAllSensitiveWordWithType(Integer var1, Integer var2, Integer var3);

    public List<SensitiveWordDaoObject> queryAllSensitiveWordByWordType(Integer var1);

    public List<SensitiveWordDaoObject> queryAllSensitiveWordByType(Integer var1);

    public SensitiveWordDaoObject getSensitiveWordByInfoAndWordType(String var1, Integer var2);

    public SensitiveWordDaoObject getSensitiveWordBySensitiveInfo(String var1, Integer var2);

    public boolean deleteSensitiveWord(String var1);

    public boolean deleteSensitiveWordByType(Integer var1);
}

