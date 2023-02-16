package model;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Access(AccessType.FIELD)
@Table(name = "mapas")
public class Mapas implements Serializable {
    //Author
    @Id
    @Column(name = "idmapa")
    int idmapa;

    @Column(name = "name")
    String name;

    @Column(name = "porcentaje_win")
    String porcentaje_win;

    @Column(name = "wins")
    int wins;

    @Column(name = "losses")
    int losses;

    @Column(name = "kd")
    float kd;

    @Column(name = "adr")
    float adr;

    @Column(name = "acs")
    float acs;

    public Mapas(int idmapa, String name, String porcentaje_win, int wins, int losses, float kd, float adr, float acs) {
        super();
        this.idmapa = idmapa;
        this.name = name;
        this.porcentaje_win = porcentaje_win;
        this.wins = wins;
        this.losses = losses;
        this.kd = kd;
        this.adr = adr;
        this.acs = acs;
    }

    public Mapas() {
        super();

    }

    public int getIdmapa() {
        return idmapa;
    }

    public void setIdmapa(int idmapa) {
        this.idmapa = idmapa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPorcentaje_win() {
        return porcentaje_win;
    }

    public void setPorcentaje_win(String porcentaje_win) {
        this.porcentaje_win = porcentaje_win;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public float getKd() {
        return kd;
    }

    public void setKd(float kd) {
        this.kd = kd;
    }

    public float getAdr() {
        return adr;
    }

    public void setAdr(float adr) {
        this.adr = adr;
    }

    public float getAcs() {
        return acs;
    }

    public void setAcs(float acs) {
        this.acs = acs;
    }

    @Override
    public String toString() {
        return "Mapas{" +
                "idmapa=" + idmapa +
                ", name='" + name + '\'' +
                ", porcentaje_win='" + porcentaje_win + '\'' +
                ", wins=" + wins +
                ", losses=" + losses +
                ", kd=" + kd +
                ", adr=" + adr +
                ", acs=" + acs +
                '}';
    }
}
