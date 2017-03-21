package models;

/**
 * Created by zeljko94 on 31.1.2017..
 */
public class Vrsta {
    private int id;
    private String naziv;

    public Vrsta() {
        this.id = 0;
        this.naziv = "";
    }

    public Vrsta(String naziv) {
        this.id = 0;
        this.naziv = naziv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
