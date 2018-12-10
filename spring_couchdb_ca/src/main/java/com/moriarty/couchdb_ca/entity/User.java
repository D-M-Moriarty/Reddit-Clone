package com.moriarty.couchdb_ca.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.Attachment;
import org.ektorp.support.CouchDbDocument;
import org.springframework.stereotype.Component;

//@JsonIgnoreProperties({"id", "revision"})
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class User extends CouchDbDocument {
    @JsonProperty("_id")
    private String id;
    @JsonProperty("_rev")
    private String revision;
    private String name;
    private String image;
    private String createdAt;
    private String type = "user";
    // TODO add user interests ass some list structure
    @JsonProperty("_id")
    public String getId() {
        return id;
    }
    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }
    @JsonProperty("_rev")
    public String getRevision() {
        return revision;
    }
    @JsonProperty("_rev")
    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image, String contentType, String name) {
        Attachment inline = new Attachment("theId", image, contentType);
        addInlineAttachment(inline);
        this.image = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", revision='" + revision + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
