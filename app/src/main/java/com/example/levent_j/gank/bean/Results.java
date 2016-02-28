package com.example.levent_j.gank.bean;

/**
 * Created by levent_j on 16-2-27.
 */
public class Results {
    private String _id;
    private String _ns;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public void setId(String id) {
        this._id = id;
    }

    public String getId() {
        return _id;
    }

    public void setNs(String ns) {
        this._ns = ns;
    }

    public String getNs() {
        return _ns;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWho() {
        return who;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
