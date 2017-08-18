var customers = [ {
	"name" : "Peter Jackson",
	"gender" : "male",
	"year_born" : 1961,
	"joined" : "1997",
	"num_hires" : 17000
},

{
	"name" : "Jane Campion",
	"gender" : "female",
	"year_born" : 1954,
	"joined" : "1980",
	"num_hires" : 30000
},

{
	"name" : "Roger Donaldson",
	"gender" : "male",
	"year_born" : 1945,
	"joined" : "1980",
	"num_hires" : 12000
},

{
	"name" : "Temuera Morrison",
	"gender" : "male",
	"year_born" : 1960,
	"joined" : "1995",
	"num_hires" : 15500
},

{
	"name" : "Russell Crowe",
	"gender" : "male",
	"year_born" : 1964,
	"joined" : "1990",
	"num_hires" : 10000
},

{
	"name" : "Lucy Lawless",
	"gender" : "female",
	"year_born" : 1968,
	"joined" : "1995",
	"num_hires" : 5000
},

{
	"name" : "Michael Hurst",
	"gender" : "male",
	"year_born" : 1957,
	"joined" : "2000",
	"num_hires" : 15000
},

{
	"name" : "Andrew Niccol",
	"gender" : "male",
	"year_born" : 1964,
	"joined" : "1997",
	"num_hires" : 3500
},

{
	"name" : "Kiri Te Kanawa",
	"gender" : "female",
	"year_born" : 1944,
	"joined" : "1997",
	"num_hires" : 500
},

{
	"name" : "Lorde",
	"gender" : "female",
	"year_born" : 1996,
	"joined" : "2010",
	"num_hires" : 1000
},

{
	"name" : "Scribe",
	"gender" : "male",
	"year_born" : 1979,
	"joined" : "2000",
	"num_hires" : 5000
},

{
	"name" : "Kimbra",
	"gender" : "female",
	"year_born" : 1990,
	"joined" : "2005",
	"num_hires" : 7000
},

{
	"name" : "Neil Finn",
	"gender" : "male",
	"year_born" : 1958,
	"joined" : "1985",
	"num_hires" : 6000
},

{
	"name" : "Anika Moa",
	"gender" : "female",
	"year_born" : 1980,
	"joined" : "2000",
	"num_hires" : 700
},

{
	"name" : "Bic Runga",
	"gender" : "female",
	"year_born" : 1976,
	"joined" : "1995",
	"num_hires" : 5000
},

{
	"name" : "Ernest Rutherford",
	"gender" : "male",
	"year_born" : 1871,
	"joined" : "1930",
	"num_hires" : 4200
},

{
	"name" : "Kate Sheppard",
	"gender" : "female",
	"year_born" : 1847,
	"joined" : "1930",
	"num_hires" : 1000
},

{
	"name" : "Apirana Turupa Ngata",
	"gender" : "male",
	"year_born" : 1874,
	"joined" : "1920",
	"num_hires" : 3500
},

{
	"name" : "Edmund Hillary",
	"gender" : "male",
	"year_born" : 1919,
	"joined" : "1955",
	"num_hires" : 10000
},

{
	"name" : "Katherine Mansfield",
	"gender" : "female",
	"year_born" : 1888,
	"joined" : "1920",
	"num_hires" : 2000
},

{
	"name" : "Margaret Mahy",
	"gender" : "female",
	"year_born" : 1936,
	"joined" : "1985",
	"num_hires" : 5000
},

{
	"name" : "John Key",
	"gender" : "male",
	"year_born" : 1961,
	"joined" : "1990",
	"num_hires" : 20000
},

{
	"name" : "Sonny Bill Williams",
	"gender" : "male",
	"year_born" : 1985,
	"joined" : "1995",
	"num_hires" : 15000
},

{
	"name" : "Dan Carter",
	"gender" : "male",
	"year_born" : 1982,
	"joined" : "1990",
	"num_hires" : 20000
},

{
	"name" : "Bernice Mene",
	"gender" : "female",
	"year_born" : 1975,
	"joined" : "1990",
	"num_hires" : 30000
} ];

var htmlString = "";

function makeTables() {

	//Display entire records
htmlString += "<h1>Video Rental Customer Records</h1> <table> <tr> <th>Name</th> <th>Gender</th> <th>Birth Year</th> <th>Join Date</th> <th>Videos Hired</th> </tr>";

for (var i = 0; i < customers.length; i++) {
	htmlString += "<tr>  <td>" + customers[i].name + "</td> <td>"
			+ customers[i].gender + "</td><td>"
			+ customers[i].year_born + "</td><td>" 
			+ customers[i].joined + "</td><td>"
			+ customers[i].num_hires + "</td>  </tr>";
}

htmlString += "</table>";


//Display gender profile in table
htmlString += "<h1>Customer Gender Profile</h1> <table> <tr> <th>Gender</th> <th>Number of Customers</th> <th>Percentage</th> </tr>";

var males = 0;
var females = 0;

for (var i = 0; i < customers.length; i++) {
	
	if (customers[i].gender == "male")
		males++;
	
	if (customers[i].gender == "female")
		females++;
	
}

htmlString += "<tr>  <td>Female</td> <td>"
+ females + "</td><td>"
+ (females / (females + males)*100) + "%</td> </tr>";

htmlString += "<tr>  <td>Male</td> <td>"
+ males + "</td><td>"
+ (males / (females + males)*100) + "%</td> </tr>";

htmlString += "</table>";


//Display age ranges
htmlString += "<h1>Age Range</h1> <table> <tr> <th>Gender</th> <th>Number of Customers</th>  <th>Percentage</th> </tr>";

var Under31= 0;
var Under65 = 0;
var Above64=0;

for (var i = 0; i < customers.length; i++) {
	var age = 2016 - customers[i].year_born;
	
	if (age < 31) {
		Under31++;
	} else if (age < 65) {
		Under65++;
	}else if (age > 64) {
		Above64++;
	}
	
}

htmlString += "<tr>  <td> Thirty Years and Under</td> <td>"
+ Under31 + "</td><td>"
+ (Under31 / (Under31 + Under65 + Above64)*100) + "%</td> </tr>";

htmlString += "<tr>  <td> 30 to 64 years old</td> <td>"
	+ Under65 + "</td><td>"
	+ (Under65 / (Under31 + Under65 + Above64)*100) + "%</td> </tr>";

htmlString += "<tr>  <td>65 Years old and above</td> <td>"
	+ Above64 + "</td><td>"
	+ (Above64 / (Under31 + Under65 + Above64)*100) + "%</td> </tr>";

htmlString += "</table>";


//Make table of loyalty categories
htmlString += "<h1>Loyalty Status categories</h1> <table> <tr> <th>Category</th> <th>No. of Customers</th> <th>Percentage</th> </tr>";

var gold= 0;
var silver= 0;
var bronze=0;

for (var i = 0; i < customers.length; i++) {
	var membershipDurationInWeeks = (2016 - customers[i].joined) * 52;
	var hiresPerWeek = customers[i].num_hires / membershipDurationInWeeks;
	
	if (hiresPerWeek > 4)
		gold++;
	else if (hiresPerWeek >1)
		silver++;
	else
		bronze++;
	
}

htmlString += "<tr>  <td>Gold</td> <td>"
+ gold + "</td><td>"
+ (gold / (gold + silver + bronze)*100) + "%</td> </tr>";

htmlString += "<tr>  <td>Silver</td> <td>"
	+ silver + "</td><td>"
	+ (silver / (gold + silver + bronze)*100) + "%</td> </tr>";

htmlString += "<tr>  <td>Bronze</td> <td>"
	+ bronze + "</td><td>"
	+ (bronze / (gold + silver + bronze)*100) + "%</td> </tr>";

htmlString += "</table>";




document.getElementById("main").innerHTML = htmlString;




}
