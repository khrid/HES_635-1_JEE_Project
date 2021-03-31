package ch.hevs.businessobject;

import javax.persistence.*;

@Embeddable
public class Country {

    // name
    @Column(name="country_name") // vu que c'est embedded, le nom risque d'être redondant avec une table appelante
    private String name;

    public Country() {

    }
    public Country(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
