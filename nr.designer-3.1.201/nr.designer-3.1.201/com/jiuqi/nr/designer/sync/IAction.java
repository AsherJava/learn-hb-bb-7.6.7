/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonSubTypes
 *  com.fasterxml.jackson.annotation.JsonSubTypes$Type
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.sync;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.nr.designer.sync.action.AddAction;
import com.jiuqi.nr.designer.sync.action.AddColAction;
import com.jiuqi.nr.designer.sync.action.AddRowAction;
import com.jiuqi.nr.designer.sync.action.ColTitleAction;
import com.jiuqi.nr.designer.sync.action.DeleteAction;
import com.jiuqi.nr.designer.sync.action.DeleteColAction;
import com.jiuqi.nr.designer.sync.action.DeleteRowAction;
import com.jiuqi.nr.designer.sync.action.HiddenColAction;
import com.jiuqi.nr.designer.sync.action.HiddenRowAction;
import com.jiuqi.nr.designer.sync.action.MergeAction;
import com.jiuqi.nr.designer.sync.action.MergeColAction;
import com.jiuqi.nr.designer.sync.action.MergeRowAction;
import com.jiuqi.nr.designer.sync.action.PasteAction;
import com.jiuqi.nr.designer.sync.action.RowTitleAction;
import com.jiuqi.nr.designer.sync.action.ShowHiddenColAction;
import com.jiuqi.nr.designer.sync.action.ShowHiddenRowAction;
import com.jiuqi.nr.designer.sync.action.UnMergeAction;
import com.jiuqi.nvwa.grid2.Grid2Data;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="name", visible=true)
@JsonSubTypes(value={@JsonSubTypes.Type(value=AddAction.class, name="add"), @JsonSubTypes.Type(value=AddColAction.class, name="addcol"), @JsonSubTypes.Type(value=AddRowAction.class, name="addrow"), @JsonSubTypes.Type(value=DeleteAction.class, name="delete"), @JsonSubTypes.Type(value=DeleteColAction.class, name="deletecol"), @JsonSubTypes.Type(value=DeleteRowAction.class, name="deleterow"), @JsonSubTypes.Type(value=MergeAction.class, name="merge"), @JsonSubTypes.Type(value=MergeColAction.class, name="mergecol"), @JsonSubTypes.Type(value=MergeRowAction.class, name="mergerow"), @JsonSubTypes.Type(value=UnMergeAction.class, name="unmerge"), @JsonSubTypes.Type(value=PasteAction.class, name="paste"), @JsonSubTypes.Type(value=ColTitleAction.class, name="hiddencoltitle"), @JsonSubTypes.Type(value=RowTitleAction.class, name="hiddenrowtitle"), @JsonSubTypes.Type(value=HiddenColAction.class, name="hiddencol"), @JsonSubTypes.Type(value=ShowHiddenColAction.class, name="showhiddencol"), @JsonSubTypes.Type(value=HiddenRowAction.class, name="hiddenrow"), @JsonSubTypes.Type(value=ShowHiddenRowAction.class, name="showhiddenrow")})
@JsonIgnoreProperties(ignoreUnknown=true)
public interface IAction {
    public void run(Grid2Data var1);
}

