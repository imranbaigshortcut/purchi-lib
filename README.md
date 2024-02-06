# How to add in your project


Add it in your root build.gradle at the end of repositories:

	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.imranbaigshortcut:purchi-lib:1.0.1'
	}

```
private fun openVoterList(idCardNumber: String) {

       // Provide your base url and secret key here
       
       PurchiLib.baseUrl = "https://bb0cbe11c6e4c611.azadvoter.com/api/" 
       PurchiLib.secret = "secret key here"


       val intent = Intent(this, PurchiListActivity::class.java)
       intent.putExtra("cnic", idCardNumber)
       startActivity(intent)
},,,


  
  
  # License
  ```java
  MIT License