package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

public class Consumer {
    @JsonIgnore
    private long id;

    @JsonProperty("firstName")
    private String name;

    @JsonProperty("lastName")
    private String surname;

    public Consumer(){
    }

    public Consumer(long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Consumer[ID:" + id + ", Name: " + name + ", Surname: " + surname + "]";
    }

    public JSONObject toJSONObject(){
        JSONObject objects = new JSONObject();
        objects.put("lastName", getSurname());
        objects.put("firstName", getName());
        return objects;
    }
}
