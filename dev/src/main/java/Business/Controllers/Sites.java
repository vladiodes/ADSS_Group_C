package Business.Controllers;

import Business.Objects.Site;
import java.util.*;

public class Sites implements Controller<Site> {
    private HashMap<String, Site> sites;
    private ArrayList<String> sections;

    public Sites() {
        this.sites = new HashMap<String, Site>();
        this.sections = new ArrayList<String>();
    }

    public void addSite(String _ad, int _num, String _contact, String _section) throws Exception {
        if (sites.containsKey(_ad))
            throw new Exception(_ad + " already has a record in the database.");
        String Section = getSection(_section);
        sites.put(_ad, new Site(_ad, _num, _contact, Section));
    }

    public void addSection(String section) throws Exception {
        for (String curr : sections)
            if (curr.equals(section))
                throw new Exception("Section already exists in the Database.");
        sections.add(section);
    }

    public Site getSite(String Ad) throws Exception {
        if (!sites.containsKey(Ad))
            throw new Exception(Ad + " doesn't exist in the database.");
        else return sites.get(Ad);
    }

    public ArrayList<Site> getSites() {
        return new ArrayList<Site>(sites.values());
    }

    public String getSection(String s) throws Exception {
        for (String t : sections) {
            if (t.equals(s))
                return t;
        }
        throw new Exception("Section doesn't exist.");
    }

    public ArrayList<String> getSections() {
        return sections;
    }
}
