
package eu.hop.leshan.objects;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class ObjectLink {

    @Expose
    private String url;
    @Expose
    private Attributes attributes;
    @Expose
    private Integer objectId;
    @Expose
    private Integer objectInstanceId;

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The attributes
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * 
     * @param attributes
     *     The attributes
     */
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    /**
     * 
     * @return
     *     The objectId
     */
    public Integer getObjectId() {
        return objectId;
    }

    /**
     * 
     * @param objectId
     *     The objectId
     */
    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    /**
     * 
     * @return
     *     The objectInstanceId
     */
    public Integer getObjectInstanceId() {
        return objectInstanceId;
    }

    /**
     * 
     * @param objectInstanceId
     *     The objectInstanceId
     */
    public void setObjectInstanceId(Integer objectInstanceId) {
        this.objectInstanceId = objectInstanceId;
    }

}
