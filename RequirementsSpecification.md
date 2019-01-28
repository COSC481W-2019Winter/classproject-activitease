# Mandatory requirements:

## General
- Users can use our app with no prior experience to initialize an interest to fit their needs in three minutes or less.
- All non-home pages have a home button to quickly return to the home page.
## The Home page
![picprototype](https://user-images.githubusercontent.com/45779417/51870531-dff85e80-2321-11e9-9b0b-fa861f917066.png)
 
- The home page is the starting page when the app is opened.
- The home page shows all interests, their streak counts, how much time is needed for each interest, and how many hours/minutes/seconds the user has to complete them.
- Users can swipe up and down to view all of their interests.
- Users can click on an interest from the home page to go to that interest’s page.
- At the bottom of the home page is an add interest button. This takes the user to an add interest page.
- In the top left corner of the home page is the menu button. When pressed this takes the user to the menu page.
- The menu button, as well as the add interest button, are always viewable if the user is scrolling through interests.
- The home page also has a side menu button.
- The side menu can be toggled on and off by pressing the side menu button.
## The Add Interest Page
- The add interest page will require several critical elements to form an interest, such as:
   - The name of the interest they will be participating in
   - The number of times (>=1), and how often (days, weeks, months, years) an activity is done
   - How long they will be doing an activity
      - The length of an activity multiplied by the number of times it's done cannot exceed the span of the practice period.
        For example, a user cannot have six sessions of five hour practices in one day (30 > 24). 
   - The number of notifications the user wants for an activity
## The Interest Page
- The interest page will have a timer, an interest tracker, and notification and timer settings on one page.
- There will be a pause button directly below the timer that the user can press to pause their activity. Once pressed, the button will display resume, and will start the timer once pressed again.
- Users can swipe up and down on an interest page to view the timer, tracker, or settings. Timer and tracker will be displayed on the inital page view with settings requiring you to swipe down.
- Users can swipe left and right to see other interests.
## Tracking Interest Activity
- Logging timed activities is incentivized by a streak-based system. Each interest’s streak will be displayed on the homescreen, and on an interest page.
- Users can see the total amount of time (in days, hours, and minutes) spent on an interest.
- After finishing an interest, users can comment on an activity period to record things such as milestones or interesting experiences.
- These comments have the total time logged as well as the day matched with them.
- On an interest page, the user can see the previous days in the week, and their corresponding activity completion.
- Users can monitor their activity completion time on a bar graph, over the span of days, months, and years.
## Interest Time settings
- Users can enter how many times per day, week, month, or even year, a user wants to do an action.
- Users must easily be able to set and change the amount of time they commit to an interest.
- Each interest has a corresponding timer, which shows the time needed for that interest period.
- Timers can be paused and started.
- Near the timer is a “Done” button, which can be pressed preemptively to maintain their streak, but will still record a lack in time.
## Interest Notification Settings
- Users have the option to have push notifications from the application go off for each individual interest.
- Each interest can have its notifications easily disabled or enabled with a toggle button.
- Users can enter in the number of notifications they want for an interest.
- The user can customize the time they want the notification to go off, or have the app default to times, given the number of notifications they want.
- Default notification times are calculated to be sensible, in that:
   - They will not go off too early (before 9 am)
   - They will not go off too late (after 10:30 pm)
   - They will be an event amount of time between one notification to the next
- Users can customize the message of the push notification(s), if and only if that notification has a custom set time.
- Notifications can be quickly added, deleted, or changed easily in each interest’s settings.
## Timer
- At 12:00 AM at the end of each period (day, week, month, or year), the timer is reset, and has the time required for that day is shown.
- The timer has a circular clock display, which shows the time completed and the time remaining for an activity.
   - The total time to complete a cycle on the clock is the time required for that period’s activity.
   - The total time is divided into segments, per the number of times a period an activity is to be done.
      - For example, if there is an activity that is done 3 times a day for five minutes, there are three segments of five minutes, totaling for fifteen minutes.
- The timer can be easily started and paused by pressing a button below the analog clock.
- If a user has to log an activity more than once per period, it will divide the analog clock display n times.
- The number of times per period, as well as the length of these times, can be easily customized in each interest’s settings.*
## Menu
- Once the menu button is pressed it will open up the menu, which will contain a list of opions to press.
- The first button will display "Help/FAQ" take the user to the Help/FAQ page.
- The 2nd button will display "About this app" and once pressed will take the user to a page with a description of our app, our problem statement, and a list of the developers of ActivitEase.
## Help / FAQ
- The FAQ page will have at least 5 questions and answers on how to add in interest, how to use the timer, and how to customize notifications to fit each individual need.
- The FAQ page will also have at least 3 questions we often hear pertaining to how and why to use the app.
- The help page will have contact information for a support email page, which we will respond to and check frequently.
## About this app
- The about this app page will have our problem statement and solution displayed at the top. The first paragraph will read "A problem we've identified is that people don't form habits that reward them in ways they want to. People tend to not realize sometimes how little effort they're putting in to certain activities they might find of interest and want to become better at."
- Below our problem statement our solution will be displayed "Our solution is create some kind of app that allows the user to keep track of how much time the're putting into specified hobby/ies. It also helps to encourage/alert the user that they planned to do this certain thing at this certain point in time. Our goal is to encourage people to follow their interests to their end. We want to encourage people to create goals for themselves and give them a way to accomplish those goals on a day by day basis"
- Below our solution will be a list of the developers with a brief description of each of us and our roles in creating this app.
# Desirable Requirements
## Notifications
- Allow the user multiple options for notification customizability, like custom notification times and custom notification messages.
- Allow the user to choose default notification times based on the number of notifications they’d like to receive within a 24 hour span.
## Timer
- Timer has the option to automatically proceed after an activity, so users can practice longer than they intended, if they are making critical progress.
## Interest Fundamentals
- Allow the user to change the time they wish to spend on their interest
- Allow the user to choose either a timer or stopwatch for each interest
- Allow the user to delete an interest they’ve either completed or no longer wish to continue.
- Allow the user to modify their settings for each individual interest.
## Friends List
- Allow the user to add friends to a friends list
- Allow the user to compare their Interest and interest time spent with their friends
## Minutes Currency System
- Incorporate a reward system to incentivize users to pursue their interest goals.
- The time spent on interest will earn user’s time currency, which can be used to purchase rewards from the Interest Rewards Store
- If the user selects the “Done” button, the user will complete their interest for the day. However, the time left of the interest for the period of time will then be deducted from their current time currency.
