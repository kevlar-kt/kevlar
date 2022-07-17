# Database
Kevlar comes pre-packaged with definitions for the most common pirate software. 
A simplified summary is reported in the following table:


- `Software` is the common name of the given pirate software;
- `Internal Code` is what kevlar uses to reference and report a specific software;
- `Type` defines whether the given software is a pirate store or pirate app;
- `Most Common Package` gives a probable package name for out in the wild pirate software. Usually pirate software have multiple (often numerous) package names, which are not reported in the table (but are obviously implemented in the library);
- `Range` defines which battery of tests is available against a given package. `H` is heuristic detection (package metadata string matching), `A` is alphabetical analysis and `C` are collateral tests.


| Software                	| Internal Code            	| Type    	| Most Common Package                 	| Range        	|
|-------------------------	|-------------------------	|---------	|-------------------------------------	|--------------	|
| Action Launcher Patcher 	| ACTION_LAUNCHER_PATCHER 	| `App`   	| zone.jasi2169.uretpatcher           	| `H`          	|
| AC Market               	| AC_MARKET               	| `Store` 	| ac.market.store                     	| `H`          	|
| AGK App Killer          	| AGK                     	| `App`   	| com.aag.killer                      	| `H`          	|
| All In One Downloader   	| AIOD                    	| `Store` 	| com.allinone.free                   	| `H`          	|
| App Cake                	| APP_CAKE                	| `Store` 	| com.appcake                         	| `H`          	|
| App Sara                	| APP_SARA                	| `App`   	| com.appsara.app                     	| `H`          	|
| Aptoide                 	| APTOIDE                 	| `Store` 	| cm.aptoide.pt                       	| `H`          	|
| Black Mart              	| BLACK_MART              	| `Store` 	| org.blackmart.market                	| `H`          	|
| Content Guard Disabler  	| CGD                     	| `App`   	| com.oneminusone.disablecontentguard 	| `H`          	|
| Creeplays Patcher       	| CREEPLAYS_PATCHER       	| `App`   	| org.creeplays.hack                  	| `H`          	|
| Cree Hack               	| CREE_HACK               	| `App`   	| apps.zhasik007.hack                 	| `H`          	|
| Freedom                 	| FREEDOM                 	| `App`   	| jase.freedom                        	| `H`          	|
| Game Hacker             	| GAME_HACKER             	| `App`   	| org.sbtools.gamehack                	| `H`          	|
| Game Killer Cheats      	| GAME_KILLER             	| `App`   	| com.killerapp.gamekiller            	| `H`          	|
| Get Apk                 	| GET_APK                 	| `Store` 	| com.repodroid.app                   	| `H`          	|
| Get Jar                 	| GET_JAR                 	| `Store` 	| com.getjar.reward                   	| `H`          	|
| Happymod                	| HAPPYMOD                	| `Store` 	| happygames.io                       	| `H`          	|
| Leo Playcards           	| LEO_PLAYCARDS           	| `App`   	| com.leo.playcard                    	| `H`          	|
| Lucky Patcher           	| LUCKY_PATCHER           	| `App`   	| good luck                           	| `H`, `A`,`C` 	|
| Mobilism                	| MOBILISM                	| `Store` 	| org.mobilism.android                	| `H`          	|
| Mobogenie               	| MOB_GENIE               	| `Store` 	| com.mobogenie                       	| `H`          	|
| 1Mobile                 	| ONE_MOBILE              	| `Store` 	| me.onemobile.android                	| `H`          	|
| Slide Me                	| SLIDE_ME                	| `Store` 	| com.slideme.sam.manager             	| `H`          	|
| Uret Patcher            	| URET_PATCHER            	| `App`   	| zone.jasi2169.uretpatcher           	| `H`          	|
| XModGames               	| XMG                     	| `App`   	| com.xmodgame                        	| `H`          	|
| Z Market                	| Z_MARKET                	| `Store` 	| com.zmapp                           	| `H`          	|
