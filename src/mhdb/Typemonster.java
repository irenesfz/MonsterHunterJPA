/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhdb;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sh4dowplay
 */
@Entity
@Table(name = "typemonster")
@NamedQueries({
    @NamedQuery(name = "Typemonster.findAll", query = "SELECT t FROM Typemonster t"),
    @NamedQuery(name = "Typemonster.findByIdtypemonster", query = "SELECT t FROM Typemonster t WHERE t.idtypemonster = :idtypemonster"),
    @NamedQuery(name = "Typemonster.findByTypeeng", query = "SELECT t FROM Typemonster t WHERE t.typeeng = :typeeng"),
    @NamedQuery(name = "Typemonster.findByTypeesp", query = "SELECT t FROM Typemonster t WHERE t.typeesp = :typeesp")})
public class Typemonster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtypemonster")
    private Integer idtypemonster;
    @Column(name = "typeeng")
    private String typeeng;
    @Column(name = "typeesp")
    private String typeesp;
    @OneToMany(mappedBy = "type")
    private Collection<Monster> monsterCollection;

    public Typemonster() {
    }

    public Typemonster(Integer idtypemonster) {
        this.idtypemonster = idtypemonster;
    }

    public Integer getIdtypemonster() {
        return idtypemonster;
    }

    public void setIdtypemonster(Integer idtypemonster) {
        this.idtypemonster = idtypemonster;
    }

    public String getTypeeng() {
        return typeeng;
    }

    public void setTypeeng(String typeeng) {
        this.typeeng = typeeng;
    }

    public String getTypeesp() {
        return typeesp;
    }

    public void setTypeesp(String typeesp) {
        this.typeesp = typeesp;
    }

    public Collection<Monster> getMonsterCollection() {
        return monsterCollection;
    }

    public void setMonsterCollection(Collection<Monster> monsterCollection) {
        this.monsterCollection = monsterCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtypemonster != null ? idtypemonster.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Typemonster)) {
            return false;
        }
        Typemonster other = (Typemonster) object;
        if ((this.idtypemonster == null && other.idtypemonster != null) || (this.idtypemonster != null && !this.idtypemonster.equals(other.idtypemonster))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mhdb.Typemonster[ idtypemonster=" + idtypemonster + " ]";
    }
    
}
