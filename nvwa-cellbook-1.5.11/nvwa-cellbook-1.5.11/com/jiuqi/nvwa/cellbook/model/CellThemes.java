/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.model.CellColor;
import com.jiuqi.nvwa.cellbook.model.CellTheme;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CellThemes
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String current = "current";
    private Map<String, CellTheme> themes = new HashMap<String, CellTheme>();

    public CellThemes() {
        this.themes.put(this.current, new CellTheme());
    }

    public List<CellColor> getPalette() {
        return this.themes.get(this.current).getPalette();
    }

    public void setPalette(List<CellColor> palette) {
        this.themes.get(this.current).setPalette(palette);
    }

    public Map<String, CellTheme> getThemes() {
        return this.themes;
    }

    public String getCurrent() {
        return this.current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public void setThemes(Map<String, CellTheme> themes) {
        this.themes = themes;
    }
}

