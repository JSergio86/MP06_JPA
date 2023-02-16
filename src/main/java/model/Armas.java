package model;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Access(AccessType.FIELD)
@Table(name = "armas")
public class Armas implements Serializable {
    @Id
    @Column(name = "idarma")
    int idarma;
    @Column(name = "name")
    String name;
    @Column(name = "type")
    String type;

    public Armas(int idarma, String name, String type) {
        super();
        this.idarma = idarma;
        this.name = name;
        this.type = type;
    }

    public Armas() {
        super();
    }

    public int getIdarma() {
        return idarma;
    }

    public void setIdarma(int idarma) {
        this.idarma = idarma;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Armas{" +
                "idarma=" + idarma +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
