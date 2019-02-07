# User class

## Properties

| Name  | Type | Description
| ------------- | ------------- | ------------- 
| userInterests  | Interest[]  | An array of Interest objects with accessable variables and displays.  
| addInterest             |  View        | View object for specifing button clicks           

## Functionality

| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| addInterest()  | String interestname, int activityAmount, String activityPeriod, int activityLength, int numNotifications  | None  | Initializes an interest.
| deleteInterest()  | string, interestName  | None  | Deletes an interest by searching for its name in interest class.
| updateInterest()  | None  | None  | Updates a user's interest settings.
| onAddActivity()   | View addInterest | None | Takes user to add activity page. 

## Connections 
| Input or Output  | Function | Description
| -------------    | ---------- | ---------- 
| Input            | onAddActivity   | Takes user to addInterest page when Add Interest button is clicked. 


# Interest Class

## Properties:														
| Name  | Type | Description
| ------------- | ------------- | ------------- 										
| startTimer | view | Specifies view for Start button.												
| stopTimer	| view |	Specifies view for done button.												
| interestName |	String |	Name of the User's Interest.												
| periodFreq |	int	| How frequent the user would like to practice their interest per period.												
| basePeriodSpan |	int |	How often the user wants to reset the period.												
| activityLength |	int |	How long the user wants to practice their interest per period.												
| numNotifications |	int |	The number of notifications the user would like tor receive on a daily basis.
| updateTimer |	int |	Updates the timer based on how much time remains.
| timer       | Timer | Instance of Timer API. 
														
## Functionality											
| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------					
| editInterest() |	String interestName, int periodFreq, int basePeriodSpan, int activityLength, int numNotifications | none | Edits the interest to change any of the parameters.
| submitInterest() |	none | none	| submits the interest's information back to the user class.
| displayTimer() |	none(?) |	none |	Instantiates timer object specific to interest. 
| startTimer() |	view startTimer  | none |	starts the timer for the interest. Also calls displayTime() and will do so until stopTimer() is called.	
| stopTimer() |	        view stopTimer | none |	        stops the timer. 
| timerRunning() |	int updateTimer | none |	Continuously being reduced in seconds until 0 seconds remain or stopTimer() is called.
														
## Connections:
| Input or Output  | Function | Description
| ----------  | ---------- |  -----------							
| Output |	submitInterest() |	Submits the interest's updated settings to the user class.
| Output |      timerRunning()   |      Increments timer when running. 
| Input  |      startTimer()     |      Starts timer object when user clicks Start Activity button. 
| Input  |      stopTimer()      |      Stops timer object when user clicks stop Activity button. 
