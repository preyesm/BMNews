# BMNews

Assumptions made:
  Default country - in
  Default category - Entertainment

Highlights:
1. Have followed the MVP architecture, dividing the application into 4 layers(UI, Presenter, Repository and Network) - following SOLID principles.
2. Every outer layer knows about the inner layer and inner layer doesn't know about the calling layer( in line with clean code architecture).
3. Application has implemented the material theme (androidX)

Libraries used:
1. Caching - Realm
2. Networking - Retrofit
3. Communication - RxJava
4. DI - Dagger 2

Externai gradle plugins:
1. implementation 'org.apache.commons:commons-lang3:3.4' - to escape and unescape strings while displaying
2. implementation 'eu.davidea:flexible-adapter:5.0.5'- adapter to display list, the adapter has lot more features
3. api 'com.github.bumptech.glide:glide:4.2.0' - to load images
4. implementation 'com.amulyakhare:com.amulyakhare.textdrawable:1.0.1' - to display the source name in image view
5. implementation 'com.facebook.shimmer:shimmer:0.4.0' - Shimmer effect for first time load.
  
Cache is implemented for country and catergory. Because the source is string spliited by comma, i Have not cached the feed list by source.

Items to be implemented:
1. Could not do image caching and instead have used Glide.
2. Have done the manual testing and due to time constraint have not implemented the testing frameworks.
