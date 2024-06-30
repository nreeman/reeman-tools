package fr.reeman.tools.equivalent;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.NonNull;

//Copyright (C) 2024 Reeman Nicolas
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//Lesser General Public License for more details.
//
//You should have received a copy of the GNU Lesser General Public
//License along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
/**
 * Ensemble de méthodes pour manipuler des collections d'objets différents mais équivalents. 
 */
public class EquivalentCollections {

	/**
	 * 
	 * Vérifie si la liste contient l'équivalent de l'objet passé en paramétre.
	 * 
	 * @return Le premier object équivalent trouvé dans la liste, null si aucun n'est trouvé.
	 */
	public static <T, O> T contains(@NonNull Collection<T> ts, O o, @NonNull Equivalent<T, O> equivalent) {
		for (T t : ts) {
			if (equivalent.check(t, o)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Retire de la liste le premier équivalent de l'objet passé en paramétre 
	 * 
	 * @return Le premier object équivalent trouvé dans la liste, null si aucun n'est trouvé.
	 */
	public static <T, O> T remove(Collection<T> ts, O o, @NonNull Equivalent<T, O> equivalent) {
		for (T t : ts) {
			if (equivalent.check(t, o)) {
				ts.remove(t);
				return t;
			}
		}
		return null;
	}

	/**
	 * 
	 * Même comportement que pour un appel à minus(ts, os, equivalent, new ArrayList<>()) mais sans la gestion des exceptions
	 * 
	 * 
	 * @return Une liste représentant le résultat de l'opération ou null en cas d'exception
	 */
	public static <T, O> List<T> minusAsList(Collection<T> ts, Collection<O> os, @NonNull Equivalent<T, O> equivalent) {
		try {
			return (List<T>)minus(ts, os, equivalent, new ArrayList<>());
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * Même comportement que pour un appel à minus(ts, os, equivalent, new HashSet<>()) mais sans la gestion des exceptions
	 * 
	 * 
	 * @return Un set représentant le résultat de l'opération ou null en cas d'exception
	 */
	public static <T, O> Set<T> minusAsSet(Collection<T> ts, Collection<O> os, @NonNull Equivalent<T, O> equivalent) {
		try {
			return (Set<T>)minus(ts, os, equivalent, new HashSet<>());
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Effectue l'opération ensembliste 'minus' entre deux collections.
	 * 
	 * @param <T>
	 * @param <O>
	 * @param ts
	 * @param os
	 * @param equivalent
	 * @param result
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <T, O> Collection<T> minus(Collection<T> ts, Collection<O> os, @NonNull Equivalent<T, O> equivalent, @NonNull Collection<T> result) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (ts == null) {
			return null;
		}

		result.clear();
		result.addAll(ts);

		if (os == null) {
			return result;
		}
		
		for (O o : os) {
			remove(result, o, equivalent);
		}
		
		return result;
	}

	/**
	 * 
	 * @return
	 */
	public static <T, O> boolean areEquivalent(Collection<T> ts, Collection<O> os, @NonNull Equivalent<T, O> equivalent) {
		if (ts == null && os == null) {
			return true;
		}
		
		if (ts == null || os == null) {
			return false;
		}
		
		if (ts.size() != os.size()) {
			return false;
		}
		
		Collection<T> temp = new ArrayList<>(ts);
		for (O o : os) {
			if (remove(temp, o, equivalent) == null) {
				return false;
			}
		}
				
		return true;
	}

}
