package com.hien.ketnoiviet.model;

public class Like {
    private int idreact;
    private int idpost;
    private int iduser;
    private int status;

    public Like() {
    }

    public Like(int idreact, int idpost, int iduser, int status) {
        this.idreact = idreact;
        this.idpost = idpost;
        this.iduser = iduser;
        this.status = status;
    }

    public int getIdreact() {
        return idreact;
    }

    public void setIdreact(int idreact) {
        this.idreact = idreact;
    }

    public int getIdpost() {
        return idpost;
    }

    public void setIdpost(int idpost) {
        this.idpost = idpost;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
