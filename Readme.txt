## About: 
Document classification REST Service takes in request to classify a single document and returns the predicted class and confidence.
The UI also has a feature to test the classifier against supplied test data in csv format(in same format as the provided train data). Returns summary of classification and also detailed statistics.


## How to deploy:
1) Download the documentclassification-api-0.0.1.jar (in the target folder)
2) Place the train csv data file in the same folder
3) run command: java -jar documentclassification-api-0.0.1.jar <train-filename.csv>


## Rest Services Specs:

Service endpoints: 1) /                               (loads user Interface for hitting service/testing classifier)
                   2) /documentclassification         (this endpoint handles all requests)
                   
## Supported methods = GET/POST

1) Get : /documentclassification?words=word1+word2+word3+...        (Get request to this endpoint without parameter opens the UI)
2) POST: /documentclassification   (Requires request body in either json,xml,text and gives appropriate output)
    eg: JSON Request Body:
    {
	    "words":"e04a09c87692 d6b72e591b91 5d066f0246f1 ed41171e2e6d 59260a2781dc ..."
    }
    
    eg JSON Response:
    {
	    "prediction": "class",
      "confidence": 0.543
    }
    
    eg: XML Request Body:
    <document>
        <words>e04a09c87692 d6b72e591b91 5d066f0246f1 ed41171e2e6d 59260a2781dc ...</words>
    </document>
    
    eg: XML Response:
    <document>
        <prediction>class</prediction>
	<confidence>0.543</confidence>
    </document>

All service responses depend upon the request content-header type.
1) Content-Type = application/json ==> Response Content-Type = application/json
2) Content-Type = application/xml ==> Response Content-Type = application/xml
3) Content-Type = text/html ==> Response Content-Type = text/html
4) Request Content-Type missing ==> Response Content-Type = text/html

*** Max Http Request Header Size= 20KB   (to accomodate larger documents in the GET request header parameter)


## UI Specs:
### Options available:
1) Use input field to classify a single document(enter space separated words). Returns predicted class and confidence.
2) Test model against test data from csv(same format as given training data). Returns summary of classification and also detailed statistics.


## Classifier Specs:
1) Classifier: C4.5 Decision tree using J48(Weka)
2) Features: Top 150 words with maximum TF-IDF scores and their counts.

