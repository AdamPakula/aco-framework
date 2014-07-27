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

import sys.InstanceReader;

/**
 *  Instance Reader in TSPLIB format
 * 
 * @author Thiago Nascimento
 * @since 2014-07-27
 * @version 1.0
 */
public class TSPLIBReader {
	
	protected InstanceReader reader;
	
	private int dimension;

	protected double[][] coord;
	
	private double[][] distance;

	public TSPLIBReader(InstanceReader reader) {
		this.reader = reader;
		
		readHeader();
		readCoordinates();
		convertCoordToDistance();
	}
	
	public void readHeader(){
		String line = reader.readLine();
		
		while (! line.equalsIgnoreCase("NODE_COORD_SECTION")) {
		    String[] split = line.split(":");
		    
		    String key = split[0].trim();
		   
		    if(key.equalsIgnoreCase("DIMENSION")){
		    	dimension = Integer.valueOf(split[1].trim());
		    }
		   	    
		    line = reader.readLine();
		    
		    if(line == null){
		    	break;
		    }
		}
	}
	
	private void readCoordinates(){
		coord = new double[dimension][3];
		
		String line = reader.readLine();
		int i = 0;
		while (line != null) {
			String[] split = line.split(" ");
			
			coord[i][0] = Double.valueOf(split[0].trim());
			coord[i][1] = Double.valueOf(split[1].trim());
			coord[i][2] = Double.valueOf(split[2].trim());
			
			i++;
			line = reader.readLine();
		}		
	}
	
	private void convertCoordToDistance(){
		distance = new double[dimension][dimension];
		
		for (int i = 0; i < dimension; i++) {
			for (int j = i; j < dimension; j++) {
				if (i != j) {
					double x1 = coord[i][1];
					double y1 = coord[i][2];
					double x2 = coord[j][1];
					double y2 = coord[j][2];
					
					distance[i][j] = euclideanDistance(x1,y1,x2,y2);
					distance[j][i] = distance[i][j];
				}
			}
		}
	}
	public static double euclideanDistance(double x1, double y1, double x2, double y2){
	    double xDistance = Math.abs(x1 - x2);
	    double yDistance = Math.abs(y1 - y2);
	    
		return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
	}

	public int getDimension() {
		return dimension;
	}

	public double[][] getDistance() {
		return distance;
	}
}
