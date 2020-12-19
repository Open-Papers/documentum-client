package com.openpapers.documentumclient.resource.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.openpapers.documentumclient.resource.util.DateDeserializer;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import com.openpapers.documentumclient.resource.util.DateSerializer;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Document
public class GreenDocument {

    @Indexed
    private String entityID;
    @Indexed
    private String title;
    @Indexed
    private String description;
    private String docAbstract;
    private String contentKey;
    private String contentLink;
    private String contentSize;
    private Collection<Comment> comments;
    @Indexed
    private Collection<Tag> tags;
    private String uploadedBy;
    private String updatedBy;
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date uploadedDate;
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date updatedDate;
    private boolean active = true;

    @Override
    public String toString() {
        return "GreenDocument{" +
                "entityID=" + entityID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", docAbstract='" + docAbstract + '\'' +
                ", contentKey='" + contentKey + '\'' +
                ", contentLink='" + contentLink + '\'' +
                ", contentSize='" + contentSize + '\'' +
                ", comments=" + comments +
                ", tags=" + tags +
                ", uploadedBy='" + uploadedBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", uploadedDate=" + uploadedDate +
                ", updatedDate=" + updatedDate +
                ", active=" + active +
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

    public String getDocAbstract() {
        return docAbstract;
    }

    public void setDocAbstract(String docAbstract) {
        this.docAbstract = docAbstract;
    }

    public String getContentKey() {
        return contentKey;
    }

    public void setContentKey(String contentKey) {
        this.contentKey = contentKey;
    }

    public String getContentLink() {
        return contentLink;
    }

    public void setContentLink(String contentLink) {
        this.contentLink = contentLink;
    }

    public String getContentSize() {
        return contentSize;
    }

    public void setContentSize(String contentSize) {
        this.contentSize = contentSize;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GreenDocument that = (GreenDocument) o;
        return entityID.equals(that.entityID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityID);
    }
}
