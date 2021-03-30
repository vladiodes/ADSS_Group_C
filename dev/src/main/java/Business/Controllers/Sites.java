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
        try{
        addSite("Nahariyya",052123123,"Motti",sections.get(0));
        addSite("Tel-Aviv",052555555,"Hadar",sections.get(1));
        }catch(Exception e){}
    }

    public void addSite(String _ad, int _num, String _contact, String _section) throws Exception {
        if (sites.containsKey(_ad))
            throw new Exception(_ad + " already has a record in the database.");
        sites.put(_ad, new Site(_ad, _num, _contact, _section));
    }

    public void addSection(String section) {
        sections.add(section);
    }

    public Site getSite(String Ad) throws Exception{
        if(!sites.containsKey(Ad))
            throw new Exception(Ad + " doesn't exist in the database.");
        else  return sites.get(Ad);
    }

    public ArrayList<Site> getSites(){
        return new ArrayList<Site>(sites.values());
    }

    public String getSection(String s) throws Exception {
        for(String t : sections) {
            if (t.equals(s))
                return t;
        }
        throw new Exception("Section doesn't exist.");
    }
    
    public ArrayList<String> getSections(){
        return sections;
    }
}
