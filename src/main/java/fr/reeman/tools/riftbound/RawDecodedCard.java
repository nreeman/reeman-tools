package fr.reeman.tools.riftbound;

import lombok.Data;

@Data
public class RawDecodedCard {
	private final int set;
	private final int code;
	private final int variant;
}
