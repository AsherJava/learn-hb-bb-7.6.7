/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.splittable.other;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.splittable.other.LinkObj;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@JsonIgnoreProperties(ignoreUnknown=true)
public class LinkHeap {
    LinkObj firstLink = null;
    LinkObj lastLink = null;
    TreeMap<Integer, List<LinkObj>> map = new TreeMap();

    public void addObj(LinkObj obj) {
        List<LinkObj> linkList = this.map.get(obj.posy);
        if (null == linkList) {
            linkList = new ArrayList<LinkObj>();
            this.map.put(obj.posy, linkList);
        }
        linkList.add(obj);
        if (null == this.firstLink) {
            this.firstLink = obj;
        }
        this.lastLink = obj;
    }

    public List<LinkObj> getList() {
        ArrayList<LinkObj> list = new ArrayList<LinkObj>();
        this.map.values().stream().forEach(list::addAll);
        return list;
    }

    public LinkObj getLast() {
        return this.lastLink;
    }

    public LinkObj getFirst() {
        return this.firstLink;
    }

    public List<LinkHeap> splitHeap() {
        ArrayList<LinkHeap> heaps = new ArrayList<LinkHeap>();
        int lastCount = -1;
        Set<Map.Entry<Integer, List<LinkObj>>> entrySet = this.map.entrySet();
        LinkHeap heap = null;
        for (Map.Entry<Integer, List<LinkObj>> e : entrySet) {
            if (lastCount == -1 || lastCount != e.getValue().size()) {
                heap = new LinkHeap();
                heaps.add(heap);
            }
            List<LinkObj> list = e.getValue();
            lastCount = list.size();
            list.stream().forEach(heap::addObj);
        }
        return heaps;
    }

    public LinkObj getFirstLink() {
        return this.firstLink;
    }

    public void setFirstLink(LinkObj firstLink) {
        this.firstLink = firstLink;
    }

    public LinkObj getLastLink() {
        return this.lastLink;
    }

    public void setLastLink(LinkObj lastLink) {
        this.lastLink = lastLink;
    }

    public TreeMap<Integer, List<LinkObj>> getMap() {
        return this.map;
    }

    public void setMap(TreeMap<Integer, List<LinkObj>> map) {
        this.map = map;
    }
}

