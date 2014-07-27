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

package aco.ant;

import java.util.ArrayList;

import sys.Settings;
import util.PseudoRandom;
import aco.ACO;

/**
 * The ACS Ant Class
 * 
 * @author Thiago Nascimento
 * @since 2014-07-27
 * @version 1.0
 */
public class Ant4ACS extends Ant4AS {
	
	/** Probability of best choice in tour construction */
	protected double Q0 = Settings.Q0;
	
	/** Decrease local pheromone */
	protected double P = Settings.P;
	
	public Ant4ACS(ACO aco) {
		super(aco);		
	}

	@Override
	public void explore() {
		while (!nodesToVisit.isEmpty()) {
			int nextNode = -1;

			if (PseudoRandom.randDouble(0, 1) <= Q0) {
				nextNode = doExploitation(currentNode);
			} else {
				nextNode = doExploration(currentNode);
			}

			localUpdateRule(currentNode, nextNode);

			// Save next node
			tour.add(new Integer(nextNode));
			path[currentNode][nextNode] = 1;
			path[nextNode][currentNode] = 1;

			aco.p.updateTheMandatoryNeighborhood(this);

			currentNode = nextNode;
		}
	}

	protected void localUpdateRule(int i, int j) {
		double evaporation = (1.0 - P) * aco.getTau(i, j);
		double deposition = P * aco.p.getT0();
		
		aco.setTau(i, j, evaporation + deposition);
		aco.setTau(j, i, evaporation + deposition);	
	}

	protected int doExploitation(int i) {
		int nextNode = -1;
		double maxValue = Double.MIN_VALUE;
		
		// Update the sum
		for (Integer j : nodesToVisit) {
			if (aco.getTau(i, j) == 0.0) {
				throw new RuntimeException("tau == 0.0");
			}

			double tij = aco.getTau(i, j);
			double nij = Math.pow(aco.p.getNij(i, j), BETA);
			double value = tij * nij;
			
			if(value > maxValue){
				maxValue = value;
				nextNode = j;
			}
		}
		
		if (nextNode == -1) {
			throw new RuntimeException("nextNode == -1");
		}

		nodesToVisit.remove(new Integer(nextNode));

		return nextNode;
	}

	@Override
	public Ant clone() {
		Ant ant = new Ant4ACS(aco);
		ant.id = id;
		ant.currentNode = currentNode;
		ant.tourLength = tourLength;
		ant.tour = new ArrayList<Integer>(tour);
		ant.path = path.clone();
		return ant;
	}
}