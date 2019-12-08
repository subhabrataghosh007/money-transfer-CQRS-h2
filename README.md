# money-transfer-app-h2
money-transfer-app with H2 database

Framework: Quarkus<br>
Database: H2


<h2>How to run:</h2>
`./mvnw clean install`<br>
`java -jar target/money-transfer-app-h2-1.0-runner.jar` <br><br>

<h2>SWAGGER URL</h2>
http://localhost:8080/swagger-ui/

<h1>Wallet Resources:</h1>

Base URL:<br />
`http://localhost:8080/v1/wallets`

GET "<i>/</i>"
-
Find all wallets
Success Response code: 200
Sample Success Response:
```
[
    {
        "phoneNumber": "9832799830",
        "balance": 3000
    },
    {
        "phoneNumber": "9832799831",
        "balance": 3000
    }
]
```

POST "<i>/wallet</i>"
-
Create a new Wallet
Sample Request:
```
{
	"phoneNumber": "9832799828",
	"balance": 3000
}
```

Response code: 200
Sample Success Response:
```
{
    "status": "Created",
    "description": "Account registered successfully"
}
```
Sample Request:
```
{
	"phoneNumber": "9832799828",
	"balance": 0
}
```
Response code: 400
Sample Success Response:
```
{
    "errorMessage": "Amount Should be Positive",
    "statusCode": 400
}
```


PUT "<i>/wallet/addmoney/{phoneNumber}</i>"
-
Add some additional money to a specific wallet.
`http://localhost:8080/v1/wallets/wallet/addmoney/9832799830`
Sample Request:
```
{
	"phoneNumber": "9832799830",
	"balance": 100
}
```
Response code: 200
Sample Success Response:
```
{
    "status": "OK",
    "description": "Amount 100 added to the Account 9832799830"
}
```

Sample Request:
```
{
	"phoneNumber": "9832799834",
	"balance": 100
}
```
Response code: 404
Sample Success Response:
```
{
    "errorMessage": "Wallet with mobile number 9832799822 not exists",
    "statusCode": 404
}
```




<h1>Money Transfer Resources:</h1>

Base URL:<br />
http://localhost:8080/v1/transactions

GET "<i>/</i>"
-
Response code: 200
Sample Success Response:
Find all money transfer transactions
```
[
    {
        "transactionId": "70d57d84-fdc2-418e-98ba-03bd1efd56dc",
        "sender": "9832799826",
        "receiver": "9832799827",
        "amount": 600,
        "tag": "Transfering..",
        "status": "success",
        "transferDateTime": "2019-12-01T19:27:57.869",
        "statusCode": "00",
        "description": "Transaction Approved Successfully"
    }
]
```


GET "<i>/transaction/{id}/id</i>"
-
Find money transfer transaction by transactionID
`transaction/70d57d84-fdc2-418e-98ba-03bd1efd56dc/id`
Response code: 200
Sample Success Response:
```
{
        "transactionId": "70d57d84-fdc2-418e-98ba-03bd1efd56dc",
        "sender": "9832799826",
        "receiver": "9832799827",
        "amount": 600,
        "tag": "Transfering..",
        "status": "success",
        "transferDateTime": "2019-12-01T19:27:57.869",
        "statusCode": "00",
        "description": "Transaction Approved Successfully"
}
```


GET "<i>/transaction/{sender}/sender</i>"
-
Find all money transfer transactions by a specific sender. Here {sender} is valid sender.

`/transaction/9832799826/sender`
Response code: 200
Sample Success Response:
```
{
        "transactionId": "70d57d84-fdc2-418e-98ba-03bd1efd56dc",
        "sender": "9832799826",
        "receiver": "9832799827",
        "amount": 600,
        "tag": "Transfering..",
        "status": "success",
        "transferDateTime": "2019-12-01T19:27:57.869",
        "statusCode": "00",
        "description": "Transaction Approved Successfully"
}
```

POST "<i>/transaction</i>"
-
A new transaction.

Sample Request:
```
{
	"sender": "9832799830",
	"receiver": "9832799831",
	"amount": 600,
	"tag": "Transfering.."
}
```

Response code: 200
Sample Success Response:
```
{
    "transactionId": "6ccd8b44-a737-4234-be6d-082633631a5f",
    "sender": "9832799830",
    "receiver": "9832799831",
    "amount": 600,
    "tag": "Transfering..",
    "status": "success",
    "transferDateTime": "2019-12-02T00:52:26.198",
    "statusCode": "00",
    "description": "Transaction Approved Successfully"
}
```
