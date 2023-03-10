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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idjugador")
    Jugadores jugador;
    @Column(name = "name")
    String name;
    @Column(name = "type")
    String type;

    public Armas(int idarma,Jugadores jugador, String name, String type) {
        super();
        this.idarma = idarma;
        this.name = name;
        this.type = type;
        this.jugador = jugador;
    }

    public Jugadores getJugador() {
        return jugador;
    }

    public void setJugador(Jugadores jugador) {
        this.jugador = jugador;
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
                ", jugador=" + jugador +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
