package com.openpapers.documentumclient.resource.search;

import com.openpapers.documentumclient.resource.model.Tag;

import java.util.Collection;

public class SearchParameter {
    private String entityID;
    private String title;
    private String description;
    private Collection<Tag> tags;

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

    public Collection<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }

}
