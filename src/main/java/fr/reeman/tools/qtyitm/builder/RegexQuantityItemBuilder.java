package fr.reeman.tools.qtyitm.builder;

import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.reeman.tools.qtyitm.QuantityItem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegexQuantityItemBuilder implements QuantityItemBuilder {

	private final Pattern pattern;
	private final int qtyGroup;
	private final int itemGroup;
	private final ItemBuilder itemBuilder;
	
	public RegexQuantityItemBuilder(String regex, int qtyGroup, int itemGroup, ItemBuilder itemBuilder) {
		this.pattern = Pattern.compile(regex);
		this.qtyGroup = qtyGroup;
		this.itemGroup = itemGroup;
		this.itemBuilder = itemBuilder;
	}
	
	public RegexQuantityItemBuilder(String regex, int qtyGroup, int itemGroup) {
		this(regex, qtyGroup, itemGroup, ItemBuilder.SIMPLE_STRING_ITEM_BUILDER);
	}
	
	@Override
	public QuantityItem build(Object object) {
		if (!(object instanceof String)) {
			throw new InvalidParameterException("Parameter should be a String but it's a " + object.getClass().getName());
		}

		String str = (String)object;
		if (!str.isEmpty()) {
			Matcher matcher = pattern.matcher(str);
			if (matcher.matches()) {
				return new QuantityItem(Integer.valueOf(matcher.group(qtyGroup)), itemBuilder.build(matcher.group(itemGroup)));
			} else {
				log.warn("'" + str + "' does not match the regex.");
			}
		}
		
		return null;
	}

}
