/*
--- Day 3: Crossed Wires ---

The gravity assist was successful, and you're well on your way to the Venus refuelling station. During the rush back on Earth, the fuel management system wasn't completely installed, so that's next on the priority list.

Opening the front panel reveals a jumble of wires. Specifically, two wires are connected to a central port and extend outward on a grid. You trace the path each wire takes as it leaves the central port, one wire per line of text (your puzzle input).

The wires twist and turn, but the two wires occasionally cross paths. To fix the circuit, you need to find the intersection point closest to the central port. Because the wires are on a grid, use the Manhattan distance for this measurement. While the wires do technically cross right at the central port where they both start, this point does not count, nor does a wire count as crossing with itself.

For example, if the first wire's path is R8,U5,L5,D3, then starting from the central port (o), it goes right 8, up 5, left 5, and finally down 3:

...........
...........
...........
....+----+.
....|....|.
....|....|.
....|....|.
.........|.
.o-------+.
...........

Then, if the second wire's path is U7,R6,D4,L4, it goes up 7, right 6, down 4, and left 4:

...........
.+-----+...
.|.....|...
.|..+--X-+.
.|..|..|.|.
.|.-X--+.|.
.|..|....|.
.|.......|.
.o-------+.
...........

These wires cross at two locations (marked X), but the lower-left one is closer to the central port: its distance is 3 + 3 = 6.

Here are a few more examples:

    R75,D30,R83,U83,L12,D49,R71,U7,L72
    U62,R66,U55,R34,D71,R55,D58,R83 = distance 159
    R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
    U98,R91,D20,R16,D67,R40,U7,R15,U6,R7 = distance 135

What is the Manhattan distance from the central port to the closest intersection?

Your puzzle answer was 709.

--- Part Two ---

It turns out that this circuit is very timing-sensitive; you actually need to minimize the signal delay.

To do this, calculate the number of steps each wire takes to reach each intersection; choose the intersection where the sum of both wires' steps is lowest. If a wire visits a position on the grid multiple times, use the steps value from the first time it visits that position when calculating the total value of a specific intersection.

The number of steps a wire takes is the total number of grid squares the wire has entered to get to that location, including the intersection being considered. Again consider the example from above:

...........
.+-----+...
.|.....|...
.|..+--X-+.
.|..|..|.|.
.|.-X--+.|.
.|..|....|.
.|.......|.
.o-------+.
...........

In the above example, the intersection closest to the central port is reached after 8+5+5+2 = 20 steps by the first wire and 7+6+4+3 = 20 steps by the second wire for a total of 20+20 = 40 steps.

However, the top-right intersection is better: the first wire takes only 8+5+2 = 15 and the second wire takes only 7+6+2 = 15, a total of 15+15 = 30 steps.

Here are the best steps for the extra examples from above:

    R75,D30,R83,U83,L12,D49,R71,U7,L72
    U62,R66,U55,R34,D71,R55,D58,R83 = 610 steps
    R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
    U98,R91,D20,R16,D67,R40,U7,R15,U6,R7 = 410 steps

What is the fewest combined steps the wires must take to reach an intersection?

Your puzzle answer was 13836.
*/


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day3 {
	final static int ARR_SIZE = 200000;

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(new File("day3.in"));

			String wire1 = sc.nextLine();
			String wire2 = sc.nextLine();
			
//			boolean[][] matrix1 = toMatrix(wire1);
//			boolean[][] matrix2 = toMatrix(wire2);
			
//			for (int i = 0; i < matrix1.length; i++) {
//				for (int j = 0; j < matrix1[i].length; j++) {
//					if(i == ARR_SIZE / 2 && j == ARR_SIZE / 2) System.out.print("o");
//					else if(matrix1[i][j]) System.out.print("+");
//					else System.out.print(".");
//				}
//				System.out.println();
//			}
//			
//			for (int i = 0; i < matrix2.length; i++) {
//				for (int j = 0; j < matrix2[i].length; j++) {
//					if(i == ARR_SIZE / 2 && j == ARR_SIZE / 2) System.out.print("o");
//					else if(matrix2[i][j]) System.out.print("+");
//					else System.out.print(".");
//				}
//				System.out.println();
//			}
			
//			int minDistance = ARR_SIZE;
//			
//			for (int i = 0; i < matrix1.length; i++) {
//				for (int j = 0; j < matrix1[i].length; j++) {
//					if(i == ARR_SIZE / 2 && j == ARR_SIZE / 2) continue;
//					if(matrix1[i][j] && matrix2[i][j]) {
//						int distance = Math.abs(ARR_SIZE/2 - i) + Math.abs(ARR_SIZE/2 - j);
//						if(distance < minDistance) minDistance = distance;
//					}
//				}
//			}
			
//			System.out.println(minDistance);
			
			int[][] matrix1 = toMatrix(wire1);
			int[][] matrix2 = toMatrix(wire2);
//			System.out.println(Arrays.deepToString(matrix1));
//			System.out.println(Arrays.deepToString(matrix2));
//			int minDistance = Integer.MAX_VALUE;
//			for (int i = 1; i < matrix1.length; i++) {
//				if(matrix1[i][2] == 0) break;
//				for(int j = 1; j < matrix2.length; j++) {
//					if(matrix1[i][0] == matrix2[j][0] && matrix1[i][1] == matrix2[j][1]) {
//						int distance = Math.abs(matrix1[i][0]) + Math.abs(matrix1[i][1]);
//						if(distance < minDistance) minDistance = distance;
//					}
//				}
//			}
//			
//			System.out.println(minDistance);
			
			int minSteps = Integer.MAX_VALUE;
			
			for (int i = 1; i < matrix1.length; i++) {
				if(matrix1[i][2] == 0) break;
				for(int j = 1; j < matrix2.length; j++) {
					if(matrix1[i][0] == matrix2[j][0] && matrix1[i][1] == matrix2[j][1]) {
						int steps = matrix1[i][2] + matrix2[j][2];
						if(steps < minSteps) minSteps = steps;
					}
				}
			}
			System.out.println(minSteps);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	public static int[][] toMatrix(String wire) {
		int[][] matrix = new int[ARR_SIZE][3];

		String[] tokens = wire.split(",");

		int pointsIndex = 1;

		for (int i = 0; i < tokens.length; i++) {
			int steps = Integer.parseInt(tokens[i].substring(1));
			switch(tokens[i].charAt(0)) {
			case 'L':
				for (int j = 0; j < steps; j++) {
					matrix[pointsIndex][0] = matrix[pointsIndex - 1][0] - 1;
					matrix[pointsIndex][1] = matrix[pointsIndex - 1][1];
					matrix[pointsIndex][2] = matrix[pointsIndex - 1][2] + 1;
					pointsIndex++;
				}
				break;
			case 'R':
				for (int j = 0; j < steps; j++) {
					matrix[pointsIndex][0] = matrix[pointsIndex - 1][0] + 1;
					matrix[pointsIndex][1] = matrix[pointsIndex - 1][1];
					matrix[pointsIndex][2] = matrix[pointsIndex - 1][2] + 1;
					pointsIndex++;
				}
				break;
			case 'D':
				for (int j = 0; j < steps; j++) {
					matrix[pointsIndex][0] = matrix[pointsIndex - 1][0];
					matrix[pointsIndex][1] = matrix[pointsIndex - 1][1] - 1;
					matrix[pointsIndex][2] = matrix[pointsIndex - 1][2] + 1;
					pointsIndex++;
				}
				break;
			case 'U':
				for (int j = 0; j < steps; j++) {
					matrix[pointsIndex][0] = matrix[pointsIndex - 1][0];
					matrix[pointsIndex][1] = matrix[pointsIndex - 1][1] + 1;
					matrix[pointsIndex][2] = matrix[pointsIndex - 1][2] + 1;
					pointsIndex++;
				}
				break;
			default:
				System.out.println("Instruction does not exist.");
			}
		}
		return matrix;
	}
	
//	public static boolean[][] toMatrix(String wire) {
//		boolean[][] matrix = new boolean[ARR_SIZE][ARR_SIZE];
//
//		String[] tokens = wire.split(",");
//
//		int startX = ARR_SIZE / 2;
//		int startY = ARR_SIZE / 2;
//
//		for (int i = 0; i < tokens.length; i++) {
//			int steps = Integer.parseInt(tokens[i].substring(1));
//			switch(tokens[i].charAt(0)) {
//			case 'L':
//				for (int j = 0; j >= -steps; j--) {
//					matrix[startX][startY + j] = true;
//				}
//				startY -= steps;
//				break;
//			case 'R':
//				for (int j = 0; j <= steps; j++) {
//					matrix[startX][startY + j] = true;
//				}
//				startY += steps;
//				break;
//			case 'D':
//				for (int j = 0; j <= steps; j++) {
//					matrix[startX + j][startY] = true;
//				}
//				startX += steps;
//				break;
//			case 'U':
//				for (int j = 0; j >= -steps; j--) {
//					matrix[startX + j][startY] = true;
//				}
//				startX -= steps;
//				break;
//			default:
//				System.out.println("Instruction does not exist.");
//			}
//		}
//		return matrix;
//	}
	
	
}