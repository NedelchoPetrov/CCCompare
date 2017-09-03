# CCCompare
Task1_Initial

This is what I have done for now. In the current state the app shows the top 5 cryptocurrencies but the data for their
prices and state is taken only once on startup, so it's not dynamically. It's also a problem that starting the app takes
a bit too much time in my opinion. The design should be made better too.


Short explanation of which classes the project contains and how do they work together:

  Additional Classes:
    - 'CustomAdapter' for creating the ExpandableListView (ELV).
    - 'Currency' is the class for the Objects that are inflated into ELV.
    - 'JsonTask' for sending api requests to cryptocompare.com and getting JSON String as a result.
    - 'DownloadImageTask' for downloading the Icons of the currencies (Same as 'JsonTask').
    - 'SellActivity' and 'BuyActivity' are the main classes for the empty activities that open upon clicking the corresponding buttons.
    - 'MainActivity'
    
    The implementation follows the following idea:
      To inflate the ELV we need to have the top 5 currencies as Objects of 'Currency'. They have to chosen from an API query
      and initialised with the corresponding attributes such as 'name', 'icon', 'priceUp' and 'currentPrice'. To prepare the
      values for these attributes we initialise corresponding arrays with 5 empty slots each. Further happens the following:
       
       1.1 'JsonTask' instance get response from https://www.cryptocompare.com/api/data/coinlist/.
       1.2 'manipulate()' extracts whch are the top 5, and fills the arrays 'names[]' and 'icons[]'
       
       2.1 The URL for the second query is constructed, the called with 'JSONTask' instance
       2.2 'prices[]' is filled with the extracted data from the response with 'manipulate2()'
       
       3.1 For filling 'priceUps[]' 5 queries are made and the values are extracted immediately.
       
       4.1 The objects for the currencies are constructed and the adapter fills the ELV with them. Here ends 'onCreate()' method.
       
       The adapter  downloads the icon for each currency with 5 more queries.
       


About the problems I mentioned above:

  - The app makes a total of 12 queries to get the required data which, I think, is the main factor for the performance. I'm not
  sure if there's a faster way.
  
  - A better implementation of the project will be one that shows the prices and state dynamically. I think this is a TODO.
  
  - About the design... It's bad and must be made better. I have no experience of making design that looks good on every
  OS version and device and it will take me some time to learn. And I cosnidered the time that I will spend for making it look
  good on my testing device a waste.
  
  
       
       
    
  
