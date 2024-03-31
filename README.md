README
To begin running the project on vocareum, compile the main method by typing "javac MainApplication.java" in the terminal, then run it by typing "java MainApplication" in the terminal.
The user is first prompted with the login menu, where they can choose to login to an an existing account, create a new account, or type exit to end the program.
For easy use, the user can simply enter 1 or 2 to login or create a new account, respectively.
NOTE: When being prompted to enter an input that is not a menu option (Ex. entering a username), the user can type "back" (not case sensitive) to return to the previous menu.
1. Login: When chosing the login option, the user will need to enter their username first (not case sensitive). If the username is found as a text file, the user will then be prompted to enter their password. If the password matches the one written in the user-specific text file, the main menu will show.
2. Create Account: When chosing the create account option, the user will be asked to choose a username. If the username chosen matches a username already created in the program, the user will be asked to enter a different username, as it is already taken. Once the user enters a valid username, they will need to create a password. Both the username and password will be saved to a new text named "username".txt, and the main menu will show.
Main Menu
After being prompted the main menu, the user will have the option to search for a user, view their own account settings, or logout by entering 1, 2, or 3, respectively.
1. Search for a user: If the user chooses to search a user, they will be asked to provide an input. The program will then show every user containing their input in a numbered list. The user will input the number corresponding with the user of their choice.
     Once the user selects another user, they will be presented with a user menu, where they have the option to add or remove a user as a friend, block or unblock a user, direct message a user, or exit.
       1. Add/Remove User as friend: Depending on a user's privacy settings, they may be able to only send messages and recieve messages from friends. If this is the case, they will need to add a friend before being able to message them.
       2. Block/Unblock User: By blocking another user, both accounts will be removed as each other's friends, and not be able to message each other or add each other as friends. While both users can search the other up, they will not be able to see account details or message each other. When unblocking a user, account settings will be displayed again, but they will not be added as friends again automatically.
       3. Direct message: Chosing this option will display all previous messages along with the choice to send a message, delete a message, or exit by entering 1, 2, or 3, respectively.
         1. Send Message: The user will be asked to enter a message which will then be updated in the conversations and the Direct Message menu will then be redisplayed.
         2. Delete Message: If there are no previous messages the user will be presented with the text: "There are no messages to delete." If there are previous messages the user will be displayed with a list of messages numbered and can delete a message by entering the corresponding number.
         3. Exit: If the user chooses to exit the direct message menu, they will be prompted with the user menu.
       4. Exit: If the user choose to exit the direct user menu, they will be prompted with the main menu.
2. Account Settings: If the user chooses to view their account settings, they will be prompted with the account settings menu where they can change their account password, change their direct messaging privacy, update their profile picture, or return to the main menu by chosing 1, 2, 3, or 4 respectively.
       1. Change account password: If the user chooses to change their account password, they will first need to enter their old password. If their old password is incorrect, they will  be asked to try again. Once the user correctly inputs the old password, they will be asked to enter a new password of their choice which will then be updated.
       2. Change direct messaging privacy: If the user chooses to change their direct messaging privacy, they will be prompted with the direct messaging privacy menu where they can chooses to allow messages from everyone, allow messages from just friends, or cancel.
         1. Open to everyone: By chosing to open messages to everyone, everyone will be able to message them even if they are not added as a friend. However, blocked users will still be unable to message them.
         2. Open to just your friends: By chosing to open messages to just friends, only people whom the user has added as a friend will be able to message them.
         3. Cancel: By chosing to cancel, the account settings menu will be displayed
       3. Update profile picture: If the user chooses to update their profile picture they will be asked to upload a picture in the proper format.
       4. Return to Main Menu: If the user chooses to return to the main menu, the main menu options will be displayed.
3. Logout: If the user chooses to logout they will be prompted with the login menu and their information and messages will be saved for the next time they login
Once presented with the login menu, if the user chooses to end the program, they should type "exit" and terminate the program.

Submissions
Submitted on bright space by: name
   
Class descriptions
1. MainApplication.java contains the main method and calls all the methods from the other classes to assist in running the program. It displays all the menus and takes in the users input. It also catches any errors to assure a smooth program. This classes also consists of many methods, however, they call methods from other classes inside of them. This class displays each menu in a loop until the user chooses to exit. MainApplication.java is crucial to running a smooth and convenient program for the user.
2. LoginMethods.java contains all the methods for a successful login. It checks if a username is taken and creates new files for new accounts. It also ensures the password being used to login matches the password the program has.
3. User.java handles all methods related to a user's account settings. It reads the user's text file and provides information on user settings and privacy. The methods in this class include writing and updating information in a user's text file such as password and profile picture changes.
4. DirectMessageMethods.java contains all methods related to sending, recieving and deleting messages. This method reads from a text file that holds all contents of a conversation between two users and updated the conversations everytime the user wants to send or delete a message.
5. SearchMethods.java contains a singular method that is used to search for a user.
