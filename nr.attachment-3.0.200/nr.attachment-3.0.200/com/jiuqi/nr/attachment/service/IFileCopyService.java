/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.service;

import com.jiuqi.nr.attachment.exception.FileCopyException;
import com.jiuqi.nr.attachment.input.FileCopyFixedParam;
import com.jiuqi.nr.attachment.input.FileCopyFloatParam;
import com.jiuqi.nr.attachment.message.FixedFieldAndGroupInfo;
import com.jiuqi.nr.attachment.message.FloatFieldAndGroupInfo;
import java.util.List;

public interface IFileCopyService {
    public List<FixedFieldAndGroupInfo> batchCopyFile(FileCopyFixedParam var1) throws FileCopyException;

    public List<FloatFieldAndGroupInfo> batchCopyFile(FileCopyFloatParam var1) throws FileCopyException;
}

