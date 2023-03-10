package model;

import controller.ArmasController;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Access(AccessType.FIELD)
@Table(name = "jugadores")

public class Jugadores implements Serializable {
  //Article
  @Id
  @Column(name = "idjugador")
  int idjugador;

  @Column(name = "rank")
  String rank;

  @Column(name = "wins")
  int wins;

  @Column(name = "kills")
  int kills;

  @Column(name = "deaths")
  int deaths;

  @Column(name = "assists")
  int assists;

  @Column(name = "scoreround")
  float scoreround;

  @Column(name = "kad")
  float kad;

  @Column(name = "killsround")
  float killsround;

  @Column(name = "plants")
  int plants;

  @Column(name = "firstbloods")
  int firstbloods;

  @Column(name = "clutches")
  int clutches;

  @Column(name = "flawless")
  int flawless;

  @Column(name = "aces")
  int aces;


  public Jugadores(int idjugador, String rank, int wins, int kills, int deaths, int assists, float scoreround, float kad, float killsround, int plants, int firstbloods, int clutches, int flawless, int aces) {
    super();
    this.idjugador = idjugador;
    this.rank = rank;
    this.wins = wins;
    this.kills = kills;
    this.deaths = deaths;
    this.assists = assists;
    this.scoreround = scoreround;
    this.kad = kad;
    this.killsround = killsround;
    this.plants = plants;
    this.firstbloods = firstbloods;
    this.clutches = clutches;
    this.flawless = flawless;
    this.aces = aces;
  }

  public Jugadores() {
    super();

  }


  public String getrank() {
    return rank;
  }

  public void setrank(String rank) {
    this.rank = rank;
  }

  public int getIdjugador() {
    return idjugador;
  }

  public void setIdjugador(int idjugador) {
    this.idjugador = idjugador;
  }

  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }

  public int getWins() {
    return wins;
  }

  public void setWins(int wins) {
    this.wins = wins;
  }

  public int getKills() {
    return kills;
  }

  public void setKills(int kills) {
    this.kills = kills;
  }

  public int getDeaths() {
    return deaths;
  }

  public void setDeaths(int deaths) {
    this.deaths = deaths;
  }

  public int getAssists() {
    return assists;
  }

  public void setAssists(int assists) {
    this.assists = assists;
  }

  public float getScoreround() {
    return scoreround;
  }

  public void setScoreround(float scoreround) {
    this.scoreround = scoreround;
  }

  public float getKad() {
    return kad;
  }

  public void setKad(float kad) {
    this.kad = kad;
  }

  public float getKillsround() {
    return killsround;
  }

  public void setKillsround(float killsround) {
    this.killsround = killsround;
  }

  public int getPlants() {
    return plants;
  }

  public void setPlants(int plants) {
    this.plants = plants;
  }

  public int getFirstbloods() {
    return firstbloods;
  }

  public void setFirstbloods(int firstbloods) {
    this.firstbloods = firstbloods;
  }

  public int getClutches() {
    return clutches;
  }

  public void setClutches(int clutches) {
    this.clutches = clutches;
  }

  public int getFlawless() {
    return flawless;
  }

  public void setFlawless(int flawless) {
    this.flawless = flawless;
  }

  public int getAces() {
    return aces;
  }

  public void setAces(int aces) {
    this.aces = aces;
  }

  @Override
  public String toString() {
    return "Jugadores{" +
            "idjugador=" + idjugador +
            ", rank='" + rank + '\'' +
            ", wins=" + wins +
            ", kills=" + kills +
            ", deaths=" + deaths +
            ", assists=" + assists +
            ", scoreround=" + scoreround +
            ", kad=" + kad +
            ", killsround=" + killsround +
            ", plants=" + plants +
            ", firstbloods=" + firstbloods +
            ", clutches=" + clutches +
            ", flawless=" + flawless +
            ", aces=" + aces +
            '}';
  }
}
