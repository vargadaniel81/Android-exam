package hu.technocool.todolist.model;

import java.io.Serializable;

public class ToDo implements Serializable {

    private int id;
    private String done;
    private String urgent;
    private String name;
    private String time;

    public ToDo() {
        id = -1;
    }

    public ToDo(String name, String time) {
        this.name = name;
        this.time = time;
        id = -1;
    }

    public ToDo(int id, String name, String time, String urgent) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.urgent = urgent;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
