# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## Server Design Sequence Diagram

https://sequencediagram.org/index.html#initialData=actor%20Client%0Aparticipant%20Server%0Aparticipant%20Service%0Aparticipant%20DataAccess%0Adatabase%20db%0A%0Agroup%20%23navy%20Registration%20%23white%0AClient%20-%3E%20Server%3A%20%5BPOST%5D%20%2Fuser%5Cn%7Busername%2C%20password%2C%20email%7D%0AServer%20-%3E%20Service%3A%20register(username%2C%20password%2C%20email)%0AService%20-%3E%20DataAccess%3A%20getUser(username)%0ADataAccess%20-%3E%20db%3A%20SELECT%20username%20from%20user%0ADataAccess%20--%3E%20Service%3A%20null%0AService%20-%3E%20DataAccess%3A%20createUser(username%2C%20password)%0ADataAccess%20-%3E%20db%3A%20INSERT%20username%2C%20password%2C%20email%20INTO%20user%0AService%20-%3E%20DataAccess%3A%20createAuth(username)%0ADataAccess%20-%3E%20db%3A%20INSERT%20username%2C%20authToken%20INTO%20auth%0ADataAccess%20--%3E%20Service%3A%20authToken%0AService%20--%3E%20Server%3A%20authToken%0AServer%20--%3E%20Client%3A%20200%5Cn%7BauthToken%7D%0Aend%0A%0Agroup%20%23orange%20Login%20%23white%0AClient%20-%3E%20Server%3A%20%5BPOST%5D%20%2Fsession%5Cn%7Busername%2C%20password%7D%0AServer-%3EService%3Alogin(username%2Cpassword)%0AService-%3EDataAccess%3AgetUser(username)%0ADataAccess-%3Edb%3ASELECT%20username%20from%20user%5CnSELECT%20password%20from%20user%0ADataAccess%3C--db%3Ausername%2C%20password%0AService%3C--DataAccess%3Ausername%2Cpassword%0A%0AService-%3EService%3AisPasswordMatching()%0AService-%3EDataAccess%3AcreateAuth(username)%0ADataAccess-%3Edb%3AINSERT%20username%20INTO%20auth%5CnINSERT%20authToken%20INTO%20auth%0ADataAccess%3C--db%3AauthToken%2Cusername%0AService%3C--DataAccess%3AauthToken%2C%20username%0AServer%3C--Service%3AauthToken%2C%20username%0AClient%3C--Server%3A%5B200%5D%20%7B%20%22username%22%3A%22%22%2C%20%22authToken%22%3A%22%22%20%7D%0Aend%0Agroup%20%23green%20Logout%20%23white%0AClient%20-%3E%20Server%3A%20%5BDELETE%5D%20%2Fsession%5CnauthToken%0AServer-%3EService%3Alogout(authToken)%0AService-%3EDataAccess%3Adelete(authToken)%0ADataAccess-%3Edb%3Adelete%20authToken%20from%20auth%0AService%3C--DataAccess%3AComplete%0AServer%3C--Service%3AComplete%0AClient%3C--Server%3A%5B200%5D%20OK%0Aend%0A%0Agroup%20%23red%20List%20Games%20%23white%0AClient%20-%3E%20Server%3A%20%5BGET%5D%20%2Fgame%5CnauthToken%0AServer%20-%3E%20Service%3A%20listGames(authToken)%0AService%20-%3E%20DataAccess%3A%20validateAuthToken(authToken)%0ADataAccess%20-%3E%20db%3A%20SELECT%20authToken%20FROM%20auth%20WHERE%20authToken%20%3D%20%3F%0ADataAccess%20--%3E%20Service%3A%20Valid%0AService%20-%3E%20DataAccess%3A%20getGamesList()%0ADataAccess%20-%3E%20db%3A%20SELECT%20*%20FROM%20games%0ADataAccess%20--%3E%20Service%3A%20gamesList%0AService%20--%3E%20Server%3A%20gamesList%0AServer%20--%3E%20Client%3A%5B200%5D%20%7B%20%22games%22%3A%20%5B%7B%22gameID%22%3A%201234%2C%20%22whiteUsername%22%3A%22%22%2C%20%22blackUsername%22%3A%22%22%2C%20%22gameName%3A%22%22%7D%20%5D%7D%0Aend%0A%0Agroup%20%23purple%20Create%20Game%20%23white%0AClient%20-%3E%20Server%3A%20%5BPOST%5D%20%2Fgame%20%7BauthToken%2C%20gameName%7D%0AServer%20-%3E%20Service%3A%20createGame(authToken%2C%20gameName)%0AService%20-%3E%20DataAccess%3A%20validateAuthToken(authToken)%0ADataAccess%20-%3E%20db%3A%20SELECT%20authToken%20FROM%20auth%20WHERE%20authToken%20%3D%20%3F%0AService%20-%3E%20DataAccess%3A%20insertGame(gameName)%0ADataAccess%20-%3E%20db%3A%20INSERT%20INTO%20games%20(gameName)%20VALUES%20(%3F)%0ADataAccess%20--%3E%20Service%3A%20gameID%0AService%20--%3E%20Server%3A%20gameID%0AServer%20--%3E%20Client%3A%5B200%5D%20%7B%20%22gameID%22%3A%201234%20%7D%0Aend%0A%0Agroup%20%23yellow%20Join%20Game%20%23black%0AClient%20-%3E%20Server%3A%20%5BPUT%5D%20%2Fgame%5CnauthToken%5Cn%7BClientColor%2C%20gameID%7D%0AServer%20-%3E%20Service%3A%20joinGame(authToken%2C%20gameID%2C%20playerColor)%0AService%20-%3E%20DataAccess%3A%20validateAuthToken(authToken)%0ADataAccess%20-%3E%20db%3A%20SELECT%20authToken%20FROM%20auth%20WHERE%20authToken%20%3D%20%3F%0AService%20-%3E%20DataAccess%3A%20updateGamePlayer(gameID%2C%20playerColor)%0ADataAccess%20-%3E%20db%3A%20UPDATE%20games%20SET%20playerColor%20%3D%20%3F%20WHERE%20gameID%20%3D%20%3F%0ADataAccess%20--%3E%20Service%3A%20Success%0AService%20--%3E%20Server%3A%20Success%0AServer%20--%3E%20Client%3A%5B200%5D%20OK%0Aend%0A%0Agroup%20%23gray%20Clear%20application%20%23white%0AClient%20-%3E%20Server%3A%20%5BDELETE%5D%20%2Fdb%0AServer%20-%3E%20Service%3A%20clearDatabase()%0AService%20-%3E%20DataAccess%3A%20clearAll()%0ADataAccess%20-%3E%20db%3A%20DELETE%20FROM%20users%3B%20DELETE%20FROM%20games%3B%20DELETE%20FROM%20authTokens%3B%0ADataAccess%20--%3E%20Service%3A%20Success%0AService%20--%3E%20Server%3A%20Success%0AServer%20--%3E%20Client%3A%5B200%5D%20OK%0Aend%0A


https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWOZVYSnfoccKQCLAwwAIIgQKAM4TMAE0HAARsAkoYMhZkwBzKBACu2GAGI0wKgE8YAJRRakEsFEFIIaYwHcAFkjAdEqUgBaAD4WakoALhgAbQAFAHkyABUAXRgAej0VKAAdNABvLMpTAFsUABoYXCl3aBlKlBLgJAQAX0wKcNgQsLZxKKhbe18oAAoiqFKKquUJWqh6mEbmhABKDtZ2GB6BIVFxKSitFDAAVWzx7Kn13ZExSQlt0PUosgBRABk3uCSYCamYAAzXQlP7ZTC3fYPbY9Tp9FBRNB6BAIDbULY7eRQw4wECDQQoc6US7FYBlSrVOZ1G5Y+5SJ5qBRRACSADl3lZfv8ydNKfNFssWjA2Ul4mDaHCMaFIXSJFE8SgCcI9GBPCTJjyaXtZQyXsL2W9OeKNeSYMAVZ4khAANbofWis0WiG0g6PQKwzb9R2qq22tBo+Ew0JwyLey029AByhB+DIdBgKIAJgADMm8vlzT6I2h2ugZJodPpDEZoLxjjAPhA7G4jF4fH440Fg6xQ3FEqkMiopC40Onuaa+XV2iGoCFJf0EFWkGh1VNyoOFutxygQjLXRFjmcLv2UFq7q6Qi93l8fsaAcCIKCJnlj99fguZECQcbndrXQAed0vHcU2b8gPsJ+gRrg8EQ-g+mjLmOnoIvYsR-nUACygggN4aBaKMS4wauLqgQqSoWrOmqvvuDyHkybIclyVw8vaYqZp4eSUYavwMb6doivRTogVIQEvGx2blDuAHiEBPFygJfqVMJI5AcuESSeg0k0WUmD+PGcktlAETRCmyZpPkMAAEQ7kZERGUZlRGYpaBmRZMC5mg+aFgYxg6CgdqVlo+jMLW3i+GpjbMB6XRRNEfCfG8SRvGk6RdhIPZ5DZUajsE8mTt5KqjDZWHouIOFvqBMgoAgJwoNlFrsWge7YhI5ERMVpW+GGVVPpeYYiSgYm4YccCXtgTUcLJ7ryX1JQDWVgUBGAmmhbpqZpPEADSmB5gWuiuUYgyPh8wwwAA4jyjx+fWU3xgyI5hftUWxVoPJJZV2YpRdMFRMgDiHWUEgVVmfq5YGmKFTiVDAMgci+Mqv3oD94Z-SRtW6ky5CRaeNkwAAYlY8SIWGMAAOoABKGm8LXZjAAC8MAAPzwzq7rNnlCIwAAaqDSD5suDLiUcJyfZIu0OJhtOuojrwo78ABUGNYzjd1fcL0L0707BHEdAtYJzSuXTAcv88Mz1K+pGA6XpBnGbrEhmTE+RGbrzJ8FbACMiYAMwACxWXWvhEiaKB2ZZxkKAgoDWj7Uz+1Zuusjy5lGa0MApI5+baBtxbYHoUATfA+LNXzHj+Q200vaFMQJMkt20Rmj1STrPLR2Uw5acX8LyjnKB8zDVWVFHxGa9KPVyjAINgwRUMzjlCv0j0eq3qj1d2pj2O44TxOk36FPU51XMD1E07ZGAHc92UNV088SPMUanG119MCjEfu4s8IHynG8ZC31TJ8i1rr3Xyg9tb9-Euds+AGx6EbBM819IwEMrbHk9snauzdg5VaTl1pFmMOYEqk53AwAAFIQGnAdWiRgg4hzOk2XorZYinA7OkXWD0x7pnAX1ScUBu5wL4I3LozcVYwAAFYELQB3Gy7Cyj2wpMHTBUAWHQH+lKGA3Mh5s3BigSGsNoYT3EqLZGJ5WLzzcIvHGDF8ZEysCTNGlMaZ9wUTvP42AVF81iJI4kwCJHACkTIqAn9FZnyiKcWIfBhDRV-o8d495nHSIgKwjeVMTGr2ATEyeboQot3IHoWUACUmhjIOk10oDQjgJNgtGAy0UHJxcsWHQ7jYyKlgMAbAA1CDOFcPnU64Dm6tgil8aKsV1AGwZqkkApU8C3CUCoIW1jFFDNqcIFEQstHTyRl0qKJNDHGgkAAbgUZFYJayLZbOWbsmWa90CbKSTGeSaSMmayydpK5eSRwxkKZAxaK01pAA


## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
