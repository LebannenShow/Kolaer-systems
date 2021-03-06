/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.kolaer.api.mvp.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Danilov
 */
@XmlRootElement
public class DbApplication {
    private Integer id;
    private String titleApp;
    private boolean statusApp;

    public DbApplication() {
    }

    public DbApplication(Integer id) {
        this.id = id;
    }

    public DbApplication(Integer id, String titleApp, boolean statusApp) {
        this.id = id;
        this.titleApp = titleApp;
        this.statusApp = statusApp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleApp() {
        return titleApp;
    }

    public void setTitleApp(String titleApp) {
        this.titleApp = titleApp;
    }

    public boolean getStatusApp() {
        return statusApp;
    }

    public void setStatusApp(boolean statusApp) {
        this.statusApp = statusApp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbApplication)) {
            return false;
        }
        DbApplication other = (DbApplication) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.kolaer.server.dao.DbApplication[ id=" + id + " ]";
    }
    
}
