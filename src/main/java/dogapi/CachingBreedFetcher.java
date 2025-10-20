package dogapi;

import java.io.IOException;
import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private final BreedFetcher fetcher;
    private final Map<String, List<String>> cache = new HashMap<>();
    private int callsMade = 0;

    public CachingBreedFetcher(BreedFetcher fetcher) {//abstraction, this BreedFetcher would be implemented
        this.fetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) { //from interface
        // return statement included so that the starter code can compile and run.
        if(cache.containsKey(breed)) {
            return cache.get(breed);
        }
        try {
            List<String> subbreeds = fetcher.getSubBreeds(breed);
            cache.put(breed, subbreeds);
            callsMade++;
            return subbreeds;
        }
        catch (BreedNotFoundException event) {
            callsMade++;
            throw event;
        }

    }

    public int getCallsMade() {
        return callsMade;
    }
}