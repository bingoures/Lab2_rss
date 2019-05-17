package com.lab.rss1;

import com.rometools.rome.feed.synd.SyndContent;

public class RSSItem {
    private String title;
    private String description;
    private String link;
    private String pubDate;

    public RSSItem(String title, String description, String link, String pubDate) {
        this.title = title;
        if(description.length() < 233)
            this.description = description;
        else
            this.description = description.substring(0, 230) + "...";
        this.link = link;
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.replaceAll("\\<.*?>","");
    }

}
