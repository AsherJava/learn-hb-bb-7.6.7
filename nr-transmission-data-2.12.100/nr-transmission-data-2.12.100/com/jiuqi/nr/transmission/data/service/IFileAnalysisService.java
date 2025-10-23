/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils$ZipSubFile
 */
package com.jiuqi.nr.transmission.data.service;

import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.transmission.data.dto.AnalysisDTO;
import com.jiuqi.nr.transmission.data.dto.AnalysisParam;
import com.jiuqi.nr.transmission.data.intf.AnalysisVO;
import java.util.Map;

public interface IFileAnalysisService {
    public AnalysisDTO analysisParam(Map<String, ZipUtils.ZipSubFile> var1, AnalysisParam var2) throws Exception;

    public AnalysisVO analysisParam(AnalysisVO var1) throws Exception;
}

