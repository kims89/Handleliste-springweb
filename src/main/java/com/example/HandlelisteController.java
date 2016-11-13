package com.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//webcontroller for handleliste-klassen
@Controller
public class HandlelisteController  {

    @Autowired
    HandlelisteRepository handlelisteRepository;

    @Autowired
    BrukerRepository brukerRepository;

//    redirect slik at hvis man skriver bare http://localhost:9000/ blir man redirectet til home.html
    @RequestMapping("/")
    public String redirect() {
        return "redirect:home";
    }

//    requestmapping til home.html
    @RequestMapping("/home")
    public String test(Model model) {
        model.addAttribute("HandleListe", handlelisteRepository.findAll());

        return "home";
    }

//metode som lar lærer opprette brukere i mongoDB til innlogging i handleliste
    @RequestMapping(value="/nyBruker", method = RequestMethod.POST)
    public String nyBruker(@RequestParam(value = "brukernavn") String brukernavn, @RequestParam("passord") String passord){
        Bruker brukeren = null;
        if (brukernavn != "") {
            brukeren = new Bruker(brukernavn, passord,"ROLE_ADMIN");
            brukerRepository.save(brukeren);

            System.out.println("Ny bruker opprettet med brukernavn: "+brukernavn+" og passord: "+passord);
        }
        else {
            System.out.println("Brukerfelt må ha en verdi");
        }

        return "redirect:login";
    }



    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public @ResponseBody Handleliste save(@RequestParam(value = "vare") String vare){
        Handleliste varen = null;
        int NesteNummer = 0;
        // finner alle entrys i mongodb å henter getSortering. Nestenummer blir da siste oppføring + 1
        //dette nummeret blir kun brukt til sortering
        for (Handleliste handleliste : handlelisteRepository.findAll()) {
            if (!handlelisteRepository.findAll().isEmpty()){
                if(handleliste.getSortering()>NesteNummer)
                {
                    NesteNummer = handleliste.getSortering();
                }
            }
        }
        //Her fjernes alle spesialtegn slik at vi hindrer HTML injection.
        //Vi har valgt å ikke gjøre dette på brukernavn/passord pga de ikke har tilgang til noe uansett.
        varen = new Handleliste(NesteNummer+1, vare.replaceAll("[^\\wåæøÅÆØ]", ""));
        handlelisteRepository.save(varen);
        System.out.println(varen +" er lagt til i handelisten");

        return varen;
    }

    //Denne requestmappingen gir tilgang til loginsiden.
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

// requestmapping som sletter vare (tar inn et parameter)
    @RequestMapping(value="/slettVare", method = RequestMethod.POST)
    public String sletterVare(@RequestParam("vareid") String vareid) {
        System.out.println("id: "+vareid);
        //forløkke for å printe ut varenavn
        for (Handleliste handleliste : handlelisteRepository.findByid(vareid)) {
            System.out.println("Vare: '" + handleliste.vare + "' er slettet fra handlelisten ");
        }
        handlelisteRepository.delete(vareid);

        return "redirect:home";

    }

    // requestmapping som endrer vare (tar to parameter)
    @RequestMapping(value="/endreVare", method = RequestMethod.POST)
    public String endrerVare(@RequestParam("vareid") String vareid, @RequestParam("varen") String varen) {

        //Denne finner gammel handlelisteobjektet med nevnt vareid.
        Handleliste handleliste=handlelisteRepository.findOne(vareid);
        //Her endres varenavnet.
        handleliste.vare = varen.replaceAll("[^\\wåæøÅÆØ]", "");
        //Her lagres varen til MongoDB
        handlelisteRepository.save(handleliste);

        return "redirect:home";
    }


    // requestmapping som endrer sortering (tar to parameter)
    @RequestMapping(value="/sortereVare", method = RequestMethod.POST)
    public String sortereVare(@RequestParam("vareid") String vareid, @RequestParam("sortering") int sortering) {
        String Varenavn;
        //forløkke som finner vare basert på vareid(første parameter) også setter nytt varenavn(andre parameter)
        for (Handleliste handleliste : handlelisteRepository.findByid(vareid)) {
            Varenavn = handleliste.getVare();
            handleliste.sortering = sortering;

            handlelisteRepository.save(handleliste);
            System.out.println(Varenavn+" nytt sorterings nr: "+ sortering );

        }
        return "redirect:home";
    }


//lister ut alle mongodb oppføringene i json-format
    @RequestMapping(path = "/list/json", method = RequestMethod.GET)
    public @ResponseBody List<Handleliste> getJson(){

        //lister ut i json sortert etter sorteringsnr
        return handlelisteRepository.findAll(new Sort(Sort.Direction.DESC, "sortering"));

    }

    @RequestMapping(path = "/addmin", method = RequestMethod.GET)
    public @ResponseBody List<Handleliste> addmin(){

        //lister ut i json sortert etter sorteringsnr
        return handlelisteRepository.findAll(new Sort(Sort.Direction.DESC, "sortering"));

    }


}



