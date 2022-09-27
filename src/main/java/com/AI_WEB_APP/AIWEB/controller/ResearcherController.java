package com.AI_WEB_APP.AIWEB.controller;

import com.AI_WEB_APP.AIWEB.model.Researcher;
import com.AI_WEB_APP.AIWEB.resporitory.ResearcherRepo;
import com.AI_WEB_APP.AIWEB.service.ResearcherService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/*
 * This class uses the methods inside ResearcherService to create mapping
 * Mapping is functionality in sprint boot to allow the html to connect via it
 * Controller class - middle man?
 * */
//annotations used from SpringBoot
@CrossOrigin
@RestController
@RequestMapping(value = "api/v2") // use this 'url' path to connect to the html file

public class ResearcherController {
    private ResearcherService researcherService; // create ResearcherService object

    // constructor
    public ResearcherController(ResearcherService r, ResearcherRepo repo) {
        this.researcherService = r;
    }

    // query to returns the list of researchers
    // creates mapping to allow the html to connect to the database
    // you can see this by the @GetMapping annotation
    @GetMapping("researchers")
    // returns list of users
    public List<Researcher> listOfResearchers() {
        return researcherService.returnResearcher();
    }

    // creates mapping to allow the html to connect to the database
    // you can see this by the @GetMapping annotation
    @GetMapping("name/{researcherName}") // use this to connect to the html eg: name/Kauthar (check line 13 in index.js)
    public List<Researcher> getByName(@PathVariable("researcherName") String name) {
        return researcherService.findSurname(name);
    }

    @GetMapping("institutionCount")
    public Map<String, Integer> getInstitutionCount() {
        Map<String, Integer> unSortedMap = researcherService.findAllInstitutionsCountBy();
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

    @GetMapping("institutionCount/{institutionName}")
    public int getInstitutionCountByName(@PathVariable("institutionName") String name) {
       Map<String, Integer> uni = researcherService.findAllInstitutionsCountBy();
        int count = uni.get(name);
        return count;
    }

    @GetMapping("ratingCount")
    public Map<String, Integer> getRatingCount() {
        return researcherService.findAllRatingCount();
    }

    @GetMapping("primaryFieldCount")
    public Map<String, Integer> getPrimaryFieldCount() {

        Map<String, Integer> unSortedMap = researcherService.findAllPrimaryFieldsCount();
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

    @GetMapping("secondaryFieldCount")
    public Map<String, Integer> getSecondaryFieldCount() {
        Map<String, Integer> unSortedMap = researcherService.findAllSecondaryFieldsCount();
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

    @GetMapping("specialisation/{spec}")
    public List<Researcher> getSpec(@PathVariable("spec") String specs){
        return researcherService.findSpec(specs);

    }


    /*@GetMapping("researcherInstitution/{Institution}")
    public List<Researcher> getByInstitution(@PathVariable("Institution") String institutionString)
    {
        return researcherService.findResearcherInstitution(institutionString);
    }


    /*@GetMapping("rating/{Rating}")
    public List<Researcher> getByRating(@PathVariable("Rating") String ratingChar){
        return researcherService.findRating(ratingChar);
    }*/

    @GetMapping("filter/title/{title}/institution/{instit}/rating/{rating}/field/{field}")
    public List<Researcher> getFiltered(@PathVariable("title") String title, @PathVariable("instit")String inst, @PathVariable("rating") String rting, @PathVariable("field") String fld){
        
        List<Researcher> all = researcherService.returnResearcher();
        List<Researcher> filt = new ArrayList<>();


        for (int i = 0; i < all.size(); i ++){

            Researcher temp = all.get(i);
            if (researcherService.checkSpec(temp, title) || (title.equals("All"))){

                if (temp.getInstitution().equals(inst) || (inst.equals("All"))){

                    if (temp.getRating().equals(rting) || (rting.equals("All"))){

                        if (temp.getPrimaryFields().contains(fld) || (fld.equals("All")) ){

                            //all fields match the specified criteria
                            //then add researcher to filtered list
                            filt.add(temp);
                        }
                          
                    }
                }
            }

        } // end for

    return filt;

        
        
    }

    //code for the institution table on graph page
    @GetMapping("ratingForInstitution")
    public Map<String, Map<String, Integer>> getRatingForInstitution() {
        //return researcherService.findRatingForInstitution();
        Map<String, Map<String, Integer>> unSortedMap = researcherService.findRatingForInstitution();
        LinkedHashMap<String, Map<String, Integer>> sortedMap = new LinkedHashMap<>();
        unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

    //code for the institution table on graph page
    @GetMapping("ratingForPrimaryFields")
    public Map<String, Map<String, Integer>> getRatingForPrimaryFields() {
        //return researcherService.findRatingForPrimaryFields();
        Map<String, Map<String, Integer>> unSortedMap = researcherService.findRatingForPrimaryFields();
        LinkedHashMap<String, Map<String, Integer>> sortedMap = new LinkedHashMap<>();
        unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

    //code for the institution table on graph page
    @GetMapping("ratingForSecondaryFields")
    public Map<String, Map<String, Integer>> getRatingForSecondaryFields() {
        //return researcherService.findRatingForSecondaryFields();
        Map<String, Map<String, Integer>> unSortedMap = researcherService.findRatingForSecondaryFields();
        LinkedHashMap<String, Map<String, Integer>> sortedMap = new LinkedHashMap<>();
        unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

    @GetMapping("totalResearchersPerYear")
    public Map<Integer, Integer> getTotalResearchersPerYear() {
        return researcherService.totalResearchersPerYear();
    }

    @GetMapping("ratingsPerYear")
    public Map<Integer, Map<String, Integer>> getTotalRatingsPerYear() {
        return researcherService.findRatingPerYear();
    }

    @GetMapping("specialisationsCount")
    public Map<String, Integer> getSpecialisationCount() {
        return researcherService.countForSpecialisations();
    }

    @GetMapping("ratingsPerSpecialisations")
    public Map<String, Map<String, Integer>> getRatingsPerSpecialisations() {
        //return researcherService.findRatingForSpecialisations();
        Map<String, Map<String, Integer>> unSortedMap = researcherService.findRatingForSpecialisations();
        LinkedHashMap<String, Map<String, Integer>> sortedMap = new LinkedHashMap<>();
        unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }



}
