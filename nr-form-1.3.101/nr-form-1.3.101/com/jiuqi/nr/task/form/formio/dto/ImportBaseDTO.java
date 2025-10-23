/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.dto;

import com.jiuqi.nr.task.form.formio.common.ImportCellType;
import com.jiuqi.nr.task.form.formio.dto.ImportFieldDTO;

public class ImportBaseDTO {
    private int posx;
    private int posy;
    private ImportCellType cellType;
    private ImportFieldDTO cellAttr = new ImportFieldDTO();

    public ImportFieldDTO getCellAttr() {
        return this.cellAttr;
    }

    public void setCellAttr(ImportFieldDTO cellAttr) {
        this.cellAttr = cellAttr;
    }

    public int getPosx() {
        return this.posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return this.posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public ImportCellType getCellType() {
        return this.cellType;
    }

    public void setCellType(ImportCellType cellType) {
        this.cellType = cellType;
    }
}

