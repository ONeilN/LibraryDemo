package com.nugumanov.librarydemo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String fileName;

    private String transFieName;

    public Image() {
    }

    public Image(String fileName) {
        this.fileName = fileName;
    }

    public Image(String fileName, String transFieName) {
        this.fileName = fileName;
        this.transFieName = transFieName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTransFieName() {
        return transFieName;
    }

    public void setTransFieName(String transFieName) {
        this.transFieName = transFieName;
    }
}
