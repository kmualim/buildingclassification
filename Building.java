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
	
	public Building addBuilding (OneBuilding b){
		// ADD YOUR CODE HERE
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
				
			return root; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
		} 

	
	public Building addBuildings (Building b){
		// ADD YOUR CODE HERE 
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
		return returnBuilding;// DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	public Building removeBuilding (OneBuilding b){
		// ADD YOUR CODE HERE
		// if b is the root 
		
		//if tree is empty 
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
		
	
	public int oldest(){
		// ADD YOUR CODE HERE 
		int older = 0;
		if (this.older == null) { 
			older = this.data.yearOfConstruction;
		}
		else { 
			older = this.older.oldest();
		}
		return older; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
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
		
		// DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	
	public OneBuilding highestFromYear (int year){
		// ADD YOUR CODE HERE 
		
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
		}// DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}

	
	public int numberFromYears (int yearMin, int yearMax){
		// ADD YOUR CODE HERE
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
		
	}// DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	
	public int[] costPlanning (int nbYears){
		
		// ADD YOUR CODE HERE
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
		
		return costs;  // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
}
