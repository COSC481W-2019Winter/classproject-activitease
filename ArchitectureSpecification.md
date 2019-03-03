# User class

## Properties

| Name  | Type | Description
| ------------- | ------------- | ------------- 
| userInterests  | Interest[]  | An array of Interest objects with accessable variables and displays, and a button to access their page.  	
| nInterests  | int | A size integer for the interest array.
| sidebar | Button | A button to display the sidebar.
| about_us | Button | A button to take the user to the About Us page.
| faq | Button | A button to take the user to the FAQ page along with support.
| settings | Button | A button to take the user to the settings page.
| streakCounter | int | Number of days in a row you have completed your activity. 


## Functionality

| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| loadInterests()  | None | None | Loads up interest buttons/previews when the app is opened.
| addInterest()  | String interestname, int activityAmount, String activityPeriod, int activityLength, int numNotifications  | None  | Initializes an interest.
| searchInterest()  | String interestName | int arrayPos | Given an interest name, this function searches through the userInterest array and finds the array position of that interest.	
| deleteInterest()  | int arrayPos  | None  | Deletes an interest by initializing the constructor array position to null, the pulling all subsequent interests in the array down to fill in, and subtracts one from nInterests if the interest is found.	
| displayInterest()  | int arrayPos, View v  | None  | Accesses a user's interest.
| displaySidebar()  | None* | None | Pulls up the sidebar display, which shows the settings, FAQ, and contact us buttons.
| onSupport()  |  View v | None | Takes user to the support page. 
| onFAQ()  |  View v | None | Takes user to the FAQ page. 
| onSettings()  |  View v | None | Takes user to the Settings page. 
| onAddActivity()   | View v | None | Takes user to the add activity page. 

## Connections 
| Input or Output  | Function | Description
| -------------    | ---------- | ---------- 
| Input            | onSupport()   | Takes user to the support page when the support button is clicked. 
| Input            | onFAQ()   | Takes user to the FAQ page when the FAQ button is clicked. 
| Input            | onSettings()   | Takes user to the settings page when the settings button is clicked. 
| Input            | onAddActivity()   | Takes user to addInterest page when Add Interest button is clicked. 


# Timer Class

## Properties:														
| Name  | Type | Description
| ------------- | ------------- | ------------- 										
| startTimer | boolean | boolean for whether the start button was pushed.
| stopTimer  | boolean |	boolean for whether the stop button was pushed.
|updateTimer |	int |	Updates the timer based on how much time remains.
| timer      | Timer | Instance of Timer API. 
														
## Functionality											
| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------					
| displayTimer() |	none(?) |	none |	Instantiates timer object specific to interest. 
| startTimer() |	view V | none |	starts the timer for the interest. Also calls displayTime() and will do so until stopTimer() is called.	
| stopTimer() |	view V | none |	stops the timer and saves the progress.
| timerRunning() |	int updateTimer | none |	Continuously being reduced in seconds until 0 seconds remain or stopTimer() is called.

														
## Connections:
| Input or Output  | Function | Description
| ----------  | ---------- |  -----------							
| Output |      timerRunning()   |      Increments timer when running. 
| Input  |      startTimer()     |      Starts timer object when user clicks Start Activity button. 
| Input  |      stopTimer()      |      Stops timer object when user clicks stop Activity button. 

-------------------------------------------------------------------------

# MainActivity 

## Functionality / Connections

| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| onCreate | Bundle savedInstanceState| void | Pulls up a saved state of the app, opens the home page, and pulls open all necessary components for the app. 
| onBackPressed | N/A | void | Sets up actions for when the user presses the back button.
| onCreateOptionsMenu | Menu menu | boolean | Adds items to the action bar, if it is present.
| onOptionsItemSelected | MenuItem item | boolean | Sends settings from the options menu*.
| onNavigationItemSelected | @NonNull MenuItem item | boolean | Generates fragments for pages given a page input.
| openAddInterest | View view |        | Opens add interest page.
| onInterestClick | View view |        | On interest click interest page opens to clicked activity.
| submitEditInterest | View view |      | Submits the editted interest and updates instance of interest page with new data. 
| startStopTimer | View view |      |  Starts and stops the timer, displays text message on start activity to confirm starting, changes button text on click. 

# Add_Interest

## Properties 

| Name  | Type | Description
| ------------- | ------------- | ------------- 
| interestName | String | Name of the User's Interest.												
| periodFreq |	int	| How frequent the user would like to practice their interest per period.												
| basePeriodSpan |	int |	How often the user wants to reset the period.												
| activityLength |	int |	How long the user wants to practice their interest per period.	

| numNotifications |    int |	How many notifications the user wants a day. 

## Functionality

| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| addInterest | View view |        | Calls instance of user to store form data and opens up homepage with updated interest info. 
| CheckData   |           |        | Checks to make sure that data is in correct format, if not indicates with red dots which fields are incorrect. 

## Connections:								
|Input or Output | Function | Description
| -------------    | ---------- | ---------- 
| Input | User.addInterest() | Calls instance of user add interest method to store interest data 				


# Interest_Fragment

## Properties 

| Name  | Type | Description
| ------------- | ------------- | ------------- 
| interestName | TextView | Displays the name of the interest in the edit Interest container and on top of the Timer.
| activityLength | Timer | Sets the timer to the length of the activity. 

## Functionality

| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| onCreateView() | @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState| View |

# Add_Interest_Fragment 

## Functionality

| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| onCreateView() | @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState| View |

# Home_Page Fragment

## Properties 

## Functionality

| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| onCreateView() | @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState| View |

# About_Us Fragment

## Functionality

| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| onCreateView()  | @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState  | inflater.inflate(R.layout.about_us_page, container, false)  | Returns the layout format for the About us Page

## Connections 
| Input or Output  | Function | Description
| -------------    | ---------- | ---------- 


# FAQ_Fragment
													
## Functionality:								
| Name | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| onCreateView(); | @NonNull LayoutInflater inflater, @Nullable ViewGroup container,  @Nullable Bundle savedInstanceState | inflator.inflate(R.layout.faq_page, container, false) | Returns the layout format of the FAQ page	|postButton();|@ArrayList<String>,|None|Creates and registers a clicked button for posting the content.
								
## Connections:								
|Input or Output | Function | Description
| -------------    | ---------- | ---------- 

