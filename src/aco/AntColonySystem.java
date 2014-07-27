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

package aco;

import problem.Problem;
import aco.ant.Ant;
import aco.ant.Ant4ACS;

/**
 * Ant Colony System Class
 * 
 * @author Thiago Nascimento
 * @since 2014-07-27
 * @version 1.0
 */
public class AntColonySystem extends ACO {

	public AntColonySystem(Problem problem, int numberOfAnts, int interations) {
		super(problem, numberOfAnts, interations);
	}

	@Override
	public void daemonActions() {
		
	}

	@Override
	public void initializeAnts() {
		this.ant = new Ant[numberOfAnts];

		for (int k = 0; k < numberOfAnts; k++) {
			ant[k] = new Ant4ACS(this);
			ant[k].addObserver(this);
		}
	}

	@Override
	public void globalUpdateRule() {
		for (int i = 0; i < p.getNodes(); i++) {
			for (int j = i; j < p.getNodes(); j++) {
				if (i != j && bestAnt.path[i][j] == 1) {
					double deltaTau = p.getDeltaTau(bestAnt, i, j);

					double evaporation = (1.0 - RHO) * tau[i][j];
					double deposition = RHO * deltaTau;

					tau[i][j] = evaporation + deposition;
					tau[j][i] = evaporation + deposition;
				}
			}
		}
	}
}
