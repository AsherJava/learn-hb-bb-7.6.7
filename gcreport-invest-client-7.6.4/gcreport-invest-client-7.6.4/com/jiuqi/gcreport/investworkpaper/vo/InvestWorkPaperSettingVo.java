/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.investworkpaper.vo;

import java.util.List;

public class InvestWorkPaperSettingVo {
    private String id;
    private String taskId;
    private String orgType;
    private String systemId;
    private SettingData settingData = new SettingData();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public SettingData getSettingData() {
        return this.settingData;
    }

    public void setSettingData(SettingData settingData) {
        this.settingData = settingData;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String toString() {
        return "InvestWorkPaperSettingVo{id='" + this.id + '\'' + ", taskId='" + this.taskId + '\'' + ", orgType='" + this.orgType + '\'' + ", settingData=" + this.settingData + '}';
    }

    public static class SettingData {
        private List<TzSetting> tzSetting;
        private List<ZbSetting> zbSetting;
        private List<RuleSetting> ruleSetting;

        public List<TzSetting> getTzSetting() {
            return this.tzSetting;
        }

        public void setTzSetting(List<TzSetting> tzSetting) {
            this.tzSetting = tzSetting;
        }

        public List<ZbSetting> getZbSetting() {
            return this.zbSetting;
        }

        public void setZbSetting(List<ZbSetting> zbSetting) {
            this.zbSetting = zbSetting;
        }

        public List<RuleSetting> getRuleSetting() {
            return this.ruleSetting;
        }

        public void setRuleSetting(List<RuleSetting> ruleSetting) {
            this.ruleSetting = ruleSetting;
        }

        public String toString() {
            return "SettingData{tzSetting=" + this.tzSetting + ", zbSetting=" + this.zbSetting + ", ruleSetting=" + this.ruleSetting + '}';
        }

        public static class RuleSetting {
            private String ruleId;
            private String ruleTitle;

            public String getRuleId() {
                return this.ruleId;
            }

            public void setRuleId(String ruleId) {
                this.ruleId = ruleId;
            }

            public String getRuleTitle() {
                return this.ruleTitle;
            }

            public void setRuleTitle(String ruleTitle) {
                this.ruleTitle = ruleTitle;
            }
        }

        public static class ZbSetting {
            private String zbCode;
            private String zbTitle;
            private String zbProject;
            private String zbTable;

            public String getZbCode() {
                return this.zbCode;
            }

            public void setZbCode(String zbCode) {
                this.zbCode = zbCode;
            }

            public String getZbTitle() {
                return this.zbTitle;
            }

            public void setZbTitle(String zbTitle) {
                this.zbTitle = zbTitle;
            }

            public String getZbProject() {
                return this.zbProject;
            }

            public void setZbProject(String zbProject) {
                this.zbProject = zbProject;
            }

            public String getZbTable() {
                return this.zbTable;
            }

            public void setZbTable(String zbTable) {
                this.zbTable = zbTable;
            }

            public String toString() {
                return "ZbSetting{zbCode='" + this.zbCode + '\'' + ", zbTitle='" + this.zbTitle + '\'' + ", zbProject='" + this.zbProject + '\'' + ", zbTable='" + this.zbTable + '\'' + '}';
            }
        }

        public static class TzSetting {
            private String fieldCode;
            private String fieldTitle;

            public TzSetting() {
            }

            public TzSetting(String fieldCode, String fieldTitle) {
                this.fieldCode = fieldCode;
                this.fieldTitle = fieldTitle;
            }

            public String getFieldCode() {
                return this.fieldCode;
            }

            public void setFieldCode(String fieldCode) {
                this.fieldCode = fieldCode;
            }

            public String getFieldTitle() {
                return this.fieldTitle;
            }

            public void setFieldTitle(String fieldTitle) {
                this.fieldTitle = fieldTitle;
            }
        }
    }
}

