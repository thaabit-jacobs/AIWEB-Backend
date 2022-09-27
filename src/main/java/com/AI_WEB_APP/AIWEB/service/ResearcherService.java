package com.AI_WEB_APP.AIWEB.service;

import com.AI_WEB_APP.AIWEB.model.Researcher;
import com.AI_WEB_APP.AIWEB.resporitory.ResearcherRepo;
import org.springframework.stereotype.Service;

import java.util.*;

/*
* This class implements the ResearcherRepo interface
* Uses this interface to create methods to manipulate the database and create queries
*
* */
@Service //spring boot functionality

public class ResearcherService {
    private ResearcherRepo researcherRepo;

    //constructor
    public ResearcherService(ResearcherRepo r){
        this.researcherRepo = r;
    }


    //query to create a new researcher
    public Researcher createResearcher(Researcher r)
    {
        return researcherRepo.save(r); //repo has built-in functions to save the user to the database
    }

    //get the specialisation of the researcher and return true or false if it contains the spec
    public boolean checkSpec(Researcher researcher, String spec)
    {
        boolean bool = false;
        String specString = researcher.getSpecialisations(); //get the spec of the given researcher object
        String[] specArray = specString.split(";"); //split the string

        //loop through the string array
        for (String s: specArray){
            //if the spec is inside the array, change the boolean to true
            if ((s.trim()).equals(spec))
            {
                bool = true;
            }
        }


        return bool;
    }

    //returns all the researchers in the database
    public List<Researcher> returnResearcher(){
        return researcherRepo.findAll(); //finds all researchers and returns a list of researchers, built-in functions from repo using spring boot
    }

    //query to find the researcher by surname
    public List<Researcher> findSurname(String surname)
    {
        return researcherRepo.findBySurnameContaining(surname);
    }

    public List<Researcher> findSpec(String specs){
        
        return researcherRepo.findBySpecialisationsContaining(specs);
    }

        //returns researchers at a given institution 
   /*  public List<Researcher> findResearcherInstitution(String institution)
    {
    
        return researcherRepo.findByInstitution(institution);

    } */

    /*public List<Researcher> findRating(String rating){

        return researcherRepo.findByRating(rating);
    }

    public List<Researcher> findTitle(String title){
        return researcherRepo.findByTitle(title);
    }

    public List<Researcher> findField(String field){
        return researcherRepo.findByField(field);
    }


    /*
        use a map to return the number of institutions
        eg: University of Cape Town -> 8
     */
    public Map<String, Integer> findAllInstitutionsCountBy()
    {
        Map<String, Integer> uniCount = new HashMap<>(); //create a new map
        List<Researcher> listResearchers = researcherRepo.findAll(); //return ALL the researchers in the database

        //loop through the list of researchers
        //grab a hold of every researcher
        for (Researcher r : listResearchers )
        {
            String uni = r.getInstitution(); //get the institution associated with the researcher
            //if the institution exists within the map, increment the current count value
            if (uniCount.containsKey(uni))
            {
                int count = uniCount.get(uni); //get the current count
                count++; //increment count
                uniCount.put(uni, count); //insert new count into the map

            }
            // if the institution doesn't exist in the map, add it too the map
            else{
                uniCount.put(uni, 1);
            }
        }

        return uniCount; //return the map
    }

    /*
        return a map to return the total number of ratings for each rating A, B, C, Y
        eg: A -> 8
     */
    public Map<String, Integer> findAllRatingCount()
    {
        Map<String, Integer> ratingCount = new HashMap<>(); //create a new map
        List<Researcher> listResearchers = researcherRepo.findAll(); //return all researchers from the database and add to a list

        //loop through all the researchers
        for (Researcher r : listResearchers )
        {
            String rating = r.getRating(); //get the rating of the researcher
            //if the rating key exists in the map, increment the count value
            if (ratingCount.containsKey(rating))
            {
                int count = ratingCount.get(rating); //get the rating count from the map
                count++; //increment the count
                ratingCount.put(rating, count); //put the new value of the count into the mao

            }
            //if the rating doesn't exist in the map, add it to the map
            else{
                ratingCount.put(rating, 1);
            }
        }
        return ratingCount; //return the map
    }

    /*
        return a map to return the number of primary fields
        eg: Computer Science -> 8
     */
    public Map<String, Integer> findAllPrimaryFieldsCount()
    {
        Map<String, Integer> primaryFieldCount = new HashMap<>(); //create a new map
        List<Researcher> listResearchers = researcherRepo.findAll(); //return all researchers

        //loop through the list of researchers to create the map
        for (Researcher r : listResearchers ) {
            String primaryField = r.getPrimaryFields(); //get the primary fields of the researchers
            String[] fields = primaryField.split(";"); // split the string to get individual fields of research

            //loop through the string
            for (String f : fields) {
                // check if the map contains the field, get the count and increment the count
                String tField = f.trim();
                if (primaryFieldCount.containsKey(tField)) {
                    int count = primaryFieldCount.get(tField); //get the current count
                    count++; //increment the count
                    primaryFieldCount.put(tField, count); //put the new count into the map

                } else {  //if the field doesn't exist in the map, add it to the map
                    primaryFieldCount.put(tField, 1);
                }
            }
        }
        return primaryFieldCount; //return the map
    }

    /*
        return a map to return the number of secondary fields
        eg: Computer Science -> 8
     */
    public Map<String, Integer> findAllSecondaryFieldsCount()
    {
        Map<String, Integer> secondaryFieldsCount = new HashMap<>(); //create a new map
        List<Researcher> listResearchers = researcherRepo.findAll(); //add all researchers to a list from the DB

        //loop through researchers
        for (Researcher r : listResearchers ) {
            String secondaryField = r.getSecondaryFields(); //get the value in the DB
            String[] fields = secondaryField.split(";"); //split the string to get the associated field

            //loop through the array of strings
            for (String f : fields) {
                //if the field exists in the map increment the count
                String tField = f.trim();
                if (secondaryFieldsCount.containsKey(tField)) {
                    int count = secondaryFieldsCount.get(tField); //get the current count
                    count++; //increment the count
                    secondaryFieldsCount.put(tField, count); //add the current value to the map

                } else { //if the secondary field doesn't exist, add it to the map
                    secondaryFieldsCount.put(tField, 1);
                }
            }
        }
        return secondaryFieldsCount; //return the map
    }

    public Map<String, Map<String, Integer>> findRatingForInstitution()
    {
        Map<String, Map<String, Integer>> ratingForInstitution = new HashMap<>(); //create a new map
        List<Researcher> listResearchers = researcherRepo.findAll(); //add all researchers to a list from the DB

        for (Researcher r : listResearchers )
        {
            String uni = r.getInstitution(); //get the institution associated with the researcher
            String ratingType = r.getRating();
            //if the institution exists within the map, increment the current count value
            if (ratingForInstitution.containsKey(uni))
            {
                Map<String, Integer> ratingMap = ratingForInstitution.get(uni); //get the current count
                Map<String, Integer> newRatingMap = this.calculateRatingForInstitution(ratingMap, ratingType);
                ratingForInstitution.put(uni, newRatingMap); //insert new map into the map

            }
            // if the institution doesn't exist in the map, add it too the map
            else{
                Map<String, Integer> ratingMap = new HashMap<>();
                Map<String, Integer> newMap = this.calculateRatingForInstitution(ratingMap, ratingType);
                ratingForInstitution.put(uni, newMap);
            }

        }

        return ratingForInstitution;
    }

    public Map<String, Integer> calculateRatingForInstitution(Map<String, Integer> map, String ratingType){

        Map<String, Integer> ratingMap = map;
        if (ratingMap.containsKey(ratingType)){
            int count = ratingMap.get(ratingType);
            count++;
            ratingMap.put(ratingType, count);

        }
        else {
            ratingMap.put(ratingType, 1);
        }
        return map;
    }


    public Map<String, Map<String, Integer>> findRatingForPrimaryFields()
    {
        Map<String, Map<String, Integer>> ratingForPrimaryFields = new HashMap<>(); //create a new map
        List<Researcher> listResearchers = researcherRepo.findAll(); //add all researchers to a list from the DB

        for (Researcher r : listResearchers )
        {
            String uni = r.getPrimaryFields(); //get the institution associated with the researcher
            String[] fields = uni.split(";");
            String ratingType = r.getRating();
            //if the institution exists within the map, increment the current count value
            for (String f : fields) {
                String tField = f.trim();
                if (ratingForPrimaryFields.containsKey(tField)) {
                    Map<String, Integer> ratingMap = ratingForPrimaryFields.get(tField); //get the current count
                    Map<String, Integer> newRatingMap = this.calculateRatingForInstitution(ratingMap, ratingType);
                    ratingForPrimaryFields.put(tField, newRatingMap); //insert new map into the map

                }
                // if the institution doesn't exist in the map, add it too the map
                else {
                    Map<String, Integer> ratingMap = new HashMap<>();
                    Map<String, Integer> newMap = this.calculateRatingForInstitution(ratingMap, ratingType);
                    ratingForPrimaryFields.put(tField, newMap);
                }
            }
        }

        return ratingForPrimaryFields;
    }

    public Map<String, Map<String, Integer>> findRatingForSecondaryFields()
    {
        Map<String, Map<String, Integer>> ratingForSecondaryFields = new HashMap<>(); //create a new map
        List<Researcher> listResearchers = researcherRepo.findAll(); //add all researchers to a list from the DB

        for (Researcher r : listResearchers )
        {
            String uni = r.getSecondaryFields(); //get the institution associated with the researcher
            String[] fields = uni.split(";");
            String ratingType = r.getRating();
            //if the institution exists within the map, increment the current count value
            for (String f : fields) {
                String tField = f.trim();
                if (ratingForSecondaryFields.containsKey(tField)) {
                    Map<String, Integer> ratingMap = ratingForSecondaryFields.get(tField); //get the current count
                    Map<String, Integer> newRatingMap = this.calculateRatingForInstitution(ratingMap, ratingType);
                    ratingForSecondaryFields.put(tField, newRatingMap); //insert new map into the map

                }
                // if the institution doesn't exist in the map, add it too the map
                else {
                    Map<String, Integer> ratingMap = new HashMap<>();
                    Map<String, Integer> newMap = this.calculateRatingForInstitution(ratingMap, ratingType);
                    ratingForSecondaryFields.put(tField, newMap);
                }
            }
        }

        return ratingForSecondaryFields;
    }
    //return the year from a Date object
    public int getYearFromDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        int dateYear = calendar.get(Calendar.YEAR);
        return dateYear;
    }

    public Map<Integer, Integer> totalResearchersPerYear()
    {
        Map<Integer, Integer> totalResearchersPerYear = new HashMap<>(); //create a new map
        List<Researcher> listResearchers = researcherRepo.findAll(); //add all researchers to a list from the DB

        for (Researcher r : listResearchers )
        {
            Date startDate = r.getStartDate();
            int year = this.getYearFromDate(startDate);

            if (totalResearchersPerYear.containsKey(year))
            {
                int currentTotal = totalResearchersPerYear.get(year);
                currentTotal = currentTotal + 1;
                totalResearchersPerYear.put(year, currentTotal);
            }
            else
            {
                totalResearchersPerYear.put(year, 1);
            }
        }
        return totalResearchersPerYear;
        }
        //find all the ratings per Year
    public Map<Integer, Map<String, Integer>> findRatingPerYear()
    {
        Map<Integer, Map<String, Integer>> ratingPerYear = new HashMap<>(); //create a new map
        List<Researcher> listResearchers = researcherRepo.findAll(); //add all researchers to a list from the DB

        for (Researcher r : listResearchers )
        {
            Date date = r.getStartDate(); //get the start date associated with the researcher
            int year = this.getYearFromDate(date);
            String ratingType = r.getRating();
            //if the institution exists within the map, increment the current count value
            if (ratingPerYear.containsKey(year))
            {
                Map<String, Integer> ratingMap = ratingPerYear.get(year); //get the current count
                Map<String, Integer> newRatingMap = this.calculateRatingForInstitution(ratingMap, ratingType);
                ratingPerYear.put(year, newRatingMap); //insert new map into the map

            }
            // if the institution doesn't exist in the map, add it too the map
            else{
                Map<String, Integer> ratingMap = new HashMap<>();
                Map<String, Integer> newMap = this.calculateRatingForInstitution(ratingMap, ratingType);
                ratingPerYear.put(year, newMap);
            }

        }

        return ratingPerYear;
    }

    public Map<String, Integer> countForSpecialisations()
    {
           Map<String, Integer> specialisationsCount = new HashMap<>(); //create a new map
            List<Researcher> listResearchers = researcherRepo.findAll(); //add all researchers to a list from the DB

            //loop through researchers
            for (Researcher r : listResearchers ) {
                String specialisations = r.getSpecialisations(); //get the value in the DB
                String[] fields = specialisations.split(";"); //split the string to get the associated field

                //loop through the array of strings
                for (String f : fields) {
                    //if the field exists in the map increment the count
                    String tField = f.trim();
                    if (specialisationsCount.containsKey(tField)) {
                        int count = specialisationsCount.get(tField); //get the current count
                        count++; //increment the count
                        specialisationsCount.put(tField, count); //add the current value to the map

                    } else { //if the secondary field doesn't exist, add it to the map
                        specialisationsCount.put(tField, 1);
                    }
                }
            }
            return specialisationsCount; //return the map     }
    }

    public Map<String, Integer> getMostCommonSpecialisations() {
        Map<String, Integer> map = this.countForSpecialisations();
        Map<String, Integer> newMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= 5) {
                newMap.put(entry.getKey(), entry.getValue());
            }
        }
        return newMap;
    }

    public Map<String, Map<String, Integer>> findRatingForSpecialisations()
    {
        Map<String, Integer> map = this.getMostCommonSpecialisations(); //get the most popular topics/specialisations
        Map<String, Map<String, Integer>> sMap = new HashMap<>(); //overall map

        for(Map.Entry<String, Integer> entry : map.entrySet())
        {
            String spec = entry.getKey();
            List<Researcher> r = researcherRepo.findBySpecialisationsContaining(entry.getKey()); //return all researchers containing the specialisation
            for(Researcher researcher: r)
            {
                    String rating = researcher.getRating();
                if (sMap.containsKey(spec)) {
                    Map<String, Integer> ratingMap = sMap.get(spec); //get the current count
                    Map<String, Integer> newRatingMap = this.calculateRatingForInstitution(ratingMap, rating);
                    sMap.put(spec, newRatingMap); //insert new map into the map
                }
                // if the institution doesn't exist in the map, add it too the map
                else {
                    Map<String, Integer> ratingMap = new HashMap<>();
                    Map<String, Integer> newMap = this.calculateRatingForInstitution(ratingMap, rating);
                    sMap.put(spec, newMap);
                }

            }

        }
        return sMap;
    }

}

