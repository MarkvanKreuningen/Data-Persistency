package P1;

import P1.data.entity.Reiziger;
import P1.data.repository.IReizigerDao;
import P1.data.repository.ReizigerDao;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        IReizigerDao repository = new ReizigerDao();
        List<Reiziger> reizigers = repository.findAll();
        System.out.println("Alle reizigers:");
        for (Reiziger r : reizigers) {
            StringBuilder naam = new StringBuilder();
            naam.append(r.getVoorletters()).append(".").append(" ");
            if (r.getTussenvoegsel() != null) {
                naam.append(r.getTussenvoegsel()).append(" ");
            }
            naam.append(r.getAchternaam());
            System.out.printf(" #%s %s (%s)\n", r.getReiziger_id(), naam, r.getGeboortedatum());
        }
    }
}
