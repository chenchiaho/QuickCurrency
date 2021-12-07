# QuickCurrency
=======================

I want to begin by saying thank you for reviewing this project.
It's a great practice and I had a lot of fun building it.

This App adopted MVVM architecture since it's one of the most modern and popular one, and it comes with various benefits.
The file structure is more clean, you can easily tell which file might belong to which folder,
it separates concerns between layers, 
and since ViewModel is life-cycle aware, it automatically helps you save and retrieve data when turning the phone, which is convenient.

I used Moshi to handle and parse Json file. I chose it over Gson simply because I'm more experienced with it,
but it did take me some time to manually handle all the data for different currencies (ex: @Json(name = "SGD") val SGD: Double).
There seems to be a limitation for how many currency data it can receive with 1 API call, so I omitted some less known currencies in the project.

And for some reason the data for USD is missing when the base_currency is USD,
so I manually added it (ex: @Json(name = "USD") var USD: Double = 1.0)

The part that I wasn't satisfied with is that I could not find a better way to handle the long list of currency data in the viewModel 
(for ex: "SGD" -> rates.SGD). That I needed to type them out one by one. I feel that there has to be a better way.

Overall, I enjoyed building the app. Thank you so much for the time!

Kevin
