package fr.reeman.tools.riftbound.owen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "primary", "secondary", "label" })
public class Colors {

	@JsonProperty("primary")
	private String primary;
	@JsonProperty("secondary")
	private String secondary;
	@JsonProperty("label")
	private String label;
}