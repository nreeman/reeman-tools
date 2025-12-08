package fr.reeman.tools.riftbound.owen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "url", "colors", "accessibilityText" })
public class CardImage {

	@JsonProperty("url")
	private String url;
	@JsonProperty("colors")
	private Colors colors;
	@JsonProperty("accessibilityText")
	private String accessibilityText;
}