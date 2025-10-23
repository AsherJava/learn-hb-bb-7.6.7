/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 */
package com.jiuqi.nr.task.form.formio.dto;

import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.formio.dto.ImportBaseDataDTO;
import com.jiuqi.nr.task.form.link.dto.DataLinkSettingDTO;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import java.util.ArrayList;
import java.util.List;

public class FormImportResult {
    private List<DesignFormDefine> designFormDefines = new ArrayList<DesignFormDefine>();
    private List<DesignFormGroupLink> designFormGroupLinks = new ArrayList<DesignFormGroupLink>();
    private List<DataLinkSettingDTO> linkSettings = new ArrayList<DataLinkSettingDTO>();
    private List<DataFieldSettingDTO> fieldSettings = new ArrayList<DataFieldSettingDTO>();
    private List<DataTableDTO> dataTables = new ArrayList<DataTableDTO>();
    private List<DesignFormulaDefine> designFormulaDefines = new ArrayList<DesignFormulaDefine>();
    private List<ImportBaseDataDTO> baseDatas = new ArrayList<ImportBaseDataDTO>();
    private List<DesignDataRegionDefine> dataRegionDefines = new ArrayList<DesignDataRegionDefine>();

    public List<DesignFormDefine> getDesignFormDefines() {
        return this.designFormDefines;
    }

    public void setDesignFormDefines(List<DesignFormDefine> designFormDefines) {
        this.designFormDefines = designFormDefines;
    }

    public List<DataLinkSettingDTO> getLinkSettings() {
        return this.linkSettings;
    }

    public void setLinkSettings(List<DataLinkSettingDTO> linkSettings) {
        this.linkSettings = linkSettings;
    }

    public List<DataFieldSettingDTO> getFieldSettings() {
        return this.fieldSettings;
    }

    public void setFieldSettings(List<DataFieldSettingDTO> fieldSettings) {
        this.fieldSettings = fieldSettings;
    }

    public List<DataTableDTO> getDataTables() {
        return this.dataTables;
    }

    public void setDataTables(List<DataTableDTO> dataTables) {
        this.dataTables = dataTables;
    }

    public List<DesignFormulaDefine> getDesignFormulaDefines() {
        return this.designFormulaDefines;
    }

    public void setDesignFormulaDefines(List<DesignFormulaDefine> designFormulaDefines) {
        this.designFormulaDefines = designFormulaDefines;
    }

    public List<ImportBaseDataDTO> getBaseDatas() {
        return this.baseDatas;
    }

    public void setBaseDatas(List<ImportBaseDataDTO> baseDatas) {
        this.baseDatas = baseDatas;
    }

    public List<DesignDataRegionDefine> getDataRegionDefines() {
        return this.dataRegionDefines;
    }

    public void setDataRegionDefines(List<DesignDataRegionDefine> dataRegionDefines) {
        this.dataRegionDefines = dataRegionDefines;
    }

    public List<DesignFormGroupLink> getDesignFormGroupLinks() {
        return this.designFormGroupLinks;
    }

    public void setDesignFormGroupLinks(List<DesignFormGroupLink> designFormGroupLinks) {
        this.designFormGroupLinks = designFormGroupLinks;
    }
}

