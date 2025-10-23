/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException
 */
package com.jiuqi.nr.task.tools;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.task.exception.FormSchemeException;
import com.jiuqi.nr.task.exception.TaskException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamCheck {
    public void TaskParamCheck(String taskTitle, String taskCode) {
        Pattern titleCompile = Pattern.compile("^[a-z0-9A-Z[\u4e00-\u9fa5]\uff08\uff09()_-]+$");
        Pattern codeCompile = Pattern.compile("^[A-Z][0-9A-Z_]+$");
        Matcher titleMatcher = titleCompile.matcher(taskTitle);
        Matcher codeMatcher = codeCompile.matcher(taskCode);
        if (!titleMatcher.matches()) {
            throw new NrDefinitionRuntimeException((ErrorEnum)TaskException.TASK_TITLE_ILLEGAL);
        }
        if (!codeMatcher.matches()) {
            throw new NrDefinitionRuntimeException((ErrorEnum)TaskException.TASK_CODE_ILLEGAL);
        }
        if (taskTitle.length() > 25 || taskCode.length() > 25) {
            throw new NrDefinitionRuntimeException((ErrorEnum)TaskException.TASK_CODE_TITLE_LENGTH);
        }
    }

    public void formSchemeParamCheck(String title) {
        if (title.length() > 200) {
            throw new NrDefinitionRuntimeException((ErrorEnum)FormSchemeException.FORM_SCHEME_NAME_LENGTH_ILLEGAL);
        }
        Pattern titleCompile = Pattern.compile("[/?*\\[\\]']");
        boolean result = titleCompile.matcher(title).find();
        if (result) {
            throw new NrDefinitionRuntimeException((ErrorEnum)FormSchemeException.FORM_SCHEME_TITLE_ILLEGAL);
        }
    }
}

