package fr.reeman.tools.riftbound.owen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "label" })
public class Rarity {

	@JsonProperty("id")
	private String id;
	@JsonProperty("label")
	private String label;

}