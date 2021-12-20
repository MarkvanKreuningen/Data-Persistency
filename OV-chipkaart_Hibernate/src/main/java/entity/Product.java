package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Entity
//@Table(name = "product")
public class Product {
//    @Id
//    @Column(name = "product_nummer")
    private int productNumber;
    private String naam;
    private String beschrijving;
    private double prijs;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
//    @JoinTable(name = "ov_chipkaart_product",
//        joinColumns = @JoinColumn(name = "product_nummer"),
//        inverseJoinColumns = @JoinColumn(name = "kaart_nummer"))
    private List<OVChipkaart> ovChipkaarts;

    private List<Integer> kaartnummers;

    public Product(int productNumber, String naam, String beschrijving, double prijs, List<Integer> kaartnummers) {
        this.productNumber = productNumber;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.kaartnummers = kaartnummers;
    }

    public Product(int productNumber, String naam, String beschrijving, double prijs) {
        this.productNumber = productNumber;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product() {
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public List<OVChipkaart> getOvChipkaarts() {
        return ovChipkaarts;
    }

    public void setOvChipkaarts(List<OVChipkaart> ovChipkaarts) {
        this.ovChipkaarts = ovChipkaarts;
    }

    public List<Integer> getKaartnummers() {
        return kaartnummers;
    }

    public void setKaartnummers(List<Integer> kaartnummers) {
        this.kaartnummers = kaartnummers;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productNumber=" + productNumber +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                ", kaartnummers=" + kaartnummers +
                '}';
    }
}
