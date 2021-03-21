package Business.Controllers;

import Business.Objects.Site;

import java.util.ArrayList;
import java.util.HashMap;

public class Sites implements Controller<Site> {
    HashMap<String, Site> sites;
    ArrayList<String> sections = new ArrayList<String>();

    public void addSite(Site site){
        sites.put(site.getAddress(),site);
    }

    public void addSection(String section){
        sections.add(section);
    }
}
