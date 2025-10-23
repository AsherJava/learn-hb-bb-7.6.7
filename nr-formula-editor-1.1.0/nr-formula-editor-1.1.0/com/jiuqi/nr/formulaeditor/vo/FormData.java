/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nvwa.cellbook.json.CellBookDeserialize
 *  com.jiuqi.nvwa.cellbook.json.CellBookSerialize
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 */
package com.jiuqi.nr.formulaeditor.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.formulaeditor.vo.LinkData;
import com.jiuqi.nr.formulaeditor.vo.RegionData;
import com.jiuqi.nvwa.cellbook.json.CellBookDeserialize;
import com.jiuqi.nvwa.cellbook.json.CellBookSerialize;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import java.util.List;

public class FormData {
    private String key;
    @JsonSerialize(using=CellBookSerialize.class)
    @JsonDeserialize(using=CellBookDeserialize.class)
    private CellBook cellBook;
    private List<LinkData> links;
    private List<RegionData> regions;

    public List<RegionData> getRegions() {
        return this.regions;
    }

    public void setRegions(List<RegionData> regions) {
        this.regions = regions;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public CellBook getCellBook() {
        return this.cellBook;
    }

    public void setCellBook(CellBook cellBook) {
        this.cellBook = cellBook;
    }

    public List<LinkData> getLinks() {
        return this.links;
    }

    public void setLinks(List<LinkData> links) {
        this.links = links;
    }
}

