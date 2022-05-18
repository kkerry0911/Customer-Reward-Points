# Custmoer Reward Point

## API specification:

Get Mappping, without Authentication and Authorization

EndPointType : GET
EndPoint URI/ URL : http://localhost:8056/api/v1/customer/{customerId}/rewards

### ResponseFormat: 
                    String customerId, Integer rewardPointsForThreeMonthsDuration , 
                    Integer perMonthRewardsAverage

<h2> DataSet </h2>

**Customer List**
1. *Before Transaction Processed*

|QuarterlyRewardsCollected | perMonthRewardsAverage | CustomerId | 
| :---                     |    :----:              |    ---:    |           
|0                         | 0                      |  C1        |
|0                         | 0                      |  C2        |
|0                         | 0                      |  C3        |
|0                         | 0                      |  C4        |

2. *After Transaction Processed*

|QuarterlyRewardsCollected | perMonthRewardsAverage | CustomerId |
| :---                    |         :----:          |  ----:     |
|208                       | 69                     |  C1        |
|0                         | 0                      |  C2        |
|1300                      | 1300                   |  C3        |
|0                         | 0                      |  C4        |

**Transaction List**

|AssociatedCustomerId | TransactionId | spentAmount | currency | transactionDate | isTransactionAlreadyRewarded
| :---        |    :----:   |          :----: |   :----:  |  :----:  |  :----:  |
C1                   |   "1"         | 54.0        |  USD     | 2022-04-04      |  false
C1                   |   "2"         | 100.0       |  USD     | 2022-03-04      |  false
C2                   |   "3"         | 10.0        |  USD     | 2022-04-04      |  false
C2                   |   "4"         | 10.0        |  USD     | 2022-04-04      |  false
C3                   |   "5"         | 1000.0      |  USD     | 2022-04-04      |  false
C3                   |   "6"         | 1000.0      |  USD     | 2022-04-04      |  false
C4                   |   "7"         | 1000.0      |  USD     | 2022-04-04      |  true
C4                   |   "8"         | 49.0        |  USD     | 2022-04-04      |  false


**Results Images**

have been placed at src/main/resources/images directory




### Implementation Insights:

1. The mock data is loaded during coonstructor based injection
2. After generating the applicable rewards, the state of those transactions gets updated to "REWARDED" , by means of a boolean flag
3. If you rehit this request , the error will appear telling that , it has already been redeemed.

try hitting localhost:8056/api/v1/customer/c1/rewards ,if you are running locally.

If you are running docker container, please change it port to 8080


**Java Runtime Environment : 17**
**Package Manager : Gradle 7.2 or upper versions**


## How to Run the Code:

1. Load the code to some IDE ( preferably )
2. Refresh Gradle dependencies
3. Look for a clean build
4. Run as springboot project
5. Hit the URI mentioned above with GET type

## How to Run Test Cases:

1. Select the CustomerRewardsTestSuite.java file
2. Run or debug as Junit
3. Observe the results at IDE


NOTE - For now only application.properties file carries the externalized ports related info, it can enhanced to support multiple profiles 

## How to dockerize:

1. Get to the root of this project
2. Execute following commands -

    a. docker build -t rewardscal .
    b. docker run rewardscal -p 8056:8080 -itd exec bash
    Then you can access endpoint at localhost:8080
    b. docker tag rewardscal registry/repoaddress
    c. docker push

## Health Check endpoint :

http://localhost:8056/actuator/health