package com.sreejith.cucumberdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class StudentVo {


    public StudentVo(){}

    public StudentVo(String id, String name, String country, String subject, String university) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.subject = subject;
        this.university = university;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    @Override
    public String toString()
    {

        return String.format("%s - %s - %s - %s - %s",id,name,country,subject,university);
    }

    private String subject;
    private String university;
    private String id;
    private String name;
    private String country;
}
