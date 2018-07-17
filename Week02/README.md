# Project 2 - NewYorkTimesSearch

NewYorkTimesSearch is a news article searching application powered by the New York Times Search API.

Time spent: 40 hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can enter a search query that will display a grid of news articles using the thumbnail and headline from the New York Times Search API.
* [x] User can click on "filter" icon which allows selection of advanced search options to filter articles.
	- An example of a query with filters (begin_date, sort, and news_desk) applied [can be found here](https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20160112&sort=oldest&fq=news_desk:(%22Education%22%20%22Health%22)&api-key=227c750bb7714fc39ef1559ef1bd8329). Full details of the API can be found [on this article search README](https://developer.nytimes.com/article_search_v2.json#/README).
* [x] User can configure advanced search filters such as:
	* [x] Begin Date (using a [date picker](https://guides.codepath.com/android/Using-DialogFragment#displaying-date-or-time-picker-dialogs))
	* [x] Sort order (oldest or newest) using a [spinner dropdown](https://guides.codepath.com/android/Working-with-Input-Views#spinners)
	* [x] News desk values (Arts, Fashion & Style, Sports) using [checkboxes](https://guides.codepath.com/android/Working-with-Input-Views#checkboxes)
* [x] Subsequent searches will have any filters applied to the search results.
* [x] User can tap on any article in results to view the contents in an embedded browser.
* [x] User can [scroll down "infinitely"](https://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView) to continue loading more news articles. The maximum number of articles is limited by the API search.

The following **bonus** features are implemented:

* [x] Robust error handling, [check if internet is available](https://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures. 
* [x] Use the [ActionBar SearchView](https://guides.codepath.com/android/Extended-ActionBar-Guide#adding-searchview-to-actionbar) or custom layout as the query box instead of an EditText.
* [ ] User can [share a link](https://guides.codepath.com/android/Sharing-Content-with-Intents#attach-share-for-a-webview-url) to their friends or email it to themselves.
* [ ] Replace Filter Settings Activity with a lightweight [modal overlay](https://guides.codepath.com/android/Using-DialogFragment).
* [x] Improve the user interface and experiment with image assets and/or styling and coloring 
* [ ] Use the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) with the StaggeredGridLayoutManager to display improve the grid of image results (see [Picasso guide](https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#adjusting-the-image-size-dynamically) too).
* [x] For different news articles that only have text or have text with thumbnails, use [Heterogenous Layouts](https://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) with RecyclerView.
* [ ] Apply the popular [ButterKnife annotation library](https://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate.
* [ ] Use Parcelable instead of Serializable using the popular [Parceler library](https://guides.codepath.com/android/Using-Parceler).
* [x] Replace all icon drawables and other static image assets with [vector drawables](https://guides.codepath.com/android/Drawables#vector-drawables) where appropriate.
* [ ] Leverage the [data binding support module](https://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into one or more activity layout templates.
* [x] Replace Picasso with [Glide](https://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
* [ ] Switch to [using retrolambda expressions](https://guides.codepath.com/android/Lambda-Expressions) to cleanup event handling blocks.
* [x] Leverage the popular [GSON library](https://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data.
* [ ] Consume the New York Times API using the popular [Retrofit networking library](https://guides.codepath.com/android/Consuming-APIs-with-Retrofit) instead of Android Async HTTP.
* [ ] Replace the embedded WebView with [Chrome Custom Tabs](https://guides.codepath.com/android/Chrome-Custom-Tabs) using a custom action button for sharing.

The following **additional** features are implemented:

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://imgur.com/CY5f9uc.gif' title='Video Walkthrough' width='' alt='Video Walkthrough'/>
<img src='https://imgur.com/30h2FUj.gif' title='Filter' width='' alt='Filter'/>
<img src='https://imgur.com/1iug1Im.gif' title='Search' width='' alt='Search' />
<img src='https://imgur.com/ntAEwRG.gif' title='No Internet Connection' width='' alt='No internet connection'/>

## Notes

## Open-source libraries used

- [Retrofit](http://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java
- [Glide](https://github.com/bumptech/glide) - A fast and efficient image loading library for Android

## License

    Copyright 2018 Duong Van Hung

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


