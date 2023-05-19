# Rabbi Ovadiah Yosef Calendar (Android) App

<b>Google Play Store and source code:</b>

<a href="https://play.google.com/store/apps/details?id=com.EJ.ROvadiahYosefCalendar&amp;pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1"><img class="android" alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" width="250px"></a> <a href="https://github.com/Elyahu41/RabbiOvadiahYosefCalendarApp"><img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png" width="62px"></a>

<b>App Store and source code:</b>

<img src="http://1radionews.com/wp-content/uploads/app_store_coming_soon.png" alt="Download on the App Store" width="240px"><a href="https://github.com/Elyahu41/RabbiOvadiahYosefCalendarIOSApp"><img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png" width="62px"></a>

<b>Website and source code:</b>

<a href="https://elyahu41.github.io/RabbiOvadiahYosefCalendar/"><img src="https://www.kindpng.com/picc/m/36-363991_www-icon-png-transparent-background-website-icon-png.png" width="124px"></a> <a href="https://github.com/Elyahu41/Elyahu41.github.io/tree/master/RabbiOvadiahYosefCalendar"><img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png" width="62px"></a>

The goal of this app is to recreate the "Luach HaMaor Ohr HaChaim" calendar that is widespread in Israel. This calendar is special because Rabbi Ovadiah Yosef ZT"L oversaw it's creation and used the calendar himself until he passed:

<img src="https://i.imgur.com/QqGAtTB.jpg" height="750">

In order to recreate the calendar, I needed an API that would give me the times for sunrise and sunset everyday (since all the other zmanim are based on these times). I was recommended the well known [KosherJava](https://github.com/KosherJava/zmanim) Package, and that is the basis for all of the app's zmanim (time) calculations.

The app can display the zmanim/times in hebrew and english but is primarily made for english speakers.

The only zman/time that could not be computed by the KosherJava API is the sunrise time that the Ohr HaChayim calendar uses. They explain in the calendar introduction that they take the sunrise times from a calendar called, "Luach Bechoray Yosef". That calendar calculates the time for sunrise by taking into account the geography of the land around that area and finding when is the first time the sun is seen in that area (based on the introduction to Chaitables.com). While not impossible, this would take a massive toll on a mobile phone's processor and memory, therefore, the app does not support it. However, I discovered that the creator of this calendar made a website [ChaiTables.com](http://chaitables.com) to help people use his algorithm for sunrise/sunset all over the world and create a 12 month table based on your input. I added the ability to download these times in the app with your own specific parameters. (I highly recommend that you see the introduction on chaitables.com.)


First view implemented was the daily view of the app:

![alt text](https://play-lh.googleusercontent.com/46VfUTuZLlA_ogFYMP0oLUbtgQtsj-D3lHNDnS5LvqVwwgXr4Qh0p8d0ZiJg-z69IEY=w2560-h1440-rw)

Since version 6.0, the weekly view of the original calendar has been impemented:

![alt text](https://play-lh.googleusercontent.com/NbOtQFdOia2iwFb1GQJk68j_WvLwhnMOzRnE-sbLzfuqZTrybFcuM1cCypHgL8odg4N8=w2560-h1440-rw)

# Explanation of how the zmanim are calculated:
Dawn - Alot HaShachar:

This time is calculated as 72 zmaniyot/seasonal minutes (according to the GR"A) before sunrise. Both sunrise and sunset have elevation included.

Misheyakir - Earliest Talit/Tefilin:

This time is calculated as 6 zmaniyot/seasonal minutes (according to the GR\"A) after Alot HaShachar (Dawn).

Sunrise - HaNetz:

The Ohr HaChaim calendar uses a sunrise table called, "Luach B'Choray Yosef". I explained above how the Luach B'Choray Yosef calculates the time for sunrise, however, if the user does not download the times from the website, the app defaults to Mishor/Sea Level Sunrise provided by the KosherJava API.

Eating Chametz - Achilat Chametz:

This is calculated as 4 zmaniyot/seasonal hours, according to the Magen Avraham, after Alot HaShachar (Dawn) with elevation included. We are stringent to use the MG"A zmanim because it is a Torah commandment.

Burning Chametz - Biur Chametz:

This is calculated as 5 zmaniyot/seasonal hours, according to the MG"A, after Alot HaShachar (Dawn) with elevation included. We are stringent to use the MG"A zmanim because it is a Torah commandment.

Latest time for Shma (MG"A):

The Magen Avraham/Terumat HeDeshen calculates this time as 3 zmaniyot/seasonal hours after Alot HaShachar (Dawn). The Ohr HaChaim calendar calculated these  zmaniyot/seasonal hours by taking the time between Alot HaShachar (Dawn) and Tzeit Hachocavim (Nightfall) of Rabbeinu Tam and dividing it into 12 equal parts.

Latest time for Shma (GR"A):

The GR"A calculates this time as 3 zmaniyot/seasonal hours after sunrise (elevation included). The GR"A calculates a zmaniyot/seasonal hour by taking the time between sunrise and sunset (elevation included) and divides it into 12 equal parts.

Latest Brachot Shma:

The GR"A calculates this time as 4 zmaniyot/seasonal hours after sunrise (elevation included). The GR"A calculates a zmaniyot/seasonal hour by taking the time between sunrise and sunset (elevation included) and divides it into 12 equal parts.

Mid-Day - Chatzot:

This time is calculated as 6 zmaniyot/seasonal hours after sunrise. The GR"A calculates a zmaniyot/seasonal hour by taking the time between sunrise and sunset (elevation included) and divides it into 12 equal parts.

Earliest Mincha - Mincha Gedolah:

The Ohr HaChaim calendar calculated this time as 30 regular minutes after Chatzot (Mid-Day). However, if the zmaniyot/seasonal minutes are longer, we use those minutes instead to be stringent. The GR"A calculates a zmaniyot/seasonal hour by taking the time between sunrise and sunset (elevation included) and divides it into 12 equal parts. Then we divide one of those 12 parts into 60 to get a zmaniyot/seasonal minute."

Mincha Ketana:

This time is calculated as 9 and a half zmaniyot/seasonal hours after sunrise. The GR"A calculates a zmaniyot/seasonal hour by taking the time between sunrise and sunset (elevation included) and divides it into 12 equal parts. Then we divide one of those 12 parts into 60 to get a zmaniyot/seasonal minute.

Plag HaMincha:

This time is usually calculated as 10 and 3/4th zmaniyot/seasonal hours after sunrise, however, yalkut yosef writes to calculate it as 1 hour and 15 zmaniyot/seasonal minutes before tzeit hacochavim. The Halacha Berurah writes to calculate the zman like the former way. In the app, both opinions can be used. The GR"A calculates a zmaniyot/seasonal hour by taking the time between sunrise and sunset (elevation included) and divides it into 12 equal parts. Then we divide one of those 12 parts into 60 to get a zmaniyot/seasonal minute.

Candle Lighting:

The Ohr HaChaim calendar put this time as 20 regular minutes before sunset (elevation included). The calendar also showed 40 minutes for those that have that custom.  You can choose whichever opinion you want in the settings of the app.

Sunset - Shkia:

Halachic sunset is defined as the moment when the top edge of the sun disappears on the horizon while setting (elevation included).

Nightfall - Tzeit Hacochavim:

This time is calculated as 13 and a half zmaniyot/seasonal minutes after sunset (elevation included). The GR"A calculates a zmaniyot/seasonal hour by taking the time between sunrise and sunset (elevation included) and divides it into 12 equal parts. Then we divide one of those 12 parts into 60 to get a zmaniyot/seasonal minute. Disclaimer: This time is very early in the winter and especially in the far north or south. This zman should NOT be used to decide when shabbat ends or any other serious matters without consolidating a rabbi first!

Fast Ends - Tzeit Taanit:

This time is displayed twice, the first time is calculated as 20 regular minutes after sunset (elevation included) and the second time is calculated as 30 minutes after sunset. Rabbi Ovadiah Yosef ZT"L wrote in Chazon Ovadiah to keep 20 minutes after sunset for a fast, however, Rabbi David Yosef Shlita brings down that his father would always say that the fast ends at tzait hacochavim (13.5 zmaniyot minutes) even when his father was traveling in NY, he would say the same thing. The app displays both zmanim. Chacham Ben Zion Abba Shaul wrote to wait 30 minutes for a fast to end and since many communities hold like him, his zman is displayed in the app as well but with the distinction that it is L'Chumra (Stringent).

Shabbat/Chag Ends - Tzeit Shabbat/Chag:

The Ohr HaChaim calendar uses a fixed 30 minutes after sunset for motzei shabbat and chag. There are many customs around the world on when shabbat ends, by default, it is set to 40 regular minutes after sunset (elevation included) in the app, however, you can change the time in the settings. The reason why I left it at 40 regular minutes is because I have heard that 40 minutes in the least amount of time one should wait for shabbat to end even at the very north of the USA, and people who live in Israel can always change it to 30 minutes. However, leaving it at 30 minutes could confuse some people that it is ok to wait just 30 minutes in very northern locations like Canada and France, which is not the intention. 

Rabbeinu Tam:

This time is calculated as 72 zmaniyot/seasonal minutes after sunset (elevation included). The GR"A calculates a zmaniyot/seasonal hour by taking the time between sunrise and sunset (elevation included) and divides it into 12 equal parts. Then we divide one of those 12 parts into 60 to get a zmaniyot/seasonal minute in order to calculate 72 minutes. Another way of calculating this time is by calculating how many minutes are between sunrise and sunset. Take that number and divide it by 10, and then add the result to sunset.

Midnight - Chatzot Layla:

This time is calculated as 6 zmaniyot/seasonal hours after sunset. The GR"A calculates a zmaniyot/seasonal hour by taking the time between sunrise and sunset (elevation included) and divides it into 12 equal parts. In this case, we use sunrise for the next day.




# Introduction to the calendar in Israel:

<img src="http://www.zmanim-diffusion.com/images/1.jpg" height="650">
<img src="https://i.imgur.com/udfwy3R.jpg" height="650">
<img src="https://i.imgur.com/ureV4p4.jpg" height="650">
<img src="https://i.imgur.com/HXEzXvr.jpg" height="650">
