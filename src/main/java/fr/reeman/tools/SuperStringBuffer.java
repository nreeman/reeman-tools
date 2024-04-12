package fr.reeman.tools;

import static fr.reeman.tools.Shortcuts.stringIfNull;

import java.util.Collection;

import fr.reeman.tools.bits.Bits;

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
public class SuperStringBuffer {

	private StringBuffer stringBuffer;
	
	public SuperStringBuffer() {
		this.stringBuffer = new StringBuffer();
	}

	public SuperStringBuffer(String string) {
		this.stringBuffer = new StringBuffer(string);
	}
	
	public SuperStringBuffer(int n, char c) {
		this();
		repeat(n, c);
	}
	
	public SuperStringBuffer(String format, Object ...args) {
		this();
		append(format, args);
	}

	@Override
	public String toString() {
		return stringBuffer.toString();
	}
	
	
	/**
	 * Append string then perfomr toString()
	 * 
	 * @param string
	 * @return
	 */
	public String toString(String string) {
		return stringBuffer.append(string).toString();
	}

	public byte[] getBytes() {
		return stringBuffer.toString().getBytes();
	}
	
	public SuperStringBuffer append(Object object) {
		stringBuffer.append(object);
		return this;
	}

	public SuperStringBuffer append(String string) {
		stringBuffer.append(string);
		return this;
	}

//	public SuperStringBuffer append(int i) {
//		stringBuffer.append(i);
//		return this;
//	}

	public SuperStringBuffer append(String format, Object ...args) {
		stringBuffer.append(String.format(format, args));
		return this;
	}

	/**
	 * Supprime les <code>number</code> derniers caractères de la chaîne en construction.
	 * 
	 * Si <code>number</code> est supérieur à la taille de la chaine la vide complètement
	 * 
	 * @param number
	 * @return
	 */
	public SuperStringBuffer removeLasts(int number) {
		number = Math.min(number, stringBuffer.length());
		if (number > 0) {
			stringBuffer = new StringBuffer(stringBuffer.substring(0, stringBuffer.toString().length() - number));
		}
		return this;
	}

	public SuperStringBuffer flatten(final Object[] objects, final String separator) {
		for (Object object : objects) {
			stringBuffer.append(stringIfNull(object, "null")).append(separator);
		}
		return removeLasts(separator.length());
	}

	/**
	 * Equivalent à 'flatten(objects, separator, "null")'
	 * 
	 * @param objects La collection d'objet
	 * @param separator
	 * @return
	 */
	public SuperStringBuffer flatten(final Collection<? extends Object> objects, final String separator) {
		return flatten(objects, separator, "null");
	}
	
	/**
	 * "Applati" une collection.
	 * 
	 * ie : Parcours une collection d'Object via son iterator et ajoute son 'toString()' au buffer en insérant 'separator' entre les éléments.
	 * Si un élément est null écrit la valeur de 'ifNull' à la place.
	 * 
	 * @param objects La collection d'objet
	 * @param separator
	 * @return
	 */
	public SuperStringBuffer flatten(final Collection<? extends Object> objects, final String separator, final String ifNull) {
		for (Object object : objects) {
			stringBuffer.append(stringIfNull(object, ifNull)).append(separator);
		}
		return removeLasts(separator.length());
	}

	/**
	 * @see https://stackoverflow.com/questions/1235179/simple-way-to-repeat-a-string
	 * 
	 * @param n
	 * @param pattern
	 * @return
	 */
	public SuperStringBuffer repeat(int n, String pattern) {
//		return append(new String(new char[n]).replace("\0", pattern)); // Java 8-
		return append(pattern.repeat(n)); // Java 9+
	}
	
	public SuperStringBuffer repeat(int n, char c) {
		for (int i = 0; i < n; i++) {
			stringBuffer.append(c);
		}
		return this;
	}

	/**
	 * Ajoute au buffer un affichage formatée en hexadécimal d'un tableau de bytes.
	 * 
	 * Exemple : [0b00110001, 0b11001000] devient : "0x31 0xC8"
	 * 
	 * @param bytes
	 * @return
	 */
    public SuperStringBuffer hex(byte[] bytes) {
    	return append(Bits.hex(bytes));
    }

    /**
     * Affiche l'octet sous forme hexadécimale.
     * 
     * Exemple : 0b11001000 => 0xC8
     * 
     * @param b
     * @return
     */
    public SuperStringBuffer hex(byte b) {
        return append(Bits.hex(b));
    }

    public SuperStringBuffer hex(short s) {
        return append(Bits.toBytes(s));
    }
    
    public SuperStringBuffer hex(int i) {
        return append(Bits.toBytes(i));
    }

    public SuperStringBuffer hex(long l) {
        return append(Bits.toBytes(l));
    }
}
