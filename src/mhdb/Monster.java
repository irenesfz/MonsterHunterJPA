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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author sh4dowplay
 */
@Entity
@Table(name = "monster")
@NamedQueries({
    @NamedQuery(name = "Monster.findAll", query = "SELECT m FROM Monster m"),
    @NamedQuery(name = "Monster.findByIdmonster", query = "SELECT m FROM Monster m WHERE m.idmonster = :idmonster"),
    @NamedQuery(name = "Monster.findByName", query = "SELECT m FROM Monster m WHERE m.name = :name"),
    @NamedQuery(name = "Monster.findBySize", query = "SELECT m FROM Monster m WHERE m.size = :size"),
    @NamedQuery(name = "Monster.findByImg", query = "SELECT m FROM Monster m WHERE m.img = :img"),
    @NamedQuery(name = "Monster.findBySizemin", query = "SELECT m FROM Monster m WHERE m.sizemin = :sizemin"),
    @NamedQuery(name = "Monster.findBySizemax", query = "SELECT m FROM Monster m WHERE m.sizemax = :sizemax")})
public class Monster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmonster")
    private Integer idmonster;
    @Column(name = "name")
    private String name;
    @Column(name = "size")
    private String size;
    @Column(name = "img")
    private String img;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "sizemin")
    private Float sizemin;
    @Column(name = "sizemax")
    private Float sizemax;
    @JoinColumn(name = "type", referencedColumnName = "idtypemonster")
    @ManyToOne
    private Typemonster type;

    public Monster() {
    }

    public Monster(Integer idmonster) {
        this.idmonster = idmonster;
    }

    public Integer getIdmonster() {
        return idmonster;
    }

    public void setIdmonster(Integer idmonster) {
        this.idmonster = idmonster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Float getSizemin() {
        return sizemin;
    }

    public void setSizemin(Float sizemin) {
        this.sizemin = sizemin;
    }

    public Float getSizemax() {
        return sizemax;
    }

    public void setSizemax(Float sizemax) {
        this.sizemax = sizemax;
    }

    public Typemonster getType() {
        return type;
    }

    public void setType(Typemonster type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmonster != null ? idmonster.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Monster)) {
            return false;
        }
        Monster other = (Monster) object;
        if ((this.idmonster == null && other.idmonster != null) || (this.idmonster != null && !this.idmonster.equals(other.idmonster))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mhdb.Monster[ idmonster=" + idmonster + " ]";
    }
    
}
