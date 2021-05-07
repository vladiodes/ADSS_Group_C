package Business.Controllers;

import Business.Objects.Site;
import Data.DAO.SectionsDAO;
import Data.DAO.SiteDAO;
import Data.DTO.SiteDTO;

import java.util.*;

public class Sites implements Controller<Site> {
    private HashMap<String, Site> sites;
    private List<String> sections;
    private SectionsDAO SECDAO;
    private SiteDAO SITDAO;

    public Sites() {
        this.sites = new HashMap<String, Site>();
        this.SECDAO = new SectionsDAO();
        this.SITDAO = new SiteDAO();
        this.sections = SECDAO.getAll();
    }

    public void addSite(String _ad, String _num, String _contact, String _section) throws Exception {
        if (sites.containsKey(_ad))
            throw new Exception(_ad + " already has a record in the database.");
        String Section = getSection(_section);
        Site toAdd =new Site(_ad, _num, _contact, Section);
        sites.put(_ad, toAdd);
        SITDAO.insert(toAdd.toDTO());
    }

    public void addSection(String section) throws Exception {
        for (String curr : sections)
            if (curr.equals(section))
                throw new Exception("Section already exists in the Database.");
        sections.add(section);
        SECDAO.insert(section);
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

    public List<String> getSections() {
        return sections;
    }

    @Override
    public void Load() {
        this.sections = SECDAO.getAll();
        List<SiteDTO> dtos = SITDAO.getAll();
        this.sites = new HashMap<>();
        for(SiteDTO element : dtos)
        {
            sites.put(element.address,new Site(element));
        }
    }
}
