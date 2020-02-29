/*
--- Day 6: Universal Orbit Map ---

You've landed at the Universal Orbit Map facility on Mercury. Because navigation in space often involves transferring between orbits, the orbit maps here are useful for finding efficient routes between, for example, you and Santa. You download a map of the local orbits (your puzzle input).

Except for the universal Center of Mass (COM), every object in space is in orbit around exactly one other object. An orbit looks roughly like this:

                  \
                   \
                    |
                    |
AAA--> o            o <--BBB
                    |
                    |
                   /
                  /

In this diagram, the object BBB is in orbit around AAA. The path that BBB takes around AAA (drawn with lines) is only partly shown. In the map data, this orbital relationship is written AAA)BBB, which means "BBB is in orbit around AAA".

Before you use your map data to plot a course, you need to make sure it wasn't corrupted during the download. To verify maps, the Universal Orbit Map facility uses orbit count checksums - the total number of direct orbits (like the one shown above) and indirect orbits.

Whenever A orbits B and B orbits C, then A indirectly orbits C. This chain can be any number of objects long: if A orbits B, B orbits C, and C orbits D, then A indirectly orbits D.

For example, suppose you have the following map:

COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L

Visually, the above map of orbits looks like this:

        G - H       J - K - L
       /           /
COM - B - C - D - E - F
               \
                I

In this visual representation, when two objects are connected by a line, the one on the right directly orbits the one on the left.

Here, we can count the total number of orbits as follows:

    D directly orbits C and indirectly orbits B and COM, a total of 3 orbits.
    L directly orbits K and indirectly orbits J, E, D, C, B, and COM, a total of 7 orbits.
    COM orbits nothing.

The total number of direct and indirect orbits in this example is 42.

What is the total number of direct and indirect orbits in your map data?

Your puzzle answer was 254447.

The first half of this puzzle is complete! It provides one gold star: *
--- Part Two ---

Now, you just need to figure out how many orbital transfers you (YOU) need to take to get to Santa (SAN).

You start at the object YOU are orbiting; your destination is the object SAN is orbiting. An orbital transfer lets you move from any object to an object orbiting or orbited by that object.

For example, suppose you have the following map:

COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L
K)YOU
I)SAN

Visually, the above map of orbits looks like this:

                          YOU
                         /
        G - H       J - K - L
       /           /
COM - B - C - D - E - F
               \
                I - SAN

In this example, YOU are in orbit around K, and SAN is in orbit around I. To move from K to I, a minimum of 4 orbital transfers are required:

    K to J
    J to E
    E to D
    D to I

Afterward, the map of orbits looks like this:

        G - H       J - K - L
       /           /
COM - B - C - D - E - F
               \
                I - SAN
                 \
                  YOU

What is the minimum number of orbital transfers required to move from the object YOU are orbiting to the object SAN is orbiting? (Between the objects they are orbiting - not between YOU and SAN.)
 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Day6 {
	
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(new File("day6.in"));
			
			Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
			
			while (sc.hasNext()) {
				String input = sc.nextLine();
				String[] tokens = input.split("\\)");
				
				if(!map.containsKey(tokens[1]))
					map.put(tokens[1], new ArrayList<String>());
				
//				if(map.containsKey(tokens[0])) {
//					ArrayList<String> temp = new ArrayList<String>(map.get(tokens[0]));
//					temp.add(tokens[0]);
//					map.get(tokens[1]).addAll(temp);	
//				}
//				else 
				map.get(tokens[1]).add(tokens[0]);
				
			}
			
			for (String key : map.keySet()) {
				for (int i = 0; i < map.get(key).size(); i++) {
					String tempKey = map.get(key).get(i);
						
					if(map.get(tempKey) != null) {
						map.get(key).addAll(map.get(tempKey));
						ArrayList<String> noDup = removeDuplicate(map.get(key));
						map.get(key).clear();
						map.get(key).addAll(noDup);
					}
					
				}
			}
			

//			map.forEach((k,v) -> System.out.println(k + ": " + v.toString()));
			int orbit = 0;
			
			for(ArrayList<String> values : map.values()) {
				orbit += values.size();
			}
			
			System.out.println(orbit);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> removeDuplicate(ArrayList<String> list) {
		ArrayList<String> temp = new ArrayList<String>();
		
		for (int i = 0; i < list.size(); i++) {
			if(!temp.contains(list.get(i))) temp.add(list.get(i));
		}
		
		return temp;
	}
	
	private static class MyTree {
		
		class Node {
			String label;
			LinkedList<Node> children = new LinkedList<Day6.MyTree.Node>();
			
			Node(String label) {
				this.label = label;
			}
		}
		
		void addChild(Node parent, Node child) {
			parent.children.add(child);
		}
		
		void addChildren(Node parent, Collection<Node> children) {
			parent.children.addAll(children);
		}
		
		
	}
}
