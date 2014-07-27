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

package problem;

import java.util.ArrayList;
import java.util.List;

import aco.ant.Ant;

/**
 * The Next Release Problem
 * <br>
 * Based in Article "Ant Colony Optimization for the Next Release Problem"
 * by José del Sagrado
 * 
 * @author Thiago Nascimento
 * @since 2014-07-27
 * @version 1.0
 */
public class NextReleaseProblem extends Problem{
	
	protected int requirements;
	
	protected int customers;
	
	protected int[] cost;
	
	protected double[] satisfaction;
	
	protected int[][] customerSatisfaction;
	
	protected int[] customerImportance;
	
	protected double satR;
	
	protected double B;
	
	protected static double MI = 1.0;

	public NextReleaseProblem(String filename) {
		super(filename);
		
		reader.open();
		
		int[] param = reader.readIntVector(" ");
		this.requirements = param[0];
		this.customers = param[1];
		this.customerImportance = reader.readIntVector(" ");
		this.cost = reader.readIntVector(" ");
		this.customerSatisfaction = reader.readIntMatrix(customers,requirements," ");
		this.satisfaction = new double[requirements];
		this.B = reader.readInt();
		
		reader.close();
		
		for (int i = 0; i < requirements; i++) {
			for (int j = 0; j < customers; j++) {
				satisfaction[i] += customerImportance[j] * customerSatisfaction[j][i];
			}
		}
		
		for (int i = 0; i < requirements; i++) {
			satR += satisfaction[i];
		}
	}

	@Override
	public double getNij(int i, int j) {
		return MI * (satisfaction[j] / cost[j]);
	}

	@Override
	public int getNodes() {
		return requirements;
	}

	@Override
	public double getT0() {
		return 1 / satR;
	}

	@Override
	public void initializeTheMandatoryNeighborhood(Ant ant) {
		for (int i = 0; i < getNodes(); i++) {
			if(i != ant.currentNode){
				if (cost[i] <= B) {
					ant.nodesToVisit.add(new Integer(i));
				}
			}
		}		
	}
	
	@Override
	public void updateTheMandatoryNeighborhood(Ant ant) {
		List<Integer> nodesToRemove = new ArrayList<Integer>();

		double totalCost = 0.0;

		for (Integer i : ant.tour) {
			totalCost += cost[i];
		}

		for (Integer i : ant.nodesToVisit) {
			if (totalCost + cost[i] > B) {
				nodesToRemove.add(i);
			}
		}

		for (Integer i : nodesToRemove) {
			ant.nodesToVisit.remove(i);
		}
	}

	@Override
	public double evaluate(Ant ant) {
		double sum = 0.0;

		for (Integer i : ant.tour) {
			sum += satisfaction[i];
		}

		return sum;
	}

	@Override
	public boolean better(Ant ant, Ant bestAnt) {
		return bestAnt == null || ant.tourLength > bestAnt.tourLength;
	}

	@Override
	public double getDeltaTau(Ant ant, int i, int j) {
		return ant.tourLength / satR;
	}
}
