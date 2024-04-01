package fr.reeman.tools;

import java.io.PrintStream;

/**
 * @see https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
 * 
 * @author reeman
 *
 */
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
public enum ConsoleColors {
	
	// Reset
	RESET("\033[0m"),
	
	// Regular Colors
	BLACK ("\033[0;30m"),
	RED   ("\033[0;31m"),
	GREEN ("\033[0;32m"),
	YELLOW("\033[0;33m"),
	BLUE  ("\033[0;34m"),
	PURPLE("\033[0;35m"),
	CYAN  ("\033[0;36m"),
	WHITE ("\033[0;37m"),
	
	// Bold
	BLACK_BOLD ("\033[1;30m"),
	RED_BOLD   ("\033[1;31m"),
	GREEN_BOLD ("\033[1;32m"),
	YELLOW_BOLD("\033[1;33m"),
	BLUE_BOLD  ("\033[1;34m"),
	PURPLE_BOLD("\033[1;35m"),
	CYAN_BOLD  ("\033[1;36m"),
	WHITE_BOLD ("\033[1;37m"),

	// Underline
	BLACK_UNDERLINED ("\033[4;30m"),
	RED_UNDERLINED   ("\033[4;31m"),
	GREEN_UNDERLINED ("\033[4;32m"),
	YELLOW_UNDERLINED("\033[4;33m"),
	BLUE_UNDERLINED  ("\033[4;34m"),
	PURPLE_UNDERLINED("\033[4;35m"),
	CYAN_UNDERLINED  ("\033[4;36m"),
	WHITE_UNDERLINED  ("\033[4;37m"),

	// Background
	BLACK_BACKGROUND ("\033[40m"),
	RED_BACKGROUND   ("\033[41m"),
	GREEN_BACKGROUND ("\033[42m"),
	YELLOW_BACKGROUND("\033[43m"),
	BLUE_BACKGROUND  ("\033[44m"),
	PURPLE_BACKGROUND("\033[45m"),
	CYAN_BACKGROUND  ("\033[46m"),
	WHITE_BACKGROUND ("\033[47m"),

	// High Intensity
	BLACK_BRIGHT ("\033[0;90m"),
	RED_BRIGHT   ("\033[0;91m"),
	GREEN_BRIGHT ("\033[0;92m"),
	YELLOW_BRIGHT("\033[0;93m"),
	BLUE_BRIGHT  ("\033[0;94m"),
	PURPLE_BRIGHT("\033[0;95m"),
	CYAN_BRIGHT  ("\033[0;96m"),
	WHITE_BRIGHT ("\033[0;97m"),

	// Bold High Intensity
	BLACK_BOLD_BRIGHT ("\033[1;90m"),
	RED_BOLD_BRIGHT   ("\033[1;91m"),
	GREEN_BOLD_BRIGHT ("\033[1;92m"),
	YELLOW_BOLD_BRIGHT("\033[1;93m"),
	BLUE_BOLD_BRIGHT  ("\033[1;94m"),
	PURPLE_BOLD_BRIGHT("\033[1;95m"),
	CYAN_BOLD_BRIGHT  ("\033[1;96m"),
	WHITE_BOLD_BRIGHT ("\033[1;97m"),

	// High Intensity backgrounds
	BLACK_BACKGROUND_BRIGHT ("\033[0;100m"),
	RED_BACKGROUND_BRIGHT   ("\033[0;101m"),
	GREEN_BACKGROUND_BRIGHT ("\033[0;102m"),
	YELLOW_BACKGROUND_BRIGHT("\033[0;103m"),
	BLUE_BACKGROUND_BRIGHT  ("\033[0;104m"),
	PURPLE_BACKGROUND_BRIGHT("\033[0;105m"),
	CYAN_BACKGROUND_BRIGHT  ("\033[0;106m"),
	WHITE_BACKGROUND_BRIGHT ("\033[0;107m")
	
	;
	
	private String val;
	
	private ConsoleColors(String val) {
		this.val = val;
	}
	
	@Override
	public String toString() {
		return val;
	}
	
    public static void sample() {
    	sample(System.out);
    }
    
    public static void sample(PrintStream out) {
    	out.println("Regular Colors");
    	out.println(" " + BLACK + "This is " + BLACK.name() + RESET);
    	out.println(" " + RED + "This is " + RED.name() + RESET);
    	out.println(" " + GREEN + "This is " + GREEN.name() + RESET);
    	out.println(" " + YELLOW + "This is " + YELLOW.name() + RESET);
    	out.println(" " + BLUE + "This is " + BLUE.name() + RESET);
    	out.println(" " + PURPLE + "This is " + PURPLE.name() + RESET);
    	out.println(" " + CYAN + "This is " + CYAN.name() + RESET);
    	out.println(" " + WHITE + "This is " + WHITE.name() + RESET);
    	out.println();
    	out.println("Bold");
    	out.println(" " + BLACK_BOLD + "This is " + BLACK_BOLD.name() + RESET);
    	out.println(" " + RED_BOLD + "This is " + RED_BOLD.name() + RESET);
    	out.println(" " + GREEN_BOLD + "This is " + GREEN_BOLD.name() + RESET);
    	out.println(" " + YELLOW_BOLD + "This is " + YELLOW_BOLD.name() + RESET);
    	out.println(" " + BLUE_BOLD + "This is " + BLUE_BOLD.name() + RESET);
    	out.println(" " + PURPLE_BOLD + "This is " + PURPLE_BOLD.name() + RESET);
    	out.println(" " + CYAN_BOLD + "This is " + CYAN_BOLD.name() + RESET);
    	out.println(" " + WHITE_BOLD + "This is " + WHITE_BOLD.name() + RESET);
    	out.println();
    	out.println("Underline");
    	out.println(" " + BLACK_UNDERLINED + "This is " + BLACK_UNDERLINED.name() + RESET);
    	out.println(" " + RED_UNDERLINED + "This is " + RED_UNDERLINED.name() + RESET);
    	out.println(" " + GREEN_UNDERLINED + "This is " + GREEN_UNDERLINED.name() + RESET);
    	out.println(" " + YELLOW_UNDERLINED + "This is " + YELLOW_UNDERLINED.name() + RESET);
    	out.println(" " + BLUE_UNDERLINED + "This is " + BLUE_UNDERLINED.name() + RESET);
    	out.println(" " + PURPLE_UNDERLINED + "This is " + PURPLE_UNDERLINED.name() + RESET);
    	out.println(" " + CYAN_UNDERLINED + "This is " + CYAN_UNDERLINED.name() + RESET);
    	out.println(" " + WHITE_UNDERLINED + "This is " + WHITE_UNDERLINED.name() + RESET);
    	out.println();
    	out.println("Background");
    	out.println(" " + BLACK_BACKGROUND + "This is " + BLACK_BACKGROUND.name() + RESET);
    	out.println(" " + RED_BACKGROUND + "This is " + RED_BACKGROUND.name() + RESET);
    	out.println(" " + GREEN_BACKGROUND + "This is " + GREEN_BACKGROUND.name() + RESET);
    	out.println(" " + YELLOW_BACKGROUND + "This is " + YELLOW_BACKGROUND.name() + RESET);
    	out.println(" " + BLUE_BACKGROUND + "This is " + BLUE_BACKGROUND.name() + RESET);
    	out.println(" " + PURPLE_BACKGROUND + "This is " + PURPLE_BACKGROUND.name() + RESET);
    	out.println(" " + CYAN_BACKGROUND + "This is " + CYAN_BACKGROUND.name() + RESET);
    	out.println(" " + WHITE_BACKGROUND + "This is " + WHITE_BACKGROUND.name() + RESET);
    	out.println();
    	out.println("High Intensity");
    	out.println(" " + BLACK_BRIGHT + "This is " + BLACK_BRIGHT.name() + RESET);
    	out.println(" " + RED_BRIGHT + "This is " + RED_BRIGHT.name() + RESET);
    	out.println(" " + GREEN_BRIGHT + "This is " + GREEN_BRIGHT.name() + RESET);
    	out.println(" " + YELLOW_BRIGHT + "This is " + YELLOW_BRIGHT.name() + RESET);
    	out.println(" " + BLUE_BRIGHT + "This is " + BLUE_BRIGHT.name() + RESET);
    	out.println(" " + PURPLE_BRIGHT + "This is " + PURPLE_BRIGHT.name() + RESET);
    	out.println(" " + CYAN_BRIGHT + "This is " + CYAN_BRIGHT.name() + RESET);
    	out.println(" " + WHITE_BRIGHT + "This is " + WHITE_BRIGHT.name() + RESET);
    	out.println();
    	out.println("Bold High Intensity");
    	out.println(" " + BLACK_BOLD_BRIGHT + "This is " + BLACK_BOLD_BRIGHT.name() + RESET);
    	out.println(" " + RED_BOLD_BRIGHT + "This is " + RED_BOLD_BRIGHT.name() + RESET);
    	out.println(" " + GREEN_BOLD_BRIGHT + "This is " + GREEN_BOLD_BRIGHT.name() + RESET);
    	out.println(" " + YELLOW_BOLD_BRIGHT + "This is " + YELLOW_BOLD_BRIGHT.name() + RESET);
    	out.println(" " + BLUE_BOLD_BRIGHT + "This is " + BLUE_BOLD_BRIGHT.name() + RESET);
    	out.println(" " + PURPLE_BOLD_BRIGHT + "This is " + PURPLE_BOLD_BRIGHT.name() + RESET);
    	out.println(" " + CYAN_BOLD_BRIGHT + "This is " + CYAN_BOLD_BRIGHT.name() + RESET);
    	out.println(" " + WHITE_BOLD_BRIGHT + "This is " + WHITE_BOLD_BRIGHT.name() + RESET);
    	out.println();
    	out.println("High Intensity backgrounds");
    	out.println(" " + BLACK_BACKGROUND_BRIGHT + "This is " + BLACK_BACKGROUND_BRIGHT.name() + RESET);
    	out.println(" " + RED_BACKGROUND_BRIGHT + "This is " + RED_BACKGROUND_BRIGHT.name() + RESET);
    	out.println(" " + GREEN_BACKGROUND_BRIGHT + "This is " + GREEN_BACKGROUND_BRIGHT.name() + RESET);
    	out.println(" " + YELLOW_BACKGROUND_BRIGHT + "This is " + YELLOW_BACKGROUND_BRIGHT.name() + RESET);
    	out.println(" " + BLUE_BACKGROUND_BRIGHT + "This is " + BLUE_BACKGROUND_BRIGHT.name() + RESET);
    	out.println(" " + PURPLE_BACKGROUND_BRIGHT + "This is " + PURPLE_BACKGROUND_BRIGHT.name() + RESET);
    	out.println(" " + CYAN_BACKGROUND_BRIGHT + "This is " + CYAN_BACKGROUND_BRIGHT.name() + RESET);
    	out.println(" " + WHITE_BACKGROUND_BRIGHT + "This is " + WHITE_BACKGROUND_BRIGHT.name() + RESET);
	}
}