/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2;

import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class CacheSXSSFWorkbook
extends SXSSFWorkbook {
    private Map<String, Font> cellFontMap = new HashMap<String, Font>();
    private Map<String, XSSFCellStyle> xSSFCellStyleMap = new HashMap<String, XSSFCellStyle>();

    public CacheSXSSFWorkbook() {
    }

    public CacheSXSSFWorkbook(int rowAccessWindowSize) {
        super(rowAccessWindowSize);
    }

    public void addCacheCellFontMap(String styleKey, XSSFFont font) {
        this.cellFontMap.put(styleKey, font);
    }

    public Map<String, Font> getCacheCellFontMap() {
        return this.cellFontMap;
    }

    public Map<String, XSSFCellStyle> getCacheXSSFCellStyleMap() {
        return this.xSSFCellStyleMap;
    }

    public void addCacheXSSFCellStyleMap(String styleKey, XSSFCellStyle xSSFCellStyle) {
        this.xSSFCellStyleMap.put(styleKey, xSSFCellStyle);
    }
}

