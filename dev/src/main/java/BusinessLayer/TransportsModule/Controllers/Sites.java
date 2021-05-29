package BusinessLayer.TransportsModule.Controllers;

import BusinessLayer.Interfaces.Controller;
import BusinessLayer.TransportsModule.Objects.Site;
import DataAccessLayer.SectionsDAO;
import DataAccessLayer.SiteDAO;
import DTO.SectionDTO;
import DTO.SiteDTO;

import java.util.*;

public class Sites implements Controller<Site> {
    private HashMap<String, Site> sites;
    private List<String> sections;
    private SectionsDAO SECDAO;
    private SiteDAO SITDAO;
    private static Sites instance=null;

    private Sites() {
        this.sites = new HashMap<String, Site>();
        this.SECDAO = new SectionsDAO();
        this.SITDAO = new SiteDAO();
        this.sections = SECDAO.getAll();
    }

    public static Sites getInstance() {
        if(instance==null)
            instance=new Sites();
        return instance;
    }

    public void addSite(String _ad, String _num, String _contact, String _section) throws Exception {
        if (getSite(_ad) !=null)
            throw new Exception(_ad + " already has a record in the database.");
        String Section = getSection(_section);
        if (Section == null)
            throw new Exception(Section + " Section doesn't exist in the database.");
        Site toAdd =new Site(_ad, _num, _contact, Section);
        sites.put(_ad, toAdd);
        SITDAO.insert(toAdd.toDTO());
    }

    public void addSection(String section) throws Exception {
        if(getSection(section) != null)
            throw new Exception(section+" already exists in the databsase.");
        sections.add(section);
        SECDAO.insert(section);
    }

    public Site getSite(String Ad) {
        if (sites.containsKey(Ad))
            return sites.get(Ad);
        SiteDTO DTOoutput = SITDAO.getSite(Ad);
        if(DTOoutput == null) //doesn't exist in the DB
            return null;
        else{
            Site output = new Site(DTOoutput);
            sites.put(output.getAddress(),output);
            return output;
        }
    }

    public ArrayList<Site> getSites() {
        LoadSites();
        return new ArrayList<Site>(sites.values());
    }

    public String getSection(String s) {
        for (String t : sections) {
            if (t.equals(s))
                return t;
        }
        SectionDTO output = SECDAO.getSection(s);
        if(output == null)
            return null;
        sections.add(output.name);
        return output.name;
    }

    public List<String> getSections() {
        LoadSections();
        return sections;
    }

    private void LoadSites(){
        List<SiteDTO> dtos = SITDAO.getAll();
        this.sites = new HashMap<>();
        for(SiteDTO element : dtos)
        {
            sites.put(element.address,new Site(element));
        }
    }

    private void LoadSections(){
        this.sections = SECDAO.getAll();
    }

    @Override
    public void Load() {
        LoadSections();
       LoadSites();
    }
}
