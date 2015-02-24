
package eu.hop.leshan.responses;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class GETResourceResponse {

    @Expose
    private String status;
    @Expose
    private Content content;

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The content
     */
    public Content getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(Content content) {
        this.content = content;
    }

}
