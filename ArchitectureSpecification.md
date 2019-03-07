# User class


## Properties

| Name  | Type | Description
| ------------- | ------------- | ------------- 
| userInterests  | Interest[]  | An array of Interest objects with accessable variables and displays, and a button to access their page.  	
| nInterests  | int | A size integer for the interest array.
| interestName | String | Stores the name of an interest. 
| activityLength | int  | Stores the length of activity - seconds. 
| numNotifications | int | Stores the number of notifications app will have 
| numNotificationSpan | String | Stores the span that you want notifications through 
| streakCounter | int | Stores the streak of an interest 
| periodFreq | String | Stores the frequency of the activity 
| streakCounter | int | Number of days in a row you have completed your activity. 



## Functionality

| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| addInterest()  | String interestname, int activityLength, String basePeriodSpan, int activityLength, int numNotifications  | None  | Initializes an interest from add interest form.
| setInterestName() | String interestName | none | Sets interest name in User DB. Edit Interest form
| setActivityLength() | String activityLength | none | Sets the length of an activity.  
| setPeriodFreq() |  activityPeriod  | none | Sets the frequency of an interest. Daily/weekly/monthly. 
| setNumNotifications() | int numNotifications | none | Sets the number of notifications that you want.
| setNotificationSpan() | String notificationSpan | none | Sets the notification span. Daily/weekly/monthly. 
| setStreakCounter() | int streakCounter | none | Sets the streak of an interest 
| getInterestName() | none | interestName| Retrieves the interestName
| getActivityLength() | none | activityLength| Retrieves the activity length. 
| getPeriodFreq() | none | periodFreq | Retrieves the period frequency. 
| getNumNotifications() | none | numNotifications | Retrieves the number of notifications 
| getNotificationSpan() | none | notificationSpan | Retrieves the span of notifications 
| getStreakCounter() | none | streakCounter | Returns the streak of a specific interest 
| searchInterest() | String interestName | int arrayPos | Given an interest name, this function searches through the userInterest array and finds the array position of that interest.	
| deleteInterest()  | int arrayPos  | None  | Deletes an interest by initializing the constructor array position t
to null, the pulling all subsequent interests in the array down to fill in, and subtracts one from nInterests if the interest is found.
|onCreate() | | SQLLiteDatabase db| none | Creates the database tables
| onUpgrade() | SQLLiteDatabase db, int i, int i1| | none | Calls onCreate method to update database 
| updateInterest() | Interest interest | db.update() | Updates an interest 
| getInterestCount() | Interest interest | cursor.getCount() | Gets number of user interest 

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
			
## WebGLTimer

| Name  | Type | Description
| ------------- | ------------- | ------------- 
| activityLength | int | Gets length of activity for analog timer 

## Functionality 
| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| Render() | none | none | Draws the clock animation to the screen 

## Connections 

## Connections
| Input or Output | Function | Description 
| -------------    | ---------- | ---------- 
| Output | user.getActivityLength | Gets the activity length of the clicked activity for analog timer 
## Properties 

# Interest_Fragment

## Functionality 
| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| onCreateView() |  @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState | view | Creates interest view, populates with interest data. 

## Properties 

| Name  | Type | Description
| ------------- | ------------- | ------------- 
| interestName | TextView | Displays the name of the interest in the edit Interest container and on top of the Timer.
| periodFreq   | TextView | Text view object to set periodFreq in edit interest container
| basePeriodSpan| TextView | Text view object to set base period span in edit interest container
| activityLength | TextView | Text view object to set activityLength in edit interest container
| numNotifications | TextView | Text view object to set number of notifications in edit interest container
| numNotificationsSpan |  TextView | Text view object to set num notifications span in edit interest container
| timer                | GLSurfaceView   | GLSurfaceView that holds context of openGL timer class

## Connections
| Input or Output | Function | Description 
| -------------    | ---------- | ---------- 
| output   |  User.getInterestName() | Populates interest name text box and interest title text view.
| output   |  User.getPeriodFreq()  | Populates period freq text box with current data 
| output   |  User.getBasePeriodSpan() | Populates period span container 
| output   |  User.getActivityLength() | Populates activity length text box and activity length text view
| output   |  User.getNumNotifications() | Populates number of notification text box. 
| output   |  User.getNumNotificationSpan() | Populates num notification span text box. 

# Add_Interest_Fragment 

## Functionality

| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| onCreateView() | @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState| View |

## Connections
| Input or Output | Function | Description 
| -------------    | ---------- | ---------- 
| input   |  User.setInterestName() | Creates new interest name
| input   |  User.setPeriodFreq()  | Creates new period frequency
| input   |  User.setActivityLength() | Creates new activity length 
| input   |  User.setNumNotifications() | creates new number of notifications
| input   |  User.setNumNotificationSpan() | Creates new number of notification span 


# Home_Page Fragment

## Properties 
| Name  | Type | Description
| ------------- | ------------- | ------------- 
| interestName1 - interestName10 | textView | textView objects to store interest names in interest container, max 10 interest.

## Functionality

| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| onCreateView() | @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState| View |

## Connections 
| Input or Output | Function | Description 
| -------------    | ---------- | ---------- 
| Output  | User.getInterestName() | Populates interest container with interest name. 

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

