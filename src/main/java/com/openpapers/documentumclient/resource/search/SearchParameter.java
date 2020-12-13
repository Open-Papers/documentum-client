package com.openpapers.documentumclient.resource.search;

import java.util.Collection;

public class SearchParameter {
    private String entityID;
    private String title;
    private String description;
    private Collection<String> tags;

    @Override
    public String toString() {
        return "SearchParameter{" +
                "entityID=" + entityID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                '}';
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

}
