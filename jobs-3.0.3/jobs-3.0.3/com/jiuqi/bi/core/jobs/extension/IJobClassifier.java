/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.extension;

import com.jiuqi.bi.core.jobs.extension.item.FolderItem;
import com.jiuqi.bi.core.jobs.extension.item.JobItem;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public interface IJobClassifier {
    public List<FolderItem> getFolders(String var1, ClassifierContext var2) throws Exception;

    public List<JobItem> getItems(String var1, ClassifierContext var2) throws Exception;

    public JobItem getJobItem(String var1) throws Exception;

    public Path locate(FolderItem var1) throws Exception;

    public Path locate(JobItem var1) throws Exception;

    public static class Path {
        private List<String> guids = new ArrayList<String>();
        private List<String> titles = new ArrayList<String>();

        public void addTailLevel(String folderGuid, String folderTitle) {
            this.guids.add(folderGuid);
            this.titles.add(folderTitle);
        }

        public void addHeadLevel(String folderGuid, String folderTitle) {
            this.guids.add(0, folderGuid);
            this.titles.add(0, folderTitle);
        }

        public String toString() {
            return StringUtils.join(this.titles.iterator(), (String)"/");
        }

        public String[] getPathGuids() {
            return this.guids.toArray(new String[0]);
        }

        public String[] getPathTitles() {
            return this.titles.toArray(new String[0]);
        }
    }

    public static class ClassifierContext {
        private String user;

        public void setUser(String user) {
            this.user = user;
        }

        public String getUser() {
            return this.user;
        }
    }
}

