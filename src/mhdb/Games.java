/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhdb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author sh4dowplay
 */
@Entity
@Table(name = "games")
@NamedQueries({
    @NamedQuery(name = "Games.findAll", query = "SELECT g FROM Games g"),
    @NamedQuery(name = "Games.findByIdgames", query = "SELECT g FROM Games g WHERE g.idgames = :idgames"),
    @NamedQuery(name = "Games.findByName", query = "SELECT g FROM Games g WHERE g.name = :name")})
public class Games implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idgames")
    private Integer idgames;
    @Column(name = "name")
    private String name;

    public Games() {
    }

    public Games(Integer idgames) {
        this.idgames = idgames;
    }

    public Integer getIdgames() {
        return idgames;
    }

    public void setIdgames(Integer idgames) {
        this.idgames = idgames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgames != null ? idgames.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Games)) {
            return false;
        }
        Games other = (Games) object;
        if ((this.idgames == null && other.idgames != null) || (this.idgames != null && !this.idgames.equals(other.idgames))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mhdb.Games[ idgames=" + idgames + " ]";
    }
    
}
