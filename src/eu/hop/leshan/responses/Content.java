
package eu.hop.leshan.responses;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

import eu.hop.leshan.objects.Resource;

@Generated("org.jsonschema2pojo")
public class Content {

    @Expose
    private Integer id;
    @Expose
    private List<Resource> resources = new ArrayList<Resource>();

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The resources
     */
    public List<Resource> getResources() {
        return resources;
    }

    /**
     * 
     * @param resources
     *     The resources
     */
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

}
