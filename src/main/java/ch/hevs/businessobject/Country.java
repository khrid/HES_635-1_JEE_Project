package ch.hevs.businessobject;

import javax.persistence.*;

@Embeddable
public class Country {

    // name
    @Column(name="country_name", nullable = false) // vu que c'est embedded, le nom risque d'être redondant avec une table appelante
    private String name;
    // name
    @Column(name="country_code", nullable = false) // vu que c'est embedded, le nom risque d'être redondant avec une table appelante
    private String code;

    public Country() {

    }
    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                "code='" + code + '\'' +
                '}';
    }
}
