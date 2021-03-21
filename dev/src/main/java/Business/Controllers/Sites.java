package Business.Controllers;

import Business.Objects.Site;

import java.util.ArrayList;
import java.util.HashMap;

public class Sites implements Controller<Site> {
    HashMap<String, Site> sites;
    ArrayList<String> sections = new ArrayList<String>();

    public void addSite(String _ad, int _num, String _contact, String _section) throws Exception {
        if(sites.containsKey(_ad))
            throw new Exception(_ad+" already has a record in the database.");
        sites.put(_ad, new Site(_ad,_num,_contact,_section));
    }

    public void addSection(String section){
        sections.add(section);
    }
}
