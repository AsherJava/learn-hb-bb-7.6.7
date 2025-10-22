/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.dto;

public class MidstorePlanTaskDTO {
    private String id;
    private String plantasktitle;
    private String plantasktype;
    private String description;
    private Boolean enable;
    private String advancedsetting;
    private String starttime;
    private Boolean endtimeenable;
    private String endtime;
    private String plantaskcircletype;
    private String taskexecutetimesetting;
    private String nextexecutetime;
    private String executeplan;
    private String modelName;
    private String user;
    private String group;
    private String groupTitle;
    private String ordinal;
    private String password;
    private boolean editable = true;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlantasktitle() {
        return this.plantasktitle;
    }

    public void setPlantasktitle(String plantasktitle) {
        this.plantasktitle = plantasktitle;
    }

    public String getPlantasktype() {
        return this.plantasktype;
    }

    public void setPlantasktype(String plantasktype) {
        this.plantasktype = plantasktype;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnable() {
        return this.enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getAdvancedsetting() {
        return this.advancedsetting;
    }

    public void setAdvancedsetting(String advancedsetting) {
        this.advancedsetting = advancedsetting;
    }

    public String getStarttime() {
        return this.starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public Boolean getEndtimeenable() {
        return this.endtimeenable;
    }

    public void setEndtimeenable(Boolean endtimeenable) {
        this.endtimeenable = endtimeenable;
    }

    public String getEndtime() {
        return this.endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getPlantaskcircletype() {
        return this.plantaskcircletype;
    }

    public void setPlantaskcircletype(String plantaskcircletype) {
        this.plantaskcircletype = plantaskcircletype;
    }

    public String getTaskexecutetimesetting() {
        return this.taskexecutetimesetting;
    }

    public void setTaskexecutetimesetting(String taskexecutetimesetting) {
        this.taskexecutetimesetting = taskexecutetimesetting;
    }

    public String getNextexecutetime() {
        return this.nextexecutetime;
    }

    public void setNextexecutetime(String nextexecutetime) {
        this.nextexecutetime = nextexecutetime;
    }

    public String getExecuteplan() {
        return this.executeplan;
    }

    public void setExecuteplan(String executeplan) {
        this.executeplan = executeplan;
    }

    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupTitle() {
        return this.groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}

