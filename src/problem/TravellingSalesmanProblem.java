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

import java.util.List;

import util.NearestNeighbour;
import util.TSPLIBReader;
import aco.ant.Ant;

/**
 * The Travelling Salesman Problem Class
 * 
 * @author Thiago Nascimento
 * @since 2014-07-27
 * @version 1.0
 */
public class TravellingSalesmanProblem extends Problem {

	public static final int Q = 1;
	
	/** Distance Matrix */
	protected double[][] distance;

	/** Number of Cities */
	protected int numberOfCities;
	
	/** Nearest Neighbour heuristic */
	protected double lnn;
	
	public TravellingSalesmanProblem(String filename) {
		this(filename, false);
	}

	public TravellingSalesmanProblem(String filename,boolean isTspLibFormmat) {
		super(filename);
		
		reader.open();
		
		if (isTspLibFormmat) {
			TSPLIBReader r = new TSPLIBReader(reader);
			numberOfCities = r.getDimension();
			distance = r.getDistance();
		} else {
			numberOfCities = reader.readInt();
			distance = reader.readDoubleMatrix(numberOfCities, numberOfCities);
		}
				
		NearestNeighbour nn = new NearestNeighbour();		
		lnn = evaluate(nn.solve(this));

		reader.close();		
	}
	
	public double getDistance(int i, int j) {
		return distance[i][j];
	}
	
	@Override
	public double getNij(int i, int j) {
		return 1.0 / distance[i][j];
	}

	@Override
	public int getNodes() {
		return numberOfCities;
	}

	@Override
	public double getT0() {
		return 1.0 / (numberOfCities * lnn);
	}

	@Override
	public void initializeTheMandatoryNeighborhood(Ant ant) {
		//Add all nodes (or cities) less the start node
		for (int i = 0; i < getNodes(); i++) {
			if(i != ant.currentNode){
				ant.nodesToVisit.add(new Integer(i));
			}
		}		
	}
	
	@Override
	public void updateTheMandatoryNeighborhood(Ant ant) {
				
	}
	
	@Override
	public boolean better(Ant ant, Ant bestAnt) {
		return bestAnt == null || ant.tourLength < bestAnt.tourLength;
	}

	@Override
	public double evaluate(Ant ant) {
		// Add start cities before evaluate the solution
		ant.tour.add(ant.tour.get(0));
		ant.path[ant.currentNode][ant.tour.get(0)] = 1;
		ant.path[ant.tour.get(0)][ant.currentNode] = 1;

		return evaluate(ant.tour);
	}
	
	public double evaluate(List<Integer> tour){
		double totalDistance = 0;
		
		for (int h = 1; h < tour.size(); h++) {
			int i = tour.get(h - 1);
			int j = tour.get(h);
			totalDistance += distance[i][j];
		}
		
		return totalDistance;
	}

	@Override
	public double getDeltaTau(Ant ant, int i, int j) {
		return Q / ant.tourLength;
	}	
}
