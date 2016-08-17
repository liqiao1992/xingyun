package com.lq.xingyun.model.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2016/8/5.
 */
public class PictureBean {

    private List<PictureGirl> results;
    private boolean error;

    public List<PictureGirl> getResults() {
        return results;
    }

    public void setResults(List<PictureGirl> results) {
        this.results = results;
    }

    public static class PictureGirl {
        private String url;
        private String type;
        private String desc;
        private String who;
        private boolean used;
        private Date createdAt;
        private Date updatedAt;
        private Date publishedAt;
        private int imageWidth;
        private int imageHeight;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getImageWidth() {
            return imageWidth;
        }

        public void setImageWidth(int imageWidth) {
            this.imageWidth = imageWidth;
        }

        public int getImageHeight() {
            return imageHeight;
        }

        public void setImageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
        }

        public Date getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(Date publishedAt) {
            this.publishedAt = publishedAt;
        }
    }
}
