##Properties

| Name  | Type | Description
| ------------- | ------------- | ------------- 
| interestName  | string  | Name of interest, stored from userInput  
| numOfTimes  | number  | Number of times the user will conduct the activity in a day's period.
| interestTime  | number  | The lenrgth of time the user plans to do an activity (numOfTimes * interestTime <= 24)
| checkSun | boolean  | True if user selects Sunday. Otherwise, default state is false
| checkMon | boolean  | True if user selects Sunday. Otherwise, default state is false
| checkTues | boolean  | True if user selects Sunday. Otherwise, default state is false
| checkWedns | boolean  | True if user selects Sunday. Otherwise, default state is false
| checkThurs | boolean  | True if user selects Sunday. Otherwise, default state is false
| checkFri | boolean  | True if user selects Friday. Otherwise, default state is false
| checkSat | boolean  | True if user selects Saturday. Otherwise, default state is false

##Functionality

| Name  | Parameters | Return | Description
| ----------  | ---------- | --------- | -----------
| displayAll()  | None  | None  | Displays all of user's input
| timeSpent()  | string, interestName  | None  | Shows amount of time spent on an activity in minutes.
| clear()  | None  | None  | Clears data and returns interface to default state.



