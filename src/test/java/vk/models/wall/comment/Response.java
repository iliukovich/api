
package vk.models.wall.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "comment_id",
    "parents_stack"
})
public class Response {

    @JsonProperty("comment_id")
    private Integer commentId;
    @JsonProperty("parents_stack")
    private List<Object> parentsStack = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("comment_id")
    public Integer getCommentId() {
        return commentId;
    }

    @JsonProperty("comment_id")
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    @JsonProperty("parents_stack")
    public List<Object> getParentsStack() {
        return parentsStack;
    }

    @JsonProperty("parents_stack")
    public void setParentsStack(List<Object> parentsStack) {
        this.parentsStack = parentsStack;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
