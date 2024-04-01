package fr.reeman.tools;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

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
public class Shortcuts {

	public static boolean isNullOrEmpty(final String string) {
		return string == null || "".equals(string);
	}

	public static boolean isNullOrEmpty(final Collection<?> collection) {
		return collection == null || collection.size() == 0;
	}
	
	public static Integer zeroIfNull(final Integer integer) {
		return integer == null ? 0 : integer;
	}

	public static String stringIfNull(final Object object, final String string) {
		return object == null ? string : object.toString();
	}
	
	public static String removeAccents(final String string) {
		return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	/**
	 * Retourne le max d'une liste d'Integer en ignorant les null s'il y en a.
	 * 
	 * @param integers Une liste d'Integer
	 * @return Le max de la liste ou null si la liste est vide.
	 */
	public static Integer max(Integer... integers) {
		if (integers == null) {
			return null;
		}
		
		Integer max = null;
		int index = 0;
		for (; index < integers.length; index++) {
			if (integers[index] != null) {
				max = integers[index];
				index++;
				break;
			}
		}
		
		for (; index < integers.length; index++) {
			if (integers[index] != null) {
				max = max >= integers[index] ? max : integers[index];
			}
		}
		
		return max;
	}


	/**
	 * Retourne le min d'une liste d'Integer en ignorant les null s'il y en a.
	 * 
	 * @param integers Une liste d'Integer
	 * @return Le min de la liste ou null si la liste est vide.
	 */
	public static Integer min(Integer... integers) {
		if (integers == null) {
			return null;
		}
		
		Integer min = null;
		int index = 0;
		for (; index < integers.length; index++) {
			if (integers[index] != null) {
				min = integers[index];
				index++;
				break;
			}
		}
		
		for (; index < integers.length; index++) {
			if (integers[index] != null) {
				min = Math.min(min, integers[index]);
				min = min <= integers[index] ? min : integers[index];
			}
		}
		
		return min;
	}
	
	/**
	 * Tri une collection dans une nouvelle instance d'une liste.
	 * 
	 * @param <T>
	 * @param collection
	 * @return
	 */
	public static <T extends Comparable<? super T>> List<T> sort(Collection<T> collection) {
		return sort(collection, null);
	}

	/**
	 * Tri une collection dans une nouvelle instance d'une liste.
	 * 
	 * @param <T>
	 * @param collection
	 * @param comparator
	 * @return
	 */
	public static <T extends Comparable<? super T>> List<T> sort(Collection<T> collection, Comparator<T> comparator) {
		List<T> list = new ArrayList<>(collection);
		list.sort(comparator);
		return list;
	}
}
