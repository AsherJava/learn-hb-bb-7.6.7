/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 */
package com.jiuqi.nr.task.form.formio.dto;

import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.formio.common.ImportCellType;
import com.jiuqi.nr.task.form.formio.dto.ImportFormulaDTO;
import com.jiuqi.nr.task.form.link.dto.DataLinkSettingDTO;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportReverseResultDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final DesignFormDefine formDefine;
    private DataTableDTO dataTableDTO;
    private Map<String, DataLinkSettingDTO> links;
    private Map<String, DataFieldSettingDTO> fieldPosMap;
    private Map<String, DesignFormulaDefine> formulaPosMap;
    private Map<String, ImportCellType> cellTypeMap;
    private List<DesignFormulaDefine> parsedFormulas;
    private Map<String, ImportFormulaDTO> importFormulaDTOMap;

    public ImportReverseResultDTO(DesignFormDefine formDefine) {
        this.formDefine = formDefine;
        this.fieldPosMap = new HashMap<String, DataFieldSettingDTO>();
        this.formulaPosMap = new HashMap<String, DesignFormulaDefine>();
        this.parsedFormulas = new ArrayList<DesignFormulaDefine>();
        this.cellTypeMap = new HashMap<String, ImportCellType>();
        this.importFormulaDTOMap = new HashMap<String, ImportFormulaDTO>();
        this.links = new HashMap<String, DataLinkSettingDTO>();
    }

    public DesignFormDefine getFormDefine() {
        return this.formDefine;
    }

    public DataTableDTO getDataTableDTO() {
        return this.dataTableDTO;
    }

    public void setDataTableDTO(DataTableDTO dataTableDTO) {
        this.dataTableDTO = dataTableDTO;
    }

    public Map<String, DataLinkSettingDTO> getLinks() {
        return this.links;
    }

    public Map<String, DataFieldSettingDTO> getFieldPosMap() {
        return this.fieldPosMap;
    }

    public void setFieldPosMap(Map<String, DataFieldSettingDTO> fieldPosMap) {
        this.fieldPosMap = fieldPosMap;
    }

    public Map<String, DesignFormulaDefine> getFormulaPosMap() {
        return this.formulaPosMap;
    }

    public List<DesignFormulaDefine> getParsedFormulas() {
        return this.parsedFormulas;
    }

    public Map<String, ImportCellType> getCellTypeMap() {
        return this.cellTypeMap;
    }

    public Map<String, ImportFormulaDTO> getImportFormulaDTOMap() {
        return this.importFormulaDTOMap;
    }
}

