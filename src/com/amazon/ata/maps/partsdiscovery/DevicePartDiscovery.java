package com.amazon.ata.maps.partsdiscovery;

import java.util.*;

/**
 * Helps expose key words from new editions of part catalogs.
 */
public class DevicePartDiscovery {

    // --- Part A ---
    /**
     * Calculate how often each word appears in a Catalog.
     * @param catalog The catalog to calculate word frequencies for.
     * @return A Map of words that appear in the catalog to the number of times they appear.
     */
    public Map<String, Integer> calculateWordCounts(PartCatalog catalog) {
        // PARTICIPANTS: Implement calculateWordCounts()
        // instantiate the object to be returned (the returnedMap)
        Map<String, Integer> returnedMap = new HashMap<>();

        // go through the partCatalog catalogWordList
        for (String aWord : catalog.getCatalogWords()) {
            int wordCount = 0;
            // check to see if the word is already in the returnedMap
            if (returnedMap.containsKey(aWord)) {    // if it is in the map increment its count by 1
                wordCount = returnedMap.get(aWord) + 1; // get th current count for the word from the map, increment the current count and
                returnedMap.put(aWord, wordCount);      // then put the current count for the word back on the returnedMap
            } else {    // if it's not in the map
                returnedMap.put(aWord, 1);   //add the current word to the returnedMap with a count of 1

            }
        }

        return returnedMap;    // return the object expected
    }

    // --- Part B ---
    /**
     * Removes a word from the provided word count map.
     * @param word the word to be removed
     * @param wordCounts the map to remove the word from
     */
    public void removeWord(String word, Map<String, Integer> wordCounts) {
        // PARTICIPANTS: Implement removeWord()
        // remove the word provided from the map provided
        wordCounts.remove(word);
        return;
    }

    // --- Part C ---
    /**
     * Find the word that appears most frequently based on the word counts from a catalog.
     * @param wordCounts an association between a word and the total number of times it appeared in a catalog
     * @return The word that appears most frequently in the catalog to the number of times they appear.
     */
    public String getMostFrequentWord(Map<String, Integer> wordCounts) {
        // PARTICIPANTS: Implement getMostFrequentWord()
        // the word count is the VALUE in an entry in the Map
        // Tree map will keep the entries in KEY sequence - there is no version of MAP that keeps values sequence
        // Instantiate the return object
        String mostFrequentWord = "";
        int maxCount = 0;
        //Iterate through the map keeping track of the max value word count
        for (Map.Entry<String, Integer> anEntry : wordCounts.entrySet()) {
            if (maxCount < anEntry.getValue()) {
                maxCount = anEntry.getValue();
                mostFrequentWord = anEntry.getKey();
            }
        }
        // Alternate technique to solve the same problem

//        Set<String> theKeys = wordCounts.keySet();     //Store all the map keys in a set
//        for (String aKey : theKeys) {   // iterate through the mapkeys one at a time
//            if (maxCount < wordCounts.get(aKey)) {
//                maxCount = wordCounts.get(aKey);
//                mostFrequentWord = aKey;
//            }
//        }


        return mostFrequentWord; // return the most frequent word
    }

    // --- Part D ---
    /**
     * Calculates the TF-IDF score for each word in a catalog. The TF-IDF score for a word
     * is equal to the count * idf score. You can assume there will be an idfScore for each word
     * in wordCounts.
     * @param wordCounts - associates a count for each word from a catalog
     * @param idfScores - associates an IDF score for each word in the catalog
     * @return a map associating each word with its TF-IDF score.
     */
    public Map<String, Double> getTfIdfScores(Map<String, Integer> wordCounts, Map<String, Double> idfScores) {
        // PARTICIPANTS: Implement getTfIdfScores()
        // Instantiate the return object
        Map<String, Double> tfIdfScores = new TreeMap<>();
        // Iterate through the wordCounts Map
        // calculate the TF-IDF Score for each entry and store it in a new map
        for (Map.Entry<String, Integer> anEntry : wordCounts.entrySet()) {
            tfIdfScores.put(anEntry.getKey(), anEntry.getValue() * idfScores.get(anEntry.getKey()));
        }
        return tfIdfScores;     // return the map with TF-IDF scores
    }

    // --- Extension 1 ---
    /**
     * Gets the 10 highest (TF-IDF) scored words for a catalog.
     *
     * @param tfIdfScores - associates a TF-IDF score for each word in a catalog
     * @return a list of the 10 highest scored words for a catalog.
     */
    public List<String> getBestScoredWords(Map<String, Double> tfIdfScores) {
        // iterate through the scores
        List<Map.Entry<String, Double>> mapList = new ArrayList<>(tfIdfScores.entrySet());
        Collections.sort(mapList, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        List<String> topTenList = new ArrayList<>();
        for (int i = 0; i< 10; i++) {
            topTenList.add(mapList.get(i).getKey());
        }
        return topTenList;
    }

    // --- Extension 2 ---
    /**
     * Calculates the IDF score for each word in a set of catalogs. The IDF score for a word
     * is equal to the inverse of the total number of times appears in all catalogs.
     * Assume df is the sum of the counts of a single word across all catalogs, then idf = 1.0/df.
     *
     * @param catalogWordCounts - a list of maps that associate a count for each word for each catalog
     * @return a map associating each word with its IDF score.
     */
    public Map<String, Double> calculateIdfScores(List<Map<String,Integer>> catalogWordCounts) {
        // PARTICIPANTS: Implement getIdfScores()
        // iterate through the list,
        Map<String, Double> returnMap = new TreeMap<>();
        for (Map<String, Integer> listEntry : catalogWordCounts) {
            for (String word : listEntry.keySet()) {
                if(returnMap.containsKey(word)) {
                    returnMap.put(word, listEntry.get(word) + returnMap.get(word));
                } else {
                    returnMap.put(word, Double.valueOf(listEntry.get(word)));
                }
            }
        }
        for (Map.Entry<String, Double> eachValue : returnMap.entrySet()) {
            returnMap.put(eachValue.getKey(), 1.0 / eachValue.getValue());
            // returnedMap.replaceAll
        }
        return returnMap;
    }

}
