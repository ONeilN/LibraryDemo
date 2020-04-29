package com.nugumanov.librarydemo.domain;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String filename;

    private boolean isReverse;

    public Image() {
    }

    public Integer getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public boolean isReverse() {
        return isReverse;
    }

    public void setFilename(String filename) {

        this.filename = filename;
    }

    public void setReverse(boolean isReverse) {
        this.isReverse = isReverse;
    }
}
