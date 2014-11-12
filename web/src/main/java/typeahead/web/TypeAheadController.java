package typeahead.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import typeahead.trie.core.TypeAhead;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by abhay on 11/11/14.
 */
@Controller
@RequestMapping("/v1/trie")
public class TypeAheadController {

    @Autowired
    @Resource(name = "trieImpl")
    TypeAhead trieImpl;

    @RequestMapping("/add")
    public @ResponseBody boolean add(
            @RequestParam(value="token", required = true) String token) {
        return trieImpl.add(token);
    }

    @RequestMapping("/get")
    public @ResponseBody
    Set<String> get(
            @RequestParam(value="token", required = true) String token) {
        return trieImpl.get(token);
    }

    @RequestMapping("/remove")
    public @ResponseBody boolean remove(
            @RequestParam(value="token", required = true) String token) {
        return trieImpl.remove(token);
    }

    @RequestMapping("/size")
    public @ResponseBody int size() {
        return trieImpl.size();
    }

}
