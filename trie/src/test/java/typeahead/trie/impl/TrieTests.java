package typeahead.trie.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import typeahead.trie.ApplicationConfig;
import typeahead.trie.core.TypeAhead;

import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by abhay on 11/9/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration (classes = ApplicationConfig.class)
public class TrieTests {

    private static String[] names = {
        "arun",
        "apple",
        "john",
        "jonathan",
        "Ram",
        "rahim"
    };

    @Autowired
    private TypeAhead trie;

    @Before
    public void init() {
        for (String name : names) {
            trie.add(name);
        }
    }

    @Test
    public void simpleGetTest() {
        assertTrue("Not all names are added in the Trie.",
                trie.size() == names.length);

        Set typeAheadNames = trie.get("a");

        assertNotNull("Got null result", typeAheadNames);
        assertTrue("Did not get arun and apple as typeahead suggestion",
                typeAheadNames.contains("arun")
            && typeAheadNames.contains("apple"));

        typeAheadNames = trie.get("arun");
        assertNotNull("Got null result", typeAheadNames);
        assertTrue("Was expecting one element but got "+ typeAheadNames.size(),
                typeAheadNames.size() == 1);

        typeAheadNames = trie.get("jo");
        assertTrue("Expecting only 2 names but got "+ typeAheadNames.size(),
                typeAheadNames.size() == 2);

        //Check case insensitiveness
        typeAheadNames = trie.get("RA");
        assertTrue ("Error typeAhead is case sensitive",
                typeAheadNames.size() == 2);
    }

    @Test
    public void removeTest() {
        final String toDel = "del";
        trie.add(toDel);
        boolean removed = trie.remove(toDel);
        assertTrue("Cannot remove "+ toDel +" from trie", removed);

        assertTrue(toDel + " removed successfully but size of trie is same",
                removed && trie.size() == names.length);
    }
}
