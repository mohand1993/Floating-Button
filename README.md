#Simple Floating Button For Android
you can use this button as repacement For the old options menu


[Sample App Apk](https://github.com/mohand1993/Floating-Button/blob/master/files/app-release.apk?raw=true)
###ScreenShots

![screenShot][ss3]
![screenShot][ss4]
![screenShot][ss5]

###Usage 
```XML
  <fbutton.muf.com.floating_button_library.FloatingButton
        android:id="@+id/floatingButton"
        floatingButton:main_button_image ="@drawable/yellow_circle"
        floatingButton:dismis_button_image ="@drawable/red_circle"
        floatingButton:vertical_distance ="20dp"
        floatingButton:horizanal_distance ="20dp"
        floatingButton:direction="right"
        floatingButton:button_height="50dp"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

###Adding Options Buttons
```JAVA
        floatingButton.addButton(R.drawable.red_circle,new MakeTostClickListener("I am Red"));
        floatingButton.addButton(R.drawable.yellow_circle,new MakeTostClickListener("I am Yellow"));
        floatingButton.addButton(R.drawable.green_circle,new MakeTostClickListener("I am Green"));
        floatingButton.addButton(R.drawable.blue_circle,new MakeTostClickListener("I am blue"));
```
###Attaching to ListView
```JAVA
        list = (ListView) findViewById(R.id.list);
        floatingButton.animateWithScroll(list);
```

[ss3]: https://raw.githubusercontent.com/mohand1993/Floating-Button/master/files/anim%20(3).gif
[ss4]: https://raw.githubusercontent.com/mohand1993/Floating-Button/master/files/anim%20(2).gif
[ss5]: https://raw.githubusercontent.com/mohand1993/Floating-Button/master/files/anim%20(1).gif
