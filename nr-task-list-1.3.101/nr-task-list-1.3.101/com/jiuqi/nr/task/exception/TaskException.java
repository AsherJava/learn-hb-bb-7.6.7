/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.task.exception;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.nr.task.common.TaskI18nUtil;

public enum TaskException implements ErrorEnum
{
    TASKGROUP_ERROE_001("ERROR_TK_01", "\u4efb\u52a1\u5206\u7ec4\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u4efb\u52a1\u5206\u7ec4"),
    TASKGROUP_ERROE_002("ERROR_TK_02", "\u5f53\u524d\u5206\u7ec4\u6392\u5e8f\u5df2\u6700\u5c0f\uff0c\u65e0\u6cd5\u4e0a\u79fb"),
    TASKGROUP_ERROE_003("ERROR_TK_03", "\u5f53\u524d\u5206\u7ec4\u6392\u5e8f\u5df2\u6700\u5927\uff0c\u65e0\u6cd5\u4e0b\u79fb"),
    TASKGROUP_ERROE_004("ERROR_TK_04", "\u4efb\u52a1\u5206\u7ec4\u79fb\u52a8\u5931\u8d25"),
    TASKGROUP_ERROE_005("ERROR_TK_05", "\u4efb\u52a1\u5206\u7ec4\u4fee\u6539\u5931\u8d25"),
    TASKGROUP_ERROE_006("ERROR_TK_06", "\u4efb\u52a1\u5206\u7ec4\u6a21\u7cca\u641c\u7d22\u5931\u8d25"),
    TASKGROUP_ERROE_007("ERROR_TK_07", "\u4efb\u52a1\u5206\u7ec4\u5220\u9664\u5931\u8d25"),
    TASKGROUP_ERROE_28("ERROR_TK_28", "\u4efb\u52a1\u5206\u7ec4\u65b0\u589e\u5931\u8d25"),
    TASKGROUP_ERROE_008("ERROR_TK_08", "\u4efb\u52a1\u5206\u7ec4\u67e5\u8be2\u5931\u8d25"),
    TASKGROUP_ERROE_009("ERROR_TK_09", "\u4efb\u52a1\u5206\u7ec4\u6811\u578b\u67e5\u8be2\u5931\u8d25"),
    TASKGROUP_ERROE_010("ERROR_TK_10", "\u83b7\u53d6\u8d44\u6e90\u8def\u5f84\u5931\u8d25"),
    CODE_HAS_EXIST("ERROR_TK_11", "\u4efb\u52a1\u6807\u8bc6\u65e0\u6548\u6216\u8005\u4e0d\u552f\u4e00"),
    NO_AUTHORITY("ERROR_TK_12", "\u672a\u6388\u6743"),
    INVALID_DATA_SCHEME("ERROR_TK_13", "\u8bf7\u9009\u62e9\u6709\u6548\u7684\u6570\u636e\u65b9\u6848"),
    TASK_INIT_FAILED("ERROR_TK_14", "\u4efb\u52a1\u521d\u59cb\u5316\u5931\u8d25"),
    MOVE_TASK_FAILED("ERROR_TK_15", "\u79fb\u52a8\u4efb\u52a1\u5931\u8d25"),
    SETTING_GROUP_FAILED("ERROR_TK_16", "\u4efb\u52a1\u8bbe\u7f6e\u5206\u7ec4\u5931\u8d25"),
    INSERT_DEFAULT_PRINTSCHEME_FAILED("ERROR_TK_17", "\u65b0\u589e\u9ed8\u8ba4\u6253\u5370\u65b9\u6848\u5931\u8d25"),
    QUERY_PUBLISH_TIME_FAILED("ERROR_TK_18", "\u83b7\u53d6\u53d1\u5e03\u65f6\u95f4\u5931\u8d25"),
    TASK_TITLE_ILLEGAL("ERROR_TK_19", "\u540d\u79f0\u5305\u62ec\u6570\u5b57\u3001\u5b57\u6bcd\u3001\u4e2d\u6587\u5b57\u7b26\u3001\u62ec\u53f7\u3001\u4e0b\u5212\u7ebf\u3001\u6a2a\u6760"),
    TASK_CODE_ILLEGAL("ERROR_TK_20", "\u4efb\u52a1\u6807\u8bc6\u53ef\u4ee5\u7531\u5927\u5199\u5b57\u6bcd\u3001\u6570\u5b57\u3001\u4e0b\u5212\u7ebf\u7ec4\u6210"),
    TASK_CODE_TITLE_LENGTH("ERROR_TK_21", "\u6807\u8bc6\u6216\u540d\u79f0\u957f\u5ea6\u4e0d\u53ef\u8d85\u8fc725"),
    TASK_INSERT_FAILED("ERROR_TK_22", "\u65b0\u589e\u4efb\u52a1\u5931\u8d25"),
    TASK_DELETE_FAILED("ERROR_TK_23", "\u5220\u9664\u4efb\u52a1\u5931\u8d25"),
    TASK_UPDATE_FAILED("ERROR_TK_24", "\u66f4\u65b0\u4efb\u52a1\u5931\u8d25"),
    TASK_QUERY_FAILED("ERROR_TK_25", "\u67e5\u8be2\u4efb\u52a1\u5931\u8d25"),
    TREE_DATA_ERROR("ERROR_TK_26", "\u6570\u636e\u9879\u83b7\u53d6\u5931\u8d25"),
    TASK_CODE_ERROR("ERROR_TK_27", "\u4efb\u52a1\u6807\u8bc6\u91cd\u590d"),
    QUERY_TASK_OPTION_ERROR("QUERY_TASK_OPTION_ERROR", "\u67e5\u8be2\u4efb\u52a1\u9009\u9879\u5931\u8d25"),
    SAVE_TASK_OPTION_ERROR("SAVE_TASK_OPTION_ERROR", "\u4fdd\u5b58\u4efb\u52a1\u9009\u9879\u5931\u8d25"),
    TASK_FUZZY_SEARCH_ERROR("TASK_FUZZY_SEARCH_ERROR", "\u4efb\u52a1\u6a21\u7cca\u641c\u7d22\u5931\u8d25");

    private String code;
    private String message;

    private TaskException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return TaskI18nUtil.getMessage("jqException.task." + this.code, new Object[0]);
    }
}

