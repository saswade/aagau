package typeahead.trie.core;

import java.util.Set;

/**
 * Created by abhay on 11/8/14.
 */
public interface TypeAhead {
    /**
     * Added given string token in to the typeahead lookup
     * @param token
     */
    boolean add(String token);

    /**
     * Returns matching strings from the lookup
     * @param token
     * @return
     */
    Set<String> get (String token);

    /**
     * Removes token from the cache. Returns removed else null if none found.
     * @param token
     * @return
     */
    boolean remove (String token);

    /**
     * Returns number of tokens available in TypeAhead
     * @return
     */
    int size();
}
