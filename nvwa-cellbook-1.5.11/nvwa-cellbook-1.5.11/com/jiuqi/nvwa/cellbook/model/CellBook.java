/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import com.jiuqi.nvwa.cellbook.exception.CellBookException;
import com.jiuqi.nvwa.cellbook.model.BookStyle;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.cellbook.model.CellSheetGroup;
import com.jiuqi.nvwa.cellbook.model.CellThemes;
import com.jiuqi.nvwa.cellbook.model.DocProps;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CellBook
implements Serializable {
    private static final long serialVersionUID = 1L;
    private DocProps docProps = new DocProps();
    private List<CellSheetGroup> groups = new ArrayList<CellSheetGroup>();
    private List<CellSheet> sheets = new ArrayList<CellSheet>();
    private CellThemes theme = new CellThemes();
    private BookStyle bookStyle = new BookStyle(this);

    public CellSheetGroup createSheetGroup(String title, String name) {
        if (StringUtils.isEmpty(name)) {
            name = "";
        }
        if (StringUtils.isEmpty(title)) {
            title = "";
        }
        String finalName = name;
        Optional<CellSheetGroup> findFirst = this.groups.stream().filter(group -> group.getName().equals(finalName)).findFirst();
        if (findFirst.isPresent()) {
            throw new CellBookException("group code must unique!");
        }
        CellSheetGroup cellSheetGroup = new CellSheetGroup(title, name, this);
        this.groups.add(cellSheetGroup);
        return cellSheetGroup;
    }

    public CellSheet createSheet(String title, String code, int rowNum, int colNum) {
        return this.createSheet(title, code, rowNum, colNum, null);
    }

    public CellSheet createSheet(String title, String name, int rowNum, int colNum, String groupName) {
        Optional<CellSheetGroup> findGroup;
        if (StringUtils.isEmpty(name)) {
            name = "";
        }
        if (StringUtils.isEmpty(title)) {
            title = "";
        }
        if (rowNum <= 0) {
            throw new CellBookException("rowNum must > 0 !");
        }
        if (colNum <= 0) {
            throw new CellBookException("colNum must > 0 !");
        }
        String finalName = name;
        Optional<CellSheet> findFirst = this.sheets.stream().filter(sheet -> sheet.getName().equals(finalName)).findFirst();
        if (findFirst.isPresent()) {
            throw new CellBookException("sheet code must unique!");
        }
        if (!StringUtils.isEmpty(groupName) && !(findGroup = this.groups.stream().filter(group -> group.getName().equals(groupName)).findFirst()).isPresent()) {
            throw new CellBookException("groupCode not find !");
        }
        CellSheet cellSheet = new CellSheet(title, name, rowNum, colNum, groupName, this);
        this.sheets.add(cellSheet);
        return cellSheet;
    }

    public DocProps getDocProps() {
        return this.docProps;
    }

    public void setDocProps(DocProps docProps) {
        this.docProps = docProps;
    }

    public List<CellSheetGroup> getGroups() {
        return this.groups;
    }

    public void setGroups(List<CellSheetGroup> groups) {
        this.groups = groups;
    }

    public List<CellSheet> getSheets() {
        return this.sheets;
    }

    public void setSheets(List<CellSheet> sheets) {
        this.sheets = sheets;
    }

    public BookStyle getBookStyle() {
        return this.bookStyle;
    }

    public void setBookStyle(BookStyle bookStyle) {
        this.bookStyle = bookStyle;
    }

    public CellThemes getTheme() {
        return this.theme;
    }

    public void setTheme(CellThemes theme) {
        this.theme = theme;
    }
}

