#Setup IntelliJ

-   For the most part, gradle will set everything up for you. However IntelliJ's testing tools will not pick up the library path property in the build.gradle file. You'll have to set this manually.

1)  Click the dropdown next to the run button and select Edit Configurations...
2)  Expand the Defaults section and select JUnit
3)  in the VM Arguments field enter ``-Djava.library.path=<full path to>/Demo/lib/linux`` Note, this needs to be the full path from the root directory to the directory containing the libopencv_java2413.so file.

If you also want to run the app from intelliJ, you'll have to do the same for the Application default.

