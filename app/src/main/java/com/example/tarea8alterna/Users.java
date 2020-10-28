package com.example.tarea8alterna;

public class Users {

    private String id, recordatorio, fecha;

    public Users() {
    }

    public Users(String id, String recordatorio, String fecha) {
        this.id = id;
        this.recordatorio = recordatorio;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
