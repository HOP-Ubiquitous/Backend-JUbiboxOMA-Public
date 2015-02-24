
package eu.hop.leshan.objects;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class OMAClient {

    @Expose
    private String endpoint;
    @Expose
    private String registrationId;
    @Expose
    private String registrationDate;
    @Expose
    private String address;
    @Expose
    private String lwM2MmVersion;
    @Expose
    private Integer lifetime;
    @Expose
    private String bindingMode;
    @Expose
    private String rootPath;
    @Expose
    private List<ObjectLink> objectLinks = new ArrayList<ObjectLink>();
    @Expose
    private Boolean secure;

    /**
     * 
     * @return
     *     The endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * 
     * @param endpoint
     *     The endpoint
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * 
     * @return
     *     The registrationId
     */
    public String getRegistrationId() {
        return registrationId;
    }

    /**
     * 
     * @param registrationId
     *     The registrationId
     */
    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    /**
     * 
     * @return
     *     The registrationDate
     */
    public String getRegistrationDate() {
        return registrationDate;
    }

    /**
     * 
     * @param registrationDate
     *     The registrationDate
     */
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The lwM2MmVersion
     */
    public String getLwM2MmVersion() {
        return lwM2MmVersion;
    }

    /**
     * 
     * @param lwM2MmVersion
     *     The lwM2MmVersion
     */
    public void setLwM2MmVersion(String lwM2MmVersion) {
        this.lwM2MmVersion = lwM2MmVersion;
    }

    /**
     * 
     * @return
     *     The lifetime
     */
    public Integer getLifetime() {
        return lifetime;
    }

    /**
     * 
     * @param lifetime
     *     The lifetime
     */
    public void setLifetime(Integer lifetime) {
        this.lifetime = lifetime;
    }

    /**
     * 
     * @return
     *     The bindingMode
     */
    public String getBindingMode() {
        return bindingMode;
    }

    /**
     * 
     * @param bindingMode
     *     The bindingMode
     */
    public void setBindingMode(String bindingMode) {
        this.bindingMode = bindingMode;
    }

    /**
     * 
     * @return
     *     The rootPath
     */
    public String getRootPath() {
        return rootPath;
    }

    /**
     * 
     * @param rootPath
     *     The rootPath
     */
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * 
     * @return
     *     The objectLinks
     */
    public List<ObjectLink> getObjectLinks() {
        return objectLinks;
    }

    /**
     * 
     * @param objectLinks
     *     The objectLinks
     */
    public void setObjectLinks(List<ObjectLink> objectLinks) {
        this.objectLinks = objectLinks;
    }

    /**
     * 
     * @return
     *     The secure
     */
    public Boolean getSecure() {
        return secure;
    }

    /**
     * 
     * @param secure
     *     The secure
     */
    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

}
