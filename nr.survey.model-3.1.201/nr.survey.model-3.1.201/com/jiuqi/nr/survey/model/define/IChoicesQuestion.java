/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.define;

import com.jiuqi.nr.survey.model.Choice;
import com.jiuqi.nr.survey.model.ChoicesByUrl;
import com.jiuqi.nr.survey.model.OtherChoice;
import java.util.List;

public interface IChoicesQuestion {
    public List<Choice> getChoices();

    public void setChoices(List<Choice> var1);

    public List<Choice> getChoiceFormulas();

    public void setChoiceFormulas(List<Choice> var1);

    public ChoicesByUrl getChoicesByUrl();

    public void setChoicesByUrl(ChoicesByUrl var1);

    public boolean isShowOtherItem();

    public String getOtherPlaceholder();

    public String getOtherText();

    public OtherChoice getOther();

    public void setOther(OtherChoice var1);

    public String getFilterFormula();

    public void setFilterFormula(String var1);
}

