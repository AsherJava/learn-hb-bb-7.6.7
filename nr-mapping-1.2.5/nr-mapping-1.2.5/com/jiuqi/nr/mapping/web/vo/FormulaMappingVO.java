/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.web.vo;

import com.jiuqi.nr.mapping.web.dto.FormulaMappingDTO;
import com.jiuqi.nr.mapping.web.vo.CommonTreeNode;
import com.jiuqi.nr.mapping.web.vo.SelectOptionVO;
import java.util.List;

public class FormulaMappingVO {
    private String msKey;
    private List<CommonTreeNode> nodes;
    private List<SelectOptionVO> formulaSchemes;
    private List<FormulaMappingDTO> mappings;

    public String getMsKey() {
        return this.msKey;
    }

    public void setMsKey(String msKey) {
        this.msKey = msKey;
    }

    public List<CommonTreeNode> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<CommonTreeNode> nodes) {
        this.nodes = nodes;
    }

    public List<SelectOptionVO> getFormulaSchemes() {
        return this.formulaSchemes;
    }

    public void setFormulaSchemes(List<SelectOptionVO> formulaSchemes) {
        this.formulaSchemes = formulaSchemes;
    }

    public List<FormulaMappingDTO> getMappings() {
        return this.mappings;
    }

    public void setMappings(List<FormulaMappingDTO> mappings) {
        this.mappings = mappings;
    }
}

