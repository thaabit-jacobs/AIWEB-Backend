package com.AI_WEB_APP.AIWEB.controller;

import com.AI_WEB_APP.AIWEB.model.Researcher;
import com.AI_WEB_APP.AIWEB.model.ResearchArticles;
import com.AI_WEB_APP.AIWEB.service.ResearchArticlesService;
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
@RequestMapping(value = "api/v1") // use this 'url' path to connect to the html file

public class ResearchArticlesController {

    private ResearchArticlesService researchArticlesService;

    public ResearchArticlesController(ResearchArticlesService r) {
        this.researchArticlesService = r;
    }

    @GetMapping("researchArticles")
    public List<ResearchArticles> listArticles() {
        return researchArticlesService.returnAll();
    }

    @GetMapping("articles/search/Title/{titleName}")
    public List<ResearchArticles> findByTitles(@PathVariable("titleName") String tname) {
        return researchArticlesService.getByTitle(tname);
    };

    @GetMapping("articles/search/ISSN/{issnNo}")
    public List<ResearchArticles> searchISSN(@PathVariable("issnNo") String issn) {
        return researchArticlesService.getByISSN(issn);
    }

    @GetMapping("articles/search/Author/{authname}")
    public List<ResearchArticles> searchAuthor(@PathVariable("authname") String author) {
        return researchArticlesService.getByAuthors(author);
    }

    @GetMapping("articles/search/Keyword/{keyword}")
    public List<ResearchArticles> searchKeyword(@PathVariable("keyword") String keyword) {
        return researchArticlesService.getByKeywords(keyword);
    }


    @GetMapping("articles/fetchbyid/{id}")
    public ResearchArticles returnArticleInfo(@PathVariable("id") Long id) {
        return researchArticlesService.returnById(id);
    }
    /*
     * //THIS IS WHERE THE PROBLEM IS, DOI INCLUDES /
     * 
     * @GetMapping("doi/{doiCode}")
     * public ResearchArticles searchDOI(@PathVariable ("doiCode") String doi){
     * return researchArticlesService.getByDOI(doi);
     * }
     */
    @GetMapping("sourceTitleVsCited")
    // returns list of users
    public Map<String, Integer> listOfSourceTitleVsCited() {
        Map<String, Integer> map = researchArticlesService.sourceTitleVsCited();
        Map<String, Integer> newMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= 350) {
                newMap.put(entry.getKey(), entry.getValue());
            }

        }
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        newMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

    @GetMapping("mostPopularResearchTitles")
    // returns list of users
    public Map<String, Integer> listOfMostPopularResearchTitles() {
        Map<String, Integer> map = researchArticlesService.mostPopularResearchTitles();
        Map<String, Integer> newMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= 10) {
                newMap.put(entry.getKey(), entry.getValue());
            }

        }

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        newMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;

    }
    @GetMapping("mostUsedArticlesPerYear")
    // returns list of users
    public Map<String, Integer> listOfMostUsedArticlesPerYear() {
        Map<String, Integer> map = researchArticlesService.mostUsedArticlesPerYear();
        Map<String, Integer> newMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            int year = 0;
            if (entry.getKey() == ""){
                continue;
            }
            else{
                String[] getYear =  entry.getKey().split("\\.");
                year = Integer.valueOf(getYear[0]);
            }
            String year1 = "1980-1985";
            String year2 = "1986-1990";
            String year3 = "1991-1995";
            String year4 = "1996-2000";
            String year5 = "2001-2005";
            String year6 = "2006-2010";
            String year7 = "2011-2015";
            String year8 = "2016-2022";
            if (year >= 1980 && year <= 1985 ) {
                if (newMap.containsKey(year1)){
                    int currentValue = newMap.get(year1);
                    currentValue = currentValue + entry.getValue();
                    newMap.put(year1, currentValue);
                }
                else{
                    newMap.put(year1, entry.getValue());
                }
                newMap.put(year1, entry.getValue());
            }
            else if (year >= 1986 && year <= 1990 ) {
                if (newMap.containsKey(year2)){
                    int currentValue = newMap.get(year2);
                    currentValue = currentValue + entry.getValue();
                    newMap.put(year2, currentValue);
                }
                else{
                    newMap.put(year2, entry.getValue());
                }
                newMap.put(year2, entry.getValue());
            }
            else if (year >= 1991 && year <= 1995 ) {
                if (newMap.containsKey(year3)){
                    int currentValue = newMap.get(year3);
                    currentValue = currentValue + entry.getValue();
                    newMap.put(year3, currentValue);
                }
                else{
                    newMap.put(year3, entry.getValue());
                }
                newMap.put(year3, entry.getValue());
            }
            else if (year >= 1996 && year <= 2000 ) {
                if (newMap.containsKey(year4)){
                    int currentValue = newMap.get(year4);
                    currentValue = currentValue + entry.getValue();
                    newMap.put(year4, currentValue);
                }
                else{
                    newMap.put(year4, entry.getValue());
                }
                newMap.put(year4, entry.getValue());
            }
            else if (year >= 2001 && year <= 2005 ) {
                if (newMap.containsKey(year5)){
                    int currentValue = newMap.get(year5);
                    currentValue = currentValue + entry.getValue();
                    newMap.put(year5, currentValue);
                }
                else{
                    newMap.put(year5, entry.getValue());
                }
                newMap.put(year5, entry.getValue());
            }
            else if (year >= 2006 && year <= 2010 ) {
                if (newMap.containsKey(year6)){
                    int currentValue = newMap.get(year6);
                    currentValue = currentValue + entry.getValue();
                    newMap.put(year6, currentValue);
                }
                else{
                    newMap.put(year6, entry.getValue());
                }
                newMap.put(year6, entry.getValue());
            }
            else if (year >= 2011 && year <= 2015 ) {
                if (newMap.containsKey(year7)){
                    int currentValue = newMap.get(year7);
                    currentValue = currentValue + entry.getValue();
                    newMap.put(year7, currentValue);
                }
                else{
                    newMap.put(year7, entry.getValue());
                }
                newMap.put(year7, entry.getValue());
            }
            else if (year >= 2016 && year <= 2022 ) {
                if (newMap.containsKey(year8)){
                    int currentValue = newMap.get(year8);
                    currentValue = currentValue + entry.getValue();
                    newMap.put(year8, currentValue);
                }
                else{
                    newMap.put(year8, entry.getValue());
                }
                newMap.put(year8, entry.getValue());
            }

        }
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        newMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;

//        return newMap;

    }

    @GetMapping("articles/totalPublicationsPerYear")
    public Map<String, Integer> returnTotalPublicationsPerYear() {
        Map<String, Integer> unSortedMap = researchArticlesService.getTotalPublicationsPerYear();
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

}
