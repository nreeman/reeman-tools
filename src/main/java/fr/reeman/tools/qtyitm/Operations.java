package fr.reeman.tools.qtyitm;

public class Operations {

	public static QuantityItemSet intersection(QuantityItemSet set1, QuantityItemSet set2) {
		QuantityItemSet result = new QuantityItemSet();
		set1.forEach(current -> {
			set2
			.find(current.getItem())
			.ifPresent(found ->
				result.add(Math.min(current.getQuantity(), found.getQuantity()), current.getItem())
			);
			
		});
		
		return result;
	}
	
	public static QuantityItemSet intersection(QuantityItemSet... sets) {
		if (sets.length == 0) {
			return null;
		}
		
		if (sets.length == 1) {
			return sets[0];
		}
		
		QuantityItemSet intersection = intersection(sets[0], sets[1]);
		
		for (int i = 2; i < sets.length; i++) {
			intersection = intersection(intersection, sets[i]);
		}
		
		return intersection;
	}
}
