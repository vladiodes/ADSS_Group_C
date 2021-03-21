package Business.Controllers;

import Business.Objects.Site;
import java.util.*;

public class Sites implements Controller<Site> {
    private HashMap<String, Site> sites;
    private ArrayList<String> sections;

    public Sites() {
        this.sites = new HashMap<String, Site>();
        this.sections = new ArrayList<String>();
        sections.add("North");
        sections.add("Center");
        sections.add("South");
    }

    public void addSite(String _ad, int _num, String _contact, String _section) throws Exception {
        if (sites.containsKey(_ad))
            throw new Exception(_ad + " already has a record in the database.");
        sites.put(_ad, new Site(_ad, _num, _contact, _section));
    }

    public void addSection(String section) {
        sections.add(section);
    }
}
