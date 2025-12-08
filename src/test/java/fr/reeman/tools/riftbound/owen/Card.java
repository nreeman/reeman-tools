package fr.reeman.tools.riftbound.owen;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "name", "collectorNumber", "publicCode", "orientation", "set", "setName", "domains",
		"rarity", "cardType", "cardImage", "illustrator", "text", "energy", "power" })
public class Card {

	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("collectorNumber")
	private Integer collectorNumber;
	@JsonProperty("publicCode")
	private String publicCode;
	@JsonProperty("orientation")
	private String orientation;
	@JsonProperty("set")
	private String set;
	@JsonProperty("setName")
	private String setName;
	@JsonProperty("domains")
	private List<Domain> domains;
	@JsonProperty("rarity")
	private Rarity rarity;
	@JsonProperty("cardType")
	private List<CardType> cardType;
	@JsonProperty("cardImage")
	private CardImage cardImage;
	@JsonProperty("illustrator")
	private List<String> illustrator;
	@JsonProperty("text")
	private String text;
	@JsonProperty("energy")
	private Integer energy;
	@JsonProperty("power")
	private Integer power;

}