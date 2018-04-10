package assignment3;

public class Building {

	OneBuilding data;
	Building older;
	Building same;
	Building younger;
	
	public Building(OneBuilding data){
		this.data = data;
		this.older = null;
		this.same = null;
		this.younger = null;
	}
	
	//TO STRING METHOD
	public String toString(){
		String result = this.data.toString() + "\n";
		if (this.older != null){
			result += "older than " + this.data.toString() + " :\n";
			result += this.older.toString();
		}
		if (this.same != null){
			result += "same age as " + this.data.toString() + " :\n";
			result += this.same.toString();
		}
		if (this.younger != null){
			result += "younger than " + this.data.toString() + " :\n";
			result += this.younger.toString();
		}
		return result;
	}
	
	// THIS METHOD ADDS A BUILDING TO ONEBUILDING BASED ON YEAR OF CONSTRUCTION 
	// IF YEAR OF CONSTRUCTION IS THE SAME, BUILDINGS ARE SORTED BY HEIGHT
	// RETURNS THE ROOT OF THE UPDATED TREE
	public Building addBuilding (OneBuilding b){
			int x = b.yearOfConstruction;
			int y = b.height;
	
			Building root = this;

			if(root.data.yearOfConstruction < x) { 
				//if younger is not null, add building to younger
				if (root.younger != null) {
					root.younger.addBuilding(b);
			}
				// if younger is null, initialize building
				else {
					root.younger = new Building(b); 
			}	  
		}
				//if b is older 
			else if (root.data.yearOfConstruction > x) { 
				if (root.older != null) { 
					root.older.addBuilding(b); 
				}
				else { 
					root.older = new Building(b); 
				}
			}
			// if b is same year
			else{ 		 
				if (root.data.height < y) { 
					OneBuilding temp = this.data; 
					this.data = b;
					Building b2 = new Building(temp); 
					b2.same = this.same; 
					this.same = b2;
				}
				else if (root.same != null) { 
					if (root.same.data.height < y) { 
						Building b2 = new Building(b); 
						b2.same = this.same; 
						this.same = b2; 
					}
					else { 
						root.same.addBuilding(b); 
					}
				}
				else {
					root.same = new Building(b);
				}
			}  
				
			return root; 
		} 

	// ADDS A BUILDING STRUCTURE B TO CURRENT BUILDING
	// RETURNS UPDATED BUILDING
	public Building addBuildings (Building b){
		
			Building returnBuilding = this.addBuilding(b.data);
			
			if (b.older != null) { 
				returnBuilding = addBuildings(b.older); 
				
			}
			if (b.same != null) { 
				returnBuilding = addBuildings(b.same); 
				
			}
			if (b.younger != null) { 
				returnBuilding = addBuildings(b.younger); 
			}
		return returnBuilding;
	}
	
	// REMOVING A ONEBUILDING FROM THE TREE STRUCTURE
	public Building removeBuilding (OneBuilding b){
		
		//IF TREE IS NULL
		if (this.data == null) { 
			return null; 
		}
		
		//if the tree is the root 
		if (this.data.equals(b)) {
			if (this.same != null) { 
				// replace the root with this.same 
				Building carry = this.same;  
				if (this.older != null) { 
					carry.addBuildings(this.older); 
				}
				if (this.younger!= null) { 
					carry.addBuildings(this.younger);
				}
				if (this.younger == null && this.older == null) { 
					return carry; 
				}
				return carry; 
			}
			
			else if (this.older != null) { 
				Building b1 = this.older; 
				if (this.younger != null) {
					b1.addBuildings(this.younger);
				}
				return b1;
			}
			
			else if (this.younger != null) { 
					Building b2 = this.younger;
					return b2;
			}
			
			else { 
				return null;
			}
		}
		
		else {
			if(this.older != null) { 
				this.older = this.older.removeBuilding(b);
				}
			if (this.same != null) {
				this.same = this.same.removeBuilding(b);
				}
			if (this.younger != null) {
				this.younger = this.younger.removeBuilding(b);
				}
			}
			return this; 
	}
		
	// RETURNS THE OLDEST BUILDING
	public int oldest(){
		 
		int older = 0;
		if (this.older == null) { 
			older = this.data.yearOfConstruction;
		}
		else { 
			older = this.older.oldest();
		}
		return older; 
	}
	
	// RETURNS THE HEIGHT OF THE HEIGHEST ONEBUILDING
	public int highest(){
		// ADD YOUR CODE HERE
		int temp = 0;
		int temp2 = 0;
		int highest = this.data.height;
		
		if (this.older != null) {
			temp = this.older.highest(); 
			if (temp > highest) { 
				highest = temp;
				}
		}
		if (this.younger != null) {
			temp2 = this.younger.highest();
			if (temp2 > highest) { 
				highest = temp2; 
			}
		}
		
		return highest;
		}
		
		
	// RETURNS HIGHEST ONEBUILDING IN THAT YEAR
	public OneBuilding highestFromYear (int year){
		
		if (this.data.yearOfConstruction == year) { 
			return this.data; 
		}
		else if (this.data.yearOfConstruction> year && this.older != null) { 
			return this.older.highestFromYear(year); 
		}
		else if (this.data.yearOfConstruction < year && this.younger != null) {
			return this.younger.highestFromYear(year);	
			}
		else{ 
			return null;
		}
	}

	// RETURNS THE NUMBER OF ONEBUILDINGS WHOSE YEAR OF CONSTRUCTION 
	// IS WITHIN YEARMIN TO YEARMAX
	public int numberFromYears (int yearMin, int yearMax){
		
		int total = 0; 
		
		if(yearMin>yearMax) { 
			return 0;
		}
		if(this.data.yearOfConstruction >= yearMin && this.data.yearOfConstruction <= yearMax) {
			total += 1; 
			Building temp = this.same; 
			while( temp!= null) {
				total += 1; 
				temp = temp.same; 
			}
		}
		if(this.older != null) { 
			total += this.older.numberFromYears(yearMin, yearMax);
		}
		
		if(this.younger != null) { 
			total += this.younger.numberFromYears(yearMin, yearMax);
		}
	
		return total;
		
	}
	
	// METHODS TAKES THE NBYEARS AND RETURNS THE TOTAL COST FOR ALL THE BUILDINGS 
	// THAT NEED REPAIRS WITHIN 2018 TO 2018+NBYEARS
	public int[] costPlanning (int nbYears){
		
		
		int [] costs = new int[nbYears]; 
		
		if(this.data.yearForRepair<(2018+nbYears)) {
				costs[(this.data.yearForRepair-2018)%nbYears] += this.data.costForRepair; 
			}
		
		if(this.same != null) {
			int [] temp = this.same.costPlanning(nbYears);
				for (int i=0; i<nbYears; i++) { 
					costs[i] += temp[i]; 
				}
			}
		
		if(this.older != null) { 
			int [] temp = this.older.costPlanning(nbYears);
				for (int i=0; i<nbYears; i++) { 
					costs[i] += temp[i];
				}
			}
		if(this.younger != null) { 
			int [] temp = this.younger.costPlanning(nbYears); 
			for (int i=0; i<nbYears; i++) { 
				costs[i] += temp[i];
			}
		}
		
		return costs;  
	}
	
}
