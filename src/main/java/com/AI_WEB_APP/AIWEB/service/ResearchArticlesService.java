package com.AI_WEB_APP.AIWEB.service;

import com.AI_WEB_APP.AIWEB.model.ResearchArticles;
import com.AI_WEB_APP.AIWEB.resporitory.ResearchArticlesRepo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This class implements the ResearcherRepo interface
 * Uses this interface to create methods to manipulate the database and create queries
 *
 * */

@Service //spring boot functionality
public class ResearchArticlesService {
    private ResearchArticlesRepo researchArticlesRepo;

    //constructor
    public ResearchArticlesService(ResearchArticlesRepo r){
        this.researchArticlesRepo = r;
    }

    //query to create a new researcher
    public void createResearcher(ResearchArticles r)
    {
         researchArticlesRepo.save(r); //repo has built-in functions to save the user to the database
    }

    //query to return all ResearchArticles

    public List<ResearchArticles> returnAll(){
        return researchArticlesRepo.findAll();
    }

    public List<ResearchArticles> getByAuthors(String authname){
        return researchArticlesRepo.findByAuthorsContaining(authname);
    }

    public List<ResearchArticles> getByTitle(String titles){
        return researchArticlesRepo.findByArticleTitleContaining(titles);
    }

    public List<ResearchArticles> getByISSN(String issn){
        return researchArticlesRepo.findByIssnContaining(issn);
    }

    public List<ResearchArticles> getByKeywords(String keyword){
        return researchArticlesRepo.findByKeywordsContaining(keyword);
    }

    /*
    public ResearchArticles getByDOI(String doi){
        return researchArticlesRepo.findBydoi(doi);
    }
    */


    public Map<String, Integer> sourceTitleVsCited()
    {
        Map<String, Integer> map = new HashMap<>(); //create a new map
        List<ResearchArticles> listResearchers = researchArticlesRepo.findAll(); //return ALL the researchers in the database

        //loop through the list of researchers
        //grab a hold of every researcher
        for (ResearchArticles r : listResearchers )
        {
            String sourceTitle = r.getSourceTitle(); //get the institution associated with the researcher
            //if the institution exists within the map, increment the current count value
            if (map.containsKey(sourceTitle))
            {
                int count = map.get(sourceTitle); //get the current count
                count = count + r.getCitedCount(); //increment count
                map.put(sourceTitle, count); //insert new count into the map

            }
            // if the institution doesn't exist in the map, add it too the map
            else{
                map.put(sourceTitle, r.getCitedCount());
            }
        }

        return map; //return the map
    }

    public Map<String, Integer> mostPopularResearchTitles(){
        Map<String, Integer> map = new HashMap<>(); //create a new map
        List<ResearchArticles> listResearchers = researchArticlesRepo.findAll(); //return ALL the researchers in the database

        //loop through the list of researchers
        //grab a hold of every researcher
        for (ResearchArticles r : listResearchers )
        {
            String sourceTitle = r.getSourceTitle(); //get the institution associated with the researcher
            //if the institution exists within the map, increment the current count value
            if (map.containsKey(sourceTitle))
            {
                int count = map.get(sourceTitle); //get the current count
                count++; //increment count
                map.put(sourceTitle, count); //insert new count into the map

            }
            // if the institution doesn't exist in the map, add it too the map
            else{
                map.put(sourceTitle, 1);
            }
        }

        return map; //return the map
    }

    public Map<String, Integer> mostUsedArticlesPerYear()
    {
        Map<String, Integer> map = new HashMap<>(); //create a new map
        List<ResearchArticles> listResearchers = researchArticlesRepo.findAll(); //return ALL the researchers in the database

        //loop through the list of researchers
        //grab a hold of every researcher
        for (ResearchArticles r : listResearchers )
        {
            String year = r.getYear(); //get the institution associated with the researcher
            //if the institution exists within the map, increment the current count value
            if (map.containsKey(year))
            {
                int count = map.get(year); //get the current count
                count = count + r.getSinceUsageCount(); //increment count
                map.put(year, count); //insert new count into the map

            }
            // if the institution doesn't exist in the map, add it too the map
            else{
                map.put(year, r.getSinceUsageCount());
            }
        }

        return map; //return the map
    }
    //find by ID and return the details of the article
    public ResearchArticles returnById(Long id){
        return researchArticlesRepo.findByid(id);
    }

    public String returnYearAsString(String y){
        String[] getYear =  y.split("\\.");
        return getYear[0];
    }
    //get the total number of publications per year
    public Map<String, Integer> getTotalPublicationsPerYear()
    {
        Map<String, Integer> totalPubYear = new HashMap<>();
        List<ResearchArticles> listResearchers = researchArticlesRepo.findAll(); //return ALL the researchers in the database

        for(ResearchArticles r : listResearchers)
        {
            String year = this.returnYearAsString(r.getYear());
            //year = this.returnYearAsString(year);
            if (totalPubYear.containsKey(year))
            {
                int currentTotal = totalPubYear.get(year); //
                currentTotal = currentTotal + 1; //add 1 to current total
                totalPubYear.put(year, currentTotal);
            }
            else
            {
                totalPubYear.put(year, 1);
            }
        }
        totalPubYear.remove("");
        return totalPubYear;
    }


}
