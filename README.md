# SleepSchedule

SleepSchedule (name is questioned) is a simple Android app written in Kotlin that allows users to donate money to charity if they miss their sleeping schedule. 

The app tracks the user's sleep patterns and automatically transfers a portion of their account balance to a selected charity if they fail to meet their sleep schedule.

It is a great way to manage your sleeping schedule, because otherwise you will lose money.



## Features

- Track sleep patterns using a Xiaomi Mi Band 7.

- Automatically transfer a portion of the user's account balance to a selected charity if they miss their sleep schedule.

- Customize the amount of money to donate.

- View a history of donations and sleep patterns.



## Usage

- The app isn't finished, so in this paragraph i just describe my steps to make this app work. It may be different in your case, if you are using other devices.



1) git clone current repository

2) install gadgetbridge on your smartphone, and connect it to your mi band. You will probably need an auth key to pair your device to GadgetBridge, so google how to obtain it.

3) in Gadgetbridge change export of db files to Downloads folder of your phone. This app is copying db with every launch. You can set up autoexport if you want to, otherwise app will work with last exported data.

4) go to settings and grant permission to manage all files located on your device. Otherwise, the app will crash without any warning and will not ask for any permissions.



## Contributing

  If you would like to contribute to the development of this app, please feel free to fork the repository and submit a pull request with your proposed changes.
