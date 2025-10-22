/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.service;

import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.para.parser.JIOParamParser;

public interface SingleFileParserService {
    public SingleFile getSingleFile(String var1) throws SingleFileException;

    public JIOParamParser getParaParaser(String var1) throws Exception;

    public JIOParamParser getParaParaserByTaskDir(String var1, SingleFile var2) throws SingleFileException;
}

