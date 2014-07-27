/*
 * Copyright 2014 Thiago Nascimento
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package util;

import java.util.ArrayList;
import java.util.List;

import problem.TravellingSalesmanProblem;

/**
 * Nearest Neighbour Algorithm
 * 
 * @author Thiago Nascimento
 * @version 1.0
 * @since 2014-07-24
 */
public class NearestNeighbour {
	
	public List<Integer> solve(TravellingSalesmanProblem p) {
		List<Integer> citiesToVisit = new ArrayList<Integer>();
		List<Integer> solution = new ArrayList<Integer>();
		int currentCity = PseudoRandom.randInt(0, p.getNodes() - 1);

		for (int i = 0; i < p.getNodes(); i++) {
			if (i != currentCity) {
				citiesToVisit.add(new Integer(i));
			}
		}
		
		solution.add(new Integer(currentCity));

		while (!citiesToVisit.isEmpty()) {
			int nextCity = -1;
			double minDistance = Double.MAX_VALUE;

			for (Integer j : citiesToVisit) {
				double distance = p.getDistance(currentCity, j);
				if (distance < minDistance) {
					minDistance = distance;
					nextCity = j;
				}
			}

			solution.add(new Integer(nextCity));
			citiesToVisit.remove(new Integer(nextCity));
			currentCity = nextCity;
		}
		
		//Add the start city in the solution
		solution.add(solution.get(0));

		return solution;
	}
}
