/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.nr.customExcelBatchImport.service;

import com.jiuqi.nr.definition.facade.DataRegionDefine;
import org.apache.poi.ss.usermodel.Workbook;

public interface ICustomExcelTemplateService {
    public Workbook createTemplate(DataRegionDefine var1);
}

