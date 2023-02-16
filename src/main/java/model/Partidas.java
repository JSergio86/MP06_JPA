package model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Access(AccessType.FIELD)
@Table(name = "partidas")
public class Partidas implements Serializable {
    //Magazine
    @Id
    @Column(name = "idpartida")
    int idpartida;

    /*@OneToMany
     @JoinColumn(name="id_revista", referencedColumnName="id_revista")
     @Cascade(CascadeType.ALL)
     private List<Article> articles = new ArrayList<Article> ();

      */

    @OneToMany
    @JoinColumn(name="idjugador", referencedColumnName="idjugador")
    @Column(name = "idjugador")
    int idjugador;

    @Column(name = "idmapa")
    int idmapa;

    @Column(name = "type")
    String type;

    @Column(name = "result")
    String result;


    public Partidas(int idpartida, int idjugador, int idmapa, String type, String result) {
        super();
        this.idpartida = idpartida;
        this.idjugador = idjugador;
        this.idmapa = idmapa;
        this.type = type;
        this.result = result;
    }

    public Partidas() {
        super();
    }

    public int getIdpartida() {
        return idpartida;
    }

    public void setIdpartida(int idpartida) {
        this.idpartida = idpartida;
    }

    public int getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(int idjugador) {
        this.idjugador = idjugador;
    }

    public int getIdmapa() {
        return idmapa;
    }

    public void setIdmapa(int idmapa) {
        this.idmapa = idmapa;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Partidas{" +
                "idpartida=" + idpartida +
                ", idjugador=" + idjugador +
                ", idmapa=" + idmapa +
                ", type='" + type + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
