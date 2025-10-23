/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.vo;

import com.jiuqi.nr.calibre2.api.IReferenceCalibre;
import java.util.List;

public class ReferenceCalibreVO {
    private String ownKey;
    private String ownTitle;
    private String ownGroup;
    private String ownGroupName;
    private String calibreDefineKey;
    private String calibreDefineCode;
    private String calibreDataCode;
    private List<String> calibreKeys;
    private boolean enableDelete;
    private String calibreTitle;

    public String getOwnKey() {
        return this.ownKey;
    }

    public void setOwnKey(String ownKey) {
        this.ownKey = ownKey;
    }

    public String getOwnTitle() {
        return this.ownTitle;
    }

    public void setOwnTitle(String ownTitle) {
        this.ownTitle = ownTitle;
    }

    public String getOwnGroup() {
        return this.ownGroup;
    }

    public void setOwnGroup(String ownGroup) {
        this.ownGroup = ownGroup;
    }

    public String getOwnGroupName() {
        return this.ownGroupName;
    }

    public void setOwnGroupName(String ownGroupName) {
        this.ownGroupName = ownGroupName;
    }

    public String getCalibreDefineKey() {
        return this.calibreDefineKey;
    }

    public void setCalibreDefineKey(String calibreDefineKey) {
        this.calibreDefineKey = calibreDefineKey;
    }

    public String getCalibreDefineCode() {
        return this.calibreDefineCode;
    }

    public void setCalibreDefineCode(String calibreDefineCode) {
        this.calibreDefineCode = calibreDefineCode;
    }

    public String getCalibreDataCode() {
        return this.calibreDataCode;
    }

    public void setCalibreDataCode(String calibreDataCode) {
        this.calibreDataCode = calibreDataCode;
    }

    public String getCalibreTitle() {
        return this.calibreTitle;
    }

    public void setCalibreTitle(String calibreTitle) {
        this.calibreTitle = calibreTitle;
    }

    public List<String> getCalibreKeys() {
        return this.calibreKeys;
    }

    public void setCalibreKeys(List<String> calibreKeys) {
        this.calibreKeys = calibreKeys;
    }

    public boolean isEnableDelete() {
        return this.enableDelete;
    }

    public void setEnableDelete(boolean enableDelete) {
        this.enableDelete = enableDelete;
    }

    public static ReferenceCalibreVO getInstance(IReferenceCalibre referenceCalibre, boolean enableDelete) {
        ReferenceCalibreVO vo = new ReferenceCalibreVO();
        vo.setOwnKey(referenceCalibre.ownKey());
        vo.setOwnTitle(referenceCalibre.ownTitle());
        vo.setOwnGroup(referenceCalibre.ownGroupCode());
        vo.setOwnGroupName(referenceCalibre.ownGroupName());
        vo.setCalibreDataCode(referenceCalibre.calibreDataCode());
        vo.setCalibreDefineKey(referenceCalibre.calibreDefineKey());
        vo.setEnableDelete(enableDelete);
        return vo;
    }
}

